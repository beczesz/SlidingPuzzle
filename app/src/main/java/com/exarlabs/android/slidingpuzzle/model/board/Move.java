package com.exarlabs.android.slidingpuzzle.model.board;


import com.exarlabs.android.slidingpuzzle.utils.Pair;

/**
 * Represents a move on the board.
 * Created by becze on 9/17/2015.
 */
public class Move {

    // ------------------------------------------------------------------------
    // TYPES
    // ------------------------------------------------------------------------

    public enum Direction {
        LEFT, UP, RIGHT, DOWN;

        public static Direction reverse(Direction direction) {
            switch (direction) {
                case RIGHT:
                    return LEFT;
                case LEFT:
                    return RIGHT;
                case UP:
                    return DOWN;
                default:
                case DOWN:
                    return UP;
            }
        }

        /**
         * @param currentPosition
         * @return using this direction a new position is returned after 1 step is made.
         */
        public Pair<Integer, Integer> getNextPosition(Pair<Integer, Integer> currentPosition) {
            int i = currentPosition.first;
            int j = currentPosition.second;

            switch (this) {
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

            return new Pair<>(i, j);
        }


    }

    // ------------------------------------------------------------------------
    // STATIC FIELDS
    // ------------------------------------------------------------------------

    // ------------------------------------------------------------------------
    // STATIC METHODS
    // ------------------------------------------------------------------------

    // ------------------------------------------------------------------------
    // FIELDS
    // ------------------------------------------------------------------------

    private Direction mDirection;

    private Pair<Integer, Integer> mPosition;

    // ------------------------------------------------------------------------
    // CONSTRUCTORS
    // ------------------------------------------------------------------------

    /**
     * Creates a new move with the given position and direction
     *
     * @param mPosition the position of the tile.
     * @param mDirection the direction of the move.
     */
    public Move(Pair<Integer, Integer> mPosition, Direction mDirection) {
        this.mDirection = mDirection;
        this.mPosition = mPosition;
    }

    public Move(int i, int j, Direction mDirection) {
        this(new Pair<Integer, Integer>(i, j), mDirection);
    }

    // ------------------------------------------------------------------------
    // METHODS
    // ------------------------------------------------------------------------

    @Override
    public String toString() {
        return String.format("Move: (%2d, %2d) -> ", mPosition.first, mPosition.second) + mDirection;
    }


    // ------------------------------------------------------------------------
    // GETTERS / SETTTERS
    // ------------------------------------------------------------------------

    public Pair<Integer, Integer> getPosition() {
        return mPosition;
    }

    public Direction getDirection() {
        return mDirection;
    }

    /**
     * @return the new position after the move has been made.
     */
    public Pair<Integer, Integer> getNextPosition() {
        return mDirection.getNextPosition(mPosition);
    }
}
