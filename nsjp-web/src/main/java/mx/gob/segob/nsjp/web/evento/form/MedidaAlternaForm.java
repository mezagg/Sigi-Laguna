/**
 * 
 */
package mx.gob.segob.nsjp.web.evento.form;

import mx.gob.segob.nsjp.web.base.form.GenericForm;

/**
 * @author Ultrasist
 *
 */
public class MedidaAlternaForm extends GenericForm {
	private static final long serialVersionUID = 1L;
	
	private String fechaInicio;
	private String fechaFin;
	private String seguimiento;
	private String medidaCautelar;
	private Long medidaCautelarId;
	private Long periodicidad;
	private boolean activo;
	
	/**
	 * M�todo de acceso al campo fechaInicio.
	 * @return El valor del campo fechaInicio
	 */
	public String getFechaInicio() {
		return fechaInicio;
	}
	/**
	 * Asigna el valor al campo fechaInicio.
	 * @param fechaInicio el valor fechaInicio a asignar
	 */
	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	/**
	 * M�todo de acceso al campo fechaFin.
	 * @return El valor del campo fechaFin
	 */
	public String getFechaFin() {
		return fechaFin;
	}
	/**
	 * Asigna el valor al campo fechaFin.
	 * @param fechaFin el valor fechaFin a asignar
	 */
	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}
	/**
	 * M�todo de acceso al campo seguimiento.
	 * @return El valor del campo seguimiento
	 */
	public String getSeguimiento() {
		return seguimiento;
	}
	/**
	 * Asigna el valor al campo seguimiento.
	 * @param seguimiento el valor seguimiento a asignar
	 */
	public void setSeguimiento(String seguimiento) {
		this.seguimiento = seguimiento;
	}
	/**
	 * M�todo de acceso al campo medidaCautelar.
	 * @return El valor del campo medidaCautelar
	 */
	public String getMedidaCautelar() {
		return medidaCautelar;
	}
	/**
	 * Asigna el valor al campo medidaCautelar.
	 * @param medidaCautelar el valor medidaCautelar a asignar
	 */
	public void setMedidaCautelar(String medidaCautelar) {
		this.medidaCautelar = medidaCautelar;
	}
	/**
	 * M�todo de acceso al campo medidaCautelarId.
	 * @return El valor del campo medidaCautelarId
	 */
	public Long getMedidaCautelarId() {
		return medidaCautelarId;
	}
	/**
	 * Asigna el valor al campo medidaCautelarId.
	 * @param medidaCautelarId el valor medidaCautelarId a asignar
	 */
	public void setMedidaCautelarId(Long medidaCautelarId) {
		this.medidaCautelarId = medidaCautelarId;
	}
	/**
	 * M�todo de acceso al campo activo.
	 * @return El valor del campo activo
	 */
	public boolean isActivo() {
		return activo;
	}
	/**
	 * Asigna el valor al campo activo.
	 * @param activo el valor activo a asignar
	 */
	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	/**
	 * M�todo de acceso al campo periodicidad.
	 * @return El valor del campo periodicidad
	 */
	public Long getPeriodicidad() {
		return periodicidad;
	}
	/**
	 * Asigna el valor al campo periodicidad.
	 * @param periodicidad el valor periodicidad a asignar
	 */
	public void setPeriodicidad(Long periodicidad) {
		this.periodicidad = periodicidad;
	}

}
