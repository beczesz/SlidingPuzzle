package com.exarlabs.android.slidingpuzzle.model.dao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "PLAY".
 */
public class Play {

    private Long id;
    /** Not-null value. */
    private java.util.Date startDate;
    private int boardSize;
    private int duration;
    private int numberOfMoves;
    /** Not-null value. */
    private byte[] encodedMoves;

    public Play() {
    }

    public Play(Long id) {
        this.id = id;
    }

    public Play(Long id, java.util.Date startDate, int boardSize, int duration, int numberOfMoves, byte[] encodedMoves) {
        this.id = id;
        this.startDate = startDate;
        this.boardSize = boardSize;
        this.duration = duration;
        this.numberOfMoves = numberOfMoves;
        this.encodedMoves = encodedMoves;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /** Not-null value. */
    public java.util.Date getStartDate() {
        return startDate;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setStartDate(java.util.Date startDate) {
        this.startDate = startDate;
    }

    public int getBoardSize() {
        return boardSize;
    }

    public void setBoardSize(int boardSize) {
        this.boardSize = boardSize;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getNumberOfMoves() {
        return numberOfMoves;
    }

    public void setNumberOfMoves(int numberOfMoves) {
        this.numberOfMoves = numberOfMoves;
    }

    /** Not-null value. */
    public byte[] getEncodedMoves() {
        return encodedMoves;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setEncodedMoves(byte[] encodedMoves) {
        this.encodedMoves = encodedMoves;
    }

}
