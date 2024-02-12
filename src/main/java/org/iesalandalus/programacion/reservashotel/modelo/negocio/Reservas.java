package org.iesalandalus.programacion.reservashotel.modelo.negocio;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.Habitacion;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Huesped;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.TipoHabitacion;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
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
            throw new NullPointerException("La colecci�n no ha sido creada a�n");

        List<Reserva> copia = new ArrayList<>();

        for (Reserva reserva : coleccionReservas)
            copia.add(new Reserva(reserva));
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
            throw new NullPointerException("La colecci�n no ha sido creada a�n");
        if (huesped == null)
            throw new NullPointerException("ERROR: No se pueden buscar reservas de un huesped nulo.");

        List<Reserva> copiaEspecial = new ArrayList<>();

        for (Reserva reserva : coleccionReservas){
            if (reserva.getHuesped().equals(huesped)) {
                copiaEspecial.add(new Reserva(reserva));
            }
        }
        return copiaEspecial;

    }
    public List<Reserva> getReservas(TipoHabitacion tipoHabitacion){
        if (coleccionReservas==null)
            throw new NullPointerException("La colecci�n no ha sido creada a�n");
        if (tipoHabitacion == null)
            throw new NullPointerException("ERROR: No se pueden buscar reservas de un tipo de habitaci�n nula.");

        List<Reserva> copiaEspecial = new ArrayList<>();

        for (Reserva reserva : coleccionReservas){
            if (reserva.getHabitacion().getTipoHabitacion().equals(tipoHabitacion)) {
                copiaEspecial.add(new Reserva(reserva));
            }
        }
        return copiaEspecial;
    }
    public List<Reserva> getReservasFuturas(Habitacion habitacion){
        if (coleccionReservas==null)
            throw new NullPointerException("La colecci�n no ha sido creada a�n");
        if (habitacion==null)
            throw new NullPointerException("ERROR: No se pueden buscar reservas de una habitaci�n nula.");

        List<Reserva> copiaEspecial = new ArrayList<>();

        for (Reserva reserva : coleccionReservas){
            if (reserva.getFechaInicioReserva().isAfter(LocalDate.now()) && reserva.getHabitacion().equals(habitacion)) {
                copiaEspecial.add(new Reserva(reserva));
            }
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
            throw new IllegalArgumentException("No puede hacerse un CheckIn despu�s de la fecha fin de reserva");


        reserva.setCheckIn(fecha);
    }

    public void realizarCheckout(Reserva reserva, LocalDateTime fecha){
        if (reserva == null)
            throw new NullPointerException("La reserva es nula (CheckOut)");
        if (fecha == null)
            throw new NullPointerException("La fecha es nula (CheckOut)");
        if (fecha.isBefore(reserva.getCheckIn()))
            throw new IllegalArgumentException("No se puede hacer un CheckOut antes de la fecha del CheckIn");
        if (fecha.isAfter(reserva.getFechaFinReserva().atStartOfDay().plusHours(Reserva.MAX_HORAS_POSTERIOR_CHECKOUT)))
            throw new IllegalArgumentException("El Checkout no puede hacerse despu�s del periodo m�ximo permitido");

        reserva.setCheckOut(fecha);
    }
}
