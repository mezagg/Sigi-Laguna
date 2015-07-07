/**
* Nombre del Programa : ObjetoDTO.java                                    
* Autor                            : Tattva-IT                                              
* Compania                    : Ultrasist                                                
* Proyecto                      : NSJP                    Fecha: 3 May 2011 
* Marca de cambio        : N/A                                                     
* Descripcion General    : Clase para la transferencia de datos entre la vista y el negocio, del objeto Objeto.                      
* Programa Dependiente  :N/A                                                      
* Programa Subsecuente :N/A                                                      
* Cond. de ejecucion        :N/A                                                      
* Dias de ejecucion          :N/A                             Horario: N/A       
*                              MODIFICACIONES                                       
*------------------------------------------------------------------------------           
* Autor                       :N/A                                                           
* Compania               :N/A                                                           
* Proyecto                 :N/A                                   Fecha: N/A       
* Modificacion           :N/A                                                           
*------------------------------------------------------------------------------           
*/
package mx.gob.segob.nsjp.dto.objeto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mx.gob.segob.nsjp.comun.enums.objeto.Objetos;
import mx.gob.segob.nsjp.dto.almacen.AlmacenDTO;
import mx.gob.segob.nsjp.dto.cadenadecustoria.CadenaDeCustodiaDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.configuracion.ConfInstitucionDTO;
import mx.gob.segob.nsjp.dto.elemento.ElementoDTO;
import mx.gob.segob.nsjp.dto.evidencia.EvidenciaDTO;


/**
 * Clase para la transferencia de datos entre la vista y el negocio, del objeto Objeto.
 * @version 1.0
 * @author Tattva-IT
 *
 */
public class ObjetoDTO extends ElementoDTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6736636006991218470L;
	private ValorDTO valorByCondicionFisicaVal;
	private CadenaDeCustodiaDTO cadenaDeCustodia; 
	private Objetos tipoObjeto;
	private Long idTipoObjeto;
	private AlmacenDTO almacen; 
	private String descripcion;
	private Boolean esPertenencia;
	private String nombreObjeto;
	private List<String> numerosDeCasos = new ArrayList<String>(); 
	private ValorDTO relacionHechoVal;
	private Boolean consultarArchivoDigital;
	
	 private EvidenciaDTO evidencia;
	/**
     * 
     */
    public ObjetoDTO() {
        super();
    }
    /**
     * @param elementoId
     */
    public ObjetoDTO(Long elementoId) {
        super(elementoId);
    }
    /**
	 * Usada en la relaci�n de evidencias con audiencias.
	 */
	private Date fechaRecepcion;
	
    /**
     * Relaci�n con la instituci�n que lo presenta a la audiencia.
     */
    private ConfInstitucionDTO institucionPresenta;
	
    /**
     * @return the nombreArchivo
     */
    public String getNombreObjeto() {
        return nombreObjeto;
    }
    /**
     * @param nombreArchivo
     *            the nombreArchivo to set
     */
    public void setNombreObjeto(String nombreObjeto) {
        this.nombreObjeto = nombreObjeto;
    }
    
	/**
	 * M�todo de acceso al campo valorByCondicionFisicaVal.
	 * @return El valor del campo valorByCondicionFisicaVal
	 */
	public ValorDTO getValorByCondicionFisicaVal() {
		return valorByCondicionFisicaVal;
	}
	/**
	 * Asigna el valor al campo valorByCondicionFisicaVal.
	 * @param valorByCondicionFisicaVal el valor valorByCondicionFisicaVal a asignar
	 */
	public void setValorByCondicionFisicaVal(ValorDTO valorByCondicionFisicaVal) {
		this.valorByCondicionFisicaVal = valorByCondicionFisicaVal;
	}

	/**
	 * M�todo de acceso al campo cadenaDeCustodia.
	 * @return El valor del campo cadenaDeCustodia
	 */
	public CadenaDeCustodiaDTO getCadenaDeCustodia() {
		return cadenaDeCustodia;
	}
	/**
	 * Asigna el valor al campo cadenaDeCustodia.
	 * @param cadenaDeCustodia el valor cadenaDeCustodia a asignar
	 */
	public void setCadenaDeCustodia(CadenaDeCustodiaDTO cadenaDeCustodia) {
		this.cadenaDeCustodia = cadenaDeCustodia;
	}

	/**
	 * M�todo de acceso al campo almacen.
	 * @return El valor del campo almacen
	 */
	public AlmacenDTO getAlmacen() {
		return almacen;
	}
	/**
	 * Asigna el valor al campo almacen.
	 * @param almacen el valor almacen a asignar
	 */
	public void setAlmacen(AlmacenDTO almacen) {
		this.almacen = almacen;
	}
	/**
	 * M�todo de acceso al campo descripcion.
	 * @return El valor del campo descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}
	/**
	 * Asigna el valor al campo descripcion.
	 * @param descripcion el valor descripcion a asignar
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	/**
	 * M�todo de acceso al campo esPertenencia.
	 * @return El valor del campo esPertenencia
	 */
	public Boolean getEsPertenencia() {
		return esPertenencia;
	}
	/**
	 * Asigna el valor al campo esPertenencia.
	 * @param esPertenencia el valor esPertenencia a asignar
	 */
	public void setEsPertenencia(Boolean esPertenencia) {
		this.esPertenencia = esPertenencia;
	}
	/**
	 * M�todo de acceso al campo tipoObjeto.
	 * @return El valor del campo tipoObjeto
	 */
	public Objetos getTipoObjeto() {	
		return tipoObjeto;
	}
	/**
	 * Asigna el valor al campo tipoObjeto.
	 * @param tipoObjeto el valor tipoObjeto a asignar
	 */
	public void setTipoObjeto(Objetos tipoObjeto) {
		if(tipoObjeto != null)
			idTipoObjeto = tipoObjeto.getValorId();
		this.tipoObjeto = tipoObjeto;
	}
    /**
     * M�todo de acceso al campo fechaRecepcion.
     * @return El valor del campo fechaRecepcion
     */
    public Date getFechaRecepcion() {
        return fechaRecepcion;
    }
    /**
     * Asigna el valor al campo fechaRecepcion.
     * @param fechaRecepcion el valor fechaRecepcion a asignar
     */
    public void setFechaRecepcion(Date fechaRecepcion) {
        this.fechaRecepcion = fechaRecepcion;
    }
	/**
	 * @return the institucionPresenta
	 */
	public ConfInstitucionDTO getInstitucionPresenta() {
		return institucionPresenta;
	}
	/**
	 * @param institucionPresenta the institucionPresenta to set
	 */
	public void setInstitucionPresenta(ConfInstitucionDTO institucionPresenta) {
		this.institucionPresenta = institucionPresenta;
	}
    /**
     * M�todo de acceso al campo evidencia.
     * @return El valor del campo evidencia
     */
    public EvidenciaDTO getEvidencia() {
        return evidencia;
    }
    /**
     * Asigna el valor al campo evidencia.
     * @param evidencia el valor evidencia a asignar
     */
    public void setEvidencia(EvidenciaDTO evidencia) {
        this.evidencia = evidencia;
    }

	
	/**
	 * M�todo de acceso al campo numerosDeCasos.
	 * @return El valor del campo numerosDeCasos
	 */
	public List<String> getNumerosDeCasos() {
		return numerosDeCasos;
	}
	/**
	 * Asigna el valor al campo numerosDeCasos.
	 * @param numerosDeCasos el valor numerosDeCasos a asignar
	 */
	public void setNumerosDeCasos(List<String> numerosDeCasos) {
		this.numerosDeCasos = numerosDeCasos;
	}
	public void addNumeroDeCaso(String numExp){
		this.numerosDeCasos.add(numExp);
	}
	public Long getIdTipoObjeto() {
		return idTipoObjeto;
	}
	public void setIdTipoObjeto(Long idTipoObjeto) {
		this.idTipoObjeto = idTipoObjeto;
	}
	
	/**
	 * M�todo de acceso al campo relacionHechoVal.
	 * @return El valor del campo relacionHechoVal
	 */
	public ValorDTO getRelacionHechoVal() {
		return relacionHechoVal;
	}

	/**
	 * Asigna el valor al campo relacionHechoVal.
	 * @param relacionHechoVal el valor relacionHechoVal a asignar
	 */
	public void setRelacionHechoVal(ValorDTO relacionHechoVal) {
		this.relacionHechoVal = relacionHechoVal;
	}
	/**
	 * @return the consultarArchivoDigital
	 */
	public Boolean getConsultarArchivoDigital() {
		return consultarArchivoDigital;
	}
	/**
	 * @param consultarArchivoDigital the consultarArchivoDigital to set
	 */
	public void setConsultarArchivoDigital(Boolean consultarArchivoDigital) {
		this.consultarArchivoDigital = consultarArchivoDigital;
	}

}


