
package com.threew.dcr.utilidades;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Clase para extraccion de configuracion de SO
 * @author Pavel Núñez Deschamps
 */
public class ExtraccionConfiguracionOS {
    /// Comandos Alternativos para determinar si hay configuracion DHCP en Windows
    private static final String WINDOWS_DHCP_ESTA_HABILITADO_ALTERNATIVO = "ipconfig /all | find /n /i \"dhcp enabled\"";
    private static final String WINDOWS_DHCP_ESTA_HABILITADO_ALTERNATIVO_INGLES = "ipconfig /all | find /n /i \"dhcp enabled\"";
    private static final String WINDOWS_DHCP_ESTA_HABILITADO_ALTERNATIVO_ESPANOL = "ipconfig /all | find /n /i \"dhcp habilitado\"";
    /// Comandos para determinar si hay configuracion DHCP en Windows
    private static final String WINDOWS_DHCP_ESTA_HABILITADO_INGLES = "systeminfo | find /n \"DHCP Enabled\"";
    private static final String WINDOWS_DHCP_ESTA_HABILITADO_ESPANOL = "systeminfo | find /n \"DHCP Habilitado\"";
    /// Comandos para determinar si hay configuracion DHCP en Linux
    private static final String LINUX_DHCP_ESTA_HABILITADO_INGLES = "systeminfo | find /n \"DHCP Enabled\"";
    private static final String LINUX_DHCP_ESTA_HABILITADO_ESPANOL = "systeminfo | find /n \"DHCP Habilitado\"";
    /// Determinar la interfaz activa en Linux
    private static final String LINUX_DETERMINAR_INTERFAZ_ETHERNET_COMUNICACION = "ip route get %s | grep src";
    /// Determinar servidor que sirvio configuraciones DHCP
    private static final String LINUX_DETERMINAR_SERVIDOR_DHCP = "";
    
    /// Nombre(s) de dominio del servidor(es) de dominio mas proximo(s)
    private static final String NOMBRE_GENERICO_DOMAIN_CONTROLLER = "local.dns.internal.server";
    private static final String NOMBRE_GENERICO_REPLICADOR_DOMAIN_CONTROLLER = "ensrpl." + NOMBRE_GENERICO_DOMAIN_CONTROLLER;
    
    /// IP del replicador y del servidor de dominio mas cercano
    private static final String IP_REPLICADOR_DOMAIN_CONTROLLER = "10.200.8.134";
    private static final String IP_DOMAIN_CONTROLLER = "10.200.71.5";
    
    private static final String NOMBRE_DOMAIN_CONTROLLER_MAS_CERCANO = "";
    /// Determinar mi servidor de dominio mas cercano
    private static final String LINUX_DETERMINAR_SERVIDOR_DOMINIO_MAS_CERCANO = "echo %LOGONSERVER%";
    private static final String WINDOWS_DETERMINAR_SERVIDOR_DOMINIO_MAS_CERCANO = "ping -c 1 " + NOMBRE_GENERICO_DOMAIN_CONTROLLER  + " | grep icmp_seq | cut -d\" \" -f 4";
    
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
    
    
}
