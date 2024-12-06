package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf.rest.resouce;

import jakarta.inject.Inject;
import jakarta.ws.rs.Path;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersist;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.SalaCaracteristicaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.SalaCaracteristica;

import java.io.Serializable;

@Path("salacaracteristica")
public class SalaCaracteristicaResource extends AbstracDataSource<SalaCaracteristica> implements Serializable {
    @Inject
    SalaCaracteristicaBean salaCaracteristicaBean;
    @Override
    public AbstractDataPersist<SalaCaracteristica> getBean() {
        return salaCaracteristicaBean;
    }

    @Override
    public Integer getId(SalaCaracteristica registro) {
        if (registro.getIdTipoSala()!=null){
            return registro.getIdSalaCaracteristica().intValue();
        }
        return null;
    }

    @Override
    public String getClassName() {
        return "SalaCaracteristica";
    }
}
