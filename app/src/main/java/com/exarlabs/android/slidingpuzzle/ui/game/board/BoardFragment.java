package com.exarlabs.android.slidingpuzzle.ui.game.board;

import javax.inject.Inject;

import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.exarlabs.android.slidingpuzzle.R;
import com.exarlabs.android.slidingpuzzle.SlidingPuzzleApplication;
import com.exarlabs.android.slidingpuzzle.business.board.GamEvent;
import com.exarlabs.android.slidingpuzzle.business.board.GameHandler;
import com.exarlabs.android.slidingpuzzle.ui.ExarFragment;
import com.exarlabs.android.slidingpuzzle.ui.navigation.NavigationManager;
import com.exarlabs.android.slidingpuzzle.utils.ui.ScreenUtils;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Subscriber;

/**
 * Displays a NxN board.
 * Created by becze on 9/14/2015.
 */
public class BoardFragment extends ExarFragment {

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

    private BoardView mBoardView;

    @Bind(R.id.shuffle_board)
    public TextView mShuffleButton;

    @Bind(R.id.board_container)
    public FrameLayout mBoardContainer;

    @Bind(R.id.reset_board)
    public TextView mResetButton;

    @Bind(R.id.settings)
    public TextView mSettingsButton;

    @Inject
    public GameHandler mGameHandler;

    @Inject
    public NavigationManager mNavigationManager;

    // ------------------------------------------------------------------------
    // INITIALIZERS
    // ------------------------------------------------------------------------

    // ------------------------------------------------------------------------
    // CONSTRUCTORS
    // ------------------------------------------------------------------------

    // ------------------------------------------------------------------------
    // METHODS
    // ------------------------------------------------------------------------


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SlidingPuzzleApplication.component().inject(this);

        mGameHandler.subscribe(new Subscriber<GamEvent>() {
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
                        mResetButton.setEnabled(false);
                        handleResetBoard();
                        break;

                    case GamEvent.GAME_SHUFFLED:
                        mResetButton.setEnabled(true);
                        break;
                }
            }
        });
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup mRootView = (ViewGroup) inflater.inflate(R.layout.board, null);
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        resize((ViewGroup) view);
        createBoard();
    }

    /**
     * Resizes the height of this fragment to be equal with the width of the screen.
     *
     * @param rootView
     */
    private void resize(ViewGroup rootView) {
        // calculate the tile dimension in pixels
        Point screenDimensions = ScreenUtils.getScreenDimensions(getActivity());
        int screenWidth = Math.min(screenDimensions.x, screenDimensions.y);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(screenWidth, screenWidth);
        rootView.setLayoutParams(params);
    }


    /**
     * Creates and adds a new board
     */
    private void createBoard() {
        // calculate the tile dimension in pixels
        Point screenDimensions = ScreenUtils.getScreenDimensions(getActivity());
        int boardWidth = (int) (Math.min(screenDimensions.x, screenDimensions.y) - 2 * getResources().getDimension(R.dimen.board_padding));
        mBoardView = new BoardView(getActivity(), boardWidth);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(boardWidth, boardWidth);
        mBoardView.setLayoutParams(params);
        mBoardContainer.addView(mBoardView);
    }

    @OnClick(R.id.shuffle_board)
    public void shuffleBoard() {
        mGameHandler.startNewGame();
        mShuffleButton.setVisibility(View.GONE);
    }

    @OnClick(R.id.reset_board)
    public void resetBoard() {
        mGameHandler.initializeGame();
    }

    @OnClick(R.id.settings)
    public void settings() {
       mNavigationManager.startSettings();
    }

    /**
     * Handle board reset.
     */
    private void handleResetBoard() {
        mShuffleButton.setVisibility(View.VISIBLE);
    }
}
