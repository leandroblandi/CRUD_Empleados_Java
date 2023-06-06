package bbdd;

import java.sql.*;
import java.util.ArrayList;

public class Conexion {

    // Atributos de la base de datos
    private static final String url = "jdbc:mysql://localhost:3306/empresa";
    private static final String username = "root";
    private static final String password = "4250_96Lz29072002 admin";

    private static Connection conexion = null;

    // Conecta con la base de datos
    public void conectar() {
        if(conexion == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                conexion = DriverManager.getConnection(url, username, password);

            } catch (Exception exception) {
                System.out.print("\n(!) Error al conectar con la Base de Datos.");
                exception.printStackTrace();
            }
        }
    }

    // A partir de un objeto Empleado, inserta sus propiedades en una fila de la bbdd
    public boolean insertarEmpleado(Empleado e) {
        if(conexion != null) {
            try {

                // Preparamos la consulta
                PreparedStatement sentencia = conexion.prepareStatement("INSERT INTO empleados(nombre, apellido, sueldo) VALUES (?, ?, ?)");

                // Enlazamos los parámetros
                sentencia.setString(1, e.getNombre());
                sentencia.setString(2, e.getApellido());
                sentencia.setFloat(3, e.getSueldo());
                sentencia.executeUpdate();

                return true;

            } catch (Exception exception) {
                System.out.print("\n(ERROR) Error al crear el empleado.");
                exception.printStackTrace();
            }
        }
        return false;
    }

    // Convierte cada fila de la bbdd en un objeto de la lista que retornara
    public ArrayList<Empleado> obtenerEmpleados() {

        ArrayList<Empleado> empleados = new ArrayList<>();

        if(conexion != null) {

            try {

                Statement sentencia = conexion.createStatement();
                ResultSet resultado = sentencia.executeQuery("SELECT * FROM EMPLEADOS");

                // Convertimos una fila en un objeto tipo Empleado
                while(resultado.next()) {
                    // Cada columna es un atributo del objeto
                    Empleado e = new Empleado(
                            resultado.getInt("legajo"),
                            resultado.getString("nombre"),
                            resultado.getString("apellido"),
                            resultado.getFloat("sueldo"));
                    empleados.add(e);
                }
                System.out.printf("\n(INFO) %d Empleados obtenidos.", empleados.size());

            } catch (Exception exception) {
                System.out.print("\n(ERROR) No se pudo obtener los empleados.");
                exception.printStackTrace();
            }
        }
        return empleados;
    }

    public boolean existeEmpleado(int legajo) {

        int cantidadFilas = 0;

        if(conexion != null) {
            try {

                PreparedStatement sentencia = conexion.prepareStatement("SELECT * FROM EMPLEADOS WHERE legajo = ?");
                sentencia.setInt(1, legajo);
                ResultSet resultado = sentencia.executeQuery();

                // Con un contador, verificamos que al menos haya una fila con ese legajo único
                while (resultado.next()) {
                    cantidadFilas++;
                }

            } catch (Exception exception) {
                System.out.print("\n(ERROR) No se pudo realizar la búsqueda del empleado");
                exception.printStackTrace();
            }
        }
        return cantidadFilas == 1;
    }

    public boolean modificarEmpleado(int legajo, String nuevoNombre, String nuevoApellido, float nuevoSueldo) {

        if(conexion != null) {
            try {

                PreparedStatement sentencia = conexion.prepareStatement("UPDATE empleados SET nombre = ?, apellido = ?, sueldo = ? WHERE legajo = ?");

                sentencia.setString(1, nuevoNombre);
                sentencia.setString(2, nuevoApellido);
                sentencia.setFloat(3, nuevoSueldo);
                sentencia.setInt(4, legajo);

                sentencia.executeUpdate();

                return true;

            } catch (Exception exception) {
                System.out.printf("\n(ERROR) No se pudo modificar el empleado (leg: %d)", legajo);
                exception.printStackTrace();
            }
        }
        return false;
    }

    public boolean eliminarEmpleado(int legajo) {

        if (conexion != null) {
            try {

                PreparedStatement sentencia = conexion.prepareStatement("DELETE FROM empleados WHERE legajo = ?");

                sentencia.setInt(1, legajo);
                sentencia.executeUpdate();

                return true;

            } catch (Exception exception) {
                System.out.printf("\n(ERROR) No se pudo eliminar el empleado (leg: %d)", legajo);
                exception.printStackTrace();
            }
        }
        return false;
    }

    public void desconectar() {
        if(conexion != null) {
            conexion = null;
        }
    }
}
