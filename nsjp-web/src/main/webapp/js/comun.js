//Bloquea cuando todo el DOM ya esta completo
$(document).ready(function(){
	
	administraEventosDelEditorDeDocumentos();
	bloquearPantalla();
});

/**
 * Des-bloquea cuando las im·genes y elementos ya se han cargado por completo
 * Nota, las cosas con peticiones Ajax no se concideran por lo que aun no podrÌa
 * estar cargado el contenido de esos componente. 
 */ 

$(window).load(
	function() { 
		desbloquearPantalla();
	}
); 


//sombrea los grids al momento de estar cargando
try {
	jQuery.extend(jQuery.jgrid.defaults, { loadui: "block" });		
} catch(e){
	console.info("No utiliza jqgrid",e);
}

/**
 * Realiza una solicitud port al path indicado como parametro, la respuesta
 * la procesa con la "funcionSucces" que debe recibir como parametro un objeto
 * que sera procesado como xml.
 */
// /consultarCasoPorExpediente.do
function ejecutaAction(pathAction, funcionSucces, parametros, esSincrono){
	
    $.ajax({
        type: 'POST',
        url: CONTEXT_ROOT + pathAction + ".do",
        data: parametros == null ? '': parametros,
        dataType: 'xml',
        async: (esSincrono!=undefined? esSincrono : false),
        success: function(respuesta){
            funcionSucces(respuesta);
        }
    });

}

/**
 * pathAction = "/consultarCatalogoTipoBajaEvidencia"
 * idElemento = "tipoDeBaja"
 * aliasDto = "evidencia" En el action, "evidencia" corresponde a:
 *             converter.alias("evidencia", CatalogoDTO.class);
 */
function cargaCatalogo(pathAction, idElemento, aliasDto){
    ejecutaAction(pathAction, function(respuesta){
        var catalogo = $(respuesta);
        catalogo.find(aliasDto).each(function(){
            var elementoCatalogo = $(this);
            var option = $('<option value="' + elementoCatalogo.find('clave').text() + '">' + elementoCatalogo.find('valor').text() + '</option>');
            $('#' + idElemento).append(option);
        });
    });
}

/**
 * Obtiene el valor de los campos cuya clase sea igual al parametro clase
 * y los acomoda como una cadena campo1=valor1&campo2=valor2... etc.
 */
function obtenParametros(clase){
    var parametrosValor = "";
    var parametros = $("." + clase);
    var distintosDeNull = 0;
    for(var i = 0; i < parametros.length; ++i){
        var texto = parametros[i];
        var value = $(texto).val();
        if (value != null && value != "") {
            if (distintosDeNull > 0) {
                parametrosValor += "&" + texto.id + "=" + $(texto).val();
            }else{
                parametrosValor += "" + texto.id + "=" + $(texto).val();
            }
            distintosDeNull++;
        }
    }
    return parametrosValor;
}

function muestraAlert(idElemento, width, height){
    $("#" + idElemento).dialog({ autoOpen: true,
        modal: true,
        title: 'Atenci\u00f3n',
        dialogClass: 'alert',
        position: [200,30],
        width: width == null ? 412: width,
        height: height == null? 120:height,
        maxWidth: 500,
        buttons:{"Ok":function() {
                $(this).dialog("close");
            }
        }
    });
}

function validaParametrosRequeridos(parametros, requeridos){
    var elementosParametros = parametros.split("&");
    var llaves = new Array();
    var valores = new Array();
    var faltantes = new Array();
    // separamos la cadena de parametros en llaves y valores
    for(var i = 0; i < elementosParametros.length; ++i){
        var elementoParametro = elementosParametros[i];
        var llave = elementoParametro.split("=")[0];

        var valor = elementoParametro.split("=")[1];
        llaves[i] = llave;
        valores[i] = valor;
    }
    for(i in requeridos){
        var requerido = i + "";
        var requeridoEncontrado = false;
        for(var j = 0; j < llaves.length; ++j){
//            alert("requerido = " + requerido + " llave = " + llaves[j] + " no esperado = " + requeridos[i] + " encontrado = " + valores[j]);
//            alert("(requerido == llaves[j]) = " + (requerido == (""+llaves[j])));
//            alert("(valores[j] != requeridos[i]) = " + (valores[j] != requeridos[i]));
            if ((requerido == (""+llaves[j])) && (valores[j]) && (valores[j] != "" + requeridos[i])) {
//                alert("encontrado");
                requeridoEncontrado = true;
                break;
            }
        }
        if (!requeridoEncontrado) {
            faltantes[faltantes.length] = requerido;
        }
    }
    return faltantes;
}

function validaDecimalOchoDos(decimal){
	if (decimal == "" 
			|| isNaN(decimal)
			|| decimal <= 0){
		return false;
	} else {
		var numeros = decimal.split('.');
		if(numeros.length == 1){
			return true;
		}
		if (numeros.length > 2
				|| numeros[0].length > 6 // se cambia de 8 a 6 por la longitud en la BD
				|| numeros[1].length > 2){
			return false
		}else{
			return true;
		}
	}
}

function obtenParametroDeUrl(nombre){
    nombre = nombre.replace(/[\[]/,"\\\[").replace(/[\]]/,"\\\]");
    var regexS = "[\\?&]"+nombre+"=([^&#]*)";
    var regex = new RegExp( regexS );
    var results = regex.exec( window.location.href );
    if( results == null ){
        return "";
    }else{
        return results[1];
    }
}

function obtenParametrosDeUrlComoCadena(nombreParametro){
    var parametros = obtenParametrosDeUrlComoArreglo(nombreParametro);
    var resultado = "";
    for(var i = 0; i < parametros.length; ++i){
        var parametro = parametros[i];
        if(i == 0){
            resultado += nombreParametro + "=" + parametro;
        }else{
            resultado += "&" + nombreParametro + "=" + parametro;
        }
    }
    return resultado;
}

/**
 * Regresa un arreglo con los valores de los parametros en la url que son
 * igual a nombreParametro.
 * EG.
 * si la url = htt://...?id=1&id=2&nombre=usuario
 * este metodo regresa el arreglo = [1,2]
 */
function obtenParametrosDeUrlComoArreglo(nombreParametro) {
    var url = window.location.href;
    var request = new Array();
    var agregados = 0;
    var pairs = url.substring(url.indexOf('?') + 1).split('&');
    for (var i = 0; i < pairs.length; i++) {
        var pair = pairs[i].split('=');
        if (pair[0] == nombreParametro) {
            request[agregados++] = decodeURIComponent(pair[1]);
        }
    }
    return request;
}

function llenaCamposEnJspConObjetoXML(camposEnJsp, objetoXml){
    var objetoJquery = $(objetoXml);
    for(var i = 0; i < camposEnJsp.length; ++i){
        var campo = $(camposEnJsp[i]);
        var idCampo = campo.attr("id");
        campo.html(objetoJquery.find("" + idCampo));
    }
}

/**
 * idFrame = "iframewindowSolicitarAudiencia"
 * jsp = "/solicitarAudiencia.jsp"
 * x = 20
 * y = 20
 * width = 740
 * height = 520
 * titulo = "Solicitar Audiencia"
 */
function abreNuevoFrame(idFrame, jsp, x, y, width, height, titulo){
    $.newWindow({
        id:idFrame,
        statusBar: true,
        posx:x,
        posy:y,
        width:width,
        //        height:height,
        title:titulo,
        type:"iframe"
    });
    // height="' + (height) + '"
    $.updateWindowContent(idFrame,'<iframe src="' + CONTEXT_ROOT + jsp + ' "width="'+ (width) +'" height="' + (height) + '" />');
}

function joinComoParametros(parametros, separador){
    var join = "";
    for(var i = 0; i< parametros.length; ++i){
        if(i == 0){
            join += separador + "=" + parametros[i];
        }else{
            join += "&" + separador + "=" + parametros[i];
        }
    }
    return join;
}

function getHoraMinutoActual(fechaActual){
	minutosInc =fechaActual.getMinutes();
	
//	alert(minutosInc);
	if(minutosInc=="0"||minutosInc=="1"||minutosInc=="2"||minutosInc=="3"||minutosInc=="4"||minutosInc=="5"||minutosInc=="6"||minutosInc=="7"||minutosInc=="8"||minutosInc=="9"){
		 return  fechaActual.getHours()+ ":" + "0" +
		    fechaActual.getMinutes()+ " hrs ";
		
	}else{
    return  fechaActual.getHours()+ ":" +
    fechaActual.getMinutes()+ " hrs ";
	}
}

function getMesLetra(date){
    var mes = date.getMonth();
    var mesLetra = "";
    switch (mes) {
        case 0:
            mesLetra = "enero";
            break;
        case 1:
            mesLetra = "febrero";
            break;
        case 2:
            mesLetra = "marzo";
            break;
        case 3:
            mesLetra = "abril";
            break;
        case 4:
            mesLetra = "mayo";
            break;
        case 5:
            mesLetra = "junio";
            break;
        case 6:
            mesLetra = "julio";
            break;
        case 7:
            mesLetra = "agosto";
            break;
        case 8:
            mesLetra = "septiembre";
            break;
        case 9:
            mesLetra = "octubre";
            break;
        case 10:
            mesLetra = "noviembre";
            break;
        case 11:
            mesLetra = "diciembre";
            break;

        default:
            break;
    }
    return mesLetra;

}

/*
 * Funcion que permite solo capturar numeros en los campos de texto
 * Se debe colocar dentro de la accion onkeypressdown del textbox
 * */
function solonumeros(e) {
    var unicode = e.charCode ? e.charCode : e.keyCode;

    // if the key is backspace, tab, or numeric
    if (unicode == 8 || unicode == 9 || (unicode >= 48 && unicode <= 57)) {
        // we allow the key press
        return true;
    }
    else {
        // otherwise we don't
        return false;
    }
}

/*
 * Funcion que permite capturar numeros, punto guion y gato en los campos de texto
 * Se debe colocar dentro de la accion onkeypressdown del textbox
 * */
function numerosYGato(e) {
    var unicode = e.charCode ? e.charCode : e.keyCode;

    // si la tecla es backspace, tab, numeric, gato, punto y guion
    if (unicode == 8 || unicode == 9 || (unicode >= 48 && unicode <= 57) ||  unicode == 35 ||  unicode == 45) {
        return true;
    }
    patron =/[A-Za-z\s]/;
    te = String.fromCharCode(unicode);
    return patron.test(te);
}

/*
 *Funcion que recibe una hora en el formato HH:MM
 *y le da formato para setear un campo TimeEntry con el 
 *formato HH:MMAM o HH:MMPM 
 */
function formateaHoraTimeEntryTextBox(horaSinFormato)
{
	var horas=horaSinFormato.substring(0,2);
	var amPM="AM";
	var horaConFormato="";
	if(parseInt(horas)<12)
	{
		horaConFormato=horaSinFormato+"AM";
	}
	else
	{
		horaConFormato=horaSinFormato+"PM";
	}
	return horaConFormato;
}

/*
 * Funcion para setear los campos de fecha y hora que se generan a partir de los plugins de Jquery de DatePicker y TimeEntry
 * fechaHora - cadena que contiene la fecha y hora regresada de la BD en el formato : 2011-09-08 08:00:00.0
 * idCampoFecha - id del campo fecha donde se seteara la fecha una vez que sea formateada correctamente
 * idCampoHora - id del campo hora donde se seteara la fecha una vez que sea formateada correctamente
 */
function seteaFechaHoraEnCamposPlugin(fechaHora,idCampoFecha,idCampoHora)
{
	var datos=fechaHora.split(' ');
	var fechaBien=datos[0].split('-');
	var hora=datos[1].split(".");
	var horaBien=hora[0].split(":");
	$("#"+idCampoFecha).val(fechaBien[2]+"/"+fechaBien[1]+"/"+fechaBien[0]);
	//destruimos el plugin para el campo de hora
	$("#"+idCampoHora).timeEntry('destroy');
	//seteamos la configuracion del campo hora
	$("#"+idCampoHora).timeEntry({beforeShow: customRange,timeSteps:[1,1,0],ampmPrefix: ' '});
	//generamos de nuevo el campo con el plugin de hora
	$("#"+idCampoHora).timeEntry('setTime', formateaHoraTimeEntryTextBox(datos[1].substring(0,5)));
}

/**
 * Funcion que simula la funcion TRIM de otros lenguajes 
 */
function trim (myString)
{
	return myString.replace(/^\s+/g,'').replace(/\s+$/g,'');
}

/**
 * Funcion para validar si un valor o variable es indefinido o nulo
 */
function existe(val){
	if((val != undefined) && (val != null) && (val != "") && (val.length > 0)){
		return true;
	}
	return false;
}


/*
 * Funcion para regresar la fecha maxima obtenida desde el servidor
 * fechaCompleta - cadena con el siguiente formato : YYYY-MM-DD HH:MI:SS
 * regresa una cadena con la fecha en el formato : DD/MM/YYYY
 */
function getFechaMaximaServer(fechaCompleta)
{
	var arrFechaHora=fechaCompleta.split(" ");
	var digitosFecha=arrFechaHora[0].split("-");
	return digitosFecha[2]+'/'+digitosFecha[1]+'/'+digitosFecha[0];
}

/*
 * Funcion para regresar la hora maxima obtenida desde el servidor
 * fechaCompleta - cadena con el siguiente formato : YYYY-MM-DD HH:MI:SS
 * regresa una cadena con la hora en el formato : HH:MMAM o HH:MMPM
 */
function getHoraMaximaServer(fechaCompleta)
{
	var arrFechaHora=fechaCompleta;
	var horaC=arrFechaHora[1].substring(0,5);
	var horaD=horaC.substring(0,2);
	var horaCorrecta="";
	if(parseInt(horaD)<13)
	{	
		horaCorrecta=horaC+'AM';
	}
	else
	{
		horaCorrecta=horaC+'PM';
	}
	return horaCorrecta;
}

function soloLetrasDos(e) 
{
	var ExpReg_cad=/^[aA-zZ]*$/;
	var unicode = e.charCode ? e.charCode : e.keyCode;
	//alert(unicode);
	//alert("charcode:: "+e.charCode);
	//alert("keycode:: "+e.keyCode);
	if (unicode == 241 || unicode == 46 || unicode == 39 || unicode == 32 || unicode == 8 || unicode == 9 || (unicode >= 65 && unicode <= 90) || (unicode >= 97 && unicode <= 122) || unicode == 165) { //todas las letras en mayuscula
       // we allow the key press
       if(unicode == 39 || unicode == 46)
       {
    		if(e.keyCode==0)
    		{
    			return false;
    		}
    		else
    		{
    			return true;
    		}
       }
       else
       {
       return true;
       }
	}
	else 
	{
       // otherwise we don't
       return false;
	}
	return true;
}
    
//validaRangoFechasyHoras(Fecha Inicial,Fecha Final,Hora Inicial, Hora Final)
function validaRangoFechasyHoras(fechaini,fechafin,horaini,horafin){
	
	var fechaIniVal = fechaini;
	var fechaFinVal = fechafin;
	var hIniVal = horaini; 
	var hFinVal= horafin;
	var inicio = fechaIniVal.split("/");
	var fin = fechaFinVal.split("/");
	var hInicio=hIniVal.split(":");
	var hFin=hFinVal.split(":");


//si el anio fin es mayor termina

 if (fin[2] > inicio[2] )   
{   
	 return(true);
}   
else  
{   
//si el anio fin es = al inicial compara los meses

if (fin[2] == inicio[2])   
{    
//comara que el mes final sea mayor al inicial termina
if (fin[1]> inicio[1])   
{   
	return(true);
}   
else  
{    
	//si el mes fin es = al inicial compara los dias
  if (fin[1] == inicio[1])   
  {   
    if (fin[0]> inicio[0])  
        {
    	return(true);
    	}
	 else  {
		 if (fin[0] ==  inicio[0])   
	      {    
	        //comara que el mes final sea mayor al inicial termina
	        if (hFin[0]> hInicio[0])   
	        {   
	        	return(true);
	        }   
		 else  {
   		alert("La hora l\u00edmite debe de ser mayor a la hora de solicitud");
   		return(false);
 	  		 } 	 
	 }
	 }
  }   
  else  {
	  return(false); 


	  alert("La fecha l\u00edmite debe de ser mayor a la fecha de solicitud");
	  }
	    
}   
}   
else  {

 alert("La fecha l\u00edmite debe de ser mayor a la fecha de solicitud");

      return(false);

 }
  
}   
	
	
	
}

//Funcion que valida si los campos estan llenos al enviar y si es valido el rango de fechas 
function validaCamposFecha(fechaini,fechafin) {

	if (fechaini == '' || fechafin == '') {
		customAlert("Debes ingresar tanto la fecha de inicio como la de fin");
		validaFecha = false;
	} else {

		var fechaIniVal = fechaini;
		var fechaFinVal = fechafin;

		var inicio = fechaIniVal.split("/");
		var fin = fechaFinVal.split("/");

		if ((fin[2]) >= (inicio[2])) {
			if((fin[2]) == (inicio[2])){
				if((fin[1]) >= (inicio[1])){
					if((fin[1]) == (inicio[1])){
						if((fin[0]) < (inicio[0])){
							customAlert("La Fecha de fin debe de ser mayor a la fecha de inicio");
							validaFecha = false;
						}else {
							validaFecha = true;
						}	
					}else{
						validaFecha = true;
					}
				}else{
					customAlert("La Fecha de fin debe de ser mayor a la fecha de inicio");
					validaFecha = false;
				}
			}else{
				validaFecha = true;
			}
		}else{
			customAlert("La Fecha de fin debe de ser mayor a la fecha de inicio");
			validaFecha = false;
		} 		
	}	
	return validaFecha;
}

/**
 * Funcion que valida si el campo numero expediente(causa )esta lleno al enviar 
 * @param valorCampo lo que se obtuvo del campo de texto
 * @returns {Boolean}
 */
function validaCamposExpediente(valorCampo){
	var datosCorrectos=false;
	if( validarCampo(/[^0-9a-zA-Z/-]/g , valorCampo) == false ){
		customAlert("Favor de revisar el n&uacute;mero de causa");
		//Pone el foco en el primer input con clase de error
		$(":input.error").first().focus();
		datosCorrectos=false;
		
	}else{
		if (valorCampo==''){
			customAlert("Favor de ingresar un n&uacute;mero de causa");
			datosCorrectos=false;
		}else {
			datosCorrectos=true;
		}
	}
	return datosCorrectos;
										
}

//Funcion para alertDinamico
function alertDinamicoEvCadCus(textoAlert, id){
	$("#divAlertTexto").html(textoAlert);
    $( "#dialog-Alert" ).dialog({
		autoOpen: true,
		resizable: false,
		zIndex:19500,
		modal: true,
		buttons: {				
			
			"Aceptar": function() {
				$( this ).dialog( "close" );
				$("#divAlertTexto").html("");					
			}				
		}
	});    
 }

//Funcion para alertDinamico
function alertDinamico(textoAlert, id){
	$("#divAlertTexto").html(textoAlert);
    $( "#dialog-Alert" ).dialog({
		autoOpen: true,
		resizable: false,
		//height:290,
		//width:300,
		modal: true,
		buttons: {				
			
			"Aceptar": function() {
				$( this ).dialog( "close" );
				$("#divAlertTexto").html("");					
			}				
		}
	});    
 }

//Funcion para alertDinamico
function alertDinamicoDosBotones(textoAlert, id){
    $("#divAlertTexto").html(textoAlert);
    $( "#dialog-Alert" ).dialog({
		autoOpen: true,
		resizable: false,
		//height:290,
		//width:300,
		modal: true,
		buttons: {				
			"Aceptar": function() {	
				activaConfirm(id);
				$( this ).dialog( "close" );
				$("#divAlertTexto").html("");					
			},
			"Cancelar": function() {
				$( this ).dialog( "close" );
				$("#divAlertTexto").html("");					
			}				
		}
	});    
 }


function customAlert(texto, titulo, funcion){
	var tituloTmp;
	if ( titulo === undefined ) {
		tituloTmp = '';
	}else{
		tituloTmp = titulo;
	}
	
	if (jQuery('#customAlert').length){
		jQuery('#customAlertText').empty();
		jQuery('#customAlertText').html(texto);
	}else{
		jQuery("body").append("<div id='customAlert'>" +
				"<span id=\"customAlertText\">"+ texto+"</span></div>");
	}
	
	jQuery( "#dialog:ui-dialog" ).dialog( "destroy" );
	
	jQuery( "#customAlert" ).dialog({
		resizable: false,
		title: tituloTmp,
		height:'auto',
		width:'auto',
		modal: true,
		autoOpen: false,
		closeOnEscape: false,
		close:function(){
			jQuery('#customAlertText').empty();
		},
		buttons: {
			'Aceptar': function() {
				jQuery( this ).dialog( "close" );
				if (!(funcion === undefined) ) {
					funcion();
				}
			}
		}
	});	
	
	return jQuery( "#customAlert" ).dialog( "open" );
	
}

function customConfirm(texto, titulo, fAceptar, fCancelar){
	
	jQuery( "#dialog:ui-dialog" ).dialog( "destroy" );
	
	var tituloTmp;
	if ( titulo === undefined ) {
		tituloTmp = '';
	}else{
		tituloTmp = titulo;
	}
	
	if (jQuery('#customConfirm').length != 0){
		jQuery('#customConfirmText').empty();
		jQuery('#customConfirmText').html(texto);
	}else{
		jQuery("body").append("<div id='customConfirm'>" +
				"<span id=\"customConfirmText\">"+ texto+"</span></div>");
	}
	
	jQuery( "#customConfirm" ).dialog({
		resizable: false,
		title: tituloTmp,
		height:'auto',
		width:'auto',
		modal: true,
		autoOpen: false,
		closeOnEscape: false,
		close:function(){
			jQuery('#customConfirmText').empty();
			jQuery( "#dialog:ui-dialog" ).dialog( "destroy" );
		},
		buttons:
			[
			    {
			        text: "Aceptar",
			        id:"btn_ccAceptar",
			        click: function() { 
			        			$(this).dialog("close");
			        			if (!(fAceptar === undefined) ) {
			    					fAceptar();
			    				}
			        }
			    },
			    {
			        text: "Cancelar",
			        id:"btn_ccCancelar",
			        click: function() { 
			        			$(this).dialog("close");
			    				if (!(fCancelar === undefined) ) {
			    					fCancelar();
			    				}
			        }
			    }			    
			]
	});	
		
	return jQuery( "#customConfirm" ).dialog( "open" );
	
}


/** m√©todo que crea una ventana:
 * @param id del la ventana
 * @param titulo de la ventana
 * @param url relativa direcci&oacute;n a mostrar eg: /path.do
 * @param parametros que se le enviaran a la ventana debe empezar con ?, eg: ?expediente=1&caso=1
 */

function customVentana(id, titulo, url, parametros, fCerrar) {
	var urlCompleta;
	if ( parametros === undefined ) {
		urlCompleta = contextoPagina + url;
	}else{
		urlCompleta = contextoPagina + url + parametros;
	}
	$.newWindow( {
					id:id, 
					statusBar: true, 
					posx:0,
					posy:0,
					width:$(document).width(),
					height:$(document).height(),
					title:titulo, 
					type:"iframe",
					onWindowClose: function(id){
						if ( !(fCerrar === undefined) ) {
							fCerrar();
						}
					}
	} );
	
	$.maximizeWindow(id);
    $.updateWindowContent(
    		id,
    		'<iframe src="' + urlCompleta + '" width="100%" height="100%" />'
    );
}

/*
 * Funcion para cerrar una ventana mediante su Id
 */
function cerrarVentanaGenerico(idWindow){
	$.closeWindow(idWindow);
}

//Funcion para validar numeros decimales
function isnumeroDecimal(numero) {	
	var num = numero;
	var filtro=/^([0-9])*[.]?[0-9]*$/;
			
	if(filtro.test(num) == false) {
		alertDinamico('El n\u00famero es inv\u00e1lido');
		return false;
	}
	if (filtro.test(num)){
		return true;
	}
	return false;
}

/*
 * Funcion que permite solo capturar numeros decimales en los campos de texto
 * Se debe colocar dentro de la accion onkeypressdown del textbox
 * */
function numerosDecimales(evt){
	var nav = window.Event ? true : false;
	// 'Backspace' = 8, 'Enter' = 13, '0' = 48, '9' = 57, '.' = 46
	var key = nav ? evt.which : evt.keyCode;
	return (key <= 13 || (key >= 48 && key <= 57) || key == 46);
}


/*
 * Funcion que permite solo capturar letras con '—' y 'Ò'
 * Se debe colocar dentro de la accion onkeypressdown del textbox
 * */
function soloLetrasN(e) { 
    if (e.charCode){
    	tecla = e.charCode;
    	op = true;
    }else{
    	tecla =  e.keyCode;
    	op = false;
    }
 // 'Backspace' = 8, 'Tab' = 9, '.' = 46, FlechaCursora <- = 37, FlechaCursora -> = 39
    if (tecla==8 || tecla==9 || tecla==46 || tecla==37 || tecla== 39 ){
    	if(op && tecla == 37){//esto es para el %
    		return false;
    	}
    	return true;		
    }
    patron =/[A-Za-z—Ò·ÈÌÛ˙¡…Õ”⁄‰ÎÔˆ¸ƒÀœ÷‹‡ËÏÚ˘¿»Ã“Ÿ\s]/;
    te = String.fromCharCode(tecla);
    return patron.test(te);
}

/*
 * Funcion que permite solo capturar letras con '—' , 'Ò' y '.'
 * Se debe colocar dentro de la accion onkeypressdown del textbox
 * */
function soloLetrasNPunto(e) { 
	if (e.charCode){
    	tecla = e.charCode;
    	op = true;
    }else{
    	tecla =  e.keyCode;
    	op = false;
    }
    // 'Backspace' = 8, 'Tab' = 9, '.' = 46, FlechaCursora <- = 37, FlechaCursora -> = 39
    if (tecla==8 || tecla==9 || tecla==46 || tecla==37 || tecla== 39 ) {
    	if(op && (tecla == 37 || tecla == 39)){//esto es para el % y '
    		return false;
    	}
    	return true;	
    }
    patron =/[A-Za-z—Ò·ÈÌÛ˙¡…Õ”⁄‰ÎÔˆ¸ƒÀœ÷‹‡ËÏÚ˘¿»Ã“Ÿ\s]/;
    te = String.fromCharCode(tecla);
    return patron.test(te);
}

/*
 * Funcion que permite solo capturar letras con numeros y guion diagonal
 * Se debe colocar dentro de la accion onkeypressdown del textbox
 * */
function letrasNumero(e) { 
	if (e.charCode){
    	tecla = e.charCode;
    	op = true;
    }else{
    	tecla =  e.keyCode;
    	op = false;
    }
    // 'Backspace' = 8, 'Tab' = 9, '.' = 46, FlechaCursora <- = 37, FlechaCursora -> = 39, 45 guion, diagonal
    if (tecla==8 || tecla==9 || tecla==37 || tecla== 39 || tecla == 45 || tecla == 47) {
    	if(op && tecla == 37){//esto es para el %
    		return false;
    	}
    	return true;	
    }
    patron =/[A-Za-z0-9\s]/;
    te = String.fromCharCode(tecla);
    return patron.test(te);
}

function getFrameElement() {
    var iframes = parent.document.getElementsByTagName('iframe');
    for (var i= iframes.length; i-->0;) {
        var iframe = iframes[i];
        try {
            var idoc= 'contentDocument' in iframe? iframe.contentDocument : iframe.contentWindow.document;
        } catch (e) {
            continue;
        }
        if (idoc === document)
            return iframe;
    }
    return null;
}


function cerrarCustomVentana(){
	var iframe = getFrameElement();
	if (iframe != null){
		var idAbuelo = iframe.parentNode.parentNode.id;
		try{
		window.parent.cerrarCustomVentanaDesdeHijo(idAbuelo);
		}catch(e){}
	}
}

function cerrarCustomVentanaDesdeHijo(iframe){
	$.closeWindow(iframe);
}

//Para ejecutar esta funcion, es necesario que la invocacion AJAX sea asincrona
function bloquearPantalla (conImagen, titulo, mensaje){

	var tituloTmp;
	var mensajeTmp;
	
	var imagen =""; 
		try{
		imagen = "<p><center><img src='" + contextoPagina +"/resources/images/loading6.gif' /></center></p>" ;
		} catch(e){
			console.info("No esta definida la variable 'contextoPagina'", e);
		}
	if ( conImagen === undefined) {
		conImagen = false;
	} else {
		 if(imagen.length > 0) 
			 conImagen = true;
	}
	
	if ( titulo === undefined ) {
		tituloTmp = 'Cargando el contenido';
	}else{
		tituloTmp = titulo;
	}

	if ( mensaje === undefined ) {
		mensajeTmp = '<p><center>Espere un momento por favor.</center></p>';
	}else{
		mensajeTmp = mensaje;
	}
	
	if (conImagen){
		mensajeTmp = imagen + mensajeTmp; 
	}
	
	try{
		$.blockUI({ 
			theme:    true, 
			title:    tituloTmp, 
			message:  mensajeTmp 
		});
				
	} catch(e){
		console.info("No esta definido el archivo jquery.blockUI.js",e);
	}
}

function desbloquearPantalla() {
	try{
		$.unblockUI();
	} catch (e){
		console.info("No esta definido el archivo jquery.blockUI.js",e);
		//si el objeto existe pero fallo la funcion del plug-in lo removera por la clase 'blockUI'
		$(".blockUI").remove();
	}
}


function esFecha(value) {
    try {
        //Change the below values to determine which format of date you wish to check. It is set to dd/mm/yyyy by default.
        var DayIndex = 0;
        var MonthIndex = 1;
        var YearIndex = 2;
 
        value = value.replace(/-/g, "/").replace(/\./g, "/"); 
        var SplitValue = value.split("/");
        var OK = true;
        if (!(SplitValue[DayIndex].length == 1 || SplitValue[DayIndex].length == 2)) {
            OK = false;
        }
        if (OK && !(SplitValue[MonthIndex].length == 1 || SplitValue[MonthIndex].length == 2)) {
            OK = false;
        }
        if (OK && SplitValue[YearIndex].length != 4) {
            OK = false;
        }
        if (OK) {
            var Day = parseInt(SplitValue[DayIndex], 10);
            var Month = parseInt(SplitValue[MonthIndex], 10);
            var Year = parseInt(SplitValue[YearIndex], 10);
 
            if (OK = ((Year > 1900) && (Year <= new Date().getFullYear()))) {
                if (OK = (Month <= 12 && Month > 0)) {
                    var LeapYear = (((Year % 4) == 0) && ((Year % 100) != 0) || ((Year % 400) == 0));
 
                    if (Month == 2) {
                        OK = LeapYear ? Day <= 29 : Day <= 28;
                    }
                    else {
                        if ((Month == 4) || (Month == 6) || (Month == 9) || (Month == 11)) {
                            OK = (Day > 0 && Day <= 30);
                        }
                        else {
                            OK = (Day > 0 && Day <= 31);
                        }
                    }
                }
            }
        }
        return OK;
    }
    catch (e) {
        return false;
    }
}

function cambiarTituloVisor(idFrame, texto){
	var idFrameModificado = "#" + idFrame + " div.window-titleBar-content";
	$(idFrameModificado).html(texto);
}


function recuperaTituloVisor(idFrame){
	var idFrameModificado = "#" + idFrame + " div.window-titleBar-content";
	return $(idFrameModificado).text();
}


function abreVentanaAdjuntarDocumentoAExpediente(){
	
	var extensionesPermitidas = ".pdf";


    if( extensionesPermitidasAudio.length > 0 ){
        extensionesPermitidas = extensionesPermitidas + ","+ extensionesPermitidasAudio;
    }

    if( extensionesPermitidasImagen.length > 0 ){
        extensionesPermitidas = extensionesPermitidas + ","+ extensionesPermitidasImagen;
    }
	
	if(typeof(idExpedienteop) != "undefined" && typeof(idExpedienteop) != "null" && idExpedienteop != ""){
		$.newWindow({id:"iframewindowAdjuntarDocumento", statusBar: true, posx:50,posy:50,width:450,height:200,title:"Adjuntar documento", type:"iframe"});
		$.updateWindowContent("iframewindowAdjuntarDocumento",'<iframe src="' + contextoPagina + '/adjuntarDocumentoAExpedienteGenerico.jsp?extensionesPermitidas=' + extensionesPermitidas + '&idExpediente='+idExpedienteop+'" width="450" height="200" />');
	}else{
            if(typeof(idNumeroExpediente) != "undefined" && typeof(idNumeroExpediente) != "null" && idNumeroExpediente != ""){
			$.newWindow({id:"iframewindowAdjuntarDocumento", statusBar: true, posx:50,posy:50,width:450,height:200,title:"Adjuntar documento", type:"iframe"});
			$.updateWindowContent("iframewindowAdjuntarDocumento",'<iframe src="' + contextoPagina + '/adjuntarDocumentoAExpedienteGenerico.jsp?extensionesPermitidas=' + extensionesPermitidas + '&idNumeroExpediente='+idNumeroExpediente+'" width="450" height="200" />');
		}else{
			alert("Imposible adjuntar!");
		}
	}   
}

function abreVentanaAdjuntarDocumentoAAudiencia(){
	
	var extensionesPermitidas = ".pdf," + extensionesPermitidasAudio + "," + extensionesPermitidasImagen;
	
	if(typeof(idAudiencia) != "undefined" && typeof(idAudiencia) != "null" && idAudiencia != ""){
		$.newWindow({id:"iframewindowAdjuntarDocumento", statusBar: true, posx:50,posy:50,width:450,height:200,title:"Adjuntar documento", type:"iframe"});
		$.updateWindowContent("iframewindowAdjuntarDocumento",'<iframe src="' + contextoPagina + '/adjuntarDocumentoAAudiencia.jsp?extensionesPermitidas=' + extensionesPermitidas + '&idAudiencia='+idAudiencia+'" width="450" height="200" />');
	}else{
		alert("Imposible adjuntar!");
	}   
}

function ajustarGridAlCentro(grid, params){
	var height = 0;
	var restarHeight = 60;
	grid.setGridWidth($("#mainContent").width() - 15, true);
	var caption = grid.jqGrid('getGridParam','caption');
	
	
	if (params === undefined){
		height = (($("#mainContent").height() - $("#ui-layout-south").height()) - restarHeight);
	} else {
		height = (($("#mainContent").height() - $("#ui-layout-south").height()) - restarHeight);	
	}
	try {
		if(caption.length > 0){
			height-=20;
		}
	}catch(e){};
	grid.setGridHeight(height, true);
	
}

function ocultaMuestraTabVisor(claseTab,bandera)
{
	if(parseInt(bandera)==0)//oculta
	{
		$("."+claseTab).hide();
	}
	else///muestra
	{
		$("."+claseTab).show();
	}
}

function ocultaBotonesGenerico() {
	$('input[type="submit"]').hide();
	$('input[type="button"]').hide();
}

function inhabilitaCamposGenerico(){
		$(":enabled").attr('disabled','disabled');
}

var DIGPESO = 3;
var DIGESTATURA = 1;
var MONTO = 6;

function validaDecimales(campo,e,num){
	if (e.charCode){
    	tecla = e.charCode;
    	op = true;
    }else{
    	tecla =  e.keyCode;
    	op = false;
    }
	
	if (tecla==8 ||tecla==9  || tecla==37 || tecla== 39 ) {
		if(op && tecla == 37){//esto es para el %
    		return false;
    	}
    	return true;
	}
	exp = "^[0-9]{0,"+num+"}(\\.[0-9]{1,2})?$";
	var regexp = new RegExp(exp);
	aEvaluar = campo.value+String.fromCharCode(e.charCode);
	
	if (!regexp.test(aEvaluar)) {
		if(tecla == 46 && regexp.test(aEvaluar+"1") )
			return true;
		return false;
    }
	return true;
}

function validaSoloLetras(campo){
	var regexp = new RegExp(/^[A-Za-z—Ò·ÈÌÛ˙¡…Õ”⁄‰ÎÔˆ¸ƒÀœ÷‹‡ËÏÚ˘¿»Ã“Ÿ\.\s]*$/);
    aEvaluar = campo.value;
    if (!regexp.test(aEvaluar)) {
    	customAlert("Caracteres invalidos en:<br>"+aEvaluar);
        campo.value = "";
    } 
}

function validaSoloNumeros(campo){
	var regexp = new RegExp(/^[0-9]*$/);
    aEvaluar = campo.value;
    if (!regexp.test(aEvaluar)) {
    	customAlert("Caracteres invalidos en:<br>"+aEvaluar);
        campo.value = "";
    } 
}


//Funcion que valida si los campos estan llenos al enviar y si es valido el rango de fechas
//en caso de ser correcto devuelve cadena vacia, de lo contrario el mensajde de error
function validaCamposFechaConMensaje(fechaini,fechafin) {
	var mensaje = "";

	if (fechaini == '' || fechafin == '') {
		mensaje = "Debe ingresar tanto la fecha de inicio como la de fin";
	} else {

		var fechaIniVal = fechaini;
		var fechaFinVal = fechafin;

		var inicio = fechaIniVal.split("/");
		var fin = fechaFinVal.split("/");

		if ((fin[2]) >= (inicio[2])) {
			if((fin[2]) == (inicio[2])){
				if((fin[1]) >= (inicio[1])){
					if((fin[1]) == (inicio[1])){
						if((fin[0]) < (inicio[0])){
							mensaje = "La Fecha de fin debe de ser mayor o igual a la fecha de inicio";
						}	
					}
				}else{
					mensaje = "La Fecha de fin debe de ser mayor a la fecha de inicio";
				}
			}
		}else{
			mensaje = "La Fecha de fin debe de ser mayor a la fecha de inicio";
		} 		
	}	
	return mensaje;
}



/**
* Deshabilitar los botones de guardado parcial y guardado definitivo en el visor de documentos
*/
function deshabilitarBotonesGuardado(){
	//Guardado Parcial
	try{
		$('#guardadoParcialNarrativa').unbind();
	}catch(e){};
	
	//Guardado Definitivo
	try{
		$('#imprimirNarraTiva').unbind();
	}catch(e){};
	
	//Enviar solicitud
	try{
		$('#btnEnviarSolicitud').unbind();
	}catch(e){};
	
}

/**
* Habilita los botones de guardado parcial y guardado definitivo en el visor de documentos
*/
function habilitarBotonesGuardado(){
	//Guardado Parcial
	try{
		$('#guardadoParcialNarrativa').click(guardadoParcial);
	}catch(e){};
	
	//Guardado Definitivo
	try{
		$('#imprimirNarraTiva').click(crearPdf);
	}catch(e){};
	
	//Enviar solicitud
	try{
		$('#btnEnviarSolicitud').click(crearPdf);
	}catch(e){};
	
	try{
		administraEventosDelEditorDeDocumentos();
	}catch(e){};
	
}

function isEmpty(variable){
	if(variable == undefined || variable == null 
	|| variable == "" || variable.length == 0
	|| variable == 'null'){
		return true;
	}
	return false;
}



function activarAvisoAlCerrarVentanaDesdePadre(idFrame,bandera){
	try{
		$.activarAvisoAlCerrarVentana(idFrame, bandera);
	}catch(e){
		console.info("Error al activarAvisoAlCerrarVentanaDesdePadre " + e);
	}
	
}

/**
 * Funcion que permite desactivar o activar el mensaje 
 * "øEst· seguro que desea salir? Si sale y no ha guardado sus cambios estos se perder·n"
 */
function activarAvisoAlCerrarVentanEnVisorDocumentos(idFrame, bandera){
		try{
			window.parent.activarAvisoAlCerrarVentanaDesdePadre(idFrame, bandera);
		}catch(e){};	
}

/**
 * Funcion generica que aplica a los visores que permien realizar guardados parciales, definitivos o
 * realizar un envio de solicitud. Por ejemplo: 
 * 
 * - generarDocumentoSinCaso.jsp
 * - elaborarSolicitud.jsp
 * 
 * Si el usuario intenta salir del editor sin previamente haber guardado los cambios, el sistema 
 * debera de mostrar el sisguiente mensaje:
 *    "øEst· seguro que desea salir? Si sale y no ha guardado sus cambios estos se perder·n"
 * 
 * Para poder controlar dicho mensaje, es necesario activar/desactivar la bandera asociada al editor, 
 * para ello, se definen eventos que realizaon dicha accion.
 *    
 */
function administraEventosDelEditorDeDocumentos(){
	
	try{
		//Validamos que la ventana del editor tenga un identificador
		if(idWindowPantallaActuaciones !== undefined){
			var idFrame ="iframewindowGenerarDocumento" + idWindowPantallaActuaciones;
			
			//Siempre que hagan un guardado parcial, un defenitivo o se envie una solicitud, se desactivara el mensaje
			try{
				$('#guardadoParcialNarrativa').click(function(){
					activarAvisoAlCerrarVentanEnVisorDocumentos(idFrame, false);
				});
			}catch(e){};
			
			//Siempre que el editor detecte un cambio en el editor, el sistema se activara el mensaje
			try{
				CKEDITOR.instances.editor1.on("change", function() {
		            try{
		            	if(!isEmpty(idFrame)){
							activarAvisoAlCerrarVentanEnVisorDocumentos(idFrame, true);
			            }	
		            }catch(e){
		            	console.log(e);
		            }
		        });
			}catch(e){
				//console.log("Error al configurar el evento change en el editor cfkeditor by gamasoft " + e);
			}
			
			
		}else{
			console.error("La variable idWindowPantallaActuaciones NO existe");
		}
		
	}catch(e){};
}





/*
 * Funcion que permite solo capturar
 * numeros del 0-9 y letas de la a-z y mayussculas de la A-Z
 * Se debe colocar dentro de la accion onkeypressdown del textbox
 */
function teclasAlfanumericas(e) {
    var unicode = e.charCode ? e.charCode : e.keyCode;

    // if the key is backspace, tab, or numeric
    if (unicode == 8 || unicode == 9  || (unicode >= 48 && unicode <= 57) || (unicode >= 65 && unicode <= 90) || (unicode >= 97 && unicode <= 122)) {
        // we allow the key press
        return true;
    }
    else {
        // otherwise we don't
        return false;
    }
}

/*
 * Funcion que evalua la expresion para solo poder capturar letras y numeros
 */
function soloLestrasYNumeros (e) { 
	var regexp = new RegExp(/[A-Za-z0-9]/);
    aEvaluar = campo.value;
    if (!regexp.test(aEvaluar)) {
    	customAlert("Caracteres invalidos en:<br>"+aEvaluar);
        campo.value = "";
    }
}

/**
 * Funci&oacute;n generica para consultar 
 * valor de un parametro de la BD
 * 
 * @param idParametro identificador del parametro a cosnultar.
 * @returns
 * 		null, si no se encuentra el valor del parametro consultado.
 * 		valor, del parametro consultado.
 */
function consultarParametroGenerico( idParametro ){
	var parametroConfirm = null;
	
	$.ajax({
		type: 'POST',
		url: contextoPagina+'/consultarParametro.do',
		data: 'idParametro='+ idParametro, 
		async: false,
		dataType: 'xml',
		success: function(xml){					
			parametroConfirm = $(xml).find('body').find('respuesta').text();
		}
	});

	return parametroConfirm;
}
/**
 * Funcion que permite capturar letras y numeros con acentos
 * 
 * */
function soloLetrasConAcentosYNumeros(e) { 
	if (e.charCode){
    	tecla = e.charCode;
    	op = true;
    }else{
    	tecla =  e.keyCode;
    	op = false;
    }
    // 'Backspace' = 8, 'Tab' = 9, '.' = 46, FlechaCursora <- = 37, FlechaCursora -> = 39
    if (tecla==8 || tecla==9 || tecla==46 || tecla==37 || tecla== 39 ) {
    	if(op && tecla == 37){//esto es para el %
    		return false;
    	}
    	return true;	
    }
    patron =/[A-Za-z—Ò·ÈÌÛ˙¡…Õ”⁄‰ÎÔˆ¸ƒÀœ÷‹‡ËÏÚ˘¿»Ã“Ÿ0-9,()\s]/;

    te = String.fromCharCode(tecla);
    return patron.test(te);
}



/**
 * Funcion que permite validar letras y numeros con acentos
 * 
 * */
function validaSoloLetrasConAcentosYNumeros(campo){
	var regexp = new RegExp(/^[A-Za-z—Ò·ÈÌÛ˙¡…Õ”⁄‰ÎÔˆ¸ƒÀœ÷‹‡ËÏÚ˘¿»Ã“Ÿ0-9,()\.\s]*$/);
    aEvaluar = campo.value;
    if (!regexp.test(aEvaluar)) {
    	customAlert("Caracteres invalidos en:<br>"+aEvaluar);
        campo.value = "";
    } 
}

function validarCampo(filtro, valor)
{
        // utilizamos test para comprobar si el parametro valor cumple la regla
        if(!filtro.test(valor))
            return true;
        else
            return false;
}




////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////						FUNCIONES PARA LA MANIPULACION DE GRID						////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * GRID MULTISELECT
 * Constante que sirve de Referencia para guardar los identificadores 
 * de todas las paginas de un grid multiselect
 */
var PAGINA_TOTAL = "PAGINA_TOTAL";

/**
 * Constante para identificar si un elemento se encuentra en un arreglo,
 * utilizado con la funcion indexOf
 */
var NO_ENCONTRADO = "-1";

/**
 * Constante que identifica un arreglo vacio
 */
var ARREGLO_VACIO = "";


/**
 * GRID MULTISELECT
 * Funcion para seleccionar los identificadores previamente guardados en 
 * en el pagina PAGINA_TOTAL dentro del grid multiselect.
 * 
 * Se debe de invocar cada vez que se carga el grid, para consultar cuales son los identificadores 
 * previamente guardados, para ser colocados como seleccionados.
 * 
 * Similar a seleccionarItems en comun.js
 */
function  seleccionarIdentificadores(grid){
	//Obtiene la Pagina actual
	//var currentPage = grid.getGridParam('page').toString();
	//Obtiene los datos d euna pagina, previamente guardado con ese identificador		
	//var retrieveSelectedRows = grid.data(currentPage);
	
	//Obtiene los datos de la pagina TOTAL
	var idGuardados = grid.data(PAGINA_TOTAL);

	if (idGuardados != undefined && idGuardados !=  ARREGLO_VACIO) {
		 //Recorre todo el arreglo, de acuerdo al indice y el valor indicado
         $.each(idGuardados, function (indice, valor) {
        	 //Checa aquellos valores previamente guardados
        	 //no importa que no esten visibles en la pagina actual
        	 grid.setSelection(valor, false);
		});
	}
}


/**
 * GRID MULTISELECT
 * Funcion que recorre el arreglo con los identificadores para, 
 * de acuerdo a esSeleccionado:
 *  -true: agregar.
 *  -false: quitar.
 *  
 *  Debe de ser utilizado cuando se tiene multiselect habilitado y en la definicion de "onSelectAll"
 */
function actualizarIdentificadores(idIdentificadores, esSeleccionado, grid){
	$.each(idIdentificadores, function (index, value) {
    	 //Checa aquellos valores previamente guardados, no importa que no esten visibles en la pagina actual
    	actualizarIdentificador(value, esSeleccionado, grid);
	});
}


/**
 * GRID MULTISELECT
 * Funcion que permite actualizar los identificadores en la pagina TOTAL,
 * es decir, los que son seleccionados.
 * 
 * De acuerdo a esSeleccionado:
 *  -true: se ha seleccionado, se debe de agregar, sin considerar duplicados
 *   -false: se ha deseleccionado, se debe de quitar
 *   
 * Una vez finalizada la actualizacion se vuelve actualizar los identificadores
 * en la pagina TOTAL.
 *   
 *   
 * Debe de ser utilizado cuando se tiene multiselect habilitado y en la definicion de "onSelectRow"
 */
function actualizarIdentificador(idIdentificador, esSeleccionado, grid){

	//Recupera los valores guardados, mediante la referencia la pagina TOTAL
	var idGuardados = grid.data(PAGINA_TOTAL);
	//alert("actualizarIdentificador-   idGuardados:"+idGuardados);
	
	//Agregar el identificador
	if(esSeleccionado!=undefined && esSeleccionado){
		
		//Verifica si el arreglo esta creado
		if(idGuardados ==undefined || idGuardados == ARREGLO_VACIO ){
			idGuardados = new Array();
		}
		
		//Evitar duplicados
		var indice = idGuardados.indexOf(idIdentificador);
		if(indice == NO_ENCONTRADO){
			idGuardados.push(idIdentificador);
		}
	}
	else{ 
		//Buscar el identificador en los guardados
		var indice = idGuardados.indexOf(idIdentificador);
		
		//Si existe, se debe de quitar
		if(indice != NO_ENCONTRADO){
			//Se quita el elemento
			//A partir del indice definido en el primer parametro hasta 
			//el numero indicado en el segundo parametro (1) 
			idGuardados.splice(indice,1);
		}
	}
				
	//Actualizar los datos
	grid.data(PAGINA_TOTAL,idGuardados);
}

/**
 * GRID MULTISELECT
 * Funcion que limpia los datos seleccionados del grid.
 * Puede ser utilizada para cuando:
 *   1)Limpia el grid:  Ver la funcion limpiarGridGenerico
 *   2) Recarga del grid: antes de la invocacion de la lectura de datos del grid.
 */
function limpiarIdentificadores(grid){
	grid.data(PAGINA_TOTAL,"");
}

/**
 * GRID MULTISELECT
 * Funcion para obtener los ids seleccionados y guardados del grid multiselect
 * independiente de la pagina en donde sea guardado.
 * 
 * Puede se utilizado para saber cuales son los id de los elementos selccionados(multiselect) del grid.
 */
function obtenerIdentificadoresSeleccionados(grid){
	
    var retrieveSelectedRows = grid.data(PAGINA_TOTAL);
    return retrieveSelectedRows; 

    //Codigo revisado para evitar esfuerzo extra.
    //var arr_values = new Array();
    //if (retrieveSelectedRows) {
    //    $.each(retrieveSelectedRows, function (index, value) {
    //    	arr_values.push(value);
    //        /* $.each(value, function (index, sub_value) {
    //            if(typeof(sub_value)=='string'){
    //            	arr_values.push(sub_value);
    //            }
    //        });
    //    });
    //}
    // return arr_values; */
}			



/**
 * GRID
 * Funcion Generica para limpiar un grid.
 * 
 * Es posible pasar una funcion de referencia para hacer otros mecanismos de limpieza, particular
 * del flujo.
 * 
 * Parametros:
 * 		grid : nombre del grid.
 * 		funcionLimpieza: Funcion de limpieza particular del flujo que invoca la limpieza del grid.
 * 		bloqueaGrid: bandera que determina, si se bloquea o no el grid, va ligado con el parametro de idDivContendor
 * 		idDivContendor: nombre del div contenedor del grid.
*/
function limpiarGridGenerico(grid, funcionLimpieza, bloqueaGrid, idDivContendor){
	
	if(grid==undefined || grid == null){
		return;
	}
	
	jQuery(grid).jqGrid('setGridParam', {
		url: CONTEXT_ROOT + '/limpiarGrid.do',
		datatype: "xml",
		loadComplete: function (){
		},
		onSelectRow: function(){
		},
		onSelectAll: function(){
		},
		ondblClickRow: function() {
		},
		gridComplete: function () {
		},
		onPaging: function () {
		},
		onSortCol: function(){
		}
	});
	
	//Recargar el grid
	grid.trigger("reloadGrid");
	
	if(funcionLimpieza !=null && 
			funcionLimpieza!=undefined && 
			typeof funcionLimpieza == 'function' ){
		funcionLimpieza();
	}

	//Limpia los identificadores del grid, previamente guardados
	limpiarIdentificadores(grid);
	
	habilitarGrid(bloqueaGrid, idDivContendor);
}

/**
 * GRID
 * Funcion que se encarga de habiliar/deshabilitar un grid, dependiendo del valor de la 
 * bandera. 
 * 
 * Se requiere que el grid sea definido dentro de un contenedor o div, donde se requiere de su
 * identificador.
*/
function habilitarGrid(bandera, idDivContendor){
	if(bandera != undefined && idDivContendor != undefined){
		
		//Habilitar
		if(bandera){
			//Se habilita el div contenedor del grid
			$("#"+idDivContendor).removeClass('ui-state-disabled');
			
			//Habilitar los componentes internos
			$("#"+idDivContendor+" :disabled").attr('disabled','');
		
		}else{
			//Se desabilita el div
			$("#"+idDivContendor).addClass('ui-state-disabled');
			//Deshabilita los elementos dentro de la definicion del div que contiene el grid.
			$("#"+idDivContendor+" :enabled").attr('disabled', 'disabled');
			
		}
	}
	
	//Codigo revisado para evitar esfuerzo extra.
	//grid.attr("disabled",true);
	//Se desabilita el div
	//$("#"+nombreDiv).addClass('ui-state-disabled');
	//Deshabilita los elementos dentro de la definicion del div que contiene el grid.
	//$("#"+nombreDiv+" :enabled").attr('disabled', 'disabled');
	//No utilizado
	//grid.unbind('click');
	//Bloquea la pantalla con los valores de default
	//grid.block();
	//Se sobreescriben atributos.
	//grid.closest(".ui-jqgrid").block({
	//message: null,
	//css: null
	//});
}

/**
 * GRID
 * Funcion para obtener el id de la fila seleccionada
 */			
function obtenerIdFilaSeleccionada(grid){
	var id = grid.jqGrid('getGridParam','selrow');
	if(id) {
		return id;
	} else {
		return false;
	}
}


//TODO Estas funciones deben de revisarse, 
//para saber si se hace la migracion a la nueva funcionalidad.

/*
*Funcion para seleccionar en el gird multiselect
*/
function  seleccionarItems(grid){
		
	var currentPage = grid.getGridParam('page').toString();

	//retrieve any previously stored rows for this page and re-select them
	var retrieveSelectedRows = grid.data(currentPage);
	
	if (retrieveSelectedRows) {
         $.each(retrieveSelectedRows, function (index, value) {
             grid.setSelection(value, false);
		});
	}
}
		
		
/*
*Funcion para guardar en el gird multiselect
*/
function guardarItemsSeleccionados(grid) {
	
	var pagerId = grid.getGridParam('pager').toString();
	
	if(pagerId.indexOf("#") != -1){
		pagerId = pagerId.substring(1, pagerId.length);
	}
	// ger paper id like "pager" 
	var pageValue = $('input.ui-pg-input', "#pg_" + $.jgrid.jqID(pagerId)).val(); 
	var saveSelectedRows = grid.getGridParam('selarrrow'); 
	//Store any selected rows 
	grid.data(pageValue.toString(), saveSelectedRows); 
}


/*
*Funcion para borrar la seleccion en el gird multiselect
*/		
function eliminarItemsSeleccionados(grid){
	
	guardarItemsSeleccionados(grid);
	grid.jqGrid('resetSelection');
    var retrieveSelectedRows = grid.data();       
    if (retrieveSelectedRows) {
        $.each(retrieveSelectedRows, function (index, value) {
            $.each(value, function (sub_index, sub_value) {
                if(typeof(sub_value)=='string'){
                	grid.data(index, "");
                }
            });
        });
    }
    
}

/*
*Funcion para obtener los ids seleccionados del grid multiselect
*/
function obtenerSeleccionados(grid){
	
	guardarItemsSeleccionados(grid);

    var retrieveSelectedRows = grid.data();       
    var arr_values = new Array();

    if (retrieveSelectedRows) {
        $.each(retrieveSelectedRows, function (index, value) {
            $.each(value, function (index, sub_value) {
                if(typeof(sub_value)=='string')
                arr_values.push(sub_value);
            });
        });
    }

    return arr_values;
}

////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////		TERMINAN LAS FUNCIONES PARA GRID	////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////

function abreFrameAudio(id,titulo,tipoId){
//	var vlc = navigator.plugins['VLC Web Plugin'];
//	if (vlc === undefined) {
//		customAlert("necesita instalar el plugin de VLC para reproducir el archivo de audio");
//	}else{
//	alert( 'application/x-vlc-plugin' in navigator.mimeTypes);
	
	titulo = titulo.substring(titulo.indexOf('>') +1,titulo.length) //Se elimina <div class="celdaGrid">
	titulo = titulo.substring(0,titulo.indexOf('<')) //Se elimina </div> del titulo
    
	$.newWindow({
        id:"iframewindowAudio"+id,
        statusBar: true,
        width:500,
        height:40,
        title:titulo,
        type:"iframe"
    });
    $.updateWindowContent("iframewindowAudio"+id,'<iframe src="' + CONTEXT_ROOT + '/ConsultarContenidoArchivoDigital.do?'+tipoId+'='+id+'&mostrarTipoArchivo=true "width="'+ (500) +'" height="' + (40) + '" />');
}

function inicializaMensajes(){
      $("#msgErrorBox").addClass("ui-helper-hidden");
      $("#msgInfoBox").addClass("ui-helper-hidden");
}
function muestraMensajeInfo(mensaje){
     $("#msgInfoBox").removeClass("ui-helper-hidden");
     $("#msgInfo").text(mensaje);
    
}
function muestraMensajeError(mensaje){
    $("#msgErrorBox").removeClass("ui-helper-hidden");
    $("#msgError").text(mensaje);
}

function missingField(fieldname, valueWrong, tabname, message){

    //$("#msgErrorBox").addClass("ui-helper-hidden");
    //$("#msgError").text("");
    inicializaMensajes();
    $(tabname).removeClass("ui-state-error ui-corner-all");

    if($(fieldname).val() === valueWrong){
             $(fieldname).focus();  
             $(fieldname).addClass("ui-state-error ui-corner-all");
             //alert(msgError);
             //$("#msgErrorBox").removeClass("ui-helper-hidden");
             //$("#msgError").text(message);
             muestraMensajeError(message);
             $(tabname).addClass("ui-state-error ui-corner-all");
             return true;
    }else{
        $(fieldname).removeClass("ui-state-error ui-corner-all");
             //alert(msgError);
    }
    return false;
}