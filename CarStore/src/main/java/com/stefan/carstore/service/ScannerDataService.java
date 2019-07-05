package com.stefan.carstore.service;

import com.stefan.carstore.exceptions.MyException;

import java.math.BigDecimal;
import java.util.Scanner;

public class ScannerDataService
{
    private Scanner sc = new Scanner(System.in);


    // GET INT --------------------------------------------------------
    public Integer getInt(String message) {
        System.out.println(message);

        String text = sc.nextLine();

        if (!text.matches("\\d+")) {
            throw new MyException("INT VALUE IS NOT CORRECT: " + text);
        }

        return Integer.parseInt(text);
    }

    // GET STRING -----------------------------------------------------
    public String getString(String message)
    {
        System.out.println(message);
        return sc.nextLine();
    }

    // GET BIG DECIMAL ------------------------------------------------
    public BigDecimal getBigDecimal(String message)
    {
        System.out.println(message);
        return new BigDecimal(sc.nextLine());
    }

    // GET BOOLEAN ----------------------------------------------------
    public Boolean getBoolean(String message)
    {
        System.out.println(message);
        return sc.nextBoolean();
    }


    // CLOSE ----------------------------------------------------------
    public void close() {
        if (sc != null) {
            sc.close();
            sc = null;
        }
    }
}
