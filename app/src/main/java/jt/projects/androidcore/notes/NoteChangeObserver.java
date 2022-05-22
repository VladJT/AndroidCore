package jt.projects.androidcore.notes;


interface NoteChangeObserver {
    void changeNote(NotesData.Note note, int index);
}
