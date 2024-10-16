package pt.ipleiria.estg.dei.ei.dae.wedelivery.ejbs;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.entities.Student;

import java.util.List;

@Stateless
public class StudentBean {
    @PersistenceContext
    private EntityManager entityManager;
    @EJB
    private CourseBean courseBean;
    @EJB
    private SubjectBean subjectBean;

    public void create(String username, String password, String name, String email, long course_code) {
        var course = courseBean.find(course_code);
        var student = new Student(username, password, name, email, course);
        entityManager.persist(student);
    }

    public List<Student> findAll() {
        // remember, maps to: “SELECT s FROM Student s ORDER BY s.name”
        return entityManager.createNamedQuery("getAllStudents", Student.class).getResultList();
    }

    public Student find(String username) {
        var student = entityManager.find(Student.class, username);
        if (student == null) {
            throw new RuntimeException("student " + username + " not found");
        }
        return student;
    }

    public void enrollStudentInSubject(String username, long subject_code){
        var student = this.find(username);
        var subject = subjectBean.find(subject_code);

        if(!student.getSubjects().contains(subject)){
            if(student.getCourse().getCode() == subject.getCourse().getCode()){
                student.addSubject(subject);
                subject.addStudent(student);
                entityManager.merge(student);
                entityManager.merge(subject);
            }
        }
    }

    public Student findWithSubjects(String username){
        var student = this.find(username);
        Hibernate.initialize(student.getSubjects());
        return student;
    }
}
