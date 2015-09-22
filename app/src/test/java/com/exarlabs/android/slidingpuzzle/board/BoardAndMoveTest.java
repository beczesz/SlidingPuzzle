package com.exarlabs.android.slidingpuzzle.board;

import org.junit.Test;

import com.exarlabs.android.slidingpuzzle.model.board.BoardState;
import com.exarlabs.android.slidingpuzzle.model.board.Move;
import com.exarlabs.android.slidingpuzzle.utils.Pair;

import junit.framework.Assert;
import junit.framework.TestCase;


/**
 * Testst the Board BoardState and moves on applied on the board
 * Created by becze on 9/17/2015.
 */
public class BoardAndMoveTest extends TestCase {

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

    // ------------------------------------------------------------------------
    // METHODS
    // ------------------------------------------------------------------------


    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Test
    public void testStateCreation() {
        // Create a new state

        // Test if its stable to illegal arguments
        boolean exceptionThrown = false;
        try {
            new BoardState(0);
        } catch (IllegalArgumentException ex) {
            exceptionThrown = true;
        }
        Assert.assertTrue(exceptionThrown);

        // A newly created board with arbitrary dimension should be initialized with a solved state.
        for (int i = 3; i < 10; i++) {
            BoardState boardState = new BoardState(i);
            Assert.assertTrue(boardState.isSolved());
        }

    }

    @Test
    public void testDirectionAndMove() {
        // Decode test
        assertTrue(Move.Direction.decode("00") == Move.Direction.LEFT);
        assertTrue(Move.Direction.decode("01") == Move.Direction.UP);
        assertTrue(Move.Direction.decode("10") == Move.Direction.RIGHT);
        assertTrue(Move.Direction.decode("11") == Move.Direction.DOWN);

        // Reverse test
        assertTrue(Move.Direction.reverse(Move.Direction.LEFT) == Move.Direction.RIGHT);
        assertTrue(Move.Direction.reverse(Move.Direction.UP) == Move.Direction.DOWN);
        assertTrue(Move.Direction.reverse(Move.Direction.RIGHT) == Move.Direction.LEFT);
        assertTrue(Move.Direction.reverse(Move.Direction.DOWN) == Move.Direction.UP);

        // Move tests
        Move move = new Move(new Pair<>(1,1), Move.Direction.RIGHT);
        // test simple move
        assertTrue(move.getNextPosition().first == 1 && move.getNextPosition().second == 2);
        assertTrue(move.reverse().getPosition().first == 1 && move.reverse().getPosition().second == 2);
        assertTrue(move.reverse().getDirection() == Move.Direction.LEFT);
    }

    @Test
    public void testMoves() {
        BoardState boardState = new BoardState(4);
        Move move = new Move(new Pair<>(3, 2), Move.Direction.RIGHT);
        Assert.assertTrue(boardState.isSolved());
        Assert.assertTrue(boardState.isValid(move));

        boardState.makeMove(move);
        Assert.assertTrue(!boardState.isSolved());
        Assert.assertTrue(!boardState.isValid(move));

        boardState.undoMove(move);
        Assert.assertTrue(boardState.isSolved());
        Assert.assertTrue(boardState.isValid(move));
    }

    @Test
    public void testEquality() {

        BoardState boardState = new BoardState(4);
        BoardState anotherState = new BoardState(4);
        Move move = new Move(new Pair<>(3, 2), Move.Direction.RIGHT);
        Assert.assertTrue(boardState.isSolved());
        Assert.assertTrue(boardState.isValid(move));
        Assert.assertTrue(boardState.equals(anotherState));

        boardState.makeMove(move);
        Assert.assertTrue(!boardState.isSolved());
        Assert.assertTrue(!boardState.isValid(move));
        Assert.assertTrue(!boardState.equals(anotherState));


        boardState.undoMove(move);
        Assert.assertTrue(boardState.isSolved());
        Assert.assertTrue(boardState.isValid(move));
        Assert.assertTrue(boardState.equals(anotherState));

    }


    // ------------------------------------------------------------------------
    // GETTERS / SETTTERS
    // ------------------------------------------------------------------------
}
