package mx.gob.segob.nsjp.service.notificacion.impl;

//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import mx.gob.segob.nsjp.comun.enums.actividad.Actividades;
import mx.gob.segob.nsjp.comun.enums.documento.EstatusNotificacion;
import mx.gob.segob.nsjp.comun.enums.documento.TipoDocumento;
import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.enums.forma.Formas;
import mx.gob.segob.nsjp.comun.enums.tarea.TipoEvento;
import mx.gob.segob.nsjp.comun.enums.tarea.TipoTarea;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.archivo.ArchivoDigitalDAO;
import mx.gob.segob.nsjp.dao.audiencia.AudienciaDAO;
import mx.gob.segob.nsjp.dao.audiencia.InvolucradoAudienciaDAO;
import mx.gob.segob.nsjp.dao.catalogo.ValorDAO;
import mx.gob.segob.nsjp.dao.documento.NotificacionDAO;
import mx.gob.segob.nsjp.dao.expediente.ActividadDAO;
import mx.gob.segob.nsjp.dao.expediente.ExpedienteDAO;
import mx.gob.segob.nsjp.dao.expediente.RelNumExpedienteAuditoriaDAO;
import mx.gob.segob.nsjp.dao.forma.FormaDAO;
import mx.gob.segob.nsjp.dao.funcionario.FuncionarioAudienciaDAO;
import mx.gob.segob.nsjp.dao.funcionario.FuncionarioDAO;
import mx.gob.segob.nsjp.dao.funcionarioexterno.FuncionarioExternoAudienciaDAO;
import mx.gob.segob.nsjp.dao.funcionarioexterno.FuncionarioExternoDAO;
import mx.gob.segob.nsjp.dao.institucion.JerarquiaOrganizacionalDAO;
import mx.gob.segob.nsjp.dao.involucrado.InvolucradoDAO;
import mx.gob.segob.nsjp.dto.audiencia.AudienciaDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.documento.DocumentoDTO;
import mx.gob.segob.nsjp.dto.documento.NotificacionDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.dto.funcionarioexterno.FuncionarioExternoDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.dto.tarea.EventoCitaDTO;
import mx.gob.segob.nsjp.dto.tarea.TareaDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;
import mx.gob.segob.nsjp.model.Actividad;
import mx.gob.segob.nsjp.model.ArchivoDigital;
import mx.gob.segob.nsjp.model.Audiencia;
import mx.gob.segob.nsjp.model.Documento;
import mx.gob.segob.nsjp.model.Expediente;
import mx.gob.segob.nsjp.model.Funcionario;
import mx.gob.segob.nsjp.model.FuncionarioAudiencia;
import mx.gob.segob.nsjp.model.FuncionarioAudienciaId;
import mx.gob.segob.nsjp.model.FuncionarioExterno;
import mx.gob.segob.nsjp.model.FuncionarioExternoAudiencia;
import mx.gob.segob.nsjp.model.FuncionarioExternoAudienciaId;
import mx.gob.segob.nsjp.model.Involucrado;
import mx.gob.segob.nsjp.model.InvolucradoAudiencia;
import mx.gob.segob.nsjp.model.InvolucradoAudienciaId;
import mx.gob.segob.nsjp.model.JerarquiaOrganizacional;
import mx.gob.segob.nsjp.model.Notificacion;
import mx.gob.segob.nsjp.model.NumeroExpediente;
import mx.gob.segob.nsjp.model.RelNumExpedienteAuditoria;
import mx.gob.segob.nsjp.model.Valor;
import mx.gob.segob.nsjp.service.archivo.impl.transform.ArchivoDigitalTransformer;
import mx.gob.segob.nsjp.service.audiencia.impl.transform.AudienciaTransformer;
import mx.gob.segob.nsjp.service.forma.impl.transform.FormaTransformer;
import mx.gob.segob.nsjp.service.funcionario.impl.transform.FuncionarioTransformer;
import mx.gob.segob.nsjp.service.funcionarioexterno.impl.transform.FuncionarioExternoTransformer;
import mx.gob.segob.nsjp.service.infra.ClienteGeneralService;
import mx.gob.segob.nsjp.service.notificacion.GuardarNotificacionService;
import mx.gob.segob.nsjp.service.solicitud.GenerarFolioSolicitudService;
import mx.gob.segob.nsjp.service.tarea.AsignarTareaFuncionarioService;
import mx.gob.segob.nsjp.service.usuario.impl.transformer.ValorTransformer;

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
public class GuardarNotificacionServiceImpl implements
		GuardarNotificacionService {

	public final static Logger logger = Logger
			.getLogger(GuardarNotificacionServiceImpl.class);
	@Autowired
	private NotificacionDAO notificacionDAO;
	@Autowired
    private AsignarTareaFuncionarioService asignarTareaFuncionarioService;    
	@Autowired
	private InvolucradoAudienciaDAO involucradoAudienciaDAO;
	@Autowired
	private FuncionarioAudienciaDAO funcionarioAudienciaDAO;
	@Autowired
	private FuncionarioExternoAudienciaDAO funcionarioExternoAudienciaDAO;
	@Autowired
	private FormaDAO formaDAO;
	@Autowired
    private FuncionarioDAO funDao;    
	@Autowired
	private GenerarFolioSolicitudService generarFolioSolicitudService;
	@Autowired
	private AudienciaDAO audienciaDao;
	@Autowired
	private ClienteGeneralService clienteWs;
	@Autowired
	private InvolucradoDAO involucradoDAO;
	@Autowired
	private FuncionarioDAO funcionarioDAO;
	@Autowired
	private ExpedienteDAO expedienteDAO;
	@Autowired
	private ActividadDAO actividadDAO;
	@Autowired
	private ValorDAO valorDAO;
	@Autowired
	private ArchivoDigitalDAO archivoDigitalDAO;
	@Autowired
	private RelNumExpedienteAuditoriaDAO relNumExpedienteAuditoriaDAO;
	@Autowired
	private JerarquiaOrganizacionalDAO jerarquiaOrganizacionalDAO;
	@Autowired
	private FuncionarioExternoDAO funcionarioExternoDAO;
	
	public static final String ACTIVIDAD = "Acudir a Audiencia";
	public static final String DOMICILIO_SALA = " Domicilio:";
	public static final String SALA = "Sala:";
	public static final String UBICACION_SALA = " Ubicaci\u00F3n:";
	public static final String NUMERO_DE_CAUSA = " N\u00FAmero de Causa:";
	public static final String FOLIO= "Folio:";
	public static final String TIPO_DE_AUDIENCIA= " Tipo de Audiencia:";
	public static final String ESTADO= " Estado:";
	public static final String ESPACIO_HTML= "&nbsp;";
	public static final String SALTO_HTML="<br/>";

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.gob.segob.nsjp.service.notificacion.GuardarNotificacionService#
	 * guardarNotificacion(mx.gob.segob.nsjp.dto.documento.NotificacionDTO)
	 */
	@Override
	public Long guardarNotificacion(NotificacionDTO notificacionDTO,
			AudienciaDTO audienciaDTO, InvolucradoDTO involucradoDTO)
			throws NSJPNegocioException {

		if (logger.isDebugEnabled())
			logger.debug("/**** SERVICIO PARA GUARDAR UN DOCUMENTO TIPO NOTIFICACION DE AUDIENCIA ****/");

		/* Verificacion de parametros */
		if (notificacionDTO == null || audienciaDTO == null
				|| involucradoDTO == null)
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);

		Audiencia audiencia = audienciaDao.read(audienciaDTO.getId());
		Involucrado involucrado = involucradoDAO.read(involucradoDTO
				.getElementoId());

		Notificacion notificacion = NotificacionTransformer
				.transformarNotificacion(notificacionDTO);
		/* Obligatorios de Docmento */
		notificacion.setNombreDocumento("NOTIFICACION DE AUDIENCIA");
		notificacion.setFechaCreacion(new Date());
		// notificacion.setForma(formaDAO.consultarFormaPorNombre("Blanco"));
		notificacion.setForma(formaDAO.read(Formas.NOTIFICACION_DE_AUDIENCIA
				.getValorId()));

		notificacion.setTipoDocumento(new Valor(TipoDocumento.NOTIFICACION
				.getValorId()));

		/* Obligatorios de Notificacion */
		InvolucradoAudiencia invAud = new InvolucradoAudiencia(
				new InvolucradoAudienciaId(audienciaDTO.getId(),
						involucradoDTO.getElementoId()), new Involucrado(
						involucradoDTO.getElementoId()), new Audiencia(
						audienciaDTO.getId()));

		InvolucradoAudiencia bdInvAud = involucradoAudienciaDAO.read(invAud
				.getId());
		if (bdInvAud == null)
			involucradoAudienciaDAO.create(invAud);

		notificacion.setInvolucrado(involucrado);
		notificacion.setAudiencia(audiencia);
		List<Notificacion> notificaciones = notificacionDAO
				.consultarNotificacionesPorAudienciaInvolucrado(
						audienciaDTO.getId(), involucradoDTO.getElementoId());
		int consecutivo = 1;
		if (notificaciones != null)
			consecutivo = notificaciones.size() + 1;
		notificacion.setConsecutivoNotificacion(String.valueOf(consecutivo));

		notificacion.setFolioNotificacion(generarFolioSolicitudService
				.generarFolioNotificacion());

		return notificacionDAO.create(notificacion);
	}

	/**
	 * M&eacute; para registrar el evento de tipo audiencia en la agenda
	 * de un funcionario
	 * @param funcionarioDTO, funcionario al que se le registrar&aacute; la audiencia.
	 * @param audienciaDTO,  audiencia que ser&aacute; registrada
	 * @throws NSJPNegocioException, Si ocurre alguna excepcion
	 */
	public Boolean registrarAudienciaEnAgendaDeFuncionario(
			FuncionarioDTO funcionarioDTO, AudienciaDTO audienciaDTO)
			throws NSJPNegocioException {

		if (funcionarioDTO == null || funcionarioDTO.getUsuario() == null
				|| funcionarioDTO.getClaveFuncionario() == null
				|| funcionarioDTO.getClaveFuncionario() <= 0L
				|| audienciaDTO == null
				|| audienciaDTO.getFechaEvento() == null
				|| audienciaDTO.getDuracionEstimada() == null
				|| audienciaDTO.getTipoAudiencia() == null
				|| audienciaDTO.getTipoAudiencia().getIdCampo() == null
				|| audienciaDTO.getTipoAudiencia().getIdCampo() <= 0L
				|| audienciaDTO.getEstatusAudiencia()== null
				|| audienciaDTO.getEstatusAudiencia().getIdCampo() == null
				|| audienciaDTO.getEstatusAudiencia().getIdCampo() <= 0L) {
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}

		Boolean respuesta=false;
		final UsuarioDTO usuarioDTO = funcionarioDTO.getUsuario();
		
		if(usuarioDTO == null){
			throw new NSJPNegocioException(CodigoError.FUNCIONARIOS_NO_DISPONILBES);
		}

		//Se crean los objetos
		TareaDTO tareaDTO = new TareaDTO();
		EventoCitaDTO eventoCitaDTO = new EventoCitaDTO();
		StringBuffer sbNombre = new StringBuffer();
		StringBuffer sbDescripcion = new StringBuffer();

		/**
		 * Se llenar&aacute;n los datos del eventoCitaDTO
		 */
		eventoCitaDTO.setUsuario(usuarioDTO);
		eventoCitaDTO.setFechaInicioEvento(audienciaDTO.getFechaEvento());
		eventoCitaDTO.setFechaFinEvento(audienciaDTO.getFechaHoraFin());

		if (audienciaDTO.getSala() != null) {

			StringBuffer sbUbicacion = new StringBuffer();

			sbUbicacion.append(SALA + audienciaDTO.getSala().getNombreSala());
			if(audienciaDTO.getSala().getDomicilioSala() != null && !audienciaDTO.getSala().getDomicilioSala().isEmpty()){
				sbUbicacion.append(DOMICILIO_SALA
						+ audienciaDTO.getSala().getDomicilioSala());				
			}
			if(audienciaDTO.getSala().getUbicacionSala() != null && !audienciaDTO.getSala().getUbicacionSala().isEmpty()){
				sbUbicacion.append(UBICACION_SALA
						+ audienciaDTO.getSala().getUbicacionSala());				
			}
			eventoCitaDTO.setDireccion(sbUbicacion.toString());
		}

		// Llenamos el campo asunto (en vista), que corresponde con el nombre
		// del evento
		sbNombre.append(ACTIVIDAD);

		if (audienciaDTO.getNumeroCausa() != null
				&& !audienciaDTO.getNumeroCausa().isEmpty()) {
			sbNombre.append(NUMERO_DE_CAUSA
					+ audienciaDTO.getNumeroCausa());
		}

		if (audienciaDTO.getFolioAudiencia() != null
				&& !audienciaDTO.getFolioAudiencia().isEmpty()) {
			sbNombre.append(FOLIO + audienciaDTO.getFolioAudiencia());
		}

		eventoCitaDTO.setNombreEvento(sbNombre.toString());	

		eventoCitaDTO.setTipoEvento(new ValorDTO(TipoEvento.TAREA.getValorId()));
		
		//LLenamos el tipo de audiencia si no tenemos el nombre del campo
		//esto es en caso que se invoque desde el WS
		Valor tipoAudienciaBd = valorDAO.read(audienciaDTO.getTipoAudiencia()
				.getIdCampo());

		if (tipoAudienciaBd == null) {
			throw new NSJPNegocioException(
					CodigoError.INFORMACION_PARAMETROS_ERRONEA);
		}

		audienciaDTO.setTipoAudiencia(ValorTransformer
				.transformar(tipoAudienciaBd));

		sbDescripcion.append(TIPO_DE_AUDIENCIA
				+ audienciaDTO.getTipoAudiencia().getValor());
		
		//LLenamos el estatus de audiencia
		Valor estatusAudienciaBd = valorDAO.read(audienciaDTO
				.getEstatusAudiencia().getIdCampo());

		if (estatusAudienciaBd == null) {
			throw new NSJPNegocioException(
					CodigoError.INFORMACION_PARAMETROS_ERRONEA);
		}
		audienciaDTO.setEstatusAudiencia(ValorTransformer
				.transformar(estatusAudienciaBd));
		
		sbDescripcion.append(ESTADO	+ audienciaDTO.getEstatusAudiencia().getValor());
		
		eventoCitaDTO.setDescripcionEvento(sbDescripcion.toString());
				
		eventoCitaDTO.setEsAlertaAlarma(Boolean.TRUE);
		
		/**
		 * Se llenar&aacute;n los datos del tareaDTO
		 */
		tareaDTO.setValor(new ValorDTO(TipoTarea.ACUDIR_AUDIENCIA.getValorId()));
		tareaDTO.setEventoCita(eventoCitaDTO);
		tareaDTO.setUsuario(usuarioDTO);
		tareaDTO.setIdFuncionario(funcionarioDTO.getClaveFuncionario());
		eventoCitaDTO.setTarea(tareaDTO);
		tareaDTO.setDiaTarea(eventoCitaDTO.getFechaInicioEvento());
		tareaDTO.setNtiempoReal(audienciaDTO.getDuracionEstimada().shortValue());

		TareaDTO tareaDTORegreso = asignarTareaFuncionarioService.asignarTareaFuncionario(tareaDTO);
		
		if(tareaDTORegreso != null){
			respuesta = true;
		}
		
		return respuesta;
	}
	 
	
	public Boolean enviarNotificacion(NotificacionDTO notificacionDTO,
			AudienciaDTO audienciaDTO, FuncionarioDTO funcionarioDTO)
			throws NSJPNegocioException {

		if (notificacionDTO == null
				|| notificacionDTO.getDocumentoId() == null
				|| notificacionDTO.getDocumentoId() <= 0L
				|| audienciaDTO == null
				|| audienciaDTO.getId() == null
				|| audienciaDTO.getId() <= 0L
				|| funcionarioDTO == null
				|| funcionarioDTO.getClaveFuncionario() == null
				|| funcionarioDTO.getClaveFuncionario() <= 0L
				|| funcionarioDTO.getInstitucion() == null
				|| funcionarioDTO.getInstitucion().getConfInstitucionId() == null
				|| funcionarioDTO.getInstitucion().getConfInstitucionId() <= 0L) {
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}

		Boolean respuesta = false;
		Audiencia audFromBD = audienciaDao.read(audienciaDTO.getId());

		if (audFromBD == null) {
			throw new NSJPNegocioException(CodigoError.AUDIENCIA_INEXISTENTE);
		}

		AudienciaDTO audienciaDtoBd = AudienciaTransformer
				.transformarDTO(audFromBD);

		// CUANDO EL FUNCIONARIO ES DE OTRA INSTITUCION
		if (funcionarioDTO.getInstitucion().getConfInstitucionId().longValue() != this.notificacionDAO
				.consultarInsitucionActual().getConfInstitucionId().longValue()) {

			FuncionarioExterno funcionarioExterno = funcionarioExternoDAO
					.read(funcionarioDTO.getClaveFuncionario());

			if (funcionarioExterno == null) {
				throw new NSJPNegocioException(
						CodigoError.FUNCIONARIO_INEXISTENTE);
			}

			logger.debug("Enviando la notificacion "
					+ notificacionDTO.getDocumentoId()
					+ " para el funcionario con clave="
					+ funcionarioExterno.getCveFuncionarioInstExt()
					+ " a la institucion"
					+ funcionarioExterno.getConfInstitucion()
							.getConfInstitucionId());

			Notificacion notificacionBd = this.notificacionDAO
					.read(notificacionDTO.getDocumentoId());

			NotificacionDTO notificacionDTOToSend = NotificacionTransformer
					.transformarNotificacion(notificacionBd);

			FuncionarioExternoDTO funcionarioExternoDTO = FuncionarioExternoTransformer
					.transformar(funcionarioExterno);

			respuesta = this.enviarNotificacionAOtraInstitucion(audienciaDtoBd,
					funcionarioExternoDTO, notificacionDTOToSend);
		}
		// CUANDO EL FUNCIONARIO ES DE LA MISMA INSTITUCION QUE EL NOTIFICADOR
		else {
			Funcionario funcionario = funDao.read(funcionarioDTO
					.getClaveFuncionario());

			if (funcionario == null) {
				throw new NSJPNegocioException(
						CodigoError.FUNCIONARIO_INEXISTENTE);
			}

			respuesta = this.registrarAudienciaEnAgendaDeFuncionario(
					FuncionarioTransformer.transformarFuncionario(funcionario),
					audienciaDtoBd);
		}

		return respuesta;
	}
	
	/**
	 * M&eacute;todo privado que env&iacute;a una notificaci&oacute;n de
	 * audiencia a un funcionario de otra instituci&oacute;n a traves de un Web
	 * Service.
	 * 
	 * @param audienciaDTO
	 *            , audiencia que se va a notificar
	 * @param FuncionarioExternoDTO
	 *            , funcionarioExterno al que se va a notificar
	 * @param notificacionDTO
	 *            , notificacion que ser&aacute; enviada al funcionario
	 * @return verdadero en caso de que se haya programado el evento en la
	 *         agenda del funcionario, falso en otro caso
	 * @throws NSJPNegocioException
	 */
	private Boolean enviarNotificacionAOtraInstitucion(AudienciaDTO audienciaDTO,
			FuncionarioExternoDTO funcionarioExternoDTO,NotificacionDTO notificacionDTO) throws NSJPNegocioException {
	
		if(audienciaDTO== null || funcionarioExternoDTO== null || notificacionDTO == null){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		 
		return clienteWs.enviarNotificacionAudienciaFuncionarioExterno(
				audienciaDTO, funcionarioExternoDTO, notificacionDTO);
	}

	
	@Override
	public Long guardarNotificacion(NotificacionDTO notificacionDTO,
			AudienciaDTO audienciaDTO, FuncionarioDTO funcionario)
			throws NSJPNegocioException {
		
		if (logger.isDebugEnabled()) {
			logger.debug("/**** SERVICIO PARA GUARDAR UN DOCUMENTO TIPO NOTIFICACION DE AUDIENCIA ****/");
		}

		/* Verificacion de parametros */
		if (notificacionDTO == null || audienciaDTO == null
				|| funcionario == null)
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);

		Funcionario fun = funcionarioDAO
				.read(funcionario.getClaveFuncionario());
		Audiencia audiencia = audienciaDao.read(audienciaDTO.getId());

		Notificacion notificacionBD = NotificacionTransformer
				.transformarNotificacion(notificacionDTO);
		/* Obligatorios de Docmento */
		notificacionBD.setNombreDocumento("NOTIFICACION DE AUDIENCIA");
		notificacionBD.setFechaCreacion(new Date());
		notificacionBD.setForma(formaDAO.read(Formas.NOTIFICACION_DE_AUDIENCIA
				.getValorId()));
		notificacionBD.setTipoDocumento(new Valor(TipoDocumento.NOTIFICACION
				.getValorId()));

		/* Obligatorios de Notificacion */
		FuncionarioAudiencia funcionarioAudiencia = new FuncionarioAudiencia(
				new FuncionarioAudienciaId(audienciaDTO.getId(),
						funcionario.getClaveFuncionario()), new Funcionario(
						funcionario.getClaveFuncionario()), new Audiencia(
						audienciaDTO.getId()));

		FuncionarioAudiencia bdInvAud = funcionarioAudienciaDAO
				.read(funcionarioAudiencia.getId());
		if (bdInvAud == null) {
			funcionarioAudienciaDAO.create(funcionarioAudiencia);
		}

		notificacionBD.setFuncionario(fun);
		notificacionBD.setAudiencia(audiencia);
		List<Notificacion> notificaciones = null;
		notificaciones = notificacionDAO
				.consultarNotificacionesPorAudienciaFuncionario(
						audienciaDTO.getId(), funcionario.getClaveFuncionario());
		int consecutivo = 1;
		if (notificaciones != null) {
			consecutivo = notificaciones.size() + 1;
		}
		notificacionBD.setConsecutivoNotificacion(String.valueOf(consecutivo));
		notificacionBD.setFolioNotificacion(generarFolioSolicitudService
				.generarFolioNotificacion());

		notificacionBD.setEsFisica(false);
		//La fecha se usa como fecha fecha en la que se entrega la notificacion
		//notificacionBD.setFechaCitado(new Date());
		
//		registrarEventoNotificacion(funcionario, notificacionBD);
		return notificacionDAO.create(notificacionBD);
	}
	

	@Override
	public Long guardarNotificacion(NotificacionDTO notificacionDTO,
			AudienciaDTO audienciaDTO,
			FuncionarioExternoDTO funcionarioExternoDTO)
			throws NSJPNegocioException {

		if (logger.isDebugEnabled()) {
			logger.debug("/**** SERVICIO PARA GUARDAR UN DOCUMENTO TIPO NOTIFICACION DE AUDIENCIA  A FUNCIONARIO EXTERNO****/");
		}

		// Verificacion de parametros
		if (notificacionDTO == null || audienciaDTO == null
				|| audienciaDTO.getId() <= 0L || funcionarioExternoDTO == null
				|| funcionarioExternoDTO.getFuncionarioExternoId() <= 0L) {
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}

		FuncionarioExternoAudienciaId funcionarioExternoAudienciaId = new FuncionarioExternoAudienciaId();
		funcionarioExternoAudienciaId.setAudienciaId(audienciaDTO.getId());
		funcionarioExternoAudienciaId
				.setFuncionarioExternoId(funcionarioExternoDTO
						.getFuncionarioExternoId());

		FuncionarioExternoAudiencia funcionarioExternoAudiencia = funcionarioExternoAudienciaDAO
				.read(funcionarioExternoAudienciaId);

		// Si no existe la relaci&oacute;n no se puede notificar al funcionario
		if (funcionarioExternoAudiencia == null) {
			throw new NSJPNegocioException(
					CodigoError.INFORMACION_PARAMETROS_ERRONEA);
		}

		Notificacion notificacionBD = NotificacionTransformer
				.transformarNotificacion(notificacionDTO);

		/* Obligatorios de Docmento */
		notificacionBD.setNombreDocumento("NOTIFICACION DE AUDIENCIA");
		notificacionBD.setFechaCreacion(new Date());
		notificacionBD.setForma(formaDAO.read(Formas.NOTIFICACION_DE_AUDIENCIA
				.getValorId()));
		notificacionBD.setTipoDocumento(new Valor(TipoDocumento.NOTIFICACION
				.getValorId()));

		notificacionBD.setFuncionarioExterno(new FuncionarioExterno(
				funcionarioExternoDTO.getFuncionarioExternoId()));

		notificacionBD.setAudiencia(new Audiencia(audienciaDTO.getId()));

		List<Notificacion> notificaciones = null;

		notificaciones = notificacionDAO
				.consultarNotificacionesPorAudienciaFuncionarioExterno(
						audienciaDTO.getId(),
						funcionarioExternoDTO.getFuncionarioExternoId());

		// consecutivo de notificacion
		int consecutivo = 1;

		if (notificaciones != null && !notificaciones.isEmpty()) {
			consecutivo = notificaciones.size() + 1;
		}
		
		notificacionBD.setConsecutivoNotificacion(String.valueOf(consecutivo));
		notificacionBD.setFolioNotificacion(generarFolioSolicitudService
				.generarFolioNotificacion());

		notificacionBD.setEsFisica(false);
		// Nota: La fecha se usa como fecha fecha en la que se entrega la notificacion
		// notificacionBD.setFechaCitado(new Date());
		// registrarEventoNotificacion(funcionario, notificacionBD);
		return notificacionDAO.create(notificacionBD);
	}


	@Override
	public Long guardarYEnviarNotificacionAMismaInstitucion(
			ExpedienteDTO expedienteDTO, DocumentoDTO documentoDTO)
			throws NSJPNegocioException {

		logger.info("/**** Servicio enviarNotificacionMismaInstitucion ****/");
		if (expedienteDTO == null
				|| expedienteDTO.getNumeroExpedienteId() == null
				|| expedienteDTO.getNumeroExpedienteId() < 0
				|| expedienteDTO.getUsuario() == null
				|| expedienteDTO.getUsuario().getAreaActual() == null
				|| expedienteDTO.getUsuario().getAreaActual().getAreaId() == null
				|| expedienteDTO.getUsuario().getFuncionario() == null
				|| expedienteDTO.getUsuario().getFuncionario()
						.getClaveFuncionario() == null || documentoDTO == null)
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		logger.info(" NumeroExpedienteId:"
				+ expedienteDTO.getNumeroExpedienteId());
		logger.info(" Usuario:" + expedienteDTO.getUsuario());
		logger.info(" Funcionario:"
				+ expedienteDTO.getUsuario().getFuncionario());

		// Buscar el documento por medio de la Actividad asociada a dicho
		// Expediente.
		// 1.- Buscar el expedienteID
		Expediente expediente = expedienteDAO
				.consultarExpedientePorIdNumerExpediente(expedienteDTO
						.getNumeroExpedienteId());

		// 2-. Obtener la ultima actividad asociada al expediente, dado que el
		// tipo de actividad es => Actividades.NOTIFICAR_AUDITORIA
		Long tipoActividad = Actividades.NOTIFICAR_AUDITORIA.getValorId();
		Actividad actividad = actividadDAO
				.consultarActividadPorExpedienteIdTipoActividad(
						expediente.getExpedienteId(), tipoActividad);

		// Obtener el documento para ser asigando a una notificacion
		// Documento documento =
		// documentoDAO.consultarDocumentoPorId(actividad.getDocumento().getDocumentoId());

		// Ingresar los elementos del documento en la notificacion
		Notificacion notificacion = new Notificacion();
		notificacion.setTextoParcial(documentoDTO.getTextoParcial());
		notificacion.setForma(FormaTransformer.transformarFormaDTO(documentoDTO
				.getFormaDTO()));
		notificacion
				.setArchivoDigital(ArchivoDigitalTransformer
						.transformarArchivoDigitalDTO(documentoDTO
								.getArchivoDigital()));
		notificacion.setFechaCreacion(new Date());
		notificacion.setNombreDocumento(documentoDTO.getNombreDocumento());
		if (documentoDTO.getTipoDocumentoDTO() != null
				&& documentoDTO.getTipoDocumentoDTO().getIdCampo() != null)
			notificacion.setTipoDocumento(new Valor(documentoDTO
					.getTipoDocumentoDTO().getIdCampo()));

		// Datos Propios de la Notificacion
		// Luga => Nombre del area del usuario firmado
		notificacion.setLugar(expedienteDTO.getUsuario().getAreaActual()
				.getNombre());
		// Motivo => Nombre de la actividad
		Valor actividadValor = valorDAO.read(Actividades.NOTIFICAR_AUDITORIA
				.getValorId());
		notificacion.setMotivo(actividadValor.getValor());

		// PersonaAutoriza => Nombre completo del Usuario Firmado
		notificacion.setPersonaAutoriza(expedienteDTO.getUsuario()
				.getFuncionario().getNombreCompleto());
		// Puesto Autoriza => Nombre del puesto usuario firmado
		if (expedienteDTO.getUsuario().getFuncionario().getPuesto() != null)
			notificacion.setPuestoAutoriza(expedienteDTO.getUsuario()
					.getFuncionario().getPuesto().getValor());

		// Obtener el Expediente auditado consultando la relacion de Visitaduria
		NumeroExpediente numCarpetaAuditoria = new NumeroExpediente(
				expedienteDTO.getNumeroExpedienteId());
		numCarpetaAuditoria.setEstatus(null);
		List<RelNumExpedienteAuditoria> relaciones = relNumExpedienteAuditoriaDAO
				.consultarRelNumeroExpedienteAuditoriaPorFiltro(null, null,
						numCarpetaAuditoria);
		// Obtener el Funcionario Auditado
		if (relaciones != null && !relaciones.isEmpty()
				&& relaciones.get(0).getNumeroExpediente() != null) {
			Funcionario ampAuditado = relaciones.get(0).getNumeroExpediente()
					.getFuncionario();
			JerarquiaOrganizacional departamentoAMP = ampAuditado.getArea();
			logger.info(" DepartamentoAMP:"
					+ departamentoAMP.getJerarquiaOrganizacionalId());

			// Coordinador del area auditado
			JerarquiaOrganizacional areaCoordinacion = jerarquiaOrganizacionalDAO
					.read(departamentoAMP.getJerarquiaOrganizacionalId());
			logger.info(" Area Coordinacion:"
					+ areaCoordinacion.getJerarquiaOrgResponsable()
							.getJerarquiaOrganizacionalId());
			List<Funcionario> listaCoordinadorDep = funcionarioDAO
					.consultarFuncionariosPorAreaYPuesto(areaCoordinacion
							.getJerarquiaOrgResponsable()
							.getJerarquiaOrganizacionalId(), null);
			if (listaCoordinadorDep != null && !listaCoordinadorDep.isEmpty()) {
				logger.info(" Numero de Coordinadores:"
						+ listaCoordinadorDep.size());
				Funcionario coordinadorDep = listaCoordinadorDep.get(0);
				// ClaveFuncionario => Clave de la persona a la que se le
				// mandara la notificacion.
				notificacion.setFuncionario(new Funcionario(coordinadorDep
						.getClaveFuncionario()));

				List<Notificacion> notificaciones = notificacionDAO
						.consultarNotificacionesXFuncionario(
								coordinadorDep.getClaveFuncionario(), null);
				logger.info(" notificaciones " + notificaciones);
				logger.info(" notificaciones " + notificaciones != null ? notificaciones
						.size() : "null");
				int consecutivo = 1;
				if (notificaciones != null)
					consecutivo = notificaciones.size() + 1;
				// Consecutivo de la notificacion correspondiente a la persona a
				// la que se le manda la notificacion (iClaveFuncionario)
				notificacion.setConsecutivoNotificacion(String
						.valueOf(consecutivo));
			} else
				notificacion.setConsecutivoNotificacion("0");
		}
		notificacion.setEstatus(new Valor(EstatusNotificacion.NO_ATENDIDA
				.getValorId()));

		// Folio de la notificacion conformado
		notificacion.setFolioNotificacion(generarFolioSolicitudService
				.generarFolioNotificacion());

		// Numero de Caso del Expediente
		if (expediente.getCaso() != null)
			notificacion.setNumeroCasoAsociado(expediente.getCaso()
					.getNumeroGeneralCaso());

		if (documentoDTO.getArchivoDigital() != null) {
			ArchivoDigital archivoDigital = ArchivoDigitalTransformer
					.transformarArchivoDigitalDTO(documentoDTO
							.getArchivoDigital());
			Long idAD = archivoDigitalDAO.create(archivoDigital);
			logger.info(" idAD:" + idAD);
			archivoDigital.setArchivoDigitalId(idAD);
			//Se necesita que genere el documento de forma definitiva
			notificacion.setEsGuardadoParcial(false);
			notificacion.setArchivoDigital(archivoDigital);
		}
		Long idNotificacion = notificacionDAO.create(notificacion);

		// Actualizar la actividad con el nuevo Id de Documento(Notificacion)
		// creado.
		actividad.setDocumento(new Documento(idNotificacion));
		actividadDAO.update(actividad);

		return idNotificacion;
	}
	
	
    /**
     * M&eacute;todo que lleva a cabo el registro de un evento dentro de la agenda para el funcionario
     * al que le ha sido enviada una notificaci&oacute;n.
     * @param funcionario - El funcionario al cual se le va a asignar el evento derivado de la 
     * 						notificaci&oacute;n
     * @param notificacion - La notificaci&oacute;n a partir de la cual se va a registrar el evento en 
     * 						 la agenda.
     */
//    private void registrarEventoNotificacion(FuncionarioDTO funcionario, Notificacion notificacion){
//    	DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//    	AgendaFuncionario agendaFuncionario = agendaFuncionarioDAO.obtenerAgendaFuncionario(funcionario.getClaveFuncionario());
//		/*Revisar existencia de Agenda*/
//		if(agendaFuncionario==null){
//			agendaFuncionario=new AgendaFuncionario();
//			agendaFuncionario.setFuncionario(new Funcionario(funcionario.getClaveFuncionario()));
//			agendaFuncionario.setDinicioAgenda(new Date());
//			Long idAgenda = agendaFuncionarioDAO.create(agendaFuncionario);
//			agendaFuncionario.setAgendaFuncionarioId(idAgenda);
//		}
//		
//		EventoCita evento = new EventoCita();
//		evento.setAgendaFuncionario(agendaFuncionario);
//		evento.setEstatus(new Valor(EstatusEventoCita.NO_ATENDIDO.getValorId()));
//		if (notificacion.getAudiencia() != null){
////			Tarea tarea = new Tarea();
////			tarea.setValor(new Valor(TipoTarea.ACUDIR_AUDIENCIA.getValorId()));
//			evento.setFechaInicioEvento(notificacion.getAudiencia().getFechaAudiencia());
//			evento.setFechaFinEvento(notificacion.getAudiencia().getFechaHoraFin());
//			String audiencia = "Audiencia";
//			StringBuilder nombreEventoAudiencia = new StringBuilder(audiencia);
//			StringBuilder descEventoAudiencia = new StringBuilder(audiencia);
//			if (notificacion.getAudiencia().getFolioAudiencia() != null){
//				nombreEventoAudiencia.append(" folio: "+notificacion.getAudiencia().getFolioAudiencia());
//				descEventoAudiencia.append(" folio: "+notificacion.getAudiencia().getFolioAudiencia());
//			}
//			if (notificacion.getAudiencia().getTipo() != null
//					&& notificacion.getAudiencia().getTipo().getValor() != null){
//				descEventoAudiencia.append(" del tipo: "+notificacion.getAudiencia().getTipo().getValor());				
//			}
//			if (notificacion.getAudiencia().getSala() != null 
//					&& notificacion.getAudiencia().getSala().getUbicacionSala() != null){
//				evento.setDireccion(notificacion.getAudiencia().getSala().getUbicacionSala());
//			}else if(notificacion.getAudiencia().getSalaTemporal() != null 
//					&& notificacion.getAudiencia().getSalaTemporal().getUbicacionSala() != null){
//				evento.setDireccion(notificacion.getAudiencia().getSalaTemporal().getUbicacionSala());
//			}else if (notificacion.getAudiencia().getSalaAudiencia() != null && 
//					notificacion.getAudiencia().getSalaAudiencia().getDomicilioSala() != null){
//				evento.setDireccion(notificacion.getAudiencia().getSalaAudiencia().getDomicilioSala());
//			}
//			
//			String strFechaAudiencia = sdf.format(notificacion.getAudiencia().getFechaAudiencia());
//			String programacion = " programada: " + strFechaAudiencia;
//			nombreEventoAudiencia.append(programacion);
//			descEventoAudiencia.append(programacion);
//			evento.setNombreEvento(nombreEventoAudiencia.toString());
//			evento.setDescripcionEvento(descEventoAudiencia.toString());
//			evento.setTieneAlarma(true);
////			evento.setTarea(tarea);
////			tarea.setEventoCita(evento);
//			eventoCitaDAO.create(evento);
//		}
//    }
	
	@Override
	public void actualizarNotificacion(NotificacionDTO notificacionDTO)throws NSJPNegocioException {

		if (logger.isDebugEnabled())
			logger.debug("/**** SERVICIO PARA ACTUALIZAR LAS NOTIFICACIONES****/");

		/* Verificacion de parametros */
		if (notificacionDTO == null || notificacionDTO.getDocumentoId() == null) {
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}

		Notificacion notificacion = notificacionDAO.read(notificacionDTO.getDocumentoId());
		
		notificacion = NotificacionTransformer
				.transformarNotificacionDTOupdate(notificacion,notificacionDTO);
						
		notificacionDAO.update(notificacion);
	}
}
