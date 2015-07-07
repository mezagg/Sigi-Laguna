/**
* Nombre del Programa : CatUIEspecializada.java
* Autor                            : AdrianECH
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 05/03/2012
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
package mx.gob.segob.nsjp.dto.catalogo;

import mx.gob.segob.nsjp.dto.base.GenericDTO;

/**
 * Describir el objetivo de la clase con punto al final.
 * Fecha: 05/03/2012
 * @version 1.0
 * @author AdrianECH
 * 
 */
public class CatUIEspecializadaDTO extends GenericDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -82075361856458920L;
	private Long catUIEId;
    private String claveUIE;
    private String nombreUIE;
    private String acronimo;
    
    public CatUIEspecializadaDTO(){
    	super();
    }
    
    
    
	public CatUIEspecializadaDTO(Long catUIEId) {
		super();
		this.catUIEId = catUIEId;
	}



	public CatUIEspecializadaDTO(Long catUIEId, String claveUIE,
			String nombreUIE, String acronimo ) {
		super();
		this.catUIEId = catUIEId;
		this.claveUIE = claveUIE;
		this.nombreUIE = nombreUIE;
		this.acronimo = acronimo;
	}
	/**
	 * M�todo de acceso al campo catUIEId.
	 * @return El valor del campo catUIEId
	 */
	public Long getCatUIEId() {
		return catUIEId;
	}
	/**
	 * M�todo de acceso al campo claveUIE.
	 * @return El valor del campo claveUIE
	 */
	public String getClaveUIE() {
		return claveUIE;
	}
	/**
	 * M�todo de acceso al campo nombreUIE.
	 * @return El valor del campo nombreUIE
	 */
	public String getNombreUIE() {
		return nombreUIE;
	}
	/**
	 * Asigna el valor al campo catUIEId.
	 * @param catUIEId el valor catUIEId a asignar
	 */
	public void setCatUIEId(Long catUIEId) {
		this.catUIEId = catUIEId;
	}
	/**
	 * Asigna el valor al campo claveUIE.
	 * @param claveUIE el valor claveUIE a asignar
	 */
	public void setClaveUIE(String claveUIE) {
		this.claveUIE = claveUIE;
	}
	/**
	 * Asigna el valor al campo nombreUIE.
	 * @param nombreUIE el valor nombreUIE a asignar
	 */
	public void setNombreUIE(String nombreUIE) {
		this.nombreUIE = nombreUIE;
	}



	/**
	 * @return the acronimo
	 */
	public String getAcronimo() {
		return acronimo;
	}



	/**
	 * @param acronimo the acronimo to set
	 */
	public void setAcronimo(String acronimo) {
		this.acronimo = acronimo;
	}

}
