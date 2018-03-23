package com.takehome.numbers;

import java.util.regex.Pattern;

/**
 * This class is is used to verify that a string can be represented by an integer. If it can be, that integer is
 * returned. Otherwise, an appropriate exception is thrown.
 *
 * @author jeff.snyder
 */
public class ParseInteger {

    private Pattern NUMERIC_CHARACTERS = Pattern.compile("^[\\-]?\\d+$");
    private Pattern LEADING_ZEROS = Pattern.compile("^[\\-]?[0]+.*");
    private Pattern LEADING_ZEROS_WITH_DIGIT = Pattern.compile("^[\\-]?[0]+[1-9]+.*");
    private Pattern VALID_LENGTH = Pattern.compile("^[-]?\\d{1,10}");

    /**
     * This method is used to take a string and return its integer equivalent. Any leading zeros are ignored and this
     * would include any zeros immediately after the minus sign if the number is negative.
     *
     * @param value string representing an integer
     * @return an integer parsed from the input value
     * @throws NumberFormatException when there is a non-numeric character is the input value
     * @throws ArithmeticException when the input value is too big (either positive or negative) to fit in a 32-bit integer
     */
    public int parseIntegerFromString(String value) {

        // Ignore leading or trailing white space and any leading zeros.
        String current = value.trim();

        if (! NUMERIC_CHARACTERS.matcher(current).matches()) {
            throw new NumberFormatException("Non-numeric character in input.");
        }

        // Looking for leading zeros that can be removed
        if (LEADING_ZEROS.matcher(current).matches()) {

            // Remove leading zeros when there is a non-zero digit in the string
            if (LEADING_ZEROS_WITH_DIGIT.matcher(current).matches()) {
                current = current.replaceFirst("^[0]+", "");

                // Ignore any leading zeros after the minus sign
                current = current.replaceFirst("^-[0]+", "-");
            } else {
                // String was all zeros, so set to zero.
                current = "0";
            }
        }

        // Determine if the length is too long for a valid integer (this also means it will fit into a long)
        if (! VALID_LENGTH.matcher(current).matches()) {
            throw new ArithmeticException("Number can not be stored in a 32 bit integer.");
        }

        // Determine if the value would fit into an integer's range
        Long result = Long.parseLong(current);
        if (result > Integer.MAX_VALUE || result < Integer.MIN_VALUE) {
            throw new ArithmeticException("Number can not be stored in a 32 bit integer.");
        }

        return result.intValue();
    }
}
