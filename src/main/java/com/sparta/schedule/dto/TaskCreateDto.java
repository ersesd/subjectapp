package com.sparta.schedule.dto;

import lombok.Getter;

import java.util.Date;

@Getter
public class TaskCreateDto {
    private String title;
    private String content;
    private String personInCharge;
    private Date creationDate;
    private String password;
    // other fields as needed

    // getters and setters
}