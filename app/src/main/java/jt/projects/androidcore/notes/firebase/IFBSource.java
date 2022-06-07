package jt.projects.androidcore.notes.firebase;

import jt.projects.androidcore.notes.NotesData;
import jt.projects.androidcore.notes.NotesData.*;


public interface IFBSource {
    IFBSource init(IFBResponse response);

    Note getNote(int position);
    int size();
    void deleteNote(int position);
    void updateNote(int position, Note note);
    void addNote(Note note);
    void clearNote();
}
