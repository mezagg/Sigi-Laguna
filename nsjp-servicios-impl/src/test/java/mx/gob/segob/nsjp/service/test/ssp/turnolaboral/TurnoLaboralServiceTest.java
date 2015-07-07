package mx.gob.segob.nsjp.service.test.ssp.turnolaboral;

import java.util.List;

import mx.gob.segob.nsjp.dto.ssp.turnolaboral.TurnoLaboralDTO;
import mx.gob.segob.nsjp.service.ssp.turnolaboral.TurnoLaboralService;
import mx.gob.segob.nsjp.service.test.base.BaseTestServicios;

public class TurnoLaboralServiceTest 
		extends BaseTestServicios<TurnoLaboralService> {
	
	public void testConsultarCatalogo()
	{
		List<TurnoLaboralDTO> list = service.ConsultarCatalogoTurnoLaboral();
		for(TurnoLaboralDTO tl: list)
		{
			logger.debug("------CONSULTA CATALOGO DE TURNO LABORAL-----");
			logger.debug("ID: " + tl.getTurnoLaboralId());
			logger.debug("Nombre de turno: " + tl.getNombreTurno());
			logger.debug("Hora Inicio turno: " + tl.getHoraInicioTurno());
			logger.debug("Hora fin turno: " + tl.getHoraFinTurno());
			logger.debug("---------------------------------------------");
		}
	}

}
