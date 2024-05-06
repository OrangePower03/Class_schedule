package com.example.classschedule.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    private String teacherName="";
    private String courseName="";
    private String weekSchedule="";
    private String classroom="";
}
