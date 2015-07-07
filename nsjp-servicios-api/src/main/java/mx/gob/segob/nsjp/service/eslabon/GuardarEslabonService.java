/**
 * 
 */
package mx.gob.segob.nsjp.service.eslabon;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.evidencia.EslabonDTO;
import mx.gob.segob.nsjp.dto.evidencia.EvidenciaDTO;

/**
 * @author adrian
 *
 */
public interface GuardarEslabonService {

	/**
	 * Operaci�n que realiza la funcionalidad de guardar(actualizar) el eslab�n con la asociaci�n a la evidencia que le corresponde
	 * @param evidenciaDTO: idEvidencia
	 * @param eslabonDTO: Objeto
	 * @return
	 * @throws NSJPNegocioException
	 */
	Long guardarEslabon(EvidenciaDTO evidenciaDTO, EslabonDTO eslabonDTO)throws NSJPNegocioException;

	/**
	 * Operaci�n que permite asociar un documento al eslab�n
	 * @param eslabonDTO: idEslabon y idDocumento
	 * @return
	 * @throws NSJPNegocioException
	 */
	Long asociarDocumentoAEslabon(EslabonDTO eslabonDTO)throws NSJPNegocioException;

	/**
	 * Guarda un eslab�n para una evidencia cuando se recibe la Carpeta de Investigacion en defensoria
	 * @param evidenciaDTO
	 * @param eslabonDTO
	 * @throws NSJPNegocioException
	 */
	void guardarEslabonCarpetaInvestigacion(EvidenciaDTO evidenciaDTO,
			EslabonDTO eslabonDTO) throws NSJPNegocioException;

}
