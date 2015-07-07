/**
 * 
 */
package mx.gob.segob.nsjp.service.expediente.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.expediente.ConvenioDAO;
import mx.gob.segob.nsjp.dto.expediente.ConvenioDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.model.Convenio;
import mx.gob.segob.nsjp.service.expediente.ConsultarConvenioService;
import mx.gob.segob.nsjp.service.expediente.impl.transform.ConvenioTransformer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author adrian
 *
 */
@Service
@Transactional
public class ConsultarConvenioServiceImpl implements
		ConsultarConvenioService {
	public final static Logger logger = 
		Logger.getLogger(ConsultarConvenioServiceImpl.class);
	@Autowired
	private ConvenioDAO restaurativoDAO;

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.expediente.ConsultarAcuerdoRestaurativoService#consultarConveniosPorExpediente(mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO)
	 */
	@Override
	public List<ConvenioDTO> consultarConveniosPorExpediente(
			ExpedienteDTO expedienteDTO) throws NSJPNegocioException {
		if (logger.isDebugEnabled())
			logger.debug("/**** SERVICIO PARA CONSULTAR LOS ACUERDOS RESTAURATIVOS RELACIONADOS A UN NUMERO DE EXPEDIENTE ****/");
		
		/*Verificaci�n de par�metros*/
		if (expedienteDTO==null)
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		else if(expedienteDTO.getNumeroExpedienteId()==null)
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		
		/*Operaci�n*/
		List<Convenio> acuerdos=restaurativoDAO.consultarConveniosPorExpediente(expedienteDTO.getNumeroExpedienteId());
		List<ConvenioDTO> acuerdosDTO=new ArrayList<ConvenioDTO>();
		for (Convenio ar : acuerdos) {
			acuerdosDTO.add(ConvenioTransformer.transformarConvenioSimple(ar));
		}
		
		return acuerdosDTO;
	}

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.expediente.ConsultarAcuerdoRestaurativoService#consultarDetalleConvenio(mx.gob.segob.nsjp.dto.expediente.AcuerdoRestaurativoDTO)
	 */
	@Override
	public ConvenioDTO consultarDetalleConvenio(
			ConvenioDTO acuerdoRestaurativoDTO)
			throws NSJPNegocioException {
		if (logger.isDebugEnabled())
			logger.debug("/**** SERVICIO PARA CONSULTAR EL DETALLE DE UN CONVENIO  ****/");
		
		/*Verificaci�n de par�metros*/
		if (acuerdoRestaurativoDTO==null)
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		else if(acuerdoRestaurativoDTO.getConvenioID()==null)
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		
		/*Operaci�n*/
		Convenio convenio = restaurativoDAO.read(acuerdoRestaurativoDTO.getConvenioID());
		
		return ConvenioTransformer.transformarConvenio(convenio);
	}

}
