package com.herprogramacion.guadehotelesenroma;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa cada hotel del archivo XML
 */
public class Hotel {
    private int idHotel;
    private String nombre;
    private String precio;
    private float calificacion;
    private String urlImagen;
    private int noOpiniones;
    private String descripcion;

    // Proveedor estático de datos para el adaptador
    public static List<Hotel> HOTELES = new ArrayList<>();

    public static Hotel getItem(int id) {
        for (Hotel item : HOTELES) {
            if (item.getIdHotel() == id) {
                return item;
            }
        }
        return null;
    }

    public Hotel(int idHotel,
                 String nombre,
                 String precio,
                 float valoracion,
                 String urlImagen,
                 int noOpiniones,
                 String descripcion) {
        this.idHotel = idHotel;
        this.nombre = nombre;
        this.precio = precio;
        this.calificacion = valoracion;
        this.urlImagen = urlImagen;
        this.noOpiniones = noOpiniones;
        this.descripcion = descripcion;
    }

    public int getNoOpiniones() {
        return noOpiniones;
    }

    public String getNombre() {
        return nombre;
    }

    public int getIdHotel() {
        return idHotel;
    }

    public String getPrecio() {
        return precio;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public float getCalificacion() {
        return calificacion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
