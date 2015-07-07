package mx.gob.segob.nsjp.delegate.exhorto.impl;

import java.util.List;

import mx.gob.segob.nsjp.delegate.exhorto.ExhortoDelegate;
import mx.gob.segob.nsjp.dto.exhorto.ExhortoDTO;
import mx.gob.segob.nsjp.service.exhorto.ExhortoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("exhortoDelegate")
public class ExhortoDelegateImpl implements ExhortoDelegate {
	
	@Autowired
	private ExhortoService exhortoService;

	@Override
	public Long registrarExhorto(ExhortoDTO exhorto) {

		Long exhortoId = exhortoService.registrarExhorto(exhorto);
		
		return exhortoId;
	}

	@Override
	public ExhortoDTO consultarExhorto(Long idExhorto) {

		ExhortoDTO exhortoDTO = exhortoService.consultarExhorto(idExhorto);
		
		return exhortoDTO;
	}

	@Override
	public List<ExhortoDTO> consultarExhortos(String idExpediente) {

		List<ExhortoDTO> listaExhorto = exhortoService.consultarExhortos(idExpediente);
		
		return listaExhorto;
	}

	@Override
	public void updateExhorto(ExhortoDTO exhortoDT) {
		exhortoService.updateExhorto(exhortoDT);
	}

}
