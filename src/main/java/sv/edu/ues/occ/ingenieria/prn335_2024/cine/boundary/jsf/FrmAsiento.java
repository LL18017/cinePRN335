package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.event.SelectEvent;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersist;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AsientoBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Asiento;

import jakarta.faces.view.ViewScoped;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Factura;

import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class FrmAsiento extends AbstractFrm<Asiento> implements Serializable {

    @Inject
    AsientoBean asientoBean;
    @Inject
    FacesContext fc;

    @Override
    public void instanciarRegistro() {
        registro = new Asiento();
    }

    @Override
    public FacesContext getFC() {
        return fc;
    }

    @Override
    public AbstractDataPersist<Asiento> getAbstractDataPersist() {
        return asientoBean;
    }

    @Override
    public String getIdByObject(Asiento object) {
        if (object != null) {
            return object.getIdAsiento().toString();
        }
        return "";
    }

    @Override
    public Asiento getObjectById(String id) {
        if (id!=null && modelo!=null && modelo.getWrappedData()!=null) {
            return modelo.getWrappedData().stream().filter(a->id.equals(a.getIdAsiento().toString())).findFirst().orElse(null);
        }
        return null;
    }

    @Override
    public void selecionarFila(SelectEvent<Asiento> event) {
        Asiento facturaSelected =  event.getObject();
        FacesMessage mensaje=new FacesMessage("se selecionado la factura n°",facturaSelected.getIdAsiento().toString());
        fc.addMessage(null, mensaje);
        this.estado=ESTADO_CRUD.MODIFICAR;

    }

    @Override
    public String paginaNombre() {
        return "Asientos";
    }

    @Override
    public int contar() {
        return asientoBean.countAsientos(registro.getIdSala().getIdSala());
    }

    public List<Asiento> cargarAsiento(int first, int max) {
        return  asientoBean.findIdAsientoBySala(registro.getIdSala().getIdSala(), first, max);
    }
}
