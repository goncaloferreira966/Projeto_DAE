package pt.ipleiria.estg.dei.ei.dae.wedelivery.ejbs;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;

import java.util.Date;
import java.util.UUID;

@Singleton
@Startup
public class ConfigBean {

    @EJB
    private ClientBean clientBean;
    @EJB
    private OperatorBean operatorBean;
    @EJB
    private ManagerBean managerBean;
    @EJB
    private OrderBean orderBean;
    @EJB
    private ProductBean productBean;
    @EJB
    private WarehouseBean warehouseBean;

    @PostConstruct
    public void populateDB() {
        clientBean.create("Goncalo", "password", "Gonçalo", "goncalo@gmail.com", 123123123, "2565-834", "PT", "Leiria", "Rua das Igrejas");
        clientBean.create("Guilherme", "password", "Guilherme", "guilherme@gmail.com" ,123123124, "2565-834", "PT", "Leiria", "Rua das Igrejas");
        clientBean.create( "Cristiano", "password", "Papai Cris", "cr7@gmail.com", 123123125, "2565-834", "PT", "Leiria", "Rua das Igrejas");

        operatorBean.create("Goncalo@gmail.com", "Goncalo Ferreira", "password", "GoncaloF00");
        operatorBean.create("Dinis@gmail.com", "Dinis Roxo", "password", "DinisRX");
        operatorBean.create("Gui@gmail.com", "Guilherme Cruz", "password", "Gui0000");

        managerBean.create("rui@mgmail.pt", "Rui", "Rui", "Rui");


        orderBean.create(Math.abs(UUID.randomUUID().hashCode()), new Date(), new Date(), "Guilherme", "DinisRX", "In distribution");
        orderBean.create(Math.abs(UUID.randomUUID().hashCode()), new Date(), new Date(), "Guilherme","DinisRX", "Pending");
        orderBean.create(Math.abs(UUID.randomUUID().hashCode()), new Date(), new Date(), "Goncalo","GoncaloF00","Shipped");
        orderBean.create(Math.abs(UUID.randomUUID().hashCode()), new Date(), new Date(), "Goncalo","DinisRX","Delivered");
        orderBean.create(Math.abs(UUID.randomUUID().hashCode()), new Date(), new Date(), "Goncalo","Gui0000","In distribution");
        orderBean.create(Math.abs(UUID.randomUUID().hashCode()), new Date(), new Date(), "Goncalo","Gui0000","Pending");
        orderBean.create(Math.abs(UUID.randomUUID().hashCode()), new Date(), new Date(), "Goncalo","Gui0000","Shipped");
        orderBean.create(Math.abs(UUID.randomUUID().hashCode()), new Date(), new Date(), "Goncalo","Gui0000","Delivered");

        warehouseBean.create("Leiria", "Rua das Igrejas", "Leiria", "2565-834");
        warehouseBean.create("Lisboa", "Rua das Igrejas", "Lisboa", "2565-834");
        warehouseBean.create("Porto", "Rua das Igrejas", "Porto", "2565-834");

        productBean.create(1, "Coca-Cola", "Coca-Cola", 1.5, "coca-cola.jpg", 0, true, false, "Leiria");
        productBean.create(2, "Pepsi", "Pepsi", 1.5, "pepsi.jpg", 2, true, false, "Leiria");
        productBean.create(3, "Sprite", "Sprite", 1.5, "sprite.jpg", 100, true, false, "Leiria");
        productBean.create(4, "Fanta", "Fanta", 1.5, "fanta.jpg", 100, true, false, "Leiria");
        productBean.create(5, "7up", "7up", 1.5, "7up.jpg", 100, true, false, "Leiria");
        productBean.create(6, "Sumol", "Sumol", 1.5, "sumol.jpg", 100, true, false, "Leiria");





    }
}
