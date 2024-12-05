package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf.rest.resouce;

import jakarta.inject.Inject;
import jakarta.ws.rs.Path;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersist;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AsientoBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.SalaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Asiento;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Sala;

import java.io.Serializable;

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
}
