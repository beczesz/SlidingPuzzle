package com.exarlabs.android.slidingpuzzle.ui.settings;

import javax.inject.Inject;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.exarlabs.android.slidingpuzzle.R;
import com.exarlabs.android.slidingpuzzle.SlidingPuzzleApplication;
import com.exarlabs.android.slidingpuzzle.business.AppConstants;
import com.exarlabs.android.slidingpuzzle.ui.ExarFragment;

/**
 * Displays a screen with the fragments.
 * Created by becze on 9/30/2015.
 */
public class SettingsFragment extends ExarFragment implements RadioGroup.OnCheckedChangeListener {

    // ------------------------------------------------------------------------
    // TYPES
    // ------------------------------------------------------------------------

    // ------------------------------------------------------------------------
    // STATIC FIELDS
    // ------------------------------------------------------------------------

    // ------------------------------------------------------------------------
    // STATIC METHODS
    // ------------------------------------------------------------------------

    /**
     * @return newInstance of SettingsFragment
     */
    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    // ------------------------------------------------------------------------
    // FIELDS
    // ------------------------------------------------------------------------

    private RadioGroup mBoardSizedGroup;

    @Inject
    public SharedPreferences mPrefs;

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
        View rootView = inflater.inflate(R.layout.settings_layout, null);

        init(rootView);

        return rootView;
    }

    /**
     * Initializes the views
     *
     * @param rootView
     */
    private void init(View rootView) {

        int boardSize = mPrefs.getInt(AppConstants.SP_KEY_BOARD_SIZE, AppConstants.DEFAULT_BOARD_SIZE);

        mBoardSizedGroup = (RadioGroup) rootView.findViewById(R.id.board_size_radio_group);
        mBoardSizedGroup.setOnCheckedChangeListener(this);

        switch (boardSize) {
            case 3:
                ((RadioButton) mBoardSizedGroup.findViewById(R.id.option3x3)).setChecked(true);
                break;

            case 4:
                ((RadioButton) mBoardSizedGroup.findViewById(R.id.option4x4)).setChecked(true);
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        int boardSize = mPrefs.getInt(AppConstants.SP_KEY_BOARD_SIZE, AppConstants.DEFAULT_BOARD_SIZE);

        switch (checkedId) {
            case R.id.option3x3:
                boardSize = 3;
                break;

            case R.id.option4x4:
                boardSize = 4;
                break;
        }

        // save the new board size
        mPrefs.edit().putInt(AppConstants.SP_KEY_BOARD_SIZE, boardSize).commit();
    }


    // ------------------------------------------------------------------------
    // GETTERS / SETTTERS
    // ------------------------------------------------------------------------
}
