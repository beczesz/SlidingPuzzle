package com.exarlabs.android.slidingpuzzle.utils.generator;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.exarlabs.android.slidingpuzzle.model.board.BoardState;
import com.exarlabs.android.slidingpuzzle.model.board.Move;
import com.exarlabs.android.slidingpuzzle.utils.Pair;

/**
 * This is a utility class for generating boards with fixed complexity.
 * The complexity of the board is the number of steps needed for an optimal solution.
 * For this we will use a DFS algorithm, since we don't need performance here, just the solutions.
 * <p/>
 * The results are compressed and written into a file, so later on these files can be used to
 * efficiently get a fixed complexity solution.
 * <p/>
 * Created by becze on 9/18/2015.
 */
public class SlidingPuzzleSolutionsGenerator {

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

    private int mBoardDimension;

    // return s only the fist couple of solutions.
    private int mNumberOfSolutions;

    // Flag to indicate that we need random solutions
    private final boolean mRandomizedSolutions;


    private int mDepth;

    private HashMap<BoardState, String> mSolutions;

    private BoardState mInitialState;


    // ------------------------------------------------------------------------
    // CONSTRUCTORS
    // ------------------------------------------------------------------------

    /**
     * Cosntruct s anew solution generator with the gien board dimension and depth.
     *
     * @param boardDimension
     * @param depth
     */
    public SlidingPuzzleSolutionsGenerator(int boardDimension, int depth, int numberOfSolutions, boolean randomizedSolutions) {
        mBoardDimension = boardDimension;
        mDepth = depth;
        mSolutions = new HashMap<>();
        mNumberOfSolutions = numberOfSolutions;
        mRandomizedSolutions = randomizedSolutions;
    }


    // ------------------------------------------------------------------------
    // METHODS
    // ------------------------------------------------------------------------

    /**
     * Generate solutions with the fixed
     */
    public void generateSolutions() {
        mInitialState = new BoardState(mBoardDimension);

        // start to generate the steps
        generateSteps(mInitialState, 0, "");
    }

    private void generateSteps(BoardState state, int i, String path) {

        // Display the progress
        if (i == 4) {
            System.out.print("Progress: " + (mSolutions.size() / mNumberOfSolutions) + "\r");
        }

        // Just stop when we have the necessary solutions
        if (mSolutions.size() == mNumberOfSolutions) {
            return;
        }

        if (i == mDepth) {
            mSolutions.put(state, path);
        } else {
            // If we arrived to the same state, but before the desired depth then remove from the solutions
            if (mSolutions.containsKey(state)) {
                mSolutions.remove(state);
            }

            List<Pair<BoardState, Move.Direction>> possibleSteps = state.getPossibleSteps();

            if (mRandomizedSolutions) {
                Collections.shuffle(possibleSteps);
            }

            // For each possible step repeat
            for (Pair<BoardState, Move.Direction> step : possibleSteps) {
                generateSteps(step.first, i + 1, path  + step.second.ordinal());
            }

        }
    }

    public BoardState getInitialState() {
        return mInitialState;
    }

    public HashMap<BoardState, String> getSolutions() {
        return mSolutions;
    }

    // ------------------------------------------------------------------------
    // GETTERS / SETTTERS
    // ------------------------------------------------------------------------
}
