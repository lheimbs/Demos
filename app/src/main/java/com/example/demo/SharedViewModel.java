package com.example.demo;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    final MutableLiveData<Boolean> item = new MutableLiveData<>();

    public SharedViewModel() { }

    public Boolean getItem() {
        return item.getValue();
    }

    void setItem(Boolean item) {
        this.item.setValue(item);
    }
}
