package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf.rest.WS;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

import java.io.IOException;

@Named
@ApplicationScoped
@ServerEndpoint("/notificadortipoproducto")
public class TipoProductoEndPoint extends WS {
    @OnMessage
    @Override
    public void PropargarMensaje(String mensaje) throws IOException {
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
