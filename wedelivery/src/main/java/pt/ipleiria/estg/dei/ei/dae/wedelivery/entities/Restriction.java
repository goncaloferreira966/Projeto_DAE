package pt.ipleiria.estg.dei.ei.dae.wedelivery.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Table(name = "restrictions")
@NamedQueries(
        {
                @NamedQuery(
                        name = "getAllRestrictions",
                        query = "SELECT r FROM Restriction r"
                )
        }
)
@Entity
public class Restriction {
    @Id
    private long id;
    @NotNull
    private String type;
    private int maxValue;
    private int minValue;

    public Restriction() {
    }

    public Restriction(long id, String type, int maxValue, int minValue) {
        this.id = id;
        this.type = type;
        this.maxValue = maxValue;
        this.minValue = minValue;
    }

    public long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public int getMinValue() {
        return minValue;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }
}
