package me.kjeok.monvoca.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import me.kjeok.monvoca.dto.*;
import me.kjeok.monvoca.service.NoteService;
import me.kjeok.monvoca.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor

@Tag(name = "Voca Controller", description = "APIs for managing vocabulary and users")
public class VocaController {
    private final UserService userService;
    private final NoteService noteService;

    // POST

    @PostMapping("/createUser")
    @Operation(summary = "Create User",
            description = "request: userName, password, email")
    public ResponseEntity<String> createUser(@RequestBody UserRequest userRequest) {
        UserResponse userResponse = userService.createUser(userRequest);
        return ResponseEntity.ok("User created: " + userResponse.getUserName());
    }

    @PostMapping("/createNote")
    @Operation(summary = "Create note",
            description = "request: userName, title")
    public ResponseEntity<String> createNote(@RequestBody NoteCreateRequest noteCreateRequest) {
        NoteResponse noteResponse = noteService.createNote(noteCreateRequest);
        return ResponseEntity.ok("Note created: " + noteResponse.getTitle());
    }

    // Detail 추가 API
    @PostMapping("/createDetail/{userName}/{noteId}")
    public ResponseEntity<NoteResponse> addDetailToNote(
            @PathVariable("userName") String userName,
            @PathVariable("noteId") String noteId,
            @RequestBody CreateDetailRequest createDetailRequest) {

        NoteResponse noteResponse = noteService.addDetailToNote(userName, noteId, createDetailRequest);
        return ResponseEntity.ok(noteResponse);
    }


    // GET

    @GetMapping("/getUser/{userName}")
    @Operation(summary = "Get One User", description = "parameter: userName")
    public ResponseEntity<UserResponse> findUserById(@PathVariable("userName") String userName) {
        UserResponse user = userService.findUserByUserName(userName);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/getNotes/{userName}")
    @Operation(summary = "Get User's Note",
            description = "parameter: userName")
    public ResponseEntity<List<NoteResponse>> getNotes(@PathVariable("userName") String userName) {
        List<NoteResponse> notes = noteService.findByUserId(userName);
        return ResponseEntity.ok(notes);
    }

    @GetMapping("/getDetails/{userName}/{noteId}")
    @Operation(summary = "Get One UserNote's Details",
            description = "parameter: userId, title")
    public ResponseEntity<List<NoteDetailResponse>> getDetails(@PathVariable("userName") String userName ,@PathVariable("noteId") String noteId) {
        List<NoteDetailResponse> noteDetails = noteService.findByUserNameAndId(userName, noteId);
        return ResponseEntity.ok(noteDetails);
    }

    // PUT

    @PutMapping("/updateUser/{userName}")
    public ResponseEntity<String> updateUser(@RequestBody UserUpdateRequest userUpdateRequest, @PathVariable("userName") String userName) {
        userService.updateUser(userUpdateRequest, userName);
        return ResponseEntity.ok("User updated success");
    }
    @PutMapping("/updateNote/{userName}/{noteId}")
    public ResponseEntity<String> updateNote(
            @PathVariable String userName,
            @PathVariable String noteId,
            @RequestBody NoteTitleUpdateRequest noteTitleUpdateRequest) {
        noteService.updateNote(userName, noteId, noteTitleUpdateRequest);
        return ResponseEntity.ok("Note updated success");
    }
    @PutMapping("/updateDetail/{userName}/{noteId}/{detailId}")
    public ResponseEntity<String> updateDetail(
            @PathVariable String userName,
            @PathVariable String noteId,
            @PathVariable String detailId,
            @RequestBody NoteDetailRequest noteDetailRequest) {
        noteService.updateDetail(userName, noteId, detailId, noteDetailRequest);
        return ResponseEntity.ok("Detail updated success");
    }


    // DELETE

    @DeleteMapping("/deleteUser/{userName}")
    public ResponseEntity<String> deleteUser(@PathVariable("userName") String userName) {
        userService.deleteUser(userName);
        return ResponseEntity.ok("User deleted success");
    }

    @DeleteMapping("/deleteNote/{userName}/{noteId}")
    public ResponseEntity<String> deleteNote(@PathVariable("userName") String userName ,@PathVariable("noteId") String noteId) {
        noteService.deleteNote(noteId, userName);
        return ResponseEntity.ok("Note deleted success");
    }

    @DeleteMapping("/deleteDetail/{userName}/{noteId}/{detailId}")
    public ResponseEntity<String> deleteDetail(@PathVariable("userName") String userName ,@PathVariable("noteId") String noteId, @PathVariable("detailId") String detailId) {
        noteService.deleteDetail(noteId, detailId);
        return ResponseEntity.ok("Detail deleted success");
    }

}
