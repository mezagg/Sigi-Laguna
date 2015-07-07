/**
* Nombre del Programa : MedidaAlterna.java
* Autor                            : GustavoBP
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 05/08/2011
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
package mx.gob.segob.nsjp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * Clase que contiene las operaciones y los atributos 
 * para realizar la funci�n asociada a las medidas alternas 
 * que le son impuestas a los involucrados como sentencia.
 * @version 1.0
 * @author GustavoBP
 *
 */
@Entity
@Table(name = "MedidaAlterna")
@Inheritance(strategy = InheritanceType.JOINED)
@PrimaryKeyJoinColumn(name = "MedidaAlterna_id")
public class MedidaAlterna extends Medida {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7197004648140557535L;
	/**A�os de duraci�n de la sentencia**/
	private Short anios;
	/**Meses de duraci�n de la sentencia**/
	private Short meses;
	
	
	public MedidaAlterna(){
		super();
	}
	
	@Column(name = "iAnios", precision = 4, scale = 0)
	public Short getAnios() {
		return anios;
	}

	public void setAnios(Short anios) {
		this.anios = anios;
	}

	@Column(name = "iMeses", precision = 4, scale = 0)
	public Short getMeses() {
		return meses;
	}

	public void setMeses(Short meses) {
		this.meses = meses;
	}


}