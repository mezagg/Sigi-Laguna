/**
 * 
 */
package mx.gob.segob.nsjp.delegate.policiaministerial;

import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.documento.DocumentoDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.policiaministerial.LineaInvestigacionDTO;

/**
 * @author adrian
 *
 */
public interface LineaInvestigacionDelegate {
	
	/**
	 * Operaci�n que permite guardar / actualizar una L�nea de investigaci�n
	 * guardar: Id = 0 o NULL
	 * @param investigacionDTO
	 * @return
	 * @throws NSJPNegocioException
	 */
	public Long guardarLineaInvestigacion(LineaInvestigacionDTO investigacionDTO)throws NSJPNegocioException;
	
	/**
	 * Operaci�n que permite consultar una l�nea de investigaci�n por su ID
	 * @param investigacionDTO: ID
	 * @return
	 * @throws NSJPNegocioException
	 */
	public LineaInvestigacionDTO consultarLineaInvestigacion(LineaInvestigacionDTO investigacionDTO)throws NSJPNegocioException;
	
	/**
	 * Operaci�n que permite consultar las l�neas de investigaci�n que pertenezcan a un ExpedienteId
	 * @param ExpedienteDTO: ID
	 * @return
	 * @throws NSJPNegocioException
	 */
	public List<LineaInvestigacionDTO> consultarLineasInvestigacionXExpedienteId(ExpedienteDTO expedienteDTO)throws NSJPNegocioException;
	
	/**
	 * Operaci�n que permite generar el PDF de una L�nea de Investigaci�n y sus comentarios
	 * @param investigacionDTO
	 * @param esGuardado: True=Si se guarda en BD, False=No se guarda en BD
	 * @return
	 * @throws NSJPNegocioException
	 */
	public DocumentoDTO crearDocumentoLineaInvestigacion(LineaInvestigacionDTO investigacionDTO,Boolean esGuardado, Long area)throws NSJPNegocioException;

}
