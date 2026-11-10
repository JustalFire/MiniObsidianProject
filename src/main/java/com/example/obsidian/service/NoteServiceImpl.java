package com.example.obsidian.service;

import com.example.obsidian.model.Note;
import com.example.obsidian.repository.NoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;

    public NoteServiceImpl(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @Override
    public List<Note> getAllNotes() {
        return noteRepository.findAll();
    }

    @Override
    public Note getNoteById(Long id) {
        return noteRepository.findById(id);
    }

    @Override
    public Note createNote(Note note) {
        return noteRepository.save(note);
    }

    @Override
    public Note updateNote(Long id, Note note) {
        Note existingNote = noteRepository.findById(id);
        existingNote.setTitle(note.getTitle());
        existingNote.setContent(note.getContent());
        existingNote.setColor(note.getColor());
        existingNote.setX(note.getX());
        existingNote.setY(note.getY());
        return noteRepository.update(existingNote);
    }

    @Override
    public void deleteNote(Long id) {
        noteRepository.deleteById(id);
    }
}
