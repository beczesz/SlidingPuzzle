package com.exarlabs.android.slidingpuzzle.ui.splash;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.exarlabs.android.slidingpuzzle.R;
import com.exarlabs.android.slidingpuzzle.SlidingPuzzleApplication;
import com.exarlabs.android.slidingpuzzle.business.AppConstants;
import com.exarlabs.android.slidingpuzzle.business.solutions.SolutionsHandler;
import com.exarlabs.android.slidingpuzzle.ui.ExarActivity;
import com.exarlabs.android.slidingpuzzle.ui.SlidingPuzzleActivity;
import com.greenfrvr.rubberloader.RubberLoaderView;

import butterknife.Bind;
import rx.Observable;
import rx.Observer;
import rx.functions.Func2;

/**
 * Displays a SplashScreen loading an dinitializing the necessary component for the app.
 * Created by becze on 9/21/2015.
 */
public class SplashScreenActivity extends ExarActivity {

    // ------------------------------------------------------------------------
    // TYPES
    // ------------------------------------------------------------------------

    // ------------------------------------------------------------------------
    // STATIC FIELDS
    // ------------------------------------------------------------------------

    // delay of seconds
    private static final int SPLASH_DELAY_SECONDS = 3;

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

    @Inject
    public Application mApplication;

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
        initializeApplication();
    }


    /**
     * Initialize the application and wait for the result. We might introduce an artificial delay
     * to be able to show the logo a bit to the user, and to not to have a blinking view on the high end devices.
     */
    private void initializeApplication() {
        // start the loader view
        mLoaderView.startLoading();

        if (mApplication instanceof SlidingPuzzleApplication) {
            //@formatter:off
            ((SlidingPuzzleApplication) mApplication).initializeApplication().
                zipWith(getSplashScreenDelayer(),
                            new Func2<Boolean, Boolean, Boolean>() {
                                @Override
                                public Boolean call(Boolean first, Boolean second) {
                                    return first && second;
                                }
                            })
            .subscribe(new Observer<Boolean>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(Boolean initSuccessful) {
                    if (initSuccessful) {
                        onInitFinished();
                    }
                }
            });
            //@formatter:on
        }
    }

    private Observable<Boolean> getSplashScreenDelayer() {
        //@formatter:off
        return Observable.just(true)
                        .delay(mPrefs.getBoolean(AppConstants.SP_KEY_DELAYED_SPLASH_SCREEN, false) ? SPLASH_DELAY_SECONDS : 0, TimeUnit.SECONDS);
        //@formatter:on
    }


    /**
     * Shoudl be called when all the nexessary initialization is done.
     */
    public void onInitFinished() {
        startActivity(new Intent(this, SlidingPuzzleActivity.class));
        finish();
    }


    // ------------------------------------------------------------------------
    // GETTERS / SETTTERS
    // ------------------------------------------------------------------------
}
