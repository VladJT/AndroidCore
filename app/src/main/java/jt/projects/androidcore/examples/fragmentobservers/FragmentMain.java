package jt.projects.androidcore.examples.fragmentobservers;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import jt.projects.androidcore.R;


public class FragmentMain extends Fragment {

    private Publisher publisher; // Обработчик подписок

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        publisher = ((PublisherGetter) context).getPublisher(); // получим обработчика подписок
    }

    public FragmentMain() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container,
                false);
        final EditText textView = view.findViewById(R.id.editText);
        Button button = view.findViewById(R.id.button); // По этой кнопке будем отправлять события
        button.setOnClickListener(v -> {
            String text = textView.getText().toString();
            publisher.notify(text); // Отправить изменившуюся  строку
        });
        return view;
    }
}
