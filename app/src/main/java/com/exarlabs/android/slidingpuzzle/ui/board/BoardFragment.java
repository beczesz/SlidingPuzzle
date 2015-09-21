package com.exarlabs.android.slidingpuzzle.ui.board;

import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.exarlabs.android.slidingpuzzle.R;
import com.exarlabs.android.slidingpuzzle.utils.ui.ScreenUtils;

/**
 * Displays a NxN board.
 * Created by becze on 9/14/2015.
 */
public class BoardFragment extends Fragment {

    // ------------------------------------------------------------------------
    // TYPES
    // ------------------------------------------------------------------------

    // ------------------------------------------------------------------------
    // STATIC FIELDS
    // ------------------------------------------------------------------------

    // ------------------------------------------------------------------------
    // STATIC INITIALIZERS
    // ------------------------------------------------------------------------

    // ------------------------------------------------------------------------
    // STATIC METHODS
    // ------------------------------------------------------------------------

    /**
     * @return newInstance of BoardFragment
     */
    public static BoardFragment newInstance() {
        return new BoardFragment();
    }

    // ------------------------------------------------------------------------
    // FIELDS
    // ------------------------------------------------------------------------
    private ViewGroup mRootView;

    private BoardView mBoardView;
    // ------------------------------------------------------------------------
    // INITIALIZERS
    // ------------------------------------------------------------------------

    // ------------------------------------------------------------------------
    // CONSTRUCTORS
    // ------------------------------------------------------------------------

    // ------------------------------------------------------------------------
    // METHODS
    // ------------------------------------------------------------------------


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = (ViewGroup) inflater.inflate(R.layout.board, null);
        resize(mRootView);
        createBoard();
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        resize(mRootView);

    }

    /**
     * Resizes the height of this fragment to be equal with the width of the screen.
     * @param rootView
     */
    private void resize(ViewGroup rootView) {
        // calculate the tile dimension in pixels
        Point screenDimensions = ScreenUtils.getScreenDimensions(getContext());
        int screenWidth = Math.min(screenDimensions.x, screenDimensions.y);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(screenWidth, screenWidth);
        rootView.setLayoutParams(params);
    }


    /**
     * Creates and adds a new board
     */
    private void createBoard() {
        mBoardView = new BoardView(getActivity());
        // calculate the tile dimension in pixels
        Point screenDimensions = ScreenUtils.getScreenDimensions(getContext());
        int screenWidth = Math.min(screenDimensions.x, screenDimensions.y);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(screenWidth, screenWidth);
        mBoardView.setLayoutParams(params);
        mRootView.addView(mBoardView);
    }
}
