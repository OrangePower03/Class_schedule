package com.example.classschedule.domain.entity;

import com.example.classschedule.domain.Course;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseEntity {
    private int section; // 节次
    private int week;    // 周次
    private Course course;  // 课程
}
