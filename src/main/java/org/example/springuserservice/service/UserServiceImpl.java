package org.example.springuserservice.service;

import org.example.springuserservice.dto.UserDTO;
import org.example.springuserservice.entity.User;
import org.example.springuserservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repo;

    private UserDTO toDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .age(user.getAge())
                .build();
    }

    private User toEntity(UserDTO dto) {
        return User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .age(dto.getAge())
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Override
    public UserDTO create(UserDTO dto) {
        return toDTO(repo.save(toEntity(dto)));
    }

    @Override
    public UserDTO get(Long id) {
        return repo.findById(id).map(this::toDTO).orElseThrow();
    }

    @Override
    public List<UserDTO> getAll() {
        return repo.findAll().stream().map(this::toDTO).toList();
    }

    @Override
    public UserDTO update(Long id, UserDTO dto) {
        User user = repo.findById(id).orElseThrow();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setAge(dto.getAge());
        return toDTO(repo.save(user));
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }
}
