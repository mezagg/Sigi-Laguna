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
     * Entidad federativa donde estÃ¡ el despliegue
     */
    ENTIDAD_FEDERATIVA_DESPLIEGUE, //1
    /**
     * En dÃ­as.
     */
    LIMITE_HISTORICO_CONSULTAS,//2
    /**
     * Incrementa la pena a un delito
     */
    CALIFICATIVA,//3
    /**
     * Decrementa la pena a un delito
     */
    MODIFICATIVA,//4
    /**
     * Incrementa la pena a un delito
     */
    
    NATURALEZA_DELITO, //5
    TIEMPO_REVISION_ALARMAS, //6
    ID_USUARIO_ROBOT_SISTEMA, //7
    /**
     * URL para localizar el servidor del chat
     */
    URL_SERVIDOR_CHAT,//8
    
    /**
     * Ruta y Nombre donde se tienes el JAVS
     */
    RUTA_JAVS, //9
    
    /**
     * 10-. Valor que permite determinar si se habilita el turno o no.
     * 0 - Deshabilitado, es decir, no se muestra el turno dentro de la aplicaciÃ³n.
     * 1 - Habilitar el turno, es decir, se muestra el turno dentro de la aplicaciÃ³n
     */
    HABILITAR_TURNO, //10
    
    /**
     * Indica la version de BD que se libera
     */
    VERSION_DEL_SISTEMA, //11
    
    /**
     * Valor que permite saber si el chat ya se ejecuto si es cero no se a ejecutado si es 1 ya se ejecuto.
     */
    USUARIOS_CHAT, //12
    
    /**
     * Media para aritmetica para los delitos que pertenecen a un expediente
     */
    MEDIA_ARITMETICA_DELITOS, //13
    
    /**
     * Determina el la validicacion de delito en caso de  
     * 
     */
    VALIDA_DELITO_GRAVE, //14
    
    /**
     * Valor que determina el tiempo permitido de inactividad antes de bloquear la aplicacion
     */
    TIEMPO_BLOQUEO_SESION, //15
    
    /**
     * Indica version de seguridad
     */
    VERSION_SEGURIDAD, //16
    
    /**
     * URL para localizar el servidor de imagenes
     */
    URL_SERVIDOR_IMAGENES, //17
    
    /**
     * Bandera que indica si se deben de mostrar los combos innecesarios dentro de la pantalla de asociaciÃ³n delito-persona
     */
    MUESTRA_COMBOS_DELITO_PERSONA, //18
    
    /**
     * Bandera que indica si se deben de mostrar los alerts (3) para las actuaciones de canalizacion
     */
    MUESTRA_ALERTS_ACTUACIONES, //19
    /**
     * 20-. Indica si se requiere agregar el tipo de expediente Reporte a un expediente
     */
    VALIDA_TIPO_EXPEDIENTE_REPORTE,//20
    /**
     * Bandera que indica si se debe generar un numero de expediente alterno
     */
    NUMERO_EXPEDIENTE_ALTERNO,//21
    /**
     * En el detalle general del expediente, bandera que indica si se mostraran las pestañas de hechos, 
     * involucrados, delitos, objetos y evidencias
     * 1 - Oculta tabs
     * 0 ó cualquier valor distinto - Muestra los tabs
     */
    PESTANAS_JAR, //22
    /**
     * Bandera que indica si se motrara el menú de asignar expedientes en coordinadorAmp
     * 1 - Sí
     * 2 - No
     */
    AUTOASIGNAR_EXPEDIENTES,//23
    
    /**
     * Define el umbral de anticipación para atender audiencias por parte del encargado de Sala
     */
    UMBRAL_ATENDER_AUDIENCIAS,//24
    
    /**
     * Define si se debe de aplicar o no el umbral de anticipación para atender audiencias por parte del encargado de Sala
     */
    APLICAR_REGLA_UMBRAL_AUDIENCIA, //25
    
    /**
     * Texto que se muestra en que ambiente se encuentra el desplegada la aplicación: AMBIENTE DE PRODUCCIÓN o AMBIENTE DE PRUEBAS
     */
    AMBIENTE, //26
    
    /**
     * Bandera que indica si se deben mostrar los controles da sala javs en encargadoSala y juezPj
     */
    CONTROLES_JAVS,  //27
    /**
     * Bandera que indica si se mostrara mensaje de confirmación al cerrar el visor de generacion de documentos
     * 1 - Sí
     * 0 ó cualquier valor distinto - Muestra los tabs
     */
    MENSAJE_DE_CONF_EN_EDITOR_DOC,  //28
    /**
     * Permite definir el año inicial para la busqueda avanzada de expediente por anio,
     * Con dicho parametro se cargara un combobox con la fecha inicial hata el año actual
     */
    ANIO_INICIAL_BUSQUEDA_EXP, //29
    /**
     * Permite definir la ruta en disco duro en donde se guardaran los archivos adjuntados al expediente
     * 
     */
    URL_DESTINO_ARCHIVOS, //30
    /**
     * Valor que permite saber si el trapaso de archivos ya se ejecuto si es cero no se a ejecutado si es 1 ya se ejecuto.
     */
    ACTIVA_DESTINO_ARCHIVOS, //31
    
	/**
	 * Valor que indica si se desea generar el N&uacute;mero de Causa y
	 * N&uacute;mero de carpeta de Ejecuci&oacute;n para Poder Judicial
	 */
    ACTIVA_NUMERO_CAUSA_EJECUCION, //32
    
    /**
     * Valor que indica si se puede editar el N&uacute;mero de Expediente
     * durante la atenci&oacute;n de una solicitud de audiencia
     */
    EDITAR_NUMERO_EXPEDIENTE,//33
    /**
     * Valor que indica si se desea habilitar el modulo de Asesoria Legal, 
     * utilizado para Defensoria
     */
    ACTIVA_MODULO_ASESORIA_LEGAL, //34
    
    /**
     * Conjunto de extensiones validas al momento de adjuntar un documento
     */
    EXTS_VALIDAS_ADJUNTAR_AUDIO, //35
    /**
     * Conjunto de extensiones validas al momento de adjuntar un documento
     */
    EXTS_VALIDAS_ADJUNTAR_IMAGEN, //36
    /**
     * Valor para hacer la actualizacion de turnos en el rol visorTurno
     */
    TIEMPO_REVISION_TURNOS, //37
    
    
    /**
     * Valores para la notificacion por correo de la conclusion de resolicion de audiencia, solo para PJ
     */
    USER_EMAIL_NOTIFICACIONES, //38
    PASSWORD_EMAIL_NOTIFICACIONES,//39
    HOST_EMAIL_NOTIFICACIONES,//40
    PORT_EMAIL_NOTIFICACIONES,//41
    PROTOCOL_EMAIL_NOTIFICACIONES,//42
    AUTH_EMAIL_NOTIFICACIONES,//43
    STARTTLS_EMAIL_NOTIFICACIONES,//44
    SUBJECT_EMAIL_CON_RES_AUD,//45
    CONTENT_EMAIL_CON_RES_AUD,//46
    SUBJECT_EMAIL_SOLICITUD_AUD,//47
    CONTENT_EMAIL_SOLICITUD_AUD,//48
    
  
    URL_SERVIDOR_CAPTURE, //49
    URL_SERVIDOR_PORTAL, //50
      /**
     * Valor para obtener el número de días necesarios para enajenar un bien asegurado
     */
    DIAS_PARA_ENAJENAR, // 51
    NUC_ESTADO,  //52
    NUC_REGION,  //53
    NUC_INSTITUCION, //54
    NUC_DISTRITO  //55
   
    ;

}
