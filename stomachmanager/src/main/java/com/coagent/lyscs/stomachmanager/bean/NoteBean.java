package com.coagent.lyscs.stomachmanager.bean;

/**
 * Created by lyscs on 2017/8/27.
 * 用于记录日记
 */
public class NoteBean {

    private String noteTitle;
    private String noteDate;
    private String noteType;
    private String noteContent;

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteDate() {
        return noteDate;
    }

    public void setNoteDate(String noteDate) {
        this.noteDate = noteDate;
    }

    public String getNoteType() {
        return noteType;
    }

    public void setNoteType(String noteType) {
        this.noteType = noteType;
    }

    public String getNoteContent() {
        return noteContent;
    }

    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
    }
}
