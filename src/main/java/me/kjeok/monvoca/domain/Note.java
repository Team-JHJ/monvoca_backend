package me.kjeok.monvoca.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Document(collection = "Note")
public class Note {
    @Id
    private String id;

    private String userName;
    private String title;
    private List<Detail> details;

    @Builder
    public Note(String userName, String title, List<Detail> details) {
        this.userName = userName;
        this.title = title;
        this.details = details;
    }

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Detail {
        private String _id = UUID.randomUUID().toString();
        private String word;
        private String meaning;
        private String example;
        private String memo;

        @Builder
        public Detail(String word, String meaning, String example, String memo) {
            this._id = UUID.randomUUID().toString();
            this.word = word;
            this.meaning = meaning;
            this.example = example;
            this.memo = memo;
        }
    }
}
