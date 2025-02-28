package me.kjeok.monvoca.repository;

import me.kjeok.monvoca.domain.Note;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface NoteRepository extends MongoRepository<Note, String> {
    Optional<Note> findByIdAndUserName(String noteId, String userName);
    List<Note> findByUserName(String userName);
    List<Note> findByUserNameAndTitle(String userName, String title);
    void deleteByIdAndUserName(String id, String userName);


    // Note의 id와 Detail의 id가 일치하는 Detail을 삭제하는 커스텀 쿼리
    @Query(value = "{ 'id': ?0, 'details.id': ?1 }", delete = true)
    void deleteDetailByNoteIdAndDetailId(String noteId, String detailId);
}
