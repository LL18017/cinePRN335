package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf.rest.resouce;

import jakarta.inject.Inject;
import jakarta.ws.rs.Path;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersist;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.ReservaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Reserva;

import java.io.Serializable;

@Path("reserva")
public class ReservaResource extends AbstracDataSource<Reserva> implements Serializable {
    @Inject
    ReservaBean reservaBean;

    @Override
    public AbstractDataPersist<Reserva> getBean() {
        return reservaBean;
    }

    @Override
    public Integer getId(Reserva registro) {
        if (registro.getIdReserva() != null){

        return registro.getIdReserva().intValue();
        }
        return null;
    }

    @Override
    public String getClassName() {
        return "Reserva";
    }
}
