package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;


import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class FrmMenu implements Serializable {

    @Inject
    FacesContext facesContext;

    private DefaultMenuModel model;
    private List<String> tiposList = new ArrayList<>();
    private List<String> cineList = new ArrayList<>();

    @PostConstruct
    public void init() {
        model = new DefaultMenuModel();

        // Inicializar listas
        inicializarListas();

        // Crear submenús dinámicos
        agregarSubMenu("Tipos","Tipo", tiposList);
        agregarSubMenu("Cine","", cineList);

        // Registro para depuración
        System.out.println("Modelo de menú inicializado: " + model.getElements());
    }

    private void inicializarListas() {
        cineList.add("Pelicula");
        cineList.add("Sucursal");
        cineList.add("Sala");
        cineList.add("Reserva");
        cineList.add("Producto");

        tiposList.add("Pago");
        tiposList.add("Asiento");
        tiposList.add("Producto");
        tiposList.add("Reserva");
        tiposList.add("Sala");
    }

    private void agregarSubMenu(String titulo,String sufijo, List<String> lista) {
        DefaultSubMenu subMenu = DefaultSubMenu.builder()
                .label(titulo)
                .expanded(true)
                .build();

        lista.forEach(item -> {
            String formulario =sufijo+ item + ".jsf"; // Construir la ruta del formulario
            DefaultMenuItem menuItem = DefaultMenuItem.builder()
                    .value(item) // Nombre visible en el menú
                    .ajax(true)
                    .command("#{frmMenu.navegar('" + formulario + "')}")
                    .build();
            subMenu.getElements().add(menuItem);
        });

        model.getElements().add(subMenu);
    }

    public void navegar(String formulario) {
        try {
            facesContext.getExternalContext().redirect(formulario);
        } catch (IOException e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo navegar.");
            facesContext.addMessage(null, message);
            e.printStackTrace();
        }
    }

    public DefaultMenuModel getModel() {
        return model;
    }
}
