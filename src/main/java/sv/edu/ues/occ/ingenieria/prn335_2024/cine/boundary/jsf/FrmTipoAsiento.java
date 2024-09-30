package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf;

import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersist;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.TipoAsientoBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.TipoSalaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoAsiento;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoSala;

import java.io.Serializable;

@Named
@ViewScoped
public class FrmTipoAsiento extends AbstractFrm<TipoAsiento> implements Serializable {

    @Inject
    FacesContext fc;
    @Inject
    TipoAsientoBean TsBean;

    @Override
    public void instanciarRegistro() {
        this.registro = new TipoAsiento();
    }

    @Override
    public FacesContext getFC() {
        return fc;
    }

    @Override
    public AbstractDataPersist<TipoAsiento> getAbstractDataPersist() {
        return TsBean;
    }

    @Override
    public void btnSelecionarRegistroHandler(Object id) {
        if (id!=null){
            this.registro = this.registros.stream().filter(t -> t.getIdTipoAsiento() == id).
                    findFirst().orElse(null);
            this.estado=ESTADO_CRUD.MODIFICAR;
            return;
        }
        this.registro=null;
    }
    @Override
    public Object getIdEntity() {
        return this.registro.getIdTipoAsiento();
    }
}
