package com.exarlabs.android.slidingpuzzle.business.solutions;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.exarlabs.android.slidingpuzzle.SlidingPuzzleApplication;
import com.exarlabs.android.slidingpuzzle.business.AppConstants;
import com.exarlabs.android.slidingpuzzle.model.dao.DaoSession;
import com.exarlabs.android.slidingpuzzle.model.dao.GeneratedSolution;
import com.exarlabs.android.slidingpuzzle.model.dao.GeneratedSolutionDao;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Reads and decode a board state from the database. If the database does not
 * exist it creates one and populates its content.
 * Created by becze on 9/21/2015.
 */
public class SolutionsHandler {

    // ------------------------------------------------------------------------
    // TYPES
    // ------------------------------------------------------------------------

    private static final String TAG = SolutionsHandler.class.getSimpleName();


    private final static String ASSET_NAME_SOLUTIONS_3X3 = "gen_3x3_depth_25.txt";
    private final static String ASSET_NAME_SOLUTIONS_4X4 = "gen_4x4_depth_64.txt";

    // ------------------------------------------------------------------------
    // STATIC FIELDS
    // ------------------------------------------------------------------------

    // ------------------------------------------------------------------------
    // STATIC METHODS
    // ------------------------------------------------------------------------

    // ------------------------------------------------------------------------
    // FIELDS
    // ------------------------------------------------------------------------

    @Inject
    public DaoSession mDaoSession;

    @Inject
    public SharedPreferences mPrefs;

    @Inject
    public Context mContext;

    private final GeneratedSolutionDao mSolutionsDao;
    // ------------------------------------------------------------------------
    // CONSTRUCTORS
    // ------------------------------------------------------------------------

    public SolutionsHandler() {
        SlidingPuzzleApplication.component().inject(this);

        mSolutionsDao = mDaoSession.getGeneratedSolutionDao();

    }


    // ------------------------------------------------------------------------
    // METHODS
    // ------------------------------------------------------------------------

    public boolean isDatabaseGenerated() {
        return mPrefs.getBoolean(AppConstants.SP_KEY_DATABASE_GENERATED, false);
    }

    /**
     * Generates the solutions in a background thread and notifies the subscriber if it is done.
     *
     * @param subscriber
     */
    public void generateSolutions(Observer<Boolean> subscriber) {
        //@formatter:off
        getGenerator()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(subscriber);
        //@formatter:on
    }

    @NonNull
    private Observable<Boolean> getGenerator() {
        return Observable.just(true).map(new Func1<Boolean, Boolean>() {
            @Override
            public Boolean call(Boolean aBoolean) {
                return generateSolutions();
            }
        });
    }

    /**
     * Reads the encoded solutions from the assets and stores into database.
     */
    private boolean generateSolutions() {
        return generate3x3() && generate4x4();
    }

    private boolean generate4x4() {
        try {
            InputStream inputStream = mContext.getAssets().open(ASSET_NAME_SOLUTIONS_4X4);

            // for 4x4 with 64 steps we can encode it into 16 bytes, so our buffer will be 16
            byte[] buffer = new byte[16];
            List<GeneratedSolution> solutions = new ArrayList<>();

            while ((inputStream.read(buffer)) != -1) {
                GeneratedSolution solution = new GeneratedSolution();
                solution.setSize(4);
                solution.setSteps(buffer);
                solutions.add(solution);
            }

            inputStream.close();

            // insert into the database all the solutions
            mSolutionsDao.insertInTx(solutions);

            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean generate3x3() {
        try {
            InputStream inputStream = mContext.getAssets().open(ASSET_NAME_SOLUTIONS_3X3);

            // for 3x3 with 25 steps we can encode it into 7 bytes, so our buffer will be 7
            byte[] buffer = new byte[7];
            List<GeneratedSolution> solutions = new ArrayList<>();

            while ((inputStream.read(buffer)) != -1) {
                GeneratedSolution solution = new GeneratedSolution();
                solution.setSize(3);
                solution.setSteps(buffer);
                solutions.add(solution);
            }

            inputStream.close();

            // insert into the database all the solutions
            mSolutionsDao.insertInTx(solutions);

            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public byte[] getRandomSolution(int boardSize) {
        return null;
    }

    // ------------------------------------------------------------------------
    // GETTERS / SETTTERS
    // ------------------------------------------------------------------------
}
