/**
 * Nombre del Programa : PJClienteServiceImpl.java
 * Autor                            : vaguirre
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 9 Jul 2011
 * Marca de cambio        : N/A
 * Descripcion General    : Implementaci�n del cliente del ws
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
package mx.gob.segob.nsjp.service.infra.impl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.xml.namespace.QName;

import mx.gob.segob.nsjp.comun.enums.audiencia.EstatusAudiencia;
import mx.gob.segob.nsjp.comun.enums.audiencia.EstatusPermisosAudiencia;
import mx.gob.segob.nsjp.comun.enums.calidad.Calidades;
import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.enums.institucion.Instituciones;
import mx.gob.segob.nsjp.comun.enums.seguridad.Roles;
import mx.gob.segob.nsjp.comun.enums.solicitud.EstatusSolicitud;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.catalogo.ValorDAO;
import mx.gob.segob.nsjp.dao.institucion.ConfInstitucionDAO;
import mx.gob.segob.nsjp.dao.persona.CorreoElectronicoDAO;
import mx.gob.segob.nsjp.dto.archivo.ArchivoDigitalDTO;
import mx.gob.segob.nsjp.dto.audiencia.AudienciaDTO;
import mx.gob.segob.nsjp.dto.audiencia.AudienciaJAVSTransporteDTO;
import mx.gob.segob.nsjp.dto.audiencia.SalaAudienciaDTO;
import mx.gob.segob.nsjp.dto.caso.CasoDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.documento.DocumentoDTO;
import mx.gob.segob.nsjp.dto.expediente.DelitoPersonaDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.forma.FormaDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoViewDTO;
import mx.gob.segob.nsjp.dto.objeto.ObjetoDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudAudienciaDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudTranscripcionAudienciaDTO;
import mx.gob.segob.nsjp.model.ConfInstitucion;
import mx.gob.segob.nsjp.model.CorreoElectronico;
import mx.gob.segob.nsjp.model.Valor;
import mx.gob.segob.nsjp.service.caso.BuscarCasoService;
import mx.gob.segob.nsjp.service.infra.PJClienteService;
import mx.gob.segob.nsjp.service.infra.impl.transform.WsTransformer;
import mx.gob.segob.nsjp.service.infra.impl.transform.enviarReplicaCaso.CasoWSDTOTransformer;
import mx.gob.segob.nsjp.service.infra.impl.transform.enviardocumentoincumplimiento.DocumentoWSDTOTransformer;
import mx.gob.segob.nsjp.service.infra.impl.transform.recibiracusederecibodesolicituddedefensor.SolicitudWSDTOTransformer;
import mx.gob.segob.nsjp.service.infra.impl.transform.solicitudaudiencia.FuncionarioExternoWSDTOTransformer;
import mx.gob.segob.nsjp.service.infra.impl.transform.solicitudaudiencia.InvolucradoWSDTOTransformer;
import mx.gob.segob.nsjp.service.infra.impl.transform.solicitudaudiencia.ObjetosWSDTOTransformer;
import mx.gob.segob.nsjp.service.solicitud.RegistrarSolicitudService;
import mx.gob.segob.nsjp.ws.cliente.administrarPermisosAudiencia.AdministrarPermisosAudienciaExporter;
import mx.gob.segob.nsjp.ws.cliente.administrarPermisosAudiencia.AdministrarPermisosAudienciaExporterImplService;
import mx.gob.segob.nsjp.ws.cliente.administrarPermisosAudiencia.AudienciaJAVSTransporteWSDTO;
import mx.gob.segob.nsjp.ws.cliente.administrarPermisosAudiencia.FuncionarioExternoWSDTO;
import mx.gob.segob.nsjp.ws.cliente.consultaraudiencia.AudienciaDefensoriaWSDTO;
import mx.gob.segob.nsjp.ws.cliente.consultaraudiencia.ConsultarAudienciasByFechasYEstatusExporter;
import mx.gob.segob.nsjp.ws.cliente.consultaraudiencia.ConsultarAudienciasByFechasYEstatusExporterImplService;
import mx.gob.segob.nsjp.ws.cliente.consultaraudiencia.InvolucradoAudienciaDefWSDTO;
import mx.gob.segob.nsjp.ws.cliente.enviardocumentoincumplimientomedida.DocumentoWSDTO;
import mx.gob.segob.nsjp.ws.cliente.enviardocumentoincumplimientomedida.EnviarDocumentoIncumplimientoMedidaExporter;
import mx.gob.segob.nsjp.ws.cliente.enviardocumentoincumplimientomedida.EnviarDocumentoIncumplimientoMedidaExporterImplService;
import mx.gob.segob.nsjp.ws.cliente.seguimiento.ActualizarSeguimientoMandamientoMedidaExporter;
import mx.gob.segob.nsjp.ws.cliente.seguimiento.ActualizarSeguimientoMandamientoMedidaExporterImplService;
import mx.gob.segob.nsjp.ws.cliente.seguimiento.ArchivoDigitalWSDTO;
import mx.gob.segob.nsjp.ws.cliente.seguimiento.SeguimientoMandamientoMedidaWSDTO;
import mx.gob.segob.nsjp.ws.cliente.solicitudaudiencia.AudienciaWSDTO;
import mx.gob.segob.nsjp.ws.cliente.solicitudaudiencia.CasoWSDTO;
import mx.gob.segob.nsjp.ws.cliente.solicitudaudiencia.DelitoPersonaWSDTO;
import mx.gob.segob.nsjp.ws.cliente.solicitudaudiencia.InvolucradoWSDTO;
import mx.gob.segob.nsjp.ws.cliente.solicitudaudiencia.NSJPNegocioException_Exception;
import mx.gob.segob.nsjp.ws.cliente.solicitudaudiencia.ObjetoWSDTO;
import mx.gob.segob.nsjp.ws.cliente.solicitudaudiencia.SolicitarAudienciaBasicoExporter;
import mx.gob.segob.nsjp.ws.cliente.solicitudaudiencia.SolicitarAudienciaBasicoExporterImplService;
import mx.gob.segob.nsjp.ws.cliente.solicitudaudiencia.SolicitudAudienciaBasicoWSDTO;
import mx.gob.segob.nsjp.ws.cliente.solicitudtranscripcion.RegistrarSolicitudTranscripcionAreaExternaExporter;
import mx.gob.segob.nsjp.ws.cliente.solicitudtranscripcion.RegistrarSolicitudTranscripcionAreaExternaExporterImplService;
import mx.gob.segob.nsjp.ws.cliente.solicitudtranscripcion.SolicitudTranscripcionWSDTO;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementaci�n del cliente del ws.
 * 
 * @version 1.0
 * @author vaguirre
 * 
 */
@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class PJClienteServiceImpl implements PJClienteService {
    /**
     * Logger.
     */
    private final static Logger logger = Logger
            .getLogger(PJClienteServiceImpl.class);
    @Autowired
    private ConfInstitucionDAO confInsDao;
    @Autowired
    private ValorDAO valorDAO;   
    @Autowired
    private CorreoElectronicoDAO correoElectronicoDAO;   
    @Autowired
    private RegistrarSolicitudService registrarSolicitudService;
    @Autowired
	private BuscarCasoService buscarCasoService;
    
    /*
     * (non-Javadoc)
     * 
     * @see
     * mx.gob.segob.nsjp.service.infra.PJClienteService#enviarSolicitudAudiencia
     * (mx.gob.segob.nsjp.dto.solicitud.SolicitudAudienciaDTO)
     */
	@Override
	public SolicitudAudienciaDTO enviarSolicitudAudiencia(
			SolicitudAudienciaDTO input, ExpedienteDTO expediente,
			Long idDistrito, Long idTribunal, Long idClaveFuncionario,
			List<DelitoPersonaDTO> relacionesDelitoPersona)
			throws NSJPNegocioException {

		URL ep;
		if (logger.isDebugEnabled()) {
			logger.debug("input :: " + input);
		}
		try {
			ep = new URL(confInsDao.read(Instituciones.PJ.getValorId())
					.getUrlInst()
					+ "SolicitarAudienciaBasicoExporterImplService?wsdl");

			final QName SERVICE_NAME = new QName(
					"http://impl.ws.service.nsjp.segob.gob.mx/",
					"SolicitarAudienciaBasicoExporterImplService");
			SolicitarAudienciaBasicoExporterImplService ss = new SolicitarAudienciaBasicoExporterImplService(
					ep, SERVICE_NAME);

			SolicitarAudienciaBasicoExporter port = ss
					.getSolicitarAudienciaBasicoExporterImplPort();

			SolicitudAudienciaBasicoWSDTO toSend = new SolicitudAudienciaBasicoWSDTO();

			ConfInstitucion institucion = confInsDao
					.consultarInsitucionActual();

			if (expediente.getCasoDTO() != null) {
				toSend.setNumeroCasoAsociado(expediente.getCasoDTO()
						.getNumeroGeneralCaso());
			}
			toSend.setNombreSolicitante(input.getNombreSolicitante());
			toSend.setFechaLimite(WsTransformer.transformFecha(input
					.getFechaLimite()));
			AudienciaWSDTO aud = new AudienciaWSDTO();
			aud.setTipoAudienciaId(input.getAudiencia().getTipoAudiencia()
					.getIdCampo());
			aud.setMotivo(input.getMotivo());
			aud.setEstatusAudienciaId(EstatusAudiencia.SOLICITADA.getValorId());
			toSend.setConfInstitucionId(institucion.getConfInstitucionId());
			toSend.setAudiencia(aud);
			toSend.setAreaSolicitanteId(input.getAreaOrigen());
			toSend.setNumeroCasoAsociado(input.getNumeroCasoAsociado());
			toSend.setFolioSolicitud(input.getFolioSolicitud());

			toSend.getInvolucradosDTO().addAll(
					this.tranformarInvolucrados(this
							.recuperarInvolucrados(expediente)));

			if (institucion.getConfInstitucionId().longValue() != Instituciones.DEF
					.getValorId()
					&& institucion.getConfInstitucionId().longValue() != Instituciones.PGJ
							.getValorId()) {
				if (expediente.getObjetosDTO() != null) {
					List<ObjetoWSDTO> objetosWSDTO = new ArrayList<ObjetoWSDTO>();
					for (ObjetoDTO objetoDTO : expediente.getObjetosDTO()) {
						ObjetoWSDTO objetoWSDTO = new ObjetoWSDTO();
						objetoWSDTO = ObjetosWSDTOTransformer
								.transformarObjeto(objetoDTO);
						objetosWSDTO.add(objetoWSDTO);
					}
					logger.debug("objetosWSDTO.size() :: "
							+ objetosWSDTO.size());
					toSend.getObjetosDTO().addAll(objetosWSDTO);
				}
			}
			
			mx.gob.segob.nsjp.ws.cliente.solicitudaudiencia.FuncionarioExternoWSDTO solicitanteFunExt = FuncionarioExternoWSDTOTransformer
					.transformarWSDTO(expediente.getUsuario().getFuncionario());

			// Para NO tener impacto en las consultas de PJ en individualizacion
			//TODO generar un servicio para concentrar estas reglas de negocio
			//se utiliza tambien en acuse de recibo de solicitu de defensor
			if (institucion.getConfInstitucionId().equals(
					Instituciones.PGJ.getValorId())) {
				solicitanteFunExt.setRolId(Roles.AGENTEMP.getValorId());
			} else if (institucion.getConfInstitucionId().equals(
					Instituciones.DEF.getValorId())) {
				solicitanteFunExt.setRolId(Roles.DEFENSOR.getValorId());
			} else {
				solicitanteFunExt.setRolId(Roles.AGENTEMP.getValorId());
			}

			solicitanteFunExt.setConfInstId(institucion.getConfInstitucionId());
			solicitanteFunExt.setConfInstitucionId(institucion
					.getConfInstitucionId());
			
			List<CorreoElectronico> correoElects = correoElectronicoDAO
					.consultarCorreosByFuncionario(solicitanteFunExt
							.getCveFuncionarioInstExt());
			
			if (correoElects != null && !correoElects.isEmpty()
					&& correoElects.get(0) != null
					&& correoElects.get(0).getDireccionElectronica() != null) {
				solicitanteFunExt.setEmail(correoElects.get(0)
						.getDireccionElectronica());
			}

			toSend.setFuncionarioExternoSolicitante(solicitanteFunExt);

			// Se pasan los nuevos valores
			toSend.setDistritoId(idDistrito);
			toSend.setTribunalId(idTribunal);
			toSend.setClaveFuncionarioId(idClaveFuncionario);

			// Relaciones delito persona
			List<DelitoPersonaWSDTO> relDelitoPerWSDTO = SolicitudWSDTOTransformer
					.transforma(relacionesDelitoPersona);
			if (relDelitoPerWSDTO != null && !relDelitoPerWSDTO.isEmpty()) {
				toSend.getRelacionesDelitoPersona().addAll(relDelitoPerWSDTO);
			}

			logger.debug("A punto de enviar la solicitud de audiencia por :: "
					+ ep);

			// Consulta de Datos del caso
			CasoDTO casoDTO = buscarCasoService
					.buscarCasoPorNumeroExacto(new CasoDTO(null, input
							.getNumeroCasoAsociado()));
			// Se asocian a la solicitud 
			CasoWSDTO casoWSDTO =CasoWSDTOTransformer.transformarCasoSolicitudAudiencia(casoDTO);
			toSend.setCasoWSDTO(casoWSDTO);
			logger.info("###CASO: " + casoWSDTO.getNumeroGeneralCaso() + "-"
					+ casoWSDTO.getFechaApertura());
			
			final Long resp = port.registrarSolicitudAudienciaBasico(toSend);
			logger.info(" Respuesta: " + resp);
			SolicitudAudienciaDTO response = new SolicitudAudienciaDTO();
			response.setDocumentoId(resp);
			return response;

		} catch (javax.xml.ws.soap.SOAPFaultException e) {
			logger.error(e.getMessage());
			throw new NSJPNegocioException(CodigoError.ERROR_COMUNICACION, e);
		} catch (MalformedURLException e) {
			logger.error(e.getMessage());
			throw new NSJPNegocioException(CodigoError.ERROR_COMUNICACION, e);
		} catch (NSJPNegocioException_Exception e) {
			throw new NSJPNegocioException(CodigoError.ERROR_COMUNICACION);				
		} catch (Exception e){
			throw new NSJPNegocioException(CodigoError.ERROR_COMUNICACION);
		}
	}

    /**
     * 
     * @param input
     * @return
     */
    private List<InvolucradoWSDTO> tranformarInvolucrados(List<InvolucradoDTO> input) {
        List<InvolucradoWSDTO> resp = new ArrayList<InvolucradoWSDTO>();
        for (InvolucradoDTO invo: input) {
            resp.add(InvolucradoWSDTOTransformer.transforma2Send(invo));
        }
        logger.debug("resp.size() :: "+resp.size());
        return resp;
    }
    

    /**
     * @param expConCaso
     * @return
     */
    private List<InvolucradoDTO> recuperarInvolucrados(ExpedienteDTO expConCaso) {
        final List<InvolucradoDTO> invoFuente = new ArrayList<InvolucradoDTO>();
        invoFuente.addAll(expConCaso.getInvolucradoByCalidad(Calidades.DENUNCIANTE));
        invoFuente.addAll(expConCaso.getInvolucradoByCalidad(Calidades.DENUNCIANTE_ORGANIZACION));
        invoFuente.addAll(expConCaso.getInvolucradoByCalidad(Calidades.PROBABLE_RESPONSABLE_PERSONA));
        invoFuente.addAll(expConCaso.getInvolucradoByCalidad(Calidades.PROBABLE_RESPONSABLE_ORGANIZACION));
        invoFuente.addAll(expConCaso.getInvolucradoByCalidad(Calidades.VICTIMA_PERSONA));
        invoFuente.addAll(expConCaso.getInvolucradoByCalidad(Calidades.TESTIGO));
        invoFuente.addAll(expConCaso.getInvolucradoByCalidad(Calidades.DEFENSOR_PRIVADO));
        invoFuente.addAll(expConCaso.getInvolucradoByCalidad(Calidades.DEFENSOR_PUBLICO));

        return invoFuente;
    }
    
    @Override
    public List<AudienciaDTO> consultarAudienciasByFechasYEstatus(
            AudienciaDTO input) throws NSJPNegocioException {

        try {
            URL url = new URL(
                    confInsDao.read(Instituciones.PJ.getValorId()).getUrlInst()
                            + "/ConsultarAudienciasByFechasYEstatusExporterImplService?wsdl");

            final QName SERVICE_NAME = new QName(
                    "http://impl.ws.service.nsjp.segob.gob.mx/",
                    "ConsultarAudienciasByFechasYEstatusExporterImplService");

            ConsultarAudienciasByFechasYEstatusExporterImplService ss = new ConsultarAudienciasByFechasYEstatusExporterImplService(
                    url, SERVICE_NAME);

            ConsultarAudienciasByFechasYEstatusExporter port = ss
                    .getConsultarAudienciasByFechasYEstatusExporterImplPort();

            AudienciaDefensoriaWSDTO toSend = new AudienciaDefensoriaWSDTO();

            if (input.getFechaFiltroInicio() != null){
            		toSend.setFiltroFechaInicio(WsTransformer.transformFecha(input
                            .getFechaFiltroInicio()));
            }
                    
            if(input.getFechaFiltroFin() != null) {
            		toSend.setFiltroFechaFinal(WsTransformer.transformFecha(input
            				.getFechaFiltroFin()));
            }            
            
            if (input.getIdDistritoFiltroAudiencias() != null){
        		toSend.setIdDistritoFiltroAudiencias(input.getIdDistritoFiltroAudiencias());
        }
            
            
            logger.debug("Consultado audiencias a trav�s de :: " + url);
            List<AudienciaDefensoriaWSDTO> respuesta = port
                    .consultarAudienciasByFechasYEstatus(toSend);
            List<AudienciaDTO> retorno = new ArrayList<AudienciaDTO>();
            for (AudienciaDefensoriaWSDTO audiencia : respuesta) {
                AudienciaDTO audienciaDTO = new AudienciaDTO();
                audienciaDTO.setId(audiencia.getAudienciaId());
                Valor tipo = valorDAO.read(audiencia.getTipoAudienciaId());
                audienciaDTO.setTipoAudiencia(new ValorDTO(tipo.getValorId(),
                        tipo.getValor()));
                ExpedienteDTO expediente = new ExpedienteDTO();
                CasoDTO caso = new CasoDTO();
                caso.setNumeroGeneralCaso(audiencia.getNumeroCaso());
                expediente.setCasoDTO(caso);
                audienciaDTO.setExpediente(expediente);
                SalaAudienciaDTO sala = new SalaAudienciaDTO();
                sala.setNombreSala(audiencia.getNombreSala());
                sala.setUbicacionSala(audiencia.getUbicacionSala());
                sala.setDomicilioSala(audiencia.getDomicilioSala());
                audienciaDTO.setSala(sala);
                audienciaDTO.setCaracter(audiencia.getCaracter());
                audienciaDTO.setFechaEvento(WsTransformer
                        .tranformXmlDate(audiencia.getFechaAudiencia()));
                retorno.add(audienciaDTO);
            }

            return retorno;
        } catch (MalformedURLException e) {
            logger.error(e.getMessage());
            throw new NSJPNegocioException(CodigoError.ERROR_COMUNICACION, e);
        } catch (mx.gob.segob.nsjp.ws.cliente.consultaraudiencia.NSJPNegocioException_Exception e) {
            logger.error(e.getMessage());
            throw new NSJPNegocioException(CodigoError.ERROR_COMUNICACION, e);
        }

    }
    
    @Override
    public List<AudienciaDTO>  consultarAudienciasPorFechasYSolicitudTranscripcion(
    		AudienciaDTO input, Long claveFuncionarioExt,
			String cadenaEstatus, String cadenaTipo, Long confInstId) throws NSJPNegocioException {
    	
        try {
        	if(input==null){
        		throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
        	}
            URL url = new URL(
                    confInsDao.read(Instituciones.PJ.getValorId()).getUrlInst()
                            + "/ConsultarAudienciasByFechasYEstatusExporterImplService?wsdl");

            final QName SERVICE_NAME = new QName(
                    "http://impl.ws.service.nsjp.segob.gob.mx/",
                    "ConsultarAudienciasByFechasYEstatusExporterImplService");

            ConsultarAudienciasByFechasYEstatusExporterImplService ss = new ConsultarAudienciasByFechasYEstatusExporterImplService(
                    url, SERVICE_NAME);

            ConsultarAudienciasByFechasYEstatusExporter port = ss
                    .getConsultarAudienciasByFechasYEstatusExporterImplPort();

            AudienciaDefensoriaWSDTO toSend = new AudienciaDefensoriaWSDTO();

            if (input.getFechaFiltroInicio() != null){
            		toSend.setFiltroFechaInicio(WsTransformer.transformFecha(input
                            .getFechaFiltroInicio()));
            }
                    
            if(input.getFechaFiltroFin() != null) {
            		toSend.setFiltroFechaFinal(WsTransformer.transformFecha(input
            				.getFechaFiltroFin()));
            }            
            
            if(input.getIdDistritoFiltroAudiencias() != null){
        		toSend.setIdDistritoFiltroAudiencias(input.getIdDistritoFiltroAudiencias());
            }
            
           	toSend.setClaveFuncionarioExt(claveFuncionarioExt);           
           	toSend.setCadenaEstatus(cadenaEstatus);            	
           	toSend.setCadenaTipo(cadenaTipo);
           	toSend.setConfInstId(confInstId);
            
            logger.debug("Consultado audiencias a traves de :: " + url);
            List<AudienciaDefensoriaWSDTO> respuesta = port
                    .consultarAudienciasPorFechasYSolicitudTranscripcion(toSend);
            
            List<AudienciaDTO> retorno = new ArrayList<AudienciaDTO>();
            
			for (AudienciaDefensoriaWSDTO audiencia : respuesta) {

				AudienciaDTO audienciaDTO = new AudienciaDTO();
				ExpedienteDTO expediente = new ExpedienteDTO();
				CasoDTO caso = new CasoDTO();

				audienciaDTO.setId(audiencia.getAudienciaId());
				Valor tipo = valorDAO.read(audiencia.getTipoAudienciaId());

				audienciaDTO.setTipoAudiencia(new ValorDTO(tipo.getValorId(),
						tipo.getValor()));
				caso.setNumeroGeneralCaso(audiencia.getNumeroCaso());
				expediente.setCasoDTO(caso);
				audienciaDTO.setExpediente(expediente);
				SalaAudienciaDTO sala = new SalaAudienciaDTO();
				sala.setNombreSala(audiencia.getNombreSala());
				sala.setUbicacionSala(audiencia.getUbicacionSala());
				sala.setDomicilioSala(audiencia.getDomicilioSala());
				audienciaDTO.setSala(sala);
				audienciaDTO.setCaracter(audiencia.getCaracter());
				audienciaDTO.setFechaEvento(WsTransformer
						.tranformXmlDate(audiencia.getFechaAudiencia()));
				retorno.add(audienciaDTO);
			}
            return retorno;
            
        } catch (MalformedURLException e) {
            logger.error(e.getMessage());
            throw new NSJPNegocioException(CodigoError.ERROR_COMUNICACION, e);
        } catch (mx.gob.segob.nsjp.ws.cliente.consultaraudiencia.NSJPNegocioException_Exception e) {
            logger.error(e.getMessage());
            throw new NSJPNegocioException(CodigoError.ERROR_COMUNICACION, e);
        }

    }

    @Override
    public Long generarPermisoAudienciaUsuarioExterno(
    		Long confInstId, String nombreFuncionario, String apPatFuncionario, String apMatFuncionario,
			String nombreAreaFuncionario, String nombrePuestoFuncionario, String emailFuncionario,
			Long claveFuncionario, Long audiencia)
			throws NSJPNegocioException {
        try {
        	if(audiencia==null || audiencia.equals(0L) || claveFuncionario==null || claveFuncionario.equals(0L)){
        		throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
        	}
        	
        	Long idEvento = EstatusPermisosAudiencia.FALLO.getValorId();
        	
            URL url = new URL(
                    confInsDao.read(Instituciones.PJ.getValorId()).getUrlInst()
                            + "/AdministrarPermisosAudienciaExporterImplService?wsdl");

            final QName SERVICE_NAME = new QName(
                    "http://impl.ws.service.nsjp.segob.gob.mx/",
                    "AdministrarPermisosAudienciaExporterImplService");

            AdministrarPermisosAudienciaExporterImplService ss = new AdministrarPermisosAudienciaExporterImplService(
                    url, SERVICE_NAME);

            AdministrarPermisosAudienciaExporter port = ss
                    .getAdministrarPermisosAudienciaExporterImplPort();

            FuncionarioExternoWSDTO toSend = new FuncionarioExternoWSDTO();

            toSend.setConfInstId(confInstId);
            toSend.setNombre(nombreFuncionario);
            toSend.setApellidoPaterno(apPatFuncionario);
            toSend.setApellidoMaterno(apMatFuncionario);
            toSend.setArea(nombreAreaFuncionario);
            toSend.setPuesto(nombrePuestoFuncionario);
            toSend.setEmail(emailFuncionario);
            toSend.setCveFuncionarioInstExt(claveFuncionario);
            toSend.setAudienciaId(audiencia);
            
            logger.debug("Consultado audiencias a trav�s de :: " + url);
            idEvento = port
            		.generarPermisoUsuarioExterno(toSend);
           
            return idEvento;
        } catch (MalformedURLException e) {
            logger.error(e.getMessage());
            throw new NSJPNegocioException(CodigoError.ERROR_COMUNICACION, e);            
        } catch (mx.gob.segob.nsjp.ws.cliente.administrarPermisosAudiencia.NSJPNegocioException_Exception e) {
            logger.error(e.getMessage());
            throw new NSJPNegocioException(CodigoError.ERROR_COMUNICACION, e);
		}
    }


    @Override
    public AudienciaJAVSTransporteDTO verificarPermisoAudienciaUsuarioExterno(
    		Long confInstId, String nombreFuncionario, String apPatFuncionario, String apMatFuncionario,
			String nombreAreaFuncionario, String nombrePuestoFuncionario, String emailFuncionario,
			Long claveFuncionario, Long audiencia)
			throws NSJPNegocioException {
        try {
        	AudienciaJAVSTransporteWSDTO audienciaJAVSTransporteWSDTO = new AudienciaJAVSTransporteWSDTO();
        	
            URL url = new URL(
                    confInsDao.read(Instituciones.PJ.getValorId()).getUrlInst()
                            + "/AdministrarPermisosAudienciaExporterImplService?wsdl");

            final QName SERVICE_NAME = new QName(
                    "http://impl.ws.service.nsjp.segob.gob.mx/",
                    "AdministrarPermisosAudienciaExporterImplService");

            AdministrarPermisosAudienciaExporterImplService ss = new AdministrarPermisosAudienciaExporterImplService(
                    url, SERVICE_NAME);

            AdministrarPermisosAudienciaExporter port = ss
                    .getAdministrarPermisosAudienciaExporterImplPort();

            FuncionarioExternoWSDTO toSend = new FuncionarioExternoWSDTO();

            toSend.setConfInstId(confInstId);
            toSend.setNombre(nombreFuncionario);
            toSend.setApellidoPaterno(apPatFuncionario);
            toSend.setApellidoMaterno(apMatFuncionario);
            toSend.setArea(nombreAreaFuncionario);
            toSend.setPuesto(nombrePuestoFuncionario);
            toSend.setEmail(emailFuncionario);
            toSend.setCveFuncionarioInstExt(claveFuncionario);
            toSend.setAudienciaId(audiencia);
            
            logger.debug("Consultado audiencias a trav�s de :: " + url);
            audienciaJAVSTransporteWSDTO = port
            		.consultarPermisoAudienciaUsuarioExterno(toSend);
           
            AudienciaJAVSTransporteDTO audienciaJAVSTransporteDTO = new AudienciaJAVSTransporteDTO();
            
            audienciaJAVSTransporteDTO.setBytesRegistroMaestroJVL(audienciaJAVSTransporteWSDTO.getBytesRegistroMaestroJVL());
            audienciaJAVSTransporteDTO.setDirectoriosServidorJAVS(audienciaJAVSTransporteWSDTO.getDirectoriosServidorJAVS());
            audienciaJAVSTransporteDTO.setDirIPJAVS(audienciaJAVSTransporteWSDTO.getDirIPJAVS());
            audienciaJAVSTransporteDTO.setPasswordJAVS(audienciaJAVSTransporteWSDTO.getPasswordJAVS());
            audienciaJAVSTransporteDTO.setRegistroMaestroJVL(audienciaJAVSTransporteWSDTO.getRegistroMaestroJVL());
            audienciaJAVSTransporteDTO.setResultadoPermisoAudiencia(audienciaJAVSTransporteWSDTO.getResultadoPermisoAudiencia());
            audienciaJAVSTransporteDTO.setUsuarioJAVS(audienciaJAVSTransporteWSDTO.getUsuarioJAVS());            
            
            return audienciaJAVSTransporteDTO;
        } catch (MalformedURLException e) {
            logger.error(e.getMessage());
            throw new NSJPNegocioException(CodigoError.ERROR_COMUNICACION, e);            
        } catch (mx.gob.segob.nsjp.ws.cliente.administrarPermisosAudiencia.NSJPNegocioException_Exception e) {
            logger.error(e.getMessage());
            throw new NSJPNegocioException(CodigoError.ERROR_COMUNICACION, e);
		}
    }

    @Override
    public AudienciaDTO consultarAudienciasById(AudienciaDTO input)
            throws NSJPNegocioException {

        try {
            URL url = new URL(
                    confInsDao.read(Instituciones.PJ.getValorId()).getUrlInst()
                            + "/ConsultarAudienciasByFechasYEstatusExporterImplService?wsdl");

            final QName SERVICE_NAME = new QName(
                    "http://impl.ws.service.nsjp.segob.gob.mx/",
                    "ConsultarAudienciasByFechasYEstatusExporterImplService");

            ConsultarAudienciasByFechasYEstatusExporterImplService ss = new ConsultarAudienciasByFechasYEstatusExporterImplService(
                    url, SERVICE_NAME);

            ConsultarAudienciasByFechasYEstatusExporter port = ss
                    .getConsultarAudienciasByFechasYEstatusExporterImplPort();

            AudienciaDefensoriaWSDTO toSend = new AudienciaDefensoriaWSDTO();

            toSend.setAudienciaId(input.getId());

            AudienciaDefensoriaWSDTO respuesta = port
                    .consultarAudienciaById(toSend);

            AudienciaDTO audienciaDTO = new AudienciaDTO();
            audienciaDTO.setId(respuesta.getAudienciaId());
            Valor tipo = valorDAO.read(respuesta.getTipoAudienciaId());
            audienciaDTO.setTipoAudiencia(new ValorDTO(tipo.getValorId(), tipo
                    .getValor()));
            ExpedienteDTO expediente = new ExpedienteDTO();
            CasoDTO caso = new CasoDTO();
            caso.setNumeroGeneralCaso(respuesta.getNumeroCaso());
            expediente.setCasoDTO(caso);
            audienciaDTO.setExpediente(expediente);
            SalaAudienciaDTO sala = new SalaAudienciaDTO();
            sala.setNombreSala(respuesta.getNombreSala());
            sala.setUbicacionSala(respuesta.getUbicacionSala());
            sala.setDomicilioSala(respuesta.getDomicilioSala());
            audienciaDTO.setSala(sala);
            audienciaDTO.setCaracter(respuesta.getCaracter());
            audienciaDTO.setFechaEvento(WsTransformer.tranformXmlDate(respuesta
                    .getFechaAudiencia()));
            List<InvolucradoViewDTO> involucrados = new LinkedList<InvolucradoViewDTO>();
            InvolucradoViewDTO inView = null;
            for (InvolucradoAudienciaDefWSDTO involucrado : respuesta
                    .getInvolucrados()) {
                inView = new InvolucradoViewDTO();
                inView.setNombre(involucrado.getNombre());
                Valor calidad = valorDAO.read(involucrado.getCalidad());
                inView.setCalidad(calidad.getValor());
                if(involucrado.getDelito() != null){
                	inView.setDelitos(involucrado.getDelito());
                }else{
                	inView.setDelitos(" - ");
                }
                if (involucrado.isDetenido()) {
                    inView.setDetenido("SI");
                } else {
                    inView.setDetenido("NO");
                }
                involucrados.add(inView);
            }
            audienciaDTO.setInvolucradosView(involucrados);

            return audienciaDTO;
        } catch (MalformedURLException e) {
            logger.error(e.getMessage());
            throw new NSJPNegocioException(CodigoError.ERROR_COMUNICACION, e);
        } catch (mx.gob.segob.nsjp.ws.cliente.consultaraudiencia.NSJPNegocioException_Exception e) {
            logger.error(e.getMessage());
            throw new NSJPNegocioException(CodigoError.ERROR_COMUNICACION, e);
        }

    }

    @Override
    public void enviarActualizacionSeguimiento(DocumentoDTO docBase,
            ArchivoDigitalDTO adjunto, ValorDTO estatus, Long tipoOperacion)
            throws NSJPNegocioException {
        URL url;
        try {
            url = new URL(
                    confInsDao.read(Instituciones.PJ.getValorId()).getUrlInst()
                            + "/ActualizarSeguimientoMandamientoMedidaExporterImplService?wsdl");
            final QName SERVICE_NAME = new QName(
                    "http://impl.ws.service.nsjp.segob.gob.mx/",
                    "ActualizarSeguimientoMandamientoMedidaExporterImplService");

            ActualizarSeguimientoMandamientoMedidaExporterImplService ss = new ActualizarSeguimientoMandamientoMedidaExporterImplService(
                    url, SERVICE_NAME);
            ActualizarSeguimientoMandamientoMedidaExporter port = ss
                    .getActualizarSeguimientoMandamientoMedidaExporterImplPort();
            SeguimientoMandamientoMedidaWSDTO toSend = new SeguimientoMandamientoMedidaWSDTO();
            toSend.setFolioDocumento(docBase.getFolioDocumento());
            toSend.setTipoOperacion(tipoOperacion);
            if (adjunto != null) {
                ArchivoDigitalWSDTO archDig = new ArchivoDigitalWSDTO();
                archDig.setContenido(adjunto.getContenido());
                archDig.setNombreArchivo(adjunto.getNombreArchivo());
                archDig.setTipoArchivo(adjunto.getNombreArchivo());
                toSend.setArchivoDigital(archDig);
            }
            if (estatus != null && estatus.getIdCampo() != null) {
                toSend.setIdEstatus(estatus.getIdCampo());
            }
            logger.debug("A punto de enviar la actualizacion");
            port.actualizarSeguimiento(toSend);
            logger.debug("Actializacion [OK]");
        } catch (MalformedURLException e) {
            logger.error(e.getMessage());
            throw new NSJPNegocioException(CodigoError.ERROR_COMUNICACION, e);
        } catch (mx.gob.segob.nsjp.ws.cliente.seguimiento.NSJPNegocioException_Exception e) {
            logger.error(e.getMessage());
            throw new NSJPNegocioException(CodigoError.ERROR_COMUNICACION, e);
        }

    }
    /**
     * 
     */
	@Override
	public void envarActualizacionSeguimientoSolicitudTranscripcion(
			SolicitudDTO solBase, ArchivoDigitalDTO adjunto, ValorDTO estatus)
			throws NSJPNegocioException {
		URL url;
        try {
            //detectar la instituci�n de donde vino la solicitud para enviarsela
        	url = new URL(
                    confInsDao.read(solBase.getInstitucion().getConfInstitucionId()).getUrlInst()
                            + "/ActualizarSeguimientoMandamientoMedidaExporterImplService?wsdl");
            final QName SERVICE_NAME = new QName(
                    "http://impl.ws.service.nsjp.segob.gob.mx/",
                    "ActualizarSeguimientoMandamientoMedidaExporterImplService");

            ActualizarSeguimientoMandamientoMedidaExporterImplService ss = new ActualizarSeguimientoMandamientoMedidaExporterImplService(
                    url, SERVICE_NAME);
            ActualizarSeguimientoMandamientoMedidaExporter port = ss
                    .getActualizarSeguimientoMandamientoMedidaExporterImplPort();
            SeguimientoMandamientoMedidaWSDTO toSend = new SeguimientoMandamientoMedidaWSDTO();
            toSend.setFolioDocumento(solBase.getFolioSolicitud());
            toSend.setTipoOperacion(mx.gob.segob.nsjp.dto.documento.SeguimientoMandamientoMedidaWSDTO.TIPO_OPERACION_SOLICITUD_TRANSCRIPCION);
            if (adjunto != null) {
                ArchivoDigitalWSDTO archDig = new ArchivoDigitalWSDTO();
                archDig.setContenido(adjunto.getContenido());
                archDig.setNombreArchivo(adjunto.getNombreArchivo());
                archDig.setTipoArchivo(adjunto.getTipoArchivo());
                toSend.setArchivoDigital(archDig);
            }
            if (estatus != null && estatus.getIdCampo() != null) {
                toSend.setIdEstatus(estatus.getIdCampo());
            }
            logger.debug("A punto de enviar la actualizacion");
            port.actualizarSeguimiento(toSend);
            logger.debug("Actializacion [OK]");
        } catch (MalformedURLException e) {
            logger.error(e.getMessage());
            throw new NSJPNegocioException(CodigoError.ERROR_COMUNICACION, e);
        } catch (mx.gob.segob.nsjp.ws.cliente.seguimiento.NSJPNegocioException_Exception e) {
            logger.error(e.getMessage());
            throw new NSJPNegocioException(CodigoError.ERROR_COMUNICACION, e);
        }

		
	}
	
	   @Override
	    public SolicitudTranscripcionAudienciaDTO enviarSolicitudTranscripcion(
	    		SolicitudTranscripcionAudienciaDTO input) throws NSJPNegocioException {

		   input.setFormaDTO(new FormaDTO(1L));
		   input.setNombreDocumento("AudienciaDeTrancripcion");
		   input.setEstatus(new ValorDTO(EstatusSolicitud.ABIERTA.getValorId()));		
		   SolicitudTranscripcionAudienciaDTO registroBD = registrarSolicitudService.registrarSolicitudTranscripcionAudiencia(input);
		   
		   
	        URL ep;
	        try {
	            if (logger.isDebugEnabled()) {
	                logger.debug("input :: " + input);
	            }
	            ep = new URL(
	                    confInsDao.read(Instituciones.PJ.getValorId())
	                            .getUrlInst()
	                            + "/RegistrarSolicitudTranscripcionAreaExternaExporterImplService?wsdl");
	            
	    	


	            final QName SERVICE_NAME = new QName(
	                    "http://impl.ws.service.nsjp.segob.gob.mx/",
	                    "RegistrarSolicitudTranscripcionAreaExternaExporterImplService");
	            RegistrarSolicitudTranscripcionAreaExternaExporterImplService ss = new RegistrarSolicitudTranscripcionAreaExternaExporterImplService(
	                    ep, SERVICE_NAME);
	            RegistrarSolicitudTranscripcionAreaExternaExporter port = ss.getRegistrarSolicitudTranscripcionAreaExternaExporterImplPort();
	            logger.debug("Port obtenido....");
	            
	            SolicitudTranscripcionWSDTO toSend = new SolicitudTranscripcionWSDTO();
	            
	            //Tipo Solicitud
	            toSend.setIdTipoSolicitud(input.getTipoSolicitudDTO().getIdCampo());
	            //Fecha Solicitud
	            toSend.setFechaSolicitud(WsTransformer.transformFecha(input.getFechaCreacion()));
	            //Nombre solicitante
	            toSend.setNombreSolicitante(input.getNombreSolicitante());
	            //Id de la audiencia
	            mx.gob.segob.nsjp.ws.cliente.solicitudtranscripcion.AudienciaWSDTO loAudienciaWSDTO = 
	            	new mx.gob.segob.nsjp.ws.cliente.solicitudtranscripcion.AudienciaWSDTO(); 
	            loAudienciaWSDTO.setFolioAudiencia(input.getAudiencia().getFolioAudiencia());
	            toSend.setAudiencia(loAudienciaWSDTO);
	            //Folio de la solicitud
	            toSend.setFolioSolicitud(registroBD.getFolioSolicitud());
	            //Confinstitucion
	            toSend.setConfInstitucionId(registroBD.getInstitucion().getConfInstitucionId());

	            
	            logger.debug("Invocando registrarSolicitudTrancripcion...");
	            logger.debug("toSend.getAudiencia().getAudienciaId()..."+ loAudienciaWSDTO.getAudienciaId());
	            
	            SolicitudTranscripcionWSDTO resp = null;
				try {
					resp = port.registrarSolicitudTranscripcion(toSend);
				} catch (mx.gob.segob.nsjp.ws.cliente.solicitudtranscripcion.NSJPNegocioException_Exception e) {
		            logger.debug("ERROR port.registrarSolicitudTranscripcion...");

					e.printStackTrace();
				}
	                    

	            SolicitudTranscripcionAudienciaDTO response = new SolicitudTranscripcionAudienciaDTO();

	            logger.debug("Se registro en defensoria la solicitud con ID :: "
	                    + resp.getSolicitudId());
	            response.setDocumentoId(resp.getSolicitudId());

	            return response;
	        } catch (MalformedURLException e) {
	            logger.error(e.getMessage());
	            throw new NSJPNegocioException(CodigoError.ERROR_COMUNICACION, e);
	        }
	    }

	   @Override
	   public Long enviarDocumentoIncumplimientoMedidaPJ(DocumentoDTO documentoDTO, String numeroCausa,
				String numeroCarpetaEjecucion) throws NSJPNegocioException{

		   logger.debug("PJCLienteService - enviarDocumentoIncumplimientoMedidaPJ ");
		   logger.debug("PJCLienteService - documento = " + documentoDTO);
		   logger.debug("PJCLienteService - numeroCausa = " + numeroCausa);
		   logger.debug("PJCLienteService - numeroCarpetaEjecucion = " + numeroCarpetaEjecucion);
		   if(documentoDTO!= null )
			   logger.debug("PJCLienteService - documento - ArchivoDigital = " + documentoDTO.getArchivoDigital());
		   
		   URL ep;
	    	 
		try {
			// Configuracion para acceder al web service
			ep = new URL(
					confInsDao.read(Instituciones.PJ.getValorId()).getUrlInst()
							+ "/EnviarDocumentoIncumplimientoMedidaExporterImplService?wsdl");

			final QName SERVICE_NAME = new QName(
					"http://impl.ws.service.nsjp.segob.gob.mx/",
					"EnviarDocumentoIncumplimientoMedidaExporterImplService");	   
			
			EnviarDocumentoIncumplimientoMedidaExporterImplService ss =
				new EnviarDocumentoIncumplimientoMedidaExporterImplService(ep, SERVICE_NAME);
			
			EnviarDocumentoIncumplimientoMedidaExporter clienteEnviarDocumento =
				ss.getEnviarDocumentoIncumplimientoMedidaExporterImplPort();
			// Hasta aqui finaliza la configuracion para acceder al web service

			// Aqui inicia el proceso de transformar los dto en wsDto
			logger.info(" DocumentoDTO:" + documentoDTO);
			if(documentoDTO!= null )
				   logger.debug("PJCLienteService - documento - ArchivoDigital Before Transformer = " + documentoDTO.getArchivoDigital());
			
			DocumentoWSDTO documentoIncumplimientoWSDTO = DocumentoWSDTOTransformer.transformarWSDTO(documentoDTO);
			logger.info(" DocumentoWSDTO:" + documentoIncumplimientoWSDTO);
			if(documentoIncumplimientoWSDTO!= null )
				   logger.debug("PJCLienteService - documento - ArchivoDigital After Transformer = " + documentoIncumplimientoWSDTO.getArchivoDigital());
			
			
			Long idDocumento = clienteEnviarDocumento.enviarDocumentoDeIncumplimientoDeMedida(documentoIncumplimientoWSDTO, numeroCausa, numeroCarpetaEjecucion);
			logger.debug("----------------------------------------PJCLIENTE: DocumentoEnviado: idDocumento = " + idDocumento);
			
			return idDocumento;

		} catch (mx.gob.segob.nsjp.ws.cliente.enviardocumentoincumplimientomedida.NSJPNegocioException_Exception e) {
			logger.error(e.getMessage());
			throw new NSJPNegocioException(CodigoError.ERROR_COMUNICACION, e);
		} catch (MalformedURLException e) {
			logger.error(e.getMessage());
			throw new NSJPNegocioException(CodigoError.ERROR_COMUNICACION, e);
		}
	}
	   
	   
	 @Override
	 public List<Object[]>  consultarIndicadorDeAudienciasPorFechas(String fechaInicial, String fechaFinal) throws NSJPNegocioException {
	    	
	        try {
	        	if(fechaInicial == null || fechaFinal == null){
	        		throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
	        	}
	            URL url = new URL(
	                    confInsDao.read(Instituciones.PJ.getValorId()).getUrlInst()   //"http://localhost:49801/nsjp-web"                     
	                            + "/ConsultarAudienciasByFechasYEstatusExporterImplService?wsdl");
	            
	            final QName SERVICE_NAME = new QName(
	                    "http://impl.ws.service.nsjp.segob.gob.mx/",
	                    "ConsultarAudienciasByFechasYEstatusExporterImplService");

	            ConsultarAudienciasByFechasYEstatusExporterImplService ss = new ConsultarAudienciasByFechasYEstatusExporterImplService(
	                    url, SERVICE_NAME);

	            ConsultarAudienciasByFechasYEstatusExporter port = ss
                .getConsultarAudienciasByFechasYEstatusExporterImplPort();
	            
	            List<String> respuesta = port.consultarIndicadorDeAudienciasPorFechas(fechaInicial, fechaFinal);
	            
	            List<Object[]> respuestaObject = new ArrayList<Object[]>();
	            if(respuesta != null && !respuesta.isEmpty()){
	            	for (String resp : respuesta) {
	    			    Object[] arreglo = resp.split("\\|");
						respuestaObject.add(arreglo);
					}
	            }
	     	            
	            return respuestaObject;
	            
	        } catch (MalformedURLException e) {
	            logger.error(e.getMessage());
	            throw new NSJPNegocioException(CodigoError.ERROR_COMUNICACION, e);
	        } catch (mx.gob.segob.nsjp.ws.cliente.consultaraudiencia.NSJPNegocioException_Exception e) {
	            logger.error(e.getMessage());
	            throw new NSJPNegocioException(CodigoError.ERROR_COMUNICACION, e);
	        }

	    }
}
