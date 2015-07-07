/**
 * Nombre del Programa : PaginacionDTO.java
 * Autor                            : vaguirre
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 24 Aug 2011
 * Marca de cambio        : N/A
 * Descripcion General    : DTO auxiliar para resolver la paginaci�n y el ordenamiento
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
package mx.gob.segob.nsjp.dto.base;

import java.io.Serializable;

/**
 * DTO auxiliar para resolver la paginaci�n y el ordenamiento.
 * 
 * @version 1.0
 * @author vaguirre
 * 
 */
public class PaginacionDTO implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 5503303727927877397L;
	/**
	 * Valor default para el n�mero de registros por p�gina.
	 */
	public final static Integer DEFAULT_MAX_RESULT = new Integer(10);
	/**
     * Valor default para construir el grid de un catalogo.
     */
    public final static Integer DEFAULT_MAX_RESULT_CONSTRUCCION_GRID= new Integer(1);
    /**
     * P�gina a mostrar.
     */
    private Integer page;
    /**
     * Renglones a mostrar por p�gina.
     */
    private Integer rows;
    /**
     * Nombre del campo para ordenar
     */
    private String campoOrd;
    /**
     * Direcci�n del ordenamiento.
     */
    private String dirOrd;
    /**
     * Total de registros de la consulta.
     */
    private Long totalRegistros;
    /**
     * campo para la busqueda
     */
    private String searchField;
    /**
     *operador de la busqueda 
     */
    private String searchOper;
    /**
     *cadena a buscar 
     */
    private String searchString;
    
    private Long usuarioId;
    /**
     * Banderas para cuando se invocan dos <i>queries paginados</i> en el mismo hilo.
     */
    private boolean paginacionHecha;
    /**
     * Banderas para se usa para saber si se esta ocupando el findAll para construir un grid
     */
    private boolean esParaContruccionDeGridDeCatalogo = Boolean.FALSE;
    
    public boolean isPaginacionHecha() {
		return paginacionHecha;
	}
	public void setPaginacionHecha(boolean paginacionHecha) {
		this.paginacionHecha = paginacionHecha;
	}
	@Override
    public String toString() {
        final StringBuffer cad = new StringBuffer(getClass().getSimpleName());
        cad.append("=[page=").append(page);
        cad.append(", rows=").append(rows);
        cad.append(", campoOrd=").append(campoOrd);
        cad.append(", dirOrd=").append(dirOrd);
        cad.append(", totalRegistros=").append(totalRegistros);
        cad.append(", usuarioId=").append(usuarioId);
        cad.append(", searchField=").append(searchField);
        cad.append(", searchOper=").append(searchOper);
        cad.append(", searchField=").append(searchField);
        cad.append(", searchString=").append(searchString);
        cad.append("]");
        return cad.toString();
    }
    /**
     * Calcula el total de Paginas.
     * 
     * @return
     */
    public Integer getTotalPaginas() {
        if (getTotalRegistros() != null && getRows() != null) {
            long modulo = totalRegistros % (long) rows;
            int totalPaginas = (int) (totalRegistros / rows);
            totalPaginas += modulo > 0 ? 1 : 0;
            return new Integer(totalPaginas);
        }
        return new Integer(0);
    }
    /**
     * Calcula la primera posici�n de la paginaci�n.
     * 
     * @return
     */
    public Integer getFirstResult() {
        if (getRows() != null && getPage() != null) {
            return new Integer((getRows() * (getPage() - 1)));
        }
        return new Integer(0);
    }

    /**
     * M�todo de acceso al campo page.
     * 
     * @return El valor del campo page
     */
    public Integer getPage() {
        return page;
    }
    /**
     * Asigna el valor al campo page.
     * 
     * @param page
     *            el valor page a asignar
     */
    public void setPage(Integer page) {
        this.page = page;
    }
    /**
     * M�todo de acceso al campo rows.
     * 
     * @return El valor del campo rows
     */
    public Integer getRows() {
        return rows;
    }
    /**
     * Asigna el valor al campo rows.
     * 
     * @param rows
     *            el valor rows a asignar
     */
    public void setRows(Integer rows) {
        this.rows = rows;
    }
    /**
     * M�todo de acceso al campo campoOrd.
     * 
     * @return El valor del campo campoOrd
     */
    public String getCampoOrd() {
        return campoOrd;
    }
    /**
     * Asigna el valor al campo campoOrd.
     * 
     * @param campoOrd
     *            el valor campoOrd a asignar
     */
    public void setCampoOrd(String campoOrd) {
        this.campoOrd = campoOrd;
    }
    /**
     * M�todo de acceso al campo dirOrd.
     * 
     * @return El valor del campo dirOrd
     */
    public String getDirOrd() {
        return dirOrd;
    }
    /**
     * Asigna el valor al campo dirOrd.
     * 
     * @param dirOrd
     *            el valor dirOrd a asignar
     */
    public void setDirOrd(String dirOrd) {
        this.dirOrd = dirOrd;
    }

    /**
     * M�todo de acceso al campo totalRegistros.
     * 
     * @return El valor del campo totalRegistros
     */
    public Long getTotalRegistros() {
        return totalRegistros;
    }

    /**
     * Asigna el valor al campo totalRegistros.
     * 
     * @param totalRegistros
     *            el valor totalRegistros a asignar
     */
    public void setTotalRegistros(Long totalRegistros) {
        this.totalRegistros = totalRegistros;
    }
    /**
     * M�todo de acceso al campo usuarioId.
     * @return El valor del campo usuarioId
     */
    public Long getUsuarioId() {
        return usuarioId;
    }
    /**
     * Asigna el valor al campo usuarioId.
     * @param usuarioId el valor usuarioId a asignar
     */
    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }
	/**
	 * @param searchField the searchField to set
	 */
	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}
	/**
	 * @return the searchField
	 */
	public String getSearchField() {
		return searchField;
	}
	/**
	 * @param searchOper the searchOper to set
	 */
	public void setSearchOper(String searchOper) {
		this.searchOper = searchOper;
	}
	/**
	 * @return the searchOper
	 */
	public String getSearchOper() {
		return searchOper;
	}
	/**
	 * @param searchString the searchString to set
	 */
	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}
	/**
	 * @return the searchString
	 */
	public String getSearchString() {
		return searchString;
	}
	/**
	 * @return the esParaContruccionDeGridDeCatalogo
	 */
	public boolean isEsParaContruccionDeGridDeCatalogo() {
		return esParaContruccionDeGridDeCatalogo;
	}
	/**
	 * @param esParaContruccionDeGridDeCatalogo the esParaContruccionDeGridDeCatalogo to set
	 */
	public void setEsParaContruccionDeGridDeCatalogo(
			boolean esParaContruccionDeGridDeCatalogo) {
		this.esParaContruccionDeGridDeCatalogo = esParaContruccionDeGridDeCatalogo;
	}
	
	

}
