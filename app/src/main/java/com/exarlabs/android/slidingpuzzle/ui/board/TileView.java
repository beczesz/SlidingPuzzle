package com.exarlabs.android.slidingpuzzle.ui.board;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

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

    // ------------------------------------------------------------------------
    // STATIC METHODS
    // ------------------------------------------------------------------------

    // ------------------------------------------------------------------------
    // FIELDS
    // ------------------------------------------------------------------------

    // height in pixels of this tile.
    private final int mHeight;

    // the index of this tile


    private final int mIndex;
    private Paint mTilePaint;
    private Paint mLabelPaint;

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
        mTilePaint = new Paint();
        mTilePaint.setColor(getResources().getColor(android.R.color.holo_green_light));

        mLabelPaint = new Paint();
        mLabelPaint.setColor(getResources().getColor(android.R.color.black));
        mLabelPaint.setTextSize(80);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawRect(0, 0, mHeight, mHeight, mTilePaint);
        canvas.drawText(Integer.toString(mIndex), mHeight / 2, mHeight / 2, mLabelPaint);
    }


    // ------------------------------------------------------------------------
    // GETTERS / SETTTERS
    // ------------------------------------------------------------------------
    public int getIndex() {
        return mIndex;
    }
}
