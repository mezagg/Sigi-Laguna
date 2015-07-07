/**
* Nombre del Programa : FuncionDTO.java
* Autor                            : cesarAgustin
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 1 Aug 2011
* Marca de cambio        : N/A
* Descripcion General    : Objeto de transferencia para las Funciones que puede tener un Usuario
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
package mx.gob.segob.nsjp.dto.usuario;

import mx.gob.segob.nsjp.dto.base.GenericDTO;

/**
 * Objeto de transferencia para las Funciones que puede tener un Usuario.
 * @version 1.0
 * @author cesarAgustin
 *
 */
public class FuncionDTO extends GenericDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3213964753483919762L;

	private Long funcionId;
    private String nombreFuncion;
    private String descripcionFuncion;

    /**
     * Constructor por default
     */
    public FuncionDTO (){
    	
    }
    
    /**
     * Constructor m�nimo
     */
    public FuncionDTO (Long funcionId){
    	this.funcionId=funcionId;
    }
    /**
     * Constructor de String
     * @param nombreFuncion
     */
    public FuncionDTO (String nombreFuncion){
    	this.nombreFuncion=nombreFuncion;
    }
    
    /**
     * Constructor de String
     * @param nombreFuncion
     */
    public FuncionDTO (String nombreFuncion, String descripcionFuncion){
    	this.nombreFuncion=nombreFuncion;
    	this.descripcionFuncion=descripcionFuncion;
    }
	/**
	 * M�todo de acceso al campo funcionId.
	 * @return El valor del campo funcionId
	 */
	public Long getFuncionId() {
		return funcionId;
	}
	/**
	 * Asigna el valor al campo funcionId.
	 * @param funcionId el valor funcionId a asignar
	 */
	public void setFuncionId(Long funcionId) {
		this.funcionId = funcionId;
	}
	/**
	 * M�todo de acceso al campo nombreFuncion.
	 * @return El valor del campo nombreFuncion
	 */
	public String getNombreFuncion() {
		return nombreFuncion;
	}
	/**
	 * Asigna el valor al campo nombreFuncion.
	 * @param nombreFuncion el valor nombreFuncion a asignar
	 */
	public void setNombreFuncion(String nombreFuncion) {
		this.nombreFuncion = nombreFuncion;
	}
	/**
	 * M�todo de acceso al campo descripcionFuncion.
	 * @return El valor del campo descripcionFuncion
	 */
	public String getDescripcionFuncion() {
		return descripcionFuncion;
	}
	/**
	 * Asigna el valor al campo descripcionFuncion.
	 * @param descripcionFuncion el valor descripcionFuncion a asignar
	 */
	public void setDescripcionFuncion(String descripcionFuncion) {
		this.descripcionFuncion = descripcionFuncion;
	}
}
