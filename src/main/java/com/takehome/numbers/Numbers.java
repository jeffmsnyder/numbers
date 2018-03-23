package com.takehome.numbers;

import org.apache.commons.cli.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This program is an application that takes integers in a base 10 format and converts them to their English
 * language equivalent. If the command line or the user input can not be processed, an error is reported. For each
 * number that is processed, the English language equivalent will be outputted or an appropriate error message will
 * be displayed indicating why it could not be translated.
 *
 * @author jeff.snyder
 */
public class Numbers {

    // When reading from standard input, this is the maximum number of lines that will be read.
    private static int MAX_NUMBERS = 1000;

    /**
     * Main method for numbers being converted into their English language equivalent.
     *
     * @param args command line arguments
     */
    public static void main(final String[] args) {

        try {
            Numbers numbers = new Numbers();
            List<String> integersToCovert = numbers.getProgramInput(args);

            // List would only be null if only help/usage was requested
            if (integersToCovert != null) {

                // Program was run, but no numbers were requested to be converted
                if (integersToCovert.isEmpty()) {
                    System.out.println("No integers specified to be converted to English.");
                } else {

                    // Convert the numbers input to their English language equivalent
                    ParseInteger parse = new ParseInteger();
                    IntegerToEnglishWordConverter converter = new IntegerToEnglishWordConverter();

                    for (String value : integersToCovert) {
                        try {
                            System.out.println(converter.convert(parse.parseIntegerFromString(value)));
                        } catch (NullPointerException e) {
                            System.out.println("Error: No number for the given line.");
                        } catch (NumberFormatException | ArithmeticException e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                    }
                }
            }
        } catch (ParseException e) {
            // Problem parsing the command line
            System.out.println("Error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid argument for n, must be a positive integer between 1 and " + MAX_NUMBERS + ".");
        } catch (IOException e) {
            System.out.println("Error: Problem reading from standard input.");
        }
    }

    /**
     * Process the command line and any user input to accumulate a list of numbers. Help/usage will be output if it
     * was requested. If only help is requested, no numbers will be accumulated.
     *
     * @param args command line arguments
     * @return list of numbers to be processed (entries may be null or empty), null if only help was requested
     * @throws ParseException problem parsing the command line
     * @throws IOException problem reading lines from standard input
     */
    private List<String> getProgramInput(final String[] args) throws ParseException, IOException {

        // Options for the command line
        Option count = Option.builder("n")
                .desc("Number of integers to be read from standard input (ignored if integers on command line). Range: 1-" + MAX_NUMBERS)
                .longOpt("count")
                .hasArg()
                .type(Integer.class)
                .build();
        Option help = Option.builder("h")
                .longOpt("help")
                .build();

        Options options = new Options();
        options.addOption(count);
        options.addOption(help);

        boolean helpRequested = false;
        Integer processLines = 0;

        CommandLineParser parser = new DefaultParser();
        CommandLine commandLine = parser.parse(options, args, true);

        // Determine what command line options were requested
        if (commandLine.hasOption("h")) {
            HelpFormatter formatter = new HelpFormatter();
            String header = "Converts integers into English words.";
            formatter.printHelp("numbers [-h] [-n <arg>] [<integer1> <integer2>...]", header, options, "\n\n", false);
            helpRequested = true;
        }

        if (commandLine.hasOption("n")) {
            processLines = Integer.parseInt(commandLine.getOptionValue("n"));
            if (processLines <= 0) {
                throw new NumberFormatException("Count specified was less than or equal to zero.");
            } else if (processLines > MAX_NUMBERS) {
                throw new NumberFormatException("Count specified was greater than " + MAX_NUMBERS + ".");
            }
        }

        String[] remainder = commandLine.getArgs();
        List<String> integersToConvert = new ArrayList<>();

        if (remainder.length > 0) {
            // If there are integers on the command line, process those only
            Collections.addAll(integersToConvert, remainder);
        } else if (processLines > 0) {
            // If there was a request to read numbers from standard input, read in each line as a number
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            while (processLines-- > 0) {
                integersToConvert.add(in.readLine());
            }
        } else if (helpRequested) {
            // If only help was requested, return null as no further processing is necessary
            return null;
        }

        return integersToConvert;
    }
}
