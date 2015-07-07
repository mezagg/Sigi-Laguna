/**
* Nombre del Programa : SolicitudTranscripcionWSDTO.java
* Autor                            : Emigdio
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 30/06/2011
* Marca de cambio        : N/A
* Descripcion General    : Describir el objetivo de la clase de manera breve
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
package mx.gob.segob.nsjp.dto.solicitud;

import java.util.ArrayList;
import java.util.List;

import mx.gob.segob.nsjp.dto.audiencia.AudienciaWSDTO;
import mx.gob.segob.nsjp.dto.expediente.DelitoWSDTO;
import mx.gob.segob.nsjp.dto.funcionarioexterno.FuncionarioExternoWSDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoWSDTO;

/**
 * Objeto de transferencia entre sistemas para transporte de los datos de una solicitud de 
 * defensor
 * @version 1.0
 * @author Emigdio Hernández
 *
 */
public class SolicitudDefensorWSDTO extends SolicitudWSDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5669218825246523198L;
	@Deprecated
	private String folioElemento;
	@Deprecated
	private String nombreImputado;
	@Deprecated
	private String apellidoPaternoImputado;
	@Deprecated
	private String apellidoMaternoImputado;
	@Deprecated
	private Boolean esDetenido;
	private AudienciaWSDTO audiencia;
	private Long tipoAsesoria;	
	private FuncionarioExternoWSDTO defensorAsignado;
	
	
	
	//Valores que se agregan a la solicitud para ser enviados
	private List<DelitoWSDTO> delitos =  new ArrayList<DelitoWSDTO>();
	private List<InvolucradoWSDTO> involucrados = new ArrayList<InvolucradoWSDTO>();
	
	/**
	 * Establece el valor de la propiedad folioElemento
	 * @param folioElemento valo folioElemento a almacenar
	 */
	@Deprecated
	public void setFolioElemento(String folioElemento) {
		this.folioElemento = folioElemento;
	}
	

	/**
	 * Regresa el valor de la propiedad folioElemento
	 * @return the folioElemento
	 */
	@Deprecated
	public String getFolioElemento() {
		return folioElemento;
	}
	

	/**
	 * Método de acceso al campo nombreImputado.
	 * @return El valor del campo nombreImputado
	 */
	@Deprecated
	public String getNombreImputado() {
		return nombreImputado;
	}

	/**
	 * Asigna el valor al campo nombreImputado.
	 * @param nombreImputado el valor nombreImputado a asignar
	 */
	@Deprecated
	public void setNombreImputado(String nombreImputado) {
		this.nombreImputado = nombreImputado;
	}

	/**
	 * Regresa el valor de la propiedad apellidoPaternoImputado
	 * @return the apellidoPaternoImputado
	 */
	@Deprecated
	public String getApellidoPaternoImputado() {
		return apellidoPaternoImputado;
	}
	
	/**
	 * Establece el valor de la propiedad apellidoPaternoImputado
	 * @param apellidoPaternoImputado valo apellidoPaternoImputado a almacenar
	 */
	@Deprecated
	public void setApellidoPaternoImputado(String apellidoPaternoImputado) {
		this.apellidoPaternoImputado = apellidoPaternoImputado;
	}
	
	/**
	 * Regresa el valor de la propiedad apellidoMaternoImputado
	 * @return the apellidoMaternoImputado
	 */
	@Deprecated
	public String getApellidoMaternoImputado() {
		return apellidoMaternoImputado;
	}

	/**
	 * Establece el valor de la propiedad apellidoMaternoImputado
	 * @param apellidoMaternoImputado valo apellidoMaternoImputado a almacenar
	 */
	@Deprecated
	public void setApellidoMaternoImputado(String apellidoMaternoImputado) {
		this.apellidoMaternoImputado = apellidoMaternoImputado;
	}

	/**
	 * Regresa el valor de la propiedad esDetenido
	 * @return the esDetenido
	 */
	@Deprecated
	public Boolean getEsDetenido() {
		return esDetenido;
	}
	
	/**
	 * Establece el valor de la propiedad esDetenido
	 * @param esDetenido valo esDetenido a almacenar
	 */
	@Deprecated
	public void setEsDetenido(Boolean esDetenido) {
		this.esDetenido = esDetenido;
	}

	/**
	 * @return the audiencia
	 */
	public AudienciaWSDTO getAudiencia() {
		return audiencia;
	}


	/**
	 * @param audiencia the audiencia to set
	 */
	public void setAudiencia(AudienciaWSDTO audiencia) {
		this.audiencia = audiencia;
	}


	/**
	 * @return the tipoAsesoria
	 */
	public Long getTipoAsesoria() {
		return tipoAsesoria;
	}


	/**
	 * @param tipoAsesoria the tipoAsesoria to set
	 */
	public void setTipoAsesoria(Long tipoAsesoria) {
		this.tipoAsesoria = tipoAsesoria;
	}


	/**
	 * Método de acceso al campo delitos.
	 * @return El valor del campo delitos
	 */
	public List<DelitoWSDTO> getDelitos() {
		return delitos;
	}

	/**
	 * Asigna el valor al campo delitos.
	 * @param delitos el valor delitos a asignar
	 */
	public void setDelitos(List<DelitoWSDTO> delitos) {
		this.delitos = delitos;
	}


	/**
	 * @return the involucrados
	 */
	public List<InvolucradoWSDTO> getInvolucrados() {
		return involucrados;
	}


	/**
	 * @param involucrados the involucrados to set
	 */
	public void setInvolucrados(List<InvolucradoWSDTO> involucrados) {
		this.involucrados = involucrados;
	}


	/**
	 * @return el defensorAsignado al cual se le asign&oacute;
	 * la solicitud de defensor
	 */
	public FuncionarioExternoWSDTO getDefensorAsignado() {
		return defensorAsignado;
	}


	/**
	 * @param defensorAsignado el cual atender&aacute;
	 * la solicitud de defensor
	 */
	public void setDefensorAsignado(FuncionarioExternoWSDTO defensorAsignado) {
		this.defensorAsignado = defensorAsignado;
	}
}
