package com.exarlabs.android.slidingpuzzle.ui.game;

import javax.inject.Inject;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.exarlabs.android.slidingpuzzle.R;
import com.exarlabs.android.slidingpuzzle.SlidingPuzzleApplication;
import com.exarlabs.android.slidingpuzzle.business.board.GamEvent;
import com.exarlabs.android.slidingpuzzle.business.board.GameHandler;
import com.exarlabs.android.slidingpuzzle.ui.ExarFragment;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.IoniconsIcons;

import rx.Subscriber;

/**
 * Created by becze on 9/24/2015.
 */
public class BoardGameFragment extends ExarFragment {

    // ------------------------------------------------------------------------
    // TYPES
    // ------------------------------------------------------------------------

    // ------------------------------------------------------------------------
    // STATIC FIELDS
    // ------------------------------------------------------------------------

    // ------------------------------------------------------------------------
    // STATIC METHODS
    // ------------------------------------------------------------------------

    /**
     * @return newInstance of BoardGameFragment
     */
    public static BoardGameFragment newInstance() {
        return new BoardGameFragment();
    }

    // ------------------------------------------------------------------------
    // FIELDS
    // ------------------------------------------------------------------------

    private Fragment mStatsFragment;

    private Fragment mBoardFragment;

    @Inject
    public GameHandler mGameHandler;

    private View rootView;
    // ------------------------------------------------------------------------
    // CONSTRUCTORS
    // ------------------------------------------------------------------------

    // ------------------------------------------------------------------------
    // METHODS
    // ------------------------------------------------------------------------


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.game_layout, null);
            mStatsFragment = getFragmentManager().findFragmentById(R.id.stats_container);
            mBoardFragment = getFragmentManager().findFragmentById(R.id.board_container);
        }

        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SlidingPuzzleApplication.component().inject(this);

        setHasOptionsMenu(true);

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
                    case GamEvent.GAME_SOLVED:
                        handleSolved();
                        break;
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        // we have to reove those fragments which were added
        //@formatter:off
        getFragmentManager().beginTransaction()
                        .remove(mBoardFragment)
                        .remove(mStatsFragment)
                        .commit();
        //@formatter:on
        super.onDestroy();
    }

    /**
     * We display a popup that the board is solved now.
     */
    private void handleSolved() {
        // 1. Instantiate an AlertDialog.Builder with its constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // 2. Chain together various setter methods to set the dialog characteristics
        String message = String.format(getString(R.string.board_solved), (mGameHandler.getGameDuration() / 1000));
        builder.setMessage(message).setTitle(R.string.done);

        builder.setPositiveButton(R.string.play_again, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mGameHandler.initializeGame();
            }
        });

        // 3. Get the AlertDialog from create()
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.board_menu, menu);

        menu.findItem(R.id.action_stop).setIcon(new IconDrawable(getActivity(), IoniconsIcons.ion_stop).colorRes(R.color.error).actionBarSize());
        menu.findItem(R.id.action_settings).setIcon(
                        new IconDrawable(getActivity(), IoniconsIcons.ion_gear_a).colorRes(R.color.error).actionBarSize());

        super.onCreateOptionsMenu(menu, inflater);
    }

    // ------------------------------------------------------------------------
    // GETTERS / SETTTERS
    // ------------------------------------------------------------------------
}
