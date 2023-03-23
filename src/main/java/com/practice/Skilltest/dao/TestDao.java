package com.practice.Skilltest.dao;

import com.practice.Skilltest.dto.TestDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TestDao {

    List<TestDto> selectAll();


}
