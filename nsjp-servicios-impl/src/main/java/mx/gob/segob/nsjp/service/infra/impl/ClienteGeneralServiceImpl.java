/**
 * Nombre del Programa : ClienteGeneralServiceImpl.java
 * Autor                            : vaguirre
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 21 Aug 2011
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
package mx.gob.segob.nsjp.service.infra.impl;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

import mx.gob.segob.nsjp.comun.enums.actividad.Actividades;
import mx.gob.segob.nsjp.comun.enums.calidad.Calidades;
import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.enums.institucion.Instituciones;
import mx.gob.segob.nsjp.comun.enums.seguridad.Roles;
import mx.gob.segob.nsjp.comun.excepcion.NSJPCommunicationException;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.institucion.ConfInstitucionDAO;
import mx.gob.segob.nsjp.dao.persona.DelitoPersonaDAO;
import mx.gob.segob.nsjp.dto.archivo.ArchivoDigitalDTO;
import mx.gob.segob.nsjp.dto.audiencia.AudienciaDTO;
import mx.gob.segob.nsjp.dto.catalogo.CatDiscriminanteDTO;
import mx.gob.segob.nsjp.dto.catalogo.CatDistritoDTO;
import mx.gob.segob.nsjp.dto.documento.DocumentoDTO;
import mx.gob.segob.nsjp.dto.documento.MandamientoDTO;
import mx.gob.segob.nsjp.dto.documento.MedidaCautelarDTO;
import mx.gob.segob.nsjp.dto.documento.NotificacionDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.funcionario.CriterioConsultaFuncionarioExternoDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.dto.funcionarioexterno.FuncionarioExternoDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.dto.persona.NombreDemograficoDTO;
import mx.gob.segob.nsjp.dto.sentencia.SentenciaDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudDefensorDTO;
import mx.gob.segob.nsjp.model.ArchivoDigital;
import mx.gob.segob.nsjp.model.ConfInstitucion;
import mx.gob.segob.nsjp.model.DelitoPersona;
import mx.gob.segob.nsjp.model.Mandamiento;
import mx.gob.segob.nsjp.model.MedidaCautelar;
import mx.gob.segob.nsjp.service.archivo.impl.transform.ArchivoDigitalTransformer;
import mx.gob.segob.nsjp.service.infra.ClienteGeneralService;
import mx.gob.segob.nsjp.service.infra.impl.transform.WsTransformer;
import mx.gob.segob.nsjp.service.infra.impl.transform.consultarcatalogo.CatDiscriminanteWSDTOTransformer;
import mx.gob.segob.nsjp.service.infra.impl.transform.consultarcatalogo.CatDistritoWSDTOTransformer;
import mx.gob.segob.nsjp.service.infra.impl.transform.consultarcatalogo.CriterioConsultaFuncionarioExternoWSDTOTransformer;
import mx.gob.segob.nsjp.service.infra.impl.transform.consultarcatalogo.FuncionarioWSDTOTransformer;
import mx.gob.segob.nsjp.service.infra.impl.transform.enviarReplicaCaso.CasoWSDTOTransformer;
import mx.gob.segob.nsjp.service.infra.impl.transform.enviarReplicaCaso.InvolucradoWSDTOTransformer;
import mx.gob.segob.nsjp.service.infra.impl.transform.enviardocumento.DocumentoWSDTOTransformer;
import mx.gob.segob.nsjp.service.infra.impl.transform.notificacionaudiencia.AudienciaWSDTOTransformer;
import mx.gob.segob.nsjp.service.infra.impl.transform.recibiractualizacionmandamiento.MandamientoWSDTOTransformer;
import mx.gob.segob.nsjp.service.infra.impl.transform.recibiracusederecibodesolicituddedefensor.SolicitudDefensorWSDTOTransformer;
import mx.gob.segob.nsjp.service.infra.impl.transform.recibiracusederecibodesolicituddedefensor.SolicitudWSDTOTransformer;
import mx.gob.segob.nsjp.service.sentencia.impl.transform.SentenciaTransformer;
import mx.gob.segob.nsjp.service.solicitud.impl.transform.SolicitudTransformer;
import mx.gob.segob.nsjp.ws.cliente.actualizacionsentencia.ActualizacionSentenciaExporter;
import mx.gob.segob.nsjp.ws.cliente.actualizacionsentencia.ActualizacionSentenciaExporterImplService;
import mx.gob.segob.nsjp.ws.cliente.consultardistritos.CatDistritoWSDTO;
import mx.gob.segob.nsjp.ws.cliente.consultardistritos.ConsultarDistritosServiceWSExporter;
import mx.gob.segob.nsjp.ws.cliente.consultardistritos.ConsultarDistritosServiceWSExporterImplService;
import mx.gob.segob.nsjp.ws.cliente.consultarfuncionarioexterno.ConsultarFuncionarioExternoExporter;
import mx.gob.segob.nsjp.ws.cliente.consultarfuncionarioexterno.ConsultarFuncionarioExternoExporterImplService;
import mx.gob.segob.nsjp.ws.cliente.consultarfuncionariosxtribunal.ConsultarFuncionariosXTribunalExporter;
import mx.gob.segob.nsjp.ws.cliente.consultarfuncionariosxtribunal.ConsultarFuncionariosXTribunalExporterImplService;
import mx.gob.segob.nsjp.ws.cliente.consultartribunalespordistrito.CatDiscriminanteWSDTO;
import mx.gob.segob.nsjp.ws.cliente.consultartribunalespordistrito.ConsultarTribunalesPorDistritoServiceExporter;
import mx.gob.segob.nsjp.ws.cliente.consultartribunalespordistrito.ConsultarTribunalesPorDistritoServiceExporterImplService;
import mx.gob.segob.nsjp.ws.cliente.enviardocumentoainstitucion.DocumentoWSDTO;
import mx.gob.segob.nsjp.ws.cliente.enviardocumentoainstitucion.EnviarDocumentoAInstitucionExporter;
import mx.gob.segob.nsjp.ws.cliente.enviardocumentoainstitucion.EnviarDocumentoAInstitucionExporterImplService;
import mx.gob.segob.nsjp.ws.cliente.estatusmandamiento.ActualizarEstatusMandamientoInstitucionalExporter;
import mx.gob.segob.nsjp.ws.cliente.estatusmandamiento.ActualizarEstatusMandamientoInstitucionalExporterImplService;
import mx.gob.segob.nsjp.ws.cliente.estatusmandamiento.MandamientoWSDTO;
import mx.gob.segob.nsjp.ws.cliente.medidacautelar.ArchivoDigitalWSDTO;
import mx.gob.segob.nsjp.ws.cliente.medidacautelar.MedidaCautelarWSDTO;
import mx.gob.segob.nsjp.ws.cliente.medidacautelar.RegistrarMedidaCautelarServiceExporter;
import mx.gob.segob.nsjp.ws.cliente.medidacautelar.RegistrarMedidaCautelarServiceExporterImplService;
import mx.gob.segob.nsjp.ws.cliente.notificacionaudiencia.AudienciaWSDTO;
import mx.gob.segob.nsjp.ws.cliente.notificacionaudiencia.FuncionarioExternoWSDTO;
import mx.gob.segob.nsjp.ws.cliente.notificacionaudiencia.NotificacionWSDTO;
import mx.gob.segob.nsjp.ws.cliente.notificacionaudiencia.RecibirNotificacionAudienciaExporter;
import mx.gob.segob.nsjp.ws.cliente.notificacionaudiencia.RecibirNotificacionAudienciaExporterImplService;
import mx.gob.segob.nsjp.ws.cliente.notificacionaudiencia.SolicitudAudienciaBasicoWSDTO;
import mx.gob.segob.nsjp.ws.cliente.recibiractualizacionmandamiento.RecibirActualizacionMandamientoInterInstitucionalExporter;
import mx.gob.segob.nsjp.ws.cliente.recibiractualizacionmandamiento.RecibirActualizacionMandamientoInterInstitucionalExporterImplService;
import mx.gob.segob.nsjp.ws.cliente.recibiracusederecibodesolicituddedefensor.RecibirAcuseDeReciboDeSolicitudDeDefensorExporter;
import mx.gob.segob.nsjp.ws.cliente.recibiracusederecibodesolicituddedefensor.RecibirAcuseDeReciboDeSolicitudDeDefensorExporterImplService;
import mx.gob.segob.nsjp.ws.cliente.registarreplicacaso.CasoWSDTO;
import mx.gob.segob.nsjp.ws.cliente.registarreplicacaso.NSJPNegocioException_Exception;
import mx.gob.segob.nsjp.ws.cliente.registarreplicacaso.RegistrarReplicaCasoExporter;
import mx.gob.segob.nsjp.ws.cliente.registarreplicacaso.RegistrarReplicaCasoExporterImplService;
import mx.gob.segob.nsjp.ws.cliente.sentencia.SentenciaExporterImpl;
import mx.gob.segob.nsjp.ws.cliente.sentencia.SentenciaExporterImplService;
import mx.gob.segob.nsjp.ws.cliente.sentencia.SentenciaWSDTO;
import mx.gob.segob.nsjp.ws.cliente.solicitud.ActividadWSDTO;
import mx.gob.segob.nsjp.ws.cliente.solicitud.EnviarSolicitudServiceExporterImpl;
import mx.gob.segob.nsjp.ws.cliente.solicitud.EnviarSolicitudServiceExporterImplService;
import mx.gob.segob.nsjp.ws.cliente.solicitud.SolicitudWSDTO;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Describir el objetivo de la clase con punto al final.
 * 
 * @version 1.0
 * @author vaguirre
 * 
 */
@Service
@Transactional(propagation=Propagation.NOT_SUPPORTED)
public class ClienteGeneralServiceImpl implements ClienteGeneralService {
    /**
     * Logger.
     */
    private final static Logger logger = Logger
            .getLogger(ClienteGeneralServiceImpl.class);
    
    @Autowired
    private ConfInstitucionDAO institucionDao;
    
    @Autowired
    private DelitoPersonaDAO delitoPersonaDao;

    /*
     * (non-Javadoc)
     * 
     * @see
     * mx.gob.segob.nsjp.service.infra.ClienteGeneralService#enviarAcuseRecibo
     * (java.lang.Long, mx.gob.segob.nsjp.comun.enums.institucion.Instituciones)
     */
    
	@Override
	public void enviarAcuseRecibo(Long idSolicitud, Instituciones target)
			throws NSJPNegocioException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.gob.segob.nsjp.service.infra.ClienteGeneralService#enviarReplicaCaso
	 * (mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO,
	 * mx.gob.segob.nsjp.comun.enums.institucion.Instituciones)
	 */
	@Override
	public void enviarReplicaCaso(ExpedienteDTO expConCaso,
			ConfInstitucion target) throws NSJPNegocioException {
		URL ep;
		try {
			ep = new URL(target.getUrlInst()
					+ "/RegistrarReplicaCasoExporterImplService?wsdl");

			final QName SERVICE_NAME = new QName(
					"http://impl.ws.service.nsjp.segob.gob.mx/",
					"RegistrarReplicaCasoExporterImplService");
			RegistrarReplicaCasoExporterImplService ss = new RegistrarReplicaCasoExporterImplService(
					ep, SERVICE_NAME);
			
			RegistrarReplicaCasoExporter port = ss
					.getRegistrarReplicaCasoExporterImplPort();
			
			logger.debug("Port obtenido....");

			CasoWSDTO toSend = CasoWSDTOTransformer.transformar(expConCaso
					.getCasoDTO());

			//Distrito asociado
			if (expConCaso.getDiscriminante() != null
					&& expConCaso.getDiscriminante().getDistrito() != null
					&& expConCaso.getDiscriminante().getDistrito()
							.getClaveDistrito() != null
					&& !expConCaso.getDiscriminante().getDistrito()
							.getClaveDistrito().trim().isEmpty()) {
				mx.gob.segob.nsjp.ws.cliente.registarreplicacaso.CatDistritoWSDTO catDistritoWSDTO = new mx.gob.segob.nsjp.ws.cliente.registarreplicacaso.CatDistritoWSDTO();
				catDistritoWSDTO.setClaveDistrito(expConCaso.getDiscriminante()
						.getDistrito().getClaveDistrito());
				catDistritoWSDTO.setNombreDist(expConCaso.getDiscriminante()
						.getDistrito().getClaveDistrito());

				logger.debug("DISTRITO DE REPLICA DE CASO:" + catDistritoWSDTO.getClaveDistrito());
				toSend.setDistrito(catDistritoWSDTO);
			}
			
			List<InvolucradoDTO> listaInvolucradoDTO = recuperarInvolucrados(expConCaso);
			if(listaInvolucradoDTO != null && !listaInvolucradoDTO.isEmpty()){
				toSend.getInvolucradosDTO()
				.addAll(InvolucradoWSDTOTransformer
						.transformarListaInvolucradoDTOAInvolucradoWSDTO(listaInvolucradoDTO));				
			}

			logger.debug("Invocando registrarReplicaCaso...");

			port.registrarReplicaCaso(toSend);

		} catch (NSJPNegocioException_Exception e) {
			logger.error(e.getMessage());
			throw new NSJPNegocioException(CodigoError.ERROR_COMUNICACION, e);
		} catch (MalformedURLException e) {
			logger.error(e.getMessage());
			throw new NSJPNegocioException(CodigoError.ERROR_COMUNICACION, e);
		} catch (javax.xml.ws.soap.SOAPFaultException e) {
			logger.error(e.getMessage());
			throw new NSJPNegocioException(CodigoError.ERROR_COMUNICACION, e);
		}
	}

    /**
     * @param expConCaso
     * @return
     */
    private List<InvolucradoDTO> recuperarInvolucrados(ExpedienteDTO expConCaso) {
    	
		final List<InvolucradoDTO> invoFuente = new ArrayList<InvolucradoDTO>();

		final List<InvolucradoDTO> listaProbableRespPersona = expConCaso
				.getInvolucradoByCalidad(Calidades.PROBABLE_RESPONSABLE_PERSONA);
		final List<InvolucradoDTO> listaVictima = expConCaso
				.getInvolucradoByCalidad(Calidades.VICTIMA_PERSONA);
		final List<InvolucradoDTO> listaProbableRespOrganizacion = expConCaso
				.getInvolucradoByCalidad(Calidades.PROBABLE_RESPONSABLE_ORGANIZACION);

		if (listaProbableRespPersona != null
				&& !listaProbableRespPersona.isEmpty()) {
			invoFuente
					.addAll(expConCaso
							.getInvolucradoByCalidad(Calidades.PROBABLE_RESPONSABLE_PERSONA));
		}
		if (listaVictima != null && !listaVictima.isEmpty()) {
			invoFuente.addAll(expConCaso
					.getInvolucradoByCalidad(Calidades.VICTIMA_PERSONA));
		}
		if (listaProbableRespOrganizacion != null
				&& !listaProbableRespOrganizacion.isEmpty()) {
			invoFuente
					.addAll(expConCaso
							.getInvolucradoByCalidad(Calidades.PROBABLE_RESPONSABLE_ORGANIZACION));
		}
        
        List<InvolucradoDTO> tempDenunciante = expConCaso.getInvolucradoByCalidad(Calidades.DENUNCIANTE);
        if (tempDenunciante!=null) {
            for (InvolucradoDTO invoDen:tempDenunciante) {
                if (invoDen.getCondicion()!=null && invoDen.getCondicion().intValue()==1) {
                    invoFuente.add(invoDen);
                }
            }
        }

        tempDenunciante = expConCaso.getInvolucradoByCalidad(Calidades.DENUNCIANTE_ORGANIZACION);
        if (tempDenunciante!=null) {
            for (InvolucradoDTO invoDen:tempDenunciante) {
                if (invoDen.getCondicion()!=null && invoDen.getCondicion().intValue()==1) {
                    invoFuente.add(invoDen);
                }
            }
        }
        return invoFuente;
    }

    
	@Override
	public Boolean enviarNotificacionAudienciaFuncionarioExterno(
			AudienciaDTO audienciaDTO,
			FuncionarioExternoDTO funcionarioExternoDTO,
			NotificacionDTO notificacionDTO) throws NSJPNegocioException {

		URL ep;
		Boolean resp = false;

		logger.info("Inicia - enviarNotificacionAudienciaFuncionarioExtenor(...)");
		try {
			ConfInstitucion destino = this.institucionDao
					.read(funcionarioExternoDTO.getConfInstitucionDTO()
							.getConfInstitucionId());

			ep = new URL(destino.getUrlInst()
					+ "/RecibirNotificacionAudienciaExporterImplService?wsdl");

			final QName SERVICE_NAME = new QName(
					"http://impl.ws.service.nsjp.segob.gob.mx/",
					"RecibirNotificacionAudienciaExporterImplService");

			RecibirNotificacionAudienciaExporterImplService ss = new RecibirNotificacionAudienciaExporterImplService(
					ep, SERVICE_NAME);

			RecibirNotificacionAudienciaExporter port = ss
					.getRecibirNotificacionAudienciaExporterImplPort();

			// Objeto que ser&aacute; enviado
			SolicitudAudienciaBasicoWSDTO toSend = new SolicitudAudienciaBasicoWSDTO();

			// Datos del funcionario externo
			FuncionarioExternoWSDTO funcionarioExternoANotificar = new FuncionarioExternoWSDTO();

			funcionarioExternoANotificar
					.setCveFuncionarioInstExt(funcionarioExternoDTO
							.getCveFuncionarioInstExt());
			toSend.setFuncionarioExternoDestinatario(funcionarioExternoANotificar);

			// Datos de la audiencia
			AudienciaWSDTO audienciaWSDTO = AudienciaWSDTOTransformer
					.transformarDtoAWsDto(audienciaDTO);

			/*
			 * Datos de la Notificacion, SE UTILIZARA, CUANDO LAS NOTIFICACIONES
			 * SEAN VIA SISTEMA, POR EL MOMENTO SE ENTREGAN PERSONALMENTE
			 */
			NotificacionWSDTO notiws = new NotificacionWSDTO();

			notiws.setConsecutivoNotificacion(notificacionDTO
					.getConsecutivoNotificacion());
			notiws.setFolioNotificacion(notificacionDTO.getFolioNotificacion());
			audienciaWSDTO.setNotificaion(notiws);

			toSend.setConfInstitucionId(this.institucionDao
					.consultarInsitucionActual().getConfInstitucionId());

			toSend.setAudiencia(audienciaWSDTO);
			resp = port.recibirNotificacionAudiencia(toSend);
			logger.info("Fin - enviarNotificacionAudienciaFuncionarioExtenor(...)");

			return resp;
		} catch (javax.xml.ws.soap.SOAPFaultException e) {
			logger.error(e.getMessage());
			throw new NSJPNegocioException(CodigoError.ERROR_COMUNICACION, e);
		} catch (MalformedURLException e) {
			logger.error(e.getMessage());
			throw new NSJPNegocioException(CodigoError.ERROR_COMUNICACION, e);
		} catch (mx.gob.segob.nsjp.ws.cliente.notificacionaudiencia.NSJPNegocioException_Exception e) {
			logger.error(e.getMessage());
			throw new NSJPNegocioException(CodigoError.ERROR_COMUNICACION, e);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new NSJPNegocioException(CodigoError.ERROR_COMUNICACION);
		}
	}
    
    
    @Override
    public List<FuncionarioDTO> consultarFuncionariosXTribunal(
   		 Long catDiscriminanteId,Instituciones target) throws NSJPNegocioException {
        URL ep;
        List<mx.gob.segob.nsjp.ws.cliente.consultarfuncionariosxtribunal.FuncionarioWSDTO> resp = null;
        List<FuncionarioDTO> loFuncionarioDTO = new ArrayList<FuncionarioDTO>();
        logger.info("Inicia - consultarFuncionariosXTribunal(...)");
        try {
            ConfInstitucion destino = this.institucionDao.read(target.getValorId());
            ep = new URL(destino.getUrlInst()
                    + "/ConsultarFuncionariosXTribunalExporterImplService?wsdl");

            final QName SERVICE_NAME = new QName(
                    "http://impl.ws.service.nsjp.segob.gob.mx/",
                    "ConsultarFuncionariosXTribunalExporterImplService");
            
        ConsultarFuncionariosXTribunalExporterImplService ss = new ConsultarFuncionariosXTribunalExporterImplService(ep, SERVICE_NAME);
        ConsultarFuncionariosXTribunalExporter port = ss.getConsultarFuncionariosXTribunalExporterImplPort();

        resp = port.consultarFuncionariosXTribunal(catDiscriminanteId);
        
        for (mx.gob.segob.nsjp.ws.cliente.consultarfuncionariosxtribunal.FuncionarioWSDTO funcionarioWSDTO : resp) {
        	loFuncionarioDTO.add(FuncionarioWSDTOTransformer.transformarFuncionario(funcionarioWSDTO));
		}
        
        logger.info("Fin - consultarFuncionariosXTribunal(...)");
        } catch (MalformedURLException e) {
            logger.error(e.getMessage());
            throw new NSJPNegocioException(CodigoError.ERROR_COMUNICACION, e);
        } catch (mx.gob.segob.nsjp.ws.cliente.consultarfuncionariosxtribunal.NSJPNegocioException_Exception e) {
            logger.error(e.getMessage());
            throw new NSJPNegocioException(CodigoError.ERROR_COMUNICACION, e);
        }
        return loFuncionarioDTO;
    }


    @Override
    public List<CatDiscriminanteDTO> consultarTribunalesPorDistrito( Long distritoId, Instituciones target) throws NSJPNegocioException {
    	
    	logger.info("Inicia - consultarTribunalesPorDistrito(...)");

    	List<CatDiscriminanteDTO> discriminantesDTO = new ArrayList<CatDiscriminanteDTO>();
		URL ep;
		try {
			// Configuración de WS
			ConfInstitucion destino = this.institucionDao.read(target
					.getValorId());
			ep = new URL(
					destino.getUrlInst()
							+ "/ConsultarTribunalesPorDistritoServiceExporterImplService?wsdl");

			final QName SERVICE_NAME = new QName(
					"http://impl.ws.service.nsjp.segob.gob.mx/",
					"ConsultarTribunalesPorDistritoServiceExporterImplService");

			ConsultarTribunalesPorDistritoServiceExporterImplService ss = new ConsultarTribunalesPorDistritoServiceExporterImplService(
					ep, SERVICE_NAME);

			ConsultarTribunalesPorDistritoServiceExporter port = ss
					.getConsultarTribunalesPorDistritoServiceExporterImplPort();

			// Invocación del WS
			List<CatDiscriminanteWSDTO> discriminantesWSDTO = port
					.consultarTribunalesPorDistrito(distritoId);

			logger.info("Respuesta - consultarTribunalesPorDistrito:"
					+ discriminantesWSDTO);

			if (discriminantesWSDTO != null && !discriminantesWSDTO.isEmpty()) {
				logger.info("discriminantesWSDTO.size(): "
						+ discriminantesWSDTO.size());
				for (CatDiscriminanteWSDTO catDiscriminanteWSDTO : discriminantesWSDTO) {
					CatDiscriminanteDTO discriminanteDTO = CatDiscriminanteWSDTOTransformer
							.transformarCatDiscriminante(catDiscriminanteWSDTO);
					logger.info(" DiscriminanteDTO : " + discriminantesDTO);
					discriminantesDTO.add(discriminanteDTO);
				}
			}
			logger.info("Fin - consultarTribunalesPorDistrito(...)");
		} catch (MalformedURLException e) {
			logger.error(e.getMessage());
			throw new NSJPNegocioException(CodigoError.ERROR_COMUNICACION, e);
		} catch (mx.gob.segob.nsjp.ws.cliente.consultartribunalespordistrito.NSJPNegocioException_Exception e) {
			logger.error(e.getMessage());
			throw new NSJPNegocioException(CodigoError.ERROR_COMUNICACION, e);
		}

		return discriminantesDTO;
    }
    

    @Override
    public Long enviarDocumentoAInstitucion( List<DocumentoDTO> lstDocumentoDTO, String numeroDeCaso, Instituciones target) throws NSJPNegocioException {
    	if(lstDocumentoDTO != null ) {
		   for (DocumentoDTO documentoDTO : lstDocumentoDTO) {
			   logger.debug("ClienteGeneralServiceImpl - enviarDocumentoAInstitucion ");
			   logger.debug("ClienteGeneralServiceImpl - documento = " + documentoDTO);
			   logger.debug("ClienteGeneralServiceImpl - numeroExpediente = " + numeroDeCaso);
			   logger.debug("ClienteGeneralServiceImpl - documento - ArchivoDigital = " + documentoDTO.getArchivoDigital());		   
		   }
	   }
	   
	   URL ep;
	    	 
		try {
			
			// Configuracion para acceder al web service
			ConfInstitucion destino = this.institucionDao.read(target
					.getValorId());
			ep = new URL(
					destino.getUrlInst()
							+ "/EnviarDocumentoAInstitucionExporterImplService?wsdl");
			
			final QName SERVICE_NAME = new QName(
					"http://impl.ws.service.nsjp.segob.gob.mx/",
					"EnviarDocumentoAInstitucionExporterImplService");	   
			
			EnviarDocumentoAInstitucionExporterImplService ss =
				new EnviarDocumentoAInstitucionExporterImplService(ep, SERVICE_NAME);
			
			EnviarDocumentoAInstitucionExporter port =
				ss.getEnviarDocumentoAInstitucionExporterImplPort();
			// Hasta aqui finaliza la configuracion para acceder al web service

			// Aqui inicia el proceso de transformar los dto en wsDto
			List<DocumentoWSDTO> lstDocumentoWSDTO = new ArrayList<DocumentoWSDTO>();
			
			for (DocumentoDTO documentoDTO : lstDocumentoDTO) {
				DocumentoWSDTO documentoIncumplimientoWSDTO = DocumentoWSDTOTransformer.transformarWSDTO(documentoDTO);
				lstDocumentoWSDTO.add(documentoIncumplimientoWSDTO);

				logger.info(" DocumentoDTO:" + documentoDTO);
				logger.debug("PJCLienteService - documento - ArchivoDigital Before Transformer = " + documentoDTO.getArchivoDigital());
				
				logger.info(" DocumentoWSDTO:" + documentoIncumplimientoWSDTO);
				logger.debug("ClienteGeneralServiceImpl - documento - ArchivoDigital After Transformer = " + documentoIncumplimientoWSDTO.getArchivoDigital());
				
			}

			Long idDocumento = port.enviarDocumentoAInstitucion(lstDocumentoWSDTO, numeroDeCaso);
			logger.debug("----------------------------------------ClienteGeneralServiceImpl: DocumentoEnviado: idDocumento = " + idDocumento);
			
			return idDocumento;

		} catch (mx.gob.segob.nsjp.ws.cliente.enviardocumentoainstitucion.NSJPNegocioException_Exception e) {
			logger.error(e.getMessage());
			throw new NSJPNegocioException(CodigoError.ERROR_COMUNICACION, e);
		} catch (MalformedURLException e) {
			logger.error(e.getMessage());
			throw new NSJPNegocioException(CodigoError.ERROR_COMUNICACION, e);
		} catch (javax.xml.ws.soap.SOAPFaultException e) {
			logger.error(e.getMessage());
			throw new NSJPNegocioException(CodigoError.ERROR_COMUNICACION, e);
		}
		
	}
	
    @Override
    public List<CatDiscriminanteDTO> consultarAgenciasPorDistrito( Long distritoId, Instituciones target) 
            throws NSJPNegocioException, NSJPCommunicationException {
    
    	logger.info("Inicia - consultarAgenciasPorDistrito(...)");

    	List<CatDiscriminanteDTO> discriminantesDTO = new ArrayList<CatDiscriminanteDTO>();
		URL ep;
		try {
			// Configuración de WS
			ConfInstitucion destino = this.institucionDao.read(target.getValorId());
                        
			ep = new URL(
					destino.getUrlInst()
							+ "/ConsultarTribunalesPorDistritoServiceExporterImplService?wsdl");

			final QName SERVICE_NAME = new QName(
					"http://impl.ws.service.nsjp.segob.gob.mx/",
					"ConsultarTribunalesPorDistritoServiceExporterImplService");

			ConsultarTribunalesPorDistritoServiceExporterImplService ss = new ConsultarTribunalesPorDistritoServiceExporterImplService(
					ep, SERVICE_NAME);

			ConsultarTribunalesPorDistritoServiceExporter port = ss
					.getConsultarTribunalesPorDistritoServiceExporterImplPort();

			// Invocación del WS
			List<CatDiscriminanteWSDTO> discriminantesWSDTO = port
					.consultarAgenciasPorDistrito(distritoId);

			logger.info("Respuesta - consultarAgenciasPorDistrito:"
					+ discriminantesWSDTO);

			if (discriminantesWSDTO != null && !discriminantesWSDTO.isEmpty()) {
				logger.info("discriminantesWSDTO.size(): "
						+ discriminantesWSDTO.size());
				for (CatDiscriminanteWSDTO catDiscriminanteWSDTO : discriminantesWSDTO) {
					CatDiscriminanteDTO discriminanteDTO = CatDiscriminanteWSDTOTransformer
							.transformarCatDiscriminante(catDiscriminanteWSDTO);
					logger.info(" DiscriminanteDTO : " + discriminantesDTO);
					discriminantesDTO.add(discriminanteDTO);
}
			}
			logger.info("Fin - consultarAgenciasPorDistrito(...)");
                }catch (MalformedURLException e) {
                    logger.error(e.getMessage());
                    throw new NSJPCommunicationException(CodigoError.ERROR_COMUNICACION, e);
		} catch (mx.gob.segob.nsjp.ws.cliente.consultartribunalespordistrito.NSJPNegocioException_Exception e) {
                    logger.error(e.getMessage());
                    throw new NSJPCommunicationException(CodigoError.ERROR_COMUNICACION, e);
		}catch (IOException   ioe){
                    throw new  NSJPCommunicationException(CodigoError.ERROR_COMUNICACION, ioe);
                }

		return discriminantesDTO;
    }

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.infra.ClienteGeneralService#consultarFuncionariosXCriterio(mx.gob.segob.nsjp.dto.funcionario.CriterioConsultaFuncionarioExternoDTO, mx.gob.segob.nsjp.comun.enums.institucion.Instituciones)
	 */
	@Override
	public List<FuncionarioDTO> consultarFuncionariosXCriterio(
			CriterioConsultaFuncionarioExternoDTO criterioConsultaFuncionarioExternoDTO,
			Instituciones target) throws NSJPNegocioException {
		URL ep;
        List<mx.gob.segob.nsjp.ws.cliente.consultarfuncionarioexterno.FuncionarioWSDTO> resp = null;
        List<FuncionarioDTO> loFuncionarioDTO = new ArrayList<FuncionarioDTO>();
        logger.info("Inicia - consultarFuncionariosXCriterio(...)");
        try {
            ConfInstitucion destino = this.institucionDao.read(target.getValorId());
            ep = new URL(destino.getUrlInst()
                    + "/ConsultarFuncionarioExternoExporterImplService?wsdl");

            final QName SERVICE_NAME = new QName(
                    "http://impl.ws.service.nsjp.segob.gob.mx/",
                    "ConsultarFuncionarioExternoExporterImplService");
            
        ConsultarFuncionarioExternoExporterImplService ss = new ConsultarFuncionarioExternoExporterImplService(ep, SERVICE_NAME);
        ConsultarFuncionarioExternoExporter port = ss.getConsultarFuncionarioExternoExporterImplPort();

        resp = port.consultarFuncionariosXCriterio(CriterioConsultaFuncionarioExternoWSDTOTransformer.transformarWSDTOCliente(
        		criterioConsultaFuncionarioExternoDTO));
        
        for (mx.gob.segob.nsjp.ws.cliente.consultarfuncionarioexterno.FuncionarioWSDTO funcionarioWSDTO : resp) {
        	loFuncionarioDTO.add(FuncionarioWSDTOTransformer.transformarFuncionario(funcionarioWSDTO));
		}
        
        logger.info("Fin - consultarFuncionariosXCriterio(...)");
        } catch (MalformedURLException e) {
            logger.error(e.getMessage());
            throw new NSJPNegocioException(CodigoError.ERROR_COMUNICACION, e);
        } catch (mx.gob.segob.nsjp.ws.cliente.consultarfuncionarioexterno.NSJPNegocioException_Exception e) {
        	logger.error(e.getMessage());
        	throw new NSJPNegocioException(CodigoError.ERROR_COMUNICACION, e);
		} 
        return loFuncionarioDTO;
	}

	@Override
	public Boolean enviarSentencia(SentenciaDTO sentenciaDTO,
			Instituciones institucion) throws NSJPNegocioException {
		boolean resultado = false;
    	logger.info("Inicia - enviarSentencia(...)");

		URL ep;
		try {
			// Configuracion de WS
			ConfInstitucion destino = this.institucionDao.read(institucion.getValorId());
			ep = new URL(destino.getUrlInst()+ "/SentenciaExporterImplService?wsdl");

			final QName SERVICE_NAME = new QName(
					"http://impl.ws.service.nsjp.segob.gob.mx/", 
					"SentenciaExporterImplService");
			
			List<DelitoPersona> delitosPersona = 
				delitoPersonaDao.consultarDelitoPerByInvolucrado(sentenciaDTO.getInvolucradoDTO().getElementoId());
			
			SentenciaWSDTO sentenciaWSDTO = SentenciaTransformer.transformarDTO2ClienteWSDTO(sentenciaDTO, delitosPersona);
			
	        SentenciaExporterImplService ss = new SentenciaExporterImplService(ep, SERVICE_NAME);
	        SentenciaExporterImpl port = ss.getSentenciaExporterImplPort();  
	        
	        port.crearSentencia(sentenciaWSDTO);
			
			logger.info("Fin - enviarSentencia(...)");
		} catch (MalformedURLException e) {
			logger.error(e.getMessage(), e);
			throw new NSJPNegocioException(CodigoError.ERROR_COMUNICACION, e);
		} catch (mx.gob.segob.nsjp.ws.cliente.sentencia.NSJPNegocioException_Exception e) {
			logger.error(e.getMessage(), e);
			throw new NSJPNegocioException(CodigoError.ERROR_COMUNICACION, e);
		}
		return resultado;
	}
	
	
    public List<CatDistritoDTO> consultarDistritos(Instituciones target) throws NSJPNegocioException {
        
    	logger.info("Inicia - consultarDistritos(...)");

    	List<CatDistritoDTO> distritosDTO = new ArrayList<CatDistritoDTO>();
		URL ep;
		try {
			// Configuración de WS
			ConfInstitucion destino = this.institucionDao.read(target
					.getValorId());
			ep = new URL(
					destino.getUrlInst()
							+ "/ConsultarDistritosServiceWSExporterImplService?wsdl");

			final QName SERVICE_NAME = new QName(
					"http://impl.ws.service.nsjp.segob.gob.mx/",
					"ConsultarDistritosServiceWSExporterImplService");

			ConsultarDistritosServiceWSExporterImplService ss = new ConsultarDistritosServiceWSExporterImplService(
					ep, SERVICE_NAME);

			ConsultarDistritosServiceWSExporter port =  ss.getConsultarDistritosServiceWSExporterImplPort();
					
			

			// Invocación del WS
			List<CatDistritoWSDTO> distritosWSDTO = port.consultarDistritos();
					

			logger.info("Respuesta - consultarDistritos:"
					+ distritosWSDTO);

			if (distritosWSDTO != null && !distritosWSDTO.isEmpty()) {
				logger.info("distritosWSDTO.size(): "
						+ distritosWSDTO.size());
				for (CatDistritoWSDTO catDistritoWSDTO : distritosWSDTO) {
					CatDistritoDTO discriminanteDTO = CatDistritoWSDTOTransformer.transformarCatDistrito(catDistritoWSDTO);
					distritosDTO.add(discriminanteDTO);
}
			}
			logger.info("Fin - consultarDistritos(...)");
		} catch (MalformedURLException e) {
			logger.error(e.getMessage());
			throw new NSJPNegocioException(CodigoError.ERROR_COMUNICACION, e);
		} catch (mx.gob.segob.nsjp.ws.cliente.consultardistritos.NSJPNegocioException_Exception e) {
			logger.error(e.getMessage());
			throw new NSJPNegocioException(CodigoError.ERROR_COMUNICACION, e);
		}

		return distritosDTO;
    }

    @Override
    public void enviarMedidaCautelarInstitucion(MedidaCautelarDTO medidaCautelarDTO, Instituciones institucionEnviar)
            throws NSJPNegocioException {

		if (medidaCautelarDTO.getInvolucrado() == null
				|| medidaCautelarDTO.getInvolucrado().getNombresDemograficoDTO() == null
				|| medidaCautelarDTO.getArchivoDigital() == null
				|| medidaCautelarDTO.getFechaCreacion() == null
				|| medidaCautelarDTO.getFechaInicio() == null
				|| medidaCautelarDTO.getFechaFin() == null
				|| institucionEnviar == null
				|| institucionEnviar.getValorId() == null
				|| institucionEnviar.getValorId() <= 0L) {
			
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}

        try {
        	
        	ConfInstitucion destino = this.institucionDao.read(institucionEnviar.getValorId());

            URL url = new URL(
            		destino.getUrlInst()
                    + "/RegistrarMedidaCautelarServiceExporterImplService?wsdl");

            final QName SERVICE_NAME = new QName(
                    "http://impl.ws.service.nsjp.segob.gob.mx/",
                    "RegistrarMedidaCautelarServiceExporterImplService");

            RegistrarMedidaCautelarServiceExporterImplService ss = new RegistrarMedidaCautelarServiceExporterImplService(
                    url, SERVICE_NAME);

            RegistrarMedidaCautelarServiceExporter port = ss
                    .getRegistrarMedidaCautelarServiceExporterImplPort();

            MedidaCautelarWSDTO toSend = new MedidaCautelarWSDTO();

            //Es activo
            toSend.setActivo((medidaCautelarDTO.getEsActivo() != null && medidaCautelarDTO
                    .getEsActivo()));
            
            //Nombre Demografico
            NombreDemograficoDTO nombre = null;
            
			if (medidaCautelarDTO.getInvolucrado().getNombresDemograficoDTO() != null
					&& !medidaCautelarDTO.getInvolucrado()
							.getNombresDemograficoDTO().isEmpty()) {
				
				for (NombreDemograficoDTO nd : medidaCautelarDTO
						.getInvolucrado().getNombresDemograficoDTO()) {
					if (nd.getEsVerdadero()) {
						nombre = nd;
					}
				}
				
				if (nombre == null) {
					nombre = medidaCautelarDTO.getInvolucrado()
							.getNombresDemograficoDTO().get(0);
				}
				
				toSend.setNombreSujeto(nombre.getNombre());
				toSend.setAPaternoSujeto(nombre.getApellidoPaterno());
				toSend.setAMaternoSujeto(nombre.getApellidoMaterno());
			}

            //Setear el folio del elemento para identificar entre instituciones
			toSend.setFolioProbableResponsable(medidaCautelarDTO.getInvolucrado()
					.getFolioElemento());
            //Descripcion
            toSend.setDescripcionMedida(medidaCautelarDTO.getDescripcionMedida());
            //Fecha de creacion
            toSend.setFechaCreacion(WsTransformer.transformFecha(medidaCautelarDTO
                    .getFechaCreacion()));
            //Fecha de inicio
            toSend.setFechaInicio(WsTransformer.transformFecha(medidaCautelarDTO
                    .getFechaInicio()));
            //Fecha de fin
            toSend.setFechaFin(WsTransformer.transformFecha(medidaCautelarDTO.getFechaFin()));
            //Folio documento
            toSend.setFolioDocumento(medidaCautelarDTO.getFolioDocumento());
            
			// Peridiocidad
			if (medidaCautelarDTO.getValorPeriodicidad() != null
					&& medidaCautelarDTO.getValorPeriodicidad().getIdCampo() != null) {
				toSend.setIdValorPeriodicidad(medidaCautelarDTO
						.getValorPeriodicidad().getIdCampo());
			}
            //Tipo de medida
            toSend.setIdValorTipoMedida(medidaCautelarDTO.getValorTipoMedida().getIdCampo());
            //Numero carpeta Ejec.
            toSend.setNumeroCarpetaEjecucion(medidaCautelarDTO.getNumeroCarpetaEjecucion());
            //Numero de caso
            toSend.setNumeroCaso(medidaCautelarDTO.getNumeroCaso());
            //Numero de Causa
            toSend.setNumeroCausa(medidaCautelarDTO.getNumeroCausa());
            //FormaId
            toSend.setFormaId(medidaCautelarDTO.getFormaDTO().getFormaId());
            //Tipo Documento
            toSend.setTipoDocumentoDTO(medidaCautelarDTO.getTipoDocumentoDTO().getIdCampo());
            //Conf institucion
            toSend.setConfInstitucionId(medidaCautelarDTO.getConfInstitucion().getConfInstitucionId());
			// Archivo Digital
			if (medidaCautelarDTO.getArchivoDigital() != null) {
				ArchivoDigitalWSDTO archivoWSDTO = new ArchivoDigitalWSDTO();

				archivoWSDTO.setContenido(medidaCautelarDTO.getArchivoDigital()
						.getContenido());
				archivoWSDTO.setNombreArchivo(medidaCautelarDTO
						.getArchivoDigital().getNombreArchivo());
				archivoWSDTO.setTipoArchivo(medidaCautelarDTO
						.getArchivoDigital().getTipoArchivo());
				toSend.setArchivoDigital(archivoWSDTO);
			}
                       
            logger.debug("Enviando medida " + medidaCautelarDTO.getFolioDocumento() + " a través de " + url);
            port.registrarMedidaCautelar(toSend);
        } catch (MalformedURLException e) {
            logger.error(e.getMessage(), e);
            throw new NSJPNegocioException(CodigoError.ERROR_COMUNICACION, e);
        } catch (mx.gob.segob.nsjp.ws.cliente.medidacautelar.NSJPNegocioException_Exception e) {
        	logger.error(e.getMessage(), e);
        	throw new NSJPNegocioException(CodigoError.ERROR_COMUNICACION, e);
		}

    }

    public Boolean actualizarEstatusMedidaCautelarInstitucion(MedidaCautelar medidaCautelar, Instituciones institucionDestino) throws NSJPNegocioException {
        //actualizarEstatusMedidaCautelar
    	logger.info("Inicia - actualizarEstatusMedidaCautelarInstitucion(...)");
    	logger.info("Institucion destino:"+ institucionDestino);
    	logger.info("Institucion destino:"+ institucionDestino.getValorId());
    	
    	Boolean envioExitoso= false;
		try {
			// Configuración de WS
			ConfInstitucion destino = this.institucionDao.read(institucionDestino
					.getValorId());
			URL url = new URL(
					destino.getUrlInst()
							+ "/RegistrarMedidaCautelarServiceExporterImplService?wsdl");

			final QName SERVICE_NAME = new QName(
					"http://impl.ws.service.nsjp.segob.gob.mx/",
					"RegistrarMedidaCautelarServiceExporterImplService");

			RegistrarMedidaCautelarServiceExporterImplService ss = new RegistrarMedidaCautelarServiceExporterImplService(
                    url, SERVICE_NAME);

            RegistrarMedidaCautelarServiceExporter port = ss
                    .getRegistrarMedidaCautelarServiceExporterImplPort();

            MedidaCautelarWSDTO medidaCautelarEnviar = new MedidaCautelarWSDTO();
            
            medidaCautelarEnviar.setActivo((medidaCautelar.getEsActivo() != null && medidaCautelar
                    .getEsActivo()));
            medidaCautelarEnviar.setDescripcionMedida(medidaCautelar.getDescripcionMedida());
            medidaCautelarEnviar.setFechaCreacion(WsTransformer.transformFecha(medidaCautelar
                    .getFechaCreacion()));
            medidaCautelarEnviar.setFechaInicio(WsTransformer.transformFecha(medidaCautelar
                    .getFechaInicio()));
            medidaCautelarEnviar.setFechaFin(WsTransformer.transformFecha(medidaCautelar.getFechaFin()));
            medidaCautelarEnviar.setFolioDocumento(medidaCautelar.getFolioDocumento());
            if(medidaCautelar.getValorPeriodicidad()!= null && medidaCautelar.getValorPeriodicidad().getValorId()!=null){
            	medidaCautelarEnviar.setIdValorPeriodicidad(medidaCautelar.getValorPeriodicidad()
                    .getValorId());
            }
            medidaCautelarEnviar.setIdValorTipoMedida(medidaCautelar.getValorTipoMedida().getValorId());
            medidaCautelarEnviar.setJuezOrdena(medidaCautelar.getJuezOrdena());
            medidaCautelarEnviar.setNumeroCarpetaEjecucion(medidaCautelar.getNumeroCarpetaEjecucion());
            medidaCautelarEnviar.setNumeroCaso(medidaCautelar.getNumeroCaso());
            medidaCautelarEnviar.setNumeroCausa(medidaCautelar.getNumeroCausa());
            medidaCautelarEnviar.setFormaId(medidaCautelar.getForma().getFormaId());
            medidaCautelarEnviar.setTipoDocumentoDTO(medidaCautelar.getTipoDocumento().getValorId());
            medidaCautelarEnviar.setConfInstitucionId(medidaCautelar.getConfInstitucion().getConfInstitucionId());
            
            ArchivoDigitalWSDTO archivo = new ArchivoDigitalWSDTO();
            ArchivoDigital original = medidaCautelar.getArchivoDigital();
            ArchivoDigitalDTO temArchivo=ArchivoDigitalTransformer.transformarArchivoDigital(original);
            archivo.setContenido(temArchivo.getContenido());
            archivo.setNombreArchivo(original.getNombreArchivo());
            archivo.setTipoArchivo(original.getTipoArchivo());
            medidaCautelarEnviar.setArchivoDigital(archivo);

            if(medidaCautelar.getEstatus()!=null && medidaCautelar.getEstatus().getValorId() != null){
            	medidaCautelarEnviar.setIdEstatus(medidaCautelar.getEstatus().getValorId());
            }
            
			// Invocación del WS
            envioExitoso = port.actualizarEstatusMedidaCautelar(medidaCautelarEnviar);
            
			logger.info("Fin - actualizarEstatusMedidaCautelarInstitucion(...)");
		} catch (MalformedURLException e) {
			logger.error(e.getMessage());
			throw new NSJPNegocioException(CodigoError.ERROR_COMUNICACION, e);
		} catch (mx.gob.segob.nsjp.ws.cliente.medidacautelar.NSJPNegocioException_Exception e) {
			logger.error(e.getMessage());
			throw new NSJPNegocioException(CodigoError.ERROR_COMUNICACION, e);
		}

		return envioExitoso;
    }
    
    public Boolean actualizarEstatusMandamientoInstitucion(Mandamiento mandamientoJudicial, Instituciones institucionDestino) throws NSJPNegocioException {
        //actualizarEstatusMandamientoJudicial
    	logger.info("Inicia - actualizarEstatusMandamientoJudicialInstitucion(...)");
    	logger.info("Institucion destino:"+ institucionDestino);
    	logger.info("Institucion destino:"+ institucionDestino.getValorId());
    	
    	Boolean envioExitoso= false;
		try {
			// Configuración de WS
			ConfInstitucion destino = this.institucionDao.read(institucionDestino
					.getValorId());
			URL url = new URL(
					destino.getUrlInst()
							+ "/ActualizarEstatusMandamientoInstitucionalExporterImplService?wsdl");
			final QName SERVICE_NAME = new QName(
                    "http://impl.ws.service.nsjp.segob.gob.mx/",
					"ActualizarEstatusMandamientoInstitucionalExporterImplService");

			ActualizarEstatusMandamientoInstitucionalExporterImplService ss = new ActualizarEstatusMandamientoInstitucionalExporterImplService(
	                   url, SERVICE_NAME);
			ActualizarEstatusMandamientoInstitucionalExporter port = ss
	                    .getActualizarEstatusMandamientoInstitucionalExporterImplPort();

            MandamientoWSDTO toSend = new MandamientoWSDTO();
            toSend.setFolioDocumento(mandamientoJudicial.getFolioDocumento());
            

            if(mandamientoJudicial.getEstatus()!=null && mandamientoJudicial.getEstatus().getValorId() != null){
            	toSend.setEstatus(mandamientoJudicial.getEstatus().getValorId());
            }
            logger.info("URL:"+ url.getHost());
            logger.info("URL:"+ url.getPort());
        	logger.info("URL:"+ url.getFile());

            envioExitoso = port.actualizarEstatusMandamientoJudicial(toSend);
			logger.info("Fin - actualizarEstatusMandamientoJudicialInstitucion(...)");
		} catch (MalformedURLException e) {
			logger.error(e.getMessage());
			throw new NSJPNegocioException(CodigoError.ERROR_COMUNICACION, e);
		} catch (mx.gob.segob.nsjp.ws.cliente.estatusmandamiento.NSJPNegocioException_Exception e) {
			logger.error(e.getMessage());
			throw new NSJPNegocioException(CodigoError.ERROR_COMUNICACION, e);
		}

		return envioExitoso;
    }


	@Override
	public SolicitudDTO enviarSolicitud(SolicitudDTO solicitudDTO,
			Instituciones destino, List<DocumentoDTO> lstDocumentoAdjuntos,
			SentenciaDTO sentenciaDTO) throws NSJPNegocioException {
    	logger.info("Inicia - enviarSolicitud(...)");

		URL ep;
		SolicitudDTO respuesta = null;
		try {
			
			if (solicitudDTO == null 
					|| destino == null ){
				throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
			} 
			// Configuracion de WS
			ConfInstitucion instDestino = this.institucionDao.read(destino.getValorId());
			ep = new URL(instDestino.getUrlInst()+ "/EnviarSolicitudServiceExporterImplService?wsdl");

			final QName SERVICE_NAME = new QName("http://impl.ws.service.nsjp.segob.gob.mx/", "EnviarSolicitudServiceExporterImplService");
			      
	        EnviarSolicitudServiceExporterImplService ss = new EnviarSolicitudServiceExporterImplService(ep, SERVICE_NAME);
	        EnviarSolicitudServiceExporterImpl port = ss.getEnviarSolicitudServiceExporterImplPort();  

	        SolicitudWSDTO solicitudWSDTO = SolicitudTransformer.transformarDTO2ClientWSDTO(solicitudDTO);
	        
	        if (lstDocumentoAdjuntos != null
	        		&& !lstDocumentoAdjuntos.isEmpty()){	        	
	        	for (DocumentoDTO documentoDTO : lstDocumentoAdjuntos) {
	        		mx.gob.segob.nsjp.ws.cliente.solicitud.DocumentoWSDTO documentoWSDTO = 	        		
	        		DocumentoWSDTOTransformer.transformarWSDTOSolicitud(documentoDTO);
	        		// se copia el tipo de actividad para saber que actividad ejecuto este documento
	        		if (documentoWSDTO != null){
	        			if (documentoDTO.getActividadDTO() != null
	        					&& documentoDTO.getActividadDTO().getTipoActividad() != null){
	        				 ActividadWSDTO actividadWSDTO = new ActividadWSDTO();
	        				 Actividades actividad = documentoDTO.getActividadDTO().getTipoActividad();
	        				 actividadWSDTO.setTipoActividadId(actividad.getValorId());
	        				 documentoWSDTO.setActividad(actividadWSDTO);
	        			}
	        		}
	        		
	        		solicitudWSDTO.getLstDocumentosAdjuntos().add(documentoWSDTO);
	        		
	        	}
	        }
	        
	        if (sentenciaDTO != null){
	        	solicitudWSDTO.setNumeroCausaSentencia(sentenciaDTO.getCnumeroCausa());
	        }

	        solicitudWSDTO = port.recibirSolicitud(solicitudWSDTO);
	        
	        respuesta = SolicitudTransformer.transformarClientWSDTO2DTO(solicitudWSDTO);
	        
			logger.info("Fin - enviarSolicitud(...)");
		} catch (MalformedURLException e) {
			logger.error(e.getMessage(), e);
			throw new NSJPNegocioException(CodigoError.ERROR_COMUNICACION, e);
		} catch (mx.gob.segob.nsjp.ws.cliente.solicitud.NSJPNegocioException_Exception e) {
			logger.error(e.getMessage(), e);
			throw new NSJPNegocioException(CodigoError.ERROR_COMUNICACION, e);
		}
		return respuesta;
	}
	
	
	@Override
	public DocumentoDTO enviarAcuseDeReciboDeSolicitudDeDefensor(
			SolicitudDTO solicitudAResponcer, DocumentoDTO documentoAEnviar,
			Instituciones target) throws NSJPNegocioException {

		logger.info("INICIA.......ENVIAR ACUSE DE RECIBO DE SOLICITU DE DEFENSOR.......");

		URL ep;

		mx.gob.segob.nsjp.ws.cliente.recibiracusederecibodesolicituddedefensor.SolicitudWSDTO solicitudWSDTOtoSend = null;
		mx.gob.segob.nsjp.ws.cliente.recibiracusederecibodesolicituddedefensor.DocumentoWSDTO documentoWSDTOtoSend = null;
		mx.gob.segob.nsjp.ws.cliente.recibiracusederecibodesolicituddedefensor.SolicitudDefensorWSDTO solicitudDefensorWSDTO = null;

		DocumentoDTO respDocDTO = new DocumentoDTO();

		try {

			// Obtenemos la institucion destino
			ConfInstitucion destino = this.institucionDao.read(target
					.getValorId());

			// Obtenemos la url y concatenamos el la ruta del web service
			ep = new URL(
					destino.getUrlInst()
							+ "/RecibirAcuseDeReciboDeSolicitudDeDefensorExporterImplService?wsdl");

			final QName SERVICE_NAME = new QName(
					"http://impl.ws.service.nsjp.segob.gob.mx/",
					"RecibirAcuseDeReciboDeSolicitudDeDefensorExporterImplService");

			RecibirAcuseDeReciboDeSolicitudDeDefensorExporterImplService ss = new RecibirAcuseDeReciboDeSolicitudDeDefensorExporterImplService(
					ep, SERVICE_NAME);

			RecibirAcuseDeReciboDeSolicitudDeDefensorExporter port = ss
					.getRecibirAcuseDeReciboDeSolicitudDeDefensorExporterImplPort();

			if (solicitudAResponcer instanceof SolicitudDefensorDTO) {
				
				solicitudDefensorWSDTO = SolicitudDefensorWSDTOTransformer
						.transformarWSDTO((SolicitudDefensorDTO) solicitudAResponcer);
				
				solicitudDefensorWSDTO.getDefensorAsignado().setRolId(
						Roles.DEFENSOR.getValorId());
				
				solicitudWSDTOtoSend = solicitudDefensorWSDTO;
				
			} else if (solicitudAResponcer instanceof SolicitudDTO) {
				solicitudWSDTOtoSend = SolicitudWSDTOTransformer
						.transformarWSDTO(solicitudAResponcer);
			}
			
			documentoWSDTOtoSend = mx.gob.segob.nsjp.service.infra.impl.transform.recibiracusederecibodesolicituddedefensor.DocumentoWSDTOTransformer
					.transformarWSDTO(documentoAEnviar);

			documentoWSDTOtoSend = port
					.recibirAcuseDeReciboDeSolicitudDeDefensor(
							solicitudWSDTOtoSend, documentoWSDTOtoSend);

			respDocDTO.setDocumentoId(documentoWSDTOtoSend.getDocumentoId());

			logger.info("Fin - enviarAcuseDeReciboDeSolicitudDeDefensor(...)");
		} catch (mx.gob.segob.nsjp.ws.cliente.recibiracusederecibodesolicituddedefensor.NSJPNegocioException_Exception e) {
			logger.error(e.getMessage());
			throw new NSJPNegocioException(CodigoError.ERROR_COMUNICACION, e);
		} catch (MalformedURLException e) {
			logger.error(e.getMessage());
			throw new NSJPNegocioException(CodigoError.ERROR_COMUNICACION, e);
		}
		catch (javax.xml.ws.soap.SOAPFaultException e) {
			logger.error(e.getMessage());
			throw new NSJPNegocioException(CodigoError.ERROR_COMUNICACION, e);
		}
		return respDocDTO;
	}

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.infra.ClienteGeneralService#actualizarSentencia(mx.gob.segob.nsjp.dto.sentencia.SentenciaDTO, mx.gob.segob.nsjp.comun.enums.institucion.Instituciones)
	 */
	@Override
	public void actualizarSentencia(SentenciaDTO sentenciaDTO,
			Instituciones institucion) throws NSJPNegocioException {
		
		logger.info("Inicia - actualizarSentencia(...)");
		
		try {
			// Configuracion de WS
			ConfInstitucion destino = this.institucionDao.read(institucion.getValorId());
			URL ep = new URL(destino.getUrlInst()+ "/ActualizacionSentenciaExporterImplService?wsdl");

			final QName SERVICE_NAME = new QName(
					"http://impl.ws.service.nsjp.segob.gob.mx/", 
					"ActualizacionSentenciaExporterImplService");
			
			mx.gob.segob.nsjp.ws.cliente.actualizacionsentencia.SentenciaWSDTO sentenciaWSDTO = 
				SentenciaTransformer.transformarActualizacionClienteWSDTO(sentenciaDTO);
			
	        ActualizacionSentenciaExporterImplService ss = new ActualizacionSentenciaExporterImplService(ep, SERVICE_NAME);
	        ActualizacionSentenciaExporter port = ss.getActualizacionSentenciaExporterImplPort();  
	        
	        port.actualizarSentencia(sentenciaWSDTO);
			
			logger.info("Fin - enviarSentencia(...)");
		} catch (MalformedURLException e) {
			logger.error(e.getMessage(), e);
			throw new NSJPNegocioException(CodigoError.ERROR_COMUNICACION, e);
		} catch (mx.gob.segob.nsjp.ws.cliente.actualizacionsentencia.NSJPNegocioException_Exception e) {
			logger.error(e.getMessage(), e);
			throw new NSJPNegocioException(CodigoError.ERROR_COMUNICACION, e);
		}
	}

	
	@Override
	public void enviarActualizacionMandamientoInterInstitucional(
			MandamientoDTO mandamientoDTO, DocumentoDTO documentoDTO,
			Instituciones institucionDestino) throws NSJPNegocioException {

		try {

			if (mandamientoDTO == null || documentoDTO == null
					|| institucionDestino == null
					|| institucionDestino.getValorId() == null) {
				logger.error("MandamientoDTO NULL o DocumentoDTO NULL o InstitucionDestino NULL****");
				throw new NSJPNegocioException(
						CodigoError.PARAMETROS_INSUFICIENTES);
			}

			ConfInstitucion confInstitucion = institucionDao
					.read(institucionDestino.getValorId());

			if (confInstitucion == null || confInstitucion.getUrlInst() == null
					|| confInstitucion.getUrlInst().isEmpty()) {
				logger.error("NO EXISTE CONF INSTITUCION O LA URL ES VACIA****");
				throw new NSJPNegocioException(
						CodigoError.INFORMACION_PARAMETROS_ERRONEA);
			}

			URL url = new URL(
					confInstitucion.getUrlInst()
							+ "/RecibirActualizacionMandamientoInterInstitucionalExporterImplService?wsdl");

			final QName SERVICE_NAME = new QName(
					"http://impl.ws.service.nsjp.segob.gob.mx/",
					"RecibirActualizacionMandamientoInterInstitucionalExporterImplService");

			RecibirActualizacionMandamientoInterInstitucionalExporterImplService ss = new RecibirActualizacionMandamientoInterInstitucionalExporterImplService(
					url, SERVICE_NAME);

			RecibirActualizacionMandamientoInterInstitucionalExporter port = ss
					.getRecibirActualizacionMandamientoInterInstitucionalExporterImplPort();

			//Transformamos los objetos que seran enviados
			mx.gob.segob.nsjp.ws.cliente.recibiractualizacionmandamiento.MandamientoWSDTO mandamientoWSDTOSend = MandamientoWSDTOTransformer
					.Transformar(mandamientoDTO);
			mx.gob.segob.nsjp.ws.cliente.recibiractualizacionmandamiento.DocumentoWSDTO DocumentoWSDTO = mx.gob.segob.nsjp.service.infra.impl.transform.recibiractualizacionmandamiento.DocumentoWSDTOTransformer
					.transformar(documentoDTO);

			port.recibirActualizacionMandamientoInterInstitucional(
					mandamientoWSDTOSend, DocumentoWSDTO);

		} catch (javax.xml.ws.soap.SOAPFaultException e) {
			logger.error(e.getMessage());
			throw new NSJPNegocioException(CodigoError.ERROR_COMUNICACION, e);
		} catch (MalformedURLException e) {
			logger.error(e.getMessage());
			throw new NSJPNegocioException(CodigoError.ERROR_COMUNICACION, e);
		} catch (mx.gob.segob.nsjp.ws.cliente.recibiractualizacionmandamiento.NSJPNegocioException_Exception e) {
			logger.error(e.getMessage());
			throw new NSJPNegocioException(CodigoError.ERROR_COMUNICACION, e);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new NSJPNegocioException(CodigoError.ERROR_COMUNICACION);
		}

	}
}
