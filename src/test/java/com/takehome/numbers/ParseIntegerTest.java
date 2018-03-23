package com.takehome.numbers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test the parsing of the integers checking for invalid input.
 *
 * @author jeff.snyder
 */
class ParseIntegerTest {

    private static ParseInteger parse;

    @BeforeAll
    static void setup() {
        parse = new ParseInteger();
    }

    @Test
    void parseNoNumericCharsFromString() {
        assertThrows(NumberFormatException.class, ()-> parse.parseIntegerFromString("abc"));
    }

    @Test
    void parseInvalidIntegerFromString() {
        assertThrows(NumberFormatException.class, ()-> parse.parseIntegerFromString("123c"));
        assertThrows(NumberFormatException.class, ()-> parse.parseIntegerFromString("123c34"));
        assertThrows(NumberFormatException.class, ()->parse.parseIntegerFromString("123+5"));
        assertThrows(NumberFormatException.class, ()->parse.parseIntegerFromString("-12334="));
        assertThrows(NumberFormatException.class, ()->parse.parseIntegerFromString("0-0"));
    }

    @Test
    void parsePositiveTooLongToBeIntegerFromString() {
        assertThrows(ArithmeticException.class, ()->parse.parseIntegerFromString("12345678911"));
        assertThrows(ArithmeticException.class, ()->parse.parseIntegerFromString("12345678901343464564"));
        assertThrows(ArithmeticException.class, ()->parse.parseIntegerFromString("0000000000000000000000000000012345678901343464564"));
    }

    @Test
    void parseNegativeTooLongToBeIntegerFromString() {
        assertThrows(ArithmeticException.class, ()->parse.parseIntegerFromString("-512345678911"));
        assertThrows(ArithmeticException.class, ()->parse.parseIntegerFromString("-1233456790325490835408656734"));
    }

    @Test
    void parseZeroFromString() {
        assertEquals(0, parse.parseIntegerFromString("0"));
    }

    @Test
    void parseMinMaxFromString() {
        assertEquals(Integer.MIN_VALUE, parse.parseIntegerFromString(String.valueOf(Integer.MIN_VALUE)));
        assertEquals(Integer.MAX_VALUE, parse.parseIntegerFromString(String.valueOf(Integer.MAX_VALUE)));
    }

    @Test
    void parseLeadingZerosFromString() {
        assertEquals(1, parse.parseIntegerFromString("000001"));
        assertEquals(-1, parse.parseIntegerFromString("-000001"));
        assertEquals(0, parse.parseIntegerFromString("00"));
        assertEquals(0, parse.parseIntegerFromString("000000"));
        assertEquals(0, parse.parseIntegerFromString("0000000000000000000000000000000000"));
        assertEquals(101, parse.parseIntegerFromString("0000000000000000000000000101"));
        assertEquals(-110, parse.parseIntegerFromString("-0000000000000000000000110"));
    }
}