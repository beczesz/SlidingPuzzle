package com.exarlabs.android.slidingpuzzle.business.board;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import android.content.SharedPreferences;
import android.os.SystemClock;

import com.exarlabs.android.slidingpuzzle.SlidingPuzzleApplication;
import com.exarlabs.android.slidingpuzzle.business.AppConstants;
import com.exarlabs.android.slidingpuzzle.business.score.ScoreHandler;
import com.exarlabs.android.slidingpuzzle.business.solutions.SolutionsHandler;
import com.exarlabs.android.slidingpuzzle.model.board.BoardState;
import com.exarlabs.android.slidingpuzzle.model.board.Move;
import com.exarlabs.android.slidingpuzzle.model.dao.Play;
import com.exarlabs.android.slidingpuzzle.utils.Pair;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

/**
 * Responsible for keeping track of the current game's state and moves.
 * Created by becze on 9/17/2015.
 */
public class GameHandler {

    // ------------------------------------------------------------------------
    // TYPES
    // ------------------------------------------------------------------------

    // ------------------------------------------------------------------------
    // STATIC FIELDS
    // ------------------------------------------------------------------------

    private static final String TAG = GameHandler.class.getSimpleName();


    // ------------------------------------------------------------------------
    // STATIC METHODS
    // ------------------------------------------------------------------------


    // ------------------------------------------------------------------------
    // FIELDS
    // ------------------------------------------------------------------------


    // An optimal solution containing the initial state and the list of moves until the solution.
    private Pair<BoardState, List<Move>> mOptimalSolution;

    // Field for storking the current state of the game
    private BoardState mBoardState;

    private List<Move> mUserMoves;

    /**
     * Represents a play record in the database
     */
    private Play mCurrentPlay;
    private long mCurrentPlayStart;

    @Inject
    public SharedPreferences mPrefs;

    @Inject
    public SolutionsHandler mSolutionsHandler;

    @Inject
    public ScoreHandler mScoreHandler;


    private PublishSubject<GamEvent> mPublishSubsciber;

    // ------------------------------------------------------------------------
    // CONSTRUCTORS
    // ------------------------------------------------------------------------

    public GameHandler() {
        // Inject the dependencies
        SlidingPuzzleApplication.component().inject(this);

        // Create an empty list of the user move
        mUserMoves = new ArrayList<>();
    }


    // ------------------------------------------------------------------------
    // METHODS
    // ------------------------------------------------------------------------

    /**
     * Subscribe the subscriber to the GamEvents
     *
     * @param subscriber
     * @return
     */
    public Subscription subscribe(Subscriber<GamEvent> subscriber) {
        //@formatter:off
        return getPublishSubsciber()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(subscriber);
        //@formatter:on
    }


    /**
     * Initialize the game, with a new board.
     */
    public void initializeGame() {
        int boardSize = mPrefs.getInt(AppConstants.SP_KEY_BOARD_SIZE, AppConstants.DEFAULT_BOARD_SIZE);
        mBoardState = new BoardState(boardSize);

        // reset the rest of the fields
        mOptimalSolution = null;
        mUserMoves.clear();
        mCurrentPlay = null;
        mCurrentPlayStart = 0;


        // notify the subscribers that the game has been reset
        notifySubscribers(new GamEvent(GamEvent.GAME_RESET, mBoardState));
    }

    /**
     * Starts a new game by shuffling the board
     */
    public void startNewGame() {
        mUserMoves.clear();
        mCurrentPlay = new Play();
        mCurrentPlay.setStartDate(new Date());
        mCurrentPlayStart = SystemClock.elapsedRealtime();

        shuffle();
    }

    /**
     * Finish the game.
     *
     * @param move
     */
    private void finishGame(Move move) {
        // Save the current game
        saveCurrentPlay();

        // Notify all the subscribers that the game is solved
        notifySubscribers(new GamEvent(GamEvent.GAME_SOLVED, move));
    }


    /**
     * Shuffles the board taking a random solution.
     */
    public void shuffle() {
        // Get a random solution and initialize the current state with it.
        mOptimalSolution = getRandomSolution();
        mBoardState = new BoardState(mOptimalSolution.first.getTiles());
        mUserMoves.clear();

        // Notify all the subscribers that we have changed the model.
        notifySubscribers(new GamEvent(GamEvent.GAME_SHUFFLED, mBoardState));
    }

    /**
     * Make the user move
     *
     * @param move
     */
    public void makeMove(Move move) {
        mBoardState.makeMove(move);
        addUserMove(move);
        notifySubscribers(new GamEvent(GamEvent.MOVE_MADE, move));


        // check if the board is solved and if yes then stop the game and save the play
        if (mBoardState.isSolved()) {
            finishGame(move);
        }
    }

    /**
     * Notify all the subscribers that we have changed the model.
     *
     * @param event
     */
    private void notifySubscribers(GamEvent event) {
        getPublishSubsciber().onNext(event);
    }

    /**
     * Saves the current game into the database.
     */
    private void saveCurrentPlay() {
        if (mCurrentPlay != null) {
            mCurrentPlay.setDuration(getGameDuration());
            mCurrentPlay.setNumberOfMoves(mUserMoves.size());
            mCurrentPlay.setEncodedMoves(GameUtil.encodeMoves(mUserMoves));
            mCurrentPlay.setBoardSize(mBoardState.getDimension());

            // save the current play
            mScoreHandler.savePlay(mCurrentPlay);
            mCurrentPlay = null;
        }
    }

    /**
     * @return the number of milliseconds since the game start.
     */
    public int getGameDuration() {
        return (int) (SystemClock.elapsedRealtime() - mCurrentPlayStart);
    }

    /**
     * Reads a random solution from the database and parses it to a BoardState and a list of moves as the optimal solution.
     *
     * @return
     */
    private Pair<BoardState, List<Move>> getRandomSolution() {
        // get a renadom encoded solution
        int boardSize = mPrefs.getInt(AppConstants.SP_KEY_BOARD_SIZE, AppConstants.DEFAULT_BOARD_SIZE);
        // TODO make it generic in the future
        int opimalNrOfSteps = boardSize == 3 ? 25 : 64;
        byte[] encodedSolution = mSolutionsHandler.getRandomSolution(boardSize);
        // encode the solution
        return GameUtil.decodeSolution(encodedSolution, opimalNrOfSteps, boardSize);
    }

    /**
     * If there is a random solution generated then sets the current state to the generated state.
     * It there is no random solution generated then we just simply generate one.
     * Then it makes a move with the given time interval until it solves the game.
     */
    public void replayOptimalSolution(int playRate) {
        if (mOptimalSolution == null) {
            shuffle();
        } else {

            // Board state
            mBoardState = new BoardState(mOptimalSolution.first.getTiles());

            // Notify all the subscribers that we have changed the model.
            getPublishSubsciber().onNext(new GamEvent(GamEvent.GAME_SHUFFLED, mBoardState));
        }

        //@formatter:off
        // We just create an Observable which with the given rate it plays the game.
        Observable.interval(playRate, TimeUnit.MILLISECONDS)
                        .take(mOptimalSolution.second.size())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<Long>() {

            int index = 0;

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Long aLong) {
                makeMove(mOptimalSolution.second.get(index++));
            }
        });
        //@formatter:on


    }

    /**
     * Saves a user move
     *
     * @param object
     * @return
     */
    public boolean addUserMove(Move object) {
        return mUserMoves.add(object);
    }

    public int getNumberOfUserMoves() {
        return mUserMoves != null ? mUserMoves.size() : 0;
    }

    // ------------------------------------------------------------------------
    // GETTERS / SETTTERS
    // ------------------------------------------------------------------------

    /**
     * @return publish subsciber instance which will help us to implement an event bus.
     */
    private PublishSubject<GamEvent> getPublishSubsciber() {
        if (mPublishSubsciber == null) {
            mPublishSubsciber = PublishSubject.create();
        }
        return mPublishSubsciber;
    }

    public BoardState getBoardState() {
        return mBoardState;
    }

    public void setBoardState(BoardState boardState) {
        mBoardState = boardState;
    }

    /**
     * @return the optimal solution's BoardState
     */
    public BoardState getOptimalSolutionBoardState() {
        return mOptimalSolution.first;
    }

    /**
     * @return optimal solution's steps.
     */
    public List<Move> getoptimalSolutionMoves() {
        return mOptimalSolution.second;
    }


}
