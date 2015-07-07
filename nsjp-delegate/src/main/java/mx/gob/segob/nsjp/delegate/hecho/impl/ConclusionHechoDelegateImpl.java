package mx.gob.segob.nsjp.delegate.hecho.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.delegate.hecho.ConclusionHechoDelegate;
import mx.gob.segob.nsjp.dto.conclusion.ConclusionOrdenAprensionDTO;
import mx.gob.segob.nsjp.dto.hecho.ConclusionHechoDTO;
import mx.gob.segob.nsjp.dto.hecho.ConclusionNumeroExpedienteDTO;
import mx.gob.segob.nsjp.service.conclusion.ConclusionOrdenAprensionService;
import mx.gob.segob.nsjp.service.expediente.ConclusionNumeroExpedienteService;
import mx.gob.segob.nsjp.service.hecho.ConclusionHechoService;

@Service("conclusionHechoDelegate")
public class ConclusionHechoDelegateImpl implements ConclusionHechoDelegate {
	
	@Autowired
	public ConclusionHechoService conclusionHechoService;
	@Autowired
	private ConclusionNumeroExpedienteService conclusionNumeroExpedienteService;
	@Autowired
	private ConclusionOrdenAprensionService conclusionOrdenAprensionService;

	@Override
	@Deprecated
	public void ingresarModificarConclusionHecho(
			ConclusionHechoDTO conclusion) throws NSJPNegocioException{
		conclusionHechoService.ingresarModificarConclusionHecho(conclusion);
	}

	@Override
	@Deprecated
	public ConclusionHechoDTO consultarById(Long idHecho)
			throws NSJPNegocioException {
		return conclusionHechoService.consultarById(idHecho);
	}
	@Override
	public Boolean guardarConclusion(ConclusionNumeroExpedienteDTO conclusion)throws NSJPNegocioException{
		return conclusionNumeroExpedienteService.guardarConclusion(conclusion);
	}
	@Override
	public ConclusionNumeroExpedienteDTO consultarConclusionNumeroExpe(Long idNumeroExpe)throws NSJPNegocioException{
		return conclusionNumeroExpedienteService.buscarConclicionNumeroExpe(idNumeroExpe);
	}

	@Override
	public Long guardarConclusionOrdenAprensionDTO(
			ConclusionOrdenAprensionDTO conclusionOrdenAprensionDTO)
			throws NSJPNegocioException {
		return conclusionOrdenAprensionService.guardarConclusionOrdenAprensionDTO(conclusionOrdenAprensionDTO);
	}

}
