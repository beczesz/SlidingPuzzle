package com.exarlabs.android.slidingpuzzle.business.board;

import java.util.ArrayList;
import java.util.Collections;
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

    @Inject
    public SharedPreferences mPrefs;

    @Inject
    public SolutionsHandler mSolutionsHandler;


    private PublishSubject<GamEvent> mPublishSubsciber;


    // ------------------------------------------------------------------------
    // CONSTRUCTORS
    // ------------------------------------------------------------------------

    public GameHandler() {
        try {
            // Inject the dependencies
            SlidingPuzzleApplication.component().inject(this);
        } catch  (Exception ex){

        }

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
        return getPublishSubsciber().subscribe(subscriber);
    }


    /**
     * Initialize the game, with a new board.
     */
    public void initializeGame() {
        int boardSize = mPrefs.getInt(AppConstants.SP_KEY_BOARD_SIZE, AppConstants.DEFAULT_BOARD_SIZE);
        mBoardState = new BoardState(boardSize);

        // notify the subscribers that the game has been reset
        getPublishSubsciber().onNext(new GamEvent(GamEvent.GAME_RESET, mBoardState));
    }

    /**
     * Shuffles the board taking a random solution.
     */
    public void shuffle() {
        // Get a random solution and initialize the current state with it.
        mOptimalSolution = getRandomSolution();
        mBoardState = new BoardState(mOptimalSolution.first.getTiles());

        // Notify all the subscribers that we have changed the model.
        getPublishSubsciber().onNext(new GamEvent(GamEvent.GAME_RESET, mBoardState));
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
        return decodeSolution(encodedSolution, opimalNrOfSteps, boardSize);
    }

    private Pair<BoardState, List<Move>> decodeSolution(byte[] encodedSolution, int optimalSteps, int boardSize) {
        /**
         * Decode each byte separately. Each byte encodes 4 steps.
         */

        List<Move> moves = new ArrayList<>();

        // get an empty state with the solution
        BoardState state = new BoardState(boardSize);

        for (byte fourSteps : encodedSolution) {
            String binary = String.format("%8s", Integer.toBinaryString(fourSteps & 0xFF)).replace(' ', '0');
            for (int i = 0; i < 4; i++) {
                Move.Direction direction = Move.Direction.decode(binary.substring(i * 2, i * 2 + 2));
                if (direction != null) {

                    // Create a Move and make the reverse of it. We late on playback these moves
                    Move move = new Move(state.getPosition(BoardState.EMPTY_TILE_INDEX), direction);
                    Move reverseMove = move.reverse();
                    // We have to apply the reverse move
                    state.makeMove(reverseMove);
                    moves.add(move);

                    // If we arrived to the optimal number of steps already then we just simply stop
                    if (moves.size() == optimalSteps) {
                        break;
                    }
                }
            }
        }

        Collections.reverse(moves);
        return new Pair<>(state, moves);
    }

    // ------------------------------------------------------------------------
    // GETTERS / SETTTERS
    // ------------------------------------------------------------------------

    /**
     * @return the publish subsciber.
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
