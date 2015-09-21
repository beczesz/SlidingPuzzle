package com.exarlabs.android.slidingpuzzle.model;

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
        Schema schema = new Schema(1000, "com.exarlabs.android.slidingpuzzle");

        addSolutions(schema);

        new DaoGenerator().generateAll(schema, "../slidingpuzzle/model/dao");
    }

    private static void addSolutions(Schema schema) {
        Entity note = schema.addEntity("Solutions");
        note.addIdProperty();
        note.addIntProperty("size").notNull();
        note.addStringProperty("steps").notNull();
    }

    // ------------------------------------------------------------------------
    // GETTERS / SETTTERS
    // ------------------------------------------------------------------------
}
