package com.exarlabs.android.slidingpuzzle.ui.game.board;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.exarlabs.android.slidingpuzzle.R;
import com.exarlabs.android.slidingpuzzle.model.board.BoardState;
import com.exarlabs.android.slidingpuzzle.utils.FontUtil;

import butterknife.BindColor;
import butterknife.BindDimen;
import butterknife.ButterKnife;

/**
 * Generates a Tile
 * Created by becze on 9/17/2015.
 */
public class TileView extends View {

    // ------------------------------------------------------------------------
    // TYPES
    // ------------------------------------------------------------------------


    // ------------------------------------------------------------------------
    // STATIC FIELDS
    // ------------------------------------------------------------------------
    private static final long COLOR_ANIM_DURATION_UP = 100;
    private static final long COLOR_ANIM_DURATION_DOWN = 100;

    // ------------------------------------------------------------------------
    // STATIC METHODS
    // ------------------------------------------------------------------------

    // ------------------------------------------------------------------------
    // FIELDS
    // ------------------------------------------------------------------------

    // height in pixels of this tile.
    private final int mHeight;


    // we save it locally the tile poition
    private int mX;
    private int mY;

    // the index of this tile
    private final int mIndex;
    private Paint mTilePaint;
    private Paint mLabelPaint;

    @BindColor(R.color.tile_text)
    public int mTileText;

    @BindColor(R.color.tile_text_accent)
    public int mTileTextAccent;

    @BindColor(R.color.tile_default)
    public int mBackgroundDefault;

    @BindColor(R.color.tile_error)
    public int mBackgroundError;

    @BindColor(R.color.tile_highlight)
    public int mBackgroundHighlighted;

    @BindDimen(R.dimen.tiles_label_size)
    public int mTileLabelSize;

    @BindDimen(R.dimen.tiles_rounding)
    public int mTileRounding;


    private ValueAnimator.AnimatorUpdateListener updateListener = new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            invalidate();
        }
    };

    // ------------------------------------------------------------------------
    // CONSTRUCTORS
    // ------------------------------------------------------------------------
    public TileView(Context context, int height, int index) {
        super(context);
        mIndex = index;
        mHeight = height;
        init();
    }


    // ------------------------------------------------------------------------
    // METHODS
    // ------------------------------------------------------------------------
    private void init() {

        ButterKnife.bind(this);

        mTilePaint = new Paint();
        mTilePaint.setColor(mBackgroundDefault);
        mTilePaint.setAntiAlias(true);
        mTilePaint.setShadowLayer(40, 40, 40, mBackgroundError );


        mLabelPaint = new Paint();
        mLabelPaint.setColor(getResources().getColor(android.R.color.black));
        mLabelPaint.setTextSize(mTileLabelSize);
        mLabelPaint.setAntiAlias(true);
        mLabelPaint.setTypeface(FontUtil.getInstance().getNormal());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mIndex != BoardState.EMPTY_TILE_INDEX) {

            // Draw the Tile
            RectF tile = new RectF(0, 0, mHeight, mHeight);
            canvas.drawRoundRect(tile, mTileRounding, mTileRounding, mTilePaint);

            // Draw the Tile Label
            String label = Integer.toString(mIndex);
            Rect bounds = new Rect();
            mLabelPaint.getTextBounds(label, 0, label.length(), bounds);
            canvas.drawText(label, mHeight / 2 - bounds.width() / 2, mHeight / 2 + bounds.height() / 2, mLabelPaint);

        }
    }


    // ------------------------------------------------------------------------
    // GETTERS / SETTTERS
    // ------------------------------------------------------------------------
    public int getIndex() {
        return mIndex;
    }

    @Override
    public String toString() {
        return "Tile: " + getIndex();
    }


    /**
     * Highlights the background of the tile
     */
    public void onTileTapped(boolean isMovable) {
        int highLightedColor = isMovable ? mBackgroundHighlighted : mBackgroundError;
        highlight(false, mBackgroundDefault, highLightedColor);
    }

    private void highlight(final boolean isHighlighted, final int baseColor, final int highlightedColor) {

        ObjectAnimator colorAnimator = ObjectAnimator.ofObject(mTilePaint, "color", new ArgbEvaluator(), baseColor, highlightedColor).setDuration(
                        isHighlighted ? COLOR_ANIM_DURATION_UP : COLOR_ANIM_DURATION_DOWN);

        colorAnimator.addUpdateListener(updateListener);
        colorAnimator.setInterpolator(new DecelerateInterpolator());
        colorAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (!isHighlighted) {
                    highlight(true, highlightedColor, baseColor);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        colorAnimator.start();
    }

    public void setTileX(int x) {
        mX = x;
    }

    public void setTileY(int y) {
        mY = y;
    }

    public int getTileX() {
        return mX;
    }

    public int getTileY() {
        return mY;
    }


}
