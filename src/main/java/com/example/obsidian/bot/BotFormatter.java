package com.example.obsidian.bot;

import com.example.obsidian.model.Link;
import com.example.obsidian.model.Note;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BotFormatter {

    public String formatWelcome(String firstName) {
        String name = escape(firstName != null ? firstName : "друг");
        return "Привет, " + name + "!\n"
                + "Это <b>Mini Obsidian</b> — холст заметок со связями.\n\n"
                + "Нажми кнопку ниже, чтобы открыть холст (WebApp), или используй команды:\n"
                + "/notes — список заметок\n"
                + "/note 1 — открыть заметку по ID\n"
                + "/add Заголовок | Текст — создать заметку\n"
                + "/connect 1 2 — соединить две заметки\n"
                + "/links — список связей\n"
                + "/delete 1 — удалить заметку\n"
                + "/help — помощь";
    }

    public String formatHelp() {
        return "<b>Команды бота:</b>\n"
                + "/start — главное меню и кнопка WebApp\n"
                + "/notes — показать список заметок\n"
                + "/note ID — показать полное описание заметки\n"
                + "/add Заголовок | Описание — добавить новую заметку\n"
                + "/connect ID1 ID2 — соединить две заметки линией\n"
                + "/links — показать все связи между заметками\n"
                + "/delete ID — удалить заметку по ID";
    }

    public String formatNotesList(List<Note> notes) {
        StringBuilder sb = new StringBuilder();
        sb.append("<b>Список заметок (всего ").append(notes.size()).append("):</b>\n\n");
        for (Note n : notes) {
            sb.append("<b>#").append(n.getId()).append("</b> — ")
                    .append(escape(n.getTitle())).append("\n");
        }
        return sb.toString();
    }

    public String formatNoteDetail(Note note) {
        return "<b>Заметка #" + note.getId() + ": " + escape(note.getTitle()) + "</b>\n\n"
                + escape(note.getContent()) + "\n\n"
                + "Координаты: X=" + (int) note.getX() + ", Y=" + (int) note.getY();
    }

    public String formatLinksList(List<Link> links) {
        StringBuilder sb = new StringBuilder();
        sb.append("<b>Связи между заметками (всего ").append(links.size()).append("):</b>\n\n");
        for (Link link : links) {
            sb.append("Связь #").append(link.getId())
                    .append(": Заметка <b>#").append(link.getSourceNoteId()).append("</b>")
                    .append("  --->  ")
                    .append("Заметка <b>#").append(link.getTargetNoteId()).append("</b>\n");
        }
        return sb.toString();
    }

    public static String escape(String s) {
        if (s == null) {
            return "";
        }
        return s.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;");
    }
}
