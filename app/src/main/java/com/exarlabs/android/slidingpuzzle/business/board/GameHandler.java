package com.exarlabs.android.slidingpuzzle.business.board;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import android.content.SharedPreferences;

import com.exarlabs.android.slidingpuzzle.SlidingPuzzleApplication;
import com.exarlabs.android.slidingpuzzle.business.AppConstants;
import com.exarlabs.android.slidingpuzzle.business.solutions.SolutionsHandler;
import com.exarlabs.android.slidingpuzzle.model.board.BoardState;
import com.exarlabs.android.slidingpuzzle.model.board.Move;
import com.exarlabs.android.slidingpuzzle.utils.Pair;

import rx.Subscriber;
import rx.Subscription;
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

    @Inject
    public SharedPreferences mPrefs;

    @Inject
    public SolutionsHandler mSolutionsHandler;

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
        if (mPublishSubsciber == null) {
            mPublishSubsciber = PublishSubject.create();
        }

        return mPublishSubsciber.subscribe(subscriber);
    }


    /**
     * Initialize the game, with a new board.
     */
    public void initializeGame() {
        int boardSize = mPrefs.getInt(AppConstants.SP_KEY_BOARD_SIZE, AppConstants.DEFAULT_BOARD_SIZE);
        mBoardState = new BoardState(boardSize);

        // notify the subscribers that the game has been reset
        mPublishSubsciber.onNext(new GamEvent(GamEvent.GAME_RESET, mBoardState));
    }

    /**
     * Shuffles the board taking a random solution.
     */
    public void shuffle() {
        // Get a random solution and initialize the current state with it.
        mOptimalSolution = getRandomSolution();
        mBoardState = new BoardState(mOptimalSolution.first.getTiles());

        // Notify all the subscribers that we have changed the model.
        mPublishSubsciber.onNext(new GamEvent(GamEvent.GAME_RESET, mBoardState));
    }

    /**
     * Reads a random solution from the database and parses it to a BoardState and a list of moves as the optimal solution.
     *
     * @return
     */
    private Pair<BoardState, List<Move>> getRandomSolution() {
        // get a renadom encoded solution
        int boardSize = mPrefs.getInt(AppConstants.SP_KEY_BOARD_SIZE, AppConstants.DEFAULT_BOARD_SIZE);
        byte[] encodedSolution = mSolutionsHandler.getRandomSolution(boardSize);
        // ecode the solution
        return decodeSolution(encodedSolution);
    }

    private Pair<BoardState, List<Move>> decodeSolution(byte[] encodedSolution) {
        int boardSize = mPrefs.getInt(AppConstants.SP_KEY_BOARD_SIZE, AppConstants.DEFAULT_BOARD_SIZE);
        // TODO temporary hard wired shuffle
        int[][] tiles = mBoardState.getTiles();
        if (boardSize == 3) {
            tiles[0] = new int[] { 6, 4, 8 };
            tiles[1] = new int[] { 3, 5, 7 };
            tiles[2] = new int[] { 0, 2, 1 };

        } else {
            tiles[0] = new int[] { 1, 4, 3, 10 };
            tiles[1] = new int[] { 7, 2, 13, 9 };
            tiles[2] = new int[] { 5, 15, 8, 6 };
            tiles[3] = new int[] { 12, 11, 14, 0 };
        }

        return new Pair<>(new BoardState(tiles), null);
    }

    // ------------------------------------------------------------------------
    // GETTERS / SETTTERS
    // ------------------------------------------------------------------------
    public BoardState getBoardState() {
        return mBoardState;
    }

    public void setBoardState(BoardState boardState) {
        mBoardState = boardState;
    }


}
