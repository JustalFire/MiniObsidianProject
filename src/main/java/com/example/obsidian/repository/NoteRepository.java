package com.example.obsidian.repository;

import com.example.obsidian.model.Note;

import java.util.List;

public interface NoteRepository {

    List<Note> findAll();

    Note findById(Long id);

    Note save(Note note);

    Note update(Note note);

    void deleteById(Long id);

    boolean existsById(Long id);

    long count();
}
