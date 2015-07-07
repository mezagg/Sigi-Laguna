/**
* Nombre del Programa 		: RemisionWSDTOTransformer.java
* Autor 					: EdgarAT
* Compania 					: Ultrasist
* Proyecto 					: NSJP 								Fecha: 19/12/2012
* Marca de cambio 			: N/A
* Descripcion General 		: Describir el objetivo de la clase de manera breve
* Programa Dependiente 		: N/A
* Programa Subsecuente 		: N/A
* Cond. de ejecucion 		: N/A
* Dias de ejecucion 		: N/A 								Horario: N/A
*                              MODIFICACIONES
*------------------------------------------------------------------------------
* Autor 					: N/A
* Compania 					: N/A
* Proyecto 					: N/A 								Fecha: N/A
* Modificacion 				: N/A
*------------------------------------------------------------------------------
*/
package mx.gob.segob.nsjp.service.infra.impl.transform.sentencia;

import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.log4j.Logger;

import mx.gob.segob.nsjp.dto.programas.CatTipoRemisionDTO;
import mx.gob.segob.nsjp.dto.programas.RemisionDTO;
import mx.gob.segob.nsjp.model.CatTipoRemision;
import mx.gob.segob.nsjp.model.Remision;
import mx.gob.segob.nsjp.ws.cliente.sentencia.RemisionWSDTO;

/**
 * Describir el objetivo de la clase con punto al final.
 * @version 1.0
 * @author EdgarAT
 *
 */
public class RemisionWSDTOTransformer {
	
	private static final Logger LOGGER = Logger.getLogger(RemisionWSDTO.class);
	
	/**
	 * M&eacute;todo que lleva a cabo la transformaci&oacute;n de un objeto de tipo
	 * RemisionDTO a un objeto de tipo RemisionWSDTO, el cual se utiliza para el 
	 * env&iacute;o de informaci&oacute;n a otras instituciones a trav&eacute;s de 
	 * web services.
	 * @param remisionDTO - Objeto que se tranformar&aacute; en un RemisionWSDTO.
	 * @return wsdto - Objeto de tipo RemisionWSDTO con la informaci&oacute;n 
	 * 				   contenida en el dto.
	 */
	public static RemisionWSDTO transforma(RemisionDTO remisionDTO){
		RemisionWSDTO wsdto = null;
		if (remisionDTO != null){
			wsdto = new RemisionWSDTO();
			wsdto.setCumplida(remisionDTO.getCumplida());
			wsdto.setIdiasAcreditados(remisionDTO.getIdiasAcreditados());
			wsdto.setImontoDanioReparado(remisionDTO.getImontoDanioReparado());
			if (remisionDTO.getFechaAutorizacion() != null){
				try {
					GregorianCalendar gcal = (GregorianCalendar) GregorianCalendar.getInstance();
					gcal.setTime(remisionDTO.getFechaAutorizacion());
					XMLGregorianCalendar xgcal = DatatypeFactory.newInstance()
					.newXMLGregorianCalendar(gcal);
					wsdto.setFechaAutorizacion(xgcal);
				} catch (DatatypeConfigurationException e) {
					LOGGER.error(e.getMessage(), e);
				}				
			}
			if (remisionDTO.getCatTipoRemisionDTO() != null
					&& remisionDTO.getCatTipoRemisionDTO().getCatTipoRemisionId() != null
					&& remisionDTO.getCatTipoRemisionDTO().getCatTipoRemisionId() > 0L){
				wsdto.setCatTipoRemisionId(remisionDTO.getCatTipoRemisionDTO().getCatTipoRemisionId());
			}
		}
		return wsdto;
	}

	/**
	 * M&eacute;todo que lleva a cabo la transformaci&oacute;n de un objeto de tipo
	 * RemisionWSDTO a un objeto de tipo RemisionDTO, el cual se utiliza para vaciar 
	 * el contenido de informaci&oacute;n recibido de otras instituciones a trav&eacute;s 
	 * de web services.
	 * @param remisionWSDTO - Objeto que se tranformar&aacute; en un RemisionDTO.
	 * @return dto - Objeto de tipo RemisionWSDTO con la informaci&oacute;n contenida 
	 * 				 en el wsdto.
	 */
	public static RemisionDTO transforma(
			mx.gob.segob.nsjp.dto.programas.RemisionWSDTO remisionWSDTO) {
		RemisionDTO dto = null;
		if (remisionWSDTO != null){
			dto = new RemisionDTO();
			if (remisionWSDTO.getCatTipoRemisionId() != null 
					&& remisionWSDTO.getCatTipoRemisionId() > 0L){
				CatTipoRemisionDTO catTipoRemisionDTO = new CatTipoRemisionDTO();
				catTipoRemisionDTO.setCatTipoRemisionId(remisionWSDTO.getCatTipoRemisionId());
				dto.setCatTipoRemisionDTO(catTipoRemisionDTO);
			}
			dto.setCumplida(remisionWSDTO.getCumplida());
			dto.setFechaAutorizacion(remisionWSDTO.getFechaAutorizacion());
			dto.setIdiasAcreditados(remisionWSDTO.getIdiasAcreditados());
			dto.setImontoDanioReparado(remisionWSDTO.getImontoDanioReparado());
		}
		return dto;
	}
	
	/**
	 * M&eacute;todo que lleva a cabo la transformaci&oacute;n de un objeto de tipo
	 * RemisionWSDTO a un objeto de tipo RemisionDTO, el cual se utiliza para vaciar 
	 * el contenido de informaci&oacute;n recibido de otras instituciones a trav&eacute;s 
	 * de web services.
	 * @param remisionWSDTO - Objeto que se tranformar&aacute; en un RemisionDTO.
	 * @return dto - Objeto de tipo RemisionWSDTO con la informaci&oacute;n contenida 
	 * 				 en el wsdto.
	 */
	public static Remision transformaEntity(
			mx.gob.segob.nsjp.dto.programas.RemisionWSDTO remisionWSDTO) {
		Remision entity = null;
		if (remisionWSDTO != null){
			entity = new Remision();
			if (remisionWSDTO.getCatTipoRemisionId() != null 
					&& remisionWSDTO.getCatTipoRemisionId() > 0L){
				CatTipoRemision catTipoRemision = new CatTipoRemision();
				catTipoRemision.setCatTipoRemisionId(remisionWSDTO.getCatTipoRemisionId());
				entity.setCatTipoRemision(catTipoRemision);
			}
			entity.setBcumplida(remisionWSDTO.getCumplida());
			entity.setdFechaAutorizacion(remisionWSDTO.getFechaAutorizacion());
			entity.setIdiasAcreditados(remisionWSDTO.getIdiasAcreditados());
			entity.setImontoDanioReparado(remisionWSDTO.getImontoDanioReparado());
		}
		return entity;
	}
	
	/**
	 * M&eacute;todo que lleva a cabo la transformaci&oacute;n de un objeto de tipo
	 * RemisionDTO a un objeto de tipo RemisionWSDTO, el cual se utiliza para el 
	 * env&iacute;o de informaci&oacute;n a otras instituciones a trav&eacute;s de 
	 * web services.
	 * @param remisionDTO - Objeto que se tranformar&aacute; en un RemisionWSDTO.
	 * @return wsdto - Objeto de tipo RemisionWSDTO con la informaci&oacute;n 
	 * 				   contenida en el dto.
	 */
	public static mx.gob.segob.nsjp.ws.cliente.actualizacionsentencia.RemisionWSDTO transformaActualizacion(RemisionDTO remisionDTO){
		mx.gob.segob.nsjp.ws.cliente.actualizacionsentencia.RemisionWSDTO wsdto = null;
		if (remisionDTO != null){
			wsdto = new mx.gob.segob.nsjp.ws.cliente.actualizacionsentencia.RemisionWSDTO();
			wsdto.setCumplida(remisionDTO.getCumplida());
			wsdto.setIdiasAcreditados(remisionDTO.getIdiasAcreditados());
			wsdto.setImontoDanioReparado(remisionDTO.getImontoDanioReparado());
			if (remisionDTO.getFechaAutorizacion() != null){
				try {
					GregorianCalendar gcal = (GregorianCalendar) GregorianCalendar.getInstance();
					gcal.setTime(remisionDTO.getFechaAutorizacion());
					XMLGregorianCalendar xgcal = DatatypeFactory.newInstance()
					.newXMLGregorianCalendar(gcal);
					wsdto.setFechaAutorizacion(xgcal);
				} catch (DatatypeConfigurationException e) {
					LOGGER.error(e.getMessage(), e);
				}				
			}
			if (remisionDTO.getCatTipoRemisionDTO() != null
					&& remisionDTO.getCatTipoRemisionDTO().getCatTipoRemisionId() != null
					&& remisionDTO.getCatTipoRemisionDTO().getCatTipoRemisionId() > 0L){
				wsdto.setCatTipoRemisionId(remisionDTO.getCatTipoRemisionDTO().getCatTipoRemisionId());
			}
		}
		return wsdto;
	}
}
