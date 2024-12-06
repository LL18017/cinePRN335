package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf.rest.resouce;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersist;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.ProgramacionBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.SalaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Programacion;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Sala;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("programacion")
public class ProgramacionResource extends AbstracDataSource<Programacion> implements Serializable {

    @Inject
    ProgramacionBean programacionBean;
    @Inject
    SalaBean salaBean;

    @Override
    public AbstractDataPersist<Programacion> getBean() {
        return programacionBean;
    }

    @Override
    public Integer getId(Programacion registro) {
        if (registro.getIdProgramacion()!=null){

        return registro.getIdProgramacion().intValue();
        }
        return null;
    }

    @Override
    public String getClassName() {
        return "Programacion";
    }

    @Path("/sala/{id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response findProgramacionByIdSala(@PathParam("id") Integer id) {
        if (id != null) {
            try {
                List<Programacion> encontrados= programacionBean.getProgramacionByIdSalaAfter(new Sala(id));
                if (!encontrados.isEmpty()) {
                    Response.ResponseBuilder builder = Response.ok(encontrados);
                    return builder.build();
                }
                return Response.status(400).header("not-found-id-sala", id).build();
            }catch (Exception e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
                return Response.status(500).entity(e.getMessage()).build();
            }
        }
        return Response.status(500).build();

    }
}
