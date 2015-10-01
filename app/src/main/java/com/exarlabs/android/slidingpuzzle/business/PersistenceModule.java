package com.exarlabs.android.slidingpuzzle.business;

import javax.inject.Singleton;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.exarlabs.android.slidingpuzzle.BuildConfig;
import com.exarlabs.android.slidingpuzzle.business.score.ScoreHandler;
import com.exarlabs.android.slidingpuzzle.business.solutions.SolutionsHandler;
import com.exarlabs.android.slidingpuzzle.model.dao.DaoMaster;
import com.exarlabs.android.slidingpuzzle.model.dao.DaoSession;

import dagger.Module;
import dagger.Provides;

/**
 * Module for providing the persistence layer for the app
 * Created by becze on 9/21/2015.
 */
@Module
public class PersistenceModule {


    // ------------------------------------------------------------------------
    // TYPES
    // ------------------------------------------------------------------------

    // ------------------------------------------------------------------------
    // STATIC FIELDS
    // ------------------------------------------------------------------------

    public static final String DB_NAME = "sliding_puzzle-db";
    // ------------------------------------------------------------------------
    // STATIC METHODS
    // ------------------------------------------------------------------------

    // ------------------------------------------------------------------------
    // FIELDS
    // ------------------------------------------------------------------------

    public Context mContext;

    private DaoMaster.OpenHelper helper;

    // ------------------------------------------------------------------------
    // CONSTRUCTORS
    // ------------------------------------------------------------------------

    public PersistenceModule(Context context) {
        mContext = context;
    }


    // ------------------------------------------------------------------------
    // METHODS
    // ------------------------------------------------------------------------

    // ------------------------------------------------------------------------
    // GETTERS / SETTTERS
    // ------------------------------------------------------------------------


    @Provides
    @Singleton
    protected DaoSession provideSession() {
        // Initialize the GreenDao core
        if (BuildConfig.DEBUG) {
            helper = new DaoMaster.DevOpenHelper(mContext, DB_NAME, null);
        } else {
            helper = new DaoMaster.DevOpenHelper(mContext, DB_NAME, null);
        }

        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        return daoMaster.newSession();
    }

    @Provides
    @Singleton
    protected SolutionsHandler provideSolutionHandler() {
        return new SolutionsHandler();
    }

    @Provides
    @Singleton
    protected ScoreHandler provideScoreHandler() {
        return new ScoreHandler();
    }

}
