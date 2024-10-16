package pt.ipleiria.estg.dei.ei.dae.wedelivery.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Teacher extends User implements Serializable {
    @NotBlank
    private String office;
    @ManyToMany(mappedBy = "teachers")
    private List<Subject> subjects;

    public Teacher(String email, String name, String password, String username, String office) {
        super(email, name, password, username);
        this.subjects = new LinkedList<>();
        this.office = office;
    }

    public Teacher() {
        this.subjects = new LinkedList<>();
    }

    public void addSubject(Subject subject) {
        if(!subjects.contains(subject)){
            subjects.add(subject);
            subject.addTeacher(this);
        }
    }

    public void removeSubject(Subject subject) {
        if(subjects.contains(subject)){
            return;
        }
        subjects.remove(subject);
        subject.removeTeacher(this);
    }

    public String getOffice() {
        return office;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }
}
