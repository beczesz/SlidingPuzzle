package com.exarlabs.android.slidingpuzzle;

import javax.inject.Inject;

import android.app.Application;
import android.content.SharedPreferences;

import com.exarlabs.android.slidingpuzzle.business.DaggerComponentGraph;
import com.exarlabs.android.slidingpuzzle.business.DaggerGameComponent;
import com.exarlabs.android.slidingpuzzle.business.solutions.SolutionsHandler;
import com.facebook.stetho.Stetho;
import com.github.mmin18.layoutcast.LayoutCast;

import rx.Observable;

/**
 * The Application object for the game.
 * Created by becze on 9/16/2015.
 */
public class SlidingPuzzleApplication extends Application {

    // ------------------------------------------------------------------------
    // TYPES
    // ------------------------------------------------------------------------

    // ------------------------------------------------------------------------
    // STATIC FIELDS
    // ------------------------------------------------------------------------

    private static final String TAG = SlidingPuzzleApplication.class.getSimpleName();


    // ------------------------------------------------------------------------
    // STATIC METHODS
    // ------------------------------------------------------------------------

    // ------------------------------------------------------------------------
    // FIELDS
    // ------------------------------------------------------------------------
    private static DaggerComponentGraph graph;


    private static SlidingPuzzleApplication sInstance;

    @Inject
    public SolutionsHandler mSolutionsHandler;

    @Inject
    public SharedPreferences mPreferences;

    // ------------------------------------------------------------------------
    // CONSTRUCTORS
    // ------------------------------------------------------------------------

    // ------------------------------------------------------------------------
    // METHODS
    // ------------------------------------------------------------------------

    @Override
    public void onCreate() {
        super.onCreate();


        if (BuildConfig.DEBUG) {
            // Start the layout cast service
            LayoutCast.init(this);
            // Initialize the Layout Cats
            Stetho.initializeWithDefaults(this);
        }

        sInstance = this;
        buildComponentAndInject();
    }


    /**
     * Rebuilds the dagger generated object graph
     */
    public static void buildComponentAndInject() {
        graph = DaggerGameComponent.Initializer.init(sInstance);
        graph.inject(sInstance);
    }

    /**
     * @return an observable which emits a true or false if the application is initialized properly
     */
    public Observable<Boolean> initializeApplication() {
        return mSolutionsHandler.generateSolutions();
    }

    /**
     * @return the Gagger generate graph
     */
    public static DaggerComponentGraph component() {
        return graph;
    }

    // ------------------------------------------------------------------------
    // GETTERS / SETTTERS
    // ------------------------------------------------------------------------
}
