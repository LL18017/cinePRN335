package sv.edu.ues.occ.ingenieria.prn335_2024.cine.control;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract  class AbstractDataPersist<T> {
    final Class tipoDeDato;
    private String Orden;

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
                if (em==null){
                    throw new IllegalStateException("error al aceder al repositorio");
                }
                CriteriaBuilder cb=em.getCriteriaBuilder();
                CriteriaQuery cq=cb.createQuery(tipoDeDato);
                Root<T> r=cq.from(tipoDeDato);
                CriteriaQuery<T> cq2=cq.select(r);
                TypedQuery<T> q=em.createQuery(cq2);
                resultados=q.getResultList();
                return resultados;
            }catch (java.lang.IllegalStateException e){
                //error de entity manager
                throw new IllegalStateException("error al aceder al repositorio",e);
            }
    }

    public List<T> findRange(int first, int max) throws IllegalStateException, IllegalArgumentException {
        return findRange(first, max, "",""); // Llama al método con orden como cadena vacía
    }
    public List<T> findRange(int first, int max,String orden,String direccion) throws IllegalStateException,IllegalArgumentException {
        EntityManager em = null;

        if (first >= 0 && max > 0) {
            try {
                em = getEntityManager();
                if (em != null) {
                    // Construir consultas de criterios
                    CriteriaBuilder cb = em.getCriteriaBuilder();
                    CriteriaQuery<T> cq = cb.createQuery(this.tipoDeDato);
                    Root<T> raiz = cq.from(this.tipoDeDato);
                    cq.select(raiz);

                    // Agregar ordenamiento si se proporciona
                    if (!orden.isEmpty()) {
                        if (!direccion.equals("ASCENDING")) {

                            cq.orderBy(cb.desc(raiz.get(orden))); // Orden ascendente
                        }else{

                            cq.orderBy(cb.asc(raiz.get(orden))); // Orden ascendente
                        }
                    }

                    TypedQuery<T> query = em.createQuery(cq);
                    query.setFirstResult(first);
                    query.setMaxResults(max);
                    return query.getResultList();
                }
            } catch (Exception e) {
                System.out.println("error: " + e.getMessage());
            }
            throw new IllegalStateException();

        }

        return Collections.EMPTY_LIST;
    }

   /**
     *almacena un registro en le repositorio
    * @param registro registroa guardar
     * @throws IllegalStateException lanza error si no puede acceder al repositorio
     * @throws IllegalArgumentException lanza error si el registr es nulo
    * @throws
     */
    public void create(T registro) throws IllegalStateException,IllegalArgumentException {
        if (registro == null) {
            throw new IllegalArgumentException("El registro no puede ser nulo");
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            if (em == null) {
                throw new IllegalStateException("Error al acceder al repositorio");
            }

                em.persist(registro);
        } catch (Exception e) {
            throw new IllegalStateException("Error al acceder al repositorio jj", e);
        }
    }
    public T update(T registro) {
        if (registro == null) {
            throw new IllegalArgumentException("El registro no puede ser nulo");
        }
        T modificado=null;
        EntityManager em = null;
        try {
            em = getEntityManager();
            if (em != null) {
               modificado= em.merge(registro);
            }
        } catch (IllegalStateException e) {
            throw new IllegalStateException("Error al acceder al repositorio", e);
        }
        return modificado;
    }
    public int count() {
        EntityManager em = null;
        try {
            em = getEntityManager();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        if (em != null) {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery cq = cb.createQuery(this.tipoDeDato);
            Root<T> raiz = cq.from(this.tipoDeDato);

            CriteriaQuery crq=cq.select(cb.count(raiz));

            TypedQuery q = em.createQuery(crq);

            return Integer.parseInt(q.getSingleResult().toString());
        }
        return 0;
    }

    public void delete(T registro) {
        if (registro != null) {
            EntityManager em = null;
            try {
                em = getEntityManager();
                if (em != null) {
                    CriteriaBuilder cb = em.getCriteriaBuilder();
                    CriteriaDelete<T> dq = cb.createCriteriaDelete(this.tipoDeDato);
                    Root<T> raiz = dq.from(this.tipoDeDato);
                    dq.where(cb.equal(raiz, registro));
                    em.createQuery(dq).executeUpdate();
                    return;
                }
            } catch (Exception e) {
                System.out.println("error: " + e.getMessage());
            }
            throw new IllegalStateException();
        }
        throw new IllegalArgumentException();
    }

}
