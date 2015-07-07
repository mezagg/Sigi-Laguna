/**
 * Nombre del Programa : ExpedienteDTO.java
 * Autor                            : rgama
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 21 Ene 2013
 * Marca de cambio        : N/A
 * Descripcion General    : DTO para la transferencia de parametros de BitacoraEstatusNumExpediente entre la vista y servicios.
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
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.dto.institucion.JerarquiaOrganizacionalDTO;


/**
 * DTO para la transferencia de parametros de BitacoraEstatusNumExpediente entre la vista y
 * servicios.
 * 
 * @version 1.0
 * @author rgama
 * 
 */
public class BitacoraEstatusNumExpedienteDTO extends GenericDTO {

    /**
	 * 
	 */
    private static final long serialVersionUID = 76730324788081260L;
    
	private Long bitacoraEstatusNumExpedienteId;
	
	private Date fechaCreacion;
	private JerarquiaOrganizacionalDTO jerarquiaOrganizacional;
	private ExpedienteDTO expediente;
    private FuncionarioDTO funcionario;
    private ValorDTO estatus;    
    private ValorDTO tipoActividad;
    private ValorDTO tipoDocumento;
	/**
	 * @return the bitacoraEstatusNumExpedienteId
	 */
	public Long getBitacoraEstatusNumExpedienteId() {
		return bitacoraEstatusNumExpedienteId;
	}
	/**
	 * @param bitacoraEstatusNumExpedienteId the bitacoraEstatusNumExpedienteId to set
	 */
	public void setBitacoraEstatusNumExpedienteId(
			Long bitacoraEstatusNumExpedienteId) {
		this.bitacoraEstatusNumExpedienteId = bitacoraEstatusNumExpedienteId;
	}
	/**
	 * @return the fechaCreacion
	 */
	public Date getFechaCreacion() {
		return fechaCreacion;
	}
	/**
	 * @param fechaCreacion the fechaCreacion to set
	 */
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
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
	 * @return the estatus
	 */
	public ValorDTO getEstatus() {
		return estatus;
	}
	/**
	 * @param estatus the estatus to set
	 */
	public void setEstatus(ValorDTO estatus) {
		this.estatus = estatus;
	}
	/**
	 * @return the tipoActividad
	 */
	public ValorDTO getTipoActividad() {
		return tipoActividad;
	}
	/**
	 * @param tipoActividad the tipoActividad to set
	 */
	public void setTipoActividad(ValorDTO tipoActividad) {
		this.tipoActividad = tipoActividad;
	}
	/**
	 * @return the tipoDocumento
	 */
	public ValorDTO getTipoDocumento() {
		return tipoDocumento;
	}
	/**
	 * @param tipoDocumento the tipoDocumento to set
	 */
	public void setTipoDocumento(ValorDTO tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

}
