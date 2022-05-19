package jt.projects.androidcore.examples.fragmentobservers;

// Метод updateText вызывается, когда происходит какое-то событие (изменился текст) в
//основном фрагменте.
interface Observer {
    void updateText(String text);
}
