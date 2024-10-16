package pt.ipleiria.estg.dei.ei.dae.wedelivery.ejbs;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;

@Singleton
@Startup
public class ConfigBean {

    @EJB
    private StudentBean studentBean;
    @EJB
    private CourseBean courseBean;
    @EJB
    private SubjectBean subjectBean;
    @EJB
    private AdministratorBean administratorBean;
    @EJB
    private TeacherBean teacherBean;

    @PostConstruct
    public void populateDB() {
        courseBean.create(1, "Engenharia Informática");
        courseBean.create(2, "Engenharia Automóvel");
        courseBean.create(3, "Engenharia Mecânica");

        studentBean.create("Goncalo", "password", "Gonçalo", "goncalo@gmail.com", 1);
        studentBean.create("Guilherme", "password", "Guilherme", "guilherme@gmail.com", 2);
        studentBean.create( "Cristiano", "password", "Papai Cris", "cr7@gmail.com", 3);

        subjectBean.create(1,"Análise Matemática", "2023-24", 2024, 1);
        subjectBean.create(2,"Física Aplicada", "2023-24", 2024, 1);
        subjectBean.create(3,"Sistemas Computacionais", "2023-24", 2024, 3);

        administratorBean.create("goncalosantos@gmail.com", "Gonçalo Estefácio", "olasoufixe", "oioioioioioi");
        administratorBean.create("1goncalosantos@gmail.com", "1Gonçalo Estefácio", "1olasoufixe", "1oioioioioioi");
        administratorBean.create("2goncalosantos@gmail.com", "2Gonçalo Estefácio", "2olasoufixe", "2oioioioioioi");

        teacherBean.create("olaola@gmail.com", "olaola", "oioioi", "oioioi", "Lisbon");
        teacherBean.create("1olaola@gmail.com", "1olaola", "1oioioi", "1oioioi", "Leiria");
        teacherBean.create("2olaola@gmail.com", "2olaola", "2oioioi", "2oioioi", "Coimbra");

        studentBean.enrollStudentInSubject("Goncalo", 1);
        studentBean.enrollStudentInSubject("Goncalo", 2);
        studentBean.enrollStudentInSubject("Cristiano", 2);
    }
}
