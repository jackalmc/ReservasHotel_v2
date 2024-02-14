package org.iesalandalus.programacion.reservashotel.modelo.dominio;

import java.util.Objects;

public class Habitacion {
    public final static double MIN_PRECIO_HABITACION=40.0;
    public final static double MAX_PRECIO_HABITACION=150.0;
    public final static int MIN_NUMERO_PUERTA=0;
    public final static int MAX_NUMERO_PUERTA=14;
    public final static int MIN_NUMERO_PLANTA=1;
    public final static int MAX_NUMERO_PLANTA=3;
    private String identificador;
    private int planta;
    private int puerta;
    private double precio;
    private TipoHabitacion tipoHabitacion;

    public String getIdentificador() {
        return identificador;
    }

    private void setIdentificador(){
        identificador= "" +planta +puerta;
    }
    private void setIdentificador(String identificador) {
        if (identificador == null)
            throw new NullPointerException("Identificador nulo");
        if (identificador.isBlank())
            throw new IllegalArgumentException("Identificador en blanco");

        this.identificador = identificador;
    }

    public int getPlanta() {
        return planta;
    }

    private void setPlanta(int planta) {
        if (planta < MIN_NUMERO_PLANTA || planta > MAX_NUMERO_PLANTA)
            throw new IllegalArgumentException("ERROR: No se puede establecer como planta de una habitación un valor menor que 1 ni mayor que 3.");

        this.planta = planta;
    }

    public int getPuerta() {
        return puerta;
    }

    private void setPuerta(int puerta) {
        if (puerta < MIN_NUMERO_PUERTA || puerta > MAX_NUMERO_PUERTA)
            throw new IllegalArgumentException("ERROR: No se puede establecer como puerta de una habitación un valor menor que 0 ni mayor que 14.");

        this.puerta = puerta;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        if (precio < MIN_PRECIO_HABITACION || precio > MAX_PRECIO_HABITACION)
            throw new IllegalArgumentException("ERROR: No se puede establecer como precio de una habitación un valor menor que 40.0 ni mayor que 150.0.");

        this.precio = precio;
    }

    public TipoHabitacion getTipoHabitacion() {
        return tipoHabitacion;
    }

    public void setTipoHabitacion(TipoHabitacion tipoHabitacion) {
        if (tipoHabitacion == null)
            throw new NullPointerException("El tipo no puede ser nulo");

        this.tipoHabitacion = tipoHabitacion;
    }

    public Habitacion(int planta, int puerta, double precio){
        setPlanta(planta);
        setPuerta(puerta);
        setPrecio(precio);
        setIdentificador();
        setTipoHabitacion(TipoHabitacion.SIMPLE); //¿¿por defecto??


    }

    public Habitacion(int planta, int puerta, double precio, TipoHabitacion tipoHabitacion){
        if (tipoHabitacion == null)
                throw new NullPointerException("ERROR: No se puede establecer un tipo de habitación nula.");
        setPlanta(planta);
        setPuerta(puerta);
        setPrecio(precio);
        setIdentificador();
        setTipoHabitacion(tipoHabitacion);

    }

    public Habitacion(Habitacion habitacion){
        if (habitacion==null)
            throw new NullPointerException("ERROR: No es posible copiar una habitación nula.");
        setPlanta(habitacion.getPlanta());
        setPuerta(habitacion.getPuerta());
        setPrecio(habitacion.getPrecio());
        setTipoHabitacion(habitacion.getTipoHabitacion());
        setIdentificador(habitacion.getIdentificador());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Habitacion that = (Habitacion) o;
        return Objects.equals(identificador, that.identificador);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identificador);
    }

    @Override
    public String toString() {
        return "identificador="+identificador+" ("+planta+"-"+puerta+"), precio habitación="+precio+", tipo habitación="+tipoHabitacion;
    }
}
