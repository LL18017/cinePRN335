package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf.rest.resouce;

import jakarta.inject.Inject;
import jakarta.ws.rs.Path;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersist;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.TipoPagoBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoPago;

import java.io.Serializable;

@Path("tipopago")
public class TipoPagoResource extends AbstracDataSource<TipoPago> implements Serializable {
    @Inject
    TipoPagoBean tipoPagoBean;
    @Override
    public AbstractDataPersist<TipoPago> getBean() {
        return tipoPagoBean;
    }

    @Override
    public Integer getId(TipoPago registro) {
        if (registro.getIdTipoPago() != null){
            return registro.getIdTipoPago();
        }
        return null;
    }

    @Override
    public String getClassName() {
        return "TipoPago";
    }
}
