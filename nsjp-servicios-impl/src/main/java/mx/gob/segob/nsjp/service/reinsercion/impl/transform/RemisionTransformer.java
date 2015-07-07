/**
* Nombre del Programa 		: RemisionTransformer.java
* Autor 					: AlejandroGA
* Compania 					: Ultrasist
* Proyecto 					: NSJP 								Fecha: 05/04/2013
* Marca de cambio 			: N/A
* Descripcion General 		: Transfomer de Remision
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
package mx.gob.segob.nsjp.service.reinsercion.impl.transform;

import mx.gob.segob.nsjp.dto.programas.CatTipoRemisionDTO;
import mx.gob.segob.nsjp.dto.programas.RemisionDTO;
import mx.gob.segob.nsjp.model.Remision;

/**
 * @author AlejandroGA
 * @version 1.0
 */
public class RemisionTransformer {

	
	/**
	 * M&eacute;todo que lleva a cabo la transformaci&oacute;n de un objeto de tipo
	 * Remision a un objeto de tipo RemisionDTO, el cual se utiliza para vaciar 
	 * el contenido 
	 * @param src- Objeto que se tranformar&aacute; en un RemisionDTO.
	 * @return remisionDTO - Objeto de tipo RemisionDTO con la informaci&oacute;n contenida en src
	 */
	public static RemisionDTO transforma(Remision src) {

		if (src == null) {
			return null;
		}

		RemisionDTO remisionDTO = new RemisionDTO();

		remisionDTO.setCumplida(src.getBcumplida());
		if (src.getCatTipoRemision() != null
				&& src.getCatTipoRemision().getCatTipoRemisionId() != null) {
			remisionDTO.setCatTipoRemisionDTO(new CatTipoRemisionDTO(src
					.getCatTipoRemision().getCatTipoRemisionId()));
		}
		remisionDTO.setFechaAutorizacion(src.getdFechaAutorizacion());
		remisionDTO.setIdiasAcreditados(src.getIdiasAcreditados());
		remisionDTO.setImontoDanioReparado(src.getImontoDanioReparado());
		remisionDTO.setRemisionId(src.getRemisionId());

		return remisionDTO;
	}
}


