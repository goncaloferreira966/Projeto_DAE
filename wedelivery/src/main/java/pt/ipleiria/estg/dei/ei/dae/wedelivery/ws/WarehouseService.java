package pt.ipleiria.estg.dei.ei.dae.wedelivery.ws;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.dtos.ProductDTO;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.dtos.SupplierDTO;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.dtos.WarehouseDTO;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.ejbs.ProductBean;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.ejbs.SupplierBean;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.ejbs.WarehouseBean;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.entities.Warehouse;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.security.Authenticated;

import java.util.List;

@Path("/warehouses")
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON})
@Authenticated
@RolesAllowed({"Manager", "Operator", "Supplier"})
public class WarehouseService {
    @Context
    private SecurityContext securityContext;
    @EJB
    private WarehouseBean warehouseBean;

    @EJB
    private ProductBean productBean;

    @EJB
    private SupplierBean supplierBean;

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
        var warehouse = warehouseBean.find(name);
        var warehouseDTO = WarehouseDTO.from(warehouse);
        var products = productBean.findAllProductsByWarehouse(name);
        var productDTOs = ProductDTO.from(products);
        warehouseDTO.setProducts(productDTOs);
        return Response.ok(warehouseDTO).build(); // Devolve lista de ProductDTOs
    }

    @GET
    @Path("{name}/products/{id}")
    public Response getWarehouseProductById(@PathParam("name") String name, @PathParam("id") long id) {
        var warehouse = warehouseBean.find(name);
        var warehouseDTO = WarehouseDTO.from(warehouse);
        var product = productBean.findById(id);
        var productDTO = ProductDTO.from(product);
        var supplier = supplierBean.find(product.getSupplier().getUsername());
        var supplierDTO = SupplierDTO.from(supplier);
        productDTO.setSupplier(supplierDTO);
        warehouseDTO.addProduct(productDTO);
        return Response.ok(warehouseDTO).build(); // Devolve WarehouseDTO
    }
}
