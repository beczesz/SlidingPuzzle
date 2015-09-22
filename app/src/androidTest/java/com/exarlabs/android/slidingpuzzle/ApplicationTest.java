package com.exarlabs.android.slidingpuzzle;

import android.test.ApplicationTestCase;

import com.exarlabs.android.slidingpuzzle.business.board.GameHandler;
import com.exarlabs.android.slidingpuzzle.model.board.BoardState;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<SlidingPuzzleApplication> {


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

    // ------------------------------------------------------------------------
    // CONSTRUCTORS
    // ------------------------------------------------------------------------

    public ApplicationTest() {
        super(SlidingPuzzleApplication.class);
    }

    // ------------------------------------------------------------------------
    // METHODS
    // ------------------------------------------------------------------------


    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    /**
     * Test the GameHandler module mocking the solutions
     */
    public void testGameHandler() {
        GameHandler gameHandler = new GameHandler();
        gameHandler.initializeGame();
        BoardState boardState = gameHandler.getBoardState();


        // Check if the newly created board state is indeed in the initial state.
        assertTrue(boardState.equals(new BoardState(boardState.getDimension())));
        assertTrue(true);

        gameHandler.shuffle();
    }



    // ------------------------------------------------------------------------
    // GETTERS / SETTTERS
    // ------------------------------------------------------------------------


}
