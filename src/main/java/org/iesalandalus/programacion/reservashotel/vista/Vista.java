package org.iesalandalus.programacion.reservashotel.vista;

import org.iesalandalus.programacion.reservashotel.controlador.Controlador;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
import org.iesalandalus.programacion.utilidades.Entrada;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;

public class Vista {

    private Controlador controlador;

    private static boolean salir = false;

    public void setControlador(Controlador controlador){
        if (controlador==null)
            System.out.println("Controlador no puede ser nulo");

        this.controlador=controlador;
    }

    public void comenzar(){
        while(!salir) {
            try {
                Consola.mostrarMenu();
                ejecutarOpcion(Consola.elegirOpcion());

            }catch (IllegalArgumentException|NullPointerException|IllegalStateException e){
                System.out.println(e.getMessage());
            }
        }
    }

    public void terminar(){
        System.out.println("\n***\nHasta luego!!!!\n***");
    }

    private void ejecutarOpcion(Opcion opcion){

        if (opcion == null)
            throw new IllegalArgumentException("Opción nula");
        switch (opcion){
            case BUSCAR_HABITACION -> buscarHabitacion();
            case BORRAR_HUESPED -> borrarHuesped();
            case INSERTAR_HUESPED -> insertarHuesped();
            case ANULAR_RESERVA -> anularReserva();
            case BUSCAR_HUESPED -> buscarHuesped();
            case INSERTAR_RESERVA -> insertarReserva();
            case MOSTRAR_RESERVAS -> mostrarReservas();
            case BORRAR_HABITACION -> borrarHabitacion();
            case MOSTRAR_HUESPEDES -> mostrarHuespedes();
            case INSERTAR_HABITACION -> insertarHabitacion();
            case MOSTRAR_HABITACIONES -> mostrarHabitaciones();
            case CONSULTAR_DISPONIBILIDAD -> System.out.println(consultarDisponibilidad(Consola.leerTipoHabitacion(), Consola.leerFecha(Entrada.cadena()), Consola.leerFecha(Entrada.cadena())));
            case SALIR -> salir=true;
            case DEBUG -> debug();
            case REALIZAR_CHECKIN -> realizarCheckin();
            case REALIZAR_CHECKOUT -> realizarCheckout();
        }
    }

    private void insertarHuesped(){
        try{
            controlador.insertar(Consola.leerHuesped());
            System.out.println(" ");
            System.out.println("*****");
            System.out.println("Huésped insertado!!!");
            System.out.println("*****");
            System.out.println(" ");
        }catch (NullPointerException | IllegalArgumentException | OperationNotSupportedException e){
            System.out.println(e.getMessage());
        }


    }

    private void buscarHuesped(){
        System.out.println(controlador.buscar(Consola.getHuespedPorDni()));
    }

    private void borrarHuesped(){
        try{
            controlador.borrar(Consola.getHuespedPorDni());
            System.out.println(" ");
            System.out.println("*****");
            System.out.println("Huesped borrado!!!");
            System.out.println("*****");
            System.out.println(" ");

        }catch (NullPointerException|IllegalArgumentException|OperationNotSupportedException e){
            System.out.println(e.getMessage());
        }



    }

    private void mostrarHuespedes(){
        Huesped[] lista;
        lista = controlador.getHuespedes();
        System.out.println(" ");
        System.out.println("*****");
        for (int i=0; i< lista.length; i++) {

            if (lista[i] != null)
                System.out.println(lista[i]);
        }
        System.out.println("*****");
        System.out.println(" ");
    }

    private void insertarHabitacion(){
        try{
            controlador.insertar(Consola.leerHabitacion());
            System.out.println(" ");
            System.out.println("*****");
            System.out.println("Habitación insertada!!!");
            System.out.println("*****");
            System.out.println(" ");
        }catch (NullPointerException|IllegalArgumentException|OperationNotSupportedException e){
            System.out.println(e.getMessage());
        }
    }

    private void buscarHabitacion(){
        try{
            System.out.println(controlador.buscar(Consola.leerHabitacionPorIdentificador()));

        }catch (NullPointerException|IllegalArgumentException e){
            System.out.println(e.getMessage());

        }
    }

    private void borrarHabitacion(){
        try{
            controlador.borrar(Consola.leerHabitacionPorIdentificador());
            System.out.println(" ");
            System.out.println("*****");
            System.out.println("Habitación borrada!!!");
            System.out.println("*****");
            System.out.println(" ");
        }catch (NullPointerException|IllegalArgumentException|OperationNotSupportedException e){
            System.out.println(e.getMessage());
        }

    }

    private void mostrarHabitaciones(){
        Habitacion[] lista;
        lista = controlador.getHabitaciones();
        System.out.println(" ");
        System.out.println("*****");
        for (int i=0; i< lista.length; i++) {

            if (lista[i] != null)
                System.out.println(lista[i]);
        }
        System.out.println("*****");
        System.out.println(" ");

    }
    private void insertarReserva(){
        Reserva habitacionDeseada = Consola.leerReserva();
        if (consultarDisponibilidad(habitacionDeseada.getHabitacion().getTipoHabitacion(), habitacionDeseada.getFechaInicioReserva(), habitacionDeseada.getFechaFinReserva()) == null) {

            try {
                controlador.insertar(habitacionDeseada);
                System.out.println(" ");
                System.out.println("*****");
                System.out.println("Reserva insertada!!!");
                System.out.println("*****");
                System.out.println(" ");
            } catch (NullPointerException|IllegalArgumentException|OperationNotSupportedException e) {
                System.out.println(e.getMessage());
            }

        } else {
            System.out.println("No está disponible");
        }

    }

    private void listarReservas(Huesped huesped){
        Reserva [] lista;
        lista = controlador.getReservas(huesped);
        for (int i=0; i< lista.length; i++) {

            if (lista[i] != null)
                System.out.println(lista[i]);
        }
    }

    private void listarReservas(TipoHabitacion tipoHabitacion){
        Reserva [] lista;
        lista = controlador.getReservas(tipoHabitacion);
        for (int i=0; i< lista.length; i++) {

            if (lista[i] != null)
                System.out.println(lista[i]);
        }
    }


    private Reserva[] getReservasAnulables(Reserva[] reservasAAnular){

        int tamano = 0;
        for (Reserva reserva:reservasAAnular)
            if (reserva != null)
                tamano++;

        Reserva[] listaAnulables = new Reserva[tamano];

        int posicion=0;

        for (int i=0; i < listaAnulables.length; i++){
            if (reservasAAnular[i].getFechaInicioReserva().isAfter(LocalDate.now())) {

                listaAnulables[posicion] = new Reserva(reservasAAnular[i]);
                posicion++;
            }
        }

        return listaAnulables;
    }


    private void debug(){
        Huesped huesped1 = new Huesped("pepe felices", "22222222J", "aaaa@aaaa.com", "950262626", LocalDate.of(1950,1,1));
        Huesped huesped2 = new Huesped("carlos salfredo", "11111111H", "bbbb@bbbb.com", "650151515", LocalDate.of(1975,1,1));
        Habitacion habitacion1 = new Habitacion(1,1,100,TipoHabitacion.TRIPLE);
        Habitacion habitacion2 = new Habitacion(1,2,100,TipoHabitacion.TRIPLE);
        LocalDate inicio1 = LocalDate.of(2024,2,15);
        LocalDate fin1 = LocalDate.of(2024,2,20);
        LocalDate inicio2 = LocalDate.of(2024,4,15);
        LocalDate fin2 = LocalDate.of(2024,4,20);
        Reserva reserva1 = new Reserva(huesped1, habitacion1, Regimen.MEDIA_PENSION, inicio1, fin1, 2);
        Reserva reserva2 = new Reserva(huesped2, habitacion2, Regimen.MEDIA_PENSION, inicio1, fin1, 2);
        Reserva reserva3 = new Reserva(huesped1, habitacion2, Regimen.MEDIA_PENSION, inicio2, fin2, 2);
        Reserva reserva4 = new Reserva(huesped2, habitacion1, Regimen.MEDIA_PENSION, inicio2, fin2, 2);

        try {
            controlador.insertar(huesped1);
            controlador.insertar(huesped2);
            controlador.insertar(habitacion1);
            controlador.insertar(habitacion2);
            controlador.insertar(reserva1);
            controlador.insertar(reserva2);
            controlador.insertar(reserva3);
            controlador.insertar(reserva4);
        }catch(OperationNotSupportedException e){
            System.out.println(e.getMessage());
        }
        System.out.println("***DATOS DE PRUEBA INSERTADOS***");
    }

    private void anularReserva(){

        Huesped cliente = Consola.getHuespedPorDni();
        Reserva [] lista = controlador.getReservas(cliente);
        //System.out.println("DEBUG: length "+lista.length);

        //for (Reserva reserva:lista)
        //    System.out.println("DEBUG: anular"+reserva);

        lista = getReservasAnulables(lista);

        if (getNumElementosNoNulos(lista) == 0)
            System.out.println("No tiene reservas anulables");
        else if (getNumElementosNoNulos(lista) == 1) {
            String respuesta;

            do {
                System.out.println("Confirma que quiere eliminar la reserva? (Si/No)");
                respuesta = Entrada.cadena();
            } while (!respuesta.equalsIgnoreCase("si") && !respuesta.equalsIgnoreCase("no"));


            if (respuesta.equalsIgnoreCase("si"))
                try {
                    controlador.borrar(lista[0]);
                    System.out.println("**Reserva Eliminada!!!**");
                } catch (NullPointerException|IllegalArgumentException|OperationNotSupportedException e){
                    System.out.println(e.getMessage());
                }


        } else {
            //mostrar todas las posibilidades
            for  (int i = 0; i< getNumElementosNoNulos(lista) ; i++)
                System.out.println(i + " - " + lista[i]);

            //elegir option
            System.out.println("-------------");
            System.out.print("Elija cual desea borrar: ");
            int eleccion;
            do{
                eleccion = Entrada.entero();
            }while (eleccion <0 || eleccion > lista.length);

            //borrar reserva de la colección usando la posición de la lista nueva.
            try{
                controlador.borrar(lista[eleccion]);
                System.out.println("**Reserva Eliminada!!!**");
            }catch (NullPointerException|IllegalArgumentException|OperationNotSupportedException e){
                System.out.println(e.getMessage());
            }

        }
    }

    private void mostrarReservas(){
        Reserva [] lista;
        lista = controlador.getReservas();
        System.out.println(" ");
        System.out.println("*****");
        for (int i=0; i< lista.length; i++) {

            if (lista[i] != null)
                System.out.println(lista[i]);
        }
        System.out.println("*****");
        System.out.println(" ");
    }

    private Habitacion consultarDisponibilidad(TipoHabitacion tipoHabitacion, LocalDate fechaInicioReserva, LocalDate fechaFinReserva)
    {
        boolean tipoHabitacionEncontrada=false;
        Habitacion habitacionDisponible=null;
        int numElementos=0;

        Habitacion[] habitacionesTipoSolicitado= controlador.getHabitaciones(tipoHabitacion);

        if (habitacionesTipoSolicitado==null)
            return habitacionDisponible;

        for (int i=0; i<habitacionesTipoSolicitado.length && !tipoHabitacionEncontrada; i++)
        {

            if (habitacionesTipoSolicitado[i]!=null)
            {
                Reserva[] reservasFuturas = controlador.getReservas(habitacionesTipoSolicitado[i]);
                numElementos=getNumElementosNoNulos(reservasFuturas);

                if (numElementos == 0)
                {
                    //Si la primera de las habitaciones encontradas del tipo solicitado no tiene reservas en el futuro,
                    // quiere decir que está disponible.
                    habitacionDisponible=new Habitacion(habitacionesTipoSolicitado[i]);
                    tipoHabitacionEncontrada=true;
                }
                else {

                    //Ordenamos de mayor a menor las reservas futuras encontradas por fecha de fin de la reserva.
                    // Si la fecha de inicio de la reserva es posterior a la mayor de las fechas de fin de las reservas
                    // (la reserva de la posición 0), quiere decir que la habitación está disponible en las fechas indicadas.

                    Arrays.sort(reservasFuturas, 0, numElementos, Comparator.comparing(Reserva::getFechaFinReserva).reversed());

                    /*System.out.println("\n\nMostramos las reservas ordenadas por fecha de inicio de menor a mayor (numelementos="+numElementos+")");
                    mostrar(reservasFuturas);*/

                    if (fechaInicioReserva.isAfter(reservasFuturas[0].getFechaFinReserva())) {
                        habitacionDisponible = new Habitacion(habitacionesTipoSolicitado[i]);
                        tipoHabitacionEncontrada = true;
                    }

                    if (!tipoHabitacionEncontrada)
                    {
                        //Ordenamos de menor a mayor las reservas futuras encontradas por fecha de inicio de la reserva.
                        // Si la fecha de fin de la reserva es anterior a la menor de las fechas de inicio de las reservas
                        // (la reserva de la posición 0), quiere decir que la habitación está disponible en las fechas indicadas.

                        Arrays.sort(reservasFuturas, 0, numElementos, Comparator.comparing(Reserva::getFechaInicioReserva));

                        /*System.out.println("\n\nMostramos las reservas ordenadas por fecha de inicio de menor a mayor (numelementos="+numElementos+")");
                        mostrar(reservasFuturas);*/

                        if (fechaFinReserva.isBefore(reservasFuturas[0].getFechaInicioReserva())) {
                            habitacionDisponible = new Habitacion(habitacionesTipoSolicitado[i]);
                            tipoHabitacionEncontrada = true;
                        }
                    }

                    //Recorremos el array de reservas futuras para ver si las fechas solicitadas están algún hueco existente entre las fechas reservadas
                    if (!tipoHabitacionEncontrada)
                    {
                        for(int j=1;j<reservasFuturas.length && !tipoHabitacionEncontrada;j++)
                        {
                            if (reservasFuturas[j]!=null && reservasFuturas[j-1]!=null)
                            {
                                if(fechaInicioReserva.isAfter(reservasFuturas[j-1].getFechaFinReserva()) &&
                                        fechaFinReserva.isBefore(reservasFuturas[j].getFechaInicioReserva())) {

                                    habitacionDisponible = new Habitacion(habitacionesTipoSolicitado[i]);
                                    tipoHabitacionEncontrada = true;
                                }
                            }
                        }
                    }


                }
            }
        }

        return habitacionDisponible;
    }


    private static int getNumElementosNoNulos(Reserva[] reservas){
        int numero=0;
        for (int i=0; i<reservas.length; i++) {
            if (reservas[i] != null)
                numero++;
        }


        return numero;
    }

    private void realizarCheckin(){
        Huesped cliente = Consola.getHuespedPorDni();

        Reserva [] lista = controlador.getReservas(cliente);

        /*//Lineas para ayudar con el debug
        System.out.println("DEBUG: length "+lista.length);

        for (Reserva reserva:lista)
            System.out.println("DEBUG: anular"+reserva);*/


        if (getNumElementosNoNulos(lista) == 0)
            System.out.println("El cliente no tiene reservas");

        else if (getNumElementosNoNulos(lista) == 1) {

            System.out.println("Fecha y Hora de CheckIn (dd/MM/yyyy hh:mm:ss): ");
            LocalDateTime fechacheckin = Consola.leerFechaHora(Entrada.cadena());

            try{

                controlador.realizarCheckin(controlador.buscar(lista[0]), fechacheckin);
                System.out.println("*** CheckIn Realizado ***");

            }catch(IllegalArgumentException|NullPointerException e){
                System.out.println(e.getMessage());
            }

        } else {
            //mostrar todas las posibilidades
            for  (int i = 0; i< getNumElementosNoNulos(lista) ; i++)
                System.out.println(i + " - " + lista[i]);

            //elegir option
            System.out.println("-------------");
            System.out.print("Elija de cual desea realizar CheckIn: ");
            int eleccion;
            do{
                eleccion = Entrada.entero();
            }while (eleccion <0 || eleccion > lista.length);

            System.out.println("Fecha y Hora de CheckIn (dd/MM/yyyy hh:mm:ss): ");
            LocalDateTime fechacheckin = Consola.leerFechaHora(Entrada.cadena());

            try{

                controlador.realizarCheckin(controlador.buscar(lista[eleccion]), fechacheckin);
                System.out.println("*** CheckIn Realizado ***");

            }catch(IllegalArgumentException|NullPointerException e){
                System.out.println(e.getMessage());
            }

        }
    }

    private void realizarCheckout(){
        Huesped cliente = Consola.getHuespedPorDni();

        Reserva [] lista = controlador.getReservas(cliente);

        /*//Lineas para ayudar con el debug
        System.out.println("DEBUG: length "+lista.length);

        for (Reserva reserva:lista)
            System.out.println("DEBUG: anular"+reserva);*/


        if (getNumElementosNoNulos(lista) == 0)
            System.out.println("El cliente no tiene reservas");

        else if (getNumElementosNoNulos(lista) == 1) {

            System.out.println("Fecha y Hora de CheckOut (dd/MM/yyyy hh:mm:ss): ");
            LocalDateTime fechacheckout = Consola.leerFechaHora(Entrada.cadena());

            try{

                controlador.realizarCheckout(controlador.buscar(lista[0]), fechacheckout);
                System.out.println("*** CheckOut Realizado ***");

            }catch(IllegalArgumentException|NullPointerException e){
                System.out.println(e.getMessage());
            }

        } else {
            //mostrar todas las posibilidades
            for  (int i = 0; i< getNumElementosNoNulos(lista) ; i++)
                System.out.println(i + " - " + lista[i]);

            //elegir option
            System.out.println("-------------");
            System.out.print("Elija de cual desea realizar CheckOut: ");
            int eleccion;
            do{
                eleccion = Entrada.entero();
            }while (eleccion <0 || eleccion > lista.length);

            System.out.println("Fecha y Hora de CheckOut (dd/MM/yyyy hh:mm:ss): ");
            LocalDateTime fechacheckout = Consola.leerFechaHora(Entrada.cadena());

            try{

                controlador.realizarCheckout(controlador.buscar(lista[eleccion]), fechacheckout);
                System.out.println("*** CheckOut Realizado ***");

            }catch(IllegalArgumentException|NullPointerException e){
                System.out.println(e.getMessage());
            }

        }
    }
}
