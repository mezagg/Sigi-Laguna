/**
 * Nombre del Programa : GenerarFolioDelitoPersonaService.java
 * Autor               : AlejandroGA
 * Compania            : Ultrasist
 * Proyecto            : NSJP                    	  Fecha: 18/04/2013
 * Marca de cambio     : N/A
 * Descripcion General : Generar un folio de la relaci&oacute; delito-persona
 * Programa Dependiente: N/A
 * Programa Subsecuente: N/A
 * Cond. de ejecucion  : N/A
 * Dias de ejecucion   : N/A                           Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor               : N/A
 * Compania            : N/A
 * Proyecto            : N/A                           Fecha: N/A
 * Modificacion        : N/A
 *------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.service.delito;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;

/**
 * @author AlejandroGA
 * @version 1.0
 */
public interface GenerarFolioDelitoPersonaService {
	
	/**
	 * M&eacute;todo para generar un folio en la relaci&oacute;n delito-persona
	 * Con las siguentes regla:
	 * 
	 * cMonograma Anio - Consecutivo
	 * 
	 * 
	 * Ejemplo:
	 *  	FG2013-1, FG2013-2, ……………, FG2013-115, ……….., FG2014-1………
	 *  
	 *  o bien:
	 *  
	 *      PJ2013-1, PJ2013-2, ……………, PJ2013-115, ……….., PJ2014-1………
	 * 
	 * donde:
	 * 
	 * cMonograma	- Corresponde al monograma de la instituci&oacute;n actual.
	 * Anio			- A&ntilde;o en curso
	 * "-"			- Separador
	 * Consecutivo	- consecutivo auto incremental en 1 (reiniciando el contador cada a&ntilde;o)
	 * 
	 * @return String - folio de la relaci&oacute;n delito-persona
	 * @throws NSJPNegocioException , en caso de alguna excepcion
	 */
	public String generarFolioDelitoPersona()throws NSJPNegocioException;

}
