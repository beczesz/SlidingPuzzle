package com.exarlabs.android.slidingpuzzle.ui;

import javax.inject.Inject;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.exarlabs.android.slidingpuzzle.R;
import com.exarlabs.android.slidingpuzzle.SlidingPuzzleApplication;
import com.exarlabs.android.slidingpuzzle.business.AppConstants;
import com.exarlabs.android.slidingpuzzle.business.board.GameHandler;
import com.exarlabs.android.slidingpuzzle.ui.board.BoardFragment;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Activity which holds the board and connects with the model and the solver.
 */
public class BoardGameActivity extends AppCompatActivity {

    // ------------------------------------------------------------------------
    // TYPES
    // ------------------------------------------------------------------------

    private static final String TAG = BoardGameActivity.class.getSimpleName();


    // ------------------------------------------------------------------------
    // STATIC FIELDS
    // ------------------------------------------------------------------------

    // ------------------------------------------------------------------------
    // STATIC METHODS
    // ------------------------------------------------------------------------

    // ------------------------------------------------------------------------
    // FIELDS
    // ------------------------------------------------------------------------

    // The board fragment
    private BoardFragment mBoardFragment;

    @Inject
    public GameHandler mGameHandler;
    
    @Inject
    public SharedPreferences mPreferences;

    // ------------------------------------------------------------------------
    // CONSTRUCTORS
    // ------------------------------------------------------------------------

    // ------------------------------------------------------------------------
    // METHODS
    // ------------------------------------------------------------------------

    // ------------------------------------------------------------------------
    // GETTERS / SETTTERS
    // ------------------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_game);
        ButterKnife.bind(this);
        SlidingPuzzleApplication.component().inject(this);

        // Embed the board
        mBoardFragment = new BoardFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.board_container, mBoardFragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_board_game, menu);
        return true;
    }

    @OnClick(R.id.reset_board)
    public void resetBoard() {
        mGameHandler.initializeGame();
    }

    @OnClick(R.id.shuffle_board)
    public void shuffleBoard() {
        mGameHandler.shuffle();
    }
    
    @OnClick ({R.id.option3x3, R.id.option4x4})
    public void optionsChanged(View v) {
        switch (v.getId()) {
            case R.id.option3x3:
                mPreferences.edit().putInt(AppConstants.SP_KEY_BOARD_SIZE, 3).commit();
                break;
            case R.id.option4x4:
                mPreferences.edit().putInt(AppConstants.SP_KEY_BOARD_SIZE, 4).commit();
                break;
        }

        resetBoard();
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
