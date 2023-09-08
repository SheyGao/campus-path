/*
 * Copyright (C) 2023 Soham Pardeshi.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Autumn Quarter 2022 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

package setup;

import org.junit.Test;
import org.junit.BeforeClass;
import static org.junit.Assert.*;

/**
 * FinderTest is a glassbox test of the Finder class.
 */
public class FinderTest {

    /**
     * An empty array.
     */
    private final int[] emptyArr = new int[] {};

     /**
     * A singleton array.
     */
    private final int[] singletonArr = new int[] { 2 };

     /**
     * An array with a single pair of values.
     */
    private final int[] pairArr = new int[] { 2, 5 };

     /**
     * An array with many values, or a long array.
     */
    private final int[] longArr = new int[] { 17, 5, 12, 2, 3, 8, 15, -1, 27 };

    ///////////////////////////////////////////////////////////////////////////
    /// Finder.find1() Tests
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Tests Finder.find1 when the array is empty.
     */
    @Test
    public void testFind1Empty() {
        assertEquals("find1([], 3)", -1, Finder.find1(emptyArr, 3));
        assertEquals("find1([], -3)", -1, Finder.find1(emptyArr, -3));
    }

    /**
     * Tests Finder.find1 when the array has one value. The "singleton"
     * test.
     */
    @Test
    public void testFind1Singleton() {
        assertEquals("find1([2], 1)", -1, Finder.find1(singletonArr, 1));
        assertEquals("find1([2], 2)", 0, Finder.find1(singletonArr, 2));
        assertEquals("find1([2], 3)", -1, Finder.find1(singletonArr, 3));
    }

    /**
     * Tests Finder.find1 when the array has a pair of values.
     */
    @Test
    public void testFind1Pairs() {
        assertEquals("find1([2, 5], 1)", -1, Finder.find1(pairArr, 1));
        assertEquals("find1([2, 5], 2)", 0, Finder.find1(pairArr, 2));
        assertEquals("find1([2, 5], 3)", -1, Finder.find1(pairArr, 3));
        assertEquals("find1([2, 5], 4)", -1, Finder.find1(pairArr, 4));
        assertEquals("find1([2, 5], 5)", 1, Finder.find1(pairArr, 5));
        assertEquals("find1([2, 5], 6)", -1, Finder.find1(pairArr, 6));
    }

    /**
     * Tests Finder.find1 when the array is long and has a lot of
     * values.
     */
    @Test
    public void testFind1Complex() {
        assertEquals("find1(A, 1)", -1, Finder.find1(longArr, 1));
        assertEquals("find1(A, -1)", 7, Finder.find1(longArr, -1));
        assertEquals("find1(A, 17)", 0, Finder.find1(longArr, 17));
        assertEquals("find1(A, 27)", 8, Finder.find1(longArr, 27));
    }

    ///////////////////////////////////////////////////////////////////////////
    /// Finder.find2() Tests
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Tests Finder.find2 when the array is empty.
     */
    @Test
    public void testFind2Empty() {
        assertEquals("find2([], 3)", -1, Finder.find2(emptyArr, 3));
        assertEquals("find2([], -3)", -1, Finder.find2(emptyArr, -3));
    }

    /**
     * Tests Finder.find2 when the array has one value. The "singleton"
     * test.
     */
    @Test
    public void testFind2Singleton() {
        assertEquals("find2([2], 1)", -1, Finder.find2(singletonArr, 1));
        assertEquals("find2([2], 2)", 0, Finder.find2(singletonArr, 2));
        assertEquals("find2([2], 3)", -1, Finder.find2(singletonArr, 3));
    }

    /**
     * Tests Finder.find2 when the array has a pair of values.
     */
    @Test
    public void testFind2Pairs() {
        assertEquals("find2([2, 5], 1)", -1, Finder.find2(pairArr, 1));
        assertEquals("find2([2, 5], 2)", 0, Finder.find2(pairArr, 2));
        assertEquals("find2([2, 5], 3)", -1, Finder.find2(pairArr, 3));
        assertEquals("find2([2, 5], 4)", -1, Finder.find2(pairArr, 4));
        assertEquals("find2([2, 5], 5)", 1, Finder.find2(pairArr, 5));
        assertEquals("find2([2, 5], 6)", -1, Finder.find2(pairArr, 6));
    }

    /**
     * Tests Finder.find2 when the array is long and has a lot of
     * values.
     */
    @Test
    public void testFind2Complex() {
        assertEquals("find2(A, 1)", -1, Finder.find2(longArr, 1));
        assertEquals("find2(A, -1)", 7, Finder.find2(longArr, -1));
        assertEquals("find2(A, 17)", 0, Finder.find2(longArr, 17));
        assertEquals("find2(A, 27)", 8, Finder.find2(longArr, 27));
    }

    ///////////////////////////////////////////////////////////////////////////
    /// Finder.find3() Tests
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Tests Finder.find3 when the array is empty.
     */
    @Test
    public void testFind3Empty() {
        assertEquals("find3([], 3)", -1, Finder.find3(emptyArr, 3));
        assertEquals("find3([], -3)", -1, Finder.find3(emptyArr, -3));
    }

    /**
     * Tests Finder.find3 when the array has one value. The "singleton"
     * test.
     */
    @Test
    public void testFind3Singleton() {
        assertEquals("find3([2], 1)", -1, Finder.find3(singletonArr, 1));
        assertEquals("find3([2], 2)", 0, Finder.find3(singletonArr, 2));
        assertEquals("find3([2], 3)", -1, Finder.find3(singletonArr, 3));
    }

    /**
     * Tests Finder.find3 when the array has a pair of values.
     */
    @Test
    public void testFind3Pairs() {
        assertEquals("find3([2, 5], 1)", -1, Finder.find3(pairArr, 1));
        assertEquals("find3([2, 5], 2)", 0, Finder.find3(pairArr, 2));
        assertEquals("find3([2, 5], 3)", -1, Finder.find3(pairArr, 3));
        assertEquals("find3([2, 5], 4)", -1, Finder.find3(pairArr, 4));
        assertEquals("find3([2, 5], 5)", 1, Finder.find3(pairArr, 5));
        assertEquals("find3([2, 5], 6)", -1, Finder.find3(pairArr, 6));
    }

    /**
     * Tests Finder.find3 when the array is long and has a lot of
     * values.
     */
    @Test
    public void testFind3Complex() {
        assertEquals("find3(A, 1)", -1, Finder.find3(longArr, 1));
        assertEquals("find3(A, -1)", 7, Finder.find3(longArr, -1));
        assertEquals("find3(A, 17)", 0, Finder.find3(longArr, 17));
        assertEquals("find3(A, 27)", 8, Finder.find3(longArr, 27));
    }

    ///////////////////////////////////////////////////////////////////////////
    /// Finder.find4() Tests
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Tests Finder.find4 when the array is empty.
     */
    @Test
    public void testFind4Empty() {
        assertEquals("find4([], 3)", -1, Finder.find4(emptyArr, 3));
        assertEquals("find4([], -3)", -1, Finder.find4(emptyArr, -3));
    }

    /**
     * Tests Finder.find4 when the array has one value. The "singleton"
     * test.
     */
    @Test
    public void testFind4Singleton() {
        assertEquals("find4([2], 1)", -1, Finder.find4(singletonArr, 1));
        assertEquals("find4([2], 2)", 0, Finder.find4(singletonArr, 2));
        assertEquals("find4([2], 3)", -1, Finder.find4(singletonArr, 3));
    }

    /**
     * Tests Finder.find4 when the array has a pair of values.
     */
    @Test
    public void testFind4Pairs() {
        assertEquals("find4([2, 5], 1)", -1, Finder.find4(pairArr, 1));
        assertEquals("find4([2, 5], 2)", 0, Finder.find4(pairArr, 2));
        assertEquals("find4([2, 5], 3)", -1, Finder.find4(pairArr, 3));
        assertEquals("find4([2, 5], 4)", -1, Finder.find4(pairArr, 4));
        assertEquals("find4([2, 5], 5)", 1, Finder.find4(pairArr, 5));
        assertEquals("find4([2, 5], 6)", -1, Finder.find4(pairArr, 6));
    }

    /**
     * Tests Finder.find4 when the array is long and has a lot of
     * values.
     */
    @Test
    public void testFind4Complex() {
        assertEquals("find4(A, 1)", -1, Finder.find4(longArr, 1));
        assertEquals("find4(A, -1)", 7, Finder.find4(longArr, -1));
        assertEquals("find4(A, 17)", 0, Finder.find4(longArr, 17));
        assertEquals("find4(A, 27)", 8, Finder.find4(longArr, 27));
    }
}
