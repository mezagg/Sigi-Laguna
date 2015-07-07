/**
* Nombre del Programa 		: BitacoraElementoTransformer.java
* Autor 					: EdgarAT
* Compania 					: Ultrasist
* Proyecto 					: NSJP 								Fecha: 07/11/2012
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
package mx.gob.segob.nsjp.service.elemento.impl.transform;

import mx.gob.segob.nsjp.dto.elemento.BitacoraElementoDTO;
import mx.gob.segob.nsjp.dto.elemento.ElementoDTO;
import mx.gob.segob.nsjp.model.BitacoraElemento;
import mx.gob.segob.nsjp.model.Elemento;

/**
 * Describir el objetivo de la clase con punto al final.
 * @version 1.0
 * @author EdgarAT
 *
 */
public class BitacoraElementoTransformer {
	
	/**
	 * M&eacute;todo que lleva a cabo la transformaci&oacute;n de un objeto de
	 * tipo BitacoraElemento (Entity) a un objeto de tipo BitacoraElementoDTO (DTO).
	 * @param bitacoraElemento - El entity a partir del cual se generar&aacute; el DTO.
	 * @return dto - El BitacoraElementoDTO con la informaci&oacute;n del entity una vez
	 * 				 que ha sido transformado.
	 */
	public static BitacoraElementoDTO transformar (BitacoraElemento bitacoraElemento){
		BitacoraElementoDTO dto = null;
		if (bitacoraElemento != null){
			dto = new BitacoraElementoDTO();
			dto.setBitacoraElementoId(bitacoraElemento.getBitacoraElementoId());
			dto.setFechaModificacion(bitacoraElemento.getFechaModificacion());
			if (bitacoraElemento.getElemento() != null){
				ElementoDTO elementoDTO = new ElementoDTO();
				elementoDTO.setElementoId(bitacoraElemento.getElemento().getElementoId());
				dto.setElemento(elementoDTO);
			}
		}
		return dto;
	}
	
	/**
	 * M&eacute;todo que lleva a cabo la transformaci&oacute;n de un objeto de
	 * tipo BitacoraElementoDTO (DTO) a un objeto de tipo BitacoraElemento (Entity).
	 * @param bitacoraElementoDTO - El DTO a partir del cual se generar&aacute; el Entity.
	 * @return entity - El BitacoraElemento con la informaci&oacute;n del DTO una vez
	 * 					que ha sido transformado.
	 */
	public static BitacoraElemento transformar (BitacoraElementoDTO bitacoraElementoDTO){
		BitacoraElemento entity = null;
		if (bitacoraElementoDTO != null){
			entity = new BitacoraElemento();
			entity.setBitacoraElementoId(bitacoraElementoDTO.getBitacoraElementoId());
			entity.setFechaModificacion(bitacoraElementoDTO.getFechaModificacion());
			if (bitacoraElementoDTO.getElemento() != null){
				Elemento elemento = new Elemento();
				elemento.setElementoId(bitacoraElementoDTO.getElemento().getElementoId());
				entity.setElemento(elemento);
			}
		}
		return entity;
	}
	
	/**
	 * M&eacute;todo utilitario que lleva a cabo la actualizaci&oacute;n de un objeto
	 * de tipo entity con la informaci&oacute;n contenida en un objeto de tipo DTO. 
	 * @param origen - El DTO con el cual se va a actualizar la informaci&oacute;n 
	 * 				   del entity.
	 * @param destino - El entity del que se llevar&aacute; a cabo la actualizaci&oacute;n
	 * 					de la informaci&oacute;n
	 * @return destino - El entity una vez que la informaci&oacute;n ha sido actualizada.
	 */
	public static BitacoraElemento mergeForUpdate(BitacoraElementoDTO origen, BitacoraElemento destino){
		if (origen != null
				&& destino != null){
			destino.setFechaModificacion(origen.getFechaModificacion());
			if (origen.getElemento() != null
					&& origen.getElemento().getElementoId() > 0L){
				destino.setElemento(new Elemento(origen.getElemento().getElementoId()));
			}
		}
		return destino;
	}

}
