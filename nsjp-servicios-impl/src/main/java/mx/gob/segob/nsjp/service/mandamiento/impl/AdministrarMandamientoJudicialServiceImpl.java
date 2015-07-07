/**
 * Nombre del Programa		: AdministrarMandamientoJudicialServiceImp.java
 * Autor                    : AlejandroGA
 * Compania                 : Ultrasist
 * Proyecto                 : NSJP							Fecha: 15/08/2011
 * Marca de cambio			: N/A
 * Descripcion General		: Clase Impl para lo relacionado con mandamiento judicial
 * Programa Dependiente		: N/A
 * Programa Subsecuente		: N/A
 * Cond. de ejecucion       : N/A
 * Dias de ejecucion        : N/A                           Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor                    : N/A
 * Compania               	: N/A
 * Proyecto                 : N/A                           Fecha: N/A
 * Modificacion           	: N/A
 *------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.service.mandamiento.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import mx.gob.segob.nsjp.comun.enums.documento.EstatusMandamiento;
import mx.gob.segob.nsjp.comun.enums.documento.TipoDocumento;
import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.enums.forma.Formas;
import mx.gob.segob.nsjp.comun.enums.institucion.Areas;
import mx.gob.segob.nsjp.comun.enums.institucion.Instituciones;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.comun.mandamiento.EstatusMandamientoPersona;
import mx.gob.segob.nsjp.dao.actividad.ConfActividadDocumentoDAO;
import mx.gob.segob.nsjp.dao.archivo.ArchivoDigitalDAO;
import mx.gob.segob.nsjp.dao.audiencia.AudienciaDAO;
import mx.gob.segob.nsjp.dao.audiencia.ResolutivoDAO;
import mx.gob.segob.nsjp.dao.catalogo.ValorDAO;
import mx.gob.segob.nsjp.dao.documento.DocumentoDAO;
import mx.gob.segob.nsjp.dao.documento.MandamientoAdjuntosDAO;
import mx.gob.segob.nsjp.dao.documento.MandamientoDAO;
import mx.gob.segob.nsjp.dao.expediente.NumeroExpedienteDAO;
import mx.gob.segob.nsjp.dao.forma.FormaDAO;
import mx.gob.segob.nsjp.dao.institucion.ConfInstitucionDAO;
import mx.gob.segob.nsjp.dao.mandamiento.MandamientoPersonaDAO;
import mx.gob.segob.nsjp.dao.mandamiento.MandamientoPersonaDocumentoDAO;
import mx.gob.segob.nsjp.dto.caso.CasoDTO;
import mx.gob.segob.nsjp.dto.catalogo.CatalogoDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.documento.DocumentoDTO;
import mx.gob.segob.nsjp.dto.documento.MandamientoDTO;
import mx.gob.segob.nsjp.dto.expediente.DelitoPersonaDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.forma.FormaDTO;
import mx.gob.segob.nsjp.dto.mandamiento.FiltroMandamientoDTO;
import mx.gob.segob.nsjp.dto.mandamiento.FiltroMandamientoPersonaDTO;
import mx.gob.segob.nsjp.dto.mandamiento.MandamientoPersonaDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;
import mx.gob.segob.nsjp.model.Actividad;
import mx.gob.segob.nsjp.model.ArchivoDigital;
import mx.gob.segob.nsjp.model.Audiencia;
import mx.gob.segob.nsjp.model.ConfActividadDocumento;
import mx.gob.segob.nsjp.model.ConfInstitucion;
import mx.gob.segob.nsjp.model.Documento;
import mx.gob.segob.nsjp.model.Expediente;
import mx.gob.segob.nsjp.model.Forma;
import mx.gob.segob.nsjp.model.JerarquiaOrganizacional;
import mx.gob.segob.nsjp.model.Mandamiento;
import mx.gob.segob.nsjp.model.MandamientoAdjuntos;
import mx.gob.segob.nsjp.model.MandamientoAdjuntosId;
import mx.gob.segob.nsjp.model.MandamientoPersona;
import mx.gob.segob.nsjp.model.MandamientoPersonaDocumento;
import mx.gob.segob.nsjp.model.MandamientoPersonaDocumentoId;
import mx.gob.segob.nsjp.model.NumeroExpediente;
import mx.gob.segob.nsjp.model.Persona;
import mx.gob.segob.nsjp.model.Resolutivo;
import mx.gob.segob.nsjp.model.Valor;
import mx.gob.segob.nsjp.service.archivo.impl.transform.ArchivoDigitalTransformer;
import mx.gob.segob.nsjp.service.documento.impl.tranform.DocumentoTransformer;
import mx.gob.segob.nsjp.service.documento.impl.tranform.MandamientoJudicialTransformer;
import mx.gob.segob.nsjp.service.funcionario.impl.transform.FuncionarioTransformer;
import mx.gob.segob.nsjp.service.infra.ClienteGeneralService;
import mx.gob.segob.nsjp.service.infra.SSPClienteService;
import mx.gob.segob.nsjp.service.mandamiento.AdministrarMandamientoJudicialService;
import mx.gob.segob.nsjp.service.solicitud.GenerarFolioSolicitudService;
import mx.gob.segob.nsjp.service.usuario.impl.transformer.ValorTransformer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author AlejandroGA
 * @version 1.0
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class AdministrarMandamientoJudicialServiceImpl implements
		AdministrarMandamientoJudicialService {

	@Autowired
	private AudienciaDAO audienciaDAO;
	@Autowired
	private MandamientoDAO mandamientoDAO;
	@Autowired
	private GenerarFolioSolicitudService generarFolioService;
	@Autowired
	private ConfInstitucionDAO confInstitucionDAO;
	@Autowired
	private ResolutivoDAO resolutivoDAO;
	@Autowired
	private ConfActividadDocumentoDAO confActividadDocumentoDAO;
	@Autowired
	private NumeroExpedienteDAO numeroExpedienteDAO;
	@Autowired
	private MandamientoPersonaDAO mandamientoPersonaDAO;
	@Autowired
	private ValorDAO valorDAO;
	@Autowired
	private ArchivoDigitalDAO archivoDigitalDAO;
	@Autowired
	private DocumentoDAO documentoDAO;
	@Autowired
	private MandamientoPersonaDocumentoDAO mandamientoPersonaDocumentoDAO;
	@Autowired
	private SSPClienteService sspClientService;
	@Autowired
	private ClienteGeneralService clienteGeneralService;
	@Autowired
	private MandamientoAdjuntosDAO mandamientoAdjuntosDAO;
	@Autowired
	private FormaDAO formaDAO;

	
	
	private EstatusMandamientoPersona[] grupoEstatusInicial = { EstatusMandamientoPersona.NO_ATENDIDO };
	private List<EstatusMandamientoPersona> mandPerEstatusInicial = Arrays
			.asList(grupoEstatusInicial);

	private EstatusMandamientoPersona[] grupoEstatusMedio = { EstatusMandamientoPersona.EN_PROCESO };
	private List<EstatusMandamientoPersona> mandPerEstatusMedio = Arrays
			.asList(grupoEstatusMedio);

	private EstatusMandamientoPersona[] grupoEstatusFinal = {
			EstatusMandamientoPersona.ATENDIDO,
			EstatusMandamientoPersona.CANCELADO,
			EstatusMandamientoPersona.CONCLUIDO };
	private List<EstatusMandamientoPersona> mandPerEstatusFinal = Arrays
			.asList(grupoEstatusFinal);
	
	
	private static final int TAM_MAX_NOMBRE_ARCH = 60;
	private static final int TAM_MAX_EXTENSION_ARCH = 10;
	
	private final static Logger logger = Logger
			.getLogger(AdministrarMandamientoJudicialServiceImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.gob.segob.nsjp.service.mandamiento.AdministrarMandamientoJudicialService
	 * #
	 * generarMandamientoJudicial(mx.gob.segob.nsjp.dto.documento.MandamientoDTO
	 * )
	 */
	@Override
	public MandamientoDTO generarMandamientoJudicial(
			MandamientoDTO mandamientoDTO) throws NSJPNegocioException {

		logger.info("***SERVICIO PARA GENERAR UN MANDAMIENTO JUDICIAL***");
		if (mandamientoDTO == null
				|| mandamientoDTO.getTipoMandamiento() == null
				|| mandamientoDTO.getTipoMandamiento().getIdCampo() <= 0L
				|| mandamientoDTO.getDelitosPersona() == null
				|| mandamientoDTO.getDelitosPersona().isEmpty()
				|| mandamientoDTO.getUsuario() == null
				|| mandamientoDTO.getUsuario().getFuncionario() == null
				|| mandamientoDTO.getMandamientosPersona() == null
				|| mandamientoDTO.getMandamientosPersona().isEmpty()
				|| mandamientoDTO.getNumeroCausa() == null
				|| mandamientoDTO.getNumeroCausa().isEmpty()
				|| mandamientoDTO.getActividadDTO() == null
				|| mandamientoDTO.getActividadDTO().getActividadId() == null
				|| mandamientoDTO.getActividadDTO().getActividadId() <= 0L) {

			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}

		// Consultamos la institucion actual
		ConfInstitucion institucionActual = confInstitucionDAO
				.consultarInsitucionActual();

		if (institucionActual == null
				|| institucionActual.getConfInstitucionId() == null
				|| institucionActual.getConfInstitucionId() <= 0L) {
			throw new NSJPNegocioException(CodigoError.FALLA_OPERACION_BD);
		}

		if (institucionActual.getConfInstitucionId().equals(
				Instituciones.PJ.getValorId())) {
			/**
			 * VALIDAR EL RESOLUTIVO AL QUE SE ASOCIARA
			 */
			if (mandamientoDTO.getResolutivo() == null
					|| mandamientoDTO.getResolutivo().getResolutivoId() == null
					|| mandamientoDTO.getResolutivo().getResolutivoId() <= 0L) {

				throw new NSJPNegocioException(
						CodigoError.PARAMETROS_INSUFICIENTES);
			}
		} else {
			/**
			 * VALIDAR LOS DATOS OBLIGATORIOS
			 */
			if (mandamientoDTO.getEstatus() == null
					|| mandamientoDTO.getEstatus().getIdCampo() == null
					|| mandamientoDTO.getEstatus().getIdCampo() <= 0L
					|| mandamientoDTO.getFechaCreacion() == null
					|| mandamientoDTO.getExpedienteDTO() == null
					|| mandamientoDTO.getExpedienteDTO().getExpedienteId() == null
					|| mandamientoDTO.getJerarquiaOrganizacional() == null) {
				throw new NSJPNegocioException(
						CodigoError.PARAMETROS_INSUFICIENTES);
			}
		}

		// Audiencia donde se obtendra el expediente (PJ)
		Audiencia audiencia = null;
		// ConfActividad Documento
		ConfActividadDocumento confActividadDocumento = null;

		// Si la institucion actual es PJ
		if (institucionActual.getConfInstitucionId().equals(
				Instituciones.PJ.getValorId())) {

			Resolutivo resolutivoDB = resolutivoDAO.read(mandamientoDTO
					.getResolutivo().getResolutivoId());

			if (resolutivoDB == null || resolutivoDB.getAudiencia() == null
					|| resolutivoDB.getAudiencia().getAudienciaId() == null) {
				throw new NSJPNegocioException(
						CodigoError.INFORMACION_PARAMETROS_ERRONEA);
			}

			/**
			 * VERIFICAR QUE POR CADA UNA DE LAS RELACIONES DELITO PERSONA, NO
			 * HAYAN SIDO ASOCIADAS A UN MISMO TIPO DE MANDAMIENTO DENTRO DE LA
			 * MISMA AUDIENCIA CON ANTERIORIDAD
			 */
			for (DelitoPersonaDTO delPerAux : mandamientoDTO
					.getDelitosPersona()) {

				Boolean existenRDPAsociadas = mandamientoDAO
						.consultarExistenRelacionesDelitoPersonaPorMandamiento(
								resolutivoDB.getAudiencia().getAudienciaId(),
								mandamientoDTO.getTipoMandamiento()
										.getIdCampo(), delPerAux
										.getDelitoPersonaId());
				if (existenRDPAsociadas.equals(true)) {
					throw new NSJPNegocioException(
							CodigoError.UNA_O_MAS_RELACIONES_YA_ESTAN_ASOCIADAS_AL_TIPO_DE_MANDAMIENTO);
				}
			}

			// Obtener el id del expediente a traves de la audiencia
			audiencia = audienciaDAO.consultarAudienciaById(resolutivoDB
					.getAudiencia().getAudienciaId());

			if (audiencia == null || audiencia.getExpediente() == null) {
				throw new NSJPNegocioException(
						CodigoError.INFORMACION_PARAMETROS_ERRONEA);
			}

			/**
			 * GENERAR FOLIO MANDAMIENTO JUDICIAL
			 */
			mandamientoDTO
					.setFolioDocumento(generarFolioService
							.generarFolioMandamientoJudicial(mandamientoDTO
									.getTipoMandamiento().getIdCampo(),
									resolutivoDB.getTemporizador(),
									mandamientoDTO.getNumeroCausa()));
		}

		/**
		 * OBTENER CONF ACTIVIDAD DOCUMENTO
		 */
		confActividadDocumento = confActividadDocumentoDAO
				.consultaConfActividadDocumentoPorIdActividad(mandamientoDTO
						.getActividadDTO().getActividadId());

		// Validar confActividadDocumento
		if (confActividadDocumento == null
				|| confActividadDocumento.getForma() == null
				|| confActividadDocumento.getForma().getFormaId() == null
				|| confActividadDocumento.getTipoDocumento() == null
				|| confActividadDocumento.getTipoDocumento().getValorId() == null
				|| confActividadDocumento.getTipoDocumento().getValor() == null
				|| confActividadDocumento.getTipoActividad() == null
				|| confActividadDocumento.getTipoActividad().getValorId() == null
				|| confActividadDocumento.getTipoActividad().getValor() == null) {

			throw new NSJPNegocioException(
					CodigoError.INFORMACION_PARAMETROS_ERRONEA);
		}

		mandamientoDTO.setFormaDTO(new FormaDTO(confActividadDocumento
				.getForma().getFormaId()));
		mandamientoDTO.setTipoDocumentoDTO(new ValorDTO(confActividadDocumento
				.getTipoDocumento().getValorId()));
		mandamientoDTO.setNombreDocumento(confActividadDocumento
				.getTipoDocumento().getValor());

		// En este paso se transforman tambien los mandamientos-delitopersona
		Mandamiento mandamientoBD = MandamientoJudicialTransformer
				.transformarMandamientoDTO(mandamientoDTO);

		if (institucionActual.getConfInstitucionId().equals(
				Instituciones.PJ.getValorId())) {
			mandamientoBD.setEstatus(new Valor(
					EstatusMandamiento.SIN_DOCUMENTO_DE_CREACION.getValorId()));
			mandamientoBD.setFechaCreacion(new Date());

			mandamientoBD
					.setJerarquiaOrganizacional(new JerarquiaOrganizacional(
							Areas.DEPARTAMENTO_CAUSA.parseLong()));
		}

		/**
		 * GENERAR LA ACTIVIDAD ASOCIADA AL DOCUMENTO Y AL EXPEDIENTE
		 */
		Actividad actividad = new Actividad();

		actividad.setFuncionario(FuncionarioTransformer
				.transformarFuncionario(mandamientoDTO.getUsuario()
						.getFuncionario()));

		Valor tipoActividad = new Valor(confActividadDocumento
				.getTipoActividad().getValorId(), confActividadDocumento
				.getTipoActividad().getValor());

		actividad.setTipoActividad(tipoActividad);

		if (institucionActual.getConfInstitucionId().equals(
				Instituciones.PJ.getValorId())) {
			actividad.setFechaCreacion(new Date());
			actividad.setExpediente(audiencia.getExpediente());
		} else {
			// Setear el expediente obtenido del caso para PG
			actividad.setExpediente(new Expediente(mandamientoDTO
					.getExpedienteDTO().getExpedienteId()));
			actividad.setFechaCreacion(mandamientoDTO.getFechaCreacion());
		}

		mandamientoBD.setActividad(actividad);
		actividad.setDocumento(mandamientoBD);

		final Set<MandamientoPersona> mandamientosPersonaBD = new HashSet<MandamientoPersona>(
				0);

		/**
		 * GENERAMOS LOS MANDAMIENTOS PERSONA
		 */
		for (MandamientoPersonaDTO mandamientosPersona : mandamientoDTO
				.getMandamientosPersona()) {

			// cada probable responsable sin repetir este parametro debe estar
			// filtrado

			if (mandamientosPersona.getPersona() == null
					|| mandamientosPersona.getPersona().getElementoId() == null) {
				throw new NSJPNegocioException(
						CodigoError.PARAMETROS_INSUFICIENTES);
			}

			MandamientoPersona mandamientoPersona = new MandamientoPersona();
			mandamientoPersona.setMandamiento(mandamientoBD);
			mandamientoPersona.setPersona(new Persona(mandamientosPersona
					.getPersona().getElementoId()));
			mandamientoPersona.setEstatus(new Valor(
					EstatusMandamientoPersona.NO_ATENDIDO.getValorId()));

			// No se permite por negocio pero no se omite
			mandamientoPersona.setFechaEjecucion(mandamientosPersona
					.getFechaEjecucion());

			if (institucionActual.getConfInstitucionId().equals(
					Instituciones.PJ.getValorId())) {
				mandamientoPersona
						.setFolioInterInstitucional(generarFolioService
								.generarFolioInterInstitucionalMandamientoPersona());
			} else {
				if (mandamientosPersona.getFolioInterInstitucional() == null) {
					throw new NSJPNegocioException(
							CodigoError.PARAMETROS_INSUFICIENTES);
				}
				mandamientoPersona
						.setFolioInterInstitucional(mandamientosPersona
								.getFolioInterInstitucional());
			}
			mandamientosPersonaBD.add(mandamientoPersona);
		}
		mandamientoBD.setMandamientosPersona(mandamientosPersonaBD);

		// Generamos el archivo digital para las otras instituciones
		if (!institucionActual.getConfInstitucionId().equals(
				Instituciones.PJ.getValorId())) {
			archivoDigitalDAO.create(mandamientoBD.getArchivoDigital());
		}

		Long mandamientoId = mandamientoDAO.create(mandamientoBD);

		logger.info("MANDAMIENTO JUDICIAL GENERADO CON ID=" + mandamientoId);
		mandamientoDTO.setDocumentoId(mandamientoId);

		return mandamientoDTO;
	}

	@Override
	public List<MandamientoDTO> consultarMandamientoPorFiltro(
			FiltroMandamientoDTO filtroMandamientoDTO)
			throws NSJPNegocioException {

		if (filtroMandamientoDTO == null) {
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}

		List<MandamientoDTO> resultado = new ArrayList<MandamientoDTO>();

		ConfInstitucion institucionActual = mandamientoDAO
				.consultarInsitucionActual();
		List<Mandamiento> mandamientosBD = null;

		if (institucionActual != null
				&& institucionActual.getConfInstitucionId().equals(
						Instituciones.PGJ.getValorId())) {
			filtroMandamientoDTO.setIdInstitucion(institucionActual
					.getConfInstitucionId());
			mandamientosBD = mandamientoDAO
					.consultarMandamientoPorFiltro(filtroMandamientoDTO);
		} else {
			mandamientosBD = mandamientoDAO
					.consultarMandamientoPorFiltro(filtroMandamientoDTO);
		}

		for (Mandamiento loMandamientoBD : mandamientosBD) {
			MandamientoDTO loMandamientoDTO = MandamientoJudicialTransformer
					.transformarMandamiento(loMandamientoBD);
			ExpedienteDTO loExp = new ExpedienteDTO();
			CasoDTO loCasoDTO = new CasoDTO();

			if (institucionActual != null
					&& institucionActual.getConfInstitucionId().equals(
							Instituciones.PGJ.getValorId())) {
				// Se obtiene el numero de expediente y caso en base a la
				// actividad
				if (loMandamientoBD.getActividad() != null
						&& loMandamientoBD.getActividad().getExpediente() != null
						&& loMandamientoBD.getActividad().getExpediente()
								.getExpedienteId() != null) {
					List<NumeroExpediente> numerosExpedientes = numeroExpedienteDAO
							.consultarNumeroExpedientesXIdExpAreaDiscriminante(
									loMandamientoBD.getActividad()
											.getExpediente().getExpedienteId(),
									Areas.UNIDAD_INVESTIGACION.parseLong(),
									null);
					// Se obtiene el numero de expediente del area
					if (numerosExpedientes != null
							&& numerosExpedientes.size() > 0)
						loExp.setNumeroExpediente(numerosExpedientes.iterator()
								.next().getNumeroExpediente());
					if (loMandamientoBD.getActividad() != null
							&& loMandamientoBD.getActividad().getExpediente() != null
							&& loMandamientoBD.getActividad().getExpediente()
									.getCaso() != null) {
						loCasoDTO.setNumeroGeneralCaso(loMandamientoBD
								.getActividad().getExpediente().getCaso()
								.getNumeroGeneralCaso());
						loExp.setCasoDTO(loCasoDTO);
					}
				}

			} else {
				// Se obtiene el numero de expediente y caso en a la audiencia
				// asociada a un resolutivo
				if (loMandamientoBD.getResolutivo() != null
						&& loMandamientoBD.getResolutivo().getAudiencia() != null
						&& loMandamientoBD.getResolutivo().getAudiencia()
								.getNumeroExpediente() != null) {
					loExp.setNumeroExpediente(loMandamientoBD.getResolutivo()
							.getAudiencia().getNumeroExpediente()
							.getNumeroExpediente());
					if (loMandamientoBD.getResolutivo().getAudiencia()
							.getExpediente().getCaso() != null) {
						loCasoDTO.setCasoId(loMandamientoBD.getResolutivo()
								.getAudiencia().getExpediente().getCaso()
								.getCasoId());
						loCasoDTO.setNumeroGeneralCaso(loMandamientoBD
								.getResolutivo().getAudiencia().getExpediente()
								.getCaso().getNumeroGeneralCaso());
						loExp.setCasoDTO(loCasoDTO);
					}
				}
			}
			loMandamientoDTO.setExpedienteDTO(loExp);
			resultado.add(loMandamientoDTO);
		}
		return resultado;
	}

	@Override
	public MandamientoDTO consultarMandamientoPorId(Long mandamientoJudicialId)
			throws NSJPNegocioException {

		/**
		 * Validamos parametros
		 */
		if (mandamientoJudicialId == null || mandamientoJudicialId < 0) {
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}

		ExpedienteDTO expedienteMandamiento = new ExpedienteDTO();
		CasoDTO casoMandamiento = new CasoDTO();

		Mandamiento mandamientoBD = mandamientoDAO.read(mandamientoJudicialId);

		if (mandamientoBD == null) {
			throw new NSJPNegocioException(
					CodigoError.INFORMACION_PARAMETROS_ERRONEA);
		}

		ConfInstitucion institucionActual = mandamientoDAO
				.consultarInsitucionActual();

		MandamientoDTO mandamientoDTO = MandamientoJudicialTransformer
				.transformarMandamiento(mandamientoBD);

		if (institucionActual != null
				&& institucionActual.getConfInstitucionId().equals(
						Instituciones.PGJ.getValorId())) {
			// Se obtiene el numero de expediente y caso en base a la actividad
			if (mandamientoBD.getActividad() != null
					&& mandamientoBD.getActividad().getExpediente() != null
					&& mandamientoBD.getActividad().getExpediente()
							.getExpedienteId() != null) {
				expedienteMandamiento.setExpedienteId(mandamientoBD
						.getActividad().getExpediente().getExpedienteId());
				List<NumeroExpediente> numerosExpedientes = numeroExpedienteDAO
						.consultarNumeroExpedientesXIdExpAreaDiscriminante(
								mandamientoBD.getActividad().getExpediente()
										.getExpedienteId(),
								Areas.UNIDAD_INVESTIGACION.parseLong(), null);
				// Se obtiene el numero de expediente del area
				if (numerosExpedientes != null && numerosExpedientes.size() > 0)
					expedienteMandamiento
							.setNumeroExpediente(numerosExpedientes.iterator()
									.next().getNumeroExpediente());
				expedienteMandamiento.setNumeroExpedienteId(numerosExpedientes
						.iterator().next().getNumeroExpedienteId());
				if (mandamientoBD.getActividad() != null
						&& mandamientoBD.getActividad().getExpediente() != null
						&& mandamientoBD.getActividad().getExpediente()
								.getCaso() != null) {
					casoMandamiento.setNumeroGeneralCaso(mandamientoBD
							.getActividad().getExpediente().getCaso()
							.getNumeroGeneralCaso());
					expedienteMandamiento.setCasoDTO(casoMandamiento);
				}
			}

		} else {
			// Se obtiene el numero de expediente y caso en a la audiencia
			// asociada a un resolutivo
			if (mandamientoBD.getResolutivo() != null
					&& mandamientoBD.getResolutivo().getAudiencia() != null
					&& mandamientoBD.getResolutivo().getAudiencia()
							.getNumeroExpediente() != null) {
				expedienteMandamiento.setNumeroExpediente(mandamientoBD
						.getResolutivo().getAudiencia().getNumeroExpediente()
						.getNumeroExpediente());
				expedienteMandamiento.setNumeroExpedienteId(mandamientoBD
						.getResolutivo().getAudiencia().getNumeroExpediente()
						.getNumeroExpedienteId());
				expedienteMandamiento.setExpedienteId(mandamientoBD
						.getResolutivo().getAudiencia().getExpediente()
						.getExpedienteId());
				if (mandamientoBD.getResolutivo().getAudiencia()
						.getExpediente().getCaso() != null) {
					casoMandamiento.setCasoId(mandamientoBD.getResolutivo()
							.getAudiencia().getExpediente().getCaso()
							.getCasoId());
					casoMandamiento.setNumeroGeneralCaso(mandamientoBD
							.getResolutivo().getAudiencia().getExpediente()
							.getCaso().getNumeroGeneralCaso());
					expedienteMandamiento.setCasoDTO(casoMandamiento);
				}
			}
		}
		mandamientoDTO.setExpedienteDTO(expedienteMandamiento);
		return mandamientoDTO;

	}

	@Override
	public List<MandamientoPersonaDTO> consultarMandamientosPersonaPorFiltro(
			FiltroMandamientoPersonaDTO filtroMandamientoPersonaDTO)
			throws NSJPNegocioException {

		if (filtroMandamientoPersonaDTO == null
				|| (filtroMandamientoPersonaDTO.getEstatusId() == null
						&& filtroMandamientoPersonaDTO.getFechaEjecucion() == null
						&& filtroMandamientoPersonaDTO
								.getFolioInterInstitucional() == null
						&& filtroMandamientoPersonaDTO.getMandamientoId() == null
						&& filtroMandamientoPersonaDTO
								.getMandamientoPersonaId() == null
						&& filtroMandamientoPersonaDTO.getPersonaId() == null
						&& filtroMandamientoPersonaDTO.getTipoMandamiento() == null && filtroMandamientoPersonaDTO
						.getAudienciaId() == null)) {
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}

		List<MandamientoPersona> listaMandamientoPersona = mandamientoPersonaDAO
				.consultarMandamientosPersonaPorFiltro(filtroMandamientoPersonaDTO);

		List<MandamientoPersonaDTO> listaMandamientoPersonaDTO = null;

		if (listaMandamientoPersona != null
				&& !listaMandamientoPersona.isEmpty()) {

			listaMandamientoPersonaDTO = new ArrayList<MandamientoPersonaDTO>();

			for (MandamientoPersona mandamientoPersona : listaMandamientoPersona) {

				MandamientoPersonaDTO mandamientoPersonaDTO = MandamientoPersonaTransformer
						.transformar(mandamientoPersona);

				if (mandamientoPersona.getMandamientosPersonaDocumento() != null
						&& !mandamientoPersona
								.getMandamientosPersonaDocumento().isEmpty()) {
					// Maximo, tendra dos documentos de cambio de estatus, no
					// impacta performance
					for (MandamientoPersonaDocumento mandamientoPersonaDocumento : mandamientoPersona
							.getMandamientosPersonaDocumento()) {
						/*
						 * Obtenemos el documento de cambio de estatus
						 * correspondiente con el estatus actual del mandamiento
						 * persona
						 */
						if (mandamientoPersonaDocumento.getEstatus() != null
								&& mandamientoPersonaDocumento.getEstatus()
										.getValorId() != null
								&& mandamientoPersonaDocumento
										.getEstatus()
										.getValorId()
										.equals(mandamientoPersona.getEstatus()
												.getValorId())) {
							mandamientoPersonaDTO
									.setDocumentoEstatusActual(MandamientoPersonaDocumentoTransformer
											.transformarBasico(mandamientoPersonaDocumento));
						}
					}

				}

				// Se obtinene los estatus a los cuales puede transitar desde el
				// estado actual
				mandamientoPersonaDTO.setEstatusTransicion(this
						.obtenerEstatusTransicion(EstatusMandamientoPersona
								.getByValor(mandamientoPersonaDTO.getEstatus()
										.getIdCampo())));

				// Se agrega a la lista de estatus
				listaMandamientoPersonaDTO.add(mandamientoPersonaDTO);
			}
		}

		return listaMandamientoPersonaDTO;
	}

	/**
	 * M&eacute;todo que consulta la transici&oacute;n de estatus que se pueden
	 * efectuar con base al estatus actual del mandamiento persona
	 * 
	 * @param estatusActual
	 * @return listaCatalogo con la infomarcion de los estutatus al los que se
	 *         puede transitar, lista vacía en caso de que sean estatus
	 *         terminales
	 * @throws NSJPNegocioException
	 */
	private List<CatalogoDTO> obtenerEstatusTransicion(
			EstatusMandamientoPersona estatusActual)
			throws NSJPNegocioException {

		if (estatusActual == null || estatusActual.getValorId() == null) {
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}

		Set<CatalogoDTO> estatusSiguientes = new HashSet<CatalogoDTO>();

		if (mandPerEstatusInicial.contains(estatusActual)) {
			estatusSiguientes.addAll(this
					.convertEnumToCatalogo(mandPerEstatusMedio));
			return new ArrayList<CatalogoDTO>(estatusSiguientes);
		}

		if (mandPerEstatusMedio.contains(estatusActual)) {
			estatusSiguientes.addAll(this
					.convertEnumToCatalogo(mandPerEstatusFinal));
			return new ArrayList<CatalogoDTO>(estatusSiguientes);
		}

		return new ArrayList<CatalogoDTO>(estatusSiguientes);
	}

	/**
	 * M&eacute;todo que convierte una lista de estatus mandamiento persona en
	 * un catalogo de BD
	 * 
	 * @param listaEstatus
	 * @return catalos de estatus desde BD
	 */
	private Set<CatalogoDTO> convertEnumToCatalogo(
			List<EstatusMandamientoPersona> listaEstatus) {
		Set<CatalogoDTO> listaCatalogo = new HashSet<CatalogoDTO>();
		for (EstatusMandamientoPersona estatusAudiencia : listaEstatus) {
			Valor valor = valorDAO.read(estatusAudiencia.getValorId());
			listaCatalogo.add(new CatalogoDTO(valor.getValorId(), valor
					.getValor()));
		}
		return listaCatalogo;
	}

	@Override
	public Long actualizarMandamientoPersona(
			List<MandamientoPersonaDTO> listaMandamientosPersona,
			UsuarioDTO usuarioDTO, MandamientoDTO mandamientoDTO,
			DocumentoDTO documentoDTO) throws NSJPNegocioException {

		if (listaMandamientosPersona == null
				|| listaMandamientosPersona.isEmpty() || usuarioDTO == null
				|| usuarioDTO.getFuncionario() == null
				|| usuarioDTO.getFuncionario().getClaveFuncionario() == null
				|| usuarioDTO.getFuncionario().getClaveFuncionario() <= 0L
				|| usuarioDTO.getAreaActual() == null
				|| usuarioDTO.getAreaActual().getAreaId() == null
				|| mandamientoDTO == null
				|| mandamientoDTO.getDocumentoId() == null
				|| mandamientoDTO.getDocumentoId() <= 0L
				|| mandamientoDTO.getExpedienteDTO() == null
				|| mandamientoDTO.getExpedienteDTO().getExpedienteId() == null
				|| mandamientoDTO.getExpedienteDTO().getExpedienteId() <= 0L) {
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}

		// Si el documento es no nulo debe contener lo siguiente
		if (documentoDTO != null) {
			if (documentoDTO.getJerarquiaOrganizacional() == null
					|| documentoDTO.getArchivoDigital() == null
					|| documentoDTO.getFechaCreacion() == null) {
				logger.error("EL DOCUMENTO NO CUENTA CON JERARQUIA ORG O ARCHIVO DIGITAL O FECHA CREACION");
				throw new NSJPNegocioException(
						CodigoError.PARAMETROS_INSUFICIENTES);
			}
		}

		/**
		 * SE GENERA LOS CAMBIOS DE ESTATUS
		 */
		List<MandamientoPersona> listaMandamientoPersona = new ArrayList<MandamientoPersona>();

		for (MandamientoPersonaDTO mandamientoPersonaDTO : listaMandamientosPersona) {

			if (mandamientoPersonaDTO.getMandamientoPersonaId() == null
					|| mandamientoPersonaDTO.getMandamientoPersonaId() <= 0L
					|| mandamientoPersonaDTO.getEstatus() == null
					|| mandamientoPersonaDTO.getEstatus().getIdCampo() == null
					|| mandamientoPersonaDTO.getEstatus().getIdCampo() <= 0L) {
				throw new NSJPNegocioException(
						CodigoError.PARAMETROS_INSUFICIENTES);
			}

			MandamientoPersona mandamientoPersonaBD = mandamientoPersonaDAO
					.read(mandamientoPersonaDTO.getMandamientoPersonaId());

			/*
			 * Validar que el mandamientoPersona no se encuentre en un estatus
			 * terminal, ya quede ser asi, no se le puede generar otro documento
			 * de cambio de estatus ademas de que el estatus actual no puede ser
			 * igual al estatus al que se desea actualizar
			 */
			if (mandamientoPersonaBD == null
					|| mandamientoPersonaBD.getEstatus() == null
					|| mandamientoPersonaBD.getEstatus().getValorId() == null
					|| mandamientoPersonaBD
							.getEstatus()
							.getValorId()
							.equals(EstatusMandamientoPersona.ATENDIDO
									.getValorId())
					|| mandamientoPersonaBD
							.getEstatus()
							.getValorId()
							.equals(EstatusMandamientoPersona.CANCELADO
									.getValorId())
					|| mandamientoPersonaBD
							.getEstatus()
							.getValorId()
							.equals(EstatusMandamientoPersona.CONCLUIDO
									.getValorId())
			/*
			 * Esta linea se comenta, para que se puedan recibir documentos de
			 * los mismos cambios de estatus, dado a que no se ha especificado
			 * esa parte del  flujo.
			 * || mandamientoPersonaBD .getEstatus().getValorId() .equals(mandamientoPersonaDTO.getEstatus().getIdCampo())
			 */
			) {
				throw new NSJPNegocioException(
						CodigoError.INFORMACION_PARAMETROS_ERRONEA);
			}

			mandamientoPersonaBD.setEstatus(ValorTransformer
					.transformar(mandamientoPersonaDTO.getEstatus()));

			if (mandamientoPersonaDTO.getFechaEjecucion() != null) {
				mandamientoPersonaBD.setFechaEjecucion(mandamientoPersonaDTO
						.getFechaEjecucion());
			}

			listaMandamientoPersona.add(mandamientoPersonaBD);
		}

		/**
		 * GENERAMOS EL DOCUMENTO DE CAMBIO DE ESTATUS
		 */
		// Leemos el mandamiento para obtener el ecxpediente
		Mandamiento mandamientoBD = mandamientoDAO.read(mandamientoDTO
				.getDocumentoId());

		if (mandamientoBD == null) {
			throw new NSJPNegocioException(
					CodigoError.INFORMACION_PARAMETROS_ERRONEA);
		}

		// Obtenemos el confActividadDocumento
		ConfActividadDocumento confActividadDocumento = confActividadDocumentoDAO
				.read(mx.gob.segob.nsjp.comun.enums.actividad.ConfActividadDocumento.GENERAR_DOCUMENTO_DE_CAMBIO_DE_ESTATUS_DE_MANDAMIENTO_JUDICIAL
						.getValorId());

		if (confActividadDocumento == null
				|| confActividadDocumento.getForma() == null
				|| confActividadDocumento.getForma().getFormaId() == null
				|| confActividadDocumento.getTipoDocumento() == null
				|| confActividadDocumento.getTipoDocumento().getValorId() == null
				|| confActividadDocumento.getTipoDocumento().getValor() == null
				|| confActividadDocumento.getTipoActividad() == null
				|| confActividadDocumento.getTipoActividad().getValorId() == null
				|| confActividadDocumento.getTipoActividad().getValor() == null) {

			throw new NSJPNegocioException(
					CodigoError.INFORMACION_PARAMETROS_ERRONEA);
		}

		// Generamos el documento
		Documento documentoCambioEstatus = new Documento();

		documentoCambioEstatus.setForma(new Forma(confActividadDocumento
				.getForma().getFormaId()));
		documentoCambioEstatus.setTipoDocumento(new Valor(
				confActividadDocumento.getTipoDocumento().getValorId()));
		documentoCambioEstatus.setNombreDocumento(confActividadDocumento
				.getTipoDocumento().getValor());
		
		if (documentoDTO != null) {
			documentoCambioEstatus.setFechaCreacion(documentoDTO
					.getFechaCreacion());

			documentoCambioEstatus
					.setJerarquiaOrganizacional(new JerarquiaOrganizacional(
							documentoDTO.getJerarquiaOrganizacional()));

			documentoCambioEstatus.setFolioDocumento(documentoDTO
					.getFolioDocumento());
			
			documentoCambioEstatus.setArchivoDigital(ArchivoDigitalTransformer
					.transformarArchivoDigitalDTO(documentoDTO
							.getArchivoDigital()));
			
			documentoCambioEstatus.setEsGuardadoParcial(false);
			documentoCambioEstatus.setEsEnviado(true);
			
			archivoDigitalDAO.create(documentoCambioEstatus.getArchivoDigital());

		} else {
			documentoCambioEstatus.setFechaCreacion(new Date());

			documentoCambioEstatus
					.setJerarquiaOrganizacional(new JerarquiaOrganizacional(
							usuarioDTO.getAreaActual().getAreaId()));

			documentoCambioEstatus.setFolioDocumento(generarFolioService
					.generarFoliodDocumento());
		}

		Actividad actividad = new Actividad();

		actividad.setFuncionario(FuncionarioTransformer
				.transformarFuncionario(usuarioDTO.getFuncionario()));

		// Obtenemos el expediente asociado al mandamiento
		actividad.setExpediente(new Expediente(mandamientoDTO
				.getExpedienteDTO().getExpedienteId()));

		Valor tipoActividad = new Valor(confActividadDocumento
				.getTipoActividad().getValorId(), confActividadDocumento
				.getTipoActividad().getValor());

		actividad.setTipoActividad(tipoActividad);
		actividad.setFechaCreacion(new Date());
		actividad.setDocumento(documentoCambioEstatus);

		// seteamos la actividad
		documentoCambioEstatus.setActividad(actividad);

		documentoDAO.create(documentoCambioEstatus);

		// Guardar los mandamiento persona documento
		for (MandamientoPersona mandamientoPersona : listaMandamientoPersona) {
			// Creamos el nuevo objeto mandamiento persona documento
			MandamientoPersonaDocumento mandamientoPersonaDocumento = new MandamientoPersonaDocumento();

			mandamientoPersonaDocumento.setDocumento(documentoCambioEstatus);
			// /Checar estatus que se pondra
			mandamientoPersonaDocumento.setEstatus(mandamientoPersona
					.getEstatus());
			mandamientoPersonaDocumento
					.setMandamientoPersona(mandamientoPersona);

			// generamos la llave
			MandamientoPersonaDocumentoId mandamientoPersonaDocumentoId = new MandamientoPersonaDocumentoId();

			mandamientoPersonaDocumentoId.setDocumentoId(documentoCambioEstatus
					.getDocumentoId());
			mandamientoPersonaDocumentoId
					.setMandamientoPersonaId(mandamientoPersona
							.getMandamientoPersonaId());

			mandamientoPersonaDocumento
					.setMandamientoPersonaDocumentoId(mandamientoPersonaDocumentoId);

			mandamientoPersonaDocumentoDAO.create(mandamientoPersonaDocumento);

		}

		// Actualizamos los estatus y la fecha de ejecucion
		mandamientoPersonaDAO.saveOrUpdateAll(listaMandamientoPersona);

		/**
		 * ACTUALIZAR EL ESTATUS DEL MANDAMIENTO
		 */
		if (documentoDTO == null){
			mandamientoBD.setEstatus(new Valor(
					EstatusMandamiento.SIN_DOCUMENTO_DE_ESTATUS.getValorId()));
			mandamientoDAO.update(mandamientoBD);
		}else{
			//Se calcula el estatus en el que debe quedar el mandamiento
			actualizarEstatusMandamiento(mandamientoDTO,true);
			
		}
		return documentoCambioEstatus.getDocumentoId();
	}

	@Override
	public void enviarMandamientoJudicial(MandamientoDTO mandamientoDTO)
			throws NSJPNegocioException {

		if (mandamientoDTO == null || mandamientoDTO.getDocumentoId() == null
				|| mandamientoDTO.getDocumentoId() <= 0L) {
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}

		// Consultamos el mandamiento
		Mandamiento mandamientoBD = mandamientoDAO.read(mandamientoDTO
				.getDocumentoId());

		if (mandamientoBD == null) {
			logger.info("No existe el mandamiento con mandamientoId.....:"
					+ mandamientoDTO.getDocumentoId());
			throw new NSJPNegocioException(
					CodigoError.INFORMACION_PARAMETROS_ERRONEA);
		}

		mandamientoDTO = MandamientoJudicialTransformer
				.transformarMandamiento(mandamientoBD);

		sspClientService.enviarMandamiento(mandamientoDTO);

		mandamientoDTO.setEstatus(new ValorDTO(EstatusMandamiento.NO_ATENDIDO
				.getValorId()));
		this.actualizarEstatusMandamiento(mandamientoDTO, false);
	}

	@Override
	public void actualizarEstatusMandamiento(MandamientoDTO mandamientoDTO,
			Boolean esCalcularEstatus) throws NSJPNegocioException {

		if (mandamientoDTO == null || mandamientoDTO.getDocumentoId() == null) {
			logger.error("MANDAMIENTO O MANDAMIENTO ID NULO****");
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		} else if (esCalcularEstatus == null || esCalcularEstatus.equals(false)) {
			if (mandamientoDTO.getEstatus() == null
					|| mandamientoDTO.getEstatus().getIdCampo() == null) {
				logger.error("NO SE PROPORCIONA ESTATUS A ACTUALIZAR****");
				throw new NSJPNegocioException(
						CodigoError.PARAMETROS_INSUFICIENTES);
			}
		}

		Mandamiento mandamientoBD = mandamientoDAO.read(mandamientoDTO
				.getDocumentoId());

		if (mandamientoBD == null) {
			logger.error("NO SE ENCONTRO EL MANDAMIENTO CON ID****"
					+ mandamientoDTO.getDocumentoId());
			throw new NSJPNegocioException(
					CodigoError.INFORMACION_PARAMETROS_ERRONEA);
		}

		if (esCalcularEstatus == null || esCalcularEstatus.equals(false)) {
			mandamientoBD.setEstatus(ValorTransformer
					.transformar(mandamientoDTO.getEstatus()));
		} else {

			if (mandamientoBD.getMandamientosPersona() != null
					&& !mandamientoBD.getMandamientosPersona().isEmpty()) {

				Integer tamanioListaMandPer = mandamientoBD
						.getMandamientosPersona().size();
				Integer contEstatusInicial = 0;
				Integer contEstatusFinal = 0;
				Valor estatusCalculado = new Valor();

				for (MandamientoPersona mandamientoPersona : mandamientoBD
						.getMandamientosPersona()) {
					if (mandamientoPersona.getEstatus() == null
							|| mandamientoPersona.getEstatus().getValorId() == null) {
						throw new NSJPNegocioException(
								CodigoError.INFORMACION_PARAMETROS_ERRONEA);
					}

					EstatusMandamientoPersona estatusMandamientoPersona = EstatusMandamientoPersona
							.getByValor(mandamientoPersona.getEstatus()
									.getValorId());
					if (mandPerEstatusInicial
							.contains(estatusMandamientoPersona)) {
						contEstatusInicial++;
					} else if (mandPerEstatusFinal
							.contains(estatusMandamientoPersona)) {
						contEstatusFinal++;
					}

				}

				if (tamanioListaMandPer == contEstatusInicial) {
					estatusCalculado.setValorId(EstatusMandamiento.NO_ATENDIDO
							.getValorId());
				} else if (tamanioListaMandPer == contEstatusFinal) {
					estatusCalculado.setValorId(EstatusMandamiento.ATENDIDO
							.getValorId());
				} else {
					estatusCalculado.setValorId(EstatusMandamiento.EN_PROCESO
							.getValorId());
				}

				mandamientoBD.setEstatus(estatusCalculado);
			}
		}
		mandamientoDAO.update(mandamientoBD);
	}

	@Override
	public void enviarDocumentoCambioEstatusMandamiento(
			MandamientoDTO mandamientoDTO, DocumentoDTO documentoDTO)
			throws NSJPNegocioException {

		if ((mandamientoDTO == null || mandamientoDTO.getDocumentoId() == null)) {
			logger.error("mandamientoDTO o mandamientoDTO.getDocumentoId() NULOS ****");
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}

		/**
		 * Se obtiene el mandamiento que sera enviado
		 */
		Mandamiento mandamientoBD = mandamientoDAO.read(mandamientoDTO
				.getDocumentoId());

		if (mandamientoBD == null) {
			logger.error("No existe el mandamiento con id="
					+ mandamientoDTO.getDocumentoId());
			throw new NSJPNegocioException(
					CodigoError.INFORMACION_PARAMETROS_ERRONEA);
		}

		/**
		 * Si no se cuenta con el Id del documento, se buscara por mandamiento
		 */
		if (documentoDTO == null || documentoDTO.getDocumentoId() == null || documentoDTO.getDocumentoId() <= 0L) {

			// Etiqueta para romper el for en caso de encuentre el primer
			// documento no enviado
			mandamiento:
			// Obtenemos los mandamiento persona del mandamiento
			for (MandamientoPersona mandamientoPersona : mandamientoBD
					.getMandamientosPersona()) {
				// Por cada mandamiento persona obtenido, buscamos sus
				// mandamiento persona documento asoiados
				for (MandamientoPersonaDocumento mandamientoPersonaDocumento : mandamientoPersona
						.getMandamientosPersonaDocumento()) {
					// por cada mandamiento persona documento obtenemos el
					// documento que lo cambio de estatus
					Documento documentoCambioEstatus = mandamientoPersonaDocumento
							.getDocumento();
					// Si el documento no es parcial y el documento no ha sido
					// enviado, es el documento que buscamos
					if (documentoCambioEstatus != null
							&& (documentoCambioEstatus.getEsEnviado() == null || documentoCambioEstatus
									.getEsEnviado().equals(false))
							&& (documentoCambioEstatus.getEsGuardadoParcial() == null || documentoCambioEstatus
									.getEsGuardadoParcial().equals(false))) {
						// Se encontro el primer documento no enviado de cambio
						// de estatus
						documentoDTO = new DocumentoDTO();
						// Obtenemos el id y lo guandamos
						documentoDTO.setDocumentoId(documentoCambioEstatus
								.getDocumentoId());
						// rompemos los ciclos
						break mandamiento;
					}
				}
			}
		}

		// A este punto debemos tener el documento que sera enviado
		if (documentoDTO == null || documentoDTO.getDocumentoId() == null) {
			logger.error("Imposible obtener un documento de cambio de estatus*****");
			throw new NSJPNegocioException(
					CodigoError.INFORMACION_PARAMETROS_ERRONEA);
		}

		/**
		 * Consultamos los mandamientos persona que vamos a enviar de acuerdo al
		 * documento de cambio de estatus obtenido en el paso anterior o el
		 * recibido como parametro
		 */
		List<MandamientoPersona> listaMandamientosPersonaRelacionados = mandamientoPersonaDocumentoDAO
				.consultarMandamientosPersonaPorDocumentoId(documentoDTO
						.getDocumentoId());

		if (listaMandamientosPersonaRelacionados == null
				|| listaMandamientosPersonaRelacionados.isEmpty()) {
			logger.error("El documento no tiene asociados mandamientos persona****");
			throw new NSJPNegocioException(
					CodigoError.INFORMACION_PARAMETROS_ERRONEA);
		}

		/**
		 * Consultamos el documento de cambio de estatus con su archivo digital,
		 * para ser enviado
		 */
		Documento documentoBDSend = documentoDAO.read(documentoDTO
				.getDocumentoId());

		/**
		 * Consultamos la institucion actual para obtener la institucion de
		 * destino
		 */
		ConfInstitucion institucionActual = confInstitucionDAO
				.consultarIntitucionActual();

		if (institucionActual == null
				|| institucionActual.getConfInstitucionId() == null) {
			logger.error("Imposible obtener la institucion actual***");
			throw new NSJPNegocioException(CodigoError.FALLA_OPERACION_BD);
		}

		// Obtenemos la institucion Destino
		Instituciones institucionSend = null;

		if (institucionActual.getConfInstitucionId().equals(
				Instituciones.PJ.getValorId())) {

			institucionSend = Instituciones.getByValor(Instituciones.PGJ
					.getValorId());
		} else {
			institucionSend = Instituciones.getByValor(Instituciones.PJ
					.getValorId());
		}

		// Transformamos el documento
		DocumentoDTO DocumentoDTOSend = DocumentoTransformer
				.transformarDocumento(documentoBDSend);

		// Transformamos los datos necesarios del mandamiento
		MandamientoDTO MandamientoDTOSend = new MandamientoDTO();

		MandamientoDTOSend.setDocumentoId(mandamientoBD.getDocumentoId());
		MandamientoDTOSend.setFolioDocumento(mandamientoBD.getFolioDocumento());
		MandamientoDTOSend.setEstatus(new ValorDTO(mandamientoBD.getEstatus()
				.getValorId()));

		MandamientoDTOSend.setMandamientosPersona(MandamientoPersonaTransformer
				.transformar(listaMandamientosPersonaRelacionados));

		logger.info("Institucion a enviar=" + institucionSend.getValorId());

		clienteGeneralService.enviarActualizacionMandamientoInterInstitucional(
				MandamientoDTOSend, DocumentoDTOSend, institucionSend);
		
		//Marcamos el documento como enviado
		documentoBDSend.setEsEnviado(true);
		documentoDAO.update(documentoBDSend);
		
		
		//Actualizamos el estatus del mandamiento al calculado
		//con base a sus mandamientos persona
		actualizarEstatusMandamiento(mandamientoDTO,true);
	}

	@Override
	public Long adjuntarDocumentoAMandamientoJudicial(
			DocumentoDTO documentoDTO, MandamientoDTO mandamientoJudicialDTO,
			TipoDocumento tipoDocumento) throws NSJPNegocioException {

		if (mandamientoJudicialDTO == null
				|| mandamientoJudicialDTO.getDocumentoId() == null
				|| documentoDTO == null
				|| documentoDTO.getArchivoDigital() == null
				|| documentoDTO.getArchivoDigital().getContenido() == null
				|| documentoDTO.getArchivoDigital().getNombreArchivo() == null
				|| documentoDTO.getArchivoDigital().getTipoArchivo() == null) {

			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		Documento documento = DocumentoTransformer
				.transformarDocumentoDTO(documentoDTO);
		
		ArchivoDigital archivoDig = documento.getArchivoDigital();

		if (archivoDig.getNombreArchivo() != null
				&& archivoDig.getNombreArchivo().length() > TAM_MAX_NOMBRE_ARCH) {
			archivoDig.setNombreArchivo(archivoDig.getNombreArchivo()
					.substring(0, TAM_MAX_NOMBRE_ARCH));
		}
		if (archivoDig.getTipoArchivo() != null
				&& archivoDig.getTipoArchivo().length() > TAM_MAX_EXTENSION_ARCH) {
			archivoDig.setTipoArchivo(archivoDig.getTipoArchivo().substring(0,
					TAM_MAX_EXTENSION_ARCH));
		}

		/**
		 * Generamos el archivo digital
		 */
		Long idArchivo = archivoDigitalDAO.create(archivoDig);
		
		ArchivoDigital archDigitalGenerado = new ArchivoDigital();
		archDigitalGenerado.setArchivoDigitalId(idArchivo);
		
		
		/**
		 * Generamos el documento
		 */
		documento.setArchivoDigital(archDigitalGenerado);

		/* Obligatorios de Documento */
		documento.setNombreDocumento(archivoDig.getNombreArchivo());
		documento.setFechaCreacion(new Date());
		
		// Se asigna el tipo de documento
		if (tipoDocumento != null && tipoDocumento.getValorId() > 0) {
			documento.setTipoDocumento(new Valor(tipoDocumento.getValorId()));
		} else {
			documento.setTipoDocumento(new Valor(
					TipoDocumento.ARCHIVO_ADJUNTADO.getValorId()));
		}

		documento.setForma(formaDAO
				.consultarFormaPorId(Formas.PLANTILLA_EN_BLANCO.getValorId()));
		documentoDAO.create(documento);

		Long archivoDigitalId = null;

		/**
		 * Genera la relacion con mandamiento
		 */
		if (idArchivo != null
				&& mandamientoJudicialDTO.getDocumentoId() != null) {

			MandamientoAdjuntosId mandamientoAdjuntoId = new MandamientoAdjuntosId(
					mandamientoJudicialDTO.getDocumentoId(), idArchivo);

			MandamientoAdjuntos mandamientoAdjuntosBD = new MandamientoAdjuntos();
			mandamientoAdjuntosBD.setId(mandamientoAdjuntoId);

			mandamientoAdjuntoId = mandamientoAdjuntosDAO
					.create(mandamientoAdjuntosBD);

			if (mandamientoAdjuntoId.getArchivoDigitalId() != null) {
				archivoDigitalId = mandamientoAdjuntoId.getArchivoDigitalId();
			}
		}

		return archivoDigitalId;
	}
}
