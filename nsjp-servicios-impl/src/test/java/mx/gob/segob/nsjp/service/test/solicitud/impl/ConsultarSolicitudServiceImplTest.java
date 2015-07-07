/**
 * Nombre del Programa : ConsultarSolicitudServiceImplTest.java
 * Autor                            : vaguirre
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 16 Jun 2011
 * Marca de cambio        : N/A
 * Descripcion General    : Prueba unitaria de ConsultarSolicitudServiceImpl
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
package mx.gob.segob.nsjp.service.test.solicitud.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mx.gob.segob.nsjp.comun.enums.documento.EstatusNotificacion;
import mx.gob.segob.nsjp.comun.enums.institucion.Instituciones;
import mx.gob.segob.nsjp.comun.enums.solicitud.EstatusSolicitud;
import mx.gob.segob.nsjp.comun.enums.solicitud.TiposSolicitudes;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.comun.util.DateUtils;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.documento.AvisoDesignacionDTO;
import mx.gob.segob.nsjp.dto.documento.MandamientoDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudMandamientoDTO;
import mx.gob.segob.nsjp.service.solicitud.ConsultarSolicitudService;
import mx.gob.segob.nsjp.service.test.base.BaseTestServicios;

/**
 * Prueba unitaria de ConsultarSolicitudServiceImpl.
 * 
 * @version 1.0
 * @author vaguirre
 * 
 */
public class ConsultarSolicitudServiceImplTest
        extends
            BaseTestServicios<ConsultarSolicitudService> {

    public void testConsultarSolicitudesPorExpediente() {
        ExpedienteDTO f = new ExpedienteDTO();
        f.setNumeroExpedienteId(1L);
        try {
            List<SolicitudDTO> resp = service.consultarSolicitudesPorExpediente(f);
            assertNotNull("No puede ser nula la lista", resp);
            for (SolicitudDTO solicitudDTO : resp) {
				logger.info(" Folio:" + solicitudDTO.getFolioDocumento());
			}
        } catch (NSJPNegocioException e) {
            fail(e.getMessage());
        }
    }
    
    public void testObtenerSolicitud() {
    	SolicitudDTO sol = new SolicitudDTO();
        sol.setDocumentoId(652L);
        try {
        	
            SolicitudDTO resp = service.obtenerSolicitud(sol);
            System.out.println("Número de caso \t\t: "+resp.getNumeroCasoAsociado());
            System.out.println("Fecha-hora sol\t: "+resp.getFechaCreacion());
            if(resp.getInstitucion()!=null){
            	System.out.println("Institución solicitante: "+resp.getInstitucion().getConfInstitucionId());
            	System.out.println("Institución solicitante: "+resp.getInstitucion().getNombreInst());
            }
            System.out.println("Nombre del solicitante\t: "+resp.getNombreSolicitanteExternoInterno());
            System.out.println("ESTADO\t\t: "+resp.getEstatus().getValor());
            System.out.println("Fecha-hora entrega\t: "+resp.getFechaCierre());
            if(resp.getTipoSolicitudDTO()!=null)
            System.out.println("TIPO SOL\t\t: "+resp.getTipoSolicitudDTO().getValor());
            System.out.println("Identificador AUDIENCIA: ");
            if(resp.getExpedienteDTO()!= null){
            	System.out.println("TOCA\t\t: "+resp.getExpedienteDTO().getNumeroExpediente());
//            if(resp.getExpedienteDTO().getNumExpHijos()!=null){
//            	for (ExpedienteDTO expH : resp.getExpedienteDTO().getNumExpHijos()) {
//                	logger.info("------------------------------");
//        			logger.info("id: "+expH.getExpedienteId());
//        			logger.info("numero:"+expH.getNumeroExpedienteId()+ " ("+expH.getNumeroExpediente()+")");
//				}
//            }
            
            	System.out.println("Numero Expediente\t\t: "+resp.getExpedienteDTO().getNumeroExpediente());
            }
            
            System.out.println("Urgente\t\t: "+resp.getEsUrgente());
            System.out.println("Motivo\t\t: "+resp.getMotivo());
            System.out.println("Funcionario Remitente\t\t: "+resp.getDestinatario());
            if(resp.getDestinatario()!= null){
            	System.out.println("Solicitud - getClaveFuncionario:"+ resp.getDestinatario().getClaveFuncionario());
				System.out.println("*Solicitud - getNombreFuncionario:"+ resp.getDestinatario().getNombreFuncionario());
				System.out.println("Solicitud - getNombreCompleto:"+ resp.getDestinatario().getNombreCompleto());
				System.out.println("**Solicitud - getPuesto:"+ resp.getDestinatario().getPuesto());
				System.out.println("**Solicitud - getJerarquiaOrganizacional:"+ resp.getDestinatario().getJerarquiaOrganizacional());
				System.out.println("**Solicitud - getNombreInstitucion:"+ resp.getDestinatario().getNombreInstitucion());
				System.out.println("**Solicitud - Area - getDepartamento:"+ resp.getDestinatario().getDepartamento());
				if(resp.getDestinatario().getDepartamento()!= null)
					System.out.println("**Solicitud - Area - getDepartamento:"+ resp.getDestinatario().getDepartamento().getArea());
			}
            
            assertNotNull("No puede ser nulo el objeto", resp);
        } catch (NSJPNegocioException e) {
            fail(e.getMessage());
        }
    }
    
    public void testConsultarSolicitudPorNumeroExpyTipo(){
    	
    	try {
			List<SolicitudDTO> solicitudes = service.consultarSolicitudesPorNumeroExpedienteYTipo(95L, TiposSolicitudes.AUDIENCIA);
			assertNotNull(solicitudes);
			logger.info("Existen "+solicitudes.size()+" solicitudes");
			for (SolicitudDTO sol : solicitudes) {
				logger.info("----------------------------------");
				logger.info("Id: "+sol.getDocumentoId());
				logger.info("Exp: "+sol.getExpedienteDTO().getExpedienteId());
//				logger.info("Tipo Doc: "+sol.getTipoDocumentoDTO().getValor());
				logger.info("Tipo Sol: "+sol.getTipoSolicitudDTO().getValor());
			}
		} catch (NSJPNegocioException e) {
			assertNull(e);
		}
    	
    }
    
    public void testConsultarDatosDeSolicitud(){
    	String folioSolicitud = "CD/199900001";
    	try {
			SolicitudDTO solicitud= service.consultarDatosDeSolicitud(folioSolicitud);
			System.out.println(solicitud);
			assertNotNull(solicitud);
		} catch (NSJPNegocioException e) {
			assertNull(e);
		}    	
    }
    
	public void testConsultarSolicitudesGeneradas() {
		//{1775L, 1776L, 1777L, 1778L, 1779L, 2096L};
		Long[] idTS= 
		{TiposSolicitudes.RECURSO_APELACION.getValorId(), TiposSolicitudes.RECURSO_CASACION.getValorId(), //TiposSolicitudes.AUDIENCIA.getValorId(),
				TiposSolicitudes.AUDIO_VIDEO.getValorId(),TiposSolicitudes.TRANSCRIPCION_DE_AUDIENCIA.getValorId()};
		//1714L, 1715L,1718L, 1771L, 1772L,1774L, 1767L, 1766L, 2114L };
		Long[] idES= {EstatusSolicitud.EN_PROCESO.getValorId()};
		List<Long> idEstatusSolicitud= Arrays.asList(idES);
		List<Long> idTipoSolicitud = Arrays.asList(idTS);
		Long idAreaOrigen = null;//44L;//Instituciones.PGJ.getValorId(); 
		Long idFuncionarioSolicitante = null;//3L;
		try {
			
			List<SolicitudDTO> solicitudesDTO = service
					.consultarSolicitudesGeneradas(idEstatusSolicitud, idTipoSolicitud, idAreaOrigen, idFuncionarioSolicitante);
			assertTrue( !solicitudesDTO.isEmpty());
			logger.info("Solicitudes:"+ solicitudesDTO.size());
			for (SolicitudDTO solicitudDTO : solicitudesDTO) {
				imprimirSolicitudDTO(solicitudDTO);
			}
		} catch (NSJPNegocioException e) {
			assertNull(e);
		}
	}
	
	public void testConsultarSolicitudesGeneradasPorNumeroExpediente() {
		
		Long[] idES= {};//{1775L, 1776L, 1777L, 1778L, 1779L, 2096L};
		List<Long> idEstatusSolicitud= Arrays.asList(idES);
		Long[] idTS= {};//TiposSolicitudes.AUDIENCIA.getValorId() ; //1714L, 1715L,1718L, 1771L, 1772L,1774L, 1767L, 1766L, 2114L };
		List<Long> idTipoSolicitud = Arrays.asList(idTS);
		
		Long idAreaOrigen = 10L;//Instituciones.PGJ.getValorId(); 
		Long idFuncionarioSolicitante = 7L;
		String numeroExpediente  = "NSJYUCPG201110333AV";//
		try {
			
			
			List<SolicitudDTO> solicitudesDTO = service
					.consultarSolicitudesGeneradasPorNumeroExpediente(
							idEstatusSolicitud, idTipoSolicitud, idAreaOrigen,
							idFuncionarioSolicitante, numeroExpediente);
			assertTrue( !solicitudesDTO.isEmpty());
			for (SolicitudDTO solicitudDTO : solicitudesDTO) {
				imprimirSolicitudDTO(solicitudDTO);
			}
			logger.info("Solicitudes:"+ solicitudesDTO.size());
		} catch (NSJPNegocioException e) {
			assertNull(e);
		}
	}
	
	public void testConsultarSolicitudesParaAtender() {
		//{1775L, 1776L, 1777L, 1778L, 1779L, 2096L};
		Long[] idES= {};//{};
		//1714L, 1715L,1718L, 1771L, 1772L,1774L, 1767L, 1766L, 2114L };
		Long[] idTS= {};
		List<Long> idEstatusSolicitud= Arrays.asList(idES);
		List<Long> idTipoSolicitud = Arrays.asList(idTS);
		Long idAreaDestino = Instituciones.PGJ.getValorId(); 
		Long idFuncionarioDestinatario = 3L;
		Long catDiscriminanteOrigen = 1L;
		try {
			
			List<SolicitudDTO> solicitudesDTO = service
					.consultarSolicitudesParaAtender(idEstatusSolicitud, idTipoSolicitud, idAreaDestino, idFuncionarioDestinatario,catDiscriminanteOrigen);
			assertTrue("No se regresaron elementos", !solicitudesDTO.isEmpty());
			logger.info("Solicitudes:"+ solicitudesDTO.size());
			for (SolicitudDTO solicitudDTO : solicitudesDTO) {
				imprimirSolicitudDTO(solicitudDTO);
			}
		} catch (NSJPNegocioException e) {
			assertNull(e);
		}
	}
	
	public void testConsultarSolicitudesPorTipoYEstatus () {		
		try {
			List<SolicitudDTO> respuesta = service.consultarSolicitudesPorTipoYEstatus(
					TiposSolicitudes.BENEFICIO_PRELIBERACION, EstatusSolicitud.ABIERTA, null,null);
//			assertTrue("La lista debe tener minimo un registro", respuesta.size()>0);
			logger.info("La lista debe tener minimo un registro : " + respuesta.size());
		} catch (NSJPNegocioException e) {
			logger.error(e.getMessage());
		}			
	}

	public void imprimirSolicitudDTO(SolicitudDTO solicitudDTO ){
		logger.info("*********************************************");
		logger.info("*Solicitud - getDocumentoId:"+ solicitudDTO.getDocumentoId());
		logger.info("*Solicitud - getNumeroCasoAsociado:"+ solicitudDTO.getNumeroCasoAsociado());
		logger.info("*Solicitud - getMotivo:"+ solicitudDTO.getMotivo());
		logger.info("Solicitud - getExpediente:"+ solicitudDTO.getExpedienteDTO());
		
		if(solicitudDTO.getExpedienteDTO()!= null){
			ExpedienteDTO expedienteDTO = solicitudDTO.getExpedienteDTO();
			logger.info("expediente - getExpedienteId:"+ expedienteDTO.getExpedienteId());
			logger.info("*expediente - getNumeroExpediente:"+ expedienteDTO.getNumeroExpediente());
			logger.info("*expediente - getNumeroExpediente:"+ expedienteDTO.getCasoDTO());
		}
		logger.info("*Solicitud - getFolioSolicitud:"+ solicitudDTO.getFolioSolicitud());
		logger.info("*Solicitud - getEstatus:"+ solicitudDTO.getEstatus());
		
		if(solicitudDTO.getEstatus()!= null)
			logger.info("Solicitud - getEstatus:"+ solicitudDTO.getEstatus().getValor());
		logger.info("*Solicitud - getFechaCreacion:"+ solicitudDTO.getFechaCreacion());
		logger.info("*Solicitud - getStrFechaCreacion:"+ solicitudDTO.getStrFechaCreacion());
		logger.info("*Solicitud - getFechaLimite:"+ solicitudDTO.getFechaLimite());
		logger.info("*Solicitud - getStrFechaLimite:"+ solicitudDTO.getStrFechaLimite());
		logger.info("Solicitud - getConfInstitucion:"+ solicitudDTO.getInstitucion());

		
		if(solicitudDTO.getInstitucion()!= null)
			logger.info("*Solicitud - getConfInstitucion:"+ solicitudDTO.getInstitucion().getNombreInst());
		logger.info("Solicitud - getDestinatario:"+ solicitudDTO.getDestinatario());
		if(solicitudDTO.getDestinatario()!= null){
			logger.info("Solicitud - getClaveFuncionario:"+ solicitudDTO.getDestinatario().getClaveFuncionario());
			logger.info("*Solicitud - getNombreFuncionario:"+ solicitudDTO.getDestinatario().getNombreFuncionario());
			logger.info("Solicitud - getNombreCompleto:"+ solicitudDTO.getDestinatario().getNombreCompleto());
			logger.info("**Solicitud - getPuesto:"+ solicitudDTO.getDestinatario().getPuesto());
			logger.info("**Solicitud - Area - getJerarquiaOrganizacional:"+ solicitudDTO.getDestinatario().getJerarquiaOrganizacional());
			logger.info("**Solicitud - getDepartamento:"+ solicitudDTO.getDestinatario().getDepartamento());
		}
		logger.info("**Solicitud - getAsuntoSolicitud:"+ solicitudDTO.getAsuntoSolicitud());
		logger.info("**Solicitud - getObservaciones:"+ solicitudDTO.getObservaciones());
		
		if(solicitudDTO.getDestinatario()!= null){
			logger.info("solicitudDTO - getDestinatario:"+ solicitudDTO.getDestinatario().getClaveFuncionario());
			logger.info("solicitudDTO - getDestinatario:"+ solicitudDTO.getDestinatario().getNombreCompleto());
		}
			
		if(solicitudDTO.getUsuarioSolicitante()!= null){
			logger.info("solicitudDTO - getUsuarioSolicitante:"+ solicitudDTO.getUsuarioSolicitante().getClaveFuncionario());
			logger.info("solicitudDTO - getUsuarioSolicitante:"+ solicitudDTO.getUsuarioSolicitante().getNombreCompleto());
		}
	}
	
	/**
	 * Prueba Unitaria para la consulta de las solicitudes por Filtro
	 * -Defensoria:
	 * 		Solicitudes de Defensor
	 * 		Solicitudes de Asesoria Legal
	 * -PJ
	 * 		Solicitudes de audiencia
	 */
	public void testConsultarSolicitudesPorFiltro() {
		try {

			SolicitudDTO solFiltro = new SolicitudDTO();

			//Prueba con consulta de Distrito
			solFiltro.setTipoSolicitudDTO(new ValorDTO(TiposSolicitudes.ASESORIA_DEFENSORIA
					.getValorId()));
			solFiltro.setEstatus(new ValorDTO(EstatusSolicitud.ASIGNADO
					.getValorId()));
			Long distritoId =33L;
			Boolean esAsociadaNumeroExpediente = null;
			
			//Filtro para Tipo de Solicitud de ASesoria y Defensor
			AvisoDesignacionDTO avisoDesignacionFiltroDTO = new AvisoDesignacionDTO();
			avisoDesignacionFiltroDTO.setEstatus(new ValorDTO(EstatusNotificacion.NO_ATENDIDA.getValorId()));
			Long claveFuncionario = 80L;
			FuncionarioDTO funcionarioDTO = new FuncionarioDTO(claveFuncionario );
			avisoDesignacionFiltroDTO.setFuncionario(funcionarioDTO );
			solFiltro.setAvisoDesignacionDTO(avisoDesignacionFiltroDTO);
			
			//ASESORIA LEGAL 
			solFiltro.setTipoSolicitudDTO(new ValorDTO(
					TiposSolicitudes.ASESORIA_DEFENSORIA.getValorId()));
					
//			solFiltro.setConfInstitucion(new ConfInstitucionDTO(
//					Instituciones.PGJ.getValorId()));
//			solFiltro.setEstatus(new ValorDTO(EstatusSolicitud.ABIERTA
//					.getValorId()));
//			solFiltro.setTipoSolicitudDTO(new ValorDTO(
//					TiposSolicitudes.DEFENSOR.getValorId()));
			
//			solFiltro.setTipoSolicitudDTO(new ValorDTO(TiposSolicitudes.POLICIA_MINISTERIAL.getValorId()));
//			
//			ExpedienteDTO loExpedienteDTO = new ExpedienteDTO();
//			loExpedienteDTO.setNumeroExpedienteId(4440L);			
//			solFiltro.setExpedienteDTO(loExpedienteDTO);
//			
			List<SolicitudDTO> respuesta = service
					.consultarSolicitudesPorFiltro(solFiltro, esAsociadaNumeroExpediente, distritoId);
			
//			List<SolicitudDTO> respuesta = service
//					.consultarSolicitudesPorFiltro(solFiltro, null, null);

			assertFalse("La lista debe no puede estar vacia",
					respuesta.isEmpty());
			if(respuesta != null && !respuesta.isEmpty()){
				logger.info("Tamaño de la lista : "
						+ respuesta.size());

				for(SolicitudDTO solicitudDto:respuesta){
					logger.info("Solicitud Id : "
							+ solicitudDto.getDocumentoId());
					logger.info("Tipo Solicitud_val: "
							+ solicitudDto.getTipoSolicitudDTO().getIdCampo());
					logger.info("Fecha Limite: "
							+ DateUtils.formatear(solicitudDto.getFechaLimite()));
					logger.info("Solicitante: "
							+ solicitudDto.getUsuarioSolicitante()!=null?
									solicitudDto.getUsuarioSolicitante().getNombreCompleto():"-");
					logger.info("Solicitante: "
							+ ((solicitudDto.getDestinatario() != null
							&& solicitudDto.getDestinatario()
									.getNombreCompleto() != null )? solicitudDto
							.getDestinatario().getNombreCompleto() : "-"));
					logger.info("Solicitante: "
							+ solicitudDto.getNumeroExpediente());
							
					logger.info("Solicitante: "
									+ solicitudDto.getNumeroExpedienteAsociado());
					if(solicitudDto.getAvisoDesignacionDTO()!=null){
						AvisoDesignacionDTO avisoDesignacionDTO = solicitudDto.getAvisoDesignacionDTO();
						logger.info("avisoDesignacionDTO: "
								+ avisoDesignacionDTO);
						logger.info("avisoDesignacionDTO: "
								+ avisoDesignacionDTO.getFuncionario() != null ? avisoDesignacionDTO
								.getFuncionario().getNombreCompleto() : "--");
					}
				}
			}else{
				logger.info("NO SE ENCONTRARON RESULTADOS.................");
			}
			
		} catch (NSJPNegocioException e) {
			logger.error(e.getMessage());
		}
	}
	
	
	public void consultarSolicitudesPorTipoYNumeroExps() {
		try {
		
			List<Long> idTipoSolicitud = new ArrayList<Long>();
			idTipoSolicitud.add(TiposSolicitudes.ATENCION_PSICOLOGICA.getValorId());
			idTipoSolicitud.add(TiposSolicitudes.TRABAJO_SOCIAL.getValorId());
			idTipoSolicitud.add(TiposSolicitudes.ATENCION_JURIDICA.getValorId());
			
			List<SolicitudDTO> respuesta = service.consultarSolicitudesPorTipoYNumeroExps(1711L, idTipoSolicitud);

			if(respuesta != null && !respuesta.isEmpty()){
				for (SolicitudDTO solicitudDTO : respuesta) {
					logger.info(" Fecha de cracion: "  + solicitudDTO.getExpedienteDTO().getFechaAperturaNumeroExp());
					logger.info(" Numero de expediente: "  + solicitudDTO.getExpedienteDTO().getNumeroExpediente());
					logger.info(" Funcionario: "  + solicitudDTO.getExpedienteDTO().getResponsableTramite().getNombreCompleto());
					logger.info(" Estatus: "  + solicitudDTO.getExpedienteDTO().getEstatusNumeroExpediente().getValor());
					logger.info(" Tipo Solicitud: "  + solicitudDTO.getTipoSolicitudDTO().getValor());
					
				}
				
				
				logger.info("Tamaño de la lista : "	+ respuesta.size());
				
			}else{
				logger.info("NO SE ENCONTRARON RESULTADOS.................");
			}
			
		} catch (NSJPNegocioException e) {
			logger.error(e.getMessage());
		}
	}

	public void testsConsultarSolicitudesMandatoJudicialPorFiltro() {

		SolicitudMandamientoDTO solicitudMandamientoDTO = new SolicitudMandamientoDTO();

		MandamientoDTO mandamientoDTO = new MandamientoDTO();
		mandamientoDTO.setDocumentoId(7321L);
		solicitudMandamientoDTO.setMandamiento(mandamientoDTO);

		try {
			List<SolicitudMandamientoDTO> listaSolsMandamientos = service
					.consultarSolicitudesMandatoJudicialPorFiltro(solicitudMandamientoDTO);

			assertNotNull(listaSolsMandamientos);

			for (SolicitudMandamientoDTO solMandamientoDTO : listaSolsMandamientos) {
				logger.info("Mandamiento: "
						+ solMandamientoDTO.getMandamiento()
								.getDocumentoId());
				logger.info("Destinatario: "
						+ solMandamientoDTO.getDestinatario()
								.getNombreCompleto());
				logger.info("Responsable: "
						+ solMandamientoDTO.getResponsableDocumento()
								.getNombreCompleto());
			}
			

		} catch (NSJPNegocioException e) {
			logger.error(e.getMessage(),e);
		}
	}

	
}
