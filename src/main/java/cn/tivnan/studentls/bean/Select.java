package cn.tivnan.studentls.bean;

public class Select {
    private Integer noteId;

    private Integer timeId;

    public Select() {
    }

    public Select(Integer noteId, Integer timeId) {
        this.noteId = noteId;
        this.timeId = timeId;
    }

    public Integer getNoteId() {
        return noteId;
    }

    public void setNoteId(Integer noteId) {
        this.noteId = noteId;
    }

    public Integer getTimeId() {
        return timeId;
    }

    public void setTimeId(Integer timeId) {
        this.timeId = timeId;
    }
}