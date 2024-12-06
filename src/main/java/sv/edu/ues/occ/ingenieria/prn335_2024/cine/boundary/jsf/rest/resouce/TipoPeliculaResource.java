package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf.rest.resouce;

import jakarta.inject.Inject;
import jakarta.ws.rs.Path;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersist;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.TipoPeliculaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoPelicula;

import java.io.Serializable;

@Path("tipopelicula")
public class TipoPeliculaResource extends AbstracDataSource<TipoPelicula> implements Serializable {
    @Inject
    TipoPeliculaBean tipoPeliculaBean;
    @Override
    public AbstractDataPersist<TipoPelicula> getBean() {
        return tipoPeliculaBean;
    }

    @Override
    public Integer getId(TipoPelicula registro) {
        if (registro.getIdTipoPelicula() != null){
            return registro.getIdTipoPelicula();
        }
        return null;
    }

    @Override
    public String getClassName() {
        return "TipoPelicula";
    }
}
