package com.exarlabs.android.slidingpuzzle.ui;

import javax.inject.Inject;

import android.os.Bundle;

import com.exarlabs.android.slidingpuzzle.R;
import com.exarlabs.android.slidingpuzzle.SlidingPuzzleApplication;
import com.exarlabs.android.slidingpuzzle.ui.navigation.NavigationManager;

/**
 * As a design decision we will have one activity - multiple fragment architecture.
 * Main activity for holding all the fragments (screens) for the game.
 * <p/>
 * Created by becze on 9/30/2015.
 */
public class SlidingPuzzleActivity extends ExarActivity {

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

    @Inject
    public NavigationManager mNavigationManager;

    // ------------------------------------------------------------------------
    // CONSTRUCTORS
    // ------------------------------------------------------------------------

    // ------------------------------------------------------------------------
    // METHODS
    // ------------------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        SlidingPuzzleApplication.component().inject(this);

        // Initialize the NavigationManager with this activity's FragmentManager
        mNavigationManager.init(getFragmentManager());

        // Start the main menu
        mNavigationManager.startMainMenu();
    }

    @Override
    public void onBackPressed() {
        mNavigationManager.popBackstack();
    }

    // ------------------------------------------------------------------------
    // GETTERS / SETTTERS
    // ------------------------------------------------------------------------
}
