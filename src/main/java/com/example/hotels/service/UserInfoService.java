package com.example.hotels.service;

import com.example.hotels.dto.filter.Filter;
import com.example.hotels.entity.UserInfoMongo;

import java.util.List;

public interface UserInfoService {
    List<UserInfoMongo> findAll(Filter filter);

    UserInfoMongo save(UserInfoMongo userInfo);

    UserInfoMongo findById(String id);

    void deleteById(String id);

    List<String> saveToFile();
}
