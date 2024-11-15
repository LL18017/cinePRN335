package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.Dependent;
import jakarta.faces.annotation.ManagedProperty;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
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
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Programacion;

import jakarta.faces.view.ViewScoped;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Sala;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.RecursiveTask;
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

//    @ManagedProperty("#{frmSala}")
//    private FrmSala frmSala;

    List<Pelicula> peliculasDisponibles = new ArrayList<>();

    String idPeliculaProgramar;

    Sala SalaSelecionada;
    Integer idSalaSelecionada;

    private ScheduleModel eventModel;
    private ScheduleEvent<?> event = new DefaultScheduleEvent<>();

    @Inject FacesContext fc;

    List<Programacion> programaciones;

    @Override
    public void instanciarRegistro() {
        registro=new Programacion();
        programaciones=pBean.findAll();

    }
    @PostConstruct
    @Override
    public void inicioRegistros() {
        super.inicioRegistros();
        BuscarPeliculaDisponibles();
        List<Programacion> programacionesPorSala=findProgramacionBySala();
        eventModel = new DefaultScheduleModel();
        if (programacionesPorSala!=null && !programacionesPorSala.isEmpty()) {
            programacionesPorSala.forEach(p->{
                eventModel.addEvent(DefaultScheduleEvent.builder()
                        .title(p.getIdPelicula().getNombre())
                        .startDate(p.getDesde().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())
                        .endDate(p.getHasta().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()) // +1 hora
                        .description(p.getIdPelicula().getSinopsis())
                        .build());
            });
        }
    }










    public List<Programacion> findProgramacionBySala() {
        try {
            return pBean.getProgramacionByIdSala(new Sala(1));
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

    public void onEventSelect(org.primefaces.event.SelectEvent<ScheduleEvent<?>> selectEvent) {
        event = selectEvent.getObject();
    }

    public void onDateSelect(org.primefaces.event.SelectEvent<Date> selectEvent) {
        Date date = selectEvent.getObject();
        event = DefaultScheduleEvent.builder()
                .title("Nuevo Evento")
                .startDate(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())
                .endDate(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())
                .build();
    }


    public List<String> sugerencias(String clave) {

        List<String> sugerencias=new ArrayList<>();
        if (!peliculasDisponibles.isEmpty()){

            peliculasDisponibles.forEach(p->{

                sugerencias.add(
                        p.getIdPelicula() + "_" +
                                p.getNombre() );
            });

            List<String> results = new ArrayList<>();
            // Filtrar las sugerencias que coincidan con el texto ingresado
            for (String option : sugerencias) {
                if (option.toLowerCase().contains(clave.toLowerCase())) {
                    results.add(option);
                }
            }
            return results;
        }
        return List.of();
    }


    public void pruebas(){
        System.out.println("prueba soijapo");
    }
    public List<Programacion> getProgramaciones() {
        return programaciones;
    }

    public void setProgramaciones(List<Programacion> programaciones) {
        this.programaciones = programaciones;
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

    public String getIdPeliculaProgramar() {
        return idPeliculaProgramar;
    }

    public void setIdPeliculaProgramar(String idPeliculaProgramar) {
        this.idPeliculaProgramar = idPeliculaProgramar;
    }

    public Sala getSalaSelecionada() {
        return SalaSelecionada;
    }

    public void setSalaSelecionada(Sala salaSelecionada) {
        SalaSelecionada = salaSelecionada;
    }

    public void addEvent() {

        if (colicionFechas() && verificarPelicula() && verificarSala()){

        if (event.getId() == null) {

            eventModel.addEvent(event);
        } else {
            eventModel.updateEvent(event);
        }
        event = new DefaultScheduleEvent<>();
        }

    }

    public boolean colicionFechas(){
            if (event!=null && event.getStartDate()!=null && event.getEndDate()!=null) {
                    if (event.getStartDate().isAfter(event.getEndDate())){
                        enviarMensaje(FacesMessage.SEVERITY_ERROR,"problemas de fechas");

                        return false;
                }
                    return true;
            }
            enviarMensaje(FacesMessage.SEVERITY_ERROR,"no se ha selelcionado fechas");
            return false;
    }

    public boolean verificarPelicula(){
        if (idPeliculaProgramar!=null && !idPeliculaProgramar.isEmpty()){
            BuscarPeliculaDisponibles();
            Pelicula peli=peliculasDisponibles.stream().filter(p->p.getIdPelicula().toString().equals(idPeliculaProgramar)).findFirst().orElse(null);
            enviarMensaje(FacesMessage.SEVERITY_INFO,"el pelicula seleccionada es "+peli.getNombre().toString());
            return true;
        }
        enviarMensaje(FacesMessage.SEVERITY_ERROR,"no se ha seleccionado pelicula");
        return false;
    }

    public boolean verificarSala(){
        if (getSalaSelecionada()!=null){
//            idSalaSelecionada=frmSala.registro.getIdSala();
            enviarMensaje(FacesMessage.SEVERITY_INFO,"el sala seleccionada es "+getSalaSelecionada().getNombre());
            return true;
        }

        enviarMensaje(FacesMessage.SEVERITY_ERROR,"no se ha seleccionado sala"+idSalaSelecionada);
        return false;
    }

    public void enviarMensaje(FacesMessage.Severity severity, String mensaje) {
        fc.addMessage(null,new FacesMessage(severity,mensaje,null));
    }

}
