/**
 * 
 */
package mx.gob.segob.nsjp.service.documento;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.documento.DocumentoDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;

/**
 * @author adrian
 *
 */
public interface ConsultarTeoriaDelCasoService {

	/**
	 * Operaci�n que realiza la consulta de las teor�a de caso por expedientes
	 * @param expedienteDTO: idExpediente
	 * @return
	 * @throws NSJPNegocioException
	 */
	DocumentoDTO consultarTeoriasDelCasoXExpediente(
			ExpedienteDTO expedienteDTO)throws NSJPNegocioException;

}
