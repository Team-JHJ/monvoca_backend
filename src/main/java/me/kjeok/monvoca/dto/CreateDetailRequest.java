package me.kjeok.monvoca.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.kjeok.monvoca.domain.Note;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateDetailRequest {
    private String word;    // 단어
    private String meaning; // 의미
    private String example; // 예문
    private String memo;    // 메모
}

