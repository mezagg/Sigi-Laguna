/**
* Nombre del Programa : ObjetoWSDTO.java                                    
* Autor                            : GustavoBP                                              
* Compania                    : Ultrasist                                                
* Proyecto                      : NSJP                    Fecha: 24/08/2011 
* Marca de cambio        : N/A                                                     
* Descripcion General    : DTO de intercambio entre sistemas para transportar los datos b�sicos de un Objeto.                      
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

import mx.gob.segob.nsjp.dto.elemento.ElementoWSDTO;
import mx.gob.segob.nsjp.dto.evidencia.EvidenciaWSDTO;


/**
 * DTO de intercambio entre sistemas para transportar los datos b�sicos de un Objeto.
 * @author GustavoBP
 * @version 1.0
 */
public class ObjetoWSDTO extends ElementoWSDTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4581684582405302080L;
	private Long valorByCondicionFisicaVal; 
	private Long tipoObjeto;
	//private AlmacenDTO almacen; 
	private String descripcion;
	private Boolean esPertenencia;
	private EvidenciaWSDTO evidencia;
	private Long relacionHechoVal;
				 
    //Usada en la relaci�n de evidencias con audiencias.
	//private Date fechaRecepcion;
    /**
     * Relaci�n con la instituci�n que lo presenta a la audiencia.
     */
    private Long institucionPresenta;
    //Nombre del objeto, usado para objetos OTROS
    private String nombreObjeto;
	
    /**
     * 
     */
    public ObjetoWSDTO() {
        super();
    }
    
	/**
	 * M�todo de acceso al campo valorByCondicionFisicaVal.
	 * @return El valor del campo valorByCondicionFisicaVal
	 */
	public Long getValorByCondicionFisicaVal() {
		return valorByCondicionFisicaVal;
	}
	/**
	 * Asigna el valor al campo valorByCondicionFisicaVal.
	 * @param valorByCondicionFisicaVal el valor valorByCondicionFisicaVal a asignar
	 */
	public void setValorByCondicionFisicaVal(Long valorByCondicionFisicaVal) {
		this.valorByCondicionFisicaVal = valorByCondicionFisicaVal;
	}

	/**
	 * M�todo de acceso al campo descripcion.
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
	/**
	 * M�todo de acceso al campo esPertenencia.
	 * @return El valor del campo esPertenencia
	 */
	public boolean getEsPertenencia() {
		return esPertenencia;
	}
	/**
	 * Asigna el valor al campo esPertenencia.
	 * @param esPertenencia el valor esPertenencia a asignar
	 */
	public void setEsPertenencia(boolean esPertenencia) {
		this.esPertenencia = esPertenencia;
	}
	/**
	 * M�todo de acceso al campo tipoObjeto.
	 * @return El valor del campo tipoObjeto
	 */
	public Long getTipoObjeto() {
		return tipoObjeto;
	}
	/**
	 * Asigna el valor al campo tipoObjeto.
	 * @param tipoObjeto el valor tipoObjeto a asignar
	 */
	public void setTipoObjeto(Long tipoObjeto) {
		this.tipoObjeto = tipoObjeto;
	}
	/**
	 * @return the institucionPresenta
	 */
	public Long getInstitucionPresenta() {
		return institucionPresenta;
	}
	/**
	 * @param institucionPresenta the institucionPresenta to set
	 */
	public void setInstitucionPresenta(Long institucionPresenta) {
		this.institucionPresenta = institucionPresenta;
	}

	/**
	 * Regresa el valor de la propiedad evidencia
	 * @return the evidencia
	 */
	public EvidenciaWSDTO getEvidencia() {
		return evidencia;
	}

	/**
	 * Establece el valor de la propiedad evidencia
	 * @param evidencia valo evidencia a almacenar
	 */
	public void setEvidencia(EvidenciaWSDTO evidencia) {
		this.evidencia = evidencia;
	}

	/**
	 * Establece el valor de la propiedad esPertenencia
	 * @param esPertenencia valo esPertenencia a almacenar
	 */
	public void setEsPertenencia(Boolean esPertenencia) {
		this.esPertenencia = esPertenencia;
	}

	/**
	 * @return the relacionHechoVal
	 */
	public Long getRelacionHechoVal() {
		return relacionHechoVal;
	}

	/**
	 * @param relacionHechoVal the relacionHechoVal to set
	 */
	public void setRelacionHechoVal(Long relacionHechoVal) {
		this.relacionHechoVal = relacionHechoVal;
	}

	/**
	 * @return the nombreObjeto
	 */
	public String getNombreObjeto() {
		return nombreObjeto;
	}

	/**
	 * @param nombreObjeto the nombreObjeto to set
	 */
	public void setNombreObjeto(String nombreObjeto) {
		this.nombreObjeto = nombreObjeto;
	}
}
