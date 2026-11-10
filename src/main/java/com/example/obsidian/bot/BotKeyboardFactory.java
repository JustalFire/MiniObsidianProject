package com.example.obsidian.bot;

import com.example.obsidian.config.AppProperties;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.menubutton.SetChatMenuButton;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.menubutton.MenuButtonWebApp;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import org.telegram.telegrambots.meta.api.objects.webapp.WebAppInfo;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.ArrayList;
import java.util.List;

@Component
public class BotKeyboardFactory {

    private final AppProperties properties;

    public BotKeyboardFactory(AppProperties properties) {
        this.properties = properties;
    }

    public boolean isWebAppAvailable() {
        String url = properties.getWebAppUrl();
        return url != null && url.startsWith("https://");
    }

    private String buildWebAppUrl() {
        String base = properties.getWebAppUrl();
        if (base.contains("?")) {
            return base + "&v=3";
        }
        if (base.endsWith("/")) {
            return base + "?v=3";
        }
        return base + "/?v=3";
    }

    public InlineKeyboardMarkup createOpenAppKeyboard() {
        if (!isWebAppAvailable()) {
            return null;
        }
        InlineKeyboardButton button = InlineKeyboardButton.builder()
                .text("Открыть Mini Obsidian")
                .webApp(new WebAppInfo(buildWebAppUrl()))
                .build();
        InlineKeyboardRow row = new InlineKeyboardRow(button);
        return InlineKeyboardMarkup.builder()
                .keyboardRow(row)
                .build();
    }

    public void registerCommandsAndMenu(TelegramClient telegram) {
        try {
            List<BotCommand> commands = new ArrayList<>();
            commands.add(new BotCommand("start", "Открыть меню и WebApp"));
            commands.add(new BotCommand("notes", "Список всех заметок"));
            commands.add(new BotCommand("links", "Список связей"));
            commands.add(new BotCommand("help", "Помощь по командам"));
            telegram.execute(SetMyCommands.builder().commands(commands).build());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

        if (!isWebAppAvailable()) {
            return;
        }

        try {
            MenuButtonWebApp menuButton = MenuButtonWebApp.builder()
                    .text("Заметки")
                    .webAppInfo(new WebAppInfo(buildWebAppUrl()))
                    .build();
            telegram.execute(SetChatMenuButton.builder().menuButton(menuButton).build());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
