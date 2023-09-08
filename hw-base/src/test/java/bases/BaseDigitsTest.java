/*
 * Copyright (C) 2023 Soham Pardeshi.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Spring Quarter 2021 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

package bases;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * BaseDigitsTest is a glassbox test of the BaseDigits class.
 */
public class BaseDigitsTest {

    ///////////////////////////////////////////////////////////////////////////
    /// BaseDigits.digitToChar() Tests
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Tests converting digits to characters in base 12.
     */
    @Test
    public void testDigitToCharBase12() {
        assertEquals("digitToChar(0, 12) == '0'", '0',
            BaseDigits.digitToChar(0, 12));
    }

    /**
     * Tests converting digits to characters in base 16.
     * This is a test focusing on numerical output.
     */
    @Test
    public void testDigitToCharBase16Nums() {
        assertEquals("digitToChar(0, 16) == '0'", '0',
            BaseDigits.digitToChar(0, 16));
        assertEquals("digitToChar(5, 16) == '5'", '5',
            BaseDigits.digitToChar(5, 16));
        assertEquals("digitToChar(9, 16) == '9'", '9',
            BaseDigits.digitToChar(9, 16));
    }

    /**
     * Tests converting digits to characters in base 16.
     * This is a test focusing on alphabetical output.
     */
    @Test
    public void testDigitToCharBase16Letters() {
        assertEquals("digitToChar(10, 16) == 'a'", 'a',
            BaseDigits.digitToChar(10, 16));
        assertEquals("digitToChar(11, 16) == 'b'", 'b',
            BaseDigits.digitToChar(11, 16));
        assertEquals("digitToChar(15, 16) == 'f'", 'f',
            BaseDigits.digitToChar(15, 16));
    }

    ///////////////////////////////////////////////////////////////////////////
    /// BaseDigits.charToDigit() Tests
    ///////////////////////////////////////////////////////////////////////////


    /**
     * Tests converting digits to characters in base 12.
     */
    @Test
    public void testCharToDigitBase12() {
        assertEquals("charToDigit('0', 12) == 0", 0,
            BaseDigits.charToDigit('0', 12));
    }

    /**
     * Tests converting digits to characters in base 16.
     * * This is a test focusing on numerical input.
     */
    @Test
    public void testCharToDigitBase16Nums() {
        assertEquals("charToDigit('0', 16) == 0", 0,
            BaseDigits.charToDigit('0', 16));
        assertEquals("charToDigit('5', 16) == 5", 5,
            BaseDigits.charToDigit('5', 16));
        assertEquals("charToDigit('9', 16) == 9", 9,
            BaseDigits.charToDigit('9', 16));
    }

    /**
     * Tests converting digits to characters in base 16.
     * * This is a test focusing on alphabetical input.
     */
    @Test
    public void testCharToDigitBase16Letters() {
        assertEquals("charToDigit('a', 16) == 10", 10,
            BaseDigits.charToDigit('a', 16));
        assertEquals("charToDigit('b', 16) == 11", 11,
            BaseDigits.charToDigit('b', 16));
        assertEquals("charToDigit('f', 16) == 15", 15,
            BaseDigits.charToDigit('f', 16));
    }
}
