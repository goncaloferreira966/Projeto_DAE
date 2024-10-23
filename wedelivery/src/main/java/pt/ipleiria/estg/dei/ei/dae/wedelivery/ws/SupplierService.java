package pt.ipleiria.estg.dei.ei.dae.wedelivery.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.dtos.ClientDTO;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.dtos.OrderDTO;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.dtos.ProductDTO;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.dtos.SupplierDTO;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.ejbs.ProductBean;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.ejbs.SupplierBean;

import java.util.List;

@Path("/suppliers")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class SupplierService {
    @EJB
    private SupplierBean supplierBean;
    @EJB
    private ProductBean productBean;

    @GET
    @Path("/")
    public List<SupplierDTO> getAllSuppliers() {
        return SupplierDTO.from(supplierBean.findAll());
    }


    @GET
    @Path("{username}")
    public Response getSupplier(@PathParam("username") String username) {
        var supplier = supplierBean.findWithProducts(username);
        var supplierDTO = SupplierDTO.from(supplier);
        supplierDTO.setProducts(ProductDTO.from(supplier.getProducts()));
        return Response.ok(supplierDTO).build();
    }

    @GET
    @Path("{username}/products/{id}")
    public Response getSupplierProductById(@PathParam("username") String username, @PathParam("id") long id) {
        var supplier = supplierBean.findWithProductId(username, id);
        var supllierDTO = SupplierDTO.from(supplier);
        var product = productBean.findById(id);
        var productDTO = ProductDTO.from(product);
        supllierDTO.addProduct(productDTO);
        return Response.ok(supllierDTO).build();
    }

    @GET
    @Path("{username}/products/name/{name}")
    public Response getSupplierProductByName(@PathParam("username") String username, @PathParam("name") String name) {
        var supplier = supplierBean.find(username);
        var supplierDTO = SupplierDTO.from(supplier);
        var products = productBean.findByName(name);
        var productDTOs = ProductDTO.from(products);
        supplierDTO.setProducts(productDTOs);
        return Response.ok(supplierDTO).build();
    }

    @GET
    @Path("{username}/products")
    public Response getSupplierProducts(@PathParam("username") String username) {
        var supplier = supplierBean.findWithProducts(username);
        var supplierDTO = SupplierDTO.from(supplier);
        supplierDTO.setProducts(ProductDTO.from(supplier.getProducts()));
        return Response.ok(supplierDTO).build();
    }
}
