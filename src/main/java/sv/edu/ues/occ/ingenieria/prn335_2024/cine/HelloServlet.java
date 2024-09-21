package sv.edu.ues.occ.ingenieria.prn335_2024.cine;

import java.io.*;
import java.util.Collections;
import java.util.List;

import jakarta.inject.Inject;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.TipoSalaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoSala;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private String message;
    @Inject
    TipoSalaBean tsBean;
    public void init() {
        message = "Hello!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        int nuemroRegistros=0;
        TipoSala tipoSalaEncontrado;
        List<TipoSala> listSalas= Collections.EMPTY_LIST;
        TipoSala tipoSalaCreada= new TipoSala();
        try {
            nuemroRegistros = tsBean.count();//nuemero de registros
            tipoSalaEncontrado=tsBean.findById(1);//numero de registros especifico
            listSalas=tsBean.findRange(0,11);//todos los registros
            //crear un registros
//            tipoSalaCreada.setNombre("imax");
//            tipoSalaCreada.setActivo(true);
//            tipoSalaCreada.setComentarios("nueva");
//            tsBean.create(tipoSalaCreada);

//            tipoSalaCreada.setNombre("imax4");
//            tsBean.modify(tipoSalaCreada);

           // tsBean.delete(listSalas.get(1));

        } catch (IllegalStateException e) {
            throw new RuntimeException(e);
        }

        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + "</h1>");
        out.println("<h1>" + "numero de registros" + "</h1>");
        out.println("<h1>" + nuemroRegistros + "</h1>");
        out.println("</br>");
        out.println("<h1>" + "registro con id 1:" + "</h1>");
        out.println("<h1>" +tipoSalaEncontrado.getIdTipoSala()+"  "+ tipoSalaEncontrado.getNombre() + "</h1>");
        out.println("</br>");
        out.println("<h1>" + "registros:" + "</h1>");
        for (int i = 0; i < listSalas.size(); i++) {
        out.println("<h1>"+ listSalas.get(i).getIdTipoSala()+"  "+ listSalas.get(i).getNombre()+ "</h1>");

        }
        out.println("</br>");
        out.println("<h1>" + "registro modificado:" + "</h1>");
        out.println("<h1>" + tipoSalaCreada.getNombre() + "</h1>");
        out.println("</br>");
        out.println("</body></html>");
    }

    public void destroy() {
    }
}