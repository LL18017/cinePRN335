package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf.rest.resouce;

import jakarta.inject.Inject;
import jakarta.ws.rs.Path;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersist;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.ReservaDetalleBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.ReservaDetalle;

import java.io.Serializable;

@Path("reservadetalle")
public class ReservaDetalleResource extends AbstracDataSource<ReservaDetalle> implements Serializable {
    @Inject
    ReservaDetalleBean reservaDetalleBean;
    @Override
    public AbstractDataPersist<ReservaDetalle> getBean() {
        return reservaDetalleBean;
    }

    @Override
    public Integer getId(ReservaDetalle registro) {
        if (registro.getIdReservaDetalle() != null){
            return registro.getIdReservaDetalle().intValue();
        }
        return null;
    }

    @Override
    public String getClassName() {
        return "ReservaDetalle";
    }
}
