package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf.rest.resouce;

import jakarta.inject.Inject;
import jakarta.ws.rs.Path;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersist;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.TipoReservaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoReserva;

import java.io.Serializable;

@Path("tiporeserva")
public class TipoReservaResource extends AbstracDataSource<TipoReserva> implements Serializable {
    @Inject
    TipoReservaBean tipoReservaBean;
    @Override
    public AbstractDataPersist<TipoReserva> getBean() {
        return tipoReservaBean;
    }

    @Override
    public Integer getId(TipoReserva registro) {
        return registro.getIdTipoReserva();
    }

    @Override
    public String getClassName() {
        return "TipoReserva";
    }
}
