package com.example.obsidian.repository;

import com.example.obsidian.model.Link;

import java.util.List;

public interface LinkRepository {

    List<Link> findAll();

    Link findById(Long id);

    Link save(Link link);

    void deleteById(Long id);

    boolean existsById(Long id);
}
