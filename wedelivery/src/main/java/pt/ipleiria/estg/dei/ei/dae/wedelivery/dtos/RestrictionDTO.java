package pt.ipleiria.estg.dei.ei.dae.wedelivery.dtos;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.entities.Restriction;
import java.util.List;
import java.util.stream.Collectors;

public class RestrictionDTO {
    private long id;
    private String type;
    private double maxValue;
    private double minValue;

    public RestrictionDTO() {
    }

    public RestrictionDTO(long id, String type, double maxValue, double minValue) {
        this.id = id;
        this.type = type;
        this.maxValue = maxValue;
        this.minValue = minValue;
    }

    // Converts an entity to a DTO Restriction class
    public static RestrictionDTO from(Restriction restriction) {
        return new RestrictionDTO(
                restriction.getId(),
                restriction.getType(),
                restriction.getMaxValue(),
                restriction.getMinValue()
        );
    }

    public static List<RestrictionDTO> from(List<Restriction> restrictions) {
        return restrictions.stream().map(RestrictionDTO::from).collect(Collectors.toList());
    }

    public long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public double getMaxValue() {
        return maxValue;
    }

    public double getMinValue() {
        return minValue;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setMaxValue(double maxValue) {
        this.maxValue = maxValue;
    }

    public void setMinValue(double minValue) {
        this.minValue = minValue;
    }
}
