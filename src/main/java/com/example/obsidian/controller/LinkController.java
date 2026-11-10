package com.example.obsidian.controller;

import com.example.obsidian.model.Link;
import com.example.obsidian.service.LinkService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/links")
public class LinkController {

    private final LinkService linkService;

    public LinkController(LinkService linkService) {
        this.linkService = linkService;
    }

    @GetMapping
    public List<Link> getAllLinks() {
        return linkService.getAllLinks();
    }

    @PostMapping
    public Link createLink(@RequestBody Link link) {
        return linkService.createLink(link);
    }

    @DeleteMapping("/{id}")
    public void deleteLink(@PathVariable Long id) {
        linkService.deleteLink(id);
    }
}
