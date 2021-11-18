package com.assignment.chartlib;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import com.assignment.chartlib.databinding.ActivityMainBinding;
import com.assignment.chartlib.utils.ChartsManager;

import java.util.ArrayList;

//binding.
public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        float height = getResources().getDisplayMetrics().heightPixels;
        binding.chartView.getLayoutParams().height = Math.round(height * 2 / 5);
        final ChartsManager manager = new ChartsManager(binding.chartView, getApplicationContext(), viewModel.chartType, viewModel.scale);
        viewModel.setManager(manager);
        viewModel.loadChatData();
        binding.setViewModel(viewModel);
    }
}