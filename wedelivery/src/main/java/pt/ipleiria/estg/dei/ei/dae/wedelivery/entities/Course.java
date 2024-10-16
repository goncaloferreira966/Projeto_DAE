package pt.ipleiria.estg.dei.ei.dae.wedelivery.entities;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllCourses",
                query = "SELECT c FROM Course c ORDER BY c.name" // JPQL
        )
})
@Table(name = "courses")

public class Course  implements Serializable {
    @Id
    private long code;
    @NotBlank
    private String name;
    @OneToMany(mappedBy = "course")
    private List<Student> students;
    @OneToMany(mappedBy = "course")
    private List<Subject> subjects;

    public Course() {
        this.students = new LinkedList<>();
        this.subjects = new LinkedList<>();
    }

    public Course(long code, String name) {
        this.code = code;
        this.name = name;
        this.students = new LinkedList<>();
        this.subjects = new LinkedList<>();
    }

    public void addStudent(Student student) {
        if(!students.contains(student)){
            students.add(student);
            student.setCourse(this);
        }
    }

    public void removeStudent(Student student) {
        if(students.contains(student)){
            return;
        }
        students.remove(student);
        student.setCourse(null);
    }

    public void addSubject(Subject subject) {
        if(!subjects.contains(subject)){
            subjects.add(subject);
            subject.setCourse(this);
        }
    }

    public void removeSubject(Subject subject) {
        if(subjects.contains(subject)){
            return;
        }
        subjects.remove(subject);
        subject.setCourse(null);
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public long getCode() {
        return code;
    }

    public @NotBlank String getName() {
        return name;
    }

    public List<Student> getStudents() {
        return students;
    }


    public void setName(@NotBlank String name) {
        this.name = name;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(code, course.code);
    }
}
