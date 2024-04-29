package com.practice.Skilltest.board.dto;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDate;


@Getter
@Setter
@RequiredArgsConstructor
public class BoardDto {

    private long board_id;
    private String writer;
    private String title;
    private Timestamp modified_time;
    private Timestamp created_time;
    private LocalDate created_time_date;
    private String content;
    private int viewcount;


    @Override
    public String toString() {
        return "BoardDto{" +
                "board_id=" + board_id +
                ", writer='" + writer + '\'' +
                ", title='" + title + '\'' +
                ", modified_time=" + modified_time +
                ", creared_time=" + created_time +
                ", created_time_date=" + created_time_date +
                ", content='" + content + '\'' +
                ", viewcount=" + viewcount +
                '}';
    }
}
