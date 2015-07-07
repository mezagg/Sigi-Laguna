/**
* Nombre del Programa : ArmaWSDTO.java                                    
* Autor                            : GustavoBP                                              
* Compania                    : Ultrasist                                                
* Proyecto                      : NSJP                    Fecha: 24/08/2011 
* Marca de cambio        : N/A                                                     
* Descripcion General    : DTO de intercambio entre sistemas para transportar los datos b�sicos de una Arma.                       
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
 * DTO de intercambio entre sistemas para transportar los datos b�sicos de una Arma.
 * @author GustavoBP
 * @version 1.0
 */
public class ArmaWSDTO extends ObjetoWSDTO{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -4049364006676369651L;
	private String matricula;
    private String calibre;
    private Long marca;
    private Long tipoArma;
    private String modelo;
    private String valor;
    private Long catalogoPadre;
	/**
	 * M�todo de acceso al campo matricula.
	 * @return El valor del campo matricula
	 */
	public String getMatricula() {
		return matricula;
	}
	/**
	 * Asigna el valor al campo matricula.
	 * @param matricula el valor matricula a asignar
	 */
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	/**
	 * M�todo de acceso al campo calibre.
	 * @return El valor del campo calibre
	 */
	public String getCalibre() {
		return calibre;
	}
	/**
	 * Asigna el valor al campo calibre.
	 * @param calibre el valor calibre a asignar
	 */
	public void setCalibre(String calibre) {
		this.calibre = calibre;
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
	 * M�todo de acceso al campo tipoArma.
	 * @return El valor del campo tipoArma
	 */
	public Long getTipoArma() {
		return tipoArma;
	}
	/**
	 * Asigna el valor al campo tipoArma.
	 * @param tipoArma el valor tipoArma a asignar
	 */
	public void setTipoArma(Long tipoArma) {
		this.tipoArma = tipoArma;
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
	/**
	 * @return the valor
	 */
	public String getValor() {
		return valor;
	}
	/**
	 * @param valor the valor to set
	 */
	public void setValor(String valor) {
		this.valor = valor;
	}
	/**
	 * @return the catalogoPadre
	 */
	public Long getCatalogoPadre() {
		return catalogoPadre;
	}
	/**
	 * @param catalogoPadre the catalogoPadre to set
	 */
	public void setCatalogoPadre(Long catalogoPadre) {
		this.catalogoPadre = catalogoPadre;
	}
	
}
