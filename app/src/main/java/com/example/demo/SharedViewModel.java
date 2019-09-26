package com.example.demo;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    final MutableLiveData<Boolean> item = new MutableLiveData<>();
    final MutableLiveData<Integer> numCircles = new MutableLiveData<>();

    public SharedViewModel() { }

    public Boolean getItem() {
        return item.getValue();
    }

    void setItem(Boolean item) {
        this.item.setValue(item);
    }

    public int getNumCircles() {
        int n = 5;
        try {
            n = numCircles.getValue();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return n;
    }

    void setNumCircles(int item) {
        this.numCircles.setValue(item);
    }
}
