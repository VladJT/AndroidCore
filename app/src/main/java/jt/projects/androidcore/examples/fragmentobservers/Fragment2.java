package jt.projects.androidcore.examples.fragmentobservers;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import jt.projects.androidcore.R;


public class Fragment2 extends Fragment implements Observer {

    private TextView textView;

    public Fragment2() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_2, container, false);
        textView = view.findViewById(R.id.textView2);
        return view;

    }

    @Override
    public void updateText(String text) {
        textView.setText(text);
    }
}