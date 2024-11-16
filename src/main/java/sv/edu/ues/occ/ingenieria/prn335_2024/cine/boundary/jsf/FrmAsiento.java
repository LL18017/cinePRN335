package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf;

import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import org.primefaces.event.SelectEvent;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersist;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Asiento;

import jakarta.faces.view.ViewScoped;
import java.io.Serializable;
@Named
@ViewScoped
public class FrmAsiento extends AbstractFrm<Asiento> implements Serializable {
    @Override
    public void instanciarRegistro() {
        registro = new Asiento();
    }

    @Override
    public FacesContext getFC() {
        return null;
    }

    @Override
    public AbstractDataPersist<Asiento> getAbstractDataPersist() {
        return null;
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

    }

    @Override
    public String paginaNombre() {
        return "Asientos";
    }
}
