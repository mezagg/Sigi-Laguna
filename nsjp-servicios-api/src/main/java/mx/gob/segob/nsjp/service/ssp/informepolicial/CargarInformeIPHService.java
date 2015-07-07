/**
 * 
 */
package mx.gob.segob.nsjp.service.ssp.informepolicial;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.documento.DocumentoDTO;
import mx.gob.segob.nsjp.dto.ssp.informepolicial.InformePolicialHomologadoDTO;

/**
 * @author adrian
 *
 */
public interface CargarInformeIPHService {

	/**
	 * Operaci�n que p�rmite crear el documento de IPH
	 * @param homologadoDTO: idHomologado, expediente: idExpediente
	 * @return
	 * @throws NSJPNegocioException
	 */
	DocumentoDTO cargarInformeIPH(InformePolicialHomologadoDTO homologadoDTO)throws NSJPNegocioException;

}
