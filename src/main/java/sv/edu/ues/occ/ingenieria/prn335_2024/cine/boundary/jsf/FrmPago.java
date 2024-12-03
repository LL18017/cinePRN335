package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.event.SelectEvent;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf.rest.WS.PagoEndPoint;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf.rest.WS.WS;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersist;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.PagoBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Pago;

import java.io.Serializable;

@Named
@ViewScoped
public class FrmPago extends AbstractFrm<Pago> implements Serializable {
    @Inject
    FrmPago frmPago;
    @Inject
    PagoBean pagoBean;
    @Inject
    FacesContext fc;
    @Inject
    PagoEndPoint pagoEndPoint;
    @Override
    public void instanciarRegistro() {
        registro = new Pago();
    }

    @Override
    public void inicioRegistros() {
        super.inicioRegistros();
    }

    @Override
    public FacesContext getFC() {
        return fc;
    }

    @Override
    public AbstractDataPersist<Pago> getAbstractDataPersist() {
        return pagoBean;
    }

    @Override
    public String getIdByObject(Pago object) {
        if (object != null) {
            return object.getIdPago().toString();
        }
        return "";
    }

    @Override
    public Pago getObjectById(String id) {
        if (id != null && modelo.getWrappedData()!=null && modelo!=null) {
            return modelo.getWrappedData().stream().filter(p->id.equals(p.getIdPago().toString())).findFirst().
                    orElse(null);
        }
        return null;
    }

    @Override
    public void selecionarFila(SelectEvent<Pago> event) {
            enviarMensaje(FacesMessage.SEVERITY_INFO,"se ha selecionado "+registro.getIdPago());
            estado=ESTADO_CRUD.MODIFICAR;
    }

    @Override
    public String paginaNombre() {
        return "Pago";
    }

    @Override
    public WS getWebsocketController() {
        return pagoEndPoint;
    }
}
