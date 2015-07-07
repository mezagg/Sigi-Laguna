/**
* Nombre del Programa : CatTipoNivelAcademicoDTO.java
* Autor                            : AntonioBV
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 08/02/2012
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
package mx.gob.segob.nsjp.dto.niveles;

import mx.gob.segob.nsjp.dto.base.GenericDTO;

/**
 * Describir el objetivo de la clase con punto al final.
 * @version 1.0
 * @author AntonioBV
 *
 */
public class CatTipoNivelAcademicoDTO extends GenericDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5419291868057601392L;
	private Long catTipoNivelAcademicoId;
	private String descripcion;
	/**
	 * Método de acceso al campo catTipoNivelAcademicoId.
	 * @return El valor del campo catTipoNivelAcademicoId
	 */
	public Long getCatTipoNivelAcademicoId() {
		return catTipoNivelAcademicoId;
	}
	/**
	 * Asigna el valor al campo catTipoNivelAcademicoId.
	 * @param catTipoNivelAcademicoId el valor catTipoNivelAcademicoId a asignar
	 */
	public void setCatTipoNivelAcademicoId(Long catTipoNivelAcademicoId) {
		this.catTipoNivelAcademicoId = catTipoNivelAcademicoId;
	}
	/**
	 * Método de acceso al campo descripcion.
	 * @return El valor del campo descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}
	/**
	 * Asigna el valor al campo descripcion.
	 * @param descripcion el valor descripcion a asignar
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
