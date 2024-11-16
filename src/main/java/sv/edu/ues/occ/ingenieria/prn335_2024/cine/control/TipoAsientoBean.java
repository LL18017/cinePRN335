package sv.edu.ues.occ.ingenieria.prn335_2024.cine.control;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoAsiento;

import java.io.Serializable;
import java.util.List;

@Stateless
@LocalBean
public class TipoAsientoBean extends AbstractDataPersist<TipoAsiento> implements Serializable {
    @PersistenceContext(unitName = "cinePU")
    EntityManager em;

    public TipoAsientoBean() {
        super(TipoAsiento.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public List<TipoAsiento> findAllTipoAsiento() {
        try {
            return em.createNamedQuery("TipoAsiento.findAll", TipoAsiento.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return List.of();
    }
}
