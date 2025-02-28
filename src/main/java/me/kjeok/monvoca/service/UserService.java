package me.kjeok.monvoca.service;

import lombok.RequiredArgsConstructor;
import me.kjeok.monvoca.domain.User;
import me.kjeok.monvoca.dto.UserRequest;
import me.kjeok.monvoca.dto.UserResponse;
import me.kjeok.monvoca.dto.UserUpdateRequest;
import me.kjeok.monvoca.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    // POST
    public UserResponse createUser(UserRequest userRequest) {
        User user = User.builder()
                .userName(userRequest.getUserName())
                .userPassword(userRequest.getUserPassword())
                .userEmail(userRequest.getUserEmail())
                .build();

        User savedUser = userRepository.save(user);
        return new UserResponse(savedUser);
    }

    // GET
    public UserResponse findUserByUserName(String userName) {
        return userRepository.findByUserName(userName)
                .map(UserResponse::new)
                .orElseThrow(() -> new RuntimeException("User not found")); // 사용자 정보를 찾지 못한 경우 예외 처리
    }

    // GET
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserResponse::new)
                .collect(Collectors.toList());
    }


    // UPDATE
    public void updateUser(UserUpdateRequest userUpdateRequest, String userName) {
        User user = userRepository.findByUserName(userName).orElse(null);
        user.setUserPassword(userUpdateRequest.getUserPassword());
        user.setUserEmail(userUpdateRequest.getUserEmail());
        User savedUser = userRepository.save(user);
    }

    // DELETE
    public void deleteUser(String userName) {
        User user = userRepository.findByUserName(userName).orElse(null);
        userRepository.delete(user);
    }

}