/**
* Nombre del Programa : TelefoniaWSDTO.java                                    
* Autor                            : GustavoBP                                              
* Compania                    : Ultrasist                                                
* Proyecto                      : NSJP                    Fecha: 24/08/2011 
* Marca de cambio        : N/A                                                     
* Descripcion General    : DTO de intercambio entre sistemas para transportar los datos b�sicos de una Telefonia.                    
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
 * DTO de intercambio entre sistemas para transportar los datos b�sicos de una Telefonia.
 * 
 * @author GustavoBP
 * @version 1.0
 */
public class TelefoniaWSDTO extends ObjetoWSDTO {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 2001975580279432772L;
	private Long cantidad;
    private Long marca;
    private Long tipoTelefono;
    private String modelo;
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
	 * M�todo de acceso al campo marca.
	 * @return El valor del campo marca
	 */
	public Long getMarca() {
		return marca;
	}
	/**
	 * Asigna el valor al campo marca.
	 * @param marca el valor marca a asignar
	 */
	public void setMarca(Long marca) {
		this.marca = marca;
	}
	/**
	 * M�todo de acceso al campo tipoTelefono.
	 * @return El valor del campo tipoTelefono
	 */
	public Long getTipoTelefono() {
		return tipoTelefono;
	}
	/**
	 * Asigna el valor al campo tipoTelefono.
	 * @param tipoTelefono el valor tipoTelefono a asignar
	 */
	public void setTipoTelefono(Long tipoTelefono) {
		this.tipoTelefono = tipoTelefono;
	}
	/**
	 * M�todo de acceso al campo modelo.
	 * @return El valor del campo modelo
	 */
	public String getModelo() {
		return modelo;
	}
	/**
	 * Asigna el valor al campo modelo.
	 * @param modelo el valor modelo a asignar
	 */
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}


}
