package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf.rest.resouce;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.ws.rs.Path;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersist;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.PagoDetalleBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.PagoDetalle;

import java.io.Serializable;

@Path("pagodetalle")
public class PagoDetalleResource extends AbstracDataSource<PagoDetalle> implements Serializable {
    @Inject
    PagoDetalleBean PagoDetalleBean;


    @Override
    public AbstractDataPersist<PagoDetalle> getBean() {
        return PagoDetalleBean;
    }

    @Override
    public Integer getId(PagoDetalle registro) {
        if (registro.getIdPagoDetalle() != null){
            return registro.getIdPagoDetalle().intValue();
        }
        return null;
    }

    @Override
    public String getClassName() {
        return "PagoDetalle";
    }
}
