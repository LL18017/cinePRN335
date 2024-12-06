package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf.rest.resouce;

import jakarta.inject.Inject;
import jakarta.ws.rs.Path;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersist;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.PeliculaCarracteristicaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.PeliculaCaracteristica;

import java.io.Serializable;

@Path("peliculacaracterisctica")
public class PeliculaCaracteristicaResource extends AbstracDataSource<PeliculaCaracteristica> implements Serializable {
    @Inject
    PeliculaCarracteristicaBean peliculaCarracteristicaBean;
    @Override
    public AbstractDataPersist<PeliculaCaracteristica> getBean() {
        return peliculaCarracteristicaBean;
    }

    @Override
    public Integer getId(PeliculaCaracteristica registro) {
        if (registro.getIdPeliculaCaracteristica()!=null){
            return registro.getIdPeliculaCaracteristica().intValue();
        }
        return null;
    }

    @Override
    public String getClassName() {
        return "PeliculaCaracteristica";
    }
}
