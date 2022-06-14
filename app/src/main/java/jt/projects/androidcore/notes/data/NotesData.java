package jt.projects.androidcore.notes.data;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.GregorianCalendar;

import jt.projects.androidcore.notes.NotesSharedPreferences;
import jt.projects.androidcore.notes.constants.DATABASE;

public abstract class NotesData {
    static NotesData notesData = null;

    public static NotesData getInstance() {
        if (notesData == null) {
            createInstance();
        } else {
            if ((notesData instanceof NotesDataFirebase && NotesSharedPreferences.getInstance().getDBSource() != DATABASE.FIREBASE) ||
                    (notesData instanceof NotesDataSharedPref && NotesSharedPreferences.getInstance().getDBSource() != DATABASE.SHARED_PREF) ||
                    (notesData instanceof NotesDataStore && NotesSharedPreferences.getInstance().getDBSource() != DATABASE.DATASTORE))
            {
                createInstance();
            }
        }
        return notesData;
    }

    private static void createInstance() {
        if (NotesSharedPreferences.getInstance().getDBSource() == DATABASE.SHARED_PREF) {
            notesData = new NotesDataSharedPref();
        }
        if (NotesSharedPreferences.getInstance().getDBSource() == DATABASE.FIREBASE) {
            notesData = new NotesDataFirebase();
        }
        if (NotesSharedPreferences.getInstance().getDBSource() == DATABASE.DATASTORE) {
            notesData = new NotesDataStore();
        }
    }

    // ТИП БАЗЫ ДАННЫХ
    public DATABASE getSourceType() {
        return NotesSharedPreferences.getInstance().getDBSource();
    }

    protected ArrayList<Note> data;
    protected static IFBResponse response;

    protected NotesData() {
        this.data = new ArrayList<>();
    }

    public void setResponse(IFBResponse newResponse) {
        response = newResponse;
    }

    public abstract void loadData();

    public abstract void saveData();

    protected void sortByDate(ArrayList<Note> arr) {
        arr.sort(new Comparator<Note>() {
            @Override
            public int compare(Note o1, Note o2) {
                if (o1.dateOfCreation.getTimeInMillis() > o2.dateOfCreation.getTimeInMillis())
                    return 1;
                if (o1.dateOfCreation.getTimeInMillis() < o2.dateOfCreation.getTimeInMillis())
                    return -1;
                return 0;
            }
        });
    }

    void addTestData() {
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
    }

    public void editNote(Note editedNote, int index) {
        editedNote.setId(data.get(index).getId());
        data.set(index, editedNote);
    }

    public void deleteNote(int index) {
        data.remove(index);
    }
}