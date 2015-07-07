package mx.gob.segob.nsjp.service.conclusion;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.conclusion.ConclusionOrdenAprensionDTO;


public interface ConclusionOrdenAprensionService {

	public Long guardarConclusionOrdenAprensionDTO(ConclusionOrdenAprensionDTO conclusionOrdenAprensionDTO)throws NSJPNegocioException;

}
