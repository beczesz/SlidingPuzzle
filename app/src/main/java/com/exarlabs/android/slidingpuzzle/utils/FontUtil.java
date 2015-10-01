package com.exarlabs.android.slidingpuzzle.utils;

import javax.inject.Inject;

import android.content.Context;
import android.graphics.Typeface;

import com.exarlabs.android.slidingpuzzle.SlidingPuzzleApplication;
import com.tsengvn.typekit.Typekit;

/**
 *
 * Created by becze on 9/24/2015.
 */
public class FontUtil {

    // ------------------------------------------------------------------------
    // TYPES
    // ------------------------------------------------------------------------

    private static class Holder {
        static final FontUtil INSTANCE = new FontUtil();
    }

    // ------------------------------------------------------------------------
    // STATIC FIELDS
    // ------------------------------------------------------------------------

    public static FontUtil getInstance() {
        return Holder.INSTANCE;
    }

    // ------------------------------------------------------------------------
    // STATIC METHODS
    // ------------------------------------------------------------------------

    // ------------------------------------------------------------------------
    // FIELDS
    // ------------------------------------------------------------------------
    @Inject
    public Context mContext;

    private Typeface mNormal;
    private Typeface mBold;

    // ------------------------------------------------------------------------
    // CONSTRUCTORS
    // ------------------------------------------------------------------------

    public FontUtil() {
        SlidingPuzzleApplication.component().inject(this);
        mNormal = Typekit.createFromAsset(mContext, "fonts/Roboto-Light.ttf");
        mBold = Typekit.createFromAsset(mContext, "fonts/Roboto-Medium.ttf");
    }

    // ------------------------------------------------------------------------
    // METHODS
    // ------------------------------------------------------------------------

    // ------------------------------------------------------------------------
    // GETTERS / SETTTERS
    // ------------------------------------------------------------------------

    public Typeface getNormal() {
        return mNormal;
    }

    public Typeface getBold() {
        return mBold;
    }
}
