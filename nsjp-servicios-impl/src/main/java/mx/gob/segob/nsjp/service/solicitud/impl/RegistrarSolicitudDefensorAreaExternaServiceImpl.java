/**
 * Nombre del Programa : RegistrarSolicitudDefensorAreaExternaServiceImpl.java
 * Autor                            : Emigdio
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 30/06/2011
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
package mx.gob.segob.nsjp.service.solicitud.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.segob.nsjp.comun.enums.actividad.Actividades;
import mx.gob.segob.nsjp.comun.enums.audiencia.EstatusAudiencia;
import mx.gob.segob.nsjp.comun.enums.calidad.Calidades;
import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.enums.forma.Formas;
import mx.gob.segob.nsjp.comun.enums.institucion.Instituciones;
import mx.gob.segob.nsjp.comun.enums.seguridad.Roles;
import mx.gob.segob.nsjp.comun.enums.solicitud.EstatusSolicitud;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.archivo.ArchivoDigitalDAO;
import mx.gob.segob.nsjp.dao.audiencia.AudienciaDAO;
import mx.gob.segob.nsjp.dao.audiencia.SalaAudienciaDAO;
import mx.gob.segob.nsjp.dao.caso.CasoDAO;
import mx.gob.segob.nsjp.dao.catalogo.CatDistritoDAO;
import mx.gob.segob.nsjp.dao.delito.DelitoDAO;
import mx.gob.segob.nsjp.dao.expediente.ActividadDAO;
import mx.gob.segob.nsjp.dao.expediente.ExpedienteDAO;
import mx.gob.segob.nsjp.dao.funcionario.FuncionarioDAO;
import mx.gob.segob.nsjp.dao.solicitud.SolicitudDAO;
import mx.gob.segob.nsjp.dao.solicitud.SolicitudDefensorDAO;
import mx.gob.segob.nsjp.dto.audiencia.AudienciaDTO;
import mx.gob.segob.nsjp.dto.caso.CasoDTO;
import mx.gob.segob.nsjp.dto.catalogo.CatDistritoDTO;
import mx.gob.segob.nsjp.dto.documento.AvisoDesignacionDTO;
import mx.gob.segob.nsjp.dto.expediente.DelitoDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoWSDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudDefensorDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudDefensorWSDTO;
import mx.gob.segob.nsjp.model.Actividad;
import mx.gob.segob.nsjp.model.ArchivoDigital;
import mx.gob.segob.nsjp.model.Audiencia;
import mx.gob.segob.nsjp.model.AvisoDesignacion;
import mx.gob.segob.nsjp.model.Caso;
import mx.gob.segob.nsjp.model.CatDistrito;
import mx.gob.segob.nsjp.model.ConfInstitucion;
import mx.gob.segob.nsjp.model.Documento;
import mx.gob.segob.nsjp.model.Expediente;
import mx.gob.segob.nsjp.model.Forma;
import mx.gob.segob.nsjp.model.Funcionario;
import mx.gob.segob.nsjp.model.NumeroExpediente;
import mx.gob.segob.nsjp.model.SalaAudiencia;
import mx.gob.segob.nsjp.model.Solicitud;
import mx.gob.segob.nsjp.model.SolicitudDefensor;
import mx.gob.segob.nsjp.model.Valor;
import mx.gob.segob.nsjp.service.actividad.RegistrarActividadService;
import mx.gob.segob.nsjp.service.audiencia.AsignarSalaTemporalService;
import mx.gob.segob.nsjp.service.audiencia.impl.transform.AudienciaTransformer;
import mx.gob.segob.nsjp.service.caso.RegistrarReplicaCasoService;
import mx.gob.segob.nsjp.service.catalogo.ConsultarDiscriminanteService;
import mx.gob.segob.nsjp.service.catalogo.ConsultarDistritoService;
import mx.gob.segob.nsjp.service.delito.GuardarDelitoService;
import mx.gob.segob.nsjp.service.documento.AvisoDesignacionService;
import mx.gob.segob.nsjp.service.expediente.AdministrarNumeroExpedienteService;
import mx.gob.segob.nsjp.service.funcionario.impl.transform.FuncionarioTransformer;
import mx.gob.segob.nsjp.service.infra.impl.transform.enviarReplicaCaso.CasoWSDTOTransformer;
import mx.gob.segob.nsjp.service.infra.impl.transform.solicituddefensor.DelitoWSDTOTransformer;
import mx.gob.segob.nsjp.service.infra.impl.transform.solicituddefensor.InvolucradoWSDTOTransformer;
import mx.gob.segob.nsjp.service.involucrado.IngresarIndividuoService;
import mx.gob.segob.nsjp.service.solicitud.RegistrarSolicitudDefensorAreaExternaService;
import mx.gob.segob.nsjp.service.solicitud.RegistrarSolicitudDefensorService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementación del servicio de negocio para el registro de una solicitud de
 * defensor desde una institución externa
 * 
 * @version 1.0
 * @author Emigdio Hernández
 * 
 */
@Service("registrarSolicitudDefensorAreaExternaService")
@Transactional
public class RegistrarSolicitudDefensorAreaExternaServiceImpl implements
		RegistrarSolicitudDefensorAreaExternaService {

	 private final static Logger logger = Logger
			    .getLogger(RegistrarSolicitudDefensorAreaExternaServiceImpl.class);
			    		
	@Autowired
	AsignarSalaTemporalService asignarSalaTemporalService;

	@Autowired
	AdministrarNumeroExpedienteService administrarNumeroExpedienteService;
	@Autowired
	AudienciaDAO audienciaDAO;
	@Autowired
	SalaAudienciaDAO salaAudienciaDAO;
	@Autowired
	SolicitudDefensorDAO solicitudDefensorDAO;
	@Autowired
	DelitoDAO delitoDAO;
	@Autowired
	private CasoDAO casoDAO;
	@Autowired
	private ExpedienteDAO expedienteDAO;
	@Autowired
	private IngresarIndividuoService ingresarIndividuoService;
	@Autowired
	private ArchivoDigitalDAO archivoDigitalDAO;
	@Autowired
	private RegistrarSolicitudDefensorService registrarSolicitudDefensorService;
	@Autowired
	private FuncionarioDAO funcionarioDAO;
	@Autowired
	private CatDistritoDAO catDistritoDAO;
	@Autowired
	private RegistrarActividadService registrarActividadService;
	@Autowired
	private ActividadDAO actividadDAO;
	@Autowired
	private SolicitudDAO solicitudDAO;
	@Autowired
	private GuardarDelitoService guardarDelitoService;
	@Autowired
	private AvisoDesignacionService avisoDesignacionService; 
	@Autowired
	private RegistrarReplicaCasoService registrarReplicaCasoService;
	@Autowired
	private ConsultarDistritoService consultarDistritoService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.gob.segob.nsjp.service.solicitud.
	 * RegistrarSolicitudDefensorAreaExternaService
	 * #registrarSolicitudDefensor(mx
	 * .gob.segob.nsjp.dto.solicitud.SolicitudDefensorWSDTO)
	 */
	@Override
	public SolicitudDefensorWSDTO registrarSolicitudDefensor(
			SolicitudDefensorWSDTO solicitudWSDTO) throws NSJPNegocioException {

		if (solicitudWSDTO == null
				|| solicitudWSDTO.getCasoWSDTO() == null
				|| solicitudWSDTO.getCasoWSDTO().getNumeroGeneralCaso() == null
				|| solicitudWSDTO.getCasoWSDTO().getFechaApertura() == null
				|| solicitudWSDTO.getNumeroCasoAsociado() == null
				|| solicitudWSDTO.getNumeroCasoAsociado().trim().isEmpty()
				|| solicitudWSDTO.getClaveDiscriminanteOrigen() == null
				|| solicitudWSDTO.getClaveDiscriminanteOrigen().trim()
						.isEmpty() || solicitudWSDTO.getInvolucrados() == null
				|| solicitudWSDTO.getInvolucrados().isEmpty()) {
			logger.info("************WS registrarSolicitudDefensor -  solicitudWSDTO :"
					+ solicitudWSDTO);
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}

		// VerificarExistencia de caso en caso de que no exista,
		// se registra un nuevo caso y expediente
		logger.info("Validar Caso Replicado: " + solicitudWSDTO.getCasoWSDTO());
		CasoDTO casoDTO = CasoWSDTOTransformer.transformar(solicitudWSDTO
				.getCasoWSDTO());
		
		// Cuando se envia de PJ, se definen las misma reglas para
		// identificar el distrito. 
		//Buscar el distrito al que fue enviado la solicitud
		CatDistritoDTO catDistritoDTO = consultarDistritoService
				.consultarCatDistritoPorClave(solicitudWSDTO
						.getClaveDiscriminanteOrigen());
		Long catDistritoId = catDistritoDTO != null ? catDistritoDTO
				.getCatDistritoId() : null;
				
				
		casoDTO = registrarReplicaCasoService.registrarCasoExpediente(casoDTO, catDistritoDTO );
		logger.info("Caso Replicado - encontrado: "+ casoDTO);
		
		
		// Busca el unico expediente asociado al caso
		Expediente expedienteUnico = this.buscarExpedientesPorCasoAsociado(
				solicitudWSDTO.getNumeroCasoAsociado()).get(0);

		// Buscar por folio para actualizar o registrar los involucrados
		List<InvolucradoDTO> listaProbablesResponsables = this
				.ingresarActualizarInvolucradosSolicitud(
						solicitudWSDTO.getInvolucrados(),
						expedienteUnico.getExpedienteId());

		// Registrar los delitos del expediente
		if (solicitudWSDTO.getDelitos() != null
				&& !solicitudWSDTO.getDelitos().isEmpty()) {

			List<DelitoDTO> delitosDTO = DelitoWSDTOTransformer
					.transformaDelitos(solicitudWSDTO.getDelitos());

			// Crea la asociaci&oacute;n delito con expediente, siempre y cuando
			// no exista y se tenga registrado el catagolo del delito (mediante
			// la claveinterinstitucional) 
			guardarDelitoService.guardarDelitoExpedienteInterinstitucional(
					delitosDTO,
					new ExpedienteDTO(expedienteUnico.getExpedienteId()));
		}
		
		// Asociar Numero expediente para la segunda solicitud. Siempre y cuando
		// sea una solicitud que tenga asociada a un numero expediente
		Solicitud solicitudFiltro = new Solicitud();
		solicitudFiltro.setNumeroCasoAsociado(solicitudWSDTO
				.getNumeroCasoAsociado());
		List<Solicitud> listaSolicitudesAnteriores = solicitudDAO
				.consultarSolicitudPorFiltro(solicitudFiltro, true, null, null);
		NumeroExpediente numeroExpedienteAsignado =null;
		Funcionario funcionarioDestinatario = null;
		if (!listaSolicitudesAnteriores.isEmpty()) {
			numeroExpedienteAsignado = listaSolicitudesAnteriores.get(0)
					.getNumeroExpediente();
			funcionarioDestinatario = listaSolicitudesAnteriores.get(0)
					.getDestinatario();
		}
						
		// Registrar la solicitud
		Long solicitudDefensorId = this.registrarSolicitudDefensorWS(
				solicitudWSDTO, catDistritoId, numeroExpedienteAsignado,
				listaProbablesResponsables, funcionarioDestinatario);
		
		// Si existen solicitudes con numero de expediente asignado se requiere
		// verificar si cuentan con aviso de designaci&oacute;n
		if (numeroExpedienteAsignado != null) {
			// Buscar solicitudes con Aviso de Desginaci&oacute;n que hayan sido
			// asignadas a un defensor esto con el fin de que el defensor vea
			// esta nueva solicitud
			List<SolicitudDefensor> listaSolicitudesConAvisoDesignacion = solicitudDefensorDAO
					.consultarSolDeDefensorPorNumeroExpediente(
							solicitudWSDTO.getNumeroCasoAsociado(),
							numeroExpedienteAsignado.getNumeroExpediente(),
							true, true);
			if (listaSolicitudesConAvisoDesignacion != null
					&& !listaSolicitudesConAvisoDesignacion.isEmpty()) {
				// Se consulta el aviso de designaci&oacute;n de la primera
				// solicitud, considerando que la consulta anterior se hace
				// sobre solicitudes con aviso de designaci&oacute;n
				AvisoDesignacion avisoDesignacionDB = listaSolicitudesConAvisoDesignacion
						.get(0).getAvisoDesignacion();
				
				AvisoDesignacionDTO avisoDesignacionDTO = new AvisoDesignacionDTO();

				avisoDesignacionDTO.setFuncionario(FuncionarioTransformer
						.transformarFuncionarioBasico(avisoDesignacionDB
								.getFuncionario()));
				SolicitudDefensorDTO solicitudDefensorDTO = new SolicitudDefensorDTO(
						solicitudDefensorId);
				avisoDesignacionDTO.setSolicitudDefensor(solicitudDefensorDTO);
				
				ExpedienteDTO expedienteDTO = new ExpedienteDTO(
						expedienteUnico.getExpedienteId());
				avisoDesignacionDTO.setExpediente(expedienteDTO);
				avisoDesignacionService.designarAbogadoDefensor(
						avisoDesignacionDTO, false,
						false);
			}
		}

		// Registrar la nueva actividad con el documento de la solicitud.
		// Genera nueva actividad y la asocia al coordinador DEF

		// Si no se ha atendido por un coordinadorDEF alguna de las solicitudes
		// enviadas, se debe asignar al rol principal de coordinador Def
		if (funcionarioDestinatario == null) {

			// Debe de existir solo un Funcionario con rol Principal
			List<Funcionario> funcionariosCoordinadores = funcionarioDAO
					.consultarFuncionariosPorRolPrincipalyPorDistrito(
							Roles.COORDINADORDEF.getValorId(), catDistritoId);
			if (funcionariosCoordinadores != null
					&& !funcionariosCoordinadores.isEmpty()
					&& funcionariosCoordinadores.get(0) != null) {
				funcionarioDestinatario = funcionariosCoordinadores.get(0);
			}
		 } 
		if(funcionarioDestinatario==null){
			logger.info("NO EXISTE UN COORDINADORDEF (ROL PRINCIPAL) EN EL DISTRITO:"+ catDistritoId);
			throw new NSJPNegocioException(CodigoError.FUNCIONARIO_INEXISTENTE);
		}
		
		Long idActividadRegistrada = registrarActividadService
				.registrarActividad(
						new ExpedienteDTO(expedienteUnico.getExpedienteId()),
						new FuncionarioDTO(funcionarioDestinatario
								.getClaveFuncionario()),
						Actividades.SOLICITAR_DEFENSOR_PUBLICO.getValorId());
		logger.info("Actividad creada:" + idActividadRegistrada);

		// Asocia la actividad al documento
		Actividad actividad = actividadDAO.read(idActividadRegistrada);
		actividad.setDocumento(new Documento(solicitudDefensorId));

		logger.info("**DocumentoID:" + actividad.getDocumento());
		if (actividad.getDocumento() != null) {
			logger.info("**DocumentoID:" + actividad.getDocumento());
		}
		
		logger.info("**SolicitudID:" + solicitudDefensorId);
		Solicitud solicitud = solicitudDAO.read(solicitudDefensorId);
		solicitudWSDTO.setDocumentoId(solicitudDefensorId);

		solicitudWSDTO.setFolioDocumento(solicitud.getFolioDocumento());
		if (solicitud.getConfInstitucion() != null
				&& solicitud.getConfInstitucion().getConfInstitucionId() != null) {
			solicitudWSDTO.setConfInstitucionId(solicitud.getConfInstitucion()
					.getConfInstitucionId());
		}
		return solicitudWSDTO;
	}

	
	/**
	 * M&eacute;todo que es utilizado para registrar una audiencia a partir de
	 * la solicitud enviada.
	 * 
	 * @param solicitudWSDTO
	 * @param numeroExpediente
	 * @return
	 * @throws NSJPNegocioException
	 */
	private Audiencia registrarDatosAudiencia(
			SolicitudDefensorWSDTO solicitudWSDTO,
			NumeroExpediente numeroExpediente) throws NSJPNegocioException {
	
		//Buscar si ya se tiene la udiencia, mediante el folio
		Audiencia audiencia = this.audienciaDAO
				.obtnerAudienciaByFolio(solicitudWSDTO.getAudiencia()
						.getFolioAudiencia());

		// Registrar Audiencia Nueva
		if (audiencia == null) {
			audiencia = new Audiencia();
			audiencia.setTipo(new Valor(solicitudWSDTO.getAudiencia()
					.getTipoAudienciaId()));
			audiencia.setFechaAsignacionSala(solicitudWSDTO.getAudiencia()
					.getFechaAsignacionSala());
			audiencia.setDuracionEstimada(solicitudWSDTO.getAudiencia()
					.getDuracionEstimada());
			audiencia.setEstatus(new Valor(solicitudWSDTO.getAudiencia()
					.getEstatusAudienciaId()));
			audiencia.setFechaAudiencia(solicitudWSDTO.getAudiencia()
					.getFechaHoraAudiencia());
			
			audiencia.setConsecutivo((short) 1);
			// El mismo folio de la Audiencia es utilizado para 
			// generar el acuse de solitud, en PJ se busca por folio
			// de la audiencia para adjuntar el documento
			audiencia.setFolioAudiencia(solicitudWSDTO.getAudiencia()
					.getFolioAudiencia());

			//Setea el numero de expediente
			if (numeroExpediente != null) {
				audiencia.setNumeroExpediente(numeroExpediente);
			}
				
			if (solicitudWSDTO.getAudiencia().getEstatusAudienciaId() != null
					&& solicitudWSDTO.getAudiencia()
							.getEstatusAudienciaId() > 0) {
				audiencia.setEstatus(new Valor(solicitudWSDTO
						.getAudiencia().getEstatusAudienciaId()));
			}else{
				audiencia.setEstatus(new Valor(EstatusAudiencia.PROGRAMADA
						.getValorId()));
			}
			
			// guardar audiencia
			audiencia.setAudienciaId(audienciaDAO.create(audiencia));
			logger.info(" Audiencias Creada:"+ audiencia.getAudienciaId());
			
			AudienciaDTO audDTO = AudienciaTransformer.transformarDTO(audiencia);
			
			// Registrar sala o sala temporal
			if (solicitudWSDTO.getAudiencia().isSalaTemporal()) {
				audDTO.getSala().setNombreSala(
						solicitudWSDTO.getAudiencia().getNombreSala());
				audDTO.getSala().setTemporal(true);
				audDTO.getSala().setDomicilioSala(
						solicitudWSDTO.getAudiencia().getDomicilioSala());
				audDTO.getSala().setUbicacionSala(
						solicitudWSDTO.getAudiencia().getUbicacionSala());
				audDTO.getSala().setMotivo(solicitudWSDTO.getAudiencia().getMotivo());
				asignarSalaTemporalService.asignarSalaTemporal(audDTO);
			} else {
				// Buscar sala por nombre y descripcion , si no se encuentra crear
				// nueva
				SalaAudiencia sala = buscarCrearSalaAudiencia(solicitudWSDTO);
				if (sala == null) {
					sala = crearSalaAudiencia(solicitudWSDTO);
				}
				logger.info(" Sala:" + sala + " SalaId:"
						+ sala.getSalaAudienciaId());
				audiencia.setSalaAudiencia(sala);
			}
		}
		return audiencia;
	}

	/**
	 * De acuerod al numeroCasoAsociado, se hace la busqueda de los expedientes
	 * asociados al caso Se manda una excepci&oacute; de negocio para los
	 * siguientes escenarios: -En caso de que sea nulo. -No se encontro el caso.
	 * -No se tenga asociados expedientes al caso.
	 * 
	 * @param numeroCasoAsociado
	 * @return
	 * @throws NSJPNegocioException
	 */
	public List<Expediente> buscarExpedientesPorCasoAsociado(
			String numeroCasoAsociado) throws NSJPNegocioException {
		if (numeroCasoAsociado == null || numeroCasoAsociado.trim().isEmpty()) {
			logger.info("Numero caso nulo: " + numeroCasoAsociado + "!!!");
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		// Buscar el unico caso asociado.
		Caso casoAsociado = casoDAO
				.obtenerCasoByNumeroGeneral(numeroCasoAsociado);
		if (casoAsociado == null) {
			logger.info("No se ha ejecutado la replica del caso: "
					+ numeroCasoAsociado + "!!!");
			throw new NSJPNegocioException(CodigoError.SIN_CASO_ASOCIADO);
		}

		// Buscar los expedientes asociados al caso
		List<Expediente> listaExpedientes = null;
		listaExpedientes = this.expedienteDAO.consultarExpedientesPorIdCaso(
				casoAsociado.getCasoId(), null);
		if (listaExpedientes != null && listaExpedientes.isEmpty()
				&& listaExpedientes.get(0) != null) {
			logger.info("No se ha ejecutado la replica del caso: "
					+ numeroCasoAsociado + "!!!");
			throw new NSJPNegocioException(CodigoError.SIN_CASO_ASOCIADO);
		}
		return listaExpedientes;
	}

	/**
	 * M&eacute;todo que en carga de buscar, mediante el folio del elemento
	 * interinstitucional, al involucrado. Se actualiza en caso de encontrarlo
	 * actualizar, en caso contario se hacer el correspondiente registro.
	 * 
	 * @param involucradosWSDTO
	 * @param expedienteId
	 * @return
	 * @throws NSJPNegocioException
	 */
	private List<InvolucradoDTO> ingresarActualizarInvolucradosSolicitud(
			List<InvolucradoWSDTO> involucradosWSDTO, Long expedienteId)
			throws NSJPNegocioException {
		// Por cada Involucrado de la solicitud, se actualiza o se registra
		List<InvolucradoDTO> listaProbablesResponsables = new ArrayList<InvolucradoDTO>();
		for (InvolucradoWSDTO involucradoWSDTO : involucradosWSDTO) {
			InvolucradoDTO involucradoDTO = InvolucradoWSDTOTransformer
					.involucradoWsdto2InvolucradoDto(involucradoWSDTO);
			involucradoDTO.setExpedienteDTO(new ExpedienteDTO(expedienteId));

			// Buscar por folio, si existe se actualizan los datos. O se
			// registran si no existen.
			Long idIndividuo = ingresarIndividuoService
					.ingresarIndividuoInterInstitucion(involucradoDTO, true);
			logger.info("Individuo actualizado:" + idIndividuo + " - "
					+ involucradoDTO.getCalidadDTO());
			if (involucradoDTO
					.getCalidadDTO()
					.getValorIdCalidad()
					.getIdCampo()
					.equals(Calidades.PROBABLE_RESPONSABLE_PERSONA.getValorId())) {
				involucradoDTO.setElementoId(idIndividuo);
				listaProbablesResponsables.add(involucradoDTO);
			}
		}
		return listaProbablesResponsables;
	}

	/**
	 * M&eacute;todo que registra la solicitud de defensor, a partir de los
	 * datos que viene de un WS. Considerando la lista de los involucrados de la
	 * solicitud para la relación con la solciitud defensor. En caso de que se
	 * tenga una solicitud asignada, o atendida, se considera el funcionario
	 * destinatario al coordinador que atendio dicha solicitud.
	 * 
	 * @param solicitudWSDTO
	 * @param catDistritoId
	 * @param numeroExpedienteAsignado
	 * @param listaProbablesResponsables
	 * @param funcionarioDestinatario
	 * @return
	 * @throws NSJPNegocioException
	 */
	private Long registrarSolicitudDefensorWS(
			SolicitudDefensorWSDTO solicitudWSDTO, Long catDistritoId,
			NumeroExpediente numeroExpedienteAsignado,
			List<InvolucradoDTO> listaProbablesResponsables,
			Funcionario funcionarioDestinatario) throws NSJPNegocioException {

		SolicitudDefensor solicitudDefensor = new SolicitudDefensor();
		solicitudDefensor.setCatDiscriminanteOrigen(catDistritoId);
		solicitudDefensor.setClaveDiscriminanteOrigen(solicitudWSDTO
				.getClaveDiscriminanteOrigen());
		solicitudDefensor.setNumeroCasoAsociado(solicitudWSDTO
				.getNumeroCasoAsociado());
		solicitudDefensor.setFolioSolicitud(solicitudWSDTO.getFolioSolicitud());
		solicitudDefensor.setFolioDocumento(solicitudWSDTO.getFolioDocumento());
		solicitudDefensor.setConfInstitucion(new ConfInstitucion(solicitudWSDTO
				.getConfInstitucionId()));
		solicitudDefensor.setEstatus(new Valor(EstatusSolicitud.ABIERTA
				.getValorId()));
		solicitudDefensor.setNombreDocumento(solicitudWSDTO
				.getNombreDocumento());
		solicitudDefensor.setForma(new Forma(Formas.PLANTILLA_EN_BLANCO
				.getValorId()));
		solicitudDefensor.setTipoDocumento(new Valor(solicitudWSDTO
				.getTipoDocumentoDTO()));
		solicitudDefensor.setTipoSolicitud(new Valor(solicitudWSDTO
				.getIdTipoSolicitud()));
		solicitudDefensor.setEsGuardadoParcial(false);
		if (solicitudWSDTO.getArchivoDigital() != null) {
			ArchivoDigital archivo = new ArchivoDigital();
			archivo.setContenido(solicitudWSDTO.getArchivoDigital()
					.getContenido());
			archivo.setNombreArchivo(solicitudWSDTO.getArchivoDigital()
					.getNombreArchivo());
			archivo.setTipoArchivo(solicitudWSDTO.getArchivoDigital()
					.getTipoArchivo());
			archivoDigitalDAO.create(archivo);
			solicitudDefensor.setArchivoDigital(archivo);
		}

		solicitudDefensor.setFechaCreacion(solicitudWSDTO.getFechaCreacion());
		solicitudDefensor.setNombreSolicitante(solicitudWSDTO
				.getNombreSolicitante());

		solicitudDefensor.setNumeroExpedienteAsociado(solicitudWSDTO
				.getNumeroExpedienteAsociado());
		// solicitudDefensor.setSolicitanteExterno(solicitudWSDTO.getSolicitanteExternoId());
		solicitudDefensor.setFechaLimite(solicitudWSDTO.getFechaLimite());

		solicitudDefensor.setNumeroExpediente(numeroExpedienteAsignado);
		solicitudDefensor.setDestinatario(funcionarioDestinatario);
		
		solicitudDefensor
				.setNombreDeLaUnidadDeInvestigacionDelSolicitante(solicitudWSDTO
						.getNombreDeLaUnidadDeInvestigacionDelSolicitante());
		
		// Si es una solicitud de audiencia para PJ
		if (solicitudWSDTO.getAudiencia() != null
				&& solicitudWSDTO.getAudiencia().getFolioAudiencia() != null
				&& !solicitudWSDTO.getAudiencia().getFolioAudiencia().trim()
						.isEmpty()
				&& solicitudWSDTO.getConfInstitucionId() != null
				&& solicitudWSDTO.getConfInstitucionId() > 0
				&& solicitudWSDTO.getConfInstitucionId().equals(
						Instituciones.PJ.getValorId())) {

			Audiencia audiencia = this.registrarDatosAudiencia(solicitudWSDTO,
					numeroExpedienteAsignado);
			solicitudDefensor.setAudiencia(audiencia);
		}

		// Crear la solicitud Defensor
		Long solicitudDefensorId = solicitudDefensorDAO
				.create(solicitudDefensor);
		logger.info("Solicitud Defensor Creada:" + solicitudDefensorId);

		// Crear las relaciones de los involucrados con la solicitudDefensor
		registrarSolicitudDefensorService
				.registrarSolicitudDefensorInvolucrados(
						listaProbablesResponsables, new SolicitudDefensorDTO(
								solicitudDefensorId));

		return solicitudDefensorId;
	}
	
	/**
	 * M&eacute;todo que consulta el distrito por clave Interinstitucional.
	 * con el objetivo de ligarlo a la solicitud. 
	 *  
	 * @param claveclaveDistrito
	 * @return
	 * @throws NSJPNegocioException
	 */
	private Long buscarCatDistritoDeSolicitud(String claveclaveDistrito)
			throws NSJPNegocioException {
		CatDistrito distritoFiltro = new CatDistrito();
		distritoFiltro.setClaveDistrito(claveclaveDistrito);
		List<CatDistrito> listaResultados = catDistritoDAO
				.consultarDistritoPorFiltro(distritoFiltro);
		if (listaResultados.isEmpty()) {
			logger.error("No existe el distrito asociado:"
					+ claveclaveDistrito);
			throw new NSJPNegocioException(CodigoError.CLAVE_DISTRITO_INTERINSTITUCIONAL_INEXISTENTE);
		}
		//Se obtiene el primer resultado obtenido
		Long catDistritoId = null;
		if (listaResultados.get(0) != null
				&& listaResultados.get(0).getCatDistritoId() != null
				&& listaResultados.get(0).getCatDistritoId() <= 0L) {
			catDistritoId = listaResultados.get(0).getCatDistritoId();
		}
		return catDistritoId;
	}
	
	/**
	 * Crea una nueva sala de audiencia basada en los datos de la solicitud de
	 * defensor
	 * 
	 * @param solicitud
	 *            Datos de origen
	 * @return Sala creada
	 */
	private SalaAudiencia crearSalaAudiencia(SolicitudDefensorWSDTO solicitud) {
		SalaAudiencia sala = new SalaAudiencia();
		sala.setNombreSala(solicitud.getAudiencia().getNombreSala());
		sala.setDomicilioSala(solicitud.getAudiencia().getDomicilioSala());
		sala.setUbicacionSala(solicitud.getAudiencia().getUbicacionSala());
		sala.setEsActivo(true);
		salaAudienciaDAO.create(sala);
		return sala;
	}

	/**
	 * Busca una sala de audiencia por nombre y descripción, si no se encuentra
	 * se crea sala nueva
	 * 
	 * @param solicitud
	 * @return
	 */
	private SalaAudiencia buscarCrearSalaAudiencia(
			SolicitudDefensorWSDTO solicitud) {
		SalaAudiencia filtro = new SalaAudiencia();
		filtro.setNombreSala(solicitud.getAudiencia().getNombreSala());
		filtro.setDomicilioSala(solicitud.getAudiencia().getDomicilioSala());
		filtro.setUbicacionSala(solicitud.getAudiencia().getUbicacionSala());

		List<SalaAudiencia> salas = salaAudienciaDAO
				.consultarSalasPorFiltro(filtro);
		return salas != null && !salas.isEmpty() ? salas.get(0) : null;
	}

}
