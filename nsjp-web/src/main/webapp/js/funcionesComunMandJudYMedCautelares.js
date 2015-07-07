	var gridMedCau=0; 
	var idsTiposMandamientos='';
	var banderaPropios=false;
	//variables para setear las fechas y horas maximas
	var fechaServidor="";
	var fechaMax="";
	
	// Variable para controlar el id de las ventanas de medidas cautelares
	var idWindowVisorMedidasCautelares=1;
	
	// Variable para controlar el id de las ventanas de mandamientos judiciales
	var idWindowVisorMandamientosJud=1;	
	

	function consultaGeneralMedidaCautelar(selectorMedida, estatusDeFiltrado, numeroCausa){
		var fechaInicio="";
		var fechaFin="";
		var numeroExpediente = "";

		// Este valor se utiliza desde base de datos en la invocación del elementoMenu, en la columna de cComando
		// cuando selectorMedida==1, es la consulta general de medidas cautelares.
		// Se manipula que cuando selectorMedida=2 -> Búsqueda por fechas, selectorMedida=3 -> Búsqueda por número de expediente,
		// de necesitar más filtros, es necesario modificar las condicionales sobre selectorMedida.
		// consulta general -> fechaInicio = "", fechaFin = "", numeroExpediente = ""
		
		if(selectorMedida == 2){
			fechaInicio= $('#fechaInicio').val();
			fechaFin=    $('#fechaFin').val();
		}
		else if(selectorMedida == 3){
			numeroExpediente = $("#numeroExpediente").val();
		}
		else if(selectorMedida == 4){
			numeroExpediente = numeroCausa;
		}
		if(typeof estatusDeFiltrado == "undefined"){
			estatusDeFiltrado = 0;
		}
		if(gridMedCau==0){
    		jQuery("#gridMedidasCautelares").jqGrid({ 
    			url: contextoPagina + '/consultaMedidasCautelaresGenerico.do?inicio='+fechaInicio+'&fin='+fechaFin+'&numeroExpediente='+numeroExpediente+'&estatusMedida='+estatusDeFiltrado+'', 	   		
    			datatype: "xml", 
    			colNames:['N&uacute;mero','Nombre','Medida Cautelar','Fecha de creación', 'Fecha de inicio', 'Fecha de fin','Periodicidad','Descripci&oacute;n', 'Estado'], 
				colModel:[ 	{name:'numeroCausa',index:'numeroCausa', width:150 , sortable:false,align:'center'},
				           	{name:'nombre',index:'nombre', width:200, sortable:false}, 
							{name:'medidaCautelar',index:'4', width:200, sortable:true},
							{name:'fechaCreacion',index:'6', width:110, sortable:true,align:'center'},
							{name:'fechaInicio',index:'1', width:100, sortable:true,align:'center'}, 
							{name:'fechaFin',index:'2', width:100, sortable:true,align:'center'},
							{name:'periodicidad',index:'3', width:150 , sortable:true},
							{name:'descripcion',index:'descripcion', width:150 , sortable:false, hidden:true},
							{name:'estadoMedida',index:'5', width:150 , sortable:true,align:'center'}
						],		
				pager: jQuery('#pagerGridMedidasCautelares'),
				rowNum:30,
				rowList:[10,20,30],
				autowidth: true,
				autoheight:true,
				height:210,
				sortname: '6',
				viewrecords: true,
				sortorder: "desc",
				ondblClickRow: function(rowid) {
					//Solo se habilita la opcion de mostrar el visor de medidas cautelares si y solo si se trata de una consulta desde la bandeja principal del usuario
					if(selectorMedida != 4){
						numeroCausa= jQuery("#gridMedidasCautelares").jqGrid('getRowData',rowid).numeroCausa;
						mostrarVentanaMedidasCautelares(rowid,numeroCausa);
					}
				}
			}).navGrid('#pagerGridMedidasCautelares',{edit:false,add:false,del:false});
    		
    		gridMedCau=1;
		}
		else{			
			jQuery("#gridMedidasCautelares").jqGrid('setGridParam', {url: contextoPagina + '/consultaMedidasCautelaresGenerico.do?inicio='+fechaInicio+'&fin='+fechaFin+'&numeroExpediente='+numeroExpediente+'&estatusMedida='+estatusDeFiltrado+'',
					datatype: "xml" });
			$("#gridMedidasCautelares").trigger("reloadGrid");
		}
		
		if(typeof numeroCausa == "undefined")
			ocultaMuestraGrids('gridMedidasCautelares');	
	}
	
	
	//Permite controlar la carga/recarga del grid
	var gridMandJud = true;
	
	/**
	 * Consulta general de mandamientos judiciales, tanto para los usuarios de PG como los de PJ
	 * Este valor se utiliza desde base de datos en la invocación del elementoMenu, en la columna de cComando
	 * 
	 * cuando selectorMandamiento =1, Es la consulta general de mandamientos judiciales.
	 * cuando selectorMandamiento =2, Busqueda por fechas,
	 * cuando selectorMandamiento =3, Busqueda por numero de expediente (en esta caso solo consulta los estatus 
	 * 									NO_ATENDIDO, EN_PROCESO Y ATENDIDO, debido a especificacion de negocio
	 * 									La regla es utilizada en el mandamientoDAO)
	 *  
	 * En caso de necesitar mas filtros, es necesario modificar las condicionales sobre selectorMandamiento.
	 * 
	 * @param selectorMandamiento, 
	 * @param estatusDeFiltrado
	 * @param numeroCausa
	 */
	function consultaGeneralMandamientoJudicial(selectorMandamiento, estatusDeFiltrado, numeroCausa){
		
		var fechaInicio="";
		var fechaFin="";
		var numeroExpediente = "";
		
		if(selectorMandamiento == 2){
			fechaInicio= $('#fechaInicio').val();
			fechaFin=    $('#fechaFin').val();
		}
		else if(selectorMandamiento == 3){
			
			numeroExpediente = $("#numeroExpediente").val();
			
			if(parseInt(numeroExpediente.length) < 8){
				customAlert("Ingrese por lo menos ocho d&iacute;gitos");
				return;
			}
		}
		else if(selectorMandamiento == 4){
			numeroExpediente = numeroCausa;
		}
		
		if(typeof estatusDeFiltrado == "undefined"){
			estatusDeFiltrado = 0;
		}
		
		//Obtiene los parametros de busqueda para recargar las bandejas
		var paramRecargaGridMand = obtenerParametrosRecargarGridMandamientos(selectorMandamiento, fechaInicio,
										fechaFin, numeroExpediente,idsTiposMandamientos,banderaPropios,estatusDeFiltrado);
		
		
		if(gridMandJud){
    		jQuery("#gridMandamientosJudiciales").jqGrid({ 
    			url: contextoPagina + '/consultaMandamientosJudicialesGenerico.do?inicio='+fechaInicio+'&fin='+fechaFin
    				+'&numeroExpediente='+numeroExpediente+'&estatus='+estatusDeFiltrado+'&tiposMandamientos='
    				+idsTiposMandamientos+'&filtrarPropios='+banderaPropios+'',
    			datatype: "xml", 
    			colNames:['Número de Caso','Número de Causa','Mandamiento Judicial','Fecha de creación','Estatus'], 
				colModel:[ 	
				          	{name:'numeroCaso',index:'numeroCaso', width:230, sortable:false},
				            {name:'numeroCausa',index:'1', width:240, sortable:false},
							{name:'mandamientoJudicial',index:'2', width:200, sortable:true},
							{name:'fechaCreacion',index:'3', width:110, sortable:true,align:'center'},
							{name:'Estado',index:'4', width:110, sortable:false,align:'center'},
						],				
				pager: jQuery('#pagerGridMandamientosJudiciales'),
				rowNum:30,
				rowList:[10,20,30],
				autowidth: true,
				autoheight:true,
				height:210,
				sortname: '6',
				viewrecords: true,
				sortorder: "desc",
				hidegrid: false,
				toolbar: [true,"top"],
				ondblClickRow: function(rowid) {
					validarAperturaVisorAdministrarMandamientoJudicial(rowid, estatusDeFiltrado, paramRecargaGridMand, null);
				}
			}).navGrid('#pagerGridMandamientosJudiciales',{edit:false,add:false,del:false});
    		
    		//Boton para enviar mandamientos
    		$("#t_gridMandamientosJudiciales").append("<input type='button' id='btnEnviarMandamiento' class='btn_Generico' value='Enviar Mandamiento' style='height:20px; align=right;font-size:-3; '/>");
    		$("#btnEnviarMandamiento").click(enviarMandamientoDeBandeja);
    		
    	
    		//Boton para enviar documentos de actualizacion de estatus
    		$("#t_gridMandamientosJudiciales").append("<input type='button' id='btnEnvActMandamiento' class='btn_Generico' value='Enviar actualizacion' style='height:20px; align=right;font-size:-3; '/>");
    		$("#btnEnvActMandamiento").click(enviarDocumentoCambioEstatusDeBandeja);
    		
    		
    		gridMandJud=false;
		}
		else{
			
			jQuery("#gridMandamientosJudiciales").jqGrid('setGridParam', { ondblClickRow: function(rowid) {
				validarAperturaVisorAdministrarMandamientoJudicial(rowid, estatusDeFiltrado, paramRecargaGridMand, null); 
					} 
				});
			jQuery("#gridMandamientosJudiciales").jqGrid('setGridParam', {url: contextoPagina + '/consultaMandamientosJudicialesGenerico.do?inicio='+fechaInicio+'&fin='+fechaFin+'&numeroExpediente='+numeroExpediente+'&estatus='+estatusDeFiltrado+'&tiposMandamientos='+idsTiposMandamientos+'&filtrarPropios='+banderaPropios+'',
					datatype: "xml" });
			$("#gridMandamientosJudiciales").trigger("reloadGrid");
		}
		
		mostrarOcultarBotonEnviarMandamiento(estatusDeFiltrado);
		
		if(typeof numeroCausa == "undefined"){
			if(typeof ocultaMuestraGrids == 'function'){
				ocultaMuestraGrids('gridMandamientosJudiciales');				
			}
		}
	}
	
	
	/**
	 * Obtiene los par&aacute;metros por los cuales se realiz&oacute; la b&uacute;squeda
	 * de mandamientos judiciales, para posteriormente realizar la recarga del grid de
	 * de mandamientos desde la ventana de administrar 
	 * 
	 * @param selectorMandamiento
	 * @param fechaInicio
	 * @param fechaFin
	 * @param numeroExpediente
	 * @param estatusDeFiltrado
	 * @returns {String}, lista de parametros para ser enviados
	 */
	function obtenerParametrosRecargarGridMandamientos(selectorMandamiento,fechaInicio,fechaFin,numeroExpediente,
				idsTiposMandamientos,banderaPropios,estatusDeFiltrado){
		
		var paramRecargaGridMand = '';
		
		paramRecargaGridMand += '&selectorMandamiento_recarga=' + selectorMandamiento;
		paramRecargaGridMand += '&fechaInicio_recarga=' + fechaInicio;
		paramRecargaGridMand += '&fechaFin_recarga=' + fechaFin;
		paramRecargaGridMand += '&numeroExpediente_recarga=' + numeroExpediente;
		paramRecargaGridMand += '&idsTiposMandamientos_recarga=' + idsTiposMandamientos;
		paramRecargaGridMand += '&banderaPropios_recarga=' + banderaPropios;
		paramRecargaGridMand += '&estatusDeFiltrado_recarga=' + estatusDeFiltrado;
		
		return paramRecargaGridMand;
	}
	
	
	/**
	 * Funcion, que valida la apertura del visor para administrar mandamientos
	 * judiciales de acuerdo a las siguiente reglas:
	 * 
	 * El visor se mostrar&aacute; en los siguientes estatus:
	 * 	NO_ATENDIDO, EN_PROCESO, ATENDIDO, SIN_DOCUMENTO_DE_CREACION, SIN_DOCUMENTO_DE_ESTATUS, 0.
	 * 
	 * cero es un caso especial en el cual la busqueda se realizo por causa
	 * 
	 * El visor no se deber&aacute; abrir en los siguientes estatus:
	 *	NO_ENVIADO, ACTUALIZACION_NO_ENVIADA
	 *
	 * @param mandamientoId, id del mandamiento judicial
	 * @param estatusDeFiltrado, estatus de filtrado de la bandeja del mandamiento judicial
	 * @param paramRecargaGridMand, parametros para recargar la bandeja
	 * @param idSolicitudMandamiento, id de la solicitud del mandamiento judicial(Para policía ministerial
	 */
	function validarAperturaVisorAdministrarMandamientoJudicial(mandamientoId, estatusDeFiltrado, paramRecargaGridMand, idSolicitudMandamiento){
				
		if(parseInt(estatusDeFiltrado) == 0 || parseInt(estatusDeFiltrado) == NO_ATENDIDO || parseInt(estatusDeFiltrado) == EN_PROCESO
				|| parseInt(estatusDeFiltrado) == ATENDIDO || parseInt(estatusDeFiltrado) == SIN_DOCUMENTO_DE_CREACION
					|| parseInt(estatusDeFiltrado) == SIN_DOCUMENTO_DE_ESTATUS){
			
			if(validarNumeroVentanasAbiertas(idWindowAdministrarMandamientoJudicial)){
				abrirAdministrarMandamientoJudicial(mandamientoId, estatusDeFiltrado, paramRecargaGridMand, idSolicitudMandamiento);
			}
		}
	}
	
	/**
	 * Funcion que valida el n&uacute;mero de ventanas abiertas
	 * de una determinada clase, de acuerdo con el contador,
	 * @param idVentana, id de la ventana que se desea validar
	 */
	function validarNumeroVentanasAbiertas(idVentana){
		
		var esAbrir = false;
		
		if(parseInt(idVentana) == 0){
			esAbrir = true;
		}else{
			customAlert("Ya cuenta con una ventana abierta.<br> Por favor cierrela, e int&eacute;ntelo nuevamente.","Aviso");
		}
		return esAbrir;
	}
	
	
	
	//Variable que controla la apertura del jsp administrarMandamientoJudicial
	var idWindowAdministrarMandamientoJudicial = 0;

	/**
	 * Funcion que abre la ventana de Administrar Mandamiento Judicial
	 * @param mandamientoId, id del mandamiento que sera consultado
	 * @param estatusDeFiltrado, estatus del cual partio la consulta, para recargar las bandejas
	 * @param paramRecargaGridMand, parametros para la recarga de la bandeja
	 * @param idSolicitudMandamiento, id de una solicitud de mandamiento (solo para Policia Ministerial)
	 */
	function abrirAdministrarMandamientoJudicial(mandamientoId, estatusDeFiltrado, paramRecargaGridMand, idSolicitudMandamiento){
		
		var parametros='';
		parametros += '&mandamientoId=' + mandamientoId;
		parametros += '&estatusDeFiltrado=' + estatusDeFiltrado;
		parametros += '&paramRecargaGridMand=' + paramRecargaGridMand;
		parametros += '&idSolicitudMandamiento=' + idSolicitudMandamiento;

		idWindowAdministrarMandamientoJudicial++;
		$.newWindow({id:"iframeWindowAdministrarMandamientoJudicial"+idWindowAdministrarMandamientoJudicial, statusBar: true, posx:200,posy:50,width:1100,height:450,title:"Administrar Mandamiento Judicial", type:"iframe",onWindowClose: function(id){
			idWindowAdministrarMandamientoJudicial--;
		}});
		$.updateWindowContent("iframeWindowAdministrarMandamientoJudicial"+idWindowAdministrarMandamientoJudicial,'<iframe src="'+contextoPagina +'/administrarMandamientoJudicial.do?parametros='+parametros+'" width="1100" height="450" />');
		$("#" +"iframeWindowAdministrarMandamientoJudicial"+idWindowAdministrarMandamientoJudicial+ " .window-maximizeButton").click();
	}
	
	
	/**
	 * Ventana tipo popUp que se lanza para la consulta por expediente
	 * @param selector, mandamiento judicial o medida cautelar
	 */
	function popUpTipoBusquedaModalXExpediente(selector){
		// 1 -> Mandamiento Judicial
		// 2 -> Medidas Cautelares
		var titulo="";
		if(selector == 1){
			titulo = "Administrar Mandamientos Judiciales por número de expediente";
		}
		else if(selector==2){
			titulo = "Administrar Medidas Cautelares por número de expediente";
		}

		$("#numeroExpediente").val("");
		$("#divBusquedaExpediente").dialog("open");
	  	$("#divBusquedaExpediente").dialog({ autoOpen: true, 
			modal: true, 
		  	title: titulo, 
		  	dialogClass: 'alert',
		  	position: [500,220],
		  	width: 450,
		  	height: 300,
		  	maxWidth: 350,
		  	buttons:{"Realizar búsqueda":function() {
		  			if(selector == 1){
		  				consultaGeneralMandamientoJudicial(3);
		  			}
		  			else if(selector == 2){
		  				consultaGeneralMedidaCautelar(3);
		  			}
		  			$(this).dialog("close");
		  		},
				"Cancelar" : function() {
					$(this).dialog("close");
				}
		  	}
		});
	}

	/**
	 * Abre la ventana modal de mandamientos y medidas
	 * @param opcion
	 * @param estatusDeFiltrado
	 */
	function modalFechaDeMandamientoYMedidas(opcion, estatusDeFiltrado){

		// opcion = 1  -> MandamientosJudiciales
		// opcion = 2  -> MedidasCautelares
		
		$('#fechaInicio').val('');
		$('#fechaFin').val('');
		
		if(typeof estatusDeFiltrado == "undefined"){
			estatusDeFiltrado = 0;
		}
		
		fechaServidor= consultaFechaHoraMaximaServer();
		fechaMax=getFechaMaximaServer(fechaServidor);
		
		
		var dates =	$("#fechaInicio, #fechaFin").datepicker(
			{
				dateFormat: 'dd/mm/yy',
				yearRange: '-111:+0',
				changeMonth: true,
				changeYear: true,
				numberOfMonths: 1,
				maxDate: fechaMax,
				onSelect: function( selectedDate ) {
					//setter fecha minima al segunda calendario
					$( "#fechaFin" ).datepicker( "option", "minDate", date );
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
	  						if(opcion==1){
	  							selectorMandamiento=2;
	  							consultaGeneralMandamientoJudicial(selectorMandamiento,estatusDeFiltrado);
	  						}
	  						else if(opcion==2){
	  							selectorMedida=2;
	  							consultaGeneralMedidaCautelar(selectorMedida,estatusDeFiltrado);
	  						}
	  						$(this).dialog("close");
				  		}
	  					else{
	  						alert("Favor de verificar el rango seleccionado");
	  					}
	  					
	  				},
	  				"Cancelar":function() {
		  				$(this).dialog("close");
	  				}
	  		}
	  	});				
	}
	
	
	/**************************************FUNCIONALIDAD ANTERIOR************************************************************************************/
	
	/**
	* Funcion que abre la ventana modal para cambiar el estado del mandamiento judicial
	*/
	idMandamientoGlobal = "";
	
	function abreModalPdfMandamientoJudicial(idMandamiento,estatusActual){
		//colocar el estado actual
		idMandamientoGlobal = idMandamiento;
		$("#estadoActualMandamientoJud").val(estatusActual);
		$("#divPdfMandamientoJudicial").dialog("open");
	  	$("#divPdfMandamientoJudicial").dialog({ autoOpen: true, 
			modal: true, 
		  	title: '¿Cambiar el estado del mandamiento judicial?', 
		  	dialogClass: 'alert',
		  	position: [500,220],
		  	width: 450,
		  	height: 260,
		  	maxWidth: 350,
		  	buttons:{"Aceptar":function() {
				var estado = $('#estadoMandamientoJudPJENC option:selected').val();
				actualizarEstadoMandamiento(idMandamientoGlobal,estado);		  		
		  		$(this).dialog("close");
		  		},
				"Cancelar" : function() {
					$(this).dialog("close");
				}
		  	}
		});	  	
	}	
	
	/**
	* Manda a actualizar el estado de un mandamiento judicial y actualiza el grid de mandamientos
	*
	*/
	function actualizarEstadoMandamiento(idMandamiento,estadoNuevo){
		
		$.ajax({
    		type: 'POST',
    		url: contextoPagina + '/actualizarMandamientoJudicial.do?estatusMandamiento='+estadoNuevo+'&mandamientoId='+idMandamiento,
    		data: '',
    		dataType: 'xml',
    		async: true,
    		success: function(xml){    			
    			cargaGridMandamientosJudiciales(numeroCausaGlobalGrid,estadoNuevo);
    		}
    		
    	});
	}
	
	var mostrarMedidaCautelarPJENC=1;
	/*
	*Funcion que abre la ventana de medidas cautelares
	* 
	*/
	function mostrarVentanaMedidasCautelares(rowid,numeroCausa){
		var flujoMedCautelar = "" ;		
		mostrarMedidaCautelarPJENC++;
		
		var idVentana = "iframewindowMedidasCautelares"+mostrarMedidaCautelarPJENC;
		
		$.newWindow({id:"iframewindowMedidasCautelares"+mostrarMedidaCautelarPJENC, statusBar: true, posx:70,posy:20,width:1100,height:400,title:"Consultar Medidas Cautelares", type:"iframe"});
    	$.updateWindowContent("iframewindowMedidasCautelares"+mostrarMedidaCautelarPJENC,'<iframe src="' + contextoPagina + '/ingresarMedidaCautelarPJENC.do?numeroExpediente='+numeroCausa+'&rowid='+rowid+'&flujoMedCautelar='+flujoMedCautelar+'&operacion=CONSULTA&idVentana='+idVentana+'" width="1100" height="400" />');
	}
	
	/*
	*Funcion que abre la ventana de medidas cautelares
	* 
	*/
	var mostrarMandamientoJudicialPJENC =1;
	function mostrarVentanaMandamientosJudiciales(rowid,numeroCausa){
		var flujoMedCautelar = "" ;
		mostrarMandamientoJudicialPJENC++;
		
		var idVentana = "iframewindowMandamientosJudiciales"+mostrarMandamientoJudicialPJENC;

		$.newWindow({id:"iframewindowMandamientosJudiciales"+mostrarMandamientoJudicialPJENC, statusBar: true, posx:70,posy:20,width:880,height:500,title:"Consultar Mandamientos Judiciales", type:"iframe"});
    	$.updateWindowContent("iframewindowMandamientosJudiciales"+mostrarMandamientoJudicialPJENC,'<iframe src="' + contextoPagina + '/ingresarMandamientoJudicial.do?numeroExpediente='+numeroCausa+'&rowid='+rowid +'&flujoMedCautelar='+flujoMedCautelar+'&operacion=CONSULTA&idVentana='+idVentana+'" width="880" height="500" />');
	}
	
	/*
	*Funcion para cerrar la ventana de mandamiento judicial
	*/
	function cerrarVentanaMandamientoJudicialJS(idVentana){
		$.closeWindow(idVentana);
	}
	
	/*
	 * Funcion para regresar la fecha maxima obtenida desde el servidor
	 * fechaCompleta - cadena con el siguiente formato : YYYY-MM-DD HH:MI:SS
	 * regresa una cadena con la fecha en el formato : DD/MM/YYYY
	 */
	function getFechaMaximaServer(fechaCompleta){
		var arrFechaHora=fechaCompleta.split(" ");
		var digitosFecha=arrFechaHora[0].split("-");
		return digitosFecha[2]+'/'+digitosFecha[1]+'/'+digitosFecha[0];
	}
	
	/*
	*Funcion para traer la fecha y hora del servidor en el formato : YYYY-MM-DD HH:MI:SS
	*/
	function consultaFechaHoraMaximaServer(){
		var fecha="";
		   $.ajax({
			     type: 'POST',
			     url: contextoPagina + '/regresaFechaYHoraDelServidor.do',
				 dataType: 'xml',
				 async: false,
				 success: function(xml){
					fecha= $(xml).find('fecha').text();
				  }
				});
		return fecha;
	}
	
	/***************************************************NUEVAS FUNCIONES CAMBIO ESTATUS MANDAMIENTO JUDICIAL*************************************************/
	
	
	/**
	*Envia un documento de cambio de estatus via WS desde la institucion PJ a PG o viceversa
	*regresa la respuesta, si fue posible enviar el documento y refresca la bandeja
	*/
	function enviarDocumentoCambioEstatus(mandamientoJudicialId,idDocCambioEstatus,esBandeja){
		
		bloquearPantalla(true, "Intentando enviar el cambio de estatus");

		$.ajax({
			type: 'POST',
			url:contextoPagina+'/enviarDocumentoCambioEstatusMandamiento.do?mandamientoJudicialId='+mandamientoJudicialId+'&idDocCambioEstatus='+idDocCambioEstatus,
			data: '',
			dataType: 'xml',
			success: function(respuesta){
				
				//BLOQUE PARA VALIDAR RESPUESTA
				if(parseInt($(respuesta).find('code').text()) == 0){
					
					var documentoEnvio = parseInt($(respuesta).find('documentoId').text());
					
					if(documentoEnvio > 0){
						customAlert("La actulizaci&oacute;n fue enviada correctamente");			
					}else{
						customAlert("Ocurri&oacute; un error al intentar enviar la actualizaci&oacute;n.<br/>"
								 + "por favor contacte al administrador");
					}
				}else{
					customAlert("La actualizaci&oacute;n del mandamiento judicial no ha podido ser enviada.<br/>"
							 + "Por favor consulte la bandeja “Actualizaci&oacute;n No Enviada”, para intentar enviarlo m&aacute;s tarde.");
							
				}
				 
				
				//BLOQUE PARA RECARGAR BANDEJA
				if(esBandeja){
					//recarga la bandeja de estatus no enviado
					consultaGeneralMandamientoJudicial(1,ACTUALIZACION_NO_ENVIADA);
				}else{
					//Recraga el grid de administrar mandamientos persona
					cargarGridAdminManJud();
					
					//Funciones para recargar la bandeja
					if (typeof window.parent.recargarBandejaMandamiento == 'function' ){
						window.parent.recargarBandejaMandamiento(selectorMandamiento_recarga,estatusDeFiltrado_recarga,numeroExpediente_recarga,fechaInicio_recarga,fechaFin_recarga,idsTiposMandamientos_recarga,banderaPropios_recarga);
					}
					//Cierra el editor de texto y desbloquea la pantalla
					//tambien debe desbloquear si no se envio el documento
					cerrarEditorTexto(idWindowEditorTextoMandamientos);
				}
				
				desbloquearPantalla();
			}
		});
		
	}
	
	/**
	 * Funcion para enviar una actualizacion del mandamiento judicial desde la bandeja de inicio
	 * de encargado causa
	 */
	function enviarDocumentoCambioEstatusDeBandeja(){
		
		var mandamientoJudicialId = jQuery("#gridMandamientosJudiciales").jqGrid('getGridParam','selrow');
		
		if(mandamientoJudicialId){
			enviarDocumentoCambioEstatus(mandamientoJudicialId,null,true);
		}else{
			customAlert("Seleccione un mandamiento judicial para enviar");
		}
	}
	
	/***************************************************NUEVAS FUNCIONES MANDAMIENTOS JUDICIALES*************************************************/
	
		
	/**
	* Funcion para hacer el envio del Mandamiento judicial a PG
	* @param mandamientoJudicialId, mandamiento que sera enviado
	*/
	function enviarMandamiento(mandamientoJudicialId, esBandeja){
		
		//bloquear pantalla
		bloquearPantalla(true, "Intentando enviar el mandamiento");

		$.ajax({
			type: 'POST',
			url:contextoPagina+'/enviarMandamiento.do?mandamientoJudicialId='+mandamientoJudicialId,
			data: '',
			dataType: 'xml',
			success: function(respuesta){
				
				//BLOQUE PARA VALIDAR RESPUESTA
				if(parseInt($(respuesta).find('code').text()) == 0){
					
					var documentoEnvio = parseInt($(respuesta).find('documentoId').text());
					
					if(documentoEnvio > 0){
						customAlert("El mandamiento fue enviado correctamente");
					}else{
						customAlert("Ocurri&oacute; un error al intentar enviar el mandamiento.<br/>"
								 + "por favor contacte al administrador.");
					}
				}else{
					customAlert("El mandamiento judicial no ha podido ser enviado.<br/>"
							 + "Por favor consulte la bandeja “No Enviado”, para intentar enviarlo m&aacute;s tarde.");
				}
				 

				
				//BLOQUE PARA RECARGAR BANDEJAS
				
				if(esBandeja){//se invoco desde la bandeja
					//recarga la bandeja de estatus no enviado
					consultaGeneralMandamientoJudicial(1,NO_ENVIADO);
				}else{ //se invoco desde el visor
					
					//Funciones para recargar la bandeja
					if (typeof window.parent.recargarBandejaMandamiento == 'function' ){
						window.parent.recargarBandejaMandamiento(selectorMandamiento_recarga,estatusDeFiltrado_recarga,numeroExpediente_recarga,fechaInicio_recarga,fechaFin_recarga,idsTiposMandamientos_recarga,banderaPropios_recarga);
					}
					//Cierra el editor de texto y desbloquea la pantalla
					//tambien debe desbloquear si no se envio el documento
					cerrarEditorTexto(idWindowEditorTextoMandamientos);
					//Se oculta la liga de mandamiento con guardado parcial
					if (typeof ocultarLigaMandamientoParcial == 'function' ){
						ocultarLigaMandamientoParcial();
					}
				}
				//Desbloquear pantalla
				desbloquearPantalla();
			}
		});
	}
	
	
	/**
	 * Funcion para enviar un mandamiento judicial desde la bandeja de inicio
	 * de encargado causa
	 */
	function enviarMandamientoDeBandeja(){
		
		var mandamientoJudicialId = jQuery("#gridMandamientosJudiciales").jqGrid('getGridParam','selrow');
		
		if(mandamientoJudicialId){
			enviarMandamiento(mandamientoJudicialId,true);				
		}else{
			customAlert("Seleccione un mandamiento judicial para enviar");
		}
	}
	
	
	/**
	 * Funcion que muestra u oculta el boton de enviar mandamiento,
	 * dependiendo si el estatus es NO_ENVIADO
	 * @param estatusMandamiento, requerido para comparar
	 */
	function mostrarOcultarBotonEnviarMandamiento(estatusMandamiento){
	
		if(estatusMandamiento == NO_ENVIADO){
			$("#t_gridMandamientosJudiciales").show();
			$("#btnEnviarMandamiento").show();
			$("#btnEnvActMandamiento").hide();
		}else if (estatusMandamiento == ACTUALIZACION_NO_ENVIADA){
			$("#t_gridMandamientosJudiciales").show();
			$("#btnEnviarMandamiento").hide();
			$("#btnEnvActMandamiento").show();
		}else{
			$("#t_gridMandamientosJudiciales").hide();
		}
	}
	
	

	//Variable para controlar la apertura del editor de documentos
	var idWindowEditorTextoMandamientos = 0;
	

	/**
	 * Abre el editor de texo o el PDF del documento documentoId, pude ser el id
	 * del mandamiento o el id del documento de cambio de estatus
	 * 
	 * @param documentoId
	 * @param actividadId
	 * @param esParcial
	 * @param permiteEdicion
	 */
	function abrirEditorDocumentosMandamientos(documentoId, actividadId, esParcial, permiteEdicion){

		if(esParcial==false){
			//Fue guardado definitivo
			document.frmDoc.action = contextoPagina + "/ConsultarContenidoArchivoDigital.do?documentoId="+documentoId;
			document.frmDoc.submit();
		}else{
			if(permiteEdicion == true){
				if(validaAperturaDeVisor(idWindowEditorTextoMandamientos) == true){
					
					titulo="Editor de documentos";
					idWindowEditorTextoMandamientos++;
					$.newWindow({id:"iframewindowGenerarDocumento"+idWindowEditorTextoMandamientos, statusBar: true, posx:200,posy:50,width:1140,height:400,title:""+titulo, type:"iframe", confirmarCierreVentana:true,onWindowClose: function(id){
						idWindowEditorTextoMandamientos--;
						}
					});
					
					var parametrosEditor = '';
					parametrosEditor += '&documentoId='+documentoId;
					parametrosEditor += '&numeroUnicoExpediente='+numeroExpediente;
					parametrosEditor += '&ocultarNumeroOficio='+true;
					parametrosEditor += '&idWindowPantallaActuaciones='+idWindowEditorTextoMandamientos;
					parametrosEditor += '&actividadId='+actividadId;
					parametrosEditor += '&mandamientoId='+mandamientoJudicialId;
					
	    		    $.updateWindowContent("iframewindowGenerarDocumento"+idWindowEditorTextoMandamientos,'<iframe src="'+contextoPagina+'/generarDocumentoSinCaso.do?parametrosEditor='+parametrosEditor+'" width="1140" height="400" />');
	    		    $("#" +"iframewindowGenerarDocumento"+idWindowEditorTextoMandamientos+ " .window-maximizeButton").click();
				}
			}else{
				customAlert("Este documento cuenta con un guardado parcial.<br> Y no es posible continuar editandolo desde esta liga","Aviso");
			}				
		}
	}

	/**
	 * Funcion que valida la apertura del visor
	 * @param idWindowEditorTextoMandamientos, id de la ventana de editor de textos
	 * @returns {Boolean}
	 */
	function validaAperturaDeVisor(idWindowEditorTextoMandamientos){

		var respuesta = false;
		
		if( idWindowEditorTextoMandamientos == 0){
			respuesta = true;
		}else{
			customAlert("Ya cuenta con una ventana abierta.<br> Por favor cierrela, e int&eacute;ntelo nuevamente","Aviso");
		}
		return respuesta;
	}
	
	/**
	 * Funcion que cierra la ventana del editor de texto
	 * @param idWindowEditorTextoMandamientos, id de la ventana de editor de textos
	 */
	function cerrarEditorTexto(idWindowEditorTextoMandamientos){
		
		var pantalla ="iframewindowGenerarDocumento";
		pantalla += idWindowEditorTextoMandamientos;
		$.closeWindow(pantalla);
	}
	
	

	/**
	 *  Funcion para recargar los grids de acuerdo al filtro de busqueda, de
	 * igual manera de como se genera la consulta, cuando se realiza desde la
	 * bandeja.
	 * @param selectorMandamiento_recarga
	 * @param estatusDeFiltrado_recarga
	 * @param numeroExpediente_recarga
	 * @param fechaInicio_recarga
	 * @param fechaFin_recarga
	 * @param idsTiposMandamientos_recarga
	 * @param banderaPropios_recarga
	 */
	function recargarBandejaMandamiento(selectorMandamiento_recarga,estatusDeFiltrado_recarga,numeroExpediente_recarga,fechaInicio_recarga,fechaFin_recarga,idsTiposMandamientos_recarga,banderaPropios_recarga){
		
		var fechaInicio	= '';
		var fechaFin	= '';
		var numeroExpediente = '';
		
		if(typeof estatusDeFiltrado_recarga == "undefined"){
			estatusDeFiltrado_recarga = 0;
		}
		
		if(parseInt(selectorMandamiento_recarga) == 2){
			fechaInicio	= fechaInicio_recarga;
			fechaFin	= fechaFin_recarga
		}
		else if(parseInt(selectorMandamiento_recarga) == 3){
			numeroExpediente = numeroExpediente_recarga;
		}
		else if(parseInt(selectorMandamiento_recarga) == 4){
			//numeroExpediente = numeroCausa;
		}
		
		$("#gridMandamientosJudiciales").jqGrid('setGridParam', {url: contextoPagina + '/consultaMandamientosJudicialesGenerico.do?inicio='+fechaInicio+'&fin='+fechaFin+'&numeroExpediente='+numeroExpediente_recarga+'&estatus='+estatusDeFiltrado_recarga+'&tiposMandamientos='+idsTiposMandamientos_recarga+'&filtrarPropios='+banderaPropios_recarga+'',datatype: "xml" });
		$("#gridMandamientosJudiciales").trigger("reloadGrid");
		
		mostrarOcultarBotonEnviarMandamiento(estatusDeFiltrado_recarga);
	}
	
	
	
	
	