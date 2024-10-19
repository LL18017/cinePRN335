package sv.edu.ues.occ.ingenieria.prn335_2024.cine.control;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Programacion;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Sala;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collector;

@Stateless
@LocalBean
public class SalaBean extends AbstractDataPersist<Sala> implements Serializable {
    @PersistenceContext(unitName = "cinePU")
    EntityManager em;

    public SalaBean() {
        super(Sala.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public List<Programacion> getProgramacionByIdSala(Sala sala) {
//        if (sala !=null){
//            try {
//                List<Programacion> programacion = em.createNamedQuery("Sala.findallProgramacionesByIdSala", Sala.class).
//                        setParameter("SalaSelecionada", sala.getIdSala()).;
//                Programacion programacion = (Programacion) pro;
//                System.out.println(programacion.getDesde());
//                return  List.of();
//            }catch (Exception ex){
//                Logger.getLogger(SalaBean.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }


        return List.of();
    }

}
