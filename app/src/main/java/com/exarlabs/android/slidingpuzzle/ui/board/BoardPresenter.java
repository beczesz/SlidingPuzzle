package com.exarlabs.android.slidingpuzzle.ui.board;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import com.exarlabs.android.slidingpuzzle.SlidingPuzzleApplication;
import com.exarlabs.android.slidingpuzzle.business.board.GamEvent;
import com.exarlabs.android.slidingpuzzle.business.board.GameHandler;
import com.exarlabs.android.slidingpuzzle.model.board.BoardState;
import com.exarlabs.android.slidingpuzzle.model.board.Move;
import com.exarlabs.android.slidingpuzzle.utils.Pair;

import rx.Subscriber;

/**
 * Presenter for a slidingPuzzle board view. It defines an interface through which the board can be controlled
 * Created by becze on 9/17/2015.
 */
public class BoardPresenter extends Subscriber<GamEvent> {


    // ------------------------------------------------------------------------
    // TYPES
    // ------------------------------------------------------------------------

    /**
     * Defines callbacks which a board has to define.
     */
    public interface IBoardView {

        /**
         * Update the view with the given board state
         *
         * @param state
         */
        void updateWithState(BoardState state);


        /**
         * Swtich the two tiles marked with the position
         *
         * @param clickedPosition
         * @param emptyTilePosition
         */
        void switchTiles(Pair<Integer, Integer> clickedPosition, Pair<Integer, Integer> emptyTilePosition);
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

    @Inject
    public GameHandler mGameHandler;

    private IBoardView mBoardView;
    // ------------------------------------------------------------------------
    // CONSTRUCTORS
    // ------------------------------------------------------------------------

    public BoardPresenter() {
        SlidingPuzzleApplication.component().inject(this);
    }


    // ------------------------------------------------------------------------
    // METHODS
    // ------------------------------------------------------------------------

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onNext(GamEvent gamEvent) {
        switch (gamEvent.getEventType()) {
            case GamEvent.GAME_RESET:
                notifyWithstate();
                break;
        }
    }

    /**
     * Sets the view for this presenter.
     *
     * @param view
     */
    public void setBoardView(IBoardView view) {
        this.mBoardView = view;
    }

    /**
     * Initializes the presenter
     */
    public void init() {

        // subscribe to board presenter
        mGameHandler.subscribe(this);

        mGameHandler.initializeGame();
        // notfy the board with the new state
        notifyWithstate();
    }

    /**
     * Notify the board with the current state
     */
    private void notifyWithstate() {
        // if the board view is available then notify about the state change
        mBoardView.updateWithState(mGameHandler.getBoardState());
    }


    /**
     * Handler for a tile is cliked event
     *
     * @param index
     */
    public void tileClicked(int index) {
        // get the position of the tile
        Pair<Integer, Integer> clickedPosition = mGameHandler.getBoardState().getPosition(index);
        Pair<Integer, Integer> emptyTilePosition = mGameHandler.getBoardState().getPosition(BoardState.EMPTY_TILE_INDEX);

        /*
         * The clicked position must be in the same row or in the same column as the empty tile position
         */
        if ((clickedPosition.first == emptyTilePosition.first) || (clickedPosition.second == emptyTilePosition.second)) {

            Move.Direction direction = null;
            if (clickedPosition.first == emptyTilePosition.first) {
                // The empty tile in the same row
                direction = clickedPosition.second > emptyTilePosition.second ? Move.Direction.LEFT : Move.Direction.RIGHT;
            } else if (clickedPosition.second == emptyTilePosition.second) {
                direction = clickedPosition.first < emptyTilePosition.first ? Move.Direction.DOWN : Move.Direction.UP;
            }


            /*
             * Generate the moves usingthe direction and the clicked position.
             */
            List<Move> moves = generateMoves(clickedPosition, emptyTilePosition, direction);

            for (Move move : moves) {
                mGameHandler.getBoardState().makeMove(move);
                mBoardView.switchTiles(move.getPosition(), move.getNextPosition());
            }
        }
    }


    private List<Move> generateMoves(Pair<Integer, Integer> clickedPosition, Pair<Integer, Integer> emptyTilePosition, Move.Direction direction) {

        if (clickedPosition.equals(emptyTilePosition)) {
            // If we arrived to the empty tile then just return an emoty list
            return new ArrayList<>();
        } else {
            // Generate a new position
            Pair<Integer, Integer> nextPosition = direction.getNextPosition(clickedPosition);
            if (mGameHandler.getBoardState().isValidPosition(nextPosition.first, nextPosition.second)) {
                List<Move> moves = generateMoves(nextPosition, emptyTilePosition, direction);
                moves.add(new Move(clickedPosition, direction));
                return moves;
            }
        }

        // This should never happen
        return new ArrayList<>();
    }

    /**
     * @return true if the board is solved.
     */
    public boolean isSolved() {
        return mGameHandler.getBoardState().isSolved();
    }

}

// ------------------------------------------------------------------------
// GETTERS / SETTTERS
// ------------------------------------------------------------------------
