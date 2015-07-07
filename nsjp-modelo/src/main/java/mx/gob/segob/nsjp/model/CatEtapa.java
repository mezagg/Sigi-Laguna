/**
 * Nombre del Programa : CatEtapa.java
 * Autor                            : GustavoBP
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 21/01/2013
 * Marca de cambio        : N/A
 * Descripcion General    : Entida de mapeo del catalogo de Etapas de Expediente e Involucrado. Asociado con las entidades de NumeroExpediente e Involucrado.
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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Entida de mapeo del catalogo de Etapas de Expediente e Involucrado Asociado
 * con las entidades de NumeroExpediente e Involucrado.
 * 
 * @author GustavoBP
 */
@Entity
@Table(name = "CatEtapa")
public class CatEtapa implements java.io.Serializable {

	private static final long serialVersionUID = -8173436005632374874L;

	private Long catEtapaId;
	private Valor catEtapaValor;
	private CatEtapa catEtapaPadre;
	private Boolean esEtapaExpediente;
	private Short ordenSecuencia;
	private ConfInstitucion institucionOrigen;

	// Constructors

	public CatEtapa() {
	}
	
	/**
	 * 
	 * @param catEtapaId
	 */
	public CatEtapa(Long catEtapaId) {
		this.catEtapaId = catEtapaId;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CatEtapa_id", unique = true, nullable = false, precision = 18, scale = 0)
	public Long getCatEtapaId() {
		return catEtapaId;
	}

	public void setCatEtapaId(Long catEtapaId) {
		this.catEtapaId = catEtapaId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CatEtapa_val")
	public Valor getCatEtapaValor() {
		return catEtapaValor;
	}

	public void setCatEtapaValor(Valor catEtapa) {
		this.catEtapaValor = catEtapa;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CatEtapaPadre_id", nullable = true)
	public CatEtapa getCatEtapaPadre() {
		return catEtapaPadre;
	}

	public void setCatEtapaPadre(CatEtapa catEtapaPadre) {
		this.catEtapaPadre = catEtapaPadre;
	}

	@Column(name = "bEsEtapaExpediente", precision = 1, scale = 0)
	public Boolean getEsEtapaExpediente() {
		return esEtapaExpediente;
	}

	public void setEsEtapaExpediente(Boolean esEtapaExpediente) {
		this.esEtapaExpediente = esEtapaExpediente;
	}

	@Column(name = "iOrdenSecuencia", precision = 4, scale = 0, nullable = true)
	public Short getOrdenSecuencia() {
		return ordenSecuencia;
	}

	public void setOrdenSecuencia(Short ordenSecuencia) {
		this.ordenSecuencia = ordenSecuencia;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ConfInstitucion_id", nullable = true)
	public ConfInstitucion getInstitucionOrigen() {
		return institucionOrigen;
	}

	public void setInstitucionOrigen(ConfInstitucion institucionOrigen) {
		this.institucionOrigen = institucionOrigen;
	}
}
