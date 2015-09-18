package com.exarlabs.android.slidingpuzzle.business;

import javax.inject.Singleton;

import com.exarlabs.android.slidingpuzzle.business.board.GameHandler;
import com.exarlabs.android.slidingpuzzle.ui.board.BoardPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by becze on 9/17/2015.
 */
@Module
public class BoardModule {

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

    @Singleton
    @Provides
    public GameHandler provideGameHandler () {
        return new GameHandler();
    }

    @Provides
    public BoardPresenter provideBoardPresenter() {
        return new BoardPresenter();
    }

    // ------------------------------------------------------------------------
    // GETTERS / SETTTERS
    // ------------------------------------------------------------------------
}
