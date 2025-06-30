package org.example;
import java.util.Scanner;
import java.util.*;//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Temperature in Celsius: ");
        float celsius = scanner.nextInt();
        float fahrenheit = (celsius * (9.0f/5.0f) + 32.0f);
        System.out.println("Temperature in Fahrenheit: ".concat(String.format("%.2f",fahrenheit)));

    }
}