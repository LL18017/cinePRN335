package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf.WS;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.ManagerSessionWS;

import java.io.IOException;
import java.io.Serializable;

@Named
@ApplicationScoped
@ServerEndpoint(value = "/notificadorws")
public class WS implements Serializable {
    @Inject
    ManagerSessionWS msw;
    // al establecerce una conecion inmediatamente se ejecitara este metodo
    @OnOpen
    public  void conecto(Session s){
        msw.agregar(s);
        System.out.println("se conecto");
    }
    @OnClose
    public  void cerrar(Session s){
        msw.eliminar(s);
        System.out.println("se conecto");
    }
    public void PropargarMensaje(String mensaje) throws IOException {
        for (Session session : msw.getSessiones()) {
            if (session.isOpen()) {
                session.getBasicRemote().sendText(mensaje);
            }
        }
    }
}
