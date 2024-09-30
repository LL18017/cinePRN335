package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ActionEvent;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersist;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoSala;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbstractFrm<T>{
   //metodos abstractos
   public abstract void instanciarRegistro();
   public abstract FacesContext getFC();
   public abstract AbstractDataPersist<T> getAbstractDataPersist();
   public abstract void btnSelecionarRegistroHandler(final Object id);
   public abstract Object getIdEntity();

   //propiedades
   ESTADO_CRUD estado=ESTADO_CRUD.NINGUNO;
    T registro;
    List<T> registros;
   //botones
   public void btnCancelarHandler(ActionEvent actionEvent) {
      this.estado=ESTADO_CRUD.NINGUNO;
      this.registro=null;
   }
   public void btnNuevoHandler(ActionEvent actionEvent) {
      instanciarRegistro();
      this.estado=ESTADO_CRUD.CREAR;
   }
   //arranque

   @PostConstruct
   public void init() {
      estado=ESTADO_CRUD.NINGUNO;
      inicioRegistros();
   }
   public void  inicioRegistros(){
      registros = getUpdateList();

   }
   //persistencia
   public void btnGuardarHandler(ActionEvent e) {
      FacesMessage mensaje=new FacesMessage();;
      try {
         AbstractDataPersist<T> clBean = null;
         clBean = getAbstractDataPersist();
         clBean.create(registro);
         this.estado = ESTADO_CRUD.NINGUNO;
         mensaje.setSeverity(FacesMessage.SEVERITY_INFO);
         mensaje.setSummary("registro guardado");
         getFC().addMessage(null,mensaje);
         this.registro = null;
      } catch (Exception ex) {
         Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
         mensaje.setSeverity(FacesMessage.SEVERITY_ERROR);
         mensaje.setSummary("no se pudo guardar el datos");
         mensaje.setDetail(ex.getMessage());
         getFC().addMessage(null,mensaje);
      }

   }

   public void btnModificarHandler(ActionEvent ex) {
      T modificado = null;
      FacesMessage mensaje=new FacesMessage();;
      try {
         AbstractDataPersist<T> clBean = null;
         clBean = getAbstractDataPersist();
         modificado = clBean.update(registro);
         if (modificado != null) {
            //notificar que se elimino3
            mensaje.setSeverity(FacesMessage.SEVERITY_INFO);
            mensaje.setSummary("registro modificado");
            getFC().addMessage(null,mensaje);

         }
      } catch (Exception e) {
//         Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
         mensaje.setSeverity(FacesMessage.SEVERITY_ERROR);
         mensaje.setSummary("registro no se pudo modificar");
         mensaje.setDetail(e.getMessage());
         getFC().addMessage(null,mensaje);
      }
      this.estado = ESTADO_CRUD.NINGUNO;
      this.registro = null;

   }

   public void btneEliminarHandler(ActionEvent ex) {
      FacesMessage mensaje=new FacesMessage();;
      try {
         AbstractDataPersist<T> clBean = null;
         clBean = getAbstractDataPersist();
         clBean.delete(registro);
         this.estado = ESTADO_CRUD.NINGUNO;
         this.registro = null;
         mensaje.setSeverity(FacesMessage.SEVERITY_INFO);
         mensaje.setSummary("registro eliminado");
         getFC().addMessage(null,mensaje);
         return;
      } catch (Exception e) {
         mensaje.setSeverity(FacesMessage.SEVERITY_INFO);
         mensaje.setSummary("registro no se pudo eliminar");
         mensaje.setDetail(e.getMessage());
         getFC().addMessage(null,mensaje);
         Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);

      }
   }
   //otros

   public List<T> getUpdateList(){
      AbstractDataPersist<T> clBean=getAbstractDataPersist();
      List<T> list;
      int max=clBean.count();
      try {
         list =clBean.findRange(0,max);
         return list;
      }catch (Exception e){
         return Collections.emptyList();
      }
   }

   //getters y setters

   public List<T> getRegistros() {
      return registros;
   }

   public void setRegistros(List<T> registros) {
      this.registros = registros;
   }

   public T getRegistro() {
      return registro;
   }

   public void setRegistro(T registro) {
      this.registro = registro;
   }

   public ESTADO_CRUD getEstado() {
      return estado;
   }

   public void setEstado(ESTADO_CRUD estado) {
      this.estado = estado;
   }
}
