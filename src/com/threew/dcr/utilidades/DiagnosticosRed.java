package com.threew.dcr.utilidades;

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
 * Clase para diagnosticos de red
 * @author Pavel Núñez Deschamps
 */
public class DiagnosticosRed {
    /// Propiedad que contiene el timeout por defecto para el diagnostico
    private static final int TIMEOUT_POR_DEFECTO = 3000;
    /// Propiedad que almacena el servidor a diagnosticar
    private static final String SERVIDOR_DIAGNOSTICADO = "";
    /// Comandos en diversos idiomas
    private static final String WINDOWS_PING_TIMEOUT_VARIABLE = "ping -n 1 %s -w %s | find /N \"TTL\"";
    private static final String WINDOWS_PING_INGLES = "ping -n 1 %s -w " + TIMEOUT_POR_DEFECTO + " | find /N \"TTL\"";
    private static final String WINDOWS_PING_ESPANOL = WINDOWS_PING_INGLES;
    
    private static final String LINUX_PING_TIMEOUT_VARIABLE = "";
    private static final String LINUX_PING_INGLES = "";
    private static final String LINUX_PING_ESPANOL = LINUX_PING_INGLES;
    
    /// Comandos Alternativos para determinar si hay configuracion DHCP en Windows
    private static final String WINDOWS_DHCP_ESTA_HABILITADO_ALTERNATIVO = "ipconfig /all | find /n /i \"dhcp enabled\"";
    private static final String WINDOWS_DHCP_ESTA_HABILITADO_ALTERNATIVO_INGLES = "ipconfig /all | find /n /i \"dhcp enabled\"";
    private static final String WINDOWS_DHCP_ESTA_HABILITADO_ALTERNATIVO_ESPANOL = "ipconfig /all | find /n /i \"dhcp habilitado\"";
    /// Comandos para determinar si hay configuracion DHCP en Windows
    private static final String WINDOWS_DHCP_ESTA_HABILITADO_INGLES = "systeminfo | find /n /i \"dhcp enabled\"";
    private static final String WINDOWS_DHCP_ESTA_HABILITADO_ESPANOL = "systeminfo | find /n /i \"dhcp habilitado\"";
    /// Comandos para determinar si hay configuracion DHCP en Linux
    private static final String LINUX_DHCP_ESTA_HABILITADO_INGLES = "cat /etc/sysconfig/network-scripts/ifcfg-%s | grep BOOTPROTO";
    private static final String LINUX_DHCP_ESTA_HABILITADO_ESPANOL = "cat /etc/sysconfig/network-scripts/ifcfg-%s | grep BOOTPROTO";
    
    
    /// Nombre(s) de dominio del servidor(es) de dominio mas proximo(s)
    private static final String NOMBRE_GENERICO_DOMAIN_CONTROLLER = "loca.dns.internal.server";
    private static final String NOMBRE_GENERICO_REPLICADOR_DOMAIN_CONTROLLER = "ensrpl." + NOMBRE_GENERICO_DOMAIN_CONTROLLER;
    
    /// IP del replicador y del servidor de dominio mas cercano
    private static final String IP_REPLICADOR_DOMAIN_CONTROLLER = "10.200.44.123";
    private static final String IP_DOMAIN_CONTROLLER = "10.200.44.123";
    
    private static final String NOMBRE_DOMAIN_CONTROLLER_MAS_CERCANO = "";
    /// Determinar mi servidor de dominio mas cercano
    private static final String LINUX_DETERMINAR_SERVIDOR_DOMINIO_MAS_CERCANO = "echo %LOGONSERVER%";
    private static final String WINDOWS_DETERMINAR_SERVIDOR_DOMINIO_MAS_CERCANO = "ping -c 1 " + NOMBRE_GENERICO_DOMAIN_CONTROLLER  + " | grep icmp_seq | cut -d\" \" -f 4";
    /// Determinar la interfaz activa en Linux
    private static final String LINUX_DETERMINAR_INTERFAZ_ETHERNET_COMUNICACION = "ip route get " + IP_DOMAIN_CONTROLLER + " | grep src | cut -d\" \" -f 3";
    /// Determinar servidor que sirvio configuraciones DHCP
    private static final String LINUX_DETERMINAR_SERVIDOR_DHCP = "cat /etc/sysconfig/network-scripts/ifcfg-%s | grep BOOTPROTO";
    
    /**
     * Diagnostico Ping: Determinar si puedo hacer un PING a un servidor remoto. 
     * Es una simulación del comando ping, pero se hace un intento de ejecutarlo
     * disponible para "todas" la plataformas Windows/Linux.
     * @version 0.0.1
     * @return boolean 
     */
    public static boolean ping(String host_o_ip){
        Boolean respuesta = Boolean.FALSE;
        // 1 // Preguntar si es windows o linux
        if(UtilidadSistema.ES_LINUX){
            // 2 // Preguntar si es ingles o español
            if(UtilidadSistema.ES_INGLES){
                respuesta = Comandos.ejecutarComandoLinux(String.format(LINUX_PING_INGLES, host_o_ip));
            } else {
                respuesta = Comandos.ejecutarComandoLinux(String.format(LINUX_PING_ESPANOL, host_o_ip));
            }
        } else if(UtilidadSistema.ES_WINDOWS){
            // 2 // Preguntar si es ingles o español
            if(UtilidadSistema.ES_INGLES){
                respuesta = Comandos.ejecutarComandoWindows(String.format(WINDOWS_PING_INGLES, host_o_ip));
            } else {
                respuesta = Comandos.ejecutarComandoWindows(String.format(WINDOWS_PING_ESPANOL, host_o_ip));
            }
        } else {
            // 2 // Preguntar si es ingles o español
            if(UtilidadSistema.ES_INGLES){
                respuesta = Comandos.ejecutarComandoWindows(String.format(WINDOWS_PING_INGLES, host_o_ip));
            } else {
                respuesta = Comandos.ejecutarComandoWindows(String.format(WINDOWS_PING_ESPANOL, host_o_ip));
            }
        }
        return respuesta;
    }
    
    private static boolean pingPorIp(String ip){
        Boolean respuesta = Boolean.FALSE;
        return Comandos.ejecutarComando(String.format(WINDOWS_PING_ESPANOL, ip));
    }
    
    private static boolean pingPorHost(String host){
        Boolean respuesta = Boolean.FALSE;
        return Comandos.ejecutarComando(String.format(WINDOWS_PING_ESPANOL, host));
    }
    
    private static boolean pingWindows(String host_o_ip){
        Boolean respuesta = Boolean.FALSE;
        if(UtilidadSistema.ES_INGLES){
                respuesta = Comandos.ejecutarComandoWindows(String.format(WINDOWS_PING_INGLES, host_o_ip));
            } else {
                respuesta = Comandos.ejecutarComandoWindows(String.format(WINDOWS_PING_ESPANOL, host_o_ip));
            }
        return respuesta;
    }
    
    private static boolean pingLinux(String host_o_ip){
        Boolean respuesta = Boolean.FALSE;
            if(UtilidadSistema.ES_INGLES){
                respuesta = Comandos.ejecutarComandoLinux(String.format(LINUX_PING_INGLES, host_o_ip));
            } else {
                respuesta = Comandos.ejecutarComandoLinux(String.format(LINUX_PING_ESPANOL, host_o_ip));
            }
            return respuesta;
    }
    
    
    /**
     * Diagnostico 0: Determinar si la PC tiene configuracion DHCP 
     * @version 0.0.1
     * @return boolean 
     */
    public static boolean tieneConfiguracionDHCP(){
        Boolean respuesta = Boolean.FALSE;
        /// 1 /// Determinar el SO
        if(UtilidadSistema.ES_WINDOWS){
            /// 2 /// Determinar el IDIOMA
            if(UtilidadSistema.ES_INGLES){
                return ejecutarComandoExitosamente(WINDOWS_DHCP_ESTA_HABILITADO_INGLES, "yes");
            } else if(UtilidadSistema.ES_ESPANOL){
                return ejecutarComandoExitosamente(WINDOWS_DHCP_ESTA_HABILITADO_ESPANOL, "si");
            } else {
                return respuesta;
            }
        } else if(UtilidadSistema.ES_LINUX){
            String nombreInterfaz = DiagnosticosRed.recuperarInterfazEthernetLinux();
            /// 2 /// Determinar el IDIOMA
            if(UtilidadSistema.ES_INGLES){
                return ejecutarComandoExitosamente(String.format(LINUX_DETERMINAR_SERVIDOR_DHCP, nombreInterfaz)  , "dhcp");
            } else if(UtilidadSistema.ES_ESPANOL){
                return ejecutarComandoExitosamente(String.format(LINUX_DETERMINAR_SERVIDOR_DHCP, nombreInterfaz)  , "dhcp");
            } else {
                return respuesta;
            }
        } else {
            /// 2 /// Determinar el IDIOMA
            if(UtilidadSistema.ES_INGLES){
                return ejecutarComandoExitosamente(WINDOWS_DHCP_ESTA_HABILITADO_INGLES, "yes");
            } else if(UtilidadSistema.ES_ESPANOL){
                return ejecutarComandoExitosamente(WINDOWS_DHCP_ESTA_HABILITADO_ESPANOL, "si");
            } else {
                return respuesta;
            }
        }
    } /// Fin del método tieneConfiguracionDHCP()
    
    /**
     * Este método intenta ejecutar un comando y compara el resultado con una respuesta esperada.
     * Si el resultado de la busqueda de la respuesta en la peticion de la salida estandar del 
     * comando es verdadera retorna True, y False en caso contrario o de excepcion
     * @version 0.0.1
     * @param String comando: El comando en cuestion
     * @param String respuestaEsperada: Fragmento de texto que se espera encontrar en la respuesta
     **/
    private static boolean ejecutarComandoExitosamente(String comando, String respuestaEsperada){
        return Comandos.ejecutarComando(comando, respuestaEsperada);
    }
    
    /**
     * Este método intenta ejecutar un comando externo y retorna True, y False 
     * en caso contrario o de excepcion.
     * @version 0.0.1
     * @param String comando: El comando en cuestion
     **/
    private static boolean ejecutarComandoExitosamente(String comando){
        return Comandos.ejecutarComando(comando);
    }
    
    public static String recuperarInterfazEthernetLinux(){
        String interfaz = null;
        return Comandos.ejecutarComandoLinuxRecuperarSalida(LINUX_DETERMINAR_INTERFAZ_ETHERNET_COMUNICACION);
    }
    
    
}
