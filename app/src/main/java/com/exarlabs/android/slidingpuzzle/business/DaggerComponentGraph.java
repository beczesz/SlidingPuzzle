package com.exarlabs.android.slidingpuzzle.business;

import com.exarlabs.android.slidingpuzzle.business.board.GameHandler;
import com.exarlabs.android.slidingpuzzle.ui.BoardGameActivity;
import com.exarlabs.android.slidingpuzzle.ui.ExarActivity;
import com.exarlabs.android.slidingpuzzle.ui.ExarFragment;
import com.exarlabs.android.slidingpuzzle.ui.board.BoardFragment;
import com.exarlabs.android.slidingpuzzle.ui.board.BoardPresenter;
import com.exarlabs.android.slidingpuzzle.ui.board.BoardView;

/**
 * Here are listed all the places where the component is used
 * Created by becze on 9/17/2015.
 */
public interface DaggerComponentGraph {

    void inject(BoardView boardView);
    void inject(BoardPresenter boardPresenter);
    void inject(GameHandler gameHandler);
    void inject(BoardGameActivity boardGameActivity);
    void inject(BoardFragment boardFragment);

    // the will nto be actually used.
    void inject(ExarActivity exarActivity);
    void inject(ExarFragment exarFragment);

}
