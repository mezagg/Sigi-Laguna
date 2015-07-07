/**
 * Nombre del Programa : SolicitudAudienciaBasicoDTO.java
 * Autor               : Emigdio
 * Compania            : Ultrasist
 * Proyecto            : NSJP                    Fecha: 24/06/2011
 * Marca de cambio     : N/A
 * Descripcion General : Describir el objetivo de la clase de manera breve
 * Programa Dependiente: N/A
 * Programa Subsecuente: N/A
 * Cond. de ejecucion  : N/A
 * Dias de ejecucion   : N/A                             Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor               : N/A
 * Compania            : N/A
 * Proyecto            : N/A                                 Fecha: N/A
 * Modificacion        : N/A
 *------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.dto.solicitud;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mx.gob.segob.nsjp.dto.audiencia.AudienciaWSDTO;
import mx.gob.segob.nsjp.dto.base.GenericWSDTO;
import mx.gob.segob.nsjp.dto.caso.CasoWSDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioWSDTO;
import mx.gob.segob.nsjp.dto.funcionarioexterno.FuncionarioExternoWSDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoWSDTO;
import mx.gob.segob.nsjp.dto.objeto.ObjetoWSDTO;

/**
 * DTO sencillo para la creaci&oacute;n de una solicitud de audiencia ya sea invocado
 * de forma interna o de un &aacute;rea externa
 * 
 * @version 1.0
 * @author Emigdio Hernandez
 * 
 */
public class SolicitudAudienciaBasicoWSDTO extends GenericWSDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5329351388819611340L;
	private Long solicitudId;
    private String folioSolicitud;

    private String numeroCasoAsociado;
    private CasoWSDTO casoWSDTO;
    private Date fechaLimite;
    private Long areaSolicitanteId;
    private Long solicitanteExternoId;
    private Long distritoId;
    private Long tribunalId;
    private Long claveFuncionarioId;
    
    /**
     * @deprecated usar solicitante
     * 
     */
    private String nombreSolicitante;
    private FuncionarioWSDTO solicitante;
    private String nombreInvolucrado;
    private Long areaDestino;
    Long idFuncionarioSolicitante;


    private AudienciaWSDTO audiencia;
    /**
     * Involucrados, Datos generales, Domicilio(s), Medios de contacto de las
     * siguientes calidades: Denunciante, Probables responsables, V�ctimas,
     * Testigos
     */
    private List<InvolucradoWSDTO> involucradosDTO = new ArrayList<InvolucradoWSDTO>();
    /**
     * Objetos, sin la cadena de custodia.
     */
    private List<ObjetoWSDTO> objetosDTO = new ArrayList<ObjetoWSDTO>();
    
    
    protected List<DelitoPersonaWSDTO> relacionesDelitoPersona; 
    
    /**
     * Se requiere que el funcionario solicitante se env&iacute;e como
     * funcionario externo.
     */
    private FuncionarioExternoWSDTO funcionarioExternoSolicitante;
    
    /**
     * Utilizado para cuando se regrese la respuesta de la solicitud, 
     * se mande el funcionario destinatario a quien se el env�o la solicitud.
     * Eje: Solicitud de Carpeta de Investigaci�n
     */
    private FuncionarioExternoWSDTO funcionarioExternoDestinatario;
    
    /**
     * Regresa el valor de la propiedad folioSolicitud
     * 
     * @return the folioSolicitud
     */
    public String getFolioSolicitud() {
        return folioSolicitud;
    }

    /**
     * Establece el valor de la propiedad folioSolicitud
     * 
     * @param folioSolicitud
     *            valo folioSolicitud a almacenar
     */
    public void setFolioSolicitud(String folioSolicitud) {
        this.folioSolicitud = folioSolicitud;
    }

    /**
     * M�todo de acceso al campo numeroCasoAsociado.
     * 
     * @return El valor del campo numeroCasoAsociado
     */
    public String getNumeroCasoAsociado() {
        return numeroCasoAsociado;
    }

    /**
     * Asigna el valor al campo numeroCasoAsociado.
     * 
     * @param numeroCasoAsociado
     *            el valor numeroCasoAsociado a asignar
     */
    public void setNumeroCasoAsociado(String numeroCasoAsociado) {
        this.numeroCasoAsociado = numeroCasoAsociado;
    }

    /**
	 * @return the casoWSDTO
	 */
	public CasoWSDTO getCasoWSDTO() {
		return casoWSDTO;
	}

	/**
	 * @param casoWSDTO the casoWSDTO to set
	 */
	public void setCasoWSDTO(CasoWSDTO casoWSDTO) {
		this.casoWSDTO = casoWSDTO;
	}

	/**
     * M&eacute;todo de acceso al campo fechaLimite.
     * 
     * @return El valor del campo fechaLimite
     */
    public Date getFechaLimite() {
        return fechaLimite;
    }

    /**
     * Asigna el valor al campo fechaLimite.
     * 
     * @param fechaLimite
     *            el valor fechaLimite a asignar
     */
    public void setFechaLimite(Date fechaLimite) {
        this.fechaLimite = fechaLimite;
    }

    /**
     * M&eacute;todo de acceso al campo areaSolicitanteId.
     * 
     * @return El valor del campo areaSolicitanteId
     */
    public Long getAreaSolicitanteId() {
        return areaSolicitanteId;
    }

    /**
     * Asigna el valor al campo areaSolicitanteId.
     * 
     * @param areaSolicitanteId
     *            el valor areaSolicitanteId a asignar
     */
    public void setAreaSolicitanteId(Long areaSolicitanteId) {
        this.areaSolicitanteId = areaSolicitanteId;
    }

    /**
     * M&eacute;todo de acceso al campo solicitanteExternoId.
     * 
     * @return El valor del campo solicitanteExternoId
     */
    public Long getSolicitanteExternoId() {
        return solicitanteExternoId;
    }

    /**
     * Asigna el valor al campo solicitanteExternoId.
     * 
     * @param solicitanteExternoId
     *            el valor solicitanteExternoId a asignar
     */
    public void setSolicitanteExternoId(Long solicitanteExternoId) {
        this.solicitanteExternoId = solicitanteExternoId;
    }

    /**
     * M&eacute;todo de acceso al campo nombreSolicitante.
     * 
     * @return El valor del campo nombreSolicitante
     */
    public String getNombreSolicitante() {
        return nombreSolicitante;
    }

    /**
     * Asigna el valor al campo nombreSolicitante.
     * 
     * @param nombreSolicitante
     *            el valor nombreSolicitante a asignar
     */
    public void setNombreSolicitante(String nombreSolicitante) {
        this.nombreSolicitante = nombreSolicitante;
    }

    /**
     * M&eacute;todo de acceso al campo audiencia.
     * 
     * @return El valor del campo audiencia
     */
    public AudienciaWSDTO getAudiencia() {
        return audiencia;
    }

    /**
     * Asigna el valor al campo audiencia.
     * 
     * @param audiencia
     *            el valor audiencia a asignar
     */
    public void setAudiencia(AudienciaWSDTO audiencia) {
        this.audiencia = audiencia;
    }

    /**
     * M&eacute;todo de acceso al campo solicitudId.
     * 
     * @return El valor del campo solicitudId
     */
    public Long getSolicitudId() {
        return solicitudId;
    }

    /**
     * Asigna el valor al campo solicitudId.
     * 
     * @param solicitudId
     *            el valor solicitudId a asignar
     */
    public void setSolicitudId(Long solicitudId) {
        this.solicitudId = solicitudId;
    }

    /**
     * M&eacute;todo de acceso al campo nombreInvolucrado.
     * 
     * @return El valor del campo nombreInvolucrado
     */
    public String getNombreInvolucrado() {
        return nombreInvolucrado;
    }

    /**
     * Asigna el valor al campo nombreInvolucrado.
     * 
     * @param nombreInvolucrado
     *            el valor nombreInvolucrado a asignar
     */
    public void setNombreInvolucrado(String nombreInvolucrado) {
        this.nombreInvolucrado = nombreInvolucrado;
    }

    /**
     * M&eacute;todo de acceso al campo areaDestino.
     * 
     * @return El valor del campo areaDestino
     */
    public Long getAreaDestino() {
        return areaDestino;
    }

    /**
     * Asigna el valor al campo areaDestino.
     * 
     * @param areaDestino
     *            el valor areaDestino a asignar
     */
    public void setAreaDestino(Long areaDestino) {
        this.areaDestino = areaDestino;
    }

    /**
     * M&eacute;todo de acceso al campo involucradosDTO.
     * 
     * @return El valor del campo involucradosDTO
     */
    public List<InvolucradoWSDTO> getInvolucradosDTO() {
        return involucradosDTO;
    }

    /**
     * Asigna el valor al campo involucradosDTO.
     * 
     * @param involucradosDTO
     *            el valor involucradosDTO a asignar
     */
    public void setInvolucradosDTO(List<InvolucradoWSDTO> involucradosDTO) {
        this.involucradosDTO = involucradosDTO;
    }

    /**
     * M&eacute;todo de acceso al campo objetosDTO.
     * 
     * @return El valor del campo objetosDTO
     */
    public List<ObjetoWSDTO> getObjetosDTO() {
        return objetosDTO;
    }

    /**
     * Asigna el valor al campo objetosDTO.
     * 
     * @param objetosDTO
     *            el valor objetosDTO a asignar
     */
    public void setObjetosDTO(List<ObjetoWSDTO> objetosDTO) {
        this.objetosDTO = objetosDTO;
    }

    /**
     * M&eacute;todo de acceso al campo solicitante.
     * 
     * @return El valor del campo solicitante
     */
    public FuncionarioWSDTO getSolicitante() {
        return solicitante;
    }

    /**
     * Asigna el valor al campo solicitante.
     * 
     * @param solicitante
     *            el valor solicitante a asignar
     */
    public void setSolicitante(FuncionarioWSDTO solicitante) {
        this.solicitante = solicitante;
    }

	/**
	 * @return the distritoId
	 */
	public Long getDistritoId() {
		return distritoId;
	}

	/**
	 * @param distritoId the distritoId to set
	 */
	public void setDistritoId(Long distritoId) {
		this.distritoId = distritoId;
	}

	/**
	 * @return the tribunalId
	 */
	public Long getTribunalId() {
		return tribunalId;
	}

	/**
	 * @param tribunalId the tribunalId to set
	 */
	public void setTribunalId(Long tribunalId) {
		this.tribunalId = tribunalId;
	}

	/**
	 * @return the claveFuncionarioId
	 */
	public Long getClaveFuncionarioId() {
		return claveFuncionarioId;
	}

	/**
	 * @param claveFuncionarioId the claveFuncionarioId to set
	 */
	public void setClaveFuncionarioId(Long claveFuncionarioId) {
		this.claveFuncionarioId = claveFuncionarioId;
	}

	public Long getIdFuncionarioSolicitante() {
		return idFuncionarioSolicitante;
	}

	public void setIdFuncionarioSolicitante(Long idFuncionarioSolicitante) {
		this.idFuncionarioSolicitante = idFuncionarioSolicitante;
	}

	/**
	 * @return the relacionesDelitoPersona
	 */
	public List<DelitoPersonaWSDTO> getRelacionesDelitoPersona() {
		return relacionesDelitoPersona;
	}

	/**
	 * @param relacionesDelitoPersona the relacionesDelitoPersona to set
	 */
	public void setRelacionesDelitoPersona(
			List<DelitoPersonaWSDTO> relacionesDelitoPersona) {
		this.relacionesDelitoPersona = relacionesDelitoPersona;
	}

	/**
	 * M&eacute;todo de acceso al campo funcionarioExternoSolicitante
	 * @return funcionarioExternoSolicitante
	 */
	public FuncionarioExternoWSDTO getFuncionarioExternoSolicitante() {
		return funcionarioExternoSolicitante;
	}

	/**
	 * Asigna el valor al campo funcionarioExternoSolicitante.
	 * @param funcionarioExternoSolicitante
	 */
	public void setFuncionarioExternoSolicitante(
			FuncionarioExternoWSDTO funcionarioExternoSolicitante) {
		this.funcionarioExternoSolicitante = funcionarioExternoSolicitante;
	}

	/**
	 * @return the funcionarioExternoDestinatario
	 */
	public FuncionarioExternoWSDTO getFuncionarioExternoDestinatario() {
		return funcionarioExternoDestinatario;
	}

	/**
	 * @param funcionarioExternoDestinatario the funcionarioExternoDestinatario to set
	 */
	public void setFuncionarioExternoDestinatario(
			FuncionarioExternoWSDTO funcionarioExternoDestinatario) {
		this.funcionarioExternoDestinatario = funcionarioExternoDestinatario;
	}

}
