package org.example;

import org.junit.jupiter.api.*;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MainTest {

    private final String REPORT_FILE = "test-results.csv";
    private PrintWriter writer;

    @BeforeAll
    public void setupReportFile() throws IOException {
        writer = new PrintWriter(new FileWriter(REPORT_FILE, false)); // overwrite
        writer.println("Test Name,Input,Expected Output,Actual Output,Status,Timestamp");
    }

    @AfterAll
    public void closeReportFile() {
        if (writer != null) {
            writer.close();
        }
    }

    private void runTestWithCSV(String testName, String input, String expectedOutput) {
        ByteArrayInputStream in = new ByteArrayInputStream((input + "\n").getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        System.setIn(in);
        System.setOut(new PrintStream(out));

        String actualOutput;
        String status;
        try {
            Main.main(null);
            actualOutput = out.toString().trim();

            // Normalize spaces/newlines
            String cleanedOutput = actualOutput.replaceAll("\\s+", " ");
            String cleanedExpected = expectedOutput.replaceAll("\\s+", " ");

            // Extract result line only (in case multiple lines are printed)
            String resultLine = actualOutput.lines()
                    .filter(line -> line.contains("Temperature in Fahrenheit"))
                    .findFirst()
                    .orElse("");

            if (resultLine.contains(expectedOutput)) {
                status = "PASS";
            } else {
                status = "FAIL";
            }

        } catch (Exception e) {
            actualOutput = "Exception: " + e.getMessage();
            status = "FAIL";
        }

        // Log to CSV
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        writer.printf("\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\"%n",
                testName,
                input,
                expectedOutput,
                actualOutput.replace("\n", "\\n").replace("\r", ""),
                status,
                timestamp);
    }



    @Test
    public void testFreezingPoint() {
        runTestWithCSV("testFreezingPoint", "0", "Temperature in Fahrenheit: 32.00");
    }

    @Test
    public void testBoilingPoint() {
        runTestWithCSV("testBoilingPoint", "100", "Temperature in Fahrenheit: 212.00");
    }

    @Test
    public void testNegativeTemp() {
        runTestWithCSV("testNegativeTemp", "-40", "Temperature in Fahrenheit: -40.00");
    }

    @Test
    public void testSampleTemp() {
        runTestWithCSV("testSampleTemp", "27", "Temperature in Fahrenheit: 80.60");
    }

    @Test
    public void testDecimalInput() {
        runTestWithCSV("testDecimalInput", "37", "Temperature in Fahrenheit: 98.60");
    }
}
