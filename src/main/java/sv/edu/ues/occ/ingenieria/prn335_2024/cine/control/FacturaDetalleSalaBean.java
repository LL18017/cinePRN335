package sv.edu.ues.occ.ingenieria.prn335_2024.cine.control;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.FacturaDetalleSala;

import java.io.Serializable;

@Stateless
@LocalBean
public class FacturaDetalleSalaBean extends AbstractDataPersist<FacturaDetalleSala> implements Serializable {
    @PersistenceContext (unitName = "cinePU")
    EntityManager em;
    public FacturaDetalleSalaBean() {
        super(FacturaDetalleSala.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    @Override
    public String orderParameterQuery() {
        return "idFacturaDetalleSala";
    }
}
