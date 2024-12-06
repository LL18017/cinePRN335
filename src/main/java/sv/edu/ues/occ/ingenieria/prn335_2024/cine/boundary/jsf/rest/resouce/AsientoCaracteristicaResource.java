package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf.rest.resouce;

import jakarta.inject.Inject;
import jakarta.ws.rs.Path;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersist;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AsientoCaracteristicaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.AsientoCaracteristica;

import java.io.Serializable;

@Path("asientocaracteristica")
public class AsientoCaracteristicaResource extends AbstracDataSource<AsientoCaracteristica> implements Serializable {
   @Inject
    AsientoCaracteristicaBean asientoCaracteristicaBean;

    @Override
    public AbstractDataPersist<AsientoCaracteristica> getBean() {
        return asientoCaracteristicaBean;
    }

    @Override
    public Integer getId(AsientoCaracteristica registro) {
        if (registro.getIdAsientoCaracteristica()!=null){
            return registro.getIdAsientoCaracteristica().intValue();
        }
        return null;
    }

    @Override
    public String getClassName() {
        return "AsientoCaracteristica";
    }
}
