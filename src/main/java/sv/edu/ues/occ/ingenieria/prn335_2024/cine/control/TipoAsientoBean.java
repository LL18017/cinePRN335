package sv.edu.ues.occ.ingenieria.prn335_2024.cine.control;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Asiento;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.AsientoCaracteristica;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoAsiento;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    public List<TipoAsiento> findByIdAsiento(Asiento asiento) {
        try {
            return em.createNamedQuery("TipoAsiento.findAll", TipoAsiento.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return List.of();
    }

    @Override
    public String orderParameterQuery() {
        return "idTipoAsiento";
    }
}
