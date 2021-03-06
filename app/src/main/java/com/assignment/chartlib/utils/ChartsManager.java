package com.assignment.chartlib.utils;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.highsoft.highcharts.core.HIChartView;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChartsManager {
    private String title;
    public HIChartView chartView;
    Context context;
    String chartType;
    String scale;

    public ChartsManager(HIChartView chartView,Context context,String chartType,String scale) {
        this.chartView = chartView;
        this.context = context;
        this.chartType = chartType;
        this.scale = scale;
    }

    public HIChartView setChart( String dataFilename, Boolean enableExport) {
        if (chartType.equals("area")) title = "Steps";
        else title = "Calories";

        Double total = 0.0;
        String subtitle;

        Map allSeries = jsonFileToMap(dataFilename, context);

        List<Double> daySeries = (List) allSeries.get(scale); // we pass scale of the chart (day, week, etc...)


        StringBuilder series = new StringBuilder();

        for (Double d : daySeries) {
            series.append(String.valueOf(d.intValue()));
            series.append(",");
            total = total + d;
        }
        series.deleteCharAt(series.length() - 1);
        subtitle = (String.valueOf(total));
        subtitle = subtitle.substring(0, subtitle.length() - 2);

        Map<String, String> options = new HashMap<>();
        options.put("chartType", chartType);
        options.put("title", title);
        options.put("subtitle", subtitle);
        if (enableExport) options.put("export", "true");
        else options.put("export", "false");

        chartView.setOptions(OptionsProvider.provideOptionsForChartType(options, (ArrayList) daySeries, scale));

        return chartView;
    }

    public void updateChart(String dataFilename,String scale) {
        if (chartType.equals("area")) title = "Steps";
        else title = "Calories";

        Double total = 0.0;
        String subtitle;

        Map allSeries = jsonFileToMap(dataFilename, context);
        List<Double> scaleSeries = (List) allSeries.get(scale); // we pass scale of the chart (day, week, etc...)
        StringBuilder series = new StringBuilder();
        for (Double d : scaleSeries) {
            series.append(String.valueOf(d.intValue()));
            series.append(",");
            total = total + d;
        }
        series.deleteCharAt(series.length() - 1);
        subtitle = (String.valueOf(total));
        subtitle = subtitle.substring(0, subtitle.length() - 2);

        Map<String, String> options = new HashMap<>();
        options.put("chartType", chartType);
        options.put("title", title);
        options.put("subtitle", subtitle);
        options.put("export", "true");

        chartView.setOptions(OptionsProvider.provideOptionsForChartType(options, (ArrayList) scaleSeries, scale));
    }

    private static String loadJSONFromAsset(String file, Context c) {
        String json;
        try {
            InputStream is = c.getAssets().open(file);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    private static Map<String, Object> jsonFileToMap(String fileName, Context c) {
        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, Object>>() {
        }.getType();
        Map<String, Object> map = gson.fromJson(loadJSONFromAsset(fileName, c), type);
        return map;
    }
}
