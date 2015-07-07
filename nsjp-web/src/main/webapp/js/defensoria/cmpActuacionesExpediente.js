/**
 * Este archivo contiene las funciones que le otorgan su funcionalidad al contenido
 * del archivo actuaciones.jsp, el mismo que puede ser reutilizado sin mayor parametrizacion
 * que pasar a la funcion de inicializacion el numero del expediente y definir la variable contextPath
 */

var actExpedienteId = 0;
var actEtapaExpediente = "";
var actNumeroExpedienteId = 0;
var actNumeroExpedienteSt = "";
var actInvolucradosIds = [];

function inicilizarTabActuaciones(expedienteId, numeroExpedienteId, numeroExpedienteSt, etapaExpediente, involucrados, situacionActualDefendido){
	actExpedienteId = expedienteId;
	actNumeroExpedienteId = parseInt(numeroExpedienteId);
	actNumeroExpedienteSt = numeroExpedienteSt;
	actEtapaExpediente = etapaExpediente;
	actInvolucradosIds = involucrados;
	actSituacionDefendido = situacionActualDefendido;
	
	cargaActuaciones(actNumeroExpedienteId, actNumeroExpedienteSt);
//	$("#cbxAcciones").change(accionesExpediente);
	$("#cbxAcciones").dblclick(function(e){
		accionesExpediente();
	});

	$("#teoriaDelCaso").hide();
	cargarComboCambiarEtapa();
	cargarComboSituacionDefendido();
}

/**
 * Funcion que carga el catalogo de Actuaciones en el combo cbxAcciones, recicbe
 * como parametros el identificador del numero de expediente y/o el numero del expediente,
 * en caso de omitire el parametro <code>numeroExpedienteId</code> o no disponer del mismo,
 * se puede enviar solo la cadena numeroExpedientSt que requerira que previamente se encuente
 * cargado el expediente en sesión. 
 * @param numeroExpedienteId Identificador del Expediente de trabajo (relacionado a la entidad NumeroExpediente)
 * @param numeroExpedienteSt Cadena con el numero de expediente que identifica al expediente de trabajo
 */
//MOD defensorATE
function cargaActuaciones(numeroExpedienteId, numeroExpedienteSt) {
	$('#cbxAcciones').empty();
	var param = "";
	numeroExpediente = numeroExpedienteSt;
	param += "numeroExpedienteId="+numeroExpedienteId;
	param += "&numeroExpediente="+numeroExpedienteSt;
	var accion = contextPath+'/cargarActuaciones.do';
	peticionSincronaAjaxXML(accion, param, parseActuacionesXMLResponse);
}

//MOD defensorATE
function parseActuacionesXMLResponse(xml){
	$(xml).find('catActuaciones').each(function(){
		$('#cbxAcciones').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
	});
}

/*
* Funcion para sacar el valor del select de acciones
*/
//MOD defensorATE
function accionesExpediente() {
	var selected = $("#cbxAcciones option:selected");
	var confActividadId=selected.val();
	if(isEmpty(confActividadId)){
		return;
	}
	var param = "idConf="+confActividadId;
	
	var accion = contextPath+'/obtenerConfActividadDocumento.do';
	peticionSincronaAjaxXML(accion, param, ejecutarActuacionXMLResponse);	
}

//MOD defensorATE
function ejecutarActuacionXMLResponse(xml){
	var actividad=$(xml).find('confActividadDocumentoDTO').find('tipoActividadId').text();
	var formaID=$(xml).find('confActividadDocumentoDTO').find('formaId').text();
	var titulo=$(xml).find('confActividadDocumentoDTO').find('nombreActividad').text();
	var usaeditor=$(xml).find('confActividadDocumentoDTO').find('usaEditor').text();
	var estatusId=$(xml).find('confActividadDocumentoDTO').find('estadoCambioExpediente').find('idCampo').text();
	
	//TODO GBP Revisar las actuaciones
  	switch (parseInt(actividad)) {
		case 1656: //Actividades.GENERAR_ACUERDO_CON_AMP
		case 2481: //Actividades.GENERAR_LIBRO_DE_GOBIERNO
			asociarActividadExpediente(actividad);
			generarDocumentoSinCaso(formaID, titulo, usaeditor,actividad,false,false);
		break;
		case 2193: //Actividades.GENERAR_ACUERDO_DE_DEFENSA
			asociarActividadExpediente(actividad);
			generarDocumentoSinCasoConArbol(formaID, titulo, usaeditor);
		break;
		case 2192: //Actividades.GENERAR_CEDULA_DE_IDENTIFICACION
			asociarActividadExpediente(actividad);
			generarDocumentoSinCasoConArbol(formaID, titulo, usaeditor);
		break;
		case 2522: //Actividades.ADJUNTAR_ACUERDO_RESTAURATIVO
			actuacionAdjuntarDocumento('0', formaID);
		break;
		case 2483: //Actividades.SOLICITAR_CARPETA_DE_INVESTIGACION
			actuacionEnviarSolicitudCarpetaInvestigacion();
		break;
		case 2408: //Actividades.SOLICITAR_SERVICIO_PERICIAL
		case 2291: //Actividades.SOLICITAR_DICTAMENES_PERICIALES
			actuacionEnviarSolicitudServicioPericial();
		break;

		case 2255: //Actividades.SOLICITAR_AUDIENCIA
			actuacionEnviarSolicitudAudiencia();
        break;
		case 2261: //Actividades.SOLICITAR_TRANSCRIPCION_DE_AUDIENCIA
		case 2256: //Actividades.SOLICITAR_COPIA_DE_AUDIO_Y_VIDEO_DE_AUDIENCIA
			enviarSolicitudTranscripcionAudioyVideoAudienca();
        break;
		case 7477: //Actividades.GENERAR_ACUSE_DE_RECIBO_DE_CARPETA_DE_INVESTIGACION
			generarDocumentoSinCaso(formaID, titulo, usaeditor, actividad,true,true);
		break;
		case 9804: //Enviar Solicitud de Asesoría Legal
			generarDocumentoSinCaso(formaID, titulo, usaeditor, actividad,true,true, true);
		break;
		default:
			generarDocumentoSinCaso(formaID, titulo, usaeditor, actividad,null,true);
	}
}

function asociarActividadExpediente(actividad){
	var param = 'numeroExpedienteId=' + actNumeroExpedienteId; 
	param += '&actividad=' + actividad;
	param += '&numeroExpediente=' + actNumeroExpedienteSt;
	var accion = contextPath+'/asociarActividadExpediente.do';
	peticionSincronaAjaxXML(accion, param);
}

//Variable para controlar el id de la ventana
var idWindowPantallaActuaciones=1;

//MOD defensorATE, defensor
function generarDocumentoSinCaso(forma, titulo, usaeditor,actividadId,ocultarGuardadoParcial,ocultarNumeroOficio, esEnviarSolicitud) {
 	if(usaeditor){
 		var accion = contextPath+"/generarDocumentoSinCaso.do?";
 		accion += "esconderArbol=0";
 		accion += "&formaId="+ forma;
 		accion += "&numeroUnicoExpediente="+ numeroExpediente;
 		accion += "&actividadId="+ actividadId;
 		//Se manda como cadena, o en su defecto no se manda dicho parametro. No toma el false 
 		if(ocultarGuardadoParcial!=null && ocultarGuardadoParcial==true){
 			accion += "&ocultarGuardadoParcial="+true;
 		}
 		accion += "&ocultarNumeroOficio="+ ocultarNumeroOficio;
 		accion += '&idWindowPantallaActuaciones=' +idWindowPantallaActuaciones;
 		accion += '&esTransaccional=true';
 		accion += '&esEnviarSolicitud=' + esEnviarSolicitud ;
 		
 		openNewIframeWindow("iframewindowGenerarDocumento"+idWindowPantallaActuaciones, accion, titulo, 1140, 400, 200, 50);
 		
 		idWindowPantallaActuaciones++;
 	}else{
		generarDocumentoDirecto(forma, '', '', actNumeroExpedienteSt);
 	}
}


/**
 * Funci&oacute;n que es invocada por el generarDocuementoSinCaso.jsp
 * la cual cierra el editor
 */
function cerrarVentanaDocumento(id){
	var pantalla ="iframewindowGenerarDocumento";
	pantalla += id;
	$.closeWindow(pantalla);
}

/**
 * Funcion que se encarga de cerrar la ventana de acuerdo al ID 
 * @param idWindowPantallaActuaciones
 */
//MOD defensorATE
function cerrarVentanaGenerarDocumentoDefensor(idWindowPantallaActuaciones){
	var pantalla ="iframewindowGenerarDocumento";
	pantalla += idWindowPantallaActuaciones;
	$.closeWindow(pantalla);
}

/**
 * Funcion que se encarga de vaciar el combo de actuaciones
 * e invocar la funcion para recargar las actuaciones
 * de acuerdo al estatus del numero de expediente.
 */
//MOD defensorATE
function recargarActuaciones(){
	$('#cbxAcciones').empty();
	//Parametros de indexDefensoriaAtencionTemprana.jsp
	recargaComponentes(true, true, true);
}


/**
 * Funcion que se encarga de cerrar la pantalla despues de
 * ejecutar una envio de informacion desde generarDocuementoSinCaso.jsp
 * @param idWindowPantallaCerrar
 */
function cerrarPantallaActuaciones(idWindowPantallaCerrar){
	var pantalla ="iframewindowGenerarDocumento";
	pantalla += idWindowPantallaCerrar;
	$.closeWindow(pantalla);
}

function generarDocumentoSinCasoConArbol(forma, titulo, usaeditor) {
 	if(usaeditor){
 		var accion = contextPath+"/generarDocumentoSinCaso.do?";
 		accion += "&formaId="+ forma;
 		accion += "&numeroUnicoExpediente="+ numeroExpediente;
 		openNewIframeWindow("iframewindowGenerarDocumento", accion, titulo, 1140, 400, 200, 50);
 	}else{
		generarDocumentoDirecto(forma, '', '', actNumeroExpedienteSt);
 	}
}

function actuacionAdjuntarDocumento(tipo, forma){
	var accion = contextPath+"/adjuntarDocumento.do?";
	accion += "tipo="+tipo;
	accion += "&esconderArbol=0";
	if(!isEmpty(forma)){
		accion += "&formaId="+ forma;
	}
	accion += "&idExpediente="+actExpedienteId;
	accion += "&numeroExpedienteCadena="+ actNumeroExpedienteSt;
	openNewIframeWindow("iframewindowAdjuntarDocumento", accion, "Adjuntar documento", 300, 200, 650, 50);
}

function cerrarVentanaAdjuntarDocumento(){
	var pantalla ="iframewindowAdjuntarDocumento";
	$.closeWindow(pantalla);
}

function actuacionEnviarSolicitudCarpetaInvestigacion(){
	customConfirm("¿Confirma la solicitud de Carpeta de Investigaci&oacuten para el expediente ?", 
			"Solicitud de Carpeta de Investigaci&oacuten", function (){
				var accion = contextPath+"/solictarCarpetaInvestigacion.do";
				var  params = "numeroExpedienteId="+actNumeroExpedienteId;
				peticionSincronaAjaxXML(accion, params, parseSolicitudCarpetaInvestigacionXMLResponse);
	});
}

function parseSolicitudCarpetaInvestigacionXMLResponse(xml){
	var docum = $(xml).find("body").find("solicitud").find("documentoId").text();
	var folio = $(xml).find("body").find("solicitud").find("folioSolicitud").text();
	var msg = $(xml).find("body").text();
	if(docum != undefined && docum != null && docum != ""){
		
		customConfirm("Se envio la Solicitud de Carpeta de Investigaci&oacuten con Folio "+folio+"", 
				"Solicitud de Carpeta de Investigaci&oacuten", function(){
			var forma = 13; //Formas.SOLICITUD ;
			var tipoD = 82; //TipoDocumento.SOLICITUD;
			generarDocumentoDirecto(forma, docum, tipoD);
		});
	}else{
		customAlert(msg, "Solicitud de Carpeta de Investigaci&oacuten");
	}	
}

function actuacionEnviarSolicitudServicioPericial(){
	var accion = contextPath+"/solicitarServicioPericial.do";
	accion += "?numeroExpedienteId="+actNumeroExpedienteId;
	accion += "&numeroExpediente="+ actNumeroExpedienteSt;
	openNewIframeWindow("iframewindowVisorSolicitarServicioPericial", accion, "Solicitar Servicio Pericial", 850, 380, 200, 50);
}

function actuacionEnviarSolicitudAudiencia(){
	var accion = contextPath+"/solicitarAudiencia.jsp?";
	accion += "numeroExpediente=" + actNumeroExpedienteSt;
	accion += "&idExpedienteSoli="+ actExpedienteId;
	accion += "&idNumeroExpediente="+ actNumeroExpedienteId;
	openNewIframeWindow("iframewindowSolicitarAudiencia", accion, "Solicitud de Audiencia", 750, 570, 20, 20);
}

function enviarSolicitudTranscripcionAudioyVideoAudienca(){
	var accion =contextPath+"/solicitarTranscripcionEnPG.do?";
	accion += "idNumeroExpediente="+actNumeroExpedienteId;
	accion +="&nombreSolicitante="+nombreFuncionario;
	accion +="&apaterSolicitante="+apaterFuncionario;
	accion +="&amaterSolicitante="+amaterFuncionario;
	openNewIframeWindow("iframewindowSolicitudTranscripcion", accion, "Solicitud de Transcripción", 812, 454, 253, 113);
}


function cerrarVentanaSolicitarServicioPericial(){
//	cerrarVentana("iframewindowVisorSolicitarServicioPericial")
}

function cargarComboCambiarEtapa(){
	var accion = contextPath+'/consultarCatalogoEtapaExpediente.do';
	peticionSincronaAjaxXML(accion, "", parseEtapasXMLResponse);
}

function parseEtapasXMLResponse(xml){
	$('#cmbEtapaExpediente').empty();
	$('#cmbEtapaExpediente').append('<option value="-1">- Seleccione -</option>');
	$(xml).find('etapaExpediente').each(function(){
		var tip = parseInt($(this).find('clave').text());
		switch(actEtapaExpediente){
			case 2048: //EtapasExpediente.INTEGRACION
				if(tip == 2049 || tip == 2050 ){
					$('#cmbEtapaExpediente').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
				}
				break;
			case 2049://EtapasExpediente.TECNICA
				$("#teoriaDelCaso").show();
				if(tip == 2051){
					$('#cmbEtapaExpediente').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
				}
				break;
			case 2050://EtapasExpediente.CONCILIACION_Y_MEDIACION
				if(tip == 2049){
					$('#cmbEtapaExpediente').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
		  		}
				break;
		}
	});
}

function cambiarEtapaExpediente(){
	$('#etapaActual').val($("#etapaExpediente").text()); 
	$("#divCambiarEtapa").dialog("open");
	$("#divCambiarEtapa").dialog({ autoOpen: true, 
		modal: true, 
	  	title: 'Cambiar etapa del expediente', 
	  	dialogClass: 'alert',
	  	position: [312,40],
	  	width: 400,
	  	height: 250,
	  	maxWidth: 1000,
	  	buttons:{"Cambiar":function() {
		  		confirmar=confirm("Se cambiará la etapa actual del expediente, ¿está seguro?");
		  		if (confirmar){
			   		var param = "numeroExpedienteId="+actNumeroExpedienteId;
			   		param += "&etapaId="+$('#cmbEtapaExpediente').val();
			   		var accion = contextPath+'/cambiarEtapaDelExpediente.do';
			   		peticionSincronaAjaxXML(accion, param, parseActuacionesXMLResponse);
			   		$(this).dialog("close");
			   		if (typeof parent.recargarGridPorEtapa == 'function' ){
			   			parent.recargarGridPorEtapa(actEtapaExpediente);			   			
			   		}
			   		parent.cerrarEtapa();
			  	}
			},
	  		"Cancelar":function() {
		  		confirmar=confirm("¿Realmente desea salir?");
		  		if (confirmar){
			  		  		$(this).dialog("close");
			  	}else{}
		  	}
	  	}
	});	  	
}	

//Abre una nueva ventana de abre Teoria
function abreTeoria(){
	var accion = contextPath+'/abreTeoriaDelCaso.do?';
	accion += 'idExpediente='+actExpedienteId+'&numeroExpediente='+actNumeroExpedienteSt;
	//openNewIframeWindow("iframewindowAbrirteoria", accion, "Teoria del caso", 1390, 600, 5, 10);
	var iframeString = "<iframe src='"+accion+"' width='1390' height='600' />";
	$.newWindow({id:"iframewindowAbrirteoria", statusBar: true,minimizeButton: false,maximizeButton: false, posx:5,posy:5,width:1390, height:600,title:"Teoria del caso", type:"iframe"});
    $.updateWindowContent("iframewindowAbrirteoria",iframeString);
}


function generarDoctoPrueba(){
	abreVentanaDocumento(29);
}

function abreVentanaDocumento(forma){
	if(forma != ""){		
		var accion = contextPath+'/colocarExpedienteParaDefensaTecnica.do';
		var params = 'numExpediente=' + actNumeroExpedienteSt;
		peticionSincronaAjaxXML(accion, params, parseVentanDocumentoXMLResponse);
		
		accion = contextPath+'/generarDocumentoSinCaso.do?';
		accion += "formaId="+forma;
		accion += "&numeroUnicoExpediente="+actNumeroExpedienteSt;
		openNewIframeWindow("iframewindowGenerarDocumentoDefensaTecnica", accion, "Generar Documento", 1140, 400, 200, 50);	
	}
}

function parseVentanDocumentoXMLResponse(xml){
	var errorCode;
	errorCode=$(xml).find('response').find('code').text();				
	if(parseInt(errorCode)==0){	
		var exp = $(xml).find('numeroExpediente').first().text();
	}
	else{
		//Mostrar mensaje de error
	}
}

function relacionarExpedienteACaso(){
	accion = contextPath+'/RelacionarExpedienteACaso.do?';
	accion += "numeroExpedienteId="+actNumeroExpedienteId;
	openNewIframeWindow("iframeRelacionarExpedienteACaso", accion, "Relacionar expediente "+actNumeroExpedienteSt+" a caso", 1050, 600, 200, 50);	
}

function cargarComboSituacionDefendido(){
	var accion = contextPath+'/consultarCatalogoSituacionJuridica.do';
	peticionSincronaAjaxXML(accion, "", parseSituacionDefendidoXMLResponse);
}

function parseSituacionDefendidoXMLResponse(xml){
	 $('#cmbSituacionDefendido').empty();
	 $('#cmbSituacionDefendido').append('<option value="-1">- Seleccione -</option>');
	 
	 var situacionJuridicaId = 0;
	 
	 $(xml).find('situacionJuridica').each(function(){
		 situacionJuridicaId = $(this).find('clave').text();

		 if(situacionJuridicaId == idImputado || situacionJuridicaId == idSentenciado ||
				 situacionJuridicaId == idDetenido || situacionJuridicaId == idInvestigado ||
				 situacionJuridicaId == idAcusado){
			 $('#cmbSituacionDefendido').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
		 }
	 });	

	
		
	
}

function cambiarSituacionDefendido(){
	$('#situacionActual').val(actSituacionDefendido);  
	$("#divCambiarSituacionDefendido").dialog("open");
	$("#divCambiarSituacionDefendido").dialog({ autoOpen: true, 
		modal: true, 
	  	title: 'Cambiar situación jurídica del defendido', 
	  	dialogClass: 'alert',
	  	position: [312,40],
	  	width: 400,
	  	height: 250,
	  	maxWidth: 1000,
	  	buttons:{"Cambiar":function() {
		  		confirmar=confirm("¿Se cambiará la situación actual del defendido, ¿está seguro?");
		  		if (confirmar){
			   		var param = "involucradoId="+actInvolucradosIds["DEFENDIDO"];
			   		param += "&situacionJuridicaId="+$('#cmbSituacionDefendido option:selected').val();
			   		var accion = contextPath+'/cambiarSituacionDelDefendido.do';
			   		peticionSincronaAjaxXML(accion, param, parseActuacionesXMLResponse);
			   		var nuevaSituacionJuridica = $("#cmbSituacionDefendido option:selected").html();		   		
			   		$("#situacionActual").val(nuevaSituacionJuridica);
			   		actSituacionDefendido = nuevaSituacionJuridica;
			   		$("#cmbSituacionDefendido").val("-1");
			   		
			   		$(this).dialog("close");
			  	}
			},
	  		"Cancelar":function() {
		  		confirmar=confirm("¿Realmente desea cancelar la operación?");
		  		if (confirmar){
			  		  		$(this).dialog("close");
			  	}else{}
		  	}
	  	}
	});	  	
}	

/*
*Funcion que abre el visor de medidas cautelares
*(Por el momento solo paso el numero de causa)
*ocultarAdd  "true" para ocultar u otro valor para no ocultar 
*/
function mostrarVentanaInvolucradosCausa(numeroCausa, ocultarAdd){
	customVentana("iframewindowVisorMedidasCautelares", "Visor de Medidas Cautelares", "/visorMedidaCautelar.do", "?numeroCausa=" + numeroCausa +"&ocultarAdd="+ocultarAdd);
}