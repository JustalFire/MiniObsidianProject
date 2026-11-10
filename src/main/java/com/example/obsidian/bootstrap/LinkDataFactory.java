package com.example.obsidian.bootstrap;

import com.example.obsidian.model.Link;
import com.example.obsidian.model.Note;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LinkDataFactory {

    public List<Link> createLinks(List<Note> savedNotes) {
        List<Link> links = new ArrayList<>();

        links.add(createLink(savedNotes, 0, 1));
        links.add(createLink(savedNotes, 1, 2));
        links.add(createLink(savedNotes, 1, 11));
        links.add(createLink(savedNotes, 2, 3));
        links.add(createLink(savedNotes, 2, 5));
        links.add(createLink(savedNotes, 3, 4));
        links.add(createLink(savedNotes, 4, 6));
        links.add(createLink(savedNotes, 4, 30));
        links.add(createLink(savedNotes, 5, 22));
        links.add(createLink(savedNotes, 6, 30));
        links.add(createLink(savedNotes, 7, 11));
        links.add(createLink(savedNotes, 7, 27));
        links.add(createLink(savedNotes, 7, 28));
        links.add(createLink(savedNotes, 8, 9));
        links.add(createLink(savedNotes, 8, 14));
        links.add(createLink(savedNotes, 8, 19));
        links.add(createLink(savedNotes, 9, 14));
        links.add(createLink(savedNotes, 10, 4));
        links.add(createLink(savedNotes, 10, 22));
        links.add(createLink(savedNotes, 12, 16));
        links.add(createLink(savedNotes, 12, 17));
        links.add(createLink(savedNotes, 13, 19));
        links.add(createLink(savedNotes, 13, 20));
        links.add(createLink(savedNotes, 15, 16));
        links.add(createLink(savedNotes, 15, 18));
        links.add(createLink(savedNotes, 17, 29));
        links.add(createLink(savedNotes, 18, 19));
        links.add(createLink(savedNotes, 21, 23));
        links.add(createLink(savedNotes, 22, 23));
        links.add(createLink(savedNotes, 24, 25));
        links.add(createLink(savedNotes, 26, 27));

        return links;
    }

    private Link createLink(List<Note> savedNotes, int sourceIndex, int targetIndex) {
        Link link = new Link();
        link.setSourceNoteId(savedNotes.get(sourceIndex).getId());
        link.setTargetNoteId(savedNotes.get(targetIndex).getId());
        return link;
    }
}
