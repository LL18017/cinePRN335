package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf.rest.resouce;

import jakarta.inject.Inject;
import jakarta.ws.rs.Path;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersist;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.FacturaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Factura;

import java.io.Serializable;

@Path("factura")
public class FacturaResource extends AbstracDataSource<Factura> implements Serializable {
    @Inject
    FacturaBean facturaBean;
    @Override
    public AbstractDataPersist<Factura> getBean() {
        return facturaBean;
    }

    @Override
    public Integer getId(Factura registro) {
        if(registro.getIdFactura()!=null){
            return registro.getIdFactura().intValue();
        }
        return null;
    }

    @Override
    public String getClassName() {
        return "Factura";
    }
}
