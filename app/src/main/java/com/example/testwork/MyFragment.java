package com.example.testwork;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class MyFragment extends Fragment {

    public static Fragment newInstance(String title) {
        MyFragment myFragment = new MyFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        myFragment.setArguments(args);
        return myFragment;
    }

    public MyFragment(){}




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, container, false);

        Button tvTitle = (Button) view.findViewById(R.id.tv_title);
        tvTitle.setText(getArguments().getString("title"));

        return view;
    }

}

