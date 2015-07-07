/**
 * 
 */
package mx.gob.segob.nsjp.service.documento.impl;

import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.enums.institucion.Instituciones;
import mx.gob.segob.nsjp.comun.enums.solicitud.EstatusSolicitud;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.documento.DocumentoDAO;
import mx.gob.segob.nsjp.dao.institucion.ConfInstitucionDAO;
import mx.gob.segob.nsjp.dao.solicitud.SolicitudDefensorDAO;
import mx.gob.segob.nsjp.dto.documento.DocumentoDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudDefensorDTO;
import mx.gob.segob.nsjp.model.Documento;
import mx.gob.segob.nsjp.model.SolicitudDefensor;
import mx.gob.segob.nsjp.model.Valor;
import mx.gob.segob.nsjp.service.documento.EnviarAcuseDeReciboDeSolicitudDeDefensorService;
import mx.gob.segob.nsjp.service.documento.impl.tranform.DocumentoTransformer;
import mx.gob.segob.nsjp.service.infra.ClienteGeneralService;
import mx.gob.segob.nsjp.service.solicitud.impl.transform.ConfInstitucionTransformer;
import mx.gob.segob.nsjp.service.solicitud.impl.transform.SolicitudDefensorTransformer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author AlejandroGA
 *
 */

@Service
public class EnviarAcuseDeReciboDeSolicitudDeDefensorServiceImpl implements
		EnviarAcuseDeReciboDeSolicitudDeDefensorService {
	
	public static final Logger logger = Logger.getLogger(EnviarAcuseDeReciboDeSolicitudDeDefensorServiceImpl.class);

	@Autowired
	private SolicitudDefensorDAO solicitudDefensorDAO;
	@Autowired
	private DocumentoDAO documentoDAO;
	@Autowired
	private ClienteGeneralService clienteGeneralService;
	@Autowired
	private ConfInstitucionDAO confInstitucionDAO;
	
	@Override
	public void enviarAcuseDeReciboDeSolicitudDeDefensorService(
			SolicitudDTO solicitudDto, DocumentoDTO documentoDto)
			throws NSJPNegocioException {
		
		logger.info("SERVICIO PARA ENVIAR UN ACUSE DE RECIBO DE SOLICITUD DE DEFENSOR");
		
		if (solicitudDto == null || documentoDto == null
				|| solicitudDto.getDocumentoId() == null
				|| solicitudDto.getDocumentoId() <= 0L
				|| documentoDto.getDocumentoId() == null
				|| documentoDto.getDocumentoId() <= 0L) {
			
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		
		DocumentoDTO documentoDTORespuesta = null;
		
		if(solicitudDto instanceof SolicitudDefensorDTO ){
			
			SolicitudDefensor solicitudDefensor = solicitudDefensorDAO.read(solicitudDto.getDocumentoId());
			Documento documento =  documentoDAO.read(documentoDto.getDocumentoId());
			
			if(solicitudDefensor == null || documento == null){
				throw new NSJPNegocioException(CodigoError.INFORMACION_PARAMETROS_ERRONEA);
			}
			
			//El unico dato que necesitamos de la solicitud es el folio y el defensor
			SolicitudDefensorDTO solicitudDefensorToSend = SolicitudDefensorTransformer.transformarSolicitudDefensoria(solicitudDefensor);
			
			DocumentoDTO documentoToSend = DocumentoTransformer.transformarDocumento(documento);
			
			// Agregamos la Institucion al defensor para que se registre en funcionario externo
			if (solicitudDefensorToSend.getAvisoDesignacion() != null
					&& solicitudDefensorToSend.getAvisoDesignacion()
							.getFuncionario() != null) {

				solicitudDefensorToSend
						.getAvisoDesignacion()
						.getFuncionario()
						.setInstitucion(
								ConfInstitucionTransformer
										.transformarInstitucion(confInstitucionDAO
												.consultarInsitucionActual()));
			}
			
			if(solicitudDefensor.getConfInstitucion().getConfInstitucionId().equals(Instituciones.PGJ.getValorId())){
				documentoDTORespuesta =  clienteGeneralService.enviarAcuseDeReciboDeSolicitudDeDefensor(solicitudDefensorToSend, documentoToSend, Instituciones.PGJ);				
			}
			else if(solicitudDefensor.getConfInstitucion().getConfInstitucionId().equals(Instituciones.PJ.getValorId())){
				documentoDTORespuesta = clienteGeneralService.enviarAcuseDeReciboDeSolicitudDeDefensor(solicitudDefensorToSend, documentoToSend, Instituciones.PJ);				
			}
			else if(solicitudDefensor.getConfInstitucion().getConfInstitucionId().equals(Instituciones.DEF.getValorId())){
				/**
				 * No se env&iacute;a la solicitud solo se cierra la solicitud
				 */
				solicitudDefensor.setEstatus(new Valor(EstatusSolicitud.CERRADA.getValorId()));
				solicitudDefensorDAO.update(solicitudDefensor);
			}
			
			if(documentoDTORespuesta != null && documentoDTORespuesta.getDocumentoId() != null){
				logger.info("ENVIO DE DOCUMENTO CORRECTO!!!");
				logger.info("EL DOCUMENTO SE REGISTRO CON EL ID = "+documentoDTORespuesta.getDocumentoId());
				
				//Actualizamos el estatus de la solicitud
				solicitudDefensor.setEstatus(new Valor(EstatusSolicitud.CERRADA.getValorId()));
				solicitudDefensorDAO.update(solicitudDefensor);
			}
			
		}
		
		
		//Aqui se puede agregar una solicitud de audiencia por ejemplo
	}

}
