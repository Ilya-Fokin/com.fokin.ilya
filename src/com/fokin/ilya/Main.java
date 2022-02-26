package com.fokin.ilya;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void test1(){
        System.out.println("---------------ТЕСТ №1---------------");
        Cron cron = new Cron();
        String[] dates = {"2022-01-25T08:00:00",
                "2022-01-25T08:30:00",
                "2022-01-25T09:00:00",
                "2022-01-25T09:30:00",
                "2022-01-26T08:00:00",
                "2022-01-26T08:30:00",
                "2022-01-26T09:00:00",
                "2022-01-26T09:30:00"

        };
        Arrays.sort(dates);
        List<String> datesList = Arrays.asList(dates);
        cron.convert(datesList);
        System.out.println("------------------------------------");
    }
    public static void test2(){
        System.out.println("---------------ТЕСТ №2---------------");
        Cron cron = new Cron();
        String[] dates = {"2022-01-24T19:53:00",
                "2022-01-24T19:54:00",
                "2022-01-24T19:55:00",
                "2022-01-24T19:56:00",
                "2022-01-24T19:57:00",
                "2022-01-24T19:58:00",
                "2022-01-24T19:59:00",
                "2022-01-24T20:00:00",
                "2022-01-24T20:01:00",
                "2022-01-24T20:02:00"
        };
        Arrays.sort(dates);
        List<String> datesList = Arrays.asList(dates);
        cron.convert(datesList);
        System.out.println("------------------------------------");
    }
    public static void main(String[] args) {
        test1();
        test2();
    }
}
