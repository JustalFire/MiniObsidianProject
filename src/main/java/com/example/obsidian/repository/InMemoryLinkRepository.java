package com.example.obsidian.repository;

import com.example.obsidian.model.Link;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class InMemoryLinkRepository implements LinkRepository {

    private final Map<Long, Link> storage = new HashMap<>();
    private long currentId = 1;
    private final FileStorageHelper fileStorageHelper;

    public InMemoryLinkRepository(FileStorageHelper fileStorageHelper) {
        this.fileStorageHelper = fileStorageHelper;
        List<Link> loaded = fileStorageHelper.loadLinksFromFile();
        for (Link link : loaded) {
            storage.put(link.getId(), link);
            if (link.getId() >= currentId) {
                currentId = link.getId() + 1;
            }
        }
    }

    @Override
    public List<Link> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public Link findById(Long id) {
        return storage.get(id);
    }

    @Override
    public Link save(Link link) {
        if (link.getId() == null) {
            link.setId(currentId++);
        }
        storage.put(link.getId(), link);
        fileStorageHelper.saveLinksToFile(storage.values());
        return link;
    }

    @Override
    public void deleteById(Long id) {
        storage.remove(id);
        fileStorageHelper.saveLinksToFile(storage.values());
    }

    @Override
    public boolean existsById(Long id) {
        return storage.containsKey(id);
    }
}
