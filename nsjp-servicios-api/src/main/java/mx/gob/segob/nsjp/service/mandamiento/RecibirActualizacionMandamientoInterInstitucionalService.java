/**
 * Nombre del Programa 		: RecibirActualizacionMandamientoInterInstitucionalService.java
 * Autor 					: AlejandroGA
 * Compania 				: Ultrasist
 * Proyecto 				: nsjp-modelo 					Fecha: 09/07/2013
 * Marca de cambio 			: N/A
 * Descripcion General 		: Clase Service para lo relacionado con la actualizacion del mandamiento judicial
 * Programa Dependiente 	: N/A
 * Programa Subsecuente 	: N/A
 * Cond. de ejecucion 		: N/A
 * Dias de ejecucion 		: N/A 								Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor 					: N/A
 * Compania 				: N/A
 * Proyecto 				: N/A 								Fecha: N/A
 * Modificacion 			: N/A
 *------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.service.mandamiento;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.documento.DocumentoWSDTO;
import mx.gob.segob.nsjp.dto.documento.MandamientoWSDTO;

/**
 * @author AlejandroGA
 * @version 1.0
 */
public interface RecibirActualizacionMandamientoInterInstitucionalService {

	/**
	 * M&eacute;todo para la recepci&oacute;n de actualizaciones de mandamiento
	 * judiciales obtiene el mandamiento por folio interinstitucional as&iacute; como
	 * los mandamientos persona y les actualiza el estatus seg&uacute;n lo recibido de
	 * la otra instituci&oacute;n
	 * 
	 * @param mandamientoWSDTO, mandamiento a actualizar
	 * @param documentoWSDTO,	documento de actualizaci&oacute;n
	 * @throws NSJPNegocioException, en caso de excepcion
	 */
	void recibirActualizacionMandamientoInterInstitucional(
			MandamientoWSDTO mandamientoWSDTO, DocumentoWSDTO documentoWSDTO)
			throws NSJPNegocioException;

}
