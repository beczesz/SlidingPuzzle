package com.exarlabs.android.slidingpuzzle.ui.splash;

import javax.inject.Inject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.exarlabs.android.slidingpuzzle.R;
import com.exarlabs.android.slidingpuzzle.SlidingPuzzleApplication;
import com.exarlabs.android.slidingpuzzle.business.AppConstants;
import com.exarlabs.android.slidingpuzzle.business.solutions.SolutionsHandler;
import com.exarlabs.android.slidingpuzzle.ui.BoardGameActivity;
import com.exarlabs.android.slidingpuzzle.ui.ExarActivity;
import com.greenfrvr.rubberloader.RubberLoaderView;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Observer;

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

    @Inject
    public SolutionsHandler mSolutionsHandler;

    @Inject
    public SharedPreferences mPrefs;

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
        SlidingPuzzleApplication.component().inject(this);


        // Initialzie the databse
        initializeDatabase();
    }

    private void initializeDatabase() {
        if (!mSolutionsHandler.isDatabaseGenerated()) {
            mLoaderView.startLoading();
            mSolutionsHandler.generateSolutions(new Observer<Boolean>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(Boolean solutionsAreGenerated) {
                    if (solutionsAreGenerated) {
                        mPrefs.edit().putBoolean(AppConstants.SP_KEY_DATABASE_GENERATED, true).commit();
                        onInitFinished();
                    }
                }
            });
        } else {
            mLoaderView.startLoading();
        }
    }

    /**
     * Shoudl be called when all the nexessary initialization is done.
     */
    @OnClick(R.id.loader)
    public void onInitFinished() {
        startActivity(new Intent(this, BoardGameActivity.class));
    }


    // ------------------------------------------------------------------------
    // GETTERS / SETTTERS
    // ------------------------------------------------------------------------
}
