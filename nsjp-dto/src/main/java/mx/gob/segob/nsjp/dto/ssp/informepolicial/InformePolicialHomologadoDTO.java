package mx.gob.segob.nsjp.dto.ssp.informepolicial;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mx.gob.segob.nsjp.dto.base.GenericDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.dto.hecho.HechoDTO;



public class InformePolicialHomologadoDTO extends GenericDTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    private Long informePolicialHomologadoId;
    private HechoDTO hecho;
    private FuncionarioDTO funcionarioElabora;
    private FuncionarioDTO funcionarioDestinatario;
    private ExpedienteDTO expediente;
    private ValorDTO tipoParticipacion;
    private Date fechaCaptura;
    private String objetivosGenerales;
    private Long numeroOficio;
    private Long anio;
    private Long folioIPH;
    private String numEcoTransporte;
    private OperativoDTO operativo;
    private List<TurnoLaboralIphDTO> turnoLaboralIphs=new ArrayList<TurnoLaboralIphDTO>();
    private List<InvolucradoIPHDTO> involucradoIphs = new ArrayList<InvolucradoIPHDTO>();            
    private List<DelitoIphDTO> delitoIph=new ArrayList<DelitoIphDTO>();
    private List<FaltaAdministrativaIphDTO> faltaIph;
    private String asunto;
    private String claveInterInstitucionalDelito;
    //Elemnto usado para poder mostrar un solo identificador del numero de expediente en el iph
    private String unicoIdNumeroExpediente;
    //Atributo Usado Para identificar si el IPH ya fue enviado a fiscalia mediante el documento de informe
    private Boolean existeInforme;

    private Long fCatDistritoId;
    private Long fCatDiscriminanteId;  
    private Long corporacionId;

	private Long turnoIdAnt;
	private Boolean borrarOperacion;
    
    public InformePolicialHomologadoDTO(Long informePolicialHomologadoId){
    	this.informePolicialHomologadoId=informePolicialHomologadoId;
    }
    
    public InformePolicialHomologadoDTO() {

	}

	public Long getInformePolicialHomologadoId() {
		return informePolicialHomologadoId;
	}
	public void setInformePolicialHomologadoId(Long informePolicialHomologadoId) {
		this.informePolicialHomologadoId = informePolicialHomologadoId;
	}
	public HechoDTO getHecho() {
		return hecho;
	}
	public void setHecho(HechoDTO hecho) {
		this.hecho = hecho;
	}
	public FuncionarioDTO getFuncionarioElabora() {
		return funcionarioElabora;
	}
	public void setFuncionarioElabora(FuncionarioDTO funcionarioElabora) {
		this.funcionarioElabora = funcionarioElabora;
	}
	public FuncionarioDTO getFuncionarioDestinatario() {
		return funcionarioDestinatario;
	}
	public void setFuncionarioDestinatario(FuncionarioDTO funcionarioDestinatario) {
		this.funcionarioDestinatario = funcionarioDestinatario;
	}
	public ExpedienteDTO getExpediente() {
		return expediente;
	}
	public void setExpediente(ExpedienteDTO expediente) {
		this.expediente = expediente;
	}
	public ValorDTO getTipoParticipacion() {
		return tipoParticipacion;
	}
	public void setTipoParticipacion(ValorDTO tipoParticipacion) {
		this.tipoParticipacion = tipoParticipacion;
	}
	public Date getFechaCaptura() {
		return fechaCaptura;
	}
	public void setFechaCaptura(Date fechaCaptura) {
		this.fechaCaptura = fechaCaptura;
	}
	public String getObjetivosGenerales() {
		return objetivosGenerales;
	}
	public void setObjetivosGenerales(String objetivosGenerales) {
		this.objetivosGenerales = objetivosGenerales;
	}
	public Long getNumeroOficio() {
		return numeroOficio;
	}
	public void setNumeroOficio(Long numeroOficio) {
		this.numeroOficio = numeroOficio;
	}
	public Long getAnio() {
		return anio;
	}
	public void setAnio(Long anio) {
		this.anio = anio;
	}
	public Long getFolioIPH() {
		return folioIPH;
	}
	public void setFolioIPH(Long folioIPH) {
		this.folioIPH = folioIPH;
	}
	public void setNumEcoTransporte(String numEcoTransporte) {
		this.numEcoTransporte = numEcoTransporte;
	}
	public String getNumEcoTransporte() {
		return numEcoTransporte;
	}
	public OperativoDTO getOperativo() {
		return operativo;
	}
	public void setOperativo(OperativoDTO operativo) {
		this.operativo = operativo;
	}
	public void setDelitoIph(List<DelitoIphDTO> delitoIph) {
		this.delitoIph = delitoIph;
	}
	public List<DelitoIphDTO> getDelitoIph() {
		return delitoIph;
	}
	public void setFaltaIph(List<FaltaAdministrativaIphDTO> faltaIph) {
		this.faltaIph = faltaIph;
	}
	public List<FaltaAdministrativaIphDTO> getFaltaIph() {
		return faltaIph;
	}
	/**
	 * @param involucradoIphs the involucradoIphs to set
	 */
	public void setInvolucradoIphs(List<InvolucradoIPHDTO> involucradoIphs) {
		this.involucradoIphs = involucradoIphs;
	}
	/**
	 * @return the involucradoIphs
	 */
	public List<InvolucradoIPHDTO> getInvolucradoIphs() {
		return involucradoIphs;
	}
	/**
	 * @param turnoLaboralIphs the turnoLaboralIphs to set
	 */
	public void setTurnoLaboralIphs(List<TurnoLaboralIphDTO> turnoLaboralIphs) {
		this.turnoLaboralIphs = turnoLaboralIphs;
	}
	/**
	 * @return the turnoLaboralIphs
	 */
	public List<TurnoLaboralIphDTO> getTurnoLaboralIphs() {
		return turnoLaboralIphs;
	}
	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}
	public String getAsunto() {
		return asunto;
	}
	/**
	 * @return the claveInterInstitucionalDelito
	 */
	public String getClaveInterInstitucionalDelito() {
		return claveInterInstitucionalDelito;
	}
	/**
	 * @param claveInterInstitucionalDelito the claveInterInstitucionalDelito to set
	 */
	public void setClaveInterInstitucionalDelito(
			String claveInterInstitucionalDelito) {
		this.claveInterInstitucionalDelito = claveInterInstitucionalDelito;
	}
	/**
	 * @return the unicoIdNumeroExpediente
	 */
	public String getUnicoIdNumeroExpediente() {
		return unicoIdNumeroExpediente;
	}
	/**
	 * @param unicoIdNumeroExpediente the unicoIdNumeroExpediente to set
	 */
	public void setUnicoIdNumeroExpediente(String unicoIdNumeroExpediente) {
		this.unicoIdNumeroExpediente = unicoIdNumeroExpediente;
	}

	/**
	 * @return the existeInforme
	 */
	public Boolean getExisteInforme() {
		return existeInforme;
	}

	/**
	 * @param existeInforme the existeInforme to set
	 */
	public void setExisteInforme(Boolean existeInforme) {
		this.existeInforme = existeInforme;
	}

	/**
	 * @return the fCatDistritoId
	 */
	public Long getFCatDistritoId() {
		return fCatDistritoId;
	}

	/**
	 * @param fCatDistritoId the fCatDistritoId to set
	 */
	public void setFCatDistritoId(Long fCatDistritoId) {
		this.fCatDistritoId = fCatDistritoId;
	}

	/**
	 * @return the fCatDiscriminanteId
	 */
	public Long getFCatDiscriminanteId() {
		return fCatDiscriminanteId;
	}

	/**
	 * @param fCatDiscriminanteId the fCatDiscriminanteId to set
	 */
	public void setFCatDiscriminanteId(Long fCatDiscriminanteId) {
		this.fCatDiscriminanteId = fCatDiscriminanteId;
	}

	/**
	 * @return the corporacionId
	 */
	public Long getCorporacionId() {
		return corporacionId;
	}

	/**
	 * @param corporacionId the corporacionId to set
	 */
	public void setCorporacionId(Long corporacionId) {
		this.corporacionId = corporacionId;
	}

	public Long getTurnoIdAnt() {
		return turnoIdAnt;
	}

	public void setTurnoIdAnt(Long turnoIdAnt) {
		this.turnoIdAnt = turnoIdAnt;
	}

	public Boolean getBorrarOperacion() {
		return borrarOperacion;
	}

	public void setBorrarOperacion(Boolean borrarOperacion) {
		this.borrarOperacion = borrarOperacion;
	}

}
