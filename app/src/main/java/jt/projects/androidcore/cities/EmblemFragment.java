package jt.projects.androidcore.cities;

import android.content.res.TypedArray;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import jt.projects.androidcore.R;

public class EmblemFragment extends Fragment {
    static final String ARG_INDEX = "index";

    public EmblemFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // костыль
//        if (savedInstanceState != null) {
//            requireActivity().getSupportFragmentManager().popBackStack();
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_emblem, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        Bundle args = getArguments();
//        if (args != null) {
//            City city = args.getParcelable(ARG_INDEX);
//            // найдем в root view нужный ImageView
//            ImageView emblemImage = view.findViewById(R.id.city_emblem_image_view);
//
//            // Получим из ресурсов массив указателей на изображения гербов
//            // Обратите внимание на тип - TypedArray, и способ получения - obtainTypedArray
//            TypedArray images = getResources().obtainTypedArray(R.array.coat_of_arms_imgs);
//
//            emblemImage.setImageResource(images.getResourceId(city.getImageIndex(), 0));
//            // TypedArray рекомендуется закрыть после использования
//            images.recycle();
//
//            // найдем в root view нужный TextView
//            TextView textView = view.findViewById(R.id.coat_of_arms_text_view);
//            textView.setText(city.getCityName());
//        }

        super.onViewCreated(view, savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            City city = arguments.getParcelable(ARG_INDEX);
            TextView textView = view.findViewById(R.id.coat_of_arms_text_view);
            textView.setText(city.getCityName());
            getChildFragmentManager().beginTransaction().addToBackStack("").replace(R.id.coat_of_arms_child_container, CoatOfArmsChildFragment.newInstance(city)).commit();
        }

        Button buttonBack = view.findViewById(R.id.coat_of_arms_button_back);
        buttonBack.setOnClickListener(view1 -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        view.findViewById(R.id.coat_of_arms_button_remove).setOnClickListener(view1 -> {
            final FragmentManager fragmentManager =
                    requireActivity().getSupportFragmentManager();
            final List<Fragment> fragments = fragmentManager.getFragments();
            for (Fragment fragment : fragments) {
                if (fragment instanceof EmblemFragment && fragment.isVisible())
                    fragmentManager.beginTransaction().remove(fragment).commit();
            }
        });
    }

    // Фабричный метод создания фрагмента
    // Фрагменты рекомендуется создавать через фабричные методы
    public static EmblemFragment newInstance(City city) {
        EmblemFragment ef = new EmblemFragment();
        // Передача параметра через бандл
        Bundle b = new Bundle();
        b.putParcelable(ARG_INDEX, city);
        ef.setArguments(b);
        return ef;
    }
}