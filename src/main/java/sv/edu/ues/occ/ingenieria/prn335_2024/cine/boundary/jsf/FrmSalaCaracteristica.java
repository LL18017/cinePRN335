package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.Dependent;
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
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.SalaCaracteristicaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Sala;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.SalaCaracteristica;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoSala;

//import javax.faces.validator.ValidatorException;
import java.io.Serializable;
import java.sql.SQLOutput;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@Dependent
public class FrmSalaCaracteristica extends AbstractFrm<SalaCaracteristica> implements Serializable {


    @Inject
    FacesContext fc;
    @Inject
    SalaCaracteristicaBean salaCaracteristicaBean;

    List<TipoSala> tipoSalaList;
    Integer idTipoSala;
    Sala idSalaSelecionada;
    String expresionTipoSala;

    @Override
    public void instanciarRegistro() {
        registro = new SalaCaracteristica();
        registro.setValor("ingrese valor");
    }

    @PostConstruct
    @Override
    public void inicioRegistros() {
        super.inicioRegistros();
        try {
            this.tipoSalaList=salaCaracteristicaBean.findAllTiposSala();
        }catch (Exception e){
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }

    @Override
    public FacesContext getFC() {
        return fc;
    }

    @Override
    public AbstractDataPersist<SalaCaracteristica> getAbstractDataPersist() {
        return salaCaracteristicaBean;
    }

    @Override
    public String getIdByObject(SalaCaracteristica object) {
        if (object != null) {
            return object.getIdSalaCaracteristica().toString();
        }
        return null;
    }

    @Override
    public SalaCaracteristica getObjectById(String id) {
        if (id != null && modelo != null && modelo.getWrappedData() != null) {
            return modelo.getWrappedData().stream()
                    .filter(s -> s.getIdSalaCaracteristica().toString().equals(id))
                    .findFirst().orElse(null);
        }
        return null;
    }

    @Override
    public void selecionarFila(SelectEvent<SalaCaracteristica> event) {
        //Seleccionar fila
        System.out.println("selceionagj");
        SalaCaracteristica filaSelelcted = event.getObject();
        System.out.println(registro);
        if(filaSelelcted!=null){
            FacesMessage mensaje=new FacesMessage("caracteristica selecionada ", registro.getValor());
            fc.addMessage(null, mensaje);
            this.estado=ESTADO_CRUD.MODIFICAR;
            idTipoSala=registro.getIdTipoSala().getIdTipoSala();

        }else {
            fc.addMessage(null,  new FacesMessage(FacesMessage.SEVERITY_ERROR, "no se ha encontrado ", " "));
        }


    }
    public void selecionarTipoSala(){
        String ex=tipoSalaList.stream().filter(ts->ts.getIdTipoSala().equals(idTipoSala)).findFirst().orElse(null).getExpresionRegular();
        expresionTipoSala=ex;
    }

    public void validarTexto(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String inputText = (String) value;

        if (inputText != null && !inputText.matches(expresionTipoSala) && !expresionTipoSala.equals(".")) {
            FacesMessage msg = new FacesMessage("El texto no es válido.", "El texto debe ser uno de los valores permitidos: "+expresionTipoSala);
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
    }



    @Override
    public String paginaNombre() {
        return "";
    }

    @Override
    public int contar() {
        return salaCaracteristicaBean.countPeliculaCarracteristica(idSalaSelecionada);
    }

    @Override
    public List<SalaCaracteristica> cargar(int first, int max) {
        return  salaCaracteristicaBean.findByIdSala(idSalaSelecionada,first,max);
    }

    public Integer getIdTipoSala() {
        return idTipoSala;
    }

    public void setIdTipoSala(Integer idTipoSala) {
        this.idTipoSala = idTipoSala;
    }

    public List<TipoSala> getTipoSalaList() {
        return tipoSalaList;
    }

    public void setTipoSalaList(List<TipoSala> tipoSalaList) {
        this.tipoSalaList = tipoSalaList;
    }

    public Sala getIdSalaSelecionada() {
        return idSalaSelecionada;
    }

    public void setIdSalaSelecionada(Sala idSala) {
        this.idSalaSelecionada = idSala;
    }

    public String getExpresionTipoSala() {
        return expresionTipoSala;
    }

    public void setExpresionTipoSala(String expresionTipoSala) {
        this.expresionTipoSala = expresionTipoSala;
    }

    @Override
    public void btnGuardarHandler(ActionEvent e) {
        registro.setIdSala(idSalaSelecionada);
        try {
            TipoSala tipoSalaSelecionada = tipoSalaList.stream().filter(ts-> ts.getIdTipoSala().toString().equals(String.valueOf(idTipoSala))).findFirst().orElse(null);
            registro.setIdTipoSala(tipoSalaSelecionada);
            super.btnGuardarHandler(e);

        }catch (Exception ec){
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, ec.getMessage(), ec);
        }


    }

    @Override
    public void btnModificarHandler(ActionEvent ex) {

        System.out.println("modificando");
        registro.setIdSala(idSalaSelecionada);
       try {
           if (!tipoSalaList.isEmpty()){
               TipoSala tipoSalaSelecionada = tipoSalaList.stream().filter(ts-> ts.getIdTipoSala().toString().equals(String.valueOf(idTipoSala))).findFirst().orElse(null);
               registro.setIdTipoSala(tipoSalaSelecionada);
            super.btnModificarHandler(ex);
           }
       }catch (Exception ec){
           Logger.getLogger(getClass().getName()).log(Level.SEVERE, ec.getMessage(), ec);
       }
    }

}