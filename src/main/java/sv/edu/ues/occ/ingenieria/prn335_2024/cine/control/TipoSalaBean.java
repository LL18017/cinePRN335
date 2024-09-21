package sv.edu.ues.occ.ingenieria.prn335_2024.cine.control;


import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.lang.IllegalArgumentException;
import java.lang.IllegalStateException;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoSala;

import java.io.Serializable;
import java.util.List;

@Stateless
@LocalBean
public class TipoSalaBean extends AbstractDataPersist<TipoSala> implements Serializable {
    @PersistenceContext(unitName = "cinePU")
    EntityManager em;

    public TipoSalaBean() {
        super(TipoSala.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }
}
