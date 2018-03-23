package com.takehome.numbers;

import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test the main executable with different command line arguments and other user input
 *
 * @author jeff.snyder
 */
class NumbersTest {

    @Test
    void mainPositiveValid() {
        PrintStream saveOut = System.out;

        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            System.setOut(new PrintStream(out));

            Numbers.main(new String[]{"1"});
        } finally {
            System.setOut(saveOut);
        }

        assertEquals("One", out.toString().trim());
    }

    @Test
    void mainNegativeValid() {
        PrintStream saveOut = System.out;

        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            System.setOut(new PrintStream(out));

            Numbers.main(new String[]{"-1"});
        } finally {
            System.setOut(saveOut);
        }

        assertEquals("Negative one", out.toString().trim());
    }

    @Test
    void invalidArgs() {
        PrintStream saveOut = System.out;

        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            System.setOut(new PrintStream(out));

            Numbers.main(new String[]{"-n"});
        } finally {
            System.setOut(saveOut);
        }

        assertEquals("Error: Missing argument for option: n", out.toString().trim());
    }

    @Test
    void invalidCount() {
        PrintStream saveOut = System.out;

        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            System.setOut(new PrintStream(out));

            Numbers.main(new String[]{"-n", "0"});
        } finally {
            System.setOut(saveOut);
        }

        assertEquals("Error: Invalid argument for n, must be a positive integer between 1 and 1000.", out.toString().trim());
    }

    @Test
    void help() {
        PrintStream saveOut = System.out;

        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            System.setOut(new PrintStream(out));

            Numbers.main(new String[]{"-h"});
        } finally {
            System.setOut(saveOut);
        }

        String results[] = {
                "usage: numbers [-h] [-n <arg>] [<integer1> <integer2>...]",
                "Converts integers into English words.",
                " -h,--help",
                " -n,--count <arg>   Number of integers to be read from standard input",
                "                    (ignored if integers on command line). Range: 1-1000"
        };

        compareOutput(results, out.toString());
    }

    @Test
    void multipleValid() {
        PrintStream saveOut = System.out;

        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            System.setOut(new PrintStream(out));

            Numbers.main(new String[]{"0", "10", "15", "101", "5367", "1023434", "0000491578493", "1000000000"});
        } finally {
            System.setOut(saveOut);
        }

        String results[] = {
                "Zero",
                "Ten",
                "Fifteen",
                "One hundred and one",
                "Five thousand three hundred and sixty seven",
                "One million twenty three thousand four hundred and thirty four",
                "Four hundred ninety one million five hundred seventy eight thousand four hundred and ninety three",
                "One billion"
        };

        compareOutput(results, out.toString());
    }

    @Test
    void invalidNumberCharacters() {
        PrintStream saveOut = System.out;

        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            System.setOut(new PrintStream(out));

            Numbers.main(new String[]{"001at2343"});
        } finally {
            System.setOut(saveOut);
        }

        assertEquals("Error: Non-numeric character in input.", out.toString().trim());
    }

    @Test
    void tooLargeNumber() {
        PrintStream saveOut = System.out;

        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            System.setOut(new PrintStream(out));

            Numbers.main(new String[]{"04378378768547675467540892423454375467336456"});
        } finally {
            System.setOut(saveOut);
        }

        assertEquals("Error: Number can not be stored in a 32 bit integer.", out.toString().trim());
    }

    @Test
    void tooLargeNegativeNumber() {
        PrintStream saveOut = System.out;

        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            System.setOut(new PrintStream(out));

            Numbers.main(new String[]{"-04378378768547675467540892423454375467336456"});
        } finally {
            System.setOut(saveOut);
        }

        assertEquals("Error: Number can not be stored in a 32 bit integer.", out.toString().trim());
    }

    @Test
    void multipleValidAndInvalid() {
        PrintStream saveOut = System.out;

        final ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            System.setOut(new PrintStream(out));

            Numbers.main(new String[]{"0", "-10", "15d", "101", "-5367", "1023434", "00004954365461578493", "1000000000"});
        } finally {
            System.setOut(saveOut);
        }

        String results[] = {
                "Zero",
                "Negative ten",
                "Error: Non-numeric character in input.",
                "One hundred and one",
                "Negative five thousand three hundred and sixty seven",
                "One million twenty three thousand four hundred and thirty four",
                "Error: Number can not be stored in a 32 bit integer.",
                "One billion"
        };

        compareOutput(results, out.toString());
    }

    @Test
    void multipleValidAndInvalidUsingCount() {
        PrintStream saveOut = System.out;
        InputStream saveIn = System.in;

        final ByteArrayOutputStream out = new ByteArrayOutputStream();

        String data = "0\n-10\n15d\n101\n-5367\n1023434\n00004954365461578493\n1000000000\n";

        try {
            InputStream testInput = new ByteArrayInputStream(data.getBytes("UTF-8"));

            System.setIn(testInput);
            System.setOut(new PrintStream(out));

            Numbers.main(new String[]{"-n", "8"});
        } catch (UnsupportedEncodingException e) {
            fail("Bad setup");
        } finally {
            System.setOut(saveOut);
            System.setIn(saveIn);
        }

        String results[] = {
                "Zero",
                "Negative ten",
                "Error: Non-numeric character in input.",
                "One hundred and one",
                "Negative five thousand three hundred and sixty seven",
                "One million twenty three thousand four hundred and thirty four",
                "Error: Number can not be stored in a 32 bit integer.",
                "One billion"
        };

        compareOutput(results, out.toString());
    }

    private void compareOutput(String[] expected, String actual) {

        int index = 0;
        String line;
        BufferedReader reader = new BufferedReader(new StringReader(actual));

        try {
            while ((line = reader.readLine()) != null) {
                assertEquals(expected[index++], line);
            }

            if (index != expected.length) {
                fail("Not enough data in returned result.");
            }

        } catch (IOException e) {
            fail("Problem with reading output to compare to expected.");
        } catch (ArrayIndexOutOfBoundsException e) {
            fail("Not enough output to match.");
        }
    }
}