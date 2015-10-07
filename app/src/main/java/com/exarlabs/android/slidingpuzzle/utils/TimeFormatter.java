package com.exarlabs.android.slidingpuzzle.utils;

/**
 * Created by becze on 10/7/2015.
 */
public class TimeFormatter {

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

    /**
     * Format the duration in mm:ss:ms format
     * @param duration
     * @return
     */
    public static String format(int duration) {
        return String.format("%02d:%02d:%02d", duration / (1000 * 60), (duration / 1000) % 60, (duration % 1000) / 10);
    }

    /**
     * Format the duration in mm:ss:ms format
     * @param duration
     * @return
     */
    public static String formatShort(int duration) {
        return String.format("%.1f s", duration / 1000f);
    }

    // ------------------------------------------------------------------------
    // GETTERS / SETTTERS
    // ------------------------------------------------------------------------
}