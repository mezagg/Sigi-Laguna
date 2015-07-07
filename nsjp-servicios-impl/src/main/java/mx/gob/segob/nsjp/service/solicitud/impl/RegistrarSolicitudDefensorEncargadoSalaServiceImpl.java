package mx.gob.segob.nsjp.service.solicitud.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mx.gob.segob.nsjp.comun.enums.documento.TipoDocumento;
import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.enums.forma.Formas;
import mx.gob.segob.nsjp.comun.enums.solicitud.EstatusSolicitud;
import mx.gob.segob.nsjp.comun.enums.solicitud.TiposSolicitudes;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.audiencia.AudienciaDAO;
import mx.gob.segob.nsjp.dao.caso.CasoDAO;
import mx.gob.segob.nsjp.dao.institucion.ConfInstitucionDAO;
import mx.gob.segob.nsjp.dao.solicitud.SolicitudDefensorDAO;
import mx.gob.segob.nsjp.dto.audiencia.AudienciaDTO;
import mx.gob.segob.nsjp.dto.expediente.DelitoDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudDefensorDTO;
import mx.gob.segob.nsjp.model.Audiencia;
import mx.gob.segob.nsjp.model.Caso;
import mx.gob.segob.nsjp.model.ConfInstitucion;
import mx.gob.segob.nsjp.model.Forma;
import mx.gob.segob.nsjp.model.SolicitudDefensor;
import mx.gob.segob.nsjp.model.Valor;
import mx.gob.segob.nsjp.service.delito.ConsultarDelitoService;
import mx.gob.segob.nsjp.service.infra.DefensoriaClienteService;
import mx.gob.segob.nsjp.service.involucrado.ConsultarIndividuoService;
import mx.gob.segob.nsjp.service.solicitud.GenerarFolioSolicitudService;
import mx.gob.segob.nsjp.service.solicitud.RegistrarSolicitudDefensorEncargadoSalaService;
import mx.gob.segob.nsjp.service.solicitud.RegistrarSolicitudDefensorService;
import mx.gob.segob.nsjp.service.solicitud.SolicitarDefensorService;
import mx.gob.segob.nsjp.service.solicitud.impl.transform.SolicitudDefensorTransformer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegistrarSolicitudDefensorEncargadoSalaServiceImpl implements
		RegistrarSolicitudDefensorEncargadoSalaService {
	public final static Logger LOGGER = 
			Logger.getLogger(RegistrarSolicitudDefensorEncargadoSalaServiceImpl.class);
					
	@Autowired
	private DefensoriaClienteService clienteService;
	@Autowired
	private SolicitudDefensorDAO solicitudDefensorDAO;
	@Autowired
	private CasoDAO casoDAO;
	@Autowired
	private GenerarFolioSolicitudService generarFolioSolicitudService;
	@Autowired
	private ConfInstitucionDAO confInstitucionDAO;
	@Autowired 
	private AudienciaDAO audienciaDAO;
	@Autowired
	private RegistrarSolicitudDefensorService registrarSolicitudDefensorService;
	@Autowired
	private ConsultarIndividuoService consultarIndividuoService; 
	@Autowired
	private ConsultarDelitoService consultarDelitoService;
	@Autowired
	private SolicitarDefensorService solicitarDefensorService;
	
	@Transactional
	@Override
	public SolicitudDefensorDTO registrarSolicitudDefensorPJ(AudienciaDTO audienciaDTO,
            List<InvolucradoDTO> listaProbablesResponsables) throws NSJPNegocioException{
		
		// Validaciones
		if (audienciaDTO.getId() == null
				|| audienciaDTO.getId() < 0
				|| listaProbablesResponsables == null
				|| listaProbablesResponsables.isEmpty()
				|| audienciaDTO.getUsuario() == null
				|| audienciaDTO.getUsuario().getFuncionario() == null
				|| audienciaDTO.getUsuario().getFuncionario()
						.getDiscriminante().getDistrito().getClaveDistrito() == null
				|| audienciaDTO.getUsuario().getFuncionario()
						.getDiscriminante().getDistrito().getClaveDistrito()
						.isEmpty()) {
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		
		// Audiencia asociada
		Audiencia audienciaBD = audienciaDAO.read(audienciaDTO.getId());
		if (audienciaBD == null
				|| audienciaBD.getNumeroExpediente() == null
				|| audienciaBD.getNumeroExpediente().getExpediente() == null
				|| audienciaBD.getNumeroExpediente().getExpediente()
						.getExpedienteId() == null
				|| audienciaBD.getNumeroExpediente().getExpediente()
						.getExpedienteId() < 0) {
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		
		// Caso asociado
		Caso caso = casoDAO.consultarCasoPorExpediente(audienciaBD
				.getNumeroExpediente().getExpediente().getExpedienteId());
		if(caso==null){
			throw new NSJPNegocioException(CodigoError.SIN_CASO_ASOCIADO);
		}
		
		// Obtiene los datos de la solicitud Defensor
		SolicitudDefensor solicitudDefensor = this
				.obtenerDatosSolicitudDefensor(audienciaBD, caso);
		
		// Tranforma a un DTO y Obtiene los datos de los involucrados y delitos,
		// para la solicitud de defensor
		SolicitudDefensorDTO solicitudDefensorDTO = obtenerDatosSolicitudDefensor(
				solicitudDefensor, audienciaBD, listaProbablesResponsables,
				audienciaDTO);
		
		// Invocaci&oacute;n al cliente que envia la solicitud de defensor 
		SolicitudDefensorDTO solicitudRespuesta = clienteService
				.enviarSolicitudDefensor(solicitudDefensorDTO);
		
		LOGGER.info("SolicitudRespuesta:" + solicitudRespuesta);
		
		// En caso de no obtener errores se guardan los datos de la solitud
		if (solicitudRespuesta != null
				&& solicitudRespuesta.getDocumentoId() != null) {
			
			//Se coloca el folio e institución del documento de Defensoria 
			solicitudDefensor.setFolioDocumento(solicitudRespuesta
					.getFolioDocumento());
			if (solicitudRespuesta.getConfInstitucion() != null
					&& solicitudDefensor.getConfInstitucion()
							.getConfInstitucionId() != null
					&& solicitudDefensor.getConfInstitucion()
							.getConfInstitucionId() > 0) {
				solicitudDefensor.setConfInstitucion(new ConfInstitucion(
						solicitudDefensor.getConfInstitucion()
								.getConfInstitucionId()));
			}
			
			LOGGER.info("SolicitudRespuesta:"
					+ solicitudRespuesta.getDocumentoId());
			Long solicitudDefensorId = solicitudDefensorDAO
					.create(solicitudDefensor);

			// Registrar la solicitud Defensor
			registrarSolicitudDefensorService
					.registrarSolicitudDefensorInvolucrados(
							listaProbablesResponsables,
							new SolicitudDefensorDTO(solicitudDefensorId));

			LOGGER.info("SolicitudCreada:" + solicitudDefensorId);
			solicitudDefensorDTO.setDocumentoId(solicitudDefensorId);
		} else {
			LOGGER.error("NO SE PUDO REGISTRAR LA SOLICITUD DE DEFENSOR EN DEFENSORIA:::::");
			throw new NSJPNegocioException(
					CodigoError.NO_SE_REGISTRO_LA_SOLICITUD_DE_DEFENSOR);
		}
		return solicitudDefensorDTO;
	}
	
	/**
	 * M&eacute;todo que genera la solicitudDefensor a partir de los 
	 * datos de la audiencia,del caso y literales definidas.
	 *  
	 * @param audienciaBD
	 * @param caso
	 * @return
	 * @throws NSJPNegocioException
	 */
	private SolicitudDefensor obtenerDatosSolicitudDefensor(
			Audiencia audienciaBD, Caso caso) throws NSJPNegocioException {

		//TODO AGA OBTENER LOS DATOS DE LA SOLICITUD DE LA TABLA CONF
		//ACTIVIDAD DOCUMENTO (REGISTRAR LA ACTUACION PARA PJ, O TOMAR LA QUE SE TIENE PARA PG)
		
		/*
		 * Seccion para los datos generales de la solicitud de defensor
		 */
		SolicitudDefensor solicitudDefensor = new SolicitudDefensor();
		
		// Numero de caso
		solicitudDefensor.setNumeroCasoAsociado(caso.getNumeroGeneralCaso());
		
		// Folio de la solicitud
		solicitudDefensor.setFolioSolicitud(generarFolioSolicitudService
				.generarFolioSolicitud());
		
		// Conf Institucion
		ConfInstitucion confInstitucion = confInstitucionDAO
		.consultarIntitucionActual();
		solicitudDefensor.setConfInstitucion(confInstitucion);
		
		// Estatus de la solicitud
		solicitudDefensor.setEstatus(new Valor(EstatusSolicitud.ABIERTA
				.getValorId()));
		// Nombre Documento
		solicitudDefensor.setNombreDocumento("Solicitud de Defensor");
		
		// Forma del documento
		solicitudDefensor.setForma(new Forma(Formas.SOLICITUD.getValorId()));
		
		// Tipo documento
		solicitudDefensor.setTipoDocumento(new Valor(TipoDocumento.SOLICITUD
				.getValorId()));
		
		// Tipo de solicitud
		solicitudDefensor.setTipoSolicitud(new Valor(TiposSolicitudes.DEFENSOR
				.getValorId(), TiposSolicitudes.DEFENSOR.name()));	
		// Fecha y hora de la solicitud
		solicitudDefensor.setFechaCreacion(new Date());
		
		
		// Nombre del funcionario solicitante
		if (audienciaBD == null || audienciaBD.getSolicitud() == null) {
			LOGGER.info("La audiencia fue creada sin una solicitud!!");
			// TODO GBP SOLCITUD DEFENSOR Existen casos que la audiencia no contemplen una solicitud
			// asociada. Buscar las audiencias relacionadas con solicitud
			// throw new
			// NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		} else {
			solicitudDefensor.setNombreSolicitante(audienciaBD.getSolicitud()
					.getNombreSolicitante());
		}

		// Numero de expediente relacionado
		solicitudDefensor.setNumeroExpedienteAsociado(audienciaBD
				.getNumeroExpediente().getNumeroExpediente());

		// Datos de la Audiencia
		solicitudDefensor.setAudiencia(audienciaBD);
		
		// Numero de expediente relacionado a la Audiencia
		solicitudDefensor.setNumeroExpedienteAsociado(audienciaBD
				.getNumeroExpediente().getNumeroExpediente());
		solicitudDefensor
				.setNumeroExpediente(audienciaBD.getNumeroExpediente());

		return solicitudDefensor;
	}

	/**
	 * M&eacute;todo que transforma al solicitud Audiencia a DTO, obtiene
	 * los involucrados, previamente seleccionados en vista, 
	 * y la lista de delitos asociados al expediente.
	 *  
	 * @param solicitudDefensor
	 * @param audienciaBD
	 * @param listaProbablesResponsables
	 * @param audienciaDTO
	 * @return
	 * @throws NSJPNegocioException
	 */
	private SolicitudDefensorDTO obtenerDatosSolicitudDefensor(
			SolicitudDefensor solicitudDefensor, Audiencia audienciaBD,
			List<InvolucradoDTO> listaProbablesResponsables,
			AudienciaDTO audienciaDTO) throws NSJPNegocioException {

		//En el transformer se setean los datos de la audiencia.
		SolicitudDefensorDTO solicitudDefensorDTO = SolicitudDefensorTransformer
				.transformarSolicitudDefensoria(solicitudDefensor);
		
		// Se consultan los involucrados a enviar
		List<InvolucradoDTO> listaInvolucradosDTO = new ArrayList<InvolucradoDTO>();
		for (InvolucradoDTO involucradoDTO : listaProbablesResponsables) {
			listaInvolucradosDTO.add(consultarIndividuoService
					.obtenerInvolucrado(involucradoDTO));
		}
		
		// Se consultan las victimas de la relacion delito persona y
		// el denunciante del expediente
		listaInvolucradosDTO.addAll(solicitarDefensorService
				.obtenerVicimasYDenunciantesDeRelacionDelitoPersona(
						listaInvolucradosDTO, new ExpedienteDTO(audienciaBD
								.getNumeroExpediente().getExpediente()
								.getExpedienteId())));
		
		solicitudDefensorDTO.setInvolucrados(listaInvolucradosDTO);
		
		// Consultar los delitos asociados al expediente
		List<DelitoDTO> delitosExpedientes = consultarDelitoService
				.consultarDelitoExpediente(new ExpedienteDTO(audienciaBD
						.getNumeroExpediente().getExpediente()
						.getExpedienteId()));
		solicitudDefensorDTO.setDelitos(delitosExpedientes);
		
		// Clave Distrito Origen
		solicitudDefensorDTO.setClaveDiscriminanteOrigen(audienciaDTO
				.getUsuario().getFuncionario().getDiscriminante().getDistrito()
				.getClaveDistrito());
		return solicitudDefensorDTO;
	}
	
	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.solicitud.RegistrarSolicitudDefensorEncargadoSalaService#obtenerSolicitudesPorAudienciaEImputado(mx.gob.segob.nsjp.dto.audiencia.AudienciaDTO, mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO)
	 */
	@Override
	public List<Long> obtenerInvolucradosIdConSolicitudDefensor(
			AudienciaDTO audienciaDTO, List<Long> imputadosId) {

		Audiencia audiencia = audienciaDTO != null
				&& audienciaDTO.getId() != null && audienciaDTO.getId() > 0 ? new Audiencia(
				audienciaDTO.getId()) : null;

		List<Long> imputadosIdResultado = solicitudDefensorDAO
				.obtenerInvolucradosIdConSolicitudDefensor(audiencia,
						imputadosId);

		return imputadosIdResultado;
	}

}
