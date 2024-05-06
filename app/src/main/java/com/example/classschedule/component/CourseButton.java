package com.example.classschedule.component;

import android.app.AlertDialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import androidx.appcompat.widget.AppCompatButton;

import com.example.classschedule.R;
import com.example.classschedule.databinding.DialogCourseInfoBinding;
import com.example.classschedule.domain.Course;
import com.example.classschedule.domain.entity.CourseEntity;
import com.example.classschedule.utils.AlertUtils;
import com.example.classschedule.utils.ObjectUtils;
import com.example.classschedule.utils.SQLUtils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseButton extends AppCompatButton {
    private int section;
    private int week;
    private Course course;

    public CourseButton(Context context) {
        this(context, null);
    }

    public CourseButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CourseButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }

    private void init(AttributeSet attrs, int defStyleAttr) {
        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CourseButton, defStyleAttr, 0);
            section = typedArray.getInt(R.styleable.CourseButton_section, 0);
            week = typedArray.getInt(R.styleable.CourseButton_week, 0);
            typedArray.recycle();
        }
        this.course = SQLUtils.select(section, week);

        updateText();
        setOnClickListener(v -> ((CourseButton) v).update());
    }

    private void update() {
        Context context = getContext();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("输入课程信息，删除此课程请清空并完成");

        DialogCourseInfoBinding binding = DialogCourseInfoBinding.inflate(LayoutInflater.from(context));
        Course copy = ObjectUtils.copyObj(course);
        binding.setCourse(copy);
        builder.setView(binding.getRoot());
        binding.btnClear.setOnClickListener(v -> {
            Course course = binding.getCourse();
            course.setTeacherName("");
            course.setCourseName("");
            course.setWeekSchedule("");
            course.setClassroom("");
            binding.setCourse(course);
        });

        builder.setPositiveButton("完成", (dialog, which) -> {
            if(ObjectUtils.allEmpty(binding.getCourse())) {
                SQLUtils.delete(section, week);
                course = binding.getCourse();
                updateText();
            }
            else if(ObjectUtils.allNonEmpty(binding.getCourse())) {
                if(ObjectUtils.allNonEmpty(course))
                    SQLUtils.update(new CourseEntity(section, week, binding.getCourse()));
                else
                    SQLUtils.insert(new CourseEntity(section, week, binding.getCourse()));
                course = binding.getCourse();
                updateText();
            }
            else {
                AlertUtils.showAlert(context, "请保证输入的信息不为空");
            }
        });

        builder.setNegativeButton("取消", (dialog, which) -> dialog.cancel());
        builder.create().show();
    }

    private void updateText() {
        ObjectUtils.resetAllStringNullToEmpty(course);
        setText(new StringBuilder()
                .append(course.getTeacherName()).append("\n")
                .append(course.getCourseName()).append("\n")
                .append(course.getWeekSchedule()).append("\n")
                .append(course.getClassroom()));
    }
}
