package com.exarlabs.android.slidingpuzzle.ui.splash;

import android.os.Bundle;

import com.exarlabs.android.slidingpuzzle.R;
import com.exarlabs.android.slidingpuzzle.ui.ExarActivity;
import com.greenfrvr.rubberloader.RubberLoaderView;

import butterknife.Bind;

/**
 * Created by becze on 9/21/2015.
 */
public class SplashScreenActivity extends ExarActivity {

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

    @Bind(R.id.loader)
    public RubberLoaderView mLoaderView;

    // ------------------------------------------------------------------------
    // CONSTRUCTORS
    // ------------------------------------------------------------------------

    // ------------------------------------------------------------------------
    // METHODS
    // ------------------------------------------------------------------------


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splay_screen_layout);

        mLoaderView.startLoading();
    }



    // ------------------------------------------------------------------------
    // GETTERS / SETTTERS
    // ------------------------------------------------------------------------
}
