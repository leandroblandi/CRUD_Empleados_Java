
package crud;

import bbdd.Conexion;
import bbdd.Empleado;
import java.util.ArrayList;
import java.util.Scanner;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Sistema {

    private final static Scanner entrada = new Scanner(System.in);
    private final static Conexion bbdd = new Conexion();

    // Apenas se realiza la instancia, se intenta conectar con la bbdd
    public Sistema() {
        bbdd.conectar();
    }

    public void crearEmpleado() {

        Empleado e = new Empleado();

        System.out.print("\n=== Crear empleado ===");

        System.out.print("\n* Nombre: ");
        e.setNombre(entrada.next());

        System.out.print("\n* Apellido: ");
        e.setApellido(entrada.next());

        System.out.print("\n* Sueldo: ");
        e.setSueldo(entrada.nextFloat());

        if(bbdd.insertarEmpleado(e)) {
            System.out.print("\n(OK) Empleado creado exitosamente");
        }
        else {
            System.out.print("(ERROR) No se puede crear el empleado");
        }
    }

    public void obtenerListaEmpleados() {

        ArrayList<Empleado> empleados = bbdd.obtenerEmpleados();

        System.out.print("\n=== Lista de Empleados ===");

        if(empleados.size() > 0) {
            for (Empleado empleado: empleados) {
                System.out.print(empleado);
            }
        }
        else {
            System.out.print("\n(ERROR) No hay empleados cargados en el sistema");
        }
    }

    public void modificarEmpleado() {

        Scanner entrada = new Scanner(System.in);
        int legajo;
        String nombre, apellido;
        float sueldo;

        System.out.print("\n=== Modificar empleado ===");

        System.out.print("\n* Ingrese legajo del empleado a modificar: ");
        legajo = entrada.nextInt();

        // Se verifica que exista antes de modificar
        if(bbdd.existeEmpleado(legajo)) {

            System.out.printf("\n* Ingrese nuevo nombre para empleado legajo %d: ", legajo);
            nombre = entrada.next();

            System.out.printf("\n* Ingrese nuevo apellido para empleado legajo %d: ", legajo);
            apellido = entrada.next();

            System.out.printf("\n* Ingrese sueldo para empleado legajo %d: ", legajo);
            sueldo = entrada.nextFloat();

            if(bbdd.modificarEmpleado(legajo, nombre, apellido, sueldo)) {
                System.out.printf("\n(OK) Empleado modificado exitosamente (leg: %d)", legajo);
            }
        }
        else {
            System.out.printf("\n(ERROR) El empleado no existe (leg: %d)", legajo);
        }
    }

    public void eliminarEmpleado() {

        int legajo;
        System.out.print("\n=== Eliminar empleado ===");

        System.out.print("\n* Ingrese el legajo del empleado a eliminar: ");
        legajo = entrada.nextInt();

        // Se verifica que exista
        if(bbdd.existeEmpleado(legajo)) {
            if(bbdd.eliminarEmpleado(legajo)) {
                System.out.printf("\n(OK) Empleado eliminado exitosamente (leg: %d)", legajo);
            }
        }
        else {
            System.out.printf("\n(ERROR) El empleado no existe (leg: %d)", legajo);
        }
    }

    // Menu de opciones en bucle que controla todos los metodos anteriores
    public void iniciarMenu() {

        boolean seguir = true;
        int opcion;

        // Se obtiene la fecha actual, en formato día y hora, para mostrarlo en el título
        DateFormat formatoFecha = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
        String fecha = formatoFecha.format(new Date());

        System.out.printf("\n=== Sistema Empleados | %s ===", fecha);

        do {

            System.out.print("\n\n=== Menu principal ===");
            System.out.print("\n\t1. Listar empleados\n\t2. Añadir un empleado\n\t3. Modificar un empleado\n\t4. Eliminar un empleado\n\t5. Salir\n\n* Ingrese opcion: ");
            opcion = entrada.nextInt();

            switch (opcion) {
                case 1 -> obtenerListaEmpleados();
                case 2 -> crearEmpleado();
                case 3 -> modificarEmpleado();
                case 4 -> eliminarEmpleado();
                case 5 -> seguir = false; // Condición de corte
                default -> System.out.print("\n(!) Opcion incorrecta");
            }

        } while(seguir);
        bbdd.desconectar();
    }
}
