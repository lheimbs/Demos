package com.example.demo;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.example.heimbsle69869.demo.R;

public class FragmentedActivity extends /*AppCompatActivity,*/ FragmentActivity {
    private FragmentToggle toggleFragment;
    private FragmentCheck checkFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        toggleFragment = (FragmentToggle) getSupportFragmentManager().findFragmentById(R.id.toggleFragment);
        checkFragment = (FragmentCheck) getSupportFragmentManager().findFragmentById(R.id.checkFragment);
    }

    public static class FragmentToggle extends Fragment {
        ToggleButton tb;
        private SharedViewModel model;

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            //return super.onCreateView(inflater, container, savedInstanceState);
            View view = inflater.inflate(R.layout.fragment_toggle_layout, container, false);
            tb = view.findViewById(R.id.toggleButton);
            return view;
        }

        @Override
        public void onActivityCreated(@Nullable Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);

            model = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);

            tb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    model.setItem(isChecked);
                }
            });
        }
    }

    public static class FragmentCheck extends Fragment {
        CheckBox cb;
        private SharedViewModel model;

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            //return super.onCreateView(inflater, container, savedInstanceState);
            View view = inflater.inflate(R.layout.fragment_check_layout, container, false);
            cb = view.findViewById(R.id.checkBox);
            return view;
        }

        @Override
        public void onActivityCreated(@Nullable Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);

            model = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);

            model.item.observe(getActivity(), new Observer<Boolean>(){

                @Override
                public void onChanged(@Nullable Boolean updatedObject) {
                    //.i("Info", "onChanged: recieved freshObject");
                    if (updatedObject != null) {
                        cb.setChecked(updatedObject);
                    }
                }
            });
        }
    }
}

