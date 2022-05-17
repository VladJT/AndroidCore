package jt.projects.androidcore.examples.fragmentobservers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import jt.projects.androidcore.R;

public class FragmentObserversMainActivity extends AppCompatActivity implements PublisherGetter {

    private Publisher publisher = new Publisher();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_observers_main);

        // Создаём фрагменты
        Fragment1 fragment1 = new Fragment1();
        Fragment2 fragment2 = new Fragment2();
        FragmentMain mainFragment = new FragmentMain();

        publisher.subscribe(fragment1);
        publisher.subscribe(fragment2);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_main, mainFragment);
        ft.replace(R.id.fragment_1, fragment1);
        ft.replace(R.id.fragment_2, fragment2);
        ft.commit();
    }

    // Снимем с activity обязанность по передаче событий классу Publisher.
    // Главный фрагмент будет использовать его для передачи событий
    @Override
    public Publisher getPublisher() {
        return publisher;
    }
}