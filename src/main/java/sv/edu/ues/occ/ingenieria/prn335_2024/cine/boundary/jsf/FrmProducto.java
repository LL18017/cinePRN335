package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf;

import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.event.SelectEvent;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf.rest.WS.ProductoEndPoint;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf.rest.WS.WS;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersist;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.ProductoBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Producto;

import java.io.Serializable;

@Named
@ViewScoped
public class FrmProducto extends AbstractFrm<Producto> implements Serializable {
    @Inject
    ProductoBean productoBean;
    @Inject
    FacesContext facesContext;
    @Inject
    ProductoEndPoint productoEndPoint;

    @Inject
    FrmTipoProducto frmTipoProducto;
    @Override
    public void instanciarRegistro() {
        registro=new Producto();
        registro.setActivo(true);
    }

    @Override
    public FacesContext getFC() {
        return facesContext;
    }

    @Override
    public AbstractDataPersist<Producto> getAbstractDataPersist() {
        return productoBean;
    }

    @Override
    public String getIdByObject(Producto object) {
        if (object!=null){
            return object.getIdProducto().toString();
        }
        return "";
    }

    @Override
    public Producto getObjectById(String id) {
        if (id!=null && modelo.getWrappedData()!=null & modelo!=null){
            return modelo.getWrappedData().stream().filter(p->p.getIdProducto().toString().equals(id)).findFirst()
                    .orElse(null);
        }
        return null;
    }

    @Override
    public void selecionarFila(SelectEvent<Producto> event) {

    }

    @Override
    public String paginaNombre() {
        return "Productos";
    }

    @Override
    public WS getWebsocketController() {
        return productoEndPoint;
    }

    public FrmTipoProducto getFrmTipoProducto() {
        return frmTipoProducto;
    }

    public void setFrmTipoProducto(FrmTipoProducto frmTipoProducto) {
        this.frmTipoProducto = frmTipoProducto;
    }
}
