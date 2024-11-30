package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf.rest.WS;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.websocket.Session;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@ApplicationScoped
@Named
public class ManagerSessionWS implements Serializable {
    final Set<Session> SESSIONES=new HashSet<>();
    public void agregar( Session s) {
        this.SESSIONES.add(s);
    }
    public void eliminar( Session s) {
        this.SESSIONES.remove(s);
    }
    public Set<Session> getSessiones(){
        return this.SESSIONES;
    }
}
