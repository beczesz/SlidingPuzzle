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
         * Decodes a binary string array and returns the direction
         * Ex: "00" is LEFT
         * "01" is UP
         * "10" is RIGHT
         * "11" is DOWN
         *
         * @param binaryDirection
         * @return
         */
        public static Direction decode(String binaryDirection) {
            int position = Integer.parseInt(binaryDirection, 2);
            return position >= 0 && position < values().length ? values()[position] : null;
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

    /**
     * Creates a new move with the given position and direction
     *
     * @param i
     * @param j
     * @param mDirection
     */
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
     * Note the position can be invalid position as well.
     */
    public Pair<Integer, Integer> getNextPosition() {
        return mDirection.getNextPosition(mPosition);
    }

    /**
     * @return a new Move object which is the reverse of the current move: i.e.:
     * Note the position can be invalid position as well.
     */
    public Move reverse() {
        return new Move(getNextPosition(), Direction.reverse(mDirection));
    }


}
