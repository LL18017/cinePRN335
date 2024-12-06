package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf.rest.resouce;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersist;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AsientoBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.SalaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Asiento;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Programacion;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Sala;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("asiento")
public class AsientoResource extends AbstracDataSource<Asiento> implements Serializable {
    @Inject
    AsientoBean asientoBean;
    @Override
    public AbstractDataPersist<Asiento> getBean() {
        return asientoBean;
    }

    @Override
    public Integer getId(Asiento registro) {
        return registro.getIdAsiento().intValue();
    }

    @Override
    public String getClassName() {
        return "Asiento";
    }


    @Path("/libres")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAsientos(
            @QueryParam("idSala") Integer idSala,
            @QueryParam("idProgramacion") Integer idProgramacion) {

        if (idSala == null || idProgramacion == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Los parámetros 'idSala' y 'idProgramacion' son requeridos.")
                    .build();
        }

        try {
            List<Asiento> programaciones = asientoBean.findAsientosBySalaandProgramacion(new Sala(idSala),
                    new Programacion(idProgramacion.longValue()));

            if (programaciones != null && !programaciones.isEmpty()) {
                return Response.ok(programaciones).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("No se encontraron programaciones con los parámetros proporcionados.")
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al procesar la solicitud: " + e.getMessage())
                    .build();
        }
    }
}
