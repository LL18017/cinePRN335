package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersist;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.TipoSalaBean;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.*;

class TipoSalaServletTest  {

    //@Test
    void doPost() throws IOException {
        TipoSalaServlet cut=new TipoSalaServlet();
        HttpServletResponse resMock= Mockito.mock(HttpServletResponse.class);
        HttpServletRequest reqMock= Mockito.mock(HttpServletRequest.class);

        Mockito.when(reqMock.getParameter("nombre")).thenReturn("chepe");

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        TipoSalaBean mockTSB=Mockito.mock(TipoSalaBean.class);
        Mockito.when(resMock.getWriter()).thenReturn(printWriter);

        cut.tsBean=mockTSB;
        try {
            cut.doPost(reqMock,resMock);
        }catch (ServletException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Mockito.verify(resMock).setStatus(HttpServletResponse.SC_PRECONDITION_FAILED);
        fail("");
    }
    @Test
    void doGet() {
    }

}