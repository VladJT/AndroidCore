package jt.projects.androidcore.notes;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class NotesData {

    public static class Note {
        private String topic;
        private String description;
        private String author;
        private Calendar dateOfCreation;

        public Note(String topic, String description, String author, Calendar dateOfCreation) {
            this.topic = topic;
            this.description = description;
            this.author = author;
            this.dateOfCreation = dateOfCreation;
        }

        public String getTopic() {
            return topic;
        }

        public void setTopic(String topic) {
            this.topic = topic;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public Calendar getDateOfCreation() {
            return dateOfCreation;
        }

        public void setDateOfCreation(Calendar dateOfCreation) {
            this.dateOfCreation = dateOfCreation;
        }
    }

    private ArrayList<Note> data;

    public NotesData() {
        this.data = new ArrayList<>();
    }

    public void loadData() {
        // TODO сделать загрузку из какого-либо хранилища
        data.add(new Note("Заметка 1", "Давным-давно ...", "Влад", new GregorianCalendar(2021, 0, 25)));
        data.add(new Note("Заметка 2", "Высоко-высоко в горах ...", "Стас", new GregorianCalendar(2022, 2, 1)));
        data.add(new Note("Заметка 3", "В далекой галактике...", "Петр", Calendar.getInstance()));
    }

    public void saveData() {
        // TODO сделать сохранение данных в хранилище
    }

    public Note getNote(int index) {
        if (index >= 0 && index < data.size()) {
            return data.get(index);
        } else {
            return new Note("Тема Х", "Заметка не существует", "Автор не известен", Calendar.getInstance());
        }
    }

    public String[] getNotesList() {
        return data.stream().map(n -> n.topic).toArray(String[]::new);
    }

    public void addNote(Note note) {
        data.add(note);
    }

    public void editNote(Note note, int index) {
        data.set(index, note);
    }

}
