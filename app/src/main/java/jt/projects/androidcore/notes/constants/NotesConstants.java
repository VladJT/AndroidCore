package jt.projects.androidcore.notes.constants;


public class NotesConstants {
    // индекс заметки, с которой мы работаем в данный момент, для отображения в NoteInfoFragment
    public static final String CURRENT_NOTE_INDEX = "CURRENT_NOTE_INDEX";

    // ключ, по которому получаем данные об отредактированной или новой заметке в FragmentResultListener
    public static final String FRAGMENT_RESULT_NOTES_DATA = "FRAGMENT_RESULT_NOTES_DATA";

    // ин декс отредактированной или новой заметки для передачи события в другие фрагменты через FragmentResultListener
    public static final String EDITED_NOTE_INDEX = "EDITED_NOTE_INDEX";
    public static final String RESULT_EDIT = "RESULT_EDIT";


    // Имя настроек в SHARED_PREFERENCES
    public static final String NAME_SHARED_PREFERENCES = "GB_NOTES";
    // тема приложения
    public static final String APP_THEME_SHARED_PREFERENCES = "APP_THEME_SHARED_PREFERENCES";
    // user name в SHARED_PREFERENCES
    public static final String ACCOUNT_USER_NAME_SHARED_PREFERENCES = "ACCOUNT_USER_NAME_SHARED_PREFERENCES";
    // user photo в SHARED_PREFERENCES
    public static final String ACCOUNT_PHOTO_SHARED_PREFERENCES = "ACCOUNT_PHOTO_SHARED_PREFERENCES";
    // source в SHARED_PREFERENCES
    public static final String DB_SOURCE_SHARED_PREFERENCES = "DB_SOURCE_SHARED_PREFERENCES";

    // Имя SHARED_PREFERENCES для хранения данных заметок (вместо БД)
    public static final String NAME_SHARED_PREFERENCES_DATA = "GB_NOTES_DATA";
    public static String NOTES_JSON_DATA = "JSON_DATA";

    // ID для PUSH NOTIFICATION
    public static final String CHANNEL_ID = "CHANNEL_ID";
}