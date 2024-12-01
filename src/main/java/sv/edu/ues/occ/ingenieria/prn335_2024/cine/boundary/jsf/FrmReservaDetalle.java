package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf;

import jakarta.enterprise.context.Dependent;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.event.SelectEvent;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf.rest.WS.ReservaDetalleEndPoint;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf.rest.WS.WS;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersist;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.ReservaDetalleBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.ReservaDetalle;

import java.io.Serializable;
@Named
@Dependent
public class FrmReservaDetalle extends AbstractFrm<ReservaDetalle> implements Serializable {
    @Inject
    ReservaDetalleBean reservaDetalleBean;
    @Inject
    FacesContext fc;
    @Inject
    ReservaDetalleEndPoint reservaDetalleEndPoint;

    @Override
    public void instanciarRegistro() {
        registro=new ReservaDetalle();
    }

    @Override
    public FacesContext getFC() {
        return fc;
    }

    @Override
    public AbstractDataPersist<ReservaDetalle> getAbstractDataPersist() {
        return reservaDetalleBean;
    }

    @Override
    public String getIdByObject(ReservaDetalle object) {
        if (object != null) {
            return object.getIdReservaDetalle().toString();
        }
        return "";
    }

    @Override
    public ReservaDetalle getObjectById(String id) {
        if (id != null && modelo.getWrappedData()!=null && modelo!=null) {
            return modelo.getWrappedData().stream().filter(rd->id.equals(rd.getIdReservaDetalle().toString())).findFirst().orElse(null);
        }
        return null;
    }

    @Override
    public void selecionarFila(SelectEvent<ReservaDetalle> event) {

    }

    @Override
    public String paginaNombre() {
        return "Reserva Detalle";
    }

    @Override
    public WS getWebsocketController() {
        return reservaDetalleEndPoint;
    }
}
