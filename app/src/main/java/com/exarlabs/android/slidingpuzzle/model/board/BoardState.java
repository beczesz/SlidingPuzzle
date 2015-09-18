package com.exarlabs.android.slidingpuzzle.model.board;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.exarlabs.android.slidingpuzzle.utils.Pair;

/**
 * Defines an NxN board state.
 * The initial values of the state is a board with ordered tiles, and indexed from 1 to (NxN-1).
 * The empty tile is represneted with EMPTY_TILE_INDEX;
 * Created by becze on 9/17/2015.
 */
public class BoardState {

    // ------------------------------------------------------------------------
    // TYPES
    // ------------------------------------------------------------------------

    // ------------------------------------------------------------------------
    // STATIC FIELDS
    // ------------------------------------------------------------------------
    public static final int EMPTY_TILE_INDEX = 0;
    public static final int INVALID_TILE_INDEX = Integer.MIN_VALUE;

    // ------------------------------------------------------------------------
    // STATIC METHODS
    // ------------------------------------------------------------------------

    // ------------------------------------------------------------------------
    // FIELDS
    // ------------------------------------------------------------------------
    private int mDimension;

    private int[][] mTiles;

    // ------------------------------------------------------------------------
    // CONSTRUCTORS
    // ------------------------------------------------------------------------

    /**
     * Creates a new BoardState for an NxN Board.
     *
     * @param mDimension the number of tiles in a row.
     */
    public BoardState(int mDimension) {

        if (mDimension <= 2) throw new IllegalArgumentException("Dimension of the board must be grater than 2");

        this.mDimension = mDimension;

        mTiles = new int[mDimension][mDimension];

        // Initialize the tiles
        for (int i = 0; i < mDimension; i++) {
            for (int j = 0; j < mDimension; j++) {
                int index = i * mDimension + j + 1;
                mTiles[i][j] = index != mDimension * mDimension ? index : EMPTY_TILE_INDEX;
            }
        }
    }

    /**
     * Creates a new board state with the given set of tiles.
     *
     * @param tiles
     */
    public BoardState(int[][] tiles) {
        mDimension = tiles.length;
        mTiles = cloneTiles(tiles);
    }

    private int[][] cloneTiles(int[][] tiles) {
        int[][] clonedTiles = new int[tiles.length][tiles.length];

        for (int i = 0; i < clonedTiles.length; i++) {
            clonedTiles[i] = Arrays.copyOf(tiles[i], tiles[i].length);
        }

        return clonedTiles;
    }

    // ------------------------------------------------------------------------
    // METHODS
    // ------------------------------------------------------------------------

    /**
     * Generate possible states which are one step away from the current state.
     *
     * @return a list of states.
     */
    public List<Pair<BoardState, Move.Direction>> getPossibleSteps() {
        List<Pair<BoardState, Move.Direction>> possibleStates = new ArrayList<>();

        // Get the empty tile position
        Pair<Integer, Integer> position = getPosition(BoardState.EMPTY_TILE_INDEX);

        for (Move.Direction direction : Move.Direction.values()) {

            int i = position.first;
            int j = position.second;

            switch (direction) {
                case UP:
                    i--;
                    break;
                case DOWN:
                    i++;
                    break;
                case LEFT:
                    j--;
                    break;
                case RIGHT:
                    j++;
                    break;
            }

            if (isValidPosition(i, j)) {
                BoardState generatedState = new BoardState(getTiles());
                generatedState.swapIndices(position.first, position.second, i, j);
                possibleStates.add(new Pair<>(generatedState, direction));
            }
        }
        return possibleStates;
    }


    /**
     * @return true if the current state is a solved one. otherwise false
     */
    public boolean isSolved() {
        for (int i = 0; i < mDimension; i++) {
            for (int j = 0; j < mDimension; j++) {
                int index = i * mDimension + j + 1;
                if (getIndex(i, j) != (index != mDimension * mDimension ? index : EMPTY_TILE_INDEX)) return false;
            }
        }

        return true;
    }

    /**
     * Verifies if the move is a valid one.
     *
     * @param move
     * @return true if the move can be made
     */
    public boolean isValid(Move move) {
        // The move's position must be non' empty
        if (isEmpty(move.getPosition().first, move.getPosition().second)) return false;

        Pair<Integer, Integer> nextPosition = getNextPosition(move);
        if (!isEmpty(nextPosition.first, nextPosition.second)) return false;

        // Otherwise it is a valid move
        return true;
    }

    /**
     * If it is a valid move then it makes the move
     *
     * @param move
     * @return
     */
    public boolean makeMove(Move move) {
        if (isValid(move)) {
            swapIndices(move.getPosition(), getNextPosition(move));
        }

        return false;
    }

    /**
     * Revokes the last move by inverting it
     *
     * @param move
     * @return
     */
    public boolean undoMove(Move move) {
        // Obtain the reverse of this move
        Pair<Integer, Integer> nextPosition = getNextPosition(move);
        if (nextPosition != null) {
            Move reverseMove = new Move(nextPosition, Move.Direction.reverse(move.getDirection()));
            return makeMove(reverseMove);
        }

        return false;
    }

    /**
     * Returns a pair of indexes after the move has been made.
     *
     * @param move
     * @return null if the move cannot be made to that direction, otherwise a valid index
     */
    private Pair<Integer, Integer> getNextPosition(Move move) {
        Pair<Integer, Integer> initialPosition = move.getPosition();
        int i = initialPosition.first;
        int j = initialPosition.second;

        switch (move.getDirection()) {
            case UP:
                i--;
                break;
            case DOWN:
                i++;
                break;
            case LEFT:
                j--;
                break;
            case RIGHT:
                j++;
                break;
        }

        return getIndex(i, j) != INVALID_TILE_INDEX ? new Pair<>(i, j) : null;
    }

    /**
     * Checks if the tile marked with the given position is empty
     *
     * @param i
     * @param j
     * @return true if it iempty otherwise false.
     */
    public boolean isEmpty(int i, int j) {
        return getIndex(i, j) == EMPTY_TILE_INDEX;
    }

    /**
     * Returns the value of the given tile marked by the indexes.
     *
     * @param i
     * @param j
     * @return
     */
    private int getIndex(int i, int j) {
        return (!isValidPosition(i, j)) ? INVALID_TILE_INDEX : mTiles[i][j];
    }

    /**
     * Sets the index at the given position.
     *
     * @param i
     * @param index
     * @return true if the position is valid and the index could be set.
     */
    private boolean setIndex(int i, int j, int index) {
        // If the indices are invalid then just return.
        if (!isValidPosition(i, j)) return false;

        mTiles[i][j] = index;
        return true;
    }

    /**
     * Returns true if the given position is valid on this board
     *
     * @param i
     * @param j
     * @return
     */
    private boolean isValidPosition(int i, int j) {
        return (i >= 0 && i < mDimension) && (j >= 0 && j < mDimension);
    }

    /**
     * @param index
     * @return the position of the tile with teh given index. If not found then it return null.
     */
    public Pair<Integer, Integer> getPosition(int index) {
        // Initialize the tiles
        for (int i = 0; i < mDimension; i++) {
            for (int j = 0; j < mDimension; j++) {
                if (getIndex(i, j) == index) return new Pair<>(i, j);
            }
        }
        return null;
    }

    /**
     * Returns the value of the given tile marked by the indexes.
     *
     * @return true if the indices could be swap
     */
    private boolean swapIndices(Pair<Integer, Integer> startPosition, Pair<Integer, Integer> finalPosition) {
        return swapIndices(startPosition.first, startPosition.second, finalPosition.first, finalPosition.second);
    }

    /**
     * Returns the value of the given tile marked by the indexes.
     *
     * @return true if the indices could be swap
     */
    private boolean swapIndices(int firstI, int firstJ, int secondI, int secondJ) {

        int currentIndex = getIndex(firstI, firstJ);
        int nextIndex = getIndex(secondI, secondJ);
        if (currentIndex != INVALID_TILE_INDEX && nextIndex != INVALID_TILE_INDEX) {
            setIndex(firstI, firstJ, nextIndex);
            setIndex(secondI, secondJ, currentIndex);
            return true;
        }

        return false;
    }

    /**
     * Pretty print the satete of the board.
     *
     * @return
     */
    @Override
    public String toString() {
        StringBuilder tilesToString = new StringBuilder();

        String topEdge = "---------";
        String horizonalSpace = "|        ";
        String valueField = "|   %2d   ";


        for (int i = 0; i < mDimension; i++) {
            for (int toStringRow = 0; toStringRow < 4; toStringRow++) {
                tilesToString.append("|\n");
                for (int j = 0; j < mDimension; j++) {

                    switch (toStringRow) {
                        case 0:
                        case 4:
                            tilesToString.append(topEdge);
                            break;

                        case 1:
                        case 3:
                            tilesToString.append(horizonalSpace);
                            break;

                        case 2:
                            tilesToString.append(String.format(valueField, mTiles[i][j]));
                            break;

                    }
                }

            }
        }

        tilesToString.append("|\n");
        for (int i = 0; i < mDimension; i++) {
            tilesToString.append(topEdge);
        }
        tilesToString.append("|\n");

        // close with a vertical line


        return tilesToString.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof BoardState) {
            int[][] tiles = ((BoardState) o).getTiles();

            for (int i = 0; i < tiles.length; i++) {
                for (int j = 0; j < tiles.length; j++) {
                    if (mTiles[i][j] != tiles[i][j]) {
                        return false;
                    }
                }
            }

            // if we arrived here we have an equal BoardState
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hashCode = 0;
        for (int i = 0; i < mTiles.length; i++) {
            for (int j = 0; j < mTiles.length; j++) {
                hashCode += i * j * mTiles[i][j];
            }
        }
        return hashCode;
    }

    // ------------------------------------------------------------------------
    // GETTERS / SETTTERS
    // ------------------------------------------------------------------------

    public int[][] getTiles() {
        return mTiles;
    }

    public int getDimension() {
        return mDimension;
    }

}
