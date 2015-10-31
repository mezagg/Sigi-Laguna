	<%@page import="mx.gob.segob.nsjp.comun.enums.documento.EstatusNotificacion"%>
	 <script type="text/javascript"  src="<%=request.getContextPath()%>/resources/js/wdCalendar/Plugins/jquery.ui.datepicker-es.js"></script>
	 <script src="<%=request.getContextPath()%>/resources/js/wdCalendar/Plugins/jquery.ui.timepicker.js"></script>
	 <!--Hoja de estilos de Layout-->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/layout_complex.css" media="screen" />
	<!--Hoja de estilos ultrasist-->
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/estilos.css" media="screen" />	
	<!--Hojas de estilos para los componentes UI de Jquery-->
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/jquery-ui.css"/>
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/resources/css/south-street/jquery-ui-1.8.10.custom.css" />	

	<script type="text/javascript">	
	
	//Id del evento que viene desde el gird de la bandeja de notificaciones	
	idEvento= '<%=request.getParameter("idEvento")!=null ? request.getParameter("idEvento") : request.getParameter("idAudiencia")%>';

	//Variables de contexto
	var CONTEXT_ROOT = '<%= request.getContextPath()%>';
	var contextoPagina = "${pageContext.request.contextPath}";
	
	$(document).ready(function() {

		cargaNotificacionesFuncionarioExterno();
		cargaNotificacionesFuncionario();
		cargaNotificacionesInvolucrado();
		consultaTiposNotificacion();

		if(desactivarBotonesEnNotificaciones == true){
			$('#agregarDestinatarioBoton').hide();
			$('#agregarDestinatarioBoton').hide();
		}
	});


	/*
	*Funcion para consultar los tipos de notificacion
	*/
	function consultaTiposNotificacion(){

		$('#formaDeNotificacion').append('<option value="0">-Seleccione-</option>');
		
		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/consultarFormasNotificacion.do',
			data: '',
			dataType: 'xml',
			async: false,
			success: function(xml){
				$(xml).find('catFormaNotificacion').each(function(){
					$('#formaDeNotificacion').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
				});
			}
		});
	}
	
	/*
	*Funcion que consulta el detalle del evento y llena 
	*los campos de la TAB Detalle evento
	*/
	function consultaDetalleEventoNotif(idEvento){

		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/atenderSolicitudAudienciaNotificador.do?tipoDeRespuesta=1',
			data: 'idEvento='+ idEvento, 
			async: false,
			dataType: 'xml',
			success: function(xml){
				var errorCode;
				errorCode=$(xml).find('response').find('code').text();
				if(parseInt(errorCode)==0){
    				limpiaDatosDetalleEvento();
					$("#numCasoNotifDetalle").val($(xml).find('numeroGeneralCaso').first().text());
					$("#numExpedienteNotifDetalle").val($(xml).find('numeroExpediente').first().text());
					numeroExpediente = $(xml).find('numeroExpediente').first().text();
					numeroExpedienteId = $(xml).find('numeroExpedienteId').first().text();
					
					$("#tipoEventoNotifDetalle").val($(xml).find('tipoEvento').first().text());
					$("#eventoNotifDetalle").val($(xml).find('tipo').find('valor').first().text());
					$("#estadoEventoNotifDetalle").val($(xml).find('estatusAudiencia').find('valor').first().text());

					//Se le da formato a la fecha del nuevo evento
					var fechEvento =  $(xml).find('fechaEvento').first().text();
					var fechEventoPos1 = fechEvento.indexOf(":",0); 

					$("#fechaEventoNotifDetalle").val(fechEvento.substring(0,fechEventoPos1-2));

					//Se le da formato a la hora del nuevo evento
					var horaEvento =  $(xml).find('fechaEvento').first().text();
					var horaEventoPos1 = horaEvento.indexOf(":",0); 

					$("#horaEventoNotifDetalle").val(horaEvento.substring(horaEventoPos1-2,horaEventoPos1+3));
					$("#lugarEventoNotifDetalle").val($(xml).find('lugarEvento').first().text());
					$("#direccionEventoNotifDetalle").val($(xml).find('ubicacionEvento').first().text());
					idExpediente=$(xml).find('expedienteId').first().text();
    			}
				else{
					//Mostrar mensaje de error
				}
			}
		});
	}


	var recargaNotificacionesFuncionarioExterno = true;
	 
	/*
	*Funcion que carga el grid, con el detalle de las notificaciones asociadas a un funcionario
	*/
	function cargaNotificacionesFuncionarioExterno(){

		if(recargaNotificacionesFuncionarioExterno){
			jQuery("#gridDetalleNotificacionesFuncionarioExterno").jqGrid({ 
				url:'<%= request.getContextPath()%>/atenderSolicitudAudienciaNotificador.do?tipoDeRespuesta=0&idEvento='+idEvento+'&esFuncionario=2'+'', 
				datatype: "xml", 
				colNames:['Interveniente','idInstitucion','Instituci&oacute;n','Tipo Evento','&Uacute;ltima notificaci&oacute;n creada','&Uacute;ltima notificaci&oacute;n recibida','No. de Notificaciones','esFuncionario'], 
				colModel:[ 	{name:'involucrado',index:'involucrado', width:150,align:'center', cellattr: function (rowId, tv, rawObject, cm, rdata) { return 'style="white-space: normal;"'; },sortable:false },
				           	{name:'idInstitucion',index:'idInstitucion', width:150, hidden:true},
							{name:'institucion',index:'institucion', width:120,align:'center',sortable:false},
							{name:'tipoNotificacion',index:'tipoNotificacion', width:120,align:'center',sortable:false},
							{name:'notificacionEnviada',index:'notificacionEnviada', width:160,align:'center',sortable:false},
							{name:'notificacionRecibida',index:'notificacionRecibida', width:160,align:'center',sortable:false},
							{name:'noNotificaciones',index:'noNotificaciones', width:150,align:'center',sortable:false},
							{name:'esFuncionario',index:'esFuncionario', width:150, hidden:true}
																		
						],
				pager: jQuery('#pagerNotificacionesFuncionarioExterno'),
				rowNum:10,
				rowList:[10,20,30],
				autowidth: true,
				sortname: 'detalle',
				viewrecords: true,
				sortorder: "desc",
				caption: "Funcionarios Externos asociados a la audiencia",
				loadComplete: function(){
					recargaNotificacionesFuncionarioExterno=false;
				},
				onSelectRow: function(rowid) {
					var rowData = jQuery("#gridDetalleNotificacionesFuncionarioExterno").jqGrid('getRowData',rowid);
					//mostrarDetalleNotificacionesPersona(involucradoId,idEvento,esFuncionario,esFuncionarioExterno,institucionId)
					mostrarDetalleNotificacionesPersona(rowid,idEvento,false,true,false,rowData.idInstitucion);
				}
			}).navGrid('#pagerNotificacionesFuncionarioExterno',{edit:false,add:false,del:false});
		}else{
			//Recarga el grid
			$("#gridDetalleNotificacionesFuncionarioExterno").jqGrid('setGridParam', { onSelectRow: function(rowid) {
					var rowData = jQuery("#gridDetalleNotificacionesFuncionarioExterno").jqGrid('getRowData',rowid);
				 	mostrarDetalleNotificacionesPersona(rowid,idEvento,false,true,false,rowData.idInstitucion); 
				 } });
			$("#gridDetalleNotificacionesFuncionarioExterno").jqGrid('setGridParam', {url:'<%= request.getContextPath()%>/atenderSolicitudAudienciaNotificador.do?tipoDeRespuesta=0&idEvento='+idEvento+'&esFuncionario=2'+'',datatype: "xml" });
			$("#gridDetalleNotificacionesFuncionarioExterno").trigger("reloadGrid");
		}
	}
	


	var recargaNotificacionesFuncionario = true; 
	
	/*
	*Funcion que carga el grid, con el detalle de las notificaciones asociadas a un funcionario
	*/
	function cargaNotificacionesFuncionario(){

		if(recargaNotificacionesFuncionario){
			jQuery("#gridDetalleNotificaciones").jqGrid({ 
				url:'<%= request.getContextPath()%>/atenderSolicitudAudienciaNotificador.do?tipoDeRespuesta=0&idEvento='+idEvento+'&esFuncionario=1'+'', 
				datatype: "xml", 
				colNames:['Interveniente','idInstitucion','Instituci&oacute;n','Tipo Evento','&Uacute;ltima notificaci&oacute;n creada','&Uacute;ltima notificaci&oacute;n recibida','No. de Notificaciones','esFuncionario'], 
				colModel:[ 	{name:'involucrado',index:'involucrado', width:150,align:'center', cellattr: function (rowId, tv, rawObject, cm, rdata) { return 'style="white-space: normal;"'; },sortable:false },
				           	{name:'idInstitucion',index:'idInstitucion', width:150, hidden:true},
							{name:'institucion',index:'institucion', width:120,align:'center',sortable:false},
							{name:'tipoNotificacion',index:'tipoNotificacion', width:120,align:'center',sortable:false},
							{name:'notificacionEnviada',index:'notificacionEnviada', width:160,align:'center',sortable:false},
							{name:'notificacionRecibida',index:'notificacionRecibida', width:160,align:'center',sortable:false},
							{name:'noNotificaciones',index:'noNotificaciones', width:150,align:'center',sortable:false},
							{name:'esFuncionario',index:'esFuncionario', width:150, hidden:true}
																		
						],
				pager: jQuery('#pagerNotificacionesFuncionarios'),
				rowNum:10,
				rowList:[10,20,30],
				autowidth: true,
				sortname: 'detalle',
				viewrecords: true,
				sortorder: "desc",
				caption: "Funcionarios asociados a la audiencia",
				loadComplete: function(){
					recargaNotificacionesFuncionario = false;
				},
				onSelectRow: function(rowid) {
					var rowData = jQuery("#gridDetalleNotificaciones").jqGrid('getRowData',rowid);
					//mostrarDetalleNotificacionesPersona(involucradoId,idEvento,esFuncionario,esFuncionarioExterno)
					mostrarDetalleNotificacionesPersona(rowid,idEvento,true,false,false,rowData.idInstitucion);
				}
			})
		}else{
			//Recarga el grid
			$("#gridDetalleNotificaciones").jqGrid('setGridParam', { onSelectRow: function(rowid) {
					var rowData = jQuery("#gridDetalleNotificaciones").jqGrid('getRowData',rowid);
					mostrarDetalleNotificacionesPersona(rowid,idEvento,true,false,false,rowData.idInstitucion); 
				} })
			$("#gridDetalleNotificaciones").jqGrid('setGridParam', {url:'<%= request.getContextPath()%>/atenderSolicitudAudienciaNotificador.do?tipoDeRespuesta=0&idEvento='+idEvento+'&esFuncionario=1'+'',datatype: "xml" });
			$("#gridDetalleNotificaciones").trigger("reloadGrid");
		}
	}

	
	var recargaNotificacionesInvolucrado = true;
	
	/*
	*Funcion que carga el grid, con el detalle de las notificaciones asociadas a un involucrado
	*/
	function cargaNotificacionesInvolucrado(){
		
		if(recargaNotificacionesInvolucrado){
			jQuery("#gridDetalleNotificacionesInvolucrado").jqGrid({ 
				url:'<%= request.getContextPath()%>/atenderSolicitudAudienciaNotificador.do?tipoDeRespuesta=0&idEvento='+idEvento+'&esFuncionario=0'+'', 
				datatype: "xml", 
				colNames:['Interveniente','Tipo Evento','Calidad','&Uacute;ltima notificaci&oacute;n creada','&Uacute;ltima notificaci&oacute;n recibida','No. de Notificaciones','esFuncionario'], 
				colModel:[ 	{name:'involucrado',index:'involucrado', width:150,align:'center', cellattr: function (rowId, tv, rawObject, cm, rdata) { return 'style="white-space: normal;"'; },sortable:false },
							{name:'tipoNotificacion',index:'tipoNotificacion', width:150,align:'center',sortable:false},
							{name:'calidad',index:'calidad', width:150,align:'center',sortable:false}, 
							{name:'notificacionEnviada',index:'notificacionEnviada', width:160,align:'center',sortable:false},
							{name:'notificacionRecibida',index:'notificacionRecibida', width:160,align:'center',sortable:false},
							{name:'noNotificaciones',index:'noNotificaciones', width:150,align:'center',sortable:false},
							{name:'esFuncionario',index:'esFuncionario', width:150, hidden:true}
																		
						],
				pager: jQuery('#pagerNotificacionesInvolucrado'),
				rowNum:10,
				rowList:[10,20,30],
				autowidth: true,
				sortname: 'detalle',
				viewrecords: true,
				sortorder: "desc",
				caption: "Involucrados asociados a la audiencia",
				loadComplete: function(){
					recargaNotificacionesInvolucrado = false;
				},
				onSelectRow: function(rowid) {
					//mostrarDetalleNotificacionesPersona(involucradoId,idEvento,esFuncionario,esFuncionarioExterno,institucionId)
					mostrarDetalleNotificacionesPersona(rowid,idEvento,false,false,true,null);
				}
			})
		}else{
			//Recarga el grid
			$("#gridDetalleNotificacionesInvolucrado").jqGrid('setGridParam', { onSelectRow: function(rowid) { mostrarDetalleNotificacionesPersona(rowid,idEvento,false,false,true,null); } })
			$("#gridDetalleNotificacionesInvolucrado").jqGrid('setGridParam', {url:'<%= request.getContextPath()%>/atenderSolicitudAudienciaNotificador.do?tipoDeRespuesta=0&idEvento='+idEvento+'&esFuncionario=0'+'',datatype: "xml" });
			$("#gridDetalleNotificacionesInvolucrado").trigger("reloadGrid");
		}
	}

	
	/*
	*Funcion para recargar los grid de dependiendo de la seleccion
	*/
	function recargaGridsNotificacionesPersona(esFuncionario,esFuncionarioExt,esInvolucrado){

		if(esFuncionario){
			cargaNotificacionesFuncionario();
		}
		if(esFuncionarioExt){
			cargaNotificacionesFuncionarioExterno();
		}
		if(esInvolucrado){
			cargaNotificacionesInvolucrado();
		}
	}	

	
	/*
	*Funcion que recibe el id de la notificacion
	*y consulta el detalle de notificaciones asociadas a una persona
	*/
	function mostrarDetalleNotificacionesPersona(invoId,idEvento,esFuncionario,esFuncionarioExt,esInvolucrado,institucionId){

	//abre la ventana modal en la que aparecen los detalles de las notificaciones por persona
		if (desactivarBotonesEnNotificaciones == true) {
			ventanaModalSinBoton();
		} else {
			ventanaModal(invoId,esFuncionario,esFuncionarioExt,esInvolucrado,institucionId);
		}

		//Consulta y muestra los datos de la persona
		mostrarDatosPersonaNotificacion(invoId, idEvento,esFuncionario,esFuncionarioExt,esInvolucrado);

		var params = 'idEvento=' + idEvento;
		params += '&tipoDeRespuesta=0';
		
		if(esFuncionario){
			params += '&funcionarioId='+invoId;
		}
		if(esFuncionarioExt){
			params += '&funcionarioExternoId='+invoId;
		}
		if(esInvolucrado){
			params += '&involucradoId='+invoId;
		}

		if (primeraConsulta == "true") {
			//Llena el grid con los datos de la persona seleccionada
			jQuery("#gridDetalleNotificacionesPersona").jqGrid({
				
				url : '<%= request.getContextPath() %>/consultaDetalleNotificacionesPersona.do?'+params, 						
				datatype: "xml", 					
				colNames:['No. Notificaci&oacute;n','Forma notificaci&oacute;n','Fecha/Hora creaci&oacute;n','Fecha recibida','Hora recibida','Observaciones','Documento'], 
				
				colModel:[{name:'noNotificacion',index:'3', width:120, align:"center", sortable:true},
				          {name:'formaNotificacion',index:'formaNotificacion', width:150, align:"center", sortable:false},
				          {name:'notificacionEnviada',index:'notificacionEnviada', width:160, align:"center", sortable:true},
				          {name:'fechaRecibida',index:'fechaRecibida', width:130, align:"center", sortable:false},
				          {name:'horaRecibida',index:'horaRecibida', width:180, align:"center", sortable:false},
				          {name:'observaciones',index:'observaciones', width:200, align:"center", sortable:false},
				          {name:'documento',index:'documento', width:150,align:"center", sortable:false}
	
				],
				rowNum:10,
				rowList:[10,20,30],
				pager: jQuery('#pagerDetalleNotificacionesPersona'), 
				sortname: '3', 
				viewrecords: true,
				sortorder: "desc",
				height: 150,
				loadComplete: function(){
					primeraConsulta="false";
					creaDatePickers();
				},
				onSelectRow: function(rowid) {
					idnotificacion=rowid;
					//archivoDigitalId=rowid;
					documentoId=rowid;
				},
				caption:"Lista de notificaciones de la persona" 
			})			
		}
		else{
			jQuery("#gridDetalleNotificacionesPersona").jqGrid('setGridParam', {url:'<%= request.getContextPath() %>/consultaDetalleNotificacionesPersona.do?'+params,datatype: "xml" });
			$("#gridDetalleNotificacionesPersona").trigger("reloadGrid");
		}
	}
	
	/*
	*Funcion que abre la ventana modal para mostrar el detalle de las
	*notificaciones asociadas a una persona
	*/
	function ventanaModal(invoId,esFuncionario,esFuncionarioExt,esInvolucrado,institucionId){

		//abre la ventana de detalle de la persona
		$("#ventanaDetallePersona").dialog("open");
		$("#ventanaDetallePersona").dialog({ autoOpen: true, 
	  		modal: true, 
	  		title: 'Detalle de Notificaciones de Persona', 
	  		dialogClass: 'alert',
	  		position: [0,0],
	  		width: 1200,
	  		height: 550,
	  		maxWidth: 1200,
	  		maxHeight: 550,
	  		minWidth: 1200,
	  		minHeight: 550,  
	  		buttons:{"Agregar Notificaci&oacute;n":function() {

		  			if($('#formaDeNotificacion option:selected').val() == "0"){
		  				customAlert("Debe seleccionar una forma de notificacion");
		  			}else{
		  				$(this).dialog("close");
			  			generarNotificacion(invoId,esFuncionario,esFuncionarioExt,esInvolucrado,institucionId);
			  			//volver a subir el objeto a sesi&oacute;n
			  			consultaDetalleEventoNotif(idEvento);
			  			//Recarga el grid
			  			$("#gridDetalleNotificaciones").trigger("reloadGrid");
			  			$("#gridDetalleNotificacionesInvolucrado").trigger("reloadGrid");
			  		}
			  		
	  			}
	  		}
	  	});
	}

	
	/*
	*Funcion que abre la ventana modal para mostrar el detalle de las
	*notificaciones asociadas a una persona
	*/
	function ventanaModalSinBoton(){	

		//Ocultamos el tipo de notificacion
		$("#divGenerarNotificacion").hide();
		
		//abre la ventana de detalle de la persona
		$("#ventanaDetallePersona").dialog("open");
		$("#ventanaDetallePersona").dialog({ autoOpen: true, 
	  		modal: true, 
	  		title: 'Detalle de Notificaciones de Persona', 
	  		dialogClass: 'alert',
	  		position: [0,0],
	  		width: 1200,
	  		height: 550,
	  		maxWidth: 1200,
	  		maxHeight: 550,
	  		minWidth: 1200,
	  		minHeight: 550  		
	  	});
	}
	
	
	/*
	*Funcion que consulta el detalle del evento y llena 
	*los campos de la TAB Detalle evento
	*/
	function generarNotificacion(invoId,esFuncionario,esFuncionarioExt,esInvolucrado,institucionId){

        var param ='id='+eventoDTOID;
        
       	param += '&numeroExpedienteId='+numeroExpedienteId;
		param += '&formaNotificacion='+$('#formaDeNotificacion option:selected').val();
        	
       	if(esFuncionario){
			param += '&funcionarioId='+invoId;
        }
		if(esFuncionarioExt){
			param += '&funcionarioExternoId='+invoId;
		}
		if(esInvolucrado){
           	param += '&involucradoId='+invoId;
		}
            
		ejecutaAction("/generarNotificacion", function(xml){
			
			documentoId=$(xml).find('long').text();
			
			if(documentoId != undefined && documentoId != null && documentoId != "" && documentoId != 0){
				
				customAlert("Se guard&oacute; la notificaci&oacute;n correctamente.")
				generarDocumento();
				recargaGridsNotificacionesPersona(esFuncionario,esFuncionarioExt,esInvolucrado);
				//Funcion para recargar la bandeja de entrada del notificador
				window.parent.seguimientoEventos(estatusConsulta);

				if(esFuncionario || esFuncionarioExt){
					enviarNotificacionWS(invoId,eventoDTOID,documentoId,institucionId);
		        }
			}else{
				alertDinamico("No se logr&oacute; generar la notificaci&oacute;n");
			}
		} , param);
	}
	

	/*
	*Funcion consulta los datos de la persona (nombre, institucion, direccion fisica y direccion electronica)
	* y los muestra en la ventana modal de detalle de notificaciones  
	*/
	function mostrarDatosPersonaNotificacion(invoId,idEvento,esFuncionario,esFuncionarioExt,esInvolucrado){

		limpiaDatosPersonaNotificacion();
		
		var params ='idEvento='+idEvento;
		params+='&tipoDeRespuesta=1';

		if(esFuncionario){
			params += '&funcionarioId='+invoId;
		}
		if(esFuncionarioExt){
			params += '&funcionarioExternoId='+invoId;
		}
		if(esInvolucrado){
			params += '&involucradoId='+invoId;
		}


		ejecutaAction("/consultaDetalleNotificacionesPersona", function(xml){
			var nombre = "";
			var apPat = "";
			var apMat = "";
			var errorCode=$(xml).find('response').find('code').text();
			
			if(parseInt(errorCode)==0){
				if(esFuncionario){
					nombre = $(xml).find('funcionario').find('nombreFuncionario').first().text();
					apPat = $(xml).find('funcionario').find('apellidoPaternoFuncionario').first().text();
					apMat = $(xml).find('funcionario').find('apellidoMaternoFuncionario').first().text();
					$("#nombreDetallePersona").val(nombre+" "+apPat+" "+apMat);
					$("#institucionDetallePersona").val($(xml).find('nombreInstitucion').first().text());
					$("#trDirecionFisica").attr('hidden', true);
					$("#trDirecionElectronica").attr('hidden', true);
				}
				if(esFuncionarioExt){

					nombre = $(xml).find('funcionarioExterno').find('nombre').first().text();
					apPat = $(xml).find('funcionarioExterno').find('apellidoPaterno').first().text();
					apMat = $(xml).find('funcionarioExterno').find('apellidoMaterno').first().text();
					$("#nombreDetallePersona").val(nombre+" "+apPat+" "+apMat);
					$("#institucionDetallePersona").val($(xml).find('institucionPertenece').find('nombreInst').text());
					$("#trDirecionFisica").attr('hidden', true);
					$("#trDirecionElectronica").attr('hidden', true);
					
				}
				if(esInvolucrado){
					$("#trDirecionFisica").attr('hidden', false);
					$("#trDirecionElectronica").attr('hidden', false);
					nombre = $(xml).find('nombresDemograficoDTO').find('nombre').first().text();
					apPat = $(xml).find('nombresDemograficoDTO').find('apellidoPaterno').first().text();
					apMat = $(xml).find('nombresDemograficoDTO').find('apellidoMaterno').first().text();

					$("#nombreDetallePersona").val(nombre+" "+apPat+" "+apMat);

					//Institucion
					$("#etiInstitucion").hide();
					$("#institucionDetallePersona").hide();
					$("#institucionDetallePersona").val($(xml).find('institucionPresenta').find('nombreInst').first().text());
												
		 			//Direccion Fisica
					var calle="", numExt="", numInt="", colonia="", codPostal="", municipio="", entFed="", pais="";
					
					calle = $(xml).find('involucrado').find('domicilioNotificacion').find('calle').first().text();
					
					numExt = $(xml).find('involucrado').find('domicilioNotificacion').find('numeroExterior').first().text();
					numInt = $(xml).find('involucrado').find('domicilioNotificacion').find('numeroInterior').first().text();
					colonia = $(xml).find('involucrado').find('domicilioNotificacion').find('asentamientoDTO').find('nombreAsentamiento').first().text();
					codPostal = $(xml).find('involucrado').find('domicilioNotificacion').find('asentamientoDTO').find('codigoPostal').first().text();
					municipio = $(xml).find('involucrado').find('domicilioNotificacion').find('asentamientoDTO').find('municipioDTO').find('nombreMunicipio').first().text();
					entFed = $(xml).find('involucrado').find('domicilioNotificacion').find('asentamientoDTO').find('municipioDTO').find('entidadFederativaDTO').find('abreviacion').first().text();
					pais = $(xml).find('involucrado').find('domicilioNotificacion').find('asentamientoDTO').find('municipioDTO').find('entidadFederativaDTO').find('valorIdPais').find('valor').first().text();

					//Se comentan temporalmene
					//$("#direccionFisicaDetallePersona").val(calle+" No.Ext: "+numExt+" No.Int: "+numInt+" Col. "+colonia+" CP. "+codPostal+"  "+municipio+". "+pais+" ,"+entFed);
					
					//Direccion Electr&oacute;nica
    				//$("#direccionElectronicaDetallePersona").val($(xml).find('correosDTO').find('direccionElectronica').first().text());
				}
			}
		} , params);
	}	

	/*
	*Limpia los datos de la persona
	*/
	function limpiaDatosPersonaNotificacion(){
		
		$("#nombreDetallePersona").val("");
		$("#institucionDetallePersona").val("");
		//Se comentan temporalmente
		//$("#direccionFisicaDetallePersona").val("");
		//$("#direccionElectronicaDetallePersona").val("");
		$('#formaDeNotificacion').find("option[value='0']").attr("selected","selected");
	}

	/**
	*Funcion para generar los datepickers y timepickers del Grid, para
	*la funcionalidad del calendario y de la hora
	*/
	function creaDatePickers(){

		$('input[id^="fechaRecepcion_"]').each(function(){

			id = $(this).attr("id");

			//Obtenemos el id de la notificacion
			var idNotificacion = id.split("_")[1];
			//Obtenemos los datos del renglon de dicha notificacion
			var datosRenglon = jQuery("#gridDetalleNotificacionesPersona").jqGrid('getRowData',idNotificacion);

			//Obtenemos la fecha de creacion
			fechaCreacion = datosRenglon.notificacionEnviada;
			
			$("#"+id).datepicker({
				dateFormat: 'dd/mm/yy',
				yearRange: '1900:2100',
				changeMonth: true,
				changeYear: true,
				showOn: "button",
				//Set como fecha minima
				minDate: fechaCreacion,
				buttonImage: "<%= request.getContextPath()%>/resources/images/date.png",
				buttonImageOnly: true			
			});		
			
		});

		$('input[id^="horaRecepcion_"]').each(function(){

			idHour = $(this).attr("id");
			
			$('#'+idHour).timepicker({
	            onSelect: function(time, inst) {
	                $('#floating_selected_time').html('You selected ' + time);
	            }
	        });	
			
		});
	} 

	//Funcion para guardar la fecha y hora de la recepcion de la notificacion
	function confirmarFechaHoraRecepcion(idNotificacion){

		if(confirm("&iquest;La fecha y hora son correctas?")){
			
			var horaRecepcion = $('#horaRecepcion_'+idNotificacion).val();
			var fechaRecepcion = $('#fechaRecepcion_'+idNotificacion).val();
			var observaciones = $('#textArea_'+idNotificacion).val();

			if(fechaRecepcion != "" && horaRecepcion != ""){
				var parametros ='idNotificacion='+idNotificacion;
				parametros += '&fechaRecepcion='+fechaRecepcion;
				parametros += '&horaRecepcion='+horaRecepcion;
				parametros += '&estatusNotifId='+"<%=EstatusNotificacion.ATENDIDA.getValorId()%>";
				parametros += '&motivoCancelacion='+observaciones;
				
				
				actualizarNotificacion(parametros);
			}else{
				customAlert("La fecha y hora son obligatorias");
			}
		}		
	}


	//Funcion para guardar el cambio de estatus a no aplica
	//con observaciones
	function confirmarNoAplica(idNotificacion){

		if(confirm("&iquest;Esta seguro que no se ha recibido la notificacion?")){
			
			if(idNotificacion){

				var observaciones = $('#textArea_'+idNotificacion).val();
				
				if(observaciones != ""){
					var parametros ='idNotificacion='+idNotificacion;
					parametros += '&motivoCancelacion='+observaciones;
					parametros += '&estatusNotifId='+"<%=EstatusNotificacion.NO_ATENDIDA.getValorId()%>";
					actualizarNotificacion(parametros);
				}else{
					customAlert("La observacion es obligatoria");
				}
			}
		}		
	}

		


	/*
	*Funcion para actualizar los datos de una notificacion de manera generica
	*
	*Se pueden agregar los demas parametros que haga falta actualizar
	*/
	function actualizarNotificacion(parametros){
		
		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/actualizarNotificacion.do',
			data: parametros, 
			async: false,
			dataType: 'xml',
			success: function(xml){
				customAlert("La actualizaci&oacute;n se realiz&oacute; de manera correcta");
				refrescaGridsActualizaObjetoEvento();
				//Funcion para recargar la bandeja de entrada del notificador
				window.parent.seguimientoEventos(estatusConsulta);
			}
		});
	}

	
	/*
	*Refresca los grid y los datos del objeto
	*/
	function refrescaGridsActualizaObjetoEvento(){
		//volver a subir el objeto a sesi&oacute;n
		consultaDetalleEventoNotif(idEvento);
		//Recarga el grid
		$("#gridDetalleNotificaciones").trigger("reloadGrid");
		$("#gridDetalleNotificacionesPersona").trigger("reloadGrid");
		$("#gridDetalleNotificacionesInvolucrado").trigger("reloadGrid");
		$("#gridDetalleNotificacionesFuncionarioExterno").trigger("reloadGrid");
	}	
    </script>

<div id="seccionNotificaciones">

	<table align="center" cellspacing="0" cellpadding="0" border="0">
		<tr>
			<td colspan="2">&nbsp;</td>
			<td colspan="2">&nbsp;</td>
		</tr>
		<tr>
			<td colspan="4" align="center">
				<table id="gridDetalleNotificacionesFuncionarioExterno" >
						<tr>
							<td>&nbsp;</td>
						</tr>
				</table>
				<div id="pagerNotificacionesFuncionarioExterno"></div>
			</td>
		</tr>		
		<tr>
			<td colspan="4">&nbsp;</td>
		</tr>	
		<tr>
			<td colspan="4" align="center">
				<table id="gridDetalleNotificaciones" >
						<tr>
							<td>&nbsp;</td>
						</tr>
				</table>
				<div id="pagerNotificacionesFuncionarios"></div>
			</td>
		</tr>		
		<tr>
			<td colspan="4">&nbsp;</td>
		</tr>		
		<tr>
			<td colspan="4" align="center">
				<table id="gridDetalleNotificacionesInvolucrado" >
						<tr>
							<td>&nbsp;</td>
						</tr>
				</table>
				<div id="pagerNotificacionesInvolucrado"></div>
			</td>
		</tr>
		<tr>
			<td colspan="2">&nbsp;</td>
			<td colspan="2">&nbsp;</td>
		</tr>
		<tr>
			<td width="150" align="center">&nbsp;</td>
			<td width="149" align="center">
				<input type="button" value="Agregar Destinatario"
			style="display: none;" id="agregarDestinatarioBoton"
			class="btn_Generico" />
			</td>
			<td width="150" align="center">
				<!--<input type="button" value="Guardar" id="guardar" />-->
			</td>
			<td width="149" align="center">&nbsp;</td>
		</tr>
		<tr>
			<td colspan="2">&nbsp;</td>
			<td colspan="2">&nbsp;</td>
		</tr>
	</table>
			
			
			
	<div id="ventanaDetallePersona" style="display: none">
	
		<table width="800" border="0">
	
			<!--COMIENZA TR PARA LOS DATOS DE LA PERSONA-->
			<tr>
				<td align="left">
					<!--COMIENZA TABLA PARA LOS DATOS DE LA PERSONA-->
					<table width="100%" cellspacing="0" cellpadding="0" >
						<tr>
							<td colspan="3">&nbsp;</td>
						</tr>
						<tr>
							<td align="right">
								<strong>Nombre:</strong>
							</td>
							<td colspan="2">
								<input type="text" size="80" id="nombreDetallePersona" style="border: 0; background:#DDD;" readonly="readonly"/>
							</td>
						</tr>
						<tr>
							<td align="right">
								<span id="etiInstitucion"><strong>Instituci&oacute;n:</strong></span>
							</td>
							<td colspan="2">
								<input type="text" size="80" id="institucionDetallePersona" style="border: 0; background:#DDD;" readonly="readonly"/>
							</td>
						</tr>
						<tr id="trDirecionFisica">
							<td align="right">
								<!--<strong>Direcci&oacute;n F&iacute;sica:</strong>-->
							</td>
							<td colspan="2">
								<!--<input type="text" size="80" id="direccionFisicaDetallePersona" style="border: 0; background:#DDD;" readonly="readonly"/>-->
							</td>
						</tr>
						<tr id="trDirecionElectronica">
							<td align="right">
								<!--<strong>Direccion Electr&oacute;nica:</strong>-->
							</td>
							<td colspan="2">
								<!--<input type="text" size="80" id="direccionElectronicaDetallePersona" style="border: 0; background:#DDD;" readonly="readonly"/>-->
							</td>
						</tr>
						<tr>
							<td colspan="3">&nbsp;</td>
						</tr>
						<tr>
							<td colspan="3" align="center"></td>
						</tr>
					</table>	
					<!--TERMINA TABLA PARA LOS DATOS DE LA PERSONA-->
				</td>
			</tr>
			<!--TERMINA TR PARA LOS DATOS DE LA PERSONA-->
			
			
			<!--COMIENZA TR PARALA LISTA DE NOTIFICACIONES DE LA PERSONA-->
			<tr>
				<td>
					
					<table id="gridDetalleNotificacionesPersona" align="center" >                
					</table>
					<div id="pagerDetalleNotificacionesPersona"></div>
					
					<table>
						<tr>
							<td>
								<!--<input type="button" value="Generar Acta" onclick="abrirPDF()" id="botonGeneraActa" style="display: none;" disabled="disabled" class="btn_Generico">-->
							</td>
							<td>
								<!--<input type="button" value="Adjuntar Documento" id="botonAdjuntarDocumento" style="display: none;" disabled="disabled" class="btn_Generico">-->
							</td>
						</tr>
					</table>
					
				</td>
			</tr>
			<!--TERMINA TR PARALA LISTA DE NOTIFICACIONES DE LA PERSONA-->
			
			<!--COMIENZA TR PARA GENERAR NOTIFICACION-->
			<tr>
				<td>
					
					<!--COMIENZA DIV PARA GENERAR NOTIFICACION-->
					<div id="divGenerarNotificacion">
						<table width="100%" border="0">
							<tr>
								<td width="20%">
									<div align="right">
										<strong>Forma de Notificaci&oacute;n:</strong>
						        	</div>
							  	</td>
								  <td width="80%">
										<div align="left">
											<select id="formaDeNotificacion"></select>
							      		</div>
								  </td>
								<td width="0%">
								</td>
							</tr>
						</table>
					</div>
					<!--TERMINA DIV PARA GENERAR NOTIFICACION-->
					
				</td>
			</tr>
			<!--TERMINA TR PARA GENERAR NOTIFICACION-->
			
		</table>
	</div>	 	
 
 </div>