package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf.rest.resouce;

import jakarta.inject.Inject;
import jakarta.ws.rs.Path;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersist;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.ProductoBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Producto;

@Path("producto")
public class ProductoResources extends AbstracDataSource<Producto> {
    @Inject
    ProductoBean productoBean;
    @Override
    public AbstractDataPersist<Producto> getBean() {
        return productoBean;
    }

    @Override
    public Integer getId(Producto registro) {
        if (registro.getIdProducto() != null){

        return registro.getIdProducto().intValue();
        }
        return null;
    }

    @Override
    public String getClassName() {
        return "Producto";
    }
}
