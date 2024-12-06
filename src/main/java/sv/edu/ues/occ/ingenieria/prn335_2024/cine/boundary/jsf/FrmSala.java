package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ActionEvent;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf.rest.WS.SalaEndPoint;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf.rest.WS.WS;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.*;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Sala;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Sucursal;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


@Named
@ViewScoped
public class FrmSala extends AbstractFrm<Sala> {
    @Inject
    SalaBean salaBean;
    @Inject
    SucursalBean sucursalBean;
    @Inject
    ProgramacionBean programacionBean;
    @Inject
    SalaCaracteristicaBean salaCaracteristicaBean;
    @Inject
    FacesContext fc;
    @Inject
    FrmSalaCaracteristica frmSalaCaracteristica;
    @Inject
    FrmProgramacion frmProgramacion;
    @Inject
    FrmAsiento frmAsiento;
    @Inject
    SalaEndPoint salaEndPoint;



    //propiedades de sala

    Sucursal sucursalSelecionada;
    Integer idSucursalSelecionada;
    List<Sucursal> sucursalesDisponibles;




    @Override
    public void instanciarRegistro() {
            registro = new Sala();
            registro.setIdSucursal(new Sucursal());
            registro.setActivo(true);
    }

    @Override
    public void inicioRegistros() {
        super.inicioRegistros();
        cargarSucursales();
    }

    @Override
    public FacesContext getFC() {
        return fc;
    }

    @Override
    public AbstractDataPersist<Sala> getAbstractDataPersist() {
        return salaBean;
    }

    @Override
    public String getIdByObject(Sala object) {
        if (object != null) {
            return object.getIdSala().toString();
        }
        return "";
    }

    @Override
    public Sala getObjectById(String id) {
        if (id != null && modelo != null && modelo.getWrappedData() != null) {
            return modelo.getWrappedData().stream()
                    .filter(s -> s.getIdSala().toString().equals(id))
                    .findFirst().orElse(null);
        }
        return null;
    }

    @Override
    public void selecionarFila(SelectEvent<Sala> event) {

        enviarMensaje(FacesMessage.SEVERITY_INFO,"se ha selecionado: "+registro.getNombre());
        estado=ESTADO_CRUD.MODIFICAR;
    }

    @Override
    public String paginaNombre() {
        return "Sala";
    }

    @Override
    public WS getWebsocketController() {
        return salaEndPoint;
    }

    //funcionaliddaes de Sala

    public void cambiarTab(TabChangeEvent e){
        String titulo = e.getTab().getTitle();
        if (titulo.equals("generalidades")){
            inicioRegistros();
        } else if (titulo.equals("Caracteristicas")) {
            frmSalaCaracteristica.registro=null;
            frmSalaCaracteristica.estado=ESTADO_CRUD.NINGUNO;
            frmSalaCaracteristica.setSalaSelecionada(registro);
            frmSalaCaracteristica.inicioRegistros();
        }else if (titulo.equals("Asientos")) {
            frmAsiento.registro=null;
            frmAsiento.estado=ESTADO_CRUD.NINGUNO;
            frmAsiento.setSalaSelecionada(registro);
            frmAsiento.inicioRegistros();
        } else if (titulo.equals("programaciones")) {
            frmProgramacion.registro=null;
            frmProgramacion.estado=ESTADO_CRUD.NINGUNO;
            frmProgramacion.setSalaSelecionada(registro);
            frmProgramacion.inicioRegistros();
        }


    }

    public void cargarSucursales(){
        try {
            sucursalesDisponibles=sucursalBean.findAll();
        }catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE,e.getMessage(),e);
            enviarMensaje(FacesMessage.SEVERITY_ERROR,"Error al cargar datos de sucursal");
        }
    }

    public void pruebas(){
        enviarMensaje(FacesMessage.SEVERITY_INFO,"selecionado "+sucursalSelecionada.getNombre());

    }



    public Integer getIdSucursalSelecionada() {
        return idSucursalSelecionada;
    }

    public void setIdSucursalSelecionada(Integer idSucursalSelecionada) {
        this.idSucursalSelecionada = idSucursalSelecionada;
        sucursalSelecionada=sucursalesDisponibles.stream().filter(s->s.getIdSucursal().equals(idSucursalSelecionada)).findFirst().orElse(null);
        registro.setIdSucursal(sucursalSelecionada);
    }

    public Sucursal getSucursalSelecionada() {
        return sucursalSelecionada;
    }

    public void setSucursalSelecionada(Sucursal sucursalSelecionada) {
        this.sucursalSelecionada = sucursalSelecionada;
    }

    public List<Sucursal> getSucursalesDisponibles() {
        return sucursalesDisponibles;
    }

    public void setSucursalesDisponibles(List<Sucursal> sucursalesDisponibles) {
        this.sucursalesDisponibles = sucursalesDisponibles;
    }

    public FrmSalaCaracteristica getFrmSalaCaracteristica() {
        return frmSalaCaracteristica;
    }

    public void setFrmSalaCaracteristica(FrmSalaCaracteristica frmSalaCaracteristica) {
        this.frmSalaCaracteristica = frmSalaCaracteristica;
    }

    public FrmAsiento getFrmAsiento() {
        return frmAsiento;
    }

    public void setFrmAsiento(FrmAsiento frmAsiento) {
        this.frmAsiento = frmAsiento;
    }

    public FrmProgramacion getFrmProgramacion() {
        return frmProgramacion;
    }

    public void setFrmProgramacion(FrmProgramacion frmProgramacion) {
        this.frmProgramacion = frmProgramacion;
    }

    @Override
    public void btnCancelarHandler(ActionEvent actionEvent) {
        frmSalaCaracteristica.registro=null;
        frmSalaCaracteristica.estado=ESTADO_CRUD.NINGUNO;
        frmAsiento.registro=null;
        frmAsiento.estado=ESTADO_CRUD.NINGUNO;
        super.btnCancelarHandler(actionEvent);
    }

}
