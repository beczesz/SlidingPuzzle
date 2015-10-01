package com.exarlabs.android.slidingpuzzle.business.score;

import java.util.List;

import javax.inject.Inject;

import com.exarlabs.android.slidingpuzzle.SlidingPuzzleApplication;
import com.exarlabs.android.slidingpuzzle.model.dao.DaoSession;
import com.exarlabs.android.slidingpuzzle.model.dao.Play;
import com.exarlabs.android.slidingpuzzle.model.dao.PlayDao;

/**
 * Handler for requesting and saving Plays. It also generates
 * Created by becze on 9/30/2015.
 */
public class ScoreHandler {

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

    @Inject
    public DaoSession mDaoSession;

    private final PlayDao mPlayDao;
    // ------------------------------------------------------------------------
    // CONSTRUCTORS
    // ------------------------------------------------------------------------

    public ScoreHandler() {

        // Inject the fields
        SlidingPuzzleApplication.component().inject(this);

        mPlayDao = mDaoSession.getPlayDao();
    }

    /**
     * Saves into the database the given Play.
     *
     * @param play
     */
    public void savePlay(Play play) {
        mPlayDao.insert(play);
    }

    /**
     * Returns the top numberOfPlays with the shortest solution time.
     *
     * @param boardSize
     * @param numberOfPlays
     * @return a list with the plays or an empty list when there is no games played yet.
     */
    public List<Play> getTopPlaysByTime(int boardSize, int numberOfPlays) {
        //@formatter:off
        return mPlayDao.queryBuilder()
                        .where(PlayDao.Properties.BoardSize.eq(boardSize))
                        .limit(numberOfPlays)
                        .orderAsc(PlayDao.Properties.Duration)
                        .build()
                        .list();
        //@formatter:on
    }


    // ------------------------------------------------------------------------
    // METHODS
    // ------------------------------------------------------------------------


    // ------------------------------------------------------------------------
    // GETTERS / SETTTERS
    // ------------------------------------------------------------------------
}
