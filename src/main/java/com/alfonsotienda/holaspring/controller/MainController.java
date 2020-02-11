package com.alfonsotienda.holaspring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;


/**
 * MainController
 */

@Controller
public class MainController {

    @GetMapping("/")
    @ResponseBody
    public String holaMundo(@RequestParam("nombre") String name, @RequestParam("edad") Integer edad) {
        return "Hola " + name + " tienes " + edad + "años";
    }

    @GetMapping("/ingles")
    @ResponseBody
    public String helloWorld() {
        return "Hello World";
    }

    @GetMapping("/nuevo")
    public ModelAndView helloHTML() {
        ModelAndView respuesta = new ModelAndView("hello");
        String msg = "Hola Mundo html";
        respuesta.addObject("mensaje", msg);
        return respuesta;
    }

    @GetMapping("/calculadora")
    public ModelAndView calculadoraHTML() {
        ModelAndView modelAndView = new ModelAndView("hello");
        modelAndView.addObject("mensaje", "");
        return modelAndView;
    }

    @PostMapping("/calculadora")
    public ModelAndView calculadoraHTMLPost(@RequestParam("operando1") Integer operando1,
            @RequestParam(value = "operando2", required = false) Integer operando2,
            @RequestParam("operacion") String operacion) {

        ModelAndView modelAndView = new ModelAndView("hello");

        String resultado = calcula(operando1, operacion, operando2);

        modelAndView.addObject("mensaje", "El resultado es " + resultado);

        return modelAndView;
    }


    @GetMapping("/preguntas")
    public ModelAndView preguntasHTML() {
        ModelAndView modelAndView = new ModelAndView("preguntas");
        modelAndView.addObject("mensaje", "");
        return modelAndView;
    }

    @PostMapping("/preguntas")
    public ModelAndView preguntasHTMLPost(@RequestParam("respuesta1") String respuesta1,
            @RequestParam("respuesta2") String respuesta2,
            @RequestParam("respuesta3") String respuesta3) {
        ModelAndView modelAndView = new ModelAndView("preguntas");
        String resultado = "¿De que color es el caballo blanco de Santiago? "+respuesta1+"\n¿Ultimo libro leido? "+respuesta2+"\n¿Color favorito? "+respuesta3;

        modelAndView.addObject("mensaje", resultado);

        return modelAndView;
    }

    
    @GetMapping("/insertar")
    public ModelAndView insertarHTML() {
        ModelAndView modelAndView = new ModelAndView("insertar");
        modelAndView.addObject("mensaje", "");
        return modelAndView;
    }

    @PostMapping("/insertar")
    public ModelAndView insertarHTMLPost(@RequestParam("nombre") String nombre,
            @RequestParam("apellidos") String apellidos,
            @RequestParam("edad") String edad) throws Exception{
        ModelAndView modelAndView = new ModelAndView("insertar");
            // JDBC driver name and database URL
        final String JDBC_DRIVER = "com.mysql.jdbc.jdbc2.optional.MysqlDataSource";
        final String DB_URL = "jdbc:mysql://iprocuratio.com:3333/joseN";

        // Database credentials
        final String USER = "root";
        final String PASS = "once012020";
        Connection conn = null;
        Statement stmt = null;

        // Register JDBC driver
        Class.forName(JDBC_DRIVER);

        // Open a connection
        System.out.println("Connecting to database...");
        conn = DriverManager.getConnection(DB_URL, USER, PASS);
        // Execute a query
        System.out.println("Creating statement...");
        stmt = conn.createStatement();

        stmt.executeUpdate(
            "CREATE TABLE if not exists Employees ( id INT(11) PRIMARY KEY, first VARCHAR(256),  last VARCHAR(256),age INTEGER);");
        stmt.executeUpdate("INSERT ignore INTO Employees VALUES(1,'Jack','Smith', 100);");

            stmt.executeUpdate(
                "INSERT ignore INTO Employees(first, last, age) VALUES('" + nombre + "','" + apellidos + "'," + edad + ");");
        // Clean-up environment

        stmt.close();
        conn.close();
 

    

        String resultado = "INSERT ignore INTO Employees(first, last, age) VALUES('" + nombre + "','" + apellidos + "'," + edad + ");";

        modelAndView.addObject("mensaje", "INSERTADO MEDIANTE"+resultado);

        return modelAndView;
    }



    private String calcula(Integer operando1, String operacion, Integer operando2) {
        double resultado = 0.0;
        switch (operacion) {
        case "suma":
            resultado = operando1 + operando2;
            break;
        case "resta":
            resultado = operando1 - operando2;
            break;
        case "multiplicacion":
            resultado = operando1 * operando2;
            break;
        case "division":
            resultado = operando1 / operando2;
            break;
        case "^":
            resultado = (int) Math.pow(operando1, operando2);
            break;
        case "mod":
            resultado = operando1 % operando2;
            break;
        case "raiz":
            resultado = Math.sqrt(operando1);
            break;
        default:
            return "No coinciden el operador";
        }
        return operando1 + " " + operacion + " " + operando2 + " = " + resultado;
    }
} // mi calculadora