package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf.rest.server;

import jakarta.inject.Inject;
import jakarta.ws.rs.Path;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersist;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.TipoSalaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoSala;

import java.io.Serializable;

@Path("tiposala")
public class TipoSalaResorce extends AbstracDataSource<TipoSala> implements Serializable {
    @Inject
    TipoSalaBean tipoSalaBean;
    @Override
    public AbstractDataPersist<TipoSala> getBean() {
        return tipoSalaBean;
    }

    @Override
    public Integer getId(TipoSala registro) {
        return registro.getIdTipoSala();
    }

    @Override
    public String getClassName() {
        return "TipoSala";
    }
}
