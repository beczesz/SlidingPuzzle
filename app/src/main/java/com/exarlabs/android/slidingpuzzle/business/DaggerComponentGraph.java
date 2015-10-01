package com.exarlabs.android.slidingpuzzle.business;

import com.exarlabs.android.slidingpuzzle.SlidingPuzzleApplication;
import com.exarlabs.android.slidingpuzzle.business.board.GameHandler;
import com.exarlabs.android.slidingpuzzle.business.score.ScoreHandler;
import com.exarlabs.android.slidingpuzzle.business.solutions.SolutionsHandler;
import com.exarlabs.android.slidingpuzzle.ui.SlidingPuzzleActivity;
import com.exarlabs.android.slidingpuzzle.ui.game.BoardGameFragment;
import com.exarlabs.android.slidingpuzzle.ui.game.board.BoardFragment;
import com.exarlabs.android.slidingpuzzle.ui.game.board.BoardPresenter;
import com.exarlabs.android.slidingpuzzle.ui.game.board.BoardView;
import com.exarlabs.android.slidingpuzzle.ui.highscores.HighScoresFragment;
import com.exarlabs.android.slidingpuzzle.ui.menu.MainMenuFragment;
import com.exarlabs.android.slidingpuzzle.ui.settings.SettingsFragment;
import com.exarlabs.android.slidingpuzzle.ui.splash.SplashScreenActivity;
import com.exarlabs.android.slidingpuzzle.ui.game.stats.StatsFragment;
import com.exarlabs.android.slidingpuzzle.utils.FontUtil;

/**
 * Here are listed all the places where the component is used
 * Created by becze on 9/17/2015.
 */
public interface DaggerComponentGraph {

    void inject(BoardView boardView);

    void inject(BoardPresenter boardPresenter);

    void inject(GameHandler gameHandler);

    void inject(BoardFragment boardFragment);

    void inject(SplashScreenActivity splashScreenActivity);

    void inject(SolutionsHandler solutionsHandler);

    void inject(SlidingPuzzleApplication instance);

    void inject(StatsFragment statsFragment);

    void inject(BoardGameFragment boardGameFragment);

    void inject(FontUtil fontUtil);

    void inject(SlidingPuzzleActivity slidingPuzzleActivity);

    void inject(MainMenuFragment mainMenuFragment);

    void inject(SettingsFragment settingsFragment);

    void inject(ScoreHandler scoreHandler);

    void inject(HighScoresFragment highScoresFragment);
}
