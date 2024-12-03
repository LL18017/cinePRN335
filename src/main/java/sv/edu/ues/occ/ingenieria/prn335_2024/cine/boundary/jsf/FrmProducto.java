package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ActionEvent;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.event.SelectEvent;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf.rest.WS.ProductoEndPoint;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf.rest.WS.WS;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersist;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.ProductoBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Producto;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoProducto;

import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class FrmProducto extends AbstractFrm<Producto> implements Serializable {
    @Inject
    ProductoBean productoBean;
    @Inject
    FacesContext facesContext;
    @Inject
    ProductoEndPoint productoEndPoint;

    @Inject
    FrmTipoProducto frmTipoProducto;


    Integer idTipoProducto;
    List<TipoProducto> tipoProductoList;


    @Override
    public void instanciarRegistro() {
        registro=new Producto();
        registro.setActivo(true);
    }

    @Override
    public FacesContext getFC() {
        return facesContext;
    }

    @Override
    public AbstractDataPersist<Producto> getAbstractDataPersist() {
        return productoBean;
    }

    @Override
    public void inicioRegistros() {
        tipoProductoList=frmTipoProducto.getTipoProductoList();
        super.inicioRegistros();
    }

    @Override
    public String getIdByObject(Producto object) {
        if (object!=null){
            return object.getIdProducto().toString();
        }
        return "";
    }

    @Override
    public Producto getObjectById(String id) {
        if (id!=null && modelo.getWrappedData()!=null & modelo!=null){
            return modelo.getWrappedData().stream().filter(p->p.getIdProducto().toString().equals(id)).findFirst()
                    .orElse(null);
        }
        return null;
    }

    @Override
    public void selecionarFila(SelectEvent<Producto> event) {
        enviarMensaje(FacesMessage.SEVERITY_INFO,"Se ha selecionado: "+registro.getNombre());
        idTipoProducto=registro.getIdTipoProducto().getIdTipoProducto();
        estado=ESTADO_CRUD.MODIFICAR;
    }

    @Override
    public String paginaNombre() {
        return "Productos";
    }

    @Override
    public WS getWebsocketController() {
        return productoEndPoint;
    }

    public FrmTipoProducto getFrmTipoProducto() {
        return frmTipoProducto;
    }

    public void setFrmTipoProducto(FrmTipoProducto frmTipoProducto) {
        this.frmTipoProducto = frmTipoProducto;
    }

    public Integer getIdTipoProducto() {
        return idTipoProducto;
    }

    public void setIdTipoProducto(Integer idTipoProducto) {
        registro.setIdTipoProducto(tipoProductoList.stream().filter(tp->tp.getIdTipoProducto().equals(idTipoProducto)).findFirst().orElse(null));
        this.idTipoProducto = idTipoProducto;
    }

    public List<TipoProducto> getTipoProductoList() {
        return tipoProductoList;
    }

    public void setTipoProductoList(List<TipoProducto> tipoProductoList) {
        this.tipoProductoList = tipoProductoList;
    }

    @Override
    public void btnModificarHandler(ActionEvent ex) {
        idTipoProducto=tipoProductoList.getFirst().getIdTipoProducto();
        super.btnModificarHandler(ex);
    }

    @Override
    public void btnGuardarHandler(ActionEvent e) {
        idTipoProducto=tipoProductoList.getFirst().getIdTipoProducto();
        super.btnGuardarHandler(e);
    }

    @Override
    public void btneEliminarHandler(ActionEvent ex) {
        idTipoProducto=tipoProductoList.getFirst().getIdTipoProducto();
        super.btneEliminarHandler(ex);
    }
}
