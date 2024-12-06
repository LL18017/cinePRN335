package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf;

import jakarta.enterprise.context.Dependent;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ActionEvent;
import jakarta.faces.validator.ValidatorException;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.event.SelectEvent;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf.rest.WS.SalaCaracteristicaEndPoint;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf.rest.WS.WS;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersist;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.SalaCaracteristicaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Sala;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.SalaCaracteristica;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoSala;

//import javax.faces.validator.ValidatorException;
import java.io.Serializable;
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
    @Inject
    SalaCaracteristicaEndPoint salaCaracteristicaEndPoint;

    List<TipoSala> tipoSalaList;
    TipoSala tipoSalaSelecionada;
    Integer idTipoSala;
    Sala salaSelecionada;
    String expresionTipoSala;

    @Override
    public void instanciarRegistro() {
        registro = new SalaCaracteristica();
        registro.setIdSala(salaSelecionada);
        registro.setIdTipoSala(tipoSalaList.getFirst());
        expresionTipoSala=tipoSalaList.getFirst().getExpresionRegular();
        idTipoSala=tipoSalaList.getFirst().getIdTipoSala();
    }


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


        enviarMensaje(FacesMessage.SEVERITY_INFO,"se ha selecionado la caracteristica"+registro.getIdTipoSala().getNombre());
        estado=ESTADO_CRUD.MODIFICAR;
        idTipoSala=registro.getIdTipoSala().getIdTipoSala();
        expresionTipoSala=event.getObject().getIdTipoSala().getExpresionRegular();



    }
    public void selecionarTipoSala(){
       enviarMensaje(FacesMessage.SEVERITY_INFO,"se ha seleccionado la caracteristica" +registro.getIdTipoSala().getNombre());
        expresionTipoSala=expresionTipoSala==null? ".": getTipoSalaSelecionada().getExpresionRegular();
    }

    public void validarTexto(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String inputText = (String) value;
        System.out.println("la expresion es " +expresionTipoSala);
        if (inputText != null && !inputText.matches(expresionTipoSala) && !expresionTipoSala.equals(".")) {
            FacesMessage msg = new FacesMessage("El texto no es válido.", "El texto debe ser uno de los valores permitidos: "+expresionTipoSala);
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
    }

//    public List<TipoSala> ordenarList(TipoSala ts ,List<TipoSala> list){
//        Set<TipoSala> set=new HashSet<>();
//    }

    @Override
    public String paginaNombre() {
        return "SalaCaracteristica";
    }

    @Override
    public WS getWebsocketController() {
        return salaCaracteristicaEndPoint;
    }

    @Override
    public int contar() {
        return salaCaracteristicaBean.countPeliculaCarracteristica(salaSelecionada);
    }

    @Override
    public List<SalaCaracteristica> cargar(int first, int max) {
        return  salaCaracteristicaBean.findByIdSala(salaSelecionada,first,max);
    }


    public List<TipoSala> getTipoSalaList() {
        return tipoSalaList;
    }

    public void setTipoSalaList(List<TipoSala> tipoSalaList) {
        this.tipoSalaList = tipoSalaList;
    }

    public Sala getSalaSelecionada() {
        return salaSelecionada;
    }

    public void setSalaSelecionada(Sala idSala) {
        this.salaSelecionada = idSala;
    }

    public String getExpresionTipoSala() {
        return expresionTipoSala;
    }

    public void setExpresionTipoSala(String expresionTipoSala) {
        this.expresionTipoSala = expresionTipoSala;
    }

    public Integer getIdTipoSala() {
        return idTipoSala;
    }

    public void setIdTipoSala(Integer idTipoSala) {
        this.idTipoSala = idTipoSala;
        tipoSalaSelecionada=tipoSalaList.stream().filter(ts->ts.getIdTipoSala().toString().equals(idTipoSala.toString())).findFirst().orElse(null);
        registro.setIdTipoSala(tipoSalaSelecionada);
    }

    public TipoSala getTipoSalaSelecionada() {
        return tipoSalaSelecionada;
    }

    public void setTipoSalaSelecionada(TipoSala tipoSalaSelecionada) {
        this.tipoSalaSelecionada = tipoSalaSelecionada;
    }

    @Override
    public void btnGuardarHandler(ActionEvent e) {
        if (tipoSalaSelecionada == null) {
            registro.setIdTipoSala(tipoSalaList.getFirst());
        }
        super.btnGuardarHandler(e);

    }

    @Override
    public void btnModificarHandler(ActionEvent ex) {
//        try {
//            if (!tipoSalaList.isEmpty()) {
//                TipoSala tp = tipoSalaList.stream()
//                        .filter(ts -> ts.getIdTipoSala().equals(idTipoSala))
//                        .findFirst()
//                        .orElse(null);
//                if (tp != null) {
//                    // Asignar idTipoSala en el registro
//                    registro.setIdSala(salaSelecionada);
//                    registro.setIdTipoSala(tp);
//
//                    super.btnModificarHandler(ex);  // Llamada al método de la clase base
//                } else {
//                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, "TipoSala no encontrado.");
//                }
//            } else {
//                Logger.getLogger(getClass().getName()).log(Level.SEVERE, "La lista tipoSalaList está vacía.");
//            }
//        } catch (Exception ec) {
//            // Manejo de excepciones
//            Logger.getLogger(getClass().getName()).log(Level.SEVERE, ec.getMessage(), ec);
//        }
                    super.btnModificarHandler(ex);  // Llamada al método de la clase base
    }



}