package jt.projects.androidcore.cities;

import android.content.res.TypedArray;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import jt.projects.androidcore.R;


public class CoatOfArmsChildFragment extends Fragment {
    static final String ARG_INDEX_CHILD = "index";

    public CoatOfArmsChildFragment() {
        // Required empty public constructor
    }


    public static CoatOfArmsChildFragment newInstance() {
        CoatOfArmsChildFragment fragment = new CoatOfArmsChildFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_coat_of_arms_child, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            City city = arguments.getParcelable(ARG_INDEX_CHILD);
            TypedArray images =
                    getResources().obtainTypedArray(R.array.coat_of_arms_imgs);
            ((ImageView)
                    view.findViewById(R.id.city_emblem_image_view))
                    .setImageResource(images.getResourceId(city.getImageIndex(), 0));
            images.recycle();
        }

        view.findViewById(R.id.coat_of_arms_child_button_back).setOnClickListener(view1 -> {
            getParentFragmentManager().popBackStack();
        });
    }

    public static CoatOfArmsChildFragment newInstance(City city) {
        CoatOfArmsChildFragment fragment = new CoatOfArmsChildFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_INDEX_CHILD, city);
        fragment.setArguments(args);
        return fragment;
    }
}