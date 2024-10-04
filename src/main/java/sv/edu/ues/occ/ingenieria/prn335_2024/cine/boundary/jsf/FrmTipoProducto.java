package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf;

import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersist;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.TipoProductoBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoProducto;

import java.io.Serializable;
import java.util.stream.Collectors;

@Named
@ViewScoped
public class FrmTipoProducto extends AbstractFrm<TipoProducto> implements Serializable {
    @Inject
    FacesContext fc;
    @Inject
    TipoProductoBean tpBean;
    @Override
    public void instanciarRegistro() {
        this.registro=new TipoProducto();
    }

    @Override
    public FacesContext getFC() {
        return fc;
    }

    @Override
    public AbstractDataPersist<TipoProducto> getAbstractDataPersist() {
        return tpBean;
    }

    @Override
    public void btnSelecionarRegistroHandler(Object id) {

    }

    @Override
    public Object getIdEntity() {
        return 1;
    }

    @Override
    public String getIdObject(TipoProducto object) {
        if (object != null && object.getIdTipoProducto() !=null){
            return object.getIdTipoProducto().toString();
        }
        return null;
    }

    @Override
    public TipoProducto getObjectId(String id) {
        if (id != null && this.modelo != null && this.modelo.getWrappedData() != null) {
            return modelo.getWrappedData().stream().filter(r -> r.getIdTipoProducto().toString().equals(id)).toList().getFirst();

        }
        return null;
    }
}
