package com.example.cocheshibernate1an.Model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "multa")
public class Multa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_multa")
    private int id_multa;

    @Column(name ="precio")
    private double precio;

    @Column(name = "fecha")
    private LocalDate fecha;


    @ManyToOne
    @JoinColumn(name = "matricula", referencedColumnName = "matricula")
    private Coche coche;

    public Multa() {
    }

    public Multa(double precio, LocalDate fecha, Coche coche) {
        this.precio = precio;
        this.fecha = fecha;
        this.coche = coche;
    }

    public Coche getCoche() {
        return coche;
    }

    public void setCoche(Coche coche) {
        this.coche = coche;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getId_multa() {
        return id_multa;
    }

    public void setId_multa(int id_multa) {
        this.id_multa = id_multa;
    }
}
