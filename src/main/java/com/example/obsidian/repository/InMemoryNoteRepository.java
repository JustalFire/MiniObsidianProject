package com.example.obsidian.repository;

import com.example.obsidian.model.Note;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class InMemoryNoteRepository implements NoteRepository {

    private final Map<Long, Note> storage = new HashMap<>();
    private long currentId = 1;
    private final FileStorageHelper fileStorageHelper;

    public InMemoryNoteRepository(FileStorageHelper fileStorageHelper) {
        this.fileStorageHelper = fileStorageHelper;
        List<Note> loaded = fileStorageHelper.loadNotesFromFile();
        for (Note note : loaded) {
            storage.put(note.getId(), note);
            if (note.getId() >= currentId) {
                currentId = note.getId() + 1;
            }
        }
    }

    @Override
    public List<Note> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public Note findById(Long id) {
        return storage.get(id);
    }

    @Override
    public Note save(Note note) {
        if (note.getId() == null) {
            note.setId(currentId++);
        }
        storage.put(note.getId(), note);
        fileStorageHelper.saveNotesToFile(storage.values());
        return note;
    }

    @Override
    public Note update(Note note) {
        storage.put(note.getId(), note);
        fileStorageHelper.saveNotesToFile(storage.values());
        return note;
    }

    @Override
    public void deleteById(Long id) {
        storage.remove(id);
        fileStorageHelper.saveNotesToFile(storage.values());
    }

    @Override
    public boolean existsById(Long id) {
        return storage.containsKey(id);
    }

    @Override
    public long count() {
        return storage.size();
    }
}
