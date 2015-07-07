/**
* Nombre del Programa : EnviarSolicitudServiceImp.java
* Autor                            : AntonioBV
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 03/09/2012
* Marca de cambio        : N/A
* Descripcion General    : Describir el objetivo de la clase de manera breve
* Programa Dependiente  :N/A
* Programa Subsecuente :N/A
* Cond. de ejecucion        :N/A
* Dias de ejecucion          :N/A                             Horario: N/A
*                              MODIFICACIONES
*------------------------------------------------------------------------------
* Autor                       :N/A
* Compania               :N/A
* Proyecto                 :N/A                                 Fecha: N/A
* Modificacion           :N/A
*------------------------------------------------------------------------------
*/
package mx.gob.segob.nsjp.service.sentencia.impl.transform;

import java.util.List;

import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.enums.institucion.Instituciones;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.archivo.ArchivoDigitalDAO;
import mx.gob.segob.nsjp.dao.expediente.ActividadDAO;
import mx.gob.segob.nsjp.dao.funcionarioexterno.FuncionarioExternoDAO;
import mx.gob.segob.nsjp.dao.institucion.ConfInstitucionDAO;
import mx.gob.segob.nsjp.dao.solicitud.SolicitudAdjuntosDAO;
import mx.gob.segob.nsjp.dto.documento.DocumentoDTO;
import mx.gob.segob.nsjp.dto.documento.DocumentoWSDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.funcionarioexterno.FuncionarioExternoDTO;
import mx.gob.segob.nsjp.dto.sentencia.SentenciaDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudWSDTO;
import mx.gob.segob.nsjp.model.Actividad;
import mx.gob.segob.nsjp.model.ArchivoDigital;
import mx.gob.segob.nsjp.model.ConfInstitucion;
import mx.gob.segob.nsjp.model.Documento;
import mx.gob.segob.nsjp.model.FuncionarioExterno;
import mx.gob.segob.nsjp.model.SolicitudAdjuntos;
import mx.gob.segob.nsjp.model.SolicitudAdjuntosId;
import mx.gob.segob.nsjp.model.Valor;
import mx.gob.segob.nsjp.service.documento.GuardarDocumentoService;
import mx.gob.segob.nsjp.service.expediente.BuscarExpedienteService;
import mx.gob.segob.nsjp.service.funcionarioexterno.impl.transform.FuncionarioExternoTransformer;
import mx.gob.segob.nsjp.service.infra.ClienteGeneralService;
import mx.gob.segob.nsjp.service.infra.impl.transform.enviardocumento.DocumentoWSDTOTransformer;
import mx.gob.segob.nsjp.service.programa.AsignacionProgramaService;
import mx.gob.segob.nsjp.service.solicitud.EnviarSolicitudService;
import mx.gob.segob.nsjp.service.solicitud.RegistrarSolicitudService;
import mx.gob.segob.nsjp.service.solicitud.impl.transform.SolicitudTransformer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Describir el objetivo de la clase con punto al final.
 * @version 1.0
 * @author AntonioBV
 *
 */

@Service("enviarSolicitudService")
@Transactional
public class EnviarSolicitudServiceImp implements EnviarSolicitudService {

    /**
     * Logger.
     */
    private final static Logger LOGGER = Logger
            .getLogger(EnviarSolicitudServiceImp.class);

	
	
	@Autowired
	private ClienteGeneralService clienteGeneralService;
	
	@Autowired
	private RegistrarSolicitudService registrarSolicitudService;

//	@Autowired
//	private AsignarNumeroExpedienteService asignarNumeroExpedienteService;	
	
	@Autowired
	private BuscarExpedienteService buscarExpedienteService;
	
//	@Autowired
//	private UsuarioService usuarioService;

	@Autowired
	private GuardarDocumentoService documentoService;
	
	@Autowired
	private ConfInstitucionDAO confInstitucionDAO;
	
	@Autowired
	private AsignacionProgramaService asignacionProgramaService;
	
	@Autowired
	private SolicitudAdjuntosDAO solicitudAdjuntosDAO;
	
	@Autowired
	private ArchivoDigitalDAO archivoDigitalDAO;
	
	@Autowired
	private FuncionarioExternoDAO funcionarioExternoDAO;
	
	@Autowired
	private ActividadDAO actividadDAO;
	
	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.solicitud.EnviarSolicitudService#enviarSolicitud(mx.gob.segob.nsjp.dto.solicitud.SolicitudDTO, mx.gob.segob.nsjp.comun.enums.institucion.Instituciones)
	 */
	@Override
	public SolicitudDTO enviarSolicitud(SolicitudDTO solicitudDTO,
			Instituciones destino,
			List<DocumentoDTO> lstDocumentoAdjuntos, SentenciaDTO sentenciaDTO) throws NSJPNegocioException {
		return clienteGeneralService.enviarSolicitud(solicitudDTO, destino, lstDocumentoAdjuntos, sentenciaDTO);
	}
	
	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.solicitud.EnviarSolicitudService#recibirSolicitud(mx.gob.segob.nsjp.dto.solicitud.SolicitudWSDTO)
	 */
	@Override
	public SolicitudWSDTO recibirSolicitud(SolicitudWSDTO solicitudWSDTO) throws NSJPNegocioException {
		
		try {
			SolicitudDTO solicitudDTO = SolicitudTransformer.transformarServerWSDTO2DTO(solicitudWSDTO);

			if(solicitudWSDTO.getNumeroCausaSentencia()== null
					&& solicitudDTO.getAreaDestino() == null
					&& solicitudDTO.getDestinatario() == null) {
				throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
			}
			
			
			// Casos de consulta de expediente para las solicitudes de RS
			// Consultar el expediente, 
			ExpedienteDTO expedienteDTO = null; 
			ConfInstitucion confInstitucion = confInstitucionDAO.consultarIntitucionActual();
			Instituciones institucion = Instituciones.getByValor(confInstitucion.getConfInstitucionId());
			switch (institucion) {
			// si en PGJ se consulta el expediente directamente
			case PJ:
				expedienteDTO = buscarExpedienteService
									.obtenerExpedientePorNumeroExpediente(
													solicitudWSDTO.getNumeroCausaSentencia());
				break;
			default:
				// para cualquier otra se consulta el expediente que se creo con la sentencia
				SentenciaDTO sentenciaDTO = new SentenciaDTO();
				sentenciaDTO.setCnumeroCausa(solicitudWSDTO.getNumeroCausaSentencia());
				sentenciaDTO = asignacionProgramaService.consultarSentencia(sentenciaDTO);
				expedienteDTO = sentenciaDTO.getNumeroExpedienteDTO();
				break;
			}

			
// Ya no se necesita crear un nuevo numero de expediente			
//			expedienteDTO.setArea(new AreaDTO(solicitudDTO.getAreaDestino()));
//			expedienteDTO.setTipoExpediente(new ValorDTO(TipoExpediente.CARPETA_DE_EJECUCION.getValorId()));
//			FuncionarioDTO funcionarioDTO = solicitudDTO.getDestinatario();
//			UsuarioDTO usuarioDTO = usuarioService.consultarUsuarioPorClaveFuncionario(funcionarioDTO.getClaveFuncionario());
//			expedienteDTO.setUsuario(usuarioDTO);
//			
//			ExpedienteDTO nuevoExp = asignarNumeroExpedienteService.asignarNumeroExpediente(expedienteDTO);

			solicitudDTO.setExpedienteDTO(expedienteDTO);
			
			
			// se valida el funcionario solicitante externo
			FuncionarioExternoDTO fEDTO = solicitudDTO.getSolicitanteInstExterna();
			Long claveFuncionarioExterno = null;
			if(fEDTO != null
					&& fEDTO.getCveFuncionarioInstExt()!= null 
					&& fEDTO.getConfInstitucionDTO() != null
					&& fEDTO.getConfInstitucionDTO().getConfInstitucionId() != null) {
				
				FuncionarioExterno funExt = funcionarioExternoDAO
				.consultarFuncExternoPorClaveFuncExt(fEDTO.getCveFuncionarioInstExt(),
						fEDTO.getConfInstitucionDTO().getConfInstitucionId());
				
				if(funExt==null) {
					claveFuncionarioExterno = funcionarioExternoDAO.create(FuncionarioExternoTransformer.transformar(fEDTO));
					funExt = funcionarioExternoDAO.read(claveFuncionarioExterno);
					fEDTO = FuncionarioExternoTransformer.transformar(funExt);
				} else {
					fEDTO = FuncionarioExternoTransformer.transformar(funExt);
				}
				
				solicitudDTO.setSolicitanteInstExterna(fEDTO);
			}
			
			
			solicitudDTO = registrarSolicitudService.registrarSolicitud(solicitudDTO);
			
			List<DocumentoWSDTO> lstDocumentosAdjuntos = solicitudWSDTO.getLstDocumentosAdjuntos();
			
			
			if (lstDocumentosAdjuntos!= null 
					&& !lstDocumentosAdjuntos.isEmpty()) {
            	for (DocumentoWSDTO documentoWSDTO : lstDocumentosAdjuntos) {
            		DocumentoDTO documentoDTO = DocumentoWSDTOTransformer.transformarDTO(documentoWSDTO);
            		//se obtiene el tipo actividad para saber el tipo actividad origen
            		Valor tipoActividad = null;
            		if (documentoWSDTO.getActividad() != null
            				&& documentoWSDTO.getActividad().getTipoActividadId() != null ){
            			tipoActividad = new Valor(documentoWSDTO.getActividad().getTipoActividadId());
            			documentoWSDTO.setActividad(null);
            		}
            		
            		Long documentoId = documentoService.guardarDocumento(documentoDTO, expedienteDTO, null,null);
            		
            		if (tipoActividad != null
            				&& tipoActividad.getValorId() != null){
            			Actividad actividadActual = actividadDAO.consultarActividadAsociadaDocumento(new Documento(documentoId));
            			if (actividadActual != null){
            				actividadActual.setTipoActividad(tipoActividad);
            				actividadDAO.update(actividadActual);
            			}
            		}
            		
            		ArchivoDigital archivoDigital = archivoDigitalDAO.consultarArchivoDigitalPorDocumento(documentoId);
            		
            		if (archivoDigital != null) {            		
	            		SolicitudAdjuntosId solicitudAdjuntosId = new SolicitudAdjuntosId();
	            		solicitudAdjuntosId.setArchivoDigitalId(archivoDigital.getArchivoDigitalId());
	            		solicitudAdjuntosId.setSolicitudId(solicitudDTO.getDocumentoId());
	            		
	            		SolicitudAdjuntos adjuntos = new SolicitudAdjuntos();
	            		adjuntos.setId(solicitudAdjuntosId);
	            		
	            		solicitudAdjuntosDAO.create(adjuntos);
            		}
				}
			}
			
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new NSJPNegocioException(CodigoError.INFORMACION_PARAMETROS_ERRONEA,e);
		}
		
		
		return null;
	}
	
}
