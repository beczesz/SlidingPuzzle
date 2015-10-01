package com.exarlabs.android.slidingpuzzle.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.view.View;

import butterknife.ButterKnife;

/**
 * Base class for every Fragment, it provides some custom behavior for all of the fragments
 * sucn as dependency injection.
 * Created by becze on 9/21/2015.
 */
public class ExarFragment extends Fragment {

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

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    /**
     * Initializes the ActionBar
     *
     * @param showHomeButton
     * @param title
     */
    protected void initActionBar(boolean showHomeButton, String title) {
        if (getActivity() != null && getActivity().getActionBar() != null) {
            getActivity().getActionBar().setHomeButtonEnabled(showHomeButton);
            getActivity().getActionBar().setTitle(title);
        }
    }

    /**
     * Shows and hides the actionbar
     *
     * @param isShown
     */
    protected void showActionbar(boolean isShown) {
        if (getActivity() != null && getActivity().getActionBar() != null) {
            if (isShown) {
                getActivity().getActionBar().show();
            } else {
                getActivity().getActionBar().hide();
            }
        }
    }


    // ------------------------------------------------------------------------
    // GETTERS / SETTTERS
    // ------------------------------------------------------------------------
}
