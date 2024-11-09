package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf;

import jakarta.enterprise.context.Dependent;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.event.SelectEvent;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersist;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.PeliculaCarracteristicaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.TipoPeliculaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Pelicula;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.PeliculaCaracteristica;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoPelicula;

import jakarta.faces.application.FacesMessage;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@Dependent
public class FrmPeliculaCarracteristica extends AbstractFrm<PeliculaCaracteristica> implements Serializable {
   @Inject
    PeliculaCarracteristicaBean pcBean;
   @Inject
   TipoPeliculaBean tpBean;
   @Inject
   FacesContext fc;

   List<TipoPelicula> tipoPeliculaList;
    Long idPelicula;

    @Override
    public String paginaNombre() {
        return "Pelicula Carracteristica";
    }

    @Override
    public void inicioRegistros() {
        super.inicioRegistros();
        try {
        this.tipoPeliculaList=null;
        }catch (Exception e){
            Logger.getLogger(getClass().getName()).log(Level.SEVERE,e.getMessage(),e);
            enviarMensaje("error al cargar los tipos","error al carar los tipos",FacesMessage.SEVERITY_ERROR);
        }
    }

    @Override
    public void instanciarRegistro() {
        registro=new PeliculaCaracteristica();
        if (idPelicula!=null){
            registro.setIdPelicula(new Pelicula(idPelicula));
        }
        if (tipoPeliculaList!=null && tipoPeliculaList.isEmpty()){
            registro.setIdTipoPelicula(tipoPeliculaList.getFirst());
        }
    }

    @Override
    public FacesContext getFC() {
        return fc;
    }

    @Override
    public AbstractDataPersist<PeliculaCaracteristica> getAbstractDataPersist() {
        return pcBean;
    }

    @Override
    public String getIdByObject(PeliculaCaracteristica object) {
        if (object != null) {
            return object.getIdPeliculaCaracteristica().toString();
        }
        return "";
    }

    @Override
    public PeliculaCaracteristica getObjectById(String id) {
        if (id!=null && modelo.getWrappedData()!=null && modelo!=null) {
            return modelo.getWrappedData().stream().filter(r->id.equals(r.getIdPeliculaCaracteristica().toString())).findFirst().orElse(null);
        };
        return null;
    }

    @Override
    public void selecionarFila(SelectEvent<PeliculaCaracteristica> event) {

    }

    public void enviarMensaje(String mensaje, String detalle, FacesMessage.Severity level) {
        FacesMessage ms=new FacesMessage(level,detalle,mensaje);
        fc.addMessage(null,ms);
    }

    public List<TipoPelicula> getTipoPeliculaList() {
        return tipoPeliculaList;
    }

    public void setTipoPeliculaList(List<TipoPelicula> tipoPeliculaList) {
        this.tipoPeliculaList = tipoPeliculaList;
    }

    public Long getIdPelicula() {
        return idPelicula;
    }

    public void setIdPelicula(Long idPelicula) {
        this.idPelicula = idPelicula;
    }
}
