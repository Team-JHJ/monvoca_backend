package me.kjeok.monvoca.service;

import lombok.RequiredArgsConstructor;
import me.kjeok.monvoca.domain.Note;
import me.kjeok.monvoca.dto.*;
import me.kjeok.monvoca.repository.NoteRepository;
import me.kjeok.monvoca.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoteService {
    private final NoteRepository noteRepository;
    private final UserRepository userRepository;

    //POST
    public NoteResponse createNote(NoteCreateRequest noteCreateRequest) {
        Note note = Note.builder()
                .userName(noteCreateRequest.getUserName())
                .title(noteCreateRequest.getTitle())
                .details(List.of())
                .build();

        Note savedNote = noteRepository.save(note);
        return new NoteResponse(savedNote);
    }
    // POST
    public NoteResponse addDetailToNote(String userName, String noteId, CreateDetailRequest createDetailRequest) {
        // Note 문서를 id와 userId로 검색
        Optional<Note> optionalNote = noteRepository.findByIdAndUserName(noteId, userName);
        if (optionalNote.isEmpty()) {
            throw new RuntimeException("Note not found");
        }

        Note note = optionalNote.get();

        // 새로운 Detail 객체 생성
        Note.Detail newDetail = Note.Detail.builder()
                .word(createDetailRequest.getWord())
                .meaning(createDetailRequest.getMeaning())
                .example(createDetailRequest.getExample())
                .memo(createDetailRequest.getMemo())
                .build();

        // details 리스트에 새 Detail 추가
        note.getDetails().add(newDetail);

        // 업데이트된 Note를 저장
        Note updatedNote = noteRepository.save(note);
        return new NoteResponse(updatedNote);
    }


    // GET
    public List<NoteResponse> findByUserId(String userName) {
        return noteRepository.findByUserName(userName)
                .stream()
                .map(NoteResponse::new)
                .collect(Collectors.toList());
    }
    // GET
    public List<NoteDetailResponse> findByUserIdAndTitle(String userName, String title) {
        return noteRepository.findByUserNameAndTitle(userName, title)
                .stream()
                .flatMap(note -> note.getDetails().stream())
                .map(NoteDetailResponse::new)
                .collect(Collectors.toList());
    }

    public List<NoteDetailResponse> findByUserNameAndId(String userName, String noteId) {
        return noteRepository.findByIdAndUserName(noteId, userName)
                .map(note -> note.getDetails().stream() // Note의 details 리스트를 스트림으로 변환
                        .map(NoteDetailResponse::new) // 각 Detail 객체를 NoteDetailResponse로 변환
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList()); // Note가 없으면 빈 리스트 반환
    }



    // UPDATE
    public void updateNote(String userName, String noteId, NoteTitleUpdateRequest noteTitleUpdateRequest) {
        Note note = noteRepository.findByIdAndUserName(noteId, userName)
                .orElseThrow(() -> new RuntimeException("Note not found")); // Note가 없을 경우 예외 발생

        note.setTitle(noteTitleUpdateRequest.getTitle());
        noteRepository.save(note);
    }


    // UPDATE
    public void updateDetail(String userName, String noteId, String detailId ,NoteDetailRequest detailRequest) {
        Optional<Note> optionalNote = noteRepository.findByIdAndUserName(noteId, userName);

        if(optionalNote.isEmpty()) {
            throw new RuntimeException("Note not found");
        }

        Note note = optionalNote.get();

        Note.Detail detailToUpdate = note.getDetails().stream()
                .filter(detail -> detail.get_id().equals(detailId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Detail not found"));

        if (detailRequest.getWord() != null) {
            detailToUpdate.setWord(detailRequest.getWord());
        }
        if (detailRequest.getMeaning() != null) {
            detailToUpdate.setMeaning(detailRequest.getMeaning());
        }
        if (detailRequest.getExample() != null) {
            detailToUpdate.setExample(detailRequest.getExample());
        }
        if (detailRequest.getMemo() != null) {
            detailToUpdate.setMemo(detailRequest.getMemo());
        }

        noteRepository.save(note);
    }


    // DELETE
    public void deleteNote(String noteId, String userName) {
        noteRepository.deleteByIdAndUserName(noteId, userName);
    }

    /*public void deleteDetail(String noteId, String detailId) {
        noteRepository.deleteDetailByNoteIdAndDetailId(noteId, detailId);
    }*/


    public void deleteDetail(String noteId, String detailId) {
        // Note 객체를 noteId로 조회
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new RuntimeException("Note not found"));

        // Detail 리스트에서 detailId에 해당하는 Detail 객체를 제거
        boolean removed = note.getDetails().removeIf(detail -> detail.get_id().equals(detailId));

        // Detail 객체가 제거되었을 때만 Note 객체를 저장
        if (removed) {
            noteRepository.save(note);
        } else {
            throw new RuntimeException("Detail not found");
        }
    }
}
