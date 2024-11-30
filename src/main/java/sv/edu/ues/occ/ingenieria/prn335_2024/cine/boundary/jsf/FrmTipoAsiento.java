package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.event.SelectEvent;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf.WS.WS;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersist;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.TipoAsientoBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoAsiento;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoProducto;

import java.io.Serializable;
import java.util.logging.Logger;

@Named
@ViewScoped
public class FrmTipoAsiento extends AbstractFrm<TipoAsiento> implements Serializable {

    @Inject
    FacesContext fc;
    @Inject
    TipoAsientoBean taBean;

    @Override
    public String paginaNombre() {
        return "Tipo Asiento";
    }

    @Override
    public void instanciarRegistro() {
        this.registro = new TipoAsiento();
        registro.setActivo(true);
    }

    @Override
    public FacesContext getFC() {
        return fc;
    }

    @Override
    public AbstractDataPersist<TipoAsiento> getAbstractDataPersist() {
        return taBean;
    }


    public void btnSelecionarRegistroHandler(Object id) {
        if (id!=null){
            this.registro = this.registros.stream().filter(t -> t.getIdTipoAsiento() == id).
                    findFirst().orElse(null);
            this.estado=ESTADO_CRUD.MODIFICAR;
            return;
        }
        this.registro=null;
    }


    @Override
    public String getIdByObject(TipoAsiento object) {
        if (object.getIdTipoAsiento() != null){
            return object.getIdTipoAsiento().toString();
        }
        return null;
    }

    @Override
    public TipoAsiento getObjectById(String id) {
        if (id!=null && this.modelo!=null && this.modelo.getWrappedData()!=null){
                return this.modelo.getWrappedData().stream().
                        filter(t -> t.getIdTipoAsiento() == Integer.parseInt(id)).findFirst().orElseGet(()->{
                            Logger.getLogger("no hay objeto");
                            return null;
                        });
        }
        Logger.getLogger("problema para obtener objeto  Tipo producto por id");
        return null;

    }

    @Override
    public void selecionarFila(SelectEvent<TipoAsiento> event) {
        TipoAsiento selectedProduct = event.getObject();
        FacesMessage msg = new FacesMessage("Producto seleccionado", selectedProduct.getNombre());
        fc.addMessage(null, msg);

        this.registro=selectedProduct;
        estado=ESTADO_CRUD.MODIFICAR;
    }

    @Override
    public WS getWebsocketController() {
        return null;
    }
}
