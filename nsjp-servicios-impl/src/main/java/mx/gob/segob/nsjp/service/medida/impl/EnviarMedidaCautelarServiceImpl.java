package mx.gob.segob.nsjp.service.medida.impl;

import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.enums.institucion.Instituciones;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.documento.MedidaCautelarDAO;
import mx.gob.segob.nsjp.dto.documento.MedidaCautelarDTO;
import mx.gob.segob.nsjp.model.MedidaCautelar;
import mx.gob.segob.nsjp.service.infra.ClienteGeneralService;
import mx.gob.segob.nsjp.service.medida.EnviarMedidaCautelarService;
import mx.gob.segob.nsjp.service.medidascautelares.impl.transform.MedidaCautelarTransformer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EnviarMedidaCautelarServiceImpl implements
		EnviarMedidaCautelarService {

	@Autowired
	private ClienteGeneralService clienteGeneralService;
	@Autowired
	private MedidaCautelarDAO medidaCautelarDAO;
	
	@Override
	public MedidaCautelarDTO enviarMedidaCautelarInstitucion(Long idMedidaCautelar, Instituciones institucionEnviar)
			throws NSJPNegocioException {
		
		if(idMedidaCautelar == null || idMedidaCautelar <= 0L){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		
		MedidaCautelar medidaBD = medidaCautelarDAO.read(idMedidaCautelar);
		
		if(medidaBD == null){
			throw new NSJPNegocioException(CodigoError.INFORMACION_PARAMETROS_ERRONEA);
		}
		
		MedidaCautelarDTO medidaCautelarDTO = MedidaCautelarTransformer.transformarMedidaCautelar(medidaBD);
		
		clienteGeneralService.enviarMedidaCautelarInstitucion(medidaCautelarDTO, institucionEnviar);
		
		return medidaCautelarDTO;
	}

	public MedidaCautelarDTO actualizarEstatusMedidaCautelarInstitucion(Long idMedidaCautelar, Instituciones institucionEnviar)
			throws NSJPNegocioException {
		MedidaCautelar medida = medidaCautelarDAO.read(idMedidaCautelar);
		MedidaCautelarDTO mdTO = MedidaCautelarTransformer.transformarMedidaCautelar(medida);
		clienteGeneralService.actualizarEstatusMedidaCautelarInstitucion(medida, institucionEnviar);
		return mdTO;
	}
	
}
