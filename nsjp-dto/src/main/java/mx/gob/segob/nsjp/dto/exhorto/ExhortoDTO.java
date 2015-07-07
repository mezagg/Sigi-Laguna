package mx.gob.segob.nsjp.dto.exhorto;

import java.util.Date;

import mx.gob.segob.nsjp.comun.util.DateUtils;
import mx.gob.segob.nsjp.dto.base.GenericDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;

@SuppressWarnings("serial")
public class ExhortoDTO extends GenericDTO implements Comparable<Object> {
	
	private Long exhortoId;
	private FuncionarioDTO funcionario;
	private ValorDTO valorEstatus;
	private Long documento;
	private String expediente;
	private Long folio;
	private	Date fechaVencida;
	private	Date fechaDiligencia;
	private	Date fechaEnvio;
	private String diligencia;
	private	String fechaVencidaStr;
	private	String fechaDiligenciaStr;
	private	String fechaEnvioStr; 
	private Boolean esGuardadoParcial;

	
	public Long getExhortoId() {
		return exhortoId;
	}
	public void setExhortoId(Long exhortoId) {
		this.exhortoId = exhortoId;
	}
	public FuncionarioDTO getFuncionario() {
		return funcionario;
	}
	public void setFuncionario(FuncionarioDTO funcionario) {
		this.funcionario = funcionario;
	}
	public ValorDTO getValorEstatus() {
		return valorEstatus;
	}
	public void setValorEstatus(ValorDTO valorEstatus) {
		this.valorEstatus = valorEstatus;
	}
	public Long getDocumento() {
		return documento;
	}
	public void setDocumento(Long documento) {
		this.documento = documento;
	}
	public String getExpediente() {
		return expediente;
	}
	public void setExpediente(String expediente) {
		this.expediente = expediente;
	}
	public Long getFolio() {
		return folio;
	}
	public void setFolio(Long folio) {
		this.folio = folio;
	}
	public Date getFechaVencida() {
		return fechaVencida;
	}
	public void setFechaVencida(Date fechaVencida) {
		this.fechaVencida = fechaVencida;
	}
	public Date getFechaDiligencia() {
		return fechaDiligencia;
	}
	public void setFechaDiligencia(Date fechaDiligencia) {
		this.fechaDiligencia = fechaDiligencia;
	}
	public Date getFechaEnvio() {
		return fechaEnvio;
	}
	public void setFechaEnvio(Date fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
	}
	public String getDiligencia() {
		return diligencia;
	}
	public void setDiligencia(String diligencia) {
		this.diligencia = diligencia;
	}
	@Override
	public int compareTo(Object arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
	public String getFechaVencidaStr() {
		return fechaVencidaStr;
	}
	public void setFechaVencidaStr(String fechaVencidaStr) {
		this.fechaVencidaStr = fechaVencidaStr;
	}
	public String getFechaDiligenciaStr() {
		return fechaDiligenciaStr;
	}
	public void setFechaDiligenciaStr(String fechaDiligenciaStr) {
		this.fechaDiligenciaStr = fechaDiligenciaStr;
	}
	public String getFechaEnvioStr() {
		return fechaEnvioStr;
	}
	public void setFechaEnvioStr(String fechaEnvioStr) {
		this.fechaEnvioStr = fechaEnvioStr;
	}
	
    public Boolean getEsGuardadoParcial() {
        return esGuardadoParcial;
    }
    
    public void setEsGuardadoParcial(Boolean esGuardadoParcial) {
        if (esGuardadoParcial == null)
            this.esGuardadoParcial = true;
        else
            this.esGuardadoParcial = esGuardadoParcial;
    }

	
}
