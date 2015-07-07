package mx.gob.segob.nsjp.service.hecho;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.hecho.ConclusionHechoDTO;

public interface ConclusionHechoService {
	
	/**
	 * Metodo para insertar o actualizar una conclusion de un hecho
	 * @param 
	 * @return ConclusionHechoDTO
	 */
	public void ingresarModificarConclusionHecho(ConclusionHechoDTO conclusionDto)
			throws NSJPNegocioException;
	
	/**
	 * Metodo para traer la conclusion del hecho en base al Id enviado
	 * @param idHecho
	 * @return
	 * @throws NSJPNegocioException
	 */
	public ConclusionHechoDTO consultarById(Long idHecho) 
			throws NSJPNegocioException;

}
