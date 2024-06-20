package com.practice.Skilltest.board.dto;


import lombok.*;
import org.springframework.stereotype.Component;
import java.sql.Timestamp;

//@Alias("BoardDto")//매퍼 alias 지정
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Component
public class BoardDto {

    private long board_id;
    private String writer;
    private String title;
    private Timestamp modified_time;
    private Timestamp created_time;
    private String created_time_date;
    private String content;
    private int viewcount;
    private boolean is_hide;

}

