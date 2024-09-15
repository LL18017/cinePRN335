/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author mj99lopez
 */
@Entity
@Table(name = "factura_detalle_sala")
@NamedQueries({
    @NamedQuery(name = "FacturaDetalleSala.findAll", query = "SELECT f FROM FacturaDetalleSala f"),
    @NamedQuery(name = "FacturaDetalleSala.findByIdFacturaDetalleSala", query = "SELECT f FROM FacturaDetalleSala f WHERE f.idFacturaDetalleSala = :idFacturaDetalleSala"),
    @NamedQuery(name = "FacturaDetalleSala.findByMonto", query = "SELECT f FROM FacturaDetalleSala f WHERE f.monto = :monto")})
public class FacturaDetalleSala implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_factura_detalle_sala")
    private Long idFacturaDetalleSala;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "monto")
    private BigDecimal monto;
    @JoinColumn(name = "id_factura", referencedColumnName = "id_factura")
    @ManyToOne(fetch = FetchType.LAZY)
    private Factura idFactura;
    @JoinColumn(name = "id_reserva_detalle", referencedColumnName = "id_reserva_detalle")
    @ManyToOne(fetch = FetchType.LAZY)
    private ReservaDetalle idReservaDetalle;

    public FacturaDetalleSala() {
    }

    public FacturaDetalleSala(Long idFacturaDetalleSala) {
        this.idFacturaDetalleSala = idFacturaDetalleSala;
    }

    public Long getIdFacturaDetalleSala() {
        return idFacturaDetalleSala;
    }

    public void setIdFacturaDetalleSala(Long idFacturaDetalleSala) {
        this.idFacturaDetalleSala = idFacturaDetalleSala;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public Factura getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(Factura idFactura) {
        this.idFactura = idFactura;
    }

    public ReservaDetalle getIdReservaDetalle() {
        return idReservaDetalle;
    }

    public void setIdReservaDetalle(ReservaDetalle idReservaDetalle) {
        this.idReservaDetalle = idReservaDetalle;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idFacturaDetalleSala != null ? idFacturaDetalleSala.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FacturaDetalleSala)) {
            return false;
        }
        FacturaDetalleSala other = (FacturaDetalleSala) object;
        if ((this.idFacturaDetalleSala == null && other.idFacturaDetalleSala != null) || (this.idFacturaDetalleSala != null && !this.idFacturaDetalleSala.equals(other.idFacturaDetalleSala))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.FacturaDetalleSala[ idFacturaDetalleSala=" + idFacturaDetalleSala + " ]";
    }
    
}
