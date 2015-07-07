/**
* Nombre del Programa 		: FuncionarioExternoDTO.java
* Autor 					: EdgarTE
* Compania 					: Ultrasist
* Proyecto 					: NSJP 							Fecha: 13 Apr 2012
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
package mx.gob.segob.nsjp.dto.funcionarioexterno;

import java.util.ArrayList;
import java.util.List;

import mx.gob.segob.nsjp.dto.base.GenericDTO;
import mx.gob.segob.nsjp.dto.configuracion.ConfInstitucionDTO;
import mx.gob.segob.nsjp.dto.documento.NotificacionDTO;
import mx.gob.segob.nsjp.dto.usuario.RolDTO;

/**
 * Describir el objetivo de la clase con punto al final.
 * @version 1.0
 * @author EdgarTE
 *
 */
public class FuncionarioExternoDTO extends GenericDTO {


	/**
	 * 
	 */
	private static final long serialVersionUID = -8012406203193345049L;
	
	private ConfInstitucionDTO confInstitucionDTO;
	private Long funcionarioExternoId;
	private String nombre;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String area;
	private String puesto;
	private String email;
	private Long cveFuncionarioInstExt;
	private RolDTO rolDTO;
	  /**
     * Para la notificaci&oacute;n de eventos el funcionarioExterno tiene un conjunto de
     * notificaciones
     */
	private List<NotificacionDTO> notificaciones = new ArrayList<NotificacionDTO>();
	
	/**
	 * Default constructor
	 */
	public FuncionarioExternoDTO() {
		
	}
	
	/**
	 * @param funcionarioExternoId
	 */
	public FuncionarioExternoDTO(Long funcionarioExternoId) {
		super();
		this.funcionarioExternoId = funcionarioExternoId;
	}
	
	
	
	/**
	 * M&eacute;todo de acceso al campo confInstitucionDTO.
	 * @return El valor del campo confInstitucionDTO
	 */
	public ConfInstitucionDTO getConfInstitucionDTO() {
		return confInstitucionDTO;
	}
	
	/**
	 * Asigna el valor al campo confInstitucionDTO.
	 * @param confInstitucionDTO el valor confInstitucionDTO a asignar
	 */
	public void setConfInstitucionDTO(ConfInstitucionDTO confInstitucionDTO) {
		this.confInstitucionDTO = confInstitucionDTO;
	}
	
	/**
	 * M&eacute;todo de acceso al campo funcionarioExternoId.
	 * @return El valor del campo funcionarioExternoId
	 */
	public Long getFuncionarioExternoId() {
		return funcionarioExternoId;
	}
	
	/**
	 * Asigna el valor al campo funcionarioExternoId.
	 * @param funcionarioExternoId el valor funcionarioExternoId a asignar
	 */
	public void setFuncionarioExternoId(Long funcionarioExternoId) {
		this.funcionarioExternoId = funcionarioExternoId;
	}
	
	/**
	 * M&eacute;todo de acceso al campo nombre.
	 * @return El valor del campo nombre
	 */
	public String getNombre() {
		return nombre;
	}
	
	/**
	 * Asigna el valor al campo nombre.
	 * @param nombre el valor nombre a asignar
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	/**
	 * M&eacute;todo de acceso al campo apellidoPaterno.
	 * @return El valor del campo apellidoPaterno
	 */
	public String getApellidoPaterno() {
		return apellidoPaterno;
	}
	
	/**
	 * Asigna el valor al campo apellidoPaterno.
	 * @param apellidoPaterno el valor apellidoPaterno a asignar
	 */
	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}
	
	/**
	 * M&eacute;todo de acceso al campo apellidoMaterno.
	 * @return El valor del campo apellidoMaterno
	 */
	public String getApellidoMaterno() {
		return apellidoMaterno;
	}
	
	/**
	 * Asigna el valor al campo apellidoMaterno.
	 * @param apellidoMaterno el valor apellidoMaterno a asignar
	 */
	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}
	
	/**
	 * M&eacute;todo de acceso al campo area.
	 * @return El valor del campo area
	 */
	public String getArea() {
		return area;
	}
	
	/**
	 * Asigna el valor al campo area.
	 * @param area el valor area a asignar
	 */
	public void setArea(String area) {
		this.area = area;
	}
	
	/**
	 * M&eacute;todo de acceso al campo puesto.
	 * @return El valor del campo puesto
	 */
	public String getPuesto() {
		return puesto;
	}
	
	/**
	 * Asigna el valor al campo puesto.
	 * @param puesto el valor puesto a asignar
	 */
	public void setPuesto(String puesto) {
		this.puesto = puesto;
	}
	
	/**
	 * M&eacute;todo de acceso al campo email.
	 * @return El valor del campo email
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * Asigna el valor al campo email.
	 * @param email el valor email a asignar
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * M&eacute;todo de acceso al campo cveFuncionarioInstExt.
	 * @return El valor del campo cveFuncionarioInstExt
	 */
	public Long getCveFuncionarioInstExt() {
		return cveFuncionarioInstExt;
	}
	
	/**
	 * Asigna el valor al campo cveFuncionarioInstExt.
	 * @param cveFuncionarioInstExt el valor cveFuncionarioInstExt a asignar
	 */
	public void setCveFuncionarioInstExt(Long cveFuncionarioInstExt) {
		this.cveFuncionarioInstExt = cveFuncionarioInstExt;
	}
	
	/**
	 * M&eacute;todo utilitario que lleva a cabo la concatenaci&oacute;n de los
	 * atributos que conforman el nombre del funcionario externo en el caso de 
	 * que dichos atributos existan.
	 * @return nombre - Cadena con el nombre completo del funcionario.
	 */
	public String getNombreCompleto(){
		StringBuilder nombre = new StringBuilder("");
		nombre.append(this.nombre+" "+apellidoPaterno);
		if (apellidoMaterno != null){
			nombre.append(" "+apellidoMaterno);
		}
		return nombre.toString();
	}
	
	/**
	 * M&eacute;todo de acceso al campo rolDTO.
	 * @return El valor del campo rolDTO
	 */
	public RolDTO getRolDTO() {
		return rolDTO;
	}

	/**
	 * Asigna el valor al campo rolDTO.
	 * @param rolDTO, el valor del rolDTO a asignar
	 */
	public void setRolDTO(RolDTO rolDTO) {
		this.rolDTO = rolDTO;
	}

	/**
	 * @return the notificaciones
	 */
	public final List<NotificacionDTO> getNotificaciones() {
		return notificaciones;
	}

	/**
	 * @param notificaciones the notificaciones to set
	 */
	public final void setNotificaciones(List<NotificacionDTO> notificaciones) {
		this.notificaciones = notificaciones;
	}
}
