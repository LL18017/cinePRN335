package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf.rest.resouce;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersist;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.SalaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Sala;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("sala")
public class SalaResource extends AbstracDataSource<Sala> implements Serializable {
    @Inject
    SalaBean salaBean;
    @Override
    public AbstractDataPersist<Sala> getBean() {
        return salaBean;
    }

    @Override
    public Integer getId(Sala registro) {
        return registro.getIdSala();
    }

    @Override
    public String getClassName() {
        return "Sala";
    }

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

                List<Sala> encontrados= salaBean.findAll();
                Integer total=getBean().count();

                System.out.println("total:"+total);
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


}
