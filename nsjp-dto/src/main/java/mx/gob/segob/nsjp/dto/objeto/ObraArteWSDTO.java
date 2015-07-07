/**
* Nombre del Programa : ObraArteWSDTO.java                                    
* Autor                            : GustavoBP                                              
* Compania                    : Ultrasist                                                
* Proyecto                      : NSJP                    Fecha: 24/08/2011 
* Marca de cambio        : N/A                                                     
* Descripcion General    : DTO de intercambio entre sistemas para transportar los datos b�sicos de una ObraArte.                    
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

/**
 * DTO de intercambio entre sistemas para transportar los datos b�sicos de una ObraArte.
 * 
 * @author GustavoBP
 * @version 1.0
 */
public class ObraArteWSDTO extends ObjetoWSDTO {
    /**
	 * 
	 */
	private static final long serialVersionUID = 3056339556237622667L;
	private Long cantidad;
    private Long tipoObraArte;
	/**
	 * M�todo de acceso al campo cantidad.
	 * @return El valor del campo cantidad
	 */
	public Long getCantidad() {
		return cantidad;
	}
	/**
	 * Asigna el valor al campo cantidad.
	 * @param cantidad el valor cantidad a asignar
	 */
	public void setCantidad(Long cantidad) {
		this.cantidad = cantidad;
	}
	/**
	 * M�todo de acceso al campo tipoObraArte.
	 * @return El valor del campo tipoObraArte
	 */
	public Long getTipoObraArte() {
		return tipoObraArte;
	}
	/**
	 * Asigna el valor al campo tipoObraArte.
	 * @param tipoObraArte el valor tipoObraArte a asignar
	 */
	public void setTipoObraArte(Long tipoObraArte) {
		this.tipoObraArte = tipoObraArte;
	}

}
