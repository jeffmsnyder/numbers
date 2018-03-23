package com.takehome.numbers;

import java.util.Stack;

/**
 * This class is used to convert an integer into its english language equivalent. The following rules are applied:
 * <p>
 * 1. For negative numbers, the word "Negative" will be the first word.
 *    For example:
 *      -1 = "Negative one"
 * <p>
 * 2. If the number is greater than 99 and the last two digits are between 01 and 99, then the word "and" will appear
 *    between the last two digit word equivalence and the next higher non-zero digit.
 *    For example:
 *      140 = "One hundred and forty";
 *      104 = "One hundred and four"; and
 *      1040 = "One thousand and forty"
 *
 * @author jeff.snyder
 */
public class IntegerToEnglishWordConverter {

    private static final String NEGATIVE = "negative";
    private static final String AND = "and";
    private static final String HUNDRED = "hundred";

    private static final String UNDER_TWENTY[] = {
            "zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten",
            "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen"
    };

    private static final String TENS[] = {
            "", "", "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety"
    };

    // Only need to go out to a billion for an integer
    private static final String SCALE[] = {"thousand", "million", "billion"};

    /**
     * Converts an integer into its English language equivalent.
     *
     * @param n number to be converted
     * @return string containing the English language equivalent of the number
     */
    public String convert(int n) {

        // This is the word stack that contains the english words of the number.
        // Going down the stack is like reading the number from left to right
        Stack<String> words = new Stack<>();

        if (n == 0) {
            // Special case when the number is zero
            words.push(UNDER_TWENTY[n]);
        } else {
            // Break down the digits of the number into words
            breakdownNumber(words, n);
            if (n < 0) {
                // Special case for negative numbers
                words.push(NEGATIVE);
            }
        }

        // Build the string by capitalizing the first word and then appending any additional words with a space
        // in between them.
        StringBuilder result = new StringBuilder(words.pop());
        result.setCharAt(0, Character.toUpperCase(result.charAt(0)));
        while (!words.empty()) {
            result.append(" ");
            result.append(words.pop());
        }

        return result.toString();
    }

    /**
     * This breaks up the number into 3 digit chunks to be processed (negativity portion of the number is ignored).  It
     * starts with the lowest 3 digits and works its way to the higher digits.
     *
     * @param words english word stack for generating the number (words to be added in reverse order)
     * @param number integer between <code>MIN_INT</code> and <code>MAX_INT</code>
     */
    private void breakdownNumber(Stack<String> words, int number) {

        // Do the lowest three digits and then trim off the 3 digits
        int current = Math.abs(number % 1000);
        boolean andFlag = underOneThousandToString(words, current, true);
        number /= 1000;

        // The remaining portion of the number has a scale for each group of three digits that is added when those
        // three digits are not all zeros.
        for (int i=0; i<SCALE.length && number != 0; i++) {
            current = Math.abs(number % 1000);
            if (current != 0) {
                if (andFlag) {
                    // This is needed as the last two digits or the original number were between 1 and 99 and no other
                    // non-zero digit had occurred in the number up until this point.
                    words.push(AND);
                }

                // Add the scale of the current digits and then add the number to the word stack
                words.push(SCALE[i]);
                underOneThousandToString(words, current, false);
            }

            // Trim off another 3 digits
            number /= 1000;
        }
    }

    /**
     * Add English word equivalents to the absolute value of the number in reverse order to the words stack.  If the
     * "and" flag is set, then an "and" needs to be added if the last two digits are between 1 and 99. If the number is
     * over 100, the "and" will be added to the words before then hundredth portion of added.
     *
     * @param words english word stack for generating the number (words to be added in reverse order)
     * @param number an integer in the range of 1 to 999
     * @param andFlag <code>true</code> if an "and" is needed between then hundreds and tens (or single when no tens) digit
     * @return <code>true</code> if the number was between 1 and 99 and the andFlag was originally <code>true</code>,
     * <code>false</code> otherwise. When <code>true</code>, it means that the word "and" would need to be added before
     * adding additional number words to the word stack.
     */
    private boolean underOneThousandToString(Stack<String> words, int number, boolean andFlag) {

        // Only need to generate words if the number is not zero
        if (number != 0) {
            int hundredths = number / 100;
            number %= 100; // Determine the last two possible digits

            // Calculate words for the last two digits of the number when they are not both zero
            if (number != 0) {
                int tens = number / 10;

                // Under 20 are all special numbers
                if (tens < 2) {
                    words.push(UNDER_TWENTY[number]);
                } else {
                    // When over 20, check to see if the last digit is between 1 and 9, inclusive
                    int digit = number % 10;
                    if (digit != 0) {
                        // Add the last digit
                        words.push(UNDER_TWENTY[digit]);
                    }
                    // Add the tens digit
                    words.push(TENS[tens]);
                }
            } else {
                // Since this was an even factor of one hundred, no "and" will be needed.
                andFlag = false;
            }

            // Calculate words for the hundredth portion of the number
            if (hundredths > 0) {
                if (andFlag) {
                    // "and" will go here as there was a non-zero value in the last two digits
                    words.push(AND);
                    andFlag = false;
                }
                words.push(HUNDRED);
                words.push(UNDER_TWENTY[hundredths]);
            }
        } else {
            andFlag = false;
        }

        // Indicates if an "and" is still needed when there are further portions of the integer to process
        return andFlag;
    }
}
