package com.takehome.numbers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test the conversion from integer to words.
 *
 * @author jeff.snyder
 */
class IntegerToEnglishWordConverterTest {

    @Test
    void convertZero() {
        int value = 0;
        String result = "Zero";

        IntegerToEnglishWordConverter convert = new IntegerToEnglishWordConverter();
        assertEquals(result, convert.convert(value));
    }

    @Test
    void convertMinInt() {
        int value = Integer.MIN_VALUE;
        String result = "Negative two billion one hundred forty seven million four hundred eighty three thousand six hundred and forty eight";

        IntegerToEnglishWordConverter convert = new IntegerToEnglishWordConverter();
        assertEquals(result, convert.convert(value));
    }

    @Test
    void convertMaxInt() {
        int value = Integer.MAX_VALUE;
        String result = "Two billion one hundred forty seven million four hundred eighty three thousand six hundred and forty seven";

        IntegerToEnglishWordConverter convert = new IntegerToEnglishWordConverter();
        assertEquals(result, convert.convert(value));
    }

    @Test
    void convertSingleDigit() {
        int value = 9;
        String result = "Nine";

        IntegerToEnglishWordConverter convert = new IntegerToEnglishWordConverter();
        assertEquals(result, convert.convert(value));
    }

    @Test
    void convertTenToTwenty() {
        String results [] = {
                "Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen",
                "Nineteen", "Twenty"
        };

        IntegerToEnglishWordConverter convert = new IntegerToEnglishWordConverter();
        for (int i=10; i<21; i++) {
            assertEquals(results[i-10], convert.convert(i));
        }
    }

    @Test
    void convertEvenTen() {
        int value = 60;
        String result = "Sixty";

        IntegerToEnglishWordConverter convert = new IntegerToEnglishWordConverter();
        assertEquals(result, convert.convert(value));
    }

    @Test
    void convertTens() {
        int value = 78;
        String result = "Seventy eight";

        IntegerToEnglishWordConverter convert = new IntegerToEnglishWordConverter();
        assertEquals(result, convert.convert(value));
    }

    @Test
    void convertEvenHundred() {
        int value = 300;
        String result = "Three hundred";

        IntegerToEnglishWordConverter convert = new IntegerToEnglishWordConverter();
        assertEquals(result, convert.convert(value));
    }

    @Test
    void convertHundredWithDigits() {
        int value = 322;
        String result = "Three hundred and twenty two";

        IntegerToEnglishWordConverter convert = new IntegerToEnglishWordConverter();
        assertEquals(result, convert.convert(value));
    }

    @Test
    void convertThousandWithDigits() {
        int value = 34061;
        String result = "Thirty four thousand and sixty one";

        IntegerToEnglishWordConverter convert = new IntegerToEnglishWordConverter();
        assertEquals(result, convert.convert(value));
    }

    @Test
    void convertEvenThousand() {
        int value = 134000;
        String result = "One hundred thirty four thousand";

        IntegerToEnglishWordConverter convert = new IntegerToEnglishWordConverter();
        assertEquals(result, convert.convert(value));
    }

    @Test
    void convertMillionWithDigits() {
        int value = 340000045;
        String result = "Three hundred forty million and forty five";

        IntegerToEnglishWordConverter convert = new IntegerToEnglishWordConverter();
        assertEquals(result, convert.convert(value));
    }

    @Test
    void convertEvenMillion() {
        int value = 194000000;
        String result = "One hundred ninety four million";

        IntegerToEnglishWordConverter convert = new IntegerToEnglishWordConverter();
        assertEquals(result, convert.convert(value));
    }

    @Test
    void convertBillionWithDigits() {
        int value = 1000000053;
        String result = "One billion and fifty three";

        IntegerToEnglishWordConverter convert = new IntegerToEnglishWordConverter();
        assertEquals(result, convert.convert(value));
    }

    @Test
    void convertEvenBillion() {
        int value = 1000000000;
        String result = "One billion";

        IntegerToEnglishWordConverter convert = new IntegerToEnglishWordConverter();
        assertEquals(result, convert.convert(value));
    }
}