/**
 * 
 */
package mx.gob.segob.nsjp.comun.enums.remisiones;

/**
 * @author AntonioBV
 *
 */
public enum CatTipoRemision {
	
	CONDICIONAL(1L,	"Condicional"),
	DEFINITIVA(2L,	"Definitiva	Definitiva"),
	REPARACION_DEL_DANIO(3L,	"Reparaci�n del Da�o"),
	REMISION_PARCIAL_DE_LA_PENACONDICIONAL(4L,	"Remisi�n Parcial De La Pena"),
	MULTA(5L, "Multa");
	
	
	private Long id;
	private String descripcion;
	
	private CatTipoRemision(Long id) {
		this.id = id;
	}
	
	private CatTipoRemision(Long id, String descripcion) {
		this.id = id;
		this.descripcion = descripcion;
	}

	/**
	 * M&eacute;todo de acceso al campo id.
	 * @return El valor del campo id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * M&eacute;todo de acceso al campo descripcion.
	 * @return El valor del campo descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}
	
}
