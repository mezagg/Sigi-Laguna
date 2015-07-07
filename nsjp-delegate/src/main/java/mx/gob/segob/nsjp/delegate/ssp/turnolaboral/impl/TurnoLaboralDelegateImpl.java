package mx.gob.segob.nsjp.delegate.ssp.turnolaboral.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.delegate.ssp.turnolaboral.TurnoLaboralDelegate;
import mx.gob.segob.nsjp.dto.ssp.turnolaboral.TurnoLaboralDTO;
import mx.gob.segob.nsjp.service.ssp.turnolaboral.TurnoLaboralService;

@Service("turnoLaboralDelegate")
@Transactional
public class TurnoLaboralDelegateImpl implements TurnoLaboralDelegate {
	
	@Autowired
	private TurnoLaboralService turnoLaboralService;
	
	@Override
	public List<TurnoLaboralDTO> consultarCatalogoTurnoLaboral() throws NSJPNegocioException {
		return turnoLaboralService.ConsultarCatalogoTurnoLaboral();
	}

}
