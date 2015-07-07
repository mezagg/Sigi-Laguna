/**
 * Nombre del Programa : Error.java
 * Autor                            : vaguirre
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 7 Apr 2011
 * Marca de cambio        : N/A
 * Descripcion General    : Enumeración para agrupar los códigos de error
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
package mx.gob.segob.nsjp.comun.enums.excepciones;

/**
 * Enumeración para agrupar los códigos de error.
 * 
 * @version 1.0
 * @author vaguirre
 * 
 */
public enum CodigoError {
    /**
     * Para cuando algún parametro no tiene el formato requerido.
     */
    FORMATO,
    /**
     * Para cuando los parametros son insuficientes para la ejecución del
     * servicio.
     */
    PARAMETROS_INSUFICIENTES, 
    /**
     * Para cuando se intenta realizar una operación sin haber cumplido la precondiciones
     * necesarias para su ejecución
     */
    EJCUCION_OPERACION_ESTADO_INCORRECTO, 
    /**
     * Para cuando se da un rango de fechas y la FInicial es mayor o igual a FFinal
     */
    RANGO_FECHAS_CRUZADAS,
    /**
     * Para cuando hay un error de comunicación con los web services.
     */
    ERROR_COMUNICACION, 
    /**
     * Para cuando una transformación de una fecha falla
     */
    ERROR_TRANSORMACION_FECHAS, 
    /**
     * Para cuando la información de los parametros de entrada propician un 
     * comportamiento inesperado o erroneo
     */
    INFORMACION_PARAMETROS_ERRONEA,
    /**
     * Para cuando se desea programar una audiencia en una sala que ya esta ocupada
     * (valida para cuando dos usuarios distintos desean programar al mismo tiempo)
     */
    SALA_OCUPADA,
    
    /**
     * Codigo para cuando se intenta asociar un documento a una audiencia, y este se encuentra ya asociado
     * en la tabla AudienciaDocumento
     */
    DOCUMENTO_YA_ASOCIADO,
    
    /**
     * Para cuando se desea cancelar una audiencia
     * (valida para cuando dos usuarios distintos desean programar al mismo tiempo)
     */
    AUDIENCIA_CANCELADA,
    
    /**
     * Representa si una audiencia no se puede cancelar dado que se encuentra en estatus diferente a SOLICITADA / PROCESO
     */
    FALLA_CANCELACION_AUDIENCIA,
    
    /**
     * Codigo para controlar la excepcion en la que no se ecuentra la clave del distrito en forma romana
     */
    CLAVE_ROMANA_DISTRITO_INEXISTENTE,
    /**
     * Codigo para controlar la excepcion en la que no se ecuentra el identificador de un catalogo en otra institucion
     */
    INCOMPATIBILIDAD_DE_CATALOGOS_ENTRE_INSTITUCIONES,
    /*
     * Codigo para controlar la excepcion en la que no se encuentran funcionarios registrados en una UIE
     */
    FUNCIONARIOS_NO_DISPONILBES,
    
    /**
     * Cuando la clave interinstitucional de un delito no se encuentra en la Base de Datos
     */
    CLAVE_DELITO_INTERINSTITUCIONAL_INEXISTENTE,
    
    /**
     * Cuando ocurre algun error al intertar leer la BD
     */
    FALLA_OPERACION_BD,
    
    /**
     * Cuando se desea hacer una operacion con la audiencia y no es permitida por que
     * ya esta finalizada
     */
    AUDIENCIA_FINALIZADA,
    
	/**
	 * Si no se ha efecutado la Replica de Caso (Generar Denuncia) o, se efectuo
	 * pero con errores; y no se ha registrado dicho caso en la insituci&oacute;
	 * donde se ha consultado.
	 */
    SIN_CASO_ASOCIADO,
    
    /**
	 * Si no se tiene actualizado el catalogo de los distritos entre
	 * instituciones
	 */
    CLAVE_DISTRITO_INTERINSTITUCIONAL_INEXISTENTE,

    /**
     * Cuando se busca un expediente y no existe
     */
	NO_EXISTE_EXPEDIENTE,
	
	/**
	 * El numero de expediente cuenta con una solicitud de carpeta de investigacion
	 * abierta o en proceso
	 */
	NUM_EXP_CON_SOLICITUD_ACTIVA_DE_CARPETA_DE_INVESTIGACION,
	
	/**
	 * 
	 */
	MISMO_FUNCIONARIO,
	
	/**
	 * No se obtuvo respuesta de la solicitud de defensor en defensoria
	 */
	NO_SE_REGISTRO_LA_SOLICITUD_DE_DEFENSOR,
    
	/**
	 * No se ha podido generar el folio de la relacion delito persona
	 */
	IMPOSIBLE_GENERAR_FOLIO_RELACION_DELITO_PERSONA,
    
	/**
	 * No se ha generado el acta de audiencia
	 */
    SIN_ACTA_DE_AUDIENCIA,
    
    /**
     * Se genero el acta de audiencia pero no se ha generado un guardado definitivo
     */
    ACTA_DE_AUDIENCIA_CON_GUARDADO_PARCIAL,
    
	/**
	 * Cuando el nuevo numero de expediente es igual numero de expediente que se
	 * desea actualizar.
	 */
	MISMO_NUMERO_DE_EXPEDIENTE,
	
	/**
	 * Cuando ya existe el numero expediente que se desea ingresar
	 */
	YA_EXISTE_EL_NUMERO_EXPEDIENTE,
	
	/**
	 * Cuando el funcionario no existe
	 */
	FUNCIONARIO_INEXISTENTE,
	
	/**
	 * Cuando la audiencia no existe
	 */
	AUDIENCIA_INEXISTENTE,
	
	/**
	 * Cuando una o mas relaciones ya estan sociadas a un mismo tipo de 
	 * mandamiento dentro de la misma audiencia
	 */
	UNA_O_MAS_RELACIONES_YA_ESTAN_ASOCIADAS_AL_TIPO_DE_MANDAMIENTO,
    
    /**
	 * Cuando se obtiene la sesion de usuario nula
	 */
	SESSION_NULA,

	/**
	 * Cuando se intenta solicitar audiencia, no se puede generar la solicitud
	 */
	IMPOSIBLE_GENERAR_SOLICITUD_DE_AUDIENCIA;
}
