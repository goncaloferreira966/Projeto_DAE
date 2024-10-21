package pt.ipleiria.estg.dei.ei.dae.wedelivery.ws;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import jakarta.ejb.EJB;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.dtos.ProductDTO;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.dtos.WarehouseDTO;
import pt.ipleiria.estg.dei.ei.dae.wedelivery.ejbs.ProductBean;
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

    @GET
    @Path("/")
    public Response getAllProducts() {
        List<ProductDTO> productDTOs = ProductDTO.from(productBean.findAll());
        return Response.ok(productDTOs).build();
    }

    @GET
    @Path("{id}")
    public Response getProductById(@PathParam("id") long id) {
        Product product = productBean.findById(id);
        ProductDTO productDTO = ProductDTO.from(product);
        return Response.ok(productDTO).build();
    }


}
