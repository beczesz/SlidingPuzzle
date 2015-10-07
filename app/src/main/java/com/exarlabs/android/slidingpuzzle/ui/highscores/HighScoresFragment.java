package com.exarlabs.android.slidingpuzzle.ui.highscores;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Inject;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.exarlabs.android.slidingpuzzle.R;
import com.exarlabs.android.slidingpuzzle.SlidingPuzzleApplication;
import com.exarlabs.android.slidingpuzzle.business.score.ScoreHandler;
import com.exarlabs.android.slidingpuzzle.model.dao.Play;
import com.exarlabs.android.slidingpuzzle.ui.ExarFragment;
import com.exarlabs.android.slidingpuzzle.utils.Pair;
import com.exarlabs.android.slidingpuzzle.utils.TimeFormatter;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Displays the top scores by the player.
 * Created by becze on 9/30/2015.
 */
public class HighScoresFragment extends ExarFragment {

    // ------------------------------------------------------------------------
    // TYPES
    // ------------------------------------------------------------------------

    static class ViewHolder {

        @Bind(R.id.high_score_item_number)
        TextView mHighScoreItemNumber;

        @Bind(R.id.high_score_3x3_value)
        TextView mHighScore3x3Value;

        @Bind(R.id.high_score_4x4_value)
        TextView mHighScore4x4Value;


        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    /**
     * Adapter for the high score list view.
     */
    private class HiScoreAdapter extends ArrayAdapter<Pair<Play, Play>> {


        public HiScoreAdapter(Context context) {
            super(context, R.layout.high_score_view, new ArrayList<Pair<Play, Play>>());
        }

        @Override
        public android.view.View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            // Get the holder
            if (convertView != null) {
                // get the view holder
                holder = (ViewHolder) convertView.getTag();
            } else {
                LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.high_score_view, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            }

            // Initialize with the row.
            Pair<Play, Play> item = getItem(position);
            holder.mHighScoreItemNumber.setText("#" + (position + 1));
            holder.mHighScore3x3Value.setText(item.first != null ? TimeFormatter.formatShort(item.first.getDuration()) : "");
            holder.mHighScore4x4Value.setText(item.second != null ? TimeFormatter.formatShort(item.second.getDuration()) : "");
            return convertView;
        }

    }

    // ------------------------------------------------------------------------
    // STATIC FIELDS
    // ------------------------------------------------------------------------
    private static final int NUMBER_OF_PLAYS_SHOWN = 10;

    // ------------------------------------------------------------------------
    // STATIC METHODS
    // ------------------------------------------------------------------------

    /**
     * @return newInstance of HighScoresFragment
     */
    public static HighScoresFragment newInstance() {
        return new HighScoresFragment();
    }

    // ------------------------------------------------------------------------
    // FIELDS
    // ------------------------------------------------------------------------

    private HiScoreAdapter mHiScoreAdapter;

    @Bind(R.id.high_scores)
    public ListView mHighScores;

    @Inject
    public ScoreHandler mScoreHandler;

    // ------------------------------------------------------------------------
    // CONSTRUCTORS
    // ------------------------------------------------------------------------

    // ------------------------------------------------------------------------
    // METHODS
    // ------------------------------------------------------------------------


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SlidingPuzzleApplication.component().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.high_scores_layout, null);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mHiScoreAdapter = new HiScoreAdapter(getActivity());
        mHighScores.setAdapter(mHiScoreAdapter);

        // initializes the mHiScoreAdapter with the high scores
        getHighScores();

    }

    /**
     * Creates an observer which reads from the database.
     */
    private void getHighScores() {
        //@formatter:off
        Observable.just(true).map(new Func1<Boolean, List<Pair<Play,Play>> >() {
            @Override
            public  List<Pair<Play,Play>>  call(Boolean aBoolean) {
                List<Play> topPlaysByTime3x3 = mScoreHandler.getTopPlaysByTime(3, NUMBER_OF_PLAYS_SHOWN);
                List<Play> topPlaysByTime4x4 = mScoreHandler.getTopPlaysByTime(4, NUMBER_OF_PLAYS_SHOWN);

                 List<Pair<Play,Play>> result = new ArrayList<>();
                for(int i = 0; i < Math.max(topPlaysByTime3x3.size(), topPlaysByTime4x4.size()); i++) {
                   Play play3x3 = topPlaysByTime3x3.size() > i ? topPlaysByTime3x3.get(i) : null;
                   Play play4x4 = topPlaysByTime4x4.size() > i ? topPlaysByTime4x4.get(i) : null;
                    result.add(new Pair<>(play3x3, play4x4));
                }

                return result;
            }
        }).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer< List<Pair<Play,Play>> >() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext( List<Pair<Play,Play>>  pairs) {
                mHiScoreAdapter.addAll(pairs);
            }
        });

        //@formatter:on
    }


// ------------------------------------------------------------------------
// GETTERS / SETTTERS
// ------------------------------------------------------------------------
}
