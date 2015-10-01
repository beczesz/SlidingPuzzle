package com.exarlabs.android.slidingpuzzle.ui.menu;

import javax.inject.Inject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.exarlabs.android.slidingpuzzle.R;
import com.exarlabs.android.slidingpuzzle.SlidingPuzzleApplication;
import com.exarlabs.android.slidingpuzzle.ui.ExarFragment;
import com.exarlabs.android.slidingpuzzle.ui.navigation.NavigationManager;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * MainMenu fragment for the game
 * Created by becze on 9/30/2015.
 */
public class MainMenuFragment extends ExarFragment {

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
     * @return newInstance of MainMenuFragment
     */
    public static MainMenuFragment newInstance() {
        return new MainMenuFragment();
    }

    // ------------------------------------------------------------------------
    // FIELDS
    // ------------------------------------------------------------------------

    @Bind(R.id.main_menu_start_game)
    public TextView mStartGameMenu;

    @Bind(R.id.main_menu_high_scores)
    public TextView mHighScores;

    @Bind(R.id.main_menu_settings)
    public TextView mSettingsMenu;

    @Bind(R.id.main_menu_exit_game)
    public TextView mExitGameMenu;

    @Inject
    public NavigationManager mNavigationManager;


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
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_menu, null);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        showActionbar(true);
    }

    @OnClick(R.id.main_menu_start_game)
    public void startGame() {
        mNavigationManager.startGame();
    }

    @OnClick(R.id.main_menu_settings)
    public void startSettings() {
        mNavigationManager.startSettings();

    }

    @OnClick(R.id.main_menu_high_scores)
    public void startHighScores() {
        mNavigationManager.startHighScores();
    }

    @OnClick(R.id.main_menu_exit_game)
    public void startExitGame() {
        // We just stop the application by finishing the current activity
        getActivity().finish();
    }


    // ------------------------------------------------------------------------
    // GETTERS / SETTTERS
    // ------------------------------------------------------------------------
}
