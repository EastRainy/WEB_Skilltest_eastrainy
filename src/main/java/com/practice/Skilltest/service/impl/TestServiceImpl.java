package com.practice.Skilltest.service.impl;

import com.practice.Skilltest.dao.TestDao;
import com.practice.Skilltest.dto.TestDto;
import com.practice.Skilltest.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final TestDao dao;

    @Override
    public List<TestDto> testing() {

        return dao.selectAll();
    }
}
