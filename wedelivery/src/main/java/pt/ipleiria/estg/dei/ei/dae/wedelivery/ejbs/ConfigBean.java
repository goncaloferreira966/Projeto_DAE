package pt.ipleiria.estg.dei.ei.dae.wedelivery.ejbs;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
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
    @EJB
    private SensorValueBean sensorValueBean;

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

            long id_1 = Math.abs(UUID.randomUUID().hashCode());
            orderBean.create(id_1, new Date(), new Date(), "Guilherme", "DinisRX", "Shipped");
            long id_2 = Math.abs(UUID.randomUUID().hashCode());
            orderBean.create(id_2, new Date(), new Date(), "Guilherme","DinisRX", "Pending");
            long id_3 = Math.abs(UUID.randomUUID().hashCode());
            orderBean.create(id_3, new Date(), new Date(), "Goncalo","GoncaloF00","Shipped");
            long id_4 = Math.abs(UUID.randomUUID().hashCode());
            orderBean.create(id_4, new Date(), new Date(), "Goncalo","DinisRX","Delivered");
            long id_5 = Math.abs(UUID.randomUUID().hashCode());
            orderBean.create(id_5, new Date(), new Date(), "Goncalo","Gui0000","Shipped");
            long id_6 = Math.abs(UUID.randomUUID().hashCode());
            orderBean.create(id_6, new Date(), new Date(), "Goncalo","Gui0000","Shipped");
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

            sensorBean.create(1, "Temperature", "20", false, false);
            sensorValueBean.create(1,"20", new Date(), 1);

            sensorBean.create(2, "Temperature", "23", false, false);
            sensorValueBean.create(2,"23", new Date(), 2);

            sensorBean.create(3, "Temperature", "30", false, false);
            sensorValueBean.create(3,"30", new Date(), 3);

            sensorBean.create(4, "Temperature", "31", false, false);
            sensorValueBean.create(4,"31", new Date(), 4);

            sensorBean.create(5, "Temperature", "10", false, false);
            sensorValueBean.create(5,"10", new Date(), 5);

            sensorBean.create(6, "Temperature", "20", false, false);
            sensorValueBean.create(6,"20", new Date(), 6);

            sensorBean.create(7, "Temperature", "21", false, false);
            sensorValueBean.create(7,"21", new Date(), 7);

            sensorBean.create(8, "Temperature", "22", false, false);
            sensorValueBean.create(8,"22", new Date(), 8);

            sensorBean.create(9, "Temperature", "22", false, false);
            sensorValueBean.create(9,"22", new Date(), 9);

            sensorBean.create(10, "Temperature", "23", false, false);
            sensorValueBean.create(10,"23", new Date(), 10);

            sensorBean.create(11, "Temperature", "-4", false, false);
            sensorValueBean.create(11,"-4", new Date(), 11);

            sensorBean.create(12, "Temperature", "-1", false, false);
            sensorValueBean.create(12,"-1", new Date(), 12);

            sensorBean.create(13, "Temperature", "10", false, false);
            sensorValueBean.create(13,"10", new Date(), 13);

            sensorBean.create(14, "Temperature", "20", false, false);
            sensorValueBean.create(14,"20", new Date(), 14);

            sensorBean.create(15, "Temperature", "30", false, false);
            sensorValueBean.create(15,"30", new Date(), 15);

            sensorBean.create(16, "Temperature", "31", false, false);
            sensorValueBean.create(16,"31", new Date(), 16);

            sensorBean.create(17, "Temperature", "21", false, false);
            sensorValueBean.create(17,"21", new Date(), 17);

            sensorBean.create(18, "Temperature", "22", false, false);
            sensorValueBean.create(18,"22", new Date(), 18);

            sensorBean.create(19, "Temperature", "22", false, false);
            sensorValueBean.create(19,"22", new Date(), 19);

            sensorBean.create(20, "Temperature", "22", false, false);
            sensorValueBean.create(20,"22", new Date(), 20);

            sensorBean.create(21, "Humidity", "100", false, false);
            sensorValueBean.create(21,"100", new Date(), 21);

            sensorBean.create(22, "Humidity", "88", false, false);
            sensorValueBean.create(22,"88", new Date(), 22);

            sensorBean.create(23, "Humidity", "90", false, false);
            sensorValueBean.create(23,"90", new Date(), 23);

            sensorBean.create(24, "Humidity", "70", false, false);
            sensorValueBean.create(24,"70", new Date(), 24);

            sensorBean.create(25, "Humidity", "77", false, false);
            sensorValueBean.create(25,"77", new Date(), 25);

            sensorBean.create(26, "Humidity", "60", false, false);
            sensorValueBean.create(26,"60", new Date(), 26);

            sensorBean.create(27, "Humidity", "20", false, false);
            sensorValueBean.create(27,"20", new Date(), 27);

            sensorBean.create(28, "Humidity", "55", false, false);
            sensorValueBean.create(28,"55", new Date(), 28);

            sensorBean.create(29, "GPS", "38.7071,-9.1355", false, false);
            sensorValueBean.create(29,"38.7071,-9.1355", new Date(), 29);

            sensorBean.create(30, "GPS", "48.8584,2.2945", false, false);
            sensorValueBean.create(30,"48.8584,2.2945", new Date(), 30);

            sensorBean.create(31, "GPS", "40.6892,-74.0445", false, false);
            sensorValueBean.create(31,"40.6892,-74.0445", new Date(), 31);

            sensorBean.create(32, "GPS", "41.8902,9.1355", false, false);
            sensorValueBean.create(32,"41.8902,9.1355", new Date(), 32);

            sensorBean.create(33, "Accelerometer", "2", false, false);
            sensorValueBean.create(33,"2", new Date(), 33);

            sensorBean.create(34, "Accelerometer", "4", false, false);
            sensorValueBean.create(34,"4", new Date(), 34);

            sensorBean.create(35, "Accelerometer", "3", false, false);
            sensorValueBean.create(35,"3", new Date(), 35);


            productBean.create(1, "Coca-Cola", "Coca-Cola", 1.5, "coca-cola.jpg", 19, true, false, "Leiria", "safari");
            productBean.create(2, "Pepsi", "Pepsi", 1.5, "pepsi.jpg", 102, true, false, "Lisboa", "safari");
            productBean.create(3, "Sprite", "Sprite", 1.5, "sprite.jpg", 100, true, false, "Leiria", "safari");
            productBean.create(4, "Fanta", "Fanta", 1.5, "fanta.jpg", 100, true, false, "Porto", "safari");
            productBean.create(5, "7up", "7up", 1.5, "7up.jpg", 100, true, false, "Porto", "safari");
            productBean.create(6, "Sumol", "Sumol", 1.5, "sumol.jpg", 100, true, false, "Leiria", "safari");

            restrictionBean.create(1, "Temperature", 20, 0);
            restrictionBean.create(10, "Humidity", 15,9);
            restrictionBean.create(4, "Temperature", 15,9);
            restrictionBean.create(5, "Temperature", 2, -2);
            restrictionBean.create(6, "Temperature", 0,-15);
            restrictionBean.create(3, "Humidity", 10,40);//optimal
            restrictionBean.create(2,"Humidity",70,40);//too moist
            restrictionBean.create(7, "Humidity", 40, 0);//too dry
            restrictionBean.create(8, "Accelerometer", 0, 5);
            restrictionBean.create(9, "Accelerometer", 0, 10);

            productBean.addProductInVolume(1, id_9, 3);
            productBean.addProductInVolume(2, id_9, 2);
            productBean.addProductInVolume(3, id_10,5);
            productBean.addProductInVolume(4, id_10,3);
            productBean.addProductInVolume(5, id_11,1);
            productBean.addProductInVolume(6, id_11,1);


            productBean.addRestrictionToProduct(1,1);
            productBean.addRestrictionToProduct(2,1);
            productBean.addRestrictionToProduct(2,2);
            productBean.addRestrictionToProduct(3,3);
            productBean.addRestrictionToProduct(4,4);
            productBean.addRestrictionToProduct(5,5);
            productBean.addRestrictionToProduct(6,6);
            productBean.addRestrictionToProduct(6,7);
            productBean.addRestrictionToProduct(6,8);
            productBean.addRestrictionToProduct(6,9);
            productBean.addRestrictionToProduct(6,10);


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

        } catch (Exception e) {
            System.err.println("Some exception happened while creating objects");
        }
    }
}
