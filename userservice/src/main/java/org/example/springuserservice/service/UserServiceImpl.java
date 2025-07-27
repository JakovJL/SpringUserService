package org.example.springuserservice.service;

import org.example.springuserservice.dto.UserDTO;
import org.example.springuserservice.entity.User;
import org.example.springuserservice.mapper.UserMapper;
import org.example.springuserservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import org.springframework.kafka.core.KafkaTemplate;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repo;
    private final UserMapper mapper;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public UserDTO create(UserDTO dto) {
        User user = repo.save(mapper.toEntity(dto));
        sendKafkaNotification(user.getEmail(), "create");
        return mapper.toDTO(user);
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
        repo.findById(id).ifPresent(user -> {
            String email = user.getEmail();
            repo.deleteById(id);
            sendKafkaNotification(email, "delete");
        });
    }
    private void sendKafkaNotification(String email, String action) {
        String message = String.format("{\"action\":\"%s\",\"email\":\"%s\"}", action, email);
        kafkaTemplate.send("user-notifications", message);
    }
}