/**
*
* Nombre del Programa : FiltroExpedienteDTO.java                                    
* Autor                            : Cesar Agustin                                               
* Compania                    : Ultrasist                                                
* Proyecto                      : NSJP                    Fecha: 05/04/2011 
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
* Proyecto                 :N/A                                   Fecha: N/A       
* Modificacion           :N/A                                                           
*------------------------------------------------------------------------------           
*/
package mx.gob.segob.nsjp.dto.expediente;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import mx.gob.segob.nsjp.dto.caso.FiltroCasoDTO;
import mx.gob.segob.nsjp.dto.institucion.JerarquiaOrganizacionalDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;

/**
 * @author CesarAgustin
 * @version 1.0
 *
 */
public class FiltroExpedienteDTO extends FiltroCasoDTO {
	
	private static final long serialVersionUID = 6250765994676470357L;
	
	private Long identificadorEvidencia;
	private String nombreEvidencia;
	private List<String> palabrasClave = new ArrayList<String>();
	private Long anio;
	private Long idArea;
	private Long idActividad;
	private Long idFuncionario;
	private Long idDiscriminante;
	private UsuarioDTO usuario;
	private Boolean expedientesAsignados;
	private Long estatus;
	private Long estatusMenuCoorJAR;
	private List<Long> estatusNumeroExpediente;
	//Objeto utilizado para la consulta de numeros de expedientes de atpenal en unidad de investigacion y opcional para policia minister
	private Long filtroEspecificoDeAreaRolActual;
	//Objeto identificador de la busqueda de CoordinadorAT
	private Long tipoBusquedaCoorAT;
	//Objeto identificador de la fecha de busqueda de CoordinadorAT
	private Date fechaBusqueda;
	//Objeto para identificar la agencia  de busqueda de CoordinadorAT
	private Long idAgencia;
	//Atributo que indica busqueda de coordinador amp genaral para omitir cat discriminante
	private Long esCordinadorGeneralAMP;
	//Permite filtrar los expedientes, descartando aquellos que tengan el id tipo Actividad complemento
	private Long idTipoActividadComplemento;
	//Permite filtrar los expedientes,en el facilitador segun si son remitidos desde UI o ATPenal 
	private Long idJerarquiaRemitos;

	private Boolean esPropio;
	private Boolean esAsignado;


	private Set<JerarquiaOrganizacionalDTO> jerarquiaOrgSubordinadas;
	/**
	 * @param identificadorEvidencia
	 * @param palabrasClave
	 */
	public FiltroExpedienteDTO(Long identificadorEvidencia, List<String> palabrasClave) {
		super();		
		this.identificadorEvidencia = identificadorEvidencia;
		this.palabrasClave = palabrasClave;		
	}
	
	public FiltroExpedienteDTO() {
		super();		
	}

	/**
	 * Obtener el discriminante Id
	 * @return
	 */
	public Long getIdDiscriminante() {
		return idDiscriminante;
	}

	/**
	 * Asignar el discriminante id
	 * @param idDiscriminante
	 */
	public void setIdDiscriminante(Long idDiscriminante) {
		this.idDiscriminante = idDiscriminante;
	}
	
	/**
	 * M�todo utilizado para saber si un expediente ha sido asignado
	 * @return
	 */
	public Boolean getExpedientesAsignados() {
		return expedientesAsignados;
	}

	/**
	 * Recuperaci�n del par�metro expediente asignado
	 * @param expedienteAsignado
	 */
	public void setExpedientesAsignados(Boolean expedientesAsignados) {
		this.expedientesAsignados = expedientesAsignados;
	}

	/**
	 * @return the identificadorEvidencia
	 */
	public Long getIdentificadorEvidencia() {
		return identificadorEvidencia;
	}
	
	/**
	 * @param identificadorEvidencia the identificadorEvidencia to set
	 */
	public void setIdentificadorEvidencia(Long identificadorEvidencia) {
		this.identificadorEvidencia = identificadorEvidencia;
	}
	
	/**
	 * @return the palabrasClave
	 */
	public List<String> getPalabrasClave() {
		return palabrasClave;
	}
	
	/**
	 * @param palabrasClave the palabrasClave to set
	 */
	public void setPalabrasClave(List<String> palabrasClave) {
		this.palabrasClave = palabrasClave;
	}

	/**
	 * @return El valor del campo nombreEvidencia
	 */
	public String getNombreEvidencia() {
		return nombreEvidencia;
	}

	/**
	 * Asigna el valor al campo nombreEvidencia.
	 * @param nombreEvidencia el valor nombreEvidencia a asignar
	 */
	public void setNombreEvidencia(String nombreEvidencia) {
		this.nombreEvidencia = nombreEvidencia;
	}

	/**
	 * @return
	 */
	public Long getAnio() {
		return anio;
	}
 
	/**
	 * @param anio
	 */
	public void setAnio(Long anio) {
		this.anio = anio;
	}

	/**
	 * @return
	 */
	public Long getIdActividad() {
		return idActividad;
	}

	/**
	 * @param idActividad
	 */
	public void setIdActividad(Long idActividad) {
		this.idActividad = idActividad;
	}

	/**
	 * @return
	 */
	public Long getIdArea() {
		return idArea;
	}

	/**
	 * @param idArea
	 */
	public void setIdArea(Long idArea) {
		this.idArea = idArea;
	}

	public UsuarioDTO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioDTO usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return the idFuncionario
	 */
	public Long getIdFuncionario() {
		return idFuncionario;
	}

	/**
	 * @param idFuncionario the idFuncionario to set
	 */
	public void setIdFuncionario(Long idFuncionario) {
		this.idFuncionario = idFuncionario;
	}

	/**
	 * @return the estatus
	 */
	public Long getEstatus() {
		return estatus;
	}

	/**
	 * @param estatus the estatus to set
	 */
	public void setEstatus(Long estatus) {
		this.estatus = estatus;
	}

	/**
	 * @return the estatusMenuCoorJAR
	 */
	public Long getestatusMenuCoorJAR() {
		return estatusMenuCoorJAR;
	}

	/**
	 * @param estatusMenuCoorJAR the estatusMenuCoorJAR to set
	 */
	public void setestatusMenuCoorJAR(Long estatusMenuCoorJAR) {
		this.estatusMenuCoorJAR = estatusMenuCoorJAR;
	}

	/**
	 * M�todo de acceso al campo jerarquiaOrgSubordinadas.
	 * @return El valor del campo jerarquiaOrgSubordinadas
	 */
	public Set<JerarquiaOrganizacionalDTO> getJerarquiaOrgSubordinadas() {
		return jerarquiaOrgSubordinadas;
	}

	/**
	 * Asigna el valor al campo jerarquiaOrgSubordinadas.
	 * @param jerarquiaOrgSubordinadas el valor jerarquiaOrgSubordinadas a asignar
	 */
	public void setJerarquiaOrgSubordinadas(
			Set<JerarquiaOrganizacionalDTO> jerarquiaOrgSubordinadas) {
		this.jerarquiaOrgSubordinadas = jerarquiaOrgSubordinadas;
	}

	/**
	 * M�todo de acceso al campo estatusNumeroExpediente.
	 * @return El valor del campo estatusNumeroExpediente
	 */
	public List<Long> getEstatusNumeroExpediente() {
		return estatusNumeroExpediente;
	}

	/**
	 * Asigna el valor al campo estatusNumeroExpediente.
	 * @param estatusNumeroExpediente el valor estatusNumeroExpediente a asignar
	 */
	public void setEstatusNumeroExpediente(List<Long> estatusNumeroExpediente) {
		this.estatusNumeroExpediente = estatusNumeroExpediente;
	}

	/**
	 * @return the filtroEspecificoDeAreaRolActual
	 */
	public Long getFiltroEspecificoDeAreaRolActual() {
		return filtroEspecificoDeAreaRolActual;
	}

	/**
	 * @param filtroEspecificoDeAreaRolActual the filtroEspecificoDeAreaRolActual to set
	 */
	public void setFiltroEspecificoDeAreaRolActual(
			Long filtroEspecificoDeAreaRolActual) {
		this.filtroEspecificoDeAreaRolActual = filtroEspecificoDeAreaRolActual;
	}

	/**
	 * @return the tipoBusquedaCoorAT
	 */
	public Long getTipoBusquedaCoorAT() {
		return tipoBusquedaCoorAT;
	}

	/**
	 * @param tipoBusquedaCoorAT the tipoBusquedaCoorAT to set
	 */
	public void setTipoBusquedaCoorAT(Long tipoBusquedaCoorAT) {
		this.tipoBusquedaCoorAT = tipoBusquedaCoorAT;
	}

	/**
	 * @return the fechaBusqueda
	 */
	public Date getFechaBusqueda() {
		return fechaBusqueda;
	}

	/**
	 * @param fechaBusqueda the fechaBusqueda to set
	 */
	public void setFechaBusqueda(Date fechaBusqueda) {
		this.fechaBusqueda = fechaBusqueda;
	}

	/**
	 * @return the idAgencia
	 */
	public Long getIdAgencia() {
		return idAgencia;
	}

	/**
	 * @param idAgencia the idAgencia to set
	 */
	public void setIdAgencia(Long idAgencia) {
		this.idAgencia = idAgencia;
	}

	/**
	 * @return the esCordinadorGeneralAMP
	 */
	public Long getEsCordinadorGeneralAMP() {
		return esCordinadorGeneralAMP;
	}

	/**
	 * @param esCordinadorGeneralAMP the esCordinadorGeneralAMP to set
	 */
	public void setEsCordinadorGeneralAMP(Long esCordinadorGeneralAMP) {
		this.esCordinadorGeneralAMP = esCordinadorGeneralAMP;
	}

	/**
	 * @return the idTipoActividadComplemento
	 */
	public Long getIdTipoActividadComplemento() {
		return idTipoActividadComplemento;
	}

	/**
	 * @param idTipoActividadComplemento the idTipoActividadComplemento to set
	 */
	public void setIdTipoActividadComplemento(Long idTipoActividadComplemento) {
		this.idTipoActividadComplemento = idTipoActividadComplemento;
	}

	public void setIdJerarquiaRemitos(Long idJerarquiaRemitos) {
		this.idJerarquiaRemitos = idJerarquiaRemitos;
	}

	public Long getIdJerarquiaRemitos() {
		return idJerarquiaRemitos;
	}

	public Boolean getEsPropio() {
		return esPropio;
	}

	public void setEsPropio(Boolean esPropio) {
		this.esPropio = esPropio;
	}

	public Boolean getEsAsignado() {
		return esAsignado;
	}

	public void setEsAsignado(Boolean esAsignado) {
		this.esAsignado = esAsignado;
	}
}
