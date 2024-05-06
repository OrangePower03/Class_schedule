package com.example.classschedule.utils;

import com.example.classschedule.domain.Course;
import com.example.classschedule.domain.entity.CourseEntity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLUtils {
    public static final String URL="jdbc:mysql://1.117.70.65:3307/course_schedule";
    public static final String USERNAME="root";
    public static final String PASSWORD="ZstIsGod20031006";
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        }
        catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static int insert(CourseEntity course) {
        String INSERT_SQL = "insert into course (section, week, teacher_name, course_name, week_schedule, classroom) VALUES (?, ?, ?, ?, ?, ?)";
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SQL)) {
            preparedStatement.setInt(1, course.getSection());
            preparedStatement.setInt(2, course.getWeek());
            preparedStatement.setString(3, course.getCourse().getTeacherName());
            preparedStatement.setString(4, course.getCourse().getCourseName());
            preparedStatement.setString(5, course.getCourse().getWeekSchedule());
            preparedStatement.setString(6, course.getCourse().getClassroom());

            return preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static int update(CourseEntity course) {
        String UPDATE_SQL = "update course set teacher_name=?, course_name=?, week_schedule=?, classroom=? where section=? and week=?";
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setString(1, course.getCourse().getTeacherName());
            preparedStatement.setString(2, course.getCourse().getCourseName());
            preparedStatement.setString(3, course.getCourse().getWeekSchedule());
            preparedStatement.setString(4, course.getCourse().getClassroom());
            preparedStatement.setInt(5, course.getSection());
            preparedStatement.setInt(6, course.getWeek());

            return preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static int delete(int section, int week) {
        String DELETE_SQL = "delete from course where section=? and week=?";
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)) {
            preparedStatement.setInt(1, section);
            preparedStatement.setInt(2, week);

            return preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Course select(int section, int week) {
        String SELECT_SQL = "select teacher_name, course_name, week_schedule, classroom from course where section=? and week=?";
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_SQL)) {
            preparedStatement.setInt(1, section);
            preparedStatement.setInt(2, week);
            ResultSet resultSet = preparedStatement.executeQuery();
            Course course=new Course();
            while(resultSet.next()) {
                course.setTeacherName(resultSet.getString("teacher_name"));
                course.setCourseName(resultSet.getString("course_name"));
                course.setWeekSchedule(resultSet.getString("week_schedule"));
                course.setClassroom(resultSet.getString("classroom"));
            }
            resultSet.close();
            return course;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
