package com.exarlabs.android.slidingpuzzle.generator;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

/**
 * Generator class for the Dao's.
 * Just execute the main method to generate the sources for the Daos.<br><br>
 * <p/>
 * Created by becze on 9/21/2015.
 *
 * @see <a href="http://greendao-orm.com/">Green Dao Documentation</a>
 */
public class SlidingPuzlleDaoGenerator {

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

    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(1000, "com.exarlabs.android.slidingpuzzle.model.dao");

        addSolutions(schema);
        addPlays(schema);

        new DaoGenerator().generateAll(schema, "./app/src/main/java");
    }

    private static void addSolutions(Schema schema) {
        Entity note = schema.addEntity("GeneratedSolution");
        note.addIdProperty();
        note.addIntProperty("size").notNull();
        note.addByteArrayProperty("moves").notNull();
    }

    private static void addPlays(Schema schema) {
        Entity note = schema.addEntity("Play");
        note.addIdProperty();
        note.addDateProperty("startDate").notNull();
        note.addIntProperty("boardSize").notNull();
        note.addIntProperty("duration").notNull();
        note.addIntProperty("numberOfMoves").notNull();
        note.addByteArrayProperty("encodedMoves").notNull();
    }

    // ------------------------------------------------------------------------
    // GETTERS / SETTTERS
    // ------------------------------------------------------------------------
}
