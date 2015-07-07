/**
 * NOTA: Para usar dicha clase es necesario:
 *  - Configurar la variable contextoPagina en el jsp que lo utilizara * 
 *  - Definir el div de busquedaFecha
 *  - Definir el div divGridAudiencias
 * Asi cmo se usa en indexDirectivos.jsp
 */

/*
*Funcion que invoca la consulta de audiencias del dia,
*como parametro recibe, si la consulta es por semana = true
*en caso contrario debe recibir=false
*/    
function muestraGridAudiencias(porSemana){
	 gridAudiencias(porSemana);
	 ocultaMuestraGrids('gridAudiencias');
 }

//Variable para recargar el grid de audiencias
var gridAud=0;

/*
*Funcion que carga el grid de audiencias, que consulta las audiencias agendadas en PJ
*el coordinador debe ver todas las audiencias por lo que se quita el filtro de funcionario
*en la clase action
*/
function gridAudiencias(porSemana){

	var fechaInicio=$('#fechaInicio').val();
	var fechaFin=$('#fechaFin').val();
	var desarrolloJAVS="TRUE";

	if(gridAud==0){
		jQuery("#gridAudiencias").jqGrid({ 
			url: contextoPagina + '/consultarAudienciasDefensor.do?inicio='+fechaInicio+'&desarrolloJAVS='+desarrolloJAVS+'&fin='+fechaFin+'&porSemana='+porSemana+'', 
			datatype: "xml", 
			colNames:['Audiencia','Caso','Car&aacute;cter','Tipo de Audiencia','Fecha de Audiencia','Sala'], 
			colModel:[{name:'audiencia',	index:'audiencia',	width:70,  align:"center",	sortable:false},
					  {name:'caso',	 		index:'caso', 		width:180, align:"center",	sortable:false},
			          {name:'caracter',		index:'caracter', 	width:150, align:"center",	sortable:false},
			          {name:'tipo',	 		index:'tipo', 		width:200, align:"center",	sortable:false},
			          {name:'fechaHora',	index:'fechaHora',	width:200, align:"center",	sortable:false},
			          {name:'sala' ,		index:'sala', 		width:150, align:"center",	sortable:false}
					],
			
			pager: jQuery('#pagerGridAudiencias'),
			rowNum:10,
			rowList:[10,20,30,40,50,100,200],
			autowidth: true,
			sortname: 'numeroExpediente',
			viewrecords: true,
			sortorder: "desc",
			loadComplete: function(){
				$('#fechaInicio').val("");
				$('#fechaFin').val("");
			}			
		}).navGrid('#pagerGridAudiencias',{edit:false,add:false,del:false});
		gridAud=1;
	}else{		
		jQuery("#gridAudiencias").jqGrid('setGridParam', {url: contextoPagina + '/consultarAudienciasDefensor.do?inicio='+fechaInicio+'&desarrolloJAVS='+desarrolloJAVS+'&fin='+fechaFin+'&porSemana='+porSemana+'',
			datatype: "xml",
			page:1});
		$("#gridAudiencias").trigger("reloadGrid");
		$("#gview_gridAudiencias .ui-jqgrid-bdiv").css('height', '410px');
	}
	ocultaMuestraGrids('gridAudiencias');
	$("#gview_gridAudiencias .ui-jqgrid-bdiv").css('height', '410px');
}


/*
*Funcion que carga la ventana modal para seleccionar el intervalo de fechas entre las cuales
*se desea consultar las audiencias programadas
*/
function modalFecha(){

	$('#fechaInicio').val('');
	$('#fechaFin').val('');
	var dates =	$("#fechaInicio, #fechaFin").datepicker(
		{
			dateFormat: 'dd/mm/yy',
			yearRange: '1900:2100',
			changeMonth: true,
			changeYear: true,
			numberOfMonths: 1,
			onSelect: function( selectedDate ) {
				var option = this.id == "fechaInicio" ? "minDate" : "maxDate",
				instance = $( this ).data( "datepicker" ),
				date = $.datepicker.parseDate(
				instance.settings.dateFormat ||
				$.datepicker._defaults.dateFormat,
				selectedDate, instance.settings );
				dates.not( this ).datepicker( "option", option, date );
			},
			showOn: "button",
			buttonImage:  contextoPagina + "/resources/images/date.png",
			buttonImageOnly: true			
		}
	);
	
	//abre la ventana de detalle de la persona
	$("#busquedaFecha").dialog("open");
	$("#busquedaFecha").dialog({ autoOpen: true, 
  		modal: true, 
  		title: 'Buscar por Fecha', 
  		dialogClass: 'alert',
  		width: 380,
  		height: 260,
  		maxWidth: 1000,
  		buttons:{"Aceptar":function() {
  				
  					validaCamposFecha($("#fechaInicio").val(), $("#fechaFin").val());
  					
  					if(validaFecha==true){
		  				gridAudiencias(false);
		  				ocultaMuestraGrids('gridAudiencias');
	  					$(this).dialog("close");
			  		}
  				},
  				"Cancelar":function() {
	  				$(this).dialog("close");
  				}
  		}
  	});
			
}