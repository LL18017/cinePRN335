package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ActionEvent;
import jakarta.faces.validator.ValidatorException;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.event.SelectEvent;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersist;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AsientoCaracteristicaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.TipoAsientoBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.*;


import java.io.Serializable;
import java.sql.SQLOutput;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


@Named
@ViewScoped
public class FrmAsientoCaracteristica extends AbstractFrm<AsientoCaracteristica> implements Serializable {

    @Inject
    AsientoCaracteristicaBean asientoCaracteristicaBean;
    @Inject
    TipoAsientoBean tipoAsientoBean;
    @Inject
    FacesContext fc;


    Asiento AsientoSelecionado;
    TipoAsiento tipoAsientoSelecionado;
    Integer idTipoAsientoSelecionado;
    List<TipoAsiento> tipoAsientoslist;
    String expresionTipoAsiento;

    Integer idAsientoCaracteristica;

    Integer temporan;
    @Override
    public void instanciarRegistro() {
        System.out.println("CreandoInstancias");
        registro = new AsientoCaracteristica();
        registro.setIdAsiento(AsientoSelecionado);
        registro.setIdTipoAsiento(new TipoAsiento());
        expresionTipoAsiento=tipoAsientoslist.getFirst().getExpresionRegular();

    }

    @Override
    public void inicioRegistros() {
        super.inicioRegistros();
        try {
            this.tipoAsientoslist=tipoAsientoBean.findAllTipoAsiento();
        }catch (Exception e){
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }

    @Override
    public FacesContext getFC() {
        return fc;
    }

    @Override
    public AbstractDataPersist<AsientoCaracteristica> getAbstractDataPersist() {
        return asientoCaracteristicaBean;
    }

    @Override
    public String getIdByObject(AsientoCaracteristica object) {
        if (object != null) {
            return object.getIdAsientoCaracteristica().toString();
        }
        return null;
    }

    @Override
    public AsientoCaracteristica getObjectById(String id) {
        if (id != null && modelo != null && modelo.getWrappedData() != null) {
            return modelo.getWrappedData().stream()
                    .filter(s -> s.getIdAsientoCaracteristica().toString().equals(id))
                    .findFirst().orElse(null);
        }
        return null;
    }

    @Override
    public void selecionarFila(SelectEvent<AsientoCaracteristica> event) {
        AsientoCaracteristica filaSelelcted = event.getObject();
        if(filaSelelcted!=null){
            FacesMessage mensaje=new FacesMessage("caracteristica selecionada ", registro.getValor());
            fc.addMessage(null, mensaje);
            this.registro = filaSelelcted;
            this.estado=ESTADO_CRUD.MODIFICAR;

        }else {
            fc.addMessage(null,  new FacesMessage(FacesMessage.SEVERITY_ERROR, "no se ha encontrado ", " "));
        }

    }

    @Override
    public String paginaNombre() {
        return "Asiento Caracteristica";
    }

    //Metodos random y SobreEscritos-----------------------------------------------------

    @Override
    public int contar() {
        return asientoCaracteristicaBean.countByIdAsientoCaracteristica(AsientoSelecionado);
    }

    @Override
    public List<AsientoCaracteristica> cargar(int first, int max) {
        return asientoCaracteristicaBean.findByIdAsiento(AsientoSelecionado, first, max);
    }

    public void onTipoAsientoChange(){
        expresionTipoAsiento=tipoAsientoSelecionado.getExpresionRegular();
        enviarMensaje(FacesMessage.SEVERITY_INFO,"se ha selecionado un tipo de asiento: "+tipoAsientoSelecionado.getNombre());
        registro.setIdTipoAsiento(tipoAsientoSelecionado);
//        estado=ESTADO_CRUD.MODIFICAR;
//        estado=ESTADO_CRUD.CREAR;

    }

    public void validarTexto(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String inputText = (String) value;
        System.out.println("la expresion es " +expresionTipoAsiento);
        if (inputText != null && !inputText.matches(expresionTipoAsiento) && !expresionTipoAsiento.equals(".")) {
            FacesMessage msg = new FacesMessage("El texto no es válido.", "El texto debe ser uno de los valores permitidos: "+expresionTipoAsiento);
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
    }

    //Getters and Setters-------------------------------------------


    public String getExpresionTipoAsiento() {
        return expresionTipoAsiento;
    }

    public void setExpresionTipoAsiento(String expresionTipoAsiento) {
        this.expresionTipoAsiento = expresionTipoAsiento;
    }

    public List<TipoAsiento> getTipoAsientoslist() {
        return tipoAsientoslist;
    }

    public void setTipoAsientoslist(List<TipoAsiento> tipoAsientoslist) {
        this.tipoAsientoslist = tipoAsientoslist;
    }

    public Integer getIdTipoAsientoSelecionado() {
        return idTipoAsientoSelecionado;
    }

    public void setIdTipoAsientoSelecionado(Integer idTipoAsientoSelecionado) {
        this.idTipoAsientoSelecionado = idTipoAsientoSelecionado;
        tipoAsientoSelecionado=tipoAsientoslist.stream().filter(ta->ta.getIdTipoAsiento().equals(idTipoAsientoSelecionado)).findFirst().orElse(null);
    }

    public TipoAsiento getTipoAsientoSelecionado() {
        return tipoAsientoSelecionado;
    }

    public void setTipoAsientoSelecionado(TipoAsiento tipoAsientoSelecionado) {
        this.tipoAsientoSelecionado = tipoAsientoSelecionado;
    }

    public Asiento getAsientoSelecionado() {
        return AsientoSelecionado;
    }

    public Integer getIdAsientoCaracteristica() {
        return idAsientoCaracteristica;
    }

    public void setIdAsientoCaracteristica(Integer idAsientoCaracteristica) {
        this.idAsientoCaracteristica = idAsientoCaracteristica;
        registro=asientoCaracteristicaBean.findByIdAsientoCaracterisctica(idAsientoCaracteristica);
    }

    public void setAsientoSelecionado(Asiento asientoSelecionado) {
        AsientoSelecionado = asientoSelecionado;
    }

    public Integer getTemporan() {
        return temporan;
    }

    public void setTemporan(Integer temporan) {
        this.temporan = temporan;
    }

    public void probar() {
        System.out.println("sirve");
    }
    public void onAsientoCaracteristicaChange(){
//        enviarMensaje(FacesMessage.SEVERITY_INFO,"SELECION");
        estado=ESTADO_CRUD.MODIFICAR;
    }
    @Override
    public void btnNuevoHandler(ActionEvent actionEvent) {
        super.btnNuevoHandler(actionEvent);
    }

    @Override
    public void btnModificarHandler(ActionEvent ex) {

        super.btnModificarHandler(ex);
    }
}

