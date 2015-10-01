package com.exarlabs.android.slidingpuzzle.business.board;

/**
 * Created by becze on 9/18/2015.
 */
public class GamEvent {

    // ------------------------------------------------------------------------
    // TYPES
    // ------------------------------------------------------------------------

    public static final int MOVE_MADE = 1;
    public static final int GAME_RESET = 2;
    public static final int GAME_SHUFFLED = 3;
    public static final int GAME_SOLVED = 4;
    public static final int CLOCK_TICK = 5;

    // ------------------------------------------------------------------------
    // STATIC FIELDS
    // ------------------------------------------------------------------------

    // ------------------------------------------------------------------------
    // STATIC METHODS
    // ------------------------------------------------------------------------

    // ------------------------------------------------------------------------
    // FIELDS
    // ------------------------------------------------------------------------

    private int mEventType;

    private Object mEventObject;

    // ------------------------------------------------------------------------
    // CONSTRUCTORS
    // ------------------------------------------------------------------------

    /**
     * Creates a new game event with the type and the object specified
     * @param eventType
     * @param eventObject
     */
    public GamEvent(int eventType, Object eventObject) {
        mEventType = eventType;
        mEventObject = eventObject;
    }


    // ------------------------------------------------------------------------
    // METHODS
    // ------------------------------------------------------------------------

    // ------------------------------------------------------------------------
    // GETTERS / SETTTERS
    // ------------------------------------------------------------------------

    public Object getEventObject() {
        return mEventObject;
    }

    public int getEventType() {
        return mEventType;
    }
}
