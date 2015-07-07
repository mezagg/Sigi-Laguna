/**
 * Nombre del Programa : BitacoraPermisoExpedienteDTO.java
 * Autor                            : cesarAgustin
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 8 Apr 2011
 * Marca de cambio        : N/A
 * Descripcion General    : DTO para la transferencia de parametros de Expediente entre la vista y servicios.
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
package mx.gob.segob.nsjp.dto.expediente;

import java.util.Date;

import mx.gob.segob.nsjp.dto.base.GenericDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.dto.institucion.JerarquiaOrganizacionalDTO;

/**
 * DTO para la transferencia de parametros de Expediente entre la vista y
 * servicios.
 * 
 * @version 1.0
 * @author cesarAgustin
 * 
 */
public class BitacoraPermisoExpedienteDTO extends GenericDTO {
	
	private static final long serialVersionUID = -4096224374843194395L;
    private FuncionarioDTO funcionario;
    private Long bitacoraPermisoExpediente;
    private ExpedienteDTO expediente;
    private JerarquiaOrganizacionalDTO jerarquiaOrganizacional;
    private Date fechaLimite;
    private Date fechaMovimiento;
    private Boolean esEscritura;
    private Boolean esActivo;
	/**
	 * @return the funcionario
	 */
	public FuncionarioDTO getFuncionario() {
		return funcionario;
	}
	/**
	 * @param funcionario the funcionario to set
	 */
	public void setFuncionario(FuncionarioDTO funcionario) {
		this.funcionario = funcionario;
	}
	/**
	 * @return the bitacoraPermisoExpediente
	 */
	public Long getBitacoraPermisoExpediente() {
		return bitacoraPermisoExpediente;
	}
	/**
	 * @param bitacoraPermisoExpediente the bitacoraPermisoExpediente to set
	 */
	public void setBitacoraPermisoExpediente(Long bitacoraPermisoExpediente) {
		this.bitacoraPermisoExpediente = bitacoraPermisoExpediente;
	}
		/**
	 * @return the jerarquiaOrganizacional
	 */
	public JerarquiaOrganizacionalDTO getJerarquiaOrganizacional() {
		return jerarquiaOrganizacional;
	}
	/**
	 * @param jerarquiaOrganizacional the jerarquiaOrganizacional to set
	 */
	public void setJerarquiaOrganizacional(
			JerarquiaOrganizacionalDTO jerarquiaOrganizacional) {
		this.jerarquiaOrganizacional = jerarquiaOrganizacional;
	}
	/**
	 * @return the fechaLimite
	 */
	public Date getFechaLimite() {
		return fechaLimite;
	}
	/**
	 * @param fechaLimite the fechaLimite to set
	 */
	public void setFechaLimite(Date fechaLimite) {
		this.fechaLimite = fechaLimite;
	}
	/**
	 * @return the fechaMovimiento
	 */
	public Date getFechaMovimiento() {
		return fechaMovimiento;
	}
	/**
	 * @param fechaMovimiento the fechaMovimiento to set
	 */
	public void setFechaMovimiento(Date fechaMovimiento) {
		this.fechaMovimiento = fechaMovimiento;
	}
	/**
	 * @return the esEscritura
	 */
	public Boolean getEsEscritura() {
		return esEscritura;
	}
	/**
	 * @param esEscritura the esEscritura to set
	 */
	public void setEsEscritura(Boolean esEscritura) {
		this.esEscritura = esEscritura;
	}
	/**
	 * @return the esActivo
	 */
	public Boolean getEsActivo() {
		return esActivo;
	}
	/**
	 * @param esActivo the esActivo to set
	 */
	public void setEsActivo(Boolean esActivo) {
		this.esActivo = esActivo;
	}
	/**
	 * @return the expediente
	 */
	public ExpedienteDTO getExpediente() {
		return expediente;
	}
	/**
	 * @param expediente the expediente to set
	 */
	public void setExpediente(ExpedienteDTO expediente) {
		this.expediente = expediente;
	}
	
}
