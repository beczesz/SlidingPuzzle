package com.exarlabs.android.slidingpuzzle.business.board;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.support.annotation.NonNull;

import com.exarlabs.android.slidingpuzzle.model.board.BoardState;
import com.exarlabs.android.slidingpuzzle.model.board.Move;
import com.exarlabs.android.slidingpuzzle.utils.Pair;

/**
 * Contains utility methods to encode and decode a game solution
 * Created by becze on 9/24/2015.
 */
public class GameUtil {

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


    /**
     * Encodes the user moves into a byte array
     *
     * @param userMoves
     * @return
     */
    public static byte[] encodeMoves(List<Move> userMoves) {

        StringBuilder sb = new StringBuilder();

        // reverse the moves, so that the solution starts with the last moves
        Collections.reverse(userMoves);

        for (Move move : userMoves) {
            sb.append(String.format("%2d", Move.Direction.reverse(move.getDirection()).ordinal()));
        }

        return compress(sb.toString());
    }


    /**
     * Encodes the string with containing the mvoes into a byte array
     *
     * @param s
     * @return byte array containing the moves.
     */
    public static byte[] compress(String s) {
        String binary = toPaddedBinary(s);
        byte[] finalByteArra = new byte[binary.length() / 8];
        char[] charArray = new char[binary.length() / 8];

        for (int i = 0; i < binary.length() / 8; i++) {
            byte conertedByte = (byte) Integer.parseInt(binary.substring(i * 8, i * 8 + 8), 2);
            char test = (char) Integer.parseInt(binary.substring(i * 8, i * 8 + 8), 2);
            finalByteArra[i] = conertedByte;
            charArray[i] = test;
        }


        return finalByteArra;
    }

    @NonNull
    public static String toPaddedBinary(String s) {
        if (s.length() % 4 != 0) {
            // pad with the missing values
            for (int i = 0; i < s.length() % 4; i++) {
                s += "3";
            }
        }

        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case '0':
                    builder.append("00");
                    break;
                case '1':
                    builder.append("01");
                    break;
                case '2':
                    builder.append("10");
                    break;
                case '3':
                    builder.append("11");
                    break;
            }

        }
        return builder.toString();
    }

    /**
     * Decodes an encoded solution.
     *
     * @param encodedSolution encoded moves in a byte array
     * @param nrOfSteps the number of steps this decoded solution is expected to have
     * @param boardSize the board size
     * @return a pair of BoardState and the list of moves which solves this state
     */
    public static Pair<BoardState, List<Move>> decodeSolution(byte[] encodedSolution, int nrOfSteps, int boardSize) {

        /*
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
                    if (moves.size() == nrOfSteps) {
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
}
