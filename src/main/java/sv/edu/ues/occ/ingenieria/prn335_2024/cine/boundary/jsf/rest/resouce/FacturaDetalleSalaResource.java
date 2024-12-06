package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf.rest.resouce;


import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.ws.rs.Path;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersist;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.FacturaDetalleSalaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.FacturaDetalleSala;

import java.io.Serializable;

@Path("facturadetallesala")
public class FacturaDetalleSalaResource extends AbstracDataSource<FacturaDetalleSala> implements Serializable {
    @Inject
    FacturaDetalleSalaBean facturaDetalleSala;
    @Override
    public AbstractDataPersist<FacturaDetalleSala> getBean() {
        return facturaDetalleSala;
    }

    @Override
    public Integer getId(FacturaDetalleSala registro) {
        if (registro.getIdFacturaDetalleSala() != null) {
            return registro.getIdFacturaDetalleSala().intValue();
        }
        return null;
    }

    @Override
    public String getClassName() {
        return "FacturaDetalleSala";
    }
}
