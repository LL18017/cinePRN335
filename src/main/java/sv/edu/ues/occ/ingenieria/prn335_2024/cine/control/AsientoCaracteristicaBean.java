package sv.edu.ues.occ.ingenieria.prn335_2024.cine.control;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.AsientoCaracteristica;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.SalaCaracteristica;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoSala;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AsientoCaracteristicaBean extends AbstractDataPersist<AsientoCaracteristica> implements Serializable {
    @PersistenceContext(unitName = "cinePU")
    EntityManager em;

    public AsientoCaracteristicaBean() {
        super(AsientoCaracteristica.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }


    public List<TipoSala> findAllTiposAsiento() {
        try {
            TypedQuery<TipoSala> q = em.createNamedQuery("AsientoCaracteristica.findAll", TipoSala.class);
            q.setFirstResult(0);
            q.setMaxResults(Integer.MAX_VALUE);
            return q.getResultList();
        }catch (Exception e){
            Logger.getLogger(getClass().getName()).log(Level.SEVERE,e.getMessage(),e);
        }
        return List.of();
    }

    public List<SalaCaracteristica> findByIdSala(final long idSala, int first, int last) {
        try {
            TypedQuery<SalaCaracteristica> q = em.createNamedQuery("SalaCaracteristica.findByIdSalaCaracteristica", SalaCaracteristica.class);
            q.setParameter("idSalaCaracteristica", idSala);
            q.setFirstResult(first);
            q.setMaxResults(last);
            return q.getResultList();
        }catch (Exception e){
            Logger.getLogger(getClass().getName()).log(Level.SEVERE,e.getMessage(),e);
        }
        return List.of();
    }

    public int countPeliculaCarracteristica(final long idSala) {
        try {
            TypedQuery<Long> q = em.createNamedQuery("SalaCaracteristica.countByIdSalaCaracteristica", Long.class);
            q.setParameter("idSalaCaracteristica", idSala);
            return q.getSingleResult().intValue();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return 0;
    }
}
