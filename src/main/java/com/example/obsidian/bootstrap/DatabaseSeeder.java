package com.example.obsidian.bootstrap;

import com.example.obsidian.model.Link;
import com.example.obsidian.model.Note;
import com.example.obsidian.repository.LinkRepository;
import com.example.obsidian.repository.NoteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    private final NoteRepository noteRepository;
    private final LinkRepository linkRepository;
    private final NoteDataFactory noteDataFactory;
    private final LinkDataFactory linkDataFactory;

    public DatabaseSeeder(NoteRepository noteRepository,
                          LinkRepository linkRepository,
                          NoteDataFactory noteDataFactory,
                          LinkDataFactory linkDataFactory) {
        this.noteRepository = noteRepository;
        this.linkRepository = linkRepository;
        this.noteDataFactory = noteDataFactory;
        this.linkDataFactory = linkDataFactory;
    }

    @Override
    public void run(String... args) {
        if (noteRepository.count() > 0) {
            return;
        }

        List<Note> allNotes = noteDataFactory.createNotes();
        List<Note> savedNotes = new ArrayList<>();
        for (Note note : allNotes) {
            Note saved = noteRepository.save(note);
            savedNotes.add(saved);
        }

        List<Link> links = linkDataFactory.createLinks(savedNotes);
        for (Link link : links) {
            linkRepository.save(link);
        }
    }
}
