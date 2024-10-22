package pt.ipleiria.estg.dei.ei.dae.wedelivery.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.dtos.ProductDTO;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.dtos.WarehouseDTO;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.ejbs.WarehouseBean;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.entities.Warehouse;

import java.util.List;

@Path("/warehouses")
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON})
public class WarehouseService {

    @EJB
    private WarehouseBean warehouseBean;
    @GET
    @Path("/")
    public Response getAllWarehouses() {
        List<Warehouse> warehouses = warehouseBean.findAll();
        List<WarehouseDTO> warehouseDTOs = WarehouseDTO.from(warehouses);
        return Response.ok(warehouseDTOs).build(); // Devolve DTOs
    }

    @GET
    @Path("{name}")
    public Response getWarehouse(@PathParam("name") String name) {
        Warehouse warehouse = warehouseBean.find(name);
        WarehouseDTO warehouseDTO = WarehouseDTO.from(warehouse);
        return Response.ok(warehouseDTO).build(); // Devolve DTO
    }

    @GET
    @Path("{name}/products")
    public Response getWarehouseAllProducts(@PathParam("name") String name) {
        Warehouse warehouse = warehouseBean.find(name);
        List<ProductDTO> productDTOs = ProductDTO.from(warehouse.getProducts());
        return Response.ok(productDTOs).build(); // Devolve lista de ProductDTOs
    }
}
