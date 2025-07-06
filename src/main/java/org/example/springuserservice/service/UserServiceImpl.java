package org.example.springuserservice.service;

import org.example.springuserservice.dto.UserDTO;
import org.example.springuserservice.entity.User;
import org.example.springuserservice.mapper.UserMapper;
import org.example.springuserservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repo;
    private final UserMapper mapper;

    @Override
    public UserDTO create(UserDTO dto) {
        return mapper.toDTO(repo.save(mapper.toEntity(dto)));
    }

    @Override
    public UserDTO get(Long id) {
        return repo.findById(id).map(mapper::toDTO).orElseThrow();
    }

    @Override
    public List<UserDTO> getAll() {
        return repo.findAll().stream().map(mapper::toDTO).toList();
    }

    @Override
    public UserDTO update(Long id, UserDTO dto) {
        User user = repo.findById(id).orElseThrow();
        user.updateDetails(dto.getName(), dto.getEmail(), dto.getAge());
        return mapper.toDTO(repo.save(user));
    }
    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }
}