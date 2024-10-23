package pt.ipleiria.estg.dei.ei.dae.wedelivery.ws;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import jakarta.ejb.EJB;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.dtos.ProductDTO;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.dtos.SupplierDTO;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.dtos.WarehouseDTO;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.ejbs.ProductBean;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.ejbs.SupplierBean;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.ejbs.WarehouseBean;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.entities.Product;

import java.util.List;

@Path("/products")
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON})
public class ProductService {
    @EJB
    private ProductBean productBean;

    @EJB
    private WarehouseBean warehouseBean;

    @EJB
    private SupplierBean supplierbean;

    @GET
    @Path("/")
    public Response getAllProducts() {
        List<ProductDTO> productDTOs = ProductDTO.from(productBean.findAll());
        return Response.ok(productDTOs).build();
    }

//    @GET
//    @Path("{id}")
//    public Response getProductById(@PathParam("id") long id) {
//        Product product = productBean.findById(id);
//        ProductDTO productDTO = ProductDTO.from(product);
//        return Response.ok(productDTO).build();
//    }
//
//    @GET
//    @Path("name/{name}")
//    public Response getProductByName(@PathParam("name") String name) {
//        List<Product> products = productBean.findByName(name);
//        if (products.isEmpty())
//            return Response.status(Response.Status.NOT_FOUND).build();
//
//        List<ProductDTO> productDTOs = ProductDTO.from(products);
//        return Response.ok(productDTOs).build();
//    }

    @GET
    @Path("{identifier}") // is possible /{id} or /{name}
    public Response getProduct(@PathParam("identifier") String identifier) {
        try {
            long id = Long.parseLong(identifier);
            // If parsing is successful, treat it as an ID
            Product product = productBean.findById(id);
            if (product == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            ProductDTO productDTO = ProductDTO.from(product);
            return Response.ok(productDTO).build();
        } catch (NumberFormatException e) {
            // If parsing fails, treat it as a name
            List<Product> products = productBean.findByName(identifier);
            if (products.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            List<ProductDTO> productDTOs = ProductDTO.from(products);
            return Response.ok(productDTOs).build();
        }
    }

    @GET
    @Path("{id}/details")
    public Response getProductSupplier(@PathParam("id") long id) {
        var product = productBean.findById(id);
        var productDTO = ProductDTO.from(product);
        var supplier = supplierbean.find(product.getSupplier().getUsername());
        var supplierDTO = SupplierDTO.from(supplier);
        var warehouse = warehouseBean.find(product.getWarehouse().getName());
        var warehouseDTO = WarehouseDTO.from(warehouse);
        productDTO.setWarehouse(warehouseDTO);
        productDTO.setSupplier(supplierDTO);
        return Response.ok(productDTO).build();
    }





}
