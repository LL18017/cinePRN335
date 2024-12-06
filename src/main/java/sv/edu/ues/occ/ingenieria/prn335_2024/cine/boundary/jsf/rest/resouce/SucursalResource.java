package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf.rest.resouce;

import jakarta.inject.Inject;
import jakarta.ws.rs.Path;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersist;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.SucursalBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Sucursal;

import java.io.Serializable;

@Path("sucursal")
public class SucursalResource extends AbstracDataSource<Sucursal> implements Serializable {
    @Inject
    SucursalBean sucursalBean;
    @Override
    public AbstractDataPersist<Sucursal> getBean() {

        return sucursalBean;
    }

    @Override
    public Integer getId(Sucursal registro) {
        if (registro!=null){
            return registro.getIdSucursal();
        }
        return null;
    }

    @Override
    public String getClassName() {
        return "Sucursal";
    }
}
