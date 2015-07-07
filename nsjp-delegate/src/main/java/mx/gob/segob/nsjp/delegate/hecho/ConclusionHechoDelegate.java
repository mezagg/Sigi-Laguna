package mx.gob.segob.nsjp.delegate.hecho;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.conclusion.ConclusionOrdenAprensionDTO;
import mx.gob.segob.nsjp.dto.hecho.ConclusionHechoDTO;
import mx.gob.segob.nsjp.dto.hecho.ConclusionNumeroExpedienteDTO;

/**
 * Contrato del Delegate para los servicios relacionados con ConclusionHecho.
 * @version 1.0
 */
public interface ConclusionHechoDelegate {
	
	/**
	 * Metodo para insertar o actualizar una conclusion de un hecho
	 * @param 
	 * @return ConclusionHechoDTO
	 */
	public void ingresarModificarConclusionHecho(ConclusionHechoDTO conclusion) 
			throws NSJPNegocioException;
	
	/**
	 * Metodo para traer la conclusion del hecho en base al Id enviado
	 * @param idHecho
	 * @return
	 * @throws NSJPNegocioException
	 */
	public ConclusionHechoDTO consultarById(Long idHecho) 
			throws NSJPNegocioException;
	
	public Boolean guardarConclusion(ConclusionNumeroExpedienteDTO conclusion)throws NSJPNegocioException;
	
	public ConclusionNumeroExpedienteDTO consultarConclusionNumeroExpe(Long idNumeroExpe)throws NSJPNegocioException;
	
	public Long guardarConclusionOrdenAprensionDTO(ConclusionOrdenAprensionDTO conclusionOrdenAprensionDTO)throws NSJPNegocioException;

}
