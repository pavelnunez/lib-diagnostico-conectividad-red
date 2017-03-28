
package com.threew.dcr.utilidades;

import com.threew.dcr.DiagnosticoConectividadRed;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author panunez
 */
public class UtilidadSistema {
    private static final Logger LOG = Logger.getLogger(DiagnosticoConectividadRed.class.getName());
    
    /// Algunos Sistemas Operativos
    public static final String PREFIJO_WINDOWS = "Windows";
    public static final String PREFIJO_LINUX = "Linux";
    /// Algunos idiomas
    public static final String PREFIJO_ESPANOL = "es";
    public static final String PREFIJO_INGLES = "en";
    /// El sistema operativo que ha sido detectado
    public static final String NOMBRE_SO_DETECTADO = recuperarPropiedadSistema("os.name");
    /// El sistema operativo que ha sido detectado
    public static final String IDIOMA_DETECTADO = recuperarPropiedadSistema("user.language");
    /// Estas propiedades determina si el idioma es Ingles
    public static final boolean ES_INGLES = IDIOMA_DETECTADO.startsWith(PREFIJO_INGLES) || IDIOMA_DETECTADO.startsWith(PREFIJO_INGLES.toLowerCase());
    public static final boolean ES_ESPANOL = IDIOMA_DETECTADO.startsWith(PREFIJO_ESPANOL) || IDIOMA_DETECTADO.startsWith(PREFIJO_ESPANOL.toLowerCase());
    /// Estas propiedades determinan si es Windows o Linux
    public static final boolean ES_WINDOWS = NOMBRE_SO_DETECTADO.startsWith(PREFIJO_WINDOWS) || NOMBRE_SO_DETECTADO.startsWith(PREFIJO_WINDOWS.toUpperCase());
    public static final boolean ES_LINUX = NOMBRE_SO_DETECTADO.startsWith(PREFIJO_LINUX) || NOMBRE_SO_DETECTADO.startsWith(PREFIJO_LINUX.toUpperCase());
    /// Estas propiedades determinan si el commandshell es de Windows o de Linux
    public static final String LINEA_COMANDOS_WINDOWS = "cmd.exe";
    public static final String LINEA_COMANDOS_LINUX = "/bin/sh";
    public static final String PARAMETRO_LINEA_COMANDOS_WINDOWS = "/c";
    public static final String PARAMETRO_LINEA_COMANDOS_LINUX = "-c";
    /// Estas propiedades establecen si se trata de la linea de comandos Windows o Linux    
    public static final String LINEA_COMANDOS = ES_WINDOWS == true ? LINEA_COMANDOS_WINDOWS : LINEA_COMANDOS_LINUX;
    public static final String PARAMETRO_LINEA_COMANDOS =  ES_WINDOWS == true ? PARAMETRO_LINEA_COMANDOS_WINDOWS : PARAMETRO_LINEA_COMANDOS_LINUX;           
    
    
    static boolean coincideNombreSO(String nombreOs, String prefijoOs){
        Boolean coincide = Boolean.FALSE;
        if(nombreOs == null){
            return coincide; 
        }
        return nombreOs.startsWith(prefijoOs);
    }
    
    private static String recuperarPropiedadSistema(String propiedad){
        try {
            return System.getProperty(propiedad);
        } catch(SecurityException e){
            /// Por si no se tiene permiso de acceder a esta propiedad
            LOG.log(Level.SEVERE, "No tiene permiso para acceder a la propiedad: {0}", propiedad);
            return null; 
        } catch(Exception e){
            /// Por si no se logra determinar la razón de la excepcion
            LOG.log(Level.SEVERE, String.format("Ha ocurrido una excepción indeterminada al intentar acceder la propiedad del sistema : {0}. Por la siguiente razón: {1}", propiedad, e.getMessage()));
            return null; 
        }
    }
    
    static boolean correspondeSO(String nombreOS, String versionOS, String prefijoNombreOS, String prefijoVersionOS){
        if(nombreOS == null || versionOS == null){
            return false;
        }
        return nombreOS.startsWith(prefijoNombreOS) && versionOS.startsWith(prefijoVersionOS);
    }
    
    static boolean correspondeNombreOS(String nombreOS, String prefijoNombreOS){
        if(nombreOS == null){
            return false; 
        }
        return nombreOS.startsWith(prefijoNombreOS);
    }
    
    private static boolean recuperarCorrespondeNombreOS(String prefijoNombreOS){
        return correspondeNombreOS(NOMBRE_SO_DETECTADO, prefijoNombreOS);
    }
    
    public UtilidadSistema(){
        super();
    }
    
}
