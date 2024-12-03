package sv.edu.ues.occ.ingenieria.prn335_2024.cine.control;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Producto;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoProducto;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


@Stateless
    @LocalBean
public class TipoProductoBean  extends AbstractDataPersist<TipoProducto> implements Serializable {
    @PersistenceContext(unitName = "cinePU")
    EntityManager em;

    public TipoProductoBean() {
        super(TipoProducto.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    @Override
    public String orderParameterQuery() {
        return "idTipoProducto";
    }

    public List<TipoProducto> findTipoProductoByProductoId(Producto producto) {
        try {
            em.createNamedQuery("TipoProducto.findByIdProducto", TipoProducto.class).setParameter("idProducto", producto.getIdProducto()).getResultList();
        }catch (Exception e){
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(),e);
        }

        return List.of();
    }
}
