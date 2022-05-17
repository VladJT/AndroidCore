package jt.projects.androidcore.notes;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.google.android.material.textfield.TextInputEditText;

import jt.projects.androidcore.R;


public class NoteInfoFragment extends Fragment {


    public NoteInfoFragment() {
        // Required empty public constructor
    }


    public static NoteInfoFragment newInstance(int index) {
        NoteInfoFragment noteInfoFragment = new NoteInfoFragment();
        // Передача параметра через бандл
        Bundle b = new Bundle();
      //  b.putInt(ARG_INDEX, index);
   //     noteInfoFragment.setArguments(b);
        return noteInfoFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_note_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle args = getArguments();

        TextInputEditText topic = view.findViewById(R.id.notes_info_topic);
        topic.setText("Заметка Х");


//        if (args != null) {

//            int index = args.getInt(NOTE_INDEX);
//            // найдем в root view нужный ImageView
//            ImageView emblemImage = view.findViewById(R.id.city_emblem_image_view);
//
//            // Получим из ресурсов массив указателей на изображения гербов
//            // Обратите внимание на тип - TypedArray, и способ получения - obtainTypedArray
//            TypedArray images = getResources().obtainTypedArray(R.array.coat_of_arms_imgs);
//
//            emblemImage.setImageResource(images.getResourceId(index, 0));
//            // TypedArray рекомендуется закрыть после использования
//            images.recycle();
 //       }
    }
}