package org.iesalandalus.programacion.reservashotel.modelo.negocio;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.Huesped;

import javax.naming.OperationNotSupportedException;

public class Huespedes {

    private int capacidad;
    private int tamano;
    private Huesped[] coleccionHuespedes;

    public Huespedes(int capacidad){
        if (capacidad < 1)
            throw new IllegalArgumentException("ERROR: La capacidad debe ser mayor que cero.");

        coleccionHuespedes = new Huesped[capacidad];
        this.capacidad = capacidad;
        this.tamano = 0;
    }

    public Huesped[] get(){
        return copiaProfundaHuespedes();
    }

    private Huesped[] copiaProfundaHuespedes(){
        if (coleccionHuespedes==null)
            throw new NullPointerException("La colección no ha sido creada aún");

        Huesped[] copia = new Huesped[this.capacidad];
        for (int i=0; i < tamano; i++){
            copia[i]=new Huesped(coleccionHuespedes[i]);
        }
        return copia;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public int getTamano() {
        return tamano;
    }

    public void insertar(Huesped huesped) throws OperationNotSupportedException{
        if (huesped == null)
            throw new NullPointerException("ERROR: No se puede insertar un huésped nulo.");
        if (buscarIndice(huesped)!= -1)
            throw new OperationNotSupportedException("ERROR: Ya existe un huésped con ese dni.");
        if (capacidadSuperada(tamano)) //validado para negativo
            throw new OperationNotSupportedException("ERROR: No se aceptan más huéspedes.");

        coleccionHuespedes[tamano] = new Huesped(huesped);
        tamano++;
    }

    private int buscarIndice(Huesped huesped) {
        if (huesped == null)
            throw new NullPointerException("No se puede buscar un huésped nulo");

        int indice = -1;

        for (int i = 0; i < tamano; i++) {
            if (coleccionHuespedes[i].equals(huesped))
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

    public Huesped buscar(Huesped huesped){
        if (huesped == null)
            throw new NullPointerException("No se puede buscar un huésped nulo");

        if (buscarIndice(huesped) != -1)
            return coleccionHuespedes[buscarIndice(huesped)];
        else
            return null;

    }

    public void borrar(Huesped huesped)throws OperationNotSupportedException{
        if (huesped == null)
            throw new NullPointerException("ERROR: No se puede borrar un huésped nulo.");
        if (buscarIndice(huesped) == -1)
            throw new OperationNotSupportedException("ERROR: No existe ningún huésped como el indicado.");

        int posicionBorrado = buscarIndice(huesped);

        coleccionHuespedes[posicionBorrado] = null;
        if (posicionBorrado != tamano-1){
            for (int i = posicionBorrado; i< tamano-1; i++)
                desplazarUnaPosicionHaciaIzquierda(i);
        }
        tamano--;


    }

    private void desplazarUnaPosicionHaciaIzquierda(int indice){
        if (tamanoSuperado(indice)) //validado para negativo
            throw new IllegalArgumentException("El indice supera el tamaño");

        coleccionHuespedes[indice] = new Huesped(coleccionHuespedes[indice+1]);
        coleccionHuespedes[indice+1] = null;

    }

}
