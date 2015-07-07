/**
 * 
 */
package mx.gob.segob.nsjp.service.expediente;

import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.expediente.ConvenioDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;

/**
 * @author adrian
 *
 */
public interface ConsultarConvenioService {

	/**
	 * Operaci�n que realiza la funcionalidad de consultar los convenios de conciliaci�n/mediaci�n asociados al expediente.
	 * @param expedienteDTO: idExpediente, idNumeroExpediente
	 * @return
	 * @throws NSJPNegocioException
	 */
	List<ConvenioDTO> consultarConveniosPorExpediente(
			ExpedienteDTO expedienteDTO)throws NSJPNegocioException;

	/**
	 * Operaci�n que realiza la funcionalidad de consultar el detalle de un convenio 
	 * @param acuerdoRestaurativoDTO: idAcuerdo
	 * @return
	 * @throws NSJPNegocioException
	 */
	ConvenioDTO consultarDetalleConvenio(
			ConvenioDTO acuerdoRestaurativoDTO)throws NSJPNegocioException;

}
