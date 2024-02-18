package org.iesalandalus.programacion.reservashotel.modelo.negocio;


import org.iesalandalus.programacion.reservashotel.modelo.dominio.Habitacion;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.TipoHabitacion;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.Iterator;
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
            throw new NullPointerException("La colección no ha sido creada aún");

        List<Habitacion> copiaEspecial = new ArrayList<>();

        Iterator<Habitacion> habitacionIterator = coleccionHabitaciones.iterator();
        Habitacion token;
        while (habitacionIterator.hasNext()){
            token = habitacionIterator.next();
            if (token.getTipoHabitacion() == tipoHabitacion)
                copiaEspecial.add(new Habitacion(token));
        }

        return copiaEspecial;
    }

    private List<Habitacion> copiaProfundaHabitaciones(){
        if (coleccionHabitaciones==null)
            throw new NullPointerException("La colección no ha sido creada aún");

        List<Habitacion> copia = new ArrayList<>();

        Iterator<Habitacion> habitacionIterator = coleccionHabitaciones.iterator();
        while (habitacionIterator.hasNext())
            copia.add(new Habitacion(habitacionIterator.next()));

        return copia;
    }

    public int getTamano() {
        return coleccionHabitaciones.size();
    }

    public void insertar(Habitacion habitacion)throws OperationNotSupportedException{
        if (habitacion == null)
            throw new NullPointerException("ERROR: No se puede insertar una habitación nula.");
        if (coleccionHabitaciones.contains(habitacion))
            throw new OperationNotSupportedException("ERROR: Ya existe una habitación con ese identificador.");

        coleccionHabitaciones.add(new Habitacion(habitacion));

    }


    public Habitacion buscar(Habitacion habitacion){
        if (habitacion == null)
            throw new NullPointerException("ERROR: No se puede buscar una habitación nula.");

        if (coleccionHabitaciones.contains(habitacion))
            return coleccionHabitaciones.get(coleccionHabitaciones.indexOf(habitacion));
        else
            return null;

    }

    public void borrar(Habitacion habitacion)throws OperationNotSupportedException{
        if (habitacion == null)
            throw new NullPointerException("ERROR: No se puede borrar una habitación nula.");
        if (!coleccionHabitaciones.contains(habitacion))
            throw new OperationNotSupportedException("ERROR: No existe ninguna habitación como la indicada.");


        coleccionHabitaciones.remove(habitacion);

    }

}
