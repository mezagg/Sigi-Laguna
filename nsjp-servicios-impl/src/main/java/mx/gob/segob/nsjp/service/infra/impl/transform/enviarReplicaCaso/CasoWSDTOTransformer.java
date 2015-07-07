/**
 * Nombre del Programa		:CasoWSDTOTransformer.java
 * Autor                    :AlejandroGA
 * Compania                 :Ultrasist
 * Proyecto                 :NSJP                    Fecha: 15 Feb 2013
 * Marca de cambio			:N/A
 * Descripcion General		:Implementacion para transformar objetos WSDTO a DTO y viceversa
 * 							 relacionados con la replica del caso
 * Programa Dependiente		:N/A
 * Programa Subsecuente		:N/A
 * Cond. de ejecucion		:N/A
 * Dias de ejecucion		:N/A                      Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor					:N/A
 * Compania					:N/A
 * Proyecto					:N/A                      Fecha: N/A
 * Modificacion				:N/A
 *------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.service.infra.impl.transform.enviarReplicaCaso;

import mx.gob.segob.nsjp.dto.caso.CasoDTO;
import mx.gob.segob.nsjp.service.infra.impl.transform.WsTransformer;
import mx.gob.segob.nsjp.ws.cliente.registarreplicacaso.CasoWSDTO;

import com.sun.istack.logging.Logger;

/**
 * @author AlejandroGA
 * @version 1.0
 */
public class CasoWSDTOTransformer {

	public static final Logger logger = Logger
			.getLogger(CasoWSDTOTransformer.class);


	/**
	 * M&eacute;todos para tranformar de DTO a WSDTO
	 */
	public static CasoWSDTO transformar(CasoDTO casoDTO) {

		if (casoDTO == null) {
			return null;
		}

		CasoWSDTO casoWSDTO = new CasoWSDTO();

		casoWSDTO.setNumeroGeneralCaso(casoDTO.getNumeroGeneralCaso());
		casoWSDTO.setFechaApertura(WsTransformer.transformFecha(casoDTO
				.getFechaApertura()));
		return casoWSDTO;
	}
	
	/**
	 * M&eacute;todos para tranformar de WSDTO a DTO
	 */
	public static CasoDTO transformar(
			mx.gob.segob.nsjp.dto.caso.CasoWSDTO casoWSDTO) {
		
		if (casoWSDTO == null) {
			return null;
		}
		
		CasoDTO casoDTO = new CasoDTO();
		
		casoDTO.setNumeroGeneralCaso(casoWSDTO.getNumeroGeneralCaso());
		casoDTO.setFechaApertura(casoWSDTO.getFechaApertura());
		
		return casoDTO;
	}
	
	
	/**
	 * M&eacute;todos para tranformar de DTO a WSDTO, de una solicitud
	 */
	public static mx.gob.segob.nsjp.ws.cliente.solicituddefensor.CasoWSDTO transformarCasoSolicitud(
			CasoDTO casoDTO) {
		if (casoDTO == null) {
			return null;
		}
		mx.gob.segob.nsjp.ws.cliente.solicituddefensor.CasoWSDTO casoWSDTO = new mx.gob.segob.nsjp.ws.cliente.solicituddefensor.CasoWSDTO();

		casoWSDTO.setNumeroGeneralCaso(casoDTO.getNumeroGeneralCaso());
		casoWSDTO.setFechaApertura(WsTransformer.transformFecha(casoDTO
				.getFechaApertura()));
		return casoWSDTO;
	}
	
	/**
	 * M&eacute;todos para tranformar de DTO a WSDTO, de una solicitud
	 */
	public static mx.gob.segob.nsjp.ws.cliente.solicitudaudiencia.CasoWSDTO transformarCasoSolicitudAudiencia(
			CasoDTO casoDTO) {
		if (casoDTO == null) {
			return null;
		}
		mx.gob.segob.nsjp.ws.cliente.solicitudaudiencia.CasoWSDTO casoWSDTO = new mx.gob.segob.nsjp.ws.cliente.solicitudaudiencia.CasoWSDTO();

		casoWSDTO.setNumeroGeneralCaso(casoDTO.getNumeroGeneralCaso());
		casoWSDTO.setFechaApertura(WsTransformer.transformFecha(casoDTO
				.getFechaApertura()));
		return casoWSDTO;
	}
}
