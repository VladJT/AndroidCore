package jt.projects.androidcore.notes;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
    }

    private ArrayList<Note> data;

    public NotesData() {
        this.data = new ArrayList<>();
    }

    public void loadData() {
        // TODO сделать загрузку из какого-либо хранилища
        data.add(new Note("Заметка 1", "обработку надо создавать при создании элемента, то есть или на\n" +
                "onCreateViewHolder, или в самом ViewHolder. Однако метод onCreateViewHolder отвечает за\n" +
                "предоставление View элемента вновь созданному ViewHolder, не стоит перегружать его ещё и\n" +
                "созданием обработчиков. ViewHolder же сам знает всё про свои элементы, поэтому и слушателей\n" +
                "будем организовывать во ViewHolder. Говоря формальным языком, мы инкапсулируем создание\n" +
                "обработчиков элементов внутри ViewHolder", "Влад", new GregorianCalendar(2021, 0, 25)));
        data.add(new Note("Заметка 2", "Высоко-высоко в горах ...", "Стас", new GregorianCalendar(2022, 2, 1)));
        data.add(new Note("Заметка 3", "В далекой галактике...", "Петр", Calendar.getInstance()));
        data.add(new Note("Заметка 4", "Использование SimpleDateFormat для форматирование ввода-вывода даты в Java. ... SimpleDateFormat является подклассом DateFormat, который позволяет форматировать ввод-вывод даты и времени в рамках... ", "Влад", new GregorianCalendar(2021, 0, 25)));
        data.add(new Note("Заметка 5", "Высоко-высоко в горах ...", "Стас", new GregorianCalendar(2022, 2, 1)));
        data.add(new Note("Заметка 6", "В далекой галактике...", "Петр", Calendar.getInstance()));
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

    public int getSize() {
        return data.size();
    }

    public void addNote(Note note) {
        data.add(note);
    }

    public void editNote(Note note, int index) {
        data.set(index, note);
    }

    public void deleteNote(int index) {
        data.remove(index);
    }
}