package pt.ipleiria.estg.dei.ei.dae.wedelivery.ejbs;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.validation.ConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.dtos.OrderDTO;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.dtos.ProductDTO;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.dtos.VolumeDTO;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.entities.*;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.exceptions.MyEntityNotFoundException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Stateless
public class OrderBean {
    @PersistenceContext
    private EntityManager entityManager;

    @EJB
    private ClientBean clientBean;

    @EJB
    private OperatorBean operatorBean;

    @EJB
    private ProductBean productBean;

    @EJB
    private VolumeBean volumeBean;

    public void create(long code, Date deliveryDate, Date purchaseDate, String username, String usernameOperator, String state)
            throws MyEntityNotFoundException, MyEntityExistsException,
            MyConstraintViolationException
    {
        try {
            if (exists(code)) {
                throw new MyEntityExistsException(
                        "order with code '" + code + "' already exists");
            }

            var client = clientBean.find(username);
            var operator = operatorBean.find(usernameOperator);
            var order = new Order(deliveryDate, purchaseDate, client, operator, code, state);
            entityManager.persist(order);
            entityManager.flush();
        }
        catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }
    /***************************** Order **************************************************/
    public List<Order> findAll() {
        // remember, maps to: “SELECT s FROM Student s ORDER BY s.name”
        return entityManager.createNamedQuery("getAllOrders", Order.class).getResultList();
    }
    public Order find(long code) {
        var order = entityManager.find(Order.class, code);
        if (order == null) {
            throw new RuntimeException("order " + code + " not found");
        }
        return order;
    }
    public List<Order> findOrdersByState(String state) {
        return entityManager.createNamedQuery("getAllOrdersByState", Order.class)
                .setParameter("state", state)
                .getResultList();
    }
    public List<Order> findOrdersByDate(String date) {
        var data = date.split("/");
        return entityManager.createQuery(
                "SELECT o FROM Order o WHERE o.purchaseDate = :date",
                Order.class
        ).setParameter("date", data).getResultList();
    }
    public List<Order> findOrdersByDate(Date date) {
        return entityManager.createQuery(
                "SELECT o FROM Order o WHERE o.purchaseDate = :date",
                Order.class
        ).setParameter("date", date).getResultList();
    }
    public List<Order> findOrdersBetweenDates(Date date1, Date date2) {
        return entityManager.createQuery(
                "SELECT o FROM Order o WHERE o.purchaseDate BETWEEN :date1 AND :date2",
                Order.class
        ).setParameter("date1", date1).setParameter("date2", date2).getResultList();
    }

    public void update(Order order) {
        entityManager.merge(order);  // Atualiza o order na bd
    }

    /********************************* Orders by Users ******************************************/
    public List<Order> findOrdersByClientId(String username) {
        return entityManager.createNamedQuery("getAllOrdersByClient", Order.class)
                .setParameter("username", username)
                .getResultList();
    }
    public List<Order> findOrdersByClient(String username) {
        return entityManager.createQuery(
                "SELECT o FROM Order o WHERE o.client.username = :username",
                Order.class
        ).setParameter("username", username).getResultList();
    }
    public List<Order> findOrdersByOperatorId(String username) {
        return entityManager.createNamedQuery("getAllOrdersByOperator", Order.class)
                .setParameter("username", username)
                .getResultList();
    }
    public List<Order> findOrdersByOperator(Operator operator) {
        return entityManager.createQuery(
                "SELECT o FROM Order o WHERE o.operator = :operator",
                Order.class
        ).setParameter("operator", operator).getResultList();
    }




    /****************************** Orders Volume*************************************/
    public Order findWithVolumes(long code){
        var order = this.find(code);
        order.getVolumes().size();
        return order;
    }
    public List<Order> findOrdersByVolumeId(long id) {
        return entityManager.createNamedQuery("getAllOrdersByVolume", Order.class)
                .setParameter("id", id)
                .getResultList();
    }
    /****************************** Volumes Methods ****************************************/
    /*
    public void addVolumeToOrder(long code, long idVolume) {
        var order = find(code);
        var volume = volumeBean.find(idVolume);
        if (!order.getVolumes().contains(volume) && volume.getOrder() == null) {
            order.addVolume(volume);
            volume.setOrder(order);
            entityManager.merge(order);
            entityManager.merge(volume);
        }
    }
    public void removeVolumeFromOrder(long code, long idVolume) {
        var order = find(code);
        var volume = volumeBean.find(idVolume);
        if (order.getVolumes().contains(volume) && volume.getOrder() != null) {
            order.removeVolume(volume);
            volume.setOrder(null);
            entityManager.merge(order);
            entityManager.merge(volume);
        }
    }
    */

    public OrderDTO makeNewOrder(List<Product> products, String username, String operator) throws MyConstraintViolationException {
        long idOrder = getNewID();
        create(idOrder, new Date(), new Date(), username, operator, "Pending");
        Order order = find(idOrder);
        var haveVolume = false;
        for (Product product : products){

            if (findOrdersByVolumeId(idOrder).isEmpty()){
                long newVolumeID = getNewID();
                volumeBean.create(newVolumeID, "Pending",  new Date(), idOrder);
                productBean.addProductInVolume(product.getId(), newVolumeID, product.getQuantity());
                haveVolume = true;
            } else {
                var volumes = volumeBean.findVolumesByOrderId(idOrder);
                for (Volume volume : volumes){
                    var productsInVolumes = productBean.findAllProductsByVolumeId(volume.getId());
                    if (productsInVolumes.stream().noneMatch(productIn -> productIn.getId() == product.getId()) && productsInVolumes.stream().allMatch(productIn -> productIn.getWarehouse().getName().equals(productBean.findById(product.getId()).getWarehouse().getName()))) {
                        productBean.addProductInVolume(product.getId(), volume.getId(), product.getQuantity());
                        haveVolume = true;
                    }
                }
            }
            if (!haveVolume) {
                var newVolumeID = getNewID();
                volumeBean.create(newVolumeID, "Pending", new Date(), idOrder);
                productBean.addProductInVolume(product.getId(), newVolumeID, product.getQuantity());

            }
        }
        for (Volume volume : volumeBean.findVolumesByOrderId(idOrder)){
            volumeBean.setSensoresToVolume(volume.getId());
        }
        OrderDTO orderDTO = OrderDTO.from(order);
        List<Volume> volumes = volumeBean.findVolumesByOrderId(idOrder);
        List<VolumeDTO> volumeDTOS = VolumeDTO.from(volumes);
        for (VolumeDTO volumeDTO : volumeDTOS){
            volumeDTO.setProducts(ProductDTO.from(productBean.findAllProductsByVolumeId(volumeDTO.getId())));
            orderDTO.addVolume(volumeDTO);
        }
        return orderDTO;
    }


    public boolean exists(long code) {
        Query query = entityManager.createQuery(
                "SELECT COUNT(o.code) FROM Order o WHERE o.code = :code",
                Long.class
        );
        query.setParameter("code", code);
        return (Long)query.getSingleResult() > 0L;
    }
    private long getNewID(){
        return Math.abs(UUID.randomUUID().hashCode());
    }
}
