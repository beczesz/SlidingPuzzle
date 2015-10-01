package com.exarlabs.android.slidingpuzzle.ui.game.board;

import javax.inject.Inject;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;

import com.exarlabs.android.slidingpuzzle.R;
import com.exarlabs.android.slidingpuzzle.SlidingPuzzleApplication;
import com.exarlabs.android.slidingpuzzle.model.board.BoardState;
import com.exarlabs.android.slidingpuzzle.utils.Pair;

/**
 * Displays a board with NxN tiles.
 * Created by becze on 9/16/2015.
 */
public class BoardView extends RelativeLayout implements BoardPresenter.IBoardView, Animation.AnimationListener {

    // ------------------------------------------------------------------------
    // TYPES
    // ------------------------------------------------------------------------


    // ------------------------------------------------------------------------
    // STATIC FIELDS
    // ------------------------------------------------------------------------
    private static final int DEFAULT_TILE_SIZE = 300;
    private static final int DEFAULT_TILE_PADDING = 10;

    // ------------------------------------------------------------------------
    // STATIC METHODS
    // ------------------------------------------------------------------------

    // ------------------------------------------------------------------------
    // FIELDS
    // ------------------------------------------------------------------------
    @Inject
    public BoardPresenter mBoardPresenter;

    private int mBoardWidth;

    private BoardState mLastBoardState;

    private TileView[][] mTileViews;
    private TileView[][] mOriginalPositions;

    private int mTileSize = DEFAULT_TILE_SIZE;
    private int mTilePadding = DEFAULT_TILE_PADDING;


    // ------------------------------------------------------------------------
    // CONSTRUCTORS
    // ------------------------------------------------------------------------

    public BoardView(Context context) {
        super(context);
        init();
    }

    public BoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BoardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public BoardView(Activity activity, int boardWidth) {
        super(activity);
        mBoardWidth = boardWidth;
        init();
    }

    // ------------------------------------------------------------------------
    // METHODS
    // ------------------------------------------------------------------------

    private void init() {
        // Inject the dependencies
        SlidingPuzzleApplication.component().inject(this);
        mBoardPresenter.setBoardView(this);
        mBoardPresenter.init();
        updateWithState(mBoardPresenter.mGameHandler.getBoardState());
    }

    @Override
    public void updateWithState(BoardState state) {
        mLastBoardState = state;
        regenerateTiles();
        invalidate();
    }

    @Override
    public void switchTiles(Pair<Integer, Integer> clickedPosition, Pair<Integer, Integer> emptyTilePosition) {
        // Switch the two tiles and refresh their position
        final TileView firstView = mTileViews[clickedPosition.first][clickedPosition.second];
        TileView secondView = mTileViews[emptyTilePosition.first][emptyTilePosition.second];
        mTileViews[clickedPosition.first][clickedPosition.second] = secondView;
        mTileViews[emptyTilePosition.first][emptyTilePosition.second] = firstView;


        //@formatter:off
        Animation tileToEmpty = new TranslateAnimation(
                        firstView.getTileX(), // we always start from the current position
                        firstView.getTileX() + (emptyTilePosition.second - clickedPosition.second) * (mTileSize + mTilePadding),
                        firstView.getTileY(),// we always start from the current position
                        firstView.getTileY() + (emptyTilePosition.first - clickedPosition.first) * (mTileSize + mTilePadding)
        );
        tileToEmpty.setDuration(50);
        tileToEmpty.setAnimationListener(this);
        tileToEmpty.setInterpolator(new AccelerateInterpolator());
        tileToEmpty.setFillAfter(true);
        firstView.startAnimation(tileToEmpty);
        //@formatter:on


        // Update the position of the tiles
        firstView.setTileX(firstView.getTileX() + (emptyTilePosition.second - clickedPosition.second) * (mTileSize + mTilePadding));
        firstView.setTileY(firstView.getTileY() + (emptyTilePosition.first - clickedPosition.first) * (mTileSize + mTilePadding));

        // notify the tile that it has been tapped
        firstView.onTileTapped(true);
    }


    /**
     * Refreshes the tiles's position based theri position in the matrix
     */
    private void refreshTiles() {
        removeAllViews();

        int dimension = mLastBoardState.getDimension();
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                TileView tileView = mTileViews[i][j];
                if (tileView != null) {
                    tileView.setX(getTileXY(j));
                    tileView.setY(getTileXY(i));
                    addView(tileView);
                }
            }
        }
    }

    private int getTileXY(int index) {
        return index * (mTileSize + mTilePadding) + mTilePadding;
    }


    /**
     * We create as many tiles at it is necessary
     */
    private void regenerateTiles() {
        // calculate the tile dimension in pixels
        mTilePadding = (int) getResources().getDimension(R.dimen.tiles_spacing);

        mTileSize = (int) (((float) (mBoardWidth - (mLastBoardState.getDimension() + 1) * mTilePadding)) / mLastBoardState.getDimension());

        int dimension = mLastBoardState.getDimension();
        mTileViews = new TileView[dimension][dimension];
        mOriginalPositions = new TileView[dimension][dimension];

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {

                int index = mLastBoardState.getTiles()[i][j];
                TileView tileView = new TileView(getContext(), mTileSize, index);
                mTileViews[i][j] = tileView;
                mOriginalPositions[i][j] = tileView;
                tileView.setOnTouchListener(new OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                if (v instanceof TileView) {
                                    TileView tile = (TileView) v;
                                    handleTileClicked(tile);
                                }
                        }
                        return true;
                    }
                });
            }

        }

        refreshTiles();
    }

    /**
     * When you apply a translation animation, the view itself is not moved only the drawable of the view.
     * After many trials I've decided to make this workaround to get the tile view which is really clicked.
     *
     * @param v
     */
    private int mapTileView(View v) {
        int dimension = mLastBoardState.getDimension();
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (mOriginalPositions[i][j] == v) {
                    return mBoardPresenter.mGameHandler.getBoardState().getTiles()[i][j];
                }
            }
        }

        return BoardState.EMPTY_TILE_INDEX;
    }


    /**
     * Returns the tile with the given index
     *
     * @param index
     * @return
     */
    private TileView getTile(int index) {
        int dimension = mLastBoardState.getDimension();
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (mTileViews[i][j].getIndex() == index) {
                    return mTileViews[i][j];
                }
            }
        }

        return null;
    }


    private void handleTileClicked(TileView tile) {
        int index = mapTileView(tile);
        if (index != BoardState.EMPTY_TILE_INDEX) {
            boolean isValid = mBoardPresenter.tileClicked(index);
            if (!isValid) {
                getTile(index).onTileTapped(false);
            }
        }
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        invalidate();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    // ------------------------------------------------------------------------
    // GETTERS / SETTTERS
    // ------------------------------------------------------------------------
}
