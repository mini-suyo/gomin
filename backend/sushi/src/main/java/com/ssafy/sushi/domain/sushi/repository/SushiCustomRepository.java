package com.ssafy.sushi.domain.sushi.repository;

import com.ssafy.sushi.domain.sushi.entity.Sushi;

import java.time.LocalDateTime;
import java.util.List;

public interface SushiCustomRepository {
    List<Sushi> findRandomAvailableSushi(Integer userId, LocalDateTime now, int size);
}
