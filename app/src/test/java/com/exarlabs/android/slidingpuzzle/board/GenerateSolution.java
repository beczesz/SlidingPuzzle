package com.exarlabs.android.slidingpuzzle.board;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.junit.Test;

import com.exarlabs.android.slidingpuzzle.business.board.GameUtil;
import com.exarlabs.android.slidingpuzzle.model.board.BoardState;
import com.exarlabs.android.slidingpuzzle.utils.generator.SlidingPuzzleSolutionsGenerator;

/**
 * Created by becze on 9/18/2015.
 */
public class GenerateSolution {

    // ------------------------------------------------------------------------
    // TYPES
    // ------------------------------------------------------------------------

    private static final String TAG = GenerateSolution.class.getSimpleName();


    // ------------------------------------------------------------------------
    // STATIC FIELDS
    // ------------------------------------------------------------------------

    private static final boolean DISPLAY_RESULTS = false;
    private static final boolean RANDOMIED_SOLUTIONS = true;
    private static final int DIMENSION = 4;
    private static final int DEPTH = 64;
    private static final int NR_OF_SOLUTIONS = 10000;
    public static final String FILE_NAME = "src/main/assets/gen_%dx%d_depth_%d.txt";

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

    @Test
    public void testGenerateSolutions() {


        long time = System.currentTimeMillis();
        SlidingPuzzleSolutionsGenerator generator = new SlidingPuzzleSolutionsGenerator(DIMENSION, DEPTH, NR_OF_SOLUTIONS, RANDOMIED_SOLUTIONS);
        generator.generateSolutions();

        System.out.println("\nGenerated in: " + (System.currentTimeMillis() - time) + " ms");
        System.out.println("Solutions found: " + generator.getSolutions().size());

        try {
            FileOutputStream writer = new FileOutputStream(String.format(FILE_NAME, DIMENSION, DIMENSION, DEPTH));

            System.out.println(generator.getInitialState().toString());

            for (BoardState state : generator.getSolutions().keySet()) {
                byte[] compressed = GameUtil.compress(generator.getSolutions().get(state));
                String paddedBinary = GameUtil.toPaddedBinary(generator.getSolutions().get(state));

                if (DISPLAY_RESULTS) {
                    System.out.println(generator.getSolutions().get(state) + " b: " + paddedBinary);
                }
                writer.write(compressed);
            }

            writer.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testTemprandomGenerateSolutions() {

        // Temprarily we genaret one random solution NR_OF_SOLUTIONS time. This is very inefficient and bad.
        // The next generation generator will do it better.

        try {
            FileOutputStream writer = new FileOutputStream(String.format(FILE_NAME, DIMENSION, DIMENSION, DEPTH));

            for (int i = 0; i < NR_OF_SOLUTIONS; i++) {
                SlidingPuzzleSolutionsGenerator generator = new SlidingPuzzleSolutionsGenerator(DIMENSION, DEPTH, 1, RANDOMIED_SOLUTIONS);
                generator.generateSolutions();


                for (BoardState state : generator.getSolutions().keySet()) {
                    byte[] compressed = GameUtil.compress(generator.getSolutions().get(state));
                    String paddedBinary = GameUtil.toPaddedBinary(generator.getSolutions().get(state));

                    if (DISPLAY_RESULTS) {
                        System.out.println(generator.getSolutions().get(state) + " b: " + paddedBinary);
                    }
                    writer.write(compressed);
                }

            }

            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    // ------------------------------------------------------------------------
    // GETTERS / SETTTERS
    // ------------------------------------------------------------------------
}
