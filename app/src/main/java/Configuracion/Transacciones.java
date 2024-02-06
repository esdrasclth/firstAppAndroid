package Configuracion;

public class Transacciones {

    // Nombre de la base de datos
    public static final String DBName = "DBQuizPerson";

    // Creacion de las tablas de las bases de datos
    public static final String TablePersonas = "personas";

    // Creacion de los campos de la base de datos
    public static final String id = "id";
    public static final String nombres = "nombres";
    public static final String apellidos = "apellidos";
    public static final String edad = "edad";
    public static final String correo = "correo";
    public static final String direccion = "direccion";

    // Creación de la sentencia SQL para crear la tabla personas
    public static final String CreateTablePersonas = "CREATE TABLE " + TablePersonas + " ("
            + id + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + nombres + " TEXT, "
            + apellidos + " TEXT, "
            + edad + " INTEGER, "
            + correo + " TEXT, "
            + direccion + " TEXT);";

    // Sentencia SQL para eliminar la tabla personas
    public static final String DropTablePersonas = "DROP TABLE IF EXISTS " + TablePersonas + ";";

    // Sentencia SQL para seleccionar todos los registros de la tabla personas
    public static final String SelectAllPersonas = "SELECT * FROM " + TablePersonas + ";";
}
