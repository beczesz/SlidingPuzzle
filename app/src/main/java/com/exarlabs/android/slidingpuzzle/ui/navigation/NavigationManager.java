package com.exarlabs.android.slidingpuzzle.ui.navigation;

import android.app.Fragment;
import android.app.FragmentManager;

import com.exarlabs.android.slidingpuzzle.R;
import com.exarlabs.android.slidingpuzzle.ui.game.BoardGameFragment;
import com.exarlabs.android.slidingpuzzle.ui.highscores.HighScoresFragment;
import com.exarlabs.android.slidingpuzzle.ui.menu.MainMenuFragment;
import com.exarlabs.android.slidingpuzzle.ui.settings.SettingsFragment;

/**
 * Helper class to ease the navigation between screens.
 * Created by becze on 9/30/2015.
 */
public class NavigationManager {

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

    private FragmentManager mFragmentManager;

    // ------------------------------------------------------------------------
    // CONSTRUCTORS
    // ------------------------------------------------------------------------

    // ------------------------------------------------------------------------
    // METHODS
    // ------------------------------------------------------------------------

    /**
     * Initialize the NavigationManager with a FragmentManager, which will be used at the
     * fragment transactions.
     *
     * @param fragmentManager
     */
    public void init(FragmentManager fragmentManager) {
        mFragmentManager = fragmentManager;
    }


    /**
     * Starts the main menu.
     */
    public void startMainMenu() {
        if (mFragmentManager != null) {
            // Create a new main menu fragment
            Fragment fragment = MainMenuFragment.newInstance();

            //@formatter:off
            mFragmentManager.beginTransaction()
                            .replace(R.id.main_container, fragment)
                            .addToBackStack(MainMenuFragment.class.getSimpleName())
                            .commit();
            //@formatter:on
        }
    }

    public void startGame() {
        if (mFragmentManager != null) {
            // Create a new main menu fragment
            Fragment fragment = BoardGameFragment.newInstance();
            mFragmentManager.beginTransaction()
                            .replace(R.id.main_container, fragment)
                            .addToBackStack(MainMenuFragment.class.getSimpleName())
                            .commit();
        }
    }

    public void startSettings() {
        if (mFragmentManager != null) {
            // Create a new main menu fragment
            Fragment fragment = SettingsFragment.newInstance();
            mFragmentManager.beginTransaction()
                            .replace(R.id.main_container, fragment)
                            .addToBackStack(MainMenuFragment.class.getSimpleName())
                            .commit();
        }
    }

    public void startHighScores() {
        if (mFragmentManager != null) {
            // Create a new main menu fragment
            Fragment fragment = HighScoresFragment.newInstance();
            mFragmentManager.beginTransaction()
                            .replace(R.id.main_container, fragment)
                            .addToBackStack(MainMenuFragment.class.getSimpleName())
                            .commit();
        }
    }

    public void popBackstack() {
        if (mFragmentManager != null && mFragmentManager.getBackStackEntryCount() > 0) {
            mFragmentManager.popBackStack();
        }

        if (mFragmentManager.getBackStackEntryCount() == 0) {
            startMainMenu();
        }
    }



    // ------------------------------------------------------------------------
    // GETTERS / SETTTERS
    // ------------------------------------------------------------------------
}
