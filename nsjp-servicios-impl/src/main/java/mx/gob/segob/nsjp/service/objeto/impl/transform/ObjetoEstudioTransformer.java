/**
* Nombre del Programa 		: ObjetoEstudioTransformer.java
* Autor 					: EdgarTE
* Compania 					: Ultrasist
* Proyecto 					: NSJP 							Fecha: 11 Sep 2012
* Marca de cambio 			: N/A
* Descripcion General 		: Describir el objetivo de la clase de manera breve
* Programa Dependiente 		: N/A
* Programa Subsecuente 		: N/A
* Cond. de ejecucion 		: N/A
* Dias de ejecucion 		: N/A 							Horario: N/A
*                              MODIFICACIONES
*------------------------------------------------------------------------------
* Autor 					: N/A
* Compania 					: N/A
* Proyecto 					: N/A 							Fecha: N/A
* Modificacion 				: N/A
*------------------------------------------------------------------------------
*/
package mx.gob.segob.nsjp.service.objeto.impl.transform;

import mx.gob.segob.nsjp.dto.objeto.ObjetoEstudioDTO;
import mx.gob.segob.nsjp.model.ObjetoEstudio;
import mx.gob.segob.nsjp.service.usuario.impl.transformer.ValorTransformer;

/**
 * Describir el objetivo de la clase con punto al final.
 * @version 1.0
 * @author EdgarTE
 *
 */
public class ObjetoEstudioTransformer {
	
	/**
	 * M&eacute;todo que lleva a cabo la transformaci&oacute;n de un ObjetoEstudio de tipo entidad, a un
	 * ObjetoEstudio de tipo DTO.
	 * @param objetoEstudio - La entidad de la cual se va a llevar a cabo su transformación a un DTO.
	 * @return dto - El dto con la informaci&oacute;n de la entidad una vez transformado.
	 */
	public static ObjetoEstudioDTO transformar(ObjetoEstudio objetoEstudio){
		ObjetoEstudioDTO dto = null;
		if (objetoEstudio != null){
			dto = new ObjetoEstudioDTO();
			dto.setObjetoEstudioId(objetoEstudio.getObjetoEstudioId());
			dto.setEstudioPericial(ValorTransformer.transformar(objetoEstudio.getEstudioPericial()));
			dto.setTipoObjeto(ValorTransformer.transformar(objetoEstudio.getTipoObjeto()));
		}
		return dto;
	}
	
	/**
	 * M&eacute;todo que lleva a cabo la transformaci&oacute;n de un ObjetoEstudio de tipo DTO, a un
	 * ObjetoEstudio de tipo entidad. 
	 * @param dto - El DTO de ObjetoEstudio el cual se va a transformar a una entidad.
	 * @return entity - La entidad de objetoEstudio que se va a persistir en la base de datos.
	 */
	public static ObjetoEstudio transformar(ObjetoEstudioDTO dto){
		ObjetoEstudio entity = null;
		if (dto != null){
			entity = new ObjetoEstudio();
			entity.setObjetoEstudioId(dto.getObjetoEstudioId());
			entity.setEstudioPericial(ValorTransformer.transformar(dto.getEstudioPericial()));
			entity.setTipoObjeto(ValorTransformer.transformar(dto.getTipoObjeto()));
		}
		return entity;
	}

}
