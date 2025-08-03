package org.example.service;


import lombok.RequiredArgsConstructor;
import org.example.dto.UserDto;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Service;

import org.example.kafka.producer.KafkaMessageProducer;
import org.example.kafka.dto.UserEvent;


import javax.persistence.EntityNotFoundException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final KafkaMessageProducer kafkaProducer;

    @Override
    public User createUser(UserDto dto) {
        User user = new User(dto.getName(), dto.getEmail(), dto.getAge());
        kafkaProducer.sendUserEvent("CREATE", user.getEmail());

        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id){
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User id - " + id + "not found"));
    }

    @Override
    public User updateUser(Long id, UserDto userDto){
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found " + id));
        existingUser.setName(userDto.getName());
        existingUser.setEmail(userDto.getEmail());
        existingUser.setAge(userDto.getAge());

        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser (Long id){
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.deleteById(id);

        kafkaProducer.sendUserEvent("DELETE", user.getEmail());

    }



}
