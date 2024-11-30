//package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf.WS;
//
//import jakarta.inject.Inject;
//import jakarta.websocket.OnClose;
//import jakarta.websocket.OnMessage;
//import jakarta.websocket.OnOpen;
//import jakarta.websocket.Session;
//import jakarta.websocket.server.ServerEndpoint;
//import sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf.WS.ManagerSessionWS;
//
//import java.io.IOException;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//@ServerEndpoint("/notificadortiposala")
//public class TipoSalaEndPonit {
//    @Inject
//    ManagerSessionWS msw;
//    // al establecerce una conecion inmediatamente se ejecitara este metodo
//    @OnOpen
//    public  void conecto(Session s){
//        msw.agregar(s);
//        System.out.println("se conecto");
//    }
//    @OnClose
//    public  void cerrar(Session s){
//        msw.eliminar(s);
//        System.out.println("se conecto");
//    }
//    @OnMessage
//    public void PropargarMensaje(Session s, String mensaje) throws IOException {
//        for (Session session : msw.getSessiones()) {
//            if (session.isOpen() && session!=null) {
//                try {
//                    System.out.println(mensaje+" WS");
//                    session.getBasicRemote().sendText(mensaje);
//                }catch (Exception e){
//                    Logger.getLogger(getClass().getName()).log(Level.SEVERE,e.getMessage(),e);
//                }
//            }
//        }
//    }
//}
