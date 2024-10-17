package pt.ipleiria.estg.dei.ei.dae.wedelivery.ejbs;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
@Singleton
@Startup
public class ConfigBean {

    @EJB
    private ClientBean clientBean;
    @EJB
    private AdministratorBean administratorBean;
    @EJB
    private ManagerBean managerBean;

    @PostConstruct
    public void populateDB() {
        clientBean.create("Goncalo", "password", "Gonçalo", "goncalo@gmail.com");
        clientBean.create("Guilherme", "password", "Guilherme", "guilherme@gmail.com");
        clientBean.create( "Cristiano", "password", "Papai Cris", "cr7@gmail.com");

        administratorBean.create("Goncalo@gmail.com", "Goncalo Ferreira", "password", "GoncaloF00");
        administratorBean.create("Dinis@gmail.com", "Dinis Roxo", "password", "DinisRX");
        administratorBean.create("Gui@gmail.com", "Guilherme Cruz", "password", "Gui0000");

        managerBean.create("rui@mgmail.pt", "Rui", "Rui", "Rui");
    }
}
