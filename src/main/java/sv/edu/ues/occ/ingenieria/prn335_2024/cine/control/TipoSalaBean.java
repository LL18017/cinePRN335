package sv.edu.ues.occ.ingenieria.prn335_2024.cine.control;


import jakarta.ejb.Local;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import java.lang.IllegalArgumentException;
import jakarta.resource.spi.IllegalStateException;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoSala;

import java.io.Serializable;
import java.util.List;

@Stateless
@LocalBean
public class TipoSalaBean implements Serializable {
    @PersistenceContext(unitName = "cinePU")
    EntityManager em;
    public TipoSala findAll(final Integer idTipoSala) throws IllegalStateException,IllegalArgumentException{
        if (idTipoSala == null||idTipoSala<=0) {
            throw  new IllegalArgumentException("parametro no valido");
        }
        try {
            if(em==null){
                throw new IllegalStateException();
            };
        }catch (Exception ex){
            throw new IllegalStateException("Error al acceder al repositorio",ex);
        }
        TipoSala tipoSala = em.find(TipoSala.class, idTipoSala);
        return tipoSala;
    }

    public int sumar(int primero, int segundo) {
        return primero+segundo;
    }
}
