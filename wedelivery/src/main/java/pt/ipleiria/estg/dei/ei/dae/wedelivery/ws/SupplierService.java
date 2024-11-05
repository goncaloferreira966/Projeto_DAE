package pt.ipleiria.estg.dei.ei.dae.wedelivery.ws;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.dtos.ClientDTO;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.dtos.OrderDTO;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.dtos.ProductDTO;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.dtos.SupplierDTO;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.ejbs.ProductBean;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.ejbs.SupplierBean;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.security.Authenticated;

import java.util.List;

@Path("/suppliers")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
@Authenticated
@RolesAllowed({"Manager", "Operator"})
public class SupplierService {
    @Context
    private SecurityContext securityContext;
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
    @RolesAllowed({"Supplier"})//Acesso apenas a este método
    public Response getSupplier(@PathParam("username") String username) {
        var principal = securityContext.getUserPrincipal();
        if(!principal.getName().equals(username)) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        var supplier = supplierBean.findWithProducts(username);
        var supplierDTO = SupplierDTO.from(supplier);
        supplierDTO.setProducts(ProductDTO.from(supplier.getProducts()));
        return Response.ok(supplierDTO).build();
    }

    @GET
    @Path("{username}/products/{id}")
    @RolesAllowed({"Supplier"})//Acesso apenas a este método
    public Response getSupplierProductById(@PathParam("username") String username, @PathParam("id") long id) {
        var principal = securityContext.getUserPrincipal();
        if(!principal.getName().equals(username)) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        var supplier = supplierBean.findWithProductId(username, id);
        var supllierDTO = SupplierDTO.from(supplier);
        var product = productBean.findById(id);
        var productDTO = ProductDTO.from(product);
        supllierDTO.addProduct(productDTO);
        return Response.ok(supllierDTO).build();
    }

    @GET
    @Path("{username}/products")
    @RolesAllowed({"Supplier"})//Acesso apenas a este método
    public Response getSupplierProducts(@PathParam("username") String username) {
        var principal = securityContext.getUserPrincipal();
        if(!principal.getName().equals(username)) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        var supplier = supplierBean.findWithProducts(username);
        var supplierDTO = SupplierDTO.from(supplier);
        supplierDTO.setProducts(ProductDTO.from(supplier.getProducts()));
        return Response.ok(supplierDTO).build();
    }
}
