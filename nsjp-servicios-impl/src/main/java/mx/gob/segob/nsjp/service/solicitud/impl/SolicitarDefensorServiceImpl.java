/**
* Nombre del Programa : SolicitarDefensorServiceImpl.java
* Autor                            : cesarAgustin
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 19 May 2011
* Marca de cambio        : N/A
* Descripcion General    : Implementacion del servicio para realizar la solicitud de un defensor
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import mx.gob.segob.nsjp.comun.enums.calidad.Calidades;
import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.enums.solicitud.EstatusSolicitud;
import mx.gob.segob.nsjp.comun.enums.solicitud.TiposSolicitudes;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.archivo.ArchivoDigitalDAO;
import mx.gob.segob.nsjp.dao.delito.DelitoDAO;
import mx.gob.segob.nsjp.dao.expediente.NumeroExpedienteDAO;
import mx.gob.segob.nsjp.dao.institucion.ConfInstitucionDAO;
import mx.gob.segob.nsjp.dao.involucrado.InvolucradoDAO;
import mx.gob.segob.nsjp.dao.involucrado.InvolucradoSolicitudDefensorDAO;
import mx.gob.segob.nsjp.dao.persona.DelitoPersonaDAO;
import mx.gob.segob.nsjp.dao.solicitud.SolicitudDAO;
import mx.gob.segob.nsjp.dao.solicitud.SolicitudDefensorDAO;
import mx.gob.segob.nsjp.dto.documento.AvisoDesignacionDTO;
import mx.gob.segob.nsjp.dto.documento.DocumentoDTO;
import mx.gob.segob.nsjp.dto.documento.GuardadoDefinitivoDTO;
import mx.gob.segob.nsjp.dto.expediente.DelitoDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudDefensorDTO;
import mx.gob.segob.nsjp.model.AvisoDesignacion;
import mx.gob.segob.nsjp.model.ConfInstitucion;
import mx.gob.segob.nsjp.model.Delito;
import mx.gob.segob.nsjp.model.DelitoPersona;
import mx.gob.segob.nsjp.model.Funcionario;
import mx.gob.segob.nsjp.model.Involucrado;
import mx.gob.segob.nsjp.model.InvolucradoSolicitudDefensor;
import mx.gob.segob.nsjp.model.InvolucradoSolicitudDefensorId;
import mx.gob.segob.nsjp.model.NumeroExpediente;
import mx.gob.segob.nsjp.model.Solicitud;
import mx.gob.segob.nsjp.model.SolicitudDefensor;
import mx.gob.segob.nsjp.model.SolicitudDefensorDelito;
import mx.gob.segob.nsjp.model.Valor;
import mx.gob.segob.nsjp.service.archivo.impl.transform.ArchivoDigitalTransformer;
import mx.gob.segob.nsjp.service.audiencia.impl.transform.AudienciaTransformer;
import mx.gob.segob.nsjp.service.delito.impl.transform.DelitoTransfromer;
import mx.gob.segob.nsjp.service.documento.AvisoDesignacionService;
import mx.gob.segob.nsjp.service.expediente.BuscarExpedienteService;
import mx.gob.segob.nsjp.service.forma.impl.transform.FormaTransformer;
import mx.gob.segob.nsjp.service.funcionario.impl.transform.FuncionarioTransformer;
import mx.gob.segob.nsjp.service.infra.DefensoriaClienteService;
import mx.gob.segob.nsjp.service.involucrado.ConsultarProbablesResponsablesParaSolucitudDeDefensorService;
import mx.gob.segob.nsjp.service.involucrado.impl.transform.InvolucradoTransformer;
import mx.gob.segob.nsjp.service.solicitud.GenerarFolioSolicitudService;
import mx.gob.segob.nsjp.service.solicitud.SolicitarDefensorService;
import mx.gob.segob.nsjp.service.solicitud.impl.transform.SolicitudDefensorTransformer;
import mx.gob.segob.nsjp.service.usuario.impl.transformer.ValorTransformer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementacion del servicio para realizar la solicitud de un defensor.
 * @version 1.0
 * @author cesarAagustin
 *
 */
@Service
public class SolicitarDefensorServiceImpl implements SolicitarDefensorService {
	
	/**
	 * 
	 */
	private final static Logger logger = Logger.getLogger(SolicitarDefensorServiceImpl.class);
	
	@Autowired
	private SolicitudDefensorDAO solicitudDefensorDAO;
    @Autowired
    private GenerarFolioSolicitudService generarFolioSolicitudService;
    @Autowired
    private ConfInstitucionDAO confInstitucionDAO;
    @Autowired
	private ConsultarProbablesResponsablesParaSolucitudDeDefensorService consultarProbablesResponsablesParaSolucitudDeDefensorService;
    @Autowired
    private InvolucradoSolicitudDefensorDAO involucradoSolicitudDefensorDAO;
    @Autowired
    private DefensoriaClienteService clienteDefensoriaWebService;
    @Autowired
    private DelitoPersonaDAO delitoPersonaDAO;
    @Autowired
    private InvolucradoDAO involucradoDAO;
    @Autowired
    private ArchivoDigitalDAO archivoDigitalDAO;
    @Autowired
    private DelitoDAO delitoDAO; 
    @Autowired
    private NumeroExpedienteDAO numeroExpedienteDAO;
    @Autowired
    private BuscarExpedienteService buscarExpedienteService;   
    @Autowired
	private SolicitudDAO solicitudDAO;
    @Autowired
	private AvisoDesignacionService avisoDesignacionService; 
    
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public SolicitudDefensorDTO solicitarDefensor(
			ExpedienteDTO inputExpedienteDTO, DocumentoDTO doctoSolicitudDto)
			throws NSJPNegocioException {

		if (logger.isDebugEnabled()) {
			logger.debug("/**** SERVICIO PARA SOLICITAR DEFENSOR ****/");
		}

		if (inputExpedienteDTO == null
				|| inputExpedienteDTO.getExpedienteId() <= 0
				|| inputExpedienteDTO.getNumeroExpediente() == null
				|| inputExpedienteDTO.getNumeroExpediente().trim().isEmpty()
				|| inputExpedienteDTO.getNumeroExpedienteId() == null
				|| inputExpedienteDTO.getNumeroExpedienteId() < 0
				|| inputExpedienteDTO.getUsuario() == null
				|| inputExpedienteDTO.getUsuario().getFuncionario() == null
				|| inputExpedienteDTO.getUsuario().getFuncionario()
						.getDiscriminante() == null
				|| inputExpedienteDTO.getUsuario().getFuncionario()
						.getDiscriminante().getDistrito() == null
				|| inputExpedienteDTO.getUsuario().getFuncionario()
						.getDiscriminante().getDistrito().getClaveDistrito() == null
				|| inputExpedienteDTO.getUsuario().getFuncionario()
						.getDiscriminante().getDistrito().getClaveDistrito()
						.isEmpty()) {
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}

		// Obtener el expediente
		ExpedienteDTO expedienteDTO = buscarExpedienteService
				.obtenerExpediente(inputExpedienteDTO);

		if (expedienteDTO == null || expedienteDTO.getCasoDTO() == null
				|| expedienteDTO.getCasoDTO().getNumeroGeneralCaso() == null
				|| expedienteDTO.getCasoDTO().getNumeroGeneralCaso().isEmpty()) {
			throw new NSJPNegocioException(
					CodigoError.INFORMACION_PARAMETROS_ERRONEA);
		}

		/*
		 * No es necesario replicar el caso, dado a que en la solicitud se envia
		 * la informacion necesaria para crear un caso y su expediente.
		 */
		
		/*
		 * Seccion para los datos generales de la solicitud de defensor
		 */
		SolicitudDefensor solicitudDefensor = new SolicitudDefensor();

		// Numero de caso
		solicitudDefensor.setNumeroCasoAsociado(expedienteDTO.getCasoDTO()
				.getNumeroGeneralCaso());
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
		solicitudDefensor.setNombreDocumento(doctoSolicitudDto
				.getNombreDocumento());
		// Forma del documento
		solicitudDefensor.setForma(FormaTransformer
				.transformarFormaDTO(doctoSolicitudDto.getFormaDTO()));
		// Tipo documento
		solicitudDefensor.setTipoDocumento(ValorTransformer
				.transformar(doctoSolicitudDto.getTipoDocumentoDTO()));
		// Tipo de solicitud
		solicitudDefensor.setTipoSolicitud(new Valor(TiposSolicitudes.DEFENSOR
				.getValorId(), TiposSolicitudes.DEFENSOR.name()));
		// Fecha y hora de la solicitud
		solicitudDefensor.setFechaCreacion(new Date());

		// Archivo digital
		solicitudDefensor.setArchivoDigital(ArchivoDigitalTransformer
				.transformarArchivoDigitalDTO(doctoSolicitudDto
						.getArchivoDigital()));

		// Nombre del funcionario solicitante
		solicitudDefensor.setNombreSolicitante(inputExpedienteDTO.getUsuario()
				.getFuncionario().getNombreCompleto());
		// Numero de expediente relacionado
		solicitudDefensor.setNumeroExpedienteAsociado(inputExpedienteDTO
				.getNumeroExpediente());

		// Numero de expediente
		NumeroExpediente numeroExpediente = numeroExpedienteDAO
				.read(inputExpedienteDTO.getNumeroExpedienteId());
		if (numeroExpediente == null) {
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		solicitudDefensor.setNumeroExpediente(numeroExpediente);

		// Seteamos la UIE del usuario solicitante
		if (inputExpedienteDTO.getUsuario().getFuncionario()
				.getUnidadIEspecializada() != null
				&& inputExpedienteDTO.getUsuario().getFuncionario()
						.getUnidadIEspecializada().getNombreUIE() != null
				&& !inputExpedienteDTO.getUsuario().getFuncionario()
						.getUnidadIEspecializada().getNombreUIE().trim()
						.isEmpty()) {

			solicitudDefensor
					.setNombreDeLaUnidadDeInvestigacionDelSolicitante(inputExpedienteDTO
							.getUsuario().getFuncionario()
							.getUnidadIEspecializada().getNombreUIE());
		}

		/*
		 * Transformar la solicitud
		 */
		SolicitudDefensorDTO solicitudDefensorDTO = SolicitudDefensorTransformer
				.transformarSolicitudDefensoria(solicitudDefensor);

		/*
		 * Obtenemos los involucrados y los seteamos a la solicitud
		 */
		List<InvolucradoDTO> listaProbRespDTO = consultarProbablesResponsablesParaSolucitudDeDefensorService
				.consultarProbablesResponsablesParaSolucitudDeDefensor(inputExpedienteDTO);

		if (listaProbRespDTO == null || listaProbRespDTO.isEmpty()) {
			throw new NSJPNegocioException(
					CodigoError.INFORMACION_PARAMETROS_ERRONEA);
		}

		List<InvolucradoDTO> listaInvolucradosDTO = new ArrayList<InvolucradoDTO>();

		// Agregamos los probables responsables a la lista de involucrados
		listaInvolucradosDTO.addAll(listaProbRespDTO);

		// En este paso obtenemos las victimas de la relacion delito persona y
		// los denunciantes y los agregamos a la lista de involucrados
		listaInvolucradosDTO.addAll(this
				.obtenerVicimasYDenunciantesDeRelacionDelitoPersona(
						listaProbRespDTO, inputExpedienteDTO));

		solicitudDefensorDTO.setInvolucrados(listaInvolucradosDTO);

		/*
		 * Obtenemos los delitos del expediente
		 */
		List<Delito> listaDelitos = delitoDAO
				.obtenerDelitoPorExpediente(expedienteDTO.getExpedienteId());
		if (listaDelitos != null && !listaDelitos.isEmpty()) {

			List<DelitoDTO> listaDelitosDTO = new ArrayList<DelitoDTO>();

			for (Delito delitosSolicitud : listaDelitos) {
				listaDelitosDTO.add(DelitoTransfromer
						.transformarDelito(delitosSolicitud));
			}
			solicitudDefensorDTO.setDelitos(listaDelitosDTO);
		}
		/*
		 * Seteamos la clave distrito origen
		 */
		solicitudDefensorDTO.setClaveDiscriminanteOrigen(inputExpedienteDTO
				.getUsuario().getFuncionario().getDiscriminante().getDistrito()
				.getClaveDistrito());

		/*
		 * Se hace el registro de la solicitud antes de enviarla Esto para que
		 * que se mande el folio y la institucion de dicha solicitud En caso de
		 * que ocurra un error, se hace rollback de los registros.
		 */

		archivoDigitalDAO.create(solicitudDefensor.getArchivoDigital());
		Long solicitudPgId = solicitudDefensorDAO.create(solicitudDefensor);

		if (solicitudPgId != null && solicitudPgId > 0L) {

			List<InvolucradoSolicitudDefensor> listaInvolucradoSolicitudDefensor = new ArrayList<InvolucradoSolicitudDefensor>();

			for (InvolucradoDTO probResponsable : listaProbRespDTO) {

				InvolucradoSolicitudDefensorId involucradoSolicitudDefensorId = new InvolucradoSolicitudDefensorId();

				involucradoSolicitudDefensorId.setInvolucradoId(probResponsable
						.getElementoId());
				involucradoSolicitudDefensorId
						.setSolicitudDefensorId(solicitudPgId);

				InvolucradoSolicitudDefensor involucradoSolicitudDefensor = new InvolucradoSolicitudDefensor();
				involucradoSolicitudDefensor
						.setId(involucradoSolicitudDefensorId);

				listaInvolucradoSolicitudDefensor
						.add(involucradoSolicitudDefensor);
			}

			involucradoSolicitudDefensorDAO
					.createAll(listaInvolucradoSolicitudDefensor);
		}
		
		//Folio del documento
		solicitudDefensorDTO.setFolioDocumento(solicitudDefensor.getFolioDocumento());
		/*
		 * Invocamos el WS
		 */
		SolicitudDefensorDTO solicitudDefensorRespuesta = clienteDefensoriaWebService
				.enviarSolicitudDefensor(solicitudDefensorDTO);
		
		return solicitudDefensorRespuesta;
	}
	
	@Override
	public List<InvolucradoDTO> obtenerVicimasYDenunciantesDeRelacionDelitoPersona(
			List<InvolucradoDTO> listaProbRespDTO, ExpedienteDTO inputExpDTO)
			throws NSJPNegocioException {

		if (listaProbRespDTO == null || listaProbRespDTO.isEmpty()) {
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}

		List<InvolucradoDTO> listaVictimasDTO = new ArrayList<InvolucradoDTO>();
		List<DelitoPersona> listaRelDelitoPer = null;
		HashMap<String, Long> hashVictimasDenunciantes = new HashMap<String, Long>();

		//Consultamos las relaciones delito persona por involucrado y expediente
		//NO SE DEBEN TOMAR EN CUENTA LAS PERSONAS MORALES
		for (InvolucradoDTO probResDTO : listaProbRespDTO) {
			listaRelDelitoPer = delitoPersonaDAO
					.consultarDelitosPorImputadoResponsableConVictima(
							probResDTO.getElementoId(),
							inputExpDTO.getExpedienteId());
			if (listaRelDelitoPer != null && !listaRelDelitoPer.isEmpty()) {
				for (DelitoPersona delitoPer : listaRelDelitoPer) {
					//Agregamos las victimas al hashMap, sin que se repitan
					hashVictimasDenunciantes.put(delitoPer.getVictima()
							.getFolioElemento(), delitoPer.getVictima()
							.getElementoId());

				}
			}
		}

		//Si no hay calidad de victimas puede ir un denunciante/victima
		if (!hashVictimasDenunciantes.isEmpty()) {

			List<Involucrado> listaVictimas = new ArrayList<Involucrado>();

			//Consultamos todas la victimas del expediente
			listaVictimas = involucradoDAO.obtenerInvByIdExpAndCalidad(
					inputExpDTO.getExpedienteId(),
					Calidades.VICTIMA_PERSONA.getValorId(), null);

			//Consultamos todos lo denunuciantes(fisicos) del expediente
			List<Involucrado> listaDenunciantesFisicos = involucradoDAO
					.obtenerInvByIdExpAndCalidad(inputExpDTO.getExpedienteId(),
							Calidades.DENUNCIANTE.getValorId(), null);

			//agregamos los denunciantes (fisicos) al HashMap
			if (listaDenunciantesFisicos != null
					&& !listaDenunciantesFisicos.isEmpty()) {
				for (Involucrado denuncianteFisico : listaDenunciantesFisicos) {
					hashVictimasDenunciantes.put(
							denuncianteFisico.getFolioElemento(),
							denuncianteFisico.getElementoId());
				}
				listaVictimas.addAll(listaDenunciantesFisicos);
			}

			//Consultamos todos lo denunuciantes (morales) del expediente
			/* NO SE ENVIAN PERSONAS MORALES HASTA LA CARPETA DE INVESTIGACION
			List<Involucrado> listaDenunciantesMorales = involucradoDAO
					.obtenerInvByIdExpAndCalidad(inputExpDTO.getExpedienteId(),
							Calidades.DENUNCIANTE_ORGANIZACION.getValorId(),
							null);
			
			//agregamos los denunciantes (morales) al HashMap
			if (listaDenunciantesMorales != null
					&& !listaDenunciantesMorales.isEmpty()) {
				for (Involucrado denuncianteMoral : listaDenunciantesMorales) {
					hashVictimasDenunciantes.put(
							denuncianteMoral.getFolioElemento(),
							denuncianteMoral.getElementoId());
				}
				listaVictimas.addAll(listaDenunciantesMorales);
			}*/

			if (!listaVictimas.isEmpty()) {
				for (Involucrado victma : listaVictimas) {
					//Al final solo agregaremos las victimas que estan en el hashMap
					//producto de la relacion delito-persona, y ademas los denunciantes
					if (hashVictimasDenunciantes.containsKey(victma
							.getFolioElemento())) {
						listaVictimasDTO.add(InvolucradoTransformer
								.transformarInvolucrado(victma));
					}
				}
			}
		}

		return listaVictimasDTO;
	}

	@Transactional
	@Override
	public List<SolicitudDefensorDTO> consultarSolDefensorAsignadas(
			FuncionarioDTO funcionarioDTO) throws NSJPNegocioException {
		
		if (logger.isDebugEnabled()){
			logger.debug("/**** SERVICIO PARA CONSULTAR LAS SOLICITUDES DEFENSORIA ASIGNADAS A UN DEFENSOR ****/");
		}
		
		if (funcionarioDTO==null)
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		
		List<SolicitudDefensor> solsDefensoria = solicitudDefensorDAO.consultarSolDefensorAsignadas(funcionarioDTO.getClaveFuncionario());
		
		List<SolicitudDefensorDTO> solsRespuesta = new ArrayList<SolicitudDefensorDTO>();
		for (SolicitudDefensor solicitudDefensor : solsDefensoria) {
			SolicitudDefensorDTO solDefDTO = SolicitudDefensorTransformer.transformarSolicitudDefensoria(solicitudDefensor); 			
		
			List<DelitoDTO> delitosDTO = new ArrayList<DelitoDTO>();
			for (SolicitudDefensorDelito solDefDelito : solicitudDefensor.getSolicitudDefensorDelitos()) {
				if (solDefDelito.getDelito()!=null) {
					delitosDTO.add(DelitoTransfromer.transformarDelito(solDefDelito.getDelito()));
				}
			}
			solDefDTO.setDelitos(delitosDTO);
			
			if (solicitudDefensor.getAudiencia()!=null) {
				solDefDTO.setAudiencia(AudienciaTransformer.transformarDTO(solicitudDefensor.getAudiencia()));
			}
			
			solsRespuesta.add(solDefDTO);
		}
		
		return solsRespuesta;
	}

	@Transactional
	@Override
	public SolicitudDefensorDTO reasignarDefensorAExpediente(ExpedienteDTO expedienteDTO,
			FuncionarioDTO funcionarioDTO) throws NSJPNegocioException {
		if (logger.isDebugEnabled()){
			logger.debug("/**** SERVICIO REASIGNAR DEFENSOR A UN EXPEDIENTE ****/");
		}
		
		if (expedienteDTO.getExpedienteId()==null
				|| funcionarioDTO.getClaveFuncionario()==null )
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		
		SolicitudDefensor solDefensor = solicitudDefensorDAO.obtenerSolDfensorByExpedienteYFuncionario(expedienteDTO.getExpedienteId(), funcionarioDTO.getClaveFuncionario()); 
		logger.debug("/**** SOL. DEF ****/ "+solDefensor.getDocumentoId());
		
		//Eliminar defensor de la solicitud
		SolicitudDefensorDTO solDefDTO = new SolicitudDefensorDTO();
		if (solDefensor!=null) {
			logger.debug("/**** ELIMINANDO FUNCIONARIO DE SOLICITUD ****/");
			solDefensor.setDestinatario(null);
			solicitudDefensorDAO.update(solDefensor);
			solDefDTO.setDocumentoId(solDefensor.getDocumentoId());
		} else{
			solDefDTO.setDocumentoId(new Long(0));
		}
		return solDefDTO;
	}
	
	@Override
	public SolicitudDefensorDTO solicitarDefensorDesdeDefensoria(
			GuardadoDefinitivoDTO guardadoDefinitivoDTO)
			throws NSJPNegocioException {
		/**
		 * Validaciones generales
		 */
		if (guardadoDefinitivoDTO == null
				|| guardadoDefinitivoDTO.getExpedienteDTO() == null
				|| guardadoDefinitivoDTO.getExpedienteDTO().getExpedienteId() == null
				|| guardadoDefinitivoDTO.getExpedienteDTO().getExpedienteId() <= 0
				|| guardadoDefinitivoDTO.getExpedienteDTO().getUsuario() == null
				|| guardadoDefinitivoDTO.getExpedienteDTO().getUsuario()
						.getFuncionario() == null
				|| guardadoDefinitivoDTO.getExpedienteDTO().getUsuario()
						.getFuncionario().getDiscriminante() == null
				|| guardadoDefinitivoDTO.getExpedienteDTO().getUsuario()
						.getFuncionario().getDiscriminante().getDistrito() == null
				|| guardadoDefinitivoDTO.getExpedienteDTO().getUsuario()
						.getFuncionario().getDiscriminante().getDistrito()
						.getClaveDistrito() == null
				|| guardadoDefinitivoDTO.getExpedienteDTO().getUsuario()
						.getFuncionario().getDiscriminante().getDistrito()
						.getClaveDistrito().isEmpty()
				|| guardadoDefinitivoDTO.getDocumentoDTO() == null
				|| guardadoDefinitivoDTO.getIdsInvolucrados().isEmpty()
				|| guardadoDefinitivoDTO.getSolicitudDTO() == null
				|| guardadoDefinitivoDTO.getSolicitudDTO().getInvolucradoDTO() == null
				|| guardadoDefinitivoDTO.getSolicitudDTO().getInvolucradoDTO()
						.getElementoId() == null
				|| guardadoDefinitivoDTO.getSolicitudDTO().getInvolucradoDTO()
						.getElementoId() <= 0L) {
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}

		ExpedienteDTO expedienteDto = buscarExpedienteService
				.obtenerExpedientePorExpedienteId(new ExpedienteDTO(
						guardadoDefinitivoDTO.getExpedienteDTO()
								.getExpedienteId()));

		/**
		 * Seccion de validacio del expediente y caso
		 */
		if (expedienteDto == null) {
			throw new NSJPNegocioException(CodigoError.NO_EXISTE_EXPEDIENTE);
		}

		if (expedienteDto.getCasoDTO() == null
				|| expedienteDto.getCasoDTO().getNumeroGeneralCaso() == null
				|| expedienteDto.getCasoDTO().getNumeroGeneralCaso().trim()
						.isEmpty()) {
			throw new NSJPNegocioException(CodigoError.SIN_CASO_ASOCIADO);
		}

		/**
		 * Asociar Numero expediente para la segunda solicitud. Siempre y cuando
		 * sea una solicitud que tenga asociada a un numero expediente
		 */
		Solicitud solicitudFiltro = new Solicitud();
		solicitudFiltro.setNumeroCasoAsociado(expedienteDto.getCasoDTO()
				.getNumeroGeneralCaso().trim());
		List<Solicitud> listaSolicitudesAnteriores = solicitudDAO
				.consultarSolicitudPorFiltro(solicitudFiltro, true, null, null);

		NumeroExpediente numeroExpedienteAsignado = null;
		Funcionario funcionarioDestinatario = null;

		if (!listaSolicitudesAnteriores.isEmpty()) {
			numeroExpedienteAsignado = listaSolicitudesAnteriores.get(0)
					.getNumeroExpediente();
			funcionarioDestinatario = listaSolicitudesAnteriores.get(0)
					.getDestinatario();
		}

		/**
		 * Seccion para los datos generales de la solicitud de defensor
		 */
		SolicitudDefensor solicitudDefensor = new SolicitudDefensor();

		// Numero Expediente
		if (numeroExpedienteAsignado != null) {

			solicitudDefensor.setNumeroExpediente(numeroExpedienteAsignado);
			solicitudDefensor
					.setNumeroExpedienteAsociado(numeroExpedienteAsignado
							.getNumeroExpediente());
		}

		// Funcionario Destinatario
		if (funcionarioDestinatario != null) {
			solicitudDefensor.setDestinatario(funcionarioDestinatario);
		}

		// Numero de caso
		solicitudDefensor.setNumeroCasoAsociado(expedienteDto.getCasoDTO()
				.getNumeroGeneralCaso());
		// Folio de la solicitud
		solicitudDefensor.setFolioSolicitud(generarFolioSolicitudService
				.generarFolioSolicitud());
		// Conf Institucion
		ConfInstitucion confInstitucion = confInstitucionDAO
				.consultarIntitucionActual();
		solicitudDefensor.setConfInstitucion(confInstitucion);

		// Cta discriminante origen
		solicitudDefensor.setCatDiscriminanteOrigen(guardadoDefinitivoDTO
				.getExpedienteDTO().getUsuario().getFuncionario()
				.getDiscriminante().getCatDiscriminanteId());

		// Clave discriminante origen
		solicitudDefensor.setClaveDiscriminanteOrigen(guardadoDefinitivoDTO
				.getExpedienteDTO().getUsuario().getFuncionario()
				.getDiscriminante().getClave());

		// Estatus de la solicitud
		solicitudDefensor.setEstatus(new Valor(EstatusSolicitud.ABIERTA
				.getValorId()));
		// Nombre Documento
		solicitudDefensor.setNombreDocumento(guardadoDefinitivoDTO
				.getDocumentoDTO().getNombreDocumento());
		// Forma del documento
		solicitudDefensor.setForma(FormaTransformer
				.transformarFormaDTO(guardadoDefinitivoDTO.getDocumentoDTO()
						.getFormaDTO()));
		// Tipo documento
		solicitudDefensor.setTipoDocumento(ValorTransformer
				.transformar(guardadoDefinitivoDTO.getDocumentoDTO()
						.getTipoDocumentoDTO()));
		// Tipo de solicitud
		solicitudDefensor.setTipoSolicitud(new Valor(TiposSolicitudes.DEFENSOR
				.getValorId(), TiposSolicitudes.DEFENSOR.name()));
		// Fecha y hora de la solicitud
		solicitudDefensor.setFechaCreacion(new Date());

		// Guardado definitivo
		solicitudDefensor.setEsGuardadoParcial(false);

		// Archivo digital
		solicitudDefensor.setArchivoDigital(ArchivoDigitalTransformer
				.transformarArchivoDigitalDTO(guardadoDefinitivoDTO
						.getDocumentoDTO().getArchivoDigital()));

		// Nombre del funcionario con rol defensorAte
		solicitudDefensor.setNombreSolicitante(guardadoDefinitivoDTO
				.getExpedienteDTO().getUsuario().getFuncionario()
				.getNombreCompleto());

		/**
		 * Seccion para llenar el involucrado solicitante
		 */
		Involucrado involucradoSolicitate = involucradoDAO
				.read(guardadoDefinitivoDTO.getSolicitudDTO()
						.getInvolucradoDTO().getElementoId());

		// Involucrado Solicitante
		solicitudDefensor.setInvolucradoSolicitante(involucradoSolicitate);

		// Id de la solicitud que se generar&aacute;
		Long solDefensorId = 0L;

		archivoDigitalDAO.create(solicitudDefensor.getArchivoDigital());
		solDefensorId = solicitudDefensorDAO.create(solicitudDefensor);

		if (solDefensorId != null && solDefensorId > 0L) {

			/**
			 * Seccion para asociar la solicitud a los involucrados a los
			 * probables participes que fueron seleccionados
			 */

			String listaIds[] = guardadoDefinitivoDTO.getIdsInvolucrados()
					.split(",");

			List<InvolucradoDTO> listaProbablesResponsables = new ArrayList<InvolucradoDTO>(
					listaIds.length);

			for (String valorString : listaIds) {
				try {
					Long valor = Long.parseLong(valorString);
					if (valor != null && valor > 0) {
						listaProbablesResponsables
								.add(new InvolucradoDTO(valor));
					}
				} catch (NumberFormatException e) {
					logger.info("No se logro hacer un cast del valor:"
							+ valorString);
				}
			}

			if (listaProbablesResponsables != null
					&& !listaProbablesResponsables.isEmpty()) {
				List<InvolucradoSolicitudDefensor> listaInvolucradoSolicitudDefensor = new ArrayList<InvolucradoSolicitudDefensor>();

				for (InvolucradoDTO probResponsable : listaProbablesResponsables) {

					InvolucradoSolicitudDefensorId involucradoSolicitudDefensorId = new InvolucradoSolicitudDefensorId();

					involucradoSolicitudDefensorId
							.setInvolucradoId(probResponsable.getElementoId());
					involucradoSolicitudDefensorId
							.setSolicitudDefensorId(solDefensorId);

					InvolucradoSolicitudDefensor involucradoSolicitudDefensor = new InvolucradoSolicitudDefensor();
					involucradoSolicitudDefensor
							.setId(involucradoSolicitudDefensorId);

					listaInvolucradoSolicitudDefensor
							.add(involucradoSolicitudDefensor);
				}

				involucradoSolicitudDefensorDAO
						.createAll(listaInvolucradoSolicitudDefensor);
			}

			/**
			 * Seccion para crear el aviso de designacion
			 */
			if (numeroExpedienteAsignado != null) {
				/*
				 * Buscar solicitudes con Aviso de Desginaci&oacute;n que hayan
				 * sido asignadas a un defensor esto con el fin de que el
				 * defensor veaesta nueva solicitud
				 */

				List<SolicitudDefensor> listaSolicitudesConAvisoDesignacion = solicitudDefensorDAO
						.consultarSolDeDefensorPorNumeroExpediente(
								expedienteDto.getCasoDTO()
										.getNumeroGeneralCaso(),
								numeroExpedienteAsignado.getNumeroExpediente(),
								true, true);
				if (listaSolicitudesConAvisoDesignacion != null
						&& !listaSolicitudesConAvisoDesignacion.isEmpty()) {
					/*
					 * Se consulta el aviso de designaci&oacute;n de la primera
					 * solicitud, considerando que la consulta anterior se hace
					 * sobre solicitudes con aviso de designaci&oacute;n
					 */
					AvisoDesignacion avisoDesignacionDB = listaSolicitudesConAvisoDesignacion
							.get(0).getAvisoDesignacion();

					AvisoDesignacionDTO avisoDesignacionDTO = new AvisoDesignacionDTO();

					avisoDesignacionDTO.setFuncionario(FuncionarioTransformer
							.transformarFuncionarioBasico(avisoDesignacionDB
									.getFuncionario()));
					SolicitudDefensorDTO solicitudDefensorDTO = new SolicitudDefensorDTO(
							solDefensorId);
					avisoDesignacionDTO
							.setSolicitudDefensor(solicitudDefensorDTO);

					ExpedienteDTO expedienteDTO = new ExpedienteDTO(
							expedienteDto.getExpedienteId());
					avisoDesignacionDTO.setExpediente(expedienteDTO);
					avisoDesignacionService.designarAbogadoDefensor(
							avisoDesignacionDTO, false, false);
				}
			}

		}

		return new SolicitudDefensorDTO(solDefensorId);
	}
		
}
