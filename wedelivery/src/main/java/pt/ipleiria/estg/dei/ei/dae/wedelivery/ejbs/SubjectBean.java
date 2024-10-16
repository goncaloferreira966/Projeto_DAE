package pt.ipleiria.estg.dei.ei.dae.wedelivery.ejbs;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.entities.Subject;

import java.util.List;

@Stateless
public class SubjectBean {
    @PersistenceContext
    private EntityManager entityManager;

    @EJB
    private CourseBean courseBean;

    public void create(long code, String name, String schoolYear,int courseYear, long course_code) {
        var course = courseBean.find(course_code);
        var subject = new Subject(code, name, schoolYear, courseYear, course);
        entityManager.persist(subject);
    }

    public List<Subject> findAll() {
        // remember, maps to: “SELECT s FROM Student s ORDER BY s.name”
        return entityManager.createNamedQuery("getAllSubjects", Subject.class).getResultList();
    }

    public Subject find(long code) {
        var subject = entityManager.find(Subject.class, code);
        if (subject == null) {
            throw new RuntimeException("subject " + code + " not found");
        }
        return subject;
    }
}
