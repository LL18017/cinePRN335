package sv.edu.ues.occ.ingenieria.prn335_2024.cine.control;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoSala;

import static org.junit.jupiter.api.Assertions.*;

class TipoSalaBeanTest extends AbstractDataPersist<TipoSala> {

    public TipoSalaBeanTest() {
        super(TipoSala.class);
    }

    @Test
    void findById()  {
        System.out.println("tipoSalaBean.findAll");
        final Integer id=1;
        TipoSala encontrado = new TipoSala();
        TipoSalaBean cut=new TipoSalaBean();

        //prueba em nullo
        assertThrows(IllegalStateException.class,()->{
            cut.findById(id);
        });
        //prueba normal
        EntityManager mock= Mockito.mock(EntityManager.class);
        Mockito.when(mock.find(TipoSala.class,id)).thenReturn(encontrado);
        cut.em=mock;
        TipoSala resultado = cut.findById(1);

        assertNotNull(resultado);

        assertEquals(encontrado,resultado);
        //prueba id nullo
        assertThrows(IllegalArgumentException.class,()->{
            cut.findById(null);
        });


//        fail("tu codigo fallo exitosamente");
    }

    @Test
    void testFindAll() {
    }

    @Test
    void testCreate() {
    }

    @Test
    void testModify() {
    }

    @Test
    void testGetEntityManager() {
    }

    @Override
    public EntityManager getEntityManager() {
        return null;
    }
}