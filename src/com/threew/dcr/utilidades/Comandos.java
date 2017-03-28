/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.threew.dcr.utilidades;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author panunez
 */
public class Comandos {
    private static final Logger LOG = Logger.getLogger(Comandos.class.getName());
    
    /**
     * Este método intenta ejecutar un comando externo y retorna True, y False 
     * en caso contrario o de excepcion.
     * @version 0.0.1
     * @param String comando: El comando en cuestion
     **/
    public static boolean ejecutarComando(String comando){
        Boolean resultado = Boolean.FALSE;
        String s = null;
 
        try {
            Process p;
            /// 1 /// Ejecutar el comando
            ///Process p = Runtime.getRuntime().exec(comando); /// En lugar de usar Process se usa ProcessBuilder
            /// 2 /// Se crea un proceso especial con parametros invocando el shell de unix/linux por que este comando
            ///       usa tubería
            if(UtilidadSistema.ES_WINDOWS){
                p = new ProcessBuilder(UtilidadSistema.LINEA_COMANDOS_WINDOWS, UtilidadSistema.PARAMETRO_LINEA_COMANDOS_WINDOWS, comando).start();
            } else if(UtilidadSistema.ES_LINUX) {
                p = new ProcessBuilder(UtilidadSistema.LINEA_COMANDOS_LINUX, UtilidadSistema.PARAMETRO_LINEA_COMANDOS_LINUX, comando).start();
            } else {
                p = new ProcessBuilder(UtilidadSistema.LINEA_COMANDOS_WINDOWS, UtilidadSistema.PARAMETRO_LINEA_COMANDOS_WINDOWS, comando).start();
            }
            /// 3 /// Esperar por la salida de ser necesario
            p.waitFor(); 
            LOG.log(Level.INFO, "Ejecutando el comando: {0}", comando);
            
            /// 4 /// Crear un bufer para la salida standard
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
            /// 5 /// Crear un bufer para la salida standard
            BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
 
            // read the output from the command
            System.out.println("Salida estandard del comando:\n");
            while ((s = stdInput.readLine()) != null) {
                if(s.length() > 0){
                    resultado = Boolean.TRUE;
                    LOG.log(Level.INFO, "Respuesta del comando: {0}", s);
                    break;
                }
                
            }
            stdInput.close();
            // read any errors from the attempted command
            System.out.println("Error estandard del comando (si hay alguno):\n");
            while ((s = stdError.readLine()) != null) {
                LOG.log(Level.INFO, "Respuesta de ERROR del comando: {0}", s);
            }
            stdError.close();
            //System.exit(0);
        } catch (InterruptedException e) {
            System.out.println("Excepcion en el proceso: {0} " + e.getMessage());
            e.printStackTrace();
            //System.exit(-1);
        } catch (IOException e) {
            System.out.println("Excepcion en el proceso: {0} " + e.getMessage());
            e.printStackTrace();
            //System.exit(-1);
        }
        return resultado; 
    }
    
    /**
     * Este método intenta ejecutar un comando y compara el resultado con una respuesta esperada.
     * Si el resultado de la busqueda de la respuesta en la peticion de la salida estandar del 
     * comando es verdadera retorna True, y False en caso contrario o de excepcion
     * @version 0.0.1
     * @param String comando: El comando en cuestion
     * @param String respuestaEsperada: Fragmento de texto que se espera encontrar en la respuesta
     **/
    public static boolean ejecutarComando(String comando, String respuestaEsperada){
        Boolean resultado = Boolean.FALSE;
        String s = null;
 
        try {
            Process p;
            /// 1 /// Ejecutar el comando
            ////Process p = Runtime.getRuntime().exec(comando); /// Este metodo fue abolido en funcion de
            if(UtilidadSistema.ES_WINDOWS){
                p = new ProcessBuilder(UtilidadSistema.LINEA_COMANDOS_WINDOWS, UtilidadSistema.PARAMETRO_LINEA_COMANDOS_WINDOWS, comando).start();
            } else if(UtilidadSistema.ES_LINUX) {
                p = new ProcessBuilder(UtilidadSistema.LINEA_COMANDOS_LINUX, UtilidadSistema.PARAMETRO_LINEA_COMANDOS_LINUX, comando).start();
            } else {
                p = new ProcessBuilder(UtilidadSistema.LINEA_COMANDOS_WINDOWS, UtilidadSistema.PARAMETRO_LINEA_COMANDOS_WINDOWS, comando).start();
            }
            
            /// 2 /// Esperar por la salida de ser necesario
            p.waitFor(); 
            
            /// 3 /// Crear un bufer para la salida standard
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
            /// 4 /// Crear un bufer para la salida standard
            BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
 
            // read the output from the command
            System.out.println("Salida estandard del comando:\n");
            while ((s = stdInput.readLine()) != null) {
                if(s.toLowerCase().contains(respuestaEsperada)){
                    resultado = Boolean.TRUE;
                    LOG.log(Level.INFO, "Respuesta del comando: {0}", s);
                    break;
                }
                System.out.println(s);
            }
             
            // read any errors from the attempted command
            System.out.println("Error estandard del comando (si hay alguno):\n");
            while ((s = stdError.readLine()) != null) {
                System.out.println(s);
            }
             
            //System.exit(0);
        } catch (InterruptedException e) {
            System.out.println("Excepcion en el proceso: {0} " + e.getMessage());
            e.printStackTrace();
            //System.exit(-1);
        } catch (IOException e) {
            System.out.println("Excepcion en el proceso: {0} " + e.getMessage());
            e.printStackTrace();
            //System.exit(-1);
        }
        return resultado; 
    } /// Fin del metodo ejecutarComando(String, String)
    
    /**
     * Este método intenta ejecutar un comando externo exclusivamente de Windows
     * y retorna True, y False en caso contrario o de excepcion.
     * @version 0.0.2
     * @param String comando: El comando en cuestion
     **/
    public static boolean ejecutarComandoWindows(String comando){
        Boolean resultado = Boolean.FALSE;
        String s = null;
 
        try {
            /// 1 /// Ejecutar el comando
            ///Process p = Runtime.getRuntime().exec(comando); /// En lugar de usar Process se usa ProcessBuilder
            /// 2 /// Se crea un proceso especial con parametros invocando el shell de unix/linux por que este comando
            ///       usa tubería
            Process p = new ProcessBuilder(UtilidadSistema.LINEA_COMANDOS_WINDOWS, UtilidadSistema.PARAMETRO_LINEA_COMANDOS_WINDOWS, comando).start();
            /// 3 /// Esperar por la salida de ser necesario
            p.waitFor(); 
            LOG.log(Level.INFO, "Ejecutando el comando: {0}", comando);
            
            /// 4 /// Crear un bufer para la salida standard
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
            /// 5 /// Crear un bufer para la salida standard
            BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
 
            // read the output from the command
            System.out.println("Salida estandard del comando:\n");
            while ((s = stdInput.readLine()) != null) {
                if(s.length() > 0){
                    resultado = Boolean.TRUE;
                    LOG.log(Level.INFO, "Respuesta del comando: {0}", s);
                    break;
                }
                
            }
            stdInput.close();
            // read any errors from the attempted command
            System.out.println("Error estandard del comando (si hay alguno):\n");
            while ((s = stdError.readLine()) != null) {
                LOG.log(Level.INFO, "Respuesta de ERROR del comando: {0}", s);
            }
            stdError.close();
            //System.exit(0);
        } catch (InterruptedException e) {
            System.out.println("Excepcion en el proceso: {0} " + e.getMessage());
            e.printStackTrace();
            //System.exit(-1);
        } catch (IOException e) {
            System.out.println("Excepcion en el proceso: {0} " + e.getMessage());
            e.printStackTrace();
            //System.exit(-1);
        }
        return resultado; 
    } /// Fin del metodo ejecutarComandoWindows(String)

    /**
     * Este método intenta ejecutar un comando externo exclusivamente de Linux
     * y retorna True, y False en caso contrario o de excepcion.
     * @version 0.0.2
     * @param String comando: El comando en cuestion
     **/
    public static boolean ejecutarComandoLinux(String comando){
        Boolean resultado = Boolean.FALSE;
        String s = null;
 
        try {
            /// 1 /// Ejecutar el comando
            ///Process p = Runtime.getRuntime().exec(comando); /// En lugar de usar Process se usa ProcessBuilder
            /// 2 /// Se crea un proceso especial con parametros invocando el shell de unix/linux por que este comando
            ///       usa tubería
            Process p = new ProcessBuilder(UtilidadSistema.LINEA_COMANDOS_LINUX, UtilidadSistema.PARAMETRO_LINEA_COMANDOS_LINUX, comando).start();
            /// 3 /// Esperar por la salida de ser necesario
            p.waitFor(); 
            LOG.log(Level.INFO, "Ejecutando el comando: {0}", comando);
            
            /// 4 /// Crear un bufer para la salida standard
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
            /// 5 /// Crear un bufer para la salida standard
            BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
 
            // read the output from the command
            System.out.println("Salida estandard del comando:\n");
            while ((s = stdInput.readLine()) != null) {
                if(s.length() > 0){
                    resultado = Boolean.TRUE;
                    LOG.log(Level.INFO, "Respuesta del comando: {0}", s);
                    break;
                }
                
            }
            stdInput.close();
            // read any errors from the attempted command
            System.out.println("Error estandard del comando (si hay alguno):\n");
            while ((s = stdError.readLine()) != null) {
                LOG.log(Level.INFO, "Respuesta de ERROR del comando: {0}", s);
            }
            stdError.close();
            //System.exit(0);
        } catch (InterruptedException e) {
            System.out.println("Excepcion en el proceso: {0} " + e.getMessage());
            e.printStackTrace();
            //System.exit(-1);
        } catch (IOException e) {
            System.out.println("Excepcion en el proceso: {0} " + e.getMessage());
            e.printStackTrace();
            //System.exit(-1);
        }
        return resultado; 
    } /// Fin del metodo ejecutarComandoWindows(String)
    
    /**
     * Este método intenta ejecutar un comando externo exclusivamente de Linux
     * y retorna True, y False en caso contrario o de excepcion.
     * @version 0.0.2
     * @param String comando: El comando en cuestion
     **/
    public static String ejecutarComandoLinuxRecuperarSalida(String comando){
        String resultado = "";
        String s = null;
 
        try {
            /// 1 /// Ejecutar el comando
            ///Process p = Runtime.getRuntime().exec(comando); /// En lugar de usar Process se usa ProcessBuilder
            /// 2 /// Se crea un proceso especial con parametros invocando el shell de unix/linux por que este comando
            ///       usa tubería
            Process p = new ProcessBuilder(UtilidadSistema.LINEA_COMANDOS_LINUX, UtilidadSistema.PARAMETRO_LINEA_COMANDOS_LINUX, comando).start();
            /// 3 /// Esperar por la salida de ser necesario
            p.waitFor(); 
            LOG.log(Level.INFO, "Ejecutando el comando: {0}", comando);
            
            /// 4 /// Crear un bufer para la salida standard
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
            /// 5 /// Crear un bufer para la salida standard
            BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
 
            // read the output from the command
            System.out.println("Salida estandard del comando:\n");
            while ((s = stdInput.readLine()) != null) {
                if(s.length() > 0){
                    resultado = s;
                    LOG.log(Level.INFO, "Respuesta del comando: {0}", s);
                    break;
                }
                
            }
            stdInput.close();
            // read any errors from the attempted command
            System.out.println("Error estandard del comando (si hay alguno):\n");
            while ((s = stdError.readLine()) != null) {
                LOG.log(Level.INFO, "Respuesta de ERROR del comando: {0}", s);
            }
            stdError.close();
            //System.exit(0);
        } catch (InterruptedException e) {
            System.out.println("Excepcion en el proceso: {0} " + e.getMessage());
            e.printStackTrace();
            //System.exit(-1);
        } catch (IOException e) {
            System.out.println("Excepcion en el proceso: {0} " + e.getMessage());
            e.printStackTrace();
            //System.exit(-1);
        }
        return resultado; 
    } /// Fin del metodo ejecutarComandoWindows(String)
}
