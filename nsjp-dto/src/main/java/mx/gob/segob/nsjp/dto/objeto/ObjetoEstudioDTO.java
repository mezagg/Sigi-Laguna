/**
* Nombre del Programa 		: ObjetoEstudioDTO.java
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
package mx.gob.segob.nsjp.dto.objeto;

import mx.gob.segob.nsjp.dto.base.GenericDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;

/**
 * Describir el objetivo de la clase con punto al final.
 * @version 1.0
 * @author EdgarTE
 *
 */
public class ObjetoEstudioDTO extends GenericDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8764405278148028598L;
	
	private Long objetoEstudioId;
	private ValorDTO tipoObjeto;
	private ValorDTO estudioPericial;
	
	/**
	 * M&eacute;todo de acceso al campo objetoEstudioId.
	 * @return El valor del campo objetoEstudioId
	 */
	public Long getObjetoEstudioId() {
		return objetoEstudioId;
	}

	/**
	 * Asigna el valor al campo objetoEstudioId.
	 * @param objetoEstudioId el valor objetoEstudioId a asignar
	 */
	public void setObjetoEstudioId(Long objetoEstudioId) {
		this.objetoEstudioId = objetoEstudioId;
	}

	/**
	 * M&eacute;todo de acceso al campo tipoObjeto.
	 * @return El valor del campo tipoObjeto
	 */
	public ValorDTO getTipoObjeto() {
		return tipoObjeto;
	}
	
	/**
	 * Asigna el valor al campo tipoObjeto.
	 * @param tipoObjeto el valor tipoObjeto a asignar
	 */
	public void setTipoObjeto(ValorDTO tipoObjeto) {
		this.tipoObjeto = tipoObjeto;
	}
	
	/**
	 * M&eacute;todo de acceso al campo estudioPericial.
	 * @return El valor del campo estudioPericial
	 */
	public ValorDTO getEstudioPericial() {
		return estudioPericial;
	}
	
	/**
	 * Asigna el valor al campo estudioPericial.
	 * @param estudioPericial el valor estudioPericial a asignar
	 */
	public void setEstudioPericial(ValorDTO estudioPericial) {
		this.estudioPericial = estudioPericial;
	}
}
