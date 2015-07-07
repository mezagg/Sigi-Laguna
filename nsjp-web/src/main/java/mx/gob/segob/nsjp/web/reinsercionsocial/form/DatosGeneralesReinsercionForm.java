/**
* Nombre del Programa 	: ReinsercionSocialForm.java
* Autor					: EdgarTE
* Compania             	: Ultrasist
* Proyecto              : NSJP							Fecha: 21/02/2012
* Marca de cambio       : N/A
* Descripcion General 	: Describir el objetivo de la clase de manera breve
* Programa Dependiente 	: N/A
* Programa Subsecuente 	: N/A
* Cond. de ejecucion 	: N/A
* Dias de ejecucion 	: N/A                      		Horario: N/A
*                              MODIFICACIONES
*------------------------------------------------------------------------------
* Autor 				: N/A
* Compania				: N/A
* Proyecto 				: N/A                    		Fecha: N/A
* Modificacion 			: N/A
*------------------------------------------------------------------------------
*/
package mx.gob.segob.nsjp.web.reinsercionsocial.form;

import java.util.List;

import mx.gob.segob.nsjp.web.base.form.GenericForm;

/**
 * Clase que modela la forma utilizada por struts, para las pantallas de datos 
 * generales de Reinserción Social.
 * @version 1.0
 * @author EdgarTE.
 *
 */
public class DatosGeneralesReinsercionForm extends GenericForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5279400528089193428L;

	private String nus;
	private String nombre;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String edad;
	private String lesionado;
	private String caso;
	private String causa;
	private String carpeta;
	private String tipoSentencia;
	private String fechaInicioPenaSTR;
	private String fechaFinPenaSTR;
	private List<String> lstMedidasAlternativas;
	private boolean encarcelado;
	private String motivo;
	private String preliberacion;
	private boolean reparacionDanio;
	private String montoDanioReparado;
	private boolean remisionParcial;
	private List<String> lstProgramas;
	private int puntosAcumulados;
	private int puntosTotales;
	private int porcentajeCumplido;
	private int porcentajeCubiertoPena;
	private String candidatoBeneficio;
	private String beneficio;
	private String actuacionSeleccionada;
	private String expedienteId;
	private String numeroExpedienteId;
	private String sentenciaId;
	private String ceresoAsignado;
	private String duracionPena;
	private int aniosPena;
	private int mesesPena;
	private int diasPena;
	private String delitoCometido;
	private long estatusNumExpId;
	private long rolActivoId;
	
	private String forward;
	
	private String fechaSalidaSTR;
	private String computoActual;
	private String fechaIngresoCeresoSTR;
	private String fechaRemisionSTR;
	private String computoRemision;
	private boolean libertadCondicional;
	private String fechaLibCondSTR;
	private boolean libertadCondicionalRemision;
	private String fechaLibCondRemisionSTR;
	private boolean aplicaMulta;
	private String montoMulta;
	private boolean multaPagada;
	private boolean reparacionPagada;
	private int aniosComputoRemision;
	private int mesesComputoRemision;
	private int diasComputoRemision;
	private int aniosComputoActual;
	private int mesesComputoActual;
	private int diasComputoActual;
	private boolean autorizacionRPP;
	private boolean autorizacionLC;
	private long idSolRPP;
	private long idSolLC;
	private String fechaProbableLC;
	private long idSolRD;
	private long idSolMulta;
	private long idRD;
	private long idMulta;

	/**
	 * @return the forward
	 */
	public String getForward() {
		return forward;
	}

	/**
	 * @param forward the forward to set
	 */
	public void setForward(String forward) {
		this.forward = forward;
	}

	/**
	 * M&eacute;todo de acceso al campo nus.
	 * @return El valor del campo nus
	 */
	public String getNus() {
		return nus;
	}

	/**
	 * Asigna el valor al campo nus.
	 * @param nus el valor nus a asignar
	 */
	public void setNus(String nus) {
		this.nus = nus;
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
	 * M&eacute;todo de acceso al campo edad.
	 * @return El valor del campo edad.
	 */
	public String getEdad() {
		return edad;
	}

	/**
	 * Asigna el valor al campo edad.
	 * @param edad la edad a asignar.
	 */
	public void setEdad(String edad) {
		this.edad = edad;
	}

	/**
	 * M&eacute;todo de acceso al campo lesionado.
	 * @return El valor del campo lesionado.
	 */
	public String getLesionado() {
		return lesionado;
	}

	/**
	 * Asigna el valor al campo lesionado.
	 * @param lesionado el valor del campo lesionado a asignar.
	 */
	public void setLesionado(String lesionado) {
		this.lesionado = lesionado;
	}
	
	/**
	 * M&eacute;todo de acceso al campo caso.
	 * @return El valor del campo caso
	 */
	public String getCaso() {
		return caso;
	}

	/**
	 * Asigna el valor al campo caso.
	 * @param caso el valor caso a asignar
	 */
	public void setCaso(String caso) {
		this.caso = caso;
	}

	/**
	 * M&eacute;todo de acceso al campo causa.
	 * @return El valor del campo causa
	 */
	public String getCausa() {
		return causa;
	}

	/**
	 * Asigna el valor al campo causa.
	 * @param causa el valor causa a asignar
	 */
	public void setCausa(String causa) {
		this.causa = causa;
	}

	/**
	 * M&eacute;todo de acceso al campo carpeta.
	 * @return El valor del campo carpeta
	 */
	public String getCarpeta() {
		return carpeta;
	}

	/**
	 * Asigna el valor al campo carpeta.
	 * @param carpeta el valor carpeta a asignar
	 */
	public void setCarpeta(String carpeta) {
		this.carpeta = carpeta;
	}

	/**
	 * M&eacute;todo de acceso al campo tipoSentencia.
	 * @return El valor del campo tipoSentencia.
	 */
	public String getTipoSentencia() {
		return tipoSentencia;
	}

	/**
	 * Asigna el valor al campo tipoSentencia.
	 * @param tipoSentencia el tipoSentencia a asignar.
	 */
	public void setTipoSentencia(String tipoSentencia) {
		this.tipoSentencia = tipoSentencia;
	}

	/**
	 * M&eacute;todo de acceso al campo fechaInicioPenaSTR.
	 * @return El valor del campo fechaInicioPenaSTR
	 */
	public String getFechaInicioPenaSTR() {
		return fechaInicioPenaSTR;
	}

	/**
	 * Asigna el valor al campo fechaInicioPenaSTR.
	 * @param fechaInicioSTR el valor fechaInicioPenaSTR a asignar
	 */
	public void setFechaInicioPenaSTR(String fechaInicioPenaSTR) {
		this.fechaInicioPenaSTR = fechaInicioPenaSTR;
	}

	/**
	 * M&eacute;todo de acceso al campo fechaFinPenaSTR.
	 * @return El valor del campo fechaFinPenaSTR
	 */
	public String getFechaFinPenaSTR() {
		return fechaFinPenaSTR;
	}

	/**
	 * Asigna el valor al campo fechaFinPenaSTR.
	 * @param fechaFinPenaSTR el valor fechaFinPenaSTR a asignar
	 */
	public void setFechaFinPenaSTR(String fechaFinPenaSTR) {
		this.fechaFinPenaSTR = fechaFinPenaSTR;
	}

	/**
	 * M&eacute;todo de acceso a la lista de medidas alternativas.
	 * @return El valor de la lista de medidas alternativas.
	 */
	public List<String> getLstMedidasAlternativas() {
		return lstMedidasAlternativas;
	}

	/**
	 * Asigna el valor a la lista de medidas alternativas.
	 * @param lstMedidasAlternativas el valor lstMedidasAlternativas a asignar.
	 */
	public void setLstMedidasAlternativas(List<String> lstMedidasAlternativas) {
		this.lstMedidasAlternativas = lstMedidasAlternativas;
	}

	/**
	 * M&eacute;todo de acceso al campo encarcelado.
	 * @return El valor del campo encarcelado
	 */
	public boolean getEncarcelado() {
		return encarcelado;
	}

	/**
	 * Asigna el valor al campo encarcelado.
	 * @param encarcelado el valor encarcelado a asignar
	 */
	public void setEncarcelado(boolean encarcelado) {
		this.encarcelado = encarcelado;
	}

	/**
	 * M&eacute;todo de acceso al campo motivo.
	 * @return El valor del campo motivo
	 */
	public String getMotivo() {
		return motivo;
	}

	/**
	 * Asigna el valor al campo motivo.
	 * @param motivo el valor motivo a asignar
	 */
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	/**
	 * M&eacute;todo de acceso al campo preliberacion.
	 * @return El valor del campo preliberacion
	 */
	public String getPreliberacion() {
		return preliberacion;
	}

	/**
	 * Asigna el valor al campo preliberacion.
	 * @param preliberacion el valor preliberacion a asignar
	 */
	public void setPreliberacion(String preliberacion) {
		this.preliberacion = preliberacion;
	}
	
	/**
	 * M&eacute;todo de acceso al campo reparacionDanio.
	 * @return El valor del campo reparacionDanio
	 */
	public boolean getReparacionDanio() {
		return reparacionDanio;
	}

	/**
	 * Asigna el valor al campo reparacionDanio.
	 * @param reparacionDanio el valor reparacionDanio a asignar
	 */
	public void setReparacionDanio(boolean reparacionDanio) {
		this.reparacionDanio = reparacionDanio;
	}
	
	/**
	 * M&eacute;todo de acceso al campo montoDanioReparado.
	 * @return El valor del campo montoDanioReparado
	 */
	public String getMontoDanioReparado() {
		return montoDanioReparado;
	}

	/**
	 * Asigna el valor al campo montoDanioReparado.
	 * @param montoDanioReparado el valor montoDanioReparado a asignar
	 */
	public void setMontoDanioReparado(String montoDanioReparado) {
		this.montoDanioReparado = montoDanioReparado;
	}

	/**
	 * M&eacute;todo de acceso al campo remisionParcial.
	 * @return El valor del campo remisionParcial
	 */
	public boolean isRemisionParcial() {
		return remisionParcial;
	}

	/**
	 * Asigna el valor al campo remisionParcial.
	 * @param remisionParcial el valor remisionParcial a asignar
	 */
	public void setRemisionParcial(boolean remisionParcial) {
		this.remisionParcial = remisionParcial;
	}

	/**
	 * M&eacute;todo de acceso al campo lstProgramas.
	 * @return El valor del campo lstProgramas
	 */
	public List<String> getLstProgramas() {
		return lstProgramas;
	}

	/**
	 * Asigna el valor al campo lstProgramas.
	 * @param lstProgramas el valor lstProgramas a asignar
	 */
	public void setLstProgramas(List<String> lstProgramas) {
		this.lstProgramas = lstProgramas;
	}

	/**
	 * M&eacute;todo de acceso al campo puntosAcumulados.
	 * @return El valor del campo puntosAcumulados
	 */
	public int getPuntosAcumulados() {
		return puntosAcumulados;
	}

	/**
	 * Asigna el valor al campo puntosAcumulados.
	 * @param puntosAcumulados el valor puntosAcumulados a asignar
	 */
	public void setPuntosAcumulados(int puntosAcumulados) {
		this.puntosAcumulados = puntosAcumulados;
	}

	/**
	 * M&eacute;todo de acceso al campo puntosTotales.
	 * @return El valor del campo puntosTotales
	 */
	public int getPuntosTotales() {
		return puntosTotales;
	}

	/**
	 * Asigna el valor al campo puntosTotales.
	 * @param puntosTotales el valor puntosTotales a asignar
	 */
	public void setPuntosTotales(int puntosTotales) {
		this.puntosTotales = puntosTotales;
	}

	/**
	 * M&eacute;todo de acceso al campo porcentajeCumplido.
	 * @return El valor del campo porcentajeCumplido
	 */
	public int getPorcentajeCumplido() {
		return porcentajeCumplido;
	}

	/**
	 * Asigna el valor al campo porcentajeCumplido.
	 * @param porcentajeCumplido el valor porcentajeCumplido a asignar
	 */
	public void setPorcentajeCumplido(int porcentajeCumplido) {
		this.porcentajeCumplido = porcentajeCumplido;
	}

	/**
	 * M&eacute;todo de acceso al campo porcentajeCubiertoPena.
	 * @return El valor del campo porcentajeCubiertoPena
	 */
	public int getPorcentajeCubiertoPena() {
		return porcentajeCubiertoPena;
	}

	/**
	 * Asigna el valor al campo porcentajeCubiertoPena.
	 * @param porcentajeCubiertoPena el valor porcentajeCubiertoPena a asignar
	 */
	public void setPorcentajeCubiertoPena(int porcentajeCubiertoPena) {
		this.porcentajeCubiertoPena = porcentajeCubiertoPena;
	}

	/**
	 * M&eacute;todo de acceso al campo candidatoBeneficio.
	 * @return El valor del campo candidatoBeneficio
	 */
	public String getCandidatoBeneficio() {
		return candidatoBeneficio;
	}

	/**
	 * Asigna el valor al campo candidatoBeneficio.
	 * @param candidatoBeneficio el valor candidatoBeneficio a asignar
	 */
	public void setCandidatoBeneficio(String candidatoBeneficio) {
		this.candidatoBeneficio = candidatoBeneficio;
	}

	/**
	 * M&eacute;todo de acceso al campo beneficio.
	 * @return El valor del campo beneficio
	 */
	public String getBeneficio() {
		return beneficio;
	}

	/**
	 * Asigna el valor al campo beneficio.
	 * @param beneficio el valor beneficio a asignar
	 */
	public void setBeneficio(String beneficio) {
		this.beneficio = beneficio;
	}

	/**
	 * M&eacute;todo de acceso al campo actuacionSeleccionada.
	 * @return El valor del campo actuacionSeleccionada
	 */
	public String getActuacionSeleccionada() {
		return actuacionSeleccionada;
	}

	/**
	 * Asigna el valor al campo actuacionSeleccionada.
	 * @param actuacionSeleccionada el valor actuacionSeleccionada a asignar
	 */
	public void setActuacionSeleccionada(String actuacionSeleccionada) {
		this.actuacionSeleccionada = actuacionSeleccionada;
	}

	/**
	 * @return the expedienteId
	 */
	public String getExpedienteId() {
		return expedienteId;
	}

	/**
	 * @param expedienteId the expedienteId to set
	 */
	public void setExpedienteId(String expedienteId) {
		this.expedienteId = expedienteId;
	}

	/**
	 * @return the numeroExpedienteId
	 */
	public String getNumeroExpedienteId() {
		return numeroExpedienteId;
	}

	/**
	 * @param numeroExpedienteId the numeroExpedienteId to set
	 */
	public void setNumeroExpedienteId(String numeroExpedienteId) {
		this.numeroExpedienteId = numeroExpedienteId;
	}

	/**
	 * @return the sentenciaId
	 */
	public String getSentenciaId() {
		return sentenciaId;
	}

	/**
	 * @param sentenciaId the sentenciaId to set
	 */
	public void setSentenciaId(String sentenciaId) {
		this.sentenciaId = sentenciaId;
	}

	/**
	 * M&eacute;todo de acceso al campo ceresoAsignado.
	 * @return El valor del campo ceresoAsignado
	 */
	public String getCeresoAsignado() {
		return ceresoAsignado;
	}

	/**
	 * Asigna el valor al campo ceresoAsignado.
	 * @param ceresoAsignado el valor ceresoAsignado a asignar
	 */
	public void setCeresoAsignado(String ceresoAsignado) {
		this.ceresoAsignado = ceresoAsignado;
	}

	/**
	 * M&eacute;todo de acceso al campo duracionPena.
	 * @return El valor del campo duracionPena
	 */
	public String getDuracionPena() {
		return duracionPena;
	}

	/**
	 * Asigna el valor al campo duracionPena.
	 * @param duracionPena el valor duracionPena a asignar
	 */
	public void setDuracionPena(String duracionPena) {
		this.duracionPena = duracionPena;
	}

	/**
	 * M&eacute;todo de acceso al campo aniosPena.
	 * @return El valor del campo aniosPena
	 */
	public int getAniosPena() {
		return aniosPena;
	}

	/**
	 * Asigna el valor al campo aniosPena.
	 * @param aniosPena el valor aniosPena a asignar
	 */
	public void setAniosPena(int aniosPena) {
		this.aniosPena = aniosPena;
	}

	/**
	 * M&eacute;todo de acceso al campo mesesPena.
	 * @return El valor del campo mesesPena
	 */
	public int getMesesPena() {
		return mesesPena;
	}

	/**
	 * Asigna el valor al campo mesesPena.
	 * @param mesesPena el valor mesesPena a asignar
	 */
	public void setMesesPena(int mesesPena) {
		this.mesesPena = mesesPena;
	}

	/**
	 * M&eacute;todo de acceso al campo diasPena.
	 * @return El valor del campo diasPena
	 */
	public int getDiasPena() {
		return diasPena;
	}

	/**
	 * Asigna el valor al campo diasPena.
	 * @param diasPena el valor diasPena a asignar
	 */
	public void setDiasPena(int diasPena) {
		this.diasPena = diasPena;
	}

	/**
	 * M&eacute;todo de acceso al campo delitoCometido.
	 * @return El valor del campo delitoCometido
	 */
	public String getDelitoCometido() {
		return delitoCometido;
	}

	/**
	 * Asigna el valor al campo delitoCometido.
	 * @param delitoCometido el valor delitoCometido a asignar
	 */
	public void setDelitoCometido(String delitoCometido) {
		this.delitoCometido = delitoCometido;
	}

	/**
	 * M&eacute;todo de acceso al campo estatusNumExpId.
	 * @return El valor del campo estatusNumExpId
	 */
	public long getEstatusNumExpId() {
		return estatusNumExpId;
	}

	/**
	 * Asigna el valor al campo estatusNumExpId.
	 * @param estatusNumExpId el valor estatusNumExpId a asignar
	 */
	public void setEstatusNumExpId(long estatusNumExpId) {
		this.estatusNumExpId = estatusNumExpId;
	}

	/**
	 * M&eacute;todo de acceso al campo rolActivoId.
	 * @return El valor del campo rolActivoId
	 */
	public long getRolActivoId() {
		return rolActivoId;
	}

	/**
	 * Asigna el valor al campo rolActivoId.
	 * @param rolActivoId el valor rolActivoId a asignar
	 */
	public void setRolActivoId(long rolActivoId) {
		this.rolActivoId = rolActivoId;
	}

	/**
	 * M&eacute;todo de acceso al campo fechaSalidaSTR.
	 * @return El valor del campo fechaSalidaSTR
	 */
	public String getFechaSalidaSTR() {
		return fechaSalidaSTR;
	}

	/**
	 * Asigna el valor al campo fechaSalidaSTR.
	 * @param fechaSalidaSTR el valor fechaSalidaSTR a asignar
	 */
	public void setFechaSalidaSTR(String fechaSalidaSTR) {
		this.fechaSalidaSTR = fechaSalidaSTR;
	}

	/**
	 * M&eacute;todo de acceso al campo computoActual.
	 * @return El valor del campo computoActual
	 */
	public String getComputoActual() {
		return computoActual;
	}

	/**
	 * Asigna el valor al campo computoActual.
	 * @param computoActual el valor computoActual a asignar
	 */
	public void setComputoActual(String computoActual) {
		this.computoActual = computoActual;
	}

	/**
	 * M&eacute;todo de acceso al campo fechaIngresoCeresoSTR.
	 * @return El valor del campo fechaIngresoCeresoSTR
	 */
	public String getFechaIngresoCeresoSTR() {
		return fechaIngresoCeresoSTR;
	}

	/**
	 * Asigna el valor al campo fechaIngresoCeresoSTR.
	 * @param fechaIngresoCeresoSTR el valor fechaIngresoCeresoSTR a asignar
	 */
	public void setFechaIngresoCeresoSTR(String fechaIngresoCeresoSTR) {
		this.fechaIngresoCeresoSTR = fechaIngresoCeresoSTR;
	}

	/**
	 * M&eacute;todo de acceso al campo fechaRemisionSTR.
	 * @return El valor del campo fechaRemisionSTR
	 */
	public String getFechaRemisionSTR() {
		return fechaRemisionSTR;
	}

	/**
	 * Asigna el valor al campo fechaRemisionSTR.
	 * @param fechaRemisionSTR el valor fechaRemisionSTR a asignar
	 */
	public void setFechaRemisionSTR(String fechaRemisionSTR) {
		this.fechaRemisionSTR = fechaRemisionSTR;
	}

	/**
	 * M&eacute;todo de acceso al campo computoRemision.
	 * @return El valor del campo computoRemision
	 */
	public String getComputoRemision() {
		return computoRemision;
	}

	/**
	 * Asigna el valor al campo computoRemision.
	 * @param computoRemision el valor computoRemision a asignar
	 */
	public void setComputoRemision(String computoRemision) {
		this.computoRemision = computoRemision;
	}

	/**
	 * M&eacute;todo de acceso al campo libertadCondicional.
	 * @return El valor del campo libertadCondicional
	 */
	public boolean isLibertadCondicional() {
		return libertadCondicional;
	}

	/**
	 * Asigna el valor al campo libertadCondicional.
	 * @param libertadCondicional el valor libertadCondicional a asignar
	 */
	public void setLibertadCondicional(boolean libertadCondicional) {
		this.libertadCondicional = libertadCondicional;
	}

	/**
	 * M&eacute;todo de acceso al campo fechaLibCondSTR.
	 * @return El valor del campo fechaLibCondSTR
	 */
	public String getFechaLibCondSTR() {
		return fechaLibCondSTR;
	}

	/**
	 * Asigna el valor al campo fechaLibCondSTR.
	 * @param fechaLibCondSTR el valor fechaLibCondSTR a asignar
	 */
	public void setFechaLibCondSTR(String fechaLibCondSTR) {
		this.fechaLibCondSTR = fechaLibCondSTR;
	}

	/**
	 * M&eacute;todo de acceso al campo libertadCondicionalRemision.
	 * @return El valor del campo libertadCondicionalRemision
	 */
	public boolean isLibertadCondicionalRemision() {
		return libertadCondicionalRemision;
	}

	/**
	 * Asigna el valor al campo libertadCondicionalRemision.
	 * @param libertadCondicionalRemision el valor libertadCondicionalRemision a asignar
	 */
	public void setLibertadCondicionalRemision(boolean libertadCondicionalRemision) {
		this.libertadCondicionalRemision = libertadCondicionalRemision;
	}

	/**
	 * M&eacute;todo de acceso al campo fechaLibCondRemisionSTR.
	 * @return El valor del campo fechaLibCondRemisionSTR
	 */
	public String getFechaLibCondRemisionSTR() {
		return fechaLibCondRemisionSTR;
	}

	/**
	 * Asigna el valor al campo fechaLibCondRemisionSTR.
	 * @param fechaLibCondRemisionSTR el valor fechaLibCondRemisionSTR a asignar
	 */
	public void setFechaLibCondRemisionSTR(String fechaLibCondRemisionSTR) {
		this.fechaLibCondRemisionSTR = fechaLibCondRemisionSTR;
	}

	/**
	 * M&eacute;todo de acceso al campo aplicaMulta.
	 * @return El valor del campo aplicaMulta
	 */
	public boolean isAplicaMulta() {
		return aplicaMulta;
	}

	/**
	 * Asigna el valor al campo aplicaMulta.
	 * @param aplicaMulta el valor aplicaMulta a asignar
	 */
	public void setAplicaMulta(boolean aplicaMulta) {
		this.aplicaMulta = aplicaMulta;
	}

	/**
	 * M&eacute;todo de acceso al campo montoMulta.
	 * @return El valor del campo montoMulta
	 */
	public String getMontoMulta() {
		return montoMulta;
	}

	/**
	 * Asigna el valor al campo montoMulta.
	 * @param montoMulta el valor montoMulta a asignar
	 */
	public void setMontoMulta(String montoMulta) {
		this.montoMulta = montoMulta;
	}

	/**
	 * M&eacute;todo de acceso al campo multaPagada.
	 * @return El valor del campo multaPagada
	 */
	public boolean isMultaPagada() {
		return multaPagada;
	}

	/**
	 * Asigna el valor al campo multaPagada.
	 * @param multaPagada el valor multaPagada a asignar
	 */
	public void setMultaPagada(boolean multaPagada) {
		this.multaPagada = multaPagada;
	}

	/**
	 * M&eacute;todo de acceso al campo reparacionPagada.
	 * @return El valor del campo reparacionPagada
	 */
	public boolean isReparacionPagada() {
		return reparacionPagada;
	}

	/**
	 * Asigna el valor al campo reparacionPagada.
	 * @param reparacionPagada el valor reparacionPagada a asignar
	 */
	public void setReparacionPagada(boolean reparacionPagada) {
		this.reparacionPagada = reparacionPagada;
	}

	/**
	 * M&eacute;todo de acceso al campo aniosComputoRemision.
	 * @return El valor del campo aniosComputoRemision
	 */
	public int getAniosComputoRemision() {
		return aniosComputoRemision;
	}

	/**
	 * Asigna el valor al campo aniosComputoRemision.
	 * @param aniosComputoRemision el valor aniosComputoRemision a asignar
	 */
	public void setAniosComputoRemision(int aniosComputoRemision) {
		this.aniosComputoRemision = aniosComputoRemision;
	}

	/**
	 * M&eacute;todo de acceso al campo mesesComputoRemision.
	 * @return El valor del campo mesesComputoRemision
	 */
	public int getMesesComputoRemision() {
		return mesesComputoRemision;
	}

	/**
	 * Asigna el valor al campo mesesComputoRemision.
	 * @param mesesComputoRemision el valor mesesComputoRemision a asignar
	 */
	public void setMesesComputoRemision(int mesesComputoRemision) {
		this.mesesComputoRemision = mesesComputoRemision;
	}

	/**
	 * M&eacute;todo de acceso al campo diasComputoRemision.
	 * @return El valor del campo diasComputoRemision
	 */
	public int getDiasComputoRemision() {
		return diasComputoRemision;
	}

	/**
	 * Asigna el valor al campo diasComputoRemision.
	 * @param diasComputoRemision el valor diasComputoRemision a asignar
	 */
	public void setDiasComputoRemision(int diasComputoRemision) {
		this.diasComputoRemision = diasComputoRemision;
	}

	/**
	 * M&eacute;todo de acceso al campo aniosComputoActual.
	 * @return El valor del campo aniosComputoActual
	 */
	public int getAniosComputoActual() {
		return aniosComputoActual;
	}

	/**
	 * Asigna el valor al campo aniosComputoActual.
	 * @param aniosComputoActual el valor aniosComputoActual a asignar
	 */
	public void setAniosComputoActual(int aniosComputoActual) {
		this.aniosComputoActual = aniosComputoActual;
	}

	/**
	 * M&eacute;todo de acceso al campo mesesComputoActual.
	 * @return El valor del campo mesesComputoActual
	 */
	public int getMesesComputoActual() {
		return mesesComputoActual;
	}

	/**
	 * Asigna el valor al campo mesesComputoActual.
	 * @param mesesComputoActual el valor mesesComputoActual a asignar
	 */
	public void setMesesComputoActual(int mesesComputoActual) {
		this.mesesComputoActual = mesesComputoActual;
	}

	/**
	 * M&eacute;todo de acceso al campo diasComputoActual.
	 * @return El valor del campo diasComputoActual
	 */
	public int getDiasComputoActual() {
		return diasComputoActual;
	}

	/**
	 * Asigna el valor al campo diasComputoActual.
	 * @param diasComputoActual el valor diasComputoActual a asignar
	 */
	public void setDiasComputoActual(int diasComputoActual) {
		this.diasComputoActual = diasComputoActual;
	}

	/**
	 * M&eacute;todo de acceso al campo autorizacionRPP.
	 * @return El valor del campo autorizacionRPP
	 */
	public boolean isAutorizacionRPP() {
		return autorizacionRPP;
	}

	/**
	 * Asigna el valor al campo autorizacionRPP.
	 * @param autorizacionRPP el valor autorizacionRPP a asignar
	 */
	public void setAutorizacionRPP(boolean autorizacionRPP) {
		this.autorizacionRPP = autorizacionRPP;
	}

	/**
	 * M&eacute;todo de acceso al campo autorizacionLC.
	 * @return El valor del campo autorizacionLC
	 */
	public boolean isAutorizacionLC() {
		return autorizacionLC;
	}

	/**
	 * Asigna el valor al campo autorizacionLC.
	 * @param autorizacionLC el valor autorizacionLC a asignar
	 */
	public void setAutorizacionLC(boolean autorizacionLC) {
		this.autorizacionLC = autorizacionLC;
	}

	/**
	 * M&eacute;todo de acceso al campo idSolRPP.
	 * @return El valor del campo idSolRPP
	 */
	public long getIdSolRPP() {
		return idSolRPP;
	}

	/**
	 * Asigna el valor al campo idSolRPP.
	 * @param idSolRPP el valor idSolRPP a asignar
	 */
	public void setIdSolRPP(long idSolRPP) {
		this.idSolRPP = idSolRPP;
	}

	/**
	 * M&eacute;todo de acceso al campo idSolLC.
	 * @return El valor del campo idSolLC
	 */
	public long getIdSolLC() {
		return idSolLC;
	}

	/**
	 * Asigna el valor al campo idSolLC.
	 * @param idSolLC el valor idSolLC a asignar
	 */
	public void setIdSolLC(long idSolLC) {
		this.idSolLC = idSolLC;
	}

	/**
	 * M&eacute;todo de acceso al campo fechaProbableLC.
	 * @return El valor del campo fechaProbableLC
	 */
	public String getFechaProbableLC() {
		return fechaProbableLC;
	}

	/**
	 * Asigna el valor al campo fechaProbableLC.
	 * @param fechaProbableLC el valor fechaProbableLC a asignar
	 */
	public void setFechaProbableLC(String fechaProbableLC) {
		this.fechaProbableLC = fechaProbableLC;
	}

	/**
	 * M&eacute;todo de acceso al campo idSolRD.
	 * @return El valor del campo idSolRD
	 */
	public long getIdSolRD() {
		return idSolRD;
	}

	/**
	 * Asigna el valor al campo idSolRD.
	 * @param idSolRD el valor idSolRD a asignar
	 */
	public void setIdSolRD(long idSolRD) {
		this.idSolRD = idSolRD;
	}

	/**
	 * M&eacute;todo de acceso al campo idSolMulta.
	 * @return El valor del campo idSolMulta
	 */
	public long getIdSolMulta() {
		return idSolMulta;
	}

	/**
	 * Asigna el valor al campo idSolMulta.
	 * @param idSolMulta el valor idSolMulta a asignar
	 */
	public void setIdSolMulta(long idSolMulta) {
		this.idSolMulta = idSolMulta;
	}

	/**
	 * M&eacute;todo de acceso al campo idRD.
	 * @return El valor del campo idRD
	 */
	public long getIdRD() {
		return idRD;
	}

	/**
	 * Asigna el valor al campo idRD.
	 * @param idRD el valor idRD a asignar
	 */
	public void setIdRD(long idRD) {
		this.idRD = idRD;
	}

	/**
	 * M&eacute;todo de acceso al campo idMulta.
	 * @return El valor del campo idMulta
	 */
	public long getIdMulta() {
		return idMulta;
	}

	/**
	 * Asigna el valor al campo idMulta.
	 * @param idMulta el valor idMulta a asignar
	 */
	public void setIdMulta(long idMulta) {
		this.idMulta = idMulta;
	}

}
