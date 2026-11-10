package com.example.obsidian.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppProperties {

    @Value("${app.bot-token:}")
    private String botToken;

    @Value("${app.web-app-url:}")
    private String webAppUrl;

    @Value("${app.notes-file:data/notes.txt}")
    private String notesFile;

    @Value("${app.links-file:data/links.txt}")
    private String linksFile;

    @Value("${app.dev-mode:true}")
    private boolean devMode;

    public AppProperties() {
    }

    public String getBotToken() {
        return botToken != null ? botToken.trim() : "";
    }

    public void setBotToken(String botToken) {
        this.botToken = botToken;
    }

    public String getWebAppUrl() {
        return webAppUrl != null ? webAppUrl.trim() : "";
    }

    public void setWebAppUrl(String webAppUrl) {
        this.webAppUrl = webAppUrl;
    }

    public String getNotesFile() {
        return notesFile;
    }

    public void setNotesFile(String notesFile) {
        this.notesFile = notesFile;
    }

    public String getLinksFile() {
        return linksFile;
    }

    public void setLinksFile(String linksFile) {
        this.linksFile = linksFile;
    }

    public boolean isDevMode() {
        return devMode;
    }

    public void setDevMode(boolean devMode) {
        this.devMode = devMode;
    }

    public boolean isBotEnabled() {
        return botToken != null && !botToken.trim().isEmpty();
    }
}
