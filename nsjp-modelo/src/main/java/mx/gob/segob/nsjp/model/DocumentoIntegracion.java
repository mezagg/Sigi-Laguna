/**
* Nombre del Programa 		: DocumentoIntegracion.java
* Autor 					: EdgarAT
* Compania 					: Ultrasist
* Proyecto 					: NSJP 								Fecha: 26/11/2012
* Marca de cambio 			: N/A
* Descripcion General 		: Describir el objetivo de la clase de manera breve
* Programa Dependiente 		: N/A
* Programa Subsecuente 		: N/A
* Cond. de ejecucion 		: N/A
* Dias de ejecucion 		: N/A 								Horario: N/A
*                              MODIFICACIONES
*------------------------------------------------------------------------------
* Autor 					: N/A
* Compania 					: N/A
* Proyecto 					: N/A 								Fecha: N/A
* Modificacion 				: N/A
*------------------------------------------------------------------------------
*/
package mx.gob.segob.nsjp.model;

import java.io.Serializable;

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
 * @author EdgarAT
 *
 */
@Entity
@Table(name = "DocumentoIntegracion")
public class DocumentoIntegracion implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8890098675035867299L;

	private Long documentoIntegracionId;
	private Valor tipoDocumento;
	private Boolean obligatorio;
	
	/**
	 * M&eacute;todo de acceso al campo documentoIntegracionId.
	 * @return El valor del campo documentoIntegracionId
	 */
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "DocumentoIntegracion_id", unique = true, nullable = false, precision = 18, scale = 0)
	public Long getDocumentoIntegracionId() {
		return documentoIntegracionId;
	}
	
	/**
	 * Asigna el valor al campo documentoIntegracionId.
	 * @param documentoIntegracionId el valor documentoIntegracionId a asignar
	 */
	public void setDocumentoIntegracionId(Long documentoIntegracionId) {
		this.documentoIntegracionId = documentoIntegracionId;
	}
	
	/**
	 * M&eacute;todo de acceso al campo tipoDocumento.
	 * @return El valor del campo tipoDocumento
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TipoDocumento_val", nullable = false)
	public Valor getTipoDocumento() {
		return tipoDocumento;
	}
	
	/**
	 * Asigna el valor al campo tipoDocumento.
	 * @param tipoDocumento el valor tipoDocumento a asignar
	 */
	public void setTipoDocumento(Valor tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	
	/**
	 * M&eacute;todo de acceso al campo obligatorio.
	 * @return El valor del campo obligatorio
	 */
	@Column(name = "bObligatorio", nullable = false, precision = 1, scale = 0)
	public Boolean getObligatorio() {
		return obligatorio;
	}
	
	/**
	 * Asigna el valor al campo obligatorio.
	 * @param obligatorio el valor obligatorio a asignar
	 */
	public void setObligatorio(Boolean obligatorio) {
		this.obligatorio = obligatorio;
	}
	
}
