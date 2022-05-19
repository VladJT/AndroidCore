package jt.projects.androidcore.notes;

import java.util.ArrayList;
import java.util.List;

class NoteChangePublisher {
    private List<NoteChangeObserver> observers; // Все подписчики

    public NoteChangePublisher() {
        observers = new ArrayList<>();
    }

    public void subscribe(NoteChangeObserver observer) {
        observers.add(observer);
    }

    public void unsubscribe(NoteChangeObserver observer) {
        observers.remove(observer);
    }

    // Разослать событие по подписчикам
    public void notify(NotesData.Note note, int index) {
        for (NoteChangeObserver observer : observers) {
            observer.changeNote(note, index);
        }
    }

}
