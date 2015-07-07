package mx.gob.segob.nsjp.service.conclusion.impl;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.expediente.ConclusionOrdenAprensionDAO;
import mx.gob.segob.nsjp.dto.conclusion.ConclusionOrdenAprensionDTO;
import mx.gob.segob.nsjp.model.ConclusionOrdenAprension;
import mx.gob.segob.nsjp.service.conclusion.ConclusionOrdenAprensionService;
import mx.gob.segob.nsjp.service.expediente.impl.transform.ConclusionOrdenAprensionTransformer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ConclusionOrdenAprensionServiceImpl implements
		ConclusionOrdenAprensionService {

	public final static Logger logger = Logger
			.getLogger(ConclusionOrdenAprensionServiceImpl.class);

	@Autowired
	private ConclusionOrdenAprensionDAO conclusionOrdenAprensionDAO;

	@Override
	public Long guardarConclusionOrdenAprensionDTO(
			ConclusionOrdenAprensionDTO conclusionOrdenAprensionDTO)
			throws NSJPNegocioException {
		ConclusionOrdenAprension conclusionOrdenAprension = ConclusionOrdenAprensionTransformer
				.transformarConclusionOrdenAprensionDTO(conclusionOrdenAprensionDTO);
		Long conclusionOrdenAprensionID = conclusionOrdenAprensionDAO
				.create(conclusionOrdenAprension);
		return conclusionOrdenAprensionID;
	}

}
