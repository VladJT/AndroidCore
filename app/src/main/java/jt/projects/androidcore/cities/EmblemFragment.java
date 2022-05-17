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
        super.onViewCreated(view, savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            int index = args.getInt(ARG_INDEX);
            // найдем в root view нужный ImageView
            ImageView emblemImage = view.findViewById(R.id.city_emblem_image_view);

            // Получим из ресурсов массив указателей на изображения гербов
            // Обратите внимание на тип - TypedArray, и способ получения - obtainTypedArray
            TypedArray images = getResources().obtainTypedArray(R.array.coat_of_arms_imgs);

            emblemImage.setImageResource(images.getResourceId(index, 0));
            // TypedArray рекомендуется закрыть после использования
            images.recycle();
        }
    }

    // Фабричный метод создания фрагмента
    // Фрагменты рекомендуется создавать через фабричные методы
    public static EmblemFragment getInstance(int index) {
        EmblemFragment ef = new EmblemFragment();
        // Передача параметра через бандл
        Bundle b = new Bundle();
        b.putInt(ARG_INDEX, index);
        ef.setArguments(b);
        return ef;
    }
}