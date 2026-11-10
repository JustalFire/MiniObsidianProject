package com.example.obsidian.model;

public class Link {

    private Long id;

    private Long sourceNoteId;

    private Long targetNoteId;

    public Link() {
    }

    public Link(Long id, Long sourceNoteId, Long targetNoteId) {
        this.id = id;
        this.sourceNoteId = sourceNoteId;
        this.targetNoteId = targetNoteId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSourceNoteId() {
        return sourceNoteId;
    }

    public void setSourceNoteId(Long sourceNoteId) {
        this.sourceNoteId = sourceNoteId;
    }

    public Long getTargetNoteId() {
        return targetNoteId;
    }

    public void setTargetNoteId(Long targetNoteId) {
        this.targetNoteId = targetNoteId;
    }

    @Override
    public String toString() {
        return "Link{"
                + "id=" + id
                + ", sourceNoteId=" + sourceNoteId
                + ", targetNoteId=" + targetNoteId
                + '}';
    }
}
