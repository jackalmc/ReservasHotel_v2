package org.iesalandalus.programacion.reservashotel.modelo.negocio;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.Habitacion;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Huesped;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.TipoHabitacion;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Reservas {

    private List<Reserva> coleccionReservas;

    public Reservas(){

        coleccionReservas = new ArrayList<>();

    }

    public List<Reserva> get(){
        return copiaProfundaReservas();
    }

    private List<Reserva> copiaProfundaReservas(){
        if (coleccionReservas==null)
            throw new NullPointerException("La colección no ha sido creada aún");

        List<Reserva> copia = new ArrayList<>();

        Iterator<Reserva> reservaIterator = coleccionReservas.iterator();
        while (reservaIterator.hasNext())
            copia.add(new Reserva(reservaIterator.next()));

        return copia;
    }


    public int getTamano() {
        return coleccionReservas.size();
    }

    public void insertar(Reserva reserva) throws OperationNotSupportedException{
        if (reserva == null)
            throw new NullPointerException("ERROR: No se puede insertar una reserva nula.");
        if (coleccionReservas.contains(reserva))
            throw new OperationNotSupportedException("ERROR: Ya existe una reserva igual.");

        coleccionReservas.add(new Reserva(reserva));

    }

    public Reserva buscar(Reserva reserva){
        if (reserva == null)
            throw new NullPointerException("ERROR: No se puede buscar una reserva nula.");

        if (coleccionReservas.contains(reserva))
            return coleccionReservas.get(coleccionReservas.indexOf(reserva));
        else
            return null;

    }

    public void borrar(Reserva reserva)throws OperationNotSupportedException{
        if (reserva == null)
            throw new NullPointerException("ERROR: No se puede borrar una reserva nula.");
        if (!coleccionReservas.contains(reserva))
            throw new OperationNotSupportedException("ERROR: No existe ninguna reserva como la indicada.");


        coleccionReservas.remove(reserva);
    }

    public List<Reserva> getReservas(Huesped huesped){
        if (coleccionReservas==null)
            throw new NullPointerException("La colección no ha sido creada aún");
        if (huesped == null)
            throw new NullPointerException("ERROR: No se pueden buscar reservas de un huesped nulo.");

        List<Reserva> copiaEspecial = new ArrayList<>();

        Iterator<Reserva> reservaIterator = coleccionReservas.iterator();
        Reserva token;
        while (reservaIterator.hasNext()){
            token = reservaIterator.next();
            if (token.getHuesped().equals(huesped))
                copiaEspecial.add(new Reserva(token));
        }

        return copiaEspecial;

    }
    public List<Reserva> getReservas(TipoHabitacion tipoHabitacion){
        if (coleccionReservas==null)
            throw new NullPointerException("La colección no ha sido creada aún");
        if (tipoHabitacion == null)
            throw new NullPointerException("ERROR: No se pueden buscar reservas de un tipo de habitación nula.");

        List<Reserva> copiaEspecial = new ArrayList<>();

        Iterator<Reserva> reservaIterator = coleccionReservas.iterator();
        Reserva token;
        while (reservaIterator.hasNext()){
            token = reservaIterator.next();
            if (token.getHabitacion().getTipoHabitacion().equals(tipoHabitacion))
                copiaEspecial.add(new Reserva(token));
        }

        return copiaEspecial;
    }
    public List<Reserva> getReservasFuturas(Habitacion habitacion){
        if (coleccionReservas==null)
            throw new NullPointerException("La colección no ha sido creada aún");
        if (habitacion==null)
            throw new NullPointerException("ERROR: No se pueden buscar reservas de una habitación nula.");

        List<Reserva> copiaEspecial = new ArrayList<>();

        Iterator<Reserva> reservaIterator = coleccionReservas.iterator();
        Reserva token;
        while (reservaIterator.hasNext()){
            token = reservaIterator.next();
            if (token.getFechaInicioReserva().isAfter(LocalDate.now()) && token.getHabitacion().equals(habitacion))
                copiaEspecial.add(new Reserva(token));
        }

        return copiaEspecial;
    }

    public void realizarCheckin(Reserva reserva, LocalDateTime fecha){
        if (reserva == null)
            throw new NullPointerException("La reserva es nula (Checkin)");
        if (fecha == null)
            throw new NullPointerException("La fecha es nula (Checkin)");
        if (fecha.isBefore(reserva.getFechaInicioReserva().atStartOfDay()))
            throw new IllegalArgumentException("El CheckIn no puede realizarse antes de la fecha de inicio de la reserva");
        if (fecha.isAfter(reserva.getFechaFinReserva().atStartOfDay()))
            throw new IllegalArgumentException("No puede hacerse un CheckIn después de la fecha fin de reserva");


        reserva.setCheckIn(fecha);
    }

    public void realizarCheckout(Reserva reserva, LocalDateTime fecha){
        if (reserva == null)
            throw new NullPointerException("La reserva es nula (CheckOut)");
        if (fecha == null)
            throw new NullPointerException("La fecha es nula (CheckOut)");
        if (reserva.getCheckIn() == null)
            throw new NullPointerException("No se puede realizar CheckOut antes de hacer CheckIn");
        if (fecha.isBefore(reserva.getCheckIn()))
            throw new IllegalArgumentException("No se puede hacer un CheckOut antes de la fecha del CheckIn");
        if (fecha.isAfter(reserva.getFechaFinReserva().atStartOfDay().plusHours(Reserva.MAX_HORAS_POSTERIOR_CHECKOUT)))
            throw new IllegalArgumentException("El Checkout no puede hacerse después del periodo máximo permitido");

        reserva.setCheckOut(fecha);
    }
}
