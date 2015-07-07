/**
* Nombre del Programa 		: ObjetoEstudio.java
* Autor 					: EdgarTE
* Compania 					: Ultrasist
* Proyecto 					: NSJP 							Fecha: 11 Sep 2012
* Marca de cambio 			: N/A
* Descripcion General 		: Describir el objetivo de la clase de manera breve
* Programa Dependiente 		: N/A
* Programa Subsecuente 		: N/A
* Cond. de ejecucion 		: N/A
* Dias de ejecucion 		: N/A 							Horario: N/A
*                              MODIFICACIONES
*------------------------------------------------------------------------------
* Autor 					: N/A
* Compania 					: N/A
* Proyecto 					: N/A 							Fecha: N/A
* Modificacion 				: N/A
*------------------------------------------------------------------------------
*/
package mx.gob.segob.nsjp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Describir el objetivo de la clase con punto al final.
 * @version 1.0
 * @author EdgarTE
 *
 */
@Entity
@Table(name = "ObjetoEstudio")
public class ObjetoEstudio implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7296053977021187219L;
	
	private Long objetoEstudioId;
	private Valor tipoObjeto;
	private Valor estudioPericial;
	
	/**
	 * M&eacute;todo de acceso al campo objetoEstudioId.
	 * @return El valor del campo objetoEstudioId
	 */
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ObjetoEstudio_id", unique = true, nullable = false, precision = 18, scale = 0)
	public Long getObjetoEstudioId() {
		return objetoEstudioId;
	}
	
	/**
	 * Asigna el valor al campo objetoEstudioId.
	 * @param objetoEstudioId el valor objetoEstudioId a asignar
	 */
	public void setObjetoEstudioId(Long objetoEstudioId) {
		this.objetoEstudioId = objetoEstudioId;
	}
	
	/**
	 * M&eacute;todo de acceso al campo estudioPericial.
	 * @return El valor del campo estudioPericial
	 */
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EstudioPericial_val", nullable = false)
	public Valor getEstudioPericial() {
		return estudioPericial;
	}
	
	/**
	 * Asigna el valor al campo estudioPericial.
	 * @param estudioPericial el valor estudioPericial a asignar
	 */
	public void setEstudioPericial(Valor estudioPericial) {
		this.estudioPericial = estudioPericial;
	}
	
	/**
	 * M&eacute;todo de acceso al campo tipoObjeto.
	 * @return El valor del campo tipoObjeto
	 */
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TipoObjeto_val", nullable = false)
	public Valor getTipoObjeto() {
		return tipoObjeto;
	}
	
	/**
	 * Asigna el valor al campo tipoObjeto.
	 * @param tipoObjeto el valor tipoObjeto a asignar
	 */
	public void setTipoObjeto(Valor tipoObjeto) {
		this.tipoObjeto = tipoObjeto;
	}

}
