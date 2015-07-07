/**
 * Nombre del Programa 	: AAdministrarNumeroExpedienteSolicitudAudienciaServiceImpl.java                                    
 * Autor               	: AlejandroGA                                              
 * Compania            	: Ultrasist                                                
 * Proyecto            	: NSJP              			Fecha:16/05/2013 
 * Marca de cambio     	: N/A                                                     
 * Descripcion General  : Clase para lo relacionado con el numero de 
 * 							expediente en las solicitudes de audiencia
 * Programa Dependiente : N/A                                                      
 * Programa Subsecuente : N/A                                                      
 * Cond. de ejecucion   : N/A                                                  
 * Dias de ejecucion    : N/A                             Horario: N/A
 *                               MODIFICACIONES                                       
 *------------------------------------------------------------------------------           
 * Autor                 :N/A                                                           
 * Compania              :N/A                                                           
 * Proyecto              :N/A                                   Fecha: N/A       
 * Modificacion          :N/A                                                           
 *------------------------------------------------------------------------------      
 */
package mx.gob.segob.nsjp.service.solicitud.impl;

import mx.gob.segob.nsjp.comun.enums.configuracion.Parametros;
import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.audiencia.AudienciaDAO;
import mx.gob.segob.nsjp.dao.expediente.NumeroExpedienteDAO;
import mx.gob.segob.nsjp.dto.ParametroDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.model.NumeroExpediente;
import mx.gob.segob.nsjp.service.parametro.ConsultarParametroService;
import mx.gob.segob.nsjp.service.solicitud.AdministrarNumeroExpedienteSolicitudAudienciaService;

import org.apache.commons.lang.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author AlejandroGA
 * @version 1.0
 */

@Service
@Transactional (propagation=Propagation.REQUIRED)
public class AdministrarNumeroExpedienteSolicitudAudienciaServiceImpl implements
		AdministrarNumeroExpedienteSolicitudAudienciaService {
	
	@Autowired
	private ConsultarParametroService consultarParametroService;
	@Autowired
	private AudienciaDAO audienciaDAO;
	@Autowired
	private NumeroExpedienteDAO numeroExpedienteDAO;
	

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.solicitud.AdministrarNumeroExpedienteSolicitudAudienciaService#editarNumeroExpedienteSolicitudAudiencia(mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO)
	 */
	@Override
	public Boolean editarNumeroExpedienteSolicitudAudiencia(
			ExpedienteDTO expedienteDTO) throws NSJPNegocioException {
		
		if (expedienteDTO == null
				|| expedienteDTO.getNumeroExpedienteId() == null
				|| expedienteDTO.getNumeroExpedienteId() <= 0L){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		
		//Por default regresamos false
		Boolean respuesta =false;
		
		/**
		 * 1)Verifica si el par&aacute;metro de edici&oacute;n de n&uacute;mero
		 * de esta encendido (es decir regresa true).
		 */
		ParametroDTO parametroDTO = consultarParametroService.consultarParametro(Parametros.EDITAR_NUMERO_EXPEDIENTE);
		
		if (parametroDTO != null
				&& parametroDTO.getValor() != null
				&& BooleanUtils
						.isTrue(parametroDTO.getValor().equals("1") ? true
								: false)) {

			/**
			 * 2) Verifica que el numeroExpedienteId "NO" tiene asociadas
			 * audiencias, las cuales tengan una dFechaAudiencia que sea NOT NULL.
			 */
			respuesta = audienciaDAO
					.existenAudienciasConFechaAudienciaPorNumeroExpedienteId(expedienteDTO
							.getNumeroExpedienteId());
		}
		
		return respuesta;
	}

	
	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.solicitud.AdministrarNumeroExpedienteSolicitudAudienciaService#actualizarNumeroExpedienteSolicitudAudiencia(mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO)
	 */
	@Override
	public Boolean actualizarNumeroExpedienteSolicitudAudiencia(
			ExpedienteDTO expedienteDTO) throws NSJPNegocioException {

		if (expedienteDTO == null
				|| expedienteDTO.getNumeroExpedienteId() == null
				|| expedienteDTO.getNumeroExpedienteId() <= 0L
				|| expedienteDTO.getNumeroExpediente() == null
				|| expedienteDTO.getNumeroExpediente().trim().isEmpty()) {
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}

		Boolean respuesta = false;

		/**
		 * 1) Verificar que el parametro este encendido y que no se haya
		 * programado audiencia(debido a la concurrencia de usuarios)
		 */
		respuesta = this
				.editarNumeroExpedienteSolicitudAudiencia(expedienteDTO);

		if (respuesta.equals(false)) {
			return respuesta;
		}

		/**
		 * 2) Verificar que el numero de expedienteId exista, y a su vez cuente
		 * con un numero de expediente (string), el cual no sea igual al que se
		 * desea actualizar
		 */
		NumeroExpediente numeroExpedienteBD = numeroExpedienteDAO
				.read(expedienteDTO.getNumeroExpedienteId());

		if (numeroExpedienteBD == null
				|| numeroExpedienteBD.getNumeroExpediente() == null) {
			throw new NSJPNegocioException(
					CodigoError.INFORMACION_PARAMETROS_ERRONEA);
		}

		if (numeroExpedienteBD
				.getNumeroExpediente()
				.trim()
				.toUpperCase()
				.equals(expedienteDTO.getNumeroExpediente().trim()
						.toUpperCase())) {
			throw new NSJPNegocioException(
					CodigoError.MISMO_NUMERO_DE_EXPEDIENTE);
		}

		/**
		 * 3) Buscar si alg&uacute;n otro expediente, tiene asignado el mismo
		 * n&uacute;mero de expediente
		 */
		NumeroExpediente numExpedientenTemp = numeroExpedienteDAO
				.obtenerNumeroExpediente(expedienteDTO.getNumeroExpediente()
						.trim().toUpperCase(), null);

		if (numExpedientenTemp != null) {
			throw new NSJPNegocioException(
					CodigoError.YA_EXISTE_EL_NUMERO_EXPEDIENTE);
		}

		numeroExpedienteBD.setNumeroExpediente(expedienteDTO
				.getNumeroExpediente().trim().toUpperCase());

		numeroExpedienteBD.setNumExpAlterno(expedienteDTO.getNumeroExpediente()
				.trim().toUpperCase());
		numeroExpedienteDAO.update(numeroExpedienteBD);
		respuesta = true;

		return respuesta;
	}

}
