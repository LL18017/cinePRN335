package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf.rest.resouce;

import jakarta.inject.Inject;
import jakarta.ws.rs.Path;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersist;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.FacturaDetalleProductoBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.FacturaDetalleProducto;

@Path("facturadetalleproducto")
public class FacturaDetalleProductoResource extends AbstracDataSource<FacturaDetalleProducto> {
    @Inject
    FacturaDetalleProductoBean facturaDetalleProductoBean;


    @Override
    public AbstractDataPersist<FacturaDetalleProducto> getBean() {
        return facturaDetalleProductoBean;
    }

    @Override
    public Integer getId(FacturaDetalleProducto registro) {
        return 0;
    }

    @Override
    public String getClassName() {
        return "idFacturaDetalleProducto";
    }
}
