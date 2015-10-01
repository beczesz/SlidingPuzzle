package com.exarlabs.android.slidingpuzzle;

import javax.inject.Inject;

import android.app.Application;
import android.content.SharedPreferences;

import com.exarlabs.android.slidingpuzzle.business.DaggerComponentGraph;
import com.exarlabs.android.slidingpuzzle.business.DaggerGameComponent;
import com.exarlabs.android.slidingpuzzle.business.solutions.SolutionsHandler;
import com.exarlabs.android.slidingpuzzle.utils.FontUtil;
import com.facebook.stetho.Stetho;
import com.github.mmin18.layoutcast.LayoutCast;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.IoniconsModule;
import com.tsengvn.typekit.Typekit;

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

        // Build the dagger components
        buildComponentAndInject(this);

        // Initialize all the rest of the libs
        initializeLibs();
    }

    /**
     * Initializes utility libs
     */
    private void initializeLibs() {
        if (BuildConfig.DEBUG) {
            // Start the layout cast service
            LayoutCast.init(this);
            // Initialize the Layout Cats
            Stetho.initializeWithDefaults(this);
        }

        //@formatter:off
        Typekit.getInstance()
                        .addNormal(FontUtil.getInstance().getNormal())
                        .addBold(FontUtil.getInstance().getBold());

        //@formatter:on

        Iconify.with(new IoniconsModule());
    }


    /**
     * Rebuilds the dagger generated object graph
     */
    public static void buildComponentAndInject(SlidingPuzzleApplication app) {
        graph = DaggerGameComponent.Initializer.init(app);
        graph.inject(app);
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
