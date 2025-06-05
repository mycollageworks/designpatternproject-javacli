package model;

public class Note {
    public int id;
    public String date;
    public String content;

    @Override
    public String toString() {
        return "Note #" + id + " [" + date + "]: " + content;
    }
}
