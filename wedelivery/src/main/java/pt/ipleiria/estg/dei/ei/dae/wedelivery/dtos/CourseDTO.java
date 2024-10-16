package pt.ipleiria.estg.dei.ei.dae.wedelivery.dtos;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.entities.Course;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class CourseDTO implements Serializable {
    private long code;
    private String name;

    public CourseDTO() {
    }

    public CourseDTO(long code, String name) {
        this.code = code;
        this.name = name;
    }

    public static CourseDTO from(Course course) {
        return new CourseDTO(
                course.getCode(),
                course.getName()
        );
    }

    // converts an entire list of entities into a list of DTOs
    public static List<CourseDTO> from(List<Course> courses) {
        return courses.stream().map(CourseDTO::from).collect(Collectors.toList());
    }

    public long getCode() {
        return code;
    }

    public String getName() {
        return name;
    }


    public void setCode(long code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }
}
