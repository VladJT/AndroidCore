package jt.projects.androidcore.notes.firebase;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import jt.projects.androidcore.notes.NotesData.*;

public class FBSource implements IFBSource{

    private static final String CARDS_COLLECTION = "cards";

    // База данных Firestore
    private FirebaseFirestore store = FirebaseFirestore.getInstance();
    // Коллекция документов
    private CollectionReference collection = store.collection(CARDS_COLLECTION);
    // Загружаемый список карточек
    private List<Note> cardsData = new ArrayList<Note>();


    @Override
    public IFBSource init(IFBResponse response) {
        return null;
    }

    @Override
    public Note getNote(int position) {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public void deleteNote(int position) {

    }

    @Override
    public void updateNote(int position, Note note) {

    }

    @Override
    public void addNote(Note note) {

    }

    @Override
    public void clearNote() {

    }

}
