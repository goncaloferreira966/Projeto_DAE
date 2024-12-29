package pt.ipleiria.estg.dei.ei.dae.wedelivery.ejbs;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;
import java.util.logging.Logger;

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
    @EJB
    private SupplierBean supplierBean;
    @EJB
    private VolumeBean volumeBean;
    @EJB
    private SensorBean sensorBean;
    @EJB
    private RestrictionBean restrictionBean;

    private static final Logger logger = Logger.getLogger("ejbs.ConfigBean");

    @PostConstruct
    public void populateDB() {

        try {
            clientBean.create("Goncalo", "password", "Gonçalo", "goncalo@gmail.com", 123123123, "2565-834", "PT", "Leiria", "Rua das Igrejas");
            clientBean.create("Guilherme", "password", "Guilherme", "guilherme@gmail.com" ,123123124, "2565-834", "PT", "Leiria", "Rua das Igrejas");
            clientBean.create( "Cristiano", "password", "Papai Cris", "cr7@gmail.com", 123123125, "2565-834", "PT", "Leiria", "Rua das Igrejas");

            operatorBean.create("Goncalo@gmail.com", "Goncalo Ferreira", "password", "GoncaloF00");
            operatorBean.create("Dinis@gmail.com", "Dinis Roxo", "password", "DinisRX");
            operatorBean.create("Gui@gmail.com", "Guilherme Cruz", "password", "Gui0000");

            managerBean.create("rui@mgmail.pt", "Rui", "Rui", "Rui");

            supplierBean.create("safari", "qwerty", "Safari, LDA", "safari@mail.com");
            supplierBean.create("liquid", "qwerty", "Liquid, LDA", "liquid@mail.com");
            supplierBean.create("drinkLFA", "qwerty", "DrinkLFA, LDA", "drinklfa@mail.com");

         //   wait(1000);
            // wait(1000);
            //wait(1000);
            //wait(1000);
            //wait(1000);
            //wait(1000);
            //wait(1000);
            //wait(1000);
            //wait(1000);
            //wait(1000);
            //wait(1000);
            //wait(1000);

            long id_1 = Math.abs(UUID.randomUUID().hashCode());
            orderBean.create(id_1, new Date(), new Date(), "Guilherme", "DinisRX", "In distribution");
            long id_2 = Math.abs(UUID.randomUUID().hashCode());
            orderBean.create(id_2, new Date(), new Date(), "Guilherme","DinisRX", "Pending");
            long id_3 = Math.abs(UUID.randomUUID().hashCode());
            orderBean.create(id_3, new Date(), new Date(), "Goncalo","GoncaloF00","Shipped");
            long id_4 = Math.abs(UUID.randomUUID().hashCode());
            orderBean.create(id_4, new Date(), new Date(), "Goncalo","DinisRX","Delivered");
            long id_5 = Math.abs(UUID.randomUUID().hashCode());
            orderBean.create(id_5, new Date(), new Date(), "Goncalo","Gui0000","In distribution");
            long id_6 = Math.abs(UUID.randomUUID().hashCode());
            orderBean.create(id_6, new Date(), new Date(), "Goncalo","Gui0000","Pending");
            long id_7 = Math.abs(UUID.randomUUID().hashCode());
            orderBean.create(id_7, new Date(), new Date(), "Goncalo","Gui0000","Shipped");
            long id_8 = Math.abs(UUID.randomUUID().hashCode());
            orderBean.create(id_8, new Date(), new Date(), "Goncalo","Gui0000","Delivered");

            warehouseBean.create("Leiria", "Rua das Igrejas", "Leiria", "2565-834");
            warehouseBean.create("Lisboa", "Rua das Igrejas", "Lisboa", "2565-834");
            warehouseBean.create("Porto", "Rua das Igrejas", "Porto", "2565-834");

            long id_9 = Math.abs(UUID.randomUUID().hashCode());
            volumeBean.create(id_9, "No damage", new Date(), id_1);
            long id_10 = Math.abs(UUID.randomUUID().hashCode());
            volumeBean.create(id_10, "Minor damage", new Date(), id_2);
            long id_11 = Math.abs(UUID.randomUUID().hashCode());
            volumeBean.create(id_11, "Moderate damage", new Date(), id_3);
            long id_12 = Math.abs(UUID.randomUUID().hashCode());
            volumeBean.create(id_12, "Significant damage", new Date(), id_4);
            long id_13 = Math.abs(UUID.randomUUID().hashCode());
            volumeBean.create(id_13, "Heavily damaged", new Date(), id_5);
            long id_14 = Math.abs(UUID.randomUUID().hashCode());
            volumeBean.create(id_14, "Unusable", new Date(), id_6);

            sensorBean.create(1, "Temperature", 20, false, false);
            sensorBean.create(2, "Temperature", 20, false, false);
            sensorBean.create(3, "Temperature", 20, false, false);
            sensorBean.create(4, "Temperature", 20, false, false);
            sensorBean.create(5, "Temperature", 20, false, false);
            sensorBean.create(6, "Temperature", 20, false, false);
            sensorBean.create(7, "Temperature", 20, false, false);
            sensorBean.create(8, "Temperature", 20, false, false);
            sensorBean.create(9, "Temperature", 20, false, false);
            sensorBean.create(10, "Temperature", 20, false, false);
            sensorBean.create(11, "Temperature", 20, false, false);
            sensorBean.create(12, "Temperature", 20, false, false);
            sensorBean.create(13, "Temperature", 20, false, false);
            sensorBean.create(14, "Temperature", 20, false, false);
            sensorBean.create(15, "Temperature", 20, false, false);
            sensorBean.create(16, "Temperature", 20, false, false);
            sensorBean.create(17, "Temperature", 20, false, false);
            sensorBean.create(18, "Temperature", 20, false, false);
            sensorBean.create(19, "Temperature", 20, false, false);
            sensorBean.create(20, "Temperature", 20, false, false);
            sensorBean.create(21, "humidity", 20, false, false);
            sensorBean.create(22, "humidity", 20, false, false);
            sensorBean.create(23, "humidity", 20, false, false);
            sensorBean.create(24, "humidity", 20, false, false);
            sensorBean.create(25, "humidity", 20, false, false);
            sensorBean.create(26, "humidity", 20, false, false);
            sensorBean.create(27, "humidity", 20, false, false);
            sensorBean.create(28, "humidity", 20, false, false);

            productBean.create(1, "Coca-Cola", "Coca-Cola", 1.5, "coca-cola.jpg", 19, true, false, "Leiria", "safari");
            productBean.create(2, "Pepsi", "Pepsi", 1.5, "pepsi.jpg", 2, true, false, "Leiria", "safari");
            productBean.create(3, "Sprite", "Sprite", 1.5, "sprite.jpg", 100, true, false, "Lisboa", "safari");
            productBean.create(4, "Fanta", "Fanta", 1.5, "fanta.jpg", 100, true, false, "Leiria", "safari");
            productBean.create(5, "7up", "7up", 1.5, "7up.jpg", 100, true, false, "Porto", "safari");
            productBean.create(6, "Sumol", "Sumol", 1.5, "sumol.jpg", 100, true, false, "Porto", "safari");

            restrictionBean.create(1, "temperature", 20, 0);
            restrictionBean.create(2, "temprature", 15,9);
            restrictionBean.create(5, "temperatire", 2, -2);
            restrictionBean.create(6, "temperature", 0,-15);
            restrictionBean.create(3, "humidity", 10,40);//optimal
            restrictionBean.create(4,"humidity",70,40);//too moist
            restrictionBean.create(7, "humidity", 40, 0);//too dry
            restrictionBean.create(8, "accelerometer_10_G", 0, 5);
            restrictionBean.create(9, "accelerometer_16_G", 0, 10);

            productBean.addProductInVolume(1, id_9, 3);
            productBean.addProductInVolume(2, id_9, 2);
            productBean.addProductInVolume(3, id_10,5);
            productBean.addProductInVolume(4, id_10,3);
            productBean.addProductInVolume(5, id_11,1);
            productBean.addProductInVolume(6, id_11,1);

            volumeBean.addSensorToVolume(id_9, 1);
            volumeBean.addSensorToVolume(id_9, 21);
            volumeBean.addSensorToVolume(id_10, 3);
            volumeBean.addSensorToVolume(id_10, 23);
            volumeBean.addSensorToVolume(id_11, 5);
            volumeBean.addSensorToVolume(id_11, 25);
            volumeBean.addSensorToVolume(id_12, 7);
            volumeBean.addSensorToVolume(id_12, 27);
            volumeBean.addSensorToVolume(id_13, 9);
            volumeBean.addSensorToVolume(id_13, 28);

            productBean.addRestrictionToProduct(1,1);
            productBean.addRestrictionToProduct(2,1);
            productBean.addRestrictionToProduct(2,2);


        } catch (Exception e) {
            System.err.println("Some exception happened while creating objects");
        }
    }
}
