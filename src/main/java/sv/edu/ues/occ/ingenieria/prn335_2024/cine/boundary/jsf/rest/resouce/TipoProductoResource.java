package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf.rest.resouce;

import jakarta.inject.Inject;
import jakarta.ws.rs.Path;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersist;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.TipoProductoBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoProducto;

import java.io.Serializable;

@Path("tipoproducto")
public class TipoProductoResource extends AbstracDataSource<TipoProducto> implements Serializable {
    @Inject
    TipoProductoBean tipoProductoBean;
    @Override
    public AbstractDataPersist<TipoProducto> getBean() {
        return tipoProductoBean;
    }

    @Override
    public Integer getId(TipoProducto registro) {
        return registro.getIdTipoProducto();
    }

    @Override
    public String getClassName() {
        return "TipoProducto";
    }
}
