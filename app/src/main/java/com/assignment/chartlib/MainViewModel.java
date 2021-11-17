package com.assignment.chartlib;


import androidx.lifecycle.ViewModel;
import com.assignment.chartlib.utils.ChartsManager;


public class MainViewModel extends ViewModel {
    public String chartType = "column";
    public String scale = "day";
    private ChartsManager manager;


    public void setManager(ChartsManager mng) {
        manager = mng;
    }

    public void loadChatData() {
        manager.setChart("DataCaloriesOut.json", true);
    }


    public void loadDayData() {
        scale = "day";
        manager.updateChart("DataCaloriesOut.json", scale);
        manager.chartView.reload();
    }

}
