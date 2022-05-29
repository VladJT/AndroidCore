package jt.projects.androidcore.notes;


class NotesConstants {
    // индекс заметки, с которой мы работаем в данный момент, для отображения в NoteInfoFragment
    static final String CURRENT_NOTE_INDEX = "CURRENT_NOTE_INDEX";

    // ключ, по которому получаем данные об отредактированной или новой заметке в FragmentResultListener
    static final String FRAGMENT_RESULT_NOTES_DATA = "FRAGMENT_RESULT_NOTES_DATA";

    // индекс отредактированной или новой заметки для передачи события в другие фрагменты через FragmentResultListener
    static final String EDITED_NOTE_INDEX = "EDITED_NOTE_INDEX";
}
