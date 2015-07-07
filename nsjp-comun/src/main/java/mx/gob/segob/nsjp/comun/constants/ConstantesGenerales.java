package mx.gob.segob.nsjp.comun.constants;

/**
 * Clase abstracta de constantes generales de la aplicaci&oacute;n
 * @author Emigdio
 *
 */
public abstract class ConstantesGenerales {
	/**
	 * Tipo de contenido de la respuesta al emitir un reporte/documento en PDF
	 */
	public static final String CONTENT_TYPE_PDF="application/pdf";
	/**
	 * Tipo de contenido de la respuesta al emitir un reporte/documento en Excel
	 */
	public static final String CONTENT_TYPE_XLS="application/excel";
	/**
	 * Tipo de contenido de la respuesta al emitir un XML
	 */
	public static final String CONTENT_TYPE_XML = "text/xml";
	
	/**
	 * Tipo de contenido de la respuesta al emitir un javascript
	 */
	public static final String CONTENT_TYPE_JAVASCRIPT = "text/javascript";
	
	/**
	 * Header de HTML para el content disposition
	 */
	public static final String ENCABEZADO_CONTENT_DISPOSITION = "Content-Disposition";
	/**
	 * Header de HTML para el archivo adjunto
	 */
	public static final String ENCABEZADO_ATTACH_FILE_NAME ="attachment; filename=";
	public static final String ENCABEZADO_INLINE_FILE_NAME ="inline; filename=";
	/**
	 * Header de html para el control de cache
	 */
	public static final String ENCABEZADO_CACHE_CONTROL ="Cache-Control";
	/**
	 * Header de HTML para el pragma
	 */
	public static final String ENCABEZADO_PRAGMA ="pragma";
	/**
	 * Header de HTML para el no-cache
	 */
	public static final String ENCABEZADO_NOCACHE ="no-cache";
	/**
	 * Extensi&oacute;n de un archivo PDF
	 */
	public static final String EXTENSION_PDF =".pdf";
	/**
	 * Extensi&oacute;n de un archivo Excel
	 */
	public static final String EXTENSION_XLS =".xls";
	
	/**
	 * Charset ISO 8859 para response de tipo XML.
	 */
	public static final String CHARSET_ISO_8859 = "charset=ISO-8859-1";
	/**
	 * Tamanio maximo del nombre de un archivo 
	 */
	public static final int TAM_NOMBRE_ARCHIVO = 20;
		
	public final static String HTML_APERTURA = "<html>";
	
	public final static String HEAD_APERTURA = "<head>";
	
	public final static String HEAD_CIERRE = "</head>";
	
	public final static String BODY_TAG_APERTURA = "<body>";
	
	public final static String BODY_TAG_CIERRE = "</body>";
	
	// Tags definidas para la recuperaci&oacute;n del documento en el editor
	public final static String CUERPO_DOCUMENTO_TAG_APERTURA = "<cuerpoDocumento>";
	
	public final static String CUERPO_DOCUMENTO_TAG_CIERRE = "</cuerpoDocumento>";
	
	// Tag definida para la b&uacute;squeda del documento en el xml recuperado en el JSP  
	public final static String CUERPO_DOCUMENTO_TAG_BUSQUEDA = "cuerpoDocumento";

	// Tags definidas para la recuperaci&oacute;n del nu_mero de folio en el editor
	public final static String NUMERO_FOLIO_TAG_APERTURA = "<numeroFolio>";
	
	public final static String NUMERO_FOLIO_TAG_CIERRE = "</numeroFolio>";
	
	//Tag definida para la b&uacute;squeda del nu_mero de folio en el xml recuperado en el JSP
	public final static String NUMERO_FOLIO_TAG_BUSQUEDA = "numeroFolio";
	
	//Tag definida para sustituir la agencia y el puesto del usuario logueado en la plantilla
	public final static String AGENCIA_USUARIO_LOG_TAG_SUSTITUIDA = "discriUsuarioLog";
	public final static String PUESTO_USUARIO_LOG_TAG_SUSTITUIDA = "puestoUsuarioLog";
	
	//Tags definidas para los catalogos de causa
	public final static Long VEHICULO_CAUSA_RECUPERADO_ID = 2L;
	public final static Long VEHICULO_CAUSA_RECUPERADO_OTROS_ID = 7L;
	public final static Long VEHICULO_RELACION_HECHOS_RECUPERADO_ID = 6547L;
	public final static Long VEHICULO_RELACION_HECHOS_ENTREGADO_ID = 30014L;
	
	//Tag definida para discriminar el caralogo de conclusion y determinar si se mustra el subcatalogo
	public final static Long HECHOS_TIPO_CONCLUSION_ARCHIVO_TEMP_ID = 30653L;
	
	//Direccion de procuraduria
	public final static String DIRECCION_PGJ = "Av. Humberto Castilla Salas No.600, " +
			"Centro Metropolitano de Saltillo, C.P.: 25050 , Teléfonos:  (844) 438 0700";
	
	//Tags para replazo en el envio del correo de notificaciones para conclusion de audiencia
	public final static String ID_AUDIENCIA_TAG_BUSQUEDA = "<idAudiencia>";
	public final static String TIPO_AUDIENCIA_TAG_BUSQUEDA = "<tipoAudiencia>";
	public final static String CARACTER_AUDIENCIA_TAG_BUSQUEDA = "<caracterAudiencia>";
	public final static String NUM_CASO_AUDIENCIA_TAG_BUSQUEDA = "<numCaso>";
	public final static String NUM_CAUSA_AUDIENCIA_TAG_BUSQUEDA = "<numCausa>";
	public final static String FCH_INICIO_AUDIENCIA_TAG_BUSQUEDA = "<fchInicioAudiencia>";
	public final static String HR_INICIO_AUDIENCIA_TAG_BUSQUEDA = "<hrInicioAudiencia>";
	public final static String SALA_AUDIENCIA_TAG_BUSQUEDA = "<nombreSala>";
	public final static String FCH_FIN_AUDIENCIA_TAG_BUSQUEDA = "<fchFinAudiencia>";
	public final static String HR_FIN_AUDIENCIA_TAG_BUSQUEDA = "<hrFinAudiencia>";
	public final static String EDO_AUDIENCIA_TAG_BUSQUEDA = "<edoAudiencia>";
	
	//Tags para remplazo en el envio del correo de notificaciones para solicitud de audiencia
	public final static String DESTINATARIO_SOL_AUD_TAG_BUSQUEDA = "<destinatarioAud>";
	public final static String DISTRITO_DES_AUD_TAG_BUSQUEDA = "<distritoDestAud>";
	public final static String TRIBUNAL_DES_AUD_TAG_BUSQUEDA = "<tribunalDestAud>";
	public final static String SOLICITANTE_SOL_AUD_TAG_BUSQUEDA = "<solicitante>";
	public final static String INSTITUCION_SOLICITANTE_TAG_BUSQUEDA = "<institucionSol>";
	public final static String FCH_LIMITE_SOL_AUD_TAG_BUSQUEDA = "<fchLimiteAud>";
	public final static String HR_LIMITE_SOL_AUD_TAG_BUSQUEDA = "<hrLimiteAud>";
	
	//Tag utilizado para discriminar el catalogo de consciencia y determinar si se muestra el subcatalogo
	public final static Long DATOS_GRLES_EDO_CONSCIENCIA_INCONSCIENTE_ID = 30746L;
	
	//Tags utilizados para buscar al encargado de causa para la notificacion de conslusion de audiencia
	public final static Long ID_ROL_ENCARGADO_CAUSA_PJ = 16L;
	public final static Long ID_CATDISCRIMINANTE_CENTRO_JUSTICIA = 2L;
	
	public final static String HTML_CIERRE = "</html>";
	
	public final static String C_DATA_OPEN = "<![CDATA[";
	
	public final static String C_DATA_CLOSE = "]]>";
	
	public final static String XML_CELL_OPEN = "<cell>";
	
	public final static String XML_CELL_CLOSE = "</cell>";
	
	public final static String DIV_TAG_CLASS_CELDA_GRID_OPEN = "<div class='celdaGrid'>";
	
	public final static String DIV_TAG_CLOSE = "</div>";
	
	public final static String APERTURA_CAMPO_FORMATO = "&lt;";
	
	public final static String CIERRE_CAMPO_FORMATO = "&gt;";
	
	public final static String VALOR_OMISION_GRID = "---";
	
	public final static String[] HORAS_AUDIENCIA = new String[] { "09:00",
			"09:30", "10:00", "10:30", "11:00", "11:30", "12:00", "12:30",
			"13:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00",
			"16:30", "17:00", "17:30", "18:00", "18:30", "19:00", "19:30",
			"20:00", "20:30", "21:00", "21:30", "22:00", "22:30", "23:00" };
	
	
	
	//Audiencias
	/**
	 * N&uacute;mero de jueces necesarios para una audiencia del tipo Juicio Oral
	 */
	public final static int JUECES_AUTOMATICOS_JUICIO_ORAL = 3;
	/**
	 * N&uacute;mero de jueces necesarios para una audiencia que no sea del tipo Juicio Oral
	 */
	public final static int JUECES_AUTOMATICOS_PREDETERMINADO = 1;
	/**
	 * N&uacute;mero de jueces sustitutos para la asignaci&oacute;n autom&aacute;tica 
	 */
	public final static int JUECES_AUTOMATICOS_SUSTITUTOS = 1;
	
	public final static int DIAS_ATRAS_CONSULTAS_HISTORICAS=120;
		
	/////////////////////////////////////////////////////////////////////////////////////////////////////////
	// El siguiente bloque de valores, no deben ser modificados, a menos de que los valores de retorno en el
	// WS-JAVS se modifiquen, de lo contrario, el flujo normal de tales acciones no ser&aacute; as&iacute;.
	
	// Constantes de retorno del Servidor Web de JAVS - Agendar audiencias
	
	public final static int FALLO_CONEXION_WEB_SERVICE_JAVS=100;
	public final static int EXITO_AGENDAR_JAVS=1;
	public final static int EXITO_AGENDAR_JAVS_SIN_ARCH_OUT=2;
	public final static int EXITO_AGENDAR_JAVS_SIN_ARCH_IN=3;
	public final static int EXITO_AGENDAR_JAVS_SIN_ARCH_INOUT=4;
	public final static int ERROR_AGENDAR_JAVS=0;
	public final static int ERROR_PARAMETROS_CONEXION=5;
	public final static int ERROR_ELEMENTOS_INSUFICIENTES=6;
	public final static int ERROR_CREDENCIALES=7;
	
	// Constantes de retorno del Servidor Web de JAVS - Consultar estado audiencias
	
	public final static int AUDIENCIA_NO_ACTIVA=8;
	public final static int FALLO_GENERAL=11;	
	public final static int FALLO_GENERAL_JAVS=12;
	public final static int ERROR_CREDENCIALES_CONSULTA=13;
	public final static int AUDIENCIA_PROCESO=21;
	public final static int AUDIENCIA_TERMINO=20;
	
	public final static int NO_ES_JAVS=24;
	
	// Constantes de retorno del Servidor Web de JAVS - Eliminar audiencias
	
	public final static int ERROR_ELIMINACION=27;
	public final static int EXITO_ELIMINACION=26;
	public final static int NO_HAY_AUDIENCIAS=25;
	public final static int ERROR_SERVICIO_ELIMINACION=28;
	
	// Constantes de retorno del Servidor Web de JAVS - Consultar audiencias    8, 11, 12, 13, 25
	
	public final static int RESOLUTIVOS_ACTUALIZADOS=9;
	public final static int AUDIENCIA_ACTUALIZADA=10;
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////	
	
//	Prueba para imprimir  caracteres
//	public static void main(String  args[]){
//		for(int cont=0; cont < CARACTERES_HTML.length; cont++){
//		}
//	}
}
