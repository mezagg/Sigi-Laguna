/**
 * Nombre del Programa : RegistrarSolicitudServiceImpl.java
 * Autor                            : vaguirre
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 20 Jun 2011
 * Marca de cambio        : N/A
 * Descripcion General    : Implementaci�n para el registro de una solicitud
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

import java.util.Calendar;
import java.util.Date;

import mx.gob.segob.nsjp.comun.enums.documento.TipoDocumento;
import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.enums.forma.Formas;
import mx.gob.segob.nsjp.comun.enums.institucion.Instituciones;
import mx.gob.segob.nsjp.comun.enums.relacion.Relaciones;
import mx.gob.segob.nsjp.comun.enums.solicitud.EstatusSolicitud;
import mx.gob.segob.nsjp.comun.enums.solicitud.TiposSolicitudes;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.audiencia.AudienciaDAO;
import mx.gob.segob.nsjp.dao.audiencia.InvolucradoAudienciaDAO;
import mx.gob.segob.nsjp.dao.caso.CasoDAO;
import mx.gob.segob.nsjp.dao.expediente.ExpedienteDAO;
import mx.gob.segob.nsjp.dao.funcionarioexterno.FuncionarioExternoDAO;
import mx.gob.segob.nsjp.dao.institucion.ConfInstitucionDAO;
import mx.gob.segob.nsjp.dao.solicitud.SolicitudAdjuntosDAO;
import mx.gob.segob.nsjp.dao.solicitud.SolicitudAudienciaDAO;
import mx.gob.segob.nsjp.dao.solicitud.SolicitudDAO;
import mx.gob.segob.nsjp.dao.solicitud.SolicitudMandamientoDAO;
import mx.gob.segob.nsjp.dao.solicitud.SolicitudTranscricpionAudienciaDAO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.configuracion.ConfInstitucionDTO;
import mx.gob.segob.nsjp.dto.elemento.ElementoDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.forma.FormaDTO;
import mx.gob.segob.nsjp.dto.funcionarioexterno.FuncionarioExternoDTO;
import mx.gob.segob.nsjp.dto.institucion.AreaDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudAudienciaDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudMandamientoDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudTranscripcionAudienciaDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;
import mx.gob.segob.nsjp.model.Caso;
import mx.gob.segob.nsjp.model.ConfInstitucion;
import mx.gob.segob.nsjp.model.Expediente;
import mx.gob.segob.nsjp.model.Forma;
import mx.gob.segob.nsjp.model.Funcionario;
import mx.gob.segob.nsjp.model.FuncionarioExterno;
import mx.gob.segob.nsjp.model.Involucrado;
import mx.gob.segob.nsjp.model.InvolucradoAudiencia;
import mx.gob.segob.nsjp.model.InvolucradoAudienciaId;
import mx.gob.segob.nsjp.model.NumeroExpediente;
import mx.gob.segob.nsjp.model.Solicitud;
import mx.gob.segob.nsjp.model.SolicitudAdjuntos;
import mx.gob.segob.nsjp.model.SolicitudAdjuntosId;
import mx.gob.segob.nsjp.model.SolicitudAudiencia;
import mx.gob.segob.nsjp.model.SolicitudMandamiento;
import mx.gob.segob.nsjp.model.SolicitudTranscripcionAudiencia;
import mx.gob.segob.nsjp.model.Valor;
import mx.gob.segob.nsjp.service.documento.GenerarRelacionDocumentoElementoService;
import mx.gob.segob.nsjp.service.expediente.AsignarNumeroExpedienteService;
import mx.gob.segob.nsjp.service.funcionarioexterno.FuncionarioExternoService;
import mx.gob.segob.nsjp.service.funcionarioexterno.impl.transform.FuncionarioExternoTransformer;
import mx.gob.segob.nsjp.service.infra.ProcuraduriaClienteService;
import mx.gob.segob.nsjp.service.involucrado.IngresarIndividuoService;
import mx.gob.segob.nsjp.service.solicitud.GenerarFolioSolicitudService;
import mx.gob.segob.nsjp.service.solicitud.RegistrarSolicitudService;
import mx.gob.segob.nsjp.service.solicitud.impl.transform.SolicitudAudienciaTransformer;
import mx.gob.segob.nsjp.service.solicitud.impl.transform.SolicitudTransformer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementaci�n para el registro de una solicitud de una manera generica para
 * que pueda reusar.
 * 
 * @version 1.0
 * @author vaguirre
 * 
 */
@Service("registrarSolicitudService")
@Transactional
public class RegistrarSolicitudServiceImpl implements RegistrarSolicitudService {

    /**
	 * 
	 */
    public final static Logger LOGGER = Logger
            .getLogger(RegistrarSolicitudServiceImpl.class);

    @Autowired
    private SolicitudDAO solicitudDAO;
    @Autowired
    private SolicitudAudienciaDAO solicitudAudienciaDAO;
    @Autowired
    private SolicitudTranscricpionAudienciaDAO solicitudTranscricpionAudienciaDAO;
    @Autowired
    private SolicitudAdjuntosDAO solicitudAdjuntosDAO;
    @Autowired
    private GenerarRelacionDocumentoElementoService generarRelacionService;
    @Autowired
    private AsignarNumeroExpedienteService asignarNumeroExpedienteService;
    @Autowired
    private GenerarFolioSolicitudService generarFolioSolicitudService;
    @Autowired
    private AudienciaDAO audienciaDAO;
    @Autowired
    private InvolucradoAudienciaDAO involucradoAudienciaDAO;
    @Autowired
    private ProcuraduriaClienteService procuraduriaClienteService; 
    @Autowired
    private ExpedienteDAO expedienteDAO;
	@Autowired
	private ConfInstitucionDAO confInstitucionDAO;
	@Autowired
	private CasoDAO casoDAO;
	@Autowired
	private FuncionarioExternoDAO funcionarioExternoDAO;
	@Autowired
	private SolicitudMandamientoDAO solicitudMandamientoDAO;
	@Autowired
	private FuncionarioExternoService funcionarioExternoService;
	
    
    @Override
    public SolicitudDTO registrarSolicitud(SolicitudDTO input)
            throws NSJPNegocioException {

        if (input.getTipoSolicitudDTO() == null)
            throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
        
        if(input.getExpedienteDTO() == null || (input.getExpedienteDTO() != null && input.getExpedienteDTO().getNumeroExpedienteId() == null)){
        	String warning = "";
        	warning += "*************************************************************";
        	warning += "\nTODA SOLICITUD DEBE DE ESTAR ASOCIADA A UN NUMERO DE EXPEDIENTE";
        	warning += "\n*************************************************************";
        	LOGGER.warn(warning);
        }
        
        Solicitud solicitud = SolicitudTransformer.solicitudTransformer(input);
		solicitud.setEstatus(new Valor(EstatusSolicitud.ABIERTA.getValorId()));
        Long idSolicitud = input.getDocumentoId();
        String generarFolioSolicitud = "";
        if(idSolicitud != null && idSolicitud > 0){
        	Long idTipoSolicitud = solicitud.getTipoSolicitud().getValorId();
            if (idTipoSolicitud.compareTo(TiposSolicitudes.REINSERCION_SOCIAL.getValorId()) == 0
            		|| idTipoSolicitud.compareTo(TiposSolicitudes.CONSTANCIAS_CERERESO.getValorId()) == 0
            		|| idTipoSolicitud.compareTo(TiposSolicitudes.ESTUDIOS_CTI.getValorId()) == 0
            		|| idTipoSolicitud.compareTo(TiposSolicitudes.INFORMACION_DGPRS.getValorId()) == 0
            		|| idTipoSolicitud.compareTo(TiposSolicitudes.AVISO_DGPRS.getValorId()) == 0
            		|| idTipoSolicitud.compareTo(TiposSolicitudes.CUMPLIMIENTO_MANDAMIENTO_JUDICIAL.getValorId()) == 0) {
            	actualizarFuncionariosExternos(solicitud);
            	solicitud = guardarSolicitudReinsercionSocial(solicitud);
            } else {
            	
            	actualizarFuncionariosExternos(solicitud);
				solicitudDAO.update(solicitud);
            }
        }
        else{
            generarFolioSolicitud = generarFolioSolicitudService.generarFolioSolicitud();
            input.setFolioSolicitud(generarFolioSolicitud);
            solicitud.setFolioSolicitud(generarFolioSolicitud);
            solicitud.setForma(new Forma(Formas.SOLICITUD.getValorId()));
            solicitud.setTipoDocumento(new Valor(TipoDocumento.SOLICITUD.getValorId()));
            solicitud.setFechaCreacion(Calendar.getInstance().getTime());
            solicitud.setNombreDocumento("SOLICITUD_DE_"+TiposSolicitudes.getByValor(solicitud.getTipoSolicitud().getValorId()).name());
            if (solicitud.getTipoSolicitud().getValorId().compareTo(TiposSolicitudes.REINSERCION_SOCIAL.getValorId())==0
            		&& solicitud.getDocumentoId() != null) {
            	solicitud =	guardarSolicitudReinsercionSocial(solicitud);
            } else {
            	
            	actualizarFuncionariosExternos(solicitud);
            	
				idSolicitud = solicitudDAO.create(solicitud);
            }
        }
        input.setDocumentoId(idSolicitud);
        switch (TiposSolicitudes.getByValor(input.getTipoSolicitudDTO()
                .getIdCampo())) {
            case EVIDENCIA :

                for (ElementoDTO ele : input.getElementos()) {
                    generarRelacionService.generarRelacion(solicitud,
                            ele.getElementoId(),
                            Relaciones.EVIDENCIA_EN_SOLICITUD);
                }

                break;

            default :
                break;
        }

        LOGGER.debug("idNuevaSol :: " + idSolicitud);
        return input;
    }
    
	/**
	 * M&eacute;todo para guardar un funcionario externo y devolver su clave de
	 * funcionario
	 * 
	 * @param solicitud
	 * @return
	 * @throws NSJPNegocioException
	 */
    @Transactional
	private FuncionarioExterno ingrersarActulizarFuncionarioExterno(
			FuncionarioExterno funcionarioExternoInput)
			throws NSJPNegocioException {

		if (funcionarioExternoInput != null
				&& funcionarioExternoInput.getCveFuncionarioInstExt() != null
				&& funcionarioExternoInput.getConfInstitucion() != null
				&& funcionarioExternoInput.getConfInstitucion()
						.getConfInstitucionId() != null
				&& funcionarioExternoInput.getNombre() != null
				&& !funcionarioExternoInput.getNombre().trim().isEmpty()
				&& funcionarioExternoInput.getApellidoPaterno() != null
				&& !funcionarioExternoInput.getApellidoPaterno().trim().isEmpty()) {

			FuncionarioExterno funExt = funcionarioExternoDAO
					.consultarFuncExternoPorClaveFuncExt(
							funcionarioExternoInput.getCveFuncionarioInstExt(),
							funcionarioExternoInput.getConfInstitucion()
									.getConfInstitucionId());

			if (funExt != null) {
				funExt = FuncionarioExternoTransformer.updateFuncionarioExterno(funExt,
						funcionarioExternoInput);
				funcionarioExternoDAO.update(funExt);
				LOGGER.debug("el funcionario fue actualizado:: " + funExt);
				return funExt;
			} else {
				Long idFuncionarioExterno = funcionarioExternoDAO.create(funcionarioExternoInput);
				LOGGER.debug("el funcionario fue creado correctamente:: " + funcionarioExternoInput);
				funcionarioExternoInput.setFuncionarioExternoId(idFuncionarioExterno);
				return funcionarioExternoInput;
			}
		} else {
			LOGGER.debug("No se pudo crear el funcionadio :: " + funcionarioExternoInput);
			return null;
		}
	}
    

	/**
	 * @param solicitud
	 * @param idSolicitud
	 * @return
	 * @throws NSJPNegocioException
	 */
    @Transactional
	private Solicitud guardarSolicitudReinsercionSocial(Solicitud solicitud) throws NSJPNegocioException {
		
		Solicitud tmp = solicitudDAO.consultarSolicitudPorId(solicitud.getDocumentoId());
		if(tmp == null){
			String generarFolioSolicitud = generarFolioSolicitudService.generarFolioSolicitud();
            solicitud.setFolioSolicitud(generarFolioSolicitud);
		} else {
			solicitud.setFolioSolicitud(tmp.getFolioSolicitud());
		}
		solicitudDAO.crearSolicitudConDocumentoExistente(solicitud);
		return solicitud;
	}

    @Autowired
    public IngresarIndividuoService ingresarIndividuoService;
    
    @Transactional
	public SolicitudDTO registrarSolicitudOrdenDeDetencion(
			SolicitudDTO ordenDetencion, Long idDocumentoAnexo)throws NSJPNegocioException {
		
		ExpedienteDTO expedienteDTO = new ExpedienteDTO();
		expedienteDTO.setFechaApertura(Calendar.getInstance().getTime());
		expedienteDTO = asignarNumeroExpedienteService.asignarNumeroExpediente(expedienteDTO);

		ordenDetencion.setExpedienteDTO(expedienteDTO);
		ordenDetencion.getInvolucradoDTO().setExpedienteDTO(expedienteDTO);
		Long idIndividuo = ingresarIndividuoService.ingresarIndividuo(ordenDetencion.getInvolucradoDTO());
		ordenDetencion.getInvolucradoDTO().setElementoId(idIndividuo);

		ordenDetencion.setTipoSolicitudDTO(new ValorDTO(TiposSolicitudes.ORDEN_DETENCION.getValorId()));
		ordenDetencion.setInvolucradoDTO(new InvolucradoDTO(1646L));
		ordenDetencion.setEstatus(new ValorDTO(EstatusSolicitud.ABIERTA.getValorId()));
		ordenDetencion.setFechaCreacion(new Date());
		ordenDetencion.setFechaLimite(null);
		ordenDetencion.setFechaCierre(null);
		ordenDetencion.setFechaModificacion(null);
		ordenDetencion.setEsUrgente(false);
		ordenDetencion.setTipoDocumentoDTO(new ValorDTO(TipoDocumento.SOLICITUD.getValorId()));
		ordenDetencion.setFormaDTO(new FormaDTO(1L));
		ordenDetencion.setNombreDocumento("Orden de Detenci�n");
		
		Solicitud solicitud = SolicitudTransformer.solicitudTransformer(ordenDetencion);
		
        solicitud.setFolioSolicitud(generarFolioSolicitudService.generarFolioSolicitud());
		
        Long idSolicitud = solicitudDAO.create(solicitud);
		
		SolicitudAdjuntos adjunto = new SolicitudAdjuntos();
		SolicitudAdjuntosId id = new SolicitudAdjuntosId();
		id.setArchivoDigitalId(idDocumentoAnexo);
		id.setSolicitudId(idSolicitud);
		adjunto.setId(id);
		
		solicitudAdjuntosDAO.create(adjunto);
		
		return null;
	}
	
	@Override
	@Transactional
	public SolicitudAudienciaDTO registrarSolicitudAudiencia(
			SolicitudAudienciaDTO solicitud) throws NSJPNegocioException {
		
		if(solicitud.getExpedienteDTO() == null){
			ExpedienteDTO expedienteDTO = new ExpedienteDTO();
			AreaDTO area = new AreaDTO();
			area.setAreaId(Instituciones.PJ.getValorId());
			expedienteDTO.setArea(area);
			expedienteDTO.setUsuario(solicitud.getUsuario());
			expedienteDTO = asignarNumeroExpedienteService.asignarNumeroExpediente(expedienteDTO);
			solicitud.setExpedienteDTO(expedienteDTO);
		}
		
		solicitud.getAudiencia().setFechaSolicitud(Calendar.getInstance().getTime());
		SolicitudAudiencia sol = SolicitudAudienciaTransformer.transformarSolicitud(solicitud);
		sol.setFolioSolicitud(generarFolioSolicitudService.generarFolioSolicitud());
		if(solicitud.getFormaDTO() != null){
			sol.setForma(new Forma(solicitud.getFormaDTO().getFormaId()));
		}else{
			sol.setForma(new Forma(Formas.SOLICITUD.getValorId()));
		}
		
		sol.setTipoDocumento(new Valor(TipoDocumento.SOLICITUD.getValorId()));
		sol.setFechaCreacion(Calendar.getInstance().getTime());
		sol.setNombreDocumento("SOLICITUD_DE_AUDIENCIA");
		sol.getAudiencia().setNumeroExpediente(new NumeroExpediente());
		sol.getAudiencia().getNumeroExpediente().setNumeroExpedienteId(solicitud.getExpedienteDTO().getNumeroExpedienteId());
		sol.getAudiencia().setConsecutivo(Short.decode("1")); //TODO EH
		
		audienciaDAO.create(sol.getAudiencia());
		solicitud.setDocumentoId(solicitudAudienciaDAO.create(sol));
		if(solicitud.getAudiencia().getInvolucrados() != null ){
			InvolucradoAudiencia invBD = null;
			for(InvolucradoDTO invDTO:solicitud.getAudiencia().getInvolucrados()){
				invBD = new InvolucradoAudiencia();
				invBD.setAudiencia(sol.getAudiencia());
				invBD.setInvolucrado(new Involucrado(invDTO.getElementoId()));
				invBD.setId(new InvolucradoAudienciaId(sol.getAudiencia().getAudienciaId(), invDTO.getElementoId()));
				involucradoAudienciaDAO.create(invBD);
			}
		}
		return solicitud;
	}
	
	@Override
	@Transactional
	public SolicitudDTO registrarSolicitudCarpetaInvestigacion(
			Long idNumeroExpediente, UsuarioDTO usuario)
			throws NSJPNegocioException {
		
		ExpedienteDTO expedienteDTO = new ExpedienteDTO();
		SolicitudDTO solicitud = new SolicitudDTO();
		Expediente expediente = null;
		String numeroGeneralCaso = "";
		Caso caso =null;
		Long catDiscriminante=null;
		
		if(idNumeroExpediente == null || usuario == null){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
				
		expediente = expedienteDAO.consultarExpedientePorIdNumerExpediente(idNumeroExpediente);
		expedienteDTO.setExpedienteId(expediente.getExpedienteId());
		
		caso = expediente.getCaso();
		
		if(caso != null){
			numeroGeneralCaso = caso.getNumeroGeneralCaso();
		}
		solicitud.setTipoSolicitudDTO(new ValorDTO(TiposSolicitudes.CARPETA_DE_INVESTIGACION.getValorId()));
		LOGGER.info("===> Numero Geeral de Caso "+numeroGeneralCaso);
		if(numeroGeneralCaso!=null && numeroGeneralCaso.equals("")){
			Caso caso2=casoDAO.consultarCasoPorExpediente(expediente.getExpedienteId());
			numeroGeneralCaso=caso2.getNumeroGeneralCaso();
		}
		solicitud.setNumeroCasoAsociado(numeroGeneralCaso);
		solicitud.setFolioSolicitud(generarFolioSolicitudService.generarFolioSolicitud());
		solicitud.setNombreSolicitante(usuario.getFuncionario().getNombreCompleto());
		
		Solicitud sol = SolicitudTransformer.solicitudTransformer(solicitud);
		sol.setTipoDocumento(new Valor(TipoDocumento.SOLICITUD.getValorId()));
		sol.setForma(new Forma(Formas.SOLICITUD.getValorId()));
		sol.setEstatus(new Valor(EstatusSolicitud.ABIERTA.getValorId()));
		sol.setFechaCreacion(Calendar.getInstance().getTime());
		sol.setNombreDocumento("SOLICITUD_DE_CARPETA_DE_INVESTIGACION");
		sol.setNumeroExpediente(new NumeroExpediente(idNumeroExpediente));
		sol.setConfInstitucion(new ConfInstitucion(Instituciones.DEF.getValorId()));
		//Permitira consultarlas el rol agente mp en Fiscalia
		sol.setAreaOrigen(usuario.getAreaActual().getAreaId());
		
		sol.setFuncionarioSolicitante(new Funcionario(usuario.getFuncionario().getClaveFuncionario()));
		
		solicitud.setNombreSolicitanteExternoInterno(solicitud.getNombreSolicitante());

		FuncionarioExternoDTO funcionarioSolicitanteExternoDTO = FuncionarioExternoTransformer
				.transformarFuncionarioLocal(usuario.getFuncionario());
		solicitud.setSolicitanteInstExterna(funcionarioSolicitanteExternoDTO);
		
		try{
			LOGGER.info("Antes de invocar al web services.");
			LOGGER.info(solicitud);
			SolicitudDTO solicitudDTO = procuraduriaClienteService
					.solicitarCopiaCarpetaDeInvestigacion(solicitud,
							catDiscriminante);
			
			LOGGER.info("SE ENVIO LA SOLICITUD DE CARPETA DE INVESTIGACION CON FOLIO "+solicitud.getFolioSolicitud());
			solicitud.setNombreSolicitanteExternoInterno("");
			
			if (solicitudDTO.getDestinatarioInstExterna() != null
					&& solicitudDTO.getDestinatarioInstExterna()
							.getCveFuncionarioInstExt() != null
					&& solicitudDTO.getDestinatarioInstExterna()
							.getCveFuncionarioInstExt() > 0L) {
				/**
				 * Si se ingresa o actuliza al funcionario externo
				 */
				FuncionarioExternoDTO funcionarioExternoDTO = solicitudDTO
						.getDestinatarioInstExterna();
				funcionarioExternoDTO = funcionarioExternoService
						.guardarActualizarFuncionarioExternoPorIClaveFuncionarioInsExt(funcionarioExternoDTO);
				if (funcionarioExternoDTO != null
						&& funcionarioExternoDTO.getFuncionarioExternoId() != null
						&& funcionarioExternoDTO.getFuncionarioExternoId() > 0L) {
					sol.setFuncionarioDestExt(new FuncionarioExterno(
							funcionarioExternoDTO.getFuncionarioExternoId()));
					LOGGER.debug("FuncionarioDEstinatario:"
							+ sol.getFuncionarioDestExt()
									.getFuncionarioExternoId());
				}
			}
			
			LOGGER.info("A PUNTO DE CREAR LA SOLICITUD DE CARPETA DE INVESTIGACION");
			Long id = solicitudDAO.create(sol);
			LOGGER.info("SE CREO LA SOLICITUD DE CARPETA DE INVESTIGACION CON ID "+id);
			solicitud.setDocumentoId(id);
			
		}catch(Exception e){
			throw new NSJPNegocioException(CodigoError.ERROR_COMUNICACION);
		}
		return solicitud;
	}
	
	@Override
	@Transactional
	public SolicitudTranscripcionAudienciaDTO registrarSolicitudTranscripcionAudiencia(
			SolicitudTranscripcionAudienciaDTO solicitud) throws NSJPNegocioException {
		SolicitudTranscripcionAudiencia sol = SolicitudAudienciaTransformer.transformarSolicitud(solicitud);
		if(solicitud.getFolioSolicitud() == null || (solicitud.getFolioSolicitud() != null && solicitud.getFolioSolicitud().trim().equals("")))
			sol.setFolioSolicitud(generarFolioSolicitudService.generarFolioSolicitud());
		sol.setTipoDocumento(new Valor(TipoDocumento.SOLICITUD.getValorId()));
		
		sol.setNombreDocumento("SOLICITUD_DE");
		//
		
		Long idSolicitud = solicitudTranscricpionAudienciaDAO.create(sol);
		solicitud.setDocumentoId(idSolicitud);
		//Recupera el folio asignado y lo asigna a la solicitud
		solicitud.setFolioSolicitud(solicitudDAO.obtenerFolioDeSolicitud(idSolicitud));
		//Se asigna el confInstitucion
		solicitud.setInstitucion(new ConfInstitucionDTO(confInstitucionDAO.consultarInsitucionActual().getConfInstitucionId()));
		return solicitud;
	}
	
	/**
	 * M&eacute;todo que lleva a cabo la actualizaci&oacute;n o creaci&oacute;n de los funcionarios 
	 * externos contenidos dentro de una solicitud, considera tanto destinatarios como solicitantes 
	 * de la solicitud pasada como argumento.
	 * @param solicitud - Solicitud de la cual se crear&aacute;n o actualizar&aacute;n los 
	 * 					  funcionarios externos. 
	 */
	@Transactional
	private void actualizarFuncionariosExternos(Solicitud solicitud){
    	//Se valida el funcionario externo destinatario
		FuncionarioExterno funcionarioExterno;
		try {
			funcionarioExterno = ingrersarActulizarFuncionarioExterno(solicitud
					.getFuncionarioDestExt());
			if (funcionarioExterno != null
					&& funcionarioExterno.getFuncionarioExternoId() != null) {
				solicitud.setFuncionarioDestExt(funcionarioExterno);
			}
			
			//Se valida el funcionario externo solicitante
			FuncionarioExterno funcionarioExternoSolicitante = ingrersarActulizarFuncionarioExterno(solicitud
					.getFuncionarioSolExt());
			
			if (funcionarioExternoSolicitante != null
					&& funcionarioExternoSolicitante.getFuncionarioExternoId() != null) {
				solicitud.setFuncionarioSolExt(funcionarioExternoSolicitante);
			}
		} catch (NSJPNegocioException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.solicitud.RegistrarSolicitudService#crearSolicitudMandamientoConInfoExistente(mx.gob.segob.nsjp.dto.solicitud.SolicitudMandamientoDTO)
	 */
	@Override
	public SolicitudMandamientoDTO crearSolicitudMandamientoConInfoExistente(
			SolicitudMandamientoDTO solicitudMandamientoDTO)
			throws NSJPNegocioException {
		
		SolicitudMandamiento entity = SolicitudTransformer.solicitudTransformer(solicitudMandamientoDTO);
		
		solicitudMandamientoDAO.crearSolicitudMandamientoConInfoExistente(entity);
		
		return solicitudMandamientoDTO;
	}
}
