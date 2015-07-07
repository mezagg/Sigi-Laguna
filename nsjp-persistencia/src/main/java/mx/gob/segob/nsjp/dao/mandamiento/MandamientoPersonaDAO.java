/**
 * Nombre del Programa 		: MandamientoPersonaDAO.java
 * Autor 					: AlejandroGA
 * Compania 				: Ultrasist
 * Proyecto 				: NSJP 								Fecha: 05/06/2013
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
import mx.gob.segob.nsjp.dto.mandamiento.FiltroMandamientoPersonaDTO;
import mx.gob.segob.nsjp.model.MandamientoPersona;

/**
 * @author AlejandroGA
 * @version 1.0
 */
public interface MandamientoPersonaDAO extends
		GenericDao<MandamientoPersona, Long> {

	/**
	 * M&eacute;todo que consulta el &uacute;ltimo consecutivo del folio
	 * mandamiento persona, en el anio actual.
	 * 
	 * @return el consecutivo del anio (si existe), en caso contrario devuelve
	 *         null
	 */
	public Long obtenerUltimoFolioMandamientoPersona();
	
	
	/**
	 * M&eacute;todo que consulta los mandamientos - persona por filtro
	 * 
	 * @param filtroMandamientoPersonaDTO
	 *            , parametro con los posibles campos para realizar la
	 *            b&uacute;squeda
	 * @return Lista de mandamientos-persona que superaron los filtros
	 */
	public List<MandamientoPersona> consultarMandamientosPersonaPorFiltro(
			FiltroMandamientoPersonaDTO filtroMandamientoPersonaDTO);
}
