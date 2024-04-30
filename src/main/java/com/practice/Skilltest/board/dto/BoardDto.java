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
    private String content;
    private int viewcount;

    @Override
    public String toString() {
        return "BoardDto{" +
                "board_id=" + board_id +
                ", writer='" + writer + '\'' +
                ", title='" + title + '\'' +
                ", modified_time=" + modified_time +
                ", content='" + content + '\'' +
                ", viewcount=" + viewcount +
                '}';
    }
}
