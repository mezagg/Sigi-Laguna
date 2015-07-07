/**
 * Nombre del Programa : DateUtils.java
 * Autor                            : vaguirre
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 18 Apr 2011
 * Marca de cambio        : N/A
 * Descripcion General    : Clase de utileria de fechas.
 * Programa Dependiente  :N/A
 * Programa Subsecuente :N/A
 * Cond. de ejecucion        :N/A
 * Dias de ejecucion          :N/A                             Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor                       :N/A
 * Compania               :N/A
 * Proyecto                 :N/A                                 Fecha: N/A
 * Modificacion           :N/A
 *------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.comun.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Clase de utileria de fechas.
 * 
 * @version 1.0
 * @author vaguirre
 * 
 */
public class DateUtils {
    /**
     * <code>SimpleDateFormat</code> para fechas con la mascara
     * <b>dd/MM/yyyy</b>
     */
    private final static SimpleDateFormat SDF_FECHA = new SimpleDateFormat(
            "dd/MM/yyyy");
    private final static SimpleDateFormat SDF_HORA = new SimpleDateFormat(
            "HH:mm");
    private final static SimpleDateFormat SDF_HORA_AM = new SimpleDateFormat(
    "hh:mm a");
    private final static SimpleDateFormat SDF_HORA_SS = new SimpleDateFormat(
            "HH:mm:ss");
    private final static SimpleDateFormat SDF_FECHA_HORA = new SimpleDateFormat(
            "dd/MM/yyyy-HH:mm");
    private final static SimpleDateFormat SDF_FECHA_HORA_AMPM = new SimpleDateFormat(
    "dd/MM/yyyy-hh:mm aa");
    private final static SimpleDateFormat SDF_FECHA_HORA_US = new SimpleDateFormat(
            "M/d/yyyy-HH:mm");
    private final static SimpleDateFormat SDF_FECHA_SQL_112 = new SimpleDateFormat(
            "yyyyMMdd");
    private final static SimpleDateFormat SDF_FECHA_SQL_120 = new SimpleDateFormat(
    "yyyy-MM-dd HH:mm:ss");
    
    private final static SimpleDateFormat SDF_FECHA_SQL_131 = new SimpleDateFormat(
    		"dd/MM/yyyy HH:mm:ss a");
    private final static SimpleDateFormat SDF_FECHA_FOLIO = new SimpleDateFormat(
    "dd-MM-yyyy");
    
    
    /**
     * Logger-
     */
    private final static Logger logger = Logger.getLogger(DateUtils.class);

    /**
     * Convierte una cadena en una fecha.
     * 
     * @param fechaStr
     *            Fecha en <code>String</code> a transformar en formato
     *            <b>dd/MM/yyyy</b>.
     * @return
     * @throws NSJPNegocioException
     *             En caso de que la cadena no tenga el formato esperado.
     */
    public static Date obtener(String fechaStr) throws NSJPNegocioException {
        try {
            final Calendar temp = Calendar.getInstance();
            temp.setTime(SDF_FECHA.parse(fechaStr));
            setMedioDia(temp);
            return temp.getTime();
        } catch (ParseException e) {
            logger.error(e.getMessage());
            throw new NSJPNegocioException(CodigoError.FORMATO, e);
        }
    }

    /**
     * Crea una fecha a partir de las cadenas de fecha (en formato
     * <b>dd/MM/yyyy</b>) y hora (en formato <b>HH:mm</b>).
     * 
     * @param fechaStr
     *            Fecha en <code>String</code> a transformar en formato
     *            <b>dd/MM/yyyy</b>.
     * @param horaStr
     *            Hora en <code>String</code> a transformar en formato
     *            <b>HH:mm</b>.
     * @return
     * @throws NSJPNegocioException
     *             En caso de que la cadena no tenga el formato esperado.
     */
    public static Date obtener(String fechaStr, String horaStr)
            throws NSJPNegocioException {
        try {
        	Date intermedia = new Date();
        	Pattern patron = Pattern.compile("[0-2][0-9]:[0-5][0-9] [aApP][Mm]");
        	Matcher encaja = patron.matcher(horaStr);
        	 
            if(encaja.matches()){
            	intermedia = SDF_FECHA_HORA_AMPM.parse(fechaStr + "-" + horaStr);
            }else{
            	intermedia = SDF_FECHA_HORA.parse(fechaStr + "-" + horaStr);
            }
            
            final Date resp = intermedia;
			return resp;
        } catch (ParseException e) {
            logger.error(e.getMessage());
            throw new NSJPNegocioException(CodigoError.FORMATO, e);
        }
    }

    /**
     * Crea una fecha a partir de las cadenas de fecha (en formato
     * <b>dd/MM/yyyy</b>) y hora (en formato <b>HH:mm</b>).
     * 
     * @param fechaStr
     *            Fecha en <code>String</code> a transformar en formato
     *            <b>dd/MM/yyyy</b>.
     * @param horaStr
     *            Hora en <code>String</code> a transformar en formato
     *            <b>HH:mm</b>.
     * @return
     * @throws NSJPNegocioException
     *             En caso de que la cadena no tenga el formato esperado.
     */
    public static Date obtenerUS(String fechaStr, String horaStr)
            throws NSJPNegocioException {
        try {
            final Date resp = SDF_FECHA_HORA_US.parse(fechaStr + "-" + horaStr);
            return resp;
        } catch (ParseException e) {
            logger.error(e.getMessage());
            throw new NSJPNegocioException(CodigoError.FORMATO, e);
        }
    }

    /**
     * Crea una fecha a partir de las cadenas de fecha (en formato
     * <b>dd/MM/yyyy</b>) y hora (en formato <b>HH:mm</b>).
     * 
     * @param fechaStr
     *            Fecha en <code>String</code> a transformar en formato
     *            <b>dd/MM/yyyy</b>.
     * @param horaStr
     *            Hora en <code>String</code> a transformar en formato
     *            <b>HH:mm</b>.
     * @return La fecha con el a&ntilde;os, mes d&iacute;a, hora y minuto especificado. Si
     *         alguna de las cadenas es apunta a <code>null</code> regresa
     *         <code>null</code>.
     * @throws NSJPNegocioException
     *             En caso de que la cadena no tenga el formato esperado.
     */
    public static Date obtenerNulleable(String fechaStr, String horaStr) {
        if (StringUtils.isNotBlank(fechaStr) && StringUtils.isNotBlank(horaStr)) {
            try {
                return obtener(fechaStr, horaStr);
            } catch (NSJPNegocioException e) {
                logger.warn(e.getMessage());
            }
        }
        return null;
    }

    /**
     * Obtiene la hora en una representaci&oacute;n <code>String</code> con el formato
     * <b>HH:mm</b>.
     * 
     * @param date
     *            Fecha a transformar.
     * @return Fecha en <code>String</code>.
     */
    public static String formatearHora(Date date) {
        return date != null ? SDF_HORA.format(date) : StringUtils.EMPTY;
    }

    /**
     * Obtiene la hora en una representaci&oacute;n <code>String</code> con el formato
     * <b>HH:mm:ss</b>.
     * 
     * @param date
     *            Fecha a transformar.
     * @return Fecha en <code>String</code>.
     */
    public static String formatearHoraSegs(Date date) {
        return date != null ? SDF_HORA_SS.format(date) : StringUtils.EMPTY;
    }

    /**
     * Obtiene la fecha en una representaci&oacute;n <code>String</code> con el formato
     * <b>dd/MM/yyyy</b>.
     * 
     * @param date
     *            Fecha a transformar.
     * @return Fecha en <code>String</code>.
     */
    public static String formatear(Date date) {

        return date != null ? SDF_FECHA.format(date) : StringUtils.EMPTY;
    }

    /**
     * Obtiene la fecha en una representaci&oacute;n <code>String</code> con el formato
     * <b>yyyyMMdd</b> para usarse con el <code>CONVERT</code> de sql Server con
     * el <i>style</i> <b>112</b>, ejempo:
     * <code>CONVERT (nvarchar, obj.fechaAtencion, 112) =
     * ?</code> .
     * 
     * @param date
     *            Fecha a transformar.
     * @return Fecha en <code>String</code>.
     */
    public static String formatearBD(Calendar date) {
        return formatearBD(date.getTime());
    }

    /**
     * Obtiene la fecha en una representaci&oacute;n <code>String</code> con el formato
     * <b>yyyyMMdd</b> para usarse con el <code>CONVERT</code> de sql Server con
     * el <i>style</i> <b>112</b>, ejempo:
     * <code>CONVERT (nvarchar, obj.fechaAtencion, 112) =
     * ?</code> .
     * 
     * @param date
     *            Fecha a transformar.
     * @return Fecha en <code>String</code>.
     */
    public static String formatearBD(Date date) {
        return SDF_FECHA_SQL_112.format(date);
    }

    /**
     * Obtiene la fecha en una representaci&oacute;n <code>String</code> con el formato
     * <b>dd/MM/yyyy</b>.
     * 
     * @param date
     *            Fecha a transformar.
     * @return Fecha en <code>String</code>.
     */
    public static String formatear(Calendar date) {
        return formatear(date.getTime());
    }

    /**
     * Asigna la hora del d&iacute;a exactamente a las 12:00:00.00 PM.
     * 
     * @param fecha
     *            Instancia de calendario
     */
    private static void setMedioDia(final Calendar fecha) {
        fecha.set(Calendar.HOUR_OF_DAY, 12);
        fecha.set(Calendar.MINUTE, 0);
        fecha.set(Calendar.SECOND, 0);
        fecha.set(Calendar.MILLISECOND, 0);
    }
    
    /**
     * Asigna la fecha en hora, minuto, segundo y milisegundo en 0.
     * Para obtener solo la fecha.
     * @param fecha
     */
    public static void setHoraMinutoSegundoCero(final Calendar fecha) {
        fecha.set(Calendar.AM_PM, Calendar.AM);
        fecha.set(Calendar.HOUR, 0);
        fecha.set(Calendar.MINUTE, 0);
        fecha.set(Calendar.SECOND, 0);
        fecha.set(Calendar.MILLISECOND, 0);
    }
    
    /**
     * Suma un determinado numero de d&iacute;as a una  fecha.
     * Se respeta la congruencia de la fecha.
     * 
     * @param fecha
     * @param dias
     */
    public static void sumarDias(final Calendar fecha, int dias) {
        fecha.add(Calendar.DATE, dias);
    }
    
    /**
     * Suma minutos a una fecha y hora particular.
     * @param input ehca y hora
     * @param sumaMinutos cantidad de minutis a sumar.
     * @return
     */
    public static Date sumarMinutos(Date input, int sumaMinutos) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(input);
        cal.add(Calendar.MINUTE, sumaMinutos);
        return cal.getTime();
    }
    
    /**
     * Obtiene la fecha en una representaci&oacute;n <code>String</code> con el formato
     * <b>YYYY-MM-DD HH:MI:SS</b> para usarse con el <code>CONVERT</code> de sql Server con
     * el <i>style</i> <b>131/b>, ejempo:
     * <code>CONVERT (nvarchar, obj.fechaAtencion, 131) =
     * ?</code> .
     * 
     * @param date
     *            Fecha a transformar.
     * @return Fecha en <code>String</code>.
     */
    public static String formatearBD120(Date date) {
        return SDF_FECHA_SQL_120.format(date);
    }
    

    /**
     * Obtiene la hora en una representaci&oacute;n <code>String</code> con el formato
     * <b>hh:mm a</b>. La hora con formato am/pm
     * 
     * @param date
     *            Fecha a transformar.
     * @return Fecha en <code>String</code>.
     */
    public static String formatearHoraAm(Date date) {
        return date != null ? SDF_HORA_AM.format(date) : StringUtils.EMPTY;
}
    
    public static String formatearBDConHora(Date date) {
        return SDF_FECHA_SQL_131.format(date);
    }
    
	/**
	 * M&eacute;todo utilitario que se utiliza para calcular el intervalo de a&ntilde;os que ha 
	 * transcurrido entre dos fechas. 
	 * @param fechaInicial - Fecha inicial de la cual se va a calcular los a&ntilde;os transcurridos.
	 * @param fechaFinal - Fecha final de la cual se va a calcular los a&ntilde;os transcurridos.
	 * @return intervaloAnios - A&ntilde;os transcurridos entre las dos fechas.
	 */
	public static Integer calcularIntervaloAnios(Date fechaInicial,Date fechaFinal){
		Integer intervaloAnios = 0;
		if (fechaInicial != null && fechaFinal != null){
			int anioInicial = 0;
			int anioFinal = 0;
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(fechaInicial);
			anioInicial = cal.get(Calendar.YEAR);
			int mesInicial = cal.get(Calendar.MONTH);
			int diaInicial = cal.get(Calendar.DAY_OF_MONTH);
			
			cal.setTime(fechaFinal);
			anioFinal = cal.get(Calendar.YEAR);
			int mesFinal = cal.get(Calendar.MONTH);
			int diaFinal = cal.get(Calendar.DAY_OF_MONTH);
			
			if (anioFinal > anioInicial){
				if(mesFinal > mesInicial){
					intervaloAnios = anioFinal - anioInicial;							
				}else if(mesFinal == mesInicial){
					if(diaFinal >= diaInicial){
						intervaloAnios = anioFinal - anioInicial;
					}else{
						intervaloAnios = (anioFinal-1) - anioInicial;
					}
				}else{
					intervaloAnios = (anioFinal-1) - anioInicial;
				}
			}else if (anioFinal == anioInicial){
				intervaloAnios = anioFinal - anioInicial;
			}
		}
		return intervaloAnios;
	}
	
	/**
	 * M&eacute;todo utilitario que se utiliza para calcular el intervalo de meses que ha 
	 * transcurrido entre dos fechas. 
	 * @param fechaInicial - Fecha inicial de la cual se va a calcular los meses transcurridos.
	 * @param fechaFinal - Fecha final de la cual se va a calcular los meses transcurridos.
	 * @return intervaloMeses - Meses transcurridos entre las dos fechas.
	 */
	public static Integer calcularIntervaloMeses(Date fechaInicial,Date fechaFinal){
		Integer intervaloMeses = 0;
		if (fechaInicial != null & fechaInicial != null){
			Calendar cal = Calendar.getInstance();
			cal.setTime(fechaInicial);
			Integer mesInicial = cal.get(Calendar.MONTH);
			Integer diaInicial = cal.get(Calendar.DAY_OF_MONTH);
			
			cal.setTime(fechaFinal);
			Integer mesFinal = cal.get(Calendar.MONTH);
			Integer diaFinal = cal.get(Calendar.DAY_OF_MONTH);
			
			if (mesFinal > mesInicial){
				if(diaFinal >= diaInicial){
					intervaloMeses = mesFinal - mesInicial; 				
				}else{
					intervaloMeses = mesFinal - mesInicial - 1;
				}
			}else if (mesFinal == mesInicial){
				if(diaFinal >= diaInicial){
					intervaloMeses = mesFinal - mesInicial; 				
				}else{
					intervaloMeses = ((cal.getMaximum(Calendar.MONTH)+1) - mesInicial) + mesFinal - 1;
				}
			}else{
				if(diaFinal >= diaInicial){
					intervaloMeses = ((cal.getMaximum(Calendar.MONTH)+1) - mesInicial) + mesFinal; 				
				}else{
					intervaloMeses = ((cal.getMaximum(Calendar.MONTH)+1) - mesInicial) + mesFinal - 1;
				}
			}
		}
		return intervaloMeses;
	}
	
	/**
	 * M&eacute;todo utilitario que se utiliza para calcular el intervalo de d&iacute;as que ha 
	 * transcurrido entre dos fechas. 
	 * @param fechaInicial - Fecha inicial de la cual se va a calcular los d&iacute;as transcurridos.
	 * @param fechaFinal - Fecha final de la cual se va a calcular los d&iacute;as transcurridos.
	 * @return intervaloDias - D&iacute;as transcurridos entre las dos fechas.
	 */
	public static Integer calcularIntervaloDias(Date fechaInicial, Date fechaFinal){
		Integer intervaloDias = 0;
		if (fechaInicial != null && fechaFinal != null){
			Calendar cal = Calendar.getInstance();
			cal.setTime(fechaInicial);
			Integer diaInicial = cal.get(Calendar.DAY_OF_MONTH);
			
			cal.setTime(fechaFinal);
			Integer mesFinal = cal.get(Calendar.MONTH);
			Integer diaFinal = cal.get(Calendar.DAY_OF_MONTH);
			
			if (diaFinal >= diaInicial){
				intervaloDias = diaFinal - diaInicial;
			}else{
				cal.set(Calendar.MONTH, mesFinal-1);
				intervaloDias = (cal.getMaximum(Calendar.DAY_OF_MONTH) - diaInicial) + diaFinal;
			}
		}
		return intervaloDias;
	}
	
	/**
	 * M&eacute;todo que lleva a cabo la suma de una fecha y un intervalo de tiempo transcurrido.
	 * @param fechaOriginal - fecha inicial a la cual se va a sumar el intervalo de tiempo 
	 * 						  transcurrido.
	 * @param anios - A&ntilde;os que ser&aacute;n sumados a la fecha inicial.
	 * @param meses - Meses que ser&aacute;n sumados a la fecha inicial.
	 * @param dias - D&iacute;as que ser&aacute;n sumados a la fecha inicial.
	 * @return fechaCalculada - <code>Date</code> con la fecha calculada una vez que se ha sumado 
	 * 							el intervalo de tiempo transcurrido. 
	 */
	public static Date sumarIntervaloFechas(Date fechaOriginal, int anios, int meses, int dias){
		Date fechaCalculada = null;
		if (fechaOriginal != null){
			Calendar cal = Calendar.getInstance();
			cal.setTime(fechaOriginal);
			cal.add(Calendar.YEAR, anios);
			cal.add(Calendar.MONTH, meses);
			cal.add(Calendar.DAY_OF_MONTH, dias);
			fechaCalculada = cal.getTime();
		}
		return fechaCalculada;
	}
	
	/**
	 * M&eacute;todo que lleva a cabo la resta de una fecha y un intervalo de tiempo transcurrido.
	 * @param fechaOriginal - fecha inicial a la cual se va a restar el intervalo de tiempo 
	 * 						  transcurrido.
	 * @param anios - A&ntilde;os que ser&aacute;n restados a la fecha inicial.
	 * @param meses - Meses que ser&aacute;n restados a la fecha inicial.
	 * @param dias - D&iacute;as que ser&aacute;n restados a la fecha inicial.
	 * @return fechaCalculada - <code>Date</code> con la fecha calculada una vez que se ha restado 
	 * 							el intervalo de tiempo transcurrido. 
	 */
	public static Date restarIntervaloFechas(Date fechaOriginal, int anios, int meses, int dias){
		return sumarIntervaloFechas(fechaOriginal,(anios*-1),(meses*-1), (dias*-1));
	}
    
	
	 /**
     * Obtiene la fecha en una representaci&oacute;n <code>String</code> con el formato
     * <b>dd-MM-yyyy</b>.
     * 
     * @param date
     *            Fecha a transformar.
     * @return Fecha en <code>String</code>.
     */
    public static String formatearParaFolio(Date date) {

        return date != null ? SDF_FECHA_FOLIO.format(date) : StringUtils.EMPTY;
    }

}
