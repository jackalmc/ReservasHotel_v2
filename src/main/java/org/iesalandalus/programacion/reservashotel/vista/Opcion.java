package org.iesalandalus.programacion.reservashotel.vista;

public enum Opcion {

    SALIR("Salir"),
    INSERTAR_HUESPED("Insertar Huésped"),BUSCAR_HUESPED("Buscar Huésped"),BORRAR_HUESPED("Borrar Huésped"), MOSTRAR_HUESPEDES("Mostrar Huésped"),
    INSERTAR_HABITACION("Insertar Habitación"),BUSCAR_HABITACION("Buscar Habitación"), BORRAR_HABITACION("Borrar Habitación"), MOSTRAR_HABITACIONES("Mostrar Habitaciones"),
    INSERTAR_RESERVA("Insertar Reserva"),ANULAR_RESERVA("Anular Reserva"),MOSTRAR_RESERVAS("Mostrar Reserva"),
    CONSULTAR_DISPONIBILIDAD("Consultar Disponibilidad"),
    REALIZAR_CHECKIN("Realizar CheckIn"), REALIZAR_CHECKOUT("Realizar CheckOut"),
    DEBUG("[DEBUG] Insertar datos");

    private String mensajeAMostrar;

    Opcion(String mensajeAMostrar){
        this.mensajeAMostrar=mensajeAMostrar;
    }

    @Override
    public String toString() {
        return ordinal()+ " .- " + mensajeAMostrar;
    }
}
