package mx.gob.segob.nsjp.service.audiencia.impl;

import mx.gob.segob.nsjp.dao.catalogo.CatDistritoDAO;
import mx.gob.segob.nsjp.dto.catalogo.CatDistritoDTO;
import mx.gob.segob.nsjp.model.CatDistrito;
import mx.gob.segob.nsjp.service.audiencia.BuscaDistritosService;
import mx.gob.segob.nsjp.service.catalogo.impl.transform.CatDistritoTransformer;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;



@Service
public class BuscaDistritosServiceImpl implements BuscaDistritosService{
	
	@Autowired
	private CatDistritoDAO catDistritoDAO;
	@Override
	public CatDistritoDTO buscaDistritosPorDiscriminante(Long discriminante){
		CatDistrito distrito=catDistritoDAO.consultarDistritoPorDiscriminante(discriminante);
		return CatDistritoTransformer.transformarDistritoCompleto(distrito); 
	}
	
}
