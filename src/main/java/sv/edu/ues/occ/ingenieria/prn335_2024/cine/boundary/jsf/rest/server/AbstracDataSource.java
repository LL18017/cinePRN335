package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf.rest.server;

import jakarta.enterprise.inject.Default;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersist;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public abstract class AbstracDataSource<T> implements Serializable {
    public abstract AbstractDataPersist<T> getBean();
    public abstract Integer getId(T registro);
    public abstract String getClassName();

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response findRange(
            @QueryParam("first")
            @DefaultValue("0")
            int first,
            @QueryParam("max")
            @DefaultValue("20")
            int max
    ) {
        try {
            if (first >= 0 && max >= 0 && max <=50) {

            List<T> encontrados= getBean().findRange(first,max);
            Integer total=getBean().count();
                System.out.println("lalalalal");
                Response.ResponseBuilder builder = Response.ok(encontrados).
                        header("Total-records", total).
                        type(MediaType.APPLICATION_JSON);
                return builder.build();
            }else{
                return Response.status(422).header("wrong parameter, first:", first+",max: "+max  ).header("wrong parameter : max","s").build();
            }
        }catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            return Response.status(500).entity(e.getMessage()).build();
        }
    }

    @Path("/{id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response findById(@PathParam("id") Integer id) {
        if (id != null) {
            try {
                T encontrado= getBean().findById(id);
                if (encontrado != null) {
                   Response.ResponseBuilder builder = Response.ok(encontrado);
                   return builder.build();
                }
                return Response.status(404).header("not-found-id", id).build();
            }catch (Exception e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
                return Response.status(500).entity(e.getMessage()).build();
            }
        }
            return Response.status(422).header("wrong-parameter : id", id).build();
    }


    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response create(T registro, @Context UriInfo uriInfo){
        System.out.println(getId(registro));
        if (registro != null && getId(registro) == null ) {
            System.out.println(registro.toString());
            try {
                getBean().create(registro);
                    System.out.println("se ha creado");
                if (getId(registro) !=null) {

                    UriBuilder uriBuilder= uriInfo.getAbsolutePathBuilder();
                    uriBuilder.path(String.valueOf(getId(registro)));
                    return Response.created(uriBuilder.build()).build();
                }
                return Response.status(500).header("process-error","Record couldnt be created").build();
            }catch (Exception e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
                return Response.status(500).entity(e.getMessage()).build();
            }
        }
        return Response.status(500).header("Wrong-parameter", registro).build();
    }






}