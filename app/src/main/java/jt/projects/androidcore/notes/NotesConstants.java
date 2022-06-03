package jt.projects.androidcore.notes;

class NotesConstants {
    // индекс заметки, с которой мы работаем в данный момент, для отображения в NoteInfoFragment
    static final String CURRENT_NOTE_INDEX = "CURRENT_NOTE_INDEX";

    // ключ, по которому получаем данные об отредактированной или новой заметке в FragmentResultListener
    static final String FRAGMENT_RESULT_NOTES_DATA = "FRAGMENT_RESULT_NOTES_DATA";

    // индекс отредактированной или новой заметки для передачи события в другие фрагменты через FragmentResultListener
    static final String EDITED_NOTE_INDEX = "EDITED_NOTE_INDEX";


    // Имя настроек в SHARED_PREFERENCES
    static final String NAME_SHARED_PREFERENCES = "GB_NOTES";
    // тема приложения
    static final String APP_THEME_SHARED_PREFERENCES = "APP_THEME_SHARED_PREFERENCES";
    // user name в SHARED_PREFERENCES
    static final String ACCOUNT_USER_NAME_SHARED_PREFERENCES = "ACCOUNT_USER_NAME_SHARED_PREFERENCES";
    // user photo в SHARED_PREFERENCES
    static final String ACCOUNT_PHOTO_SHARED_PREFERENCES = "ACCOUNT_PHOTO_SHARED_PREFERENCES";


    // ID для PUSH NOTIFICATION
    static final String CHANNEL_ID = "CHANNEL_ID";
}