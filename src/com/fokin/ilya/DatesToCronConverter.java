package com.fokin.ilya;

import java.util.List;

public interface DatesToCronConverter {
    void convert(List<String> dates);
    void getImplementationInfo();
}
