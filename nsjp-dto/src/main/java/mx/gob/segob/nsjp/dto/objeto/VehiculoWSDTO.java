/**
* Nombre del Programa : VehiculoWSDTO.java                                    
* Autor                            : GustavoBP                                              
* Compania                    : Ultrasist                                                
* Proyecto                      : NSJP                    Fecha: 24/08/2011 
* Marca de cambio        : N/A                                                     
* Descripcion General    : DTO de intercambio entre sistemas para transportar los datos b�sicos de un Vehiculo.                    
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

/**
 * DTO de intercambio entre sistemas para transportar los datos b�sicos de un Vehiculo.
 * 
 * @author GustavoBP
 * @version 1.0
 */
public class VehiculoWSDTO extends ObjetoWSDTO{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4596787611367273421L;
	private Long valorByPaisOrigenVal;
	private Long valorByColorVal;
	private Long valorByMarcaVal;
	private Long valorBySubmarcaVal;
	private Long valorByTipoVehiculo;
	private Short modelo;
	private String placa;
	private String noSerie;
	private String noMotor;
	private String nrfv;
	private Boolean esBlindado;
	private Short noPuertas;
	private Short noCilindros;
	
	private String propietario;
	private Boolean esNumMotorAlterado;
	private Boolean esNumSerieAlterado;
	
	/**
	 * @param valorByPaisOrigenVal
	 * @param valorByColorVal
	 * @param valorByMarcaVal
	 * @param valorBySubmarcaVal
	 * @param valorByTipoVehiculo
	 * @param modelo
	 * @param placa
	 * @param noSerie
	 * @param noMotor
	 * @param nrfv
	 * @param esBlindado
	 * @param noPuertas
	 * @param noCilindros
	 */
	public VehiculoWSDTO(Long valorByPaisOrigenVal,
			Long valorByColorVal,
			Long  valorByMarcaVal,
			Long valorBySubmarcaVal,
			Long valorByTipoVehiculo,
			Short modelo, String placa, String noSerie, String noMotor,
			String nrfv, Boolean esBlindado, Short noPuertas, Short noCilindros) {
		super();
		this.valorByPaisOrigenVal = valorByPaisOrigenVal;
		this.valorByColorVal = valorByColorVal;
		this.valorByMarcaVal = valorByMarcaVal;
		this.valorBySubmarcaVal = valorBySubmarcaVal;
		this.valorByTipoVehiculo = valorByTipoVehiculo;
		this.modelo = modelo;
		this.placa = placa;
		this.noSerie = noSerie;
		this.noMotor = noMotor;
		this.nrfv = nrfv;
		this.esBlindado = esBlindado;
		this.noPuertas = noPuertas;
		this.noCilindros = noCilindros;
		
	}
	
	public VehiculoWSDTO() {
        super();
    }
    /**
	 * M�todo de acceso al campo valorByPaisOrigenVal.
	 * @return El valor del campo valorByPaisOrigenVal
	 */
	public Long getValorByPaisOrigenVal() {
		return valorByPaisOrigenVal;
	}
	/**
	 * Asigna el valor al campo valorByPaisOrigenVal.
	 * @param valorByPaisOrigenVal el valor valorByPaisOrigenVal a asignar
	 */
	public void setValorByPaisOrigenVal(Long valorByPaisOrigenVal) {
		this.valorByPaisOrigenVal = valorByPaisOrigenVal;
	}
	/**
	 * M�todo de acceso al campo valorByColorVal.
	 * @return El valor del campo valorByColorVal
	 */
	public Long getValorByColorVal() {
		return valorByColorVal;
	}
	/**
	 * Asigna el valor al campo valorByColorVal.
	 * @param valorByColorVal el valor valorByColorVal a asignar
	 */
	public void setValorByColorVal(Long valorByColorVal) {
		this.valorByColorVal = valorByColorVal;
	}
	/**
	 * M�todo de acceso al campo valorByMarcaVal.
	 * @return El valor del campo valorByMarcaVal
	 */
	public Long getValorByMarcaVal() {
		return valorByMarcaVal;
	}
	/**
	 * Asigna el valor al campo valorByMarcaVal.
	 * @param valorByMarcaVal el valor valorByMarcaVal a asignar
	 */
	public void setValorByMarcaVal(Long valorByMarcaVal) {
		this.valorByMarcaVal = valorByMarcaVal;
	}
	/**
	 * M�todo de acceso al campo valorBySubmarcaVal.
	 * @return El valor del campo valorBySubmarcaVal
	 */
	public Long getValorBySubmarcaVal() {
		return valorBySubmarcaVal;
	}
	/**
	 * Asigna el valor al campo valorBySubmarcaVal.
	 * @param valorBySubmarcaVal el valor valorBySubmarcaVal a asignar
	 */
	public void setValorBySubmarcaVal(Long valorBySubmarcaVal) {
		this.valorBySubmarcaVal = valorBySubmarcaVal;
	}
	/**
	 * M�todo de acceso al campo valorByTipoVehiculo.
	 * @return El valor del campo valorByTipoVehiculo
	 */
	public Long getValorByTipoVehiculo() {
		return valorByTipoVehiculo;
	}
	/**
	 * Asigna el valor al campo valorByTipoVehiculo.
	 * @param valorByTipoVehiculo el valor valorByTipoVehiculo a asignar
	 */
	public void setValorByTipoVehiculo(Long valorByTipoVehiculo) {
		this.valorByTipoVehiculo = valorByTipoVehiculo;
	}
	/**
	 * M�todo de acceso al campo modelo.
	 * @return El valor del campo modelo
	 */
	public Short getModelo() {
		return modelo;
	}
	/**
	 * Asigna el valor al campo modelo.
	 * @param modelo el valor modelo a asignar
	 */
	public void setModelo(Short modelo) {
		this.modelo = modelo;
	}
	/**
	 * M�todo de acceso al campo placa.
	 * @return El valor del campo placa
	 */
	public String getPlaca() {
		return placa;
	}
	/**
	 * Asigna el valor al campo placa.
	 * @param placa el valor placa a asignar
	 */
	public void setPlaca(String placa) {
		this.placa = placa;
	}
	/**
	 * M�todo de acceso al campo noSerie.
	 * @return El valor del campo noSerie
	 */
	public String getNoSerie() {
		return noSerie;
	}
	/**
	 * Asigna el valor al campo noSerie.
	 * @param noSerie el valor noSerie a asignar
	 */
	public void setNoSerie(String noSerie) {
		this.noSerie = noSerie;
	}
	/**
	 * M�todo de acceso al campo noMotor.
	 * @return El valor del campo noMotor
	 */
	public String getNoMotor() {
		return noMotor;
	}
	/**
	 * Asigna el valor al campo noMotor.
	 * @param noMotor el valor noMotor a asignar
	 */
	public void setNoMotor(String noMotor) {
		this.noMotor = noMotor;
	}
	/**
	 * M�todo de acceso al campo nrfv.
	 * @return El valor del campo nrfv
	 */
	public String getNrfv() {
		return nrfv;
	}
	/**
	 * Asigna el valor al campo nrfv.
	 * @param nrfv el valor nrfv a asignar
	 */
	public void setNrfv(String nrfv) {
		this.nrfv = nrfv;
	}
	/**
	 * M�todo de acceso al campo esBlindado.
	 * @return El valor del campo esBlindado
	 */
	public Boolean getEsBlindado() {
		return esBlindado;
	}
	/**
	 * Asigna el valor al campo esBlindado.
	 * @param esBlindado el valor esBlindado a asignar
	 */
	public void setEsBlindado(Boolean esBlindado) {
		this.esBlindado = esBlindado;
	}
	/**
	 * M�todo de acceso al campo noPuertas.
	 * @return El valor del campo noPuertas
	 */
	public Short getNoPuertas() {
		return noPuertas;
	}
	/**
	 * Asigna el valor al campo noPuertas.
	 * @param noPuertas el valor noPuertas a asignar
	 */
	public void setNoPuertas(Short noPuertas) {
		this.noPuertas = noPuertas;
	}
	/**
	 * M�todo de acceso al campo noCilindros.
	 * @return El valor del campo noCilindros
	 */
	public Short getNoCilindros() {
		return noCilindros;
	}
	/**
	 * Asigna el valor al campo noCilindros.
	 * @param noCilindros el valor noCilindros a asignar
	 */
	public void setNoCilindros(Short noCilindros) {
		this.noCilindros = noCilindros;
	}

	/**
	 * @return the esNumMotorAlterado
	 */
	public Boolean getEsNumMotorAlterado() {
		return esNumMotorAlterado;
	}

	/**
	 * @param esNumMotorAlterado the esNumMotorAlterado to set
	 */
	public void setEsNumMotorAlterado(Boolean esNumMotorAlterado) {
		this.esNumMotorAlterado = esNumMotorAlterado;
	}

	/**
	 * @return the esNumSerieAlterado
	 */
	public Boolean getEsNumSerieAlterado() {
		return esNumSerieAlterado;
	}

	/**
	 * @param esNumSerieAlterado the esNumSerieAlterado to set
	 */
	public void setEsNumSerieAlterado(Boolean esNumSerieAlterado) {
		this.esNumSerieAlterado = esNumSerieAlterado;
	}

	/**
	 * @return the propietario
	 */
	public String getPropietario() {
		return propietario;
	}

	/**
	 * @param propietario the propietario to set
	 */
	public void setPropietario(String propietario) {
		this.propietario = propietario;
	}

}
