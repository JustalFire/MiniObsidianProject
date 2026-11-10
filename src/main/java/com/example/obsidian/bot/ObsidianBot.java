package com.example.obsidian.bot;

import com.example.obsidian.config.AppProperties;
import com.example.obsidian.model.Link;
import com.example.obsidian.model.Note;
import com.example.obsidian.service.LinkService;
import com.example.obsidian.service.NoteService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Component
public class ObsidianBot implements SpringLongPollingBot, LongPollingSingleThreadUpdateConsumer {

    private final AppProperties properties;
    private final TelegramClient telegram;
    private final NoteService noteService;
    private final LinkService linkService;
    private final BotFormatter formatter;
    private final BotKeyboardFactory keyboardFactory;

    public ObsidianBot(AppProperties properties,
                       TelegramClient telegram,
                       NoteService noteService,
                       LinkService linkService,
                       BotFormatter formatter,
                       BotKeyboardFactory keyboardFactory) {
        this.properties = properties;
        this.telegram = telegram;
        this.noteService = noteService;
        this.linkService = linkService;
        this.formatter = formatter;
        this.keyboardFactory = keyboardFactory;
        this.keyboardFactory.registerCommandsAndMenu(telegram);
    }

    @Override
    public String getBotToken() {
        return properties.getBotToken();
    }

    @Override
    public LongPollingUpdateConsumer getUpdatesConsumer() {
        return this;
    }

    @Override
    public void consume(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()) {
            return;
        }
        Message message = update.getMessage();
        long chatId = message.getChatId();
        String text = message.getText().trim();
        String firstName = message.getFrom() != null ? message.getFrom().getFirstName() : "друг";

        handleCommand(chatId, firstName, text);
    }

    private void handleCommand(long chatId, String firstName, String text) {
        String command = text.split(" ")[0];
        InlineKeyboardMarkup appKeyboard = keyboardFactory.createOpenAppKeyboard();

        if ("/start".equals(command)) {
            send(chatId, formatter.formatWelcome(firstName), appKeyboard);
        } else if ("/help".equals(command)) {
            send(chatId, formatter.formatHelp(), appKeyboard);
        } else if ("/notes".equals(command)) {
            send(chatId, formatter.formatNotesList(noteService.getAllNotes()), appKeyboard);
        } else if ("/links".equals(command)) {
            send(chatId, formatter.formatLinksList(linkService.getAllLinks()), appKeyboard);
        } else if ("/note".equals(command)) {
            long id = Long.parseLong(text.substring(command.length()).trim());
            Note note = noteService.getNoteById(id);
            send(chatId, formatter.formatNoteDetail(note), appKeyboard);
        } else if ("/add".equals(command)) {
            handleAddNote(chatId, text.substring(command.length()).trim(), appKeyboard);
        } else if ("/connect".equals(command)) {
            handleConnectNotes(chatId, text.substring(command.length()).trim(), appKeyboard);
        } else if ("/delete".equals(command)) {
            long id = Long.parseLong(text.substring(command.length()).trim());
            noteService.deleteNote(id);
            send(chatId, "Заметка #" + id + " удалена из файла.", appKeyboard);
        }
    }

    private void handleAddNote(long chatId, String args, InlineKeyboardMarkup keyboard) {
        String[] parts = args.split("\\|", 2);
        String title = parts[0].trim();
        String content = parts.length > 1 ? parts[1].trim() : "";
        Note note = new Note();
        note.setTitle(title);
        note.setContent(content);
        note.setX(200 + (Math.random() * 600));
        note.setY(200 + (Math.random() * 400));
        note.setColor("#dcdcdc");
        Note created = noteService.createNote(note);
        send(chatId, "Заметка <b>#" + created.getId() + "</b> сохранена в файл!", keyboard);
    }

    private void handleConnectNotes(long chatId, String args, InlineKeyboardMarkup keyboard) {
        String[] parts = args.split(" ");
        long sourceId = Long.parseLong(parts[0]);
        long targetId = Long.parseLong(parts[1]);
        Link link = new Link();
        link.setSourceNoteId(sourceId);
        link.setTargetNoteId(targetId);
        Link created = linkService.createLink(link);
        send(chatId, "Связь <b>#" + created.getId() + "</b> между #" + sourceId + " и #" + targetId + " создана!", keyboard);
    }

    private void send(long chatId, String html, InlineKeyboardMarkup keyboard) {
        try {
            SendMessage msg = SendMessage.builder()
                    .chatId(String.valueOf(chatId))
                    .text(html)
                    .parseMode("HTML")
                    .replyMarkup(keyboard)
                    .build();
            telegram.execute(msg);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
