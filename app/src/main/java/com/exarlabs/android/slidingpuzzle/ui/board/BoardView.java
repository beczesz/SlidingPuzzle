package com.exarlabs.android.slidingpuzzle.ui.board;

import javax.inject.Inject;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.exarlabs.android.slidingpuzzle.R;
import com.exarlabs.android.slidingpuzzle.SlidingPuzzleApplication;
import com.exarlabs.android.slidingpuzzle.model.board.BoardState;
import com.exarlabs.android.slidingpuzzle.utils.Pair;
import com.exarlabs.android.slidingpuzzle.utils.ui.ScreenUtils;

/**
 * Displays a board with NxN tiles.
 * Created by becze on 9/16/2015.
 */
public class BoardView extends RelativeLayout implements BoardPresenter.IBoardView {

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

    private BoardState mLastBoardState;

    private TileView[][] mTileViews;

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

    // ------------------------------------------------------------------------
    // METHODS
    // ------------------------------------------------------------------------

    private void init() {
        // Inject the dependencies
        SlidingPuzzleApplication.component().inject(this);
        mBoardPresenter.setBoardView(this);
        mBoardPresenter.init();

        setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));

        // set the current container to be not clickable
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Board clicked", Toast.LENGTH_SHORT).show();
            }
        });
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
        TileView firstView = mTileViews[clickedPosition.first][clickedPosition.second];
        TileView secondView = mTileViews[emptyTilePosition.first][emptyTilePosition.second];
        mTileViews[clickedPosition.first][clickedPosition.second] = secondView;
        mTileViews[emptyTilePosition.first][emptyTilePosition.second] = firstView;

        refreshTiles();

        if (isSolved()) {
            Toast.makeText(getContext(), "Solved!", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * We create as many tiles at it is necessary
     */
    private void regenerateTiles() {


        // calculate the tile dimension in pixels
        Point screenDimensions = ScreenUtils.getScreenDimensions(getContext());
        int screenWidth = Math.min(screenDimensions.x, screenDimensions.y);
        mTilePadding = (int) getResources().getDimension(R.dimen.tiles_spacing);

        mTileSize = (int) (((float) (screenWidth - (mLastBoardState.getDimension() + 1) * mTilePadding)) / mLastBoardState.getDimension());

        int dimension = mLastBoardState.getDimension();
        mTileViews = new TileView[dimension][dimension];

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {

                int index = mLastBoardState.getTiles()[i][j];
                TileView tileView = new TileView(getContext(), mTileSize, index);
                mTileViews[i][j] = tileView;
                tileView.setOnTouchListener(new OnTouchListener() {
                    @Override
                    public boolean onTouch(View tile, MotionEvent event) {
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                if (tile instanceof TileView) {
                                    handleTileClicked((TileView) tile);
                                }
                                return true;
                        }
                        return false;
                    }
                });
            }

        }

        refreshTiles();

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
                    tileView.setX(j * (mTileSize + mTilePadding) + mTilePadding);
                    tileView.setY(i * (mTileSize + mTilePadding) + mTilePadding);
                    addView(tileView);
                }
            }
        }
    }

    private void handleTileClicked(TileView tile) {
        int index = tile.getIndex();
        if (index != BoardState.EMPTY_TILE_INDEX) {
            mBoardPresenter.tileClicked(index);
        }
    }

    private boolean isSolved() {
        return mBoardPresenter.isSolved();
    }

    // ------------------------------------------------------------------------
    // GETTERS / SETTTERS
    // ------------------------------------------------------------------------
}
