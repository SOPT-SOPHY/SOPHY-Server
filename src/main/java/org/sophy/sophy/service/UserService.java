package org.sophy.sophy.service;

import lombok.RequiredArgsConstructor;
import org.sophy.sophy.controller.request.dto.UserLoginRequestDto;
import org.sophy.sophy.domain.User;
import org.sophy.sophy.exception.ErrorStatus;
import org.sophy.sophy.exception.model.InvalidPasswordException;
import org.sophy.sophy.exception.model.NotFoundException;
import org.sophy.sophy.infrastructure.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public Long login(UserLoginRequestDto request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new NotFoundException(ErrorStatus.NOT_FOUND_USER_EXCEPTION, ErrorStatus.NOT_FOUND_USER_EXCEPTION.getMessage()));

        if (!user.getPassword().equals(request.getPassword())) {
            throw new InvalidPasswordException(ErrorStatus.INVALID_PASSWORD_EXCEPTION, ErrorStatus.INVALID_PASSWORD_EXCEPTION.getMessage());
        }

        return user.getId();
    }
}
