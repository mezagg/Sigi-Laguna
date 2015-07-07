/**
 * Nombre del Programa 		: MandamientoPersonaDocumentoDAO.java
 * Autor 					: AlejandroGA
 * Compania 				: Ultrasist
 * Proyecto 				: NSJP 								Fecha: 06/06/2013
 * Marca de cambio 			: N/A
 * Descripcion General 		: Clase para
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
package mx.gob.segob.nsjp.dao.mandamiento;

import java.util.List;

import mx.gob.segob.nsjp.dao.base.GenericDao;
import mx.gob.segob.nsjp.model.MandamientoPersona;
import mx.gob.segob.nsjp.model.MandamientoPersonaDocumento;
import mx.gob.segob.nsjp.model.MandamientoPersonaDocumentoId;

/**
 * @author AlejandroGA
 * @version 1.0
 */
public interface MandamientoPersonaDocumentoDAO extends GenericDao<MandamientoPersonaDocumento, MandamientoPersonaDocumentoId> {
	

	/**
	 * M&eacute;todo para consultar las relaciones mandamientos persona
	 * asociadas a un documento de cambio de estatus
	 * 
	 * @param documentoId
	 *            , id del documento de cambio de estatus
	 * @return lista de mandamientos persona asociados al mandamiento persona
	 *         documento
	 */
	List<MandamientoPersona> consultarMandamientosPersonaPorDocumentoId(
			Long documentoId);
}
