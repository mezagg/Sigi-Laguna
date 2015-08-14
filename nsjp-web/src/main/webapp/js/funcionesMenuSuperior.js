/**
 * 
 * Funciones que se ejecutan con el men� superior
 * 
 */
var iframewindowAPSE = 0;

function asigarPermisos(){
	customVentana("iframewindowAPSE"+iframewindowAPSE, "Asignar permisos sobre Expediente: ", "/asigarPermisosExpediente.do")	
	iframewindowAPSE++;
}

function nuevaDenuncia(id) {

	var idExpediente;
    var numeroExpediente;
    
	$.ajax({
		type: 'POST',
		url:  contextoPagina + '/nuevoExpedienteDenuncia.do',
		data: '',
		dataType: 'xml',
		async: false,
		success: function(xml){
			var option;
			idExpediente=$(xml).find('expedienteDTO').find('expedienteId').text();
			numeroExpediente=$(xml).find('expedienteDTO').find('numeroExpediente').text();
		}
		
	});
	
	idWindowNuevaDenuncia++;
	if (idWindowNuevaDenuncia>=3){
		customAlert("Ya se abrio una ventana");
	}
	else{
		customVentana(	"iframewindowCarpInvNuevaDenuncia"+idWindowNuevaDenuncia, 
						"Carpeta de investigaci�n: "+numeroExpediente, 
						"/IngresarMenuIntermedio.do", "?idNuevaDenuncia=1&pantallaSolicitada=" + pantallaSolicitada);
	}	
}

//crea una nueva ventana para la agenda	
function creaAgenda() {
	customVentana("iframewindowagenda", "Agenda", "/InicioAgenda.do");
}

//Abre una nueva ventana para solicitar evidencia
var idWindowSolicitarEvidencia = 0;
function autorizaEvidencia(rowid) {
    customVentana("iframewindowSolicitarEvidencia" + idWindowSolicitarEvidencia, "Autorizar Solicitud de Evidencia", "/autorizarEvidencia.do", "?rowid=" + rowid);
    idWindowSolicitarEvidencia++;
}	

var idWindowNuevaDenuncia = 0;
function consultaDenuncia(id) {

	var ingresoDenuncia = true;
    
	idWindowNuevaDenuncia++;
	
	// inicializar desde el JSP y cambiar el nombre de 
	// var pantallaSolicitada=4;
	// a
	// pantallaSolicitadaCD
	
	
	customVentana(	"iframewindowCarpInvNuevaDenuncia" + idWindowNuevaDenuncia, 
					"Carpeta de investigaci�n: ", 
					"/BusquedaExpediente.do", 
					"?abreenPenal=abrPenal&ingresoDenuncia=" + ingresoDenuncia + 
					"&idNumeroExpediente=" + id + 
					"&pantallaSolicitada=" + pantallaSolicitadaCD + 
					"&flagIndexProcView=1");							
}

function buscarExpediente() {
	//Permite validar por institon, PG debe de invocar buscarExpedienteConSP.do 
	//y el resto de las instituciones debe de invocar buscarExpediente.do
	
	if(institucionActual == idInstitucionPG){
		customVentana("iframewindowBuscarExpediente", "Buscar Expediente", "/buscarExpedienteConSP.do");
	}else{
		customVentana("iframewindowBuscarExpediente", "Buscar Expediente", "/buscarExpediente.do");		
	}
}
	
function busquedaAvanzadaDeExpediente() {
	var BUSQUEDA_AVANZADA_DE_EXPEDIENTE = 1;
	customVentana("iframewindowBuscarExpediente", "B&uacute;squeda Avanzada de Expediente", "/busquedaAvanzadaDeExpedienteConSP.do", "?tipoOperacion=" + BUSQUEDA_AVANZADA_DE_EXPEDIENTE);
}

function verHistorialExpediente() {
	var CONSULTAR_HISTORIAL_DE_EXPEDIENTE = 2;
	customVentana("iframewindowBuscarExpediente", "Consultar Historial de Expediente", "/busquedaAvanzadaDeExpedienteConSP.do", "?tipoOperacion=" + CONSULTAR_HISTORIAL_DE_EXPEDIENTE);
}

function buscarCaso() {
	customVentana("iframewindowBuscarCaso", "Buscar Caso", "/buscarCaso.do");		
}

function generarDocumento() {
	customVentana("iframewindowGenerarDocumento", "Generar Documento", "/generarDocumento.do");
}

function visorLeyesCodigos() {		
	customVentana("iframewindowRestaurativa", "Leyes y C&oacute;digos", "/detalleLeyesyCodigos.do");    			    		
}

/*
*Funcion que llama a la funcionalidad para generar un visualizador de imagen  $('#imageViewer').click(generaVisorGraficaView);
*/
function generaVisorGraficaView() {
	customVentana("iframewindowWindowImageViewer", "Visor de imagenes", "/VisorGraficas.do");
}	

	/*
*Funcion que lanza la ventana par asoliciutar una transcripcion de audiencia y de audio y video
*/
var idWindowSolicitudTranscripcion = 0;
function muestraSolicitudTranscripcion() {
	idWindowSolicitudTranscripcion++;
	customVentana("iframewindowSolicitudTranscripcion"+idWindowSolicitudTranscripcion, "Solicitud de Transcripci�n", "/solicitarTranscripcionEnPG.do");
}

function reasignarResponsableDeExpediente() {
	customVentana("iframewindowReasignarExpediente", "Reasignar responsable de un expediente en Polic&iacute;a Ministerial", "/reasignarResponsableDeExpediente.do", "");
}


function nuevaDenunciaUI() {
	var idExpediente;
    var numeroExpediente;
    var numeroExpedienteId;
    var numeroCasoNuevo;
    var idNuevaDenuncia = 1;
    //variable que indica si es un ingreso o una consulta
    var ingresoDenuncia = false;
    var registrarUIE = true;

	/*
		Cambia de nuevoExpedienteUI a nuevoExpedienteUISinCaso para Generar Denuncias sin caso
	 */
   	$.ajax({
		type: 'POST',
		url:  contextoPagina + '/nuevoExpedienteUI.do?registrarUIE='+registrarUIE,
		data: '',
		dataType: 'xml',
		async: false,
		success: function(xml){
			var option;
			idExpediente=$(xml).find('expedienteDTO').find('expedienteId').text();
			numeroExpediente=$(xml).find('expedienteDTO').find('numeroExpediente').text();
			numeroExpedienteId=$(xml).find('expedienteDTO').find('numeroExpedienteId').text();
			numeroCasoNuevo=$(xml).find('expedienteDTO').find('casoDTO').find('numeroGeneralCaso').text();
		}
		
	});

	if( ! numeroCasoNuevo){
		numeroCasoNuevo = '- PENDIENTE -';
	}

	idWindowNuevaDenuncia++;
	isWindowOpen = true;
	customVentana(	"iframewindowCarpInvNuevaDenuncia"+idWindowNuevaDenuncia, 
					"Expediente: " + numeroExpediente + " - No. Caso: " + numeroCasoNuevo, 
					"/IngresarMenuIntermedio.do",
					"?detenido=1&numeroGeneralCaso="+numeroCasoNuevo+"&abreenPenal=abrPenal&idNuevaDenuncia="+idNuevaDenuncia +"&ingresoDenuncia="+ingresoDenuncia +"&numeroExpediente="+numeroExpediente+"&pantallaSolicitada="+pantallaSolicitada+"&idNumeroExpedienteop="+numeroExpedienteId+"&idExpedienteop="+idExpediente);
					//"?detenido=1&numeroGeneralCaso="+numeroCasoNuevo+"&abreenPenal=abrPenal&idNuevaDenuncia="+idNuevaDenuncia +"&ingresoDenuncia="+ingresoDenuncia +"&numeroExpediente="+numeroExpediente+"&pantallaSolicitada="+pantallaSolicitada+"&idNumeroExpedienteop="+numeroExpedienteId+"&idExpedienteop="+idExpediente+"&numExpAlter="+true);																																			
}   


function verExpediente(idExpediente, numeroExpediente) {
    customVentana("iframewindowExp" + idExpediente, numeroExpediente, "/IngresarMenuIntermedio.do");			
}

function designaAbogadoDefensor() {
    customVentana("iframewindowAsignaAbogado", "Designar Abogado Defensor", "/designarAbogadoDefensorDefensoria.do");		
}

function nuevaDenuncia() {
    customVentana("iframewindowNuevaDenuncia", "Nueva Denuncia", "/IngresarMenuIntermedio.do","?idNuevaDenuncia=1");		
}
 
function justiciaRestaurativa() {
    customVentana("iframewindowjusticia", "Justicia Restaurativa", "/JusticiaRestaurativa.do");		
}

function registraPersona() {
    customVentana("iframewindowGenerarDocumento", "Acuse de Atencion: 0001", "/DatosGeneralesSolicitud.do");
}

//Ventana de captura de queja ciudadana
function muestraQuejaCiudadana() {
	customVentana("iframewindowQuejaCiudadana", "Queja Ciudadana", "/quejaCiudadana.do"); 
}

function generaCapturaEntrevista() {
	customVentana("iframewindowCapturaEntrevista", "Captura Entrevista", "/CapturaEntrevista.do");	    
}

function denunciaExterna(){
	customVentana("iframewindowRDE", "Registrar denuncia externa", "/registrarDenunciaExterna.jsp");
}

function nuevaVentanaMenuIntermedio(idExpediente,numeroExpediente,numeroExpedienteId,numeroCasoNuevo,pantallaSolicitada){
	var idNuevaDenuncia = 1;
    var ingresoDenuncia = false;
	idWindowNuevaDenuncia++;
	customVentana(	"iframewindowCarpInvNuevaDenuncia"+idWindowNuevaDenuncia, 
					"Expediente: " + numeroExpediente + " - No. Caso: " + numeroCasoNuevo, 
					"/IngresarMenuIntermedio.do",
					"?detenido=1&numeroGeneralCaso="+numeroCasoNuevo+"&abreenPenal=abrPenal&idNuevaDenuncia="+idNuevaDenuncia +"&ingresoDenuncia="+ingresoDenuncia +"&numeroExpediente="+numeroExpediente+"&pantallaSolicitada="+pantallaSolicitada+"&idNumeroExpedienteop="+numeroExpedienteId+"&idExpedienteop="+idExpediente);
}

function cierraVentanaAdjuntarDenuncia(){
	var pantalla ="iframewindowRDE";
	$.closeWindow(pantalla);
}
