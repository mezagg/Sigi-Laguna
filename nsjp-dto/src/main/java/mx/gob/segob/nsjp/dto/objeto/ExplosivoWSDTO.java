/**
* Nombre del Programa : ExplosivoWSDTO.java                                    
* Autor                            : GustavoBP                                              
* Compania                    : Ultrasist                                                
* Proyecto                      : NSJP                    Fecha: 24/08/2011 
* Marca de cambio        : N/A                                                     
* Descripcion General    : DTO de intercambio entre sistemas para transportar los datos b�sicos de un Explosivo.                     
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
 * DTO de intercambio entre sistemas para transportar los datos b�sicos de un Explosivo.
 * @author GustavoBP
 * @version 1.0
 */
public class ExplosivoWSDTO extends ObjetoWSDTO {
    /**
	 * 
	 */
	private static final long serialVersionUID = 8725532039499844834L;
	private Long cantidad;
    private Long tipoExplosivo;
    private Long unidadMedida;
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
	 * M�todo de acceso al campo tipoExplosivo.
	 * @return El valor del campo tipoExplosivo
	 */
	public Long getTipoExplosivo() {
		return tipoExplosivo;
	}
	/**
	 * Asigna el valor al campo tipoExplosivo.
	 * @param tipoExplosivo el valor tipoExplosivo a asignar
	 */
	public void setTipoExplosivo(Long tipoExplosivo) {
		this.tipoExplosivo = tipoExplosivo;
	}
	/**
	 * M�todo de acceso al campo unidadMedida.
	 * @return El valor del campo unidadMedida
	 */
	public Long getUnidadMedida() {
		return unidadMedida;
	}
	/**
	 * Asigna el valor al campo unidadMedida.
	 * @param unidadMedida el valor unidadMedida a asignar
	 */
	public void setUnidadMedida(Long unidadMedida) {
		this.unidadMedida = unidadMedida;
	}


}
