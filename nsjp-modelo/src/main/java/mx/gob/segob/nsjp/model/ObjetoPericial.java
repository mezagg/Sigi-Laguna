/**
* Nombre del Programa : ObjetoPericial.java
* Autor                            : GustavoBP
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 12/09/2012
* Marca de cambio        : N/A
* Descripcion General    : Entidad que permite registrar y consultar los accesos a los Objetos del Tipo Pericial.
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

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * Entidad que permite registrar y consultar los accesos a los Objetos de Tipo Pericial
 * 
 * @version 1.0
 * @author GustavoBP
 *
 */
@Entity
@Table(name = "ObjetoPericial")
@Inheritance(strategy = InheritanceType.JOINED)
@PrimaryKeyJoinColumn(name = "ObjetoPericial_id")
public class ObjetoPericial extends Objeto {

	private static final long serialVersionUID = -4114895247854877569L;
	private Valor categoriaIndicioVal;
	private Valor indicioVal;

	public ObjetoPericial(){
		super();
	}
	
	public ObjetoPericial(Valor categoriaIndicioVal, Valor indicioVal) {
		super();
		this.categoriaIndicioVal = categoriaIndicioVal;
		this.indicioVal = indicioVal;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "categoriaIndicio_val")
	public Valor getCategoriaIndicioVal() {
		return categoriaIndicioVal;
	}

	public void setCategoriaIndicioVal(Valor categoriaIndicioVal) {
		this.categoriaIndicioVal = categoriaIndicioVal;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "indicio_val")
	public Valor getIndicioVal() {
		return indicioVal;
	}


	public void setIndicioVal(Valor indicioVal) {
		this.indicioVal = indicioVal;
	} 
}
