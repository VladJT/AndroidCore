package jt.projects.androidcore.examples.fragmentobservers;

// Метод updateText вызывается, когда происходит какое-то событие (изменился текст) в
//основном фрагменте.
public interface Observer {
    void updateText(String text);
}
