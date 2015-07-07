/**
 * Nombre del Programa : AvisoDesignacionServiceImplTest.java
 * Autor                            : GustavoBP
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 16/11/2012
 * Marca de cambio        : N/A
 * Descripcion General    : Pruebas unitarias para los metodos del servicio de Aviso Designaci&oacute;n.
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
package mx.gob.segob.nsjp.service.test.avisodesignacion.impl;

import java.util.List;

import mx.gob.segob.nsjp.comun.enums.documento.EstatusNotificacion;
import mx.gob.segob.nsjp.comun.enums.solicitud.TiposSolicitudes;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.documento.AvisoDesignacionDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.service.documento.AvisoDesignacionService;
import mx.gob.segob.nsjp.service.test.base.BaseTestServicios;

/**
 * Pruebas unitarias para los metodos del servicio de Aviso Designaci&oacute;n.
 * 
 * @author GustavoBP
 */
public class AvisoDesignacionServiceImplTest extends
		BaseTestServicios<AvisoDesignacionService> {

	public void testGenerarCopiaImputadosADefendidoSolicitudDefensor() {

		try {
			Long solicitudDefensorId = 1596L;
			Long expedienteId = 0L;
			Boolean resultado = service
					.generarCopiaImputadosADefendidoSolicitudDefensor(
							solicitudDefensorId, expedienteId);
			assertTrue("Fallo la generacion de copia", resultado);
			logger.info("Generacion de la copia de defendidos exitosa!!");
		} catch (NSJPNegocioException e) {
			e.printStackTrace();
		}
	}
	
	public void testReasignarAbogadoDefensorExpediente(){
		try {
			
			AvisoDesignacionDTO avisoDesignacion = new AvisoDesignacionDTO();
			avisoDesignacion.setFuncionario(new FuncionarioDTO(76L));
			
			ExpedienteDTO expedienteDto = new ExpedienteDTO();
			expedienteDto.setNumeroExpedienteId(628L);
			avisoDesignacion.setExpediente(expedienteDto);
			
			service.reasignarAbogadoDefensorExpediente(avisoDesignacion);
			logger.info("Reasignacion Exitosa!!");
		} catch (NSJPNegocioException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Prueba Unitaria que permite consultar los avisos de designaci&oacute;n
	 * de acuerdo al filtro indicado.
	 */
	public void testConsultarAvisosDesignacion(){
		try {
			Long estadoNotificacionId = null;//EstatusNotificacion .NO_ATENDIDA.getValorId(); //2143l;
			Long claveFuncionario = null;// 76l;
			Long tipoSolicitudId = TiposSolicitudes.DEFENSOR.getValorId();
			Long distritoId = 1L;
			
			
			FuncionarioDTO funcionarioDTO = null;
			if (claveFuncionario != null && claveFuncionario > 0L) {
				funcionarioDTO = new FuncionarioDTO(claveFuncionario);
			}

			EstatusNotificacion estadoNotificacion = null;
			if (estadoNotificacionId != null && estadoNotificacionId > 0L) {
				estadoNotificacion = EstatusNotificacion
						.getByValor(estadoNotificacionId);
			}

			TiposSolicitudes tipoSolicitud = null;
			if (tipoSolicitudId != null && tipoSolicitudId > 0L) {
				tipoSolicitud = TiposSolicitudes.getByValor(tipoSolicitudId);
			}
			
			List<AvisoDesignacionDTO> avisosDTO = service
					.consultarAvisosDesignacion(estadoNotificacion,
							funcionarioDTO, tipoSolicitud, distritoId);
			assertFalse("La lista no puede ser vacia", avisosDTO.isEmpty());
			for (AvisoDesignacionDTO aviso : avisosDTO) {
				logger.debug("aviso designaion id:" + aviso.getDocumentoId());
			}
			logger.debug("#:"+ avisosDTO.size());
			
		} catch (NSJPNegocioException e) {
			logger.error(e.getMessage(),e);
		}
	}
}
