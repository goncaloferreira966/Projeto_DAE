package pt.ipleiria.estg.dei.ei.dae.wedelivery.entities;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllSubjects",
                query = "SELECT s FROM Subject s ORDER BY s.course.name, s.schoolYear DESC, s.courseYear, s.name" // JPQL
        )
})

@Table(
        name = "subjects",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"name", "course_code", "school_year"})
        }
)

public class Subject implements Serializable {
    @Id
    private long code;
    @NotBlank
    private String name;
    @Column(name = "school_year")
    private String schoolYear;
    @Column(name = "course_year")
    private int courseYear;
    @ManyToOne
    private Course course;
    @ManyToMany
    @JoinTable(
            name = "subject_student",
            joinColumns = @JoinColumn(
                    name = "subject_code",
                    referencedColumnName = "code"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "student_username",
                    referencedColumnName = "username"
            )
    )
    private List<Student> students;
    @ManyToMany
    @JoinTable(
            name = "subject_teacher",
            joinColumns = @JoinColumn(
                    name = "subject_code",
                    referencedColumnName = "code"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "teacher_username",
                    referencedColumnName = "username"
            )
    )
    private List<Teacher> teachers;

    public Subject() {
        this.students = new LinkedList<>();
        this.teachers = new LinkedList<>();
    }

    public Subject(long code, String name, String schoolYear, int courseYear, Course course) {
        this.code = code;
        this.name = name;
        this.schoolYear = schoolYear;
        this.courseYear = courseYear;
        this.course = course;
        this.students = new LinkedList<>();
        this.teachers = new LinkedList<>();
    }

    public void addStudent(Student student) {
        if(!students.contains(student)){
            students.add(student);
            student.addSubject(this);
        }
    }

    public void removeStudent(Student student) {
        if(students.contains(student)){
            return;
        }
        students.remove(student);
        student.removeSubject(this);
    }

    public void addTeacher(Teacher teacher) {
        if(!teachers.contains(teacher)){
            teachers.add(teacher);
            teacher.addSubject(this);
        }
    }

    public void removeTeacher(Teacher teacher) {
        if(teachers.contains(teacher)){
            return;
        }
        teachers.remove(teacher);
        teacher.removeSubject(this);
    }

    public void setCode(long code) {
        this.code = code;
    }

    public void setName( String name) {
        this.name = name;
    }

    public void setSchoolYear( String schoolYear) {
        this.schoolYear = schoolYear;
    }

    public void setCourseYear( int courseYear) {
        this.courseYear = courseYear;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public long getCode() {
        return code;
    }

    public  String getName() {
        return name;
    }

    public  String getSchoolYear() {
        return schoolYear;
    }

    public int getCourseYear() {
        return courseYear;
    }

    public Course getCourse() {
        return course;
    }

    public List<Student> getStudents() {
        return students;
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }
}
