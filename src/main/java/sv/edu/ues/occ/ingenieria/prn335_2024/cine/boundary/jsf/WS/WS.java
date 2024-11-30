package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf.WS;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        System.out.println("se conecto: "+ s.getId());
    }
    @OnClose
    public  void cerrar(Session s){

        msw.eliminar(s);
        System.out.println("se desconecto");
    }

    public void PropargarMensaje(String mensaje) throws IOException {
        System.out.println("tiene " + msw.getSessiones().size());
        try {
            for (Session session : msw.getSessiones()) {
                if (session.isOpen()) {
                    session.getBasicRemote().sendText(mensaje);
                    System.out.println("se envio ws");
                }
            }
                    System.out.println("no se envio ws: "+mensaje);
        }catch (Exception e){
            Logger.getLogger(WS.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
