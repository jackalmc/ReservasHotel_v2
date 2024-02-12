package org.iesalandalus.programacion.reservashotel.modelo.negocio;


import org.iesalandalus.programacion.reservashotel.modelo.dominio.Habitacion;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.TipoHabitacion;

import javax.naming.OperationNotSupportedException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class Habitaciones {

    private List<Habitacion> coleccionHabitaciones;

    public Habitaciones(){

        coleccionHabitaciones = new ArrayList<>();

    }

    public List<Habitacion> get(){
        return copiaProfundaHabitaciones();
    }
    public List<Habitacion> get(TipoHabitacion tipoHabitacion){
        if (coleccionHabitaciones==null)
            throw new NullPointerException("La colecci�n no ha sido creada a�n");

        List<Habitacion> copiaEspecial = new ArrayList<>();

        for (Habitacion habitacion : coleccionHabitaciones){
            if (habitacion.getTipoHabitacion() == tipoHabitacion)
                copiaEspecial.add(new Habitacion(habitacion));
        }
        return copiaEspecial;
    }

    private List<Habitacion> copiaProfundaHabitaciones(){
        if (coleccionHabitaciones==null)
            throw new NullPointerException("La colecci�n no ha sido creada a�n");

        List<Habitacion> copia = new ArrayList<>();

        for (Habitacion habitacion : coleccionHabitaciones)
            copia.add(new Habitacion(habitacion));
        return copia;
    }

    public int getTamano() {
        return coleccionHabitaciones.size();
    }

    public void insertar(Habitacion habitacion)throws OperationNotSupportedException{
        if (habitacion == null)
            throw new NullPointerException("ERROR: No se puede insertar una habitaci�n nula.");
        if (coleccionHabitaciones.contains(habitacion))
            throw new OperationNotSupportedException("ERROR: Ya existe una habitaci�n con ese identificador.");

        coleccionHabitaciones.add(new Habitacion(habitacion));

    }


    public Habitacion buscar(Habitacion habitacion){
        if (habitacion == null)
            throw new NullPointerException("ERROR: No se puede buscar una habitaci�n nula.");

        if (coleccionHabitaciones.contains(habitacion))
            return coleccionHabitaciones.get(coleccionHabitaciones.indexOf(habitacion));
        else
            return null;

    }

    public void borrar(Habitacion habitacion)throws OperationNotSupportedException{
        if (habitacion == null)
            throw new NullPointerException("ERROR: No se puede borrar una habitaci�n nula.");
        if (!coleccionHabitaciones.contains(habitacion))
            throw new OperationNotSupportedException("ERROR: No existe ninguna habitaci�n como la indicada.");


        coleccionHabitaciones.remove(habitacion);

    }

}
