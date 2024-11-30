package sv.edu.ues.occ.ingenieria.prn335_2024.cine.control;

import jakarta.inject.Inject;
import jakarta.websocket.*;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf.FrmTipoSala;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf.WS.ManagerSessionWS;

import java.io.IOException;

@ClientEndpoint
public class EndPoint {
    FrmTipoSala frmTipoSala;
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
    @OnMessage
    public void PropargarMensaje(Session s, String mensaje) throws IOException {
        System.out.println("datos recibidos"+mensaje);
       frmTipoSala.inicioRegistros();
    }

    public FrmTipoSala getFrmTipoSala() {
        return frmTipoSala;
    }

    public void setFrmTipoSala(FrmTipoSala frmTipoSala) {
        this.frmTipoSala = frmTipoSala;
    }
}
