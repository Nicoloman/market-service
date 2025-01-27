package com.nqlo.ch.mkt.service.services;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nqlo.ch.mkt.service.entities.User;
import com.nqlo.ch.mkt.service.exceptions.DuplicateEntryException;
import com.nqlo.ch.mkt.service.exceptions.ResourceNotFoundException;
import com.nqlo.ch.mkt.service.repositories.UserRepository;
import static com.nqlo.ch.mkt.service.utils.UpdateUtils.updateIfChanged;

import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Validator validator; // Inyección del validador de JSR-303


    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id: " + id + " couldn't be found"));
    }

    @Transactional
    public User save(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new DuplicateEntryException("Email", user.getEmail() + "' is already taken.");
        }
        return userRepository.save(user);
    }

    @Transactional
    public User updateById(Long id, User updatedUser) {
        // Check si existe
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id: " + id + " couldn't be found."));
        
                // Check si el email ya existe
        if (updatedUser.getEmail() != null && !updatedUser.getEmail().isEmpty()) {
             Set<ConstraintViolation<User>> violations = validator.validateValue(User.class, "email", updatedUser.getEmail());
        if (!violations.isEmpty()) {
            throw new IllegalArgumentException("Email '" + updatedUser.getEmail() + "' is not valid.");
        }
            if (userRepository.existsByEmail(updatedUser.getEmail()) && !existingUser.getEmail().equals(updatedUser.getEmail())) {
                throw new DuplicateEntryException("email", "Email '" + updatedUser.getEmail() + "' is already taken.");
            }
            existingUser.setEmail(updatedUser.getEmail());
        }

        updateIfChanged(updatedUser.getName(), existingUser::getName, existingUser::setName);
        updateIfChanged(updatedUser.getRole(), existingUser::getRole, existingUser::setRole);

        if (updatedUser.getPassword() != null && updatedUser.getPassword().length() >= 8) {
            existingUser.setPassword(updatedUser.getPassword());
        }

        return userRepository.save(existingUser);
    }

    @Transactional
    public void deleteById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("User with id: " + id + "couldnt be found");
        }
        userRepository.deleteById(id);
    }

    //Make a sale...
}
