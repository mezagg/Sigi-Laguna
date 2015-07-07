/**
* Nombre del Programa : ActualizarCarpetaDeInvestigacionServiceImpl.java
* Autor                            : GustavoBP
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 28/07/2011
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
package mx.gob.segob.nsjp.service.expediente.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import mx.gob.segob.nsjp.comun.enums.calidad.Calidades;
import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.enums.expediente.EstatusExpediente;
import mx.gob.segob.nsjp.comun.enums.expediente.TipoMovimiento;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.catalogo.CatDelitoDAO;
import mx.gob.segob.nsjp.dao.delito.DelitoDAO;
import mx.gob.segob.nsjp.dao.expediente.NumeroExpedienteDAO;
import mx.gob.segob.nsjp.dao.persona.DelitoPersonaDAO;
import mx.gob.segob.nsjp.dao.solicitud.SolicitudDAO;
import mx.gob.segob.nsjp.dto.documento.DocumentoDTO;
import mx.gob.segob.nsjp.dto.expediente.DelitoDTO;
import mx.gob.segob.nsjp.dto.expediente.DelitoPersonaDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.hecho.HechoDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.dto.objeto.ObjetoDTO;
import mx.gob.segob.nsjp.model.CatDelito;
import mx.gob.segob.nsjp.model.Delito;
import mx.gob.segob.nsjp.model.DelitoPersona;
import mx.gob.segob.nsjp.model.Expediente;
import mx.gob.segob.nsjp.model.Involucrado;
import mx.gob.segob.nsjp.model.NumeroExpediente;
import mx.gob.segob.nsjp.model.RegistroBitacora;
import mx.gob.segob.nsjp.model.Solicitud;
import mx.gob.segob.nsjp.model.Valor;
import mx.gob.segob.nsjp.service.bitacora.RegistrarBitacoraService;
import mx.gob.segob.nsjp.service.delito.ConsultarDelitoPersonaService;
import mx.gob.segob.nsjp.service.documento.GuardarDocumentoService;
import mx.gob.segob.nsjp.service.elemento.AdjuntarArchivoAElementoService;
import mx.gob.segob.nsjp.service.expediente.ActualizarCarpetaDeInvestigacionService;
import mx.gob.segob.nsjp.service.expediente.impl.transform.ExpedienteTransformer;
import mx.gob.segob.nsjp.service.hecho.IngresarHechoService;
import mx.gob.segob.nsjp.service.involucrado.IngresarIndividuoService;
import mx.gob.segob.nsjp.service.objeto.IngresarObjetoService;
import mx.gob.segob.nsjp.service.solicitud.ConsultarSolicitudesAudienciaService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Definicion de servisios para actualizar la información de la carpeta de investigacion (Expediente)
 * que proviene de Procuraduria.
 * 
 * @version 1.0
 * @author GustavoBP
 *
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class ActualizarCarpetaDeInvestigacionServiceImpl implements ActualizarCarpetaDeInvestigacionService {
    private final static Logger logger = Logger
    .getLogger(ActualizarCarpetaDeInvestigacionServiceImpl.class);
    
	@Autowired 
	private SolicitudDAO solicitudDAO;
	@Autowired  
	private IngresarIndividuoService ingresarIndividuoService;
	@Autowired  
	private GuardarDocumentoService guardarDocumentoService;
	@Autowired
	private NumeroExpedienteDAO numeroExpedienteDAO;
	@Autowired
	private IngresarObjetoService ingresarObjetoService;
	@Autowired
	private IngresarHechoService ingresarHechoService;
	@Autowired
	private RegistrarBitacoraService regBitService;
    @Autowired
    private AdjuntarArchivoAElementoService adjuntarArchivoAElementoService;
    @Autowired
    private ConsultarSolicitudesAudienciaService consultarSolicitudesAudienciaService;
    @Autowired
    private ConsultarDelitoPersonaService consultarDelitoPersonaService;
    @Autowired
    private DelitoDAO delitoDAO;
    @Autowired
    private CatDelitoDAO catDelitoDAO;
    @Autowired
    private DelitoPersonaDAO delitoPersonaDAO;

    @Override
    public ExpedienteDTO consultarExpedientePorFolioSolicitud(
            String folioSolicitud) throws NSJPNegocioException {

        if (folioSolicitud == null || folioSolicitud.trim().isEmpty())
            throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);

        Solicitud solicitud = solicitudDAO
                .consultarSolicitudPorFolio(folioSolicitud);
        if (solicitud == null || solicitud.getDocumentoId() == null
                || solicitud.getDocumentoId() < 0)
            throw new NSJPNegocioException(
                    CodigoError.INFORMACION_PARAMETROS_ERRONEA);

        Expediente expediente = solicitudDAO
                .consultarExpedienteDeNumeroExpedienteSolicitud(solicitud
                        .getDocumentoId());

        ExpedienteDTO expedienteDTO = ExpedienteTransformer
                .transformaExpediente(expediente);
        expedienteDTO.setNumeroExpedienteId(solicitud.getNumeroExpediente()
                .getNumeroExpedienteId());
        return expedienteDTO;
    }
	
	@Override
    public Long actualizarExpedientePorFolioSolicitud(String folioSolicitud,
            EstatusExpediente estatus) throws NSJPNegocioException {
        if (folioSolicitud == null || folioSolicitud.trim().isEmpty())
            throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);

        Solicitud solicitud = solicitudDAO
                .consultarSolicitudPorFolio(folioSolicitud);
        if (solicitud == null || solicitud.getDocumentoId() == null
                || solicitud.getDocumentoId() < 0)
            throw new NSJPNegocioException(
                    CodigoError.INFORMACION_PARAMETROS_ERRONEA);
        logger.info("Numero de Expediente a actualizar "
                + solicitud.getNumeroExpediente().getNumeroExpediente());
        NumeroExpediente expediente = solicitud.getNumeroExpediente();
        expediente.setEstatus(new Valor(estatus.getValorId()));
        numeroExpedienteDAO.update(expediente);
        RegistroBitacora regBitEta = new RegistroBitacora();

        regBitEta.setFechaInicio(new Date());
        regBitEta.setTipoMovimiento(new Valor(
                TipoMovimiento.CAMBIO_DE_ESTATUS_DE_EXPEDIENTE.getValorId()));
        regBitEta.setNuevo(String.valueOf(estatus.getValorId()));
        regBitEta.setNumeroExpediente(expediente);
        regBitService.registrarMovimientoDeExpedienteEnBitacora(regBitEta);
        return expediente.getNumeroExpedienteId();

    }
	
	@Override
	public ExpedienteDTO actualizarExpedienteDeCarpetaInvestigacionDefensoria(
			ExpedienteDTO expedienteDTO)
			throws NSJPNegocioException {

        logger.info(" *********** Servicio actualizarExpedienteDeCarpetaInvestigacionDefensoria***********");
        logger.info(" Expediente:" + expedienteDTO);

        if (expedienteDTO == null || expedienteDTO.getExpedienteId() == null
                || expedienteDTO.getExpedienteId() < 0)
            throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);

        logger.info(" Expediente ID:" + expedienteDTO.getExpedienteId());

        if (expedienteDTO.getObjetosDTO() != null
                && !expedienteDTO.getObjetosDTO().isEmpty()) {
            List<ObjetoDTO> objetosDTO = expedienteDTO.getObjetosDTO();
            for (ObjetoDTO objetoDTO : objetosDTO) {
                objetoDTO.setExpedienteDTO(new ExpedienteDTO(expedienteDTO
                        .getExpedienteId()));
                try{
                	Long idObjeto = ingresarObjetoService.ingresarObjetoCarpetaInvestigacion(objetoDTO);
                	logger.info(" Se ingreso en objeto con id: " + idObjeto);
                }catch (Exception e) {
                	logger.info(new NSJPNegocioException(CodigoError.INCOMPATIBILIDAD_DE_CATALOGOS_ENTRE_INSTITUCIONES));
                	logger.info("Error de guardado de objetos:",e);
				}
            }
        }
        // Ingresar involucrado con toda su dependencia
		if (expedienteDTO.getInvolucradosDTO() != null
				&& !expedienteDTO.getInvolucradosDTO().isEmpty()) {
			for (InvolucradoDTO involucradoDTO : expedienteDTO
					.getInvolucradosDTO()) {
				
				involucradoDTO.setExpedienteDTO(new ExpedienteDTO(expedienteDTO
						.getExpedienteId()));

				Long idIndividuo = ingresarIndividuoService
						.ingresarIndividuoInterInstitucion(involucradoDTO, true);
				logger.info("Se ingreso un individuo con id: " + idIndividuo);
				logger.info("Ingresar la imagen del involucrado: "+ involucradoDTO.getFotoDelElemento());
				logger.info("Ingresar la imagen del involucrado: "+ involucradoDTO.getImagenesAsociadas());
				
				if (involucradoDTO.getFotoDelElemento() != null) {
					adjuntarArchivoAElementoService.adjuntarArchivoAElemento(
							new InvolucradoDTO(idIndividuo),
							involucradoDTO.getFotoDelElemento());
				}
			}
		}
        // Hechos.. Utilizados en IPH
        if (logger.isDebugEnabled()) {
            logger.debug(" Se ingresara� el Hecho (Lugar-Domicilio, Tiempo): "
                    + expedienteDTO.getHechoDTO());
        }
        try{
	        if (expedienteDTO.getHechoDTO() != null) {
	            HechoDTO hechoDTO = expedienteDTO.getHechoDTO();
	            hechoDTO.setExpediente(new ExpedienteDTO(expedienteDTO
	                    .getExpedienteId()));
	            if (hechoDTO.getLugar() != null)
	                hechoDTO.getLugar().setExpedienteDTO(
	                        new ExpedienteDTO(expedienteDTO.getExpedienteId()));
	            if (hechoDTO.getDomicilio() != null)
	                hechoDTO.getDomicilio().setExpedienteDTO(
	                        new ExpedienteDTO(expedienteDTO.getExpedienteId()));
	            //Se agrega la fecha de arribo
	            hechoDTO.setFechaDeArribo(expedienteDTO.getHechoDTO().getFechaDeArribo());
	            ingresarHechoService.ingresarHecho(hechoDTO);
	            logger.info(" Se ingreso el Hecho");
	        }
        }catch (Exception e) {
        	logger.info(new NSJPNegocioException(CodigoError.INCOMPATIBILIDAD_DE_CATALOGOS_ENTRE_INSTITUCIONES));
        	logger.info("Error de guardado de ingresarHecho:",e);
		}

        // Documentos y sus Archivos Digitales
        if (logger.isDebugEnabled()) {
            logger.debug(" Se ingresará los documentos: "
                    + expedienteDTO.getDocumentosDTO());
        }
        if (expedienteDTO.getDocumentosDTO() != null
                && !expedienteDTO.getDocumentosDTO().isEmpty()) {
            for (DocumentoDTO documentoDTO : expedienteDTO.getDocumentosDTO()) {
            	try{
	            	Long idDocumento = guardarDocumentoService
	                        .guardarDocumentoIntraInstitucion(documentoDTO,
	                                expedienteDTO);
	                logger.info(" Se ingreso el documento con id: " + idDocumento);
            	}catch (Exception e) {
                	logger.info(new NSJPNegocioException(CodigoError.INCOMPATIBILIDAD_DE_CATALOGOS_ENTRE_INSTITUCIONES));
                	logger.info("Error de guardado de guardarDocumentoIntraInstitucion:",e);
        		}
            }
        }
		
        return expedienteDTO;
    }
    
	/**
	 * Metodo para actualizar el expediente en poder judicial
	 */
	
	@Override
	public ExpedienteDTO actualizarExpedienteDePoderJudicial(
			ExpedienteDTO expedienteDTO,
			List<DelitoPersonaDTO> relDelitoPersonaDto)
			throws NSJPNegocioException {

		HashMap<String, Long> hashProbablesResponsables = null;
		HashMap<String, Long> hashVictimas = null;
		Short CONDICION_VICTIMA = new Short("1");

		logger.info(" ***********SERVICIO PARA ACTUALIZAR UN EXPEDIENTE DE PODER JUDICIAL***********");

		if (expedienteDTO == null || expedienteDTO.getExpedienteId() == null
				|| expedienteDTO.getExpedienteId() < 0 ){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);			
		} 
		
		Boolean existenSolicitudes = consultarSolicitudesAudienciaService
				.existenSolicitudesAudienciaAsociadasAlNumeroExpediente(expedienteDTO);

		logger.info(" Expediente ID:" + expedienteDTO.getExpedienteId());
		
		// Ingresar involucrados del expediente con todas su dependencias
		if (expedienteDTO.getInvolucradosDTO() != null
				&& !expedienteDTO.getInvolucradosDTO().isEmpty()) {

			hashProbablesResponsables = new HashMap<String, Long>();
			hashVictimas = new HashMap<String, Long>();
			
			Long valorIdCalidadInvo = 0L;
			
			for (InvolucradoDTO involucradoDTO : expedienteDTO
					.getInvolucradosDTO()) {

				valorIdCalidadInvo = involucradoDTO.getCalidadDTO()
						.getValorIdCalidad().getIdCampo();

				involucradoDTO.setExpedienteDTO(new ExpedienteDTO(expedienteDTO
						.getExpedienteId()));

				/**
				 * Si existen solicitudes de audiencia anteriores, entonces:
				 * ingresa involucrados nuevos, pero NO actualiza los anteriores.
				 * 
				 * Si NO existen solicitudes de audiencia anteriores, entonces:
				 * ingresa involucrados nuevos y actualiza los anteriores.
				 */
				Long idIndividuo = ingresarIndividuoService
						.ingresarIndividuoInterInstitucion(involucradoDTO, !existenSolicitudes);
				
				logger.info("Se ingreso un individuo con id: " + idIndividuo);

				if (valorIdCalidadInvo
						.equals(Calidades.PROBABLE_RESPONSABLE_PERSONA
								.getValorId())
						|| valorIdCalidadInvo
								.equals(Calidades.PROBABLE_RESPONSABLE_ORGANIZACION
										.getValorId())) {

					hashProbablesResponsables.put(
							involucradoDTO.getFolioElemento(), idIndividuo);
				} else if (valorIdCalidadInvo.equals(Calidades.VICTIMA_PERSONA
						.getValorId())) {
					hashVictimas.put(involucradoDTO.getFolioElemento(),
							idIndividuo);
				} else if ((valorIdCalidadInvo.equals(Calidades.DENUNCIANTE
						.getValorId()) || valorIdCalidadInvo
						.equals(Calidades.DENUNCIANTE_ORGANIZACION.getValorId()))
						&& involucradoDTO.getCondicion() != null
						&& involucradoDTO.getCondicion().equals(
								CONDICION_VICTIMA)) {
					hashVictimas.put(involucradoDTO.getFolioElemento(),
							idIndividuo);
				}
			}// Fin For involucrados
			
			if (logger.isDebugEnabled()) {
				logger.debug(" Se ingresaran los delitos y las relaciones delito persona");
			}
			
			// Guardar Delitos y Delitos Persona
			this.crearActualizarDelitosExpedienteYRelacionesDelitoPersona(hashProbablesResponsables,
					hashVictimas, relDelitoPersonaDto, new ExpedienteDTO(
					expedienteDTO.getExpedienteId()));
		}

		// Hechos
		if (logger.isDebugEnabled()) {
			logger.debug(" Se ingresara el Hecho (Lugar-Domicilio, Tiempo): "
					+ expedienteDTO.getHechoDTO());
		}

		if (expedienteDTO.getHechoDTO() != null) {

			HechoDTO hechoDTO = expedienteDTO.getHechoDTO();
			hechoDTO.setExpediente(new ExpedienteDTO(expedienteDTO
					.getExpedienteId()));
			if (hechoDTO.getLugar() != null)
				hechoDTO.getLugar().setExpedienteDTO(
						new ExpedienteDTO(expedienteDTO.getExpedienteId()));
			if (hechoDTO.getDomicilio() != null)
				hechoDTO.getDomicilio().setExpedienteDTO(
						new ExpedienteDTO(expedienteDTO.getExpedienteId()));
			// Se agrega la fecha de arribo
			hechoDTO.setFechaDeArribo(expedienteDTO.getHechoDTO()
					.getFechaDeArribo());
			ingresarHechoService.ingresarHecho(hechoDTO);
			logger.info(" Se ingreso el Hecho");
		}

		// Documentos y sus Archivos Digitales
		if (logger.isDebugEnabled()) {
			logger.debug(" Se ingresaran los documentos: "
					+ expedienteDTO.getDocumentosDTO());
		}
		
		if (expedienteDTO.getDocumentosDTO() != null
				&& !expedienteDTO.getDocumentosDTO().isEmpty()) {
			for (DocumentoDTO documentoDTO : expedienteDTO.getDocumentosDTO()) {
				Long idDocumento = guardarDocumentoService
						.guardarDocumentoIntraInstitucion(documentoDTO,
								expedienteDTO);
				logger.info(" Se ingreso el documento con id: " + idDocumento);
			}
		}

		if (expedienteDTO.getObjetosDTO() != null
				&& !expedienteDTO.getObjetosDTO().isEmpty()) {
			List<ObjetoDTO> objetosDTO = expedienteDTO.getObjetosDTO();
			for (ObjetoDTO objetoDTO : objetosDTO) {
				objetoDTO.setExpedienteDTO(new ExpedienteDTO(expedienteDTO
						.getExpedienteId()));
				try {
					Long idObjeto = ingresarObjetoService
							.ingresarObjetoCarpetaInvestigacion(objetoDTO);
					logger.info(" Se ingreso en objeto con id: " + idObjeto);
				} catch (Exception e) {
					logger.info(new NSJPNegocioException(
							CodigoError.INCOMPATIBILIDAD_DE_CATALOGOS_ENTRE_INSTITUCIONES));
					logger.info("Error de guardado de objetos:", e);
				}
			}
		}
		
		return expedienteDTO;
	}
    
	/**
	 * M&eacute;todo para crear los delitos y las relaciones delito persona
	 * @param hashProbablesResponsables
	 * @param hashVictimas
	 * @param relDelitoPersonaDto
	 * @param expedienteDTO
	 * @throws NSJPNegocioException
	 */
	private void crearActualizarDelitosExpedienteYRelacionesDelitoPersona(
			HashMap<String, Long> hashProbablesResponsables,
			HashMap<String, Long> hashVictimas,
			List<DelitoPersonaDTO> relDelitoPersonaDto,
			ExpedienteDTO expedienteDTO) throws NSJPNegocioException {

		if (expedienteDTO == null || expedienteDTO.getExpedienteId() == null
				|| hashProbablesResponsables == null
				|| hashProbablesResponsables.isEmpty() || hashVictimas == null
				|| hashVictimas.isEmpty()
				|| expedienteDTO.getExpedienteId() <= 0L
				|| relDelitoPersonaDto == null || relDelitoPersonaDto.isEmpty()) {
			logger.error("***Expediente nulo o Expediente id <= 0***");
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}

		/**
		 * Primera parte: Guardar delitos nuevos o actualizar los que existen
		 */
		// 1) Consultamos los delitos del expediente y los guardamos en el
		// hashDelitosExpediente
		
		HashMap<String, Long> hashDelitosExpediente = new HashMap<String, Long>();

		List<Delito> delitosBD = delitoDAO
				.obtenerDelitoPorExpediente(expedienteDTO.getExpedienteId());
		
		if (delitosBD != null && !delitosBD.isEmpty()) {
			for (Delito delitoBD : delitosBD) {
				hashDelitosExpediente.put(delitoBD.getCatDelito()
						.getClaveInterInstitucional().trim(), delitoBD.getDelitoId());
			}
		}

		// 2) Obtener los delitos de la relacion delito-persona
		List<DelitoDTO> listaDelitosDeLaRelacionDelitoPersona = this
				.obtenerListaDelitosDeLaRelacionDelitoPersona(relDelitoPersonaDto);

		// 3) Guardar lo nuevos delitos de la relacion en el expediente
		for (DelitoDTO delitoDTO : listaDelitosDeLaRelacionDelitoPersona) {
			if (!hashDelitosExpediente.containsKey(delitoDTO.getCatDelitoDTO()
					.getClaveInterInstitucional().trim())) {

				CatDelito catDelitoFiltro = new CatDelito();
				catDelitoFiltro.setClaveInterInstitucional(delitoDTO.getCatDelitoDTO()
						.getClaveInterInstitucional().trim());

				List<CatDelito> catDelitosCatalogo = catDelitoDAO
						.consultarCatDelitoPorFilro(catDelitoFiltro);

				// Asociar al expediente
				if (catDelitosCatalogo == null || catDelitosCatalogo.isEmpty()) {
					logger.error("No existe el delito en el catalogo. Clave interinstitucional:"
							+ delitoDTO.getCatDelitoDTO().getClaveInterInstitucional());
					throw new NSJPNegocioException(
							CodigoError.CLAVE_DELITO_INTERINSTITUCIONAL_INEXISTENTE);
				}
				// Generamos el nuevo delito
				Delito delito = new Delito();
				delito.setEsProbable(delitoDTO.getEsProbable());
				delito.setExpediente(new Expediente(expedienteDTO
						.getExpedienteId()));
				delito.setEsPrincipal(delitoDTO.getEsPrincipal());
				delito.setCatDelito(new CatDelito(catDelitosCatalogo.get(0)
						.getCatDelitoId()));

				Long delitoId = delitoDAO.create(delito);

				// Guardamos los delitos en la lista para no repetirlos
				hashDelitosExpediente.put(delitoDTO.getCatDelitoDTO()
						.getClaveInterInstitucional(), delitoId);
				logger.info("Nuevo Delito creado:" + delitoId);
			}
			/*
			 * else{ Si se desea actualizar el delito, aqui se puede realizar }
			 */
		}

		/**
		 * Segunda parte: Guardar las nuevas relaciones delito persona
		 */

		// Buscar si la relacion ya existe, por folio
		for (DelitoPersonaDTO delitoPersonaDto : relDelitoPersonaDto) {
			DelitoPersonaDTO delitoPersonaObtenidaDto = consultarDelitoPersonaService
					.consultarUnicaRelacionDelitoPersonaPorFolio(delitoPersonaDto);
			// Si no existe la relacion
			if (delitoPersonaObtenidaDto == null) {

				DelitoPersona delPerPojo = new DelitoPersona();

				Long delitoId = hashDelitosExpediente.get(delitoPersonaDto
						.getDelito().getCatDelitoDTO()
						.getClaveInterInstitucional());

				delPerPojo.setDelito(new Delito(delitoId));

				delPerPojo.setEsActivo(delitoPersonaDto.getEsActivo());

				// Obtenemos los ids de los involucrados correspondientes
				Long probableResponsableId = hashProbablesResponsables
						.get(delitoPersonaDto.getProbableResponsable()
								.getFolioElemento());

				delPerPojo.setProbableResponsable(new Involucrado(
						probableResponsableId));

				Long victimaId = hashVictimas.get(delitoPersonaDto.getVictima()
						.getFolioElemento());

				delPerPojo.setVictima(new Involucrado(victimaId));

				delPerPojo.setFolioDelitoPersona(delitoPersonaDto
						.getFolioDelitoPersona());

				delitoPersonaDAO.create(delPerPojo);
			}
			/*
			 * else{ Aqui actualizaria la rel delito-persona encontrada}
			 */
		}
	}

	/**
	 * M&eacute;todo que obtiene la lista de delitos a partir de una lista
	 * de relaciones delito persona y valida los datos minimos de la relacion
	 * 
	 * @param listaRelDelPer, debe contener los DELITO, CLAVE_INTER_INSTITUCIONAL,PR,VICTIMA y FOLIO
	 * @return lista de delitos, de la relaci&oacute;n delito persona
	 * @throws NSJPNegocioException
	 */
	private List<DelitoDTO> obtenerListaDelitosDeLaRelacionDelitoPersona(
			List<DelitoPersonaDTO> listaRelDelPer) throws NSJPNegocioException {

		if (listaRelDelPer == null || listaRelDelPer.isEmpty()) {
			logger.error("***LISTA DE RELACIONES NULA O VACIA***");
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}

		List<DelitoDTO> listaDelitosDeLaRelacionDelitoPersona = new ArrayList<DelitoDTO>();

		for (DelitoPersonaDTO delitoPersonaDTO : listaRelDelPer) {
			if (delitoPersonaDTO.getDelito() == null
					|| delitoPersonaDTO.getDelito().getCatDelitoDTO().getClaveInterInstitucional()== null) {
				throw new NSJPNegocioException(
						CodigoError.CLAVE_DELITO_INTERINSTITUCIONAL_INEXISTENTE);
			}
			if(delitoPersonaDTO.getFolioDelitoPersona() == null){
				logger.error("***NO EXISTE EL FOLIO DE LA RELACION DP***");
				throw new NSJPNegocioException(
						CodigoError.INFORMACION_PARAMETROS_ERRONEA);
			}
			if (delitoPersonaDTO.getProbableResponsable() == null
					|| delitoPersonaDTO.getProbableResponsable()
							.getFolioElemento() == null
					|| delitoPersonaDTO.getVictima() == null
					|| delitoPersonaDTO.getVictima().getFolioElemento() == null) {
				logger.error("***NO EXISTE EL PR O LA VICTIMA EN LA RELACION***");
				throw new NSJPNegocioException(
						CodigoError.INFORMACION_PARAMETROS_ERRONEA);
			}
			listaDelitosDeLaRelacionDelitoPersona.add(delitoPersonaDTO
					.getDelito());
		}
		return listaDelitosDeLaRelacionDelitoPersona;
	}

}