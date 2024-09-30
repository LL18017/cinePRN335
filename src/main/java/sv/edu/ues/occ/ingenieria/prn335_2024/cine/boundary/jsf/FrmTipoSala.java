package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ActionEvent;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.TipoSalaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoSala;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Named
    @ViewScoped
public class FrmTipoSala implements Serializable {
    @Inject
    TipoSalaBean tsBean;
    ESTADO_CRUD estado;
    @Inject
    FacesContext fc;

    private List<TipoSala> registros;
    private TipoSala registro;

    @PostConstruct
    public void init() {
        estado=ESTADO_CRUD.NINGUNO;
        registros = getUpdateList();
    }

    public List<TipoSala> getRegistros() {
        return registros;
    }

    public void setRegistros(List<TipoSala> registros) {
        this.registros = registros;
    }

    public Integer getSelecionado() {
        if (registro == null) {
            return null;
        }
        return registro.getIdTipoSala();
    }

    public void setSelecionado(Integer selecionado) {
        if (selecionado == null || this.registros == null) {
            this.registro = null;
            this.estado = ESTADO_CRUD.NINGUNO;
            return;
        }

        this.registro = this.registros.stream()
                .filter(r -> r.getIdTipoSala().equals(selecionado))
                .findFirst()
                .orElse(null);

        if (this.registro == null) {
            this.estado = ESTADO_CRUD.NINGUNO;
        }
    }


    public TipoSala getRegistro() {
        return registro;
    }

    public void setRegistro(TipoSala registro) {
        this.registro = registro;
    }

    public ESTADO_CRUD getEstado() {
        return estado;
    }

    public void setEstado(ESTADO_CRUD estado) {
        this.estado = estado;
    }

    public void btnSelecionarRegistroHandler(final Integer idTipoSala) {
        if (idTipoSala!=null){
            this.registro = this.registros.stream().filter(t -> t.getIdTipoSala() == idTipoSala).
                    findFirst().orElse(null);
            this.estado=ESTADO_CRUD.MODIFICAR;
            return;
        }
        this.registro=null;
    }

    public void btnCancelarHandler(ActionEvent actionEvent) {
        this.estado=ESTADO_CRUD.NINGUNO;
        this.registro=null;
    }
    public void btnNuevoHandler(ActionEvent actionEvent) {
        this.registro=new TipoSala();
        this.registro.setActivo(true);
        this.estado=ESTADO_CRUD.CREAR;
    }
    public void btnGuardarHandler(ActionEvent actionEvent) {

           FacesMessage mensaje=new FacesMessage();
        try {
            this.tsBean.create(registro);
            getUpdateList();
            this.registro=null;
            this.estado=ESTADO_CRUD.NINGUNO;
           mensaje.setSeverity(FacesMessage.SEVERITY_INFO);
           mensaje.setSummary("registro guardado");
            fc.addMessage(null,mensaje);
        }catch (Exception e){
            mensaje.setSeverity(FacesMessage.SEVERITY_ERROR);
            mensaje.setSummary("no se pudo guardar el datos");
            mensaje.setDetail(e.getMessage());
            fc.addMessage(null,mensaje);
        }
    }
    public void btnModificarHandler(ActionEvent actionEvent) {
           FacesMessage mensaje=new FacesMessage();

       TipoSala actualizado= this.tsBean.update(registro);
       if (actualizado!=null){
           this.registro=null;
           this.estado=ESTADO_CRUD.NINGUNO;


           mensaje.setSeverity(FacesMessage.SEVERITY_INFO);
           mensaje.setSummary("registro modificado");
           fc.addMessage(null,mensaje);
       }else{

           mensaje.setSeverity(FacesMessage.SEVERITY_ERROR);
           mensaje.setSummary("no se pudo modificar el datos");

           fc.addMessage(null,mensaje);
       }

    }

     public void btnEliminarHandler(ActionEvent actionEvent) {
           FacesMessage mensaje=new FacesMessage();
            try {
            this.tsBean.delete(registro);
           this.registro=null;
           this.estado=ESTADO_CRUD.NINGUNO;
           mensaje.setSeverity(FacesMessage.SEVERITY_INFO);
           mensaje.setSummary("registro eliminado");
                fc.addMessage(null,mensaje);
       }catch (Exception e){
           mensaje.setSeverity(FacesMessage.SEVERITY_INFO);
           mensaje.setSummary("registro eliminado");
           mensaje.setDetail(e.getMessage());
           fc.addMessage(null,mensaje);

            }
    }

    public List<TipoSala> getUpdateList() {
        return tsBean.findRange(0,10000);
    }

}
