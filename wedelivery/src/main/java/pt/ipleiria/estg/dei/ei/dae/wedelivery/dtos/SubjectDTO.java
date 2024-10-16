package pt.ipleiria.estg.dei.ei.dae.wedelivery.dtos;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.entities.Subject;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class SubjectDTO implements Serializable {
    private long code;
    private String name;
    private long courseCode;
    private String courseName;
    private int courseYear;
    private String schoolarYear;
    public SubjectDTO() {
    }

    public SubjectDTO(long code, String name, long courseCode, String courseName, int courseYear, String schoolarYear) {
        this.code = code;
        this.name = name;
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.courseYear = courseYear;
        this.schoolarYear = schoolarYear;
    }

    // Converts an entity Student to a DTO Student class
    public static SubjectDTO from(Subject subject) {
        return new SubjectDTO(
                subject.getCode(),
                subject.getName(),
                subject.getCourse().getCode(),
                subject.getCourse().getName(),
                subject.getCourseYear(),
                subject.getSchoolYear()
        );
    }

    // converts an entire list of entities into a list of DTOs
    public static List<SubjectDTO> from(List<Subject> subjects) {
        return subjects.stream().map(SubjectDTO::from).collect(Collectors.toList());
    }

    public long getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public long getCourseCode() {
        return courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getCourseYear() {
        return courseYear;
    }

    public String getSchoolarYear() {
        return schoolarYear;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCourseCode(long courseCode) {
        this.courseCode = courseCode;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setCourseYear(int courseYear) {
        this.courseYear = courseYear;
    }

    public void setSchoolarYear(String schoolarYear) {
        this.schoolarYear = schoolarYear;
    }
}
