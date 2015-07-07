/**
 * Nombre del Programa : TipoDeBusquedaDeExpediente.java
 * Autor                            : rgama
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 18/06/2012
 * Marca de cambio        : N/A
 * Descripcion General    : Enumeracion que lista los distintas tipos de consultas sobre expedientes 
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
package mx.gob.segob.nsjp.comun.enums.expediente;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Enumeracion que lista los distintas consultas para generar los indicadores
 * 
 * @author rgama
 */
public enum TipoDeBusquedaDeExpediente {
	
	POR_DELITO(1L,
			"Busqueda por delito",			
			Arrays.asList("ai_iClaveFuncionario" ,"ai_rol_id" ,"ai_pagina","ai_registros","as_columnaOrd","as_direccionOrd","ai_catdelito_id"),
			" EXEC busquedaXDelito :ai_iClaveFuncionario, :ai_rol_id, :ai_pagina, :ai_registros, :as_columnaOrd, :as_direccionOrd, :ai_catdelito_id "
			),
	POR_NUM_EXP(2L,
			"Busqueda por numero de expediente",			
			Arrays.asList("ai_iClaveFuncionario" ,"ai_rol_id" ,"ai_pagina","ai_registros","as_columnaOrd","as_direccionOrd","as_cNumeroExpediente"),
			" EXEC busquedaXNumExp :ai_iClaveFuncionario, :ai_rol_id, :ai_pagina, :ai_registros, :as_columnaOrd, :as_direccionOrd, :as_cNumeroExpediente "
			),
	POR_NUM_CASO(3L,
			"Busqueda por numero de caso",			
			Arrays.asList("ai_iClaveFuncionario" ,"ai_rol_id" ,"ai_pagina","ai_registros","as_columnaOrd","as_direccionOrd","as_cNumeroCaso"),
			" EXEC busquedaXNumCaso :ai_iClaveFuncionario, :ai_rol_id, :ai_pagina, :ai_registros, :as_columnaOrd, :as_direccionOrd, :as_cNumeroCaso "
			),
	POR_FECHAS(4L,
			"Busqueda por fechas",			
			Arrays.asList("ai_iClaveFuncionario" ,"ai_rol_id" ,"ai_pagina","ai_registros","as_columnaOrd","as_direccionOrd","as_fechaIni","as_fechaFin"),
			" EXEC busquedaXFechas :ai_iClaveFuncionario, :ai_rol_id, :ai_pagina, :ai_registros, :as_columnaOrd, :as_direccionOrd, :as_fechaIni, :as_fechaFin "
			),
	POR_ATP(5L,
			"Busqueda por atencion temprana penal",			
			Arrays.asList("ai_iClaveFuncionario" ,"ai_rol_id" ,"ai_pagina","ai_registros","as_columnaOrd","as_direccionOrd","as_cNumeroExpediente"),
			" EXEC busquedaXATP :ai_iClaveFuncionario, :ai_rol_id, :ai_pagina, :ai_registros, :as_columnaOrd, :as_direccionOrd, :as_cNumeroExpediente "
			),
	POR_NOMBRE_PERSONA(6L,
			"Busqueda por nombre de persona",			
			Arrays.asList("ai_iClaveFuncionario" ,"ai_rol_id" ,"ai_pagina","ai_registros","as_columnaOrd","as_direccionOrd","as_nombre","as_apPaterno","as_apMaterno" ),
			" EXEC busquedaXNomPer :ai_iClaveFuncionario, :ai_rol_id, :ai_pagina, :ai_registros, :as_columnaOrd, :as_direccionOrd, :as_nombre, :as_apPaterno, :as_apMaterno "
	),
	POR_NOMBRE_ORGANIZACION(7L,
			"Busqueda por nombre de organizacion",			
			Arrays.asList("ai_iClaveFuncionario" ,"ai_rol_id" ,"ai_pagina","ai_registros","as_columnaOrd","as_direccionOrd","as_nombre"),
			" EXEC busquedaXNomOrg :ai_iClaveFuncionario, :ai_rol_id, :ai_pagina, :ai_registros, :as_columnaOrd, :as_direccionOrd, :as_nombre "
	),
	BUS_AVANZADA_DE_EXP(8L,
			"Busqueda avanzada de expediente",			
			Arrays.asList("ai_iClaveFuncionario" ,"ai_rol_id" ,"ai_pagina","ai_registros","as_columnaOrd","as_direccionOrd","as_fechaIni","as_fechaFin","as_nombreOrg","as_nombre","as_apPaterno","as_apMaterno","as_cNumeroCaso","as_cNumeroExpediente","ai_bexpedienteATP","ai_nanio","ai_catdelito_id","ai_estatus_val","as_nombreFun","as_apPaternoFun","as_apMaternoFun" ),
			" EXEC busquedaxCriterios :ai_iClaveFuncionario,  :ai_rol_id,  :ai_pagina, :ai_registros, :as_columnaOrd, :as_direccionOrd, :as_fechaIni, :as_fechaFin, :as_nombreOrg, :as_nombre, :as_apPaterno, :as_apMaterno, :as_cNumeroCaso, :as_cNumeroExpediente, :ai_bexpedienteATP, :ai_nanio, :ai_catdelito_id, :ai_estatus_val, :as_nombreFun, :as_apPaternoFun, :as_apMaternoFun "
	),
	POR_NUM_EXP_PJ(9L,
			"Busqueda por numero de expediente en PJ",			
			Arrays.asList("ai_iClaveFuncionario" ,"ai_rol_id" ,"ai_pagina","ai_registros","as_columnaOrd","as_direccionOrd","as_cNumeroExpediente"),
			" EXEC busquedaXNumExpPJ :ai_iClaveFuncionario, :ai_rol_id, :ai_pagina, :ai_registros, :as_columnaOrd, :as_direccionOrd, :as_cNumeroExpediente "
	),
	POR_NOMBRE_PERSONA_PJ(10L,
			"Busqueda por nombre de persona en PJ",			
			Arrays.asList("ai_iClaveFuncionario" ,"ai_rol_id" ,"ai_pagina","ai_registros","as_columnaOrd","as_direccionOrd","as_nombre","as_apPaterno","as_apMaterno" ),
			" EXEC busquedaXNomPerPJ :ai_iClaveFuncionario, :ai_rol_id, :ai_pagina, :ai_registros, :as_columnaOrd, :as_direccionOrd, :as_nombre, :as_apPaterno, :as_apMaterno "
	);
	
	private Long tipoBusquedaId;
	private String descripcion;
	private List<String>  parametros;
	private String consulta;
	
	private final static Map<Long, TipoDeBusquedaDeExpediente> hash = new HashMap<Long, TipoDeBusquedaDeExpediente>();
    static {
        TipoDeBusquedaDeExpediente[] objs = TipoDeBusquedaDeExpediente.values();
        int pos = 0;
        while (pos < objs.length) {
            hash.put(objs[pos].getTipoBusquedaId(), objs[pos]);
            pos++;
        }
    }

	private TipoDeBusquedaDeExpediente(Long tipoBusquedaId, String descripcion,
			List<String> parametros, String consulta) {
		this.tipoBusquedaId = tipoBusquedaId;
		this.descripcion = descripcion;
		this.parametros = parametros;
		this.consulta = consulta;
	}
	/**
	 * @return the consulta
	 */
	public String getConsulta() {
		return consulta;
	}
	/**
	 * @param consulta the consulta to set
	 */
	public void setConsulta(String consulta) {
		this.consulta = consulta;
	}
	/**
	 * @return the tipoBusquedaId
	 */
	public Long getTipoBusquedaId() {
		return tipoBusquedaId;
	}
	/**
	 * @param tipoBusquedaId the tipoBusquedaId to set
	 */
	public void setTipoBusquedaId(Long tipoBusquedaId) {
		this.tipoBusquedaId = tipoBusquedaId;
	}
	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}
	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	/**
	 * @return the parametros
	 */
	public List<String> getParametros() {
		return parametros;
	}
	/**
	 * @param parametros the parametros to set
	 */
	public void setParametros(List<String> parametros) {
		this.parametros = parametros;
	}
    
}
    
    
	
    
