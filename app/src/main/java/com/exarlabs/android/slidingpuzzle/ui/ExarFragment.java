package com.exarlabs.android.slidingpuzzle.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.exarlabs.android.slidingpuzzle.SlidingPuzzleApplication;

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

    public ExarFragment() {
        SlidingPuzzleApplication.component().inject(this);
    }


    // ------------------------------------------------------------------------
    // METHODS
    // ------------------------------------------------------------------------

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, getActivity());
    }


    // ------------------------------------------------------------------------
    // GETTERS / SETTTERS
    // ------------------------------------------------------------------------
}
