package com.ssafy.sushi.domain.user.service;

import com.ssafy.sushi.domain.user.dto.request.UpdateNicknameRequest;
import com.ssafy.sushi.domain.user.dto.response.UserLikeNumResponse;
import com.ssafy.sushi.domain.user.dto.response.UserNicknameChangeResponse;
import com.ssafy.sushi.domain.user.entity.User;
import com.ssafy.sushi.domain.user.repository.UserRepository;
import com.ssafy.sushi.global.error.ErrorCode;
import com.ssafy.sushi.global.error.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserLikeNumResponse getUserLikeNum(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new CustomException(ErrorCode.USER_NOT_FOUND));

        return UserLikeNumResponse.of(user);
    }

    @Transactional
    public UserNicknameChangeResponse updateNickname(Integer userId, UpdateNicknameRequest request) {

        User user = userRepository.findById(userId).orElseThrow(() ->
                new CustomException(ErrorCode.USER_NOT_FOUND));

        user.updateNickname(request.getNickname());

        return UserNicknameChangeResponse.of(user);
    }

    @Transactional
    public void deleteUser(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new CustomException(ErrorCode.USER_NOT_FOUND));

        userRepository.delete(user);
    }

    public Boolean existsById(Integer userId) {
        return userRepository.existsById(userId);
    }
}
