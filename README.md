# Numbers

Convert integers into their English word equivalents.

## Getting Started

### Prerequisites

Requires Java 8 SDK
1. If not installed, download from here: http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
2. Accept the license agreement
3. Select the appropriate product to download
4. Install as appropriate for your machine OS

Requires Maven
1. If not installed, follows the instruction here: https://maven.apache.org/install.html

Requires Git
1. If not installed, follows the instruction here: https://git-scm.com/downloads

### Running locally using IntelliJ IDEA

Prerequisite for running the app using IntelliJ IDEA:

1. [Fork it](https://github.com/jeffmsnyder/numbers/fork)
2. Clone the project `git clone https://github.com/YOUR_GITHUB_USERNAME/numbers.git`
3. Open IntelliJ IDEA `File -> New -> Project from Existing Sources... `
4. Navigate to the project and select `pom.xml`. Click `OK`
5. Click `Next` on Import Project from Maven window
6. Verify that project is selected, click 'Next'
7. Verify that the Java 8 SDK is selected (version 1.8), click 'Next'
8. Click 'Finished'

The first two steps can be changed to just download the project, by doing the following:

1. Go to https://github.com/jeffmsnyder/numbers
2. Select Clone or Download / Download ZIP
3. Unzip project and place where project where it is to be located
4. Continue with step 3 above.

To run under IntelliJ:

1. Edit the configuration to run Numbers
2. Update the command line arguments as desired
3. Depending on the arguments, running it will generate results or wait until additional user input before results are output

### Installing

Run the following command from the top level project directory (where the pom.xml file is):

mvn clean package

## Running the tests

To run the tests, use the following command:

mvn clean test

IntegerToEnglishWordConverterTest: tests converting an int to english words
ParseIntegerTest: tests parsing input into an int

### End to end tests

NumbersTest: tests the complete application

## Running from the command line after installation

### Windows

From the project directory, use numbers.bat.  An example of its use is as follows:

numbers 45 3243 5434 -7754 000034 43244gd

Output from the application is:

Forty five
Three thousand two hundred and forty three
Five thousand four hundred and thirty four
Negative seven thousand seven hundred and fifty four
Thirty four
Error: Non-numeric character in input.

### *nix

This command must be run before the shell script can be used:

chmod +x numbers.sh

Then, the following is an example of its usage:

./numbers 45 3243 5434 -7754 000034 43244gd

Output from the application is:

Forty five
Three thousand two hundred and forty three
Five thousand four hundred and thirty four
Negative seven thousand seven hundred and fifty four
Thirty four
Error: Non-numeric character in input.

## Usage

<pre>
usage: numbers [-h] [-n <arg>] [<integer1> <integer2>...]
Converts integers into English words.
 -h,--help
 -n,--count <arg>   Number of integers to be read from standard input
                    (ignored if integers on command line). Range: 1-1000
</pre>

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management

## Authors

* **Jeff Snyder** - *Initial work* - [jeffmsnyder](https://github.com/jeffmsnyder)

