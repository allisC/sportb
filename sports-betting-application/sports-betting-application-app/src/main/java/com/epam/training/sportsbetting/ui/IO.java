package com.epam.training.sportsbetting.ui;

import java.util.Scanner;

public class IO {

    public final Scanner sc = new Scanner(System.in);

    public void println(String message) {
        System.out.println(message);
    }

    public void print(String message) {
        System.out.print(message);
    }

    public String readOneLine() {
        String readLine = sc.nextLine();
        return readLine;
    }

}
