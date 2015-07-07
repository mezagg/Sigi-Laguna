/**
 * Nombre del Programa	: RecibirAcuseDeReciboDeSolicitudDeDefensorServiceImpl.java
 * Autor				: AlejandroGA
 * Compania             : Ultrasist
 * Proyecto             : NSJP                    Fecha: 28 Nov 2012
 * Marca de cambio      : N/A
 * Descripcion General  : Servicio para recibir los documentos de acuse de recibo
 * 						  de la solicitud de defensor
 * Programa Dependiente	: N/A
 * Programa Subsecuente	: N/A
 * Cond. de ejecucion   : N/A
 * Dias de ejecucion    : N/A                             Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor                : N/A
 * Compania             : N/A
 * Proyecto             : N/A                                 Fecha: N/A
 * Modificacion         : N/A
 *------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.service.documento.impl;

import mx.gob.segob.nsjp.comun.enums.actividad.Actividades;
import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.enums.institucion.Instituciones;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.audiencia.AudienciaDAO;
import mx.gob.segob.nsjp.dao.institucion.ConfInstitucionDAO;
import mx.gob.segob.nsjp.dto.ActividadDTO;
import mx.gob.segob.nsjp.dto.audiencia.AudienciaDTO;
import mx.gob.segob.nsjp.dto.documento.DocumentoDTO;
import mx.gob.segob.nsjp.dto.documento.DocumentoWSDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudDefensorWSDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudWSDTO;
import mx.gob.segob.nsjp.model.Audiencia;
import mx.gob.segob.nsjp.model.ConfInstitucion;
import mx.gob.segob.nsjp.service.audiencia.IngresarFuncionarioExternoAudienciaService;
import mx.gob.segob.nsjp.service.documento.AsociarDocumentoAService;
import mx.gob.segob.nsjp.service.documento.GuardarDocumentoService;
import mx.gob.segob.nsjp.service.documento.RecibirAcuseDeReciboDeSolicitudDeDefensorService;
import mx.gob.segob.nsjp.service.infra.impl.transform.enviarcarpetainvestigacion.ExpedienteWSDTOTransformer;
import mx.gob.segob.nsjp.service.infra.impl.transform.recibiracusederecibodesolicituddedefensor.FuncionarioExternoWSDTOTransformer;
import mx.gob.segob.nsjp.service.solicitud.ConsultarSolicitudService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Servicion que perimite recibir un documento de acuse de recibo de solicitud
 * de defensor
 * 
 * @author AlejandroGA
 * @version 1.0
 */

@Service("recibirAcuseDeReciboDeSolicitudDeDefensorService")
@Transactional
public class RecibirAcuseDeReciboDeSolicitudDeDefensorServiceImpl implements
		RecibirAcuseDeReciboDeSolicitudDeDefensorService {

	public static final Logger logger = Logger
			.getLogger(RecibirAcuseDeReciboDeSolicitudDeDefensorServiceImpl.class);

	@Autowired
	private ConfInstitucionDAO confInstitucionDAO;
	@Autowired
	private AudienciaDAO audienciaDAO;
	@Autowired
	private ConsultarSolicitudService solicitudService;
	@Autowired
	private GuardarDocumentoService documentoService;
	@Autowired
	private AsociarDocumentoAService asociarDocumentoAService;
	@Autowired
	private IngresarFuncionarioExternoAudienciaService ingresarFuncionarioExternoAudienciaService;


	@Override
	public DocumentoWSDTO recibirAcuseDeReciboDeSolicitudDeDefensor(
			SolicitudWSDTO solicitudWsDto, DocumentoWSDTO documentoWsDto)
			throws NSJPNegocioException {

		if (logger.isDebugEnabled()) {
			logger.debug("/**** SERVICIO PARA RECIBIR UN ACUSE DE SOLICITUD DE DEFENSOR****/");
		}

		/*
		 * Bloque de Validaciones
		 */
		if (solicitudWsDto == null
				|| solicitudWsDto.getFolioSolicitud() == null
				|| documentoWsDto == null
				|| documentoWsDto.getArchivoDigital() == null) {
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}

		SolicitudDTO solicitudEncontrada = solicitudService
				.consultarDatosDeSolicitud(solicitudWsDto.getFolioSolicitud());

		SolicitudDefensorWSDTO solicitudDefensorWsDto = null;

		if (solicitudWsDto instanceof SolicitudDefensorWSDTO) {
			solicitudDefensorWsDto = (SolicitudDefensorWSDTO) solicitudWsDto;
		}

		if (solicitudDefensorWsDto == null
				|| solicitudEncontrada == null
				|| solicitudEncontrada.getExpedienteDTO() == null
				|| solicitudEncontrada.getExpedienteDTO().getExpedienteId() == null
				|| solicitudEncontrada.getExpedienteDTO().getExpedienteId() <= 0L) {
			throw new NSJPNegocioException(
					CodigoError.INFORMACION_PARAMETROS_ERRONEA);
		}
		
		// Variable para el regreso
		DocumentoWSDTO documentoRegresoWsDto = new DocumentoWSDTO();

		ConfInstitucion institucionActual = confInstitucionDAO
				.consultarInsitucionActual();

		DocumentoDTO documentoRecibidoDto = ExpedienteWSDTOTransformer
				.transforma(documentoWsDto);
		
		//Guardado definitivo
		documentoRecibidoDto.setEsGuardadoParcial(false);

		/*
		 * Bloque de instrucciones solo para PJ
		 */
		if (Instituciones.PJ.getValorId().equals(
				institucionActual.getConfInstitucionId())) {
			
			if (solicitudDefensorWsDto.getAudiencia() == null
					|| solicitudDefensorWsDto.getAudiencia()
							.getFolioAudiencia() == null
					|| solicitudDefensorWsDto.getAudiencia()
							.getFolioAudiencia().trim().isEmpty()) {
				throw new NSJPNegocioException(
						CodigoError.PARAMETROS_INSUFICIENTES);
			}

			//Obtenemos la audiencia con la cual relacionar el documento
			Audiencia audiencia = audienciaDAO
					.obtnerAudienciaByFolio(solicitudDefensorWsDto
							.getAudiencia().getFolioAudiencia().trim());

			if (audiencia == null) {
				throw new NSJPNegocioException(
						CodigoError.INFORMACION_PARAMETROS_ERRONEA);
			}

			//Guardamos el documento sin asociar ninguna actividad
			documentoRegresoWsDto.setDocumentoId(documentoService
					.guardarDocumento(documentoRecibidoDto, new ExpedienteDTO(
							solicitudEncontrada.getExpedienteDTO()
									.getExpedienteId()), null, null));

			//Asociamos el documento a la audiencia
			asociarDocumentoAService.asociarDocumentoAAudiencia(
					new AudienciaDTO(audiencia.getAudienciaId()),
					new DocumentoDTO(documentoRegresoWsDto.getDocumentoId()));
			
			//En este bloque de instrucciones guardamos el funcionario externo y
			//y lo relacionamos a la audiencia
			ingresarFuncionarioExternoAudienciaService
					.ingresarFuncionarioExternoAudiencia(
							FuncionarioExternoWSDTOTransformer
									.transformarDTO(solicitudDefensorWsDto
											.getDefensorAsignado()),
							new AudienciaDTO(audiencia.getAudienciaId()), true);

		/*
		 * Bloque de instrucciones solo para PG
		 */
		} else if (Instituciones.PGJ.getValorId().equals(
				institucionActual.getConfInstitucionId())) {
			
			//TODO AGA LA NUEVA ACTIVIDAD A GENERERAR ES
			//DEBERIA SER RECIBIR_ACUSE_DE_RECIBO_DE_SOLICITUD_DE_DEFENSOR
			
			ActividadDTO actividadARegistrar = new ActividadDTO();
			//FIXME AGA ESPECIFICAR LA ACTIVIDAD CORRECTA PARA EL SERVICIO DE GUARDAR DOCUMENTO CON ACTIVIDAD
			actividadARegistrar.setTipoActividad(Actividades.GENERAR_ACUSE_DE_RECIBO_DE_SOLICITUD_DE_DEFENSOR_PUBLICO);
			
			documentoRecibidoDto.setActividadDTO(actividadARegistrar);
			
			// Guardamos el documento con actividad
			documentoRegresoWsDto.setDocumentoId(documentoService
					.guardarDocumentoConActividadDocumento(
							documentoRecibidoDto, new ExpedienteDTO(
									solicitudEncontrada.getExpedienteDTO()
											.getExpedienteId())));
		}

		logger.info("DOCUMENTOWSDTO REGESO, ID:::::::::::::::"+documentoRegresoWsDto.getDocumentoId());
		return documentoRegresoWsDto;
	}
}
