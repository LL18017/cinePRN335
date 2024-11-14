package sv.edu.ues.occ.ingenieria.prn335_2024.cine.control;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Asiento;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Programacion;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Sala;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.SalaCaracteristica;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
@LocalBean
public class AsientoBean extends AbstractDataPersist<Asiento> implements Serializable {
    @PersistenceContext(unitName = "cinePU")
    EntityManager em;
    public AsientoBean() {
        super(Asiento.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public List<Asiento> findAsientosBySalaandProgramacion(Sala sala, Programacion programacion) {
            try {
               return em.createNamedQuery("Asiento.findAsientosBySalaandProgramacion", Asiento.class).
                        setParameter("idSala", sala).setParameter("idProgramacion",programacion).getResultList();
            }catch (Exception e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            }
        return List.of();
    }
    public List<Asiento> findIdAsientoBySala(final long idSala, int first, int last) {
        try {
            TypedQuery<Asiento> q = em.createNamedQuery("Asiento.findIdAsientoBySala", Asiento.class);
            q.setParameter("idSala", idSala);
            q.setFirstResult(first);
            q.setMaxResults(last);
            return q.getResultList();
        }catch (Exception e){
            Logger.getLogger(getClass().getName()).log(Level.SEVERE,e.getMessage(),e);
        }
        return List.of();
    }

    public int countAsientos(final long idSala) {
        try {
            TypedQuery<Long> q = em.createNamedQuery("Asiento.countByIdAsiento", Long.class);
            q.setParameter("idAsiento", idSala);
            return q.getSingleResult().intValue();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return 0;
    }

}
