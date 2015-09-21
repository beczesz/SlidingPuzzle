package com.exarlabs.android.slidingpuzzle;

import android.app.Application;

import com.exarlabs.android.slidingpuzzle.business.DaggerComponentGraph;
import com.exarlabs.android.slidingpuzzle.business.DaggerGameComponent;
import com.facebook.stetho.Stetho;
import com.github.mmin18.layoutcast.LayoutCast;

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
