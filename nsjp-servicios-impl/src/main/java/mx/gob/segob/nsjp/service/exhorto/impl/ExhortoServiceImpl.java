package mx.gob.segob.nsjp.service.exhorto.impl;

import java.util.List;

import mx.gob.segob.nsjp.dao.exhorto.ExhortoDAO;
import mx.gob.segob.nsjp.dto.exhorto.ExhortoDTO;
import mx.gob.segob.nsjp.model.Exhorto;
import mx.gob.segob.nsjp.service.exhorto.ExhortoService;
import mx.gob.segob.nsjp.service.exhorto.impl.transform.ExhortoTransformer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ExhortoServiceImpl implements ExhortoService {
	

	public final static Logger logger = 
		Logger.getLogger(ExhortoServiceImpl.class);
	
	@Autowired
	private ExhortoDAO exhortoDAO;

	@Override
	public Long registrarExhorto(ExhortoDTO exhortoDTO) {
		
		Exhorto exhorto = ExhortoTransformer.transformarExhortoDTOToExhorto(exhortoDTO);

//		Long exhortoId = exhortoDAO.registrarExhorto(exhorto);
		
		Long exhortoId = exhortoDAO.create(exhorto);
		
		return exhortoId;
	}

	@Override
	public ExhortoDTO consultarExhorto(Long idExhorto) {
		
		Exhorto exhorto = exhortoDAO.consultarExhorto(idExhorto);
		
		ExhortoDTO exhortoDTO = ExhortoTransformer.transformarExhortoToExhortoDTO(exhorto);
		
		return exhortoDTO;
	}

	@Override
	public List<ExhortoDTO> consultarExhortos(String idExpediente) {

		List<Exhorto> exhorto = exhortoDAO.consultarExhortos(idExpediente);
		
		List<ExhortoDTO> exhortoLista = ExhortoTransformer.transformarExhortoToExhortoDTOList(exhorto);
		
		return exhortoLista;
		
	}

	@Override
	public void updateExhorto(ExhortoDTO exhortoDTO) {
		Exhorto exhorto = ExhortoTransformer.transformarExhortoDTOToExhorto(exhortoDTO);
		exhortoDAO.saveOrUpdate(exhorto);
		
	}

}
