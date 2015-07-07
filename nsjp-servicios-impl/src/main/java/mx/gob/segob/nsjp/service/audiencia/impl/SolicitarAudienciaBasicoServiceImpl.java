/**
 * Nombre del Programa : SolicitarAudienciaBasicoServiceImpl.java
 * Autor                            : Emigdio
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 24/06/2011
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
package mx.gob.segob.nsjp.service.audiencia.impl;

import java.util.Date;
import java.util.List;

import mx.gob.segob.nsjp.comun.enums.calidad.Calidades;
import mx.gob.segob.nsjp.comun.enums.documento.TipoDocumento;
import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.enums.expediente.TipoExpediente;
import mx.gob.segob.nsjp.comun.enums.forma.Formas;
import mx.gob.segob.nsjp.comun.enums.funcionario.TipoEspecialidad;
import mx.gob.segob.nsjp.comun.enums.institucion.Areas;
import mx.gob.segob.nsjp.comun.enums.solicitud.EstatusSolicitud;
import mx.gob.segob.nsjp.comun.enums.solicitud.TiposSolicitudes;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.audiencia.AudienciaDAO;
import mx.gob.segob.nsjp.dao.audiencia.InvolucradoAudienciaDAO;
import mx.gob.segob.nsjp.dao.caso.CasoDAO;
import mx.gob.segob.nsjp.dao.catalogo.CatDiscriminateDAO;
import mx.gob.segob.nsjp.dao.catalogo.CatDistritoDAO;
import mx.gob.segob.nsjp.dao.expediente.ExpedienteDAO;
import mx.gob.segob.nsjp.dao.funcionario.FuncionarioAudienciaDAO;
import mx.gob.segob.nsjp.dao.funcionario.FuncionarioDAO;
import mx.gob.segob.nsjp.dao.involucrado.InvolucradoDAO;
import mx.gob.segob.nsjp.dao.solicitud.SolicitudAudienciaDAO;
import mx.gob.segob.nsjp.dto.audiencia.AudienciaDTO;
import mx.gob.segob.nsjp.dto.caso.CasoDTO;
import mx.gob.segob.nsjp.dto.catalogo.CatDistritoDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.configuracion.ConfInstitucionDTO;
import mx.gob.segob.nsjp.dto.expediente.DelitoPersonaDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteWSDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioWSDTO;
import mx.gob.segob.nsjp.dto.funcionarioexterno.FuncionarioExternoAudienciaDTO;
import mx.gob.segob.nsjp.dto.funcionarioexterno.FuncionarioExternoDTO;
import mx.gob.segob.nsjp.dto.institucion.AreaDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoWSDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudAudienciaBasicoWSDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;
import mx.gob.segob.nsjp.model.Caso;
import mx.gob.segob.nsjp.model.CatDiscriminante;
import mx.gob.segob.nsjp.model.CatDistrito;
import mx.gob.segob.nsjp.model.ConfInstitucion;
import mx.gob.segob.nsjp.model.Expediente;
import mx.gob.segob.nsjp.model.Forma;
import mx.gob.segob.nsjp.model.Funcionario;
import mx.gob.segob.nsjp.model.FuncionarioAudiencia;
import mx.gob.segob.nsjp.model.FuncionarioAudienciaId;
import mx.gob.segob.nsjp.model.FuncionarioExterno;
import mx.gob.segob.nsjp.model.Involucrado;
import mx.gob.segob.nsjp.model.InvolucradoAudiencia;
import mx.gob.segob.nsjp.model.InvolucradoAudienciaId;
import mx.gob.segob.nsjp.model.JerarquiaOrganizacional;
import mx.gob.segob.nsjp.model.NumeroExpediente;
import mx.gob.segob.nsjp.model.SolicitudAudiencia;
import mx.gob.segob.nsjp.model.Valor;
import mx.gob.segob.nsjp.service.audiencia.IngresarFuncionarioExternoAudienciaService;
import mx.gob.segob.nsjp.service.audiencia.SolicitarAudienciaBasicoService;
import mx.gob.segob.nsjp.service.audiencia.impl.transform.EventoTransformer;
import mx.gob.segob.nsjp.service.caso.RegistrarReplicaCasoService;
import mx.gob.segob.nsjp.service.catalogo.impl.transform.CatDistritoTransformer;
import mx.gob.segob.nsjp.service.expediente.ActualizarCarpetaDeInvestigacionService;
import mx.gob.segob.nsjp.service.expediente.AsignarNumeroExpedienteService;
import mx.gob.segob.nsjp.service.infra.impl.transform.enviarReplicaCaso.CasoWSDTOTransformer;
import mx.gob.segob.nsjp.service.infra.impl.transform.enviarcarpetainvestigacion.ExpedienteWSDTOTransformer;
import mx.gob.segob.nsjp.service.infra.impl.transform.recibiracusederecibodesolicituddedefensor.SolicitudWSDTOTransformer;
import mx.gob.segob.nsjp.service.infra.impl.transform.solicitudaudiencia.FuncionarioExternoWSDTOTransformer;
import mx.gob.segob.nsjp.service.solicitud.GenerarFolioSolicitudService;
import mx.gob.segob.nsjp.service.usuario.UsuarioService;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementación del servicio de negocio para registra la solicitud de una
 * audiencia con datos básicos, ya sea interna o deste otra área
 * 
 * @version 1.0
 * @author Emigdio Hernández
 * 
 */
@Service("solicitarAudienciaBasicoService")
@Transactional (propagation = Propagation.REQUIRED)
public class SolicitarAudienciaBasicoServiceImpl implements
		SolicitarAudienciaBasicoService {

	private final static Logger logger = Logger
			.getLogger(SolicitarAudienciaBasicoServiceImpl.class);
	@Autowired
	private AudienciaDAO audienciaDAO;
	@Autowired
	private SolicitudAudienciaDAO solicitudAudienciaDAO;
	@Autowired
	private CasoDAO casoDAO;
	@Autowired
	private ExpedienteDAO expedienteDAO;
	@Autowired
	private AsignarNumeroExpedienteService asignarNumExpService;
	@Autowired
	ActualizarCarpetaDeInvestigacionService actualizarCarpetaDeInvestigacionService;
	@Autowired
	private InvolucradoDAO involucradoDao;
	@Autowired
	private InvolucradoAudienciaDAO invoAudDao;
	@Autowired
	private FuncionarioDAO funcionarioDao;
	@Autowired
	private FuncionarioAudienciaDAO funAudDao;
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private CatDiscriminateDAO catDiscriminateDAO;
	@Autowired
	private IngresarFuncionarioExternoAudienciaService ingresarFuncionarioExternoAudienciaService;
	@Autowired
	private RegistrarReplicaCasoService registrarReplicaCasoService;
	@Autowired
	private GenerarFolioSolicitudService generarFolioService;
	@Autowired
	private CatDistritoDAO catDistritoDAO;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.gob.segob.nsjp.service.audiencia.SolicitarAudienciaBasicoService#
	 * registrarSolicitudAudienciaBasico
	 * (mx.gob.segob.nsjp.dto.solicitud.SolicitudAudienciaBasicoDTO)
	 */
	@Override
	public Long registrarSolicitudAudienciaBasico(
			SolicitudAudienciaBasicoWSDTO solicitudWs)
			throws NSJPNegocioException {

		logger.info("INICIA...... registrarSolicitudAudienciaBasico(...)");

		if (solicitudWs == null || solicitudWs.getDistritoId() == null
				|| solicitudWs.getDistritoId() <= 0L
				|| solicitudWs.getTribunalId() == null
				|| solicitudWs.getTribunalId() <= 0L
				|| solicitudWs.getClaveFuncionarioId() == null
				|| solicitudWs.getClaveFuncionarioId() <= 0L
				|| solicitudWs.getFolioSolicitud() == null
				|| solicitudWs.getFolioSolicitud().isEmpty()
				|| solicitudWs.getFuncionarioExternoSolicitante() == null
				|| solicitudWs.getCasoWSDTO() == null
				|| solicitudWs.getCasoWSDTO().getNumeroGeneralCaso() == null
				|| solicitudWs.getCasoWSDTO().getFechaApertura() == null
				|| solicitudWs.getNumeroCasoAsociado() == null
				|| solicitudWs.getNumeroCasoAsociado().trim().isEmpty()
				|| solicitudWs.getAudiencia().getTipoAudienciaId() == null
				|| solicitudWs.getAudiencia().getTipoAudienciaId() <= 0L) {

			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}

		logger.info("VERIFICANDO PARAMETROS............");
		logger.debug("Folio Solicitud::::" + solicitudWs.getFolioSolicitud());
		logger.debug("idDistrito:::::::::" + solicitudWs.getDistritoId());
		logger.debug("idTribunal:::::::::" + solicitudWs.getTribunalId());
		logger.debug("idClaveFuncionario:"
				+ solicitudWs.getClaveFuncionarioId());
		
		logger.info("Validar Caso Replicado: " + solicitudWs.getCasoWSDTO());
		CasoDTO casoDTO = CasoWSDTOTransformer.transformar(solicitudWs
				.getCasoWSDTO());
		
		Long idSolAud = 0L;

		// Buscar al usuario destinatario por clave funcionario
		UsuarioDTO usuarioDTO = usuarioService
				.consultarUsuarioPorClaveFuncionario(solicitudWs
						.getClaveFuncionarioId());

		/**
		 * Si no existe el usuario o no esta activo, no recibir la solicitud de
		 * audiencia
		 */
		if (usuarioDTO == null || !usuarioDTO.getEsActivo()) {
			throw new NSJPNegocioException(CodigoError.FUNCIONARIOS_NO_DISPONILBES);
		}

		SolicitudAudiencia solicitudAudiencia = null;
		NumeroExpediente numExpediente = null;

		/**
		 * Datos de la solicitud
		 */
		solicitudAudiencia = EventoTransformer
				.transformarSolicitudAudienciaBasico(solicitudWs);

		solicitudAudiencia.setTipoSolicitud(new Valor(
				TiposSolicitudes.AUDIENCIA.getValorId()));
		solicitudAudiencia.setEstatus(new Valor(EstatusSolicitud.ABIERTA
				.getValorId()));

		//Consultar el distrito
		CatDistrito catDistrito = catDistritoDAO.read(solicitudWs
				.getDistritoId());
		CatDistritoDTO catDistritoDTO = null;
		if (catDistrito != null) {
			catDistritoDTO = CatDistritoTransformer
					.transformarDistritoCompleto(catDistrito);
		}
		
		// Buscar expediente con el numero de caso asociado
		numExpediente = this.obtenerNumeroExpedienteDeCaso(
				casoDTO, usuarioDTO, catDistritoDTO);
		
		if (numExpediente == null || numExpediente.getExpediente() == null
				|| numExpediente.getExpediente().getExpedienteId() == null) {
			throw new NSJPNegocioException(CodigoError.NO_EXISTE_EXPEDIENTE);
		}

		solicitudAudiencia.setNumeroCasoAsociado(solicitudWs
				.getNumeroCasoAsociado());

		solicitudAudiencia.setNumeroExpediente(numExpediente);
		// TODO AGA CHECAR LA FORMA DE LA SOLICITUD
		solicitudAudiencia.setForma(new Forma(Formas.SOLICITUD.getValorId()));
		solicitudAudiencia.setNombreDocumento(StringUtils.EMPTY);
		solicitudAudiencia.setTipoDocumento(new Valor(
				TipoDocumento.SOLICITUD_DE_AUDIENCIA.getValorId()));
		solicitudAudiencia.setConfInstitucion(new ConfInstitucion(solicitudWs
				.getConfInstitucionId()));
		solicitudAudiencia.setFolioSolicitud(solicitudWs.getFolioSolicitud());

		/**
		 * Datos de la audiencia
		 */
		solicitudAudiencia.getAudiencia().setNumeroExpediente(numExpediente);
		//TODO verificar los consecutivos de las audiencias
		solicitudAudiencia.getAudiencia().setConsecutivo((short) 1);
		solicitudAudiencia.getAudiencia().setTipo(
				new Valor(solicitudWs.getAudiencia().getTipoAudienciaId()));
		solicitudAudiencia.getAudiencia().setEstatus(
				new Valor(solicitudWs.getAudiencia().getEstatusAudienciaId()));
		solicitudAudiencia.getAudiencia().setFechaAsignacionSala(solicitudWs.getAudiencia()
				.getFechaAsignacionSala());
		solicitudAudiencia.getAudiencia().setDuracionEstimada(solicitudWs.getAudiencia()
				.getDuracionEstimada());
		solicitudAudiencia.getAudiencia().setFolioAudiencia(generarFolioService.generarFolioAudiencia());
		
		/**
		 * Se crea la audiencia
		 */
		final Long idAud = audienciaDAO.create(solicitudAudiencia
				.getAudiencia());

		/**
		 * Guardamos/Actulizamos el funcionario externo y lo relacionamos a la audiencia
		 */
		logger.debug("ID DE LA AUDIENCIA GENERADA :: " + idAud);

		FuncionarioExternoAudienciaDTO funcionarioExternoAudienciaDTO = ingresarFuncionarioExternoAudienciaService
				.ingresarFuncionarioExternoAudiencia(
						FuncionarioExternoWSDTOTransformer
								.transformarDTO(solicitudWs
										.getFuncionarioExternoSolicitante()),
						new AudienciaDTO(idAud), true);
		
		if (funcionarioExternoAudienciaDTO == null
				|| funcionarioExternoAudienciaDTO
						.getFuncionarioExternoAudienciaIdDTO() == null
				|| funcionarioExternoAudienciaDTO
						.getFuncionarioExternoAudienciaIdDTO()
						.getFuncionarioExternoId() == null
				|| funcionarioExternoAudienciaDTO
						.getFuncionarioExternoAudienciaIdDTO()
						.getFuncionarioExternoId() <= 0L
				|| funcionarioExternoAudienciaDTO
						.getFuncionarioExternoAudienciaIdDTO().getAudienciaId() == null
				|| funcionarioExternoAudienciaDTO
						.getFuncionarioExternoAudienciaIdDTO().getAudienciaId() <= 0L) {
			throw new NSJPNegocioException(
					CodigoError.EJCUCION_OPERACION_ESTADO_INCORRECTO);
		}

		// Si se gurad&oacute; correctamente lo seteamos a la solicitud
		solicitudAudiencia.setFuncionarioSolExt(new FuncionarioExterno(
				funcionarioExternoAudienciaDTO
						.getFuncionarioExternoAudienciaIdDTO()
						.getFuncionarioExternoId()));

		/**
		 * Se crea la solicitudAudiencia
		 */
		idSolAud = solicitudAudienciaDAO.create(solicitudAudiencia);
		logger.debug("ID DE LA AUDIENCIA GENERADA :: " + idSolAud);

		ExpedienteWSDTO expWSD = new ExpedienteWSDTO();

		expWSD.setInvolucradosDTO(solicitudWs.getInvolucradosDTO());
		expWSD.setObjetosDTO(solicitudWs.getObjetosDTO());
		ExpedienteDTO expParamActualizar = ExpedienteWSDTOTransformer
				.expedienteWsdto2ExpedienteDto(expWSD);
		expParamActualizar.setExpedienteId(numExpediente.getExpediente()
				.getExpedienteId());
		//Se requiere el numero expedienteId para consultar las solicitudes asociadas
		expParamActualizar.setNumeroExpedienteId(numExpediente.getNumeroExpedienteId());

		// Transformar la relacion Delito-Persona-Victima
		List<DelitoPersonaDTO> relDelitoPersonaDto = SolicitudWSDTOTransformer
				.transformaWSDTO(solicitudWs.getRelacionesDelitoPersona());

		// Actualizar el expediente y sus elementos
		actualizarCarpetaDeInvestigacionService
				.actualizarExpedienteDePoderJudicial(expParamActualizar,
						relDelitoPersonaDto);

		// Se actualiza el discriminante del expediente
		Expediente expedienteBD = expedienteDAO.read(numExpediente
				.getExpediente().getExpedienteId());
		CatDiscriminante dicriminante = catDiscriminateDAO.read(solicitudWs
				.getTribunalId());
		expedienteBD.setDiscriminante(dicriminante);
		expedienteDAO.update(expedienteBD);

		// Asociar los involucrados a la solicitud de audiencia
		if (expWSD.getInvolucradosDTO() != null) {
			for (InvolucradoWSDTO invow : expWSD.getInvolucradosDTO()) {
				if (invow.getCalidad() != null
						&& invow.getCalidad().getValorIdCalidad() != null
						&& invow.getCalidad().getValorIdCalidad().longValue() != Calidades.DEFENSOR_PRIVADO
								.getValorId()
						&& invow.getCalidad().getValorIdCalidad().longValue() != Calidades.DEFENSOR_PUBLICO
								.getValorId()) {

					Involucrado invoPojo = involucradoDao
							.consultarInvolucradoPorFolioElemento(invow
									.getFolioElemento());
					InvolucradoAudiencia invoAud = new InvolucradoAudiencia();
					InvolucradoAudienciaId iaID = new InvolucradoAudienciaId();
					iaID.setaudienciaId(idAud);
					iaID.setInvolucradoId(invoPojo.getElementoId());
					invoAud.setId(iaID);
					this.invoAudDao.create(invoAud);
				} else if (invow.getCalidad() != null
						&& invow.getCalidad().getValorIdCalidad() != null) {
					// TODO AGA Los defensores se deberan guardar tambien como
					// funcionarios externos???
					guardarDefensoresyAsociarAAudiencia(invow, idAud);
				}
			}
		}

		logger.info(" REGRESANDO RESPUESTA:" + idSolAud);
		return idSolAud;
	}
		
	/**
	 * Metodo para guardar el defensor como un funcionario en PJ
	 * 
	 * @param involucradoWsdto
	 * @param idAudiencia
	 */
	private void guardarDefensoresyAsociarAAudiencia(
			InvolucradoWSDTO involucradoWsdto, Long idAudiencia) {

		Funcionario funLocal = funcionarioDao
				.obtenerFuncionarioPorNombreCompleto(involucradoWsdto
						.getNombreCompleto());

		if (funLocal == null
				&& !involucradoWsdto.getNombresDemograficos().isEmpty()) {

			funLocal = new Funcionario();

			String nombre = involucradoWsdto.getNombresDemograficos().get(0)
					.getNombre();
			String apPaterno = involucradoWsdto.getNombresDemograficos().get(0)
					.getApellidoPaterno();
			String apMaterno = involucradoWsdto.getNombresDemograficos().get(0)
					.getApellidoMaterno();

			if (nombre != null && !nombre.isEmpty()) {
				funLocal.setNombreFuncionario(nombre);

				if (apPaterno != null && !apPaterno.isEmpty()) {
					funLocal.setApellidoPaternoFuncionario(apPaterno);

					if (apMaterno != null && !apMaterno.isEmpty()) {
						funLocal.setApellidoMaternoFuncionario(apMaterno);
					}

					funLocal.setTipoEspecialidad(new Valor(
							TipoEspecialidad.DEFENSOR.getValorId()));

					funLocal.setClaveFuncionario(this.funcionarioDao
							.create(funLocal));
				}
			}
		}

		if (funLocal != null && funLocal.getClaveFuncionario() != null
				&& idAudiencia != null) {

			logger.debug("funLocal.getClaveFuncionario() :: "
					+ funLocal.getClaveFuncionario());

			final FuncionarioAudiencia funAud = new FuncionarioAudiencia();
			final FuncionarioAudienciaId faID = new FuncionarioAudienciaId();
			faID.setAudienciaId(idAudiencia);
			faID.setClaveFuncionario(funLocal.getClaveFuncionario());
			funAud.setId(faID);
			this.funAudDao.create(funAud);
		} else {
			logger.warn("El defensor no fue guardado ni se asocio a la audiencia:::::::");
		}
	}

	
	/**
	 * @param solicitudWs
	 * @return
	 */
	@SuppressWarnings("unused")
	@Deprecated
	private Funcionario crearFuncionario(
			SolicitudAudienciaBasicoWSDTO solicitudWs) {
		Funcionario funLocal;
		funLocal = new Funcionario();
		funLocal.setNombreFuncionario(solicitudWs.getSolicitante().getNombre());
		funLocal.setApellidoPaternoFuncionario(solicitudWs.getSolicitante()
				.getApellidoPaterno());
		funLocal.setApellidoMaternoFuncionario(solicitudWs.getSolicitante()
				.getApellidoMaterno());
		funLocal.setPuesto(new Valor(solicitudWs.getSolicitante().getPuestoId()));
		funLocal.setTipoEspecialidad(new Valor(solicitudWs.getSolicitante()
				.getTipoEspecialidadId()));
		funLocal.setEspecialidad(new Valor(solicitudWs.getSolicitante()
				.getEspecialidadId()));
		funLocal.setArea(new JerarquiaOrganizacional(solicitudWs
				.getSolicitante().getJerarquiaOrganizacionalId()));
		funLocal.setClaveFuncionario(this.funcionarioDao.create(funLocal));
		return funLocal;
	}

	
	/**
	 * Devuelve un NumeroExpediente en base a un caso, si el caso no existe lanza excepci&oacute;n,
	 * si el caso tiene expediente:
	 * 	-buscar si dicho expediente cuenta con n&uacute;mero de expediente, si no tiene, lo generamos
	 * si el caso NO tiene expediente:
	 *  -generamos el expediente y generamos el n&uacute;mero de expediente
	 *  
	 * @param numeroCasoAsociado, necesario para obtener el caso
	 * @param usuarioDTO, necesario para el sevicio asignarNumeroExpediente
	 * @param  catDistritoDTO Distrito para Defensoria
	 * @return NumeroExpediente, obtenido o generado
	 * @throws NSJPNegocioException
	 */
	private NumeroExpediente obtenerNumeroExpedienteDeCaso(
			CasoDTO casoDTO, UsuarioDTO usuarioDTO, CatDistritoDTO catDistritoDTO)
			throws NSJPNegocioException {

		if (casoDTO == null || casoDTO.getNumeroGeneralCaso() == null
				|| usuarioDTO == null) {
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}

		// VerificarExistencia de caso en caso de que no exista,
		// se registra un nuevo caso y expediente
		casoDTO = registrarReplicaCasoService.registrarCasoExpediente(casoDTO, catDistritoDTO);
		logger.info("Caso Replicado - encontrado: "+ casoDTO);
				
		// &Uacute;nico n&uacute;mero de caso
		Caso casoAsociado = casoDAO.read(casoDTO.getCasoId());
		Expediente exp = null;
		NumeroExpediente numExpediente = null;

		/**
		 * Buscamos el expediente asociado al caso, el cual debe ser
		 * &uacute;nico
		 */
		if (casoDTO.getExpedintesDTO() != null
				&& !casoDTO.getExpedintesDTO().isEmpty()) {

			ExpedienteDTO expedienteDTO = casoDTO.getExpedintesDTO().iterator().next();
			exp = expedienteDAO.read(expedienteDTO.getExpedienteId()); 

			// Verificamos si exite, el numero de expediente
			if (exp.getNumeroExpedientes() != null
					&& !exp.getNumeroExpedientes().isEmpty()) {
				
				for(NumeroExpediente numExpTemp:exp.getNumeroExpedientes()){
					//localizamos el unico expediente, tipo causa, el cual
					//se genera, antes que el de tipo ejecucion
					if (numExpTemp.getTipoExpediente() != null
							&& numExpTemp.getTipoExpediente().getValorId()
									.equals(TipoExpediente.CAUSA.getValorId())) {
						numExpediente = numExpTemp;
						break;
					}
				}
				
			} else {
				// Si no tiene ningun, n&uacte;mero de expediente, lo generamos
				ExpedienteDTO expParam = new ExpedienteDTO(
						exp.getExpedienteId());

				expParam.setUsuario(usuarioDTO);
				expParam.setTipoExpediente(new ValorDTO(TipoExpediente.CAUSA
						.getValorId()));
				expParam.setArea(new AreaDTO(Areas.ADMINISTRACION_JUDICIAL));

				expParam = this.asignarNumExpService
						.asignarNumeroExpediente(expParam);

				numExpediente = new NumeroExpediente();

				numExpediente.setNumeroExpedienteId(expParam
						.getNumeroExpedienteId());
				numExpediente.setExpediente(exp);
			}

		} else {
			/**
			 * Generar el expediente y el numero de expediente
			 */
			final Expediente expNuevo = new Expediente();

			expNuevo.setCaso(casoAsociado);
			expNuevo.setFechaCreacion(new Date());

			final Long expNuevoId = expedienteDAO.create(expNuevo);

			ExpedienteDTO expParam = new ExpedienteDTO(expNuevoId);

			expParam.setUsuario(usuarioDTO);
			expParam.setTipoExpediente(new ValorDTO(TipoExpediente.CAUSA
					.getValorId()));
			expParam.setArea(new AreaDTO(Areas.ADMINISTRACION_JUDICIAL));

			expParam = this.asignarNumExpService
					.asignarNumeroExpediente(expParam);

			numExpediente = new NumeroExpediente();

			numExpediente.setNumeroExpedienteId(expParam
					.getNumeroExpedienteId());
			numExpediente.setExpediente(expNuevo);
		}

		return numExpediente;
	}
	

	/**
	 * M&eacute;todo para obtener un funcionario externo DTO a partir de un funcionarioWSDTO
	 * @param funcionarioWSDTO
	 * @return
	 */
	@SuppressWarnings("unused")
	@Deprecated
	private FuncionarioExternoDTO transformarFuncionarioWSDTOAFuncionarioExternoDTO(
			FuncionarioWSDTO funcionarioWSDTO) {

		if (funcionarioWSDTO == null) {
			return null;
		}

		FuncionarioExternoDTO funcionarioExternoDTO = new FuncionarioExternoDTO();

		funcionarioExternoDTO.setNombre(funcionarioWSDTO.getNombre());

		funcionarioExternoDTO.setApellidoMaterno(funcionarioWSDTO
				.getApellidoMaterno());

		funcionarioExternoDTO.setApellidoPaterno(funcionarioWSDTO
				.getApellidoPaterno());

		funcionarioExternoDTO.setCveFuncionarioInstExt(funcionarioWSDTO
				.getClaveFuncionario());
		
		funcionarioExternoDTO.setEmail(funcionarioWSDTO.getEmail());
		
		funcionarioExternoDTO.setArea(funcionarioWSDTO.getNombreArea());
			
		if(funcionarioWSDTO.getConfInstitucionId() != null){
			funcionarioExternoDTO.setConfInstitucionDTO(new ConfInstitucionDTO(
					funcionarioWSDTO.getConfInstitucionId()));			
		}

		funcionarioExternoDTO.setPuesto(funcionarioWSDTO.getNombrePuesto());
		
		return funcionarioExternoDTO;
	}
}
