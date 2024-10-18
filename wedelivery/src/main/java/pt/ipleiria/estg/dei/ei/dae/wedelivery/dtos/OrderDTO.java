package pt.ipleiria.estg.dei.ei.dae.wedelivery.dtos;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.entities.Order;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class OrderDTO implements Serializable {
    private long code;
    private String username;
    private Date purchaseDate;
    private Date deliveryDate;

    public OrderDTO(long code, String username, Date purchaseDate, Date deliveryDate) {
        this.code = code;
        this.username = username;
        this.purchaseDate = purchaseDate;
        this.deliveryDate = deliveryDate;
    }

    public OrderDTO() {
    }


    public static OrderDTO from(Order order) {
        return new OrderDTO(
                order.getCode(),
                order.getClient().getUsername(),
                order.getPurchaseDate(),
                order.getDeliveryDate()
        );
    }

    // converts an entire list of entities into a list of DTOs
    public static List<OrderDTO> from(List<Order> orders) {
        return orders.stream().map(OrderDTO::from).collect(Collectors.toList());
    }

    public long getCode() {
        return code;
    }

    public String getUsername() {
        return username;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }
}
