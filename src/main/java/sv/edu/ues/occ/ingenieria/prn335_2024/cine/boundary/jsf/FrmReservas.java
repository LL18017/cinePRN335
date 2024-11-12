package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.faces.component.UIInput;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.AjaxBehaviorEvent;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.event.SelectEvent;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersist;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.ProgramacionBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.ReservaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.TipoReservaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Programacion;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Reserva;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoReserva;

import javax.swing.event.ChangeEvent;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@ViewScoped
@Named
public class FrmReservas extends AbstractFrm<Reserva> implements Serializable {
    @Inject
    ReservaBean reservaBean;
    @Inject
    FacesContext fc;
    @Inject
    TipoReservaBean tipoReservaBean;
    @Inject
    ProgramacionBean programacionBean;


    //variables para la pagina
    int indiceTab=0;
    TipoReserva tipoReservaSelecionada;
    Date fechaReservaSelecionada;
    int idTipoReservaSelecionada;
    List<TipoReserva> tipoReservasDisponibles;
    List<Programacion> programaciones;
    Programacion programacionSelecionada;
    String idProgramacion;
    Date fechaActual;

    @Override
    public void instanciarRegistro() {
        registro=new Reserva();
    }

    @PostConstruct
    @Override
    public void init() {
        super.init();
        tipoReservasDisponibles=tipoReservaBean.findAll();
        System.out.println("la cantidad de reservas son" +tipoReservasDisponibles.size());
    }

    @Override
    public FacesContext getFC() {
        return fc;
    }

    @Override
    public AbstractDataPersist<Reserva> getAbstractDataPersist() {
        return reservaBean;
    }

    @Override
    public String getIdByObject(Reserva object) {
        if (object != null) {
            return object.getIdReserva().toString();
        }
        return "";
    }

    @Override
    public Reserva getObjectById(String id) {
        if (id != null && modelo!=null && modelo.getWrappedData()!=null) {
            return modelo.getWrappedData().stream().filter(r->id.equals(r.getIdReserva().toString())).findFirst().orElse(null);
        }
        return null;
    }

    @Override
    public void selecionarFila(SelectEvent<Reserva> event) {

    }

    @Override
    public String paginaNombre() {
        return "Reserva XD  ";
    }

    //funcionalidad
    public void nextTab() {
        switch (indiceTab) {
            case 0:
                if (tipoReservaSelecionada!=null && fechaReservaSelecionada!=null) {
                    indiceTab++;
                    buscarProgramaciones();
                }
                break;
                case 1:
                if (tipoReservaSelecionada!=null && fechaReservaSelecionada!=null) {
                    indiceTab++;
                }
                break; case 2:
                if (tipoReservaSelecionada!=null && fechaReservaSelecionada!=null) {
                    indiceTab++;
                }
                break;
        }
    }
    public void BackTab() {
       if (indiceTab>0){
           indiceTab--;
       }
    }
    public void onTipoReservaChange( ) {
        if (idTipoReservaSelecionada!=0 && !tipoReservasDisponibles.isEmpty()){
        tipoReservaSelecionada = tipoReservasDisponibles.stream().filter(r->r.getIdTipoReserva().equals(idTipoReservaSelecionada)).findFirst().orElse(null);
        }
    }
    public void cambiarTab(){
        buscarProgramaciones();
    }
    public void buscarProgramaciones() {
        programaciones=programacionBean.findProgramacionesByDate(fechaReservaSelecionada);
        programaciones=programaciones.stream().filter(p->(fechaReservaSelecionada.compareTo(p.getDesde())<0 && fechaReservaSelecionada.compareTo(p.getHasta())>0)).collect(Collectors.toList());

    }

    public List<String> sugerencias(String clave){
        List<String> sugerencias=new ArrayList<>();
        if (!programaciones.isEmpty()){
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            programaciones.forEach(p->{

                sugerencias.add(
                        p.getIdProgramacion() + "_" +
                                p.getIdPelicula().getNombre() + "," +
                                p.getIdSala().getNombre() + " (" +
                                sdf.format(p.getDesde()) + "-" + sdf.format(p.getHasta()) + ")");
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

    public void onItemSelect(AjaxBehaviorEvent event) {
        // Obtener el valor del input automáticamente
        String selectedValue = (String) ((UIInput) event.getSource()).getValue();
        System.out.println("Seleccionaste: " + selectedValue);
    }
    public void onProgramacionChange() {
        System.out.println("hola");
        if (!programaciones.isEmpty()){
        programacionSelecionada = programaciones.stream().filter(p->p.getIdProgramacion().toString().equals(idProgramacion)).findFirst().orElse(null);

        }




    }
    //getters y setter
    public int getIndiceTab() {
        return indiceTab;
    }

    public void setIndiceTab(int indiceTab) {
        this.indiceTab = indiceTab;
    }

    public TipoReservaBean getTipoReservaBean() {
        return tipoReservaBean;
    }

    public void setTipoReservaBean(TipoReservaBean tipoReservaBean) {
        this.tipoReservaBean = tipoReservaBean;
    }

    public TipoReserva getTipoReservaSelecionada() {
        return tipoReservaSelecionada;
    }

    public void setTipoReservaSelecionada(TipoReserva tipoReservaSelecionada) {
        this.tipoReservaSelecionada = tipoReservaSelecionada;
    }

    public List<TipoReserva> getTipoReservasDisponibles() {
        return tipoReservasDisponibles;
    }

    public void setTipoReservasDisponibles(List<TipoReserva> reservasDisponibles) {
        this.tipoReservasDisponibles = reservasDisponibles;
    }

    public ReservaBean getReservaBean() {
        return reservaBean;
    }

    public void setReservaBean(ReservaBean reservaBean) {
        this.reservaBean = reservaBean;
    }

    public Date getFechaActual() {
        // Crea una nueva instancia de Date y setea solo la fecha (sin hora)
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime(); // Devuelve solo la fecha sin la hora
    }

    public Date getFechaReservaSelecionada() {
        return fechaReservaSelecionada;
    }

    public void setFechaReservaSelecionada(Date fechaReservaSelecionada) {
        this.fechaReservaSelecionada = fechaReservaSelecionada;
    }

    public int getIdTipoReservaSelecionada() {
        return idTipoReservaSelecionada;
    }

    public void setIdTipoReservaSelecionada(int idTipoReservaSelecionada) {
        this.idTipoReservaSelecionada = idTipoReservaSelecionada;
    }

    public Programacion getProgramacionSelecionada() {
        return programacionSelecionada;
    }

    public void setProgramacionSelecionada(Programacion programacionSelecionada) {
        this.programacionSelecionada = programacionSelecionada;
    }

    public String getIdProgramacion() {
        return idProgramacion;
    }

    public void setIdProgramacion(String idProgramacion) {
        this.idProgramacion = idProgramacion;
    }
}
