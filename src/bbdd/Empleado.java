package bbdd;

public class Empleado {

    private final int legajo;
    private String nombre;
    private String apellido;
    private float sueldo;

    // Constructores
    public Empleado() {
        this.legajo = 0;
        this.nombre = "";
        this.apellido = "";
        this.sueldo = 0;

    }
    public Empleado(int legajo, String nombre, String apellido, float sueldo) {
        this.legajo = legajo;
        this.nombre = nombre;
        this.apellido = apellido;
        this.sueldo = sueldo;
    }

    @Override
    public String toString() {
        return "\n\nLegajo: " + legajo +
                "\nNombre: " + nombre +
                "\nApellido: " + apellido +
                "\nSueldo: " + sueldo;
    }

    // Getters
    public String getNombre() {
        return nombre;
    }
    public String getApellido() {
        return apellido;
    }
    public float getSueldo() {
        return sueldo;
    }

    // Setters
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    public void setSueldo(float sueldo) {
        this.sueldo = sueldo;
    }
}
