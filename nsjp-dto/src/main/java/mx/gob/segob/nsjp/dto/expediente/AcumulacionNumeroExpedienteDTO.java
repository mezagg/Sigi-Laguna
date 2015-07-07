/**
 * 
 */
package mx.gob.segob.nsjp.dto.expediente;

import java.util.Date;



/**
 * @author JorgeFO
 *
 */
public class AcumulacionNumeroExpedienteDTO {
	
	private Long acumulacionId;
	private ExpedienteDTO expedienteDTOPrincipal;
	private ExpedienteDTO numeroExpedienteAcumuladoId;
	private Date fecha;
	private Long claveFuncionario;
	private String nombreFuncionario;
	/**
	 * @return the acumulacionId
	 */
	public Long getAcumulacionId() {
		return acumulacionId;
	}
	/**
	 * @param acumulacionId the acumulacionId to set
	 */
	public void setAcumulacionId(Long acumulacionId) {
		this.acumulacionId = acumulacionId;
	}
	/**
	 * @return the expedienteDTOPrincipal
	 */
	public ExpedienteDTO getExpedienteDTOPrincipal() {
		return expedienteDTOPrincipal;
	}
	/**
	 * @param expedienteDTOPrincipal the expedienteDTOPrincipal to set
	 */
	public void setExpedienteDTOPrincipal(ExpedienteDTO expedienteDTOPrincipal) {
		this.expedienteDTOPrincipal = expedienteDTOPrincipal;
	}
	/**
	 * @return the numeroExpedienteAcumuladoId
	 */
	public ExpedienteDTO getNumeroExpedienteAcumuladoId() {
		return numeroExpedienteAcumuladoId;
	}
	/**
	 * @param numeroExpedienteAcumuladoId the numeroExpedienteAcumuladoId to set
	 */
	public void setNumeroExpedienteAcumuladoId(
			ExpedienteDTO numeroExpedienteAcumuladoId) {
		this.numeroExpedienteAcumuladoId = numeroExpedienteAcumuladoId;
	}
	/**
	 * @return the fecha
	 */
	public Date getFecha() {
		return fecha;
	}
	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	/**
	 * @return the claveFuncionario
	 */
	public Long getClaveFuncionario() {
		return claveFuncionario;
	}
	/**
	 * @param claveFuncionario the claveFuncionario to set
	 */
	public void setClaveFuncionario(Long claveFuncionario) {
		this.claveFuncionario = claveFuncionario;
	}
	public String getNombreFuncionario() {
		return nombreFuncionario;
	}
	public void setNombreFuncionario(String nombreFuncionario) {
		this.nombreFuncionario = nombreFuncionario;
	}
	
	
	

}
