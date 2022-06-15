package jt.projects.androidcore.notes.data;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Map;

public class NotesDataFirebase extends NotesData {
    //FIREBASE
    private static final String CARDS_COLLECTION = "cards";
    private FirebaseFirestore store = FirebaseFirestore.getInstance();
    private CollectionReference collection = store.collection(CARDS_COLLECTION);

    @Override
    public void loadData() {
        data = new ArrayList<>();
        collection.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Map<String, Object> doc = document.getData();
                        String id = document.getId();
                        Note note = getNoteFromFBDoc(id, doc);
                        data.add(note);
                    }
                    sortByDate(data);
                    if (response != null) response.initialized();
                }
            }
        });
    }

    @Override
    public void saveData() {
        // no actions
    }

    @Override
    public void addNote(Note note) {
        super.addNote(note);
        collection.add(note.toFBDoc()).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                note.setId(documentReference.getId());
            }
        });
    }

    @Override
    public void editNote(Note editedNote, int index) {
        super.editNote(editedNote, index);
        Note n = data.get(index);
        // Изменить документ по идентификатору
        collection.document(n.getId()).set(n.toFBDoc());
    }


    @Override
    public void deleteNote(int index) {
        // Удалить документ с определённым идентификатором
        collection.document(data.get(index).getId()).delete();
        super.deleteNote(index);
    }

    // FIREBASE only
    public static Note getNoteFromFBDoc(String id, Map<String, Object> doc) {
        Note note = new Note();
        note.setId(id);
        note.topic = (String) doc.get("topic");
        note.description = (String) doc.get("description");
        note.author = (String) doc.get("author");
        String date = (String) doc.get("dateofcreation");
        String[] sDate = date.split("\\.");// ex.: 13.01.2022
        int year = Integer.parseInt(sDate[2]);
        int month = Integer.parseInt(sDate[1]) - 1; // !!!
        int day = Integer.parseInt(sDate[0]);
        note.dateOfCreation = new GregorianCalendar(year, month, day);
        return note;
    }
}
