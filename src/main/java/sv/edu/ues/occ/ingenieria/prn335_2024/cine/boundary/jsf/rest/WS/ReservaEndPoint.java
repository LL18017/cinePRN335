package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf.rest.WS;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@ApplicationScoped
@ServerEndpoint("/notificadorreserva")
public class ReservaEndPoint extends WS {

    @OnMessage
    @Override
    public void PropargarMensaje(String mensaje) throws IOException {
        Logger.getLogger(getClass().getName()).log(Level.FINE, mensaje);
        super.PropargarMensaje(mensaje);
    }

    @OnClose
    @Override
    public void cerrar(Session s) {
        super.cerrar(s);
    }

    @OnOpen
    @Override
    public void conecto(Session s) {

        super.conecto(s);
    }
}
