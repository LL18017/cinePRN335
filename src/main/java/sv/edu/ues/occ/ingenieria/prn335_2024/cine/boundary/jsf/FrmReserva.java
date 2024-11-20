package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf;

import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.event.SelectEvent;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersist;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.ReservaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Reserva;

import java.io.Serializable;

@Named
public class FrmReserva extends AbstractFrm<Reserva> implements Serializable {
    @Inject
    ReservaBean reservaBean;
    @Inject
    FacesContext fc;


    //variables para la pagina

    int indiceTab=2;
    @Override
    public void instanciarRegistro() {
        registro=new Reserva();
    }

    @Override
    public FacesContext getFC() {
        return fc;
    }

    @Override
    public AbstractDataPersist<Reserva> getAbstractDataPersist() {
        return reservaBean;
    }

    @Override
    public String getIdByObject(Reserva object) {
        if (object != null) {
            return object.getIdReserva().toString();
        }
        return "";
    }

    @Override
    public Reserva getObjectById(String id) {
        if (id != null && modelo!=null && modelo.getWrappedData()!=null) {
            return modelo.getWrappedData().stream().filter(r->id.equals(r.getIdReserva().toString())).findFirst().orElse(null);
        }
        return null;
    }

    @Override
    public void selecionarFila(SelectEvent<Reserva> event) {

    }

    @Override
    public String paginaNombre() {
        return "Reserva  ";
    }

    //funcionalidad
    public void nextTab() {
        if (indiceTab < 3) {
            indiceTab++;
        }
    }

    //getters y setter
    public int getIndiceTab() {
        return indiceTab;
    }

    public void setIndiceTab(int indiceTab) {
        this.indiceTab = indiceTab;
    }

}
