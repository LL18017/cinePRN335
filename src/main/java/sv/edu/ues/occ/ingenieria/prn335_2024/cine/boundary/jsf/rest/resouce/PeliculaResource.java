package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf.rest.resouce;

import jakarta.inject.Inject;
import jakarta.ws.rs.Path;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersist;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.PeliculaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Pelicula;

import java.io.Serializable;

@Path("pelicula")
public class PeliculaResource extends AbstracDataSource<Pelicula> implements Serializable {
    @Inject
    PeliculaBean peliculaBean;
    @Override
    public AbstractDataPersist<Pelicula> getBean() {
        return peliculaBean;
    }

    @Override
    public Integer getId(Pelicula registro) {
        if (registro.getIdPelicula()!=null){

        return registro.getIdPelicula().intValue();
        }
        return null;
    }

    @Override
    public String getClassName() {
        return "Pelicula";
    }
}
