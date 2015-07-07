package mx.gob.segob.nsjp.service.exhorto;

import java.util.List;

import mx.gob.segob.nsjp.dto.exhorto.ExhortoDTO;

public interface ExhortoService {

	Long registrarExhorto(ExhortoDTO exhortoDTO);
	
	ExhortoDTO consultarExhorto(Long idExhorto);
	
	List <ExhortoDTO> consultarExhortos(String idExpediente);
	
	void updateExhorto(ExhortoDTO exhortoDT);
}
