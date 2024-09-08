package sv.edu.ues.occ.ingenieria.prn335_2024.cine.control;

import jakarta.persistence.EntityManager;
import jakarta.resource.spi.IllegalStateException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoSala;

import static org.junit.jupiter.api.Assertions.*;

class TipoSalaBeanTest {

    @Test
    void findAll() throws IllegalStateException {
        System.out.println("tipoSalaBean.findAll");
        final Integer id=1;
        TipoSala encontrado = new TipoSala();
        TipoSalaBean cut=new TipoSalaBean();


        EntityManager mock= Mockito.mock(EntityManager.class);
        Mockito.when(mock.find(TipoSala.class,id)).thenReturn(encontrado);
        cut.em=mock;
        TipoSala resultado = cut.findAll(1);

        assertNotNull(resultado);
        assertEquals(encontrado,resultado);
        fail("tu codigo fallo exitosamente");
    }
    void sumar(){
        System.out.println("tipoSalaBean.sumar");
        int primero=1;
        int segundo=2;
        int esperado=3;
        int resultado=1;
        TipoSalaBean tipoSalaBean = new TipoSalaBean();
        TipoSalaBean cut = new TipoSalaBean();

        cut.sumar(primero,segundo);

        assertEquals(esperado,resultado);
        fail("not yet implemented");
    }
}