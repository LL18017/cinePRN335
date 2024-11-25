package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf;

import jakarta.enterprise.context.Dependent;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ActionEvent;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersist;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.PeliculaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.ProgramacionBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Pelicula;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.PeliculaCaracteristica;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Programacion;

import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Sala;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Named
@Dependent
public class FrmProgramacion extends AbstractFrm<Programacion> implements Serializable {
    @Inject
    ProgramacionBean pBean;
    @Inject
    PeliculaBean peliculaBean;

    List<Pelicula> peliculasDisponibles = new ArrayList<>();

    Integer idPeliculaProgramar;
    String nombrePelicula;

    Sala SalaSelecionada;
    Integer idSalaSelecionada;

    private ScheduleModel eventModel;
    private ScheduleEvent<?> event = new DefaultScheduleEvent<>();


    Date desde;
    Date hasta;
    String dia;

    @Inject FacesContext fc;

    List<Programacion> programacionesDisponibles;

    @Override
    public void instanciarRegistro() {
        registro=new Programacion();
        estado=ESTADO_CRUD.CREAR;
        programacionesDisponibles =pBean.findAll();

    }
//    @PostConstruct
    @Override
    public void inicioRegistros() {
        super.inicioRegistros();
        BuscarPeliculaDisponibles();
        programacionesDisponibles=findProgramacionBySala();
        eventModel = new DefaultScheduleModel();
        if (programacionesDisponibles!=null && !programacionesDisponibles.isEmpty()) {
            programacionesDisponibles.forEach(p->{
                eventModel.addEvent(eventBuilder(p,6));
            });
        }
    }

    public List<Programacion> findProgramacionBySala() {
        try {
            return pBean.getProgramacionByIdSalaRangoTiempo(getSalaSelecionada());
        }catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return List.of();
    }
    @Override
    public FacesContext getFC() {
        return fc;
    }

    @Override
    public AbstractDataPersist<Programacion> getAbstractDataPersist() {
        return pBean;
    }


    @Override
    public String getIdByObject(Programacion object) {
        if (object.getIdProgramacion()!=null){
            return object.getIdProgramacion().toString();
        }
        return "";
    }

    @Override
    public Programacion getObjectById(String id) {
        fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Programacion encontrado", ""));
        if (id!=null && modelo.getWrappedData()!=null){
            return modelo.getWrappedData().stream().
                    filter(p -> p.getIdProgramacion().toString().equals(id)).findFirst().orElse(null);
        }

        return null;
    }

    @Override
    public void selecionarFila(SelectEvent<Programacion> event) {
        Programacion programacionSelected = (Programacion) event.getObject();
        this.registro= programacionSelected;
        estado =ESTADO_CRUD.MODIFICAR;
        fc.addMessage(null,new FacesMessage("se ha selecionado una programacion"+programacionSelected));
    }
    public void BuscarPeliculaDisponibles() {
        try {
            peliculasDisponibles=peliculaBean.findAll();
        }catch (Exception e){
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }

    @Override
    public String paginaNombre() {
        return "Programacion";
    }

    public void onEventSelect(SelectEvent<ScheduleEvent<?>> selectEvent) {
        // Obtener el evento seleccionado
        Integer idProgramacionSElecionada = Integer.valueOf(selectEvent.getObject().getId());

        System.out.println("selecionado: "+idProgramacionSElecionada);
        try {

        registro=programacionesDisponibles.stream().filter(p->p.getIdProgramacion().toString().equals(idProgramacionSElecionada.toString())).findFirst().orElse(null);
        estado =ESTADO_CRUD.MODIFICAR;
        if (registro!=null){
            nombrePelicula=registro.getIdPelicula().getNombre();
        }
        }catch (Exception e){
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        if (registro!=null){
        System.out.println("selecionado: "+nombrePelicula);
        }else {

        }

    }

    public void onDateSelect(SelectEvent<LocalDateTime> selectEvent) {
        if (selectEvent.getObject()!=null) {
            instanciarRegistro();
            setNombrePelicula(null);
            try {
                LocalDateTime t = selectEvent.getObject();
                registro.setDesde(Date.from(t.atZone(ZoneId.systemDefault()).toInstant()));
                registro.setHasta(Date.from(t.atZone(ZoneId.systemDefault()).toInstant()));
            } catch (Exception e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            }
        }
    }


    public List<Pelicula> getPeliculasDisponibles() {
        return peliculasDisponibles;
    }

    public void setPeliculasDisponibles(List<Pelicula> peliculasDisponibles) {
        this.peliculasDisponibles = peliculasDisponibles;
    }

    public void SelecionarFecha() {
        System.out.println("el dia es");
    }


    public List<String> sugerencias(String query) {


        return peliculasDisponibles.stream().
                map(Pelicula::getNombre)
                .filter(item -> item.toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }


    public void cambioPelicula(){
        System.out.println("se esta cambiando");
        if (!nombrePelicula.isEmpty() && !peliculasDisponibles.isEmpty()){
            Pelicula Selecionada=peliculasDisponibles.stream().filter(p->p.getNombre().equals(nombrePelicula)).findFirst().orElseGet(null);
           PeliculaCaracteristica pc = Selecionada.getPeliculaCaracteristicaList().stream().filter(p-> p.getIdTipoPelicula().getNombre().equalsIgnoreCase("DURACION")).findFirst().orElseGet(null);

            LocalDateTime now = LocalDateTime.now().withMinute(0).withSecond(0).withNano(0);
            now.plusHours(1);
            LocalDateTime nuevaFechaHora = now.plusMinutes(Integer.parseInt(pc.getValor()));
            // Convertir a Date
            registro.setDesde(Date.from(now.atZone(ZoneId.systemDefault()).toInstant()));
             registro.setHasta(Date.from(nuevaFechaHora.atZone(ZoneId.systemDefault()).toInstant()));



        }
    }


    public void pruebas(){
        System.out.println("prueba soijapo");
    }
    public List<Programacion> getProgramacionesDisponibles() {
        return programacionesDisponibles;
    }

    public void setProgramacionesDisponibles(List<Programacion> programacionesDisponibles) {
        this.programacionesDisponibles = programacionesDisponibles;
    }

    public ScheduleModel getEventModel() {
        return eventModel;
    }

    public void setEventModel(ScheduleModel eventModel) {
        this.eventModel = eventModel;
    }

    public ScheduleEvent<?> getEvent() {
        return event;
    }
    public void setEvent(ScheduleEvent<?> event) {
        this.event = event;
    }

    public Integer getIdPeliculaProgramar() {

        return idPeliculaProgramar;
    }

    public void setIdPeliculaProgramar(Integer idPeliculaProgramar) {
        this.idPeliculaProgramar = idPeliculaProgramar;
        registro.setIdPelicula(peliculasDisponibles.stream().filter(pr-> pr.getIdPelicula().equals((Long.valueOf(idPeliculaProgramar.toString())))).findFirst().orElse(null));
    }

    public Sala getSalaSelecionada() {
        return SalaSelecionada;
    }

    public void setSalaSelecionada(Sala salaSelecionada) {
        SalaSelecionada = salaSelecionada;
    }

    public Date getDesde() {
        return desde;
    }

    public void setDesde(Date desde) {
        registro.setDesde(desde);
        this.desde = desde;
    }

    public Date getHasta() {
        return hasta;
    }

    public void setHasta(Date hasta) {
        registro.setHasta(hasta);
        this.hasta = hasta;
    }

    public String getNombrePelicula() {
        return nombrePelicula;
    }

    public void setNombrePelicula(String nombrePelicula) {
        this.nombrePelicula = nombrePelicula;
        registro.setIdPelicula(peliculasDisponibles.stream().filter(p->p.getNombre().equals(nombrePelicula)).findFirst().orElse(null));
    }

    public void onItemSelect(SelectEvent<String> event) {
        String itemSeleccionado = getNombrePelicula(); // Este es el itemValue definido en el p:autoComplete
        System.out.println("el item selecionado es");
//        this.idPeliculaProgramar = idSeleccionado;
//        this.registro = programacionesDisponibles.stream()
//                .filter(pr -> pr.getIdProgramacion().equals(Long.valueOf(idSeleccionado)))
//                .findFirst()
//                .orElse(null);
//        estado=ESTADO_CRUD.MODIFICAR;

    }


    public void addEvent() {
        if (colicionFechas() && verificarPelicula() && verificarSala()){
        if (event.getId() == null) {
            try {
                Pelicula peliculaSelecionada = peliculasDisponibles.stream().filter(p->p.getIdPelicula().toString().equals(idPeliculaProgramar)).findFirst().orElse(null);
                Sala salaSelecionada = getSalaSelecionada();
                if (salaSelecionada != null && peliculaSelecionada != null) {
                    Programacion pro= new Programacion();
                    pro.setIdPelicula(peliculaSelecionada);
                    pro.setIdSala(salaSelecionada);
                    pro.setDesde(desde);
                    pro.setHasta(hasta);
                    System.out.println(desde);
                    System.out.println(hasta);

                    if (pBean.verificarColision(desde,hasta ,salaSelecionada)){
                            pBean.create(pro);
                            eventModel.addEvent(eventBuilder(pro,6));
                            desde=null;
                            hasta=null;
                            idPeliculaProgramar=null;

                            return;

                    }
                    enviarMensaje(FacesMessage.SEVERITY_ERROR,"choque de programaciones");
                    return;

                }
                enviarMensaje(FacesMessage.SEVERITY_INFO,"se ha guardado la programacion");
            }catch (Exception e){
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
                enviarMensaje(FacesMessage.SEVERITY_ERROR, "error al crear programacio");
            }
        } else {
            eventModel.updateEvent(event);
        }
        event = new DefaultScheduleEvent<>();
        }


    }
        public void ModicarProgramacion(){
                System.out.println("paso");
            if (colicionFechas() && verificarPelicula() && verificarSala()){
                System.out.println("paso");
                if (event.getId() == null) {
                    try {
                        Pelicula peliculaSelecionada = peliculasDisponibles.stream().filter(p->p.getIdPelicula().toString().equals(idPeliculaProgramar)).findFirst().orElse(null);
                        Sala salaSelecionada = getSalaSelecionada();
                        if (salaSelecionada != null && peliculaSelecionada != null) {
                            Programacion pro= new Programacion();
                            pro.setIdPelicula(peliculaSelecionada);
                            pro.setIdSala(salaSelecionada);
                            pro.setDesde(desde);
                            pro.setHasta(hasta);
                            System.out.println(desde);
                            System.out.println(hasta);
                            desde=null;
                            hasta=null;
                            idPeliculaProgramar=null;


                            if (pBean.verificarColision(desde,hasta,salaSelecionada)){
                                pBean.update(pro);
                                eventModel.addEvent(eventBuilder(pro,6));
                                return;
                            }
                            enviarMensaje(FacesMessage.SEVERITY_ERROR,"choque de programaciones");
                            return;

                        }
                        enviarMensaje(FacesMessage.SEVERITY_INFO,"se ha modificado la programacion");
                    }catch (Exception e){
                        Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
                        enviarMensaje(FacesMessage.SEVERITY_ERROR, "error al crear programacio");
                    }
                } else {
                    eventModel.updateEvent(event);
                }
                event = new DefaultScheduleEvent<>();
            }

        }

    public void removeEvent(Programacion pro){
        eventModel.deleteEvent(getEventModel().getEvent(pro.getIdProgramacion().toString()));

    }
    public DefaultScheduleEvent<Object> eventBuilder(Programacion p,int t) {
            System.out.println("el registro es: "+p);
        if (p!=null && p.getDesde()!=null && p.getHasta()!=null) {
            try {
                return DefaultScheduleEvent.builder()
                        .title(p.getIdPelicula().getNombre())

                        // Ajustar la fecha de inicio
                        .startDate(p.getDesde().toInstant()
                                .atZone(ZoneId.systemDefault()) // Interpretar la fecha como UTC
                                .toLocalDateTime()
                                .plusHours(t)
                        )

                        // Ajustar la fecha de fin
                        .endDate(p.getHasta().toInstant()
                                .atZone(ZoneId.systemDefault()) // Interpretar la fecha como UTC
                                .toLocalDateTime()
                                .plusHours(t)
                        )
                        .description(p.getIdPelicula().getSinopsis())
                        .id(p.getIdProgramacion().toString())
                        .build();
            }catch (Exception e){
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
                return null;
            }


        }
//        enviarMensaje(FacesMessage.SEVERITY_ERROR,"ERROR AL OBTENER LAS FECHAS");
         return null;
    }

    public boolean colicionFechas(){
            if (registro!=null && registro.getDesde()!=null && registro.getHasta()!=null) {
                    if (registro.getDesde().after(registro.getHasta())){
                        enviarMensaje(FacesMessage.SEVERITY_ERROR,"problemas de fechas");
                        return false;
                }
                return true;
            }
            enviarMensaje(FacesMessage.SEVERITY_ERROR,"no se ha selelcionado fechas");
            return false;
    }

    public boolean verificarPelicula(){
        if (!nombrePelicula.isEmpty()){
            BuscarPeliculaDisponibles();
            Pelicula peli=peliculasDisponibles.stream().filter(p->p.getNombre().equals(nombrePelicula)).findFirst().orElse(null);
//            enviarMensaje(FacesMessage.SEVERITY_INFO,"el pelicula seleccionada es "+peli.getNombre().toString());
            System.out.println("paso verificar peliculas");
            return true;
        }
        enviarMensaje(FacesMessage.SEVERITY_ERROR,"no se ha seleccionado pelicula");
        return false;
    }

    public boolean verificarSala(){
        if (getSalaSelecionada()!=null){
//            idSalaSelecionada=frmSala.registro.getIdSala();
            return true;
        }

        enviarMensaje(FacesMessage.SEVERITY_ERROR,"no se ha seleccionado sala"+idSalaSelecionada);
        return false;
    }

    public void enviarMensaje(FacesMessage.Severity severity, String mensaje) {
        fc.addMessage(null,new FacesMessage(severity,mensaje,null));
    }


    public void cancelarProgramacion(){
        registro=null;

    }

    public void resertearVariables(){
        desde=null;
        hasta=null;
        idPeliculaProgramar=null;

    }
    @Override
    public void btnCancelarHandler(ActionEvent actionEvent) {
        resertearVariables();
        registro=null;
        super.btnCancelarHandler(actionEvent);
    }

    @Override
    public void btnGuardarHandler(ActionEvent e) {
        if (colicionFechas() && verificarPelicula() && verificarSala()){
//        enviarMensaje(FacesMessage.SEVERITY_INFO,"guardando");
            try {
                if (pBean.verificarColision(registro.getDesde(),registro.getHasta(),getSalaSelecionada())){
                    registro.setIdSala(getSalaSelecionada());
                    Programacion pro= new Programacion();
                    pro=registro;
                    super.btnGuardarHandler(e);
                    eventModel.addEvent(eventBuilder(pro,6));
                    resertearVariables();
                    return;
                }
                enviarMensaje(FacesMessage.SEVERITY_ERROR,"choque de programaciones");
            }catch (Exception ex){
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
                enviarMensaje(FacesMessage.SEVERITY_ERROR,"error al guardar");
            }
        }
    }

    @Override
    public void btnModificarHandler(ActionEvent e) {
        if (colicionFechas() && verificarPelicula()){
//        enviarMensaje(FacesMessage.SEVERITY_INFO,"guardando");
            try {
                if (pBean.verificarColision(registro.getDesde(),registro.getHasta(),getSalaSelecionada())){
                    registro.setIdSala(getSalaSelecionada());
                    Programacion pro= new Programacion();
                    pro=registro;
                    super.btnModificarHandler(e);
                    resertearVariables();
                    inicioRegistros();
                    return;
                }
                enviarMensaje(FacesMessage.SEVERITY_ERROR,"choque de programaciones");
            }catch (Exception ex){
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
                enviarMensaje(FacesMessage.SEVERITY_ERROR,"error al guardar");
            }
        }
    }

    @Override
    public void btneEliminarHandler(ActionEvent ex) {
        Programacion pro=registro;
        super.btneEliminarHandler(ex);
        inicioRegistros();
        resertearVariables();
    }
}
