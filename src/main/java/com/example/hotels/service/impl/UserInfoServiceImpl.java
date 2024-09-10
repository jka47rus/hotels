package com.example.hotels.service.impl;

import com.example.hotels.dto.filter.Filter;
import com.example.hotels.entity.UserInfoMongo;
import com.example.hotels.repository_mongo.UserInfoRepository;
import com.example.hotels.service.UserInfoService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class UserInfoServiceImpl implements UserInfoService {

    private UserInfoRepository userInfoRepository;

    @Override
    public List<UserInfoMongo> findAll(Filter filter) {
        return userInfoRepository.findAll(PageRequest.of(filter.getPageNumber(), filter.getPageSize())).getContent();
    }

    @Override
    public UserInfoMongo save(UserInfoMongo userInfo) {
        return userInfoRepository.save(userInfo);
    }

    @Override
    public UserInfoMongo findById(String id) {
        return userInfoRepository.findById(id).get();
    }

    @Override
    public void deleteById(String id) {
        userInfoRepository.deleteById(id);
    }

    @Override
    @SneakyThrows
    public List<String> saveToFile() {
        List<UserInfoMongo> info = userInfoRepository.findAll();
        List<String> infoToString = new ArrayList<>();

        info.forEach(inform -> {
            String builder = "User Id: " + inform.getUserId() + ";";
            infoToString.add(builder);
        });


        return infoToString;
    }
}
