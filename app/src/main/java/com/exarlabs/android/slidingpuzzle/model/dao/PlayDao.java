package com.exarlabs.android.slidingpuzzle.model.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.exarlabs.android.slidingpuzzle.model.dao.Play;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "PLAY".
*/
public class PlayDao extends AbstractDao<Play, Long> {

    public static final String TABLENAME = "PLAY";

    /**
     * Properties of entity Play.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property StartDate = new Property(1, java.util.Date.class, "startDate", false, "START_DATE");
        public final static Property BoardSize = new Property(2, int.class, "boardSize", false, "BOARD_SIZE");
        public final static Property Duration = new Property(3, int.class, "duration", false, "DURATION");
        public final static Property NumberOfMoves = new Property(4, int.class, "numberOfMoves", false, "NUMBER_OF_MOVES");
        public final static Property EncodedMoves = new Property(5, byte[].class, "encodedMoves", false, "ENCODED_MOVES");
    };


    public PlayDao(DaoConfig config) {
        super(config);
    }
    
    public PlayDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"PLAY\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"START_DATE\" INTEGER NOT NULL ," + // 1: startDate
                "\"BOARD_SIZE\" INTEGER NOT NULL ," + // 2: boardSize
                "\"DURATION\" INTEGER NOT NULL ," + // 3: duration
                "\"NUMBER_OF_MOVES\" INTEGER NOT NULL ," + // 4: numberOfMoves
                "\"ENCODED_MOVES\" BLOB NOT NULL );"); // 5: encodedMoves
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"PLAY\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Play entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getStartDate().getTime());
        stmt.bindLong(3, entity.getBoardSize());
        stmt.bindLong(4, entity.getDuration());
        stmt.bindLong(5, entity.getNumberOfMoves());
        stmt.bindBlob(6, entity.getEncodedMoves());
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Play readEntity(Cursor cursor, int offset) {
        Play entity = new Play( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            new java.util.Date(cursor.getLong(offset + 1)), // startDate
            cursor.getInt(offset + 2), // boardSize
            cursor.getInt(offset + 3), // duration
            cursor.getInt(offset + 4), // numberOfMoves
            cursor.getBlob(offset + 5) // encodedMoves
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Play entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setStartDate(new java.util.Date(cursor.getLong(offset + 1)));
        entity.setBoardSize(cursor.getInt(offset + 2));
        entity.setDuration(cursor.getInt(offset + 3));
        entity.setNumberOfMoves(cursor.getInt(offset + 4));
        entity.setEncodedMoves(cursor.getBlob(offset + 5));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Play entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Play entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
