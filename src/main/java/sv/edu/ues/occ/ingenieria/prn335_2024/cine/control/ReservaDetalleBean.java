package sv.edu.ues.occ.ingenieria.prn335_2024.cine.control;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.ReservaDetalle;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
@LocalBean
public class ReservaDetalleBean extends AbstractDataPersist<ReservaDetalle> implements Serializable {
    @PersistenceContext(unitName = "cinePU")
    EntityManager em;
    public ReservaDetalleBean() {
        super(ReservaDetalle.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    @Override
    public String orderParameterQuery() {
        return "idReservaDetalle";
    }

    public List<ReservaDetalle> findByIdReserva(Integer idReserva) {

        try {
           return em.createNamedQuery("ReservaDetalle.findByIdReserva", ReservaDetalle.class).setParameter("idReserva", idReserva.longValue()).getResultList();
        }catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }

        return List.of();
    }
}
