/**
* Nombre del Programa	: ObjetoPericialWSDTO.java                                    
* Autor					: AlejandroGA                                              
* Compania              : Ultrasist                                                
* Proyecto              : NSJP                    Fecha: 11/01/2013 
* Marca de cambio       : N/A                                                     
* Descripcion General   : WSDTO de intercambio entre sistemas para transportar los datos basicos de un ObjetoPericial.                     
* Programa Dependiente  : N/A                                                      
* Programa Subsecuente	: N/A                                                      
* Cond. de ejecucion    : N/A                                                      
* Dias de ejecucion     : N/A                             Horario: N/A       
*                              MODIFICACIONES                                       
*------------------------------------------------------------------------------           
* Autor                 : N/A                                                           
* Compania              : N/A                                                           
* Proyecto              : N/A                                   Fecha: N/A       
* Modificacion          : N/A                                                           
*------------------------------------------------------------------------------           
*/
package mx.gob.segob.nsjp.dto.objeto;


/**
 * WSDTO de intercambio entre sistemas para transportar los datos basicos de un ObjetoPericial.
 * @author AlejandroGA
 * @version 1.0
 */
public class ObjetoPericialWSDTO extends ObjetoWSDTO {

	/**
	 * Autogenerado version serial
	 */
	private static final long serialVersionUID = 5319556430187386341L;
	
	private Long categoriaIndicioId;
	private Long indicioId;
	
	/**
	 * @return the categoriaIndicioId
	 */
	public Long getCategoriaIndicioId() {
		return categoriaIndicioId;
	}
	/**
	 * @param categoriaIndicioId the categoriaIndicioId to set
	 */
	public void setCategoriaIndicioId(Long categoriaIndicioId) {
		this.categoriaIndicioId = categoriaIndicioId;
	}
	/**
	 * @return the indicioId
	 */
	public Long getIndicioId() {
		return indicioId;
	}
	/**
	 * @param indicioId the indicioId to set
	 */
	public void setIndicioId(Long indicioId) {
		this.indicioId = indicioId;
	}

}
