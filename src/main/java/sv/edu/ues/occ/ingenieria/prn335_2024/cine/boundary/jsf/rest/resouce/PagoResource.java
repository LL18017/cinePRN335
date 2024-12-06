package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf.rest.resouce;

import jakarta.inject.Inject;
import jakarta.ws.rs.Path;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersist;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.PagoBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Pago;

import java.io.Serializable;

@Path("pago")
public class PagoResource extends AbstracDataSource<Pago> implements Serializable {
    @Inject
    PagoBean pagoBean;
    @Override
    public AbstractDataPersist<Pago> getBean() {
        return pagoBean;
    }

    @Override
    public Integer getId(Pago registro) {
        if (registro.getIdPago()!=null){
            return registro.getIdPago().intValue();
        }
        return null;
    }

    @Override
    public String getClassName() {
        return "Pago";
    }
}
