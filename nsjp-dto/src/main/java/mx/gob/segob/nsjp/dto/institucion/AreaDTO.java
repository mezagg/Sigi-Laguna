/**
 * Nombre del Programa : Area.java
 * Autor                            : vaguirre
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 30 May 2011
 * Marca de cambio        : N/A
 * Descripcion General    : Objeto de Transferencia del Area
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
package mx.gob.segob.nsjp.dto.institucion;

import mx.gob.segob.nsjp.comun.enums.institucion.Areas;
import mx.gob.segob.nsjp.dto.base.GenericDTO;

/**
 * Objeto de Transferencia del Area.
 * 
 * @version 1.0
 * @author vaguirre
 * 
 */

public class AreaDTO extends GenericDTO {
    /**
	 * 
	 */
	private static final long serialVersionUID = -6721640100992168584L;
	private Long areaId;
    private String nombre;
    
    private InstitucionDTO institucion;
    
    private Boolean buscarEnJerarquia; 
    /**
     * @param areaId
     */
    public AreaDTO(Long areaId) {
        super();
        this.areaId = areaId;
    }

    /**
     * @param areaId
     */
    public AreaDTO(Long areaId, String nomString) {
        super();
        this.areaId = areaId;
        this.nombre = nomString;
    }

    public AreaDTO(String nomString) {
        super();

        this.nombre = nomString;
    }
    /**
     * @param areaId
     */
    public AreaDTO(Areas areaEnum) {
        super();
        this.areaId = new Long(areaEnum.ordinal());
        this.nombre = areaEnum.name();
    }
    public AreaDTO() {
		super();
	}


	/**
     * M�todo de acceso al campo areaId.
     * 
     * @return El valor del campo areaId
     */
    public Long getAreaId() {
        return areaId;
    }

    /**
     * Asigna el valor al campo areaId.
     * 
     * @param areaId
     *            el valor areaId a asignar
     */
    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }


    /**
     * M�todo de acceso al campo nombre.
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
	 * Asigna el valor al campo institucion.
	 * @param institucion el valor institucion a asignar
	 */
	public void setInstitucion(InstitucionDTO institucion) {
		this.institucion = institucion;
	}


	/**
	 * M�todo de acceso al campo institucion.
	 * @return El valor del campo institucion
	 */
	public InstitucionDTO getInstitucion() {
		return institucion;
	}

	/**
	 * M�todo de acceso al campo buscarEnJerarquia.
	 * @return El valor del campo buscarEnJerarquia
	 */
	public Boolean getBuscarEnJerarquia() {
		return buscarEnJerarquia;
	}

	/**
	 * Asigna el valor al campo buscarEnJerarquia.
	 * @param buscarEnJerarquia el valor buscarEnJerarquia a asignar
	 */
	public void setBuscarEnJerarquia(Boolean buscarEnJerarquia) {
		this.buscarEnJerarquia = buscarEnJerarquia;
	}
}
