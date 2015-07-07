/**
 * 
 */
package mx.gob.segob.nsjp.dto.persona;

import java.util.ArrayList;
import java.util.List;

import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.domicilio.DomicilioDTO;
import mx.gob.segob.nsjp.dto.elemento.ElementoDTO;

/**
 * @author vaguirre
 * 
 */
public class PersonaDTO extends ElementoDTO {

    /**
	 * 
	 */
    private static final long serialVersionUID = -1952229731366176977L;

    // private Long personaId;
    private Boolean esVivo;
    private String folioIdentificacion;
    private ValorDTO valorIdIdentificaion;
    private DomicilioDTO domicilio;
    private DomicilioDTO domicilioNotificacion;
    private List<NombreDemograficoDTO> nombresDemograficoDTO = new ArrayList<NombreDemograficoDTO>();
    private List<TelefonoDTO> telefonosDTO = new ArrayList<TelefonoDTO>();
    private List<CorreoElectronicoDTO> correosDTO = new ArrayList<CorreoElectronicoDTO>();
    
    /**
     * Utilizado para la lista de parametros de la generacion dinamica de documento 
     */
    private String listaTelefonos;

    /**
     * @param personaId
     * @param esVivo
     * @param folioIdentificacion
     * @param valorIdIdentificaion
     * @param nombresDemograficoDTO
     * @param mediosDeContactoDTO
     */
    public PersonaDTO(Boolean esVivo, String folioIdentificacion,
            ValorDTO valorIdIdentificaion,
            List<NombreDemograficoDTO> nombresDemograficoDTO) {
        super();
        this.esVivo = esVivo;
        this.folioIdentificacion = folioIdentificacion;
        this.valorIdIdentificaion = valorIdIdentificaion;
        this.nombresDemograficoDTO = nombresDemograficoDTO;
    }
    /**
     * Regresa el nombre de la persona.
     * 
     * @return El nombre completo (nombre + apellido patarno + apellido materno)
     *         del unico nombre demografico, si tiene m�s de uno busca el
     *         verdadero � <code>null</code> en caso de no tener ning�n nombre
     *         demografico.
     */
    public String getNombreCompleto() {
        if (this.nombresDemograficoDTO != null
                && !this.nombresDemograficoDTO.isEmpty()) {
            if (this.nombresDemograficoDTO.size() == 1) {
                return this.nombresDemograficoDTO.get(0).getNombreCompleto();
            } else {
                for (NombreDemograficoDTO intento : this.nombresDemograficoDTO) {
                    if (intento.getEsVerdadero() != null
                            && intento.getEsVerdadero().booleanValue()) {
                        return intento.getNombreCompleto();
                    }
                }
            }
        }
        return null;
    }
    
    public NombreDemograficoDTO getPrimerNombreDemografico(){
    	 if (this.nombresDemograficoDTO != null
                 && !this.nombresDemograficoDTO.isEmpty()) {
             if (this.nombresDemograficoDTO.size() == 1) {
                 return this.nombresDemograficoDTO.get(0);
             } else {
                 for (NombreDemograficoDTO intento : this.nombresDemograficoDTO) {
                     if (intento.getEsVerdadero() != null
                             && intento.getEsVerdadero().booleanValue()) {
                         return intento;
                     }
                 }
             }
         }
         return null;
    }

    /**
     * 
     * @param nombre
     */
    public void addNombreDemografico(NombreDemograficoDTO nombre) {
        if (nombresDemograficoDTO == null) {
            nombresDemograficoDTO = new ArrayList<NombreDemograficoDTO>();
        }
        nombresDemograficoDTO.add(nombre);
    }

    /**
	 * 
	 */
    public PersonaDTO() {
        super();
    }
    public PersonaDTO(Long personaId) {
		super();
		setElementoId(personaId);
	}
	/**
     * @return the esVivo
     */
    public Boolean getEsVivo() {
        return esVivo;
    }

    /**
     * @param esVivo
     *            the esVivo to set
     */
    public void setEsVivo(Boolean esVivo) {
        this.esVivo = esVivo;
    }

    /**
     * M�todo de acceso al campo folioIdentificacion.
     * 
     * @return El valor del campo folioIdentificacion
     */
    public String getFolioIdentificacion() {
        return folioIdentificacion;
    }

    /**
     * Asigna el valor al campo folioIdentificacion.
     * 
     * @param folioIdentificacion
     *            el valor folioIdentificacion a asignar
     */
    public void setFolioIdentificacion(String folioIdentificacion) {
        this.folioIdentificacion = folioIdentificacion;
    }

    /**
     * M�todo de acceso al campo valorIdIdentificaion.
     * 
     * @return El valor del campo valorIdIdentificaion
     */
    public ValorDTO getValorIdIdentificaion() {
        return valorIdIdentificaion;
    }

    /**
     * Asigna el valor al campo valorIdIdentificaion.
     * 
     * @param valorIdIdentificaion
     *            el valor valorIdIdentificaion a asignar
     */
    public void setValorIdIdentificaion(ValorDTO valorIdIdentificaion) {
        this.valorIdIdentificaion = valorIdIdentificaion;
    }

    /**
     * M�todo de acceso al campo nombresDemograficoDTO.
     * 
     * @return El valor del campo nombresDemograficoDTO
     */
    public List<NombreDemograficoDTO> getNombresDemograficoDTO() {
        return nombresDemograficoDTO;
    }

    /**
     * Asigna el valor al campo nombresDemograficoDTO.
     * 
     * @param nombresDemograficoDTO
     *            el valor nombresDemograficoDTO a asignar
     */
    public void setNombresDemograficoDTO(
            List<NombreDemograficoDTO> nombresDemograficoDTO) {
        this.nombresDemograficoDTO = nombresDemograficoDTO;
    }

    /**
     * M�todo de acceso al campo telefonosDTO.
     * 
     * @return El valor del campo telefonosDTO
     */
    public List<TelefonoDTO> getTelefonosDTO() {
        return telefonosDTO;
    }

    /**
     * Asigna el valor al campo telefonosDTO.
     * 
     * @param telefonosDTO
     *            el valor telefonosDTO a asignar
     */
    public void setTelefonosDTO(List<TelefonoDTO> telefonosDTO) {
        this.telefonosDTO = telefonosDTO;
    }

    /**
     * M�todo de acceso al campo correosDTO.
     * 
     * @return El valor del campo correosDTO
     */
    public List<CorreoElectronicoDTO> getCorreosDTO() {
        return correosDTO;
    }

    /**
     * Asigna el valor al campo correosDTO.
     * 
     * @param correosDTO
     *            el valor correosDTO a asignar
     */
    public void setCorreosDTO(List<CorreoElectronicoDTO> correosDTO) {
        this.correosDTO = correosDTO;
    }

    /**
     * Método de acceso al campo domicilio.
     * 
     * @return El valor del campo domicilio
     */
    public DomicilioDTO getDomicilio() {
        return domicilio;
    }

    /**
     * Asigna el valor al campo domicilio.
     * 
     * @param domicilio
     *            el valor domicilio a asignar
     */
    public void setDomicilio(DomicilioDTO domicilio) {
        this.domicilio = domicilio;
    }

    /**
     * Método de acceso al campo domicilioNotificacion.
     * 
     * @return El valor del campo domicilioNotificacion
     */
    public DomicilioDTO getDomicilioNotificacion() {
        return domicilioNotificacion;
    }

    /**
     * Asigna el valor al campo domicilioNotificacion.
     * 
     * @param domicilioNotificacion
     *            el valor domicilioNotificacion a asignar
     */
    public void setDomicilioNotificacion(DomicilioDTO domicilioNotificacion) {
        this.domicilioNotificacion = domicilioNotificacion;
    }
    
	/**
	 * @return the listaTelefonos
	 */
	public String getListaTelefonos() {
		if(this.getTelefonosDTO()!=null){
			this.listaTelefonos ="";
			List<String> listaTemporal = new ArrayList<String>();
			for (TelefonoDTO telefonoDTO : this.getTelefonosDTO()) {
					listaTemporal.add((telefonoDTO.getCodigoPais() + " ("
							+ telefonoDTO.getCodigoArea() + ") "
							+ telefonoDTO.getNumeroTelefonico()));
			}
			if(!listaTemporal.isEmpty()){
				this.listaTelefonos =listaTemporal.toString().substring(1,
						listaTemporal.toString().length()-1);	
			}
		}
		return this.listaTelefonos;
	}
	
	/**
	 * @param listaTelefonos the listaTelefonos to set
	 */
	public void setListaTelefonos(String listaTelefonos) {
		this.listaTelefonos = listaTelefonos;
	}

}
