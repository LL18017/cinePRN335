package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf.rest.server;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.MediaType;

@ApplicationPath("v1")
public class CineApp extends Application {

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public void getCine() {
        System.out.println("aigilg");
    }
}
