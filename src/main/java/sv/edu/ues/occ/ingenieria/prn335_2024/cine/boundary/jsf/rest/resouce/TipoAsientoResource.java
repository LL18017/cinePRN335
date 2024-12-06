package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf.rest.resouce;

import jakarta.inject.Inject;
import jakarta.ws.rs.Path;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersist;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.TipoAsientoBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoAsiento;

import java.io.Serializable;

@Path("tipoasiento")
public class TipoAsientoResource extends AbstracDataSource<TipoAsiento> implements Serializable {
    @Inject
    TipoAsientoBean TipoAsientoBean;
    @Override
    public AbstractDataPersist<TipoAsiento> getBean() {
        return TipoAsientoBean;
    }

    @Override
    public Integer getId(TipoAsiento registro) {
        if(registro.getIdTipoAsiento()!=null){
            return registro.getIdTipoAsiento();
        }
        return null;
    }

    @Override
    public String getClassName() {
        return "TipoAsiento";
    }
}
