package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf.rest.WS;

import jakarta.inject.Inject;
import jakarta.websocket.Session;

import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;


public class WS implements Serializable {
    @Inject
    ManagerSessionWS msw;



    public  void conecto(Session s){
        msw.agregar(s);
        System.out.println("se conecto: "+ s.getId());
    }

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
        }catch (Exception e){
            Logger.getLogger(WS.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
