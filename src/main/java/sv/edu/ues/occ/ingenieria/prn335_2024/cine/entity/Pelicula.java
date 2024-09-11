package sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "pelicula")
public class Pelicula {
    @Id
    @Column(name = "id_pelicula", nullable = false)
    private Integer id;

    @Size(max = 255)
    @Column(name = "nombre")
    private String nombre;


    @Column(name = "sinopsis")
    private String sinopsis;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

}