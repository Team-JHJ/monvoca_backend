package me.kjeok.monvoca.dto;

import lombok.Getter;
import java.util.List;
import java.util.stream.Collectors;

import lombok.NoArgsConstructor;
import lombok.Setter;
import me.kjeok.monvoca.domain.Note;

@Getter
@Setter
@NoArgsConstructor
public class NoteResponse {
    private String id;
    private String title;

    public NoteResponse(Note note) {
        this.id = note.getId();
        this.title = note.getTitle();
    }
}
