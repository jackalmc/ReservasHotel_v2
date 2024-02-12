package org.iesalandalus.programacion.reservashotel.modelo.negocio;


import org.iesalandalus.programacion.reservashotel.modelo.dominio.Habitacion;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.TipoHabitacion;

import javax.naming.OperationNotSupportedException;

public class Habitaciones {

    private int capacidad;
    private int tamano;
    private Habitacion[] coleccionHabitaciones;

    public Habitaciones(int capacidad){
        if (capacidad < 1)
            throw new IllegalArgumentException("ERROR: La capacidad debe ser mayor que cero.");

        coleccionHabitaciones = new Habitacion[capacidad];
        this.capacidad = capacidad;
        this.tamano = 0;
    }

    public Habitacion[] get(){
        return copiaProfundaHabitaciones();
    }
    public Habitacion[] get(TipoHabitacion tipoHabitacion){
        if (coleccionHabitaciones==null)
            throw new NullPointerException("La colección no ha sido creada aún");

        Habitacion[] copiaEspecial = new Habitacion[this.capacidad];

        int posicion = 0;
        for (int i=0; i < tamano; i++){
            if (coleccionHabitaciones[i].getTipoHabitacion() == tipoHabitacion)
                copiaEspecial[posicion]=new Habitacion(coleccionHabitaciones[i]);
        }
        return copiaEspecial;
    }

    private Habitacion[] copiaProfundaHabitaciones(){
        if (coleccionHabitaciones==null)
            throw new NullPointerException("La colección no ha sido creada aún");

        Habitacion[] copia = new Habitacion[this.capacidad];

        for (int i=0; i < tamano; i++){
            copia[i]=new Habitacion(coleccionHabitaciones[i]);
        }
        return copia;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public int getTamano() {
        return tamano;
    }

    public void insertar(Habitacion habitacion)throws OperationNotSupportedException{
        if (habitacion == null)
            throw new NullPointerException("ERROR: No se puede insertar una habitación nula.");
        if (buscarIndice(habitacion)!= -1)
            throw new OperationNotSupportedException("ERROR: Ya existe una habitación con ese identificador.");
        if (capacidadSuperada(tamano)) //validado para negativo
            throw new OperationNotSupportedException("ERROR: No se aceptan más habitaciones.");

        coleccionHabitaciones[tamano] = new Habitacion(habitacion);
        tamano++;
    }

    private int buscarIndice(Habitacion habitacion) {
        if (habitacion == null)
            throw new NullPointerException("La habitación no puede ser nula (buscarIndice)");

        int indice = -1;

        for (int i = 0; i < tamano; i++) {
            if (coleccionHabitaciones[i].equals(habitacion))
                indice = i;
        }

        return indice;
    }

    private boolean tamanoSuperado(int indice){
        if (indice < 0)
            throw new IllegalArgumentException("El índice no puede ser negativo(tamaño)");

        return (indice>tamano);
    }

    private boolean capacidadSuperada(int indice){
        if (indice < 0)
            throw new IllegalArgumentException("El índice no puede ser negativo(capacidad)");

        return (indice>=capacidad);
    }

    public Habitacion buscar(Habitacion habitacion){
        if (habitacion == null)
            throw new NullPointerException("No se puede buscar una habitación nula");

        if (buscarIndice(habitacion) != -1)
            return coleccionHabitaciones[buscarIndice(habitacion)];
        else
            return null;

    }

    public void borrar(Habitacion habitacion)throws OperationNotSupportedException{
        if (habitacion == null)
            throw new NullPointerException("ERROR: No se puede borrar una habitación nula.");
        if (buscarIndice(habitacion) == -1)
            throw new OperationNotSupportedException("ERROR: No existe ninguna habitación como la indicada.");

        int posicionBorrado = buscarIndice(habitacion);

        coleccionHabitaciones[posicionBorrado] = null;
        if (posicionBorrado != tamano-1){
            for (int i = posicionBorrado; i< tamano-1; i++)
                desplazarUnaPosicionHaciaIzquierda(i);
        }
        tamano--;


    }

    private void desplazarUnaPosicionHaciaIzquierda(int indice){
        if (tamanoSuperado(indice)) //validado para negativo
            throw new IllegalArgumentException("El indice supera el tamaño");

        coleccionHabitaciones[indice] = new Habitacion(coleccionHabitaciones[indice+1]);
        coleccionHabitaciones[indice+1] = null;

    }

}
