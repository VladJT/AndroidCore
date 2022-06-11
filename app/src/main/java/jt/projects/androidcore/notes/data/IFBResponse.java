package jt.projects.androidcore.notes.data;

public interface IFBResponse {
    //Метод initialized() будет вызываться для Adapter (RecView),
    // когда данные будут загружены из Firebase
    void initialized();
}