package jt.projects.androidcore.notes;

import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import jt.projects.androidcore.notes.firebase.IFBResponse;

public class NotesData {
    private static NotesData notesData = null;

    public static NotesData getInstance() {
        if (notesData == null) {
            notesData = new NotesData();
        }
        return notesData;
    }

    public void clearData(){
        notesData = null;
    }

    // ТИП БАЗЫ ДАННЫХ
    public DATABASE getSourceType() {
        return NotesSharedPreferences.getInstance().getDBSource();
    }

    //FIREBASE
    private static final String CARDS_COLLECTION = "cards";
    private FirebaseFirestore store = FirebaseFirestore.getInstance();
    private CollectionReference collection = store.collection(CARDS_COLLECTION);
    private IFBResponse response = null;

    private ArrayList<Note> data;

    private NotesData() {
        this.data = new ArrayList<>();
    }

    public void loadData() {
        if (getSourceType() == DATABASE.SHARED_PREF) {
            loadFromSharedPreferences();
        }
        if (getSourceType() == DATABASE.FIREBASE) {
            loadFromFireBase();
        }
    }

    public void setResponse(IFBResponse response) {
        this.response = response;
    }

    private void loadFromFireBase() {
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
                    data.sort(new Comparator<Note>() {
                        @Override
                        public int compare(Note o1, Note o2) {
                            if (o1.dateOfCreation.getTimeInMillis() > o2.dateOfCreation.getTimeInMillis())
                                return 1;
                            if (o1.dateOfCreation.getTimeInMillis() < o2.dateOfCreation.getTimeInMillis())
                                return -1;
                            return 0;
                        }
                    });
                    if (response != null) response.initialized();
                }
            }
        });
    }

    private void loadFromSharedPreferences() {
        try {
            SharedPreferences sharedPref = NotesSharedPreferences.getInstance().getCustomSharedPreferences(NotesConstants.NAME_SHARED_PREFERENCES_DATA);
            String jsonNotes = sharedPref.getString(NotesConstants.NOTES_JSON_DATA, null);
            Type type = new TypeToken<ArrayList<Note>>() {
            }.getType();

            this.data = new GsonBuilder().create().fromJson(jsonNotes, type);
            if (data == null) {
                data = new ArrayList<>();
            }
        } catch (JsonSyntaxException e) {
            //    Toast.makeText(this, "Ошибка трансформации", Toast.LENGTH_SHORT).show();
        }

    }

    private void addTestData() {
        data.add(new Note("Заметка 1", "обработку надо создавать при создании элемента, то есть или на\n" +
                "onCreateViewHolder, или в самом ViewHolder. Однако метод onCreateViewHolder отвечает за\n" +
                "предоставление View элемента вновь созданному ViewHolder, не стоит перегружать его ещё и\n" +
                "созданием обработчиков. ViewHolder же сам знает всё про свои элементы, поэтому и слушателей\n" +
                "будем организовывать во ViewHolder. Говоря формальным языком, мы инкапсулируем создание\n" +
                "обработчиков элементов внутри ViewHolder", "Влад", new GregorianCalendar(2021, 0, 25)));
        data.add(new Note("Заметка 2", "Высоко-высоко в горах ...", "Стас", new GregorianCalendar(2022, 2, 1)));
        data.add(new Note("Заметка 3", "В далекой галактике...", "Петр", Calendar.getInstance()));
        data.add(new Note("Заметка 4", "Использование SimpleDateFormat для форматирование ввода-вывода даты в Java. ... SimpleDateFormat является подклассом DateFormat, который позволяет форматировать ввод-вывод даты и времени в рамках... ", "Влад", new GregorianCalendar(2021, 0, 25)));
        data.add(new Note("Заметка 5", "Настройка цветов происходит по определённым правилам. На сайте http://www.google.com/design/spec/style/color.html# есть таблица цветов. Обратите внимание на числа слева. Основным цветом (colorPrimary) считается цвет под номером 500, он идёт первым в таблицах. Этот цвет должен использоваться в качестве заголовка (Toolbar).", "Стас", new GregorianCalendar(2022, 2, 1)));
        data.add(new Note("Заметка 6", "В далекой галактике...", "Петр", Calendar.getInstance()));
    }

    public void saveData() {
        if (getSourceType() == DATABASE.FIREBASE) {
            //   automatically
        }
        if (getSourceType() == DATABASE.SHARED_PREF) {
            saveToSharedPreferences(); // on exit app
        }
    }

    private void saveToSharedPreferences() {
        SharedPreferences sharedPref = NotesSharedPreferences.getInstance().getCustomSharedPreferences(NotesConstants.NAME_SHARED_PREFERENCES_DATA);
        String jsonNotes = "";
        if (data != null) {
            jsonNotes = new GsonBuilder().create().toJson(data);
        }
        sharedPref.edit().putString(NotesConstants.NOTES_JSON_DATA, jsonNotes).apply();
    }


    public Note getNote(int index) {
        if (index >= 0 && index < data.size()) {
            return data.get(index);
        } else {
            return new Note("Тема Х", "Заметка не существует", "Автор не известен", Calendar.getInstance());
        }
    }

//    public String[] getNotesList() {
//        return data.stream().map(n -> n.topic).toArray(String[]::new);
//    }

    public int getSize() {
        return data.size();
    }

    public void addNote(Note note) {
        data.add(note);
        if (getSourceType() == DATABASE.FIREBASE) {
            collection.add(note.toFBDoc()).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    note.setId(documentReference.getId());
                }
            });
        }
    }

    public void editNote(Note editedNote, int index) {
        editedNote.setId(data.get(index).getId());
        data.set(index, editedNote);

        if (getSourceType() == DATABASE.FIREBASE) {
            Note n = data.get(index);
            // Изменить документ по идентификатору
            collection.document(n.getId()).set(n.toFBDoc());
        }
    }

    public void deleteNote(int index) {
        if (getSourceType() == DATABASE.FIREBASE) {
            // Удалить документ с определённым идентификатором
            collection.document(data.get(index).getId()).delete();
        }
        data.remove(index);
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
        int month = Integer.parseInt(sDate[1]);
        int day = Integer.parseInt(sDate[0]);
        note.dateOfCreation = new GregorianCalendar(year, month, day);
        return note;
    }

    // Внутренний класс для ЗАМЕТКИ
    public static class Note {
        private String id; // идентификатор (для firebase)
        private String topic;
        private String description;
        private String author;
        private Calendar dateOfCreation;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Note() {
        }

        public Note(String topic, String description, String author, Calendar dateOfCreation) {
            this.topic = topic;
            this.description = description;
            this.author = author;
            this.dateOfCreation = dateOfCreation;
        }

        public String getTopic() {
            return topic;
        }

        public String getDescription() {
            return description;
        }

        public String getAuthor() {
            return author;
        }

        public Calendar getDateOfCreation() {
            return dateOfCreation;
        }

        public String getDateOfCreationAsString() {
            return new SimpleDateFormat("dd.MM.yyyy").format(dateOfCreation.getTime());
        }

        // FIREBASE only
        public Map<String, Object> toFBDoc() {
            Map<String, Object> answer = new HashMap<>();
            answer.put("topic", getTopic());
            answer.put("description", getDescription());
            answer.put("author", getAuthor());
            answer.put("dateofcreation", getDateOfCreationAsString());
            return answer;

        }
    }
}