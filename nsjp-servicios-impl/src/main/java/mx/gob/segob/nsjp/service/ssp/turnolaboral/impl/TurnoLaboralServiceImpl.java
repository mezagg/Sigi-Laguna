package mx.gob.segob.nsjp.service.ssp.turnolaboral.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.segob.nsjp.dao.ssp.turnolaboral.TurnoLaboralDAO;
import mx.gob.segob.nsjp.dto.ssp.turnolaboral.TurnoLaboralDTO;
import mx.gob.segob.nsjp.model.ssp.TurnoLaboral;
import mx.gob.segob.nsjp.service.ssp.turnolaboral.TurnoLaboralService;
import mx.gob.segob.nsjp.service.ssp.turnolaboral.impl.transformer.TurnoLaboralTransformer;

@Service
@Transactional
public class TurnoLaboralServiceImpl implements TurnoLaboralService {
	@Autowired
	private TurnoLaboralDAO turnoLaboralDAO;
	

	@Override
	public List<TurnoLaboralDTO> ConsultarCatalogoTurnoLaboral() {		
		List<TurnoLaboral> catList = turnoLaboralDAO.ConsultarCatalogoTurnoLaboral();
		List<TurnoLaboralDTO> result = new ArrayList<TurnoLaboralDTO>();
		for(TurnoLaboral tl: catList)
		{
			result.add(TurnoLaboralTransformer.transormarTurnoLaboral(tl));
		}
		
		return result;
	}

}
