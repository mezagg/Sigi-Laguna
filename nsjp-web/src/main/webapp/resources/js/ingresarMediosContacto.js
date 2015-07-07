/**
* Nombre del Programa : ingresarMediosContacto.js
* Autor               : Arturo Le�n
* Compania            : Ultrasist
* Proyecto            : NSJP                    Fecha: 28/03/2011
* Marca de cambio     : N/A
* Descripcion General : Archivo javaScript con las funciones para la vista de IngresarMediosDeContacto
* Programa Dependiente: N/A
* Programa Subsecuente: N/A
* Cond. de ejecucion  : N/A
* Dias de ejecucion   : N/A                             Horario: N/A
*                              MODIFICACIONES
*------------------------------------------------------------------------------
* Autor               :N/A
* Compania            :N/A
* Proyecto            :N/A                                   Fecha: N/A
* Modificacion        :N/A
*------------------------------------------------------------------------------
*/
/*$('#cmbTipoTel�fono option:selected').val()*/

/**
 * Objeto de tipo Telefono
 */
function Telefono(idTipoTelefono, nombreTipoTelefono,cvePais,lada,telefono){
	this.idTipoTelefono = idTipoTelefono;
	this.nombreTipoTelefono = nombreTipoTelefono;
	this.cvePais = cvePais;
	this.lada = lada;
	this.telefono = telefono;
}

/**
 * Objeto de tipo Correo electronico
 */
function Correo(idCorreo,dirElectronica){
	this.idCorreo = idCorreo;
	this.dirElectronica = dirElectronica;
}

/**
 * Variables globales para controlar los datos del pop up de telefonos
 */
var lstTelefonos = new Array();
var idTelefonos = 0;
/**
 * Variables globales para controlar los datos del pop up de correos
 */
var lstCorreos = new Array();
var idCorreos = 0;

var listaTele = new Array();
var borraInfo=0;


/**
 * Funci�n que crea inicializa los controles asignados en la pantalla de Ingresar medio de contacto
 * @param contexto Contexto de la aplicaci�n
 */
function inicializaMediosContacto(contexto){
	$('#btnAgregarTelefono').click(function () {
		limpiarPopUpTelefono();
		$("#divTelefono").dialog("open");
	});
	
	$('#btnAgregarCorreo').click(function () {
		limpiarPopUpCorreo();
		$("#divCorreo").dialog("open");
	});
	
	$('#btnEliminarTelefono').click(function () {
		if(lstTelefonos.length>0){
			$('#tblTelefonos').empty();
			$('#tblTelefonos').append('<tr><td>Tipo de tel�fono</td><td>C�digo de Pa�s</td><td>Lada</td><td>N�mero telef�nico</td></tr>');
			var lstTelefonosTemp = new Array();
			for(var i=0;i<lstTelefonos.length -1;i++){
				var tel = lstTelefonos[i];
				lstTelefonosTemp[i] = tel;
				$('#tblTelefonos').append('<tr><td>'+ tel.nombreTipoTelefono +'</td><td>'+tel.cvePais+'</td><td>'+tel.lada+'</td><td>'+tel.telefono+'</td></tr>');
			}
			lstTelefonos = lstTelefonosTemp;
			idTelefono--;
		}else{
			alert('No hay tel�fonos a eliminar');
		}
	});
	
	$('#btnEliminarCorreo').click(function () {
		if(lstCorreos.length>0){
			$('#tblCorreos').empty();
			$('#tblCorreos').append('<tr><td>Correo electr�nico</td></tr>');
			var lstCorreosTemp = new Array();
			for(var i=0;i<lstCorreos.length -1;i++){
				lstCorreosTemp[i] = lstCorreos[i];
				$('#tblCorreos').append('<tr><td>'+ lstCorreos[i] +'</td></tr>');
			}
			lstCorreos = lstCorreosTemp;
			idCorreo--;
		}else{
			alert('No hay correos a eliminar');
		}
	});
	
	$("#divTelefono").dialog({ autoOpen: false, 
		modal: true, 
		title: 'Agregar tel�fono', 
		dialogClass: 'alert', 
		width: 308.733 ,
		height: 215.733,
		maxWidth: 600 ,
		buttons:{"Aceptar":function() {
			if(parseInt(idTelefono) <5){
				var telefono = new Telefono($('#cmbTipoTel�fono option:selected').val(),$('#cmbTipoTel�fono option:selected').text(),$('#txtCvePais').val(),$('#txtLada').val(),$('#txtTelefono').val());
				lstTelefonos[idTelefono] = telefono;
				idTelefono++;
				$('#tblTelefonos').empty();
				$('#tblTelefonos').append('<tr><td>Tipo de tel�fono</td>&nbsp;<td>C�digo de Pa�s</td>&nbsp;<td>Lada</td>&nbsp;<td>N�mero telef�nico</td></tr>');
				for(var i=0;i<lstTelefonos.length;i++){
					var tel = lstTelefonos[i];
					$('#tblTelefonos').append('<tr><td>'+ tel.nombreTipoTelefono +'</td>&nbsp;<td>'+tel.cvePais+'</td>&nbsp;<td>'+tel.lada+'</td>&nbsp;<td>'+tel.telefono+'</td></tr>');
				}
			}else{
				alert('ya se capturaron 5 telefonos');
			}
			$(this).dialog("close");
			},
			"Cancelar":function() {
				$(this).dialog("close");
			}
		}
	});
	
	$("#divCorreo").dialog({ autoOpen: false, 
		modal: true, 
		title: 'Agregar correo', 
		dialogClass: 'alert', 
		width: 500 ,
		maxWidth: 600,
		buttons:{"Aceptar":function() {
			if(parseInt(idCorreo) <5){
				lstCorreos[idCorreo] = $('#txtCorreo').val();
				idCorreo++;
				$('#tblCorreos').empty();
				$('#tblCorreos').append('<tr><td>Correo electr�nico</td>');
				for(var i=0;i<lstCorreos.length;i++){
					$('#tblCorreos').append('<tr><td>'+ lstCorreos[i] +'</td></tr>');
				}
			}else{
				alert('ya se capturaron 5 correos');
			}
			$(this).dialog("close");
			},
			"Cancelar":function() {
				$(this).dialog("close");
			}
		}
	});
	cargarCmbTiposTelefonos();
	
}

/**
* Funci�n que carga el combo de calidad
*/
function cargarCmbTiposTelefonos(contexto) {
    $.ajax({
    	  type: 'POST',
    	  url: contextPath+'/CargarCatalogoTiposTelefono.do',
    	  data: '',
    	  dataType: 'xml',
    	  async: false,
    	  success: function(xml){
    	      $(xml).find('catalogoDTO').each(function(){
			      $('#cmbTipoTel�fono').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
			      $('#cmbTipoTel�fono1').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
			  });
    	  }
    });
}






/**
 * Funci�n encargada de construir un String con los datos de los arreglos de telefonos y correos
 */
function obtenerMediosanterior(){
	var parametros = '&medioContactoTelefono=';
	
	for(var i=0;i<lstTelefonos.length;i++){
		var tel = lstTelefonos[i];
		parametros +=tel.idTipoTelefono + ',';
		parametros +=tel.cvePais + ',';
		parametros +=tel.lada + ',';
		parametros +=tel.telefono + '|';
	}
	//alert("Medios de contacto 1:" + parametros);
	parametros += '&medioContactoCorreo=';
	for(var i=0;i<lstCorreos.length;i++){
		parametros += lstCorreos[i] + ',' ;
	}
	
	//alert("Medios de contacto:" + parametros);
	return parametros;
}


/**
 * Funci�n encargada de construir un String con los datos de los arreglos de telefonos y correos
 */
function obtenerMediosanteriord(){
	var parametros = '&medioContactoTelefono=';
	lstTelefonos = jQuery('#list5').getDataIDs();
	for(var i=0;i<lstTelefonos.length;i++){
		var ret2 = lstTelefonos[i];   
		var tel = jQuery("#list5").jqGrid('getRowData',tel);
		parametros +=tel.tipo + ',';
		parametros +=tel.pais + ',';
		parametros +=tel.lada + ',';
		parametros +=tel.Telefono + '|';
		//alert(parametros);
	}
	//alert("Medios de contacto 1:" + parametros);
	parametros += '&medioContactoCorreo=';
	lstCorreos = jQuery('#correos').getDataIDs();
	for(var i=0;i<lstCorreos.length;i++){
		var ret2 = lstCorreos[i];   
		var cor = jQuery("#correos").jqGrid('getRowData',ret2);
		parametros += cor.correo + ',' ;
		//alert(parametros);
	}
	
	//alert("Medios de contacto:" + parametros);
	return parametros;
}


/**
 * Funci�n encargada de construir un String con los datos de los arreglos de telefonos y correos parametrizada
 */
function obtenerMediosParametrizada(postfijo){
	var parametros = '&medioContactoTelefono'+postfijo+'=';
	
	for(var i=0;i<lstTelefonos.length;i++){
		var tel = lstTelefonos[i];
		parametros +=tel.idTipoTelefono + ',';
		parametros +=tel.cvePais + ',';
		parametros +=tel.lada + ',';
		parametros +=tel.telefono + '|';
	}
	//alert("Medios de contacto 1:" + parametros);
	parametros += '&medioContactoCorreo'+postfijo+'=';
	for(var i=0;i<lstCorreos.length;i++){
		parametros += lstCorreos[i] + ',' ;
	}
	
	//alert("Medios de contacto:" + parametros);
	return parametros;
}

/**
 * Funci�n que deshabilita los botones de agregar y remover medios de contacto
 */
function deshabilitaDatosContacto(){
	$('#btnAgregarTelefono').attr("disabled","disabled");
	$('#btnEliminarTelefono').attr("disabled","disabled");
	$('#btnAgregarCorreo').attr("disabled","disabled");
	$('#btnEliminarCorreo').attr("disabled","disabled");
}

/**
 * Funci�n que habilita los botones de agregar y remover medios de contacto
 */
function habilitaDatosContacto(){
	$('#btnAgregarTelefono').attr("disabled","");
	$('#btnEliminarTelefono').attr("disabled","");
	$('#btnAgregarCorreo').attr("disabled","");
	$('#btnEliminarCorreo').attr("disabled","");

}

