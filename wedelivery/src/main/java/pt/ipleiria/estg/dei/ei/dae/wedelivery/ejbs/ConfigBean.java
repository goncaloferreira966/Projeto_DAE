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

        administratorBean.create("goncalosantos@gmail.com", "Gonçalo Estefácio", "olasoufixe", "oioioioioioi");
        administratorBean.create("1goncalosantos@gmail.com", "1Gonçalo Estefácio", "1olasoufixe", "1oioioioioioi");
        administratorBean.create("2goncalosantos@gmail.com", "2Gonçalo Estefácio", "2olasoufixe", "2oioioioioioi");

        managerBean.create("rui@mgmail.pt", "Rui", "Rui", "Rui");
    }
}
