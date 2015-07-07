/**
* Nombre del Programa : ConfTipoActividadOrigenDestinoDTO.java
* Autor                            : AntonioBV
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 19/09/2012
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
package mx.gob.segob.nsjp.dto.actividad;

import mx.gob.segob.nsjp.dto.base.GenericDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;

/**
 * Describir el objetivo de la clase con punto al final.
 * @version 1.0
 * @author AntonioBV
 *
 */
public class ConfTipoActividadOrigenDestinoDTO extends GenericDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2381057321177136300L;

	private Long confTipoActividadOrigenDestinoId;
	private ValorDTO tipoActividadOrigenVal;
	private ValorDTO tipoActividadDestinoVal;
	/**
	 * Método de acceso al campo confTipoActividadOrigenDestinoId.
	 * @return El valor del campo confTipoActividadOrigenDestinoId
	 */
	public Long getConfTipoActividadOrigenDestinoId() {
		return confTipoActividadOrigenDestinoId;
	}
	/**
	 * Asigna el valor al campo confTipoActividadOrigenDestinoId.
	 * @param confTipoActividadOrigenDestinoId el valor confTipoActividadOrigenDestinoId a asignar
	 */
	public void setConfTipoActividadOrigenDestinoId(
			Long confTipoActividadOrigenDestinoId) {
		this.confTipoActividadOrigenDestinoId = confTipoActividadOrigenDestinoId;
	}
	/**
	 * Método de acceso al campo tipoActividadOrigenVal.
	 * @return El valor del campo tipoActividadOrigenVal
	 */
	public ValorDTO getTipoActividadOrigenVal() {
		return tipoActividadOrigenVal;
	}
	/**
	 * Asigna el valor al campo tipoActividadOrigenVal.
	 * @param tipoActividadOrigenVal el valor tipoActividadOrigenVal a asignar
	 */
	public void setTipoActividadOrigenVal(ValorDTO tipoActividadOrigenVal) {
		this.tipoActividadOrigenVal = tipoActividadOrigenVal;
	}
	/**
	 * Método de acceso al campo tipoActividadDestinoVal.
	 * @return El valor del campo tipoActividadDestinoVal
	 */
	public ValorDTO getTipoActividadDestinoVal() {
		return tipoActividadDestinoVal;
	}
	/**
	 * Asigna el valor al campo tipoActividadDestinoVal.
	 * @param tipoActividadDestinoVal el valor tipoActividadDestinoVal a asignar
	 */
	public void setTipoActividadDestinoVal(ValorDTO tipoActividadDestinoVal) {
		this.tipoActividadDestinoVal = tipoActividadDestinoVal;
	}

	
	
}
