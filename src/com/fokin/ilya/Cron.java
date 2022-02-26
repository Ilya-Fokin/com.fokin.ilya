package com.fokin.ilya;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public class Cron implements DatesToCronConverter {
    private List<String> crons;
    private List<String> dates;

    public Cron() {
        this.dates = new ArrayList<>();
        this.crons = new ArrayList<>();
    }

    public Cron(List<String> dates) {
        this.dates = dates;
        this.crons = new ArrayList<>();
    }

    public List<String> getCrons() {
        return crons;
    }

    public void setCrons(List<String> crons) {
        this.crons = crons;
    }

    public List<String> getDates() {
        return dates;
    }

    public void setDates(List<String> dates) {
        this.dates = dates;
    }

    public boolean isInteger(String str) {
        boolean is = true;
        try {
            int c = Integer.parseInt(str);
        } catch (NumberFormatException e) {
            is = false;
        }
        return is;
    }

    /*Данный метод проверяет каждую дату из списка, в случае, если больше половины дат не прошло проверку, выбрасывает исключение*/
    public List<String> isValidDate(List<String> date) throws DatesToCronConvertException {
        int notValid = 0;
        List<String> validDates = new ArrayList<>();

        for (int i = 0; i < date.size(); i++) {
            boolean isValid = true;
            String[] strArr = date.get(i).split("-|T|:");
                if (strArr.length != 6) {
                    isValid = false;
                } else
                    for (int j = 0; j < strArr.length; j++) {
                        if (!isInteger(strArr[j])) {
                            isValid = false;
                            break;
                        } else
                        if (j == 0) {
                            if (strArr[j].length() != 4) {
                                isValid = false;
                                break;
                            }
                        }
                        if (j == 1) {
                            if (strArr[j].length() != 2 || Integer.parseInt(strArr[j]) < 1 || Integer.parseInt(strArr[j]) > 12) {
                                isValid = false;
                                break;
                            }
                        }
                        if (j == 2) {
                            if (strArr[j].length() != 2 || Integer.parseInt(strArr[j]) < 1 || Integer.parseInt(strArr[j]) > 31) {
                                isValid = false;
                                break;
                            }
                        }
                        if (j == 3) {
                            if (strArr[j].length() != 2 || Integer.parseInt(strArr[j]) < 0 || Integer.parseInt(strArr[j]) > 23) {
                                isValid = false;
                                break;
                            }
                        }
                        if (j == 4) {
                            if (strArr[j].length() != 2 || Integer.parseInt(strArr[j]) < 0 || Integer.parseInt(strArr[j]) > 59) {
                                isValid = false;
                                break;
                            }
                        }
                        if (j == 5) {
                            if (strArr[j].length() != 2 || Integer.parseInt(strArr[j]) < 0 || Integer.parseInt(strArr[j]) > 59) {
                                isValid = false;
                                break;
                            }
                        }
                    }
            if (!isValid) {
                notValid++;
            } else
                validDates.add(date.get(i));
        }

        if (notValid > date.size()/2) {
            throw new DatesToCronConvertException("Больше половины дат не прошли валидацию");
        } else
            return validDates;
    }

    @Override
    public void convert(List<String> datesArr) {
        List<String> isValidDates = new ArrayList<>();
        List<Integer> seconds = new ArrayList<>();
        List<Integer> minutes = new ArrayList<>();
        List<Integer> hours = new ArrayList<>();
        List<String> dates = new ArrayList<>();
        String cron = "";

        try {
            isValidDates = isValidDate(datesArr);
        } catch (DatesToCronConvertException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < isValidDates.size(); i++) {
            String date = isValidDates.get(i);
                String[] strArr = date.split("-|T|:");

                System.out.println(isValidDates.get(i));

                String date1 = strArr[0] + "-" + strArr[1] + "-" + strArr[2];

                minutes.add(Integer.parseInt(strArr[4]));
                seconds.add(Integer.parseInt(strArr[5]));
                hours.add(Integer.parseInt(strArr[3]));

                dates.add(date1);
        }
        cron = "'" + analysisSecOrMin(seconds) + " " + analysisSecOrMin(minutes) + " " + analysisHour(hours, analysisSecOrMin(minutes)) + " " + analisysDate(dates) + "'";
        System.out.println("Cron: " + cron);
    }

    public String analisysDate(List<String> dates) {
        String cron = "";
        String cronDayOfWeek = "";
        String cronMonth = "";
        String cronDayOfMonth = "";

        List<Integer> daysOfWeek = new ArrayList<>();
        List<Integer> months = new ArrayList<>();
        List<Integer> daysOfMonths = new ArrayList<>();

        int repeatedDaysOfWeek = 0;
        int repeatedMonth = 0;
        int repeatedDaysOfMonth = 0;

        for (int i = 0; i < dates.size(); i++) {
            String[] date = dates.get(i).split("-");
            Calendar calendar = new GregorianCalendar(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2]));
            if (i == 0) {
                daysOfWeek.add(calendar.get(Calendar.DAY_OF_WEEK));
                months.add(calendar.get(Calendar.MONTH));
                daysOfMonths.add(calendar.get(Calendar.DAY_OF_MONTH));
            } else {
                if (!daysOfWeek.contains(calendar.get(Calendar.DAY_OF_WEEK))) {
                    daysOfWeek.add(calendar.get(Calendar.DAY_OF_WEEK));
                } else repeatedDaysOfWeek++;
                if (!months.contains(calendar.get(Calendar.MONTH))) {
                    months.add(calendar.get(Calendar.MONTH));
                } else repeatedMonth++;
                if (!daysOfMonths.contains(calendar.get(Calendar.DAY_OF_MONTH))) {
                    daysOfMonths.add(calendar.get(Calendar.DAY_OF_MONTH));
                } else repeatedDaysOfMonth++;
            }
        }

        for (int j = 0; j < daysOfWeek.size(); j++) {
            if (j+1 == daysOfWeek.size()) {
                cronDayOfWeek += daysOfWeek.get(j);
            } else
                cronDayOfWeek += daysOfWeek.get(j) + ",";
        }

        for (int j = 0; j < months.size(); j++) {
            if (j+1 == months.size()) {
                cronMonth += months.get(j);
            } else
                cronMonth += months.get(j) + ",";
        }

        for (int j = 0; j < daysOfMonths.size(); j++) {
            if (j+1 == daysOfMonths.size()) {
                cronDayOfMonth += daysOfMonths.get(j);
            } else
                cronDayOfMonth += daysOfMonths.get(j) + ",";
        }

        if (repeatedDaysOfWeek >= dates.size()/2 && daysOfWeek.size() != 1) {
            cronDayOfWeek = "*";
        }
        if (repeatedMonth >= dates.size()/2) {
            cronMonth = "*";
        }
        if (repeatedDaysOfMonth >= dates.size()/2 && daysOfMonths.size() != 1) {
            cronDayOfMonth = "*";
        }

        cron = cronDayOfMonth + " " + cronMonth + " " + cronDayOfWeek;
        return cron;
    }
    /*Данный метод принимает список часов и анализирует его*/
    public String analysisHour(List<Integer> hours, String cronMinutes) {
        String cron = "";
        List<Integer> crons = new ArrayList<>();
        int repeated = 0;

        for (int i = 0; i < hours.size(); i++) {
            if (i == 0) {
                cron += hours.get(i);
                crons.add(hours.get(i));
            } else {
                if (!crons.contains(hours.get(i))) {
                    cron += "-" + hours.get(i);
                    crons.add(hours.get(i));
                } else repeated++;
            }
        }

        if (cron.split(",").length > 4) {
            cron = "*";
        }
        return cron;
    }

    /*Данный метод принимает список минут или секунд и анализирует его*/
    public String analysisSecOrMin(List<Integer> list) {
        String cron = "";
        List<Integer> uniqueTime = new ArrayList<>();
        int isConvert = 0;
        int difference = 0;

        for (int i = 0; i < list.size(); i++) {
            if (!uniqueTime.contains(list.get(i))) {
                uniqueTime.add(list.get(i));
            }
            if (i == 0) {
                continue;
            } else {
                if (difference != Math.abs(list.get(i) - list.get(i-1))) {
                    difference = Math.abs(list.get(i) - list.get(i-1));
                    isConvert = 1;
                } else
                    isConvert++;
                difference = Math.abs(list.get(i) - list.get(i-1));
                if (isConvert >= (list.size()/2-1)) {
                    if (difference == 0 ) {
                        cron = "0";
                        break;
                    } else
                    if (difference == 1) {
                        cron = "*";
                        break;
                    }
                    else
                        if (uniqueTime.size() == 2) {
                            cron = uniqueTime.get(0) + "," + uniqueTime.get(1);
                            break;
                        }
                        if (uniqueTime.size() > 2) {
                            for (int k = 0; k < uniqueTime.size(); k++) {
                                if (k + 1 == uniqueTime.size()) {
                                    cron += uniqueTime.get(i);
                                } else
                                    cron += uniqueTime.get(i) + ",";
                            }
                        } else
                        cron = "*/" + difference;
                    break;
                }
            }
        }

        if (cron.equals("")) {
            cron = "?";
        }
        return cron;
    }

    @Override
    public void getImplementationInfo() {
        System.out.println("              ВХОДНОЕ ЗАДАНИЕ ОТ УЧЕБНОГО ЦЕНТРА DIGITAL DESIGN");
        System.out.println("Выполнил: Фокин Илья Александрович");
        System.out.println("Имя класса реализации: Cron");
        System.out.println("Пакет: com.fokin.ilya");
    }
}
