package mx.gob.segob.nsjp.dto.involucrado;

import java.util.Date;

import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.domicilio.DomicilioDTO;

public class DatosDefuncionDTO {

	
	private Long datosDefuncionId;
	
	private DomicilioDTO domicilioDefuncion;
	private DomicilioDTO domicilioDefDenuncia;
	private String folioDefuncion;
    private Date fechaAveriguacion;
    private Date fechaDefuncion;
    private ValorDTO tipoDefuncion;
    private ValorDTO certificadaPor;
    private ValorDTO sitioDefuncion;
    private ValorDTO sitioLesion;
    private ValorDTO fueEnTrabajo;
    private String agenteExterno;
    private ValorDTO tipoEventoDefuncion;
    private ValorDTO tipoVictima;
    private ValorDTO tipoArma;
  //Causas de la defuncion
  	private String causaA;
  	private String duracionA;
  	private String causaB;
  	private String duracionB;
  	private String causaC;
  	private String duracionC;
  	private String causaD;
  	private String duracionD;
  	private String edoPatologico;
  	private String duracionPatologico;
  	private ValorDTO condicionEmbarazo;
  	private ValorDTO periodoPosparto;
  	
  	private Long edadDefuncion;
  	private ValorDTO edadUnidadDefuncion;
  	private ValorDTO condicionActividad;
  	
  	
  	
	public DatosDefuncionDTO() {
		super();
	}
	public DatosDefuncionDTO(Long datosDefuncionId) {
		super();
		this.datosDefuncionId = datosDefuncionId;
	}
	
	
	
	public Long getDatosDefuncionId() {
		return datosDefuncionId;
	}
	public void setDatosDefuncionId(Long datosDefuncionId) {
		this.datosDefuncionId = datosDefuncionId;
	}
	public DomicilioDTO getDomicilioDefuncion() {
		return domicilioDefuncion;
	}
	public void setDomicilioDefuncion(DomicilioDTO domicilioDefuncion) {
		this.domicilioDefuncion = domicilioDefuncion;
	}
	public String getFolioDefuncion() {
		return folioDefuncion;
	}
	public void setFolioDefuncion(String folioDefuncion) {
		this.folioDefuncion = folioDefuncion;
	}
	public Date getFechaAveriguacion() {
		return fechaAveriguacion;
	}
	public void setFechaAveriguacion(Date fechaAveriguacion) {
		this.fechaAveriguacion = fechaAveriguacion;
	}
	public Date getFechaDefuncion() {
		return fechaDefuncion;
	}
	public void setFechaDefuncion(Date fechaDefuncion) {
		this.fechaDefuncion = fechaDefuncion;
	}
	public ValorDTO getTipoDefuncion() {
		return tipoDefuncion;
	}
	public void setTipoDefuncion(ValorDTO tipoDefuncion) {
		this.tipoDefuncion = tipoDefuncion;
	}
	public ValorDTO getCertificadaPor() {
		return certificadaPor;
	}
	public void setCertificadaPor(ValorDTO certificadaPor) {
		this.certificadaPor = certificadaPor;
	}
	public ValorDTO getSitioDefuncion() {
		return sitioDefuncion;
	}
	public void setSitioDefuncion(ValorDTO sitioDefuncion) {
		this.sitioDefuncion = sitioDefuncion;
	}
	public ValorDTO getSitioLesion() {
		return sitioLesion;
	}
	public void setSitioLesion(ValorDTO sitioLesion) {
		this.sitioLesion = sitioLesion;
	}
	public ValorDTO getFueEnTrabajo() {
		return fueEnTrabajo;
	}
	public void setFueEnTrabajo(ValorDTO fueEnTrabajo) {
		this.fueEnTrabajo = fueEnTrabajo;
	}
	public String getAgenteExterno() {
		return agenteExterno;
	}
	public void setAgenteExterno(String agenteExterno) {
		this.agenteExterno = agenteExterno;
	}
	public ValorDTO getTipoEventoDefuncion() {
		return tipoEventoDefuncion;
	}
	public void setTipoEventoDefuncion(ValorDTO tipoEventoDefuncion) {
		this.tipoEventoDefuncion = tipoEventoDefuncion;
	}
	public ValorDTO getTipoVictima() {
		return tipoVictima;
	}
	public void setTipoVictima(ValorDTO tipoVictima) {
		this.tipoVictima = tipoVictima;
	}
	public ValorDTO getTipoArma() {
		return tipoArma;
	}
	public void setTipoArma(ValorDTO tipoArma) {
		this.tipoArma = tipoArma;
	}
	public String getCausaA() {
		return causaA;
	}
	public void setCausaA(String causaA) {
		this.causaA = causaA;
	}
	public String getDuracionA() {
		return duracionA;
	}
	public void setDuracionA(String duracionA) {
		this.duracionA = duracionA;
	}
	public String getCausaB() {
		return causaB;
	}
	public void setCausaB(String causaB) {
		this.causaB = causaB;
	}
	public String getDuracionB() {
		return duracionB;
	}
	public void setDuracionB(String duracionB) {
		this.duracionB = duracionB;
	}
	public String getCausaC() {
		return causaC;
	}
	public void setCausaC(String causaC) {
		this.causaC = causaC;
	}
	public String getDuracionC() {
		return duracionC;
	}
	public void setDuracionC(String duracionC) {
		this.duracionC = duracionC;
	}
	public String getCausaD() {
		return causaD;
	}
	public void setCausaD(String causaD) {
		this.causaD = causaD;
	}
	public String getDuracionD() {
		return duracionD;
	}
	public void setDuracionD(String duracionD) {
		this.duracionD = duracionD;
	}
	public String getEdoPatologico() {
		return edoPatologico;
	}
	public void setEdoPatologico(String edoPatologico) {
		this.edoPatologico = edoPatologico;
	}
	public String getDuracionPatologico() {
		return duracionPatologico;
	}
	public void setDuracionPatologico(String duracionPatologico) {
		this.duracionPatologico = duracionPatologico;
	}
	public ValorDTO getCondicionEmbarazo() {
		return condicionEmbarazo;
	}
	public void setCondicionEmbarazo(ValorDTO condicionEmbarazo) {
		this.condicionEmbarazo = condicionEmbarazo;
	}
	public ValorDTO getPeriodoPosparto() {
		return periodoPosparto;
	}
	public void setPeriodoPosparto(ValorDTO periodoPosparto) {
		this.periodoPosparto = periodoPosparto;
	}
	public Long getEdadDefuncion() {
		return edadDefuncion;
	}
	public void setEdadDefuncion(Long edadDefuncion) {
		this.edadDefuncion = edadDefuncion;
	}
	public ValorDTO getEdadUnidadDefuncion() {
		return edadUnidadDefuncion;
	}
	public void setEdadUnidadDefuncion(ValorDTO edadUnidadDefuncion) {
		this.edadUnidadDefuncion = edadUnidadDefuncion;
	}
	public ValorDTO getCondicionActividad() {
		return condicionActividad;
	}
	public void setCondicionActividad(ValorDTO condicionActividad) {
		this.condicionActividad = condicionActividad;
	}
	public DomicilioDTO getDomicilioDefDenuncia() {
		return domicilioDefDenuncia;
	}
	public void setDomicilioDefDenuncia(DomicilioDTO domicilioDefDenuncia) {
		this.domicilioDefDenuncia = domicilioDefDenuncia;
	}
  	
}
