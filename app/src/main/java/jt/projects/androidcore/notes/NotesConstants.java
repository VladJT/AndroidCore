package jt.projects.androidcore.notes;

import java.io.Serializable;

class NotesConstants {
    // индекс заметки, с которой мы работаем в данный момент, для отображения в NoteInfoFragment
    static final String CURRENT_NOTE_INDEX = "CURRENT_NOTE_INDEX";

    // ключ, по которому получаем данные об отредактированной или новой заметке в FragmentResultListener
    static final String FRAGMENT_RESULT_NOTES_DATA = "FRAGMENT_RESULT_NOTES_DATA";

    // индекс отредактированной или новой заметки для передачи события в другие фрагменты через FragmentResultListener
    static final String EDITED_NOTE_INDEX = "EDITED_NOTE_INDEX";
    static final String RESULT_EDIT = "RESULT_EDIT";


    // Имя настроек в SHARED_PREFERENCES
    static final String NAME_SHARED_PREFERENCES = "GB_NOTES";
    // тема приложения
    static final String APP_THEME_SHARED_PREFERENCES = "APP_THEME_SHARED_PREFERENCES";
    // user name в SHARED_PREFERENCES
    static final String ACCOUNT_USER_NAME_SHARED_PREFERENCES = "ACCOUNT_USER_NAME_SHARED_PREFERENCES";
    // user photo в SHARED_PREFERENCES
    static final String ACCOUNT_PHOTO_SHARED_PREFERENCES = "ACCOUNT_PHOTO_SHARED_PREFERENCES";

    // Имя SHARED_PREFERENCES для хранения данных заметок (вместо БД)
    static final String NAME_SHARED_PREFERENCES_DATA = "GB_NOTES_DATA";
    static final String NOTES_JSON_DATA = "JSON_DATA";


    // ID для PUSH NOTIFICATION
    static final String CHANNEL_ID = "CHANNEL_ID";
}

enum RESULT_EDIT_NOTE implements Serializable {
    ADD, DELETE, EDIT;
}