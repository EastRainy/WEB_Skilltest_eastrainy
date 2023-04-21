package com.practice.Skilltest.board.dto;

import lombok.RequiredArgsConstructor;
import org.apache.ibatis.type.Alias;

import java.sql.Timestamp;


//@Alias("BoardDto")//매퍼 alias 지정
public class BoardDto {

    private int board_id;
    private String writer;
    private String title;
    private Timestamp modified_time;
    private String content;
    private int viewcount;

    public int getBoard_id() {
        return board_id;
    }

    public void setBoard_id(int board_id) {
        this.board_id = board_id;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Timestamp getModified_time() {
        return modified_time;
    }

    public void setModified_time(Timestamp modified_time) {
        this.modified_time = modified_time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getViewcount() {
        return viewcount;
    }

    public void setViewcount(int viewcount) {
        this.viewcount = viewcount;
    }

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
