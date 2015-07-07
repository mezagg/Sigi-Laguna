/**
 * Nombre del Programa : Parametros.java
 * Autor                            : vaguirre
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 19 Jul 2011
 * Marca de cambio        : N/A
 * Descripcion General    : Describir el objetivo de la clase de manera breve
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
package mx.gob.segob.nsjp.comun.enums.configuracion;

/**
 * Describir el objetivo de la clase con punto al final.
 * 
 * @version 1.0
 * @author vaguirre
 * 
 */
public enum Parametros {
    /**
     * Entidad federativa donde está el despliegue
     */
    ENTIDAD_FEDERATIVA_DESPLIEGUE,
    /**
     * En días.
     */
    LIMITE_HISTORICO_CONSULTAS,
    /**
     * Incrementa la pena a un delito
     */
    CALIFICATIVA,
    /**
     * Decrementa la pena a un delito
     */
    MODIFICATIVA,
    /**
     * Incrementa la pena a un delito
     */
    NATURALEZA_DELITO, TIEMPO_REVISION_ALARMAS, ID_USUARIO_ROBOT_SISTEMA,
    /**
     * URL para localizar el servidor del chat
     */
    URL_SERVIDOR_CHAT,
    
    /**
     * Ruta y Nombre donde se tienes el JAVS
     */
    RUTA_JAVS, 
    
    /**
     * 10-. Valor que permite determinar si se habilita el turno o no.
     * 0 - Deshabilitado, es decir, no se muestra el turno dentro de la aplicación.
     * 1 - Habilitar el turno, es decir, se muestra el turno dentro de la aplicación
     */
    HABILITAR_TURNO,
    
    /**
     * Indica la version de BD que se libera
     */
    VERSION_DEL_SISTEMA,
    
    /**
     * Valor que permite saber si el chat ya se ejecuto si es cero no se a ejecutado si es 1 ya se ejecuto.
     */
    USUARIOS_CHAT,
    
    /**
     * Media para aritmetica para los delitos que pertenecen a un expediente
     */
    MEDIA_ARITMETICA_DELITOS,
    
    /**
     * Determina el la validicacion de delito en caso de  
     * 
     */
    VALIDA_DELITO_GRAVE,
    
    /**
     * Valor que determina el tiempo permitido de inactividad antes de bloquear la aplicacion
     */
    TIEMPO_BLOQUEO_SESION,
    
    /**
     * Indica version de seguridad
     */
    VERSION_SEGURIDAD,
    
    /**
     * URL para localizar el servidor de imagenes
     */
    URL_SERVIDOR_IMAGENES,
    
    /**
     * Bandera que indica si se deben de mostrar los combos innecesarios dentro de la pantalla de asociación delito-persona
     */
    MUESTRA_COMBOS_DELITO_PERSONA,
    
    /**
     * Bandera que indica si se deben de mostrar los alerts (3) para las actuaciones de canalizacion
     */
    MUESTRA_ALERTS_ACTUACIONES,
    /**
     * 20-. Indica si se requiere agregar el tipo de expediente Reporte a un expediente
     */
    VALIDA_TIPO_EXPEDIENTE_REPORTE,
    /**
     * Bandera que indica si se debe generar un numero de expediente alterno
     */
    NUMERO_EXPEDIENTE_ALTERNO,
    /**
     * En el detalle general del expediente, bandera que indica si se mostraran las pesta�as de hechos, 
     * involucrados, delitos, objetos y evidencias
     * 1 - Oculta tabs
     * 0 � cualquier valor distinto - Muestra los tabs
     */
    PESTANAS_JAR,
    /**
     * Bandera que indica si se motrara el men� de asignar expedientes en coordinadorAmp
     * 1 - S�
     * 2 - No
     */
    AUTOASIGNAR_EXPEDIENTES,
    
    /**
     * Define el umbral de anticipaci�n para atender audiencias por parte del encargado de Sala
     */
    UMBRAL_ATENDER_AUDIENCIAS,
    
    /**
     * Define si se debe de aplicar o no el umbral de anticipaci�n para atender audiencias por parte del encargado de Sala
     */
    APLICAR_REGLA_UMBRAL_AUDIENCIA,
    
    /**
     * Texto que se muestra en que ambiente se encuentra el desplegada la aplicaci�n: AMBIENTE DE PRODUCCI�N o AMBIENTE DE PRUEBAS
     */
    AMBIENTE,
    
    /**
     * Bandera que indica si se deben mostrar los controles da sala javs en encargadoSala y juezPj
     */
    CONTROLES_JAVS,
    /**
     * Bandera que indica si se mostrara mensaje de confirmaci�n al cerrar el visor de generacion de documentos
     * 1 - S�
     * 0 � cualquier valor distinto - Muestra los tabs
     */
    MENSAJE_DE_CONF_EN_EDITOR_DOC,
    /**
     * Permite definir el a�o inicial para la busqueda avanzada de expediente por anio,
     * Con dicho parametro se cargara un combobox con la fecha inicial hata el a�o actual
     */
    ANIO_INICIAL_BUSQUEDA_EXP,
    /**
     * Permite definir la ruta en disco duro en donde se guardaran los archivos adjuntados al expediente
     * 
     */
    URL_DESTINO_ARCHIVOS,
    /**
     * Valor que permite saber si el trapaso de archivos ya se ejecuto si es cero no se a ejecutado si es 1 ya se ejecuto.
     */
    ACTIVA_DESTINO_ARCHIVOS,
    
	/**
	 * Valor que indica si se desea generar el N&uacute;mero de Causa y
	 * N&uacute;mero de carpeta de Ejecuci&oacute;n para Poder Judicial
	 */
    ACTIVA_NUMERO_CAUSA_EJECUCION,
    
    /**
     * Valor que indica si se puede editar el N&uacute;mero de Expediente
     * durante la atenci&oacute;n de una solicitud de audiencia
     */
    EDITAR_NUMERO_EXPEDIENTE,
    /**
     * Valor que indica si se desea habilitar el modulo de Asesoria Legal, 
     * utilizado para Defensoria
     */
    ACTIVA_MODULO_ASESORIA_LEGAL,
    
    /**
     * Conjunto de extensiones validas al momento de adjuntar un documento
     */
    EXTS_VALIDAS_ADJUNTAR_AUDIO,
    /**
     * Conjunto de extensiones validas al momento de adjuntar un documento
     */
    EXTS_VALIDAS_ADJUNTAR_IMAGEN,
    /**
     * Valor para hacer la actualizacion de turnos en el rol visorTurno
     */
    TIEMPO_REVISION_TURNOS,
    /**
     * Valores para la notificacion por correo de la conclusion de resolicion de audiencia, solo para PJ
     */
    USER_EMAIL_NOTIFICACIONES,
    PASSWORD_EMAIL_NOTIFICACIONES,
    HOST_EMAIL_NOTIFICACIONES,
    PORT_EMAIL_NOTIFICACIONES,
    PROTOCOL_EMAIL_NOTIFICACIONES,
    AUTH_EMAIL_NOTIFICACIONES,
    STARTTLS_EMAIL_NOTIFICACIONES,
    SUBJECT_EMAIL_CON_RES_AUD,
    CONTENT_EMAIL_CON_RES_AUD,
    SUBJECT_EMAIL_SOLICITUD_AUD,
    CONTENT_EMAIL_SOLICITUD_AUD;
}
