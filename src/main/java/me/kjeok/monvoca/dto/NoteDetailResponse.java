package me.kjeok.monvoca.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.kjeok.monvoca.domain.Note;

@Getter
@NoArgsConstructor
@Setter
public class NoteDetailResponse {
    private String id;
    private String word;
    private String meaning;
    private String example;
    private String memo;


    public NoteDetailResponse(Note.Detail detail) {
        this.id = detail.get_id();
        this.word = detail.getWord();
        this.meaning = detail.getMeaning();
        this.example = detail.getExample();
        this.memo = detail.getMemo();
    }
}
