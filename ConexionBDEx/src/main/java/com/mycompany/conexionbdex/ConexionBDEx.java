/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.conexionbdex;

import static com.mysql.cj.util.SaslPrep.StringType.QUERY;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.zip.DataFormatException;
import oracle.ucp.jdbc.PoolDataSource;
import oracle.ucp.jdbc.PoolDataSourceFactory;


/**
 *
 * @author David
 */
public class ConexionBDEx {
    //Datos de conexión a la base de datos
    //SENTENCIAS PREPARADAS

    //pds.setConnectionFactoryClassName("oracle.jdbc.pool.OracleDataSource");
    //static final String DB_URL = "jdbc:mysql://localhost:3306/jcvd";    //jdbc:mysql://ip:puerto/base_datos
    //static final String USER = "david";
    //static final String PASS = "1234";
  
    //Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
    
    static PoolDataSource pds;


    public static void main(String[] args) {
        try {
                   
            pds = PoolDataSourceFactory.getPoolDataSource();
    
            //Conexión a la BD.
            pds.setConnectionFactoryClassName("com.mysql.cj.jdbc.MysqlDataSource");
            pds.setURL("jdbc:mysql://localhost:3306/jcvd");
            pds.setUser("david");
            pds.setPassword("1234");
        
            //Tamaño del pool.
            pds.setInitialPoolSize(5);
            
            
            Scanner sc = new Scanner(System.in);
            int opcion;
            //Abre la conexión
            try (Connection conn = pds.getConnection(); Statement stmt = conn.createStatement();) {

                //Menú para seleccionar opciones.
                System.out.println("\tEliga una opción:");
                System.out.println("\n\tConexionBD");
                System.out.println("1 - Buscar nombre por teclado.");
                System.out.println("2 - Lanza consulta 'SELECT * FROM videojuegos'.");
                System.out.println("3 - Nuevo registro por Parametro.");
                System.out.println("4 - Nuevo registro por Teclado.");
                System.out.println("5 - Eliminar un juego por teclado.");
                System.out.println("\tConexionBDEx");
                System.out.println("6 - Buscar nombre nombre.");
                System.out.println("7 - Insertar registro por Parametro.");
                System.out.println("8 - Insertar registro por Teclado.");
                System.out.println("");
                opcion = sc.nextInt();

                switch(opcion){
                    case 1: 
                        System.out.println("¿Que videojuego está en la Base de Datos?");
                        String nombreBuscar = sc.next();
                        System.out.println(buscaNombre(nombreBuscar));
                        break;
                    case 2:
                        lanzaConsulta("SELECT * FROM `videojuegos`");
                        break;
                    case 3:
                        nuevoRegistroParametro("INSERT INTO `videojuegos` (`id`, `Nombre`, `Categoría`, `FechaLanzamiento`, `Compañía`, `Precio`) VALUES (NULL, 'Minecraft', 'Mundo Abierto', '2009-05-17', 'Mojang Studios', '0')");
                        break;
                    case 4:
                        nuevoRegistroTeclado();
                        break;
                    case 5:
                        System.out.println("Introduzca el nombre del Videojuego que desea eliminar: ");
                        String nombreEliminar = sc.nextLine();
                        eliminarRegistro(nombreEliminar);
                        break;
                    case 6:
                        lanzaSentenciaPreparada();
                        break;
                    case 7:
                        //OBTENER CLAVES AUTOGENERADAS
                        insertRegistroParametro();
                        break;
                    case 8:
                        insertRegistroTeclado();
                        break;
                }
               
                //Cierra la conexión.
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }
 
    }

    /**
     * Busca un videojuego
     * @param nombre Introduce el juego pedido por teclado. 
     * @return true si se encuentra el video juego y false si no está
     */
    private static boolean buscaNombre(String nombre) {
        boolean salida = false;
        int contador = 0;

        //Abre la conexión
        try (Connection conn = pds.getConnection(); Statement stmt = conn.createStatement();) {

            String QUERY = "SELECT * FROM `videojuegos` WHERE Nombre = '" + nombre + "'";
            ResultSet rs = stmt.executeQuery(QUERY);

            //Bucle para leer lo que devuelve
            while (rs.next()) {
                contador++;
            }
            //Si encuentra un juego salida = true
            if (contador > 0) {
                salida = true;
            }

            //Cierra la conexión.
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return salida;
    }

    /**
     * Muestra todos los datos de la tabla videojuegos
     * @param miQuery Sentencia predefinida
     */
    
    private static void lanzaConsulta(String miQuery) {
        //Abre la conexión
        try (Connection conn = pds.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(miQuery);) {
            //Se repite mientras haya más resultados
            while (rs.next()) {
                //Obtiene la información según el nombre de la columna
                System.out.print("ID: " + rs.getInt("id"));
                System.out.print(", Nombre: " + rs.getString("Nombre"));
                System.out.print(", Categoría: " + rs.getString("Categoría"));
                System.out.print(", FechaLanzamiento: " + rs.getDate("FechaLanzamiento"));
                System.out.print(", Compañía: " + rs.getString("Compañía"));
                System.out.println(", Precio: " + rs.getFloat("Precio"));
            }
            //Cierra la conexión.
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Añade un videojuego en una sentencia predefinida
     * @param miQuery 
     */
    private static void nuevoRegistroParametro(String miQuery) {
        //Abre la conexión
        try (Connection conn = pds.getConnection(); Statement stmt = conn.createStatement();) {

            //Acutualiza la tabla.
            stmt.executeUpdate(miQuery);

            //Cierra la conexión.
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Introduce datos por teclado
     */
    private static void nuevoRegistroTeclado() {
        Scanner sc = new Scanner(System.in);
        String miQuery;
        String nombre, categoria, fechaLanzamiento, compania;
        float precio;
        //Calendar fechaLanzamiento;
        //Abre la conexión
        try (Connection conn = pds.getConnection(); 
                Statement stmt = conn.createStatement();) {
            
                System.out.println("\tDATOS DEL VIDEOJUEGO");
                //Obligatorio poner Nombre.
                do{
                    System.out.print("Nombre: ");
                    nombre = sc.nextLine();
                }while (nombre.length() == 0);
                System.out.print("Categoria: ");
                categoria = sc.nextLine();
                
                System.out.print("Fecha de Lanzamiento (yyyy-mm-dd): ");        //Si se introduce la fecha mal, peta.
                fechaLanzamiento = sc.nextLine();
                
                System.out.print("Compania: ");
                compania = sc.nextLine();
                System.out.print("Precio: ");
                precio = sc.nextFloat();
            
            //Sentencia final.
            miQuery =  "INSERT INTO `videojuegos` (`id`, `Nombre`, `Categoría`, `FechaLanzamiento`, `Compañía`, `Precio`) VALUES (NULL, '"  + nombre + "',  '"  + categoria + "',  '"  + fechaLanzamiento + "',  '"  + compania + "', '"  + precio + "')";
            stmt.executeUpdate(miQuery);

            //Cierra la conexión.
            conn.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Elimina un videojuego.
     * @param nombreEliminar Introduce nombre del juego a eliminar.
     * @return true si el juego ha sido eliminado, false si no ha sido eliminado.
     */
    private static boolean eliminarRegistro(String nombreEliminar) {
    Scanner sc = new Scanner(System.in);
        boolean salida = false;
        int contador = 0;

        //Abre la conexión
        try (Connection conn = pds.getConnection(); Statement stmt = conn.createStatement();) {

            String QUERY = "DELETE FROM `videojuegos` WHERE nombre = '" + nombreEliminar + "'";

            //Elimina la tabla y recoge el resultado.
            int filasAfectadas = stmt.executeUpdate(QUERY);
            
            //Si no devulve nada, no hay videojuego.
            if (filasAfectadas == 0)
                System.out.println("No se ha encontrado el juego introducido.");
            else{
                System.out.println("Juego eliminado.");
                salida = true;
            }
            
            //Cierra la conexión.
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(salida);
        return salida;
    }

    /**
     * Lanza una sentencia preparada
     * @throws SQLException 
     */
    
    private static void lanzaSentenciaPreparada() throws SQLException {
        String consulta = "SELECT * FROM `videojuegos` WHERE nombre = ? ";
        Connection conexion = pds.getConnection();
        try {
            PreparedStatement sentencia = conexion.prepareStatement (consulta);
            //1 es el primer "?" que hay en la sentencia y "fortnite" el nombre que se pone en "?".
            sentencia.setString(1, "fortnite");
            ResultSet rs = sentencia.executeQuery();
            //Lee mientras haya resultados.
            while (rs.next()) {
                //Obtiene la información según el nombre de la columna
                System.out.print("ID: " + rs.getInt("id"));
                System.out.print(", Nombre: " + rs.getString("Nombre"));
                System.out.print(", Categoría: " + rs.getString("Categoría"));
                System.out.print(", FechaLanzamiento: " + rs.getDate("FechaLanzamiento"));
                System.out.print(", Compañía: " + rs.getString("Compañía"));
                System.out.println(", Precio: " + rs.getFloat("Precio"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //Cierra la conexión.
            if (conexion != null) {
                conexion.close();
            }
        }
    }

    /**
     * Inserta un nuevo videojuego en una sentencia ya preparada.
     * @throws SQLException 
     */
    private static void insertRegistroParametro() throws SQLException {
        Connection conexion = pds.getConnection();
        try {
            PreparedStatement sentencia = conexion.prepareStatement (
            //NULL es el id.
            "INSERT INTO `videojuegos` values (NULL,?,?,?,?,?)",
            PreparedStatement.RETURN_GENERATED_KEYS);
            
            //Los números indican las posiciones en los "?".
            sentencia.setString(1, "OtroPrueba");
            sentencia.setString(2, "Shooter");
            sentencia.setString(3, "2007-05-12");
            sentencia.setString(4, "compania");
            sentencia.setFloat(5, 10);
            sentencia.executeUpdate();
            
            //Se obtiene la clave generada
            ResultSet rs = sentencia.getGeneratedKeys();
            while (rs.next()){
                int claveGenerada = rs.getInt(1);
                System.out.println("Clave generada = " + claveGenerada);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //Cierra la conexión.
            if (conexion != null)
                conexion.close();
        }
    }

    /**
     * Introduce datos por teclado.
     * @throws SQLException 
     */
    private static void insertRegistroTeclado() throws SQLException {
        Scanner sc = new Scanner(System.in);
        String miQuery;
        String nombre, categoria, fechaLanzamiento, compania;
        float precio;
        //Abre la conexión
        Connection conexion = pds.getConnection();
        try {
            PreparedStatement sentencia = conexion.prepareStatement (
            "INSERT INTO `videojuegos` values (NULL,?,?,?,?,?)");
            
                System.out.println("\tDATOS DEL VIDEOJUEGO");
                //El nombre es obligatorio.
                do{
                    System.out.print("Nombre: ");
                    nombre = sc.nextLine();
                }while (nombre.length() == 0);
                
                System.out.print("Categoria: ");
                categoria = sc.nextLine();
                
                System.out.print("Fecha de Lanzamiento (yyyy-mm-dd): ");        //Si se introduce la fecha mal, peta.
                fechaLanzamiento = sc.nextLine();
                
                System.out.print("Compania: ");
                compania = sc.nextLine();
                
                System.out.print("Precio: ");
                precio = sc.nextFloat();
                
                //Posición de los "?"
                sentencia.setString(1, nombre);
                sentencia.setString(2, categoria);
                sentencia.setString(3, fechaLanzamiento);
                sentencia.setString(4, compania);
                sentencia.setFloat(5, precio);

            //Actualiza la tabla videojuegos.
            sentencia.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //Cierra la conexión.
            if (conexion != null)
                conexion.close();
        }
   }        
}
