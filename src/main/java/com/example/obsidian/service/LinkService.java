package com.example.obsidian.service;

import com.example.obsidian.model.Link;

import java.util.List;

public interface LinkService {

    List<Link> getAllLinks();

    Link createLink(Link link);

    void deleteLink(Long id);
}
