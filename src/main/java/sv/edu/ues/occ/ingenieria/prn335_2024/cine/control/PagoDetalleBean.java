package sv.edu.ues.occ.ingenieria.prn335_2024.cine.control;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.PagoDetalle;

import java.io.Serializable;

@Stateless
@LocalBean
public class PagoDetalleBean extends AbstractDataPersist<PagoDetalle>  implements Serializable {
    @PersistenceContext
    EntityManager em;

    public PagoDetalleBean() {
        super(PagoDetalle.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    @Override
    public String orderParameterQuery() {
        return "idPagoDetalle";
    }
}
