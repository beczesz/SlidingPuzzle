package com.exarlabs.android.slidingpuzzle.ui;

import android.app.Activity;
import android.os.Bundle;

import com.exarlabs.android.slidingpuzzle.SlidingPuzzleApplication;

import butterknife.ButterKnife;

/**
 * Base activity for all the Activities, it provides some common operation for all of the sub-activities
 * such as dependency injection.
 * Created by becze on 9/21/2015.
 */
public class ExarActivity extends Activity {

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SlidingPuzzleApplication.component().inject(this);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }

    // ------------------------------------------------------------------------
    // GETTERS / SETTTERS
    // ------------------------------------------------------------------------
}
