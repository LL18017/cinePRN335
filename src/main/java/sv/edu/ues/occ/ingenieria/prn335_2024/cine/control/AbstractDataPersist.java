package sv.edu.ues.occ.ingenieria.prn335_2024.cine.control;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public abstract  class AbstractDataPersist<T> {
    final Class tipoDeDato;

    public AbstractDataPersist(Class t) {
        this.tipoDeDato=t;
    }
    public abstract EntityManager getEntityManager();

    public T findById(Object id) throws IllegalArgumentException ,IllegalStateException {
        EntityManager em = null;
        if (id==null){
           throw new IllegalStateException("parametro no valido");
        }
        try {
            em=getEntityManager();
            if (em==null){
                throw new IllegalStateException("error al aceder al repositorio");
            }
        }catch (IllegalStateException e){
            throw new IllegalStateException("error al aceder al repositorio",e);

        }
        return (T) em.find(this.tipoDeDato,id);
    }
    public List<T> findAll() throws IllegalStateException {
        EntityManager em = null;
        List<T> resultados=null;
            try {
                em=getEntityManager();
                if (em!=null){
                    TypedQuery<T> query=em.createQuery("SELECT o FROM "+this.tipoDeDato+" o", this.tipoDeDato);
                    resultados=query.getResultList();
                    return resultados;
                }
            }catch (java.lang.IllegalStateException e){
                //error de entity manager
                throw new IllegalStateException("error al aceder al repositorio",e);
            }
        return null;
    }
    public void create(T registro) throws IllegalStateException {
        if (registro == null) {
            throw new IllegalArgumentException("El registro no puede ser nulo");
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            if (em != null) {
                em.persist(registro);
            }
        } catch (IllegalStateException e) {
            throw new IllegalStateException("Error al acceder al repositorio", e);
        }
    }
    public void modify(T registro) {
        if (registro == null) {
            throw new IllegalArgumentException("El registro no puede ser nulo");
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            if (em != null) {
                em.merge(registro);
            }
        } catch (IllegalStateException e) {
            throw new IllegalStateException("Error al acceder al repositorio", e);
        }
    }

}
