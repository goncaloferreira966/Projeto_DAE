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
    private String state;
    private String usernameOperator;

    public OrderDTO(long code, Date deliveryDate, Date purchaseDate, String username, String usernameOperator, String state) {
        this.code = code;
        this.username = username;
        this.purchaseDate = purchaseDate;
        this.deliveryDate = deliveryDate;
        this.state = state;
        this.usernameOperator = usernameOperator;
    }

    public OrderDTO() {
    }


    public static OrderDTO from(Order order) {
        return new OrderDTO(
                order.getCode(),
                order.getDeliveryDate(),
                order.getPurchaseDate(),
                order.getClient().getUsername(),
                order.getOperator().getUsername(),
                order.getState()
        );
    }

    // converts an entire list of entities into a list of DTOs
    public static List<OrderDTO> from(List<Order> orders) {
        return orders.stream().map(OrderDTO::from).collect(Collectors.toList());
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public long getCode() {
        return code;
    }

    public String getUsername() {
        return username;
    }

    public String getUsernameOperator() {
        return usernameOperator;
    }

    public void setUsernameOperator(String usernameOperator) {
        this.usernameOperator = usernameOperator;
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
