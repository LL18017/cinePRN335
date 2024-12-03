package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ActionEvent;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf.rest.WS.FacturaEndPoint;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf.rest.WS.WS;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.*;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.*;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Named
@ViewScoped
public class FrmFactura extends AbstractFrm<Factura> implements Serializable {

    @Override
    public String paginaNombre() {
        return "Factura";
    }

    @Inject
    FacturaBean fBean;
    @Inject
    FacesContext fc;
    @Inject
    FacturaEndPoint facturaEndPoint;
    @Inject
    ProductoBean productoBean;
    @Inject
    ReservaBean reservaBean;
    @Inject
    ProgramacionBean programacionBean;
    @Inject
    SalaBean salaBean;
    @Inject
    ReservaDetalleBean reservaDetalleBean;
    @Inject
    AsientoBean asientoBean;


    Factura ultimoRegistro;
    Integer indexTab=0;

    //productos
    Integer idProducto=0;
    Integer cantidadProducto=1;
    List<Producto> listaProductos;
    List<String> productosSelecionados;
    String productoSeleccionado;

    //reserva
    Reserva reserva;
    Integer idReservaSelecionada;
    Integer idSalaSelecionada;
    Integer idProgramacionSelecionada;
    Date fechaActual;
    List<Sala> salaList;
    List<Programacion> programacionList;
    List<Asiento> asientosList;
    List<Asiento> asientosDisponibles;
    List<Asiento> asientosSelecionados;
    List<ReservaDetalle> reservaDetalleList;
    String idAsientoSelecionado;
    String idAsientoELiminado;



    @Override
    public void instanciarRegistro() {
        this.registro= new Factura();
        registro.setFecha(new Date());
        estado= ESTADO_CRUD.CREAR;
    }

    @Override
    public void inicioRegistros() {
        ultimoRegistro= new Factura();
        listaProductos=productoBean.findAll();
        super.inicioRegistros();

    }

    @Override
    public FacesContext getFC() {
        return fc;
    }

    @Override
    public AbstractDataPersist<Factura> getAbstractDataPersist() {
        return fBean;
    }



    @Override
    public String getIdByObject(Factura object) {
        if (object!=null){
            return object.getIdFactura().toString();
        }
        return "";
    }

    @Override
    public Factura getObjectById(String id) {
        if (id!=null && modelo!=null && modelo.getWrappedData()!=null) {
            return modelo.getWrappedData().stream().filter(r->id.equals(r.getIdFactura().toString())).findFirst().orElse(null);

        }
        return null;
    }

    @Override
    public void selecionarFila(SelectEvent<Factura> event) {
        enviarMensaje(FacesMessage.SEVERITY_INFO,"SE HA SELECIONADO LA FACTURA # "+registro.getIdFactura());
        this.estado=ESTADO_CRUD.MODIFICAR;
        indexTab=0;

    }

    @Override
    public WS getWebsocketController() {
        return facturaEndPoint;
    }


    @Override
    public void btnGuardarHandler(ActionEvent e) {
        ultimoRegistro=registro;
        super.btnGuardarHandler(e);
    }

    @Override
    public void btnModificarHandler(ActionEvent ex) {
        ultimoRegistro=registro;
        super.btnModificarHandler(ex);
    }

    @Override
    public void btneEliminarHandler(ActionEvent ex) {
        ultimoRegistro=registro;
        super.btneEliminarHandler(ex);
    }

    public void ultimoSelecionado(){
        registro=ultimoRegistro;
        indexTab=1;
    }


    @Override
    public void btnNuevoHandler(ActionEvent actionEvent) {
        indexTab=1;
        super.btnNuevoHandler(actionEvent);
    }


    public void changeTab(TabChangeEvent e) {
        String titulo = e.getTab().getTitle();
        if (titulo.equals("generalidades")){
            inicioRegistros();
        } else if (titulo.equals("productos")) {
            indexTab=1;

        }else if (titulo.equals("Asientos")) {
        } else if (titulo.equals("resumen")) {
          instanciarRegistro();
        }
    }


    public Factura getUltimoRegistro() {
        return ultimoRegistro;
    }

    public void setUltimoRegistro(Factura ultimoRegistro) {
        this.ultimoRegistro = ultimoRegistro;
    }

    public Integer getIndexTab() {
        return indexTab;
    }

    public void setIndexTab(Integer indexTab) {
        this.indexTab = indexTab;
    }

    public List<Producto> getListaProductos() {
        return listaProductos;
    }

    public void setListaProductos(List<Producto> listaProductos) {
        this.listaProductos = listaProductos;
    }

    public Integer getCantidadProducto() {
        return cantidadProducto;
    }

    public void setCantidadProducto(Integer cantidadProducto) {
        this.cantidadProducto = cantidadProducto;
    }

    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public List<String> getProductosSelecionados() {
        return productosSelecionados;
    }

    public void setProductosSelecionados(List<String> productosSelecionados) {
        this.productosSelecionados = productosSelecionados;
    }

    public String getProductoSeleccionado() {
        return productoSeleccionado;
    }

    public void setProductoSeleccionado(String productoSeleccionado) {
        this.productoSeleccionado = productoSeleccionado;
    }

    public Integer getIdProgramacionSelecionada() {
        return idProgramacionSelecionada;
    }

    public void setIdProgramacionSelecionada(Integer idProgramacionSelecionada) {
        reserva.setIdProgramacion(programacionList.stream().filter(p->p.getIdProgramacion().equals(idProgramacionSelecionada.longValue())).findFirst().orElse(null));
        this.idProgramacionSelecionada = idProgramacionSelecionada;
    }

    public Integer getIdSalaSelecionada() {
        return idSalaSelecionada;
    }

    public void setIdSalaSelecionada(Integer idSalaSelecionada) {
        reserva.getIdProgramacion().setIdSala(salaList.stream().filter(s->s.getIdSala().equals(idSalaSelecionada)).findFirst().orElse(null));
        this.idSalaSelecionada = idSalaSelecionada;
    }

    public Integer getIdReservaSelecionada() {
        return idReservaSelecionada;
    }

    public void setIdReservaSelecionada(Integer idReservaSelecionada) {
        this.idReservaSelecionada = idReservaSelecionada;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }

    public List<Programacion> getProgramacionList() {
        return programacionList;
    }

    public void setProgramacionList(List<Programacion> programacionList) {
        this.programacionList = programacionList;
    }

    public List<Sala> getSalaList() {
        return salaList;
    }

    public void setSalaList(List<Sala> salaList) {
        this.salaList = salaList;
    }

    public String getIdAsientoSelecionado() {
        return idAsientoSelecionado;
    }

    public void setIdAsientoSelecionado(String idAsientoSelecionado) {
        this.idAsientoSelecionado = idAsientoSelecionado;
    }

    public List<Asiento> getAsientosList() {
        return asientosList;
    }

    public void setAsientosList(List<Asiento> asientosList) {
        this.asientosList = asientosList;
    }

    public Date getFechaActual() {
        return new Date();
    }

    public void setFechaActual(Date fechaActual) {
        this.fechaActual = fechaActual;
    }

    public String getIdAsientoELiminado() {
        return idAsientoELiminado;
    }

    public void setIdAsientoELiminado(String idAsientoELiminado) {
        this.idAsientoELiminado = idAsientoELiminado;
    }

    public List<Asiento> getAsientosSelecionados() {
        return asientosSelecionados;
    }

    public void setAsientosSelecionados(List<Asiento> asientosSelecionados) {
        this.asientosSelecionados = asientosSelecionados;
    }

    public List<Asiento> getAsientosDisponibles() {
        return asientosDisponibles;
    }

    public void setAsientosDisponibles(List<Asiento> asientosDisponibles) {
        this.asientosDisponibles = asientosDisponibles;
    }

    public void agregarProductos() {
        try {
            Producto productoSelecionado = listaProductos.stream().
                    filter(a -> a.getIdProducto().equals(idProducto.longValue())).findFirst().orElse(null);
            if (productosSelecionados == null) {
                productosSelecionados = new ArrayList<>();
            }
            if (productoSelecionado != null && cantidadProducto>0) {
                productosSelecionados.add(productoSelecionado.getNombre()+","+cantidadProducto);
            }
            idProducto = null;
            cantidadProducto=1;
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public void eliminarProductos() {
        try {
            String productoEliminado = productosSelecionados.stream().
                    filter(a -> a.equals(productoSeleccionado)).findFirst().orElse(null);

            if (productoEliminado != null) {
                productosSelecionados.remove(productoEliminado);
            }
            idProducto = null;
            cantidadProducto=0;
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public void buscarReserva(){
        try {
           reserva= reservaBean.findById(idReservaSelecionada.longValue());
            buscarSalas();
            idProgramacionSelecionada=reserva.getIdProgramacion().getIdProgramacion().intValue();
            idSalaSelecionada=salaList.getFirst().getIdSala();
            buscarProgramacion();
            buscarAsientos();
            buscarReservaDetalle(reserva.getIdReserva().intValue());
           enviarMensaje(FacesMessage.SEVERITY_INFO,"reserva encontrada");
        }catch (Exception e){
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
           enviarMensaje(FacesMessage.SEVERITY_ERROR,"reserva no se ha encontrado");
        }
    }
    public void buscarSalas(){
        try {
            salaList=salaBean.findAll();
        }catch (Exception e){
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            enviarMensaje(FacesMessage.SEVERITY_ERROR,"problema ala encontar salas");
        }
    }
    public void buscarProgramacion(){
        try {
            programacionList=programacionBean.getProgramacionByIdSalaRangoTiempo(new Sala(idSalaSelecionada));
        }catch (Exception e){
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            enviarMensaje(FacesMessage.SEVERITY_ERROR,"problema ala encontar promacaiones");
        }
    }
    public void buscarAsientos(){
        try {
            asientosList=asientoBean.findAsientosBySalaandProgramacion(new Sala(idSalaSelecionada),new Programacion(idProgramacionSelecionada.longValue()));
            enviarMensaje(FacesMessage.SEVERITY_INFO,"reserva encontrada");
        }catch (Exception e){
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            enviarMensaje(FacesMessage.SEVERITY_ERROR,"reserva no se ha encontrado");
        }
    }
    public void buscarReservaDetalle(Integer idReserva){
        try {
            reservaDetalleList=reservaDetalleBean.findByIdReserva(idReserva);
            asientosSelecionados=reservaDetalleList.stream().map(ReservaDetalle::getIdAsiento).collect(Collectors.toCollection(ArrayList::new));
        }catch (Exception e){
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            enviarMensaje(FacesMessage.SEVERITY_ERROR,"reserva detalle no se ha encontrado");
        }
    }
    public void crearReserva(){
        try {
           reserva=new Reserva();
            buscarSalas();
            if (salaList==null) {
                idSalaSelecionada=null;
            }else{
            idSalaSelecionada=salaList.getFirst().getIdSala();
            }

            buscarProgramacion();

            if (programacionList==null) {
                idProgramacionSelecionada=null;
            }else {
                idProgramacionSelecionada=programacionList.getFirst().getIdProgramacion().intValue();
            }
            buscarAsientos();

            reserva.setIdProgramacion(programacionList.getFirst());
            reserva.getIdProgramacion().setIdSala(salaList.getFirst());
            reserva.setFechaReserva(getFechaActual());

        }catch (Exception e){
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public void agregarAsiento() {
        try {
            Asiento asientoSelecionado = asientosList.stream().
                    filter(a -> a.getIdAsiento().toString().equals(idAsientoSelecionado)).findFirst().orElse(null);
            if (asientosSelecionados == null) {
                asientosSelecionados = new ArrayList<>();
            }
            if (asientoSelecionado != null) {
                asientosList.remove(asientoSelecionado);
                asientosSelecionados.add(asientoSelecionado);
            }
            idAsientoSelecionado = "";
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public void eliminarAsiento() {
        try {
            Asiento asientoEliminado = asientosSelecionados.stream().
                    filter(a -> a.getIdAsiento().toString().equals(idAsientoELiminado)).findFirst().orElse(null);

            if (asientoEliminado != null) {
                asientosSelecionados.remove(asientoEliminado);
                asientosList.add(asientoEliminado);
                asientosList.sort(Comparator.comparingLong(Asiento::getIdAsiento));
            }
            idAsientoSelecionado = "";
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }


    public void pruebas() {
        enviarMensaje(FacesMessage.SEVERITY_INFO,"prueba"+idAsientoSelecionado);
    }

    public void nextTab() {
        switch (indexTab) {
            case 0:
                indexTab++;
                break;
            case 1:
                indexTab++;
                break;
            case 2:
                System.out.println("cambios");
                if (reserva!=null) {
                    indexTab++;
                    System.out.println("pasas");
                break;
                }
                enviarMensaje(FacesMessage.SEVERITY_WARN,"debes selecionar o crear una reserva");
                break;
            case 3:
                indexTab = 0;
                registro = null;
                ultimoRegistro = null;
                idReservaSelecionada=null;
               idSalaSelecionada=null;
                idProgramacionSelecionada=null;
                programacionList=null;
                asientosList=null;
                asientosDisponibles=null;
                idAsientoSelecionado="";
                idAsientoELiminado="";
                asientosSelecionados=null;

                break;
            default:
                indexTab++;
        }
    }

    public void BackTab() {
        if (indexTab > 0) {
            indexTab--;
        }
    }

    public void cancelarReserva(){
        reserva=null;
        idReservaSelecionada=null;
    }

}
