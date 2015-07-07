package mx.gob.segob.nsjp.service.hecho.impl;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.hecho.ConclusionHechoDAO;
import mx.gob.segob.nsjp.dto.hecho.ConclusionHechoDTO;
import mx.gob.segob.nsjp.model.ConclusionHecho;
import mx.gob.segob.nsjp.service.hecho.ConclusionHechoService;
import mx.gob.segob.nsjp.service.hecho.impl.transform.ConclusionHechoTransformer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ConclusionHechoServiceImpl implements ConclusionHechoService {
	
	public final static Logger logger = Logger
			.getLogger(ConclusionHechoServiceImpl.class);

	@Autowired
	public ConclusionHechoDAO conclusionHechoDAO;
	
	@Override
	public void ingresarModificarConclusionHecho(
			ConclusionHechoDTO conclusionDto) throws NSJPNegocioException{
		
		try{
			
			ConclusionHecho conclusion = conclusionHechoDAO.read(conclusionDto.getHecho().getHechoId());
			
			ConclusionHecho conclusionHecho = ConclusionHechoTransformer.transformarConclusion(conclusionDto);
			if(conclusion != null){
				// Es modificacion, UPDATE
				if(conclusionHecho.getFechaConclusion() != null)
					conclusion.setFechaConclusion(conclusionHecho.getFechaConclusion());
				
				if(conclusionHecho.getTipoConclusion() != null)
					conclusion.setTipoConclusion(conclusionHecho.getTipoConclusion());
				
				if(conclusionHecho.getTipoSubConclusion() != null)
					conclusion.setTipoSubConclusion(conclusionHecho.getTipoSubConclusion());
				
				conclusionHechoDAO.merge(conclusion);
			}else{
				// Es insercion, INSERT
				conclusionHechoDAO.create(conclusionHecho);
			}
			
		}catch (Exception e){
			logger.error("Error al tratar de insertar/modificar la conclusion del hecho: ConclusionHechoServiceImpl -  ingresarModificarConclusionHecho ", e);
		}
		
	}

	@Override
	public ConclusionHechoDTO consultarById(Long idHecho)
			throws NSJPNegocioException {
		
		ConclusionHechoDTO dto = null;
		
		try{
			
			ConclusionHecho conclusion = conclusionHechoDAO.read(idHecho);
			
			if(conclusion != null)
				dto = ConclusionHechoTransformer.transformarConclusion(conclusion);
			
		}catch(Exception e){
			logger.error("Error al tratar de consultar la conclusion del hecho: ConclusionHechoServiceImpl - consultarById ", e);
		}
		return dto;
	}

}
