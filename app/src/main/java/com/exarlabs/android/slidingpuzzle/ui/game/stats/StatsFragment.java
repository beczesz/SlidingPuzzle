package com.exarlabs.android.slidingpuzzle.ui.game.stats;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.exarlabs.android.slidingpuzzle.R;
import com.exarlabs.android.slidingpuzzle.SlidingPuzzleApplication;
import com.exarlabs.android.slidingpuzzle.business.board.GamEvent;
import com.exarlabs.android.slidingpuzzle.business.board.GameHandler;
import com.exarlabs.android.slidingpuzzle.ui.ExarFragment;

import butterknife.Bind;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Displays the game stats to the user.
 * Created by becze on 9/24/2015.
 */
public class StatsFragment extends ExarFragment {

    // ------------------------------------------------------------------------
    // TYPES
    // ------------------------------------------------------------------------
    private static final int MAX_DURATION = 24 * 60 * 60 * 1000;

    // ------------------------------------------------------------------------
    // STATIC FIELDS
    // ------------------------------------------------------------------------

    // ------------------------------------------------------------------------
    // STATIC METHODS
    // ------------------------------------------------------------------------

    /**
     * @return newInstance of StatsFragment
     */
    public static StatsFragment newInstance() {
        return new StatsFragment();
    }

    // ------------------------------------------------------------------------
    // FIELDS
    // ------------------------------------------------------------------------

    @Bind(R.id.tv_time_elapsed)
    public TextView mTimeElapsedTextView;

    @Bind(R.id.tv_steps_taken)
    public TextView mStepsTakenTextView;

    @Inject
    public GameHandler mGameHandler;

    @Inject
    public SharedPreferences mPreferences;

    // Internal timer used to signalize the game time.
    private Subscription mGameTimer;

    // ------------------------------------------------------------------------
    // CONSTRUCTORS
    // ------------------------------------------------------------------------

    // ------------------------------------------------------------------------
    // METHODS
    // ------------------------------------------------------------------------


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inject the variables
        SlidingPuzzleApplication.component().inject(this);

        mGameHandler.subscribe(new Subscriber<GamEvent>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(GamEvent gamEvent) {
                switch (gamEvent.getEventType()) {
                    case GamEvent.GAME_RESET:
                        stopTimer();
                        break;

                    case GamEvent.GAME_SHUFFLED:
                        startTimer();
                        break;

                    case GamEvent.GAME_SOLVED:
                        stopTimer();
                        break;

                    case GamEvent.MOVE_MADE:
                        refreshStats();
                        break;

                    case GamEvent.CLOCK_TICK:
                        refreshTime((Integer) gamEvent.getEventObject());
                        break;

                }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.game_stats, null);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        refreshTime(0);
        refreshStats();
    }

    /**
     * Refreshes the game stats
     */
    private void refreshStats() {
        if (mStepsTakenTextView != null) {
            mStepsTakenTextView.setText(Integer.toString(mGameHandler.getNumberOfUserMoves()));
        }
    }

    /**
     * Refreshes the game stats
     */
    private void refreshTime(int duration) {
        if (mTimeElapsedTextView != null && duration < MAX_DURATION) {
            mTimeElapsedTextView.setText(format(duration));
        }
    }

    private String format(int duration) {
        return String.format("%02d:%02d:%02d", duration / (1000 * 60), (duration / 1000) % 60, (duration % 1000) / 10);
    }

    private void startTimer() {
        stopTimer();

        //@formatter:off
        mGameTimer = Observable.interval(10, TimeUnit.MILLISECONDS)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<Long>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(Long aLong) {
                                // notify the subscribers that the game has been reset
                                refreshTime(mGameHandler.getGameDuration());
                            }
                        });

        //@formatter:on
    }

    /**
     * Stops the timer if it is not already stopped.
     */
    private void stopTimer() {
        if (mGameTimer != null && !mGameTimer.isUnsubscribed()) {
            mGameTimer.unsubscribe();
        }
    }


    // ------------------------------------------------------------------------
    // GETTERS / SETTTERS
    // ------------------------------------------------------------------------
}
