package org.example;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MainTest {

    private void runMainWithInput(String input, String expectedOutput) {
        // Prepare input and capture output
        ByteArrayInputStream in = new ByteArrayInputStream((input + "\n").getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setIn(in);
        System.setOut(new PrintStream(out));

        // Run the program
        Main.main(null);

        // Get the printed output
        String actualOutput = out.toString();
        assertTrue(actualOutput.contains(expectedOutput),
                "Expected: \"" + expectedOutput + "\" in output, but got:\n" + actualOutput);
    }

    @Test
    public void testFreezingPoint() {
        runMainWithInput("0", "Temperature in Fahrenheit: 32.00");
    }

    @Test
    public void testBoilingPoint() {
        runMainWithInput("100", "Temperature in Fahrenheit: 212.00");
    }

    @Test
    public void testNegativeTemp() {
        runMainWithInput("-40", "Temperature in Fahrenheit: -40.00");
    }

    @Test
    public void testSampleTemp() {
        runMainWithInput("27", "Temperature in Fahrenheit: 80.60");
    }

    @Test
    public void testDecimalInput() {
        runMainWithInput("37", "Temperature in Fahrenheit: 98.60");
    }
}