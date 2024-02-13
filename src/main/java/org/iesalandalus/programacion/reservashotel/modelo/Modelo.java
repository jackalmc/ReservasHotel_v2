package org.iesalandalus.programacion.reservashotel.modelo;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.Habitacion;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Huesped;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.TipoHabitacion;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.Habitaciones;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.Huespedes;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.Reservas;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDateTime;
import java.util.List;

public class Modelo {

    private static Habitaciones habitaciones;
    private static Reservas reservas;
    private static Huespedes huespedes;


    public Modelo(){
        habitaciones = new Habitaciones();
        reservas = new Reservas();
        huespedes = new Huespedes();
    }

    public void comenzar(){

    }

    public void terminar(){
        System.out.println("*** Modelo ha pasado a mejor vida! ***");
    }

    public void insertar(Huesped huesped) throws OperationNotSupportedException {
        huespedes.insertar(huesped);
    }

    public Huesped buscar(Huesped huesped){
        return huespedes.buscar(huesped);
    }

    public void borrar(Huesped huesped) throws OperationNotSupportedException {
        huespedes.borrar(huesped);
    }

    public List<Huesped> getHuespedes(){
        //get() llama a copiaprofunda y ya devuelve nuevas instancias
        return huespedes.get();
    }

    public void insertar(Habitacion habitacion) throws OperationNotSupportedException {
        habitaciones.insertar(habitacion);
    }

    public Habitacion buscar(Habitacion habitacion){
        return habitaciones.buscar(habitacion);
    }

    public void borrar(Habitacion habitacion) throws OperationNotSupportedException {
        habitaciones.borrar(habitacion);
    }

    public List<Habitacion> getHabitaciones(){
        return habitaciones.get();
    }

    public List<Habitacion> getHabitaciones(TipoHabitacion tipoHabitacion){
        return habitaciones.get(tipoHabitacion);
    }

    public void insertar(Reserva reserva) throws OperationNotSupportedException {
        reservas.insertar(reserva);
    }

    public Reserva buscar(Reserva reserva){
        return reservas.buscar(reserva);
    }

    public void borrar(Reserva reserva) throws OperationNotSupportedException {
        reservas.borrar(reserva);
    }

    public List<Reserva> getReservas(){
        return reservas.get();
    }

    public List<Reserva> getReservas(Huesped huesped){
        return reservas.getReservas(huesped);
    }

    public List<Reserva> getReservas(TipoHabitacion tipoHabitacion){
        return reservas.getReservas(tipoHabitacion);
    }

    public List<Reserva> getReservas(Habitacion habitacion){
        return reservas.getReservasFuturas(habitacion);
    }

    public void realizarCheckin(Reserva reserva, LocalDateTime fecha){
        reservas.realizarCheckin(reserva,fecha);
    }

    public void realizarCheckout(Reserva reserva, LocalDateTime fecha){
        reservas.realizarCheckout(reserva,fecha);
    }
}
