package jt.projects.androidcore.notes.data;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Note {
    String id; // идентификатор (для firebase)
    String topic;
    String description;
    String author;
    Calendar dateOfCreation;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Note() {
    }

    public Note(String topic, String description, String author, Calendar dateOfCreation) {
        this.topic = topic;
        this.description = description;
        this.author = author;
        this.dateOfCreation = dateOfCreation;
    }

    public String getTopic() {
        return topic;
    }

    public String getDescription() {
        return description;
    }

    public String getAuthor() {
        return author;
    }

    public Calendar getDateOfCreation() {
        return dateOfCreation;
    }

    public String getDateOfCreationAsString() {
        return new SimpleDateFormat("dd.MM.yyyy").format(dateOfCreation.getTime());
    }

    // FIREBASE only
    public Map<String, Object> toFBDoc() {
        Map<String, Object> answer = new HashMap<>();
        answer.put("topic", getTopic());
        answer.put("description", getDescription());
        answer.put("author", getAuthor());
        answer.put("dateofcreation", getDateOfCreationAsString());
        return answer;
    }
}
