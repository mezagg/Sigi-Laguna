/**
* Nombre del Programa 		: ConsultarObjetoEstudioService.java
* Autor 					: EdgarTE
* Compania 					: Ultrasist
* Proyecto 					: NSJP 							Fecha: 11 Sep 2012
* Marca de cambio 			: N/A
* Descripcion General 		: Describir el objetivo de la clase de manera breve
* Programa Dependiente 		: N/A
* Programa Subsecuente 		: N/A
* Cond. de ejecucion 		: N/A
* Dias de ejecucion 		: N/A 							Horario: N/A
*                              MODIFICACIONES
*------------------------------------------------------------------------------
* Autor 					: N/A
* Compania 					: N/A
* Proyecto 					: N/A 							Fecha: N/A
* Modificacion 				: N/A
*------------------------------------------------------------------------------
*/
package mx.gob.segob.nsjp.service.objeto;

import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.objeto.ObjetoEstudioDTO;

/**
 * Describir el objetivo de la clase con punto al final.
 * @version 1.0
 * @author EdgarTE
 *
 */
public interface ConsultarObjetoEstudioService {

	/**
	 * M&eacute;todo que lleva a cabo la consulta de los distintos estudios periciales
	 * que se encuentran asociados con un tipo de objeto.
	 * @param TipoObj - El tipo de objeto a partir del cual se consultar&aacute;n los 
	 * 					estudios periciales asociados.
	 * @return List<ObjetoEstudio> - Lista con la relaci&oacute;n de objetos y estudios 
	 * 								 asociados al tipo de objeto consultado.
	 */
	public List<ObjetoEstudioDTO> consultarEstudiosPorTipoObjeto (ValorDTO tipoObj) throws NSJPNegocioException;
}
