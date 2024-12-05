/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity;

import jakarta.json.bind.annotation.JsonbTransient;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author mj99lopez
 */
@Entity
@Table(name = "sala")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Sala.findAll", query = "SELECT s FROM Sala s"),
        @NamedQuery(name = "Sala.findSalBySucursal", query = "SELECT s FROM Sala s WHERE s.idSucursal = :sucursal"),
        @NamedQuery(name = "Sala.findByIdSala", query = "SELECT s FROM Sala s WHERE s.idSala = :idSala"),
        @NamedQuery(name = "Sala.findByNombre", query = "SELECT s FROM Sala s WHERE s.nombre = :nombre"),
        @NamedQuery(name = "Sala.findByActivo", query = "SELECT s FROM Sala s WHERE s.activo = :activo"),
        @NamedQuery(name = "Sala.findallProgramacionesByIdSala", query = "SELECT p FROM Programacion p  Where p.idSala.idSala= :SalaSelecionada"),
        @NamedQuery(name = "Sala.findByObservaciones", query = "SELECT s FROM Sala s WHERE s.observaciones = :observaciones")})
public class Sala implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_sala")
    private Integer idSala;
    @Size(max = 155)
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "activo")
    private Boolean activo;
    @Size(max = 2147483647)
    @Column(name = "observaciones")
    private String observaciones;
    @OneToMany(mappedBy = "idSala", fetch = FetchType.LAZY)
    @JsonbTransient
    private List<Asiento> asientoList;
    @OneToMany(mappedBy = "idSala", fetch = FetchType.LAZY)
    @JsonbTransient
    private List<SalaCaracteristica> salaCaracteristicaList;
    @JoinColumn(name = "id_sucursal", referencedColumnName = "id_sucursal")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonbTransient
    private Sucursal idSucursal;
    @OneToMany(mappedBy = "idSala", fetch = FetchType.LAZY)
    @JsonbTransient
    private List<Programacion> programacionList;

    public Sala() {
    }

    public Sala(Integer idSala) {
        this.idSala = idSala;
    }

    public Integer getIdSala() {
        return idSala;
    }

    public void setIdSala(Integer idSala) {
        this.idSala = idSala;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    @XmlTransient
    public List<Asiento> getAsientoList() {
        return asientoList;
    }

    public void setAsientoList(List<Asiento> asientoList) {
        this.asientoList = asientoList;
    }

    @XmlTransient
    public List<SalaCaracteristica> getSalaCaracteristicaList() {
        return salaCaracteristicaList;
    }

    public void setSalaCaracteristicaList(List<SalaCaracteristica> salaCaracteristicaList) {
        this.salaCaracteristicaList = salaCaracteristicaList;
    }

    public Sucursal getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(Sucursal idSucursal) {
        this.idSucursal = idSucursal;
    }

    @XmlTransient
    public List<Programacion> getProgramacionList() {
        return programacionList;
    }

    public void setProgramacionList(List<Programacion> programacionList) {
        this.programacionList = programacionList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSala != null ? idSala.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sala)) {
            return false;
        }
        Sala other = (Sala) object;
        if ((this.idSala == null && other.idSala != null) || (this.idSala != null && !this.idSala.equals(other.idSala))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Sala[ idSala=" + idSala + " ]";
    }
    
}
