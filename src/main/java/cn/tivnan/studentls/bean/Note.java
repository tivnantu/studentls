package cn.tivnan.studentls.bean;

public class Note {
    private Integer noteId;

    private String startTime;

    private String endTime;

    private String content;

    private Integer state;

    private Integer type;

    private Integer studentId;

    public Note() {
    }


    public Note(Integer noteId, String startTime, String endTime, String content, Integer state, Integer type, Integer studentId) {
        this.noteId = noteId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.content = content;
        this.state = state;
        this.type = type;
        this.studentId = studentId;
    }

    public Note(String startTime, String endTime, String content, Integer state, Integer type, Integer studentId) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.content = content;
        this.state = state;
        this.type = type;
        this.studentId = studentId;
    }

    @Override
    public String toString() {
        return "Note{" +
                "noteId=" + noteId +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", content='" + content + '\'' +
                ", state=" + state +
                ", type=" + type +
                ", studentId=" + studentId +
                '}';
    }


    public Integer getNoteId() {
        return noteId;
    }

    public void setNoteId(Integer noteId) {
        this.noteId = noteId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime == null ? null : startTime.trim();
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime == null ? null : endTime.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }
}