package com.example.demo;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    final MutableLiveData<Boolean> item = new MutableLiveData<>();
    final MutableLiveData<Integer> counter = new MutableLiveData<>();
    private final MutableLiveData<Integer> numCircles = new MutableLiveData<>();

    public SharedViewModel() { }

    public Boolean getItem() {
        return item.getValue();
    }

    void setItem(Boolean item) {
        this.item.setValue(item);
    }

    int getNumCircles() {
        try {
            return numCircles.getValue();
        }
        catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    void setNumCircles(int item) {
        this.numCircles.setValue(item);
    }

    public int getCounter() {
        return counter.getValue();
    }

    void setCounter(int item) {
        this.counter.setValue(item);
    }
}
