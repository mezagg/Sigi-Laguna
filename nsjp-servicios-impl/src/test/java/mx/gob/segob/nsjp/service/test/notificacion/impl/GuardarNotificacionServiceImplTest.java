/**
* Nombre del Programa : GuardarNotificacionServiceImplTest.java
* Autor                            : GustavoBP
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 04/10/2011
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
package mx.gob.segob.nsjp.service.test.notificacion.impl;

import java.util.Date;

import mx.gob.segob.nsjp.comun.enums.audiencia.EstatusAudiencia;
import mx.gob.segob.nsjp.comun.enums.audiencia.TipoAudiencia;
import mx.gob.segob.nsjp.comun.enums.documento.EstatusNotificacion;
import mx.gob.segob.nsjp.comun.enums.documento.TipoDocumento;
import mx.gob.segob.nsjp.comun.enums.institucion.Areas;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.audiencia.AudienciaDTO;
import mx.gob.segob.nsjp.dto.audiencia.SalaAudienciaDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.configuracion.ConfInstitucionDTO;
import mx.gob.segob.nsjp.dto.documento.CatFormaNotificacionDTO;
import mx.gob.segob.nsjp.dto.documento.DocumentoDTO;
import mx.gob.segob.nsjp.dto.documento.NotificacionDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.forma.FormaDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.dto.funcionarioexterno.FuncionarioExternoDTO;
import mx.gob.segob.nsjp.dto.institucion.AreaDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;
import mx.gob.segob.nsjp.service.notificacion.GuardarNotificacionService;
import mx.gob.segob.nsjp.service.test.base.BaseTestServicios;

/**
 * Describir el objetivo de la clase con punto al final.
 * @version 1.0
 * @author GustavoBP
 *
 */
public class GuardarNotificacionServiceImplTest extends BaseTestServicios<GuardarNotificacionService>  {
	
	public void testGuardarYEnviarNotificacionAMismaInstitucion(){
		
		Long numeroExpedienteId = 407L; 
		
		ExpedienteDTO expedienteDTO = new ExpedienteDTO();
		expedienteDTO.setNumeroExpedienteId(numeroExpedienteId);
//		expedienteDTO.setArea(new AreaDTO(Areas.VISITADURIA));
		
		UsuarioDTO usuario = new UsuarioDTO();
		usuario.setAreaActual(new AreaDTO(Areas.VISITADURIA));
		FuncionarioDTO funcionario = new FuncionarioDTO(42L);
		funcionario.setPuesto(new ValorDTO(2110L));
		usuario.setFuncionario(funcionario);
		expedienteDTO.setUsuario(usuario );
		
		DocumentoDTO documentoDTO = new DocumentoDTO();
		documentoDTO.setTextoParcial("Texto parcial");
		documentoDTO.setFechaCreacion(new Date());
		documentoDTO.setFormaDTO(new  FormaDTO(4L));
		documentoDTO.setNombreDocumento("NOTIFICACION DE AUDIENCIA");
		documentoDTO.setTipoDocumentoDTO(new ValorDTO(TipoDocumento.NOTIFICACION.getValorId()));
		//Archivo digital pendiente
		
		
		try {
			Long idNotificacion = service.guardarYEnviarNotificacionAMismaInstitucion(expedienteDTO, documentoDTO);
			logger.info(" idNotificacion:"+ idNotificacion);
			
		} catch (NSJPNegocioException e) {
			logger.info(e.getMessage(), e);
		}
	}
	
	public void testActualizarNotificacion(){
		
		NotificacionDTO notifDto = new NotificacionDTO();
		
		notifDto.setDocumentoId(989L);
		notifDto.setFechaCitado(new Date());
		
		ValorDTO estatus = new ValorDTO();
		estatus.setIdCampo(EstatusNotificacion.ATENDIDA.getValorId());
		notifDto.setEstatus(estatus);
		
		try {
			service.actualizarNotificacion(notifDto);
			logger.info(" idNotificacion:"+ notifDto);
			
		} catch (NSJPNegocioException e) {
			logger.info(e.getMessage(), e);
		}
	}
	
	
	public void testGuardarNotificacionFuncionarioExterno()
			throws NSJPNegocioException {

		NotificacionDTO notificacionDTO = new NotificacionDTO();
		
		notificacionDTO.setEstatus(new ValorDTO(
				EstatusNotificacion.EN_PROCESO.getValorId()));
		
		CatFormaNotificacionDTO catFormaNotificacionDTO = new CatFormaNotificacionDTO();
		catFormaNotificacionDTO.setCatFormaNotificacionId(1L);
		notificacionDTO.setCatFormaNotificacionDTO(catFormaNotificacionDTO);

		Long notificacionId = service.guardarNotificacion(notificacionDTO,
				new AudienciaDTO(827L), new FuncionarioExternoDTO(10L));
		 assertNotNull("El id no puede ser nulo", notificacionId);
		 
		logger.info("La notificacion se guardo con el id::" + notificacionId);
	}
	
	
	public void testRegistrarAudienciaEnAgendaDeFuncionario(){
		
		FuncionarioDTO funcionarioDTO = new FuncionarioDTO(189L);		
		funcionarioDTO.setUsuario(new UsuarioDTO(98L));
		
		AudienciaDTO audienciaDTO = new AudienciaDTO();
		audienciaDTO.setFechaEvento(new Date());
		audienciaDTO.setDuracionEstimada(new Integer(30));
		
		SalaAudienciaDTO salaAudienciaDTO= new SalaAudienciaDTO();
		salaAudienciaDTO.setNombreSala("Sala");
		salaAudienciaDTO.setUbicacionSala("Ubicacion Sala");
		salaAudienciaDTO.setDomicilioSala("Revolucion");
		audienciaDTO.setSala(salaAudienciaDTO);
		audienciaDTO.setNumeroCausa("NSJP/ZAC/2013/001-2");
		audienciaDTO.setFolioAudiencia("PJ/1212");
		audienciaDTO.setTipoAudiencia(new ValorDTO(TipoAudiencia.CONTROL.getValorId()));
		audienciaDTO.setEstatusAudiencia(new ValorDTO(EstatusAudiencia.PROGRAMADA.getValorId()));
		
		
		try {
			service.registrarAudienciaEnAgendaDeFuncionario(funcionarioDTO, audienciaDTO);
			logger.info("SE REGISTRO EN LA AUDIENCIA DEL NOTIFICADOR");
			
		} catch (NSJPNegocioException e) {
			logger.error("ERROR CAUSA"+e.getCause());
			e.printStackTrace();
		}
	}
	
	public void testEnviarNotificacion(){
		
		NotificacionDTO notificacionDTO = new NotificacionDTO(); 
		AudienciaDTO audienciaDTO= new AudienciaDTO ();
		FuncionarioDTO funcionarioDTO= new FuncionarioDTO();
		ConfInstitucionDTO confInstitucionDTO= new ConfInstitucionDTO(3L);
		
		notificacionDTO.setDocumentoId(2586L);
		audienciaDTO.setId(1005L);
		funcionarioDTO.setClaveFuncionario(189L);
		funcionarioDTO.setInstitucion(confInstitucionDTO);
		
		
		try {
			service.enviarNotificacion(notificacionDTO, audienciaDTO, funcionarioDTO);
			logger.info("SE ENVIO LA NOTIFICACION");
		} catch (NSJPNegocioException e) {
			e.printStackTrace();
		}
	}
}
