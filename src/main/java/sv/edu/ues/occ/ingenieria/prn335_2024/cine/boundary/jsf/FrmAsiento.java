package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.Dependent;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.event.SelectEvent;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf.rest.WS.AsientoEndPoint;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf.rest.WS.WS;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersist;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AsientoBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AsientoCaracteristicaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.TipoAsientoBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.*;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@Dependent
public class FrmAsiento extends AbstractFrm<Asiento> implements Serializable {

    @Inject
    AsientoBean asientoBean;
    @Inject
    TipoAsientoBean tipoAsientoBean;
    @Inject
    AsientoCaracteristicaBean asientoCaracteristicaBean;
    @Inject
    FacesContext fc;
    @Inject
    FrmAsientoCaracteristica frmAsientoCaracteristica;
    @Inject
    FrmTipoAsiento frmTipoAsiento;
    @Inject
    AsientoEndPoint asientoEndPoint;

    Sala SalaSelecionada;

    Asiento AsientoSelecionado;
    Integer idAsientoSelecionado;

    List<TipoAsiento> tipoAsientoList;
    TipoAsiento tipoAsientoSelecionado;
    Integer idTipoAsientoSelecionado;

    Integer idAsientoCaracteristicaSelecionado;

    @Override
    public void instanciarRegistro() {
        registro = new Asiento();
        registro.setIdSala(SalaSelecionada);
    }

    @PostConstruct
   @Override
    public void inicioRegistros() {
        super.inicioRegistros();
        try {
            this.tipoAsientoList=tipoAsientoBean.findAll();
        }catch (Exception e){
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            enviarMensaje(FacesMessage.SEVERITY_ERROR,"error al cargar datos de asiento");
        }
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
            return modelo.getWrappedData().stream()
                    .filter(s -> s.getIdAsiento().toString().equals(id))
                    .findFirst().orElse(null);
        }
        return null;
    }

    @Override
    public void selecionarFila(SelectEvent<Asiento> event) {
        Asiento asientoSelected =  event.getObject();
            enviarMensaje(FacesMessage.SEVERITY_INFO,"has selecionado el asiento: "+asientoSelected.getNombre());
            this.estado=ESTADO_CRUD.MODIFICAR;
            frmAsientoCaracteristica.setAsientoSelecionado(registro);

            frmAsientoCaracteristica.expresionTipoAsiento= frmAsientoCaracteristica.tipoAsientoslist.getFirst().getExpresionRegular();
        System.out.println("se ha selecionado algo");
    }

    @Override
    public String paginaNombre() {
        return "Asientos";
    }

    @Override
    public WS getWebsocketController() {
        return asientoEndPoint;
    }

    public Sala getSalaSelecionada() {
        return SalaSelecionada;
    }

    public void setSalaSelecionada(Sala salaSelecionada) {
        SalaSelecionada = salaSelecionada;
    }

    public List<TipoAsiento> getTipoAsientoList() {
        return tipoAsientoList;
    }

    public void setTipoAsientoList(List<TipoAsiento> tipoAsientoList) {
        this.tipoAsientoList = tipoAsientoList;
    }

    public TipoAsiento getTipoAsientoSelecionado() {
        return tipoAsientoSelecionado;
    }

    public void setTipoAsientoSelecionado(TipoAsiento tipoAsientoSelecionado) {
        this.tipoAsientoSelecionado = tipoAsientoSelecionado;
    }

    public Integer getIdTipoAsientoSelecionado() {
        return idTipoAsientoSelecionado;
    }

    public void setIdTipoAsientoSelecionado(Integer idTipoAsientoSelecionado) {
        this.idTipoAsientoSelecionado = idTipoAsientoSelecionado;
        tipoAsientoSelecionado=tipoAsientoList.stream().filter(ta->ta.getIdTipoAsiento().equals(idTipoAsientoSelecionado))
                .findFirst().orElse(null);

    }

    public FrmAsientoCaracteristica getFrmAsientoCaracteristica() {
        return frmAsientoCaracteristica;
    }

    public void setFrmAsientoCaracteristica(FrmAsientoCaracteristica frmAsientoCaracteristica) {
        this.frmAsientoCaracteristica = frmAsientoCaracteristica;
    }

    public Integer getIdAsientoCaracteristicaSelecionado() {
        return idAsientoCaracteristicaSelecionado;
    }

    public void setIdAsientoCaracteristicaSelecionado(Integer idAsientoCaracteristicaSelecionado) {
        this.idAsientoCaracteristicaSelecionado = idAsientoCaracteristicaSelecionado;
        registro=asientoBean.findById(idAsientoCaracteristicaSelecionado);
    }

    //Metodo para seleccionar la sala -------------------------------

    @Override
    public int contar() {
        try {

        return asientoBean.countAsientos(SalaSelecionada);
        }catch (Exception e){
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            enviarMensaje(FacesMessage.SEVERITY_ERROR,"eeror al cargar los asientos");
        }
        return 0;
    }

    @Override
    public List<Asiento> cargar(int first, int max) {
        try {
            return  asientoBean.findIdAsientoBySala(SalaSelecionada, first, max);

        }catch (Exception e){
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            enviarMensaje(FacesMessage.SEVERITY_ERROR,"eeror al cargar los asientos");
        }
        return List.of();
    }


    public void onTipoAsientoChange(){
        //logica para cargar las cracteristicas
        estado=ESTADO_CRUD.CREAR;
    }



    //Getters y Setters -----------------------------------------------------




//Botones -----------------------------------------------------


}
