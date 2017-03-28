/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.threew.dcr;

import com.threew.dcr.utilidades.Comandos;
import com.threew.dcr.utilidades.DiagnosticosRed;
import com.threew.dcr.utilidades.UtilidadSistema;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author panunez
 */
public class DiagnosticoConectividadRed {
    private static final Logger LOG = Logger.getLogger(DiagnosticoConectividadRed.class.getName());
    private static final String CONTROLLER_IP   =  "10.200.44.1";
    //private static final String CONTROLLER_NAME =  "local.dns.internal.server";
    private static final String CONTROLLER_NAME =  "LOCADNS";
    //private static final String CONTROLLER_NAME =  "LOCADNS";
    
    /// Algunos diagnosticos
    

    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
                 
        try {
            
            InetAddress[] addresses = InetAddress.getAllByName("http://remoteservices.local:8001/WsAuthActiveDirectoryServiceService/WsAuthActiveDirectoryService?wsdl");
            
            for (InetAddress address : addresses) {
              if (address.isReachable(10000))
              {   
                 System.out.println("Conectado: "+ address);
              }
              else
              {
                 System.out.println("Fallido: "+address);
              }
            }
                
//            if(UtilidadSistema.ES_LINUX){
//                LOG.log(Level.INFO, "El SO es: {0}", UtilidadSistema.PREFIJO_LINUX);    
//            } else if(UtilidadSistema.ES_WINDOWS){
//                LOG.log(Level.INFO, "El SO es: {0}", UtilidadSistema.PREFIJO_WINDOWS);    
//            } else {
//                LOG.log(Level.INFO, "No se pudo detectar el SO");
//            }
//            
//            if(UtilidadSistema.ES_ESPANOL){
//                LOG.log(Level.INFO, "El idioma es: ESPAÑOL");    
//            } else if(UtilidadSistema.ES_INGLES){
//                LOG.log(Level.INFO, "El idioma es: INGLES");    
//            } else {
//                LOG.log(Level.INFO, "No se pudo detectar el IDIOMA");    
//            }
//            
//            LOG.log(Level.INFO, "Diagnóstico 0: Determinar nombre interfaz");
//            LOG.log(Level.INFO, "La interfaz de red determinada es: ", DiagnosticosRed.recuperarInterfazEthernetLinux());
//            LOG.log(Level.INFO, DiagnosticosRed.tieneConfiguracionDHCP() == true ? "Se ha detectado configuración DHCP" : "NO Se ha detectado configuración DHCP");
            
            
            /// 1 /// Diagnoóstico usando InetAddress
//            LOG.log(Level.INFO, "Diagnóstico 1: Usando la clase InetAddress");
//            InetAddress direccion = InetAddress.getByName(CONTROLLER_NAME);
            //LOG.log(Level.INFO, direccion.getHostName());
            //LOG.log(Level.INFO, direccion.getHostAddress());
//            LOG.log(Level.INFO, direccion.isReachable(3000) == true ? "Servidor: " + CONTROLLER_NAME + " SI esta disponible" : "Servidor: " + CONTROLLER_NAME + " NO esta disponible");
            
            /// 2 /// Diagnoóstico usando InetAddress
//            LOG.log(Level.INFO, "Diagnóstico 2: Usando la clase HttpURLConnection");
//            HttpURLConnection conexion = (HttpURLConnection) new URL(CONTROLLER_NAME).openConnection();
//            conexion.setRequestMethod("HEAD");
//            int codigo = conexion.getResponseCode();
//            LOG.log(Level.INFO, "Diagnóstico 2: Usando la clase HttpURLConnection");
//            LOG.log(Level.INFO, "Código de Respuesta: {0}", codigo);
//            
            /// 3 /// Diagnoóstico usando Socket
//            LOG.log(Level.INFO, "Diagnóstico 3: Usando la clase Socket");
//            Socket conexion_socket = new Socket(CONTROLLER_NAME, 80);
//            LOG.log(Level.INFO, "RESPUESTA: {0}", String.valueOf(conexion_socket.getInputStream().read()));
//            
            /// 4 /// Usando método local
//            LOG.log(Level.INFO, "Diagnóstico 4: Usando un metodo local");
//            LOG.log(Level.INFO, pingLocal(CONTROLLER_NAME) ==  true ?  "Servidor: " + CONTROLLER_NAME + " SI esta disponible" : "Servidor: " + CONTROLLER_NAME + " NO esta disponible");
            
            /// 5 /// Diagnóstico usando clase interna
//            LOG.log(Level.INFO, "Diagnóstico 5: Usando una clase interna");
//            LOG.log(Level.INFO, DiagnosticosRed.ping(CONTROLLER_NAME) ==  true ?  "Servidor: " + CONTROLLER_NAME + " SI esta disponible" : "Servidor: " + CONTROLLER_NAME + " NO esta disponible");
            
//        } catch(UnknownHostException e){
//            LOG.log(Level.SEVERE, "Host desconocido: {0}",  e.getStackTrace().toString());
//        } catch(MalformedURLException e){
//            LOG.log(Level.SEVERE, "URL mal formada: {0} ", e.getStackTrace().toString());
//        } catch(IOException e){
//            LOG.log(Level.SEVERE, "Excepcion indeterminada: {0} ", e.getStackTrace().toString());

        } catch(Exception e){
            LOG.log(Level.SEVERE, String.format("Excepcion indeterminada: {%s} {%s} ", e.getStackTrace().toString(), e.getMessage()));
        }
       
    }
    
    public static boolean pingLocal(String host)
    {
        boolean isReachable = false;
        try {
            Process proc = new ProcessBuilder("ping", host).start();

            int exitValue = proc.waitFor();
            System.out.println("Exit Value:" + exitValue);
            if(exitValue == 0)
                isReachable = true;
        } catch (IOException e1) {
            System.out.println(e1.getMessage());
            e1.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return isReachable;
    }
    
    
    
}
