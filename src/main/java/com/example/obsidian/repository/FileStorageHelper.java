package com.example.obsidian.repository;

import com.example.obsidian.config.AppProperties;
import com.example.obsidian.model.Link;
import com.example.obsidian.model.Note;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class FileStorageHelper {

    private final AppProperties properties;

    public FileStorageHelper(AppProperties properties) {
        this.properties = properties;
    }

    public List<Note> loadNotesFromFile() {
        List<Note> notes = new ArrayList<>();
        File file = new File(properties.getNotesFile());
        if (!file.exists()) {
            return notes;
        }
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                String[] parts = line.split("\\|");
                if (parts.length >= 6) {
                    Note note = new Note();
                    note.setId(Long.parseLong(parts[0]));
                    note.setTitle(parts[1]);
                    note.setContent(parts[2]);
                    note.setX(Double.parseDouble(parts[3]));
                    note.setY(Double.parseDouble(parts[4]));
                    note.setColor(parts[5]);
                    notes.add(note);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return notes;
    }

    public void saveNotesToFile(Collection<Note> notes) {
        File file = new File(properties.getNotesFile());
        if (file.getParentFile() != null && !file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(file, false), StandardCharsets.UTF_8))) {
            for (Note note : notes) {
                String line = note.getId() + "|"
                        + note.getTitle() + "|"
                        + note.getContent() + "|"
                        + note.getX() + "|"
                        + note.getY() + "|"
                        + note.getColor();
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Link> loadLinksFromFile() {
        List<Link> links = new ArrayList<>();
        File file = new File(properties.getLinksFile());
        if (!file.exists()) {
            return links;
        }
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                String[] parts = line.split("\\|");
                if (parts.length >= 3) {
                    Link link = new Link();
                    link.setId(Long.parseLong(parts[0]));
                    link.setSourceNoteId(Long.parseLong(parts[1]));
                    link.setTargetNoteId(Long.parseLong(parts[2]));
                    links.add(link);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return links;
    }

    public void saveLinksToFile(Collection<Link> links) {
        File file = new File(properties.getLinksFile());
        if (file.getParentFile() != null && !file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(file, false), StandardCharsets.UTF_8))) {
            for (Link link : links) {
                String line = link.getId() + "|"
                        + link.getSourceNoteId() + "|"
                        + link.getTargetNoteId();
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
