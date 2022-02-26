package com.fokin.ilya;

import java.util.List;

public interface DatesToCronConverter {
    void convert(List<String> dates);

    static void getImplementationInfo() {
        System.out.println("              ВХОДНОЕ ЗАДАНИЕ ОТ УЧЕБНОГО ЦЕНТРА DIGITAL DESIGN");
        System.out.println("Выполнил: Фокин Илья Александрович");
        System.out.println("Имя класса реализации: Cron");
        System.out.println("Пакет: com.fokin.ilya");
        System.out.println("GitHub: https://github.com/Ilya-Fokin/com.fokin.ilya");
        System.out.println("P.S. Заранее прошу прощения за этот код, задание оказалось сложнее, чем я предполагал... ");
    }
}
