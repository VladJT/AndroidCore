package jt.projects.androidcore.examples.fragmentobservers;

import java.util.ArrayList;
import java.util.List;

class Publisher {
    private List<Observer> observers;// Все подписчики (Fragment1,    Fragment2)

    public Publisher() {
        this.observers = new ArrayList<>();
    }

    public void subscribe(Observer o) {
        observers.add(o);
    }

    // Отписать
    public void unsubscribe(Observer observer) {
        observers.remove(observer);
    }

    // Разослать событие по подписчикам (изменение в тексте
    public void notify(String text) {
        for (Observer o : observers) {
            o.updateText(text);
        }
    }

}
