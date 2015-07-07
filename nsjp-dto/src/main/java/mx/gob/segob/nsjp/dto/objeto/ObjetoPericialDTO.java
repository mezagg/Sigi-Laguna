/**
* Nombre del Programa : ObjetoPericialDTO.java                                    
* Autor                            : GustavoBP                                              
* Compania                    : Ultrasist                                                
* Proyecto                      : NSJP                    Fecha: 12/09/2012 
* Marca de cambio        : N/A                                                     
* Descripcion General    : Clase para la transferencia de datos entre la vista y el negocio, del Objeto Pericial                      
* Programa Dependiente  :N/A                                                      
* Programa Subsecuente :N/A                                                      
* Cond. de ejecucion        :N/A                                                      
* Dias de ejecucion          :N/A                             Horario: N/A       
*                              MODIFICACIONES                                       
*------------------------------------------------------------------------------           
* Autor                       :N/A                                                           
* Compania               :N/A                                                           
* Proyecto                 :N/A                                   Fecha: N/A       
* Modificacion           :N/A                                                           
*------------------------------------------------------------------------------           
*/
package mx.gob.segob.nsjp.dto.objeto;

import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;

/**
 * Clase para la transferencia de datos entre la vista y el negocio, del Objeto Pericial
 * @author GustavoBP
 *
 */
public class ObjetoPericialDTO extends ObjetoDTO {

	private static final long serialVersionUID = 3880021076975633490L;
	
	private ValorDTO categoriaIndicioVal;
	private ValorDTO indicioVal;
	
	public ObjetoPericialDTO() {
		super();
	}
	
	/**
	 * @param categoriaIndicioVal
	 * @param indicioVal
	 */
	public ObjetoPericialDTO(ValorDTO categoriaIndicioVal, ValorDTO indicioVal) {
		super();
		this.categoriaIndicioVal = categoriaIndicioVal;
		this.indicioVal = indicioVal;
	}

	/**
	 * @return the categoriaIndicioVal
	 */
	public ValorDTO getCategoriaIndicioVal() {
		return categoriaIndicioVal;
	}

	/**
	 * @param categoriaIndicioVal the categoriaIndicioVal to set
	 */
	public void setCategoriaIndicioVal(ValorDTO categoriaIndicioVal) {
		this.categoriaIndicioVal = categoriaIndicioVal;
	}

	/**
	 * @return the indicioVal
	 */
	public ValorDTO getIndicioVal() {
		return indicioVal;
	}

	/**
	 * @param indicioVal the indicioVal to set
	 */
	public void setIndicioVal(ValorDTO indicioVal) {
		this.indicioVal = indicioVal;
	}
}
