package pt.ipleiria.estg.dei.ei.dae.wedelivery.dtos;

import pt.ipleiria.estg.dei.ei.dae.wedelivery.entities.Student;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class StudentDTO implements Serializable {

    private String username;
    private String password;
    private String name;
    private String email;
    private Long courseCode;
    private String courseName;

    public StudentDTO() {
    }

    public StudentDTO(String username, String password, String name, String email, Long courseCode, String courseName) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.courseCode = courseCode;
        this.courseName = courseName;
    }

    // Converts an entity Student to a DTO Student class
    public static StudentDTO from(Student student) {
        return new StudentDTO(
                student.getUsername(),
                student.getPassword(),
                student.getName(),
                student.getEmail(),
                student.getCourse().getCode(),
                student.getCourse().getName()
        );
    }

    // converts an entire list of entities into a list of DTOs
    public static List<StudentDTO> from(List<Student> students) {
        return students.stream().map(StudentDTO::from).collect(Collectors.toList());
    }

    public void setCourseCode(Long courseCode) {
        this.courseCode = courseCode;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Long getCourseCode() {
        return courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
