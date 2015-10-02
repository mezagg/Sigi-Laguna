/**
* Nombre del Programa : ExpedienteResumenDTO.java
* Autor                            : Emigdio Hernández
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 08/06/2011
* Marca de cambio        : N/A
* Descripcion General    : DTO que contiene la información ordenada del 
* expediente
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import mx.gob.segob.nsjp.comun.enums.calidad.Calidades;
import mx.gob.segob.nsjp.comun.enums.funcionario.Puestos;
import mx.gob.segob.nsjp.comun.util.DateUtils;
import mx.gob.segob.nsjp.dto.audiencia.AudienciaDTO;
import mx.gob.segob.nsjp.dto.base.GenericDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.documento.GrupoObjetosExpedienteDTO;
import mx.gob.segob.nsjp.dto.documento.ObjetoResumenDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.dto.resolutivo.ResolutivoDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;

/**
 * DTO que contiene la información ordenada del 
 * expediente
 * @version 1.0
 * @author Emigdio Hernández
 *
 */
public class ParametrosDocumentoDTO extends GenericDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5677530541596828292L;
	private Long expedienteId;
    private String numeroExpediente;
    private Date fechaApertura;
    private Date fechaCierre;
    private String narrativa;
    private String strFechaApertura;
    private String strHoraApertura;

    private String strFechaCierre;
    private String strHoraCierre;
    
    private String strFechaActual;
    private String strHoraActual;
    
    private DelitoDTO delitoPrincipal;
    
    private String diaActual;
    private String nombreMesActual;
    private String anioActual;
    
    private UsuarioDTO usuario;
    
    private String ciudad;
	private String estado;
    
	private AudienciaDTO audiencia;
	
	List<InvolucradoDTO> denunciantes;
    
    List<InvolucradoDTO> denunciantesOrganizacion;
    
	List<InvolucradoDTO> victimasPersona;
    
    List<InvolucradoDTO> victimasOrganizaciones;
    
    List<InvolucradoDTO> probablesResponsablesPersona;
    
    List<InvolucradoDTO> probablesResponsablesOrganizacion;
    
    List<InvolucradoDTO> testigos;
    
    List<InvolucradoDTO> traductores;
    
    List<InvolucradoDTO> quienDetuvo;
    
    List<DelitoDTO> delitos;

    List<GrupoObjetosExpedienteDTO> grupoObjetosExpediente;
    
    private String listaDelitos;
    
    private FuncionarioDTO responsableTramite;
    
    private String nuc;
    
    
    /**
	 * Método de acceso al campo ciudad.
	 * @return El valor del campo ciudad
	 */
	public String getCiudad() {
		return ciudad;
	}

	/**
	 * Asigna el valor al campo ciudad.
	 * @param ciudad el valor ciudad a asignar
	 */
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	/**
	 * Método de acceso al campo estado.
	 * @return El valor del campo estado
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * Asigna el valor al campo estado.
	 * @param estado el valor estado a asignar
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}
    /**
	 * Método de acceso al campo grupoObjetosExpediente.
	 * @return El valor del campo grupoObjetosExpediente
	 */
	public List<GrupoObjetosExpedienteDTO> getGrupoObjetosExpediente() {
		return grupoObjetosExpediente;
	}

	/**
	 * Asigna el valor al campo grupoObjetosExpediente.
	 * @param grupoObjetosExpediente el valor grupoObjetosExpediente a asignar
	 */
	public void setGrupoObjetosExpediente(
			List<GrupoObjetosExpedienteDTO> grupoObjetosExpediente) {
		this.grupoObjetosExpediente = grupoObjetosExpediente;
	}

	/**
	 * Método de acceso al campo denunciantesOrganizacion.
	 * @return El valor del campo denunciantesOrganizacion
	 */
	public List<InvolucradoDTO> getDenunciantesOrganizacion() {
		return denunciantesOrganizacion;
	}

	/**
	 * Asigna el valor al campo denunciantesOrganizacion.
	 * @param denunciantesOrganizacion el valor denunciantesOrganizacion a asignar
	 */
	public void setDenunciantesOrganizacion(
			List<InvolucradoDTO> denunciantesOrganizacion) {
		this.denunciantesOrganizacion = denunciantesOrganizacion;
	}

	/**
	 * Método de acceso al campo strFechaActual.
	 * @return El valor del campo strFechaActual
	 */
	public String getStrFechaActual() {
		return strFechaActual;
	}

	/**
	 * Asigna el valor al campo strFechaActual.
	 * @param strFechaActual el valor strFechaActual a asignar
	 */
	public void setStrFechaActual(String strFechaActual) {
		this.strFechaActual = strFechaActual;
	}

	/**
	 * Método de acceso al campo strHoraActual.
	 * @return El valor del campo strHoraActual
	 */
	public String getStrHoraActual() {
		return strHoraActual;
	}

	/**
	 * Asigna el valor al campo strHoraActual.
	 * @param strHoraActual el valor strHoraActual a asignar
	 */
	public void setStrHoraActual(String strHoraActual) {
		this.strHoraActual = strHoraActual;
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
	 * Método de acceso al campo victimasPersona.
	 * @return El valor del campo victimasPersona
	 */
	public List<InvolucradoDTO> getVictimasPersona() {
		return victimasPersona;
	}

	/**
	 * Asigna el valor al campo victimasPersona.
	 * @param victimasPersona el valor victimasPersona a asignar
	 */
	public void setVictimasPersona(List<InvolucradoDTO> victimasPersona) {
		this.victimasPersona = victimasPersona;
	}

	/**
	 * Método de acceso al campo victimasOrganizaciones.
	 * @return El valor del campo victimasOrganizaciones
	 */
	public List<InvolucradoDTO> getVictimasOrganizaciones() {
		return victimasOrganizaciones;
	}

	/**
	 * Asigna el valor al campo victimasOrganizaciones.
	 * @param victimasOrganizaciones el valor victimasOrganizaciones a asignar
	 */
	public void setVictimasOrganizaciones(
			List<InvolucradoDTO> victimasOrganizaciones) {
		this.victimasOrganizaciones = victimasOrganizaciones;
	}

	/**
	 * Método de acceso al campo expedienteId.
	 * @return El valor del campo expedienteId
	 */
	public Long getExpedienteId() {
		return expedienteId;
	}

	/**
	 * Asigna el valor al campo expedienteId.
	 * @param expedienteId el valor expedienteId a asignar
	 */
	public void setExpedienteId(Long expedienteId) {
		this.expedienteId = expedienteId;
	}

	/**
	 * Método de acceso al campo numeroExpediente.
	 * @return El valor del campo numeroExpediente
	 */
	public String getNumeroExpediente() {
		return numeroExpediente;
	}

	/**
	 * Asigna el valor al campo numeroExpediente.
	 * @param numeroExpediente el valor numeroExpediente a asignar
	 */
	public void setNumeroExpediente(String numeroExpediente) {
		this.numeroExpediente = numeroExpediente;
	}

	/**
	 * Método de acceso al campo fechaApertura.
	 * @return El valor del campo fechaApertura
	 */
	public Date getFechaApertura() {
		return fechaApertura;
	}

	/**
	 * Asigna el valor al campo fechaApertura.
	 * @param fechaApertura el valor fechaApertura a asignar
	 */
	public void setFechaApertura(Date fechaApertura) {
		this.fechaApertura = fechaApertura;
	}

	/**
	 * Método de acceso al campo fechaCierre.
	 * @return El valor del campo fechaCierre
	 */
	public Date getFechaCierre() {
		return fechaCierre;
	}

	/**
	 * Asigna el valor al campo fechaCierre.
	 * @param fechaCierre el valor fechaCierre a asignar
	 */
	public void setFechaCierre(Date fechaCierre) {
		this.fechaCierre = fechaCierre;
	}

	/**
	 * Método de acceso al campo narrativa.
	 * @return El valor del campo narrativa
	 */
	public String getNarrativa() {
		return narrativa;
	}

	/**
	 * Asigna el valor al campo narrativa.
	 * @param narrativa el valor narrativa a asignar
	 */
	public void setNarrativa(String narrativa) {
		this.narrativa = narrativa;
	}

	/**
	 * Método de acceso al campo strFechaApertura.
	 * @return El valor del campo strFechaApertura
	 */
	public String getStrFechaApertura() {
		return strFechaApertura;
	}

	/**
	 * Asigna el valor al campo strFechaApertura.
	 * @param strFechaApertura el valor strFechaApertura a asignar
	 */
	public void setStrFechaApertura(String strFechaApertura) {
		this.strFechaApertura = strFechaApertura;
	}

	/**
	 * Método de acceso al campo strHoraApertura.
	 * @return El valor del campo strHoraApertura
	 */
	public String getStrHoraApertura() {
		return strHoraApertura;
	}

	/**
	 * Asigna el valor al campo strHoraApertura.
	 * @param strHoraApertura el valor strHoraApertura a asignar
	 */
	public void setStrHoraApertura(String strHoraApertura) {
		this.strHoraApertura = strHoraApertura;
	}

	/**
	 * Método de acceso al campo strFechaCierre.
	 * @return El valor del campo strFechaCierre
	 */
	public String getStrFechaCierre() {
		return strFechaCierre;
	}

	/**
	 * Asigna el valor al campo strFechaCierre.
	 * @param strFechaCierre el valor strFechaCierre a asignar
	 */
	public void setStrFechaCierre(String strFechaCierre) {
		this.strFechaCierre = strFechaCierre;
	}

	/**
	 * Método de acceso al campo strHoraCierre.
	 * @return El valor del campo strHoraCierre
	 */
	public String getStrHoraCierre() {
		return strHoraCierre;
	}

	/**
	 * Asigna el valor al campo strHoraCierre.
	 * @param strHoraCierre el valor strHoraCierre a asignar
	 */
	public void setStrHoraCierre(String strHoraCierre) {
		this.strHoraCierre = strHoraCierre;
	}

	/**
	 * Método de acceso al campo delitoPrincipal.
	 * @return El valor del campo delitoPrincipal
	 */
	public DelitoDTO getDelitoPrincipal() {
		return delitoPrincipal;
	}

	/**
	 * Asigna el valor al campo delitoPrincipal.
	 * @param delitoPrincipal el valor delitoPrincipal a asignar
	 */
	public void setDelitoPrincipal(DelitoDTO delitoPrincipal) {
		this.delitoPrincipal = delitoPrincipal;
	}

	/**
	 * Método de acceso al campo denunciantes.
	 * @return El valor del campo denunciantes
	 */
	public List<InvolucradoDTO> getDenunciantes() {
		return denunciantes;
	}

	/**
	 * Asigna el valor al campo denunciantes.
	 * @param denunciantes el valor denunciantes a asignar
	 */
	public void setDenunciantes(List<InvolucradoDTO> denunciantes) {
		this.denunciantes = denunciantes;
	}

	/**
	 * Método de acceso al campo probablesResponsablesPersona.
	 * @return El valor del campo probablesResponsablesPersona
	 */
	public List<InvolucradoDTO> getProbablesResponsablesPersona() {
		return probablesResponsablesPersona;
	}

	/**
	 * Asigna el valor al campo probablesResponsablesPersona.
	 * @param probablesResponsablesPersona el valor probablesResponsablesPersona a asignar
	 */
	public void setProbablesResponsablesPersona(
			List<InvolucradoDTO> probablesResponsablesPersona) {
		this.probablesResponsablesPersona = probablesResponsablesPersona;
	}

	/**
	 * Método de acceso al campo probablesResponsablesOrganizacion.
	 * @return El valor del campo probablesResponsablesOrganizacion
	 */
	public List<InvolucradoDTO> getProbablesResponsablesOrganizacion() {
		return probablesResponsablesOrganizacion;
	}

	/**
	 * Asigna el valor al campo probablesResponsablesOrganizacion.
	 * @param probablesResponsablesOrganizacion el valor probablesResponsablesOrganizacion a asignar
	 */
	public void setProbablesResponsablesOrganizacion(
			List<InvolucradoDTO> probablesResponsablesOrganizacion) {
		this.probablesResponsablesOrganizacion = probablesResponsablesOrganizacion;
	}

	/**
	 * Método de acceso al campo testigos.
	 * @return El valor del campo testigos
	 */
	public List<InvolucradoDTO> getTestigos() {
		return testigos;
	}

	/**
	 * Asigna el valor al campo testigos.
	 * @param testigos el valor testigos a asignar
	 */
	public void setTestigos(List<InvolucradoDTO> testigos) {
		this.testigos = testigos;
	}

	/**
	 * Método de acceso al campo traductores.
	 * @return El valor del campo traductores
	 */
	public List<InvolucradoDTO> getTraductores() {
		return traductores;
	}

	/**
	 * Asigna el valor al campo traductores.
	 * @param traductores el valor traductores a asignar
	 */
	public void setTraductores(List<InvolucradoDTO> traductores) {
		this.traductores = traductores;
	}

	/**
	 * Método de acceso al campo quienDetuvo.
	 * @return El valor del campo quienDetuvo
	 */
	public List<InvolucradoDTO> getQuienDetuvo() {
		return quienDetuvo;
	}

	/**
	 * Asigna el valor al campo quienDetuvo.
	 * @param quienDetuvo el valor quienDetuvo a asignar
	 */
	public void setQuienDetuvo(List<InvolucradoDTO> quienDetuvo) {
		this.quienDetuvo = quienDetuvo;
	}
	
	/**
	 * Obtiene el primer denunciante, si no hay denunciante se busca la primera
	 * organizción denunciante
	 * @return
	 */
	public InvolucradoDTO getPrimerDenunciante(){
		if(denunciantes != null && !denunciantes.isEmpty()){
			return denunciantes.get(0);
		}else{
			if(denunciantesOrganizacion != null && !denunciantesOrganizacion.isEmpty()){
				return denunciantesOrganizacion.get(0);
			}
		}
		return null;
	}
	/**
	 * Obtiene el primer denunciante, si no hay denunciante se busca la primera
	 * organizción denunciante
	 * @return
	 */
	public InvolucradoDTO getPrimerVictima(){
		// recorre la lista de denunciantes para verificar si alguno de ellos es victima
		if(denunciantes != null && !denunciantes.isEmpty()){
			for(InvolucradoDTO inv : denunciantes){
				if(inv.getCondicion() == 1)
					return inv;
			}
		}
		if(victimasPersona != null && !victimasPersona.isEmpty()){
			return victimasPersona.get(0);
		}else{
			if(victimasOrganizaciones != null && !victimasOrganizaciones.isEmpty()){
				return victimasOrganizaciones.get(0);
			}
		}
		return null;
	}
	
	public InvolucradoDTO getPrimerTestigo(){
		if(testigos != null && !testigos.isEmpty()){
			return testigos.get(0);
		}
		return null;
	}
	/**
	 * Obtiene el primer probable responsable, si no hay probable responsable busca
	 * la primera organización probable responsable
	 * @return
	 */
	public InvolucradoDTO getPrimerProbableResponsable(){
		
		if(probablesResponsablesPersona != null && !probablesResponsablesPersona.isEmpty()){
			return probablesResponsablesPersona.get(0);
		}else{
			if(probablesResponsablesOrganizacion != null && !probablesResponsablesOrganizacion.isEmpty()){
				return probablesResponsablesOrganizacion.get(0);
			}
		}
		return null;
		
	}
	
	/**
	 * Método de acceso al campo diaActual.
	 * @return El valor del campo diaActual
	 */
	public String getDiaActual() {
		return diaActual;
	}

	/**
	 * Asigna el valor al campo diaActual.
	 * @param diaActual el valor diaActual a asignar
	 */
	public void setDiaActual(String diaActual) {
		this.diaActual = diaActual;
	}

	/**
	 * Método de acceso al campo nombreMesActual.
	 * @return El valor del campo nombreMesActual
	 */
	public String getNombreMesActual() {
		return nombreMesActual;
	}

	/**
	 * Asigna el valor al campo nombreMesActual.
	 * @param nombreMesActual el valor nombreMesActual a asignar
	 */
	public void setNombreMesActual(String nombreMesActual) {
		this.nombreMesActual = nombreMesActual;
	}

	/**
	 * Método de acceso al campo anioActual.
	 * @return El valor del campo anioActual
	 */
	public String getAnioActual() {
		return anioActual;
	}

	/**
	 * Asigna el valor al campo anioActual.
	 * @param anioActual el valor anioActual a asignar
	 */
	public void setAnioActual(String anioActual) {
		this.anioActual = anioActual;
	}

	/**
	 * Método de acceso al campo usuario.
	 * @return El valor del campo usuario
	 */
	public UsuarioDTO getUsuario() {
		return usuario;
	}

	/**
	 * Asigna el valor al campo usuario.
	 * @param usuario el valor usuario a asignar
	 */
	public void setUsuario(UsuarioDTO usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return the audiencia
	 */
	public AudienciaDTO getAudiencia() {
		return audiencia;
	}

	/**
	 * @param audiencia the audiencia to set
	 */
	public void setAudiencia(AudienciaDTO audiencia) {
		this.audiencia = audiencia;
	}
        
        /**
	 * Método de acceso al campo nuc.
	 * @return El valor del campo nuc
	 */
	public String getNuc() {
		return nuc;
	}

	/**
	 * Asigna el valor al campo nuc.
	 * @param nuc el valor nuc a asignar
	 */
	public void setNuc(String nuc) {
		this.nuc = nuc;
	}
	
	public String getJuecesAudiencia(){
    	StringBuffer sbFuncionarios = new StringBuffer();
    	List<FuncionarioDTO> funcionarios = audiencia.getFuncionarios();
    	for(FuncionarioDTO funcionario : funcionarios){
    		if(funcionario.getPuesto().getIdCampo().equals(Puestos.JUEZ.getValorId())){
    			sbFuncionarios.append(funcionario.getNombreCompleto()+" ");
    		}
    	}
		return sbFuncionarios.toString();		
	}
		
	public String getProbablesResponsablesAudiencia(){
    	StringBuffer sbPB = new StringBuffer();
    	List<InvolucradoDTO> probablesResponsables = audiencia.getInvolucradoByCalidad(Calidades.PROBABLE_RESPONSABLE_PERSONA);
    	for(InvolucradoDTO pb : probablesResponsables){
    			sbPB.append(pb.getNombreCompleto()+" ");
    	}
		return sbPB.toString();		
	}
	
	public String getDefensoresAudiencia(){
    	StringBuffer sbFuncionarios = new StringBuffer();
    	List<FuncionarioDTO> funcionarios = audiencia.getFuncionarios();
    	for(FuncionarioDTO funcionario : funcionarios){
    		if(funcionario.getPuesto().getIdCampo().equals(Puestos.ABOGADO_DEFENSOR.getValorId())){
    			sbFuncionarios.append(funcionario.getNombreCompleto()+" ");
    		}
    	}
		return sbFuncionarios.toString();		
	}
	/**
	 * @return the nombresAgentesMinisterioPublicoAudiencia
	 */
	public String getNombresAgentesMinisterioPublicoAudiencia(){			
    	StringBuffer sbFuncionarios = new StringBuffer();
    	List<FuncionarioDTO> funcionarios = audiencia.getFuncionarios();
    	for(FuncionarioDTO funcionario : funcionarios){
    		if(funcionario.getPuesto().getIdCampo().equals(Puestos.AGENTE_DEL_MINISTERIO_PUBLICO.getValorId())){
    			sbFuncionarios.append(funcionario.getNombreCompleto()+" ");
    		}
    	}
		return sbFuncionarios.toString();		
	}
	
	/**
	 * @return the nombresProblablesResponsablesPersonaAudiencia
	 */
	public String getNombresProbablesResponsablesPersonaAudiencia() {

		StringBuffer sbPB = new StringBuffer();
		List<InvolucradoDTO> probablesResponsables = audiencia.getInvolucradoByCalidad(Calidades.PROBABLE_RESPONSABLE_PERSONA);
		for (InvolucradoDTO pb : probablesResponsables) {
			sbPB.append(pb.getNombreCompleto() + " ");
		}
		return sbPB.toString();
	}

	/**
	 * @return the nombresProblablesResponsablesOrganizacionAudiencia
	 */
	public String getNombresProblablesResponsablesOrganizacionAudiencia() {

		StringBuffer sbPB = new StringBuffer();
		List<InvolucradoDTO> probablesResponsables = audiencia.getInvolucradoByCalidad(Calidades.PROBABLE_RESPONSABLE_ORGANIZACION);
		for (InvolucradoDTO pb : probablesResponsables) {
			if (pb.getOrganizacionDTO() != null
					&& pb.getOrganizacionDTO().getNombreOrganizacion() != null) {
				sbPB.append(pb.getOrganizacionDTO().getNombreOrganizacion()
						+ " ");
			}
		}
		return sbPB.toString();
	}
	
	
    public String getResolutivosAudiencia(){
    	StringBuffer sbResolutivos = new StringBuffer();
    	sbResolutivos.append("<u>");   	
    	List<ResolutivoDTO> resolutivos = audiencia.getResolutivos();
    	//Collections.reverse(resolutivos);
    	Collections.sort(resolutivos, new Comparator<ResolutivoDTO>(){
            public int compare (ResolutivoDTO r1, ResolutivoDTO r2){
                return r1.getTemporizador().compareTo(r2.getTemporizador());
            }
        });
    	for(ResolutivoDTO resolutivo : resolutivos){
    		sbResolutivos.append("<li><strong>"+DateUtils.formatearHora(resolutivo.getTemporizador())+"</strong>");
    		sbResolutivos.append(" - "+resolutivo.getDetalle()+"</li>");
    	}
    	sbResolutivos.append("</ul>");
		return sbResolutivos.toString();
    }

	/**
	 * @return the listaDelitos
	 */
	public String getListaDelitos() {
		return listaDelitos;
	}

	
	/**
	 * @param listaDelitos the listaDelitos to set
	 */
	public void setListaDelitos(String listaDelitos) {
		this.listaDelitos = listaDelitos;
	}

	/**
	 * @return the responsableTramite
	 */
	public FuncionarioDTO getResponsableTramite() {
		return responsableTramite;
	}

	/**
	 * @param responsableTramite the propietarioTramite to set
	 */
	public void setResponsableTramite(FuncionarioDTO responsableTramite) {
		this.responsableTramite = responsableTramite;
	}

	/**
	 * @return the nombresProblablesResponsablesPersonaExpediente
	 */
	public String getNombresProbablesResponsablesPersonaExpediente() {

		StringBuffer sbPB = new StringBuffer();
		List<InvolucradoDTO> probablesResponsables = getProbablesResponsablesPersona();
		for (InvolucradoDTO pb : probablesResponsables) {
			sbPB.append(pb.getNombreCompleto() + " ");
		}
		return sbPB.toString();
	}
	
	public String getNombresVictimasPersonaExpediente() {
		
		StringBuffer sbPB = new StringBuffer();
		List<InvolucradoDTO> involucrados = null;
		
		// recorre la lista de denunciantes para verificar si alguno de ellos es victima
		if(denunciantes != null && !denunciantes.isEmpty()){
			involucrados =  new ArrayList<InvolucradoDTO>();
			for(InvolucradoDTO inv : denunciantes){
				if(inv.getCondicion() == 1)
					involucrados.add(inv);
			}
		}
		
		if(victimasPersona != null && !victimasPersona.isEmpty()){
			if(involucrados != null && !involucrados.isEmpty())
				involucrados.addAll(victimasPersona);
			else
				involucrados = victimasPersona;
		}
		
		if(involucrados != null && !involucrados.isEmpty()){
			for(InvolucradoDTO involucrado : involucrados){
				if(involucrado != null){					
					sbPB.append(involucrado.getNombreCompleto() + " ");
				}
			}
		}
		return sbPB.toString();
	}

	/**
	 * @return the nombresProblablesResponsablesOrganizacionExpediente
	 */
	public String getNombresProblablesResponsablesOrganizacionExpediente() {

		StringBuffer sbPB = new StringBuffer();
		List<InvolucradoDTO> probablesResponsables = getProbablesResponsablesOrganizacion();
		for (InvolucradoDTO pb : probablesResponsables) {
			if (pb.getOrganizacionDTO() != null
					&& pb.getOrganizacionDTO().getNombreOrganizacion() != null) {
				sbPB.append(pb.getOrganizacionDTO().getNombreOrganizacion()
						+ " ");
			}
		}
		return sbPB.toString();
	}

	/**
	 * @return the nombresDenunciantesPersonaExpediente
	 */
	public String getNombresDenunciantesPersonaExpediente() {
		
		StringBuffer sbDenunciante = new StringBuffer();
		List<InvolucradoDTO> denunciantes = getDenunciantes();
		for (InvolucradoDTO denunciante : denunciantes) {
			sbDenunciante.append(denunciante.getNombreCompleto() + " ");
		}
		return sbDenunciante.toString();
	}

	/**
	 * @return the nombresDenunciantesOrganizacionExpediente
	 */
	public String getNombresDenunciantesOrganizacionExpediente() {
		StringBuffer sbDenuncianteOrg = new StringBuffer();
		List<InvolucradoDTO> denunciantesOrganizacion = getDenunciantesOrganizacion();
		for (InvolucradoDTO denuncianteOrg : denunciantesOrganizacion) {
			if (denuncianteOrg.getOrganizacionDTO() != null
					&& denuncianteOrg.getOrganizacionDTO().getNombreOrganizacion() != null) {
				sbDenuncianteOrg.append(denuncianteOrg.getOrganizacionDTO().getNombreOrganizacion()
						+ " ");
			}
		}
		return sbDenuncianteOrg.toString();
	}
	
//	public List<InvolucradoDTO> getDenuncianteByIndex(){
//		
//		if(denunciantes != null && !denunciantes.isEmpty()){
//			return denunciantes;
//		}else{
//			if(denunciantesOrganizacion != null && !denunciantesOrganizacion.isEmpty()){
//				return denunciantesOrganizacion;
//			}
//		}
//		return null;
//	}
//							  
//	public List<InvolucradoDTO> getVictimaByIndex(){
//		
//		if(victimasPersona != null && !victimasPersona.isEmpty()){
//			return victimasPersona;
//		}else{
//			if(victimasOrganizaciones != null && !victimasOrganizaciones.isEmpty()){
//				return victimasOrganizaciones;
//			}
//		}
//		return null;
//	}
	
	/**
	 * Obtiene la leyenda 1 con los datos del denuncuante concatenados
	 * Ej: (nombreCompletoDenunciante), quien se identifica con (tipoDocumentoIdentificacionDenunciante),
	 * 		 (folioDocumentoIdentificacionDenunciante)
	 * @return leyenda1 con los datos del denunciante incorporados
	 */
	public String getDenuncianteLeyenda1(){
		StringBuffer denunciante = new StringBuffer();
		List<InvolucradoDTO> involucrados = null;
		
		// para evitar copia exacta del codigo se asigna la lista no vacia al nuevo objeto
		if( denunciantes!= null && !denunciantes.isEmpty()){
			involucrados = denunciantes;
		}else{
			if(denunciantesOrganizacion != null && !denunciantesOrganizacion.isEmpty()){
				involucrados = denunciantesOrganizacion;
			}
		}
		
		if(involucrados != null && !involucrados.isEmpty()){
			
			int size = involucrados.size();
			int cont = 0;
			
			for(InvolucradoDTO involucrado : involucrados){
				if(involucrado != null){
					
					// se obtienen los datos del involucrado
					String tipoDocumento = involucrado.getValorIdIdentificaion() != null && StringUtils.isNotBlank(involucrado.getValorIdIdentificaion().getValor()) ?
							"<b>" + involucrado.getValorIdIdentificaion().getValor() + "</b>": "<b>&lt;Tipo documento no encotrado&gt;</b>";
					String foliodocumento = StringUtils.isNotBlank(involucrado.getFolioIdentificacion()) ? 
							"<b>" + involucrado.getFolioIdentificacion() + "</b>" : "<b>&lt;Folio documento no encotrado&gt;</b>";
					
					// concatena el denunciante validado a la cadena de retorno
					cont++;
					denunciante.append("<b>" + involucrado.getNombreCompleto() + "</b>,");
					denunciante.append(" quien se identifica con " + tipoDocumento);
					denunciante.append(", " + foliodocumento);
					denunciante.append(cont < size ? "; " : ".");
					
				}
			}
		}
		
		return denunciante.toString();
	}
	
	/**
	 * Obtiene la leyenda 2 con los datos del denuncuante concatenados
	 * Ej: (nombreCompletoDenunciante), de nacionalidad _____, de (edadDenunciante) AÑOS de edad, 
	 * 		estado civil (estadoCivilDenunciante) , con instrucción (escolaridadDenunciante) , 
	 * 		y por lo tanto SI sé leer y escribir, de ocupación (ocupacionDenunciante), originario, 
	 * 		y vecino de (PaisDenunciante) y vecino de (EstadoDenunciante)  con domicilio en la 
	 * 		calle (domicilioCalleDenunciante) número (domicilioNumeroDenunciante) de la 
	 * 		colonia (domicilioColoniaDenunciante), teléfono (telefonoDenunciante)
	 * @return leyenda2 con los datos del denunciante incorporados
	 */
	public String getDenuncianteLeyenda2(){
		StringBuffer denunciante = new StringBuffer();
		List<InvolucradoDTO> involucrados = null;
		
		// para evitar copia exacta del codigo se asigna la lista no vacia al nuevo objeto
		if( denunciantes!= null && !denunciantes.isEmpty()){
			involucrados = denunciantes;
		}else{
			if(denunciantesOrganizacion != null && !denunciantesOrganizacion.isEmpty()){
				involucrados = denunciantesOrganizacion;
			}
		}
		
		if(involucrados != null && !involucrados.isEmpty()){
			
			int size = involucrados.size();
			int cont = 0;
			
			for(InvolucradoDTO involucrado : involucrados){
				if(involucrado != null){
					
					// se obtienen los datos del involucrado
					String edoCivil = involucrado.getValorIdEstadoCivil() != null && StringUtils.isNotBlank(involucrado.getValorIdEstadoCivil().getValor()) ? 
							"<b>" + involucrado.getValorIdEstadoCivil().getValor() + "</b>" : "<b>&lt;Estado civil no encotrado&gt;</b>";
					String edad = involucrado.getPrimerNombreDemografico().getEdadAproximada() != null && involucrado.getPrimerNombreDemografico().getEdadAproximada() > 0 ?
							"<b>" + String.valueOf(involucrado.getPrimerNombreDemografico().getEdadAproximada() + "</b>") : "<b>&lt;Edad no encontrada&gt;</b>";
					String escolaridad = involucrado.getValorIdEscolaridad() != null && StringUtils.isNotBlank(involucrado.getValorIdEscolaridad().getValor()) ?
							"<b>" + involucrado.getValorIdEscolaridad().getValor() + "</b>": "<b>&lt;Escolaridad no encontrada&gt;</b>";
					//se obtienen las o la ocupacion
					StringBuffer ocupacion = new StringBuffer();
					if(involucrado.getValorIdOcupacion() != null && !involucrado.getValorIdOcupacion().isEmpty()){
						ocupacion.append(involucrado.getValorIdOcupacion().size() > 1 ? " de ocupaciones " : " de ocupaci\u00f3n ");
						int contDto = 0;
						for(ValorDTO dto : involucrado.getValorIdOcupacion()){
							contDto++;
							ocupacion.append(StringUtils.isNotBlank(dto.getValor()) ? "<b>" + dto.getValor() + "</b>" : "");
							ocupacion.append(contDto < involucrado.getValorIdOcupacion().size() ? "," : "");
						}
						
					}
					// se obtiene la direccion por separado
					String pais = involucrado.getPrimerNombreDemografico().getPaisValorDTO() != null && StringUtils.isNotBlank(involucrado.getPrimerNombreDemografico().getPaisValorDTO().getValor()) ?
							"<b>" + involucrado.getPrimerNombreDemografico().getPaisValorDTO().getValor() + "</b>": "<b>&lt;Pais no encontrado&gt;</b>";
					String edo =  involucrado.getDomicilio() != null && involucrado.getDomicilio().getEntidadDTO() != null && 
							StringUtils.isNotBlank(involucrado.getDomicilio().getEntidadDTO().getNombreEntidad()) ?
							"<b>" + involucrado.getDomicilio().getEntidadDTO().getNombreEntidad() + "</b>" : "<b>&lt;Estado no encontrado&gt;</b>";
					String calle = involucrado.getDomicilio() != null && StringUtils.isNotBlank(involucrado.getDomicilio().getCalle()) ? 
							"<b>" + involucrado.getDomicilio().getCalle() + "</b>": "<b>&lt;Calle no encontrada&gt;</b>";
					String numIn = involucrado.getDomicilio() != null && StringUtils.isNotBlank(involucrado.getDomicilio().getNumeroInterior()) ? 
							"<b>" + involucrado.getDomicilio().getNumeroInterior() + "</b>": "<b>&lt;Numero no encontrado&gt;</b>";
					String numExt = involucrado.getDomicilio() != null && StringUtils.isNotBlank(involucrado.getDomicilio().getNumeroExterior()) ? 
							"<b>" + involucrado.getDomicilio().getNumeroExterior() + "</b>": "<b>&lt;Numero no encontrado&gt;</b>";
					String colonia = involucrado.getDomicilio() != null && involucrado.getDomicilio().getAsentamientoDTO() != null &&
							StringUtils.isNotBlank(involucrado.getDomicilio().getAsentamientoDTO().getNombreAsentamiento()) ? 
							"<b>" + involucrado.getDomicilio().getAsentamientoDTO().getNombreAsentamiento() + "</b>": "<b>&lt;Colonia no encontrada&gt;</b>";
					// obtenemos el primer telefono
					StringBuffer tel = new StringBuffer();
					if(involucrado.getTelefonosDTO() != null && !involucrado.getTelefonosDTO().isEmpty()){
						tel.append(StringUtils.isNotBlank(involucrado.getTelefonosDTO().get(0).getCodigoPais()) ? involucrado.getTelefonosDTO().get(0).getCodigoPais() + " " : "");
						tel.append(StringUtils.isNotBlank(involucrado.getTelefonosDTO().get(0).getCodigoArea()) ? involucrado.getTelefonosDTO().get(0).getCodigoArea() + " ": "");
						tel.append(StringUtils.isNotBlank(involucrado.getTelefonosDTO().get(0).getNumeroTelefonico()) ? involucrado.getTelefonosDTO().get(0).getNumeroTelefonico() : "");
						
					}
					
					// concatena el denunciante validado a la cadena de retorno
					cont++;
					denunciante.append("<b>" + involucrado.getNombreCompleto() + "</b>,");
					denunciante.append(" de nacionalidad ____________, de " + edad + " A\u00d1OS de edad, estado civil " + edoCivil);
					denunciante.append(", con instrucci\u00f3n " + escolaridad + ", y por lo tanto SI s\u00e9 leer y escribir, ");
					denunciante.append(StringUtils.isNotBlank(ocupacion.toString()) ? ocupacion.toString() : "<b>&lt;Ocupaci\u00f3n no encontrado&gt;</b>");
					denunciante.append(", originario, y vecino de " + pais + " y vecino de " + edo + " con domicilio en la calle ");
					denunciante.append(calle + " n\u00famero interior " + numIn + ", exterior " + numExt + " de la colonia " + colonia + ", tel\u00e9fono ");
					denunciante.append(StringUtils.isNotBlank(tel.toString()) ? "<b>" + tel.toString() + "</b>": "<b>&lt;Telefono no encontrado&gt;</b>");
					
					denunciante.append(cont < size ? "; " : ".");
					
				}
			}
		}
		
		return denunciante.toString();
	}
	
	/**
	 * Obtiene la leyenda 3 con los datos del denuncuante concatenados
	 * Ej: (PaisDenunciante) y vecino de (EstadoDenunciante) con domicilio en la calle (domicilioCalleDenunciante) 
	 * 		número (domicilioNumeroDenunciante) de la colonia (domicilioColoniaDenunciante) (domicilioCiudadDenunciante) , 
	 * 		(domicilioEstadoDenunciante), quien cuenta con (edadDenunciante) años, de estado civil (estadoCivilDenunciante), 
	 * 		de ocupación (ocupacionDenunciante), que si sabe leer y escribir por haber cursado instrucción 
	 * 		(escolaridadDenunciante), quien proporciona el número telefónico (telefonoDenunciante) para ser localizado
	 * @return leyenda3 con los datos del denunciante incorporados
	 */
	public String getDenuncianteLeyenda3(){
		StringBuffer denunciante = new StringBuffer();
		List<InvolucradoDTO> involucrados = null;
		
		// para evitar copia exacta del codigo se asigna la lista no vacia al nuevo objeto
		if( denunciantes!= null && !denunciantes.isEmpty()){
			involucrados = denunciantes;
		}else{
			if(denunciantesOrganizacion != null && !denunciantesOrganizacion.isEmpty()){
				involucrados = denunciantesOrganizacion;
			}
		}
		
		if(involucrados != null && !involucrados.isEmpty()){
			
			int size = involucrados.size();
			int cont = 0;
			
			for(InvolucradoDTO involucrado : involucrados){
				if(involucrado != null){
					
					// se obtienen los datos del involucrado
					String edoCivil = involucrado.getValorIdEstadoCivil() != null && StringUtils.isNotBlank(involucrado.getValorIdEstadoCivil().getValor()) ? 
							"<b>" + involucrado.getValorIdEstadoCivil().getValor() + "</b>" : "<b>&lt;Estado civil no encotrado&gt;</b>";
					String edad = involucrado.getPrimerNombreDemografico().getEdadAproximada() != null && involucrado.getPrimerNombreDemografico().getEdadAproximada() > 0 ?
							"<b>" + String.valueOf(involucrado.getPrimerNombreDemografico().getEdadAproximada() + "</b>") : "<b>&lt;Edad no encontrada&gt;</b>";
					String escolaridad = involucrado.getValorIdEscolaridad() != null && StringUtils.isNotBlank(involucrado.getValorIdEscolaridad().getValor()) ?
							"<b>" + involucrado.getValorIdEscolaridad().getValor() + "</b>": "<b>&lt;Escolaridad no encontrada&gt;</b>";
					//se obtienen las o la ocupacion
					StringBuffer ocupacion = new StringBuffer();
					if(involucrado.getValorIdOcupacion() != null && !involucrado.getValorIdOcupacion().isEmpty()){
						ocupacion.append(involucrado.getValorIdOcupacion().size() > 1 ? " de ocupaciones " : " de ocupaci\u00f3n ");
						int contDto = 0;
						for(ValorDTO dto : involucrado.getValorIdOcupacion()){
							contDto++;
							ocupacion.append(StringUtils.isNotBlank(dto.getValor()) ? "<b>" + dto.getValor() + "</b>" : "");
							ocupacion.append(contDto < involucrado.getValorIdOcupacion().size() ? "," : "");
						}
						
					}
					// se obtiene la direccion por separado
					String pais = involucrado.getPrimerNombreDemografico().getPaisValorDTO() != null && StringUtils.isNotBlank(involucrado.getPrimerNombreDemografico().getPaisValorDTO().getValor()) ?
							"<b>" + involucrado.getPrimerNombreDemografico().getPaisValorDTO().getValor() + "</b>": "<b>&lt;Pais no encontrado&gt;</b>";
					String edo =  involucrado.getDomicilio() != null && involucrado.getDomicilio().getEntidadDTO() != null && 
							StringUtils.isNotBlank(involucrado.getDomicilio().getEntidadDTO().getNombreEntidad()) ?
							"<b>" + involucrado.getDomicilio().getEntidadDTO().getNombreEntidad() + "</b>" : "<b>&lt;Estado no encontrado&gt;</b>";
					String ciudad = involucrado.getDomicilio() != null && involucrado.getDomicilio().getCiudadDTO() != null &&
							StringUtils.isNotBlank(involucrado.getDomicilio().getCiudadDTO().getNombreCiudad()) ? 
							"<b>" + involucrado.getDomicilio().getCiudadDTO().getNombreCiudad() + "</b>" : "<b>&lt;Ciudad no encontrada&gt;</b>";
					String calle = involucrado.getDomicilio() != null && StringUtils.isNotBlank(involucrado.getDomicilio().getCalle()) ? 
							"<b>" + involucrado.getDomicilio().getCalle() + "</b>": "<b>&lt;Calle no encontrada&gt;</b>";
					String numIn = involucrado.getDomicilio() != null && StringUtils.isNotBlank(involucrado.getDomicilio().getNumeroInterior()) ? 
							"<b>" + involucrado.getDomicilio().getNumeroInterior() + "</b>": "<b>&lt;Numero interior no encontrado&gt;</b>";
					String numExt = involucrado.getDomicilio() != null && StringUtils.isNotBlank(involucrado.getDomicilio().getNumeroExterior()) ? 
							"<b>" + involucrado.getDomicilio().getNumeroExterior() + "</b>": "<b>&lt;Numero exterior no encontrado&gt;</b>";
					String colonia = involucrado.getDomicilio() != null && involucrado.getDomicilio().getAsentamientoDTO() != null &&
							StringUtils.isNotBlank(involucrado.getDomicilio().getAsentamientoDTO().getNombreAsentamiento()) ? 
							"<b>" + involucrado.getDomicilio().getAsentamientoDTO().getNombreAsentamiento() + "</b>": "<b>&lt;Colonia no encontrada&gt;</b>";
					// obtenemos el primer telefono
					StringBuffer tel = new StringBuffer();
					if(involucrado.getTelefonosDTO() != null && !involucrado.getTelefonosDTO().isEmpty()){
						tel.append(StringUtils.isNotBlank(involucrado.getTelefonosDTO().get(0).getCodigoPais()) ? involucrado.getTelefonosDTO().get(0).getCodigoPais() + " " : "");
						tel.append(StringUtils.isNotBlank(involucrado.getTelefonosDTO().get(0).getCodigoArea()) ? involucrado.getTelefonosDTO().get(0).getCodigoArea() + " ": "");
						tel.append(StringUtils.isNotBlank(involucrado.getTelefonosDTO().get(0).getNumeroTelefonico()) ? involucrado.getTelefonosDTO().get(0).getNumeroTelefonico() : "");
						
					}
					
					// concatena el denunciante validado a la cadena de retorno
					cont++;
					denunciante.append(pais + " y vecino de " + edo + " con domicilio en la calle " + calle);
					denunciante.append(" n\u00famero interior " + numIn + ", exterior " + numExt + " de la colonia " + colonia + " " + ciudad );
					denunciante.append(", " + edo + ", quien cuenta con " + edad + " a\u00f1os, estado civil " + edoCivil + ", ");
					denunciante.append(StringUtils.isNotBlank(ocupacion.toString()) ? ocupacion.toString() : "<b>&lt;Ocupaci\u00f3n no encontrado&gt;</b>");
					denunciante.append(", que si sabe leer y escribir por haber cursado instrucci\u00f3n " + escolaridad);
					denunciante.append(", quien proporciona el n\u00famero telef\u00f3nico ");
					denunciante.append(StringUtils.isNotBlank(tel.toString()) ? "<b>" + tel.toString() + "</b>": "<b>&lt;Telefono no encontrado&gt;</b>");
					denunciante.append(" para ser localizado");
					
					denunciante.append(cont < size ? "; " : ".");
					
				}
			}
		}
		
		return denunciante.toString();
	}
	
	/**
	 * Obtiene la lista de probables responsables concatenados y redactads segun la leyendad dada
	 * Ej: (nombreCompleto) de estado civil (estadoCivil), 
	 * 		escolaridad (escolaridad) y ocupación (ocupación);
	 * @return leyenda con los probales responsables concatenados
	 */
	public String getProbablesResponsablesLeyenda(){
		StringBuffer probablesResponsables = new StringBuffer();
		List<InvolucradoDTO> involucrados = null;
		
		// para evitar copia exacta del codigo se asigna la lista no vacia al nuevo objeto
		if( probablesResponsablesPersona!= null && !probablesResponsablesPersona.isEmpty()){
			involucrados = probablesResponsablesPersona;
		}else{
			if(probablesResponsablesOrganizacion != null && !probablesResponsablesOrganizacion.isEmpty()){
				involucrados = probablesResponsablesOrganizacion;
			}
		}
		
		if(involucrados != null && !involucrados.isEmpty()){
			
			int size = involucrados.size();
			int cont = 0;
			
//			probablesResponsables.append(size > 1 ? "Los probables responsables: " : "El probable responsable: ");
			for(InvolucradoDTO involucrado : involucrados){
				if(involucrado != null){
					
					// se obtienen los datos del involucrado
					String edoCivil = involucrado.getValorIdEstadoCivil() != null && StringUtils.isNotBlank(involucrado.getValorIdEstadoCivil().getValor()) ? 
							" de estado civil <b>" + involucrado.getValorIdEstadoCivil().getValor() + "</b>": "";
					String escolaridad = involucrado.getValorIdEscolaridad() != null && StringUtils.isNotBlank(involucrado.getValorIdEscolaridad().getValor()) ?
							" escolaridad <b>" + involucrado.getValorIdEscolaridad().getValor() + "</b>" : "";
					//se obtienen las o la ocupacion
					StringBuffer ocupacion = new StringBuffer();
					if(involucrado.getValorIdOcupacion() != null && !involucrado.getValorIdOcupacion().isEmpty()){
						ocupacion.append(involucrado.getValorIdOcupacion().size() > 1 ? " y ocupaciones " : " y ocupaci\u00f3n ");
						int contDto = 0;
						for(ValorDTO dto : involucrado.getValorIdOcupacion()){
							contDto++;
							ocupacion.append(StringUtils.isNotBlank(dto.getValor()) ? "<b>" + dto.getValor() + "</b>" : "");
							ocupacion.append(contDto < involucrado.getValorIdOcupacion().size() ? "," : "");
						}
					}
					
					// concatena el probable responsable validado a la cadena de retorno
					cont++;
					probablesResponsables.append("<b>" + involucrado.getNombreCompleto() + "</b>");
					probablesResponsables.append(edoCivil).append(StringUtils.isNotBlank(escolaridad) ? "," : "");
					probablesResponsables.append(escolaridad);
					probablesResponsables.append(ocupacion.toString());
					probablesResponsables.append(cont < size ? "; " : ".");
					
				}
			}
		}
		
		return probablesResponsables.toString();
	}
	
	/**
	 * Obtiene la lista de victimas concatenadas y redactadas segun la leyendad dada
	 * Ej: (nombreCompleto) de estado civil (estadoCivil), edad (edad), 
	 * 		domicilio (domicilio), escolaridad (escolaridad), ocupación (ocupación) 
	 * 		y que presenta el documento (tipoDocumento) para identificarse;  
	 * 		(nombreCompleto) de estado civil...
	 * @return leyenda con las victimas concatenadas
	 */
	public String getVictimasLeyenda(){
		StringBuffer victimas = new StringBuffer();
		List<InvolucradoDTO> involucrados = null;
		
		// recorre la lista de denunciantes para verificar si alguno de ellos es victima
		if(denunciantes != null && !denunciantes.isEmpty()){
			involucrados =  new ArrayList<InvolucradoDTO>();
			for(InvolucradoDTO inv : denunciantes){
				if(inv.getCondicion() == 1)
					involucrados.add(inv);
			}
		}
		
		// para evitar copia exacta del codigo se asigna la lista no vacia al nuevo objeto
		if(victimasPersona != null && !victimasPersona.isEmpty()){
			if(involucrados != null && !involucrados.isEmpty())
				involucrados.addAll(victimasPersona);
			else
				involucrados = victimasPersona;
		}else{
			if(victimasOrganizaciones != null && !victimasOrganizaciones.isEmpty()){
				if(involucrados != null && !involucrados.isEmpty())
					involucrados.addAll(victimasOrganizaciones);
				else
					involucrados = victimasOrganizaciones;
			}
		}
		
		if(involucrados != null && !involucrados.isEmpty()){
			
			int size = involucrados.size();
			int cont = 0;
			
//			victimas.append(size > 1 ? "Las v\u00edctimas: " : "La v\u00edctima: ");
			for(InvolucradoDTO involucrado : involucrados){
				if(involucrado != null){
					
					// se obtienen los datos del involucrado
					String edoCivil = involucrado.getValorIdEstadoCivil() != null && StringUtils.isNotBlank(involucrado.getValorIdEstadoCivil().getValor()) ? 
							" de estado civil <b>" + involucrado.getValorIdEstadoCivil().getValor() + "</b>": "";
					String edad = involucrado.getPrimerNombreDemografico().getEdadAproximada() != null && involucrado.getPrimerNombreDemografico().getEdadAproximada() > 0 ?
							" edad aproximada <b>" + String.valueOf(involucrado.getPrimerNombreDemografico().getEdadAproximada()) + "</b> a\u00f1os" : "";
					String domicilio = involucrado.getDomicilio() != null && StringUtils.isNotBlank(involucrado.getDomicilio().getDireccion()) ?
							" domicilio <b>" + involucrado.getDomicilio().getDireccion() + "</b>" : "";
					String escolaridad = involucrado.getValorIdEscolaridad() != null && StringUtils.isNotBlank(involucrado.getValorIdEscolaridad().getValor()) ?
							" escolaridad <b>" + involucrado.getValorIdEscolaridad().getValor() + "</b>" : "";
					//se obtienen las o la ocupacion
					StringBuffer ocupacion = new StringBuffer();
					if(involucrado.getValorIdOcupacion() != null && !involucrado.getValorIdOcupacion().isEmpty()){
						ocupacion.append(involucrado.getValorIdOcupacion().size() > 1 ? " ocupaciones " : " ocupaci\u00f3n ");
						int contDto = 0;
						for(ValorDTO dto : involucrado.getValorIdOcupacion()){
							contDto++;
							ocupacion.append(StringUtils.isNotBlank(dto.getValor()) ? "<b>" + dto.getValor() + "</b>" : "");
							ocupacion.append(contDto < involucrado.getValorIdOcupacion().size() ? "," : "");
						}
					}
					
					String tipoDocumento = involucrado.getValorIdIdentificaion() != null && StringUtils.isNotBlank(involucrado.getValorIdIdentificaion().getValor()) ?
							" y que presenta el documento <b>" + involucrado.getValorIdIdentificaion().getValor() + "</b> para identificarse" : "";
					
					// concatena la victima validada a la cadena de retorno
					cont++;
					victimas.append("<b>" + involucrado.getNombreCompleto() + "</b>");
					victimas.append(edoCivil).append(StringUtils.isNotBlank(edad) ? "," : "");
					victimas.append(edad).append(StringUtils.isNotBlank(domicilio) ? "," : "");
					victimas.append(domicilio).append(StringUtils.isNotBlank(escolaridad) ? "," : "");
					victimas.append(escolaridad).append(StringUtils.isNotBlank(ocupacion.toString()) ? "," : "");
					victimas.append(ocupacion.toString());
					victimas.append(tipoDocumento);
					victimas.append(cont < size ? "; " : ".");
					
				}
			}
		}
		
		return victimas.toString();
	}
	
	/**
	 * Obtiene la lista de testigos concatenados y redactados segun la leyendad dada
	 * Ej: (nombreCompleto) de edad (edad), domicilio (domicilio) 
	 * 		y que presenta el documento (tipoDocumento) para identificarse;
	 * @return leyenda con los testigos concatenados
	 */
	public String getTestigosLeyenda(){
		StringBuffer testigosBf = new StringBuffer();		
		
		if(testigos != null && !testigos.isEmpty()){
			
			int size = testigos.size();
			int cont = 0;
			
//			testigosBf.append(size > 1 ? "Los testigos: " : "El testigo: ");
			for(InvolucradoDTO involucrado : testigos){
				if(involucrado != null){
					
					// se obtienen los datos del involucrado
					String edad = involucrado.getPrimerNombreDemografico().getEdadAproximada() != null && involucrado.getPrimerNombreDemografico().getEdadAproximada() > 0 ?
							" de edad aproximada <b>" + String.valueOf(involucrado.getPrimerNombreDemografico().getEdadAproximada()) + "</b> a\u00f1os" : "";
					String domicilio = involucrado.getDomicilio() != null && StringUtils.isNotBlank(involucrado.getDomicilio().getDireccion()) ?
							" domicilio <b>" + involucrado.getDomicilio().getDireccion() + "</b>" : "";					
					String tipoDocumento = involucrado.getValorIdIdentificaion() != null && StringUtils.isNotBlank(involucrado.getValorIdIdentificaion().getValor()) ?
							" y que presenta el documento <b>" + involucrado.getValorIdIdentificaion().getValor() + "</b> para identificarse" : "";
					
					// concatena el testigo validado a la cadena de retorno
					cont++;
					testigosBf.append("<b>" + involucrado.getNombreCompleto() + "</b>");
					testigosBf.append(edad).append(StringUtils.isNotBlank(domicilio) ? "," : "");
					testigosBf.append(domicilio);
					testigosBf.append(tipoDocumento);
					testigosBf.append(cont < size ? "; " : ".");
					
				}
			}
		}
		
		return testigosBf.toString();
	}
	
	public String getVehiculos(){
		StringBuffer vehiculos = new StringBuffer();
		
		if(grupoObjetosExpediente != null && !grupoObjetosExpediente.isEmpty()){
			GrupoObjetosExpedienteDTO grupoVehiculos = grupoObjetosExpediente.get(0);
			if(grupoVehiculos != null){
				if(grupoVehiculos.getObjetos() != null && !grupoVehiculos.getObjetos().isEmpty()){
					
					int numVeiculo = 1;
					for(ObjetoResumenDTO dto : grupoVehiculos.getObjetos()){
						vehiculos.append("Vehiculo No. " + numVeiculo + ":<br>");
						vehiculos.append(dto.getDescripcionResumen() + "<br>");
						numVeiculo++;
					}
				}
			}
		}
		
		return vehiculos.toString();
	}
	
	public String getEquipoComputo(){
		StringBuffer equipoComp = new StringBuffer();
		
		if(grupoObjetosExpediente != null && !grupoObjetosExpediente.isEmpty()){
			GrupoObjetosExpedienteDTO grupoEquipoComputo = grupoObjetosExpediente.get(1);
			if(grupoEquipoComputo != null){
				if(grupoEquipoComputo.getObjetos() != null && !grupoEquipoComputo.getObjetos().isEmpty()){
					
					int numEquipo = 1;
					for(ObjetoResumenDTO dto : grupoEquipoComputo.getObjetos()){
						equipoComp.append("Equipo de C\u00f3mputo No. " + numEquipo + ":<br>");
						equipoComp.append(dto.getDescripcionResumen() + "<br>");
						numEquipo++;
					}
				}
			}
		}
		
		return equipoComp.toString();
	}
	
	public String getTelefonia(){
		StringBuffer telefonia = new StringBuffer();
		
		if(grupoObjetosExpediente != null && !grupoObjetosExpediente.isEmpty()){
			GrupoObjetosExpedienteDTO grupoTelefonia = grupoObjetosExpediente.get(2);
			if(grupoTelefonia != null){
				if(grupoTelefonia.getObjetos() != null && !grupoTelefonia.getObjetos().isEmpty()){
					
					int numTelefo = 1;
					for(ObjetoResumenDTO dto : grupoTelefonia.getObjetos()){
						telefonia.append("Equipo de Telef\u00f3nico No. " + numTelefo + ":<br>");
						telefonia.append(dto.getDescripcionResumen() + "<br>");
						numTelefo++;
					}
				}
			}
		}
		
		return telefonia.toString();
	}
	
	public String getArma(){
		StringBuffer arma = new StringBuffer();
		
		if(grupoObjetosExpediente != null && !grupoObjetosExpediente.isEmpty()){
			GrupoObjetosExpedienteDTO grupoArma = grupoObjetosExpediente.get(3);
			if(grupoArma != null){
				if(grupoArma.getObjetos() != null && !grupoArma.getObjetos().isEmpty()){
					
					int numArma = 1;
					for(ObjetoResumenDTO dto : grupoArma.getObjetos()){
						arma.append("Arma No. " + numArma + ":<br>");
						arma.append(dto.getDescripcionResumen() + "<br>");
						numArma++;
					}
				}
			}
		}
		
		return arma.toString();
	}
	
	public String getExplosivo(){
		StringBuffer explosivo = new StringBuffer();
		
		if(grupoObjetosExpediente != null && !grupoObjetosExpediente.isEmpty()){
			GrupoObjetosExpedienteDTO grupoExpl = grupoObjetosExpediente.get(4);
			if(grupoExpl != null){
				if(grupoExpl.getObjetos() != null && !grupoExpl.getObjetos().isEmpty()){
					
					int numExpl = 1;
					for(ObjetoResumenDTO dto : grupoExpl.getObjetos()){
						explosivo.append("Explosivo No. " + numExpl + ":<br>");
						explosivo.append(dto.getDescripcionResumen() + "<br>");
						numExpl++;
					}
				}
			}
		}
		
		return explosivo.toString();
	}
	
	public String getAeronave(){
		StringBuffer aeronave = new StringBuffer();
		
		if(grupoObjetosExpediente != null && !grupoObjetosExpediente.isEmpty()){
			GrupoObjetosExpedienteDTO grupoAeronave = grupoObjetosExpediente.get(5);
			if(grupoAeronave != null){
				if(grupoAeronave.getObjetos() != null && !grupoAeronave.getObjetos().isEmpty()){
					
					int numAero = 1;
					for(ObjetoResumenDTO dto : grupoAeronave.getObjetos()){
						aeronave.append("Aeronave No. " + numAero + ":<br>");
						aeronave.append(dto.getDescripcionResumen() + "<br>");
						numAero++;
					}
				}
			}
		}
		
		return aeronave.toString();
	}
	
	public String getAnimal(){
		StringBuffer animal = new StringBuffer();
		
		if(grupoObjetosExpediente != null && !grupoObjetosExpediente.isEmpty()){
			GrupoObjetosExpedienteDTO grupoAnimal = grupoObjetosExpediente.get(6);
			if(grupoAnimal != null){
				if(grupoAnimal.getObjetos() != null && !grupoAnimal.getObjetos().isEmpty()){
					
					int numAnimal = 1;
					for(ObjetoResumenDTO dto : grupoAnimal.getObjetos()){
						animal.append("Animal No. " + numAnimal + ":<br>");
						animal.append(dto.getDescripcionResumen() + "<br>");
						numAnimal++;
					}
				}
			}
		}
		
		return animal.toString();
	}
	
	public String getDocumentoOficial(){
		StringBuffer docOficial = new StringBuffer();
		
		if(grupoObjetosExpediente != null && !grupoObjetosExpediente.isEmpty()){
			GrupoObjetosExpedienteDTO grupoDocOfi = grupoObjetosExpediente.get(7);
			if(grupoDocOfi != null){
				if(grupoDocOfi.getObjetos() != null && !grupoDocOfi.getObjetos().isEmpty()){
					
					int numDocOfi = 1;
					for(ObjetoResumenDTO dto : grupoDocOfi.getObjetos()){
						docOficial.append("Documento Oficial No. " + numDocOfi + ":<br>");
						docOficial.append(dto.getDescripcionResumen() + "<br>");
						numDocOfi++;
					}
				}
			}
		}
		
		return docOficial.toString();
	}
	
	public String getEmbarcacion(){
		StringBuffer embarcacion = new StringBuffer();
		
		if(grupoObjetosExpediente != null && !grupoObjetosExpediente.isEmpty()){
			GrupoObjetosExpedienteDTO grupoEmbarca = grupoObjetosExpediente.get(8);
			if(grupoEmbarca != null){
				if(grupoEmbarca.getObjetos() != null && !grupoEmbarca.getObjetos().isEmpty()){
					
					int numEmbarca = 1;
					for(ObjetoResumenDTO dto : grupoEmbarca.getObjetos()){
						embarcacion.append("Embarcaci\u00f3n No. " + numEmbarca + ":<br>");
						embarcacion.append(dto.getDescripcionResumen() + "<br>");
						numEmbarca++;
					}
				}
			}
		}
		
		return embarcacion.toString();
	}
	
	public String getJoya(){
		StringBuffer joya = new StringBuffer();
		
		if(grupoObjetosExpediente != null && !grupoObjetosExpediente.isEmpty()){
			GrupoObjetosExpedienteDTO grupoJoya = grupoObjetosExpediente.get(9);
			if(grupoJoya != null){
				if(grupoJoya.getObjetos() != null && !grupoJoya.getObjetos().isEmpty()){
					
					int numJoya = 1;
					for(ObjetoResumenDTO dto : grupoJoya.getObjetos()){
						joya.append("Joya No. " + numJoya + ":<br>");
						joya.append(dto.getDescripcionResumen() + "<br>");
						numJoya++;
					}
				}
			}
		}
		
		return joya.toString();
	}
	
	public String getNumerario(){
		StringBuffer numerario = new StringBuffer();
		
		if(grupoObjetosExpediente != null && !grupoObjetosExpediente.isEmpty()){
			GrupoObjetosExpedienteDTO grupoNumerario = grupoObjetosExpediente.get(10);
			if(grupoNumerario != null){
				if(grupoNumerario.getObjetos() != null && !grupoNumerario.getObjetos().isEmpty()){
					
					int numNum = 1;
					for(ObjetoResumenDTO dto : grupoNumerario.getObjetos()){
						numerario.append("Numerario No. " + numNum + ":<br>");
						numerario.append(dto.getDescripcionResumen() + "<br>");
						numNum++;
					}
				}
			}
		}
		
		return numerario.toString();
	}
	
	public String getObraArte(){
		StringBuffer obraArte = new StringBuffer();
		
		if(grupoObjetosExpediente != null && !grupoObjetosExpediente.isEmpty()){
			GrupoObjetosExpedienteDTO grupoObraArte = grupoObjetosExpediente.get(11);
			if(grupoObraArte != null){
				if(grupoObraArte.getObjetos() != null && !grupoObraArte.getObjetos().isEmpty()){
					
					int numObra = 1;
					for(ObjetoResumenDTO dto : grupoObraArte.getObjetos()){
						obraArte.append("Obra de Arte No. " + numObra + ":<br>");
						obraArte.append(dto.getDescripcionResumen() + "<br>");
						numObra++;
					}
				}
			}
		}
		
		return obraArte.toString();
	}
	
	public String getSustancia(){
		StringBuffer sustancia = new StringBuffer();
		
		if(grupoObjetosExpediente != null && !grupoObjetosExpediente.isEmpty()){
			GrupoObjetosExpedienteDTO grupoSustancia = grupoObjetosExpediente.get(12);
			if(grupoSustancia != null){
				if(grupoSustancia.getObjetos() != null && !grupoSustancia.getObjetos().isEmpty()){
					
					int numSus = 1;
					for(ObjetoResumenDTO dto : grupoSustancia.getObjetos()){
						sustancia.append("Sustancia No. " + numSus + ":<br>");
						sustancia.append(dto.getDescripcionResumen() + "<br>");
						numSus++;
					}
				}
			}
		}
		
		return sustancia.toString();
	}
	
	public String getVegetal(){
		StringBuffer vegetal = new StringBuffer();
		
		if(grupoObjetosExpediente != null && !grupoObjetosExpediente.isEmpty()){
			GrupoObjetosExpedienteDTO grupoVegetal = grupoObjetosExpediente.get(13);
			if(grupoVegetal != null){
				if(grupoVegetal.getObjetos() != null && !grupoVegetal.getObjetos().isEmpty()){
					
					int numVegetal = 1;
					for(ObjetoResumenDTO dto : grupoVegetal.getObjetos()){
						vegetal.append("Vegetal No. " + numVegetal + ":<br>");
						vegetal.append(dto.getDescripcionResumen() + "<br>");
						numVegetal++;
					}
				}
			}
		}
		
		return vegetal.toString();
	}
	
	public String getObjeto(){
		StringBuffer objeto = new StringBuffer();
		
		if(grupoObjetosExpediente != null && !grupoObjetosExpediente.isEmpty()){
			GrupoObjetosExpedienteDTO grupoObj = grupoObjetosExpediente.get(14);
			if(grupoObj != null){
				if(grupoObj.getObjetos() != null && !grupoObj.getObjetos().isEmpty()){
					
					int numObj = 1;
					for(ObjetoResumenDTO dto : grupoObj.getObjetos()){
						objeto.append("Otros No. " + numObj + ":<br>");
						objeto.append(dto.getDescripcionResumen() + "<br>");
						numObj++;
					}
				}
			}
		}
		
		return objeto.toString();
	}
	
	public String getPericial(){
		StringBuffer pericial = new StringBuffer();
		
		if(grupoObjetosExpediente != null && !grupoObjetosExpediente.isEmpty()){
			GrupoObjetosExpedienteDTO grupoPericial = grupoObjetosExpediente.get(15);
			if(grupoPericial != null){
				if(grupoPericial.getObjetos() != null && !grupoPericial.getObjetos().isEmpty()){
					
					int numPericial = 1;
					for(ObjetoResumenDTO dto : grupoPericial.getObjetos()){
						pericial.append("Pericial No. " + numPericial + ":<br>");
						pericial.append(dto.getDescripcionResumen() + "<br>");
						numPericial++;
					}
				}
			}
		}
		
		return pericial.toString();
	}
	
}
