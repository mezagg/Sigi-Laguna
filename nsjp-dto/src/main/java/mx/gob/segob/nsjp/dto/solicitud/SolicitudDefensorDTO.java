/**
* Nombre del Programa : SolicitudDefensorDTO.java
* Autor                            : cesarAgustin
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 12 May 2011
* Marca de cambio        : N/A
* Descripcion General    : DTO para el traslado de atributos de SolicitudDefensor, entre la vista y los servicios
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

import mx.gob.segob.nsjp.comun.enums.relacion.Relaciones;
import mx.gob.segob.nsjp.dto.audiencia.AudienciaDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.documento.AvisoDesignacionDTO;
import mx.gob.segob.nsjp.dto.expediente.DelitoDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;


/**
 * DTO para el traslado de atributos de SolicitudDefensor, entre la vista y los servicios.
 * @version 1.0
 * @author cesarAgustin
 *
 */
public class SolicitudDefensorDTO extends SolicitudDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 518806894367084054L;
	
    private FuncionarioDTO funcionario;
    
    @Deprecated
    private Relaciones relacionConImputado;
    @Deprecated
    private String detenido;
    @Deprecated
    private String nombreDetenido;
    @Deprecated
    private String apellidPaternoDetenido;
    @Deprecated
    private String apellidoMaternoDetenido;
    @Deprecated
    private boolean esDetenido;
    
    private AudienciaDTO audiencia;
    private List<DelitoDTO> delitos = new ArrayList<DelitoDTO>();
    private List<InvolucradoDTO> involucrados = new ArrayList<InvolucradoDTO>();

    @Deprecated
    private String folioElementoDetenido;
    private ValorDTO tipoAsesoria;
    private AvisoDesignacionDTO avisoDesignacion;
    
	public SolicitudDefensorDTO() {
		super();		
	}

	public SolicitudDefensorDTO(Long documentoId) {
		super();
		setDocumentoId(documentoId);
	}
	
	/**
	 * Método de acceso al campo funcionario.
	 * @return El valor del campo funcionario
	 */
	public FuncionarioDTO getFuncionario() {
		return funcionario;
	}
	/**
	 * Asigna el valor al campo funcionario.
	 * @param funcionario el valor funcionario a asignar
	 */
	public void setFuncionario(FuncionarioDTO funcionario) {
		this.funcionario = funcionario;
	}

	/**
	 * Método de acceso al campo relacionConImputado.
	 * @return El valor del campo relacionConImputado
	 */
	@Deprecated
	public Relaciones getRelacionConImputado() {
		return relacionConImputado;
	}

	/**
	 * Asigna el valor al campo relacionConImputado.
	 * @param relacionConImputado el valor relacionConImputado a asignar
	 */
	@Deprecated
	public void setRelacionConImputado(Relaciones relacionConImputado) {
		this.relacionConImputado = relacionConImputado;
	}

	@Deprecated
	public void setDetenido(String detenido) {
		this.detenido = detenido;
	}

	@Deprecated
	public String getDetenido() {
		return detenido;
	}

	/**
	 * Regresa el valor de la propiedad nombreDetenido
	 * @return the nombreDetenido
	 */
	@Deprecated
	public String getNombreDetenido() {
		return nombreDetenido;
	}

	/**
	 * Establece el valor de la propiedad nombreDetenido
	 * @param nombreDetenido valo nombreDetenido a almacenar
	 */
	@Deprecated
	public void setNombreDetenido(String nombreDetenido) {
		this.nombreDetenido = nombreDetenido;
	}

	/**
	 * Regresa el valor de la propiedad apellidPaternoDetenido
	 * @return the apellidPaternoDetenido
	 */
	@Deprecated
	public String getApellidPaternoDetenido() {
		return apellidPaternoDetenido;
	}

	/**
	 * Establece el valor de la propiedad apellidPaternoDetenido
	 * @param apellidPaternoDetenido valo apellidPaternoDetenido a almacenar
	 */
	@Deprecated
	public void setApellidPaternoDetenido(String apellidPaternoDetenido) {
		this.apellidPaternoDetenido = apellidPaternoDetenido;
	}

	/**
	 * Regresa el valor de la propiedad apellidoMaternoDetenido
	 * @return the apellidoMaternoDetenido
	 */
	@Deprecated
	public String getApellidoMaternoDetenido() {
		return apellidoMaternoDetenido;
	}

	/**
	 * Establece el valor de la propiedad apellidoMaternoDetenido
	 * @param apellidoMaternoDetenido valo apellidoMaternoDetenido a almacenar
	 */
	@Deprecated
	public void setApellidoMaternoDetenido(String apellidoMaternoDetenido) {
		this.apellidoMaternoDetenido = apellidoMaternoDetenido;
	}

	/**
	 * Regresa el valor de la propiedad esDetenido
	 * @return the esDetenido
	 */
	@Deprecated
	public boolean isEsDetenido() {
		return esDetenido;
	}

	/**
	 * Establece el valor de la propiedad esDetenido
	 * @param esDetenido valo esDetenido a almacenar
	 */
	@Deprecated
	public void setEsDetenido(boolean esDetenido) {
		this.esDetenido = esDetenido;
	}

	public void setAudiencia(AudienciaDTO audiencia) {
		this.audiencia = audiencia;
	}

	
	public AudienciaDTO getAudiencia() {
		return audiencia;
	}

	/**
	 * Método de acceso al campo delitos.
	 * @return El valor del campo delitos
	 */
	public List<DelitoDTO> getDelitos() {
		return delitos;
	}

	/**
	 * Asigna el valor al campo delitos.
	 * @param delitos el valor delitos a asignar
	 */
	public void setDelitos(List<DelitoDTO> delitos) {
		this.delitos = delitos;
	}

	/**
	 * Establece el valor de la propiedad folioElementoDetenido
	 * @param folioElementoDetenido valo folioElementoDetenido a almacenar
	 */
	@Deprecated
	public void setFolioElementoDetenido(String folioElementoDetenido) {
		this.folioElementoDetenido = folioElementoDetenido;
	}

	/**
	 * Regresa el valor de la propiedad folioElementoDetenido
	 * @return the folioElementoDetenido
	 */
	@Deprecated
	public String getFolioElementoDetenido() {
		return folioElementoDetenido;
	}

    /**
     * Método de acceso al campo tipoAsesoria.
     * @return El valor del campo tipoAsesoria
     */
    public ValorDTO getTipoAsesoria() {
        return tipoAsesoria;
    }

    /**
     * Asigna el valor al campo tipoAsesoria.
     * @param tipoAsesoria el valor tipoAsesoria a asignar
     */
    public void setTipoAsesoria(ValorDTO tipoAsesoria) {
        this.tipoAsesoria = tipoAsesoria;
    }


	/**
	 * @return Metodo de acceso a los involucrados de la solicitud
	 */
	public List<InvolucradoDTO> getInvolucrados() {
		return involucrados;
	}

	/**
	 * @param Asigna
	 *            los involucrados a la solicitud
	 */
	public void setInvolucrados(List<InvolucradoDTO> involucrados) {
		this.involucrados = involucrados;
	}

	/**
	 * @return the avisoDesignacion
	 */
	public AvisoDesignacionDTO getAvisoDesignacion() {
		return avisoDesignacion;
	}

	/**
	 * @param avisoDesignacion the avisoDesignacion to set
	 */
	public void setAvisoDesignacion(AvisoDesignacionDTO avisoDesignacion) {
		this.avisoDesignacion = avisoDesignacion;
	}
}
