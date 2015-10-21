<%@page import="mx.gob.segob.nsjp.dto.configuracion.ConfiguracionDTO"%>
<%@page import="mx.gob.segob.nsjp.dto.configuracion.ConfInstitucionDTO"%>
<%@page import="mx.gob.segob.nsjp.dto.usuario.UsuarioDTO"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.audiencia.TipoAudiencia"%>
<%@page import="mx.gob.segob.nsjp.comun.constants.ConstantesGenerales"%>
<%@page import="java.util.Calendar"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
    
<%
    	request.getSession().removeAttribute("mesDisponibilidadSesion");

    	//Variable para obtener la institucion actual
    	String monogramaInstitucion = "";

    	UsuarioDTO usuario = (UsuarioDTO) request.getSession()
    			.getAttribute("KEY_SESSION_USUARIO_FIRMADO");
    	
    	if (usuario != null && usuario.getInstitucion() != null
    			&& usuario.getInstitucion().getClave() != null) {
    		monogramaInstitucion = usuario.getInstitucion().getClave();
    	}
    	
    	String monogramaEntFederativa = "";

    	ConfiguracionDTO configuracionDTO = (ConfiguracionDTO) request
    			.getSession().getAttribute(
    					"KEY_SESSION_CONFIGURACION_GLOBAL");
    	
    	if(configuracionDTO != null && configuracionDTO.getEntidadFederativaDespliegue() != null){
    		monogramaEntFederativa = configuracionDTO.getEntidadFederativaDespliegueMono();
    	}
    %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Atenci&oacute;n a Sollicitudes</title>

	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/jquery-ui.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/estilos.css" media="screen" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/ui-lightness/jquery-ui-1.8.11.custom.css" />
	<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/jqgrid/ui.jqgrid.css" />
	

	
	<!--css para el estilo del combo box-->
	<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/multiselect/jquery.multiselect.css" />
	<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/multiselect/style.css" />
	<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/multiselect/prettify.css" />
	
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-1.5.1.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-ui-1.8.10.custom.js"></script>
	<script type="text/javascript" 	src="<%=request.getContextPath()%>/resources/js/jqgrid/i18n/grid.locale-es.js"></script>
	<script type="text/javascript" 	src="<%=request.getContextPath()%>/resources/js/jqgrid/jquery.jqGrid.js"></script>
	
	
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/bloqueaTecla.js?n=1"></script>

	<!--css para ventanas-->

	<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/jquery.windows-engine.css" />	
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/layout_complex.css" media="screen" />
	
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.windows-engine.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.layout-1.3.0.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/layout_complex.js"></script>
	
	<!--script para el combo box multiselect-->
	<script src="<%=request.getContextPath()%>/js/prettify.js"></script>
	<script src="<%=request.getContextPath()%>/js/jquery.multiselect.js"></script>
	
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.blockUI.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/comun.js?n=1"></script>

	<script type="text/javascript">
	//Variable para obtener el contexto de la pagina
	var CONTEXT_ROOT = '<%= request.getContextPath()%>';

	var contextoPagina = "${pageContext.request.contextPath}";
	
	//mes que se muestra en la caja de texto
	var mesActual;
	//anio que se muestra en la caja de texto
	var anioActual;
	//Arreglo usado como carrusel para obtener los meses
	var meses =["Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"];
	//bandera de cambio para recorrer el carrusel de meses
	var banderaCambio=0;
	//primera consulta muestra los grids de salas
	var primeraConsulta= "true";
	//primera consulta jueces muestra los grids de jueces
	var primeraConsultaJueces= true;
	//Datos que vienen de asignarSalaTemporalPJENA.jsp
	var datosSalaTemporal="";
	//numero de salas
	var salaTemporal="false";
	//id de la audiencia
	var idAudiencia;
	var tipoAudiencia;
	var multiselect=0;
	var fechaReal="";
	//variables para la consulta de jueces
	var horaSeleccionada="";
	var minutoSeleccionado="";
	var duracionEstimadaAudiencia="";
	var idSalaSeleccionada="";
	//variable que almacena el nombre de las salas
	var nombreSalas=new Array();

	//Variable para controlar los ids de las salas
	var identiSala=new Array();
	//variable para almacenar el expediente id
	var idExpediente=0;
	//variable para controlar la edicion del Numero de expediente
	var numeroExpedienteId=0;
	
	//Variable para el tipo de expediente para la edicion del numero de exp
	var tipoExpedienteEditarNumExp = "CA";	
	var accionConsultarPdf="<%= request.getContextPath() %>/ConsultarContenidoArchivoDigital.do";
	
	$(document).ready(function() {
		//desbloquear pantalla
		jQuery(document).ajaxStop(desbloquearPantalla());
		
		//Se crean las tabs principales
		$("#tabsprincipalconsulta" ).tabs();

		//Se oculta la ceja de Intervinientes
		//$("#tabsprincipalconsulta").tabs("option", "disabled", [3,4]);
		$("#cejaIntervinientes" ).hide();
		$("#cejaTraslados" ).hide();
		
		idAudiencia=<%=request.getAttribute("idEvento")%>;
	
		if(idAudiencia == null){
			idAudiencia=<%=request.getSession().getAttribute("idEvento")%>;
			$("#tabsprincipalconsulta" ).tabs('select', 1);
		}		
		//Consultamos el detalle de la solicitud de la audiencia
		consultarDetalleSolicitudDeAudiencia(idAudiencia);

		//obtenemos la fecha actual
		mesActual='<%=(java.util.Calendar.getInstance().get(Calendar.MONTH))%>';
		anioActual='<%=(java.util.Calendar.getInstance().get(Calendar.YEAR))%>';
		var mesSeleccionado = meses[mesActual];		

		//escrbimos en pantalla la fecha actual
		$('#mes').val(mesSeleccionado);
		$('#anio').val(anioActual);

		//Le da el estilo al combo box de duracion estimada
		$("#duracionEstimadaProgramarAudiencia").multiselect({ 
			multiple: false, 
			header: "Seleccione", 
			position: { 
				my: 'top', 
				at: 'top' 
			},
			selectedList: 1 
		});


		/**
		*Funcion que recarga el grid con la funcionalidad de la agenda
		*/
		jQuery("#gridAgendaPJENA").jqGrid({ 
			url:'<%=request.getContextPath()%>/calendarioActual.do', 
			datatype: "xml", 
			colNames:['Lu','Ma','Mi','Ju','Vi','Sa','Do'], 
			colModel:[  {name:'lunes',index:'lunes', width:40, align:'center'},						
			           	{name:'martes',index:'martes', width:40, align:'center'}, 
			           	{name:'miercoles',index:'miercoles', width:40,align:'center'}, 
			           	{name:'jueves',index:'jueves', width:40, align:'center'}, 
			           	{name:'viernes',index:'viernes', width:40, align:'center'}, 
			           	{name:'sabado',index:'sabado', width:40, align:'center'}, 
			           	{name:'domingo',index:'domingo', width:40, align:'center'}, 
										
					],
			rowNum:10,
			rowList:[10,20,30],
			sortname: 'detalle',
			viewrecords: true,
			sortorder: "desc",
			cellEdit: true,
			scrollOffset:0,
			onSelectCell: function(idRow,dia,fecha) {
				seleccionCelda(idRow,dia,fecha);
				deshabilitarHabilitarComponentes("seleccionFecha");
					},	
			loadComplete: function(){
				//Cambia la bandera para avanzar de mes
				banderaCambio=0;
			} 
		});
		

		//Se llena el gird de audiencia
		jQuery("#gridSolicitudDeAudienciaImputadosPJENA").jqGrid({
			url:'<%=request.getContextPath()%>/consultarDetalleSolicitudDeAudiencia.do?tipoDeRespuesta=0&idAudiencia='+idAudiencia+'', 
			datatype: "xml", 
			colNames:['Interviniente','Calidad','Delito','Detenido'], 
			colModel:[ 	{name:'Imputado',index:'imputado', width:230, align:"center"},
			           	{name:'Calidad',index:'calidad', width:130, align:"center"},  
						{name:'Delito',index:'delito', width:200, align:"center"},
						{name:'Detenido',index:'detenido', width:55, align:"center"}
						
					],
			pager: jQuery('#pagerGridImputados'),
			rowNum:10,
			rowList:[10,20,30],
			autowidth: false,
			sortname: 'imputado',
			viewrecords: true,
			loadComplete: function(){
				rows = jQuery("#gridSolicitudDeAudienciaImputadosPJENA").jqGrid('getGridParam','records')
				if (rows<=0)
					customAlert("No hay intervinientes asociados para la audiencia");

			},
			sortorder: "desc"
		}).navGrid('#pagerGridImputados',{edit:false,add:false,del:false});

		
		/**
		*Funcion que llena el grid de testigos
		*/		
		jQuery("#gridTestigosPJA").jqGrid({ 

			url:'<%=request.getContextPath()%>/consultarTestigosParaAudiencia.do?idAudiencia='+idAudiencia+'', 
			datatype: "xml", 
			colNames:['Lista de Intervinientes para Audiencia'], 
			colModel:[ 	{name:'listaTestigos',index:'listaTestigos', width:350,align:'center'}
												
					],
			pager: jQuery('#pagerGridTestigos'),
			rowNum:10,
			rowList:[10,20,30],
			width:600,
			autowidth: false,
			sortname: 'detalle',
			viewrecords: true,
			sortorder: "desc"
		}).navGrid('#pagerGridTestigos',{edit:false,add:false,del:false});
		$("#gview_gridTestigosPJA .ui-jqgrid-bdiv").css('height', '180px');


		/**
		*Funcion que llena el grid de traslado
		*/
		jQuery("#gridTrasladoPJA").jqGrid({
		    url:'<%=request.getContextPath()%>/consultarDetalleSolicitudDeAudiencia.do?tipoDeRespuesta=2&idAudiencia='+idAudiencia+'', 
			datatype: "xml", 
			colNames:['Centro de Detenci&oacute;n','Imputado','Delito'], 
			colModel:[ 	{name:'centroDetencion',index:'centroDetencion', width:200,align:'center'},
			           	{name:'imputado',index:'imputado', width:200,align:'center'}, 
						{name:'Delito',index:'delito', width:100,align:'center'}
					],
			pager: jQuery('#pager2'),
			rowNum:10,
			rowList:[25,50,100],
			autowidth: true,
			sortname: 'detalle',
			viewrecords: true,
			sortorder: "desc"
		}).navGrid('#pager2',{edit:false,add:false,del:false});

		deshabilitarHabilitarComponentes("inicio");
		$('#txtSalaSeleccionada').val("");
		$('#txtHoraInicioSeleccionada').val("");

		//Funcion para cargar las relaciones delito persona, que debe ser
		//llamada despues de consultar el detalle de la audiencia
		consultaDelitoPersonaPorImputadosAudiencia();

		$("#txtEditarNumExpConsecutivo").focusout(rellenaCeros);
		
		
			jQuery("#gridDetalleFrmPrincipal").jqGrid({ 
				url:'<%=request.getContextPath()%>/consultarDocumentosPorTipo.do?idAudiencia='+idAudiencia, 
				datatype: "xml",
				colNames:['Tipo de documento','Nombre de Documento','Fecha del documento', 'Documento', 'Es Parcial'],
				colModel:[ 	{name:'Tipo',index:'tipo', width:155}, 
							{name:'Nombre',index:'nombre', width:255},
				           	{name:'Fecha',index:'fecha', width:170},
				           	{name:'Documento',index:'documento', width:170},
				           	{name:'EsParcial',index:'esParcial', hidden:true}
							],
				pager: jQuery('#pager1Documentos'),
				rowNum:10,
				rowList:[10,20,30,40,50,60,70,80,90,100],
				autowidth: false,
				width:1100,
				sortname: 'turno',
				viewrecords: true,
				id: 'divgrid',
				onSelectRow: function(id){
					consultaPDF(id);
					},
				sortorder: "desc"
			}).navGrid('#pager1Documentos',{edit:false,add:false,del:false});
			$("#gview_gridDetalleFrmPrincipal .ui-jqgrid-bdiv").css('height', '500px');
	});
	//FIN ON READY
	
	function consultaPDF(id){
			
			document.frmDoc.action=accionConsultarPdf+"?documentoId="+id;
			document.frmDoc.submit();
		}
	
	/**
	*Funcion que obtiene el valor de la celda seleccionada en la agenda
	*/
	function seleccionCelda(idRow,dia,fecha){
		
		//verifica si la sub string inhabil se encuentra en la cadena fecha,
		//de NO ser asi devuelve -1
		if(fecha.indexOf("inhabil")==-1){
			var fechaPos1=fecha.indexOf(">", 0);
			var fechaPos2=fecha.indexOf("<", fechaPos1);
			fechaReal="";
			fechaReal=fecha.substring(fechaPos1+1, fechaPos2);
			muestraFechaSeleccionada(fechaReal);
			controlSalas(fechaReal);
		}
		else{
			alertDinamico("Seleccione un d&iacute;a h&aacute;bil");
		}
	}

	
	/**
	*funcion que muestra en el txtField la fecha seleccionada en el calendario
	*/
	function muestraFechaSeleccionada(){

		var contadorMeses = 0;
		var mesDisponible = $('#mes').val();
		var anioDisponible = $('#anio').val(); 
				
		while(mesDisponible != meses[contadorMeses]){
			contadorMeses++;	
		}

		var diaDisponible;
		if(fechaReal < 10){
			diaDisponible= "0"+fechaReal;  
		}
		else{
			diaDisponible=fechaReal;
		}
		contadorMeses++;
		if((contadorMeses) < 10){
			//$('#fechaSeleccionadaAudiencia').val(fechaReal+"/"+"0"+contadorMeses+"/"+anioDisponible);
			contadorMeses = "0"+contadorMeses;
		}
		$('#fechaSeleccionadaAudiencia').val(diaDisponible+"/"+contadorMeses+"/"+anioDisponible);
	}

	
	var numeroDeSalas=0;
	/**
	*Funcion que controla la agenda de las salas
	*/
	function controlSalas(fechaReal){
	
		if(primeraConsulta=="true"){
			
			numeroDeSalas=obtenerNumeroDeSalas();
			insertaTablas(numeroDeSalas);
			consultaDisponibilidadSalasPorDia(fechaReal);
			gridHorarios();			
			dibujaGrids(numeroDeSalas,fechaReal);			
			primeraConsulta="false";
			
		}
		else{
			consultaDisponibilidadSalasPorDia(fechaReal);
			
			for (i=0;i<numeroDeSalas;i++){
				salaId = identiSala[i];
				jQuery("#gridSalasPJENA"+salaId).jqGrid('setGridParam', {url:'<%=request.getContextPath()%>/consultarDisponibilidadDeUnaSalaPorDia.do?posSala='+salaId+'',datatype: "xml" });
				$("#gridSalasPJENA"+salaId).trigger("reloadGrid");
			}
		}
	}

	
	/**
	*Funcion para obtener el numero de salas disponibles
	*/
	function obtenerNumeroDeSalas(){

		//numeroDeSalas=0;
		
		$.ajax({
			type: 'POST',
			url: '<%=request.getContextPath()%>/consultarNumeroDeSalas.do',
			data: '',
			dataType: 'xml',
			async: false,
			success: function(xml){
				$(xml).find('ListaSalas').find('Sala').each(function(){
					identiSala[numeroDeSalas] = $(this).find('salaAudienciaId').first().text();
					nombreSalas[numeroDeSalas] = $(this).find('nombreSala').first().text();
					numeroDeSalas++;
				});
			}
		});
		return numeroDeSalas;
	}

	
	/**
	*Funcion que inserta las tablas en HTML,donde
	*se dibujaran los grids de las salas
	*/
	function insertaTablas(numeroDeSalas){

		var indice;
		
		$('#gridsTd').append('<tr>');
		$('#gridsTd').append('<td><table width="100%" id="gridHorarios" border="0"></table></td>');
		for (indice=0;indice<numeroDeSalas;indice++){
			salaId = identiSala[indice];
			$('#gridsTd').append('<td><table width="100%" id="gridSalasPJENA'+salaId+'" border="0"></table></td>');
		}
		$('#gridsTd').append('</tr>');
	}


	/**
	*Funcion que consulta la disponibilidad de las salas por dia
	*/
	function consultaDisponibilidadSalasPorDia(fechaReal){

		var mesDisponible = $('#mes').val();
		var anioDisponible = $('#anio').val();
		
		//Recuperamos la informacion de las salas
		$.ajax({
			type: 'POST',
			url: '<%=request.getContextPath()%>/consultarDisponibilidadDeSalasPorDia.do?diaDisp='+fechaReal+'&mesDisp='+mesDisponible+'&anioDisp='+anioDisponible+'',
			data: '',
			dataType: 'xml',
			async: false,
			success: function(xml){
			}
		});	
	}

	
	/**
	*Funcion que genera el grid con la lista de horarios
	*/
	function gridHorarios(){
		
		jQuery("#gridHorarios").jqGrid({

			datatype: "xml",
			colNames:['Horario'], 
			colModel:[{name:'hora',index:'hora', width:100, align:"center"},
					],   
			pager: jQuery('#pagerHora'),
			rowNum:28,
			rowList:[10,20,30],
			autowidth: false,
			height:630,
			scrollOffset:0,
			sortname: 'hora'
		}).navGrid('#pagerHora',{edit:false,add:false,del:false});
		
		var mydata = [ 
						{hora:"09:00-09:30"},
						{hora:"09:30-10:00"},
						{hora:"10:00-10:30"},
						{hora:"10:30-11:00"},
						{hora:"11:00-11:30"},
						{hora:"11:30-12:00"},
						{hora:"12:00-12:30"},
						{hora:"12:30-13:00"},
						{hora:"13:00-13:30"},
						{hora:"13:30-14:00"},
						{hora:"14:00-14:30"},
						{hora:"14:30-15:00"},
						{hora:"15:00-15:30"},
						{hora:"15:30-16:00"},
						{hora:"16:00-16:30"},
						{hora:"16:30-17:00"},
						{hora:"17:00-17:30"},
						{hora:"17:30-18:00"},
						{hora:"18:00-18:30"},
						{hora:"18:30-19:00"},
						{hora:"19:00-19:30"},
						{hora:"19:30-20:00"},
						{hora:"20:00-20:30"},
						{hora:"20:30-21:00"},
						{hora:"21:00-21:30"},
						{hora:"21:30-22:00"},
						{hora:"22:00-22:30"},
						{hora:"22:30-23:00"}
						]; 
		for(var i=0;i<=mydata.length;i++)
			jQuery("#gridHorarios").jqGrid('addRowData',i+1,mydata[i]);

		//Quita el espacio entre grids
		$("#gbox_gridHorarios").css('width','87px');

	}


	/**
	*Funcion que dibuja los grids dentro de las tablas, por cada grid, se ejecuta una peticion
	*,para llenar cada grid se obtienen los valores de la consulta de disponibilidaDeSalasPorDia
	* y la peticion:/consultarDisponibilidadDeUnaSalaPorDia.do?posSala='+numSala+'' obtiene le 
	* informacion para cada sala en especifico
	*/	
	function dibujaGrids(numeroDeSalas,fechaReal){

		var numSala=0;

		//Dibujamos tantos grids como numero de salas
		for (numSala=0;numSala<numeroDeSalas;numSala++){
			
			salaId = identiSala[numSala];
			
			jQuery("#gridSalasPJENA"+salaId).jqGrid({
				url:'<%=request.getContextPath()%>/consultarDisponibilidadDeUnaSalaPorDia.do?posSala='+salaId+'',
				datatype: "xml",
				colNames:[nombreSalas[numSala]], 
				colModel:[
							{name:'sala',index:'sala', width:100, align:"right"},
						],   
				pager: jQuery('#pager'),
				rowNum:28,
				rowList:[10,20,30],
				autowidth: false,
				height:630,
				sortname: 'sala',
				scrollOffset:0,
				cellEdit: true,
				onSelectCell: function(idRow) {
							programaHorarioSala(idRow,fechaReal);
						},	
				loadComplete: function(){
								
					$("#ocupado1").find("td").css('border','1px solid black');
					$("#ocupado1").find("td").css('padding','0 0 0 0');
					$("#ocupado1").find("td").css('background-color','#09F');
					
				
					
					$("#ocupadoInicio").find("td").css('border-bottom','1px solid #09F');
					$("#ocupadoInicio").find("td").css('border-left','1px solid black');
					$("#ocupadoInicio").find("td").css('border-right','1px solid black');
					$("#ocupadoInicio").find("td").css('border-top','1px solid black');
					$("#ocupadoInicio").find("td").css('padding','0 0 0 0');
					$("#ocupadoInicio").find("td").css('background-color','#09F');
				
					
					$("#ocupadoIntermedio").find("td").css('border-bottom','1px solid #09F');
					$("#ocupadoIntermedio").find("td").css('border-left','1px solid black');
					$("#ocupadoIntermedio").find("td").css('border-right','1px solid black');
					$("#ocupadoIntermedio").find("td").css('border-top','1px solid #09F');
					$("#ocupadoIntermedio").find("td").css('padding','0 0 0 0');
					$("#ocupadoIntermedio").find("td").css('background-color','#09F');
					
					
					$("#ocupadoUltimo").find("td").css('border-bottom','1px solid black');
					$("#ocupadoUltimo").find("td").css('border-left','1px solid black');
					$("#ocupadoUltimo").find("td").css('border-right','1px solid black');
					$("#ocupadoUltimo").find("td").css('border-top','1px solid #09F');
					$("#ocupadoUltimo").find("td").css('padding','0 0 0 0');
					$("#ocupadoUltimo").find("td").css('background-color','#09F');				
				}				
			}).navGrid('#pager',{edit:false,add:false,del:false});

			//Quita el espacio entre grids
			$("#gbox_gridSalasPJENA"+salaId).css('width','87px');
		}
	}
/////////////////////TERMINA FUNCIONALIDAD PARA LOS GIRDS DINAMICOS DE SALAS //////////////////////////////////////////////////


/////////////////////COMIENZA FUNCIONES PARA ASIGNAR AUDIENCIA A UNA SALA //////////////////////////////////////////////////
	/**
	*Funcion que controla el horario y la sala seleccionada, descompone la string
	*para obtener los parametros del id de la sala, la hora de inicio de cada espacio
	*y el numero de medias hrs. disponibles para ese espacio libre
	*/
	
	function programaHorarioSala(idRow,fechaReal){

		
		//verifica si la sub string ocupado se encuentra en la cadena fecha
		//de NO ser asi, devulve -1
		if(idRow.indexOf("ocupado")==-1){
					
			//id de la sala
			var idSalaPos1=idRow.indexOf("+", 0);
			idSalaSeleccionada=idRow.substring(0,idSalaPos1);		
			//hora de inicio del espacio
			var horaInicioPos1=idRow.indexOf("+",0);
			var horaInicioPos2=idRow.indexOf("/",horaInicioPos1);
			var horaInicio=idRow.substring(horaInicioPos1+1,horaInicioPos2);
			

			//tama&ntilde;o en medias hrs. del espacio disponible
			var espacioPos1=idRow.indexOf("/",0);
			var espacioPos2=idRow.indexOf("*",espacioPos2);
			var espacioDisponible=idRow.substring(espacioPos1+1,espacioPos2);
			

			//hora de inicio de la celda seleccionada
			var horaSeleccionadaPos1=idRow.indexOf("*",0);
			var horaSeleccionadaPos2=idRow.indexOf(":",horaSeleccionadaPos1);
			horaSeleccionada=idRow.substring(horaSeleccionadaPos1+1,horaSeleccionadaPos2);
			

			//Minuto de inicio de la celda seleccionada
			var minutoSeleccionadoPos1=idRow.indexOf(":",0);
			var minutoSeleccionadoPos2=idRow.indexOf("#",minutoSeleccionadoPos1);
			minutoSeleccionado=idRow.substring(minutoSeleccionadoPos1+1,minutoSeleccionadoPos2);
			
			
			var disponibilidad=parseInt("espacioDisponible");
			duracionEstimadaAudiencia = $("#duracionEstimadaProgramarAudiencia option:selected").val();
			
			
			if(duracionEstimadaAudiencia > 0 ){
				if(	validaHoraAudienciaHoraActual( horaSeleccionada, minutoSeleccionado)){
					if((duracionEstimadaAudiencia/30) <= espacioDisponible){
						muestraHoraSalaSeleccionada();		
						deshabilitarHabilitarComponentes("horarioSala");
					}
					else{
						alertDinamico("La duraci&oacute;n estimada es mayor que el tiempo disponible para esta sala.\rIntente con otra sala");	
					}
				}
				else{
					alertDinamico("Esta seleccionando una hora no disponible");
				}
			}
			else{
				
				alertDinamico("Seleccione una duraci&oacute;n estimada para la audiencia");
			}
				
		}
		else{
			alertDinamico("La sala se encuentra reservada en ese horario");
		}
	}


	/**
	*funcion que muestra en el txtField correspondiente el id de la sala seleccionada
	*y la hora de inicio seleccionada
	*/
	function muestraHoraSalaSeleccionada(){

		$('#txtSalaSeleccionada').val(idSalaSeleccionada);
		$('#txtHoraInicioSeleccionada').val(horaSeleccionada+":"+minutoSeleccionado);
	}

	
	/**
	*Funcion que sonsulta la lista de jueces disponibles en base a la 
	*hora y la fecha de la audiencia
	*/
	function controlJueces(automatico){

		var mesDisponible = $('#mes').val();
		var anioDisponible = $('#anio').val();
		duracionEstimadaAudiencia = $("#duracionEstimadaProgramarAudiencia option:selected").val();
		juezSustituto =  $("#juezSustituto").is(':checked');
		
		var multiseleccion=true;
		
		if(automatico==true){
			multiseleccion=false;
		}														
		
		if(duracionEstimadaAudiencia == "" || duracionEstimadaAudiencia <= 0){
			alertDinamico("Seleccione una duraci&oacute;n estimada para la audiencia");
		}
		else{	
			if(primeraConsultaJueces==true){	 	
				//Se llena el grid de jueces
				jQuery("#gridSolicitudDeAudienciaJuecesPJENA").jqGrid({
					url:'<%=request.getContextPath()%>/consultarDisponibilidadJueces.do?audienciaId='+idAudiencia+'&diaDisp='+fechaReal+'&mesDisp='+mesDisponible+'&anioDisp='+anioDisponible+'&horaSeleccionada='+horaSeleccionada+'&minutoSeleccionado='+minutoSeleccionado+'&duracionEstimada='+duracionEstimadaAudiencia+'&automatico='+automatico+'&juezSustituto='+juezSustituto+'&tipoAudiencia='+tipoAudiencia + '&tipoDeAudienciaSolicitada=' + $("#tipoAudienciaProgramarAudiencia").val(), 
					datatype: "xml", 
					colNames:['Nombre','Carga de trabajo','Paridad',], 
					colModel:[ 	{name:'nombre',index:'nombre', width:200, align:"left"},
				    	       	{name:'carga',index:'carga', width:150, align:"center"},  
								{name:'paridad',index:'paridad', width:50, align:"center"},							
					],
					pager: jQuery('#divPagerGridSolicitudDeAudienciaJuecesPJENA'),
					rowNum:5,
					rowList:[5,10,15],
					autowidth: false,
					sortname: 'carga',
					sortorder: "desc",
					viewrecords: true,
					multiselect: true,
					loadComplete: function(){
						if(automatico== true)
							//Deshabilita el multi select
							jQuery("#gridSolicitudDeAudienciaJuecesPJENA").setGridParam({multiselect:false}).hideCol('cb');
					},
					gridComplete: function () {
						seleccionarItems($(this));
					},
					onPaging: function (a) {
						guardarItemsSeleccionados($(this)); 
					},
					onSortCol: function(){
						eliminarItemsSeleccionados($(this));
					}
				}).navGrid('#divPagerGridSolicitudDeAudienciaJuecesPJENA',{edit:false,add:false,del:false});

				primeraConsultaJueces=false;
			}
			else{
				jQuery("#gridSolicitudDeAudienciaJuecesPJENA").jqGrid('setGridParam', {url:'<%=request.getContextPath()%>/consultarDisponibilidadJueces.do?audienciaId='+idAudiencia+'&diaDisp='+fechaReal+'&mesDisp='+mesDisponible+'&anioDisp='+anioDisponible+'&horaSeleccionada='+horaSeleccionada+'&minutoSeleccionado='+minutoSeleccionado+'&duracionEstimada='+duracionEstimadaAudiencia+'&automatico='+automatico+'&juezSustituto='+juezSustituto+'&tipoAudiencia='+tipoAudiencia + '&tipoDeAudienciaSolicitada=' + $("#tipoAudienciaProgramarAudiencia").val(), datatype: "xml" });
				$("#gridSolicitudDeAudienciaJuecesPJENA").trigger("reloadGrid");
			}
			
			deshabilitarHabilitarComponentes("juez");
			verificarTipoAudiencia(automatico);
		}
	}

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

	/*
	*Funcion para obtener el id de la fila seleccionada
	*/			
	function obtenerIdFilaSeleccionada(grid){
		var id = grid.jqGrid('getGridParam','selrow');
		if(id) {
			return id;
		} else {
			return false;
		}
	}
	
	
/////////////////////TERMINAN FUNCIONES PARA ASIGNAR AUDIENCIA A UNA SALA //////////////////////////////////////////////////

	function verificarTipoAudiencia(automatico){

		//deshabilitamos el combo de tipo de audiencia
		$("#tipoAudienciaProgramarAudiencia").attr('disabled',true);
	
		//Si pulso autom&aacute;ticamente
		if(automatico == true){
			$("#gridSolicitudDeAudienciaJuecesPJENA").unbind('click');
			$("#divPagerGridSolicitudDeAudienciaJuecesPJENA").hide();
			multiselect=0;
		}
		//Si pulso manualmente
		else{
	
			if(tipoAudiencia=="<%=TipoAudiencia.JUICIO_ORAL.getValorId()%>"){
			 	if($("#juezSustituto").is(':checked')){
			 		alertDinamico("Por favor seleccione 4 jueces del listado");
					multiselect=4;
				}else{
					alertDinamico("Por favor seleccione 3 jueces del listado");
				 	multiselect=3;
				}
			}else{
				alertDinamico("Por favor seleccione un juez del listado");
				multiselect=1;
			}
		}
		
	}

///////////////////////////////////////CONTROLA LOS EVENTOS HABILITA O INHABILITA COMPONENETES//////////////////////////////////////////////////////////
	/**
	*Funcion para deshabilitar los botones de designar 
	*Juez manualmente y autom&aacute;ticamente, Guardar y Designar Sala Temporal
	*/
	function deshabilitarHabilitarComponentes(accion){

		if(accion == "inicio"){
				
			$('#btnAsignarJuezManual').attr('disabled',true);
			$('#btnAsignarJuezAuto').attr('disabled',true);
			$('#btnGuardarAudiencia').attr('disabled',true);
			$('#btnDesignar').attr('disabled',true);	
			$('#duracionEstimadaProgramarAudiencia').multiselect('disable');
		}
		if(accion == "horarioSala"){
			$('#btnAsignarJuezManual').attr('disabled',false);
			$('#btnAsignarJuezAuto').attr('disabled',false);
			//$('txtSalaSeleccionada').attr('disabled',false);
		}
		
		if(accion == "seleccionFecha"){
			$('#btnDesignar').attr('disabled',false);
			$('#btnAsignarJuezManual').attr('disabled',true);
			$('#btnAsignarJuezAuto').attr('disabled',true);
			$('#btnGuardarAudiencia').attr('disabled',true);
			$('#duracionEstimadaProgramarAudiencia').multiselect('enable');
		}

		if(accion == "designarSala"){
			$('#btnDesignar').attr('disabled',true);
			$('#btnAsignarJuezManual').attr('disabled',false);
			$('#btnAsignarJuezAuto').attr('disabled',false);	
			for (i=0;i<numeroDeSalas;i++){
				salaId = identiSala[i];
				$('#gridSalasPJENA'+salaId).unbind('click');
			}
			$('#duracionEstimadaProgramarAudiencia').multiselect('disable');
		}
		if(accion == "juez"){
			$('#btnAsignarJuezManual').attr('disabled',true);
			$('#btnAsignarJuezAuto').attr('disabled',true);
			$('#btnDesignar').attr('disabled',true);
			$("#gridAgendaPJENA").unbind('click');
			$('#duracionEstimadaProgramarAudiencia').multiselect('disable');
			$('#btnAtrasMes').attr('disabled',true);
			$('#btnAdelanteMes').attr('disabled',true);
			$('#juezSustituto').attr('disabled',true);
			for (i=0;i<numeroDeSalas;i++){
				salaId = identiSala[i];
				$('#gridSalasPJENA'+salaId).unbind('click');
			}
			$('#btnGuardarAudiencia').attr('disabled',false);	
		}
	}
////////////////////////////////////////////////////FIN CONTROL HABILITA DESHABILITA//////////////////////////////////////////////////


///////////////////////////////////////////////COMIENZA FUNCIONALIDAD SALA TEMPORAL //////////////////////////////////////////////////
	function asignarSalaTemporal(recuperaDatosAsignacionTemporal){
	
		datosSalaTemporal = recuperaDatosAsignacionTemporal;
		salaTemporal = "true";
		
		hrSelecTemp = datosSalaTemporal.split("=")[4];
		var hrSeleccionadaPos1=hrSelecTemp.indexOf("&", 0);
		hora=hrSelecTemp.substring(0,hrSeleccionadaPos1);		
		minuto = datosSalaTemporal.split("=")[5];

		horaSeleccionada="";
		horaSeleccionada= hora;
		minutoSeleccionado="";
		minutoSeleccionado= minuto;
		cerrarVentanaAsignacionTemporal();
		
		$('#txtSalaSeleccionada').val(datosSalaTemporal.split("=")[2].split("&")[0]);
		$('#txtHoraInicioSeleccionada').val(horaSeleccionada+":"+minutoSeleccionado);
		
		
		deshabilitarHabilitarComponentes("designarSala");
		$('#duracionEstimadaProgramarAudiencia').multiselect('enable');
		
	}
	
	function mostrarAsignarSalaTemporalPJENA() {
		$.newWindow({id:"iframewindowAsignarSalaTemporalAudiencia" , statusBar: true, posx:250,posy:40,width:650,height:200,title:"Designaci&oacute;n de Sala Temporal de Audiencia", type:"iframe"});
	    $.updateWindowContent("iframewindowAsignarSalaTemporalAudiencia" ,'<iframe src="<%=request.getContextPath()%>/asignarSalaTemporalPJENA.do?idEvento=idAudiencia" width="650" height="200" />');
	    $('#btnDesignar').attr('disabled',true);			
	}
	function cerrarVentanaAsignacionTemporal(){
		$.closeWindow('iframewindowAsignarSalaTemporalAudiencia');
	}
///////////////////////////////////////////////TERMINA FUNCIONALIDAD SALA TEMPORAL //////////////////////////////////////////////////



///////////////////////////////////////////////COMIENZA FUNCIONALIDAD DEL CALENDARIO //////////////////////////////////////////////////	
	/**
	*Funcion que obtiene el valor del desplazamiento para los meses "atras" o "adelante",
	*pasa el valor al action para que este ejecute el movimiento y recarga el grid de 
	*agenda 
	*/
	function atrasAdelanteMes(movimiento){
		recorridoMeses(movimiento);
		jQuery("#gridAgendaPJENA").jqGrid('setGridParam', {url:'<%=request.getContextPath()%>/calendarioActual.do?mov='+ movimiento +'',datatype: "xml" });
		$("#gridAgendaPJENA").trigger("reloadGrid");
	}

	/**
	*Funcion que recorre el carrusel de los mese y anio si la bandera de cambio se encuentra encendida
	*los meses no se recorran, hasta que el grid sea refrescado y baje la bandera 
	*/
	function recorridoMeses(movimiento){
		
		if(banderaCambio==0){
			if(movimiento=="atras"){
				
				if(mesActual == 0){
					mesActual=11;
					if(anioActual == 0)
						anioActual=3000;
					else
						anioActual--;
				}
					
				else
					mesActual--;
			}
			else{
				if(mesActual == 11){
					mesActual=0;
					anioActual++;
				}
				else
					mesActual++;
			}
			var mesSeleccionado = meses[mesActual];

			//setea los valor de mes y anio a la caja de texto
			$('#mes').val(mesSeleccionado);
			$('#anio').val(anioActual);
			//levanta la bandera de cambio
			banderaCambio=1;
		}	
	}
///////////////////////////////////////////////TERMINA FUNCIONALIDAD DEL CALENDARIO //////////////////////////////////////////////////

	/**
	*Funcion que consulta el detalle de la solicitud de audiencia
	*/
	function consultarDetalleSolicitudDeAudiencia(idAudiencia){
		if(idAudiencia != null){
			$.ajax({
				type: 'POST',
				url: '<%=request.getContextPath()%>/consultarDetalleSolicitudDeAudiencia.do?tipoDeRespuesta=1',
				data: 'idAudiencia='+ idAudiencia, 
				async: false,
				dataType: 'xml',
				success: function(xml){
					var errorCode;
					errorCode=$(xml).find('response').find('code').text();
					if(parseInt(errorCode)==0){
		  
	    				//limpiaDatosDetalleEvento();
	    				//Tab Detalle de Solicitud			
	    				$("#numCasoSolicitudAudienciaDetalle").val($(xml).find('numeroGeneralCaso').first().text());
	    				$("#numExpedienteSolicitudAudienciaDetalle").val($(xml).find('numeroExpediente').first().text());
	    				numeroExpedienteId=$(xml).find('numeroExpedienteId').first().text();
	    				editarNumeroExpedienteSolicitudAudiencia($(xml).find('numeroExpedienteId').first().text());
	    				$("#caracterSolicitudAudienciaDetalle").val($(xml).find('caracter').first().text());
	    				$("#fechaSolicitudAudienciaDetalle").val($(xml).find('strFechaCreacion').first().text());
	    				$("#fechaSolicitudAudienciaDetalle").val($(xml).find('strFechaCreacion').first().text());
	    				$("#horaSolicitudAudienciaDetalle").val($(xml).find('strHoraCreacion').first().text());
	    				$("#fechaLimiteSolicitudAudienciaDetalle").val($(xml).find('strFechaLimite').first().text());
	    				$("#horaLimiteSolicitudAudienciaDetalle").val($(xml).find('strHoraLimite').first().text());
	    				$("#audienciaSolicitudAudienciaDetalle").val($(xml).find('tipoAudiencia').find('valor').first().text());
	    				$("#institucionSolicitudAudienciaDetalle").val($(xml).find('solicitud').find('nombreInstitucionSolicitante').first().text());
	    				$("#solicitanteSolicitudAudienciaDetalle").val($(xml).find('solicitud').find('nombreSolicitanteExternoInterno').first().text());
						
	    				//Tab Programar Audiencia
	    				$("#tipoAudienciaProgramarAudiencia").val($(xml).find('tipoAudiencia').find('valor').first().text());
	    				
	    				tipoAudiencia = $(xml).find('tipoAudiencia').find('idCampo').first().text();

	    				//Se configuran variables para registrar un amparo
	    				idExpediente = $(xml).find('audienciaDTO').find('expediente').find('expedienteId').first().text();
	    				
	    				$("#numCasoProgramarAudiencia").val($(xml).find('numeroGeneralCaso').first().text());
	    				$("#numExpedienteProgramarAudiencia").val($(xml).find('numeroExpediente').first().text());
	    				$("#fechaLimiteProgramarAudiencia").val($(xml).find('strFechaLimite').first().text());
	    				

	    				//Tab Testigos
	    				$("#tipoAudienciaTestigos").val($(xml).find('tipoAudiencia').find('valor').first().text());
	    				$("#numCasoTestigos").val($(xml).find('numeroGeneralCaso').first().text());
	    				$("#numExpedienteTestigos").val($(xml).find('numeroExpediente').first().text());

	    				//Tab Traslados
	    				$("#tipoAudienciaTralados").val($(xml).find('tipoAudiencia').find('valor').first().text());
	    				$("#numCasoTraslados").val($(xml).find('numeroGeneralCaso').first().text());
	    				$("#numExpedienteTraslados").val($(xml).find('numeroExpediente').first().text());

	    					    				
	    			}
				}
			});		
		}
	}

/*
******COMIENZAN FUNCIONES PARA EDICION DE NUMERO DE EXPEDIENTE******
*/
	/*
	* Funcion para verificar si se puede editar el numero de expediente 
	*/
	function editarNumeroExpedienteSolicitudAudiencia(numeroExpedienteId){

		var parametros = "";
		parametros += '&numeroExpedienteId=' + numeroExpedienteId;

		$.ajax({
			type: 'POST',
			url: '<%=request.getContextPath()%>/editarNumeroExpedienteSolicitudAudiencia.do',
			data: parametros,
			dataType: 'xml',
			async: false,
			success: function(respuesta){
				if(parseInt($(respuesta).find('code').text())==0){
					 if($(respuesta).find('body').find('sePuedeEditar').text() == 'true'){
							$('#editarNumeroExp').show();
					 }
				 }
			}
		});	
	}

	/*
	*Funcion que abre el Dialog para editar el numero de expediente
	*/
	function abrirDialogEditarNumExp(){
		
		limpiarNumeroExpediente();
		formarNumeroExpediente();
		$("#divEditarNumeroExpediente").dialog("open");
	  	$("#divEditarNumeroExpediente").dialog({ autoOpen: true, 
		modal: true, 
	  	title: '<bean:message key="audiencia.configurar.editarNumeroExpediente"/>', 
	  	dialogClass: 'alert',
	  	position: [170,90],
	  	width: 420,
	  	height: 150,
	  	maxWidth: 420,
		maxHeight: 150,
		minWidth: 420,
		minHeight: 150,
	  	buttons:{
		  	"Aceptar":function() {

		  		if(validarNumeroExpediente()){
			  		
		  			var nuevoNumeroExpediente = obtenerNumeroExpediente();
		  			
		  			customConfirm('<bean:message key="audiencia.configurar.confirmarEditarNumeroExpediente"/>'+'<br/>'+nuevoNumeroExpediente,
		  		  			'<bean:message key="aviso"/>',
		  		  			function(){
		  		  				//aceptar
		  		  				actualizarNumeroExpediente(nuevoNumeroExpediente);
			  				});
			  	}else{
			  		customAlert("Verifique los siguientes campos:<br/> *A\u00F1o, * Clave del juzgado", '<bean:message key="aviso"/>');
				}
		  	},
			"Cancelar" : function() {
				customConfirm('<bean:message key="audiencia.configurar.cancelarEditarNumeroExpediente"/>',
	  		  			'<bean:message key="aviso"/>',
	  		  			function(){
		  		  			//aceptar
	  		  				$("#divEditarNumeroExpediente").dialog("close");
		  				});
			}
		  }
		});
	}


	/*
	*Funcion que valida que el numero de expediente este
	*correctamente formado, regresa true, en caso de ser correcto
	*false, en caso contrario
	*/
	function validarNumeroExpediente(){
		
		var anio = $("#txtEditarNumExpAnio").val();		
		var cveJuzgado	= $("#txtEditarNumExpCveJuzgado").val();

		//Solo validamos que el anio conste de cuatro digitos
		if(anio.length < 4){
			return false; 
		}
		
		//Solo validamos que la clave del Juzgado conste de tres digitos
		if(cveJuzgado.length < 3){
			return false; 
		}

		return true;
	}

	
	/*
	*Funcion que concatena los inputs, ingresados por el usuario
	*para formar el numero de expediente
	*/
	function obtenerNumeroExpediente(){

		var consecutivo = $("#txtEditarNumExpConsecutivo").val();
		var area 		= $("#txtEditarNumExpArea").val();
		var anio 		= $("#txtEditarNumExpAnio").val();
		var institucion = $("#txtEditarNumExpInstitucion").val();
		var entidadFed	= $("#txtEditarNumExpEntidadFed").val();
		var cveJuzgado	= $("#txtEditarNumExpCveJuzgado").val();

		var nuevoNumeroExpediente =  consecutivo +"/"+area+"/"+anio+"-"+institucion+"-"+entidadFed+"-"+cveJuzgado;
		
		return nuevoNumeroExpediente;
	}

	/*
	*Funcion que al abrir el poppoup de para editar el numero de 
	*expediente limpia los campos
	*/
	function limpiarNumeroExpediente(){
		$("#txtEditarNumExpConsecutivo").val("");
		$("#txtEditarNumExpArea").val("");
		$("#txtEditarNumExpAnio").val("");
		$("#txtEditarNumExpInstitucion").val("");
		$("#txtEditarNumExpEntidadFed").val("");
		$("#txtEditarNumExpCveJuzgado").val("");
	}

	/*
	*Funcion para editar los campos del numero de expediente
	*que el usuario no puede editar:
	*	El tipo de expediente
	*	El monograma de la institucion
	*	El monograma de la entidad federativa
	*/
	function formarNumeroExpediente(){
		
		$("#txtEditarNumExpArea").val(tipoExpedienteEditarNumExp);
		$("#txtEditarNumExpInstitucion").val('<%=monogramaInstitucion%>');
		$("#txtEditarNumExpEntidadFed").val('<%=monogramaEntFederativa%>');
	}
	

	/*
	*Actualizar numero expediente en vista
	*/
	function actualizarVistaNumeroExpediente(nuevoNumeroExpediente){
		$("#numExpedienteSolicitudAudienciaDetalle").val(nuevoNumeroExpediente);
		$("#numExpedienteProgramarAudiencia").val(nuevoNumeroExpediente);
	}

	
	/*
	*Funcion para actualizar el numero de expediente
	*/
	function actualizarNumeroExpediente(nuevoNumeroExpediente){
		
		var parametros = "";
		parametros += '&numeroExpedienteId=' + numeroExpedienteId;
		parametros += '&nuevoNumeroExpediente=' + nuevoNumeroExpediente;

		$.ajax({
			type: 'POST',
			url: '<%=request.getContextPath()%>/actualizarNumeroExpedienteSolicitudAudiencia.do',
			data: parametros,
			dataType: 'xml',
			async: false,
			success: function(respuesta){
				if(parseInt($(respuesta).find('code').text())==0){
					if($(respuesta).find('body').find('seActualizo').text() == 'true'){
						actualizarVistaNumeroExpediente(nuevoNumeroExpediente);
						customAlert('<bean:message key="audiencia.configurar.confirmarAcualizoNumeroExpediente"/>','<bean:message key="aviso"/>');
						$("#divEditarNumeroExpediente").dialog("close");
						if (typeof window.parent.recargarGridAudienciaPJENA == 'function' ){
							window.parent.recargarGridAudienciaPJENA();
						}
					}else{
						customAlert('<bean:message key="audiencia.configurar.denegarAcualizoNumeroExpediente"/>','<bean:message key="aviso"/>');
					}
				 }else{
					 var codigoError=$(respuesta).find('code').text();
					 var mensageError='<bean:message key="error.general"/>';
					 
					 if(codigoError == '<%=CodigoError.PARAMETROS_INSUFICIENTES.toString()%>'){
						 mensageError += 'ocurrio un error, por favor contacte a su administrador';
					 }else if(codigoError == '<%=CodigoError.INFORMACION_PARAMETROS_ERRONEA.toString()%>'){
							mensageError += 'Ocurrio un error, por favor contacte a su administrador';
					 }else if(codigoError == '<%=CodigoError.MISMO_NUMERO_DE_EXPEDIENTE.toString()%>'){
							mensage += 'mismo n&uacute;mero de expediente';
					 }else if(codigoError == '<%=CodigoError.YA_EXISTE_EL_NUMERO_EXPEDIENTE.toString()%>'){
							mensageError += 'ya existe el n&uacute;mero de expediente';
					 }
					 customAlert(mensageError,'<bean:message key="error"/>');
					 
					
				}
			}
		});		
	}

	
	/*
	*Funcion que rellena con ceros los espacios en blanco del numero de expediente
	*en la parte de consecutivo
	*/
	function rellenaCeros(){
		
		var consecutivo = $("#txtEditarNumExpConsecutivo").val();
		var lon = consecutivo.length;
		
		if(parseInt(lon) < 5){
			while(parseInt(lon) < 5){
				consecutivo = "0"+consecutivo;
				lon = consecutivo.length;
			}
			$("#txtEditarNumExpConsecutivo").val("");
			$("#txtEditarNumExpConsecutivo").val(consecutivo);
		}		
	}	
/*
******TERMINAN FUNCIONES PARA EDICION DE NUMERO DE EXPEDIENTE******
*/
	
	var tieneMasSolicitudes ="";
	var solRepetidas =0;
	
	function guardarAudiencia(){
		
		var valorIdes = obtenerSeleccionados($("#gridSolicitudDeAudienciaJuecesPJENA"));
		
		var valor = valorIdes.length;
		
		if(valor == multiselect){
			//bloquear pantalla
			bloquearPantalla(true, "Programando Audiencia");  
			
			var parametrosGuardado='';
			  
			parametrosGuardado += 'idAudiencia='+ idAudiencia;
			parametrosGuardado += '&anioEvento=' + $('#anio').val();
			parametrosGuardado += '&mesEvento=' + $('#mes').val();
			parametrosGuardado += '&diaEvento=' + fechaReal;
			parametrosGuardado += '&horaEvento=' + horaSeleccionada;
			parametrosGuardado += '&minutoEvento=' + minutoSeleccionado;
			parametrosGuardado += '&duracionEstimada=' + duracionEstimadaAudiencia;
			parametrosGuardado += '&idSala=' + idSalaSeleccionada;
			parametrosGuardado += '&salaTemporal=' + salaTemporal;
			parametrosGuardado += '&tipoDeAudienciaSeleccionada=' + tipoAudiencia;
			parametrosGuardado += '&numCasoSolicitudAudienciaDetalle=' + escape(jQuery("#numCasoSolicitudAudienciaDetalle").val());
			//Si la asignacion de jueces fue automatica
			if(multiselect == 0){
				parametrosGuardado += '&idJueces=' + jQuery("#gridSolicitudDeAudienciaJuecesPJENA").getDataIDs();
			}
			else{
				parametrosGuardado += '&idJueces=' + obtenerSeleccionados($("#gridSolicitudDeAudienciaJuecesPJENA"));
				//parametrosGuardado += '&idJueces=' + jQuery("#gridSolicitudDeAudienciaJuecesPJENA").jqGrid('getGridParam','selarrrow');	
			}
			parametrosGuardado += '&'+datosSalaTemporal;

			$.ajax({
				type: 'POST',
				url: '<%=request.getContextPath()%>/guardarAudiencia.do',
				data: parametrosGuardado,
				dataType: 'xml',
				success: function(xml){
					var mensaje = $(xml).find('body').find('fechaActual').text();
					
					if(mensaje == "fail"){
						alertDinamico("La audiencia ya fue programada anteriormente por alg&uacute;n otro usuario");
						/*
						*recargamos el grid de audiencias para que la solicitud de audiencia que se abr&iacute;o ya no 
						*aparesca en la bandeja
						*/
						parent.recargarGridAudienciaPJENA();
						
					} else if(mensaje == "failNoObs"){
						alertDinamico("Error al intentar agendar la audiencia");							
					} else if(mensaje == "salaOcupada") {
						alertDinamico("Alg&uacute;n otro usuario acaba de programar una audiencia en esta sala y horario");
						try{
							parent.recargarGridAudienciaPJENA();
							setTimeout("recargaVisor()",3000);
						}catch(e){}
					} else {
						/*
						*Guardamos la programaci&oacute;n de la audiencia en las agendas de los Jueces
						*/
						guardarEventoAudiencia();
						/*
						*recargamos el grid de audiencias para que la audiencia que se acaba de
						*programar, no se muestre mas 
						*/
						parent.recargarGridAudienciaPJENA();
						//deshabilitamos el boton de guardado
						$('#btnGuardarAudiencia').attr('disabled',true);
						//Refrescamos el grid de las salas
						controlSalas(fechaReal);

					//********SE COMENTA FUNCIONALIDAD DE JAVS********/
					
						//var razones = mensaje.split(",");
						//if(razones.lenth==0){
						//	idEvento=0;
						//}
						//else{
						//	idEvento=razones[0];
						//	if(razones.length>=2){
						//		tieneMasSolicitudes="TieneMasSolicitudes";
						//		solRepetidas=razones[1];
						//	}
						//}
						
						//if(idEvento=="<%=ConstantesGenerales.NO_ES_JAVS%>"){
						//	customAlert(
						//			"La programaci&oacute;n de la audiencia se realiz&oacute; de manera correcta", 
						//			"Programaci&oacute;n de Audiencias", 
						//			cancelarSolicitudesRepetidas									
						//	);
						// Con agendar audiencia en JAVS							
						//}else{
						//	var S_Mensaje;
						//	S_Mensaje=mensajeEstadoJAVS(idEvento);
						//	if(S_Mensaje!=""){
						//		alertDinamicoCerrar(S_Mensaje,1);	
						//	}
						//	else{
						//		alertDinamicoCerrar(S_Mensaje,2);
						//	}
						//}
						//********TERMINA FUNCIONALIDAD DE JAVS********/
						
						customAlert("La programaci&oacute;n de la audiencia se realiz&oacute; de manera correcta");
					}
					//Desbloquear pantalla
					desbloquearPantalla();	
				}
			});			
		}else{

			if(multiselect==1){
				alertDinamico('Seleccione exactamente un juez del listado');
			}
			else{
				alertDinamico('Seleccione exactamente '+multiselect+' jueces del listado');
			}
			
		}

	}		

	//Funci&oacute;n para alertDinamicoCerrar
	function alertDinamicoCerrar(textoAlert, accion){			
		if(accion==2){
			//textoAlert="Error al agendar audiencia en servidor JAVS, no hay conexi&oacute;n con el WS.</br>No se agend&oacute; en el Sistema.";
			textoAlert="Ocurri&oacute; un error al agendar audiencia.</br>No se agend&oacute; en el Sistema.";
		}
		$("#divAlertTextoCerrar").html(textoAlert);
	    $( "#dialog-AlertCerrar" ).dialog({
			autoOpen: true,
			resizable: false,
			modal: true,
			buttons: {								
				"Aceptar": function() {
					if(accion==1){
						cancelarSolicitudesRepetidas();
					}
					$( this ).dialog( "close" );
					$("#divAlertTextoCerrar").html("");					
				}				
			}
		});    
	 }		

	function mensajeEstadoJAVS(idEvento){
		var S_Mensaje="";
		switch (idEvento){
            case "<%=ConstantesGenerales.ERROR_AGENDAR_JAVS%>":
                S_Mensaje = "Error al agendar audiencia en el Sistema y el Servidor JAVS";
                break;
            case "<%=ConstantesGenerales.EXITO_AGENDAR_JAVS%>":
                S_Mensaje = "Audiencia agendada correctamente en el Sistema y el Servidor JAVS";
                break;
            case "<%=ConstantesGenerales.EXITO_AGENDAR_JAVS_SIN_ARCH_OUT%>":
            	S_Mensaje = "Audiencia agendada correctamente en el Sistema y el Servidor JAVS";
                //S_Mensaje = "Audiencia agendada correctamente en servidor JAVS, error al crear archivo de salida";
                break;
            case "<%=ConstantesGenerales.EXITO_AGENDAR_JAVS_SIN_ARCH_OUT%>":
            	S_Mensaje = "Audiencia agendada correctamente en el Sistema y el Servidor JAVS";
                //S_Mensaje = "Audiencia agendada correctamente en servidor JAVS, error al crear archivo de recepci&oacute;n";
                break;
            case "<%=ConstantesGenerales.EXITO_AGENDAR_JAVS_SIN_ARCH_INOUT%>":
            	S_Mensaje = "Audiencia agendada correctamente en el Sistema y el Servidor JAVS";
                //S_Mensaje = "Audiencia agendada correctamente en servidor JAVS, error al crear archivos de verificaci&oacute;n";
                break;
            case "<%=ConstantesGenerales.ERROR_PARAMETROS_CONEXION%>":
                S_Mensaje = "Error al agendar audiencia en servidor JAVS, par&aacute;metros de conexi&oacute;n incorrectos.</br>No se agend&oacute; en el Sistema.";
                break;
            case "<%=ConstantesGenerales.ERROR_ELEMENTOS_INSUFICIENTES%>":
                S_Mensaje = "Error al agendar audiencia en servidor JAVS, no hay elementos suficientes en la audiencia.</br>No se agend&oacute; en el Sistema.";
                break;
            case "<%=ConstantesGenerales.ERROR_CREDENCIALES%>":
                S_Mensaje = "Error al agendar audiencia en servidor JAVS, credenciales de conexi&oacute;n incorrectas.</br>No se agend&oacute; en el Sistema.";
                break;
            case "<%=ConstantesGenerales.FALLO_CONEXION_WEB_SERVICE_JAVS%>":
                S_Mensaje = "Error al agendar audiencia en servidor JAVS, no hay conexi&oacute;n con el WS.</br>No se agend&oacute; en el Sistema.";
                break;
        }		
        return S_Mensaje;
	}
	
	function cancelarSolicitudesRepetidas(){
		if (tieneMasSolicitudes == "TieneMasSolicitudes"){
							
			var mensaje =  " El caso <strong>" + jQuery("#numCasoSolicitudAudienciaDetalle").val() + "</strong> <br /> ";
				mensaje += " tiene <strong>" + solRepetidas + "</strong> solicitudes de audiencias del tipo <strong>" + jQuery("#tipoAudienciaProgramarAudiencia").val() + "</strong><br />"; 
				mensaje += " &iquest;Desea cancelarlas?";
				customConfirm(
					mensaje, 
					"Solicitudes De Audiencia Repetidas",
					function (){				
						var	parametrosGuardado = 'tipoDeAudienciaSeleccionada=' + tipoAudiencia;
						parametrosGuardado += '&numCasoSolicitudAudienciaDetalle=' + escape(jQuery("#numCasoSolicitudAudienciaDetalle").val());
						
						$.ajax({
							type: 'POST',
							url: '<%=request.getContextPath()%>/cancelarSolicitudesDeAudiencia.do',
							data: parametrosGuardado,
							dataType: 'xml',
							async: false,
							success: function(xml){
								var mensaje = $(xml).find('body').find('fechaActual').text();
								
								if(mensaje == "fail") {
									customAlert("No se ha podido cancencelar las solicitudes<br /> por favor intente m&aacute;s tarde", "Error");
								} else {
								
									parent.recargarGridAudienciaPJENA();
									customAlert(
											"Se han cancelado las solicitudes.", 
											"Programaci&oacute;n de Audiencias"
									);
								}	
							}
						});				
					}
				);
		}
	}	


	/*Funcion que permite refrescar la ventana y redirigirla de nuevo*/
	function recargaVisor(){
			document.frmRecargaVisor.idAudiencia.value = idAudiencia;
			document.frmRecargaVisor.idVisor.value = 1;
			document.frmRecargaVisor.submit();
	}



	/*
	*Funcion que valida, que la hora y minuto seleccionados sean mayores o iguales
	*a la hora del servidor, para programar la audiencia, regresa true, en caso
	*verdadero, false caso contrario.
	*/
    function validaHoraAudienciaHoraActual(horaSelec, minutoSelec){

		var fecha = "";
		
		$.ajax({
			type: 'POST',
			url: '<%=request.getContextPath()%>/regresaFechaYHoraDelServidor.do',
			dataType : 'xml',
			async : false,
			success : function(xml) {
				fecha = $(xml).find('fecha').text();
			}
		});
		
		//mes y anio seleccionados
		var mesSelec = $('#mes').val();
		var anioSelec = $('#anio').val();
		
		//hora, minuto, dia, mes y anio actuales
		var horaC = fecha.substring(11, 13);
		var minutoC = fecha.substring(14,16);
		var dia = fecha.substring(8, 10);
		var mesNum = fecha.substring(5,7); 
		 
		var mes = meses[ parseInt(mesNum,10) - 1];
		var anio = fecha.substring(0, 4);
		
		if (parseInt(fechaReal,10) == parseInt(dia,10) && mesSelec == mes
				&& parseInt(anioSelec,10) == parseInt(anio,10)) {
			if (parseInt(horaSelec,10) > parseInt(horaC,10)) {
				return true;
			}
			else{
				if(parseInt(horaSelec,10) == parseInt(horaC,10)){
					if(parseInt(minutoSelec,10)  >= parseInt(minutoC,10)){
						return true;
					}
					else{
						return false;
					}
				}
				else{
					return false;
				}
			}
		} 
		else {
			return true;
		}
	}
	
	
	/*
	* Funcion que guarda el evento en la agenda de los funcionarios Jueces
	*/
	function guardarEventoAudiencia(){
		
		var parametros='';

		//Si la asignacion de jueces fue automatica
		if(multiselect == 0){
			parametros += '&idJueces=' + jQuery("#gridSolicitudDeAudienciaJuecesPJENA").getDataIDs();
		}
		else{
			parametros += '&idJueces=' + obtenerSeleccionados($("#gridSolicitudDeAudienciaJuecesPJENA"));
			//parametros += '&idJueces=' + jQuery("#gridSolicitudDeAudienciaJuecesPJENA").jqGrid('getGridParam','selarrrow');	
		}
		
	    parametros += '&diaEvento=' + $('#fechaSeleccionadaAudiencia').val().split("/")[0];
		parametros += '&mesEvento=' + $('#fechaSeleccionadaAudiencia').val().split("/")[1];
		parametros += '&anioEvento=' + $('#fechaSeleccionadaAudiencia').val().split("/")[2];
		parametros += '&duracionEstimada=' + duracionEstimadaAudiencia;
		parametros += '&horaEvento=' + $('#txtHoraInicioSeleccionada').val();
		parametros += '&idSala=' + $('#txtSalaSeleccionada').val();
		parametros += '&tipoDeAudienciaSolicitada=' + $("#tipoAudienciaProgramarAudiencia").val();

		$.ajax({
			type: 'POST',
			url: '<%=request.getContextPath()%>/guardarEventoAudiencia.do',
			data: parametros,
			dataType: 'xml',
			async: false,
			success: function(xml){
				var mensaje = $(xml).find('body').find('fechaActual').text();
				
				if(mensaje == "fallo"){
					alertDinamico("No se logr&oacute; guardar el evento en la agenda del juez");
				}	
			}
		});	
	}
	
	function documentos(){
		 jQuery("#gridDetalleFrmPrincipal").jqGrid('setGridParam',  
					{url:'<%= request.getContextPath()%>/consultarDocumentosPorTipo.do?idAudiencia='+idAudiencia, 
					datatype: "xml" ,
					success: function(xml){
						alertDinamico('<%=request.getSession().getAttribute(
				"totalRegistrosDocumentos")%>');	 
					 } 
					});
				 $("#gridDetalleFrmPrincipal").trigger("reloadGrid"); 
	}
	
	</script>
</head>


<body>

	<div id="tabsprincipalconsulta">
		<ul>
			<li><a href="#tabsconsultaprincipal-1">Detalle de Solicitud</a>
			</li>
			<li><a href="#tabsconsultaprincipal-5">Relaciones Delito-Persona</a>
			</li>
			<li><a href="#tabsconsultaprincipal-2">Programar Audiencia</a>
			</li>
			<!-- Se comenta el tab por el momento, ya que no tiene funcionalidad desarrollada -->
			<li id="cejaIntervinientes"><a href="#tabsconsultaprincipal-3">Intervinientes</a>
			</li>
			<li id="cejaTraslados"><a href="#tabsconsultaprincipal-4">Traslados</a>
			</li>
			<li class="tabTabsDocs"><a href="#tabs-11" onclick="documentos()" id="tabDocumentos">Documentos</a></li>

		</ul>
		
		<div id="tabsconsultaprincipal-1" align="left">
		
		<!--<table width="1060px" height="367px" border="0" class="back_obj" cellpadding="0" cellspacing="5">-->
			<table width="1060px" height="367px" border="0" cellpadding="0" cellspacing="5">
				<tr>
					<td colspan="4">&nbsp;</td>
				</tr>
				<tr> 
					<td width="16%" align="right"><strong>N&uacute;mero de Caso:</strong>
					</td>
					<td width="24%"><input type="text"
						id="numCasoSolicitudAudienciaDetalle"
						style="width: 200px; border: 0; background: #DDD;"
						readonly="readonly" />
					</td>
					<td width="7%"><strong>Audiencia Solicitada:</strong>
					</td>
					<td width="29%"><input type="text"
						id="audienciaSolicitudAudienciaDetalle"
						style="width: 350px; border: 0; background: #DDD;"
						readonly="readonly" />
					</td>
				</tr>
				<tr>
					<td align="right">
						<strong>
							<bean:message key="numeroDeCausa"/>:
						</strong>
					</td>
					<td>
						<input type="text" id="numExpedienteSolicitudAudienciaDetalle"
							style="width: 200px; border: 0; background: #DDD;"
							readonly="readonly" />
						<input type="button" id="editarNumeroExp"
							value='<bean:message key="audiencia.configurar.editarNumeroExpediente"/>'
							style="display: none" onclick="abrirDialogEditarNumExp()" class="btn_Generico"/>
					</td>
					<td colspan="2" rowspan="8" align="left"><table
							id="gridSolicitudDeAudienciaImputadosPJENA"></table>
						<div id="pagerGridImputados" style="width: 300"></div>
					</td>
				</tr>
				<tr>
					<td align="right"><strong>Car&aacute;cter: </strong>
					</td>
					<td><input type="text" id="caracterSolicitudAudienciaDetalle"
						style="width: 180px; border: 0; background: #DDD;"
						readonly="readonly" />
					</td>
				</tr>
				<tr>
					<td align="right"><strong>Fecha de Solicitud:</strong>
					</td>
					<td><input type="text" id="fechaSolicitudAudienciaDetalle"
						style="width: 180px; border: 0; background: #DDD;"
						readonly="readonly" />
					</td>
				</tr>
				<tr>
					<td align="right"><strong>Hora de Solicitud:</strong>
					</td>
					<td><input type="text" id="horaSolicitudAudienciaDetalle"
						style="width: 180px; border: 0; background: #DDD;"
						readonly="readonly" />
					</td>
				</tr>
				<tr>
					<td align="right"><strong>Instituci&oacute;n Solicitante:</strong>
					</td>
					<td><input type="text" id="institucionSolicitudAudienciaDetalle"
						style="width: 180px; border: 0; background: #DDD;"
						readonly="readonly" />
					</td>
				</tr>
				<tr>
					<td align="right"><strong>Solicitante:</strong>
					</td>
					<td><input type="text" id="solicitanteSolicitudAudienciaDetalle"
						style="width: 180px; border: 0; background: #DDD;"
						readonly="readonly" />
					</td>
				</tr>
				<tr>
					<td align="right"><strong>Fecha Limite de Audiencia:</strong>
					</td>
					<td><input type="text" id="fechaLimiteSolicitudAudienciaDetalle"
						style="width: 180px; border: 0; background: #DDD;"
						readonly="readonly" />
					</td>
				</tr>
				<tr>
					<td align="right"><strong>Hora Limite de Audiencia:</strong>
					</td>
					<td><input type="text" id="horaLimiteSolicitudAudienciaDetalle"
						style="width: 180px; border: 0; background: #DDD;"
						readonly="readonly" />
					</td>
				</tr>
				<tr>
					<td align="right">&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
			</table>



		</div>
		
		<div id="tabsconsultaprincipal-2" align="left">
		
			<table width="1180" border="0" cellspacing="0" cellpadding="0">
			  <tr>
			    <td width="258">&nbsp;</td>
			    <td width="178">&nbsp;</td>
			   
			    <td colspan="3" align="center" valign="bottom"><input type="button" value="<<" id="btnAtrasMes" onClick="atrasAdelanteMes('atras');" class="btn_Generico"/>
			      <input type="text" id="mes" disabled="disabled" style="width: 70px"/>
			      <input type="text" id="anio" disabled="disabled" style="width: 70px" />
			    <input type="button" value=">>" id="btnAdelanteMes" onClick="atrasAdelanteMes('adelante');" class="btn_Generico"/></td>
			    <td width="100" nowrap="nowrap"><strong>Designar Juez:</strong> 
			    	
			    </td>
			    <td colspan="2">
			    	
			    	<table width="100%" cellpadding="1" cellspacing="1">
			    		<tr>
			    			<td colspan="2">
			    				<input type="checkbox" value="true" name="juezSustituto" id="juezSustituto"/> Juez sustituto
			    			</td> 
			    		</tr>
			    		<tr>
			    			<td width="111"><input type="button" value="Manualmente" id="btnAsignarJuezManual" onclick="controlJueces(false);" class="btn_Generico"/></td>
			    			<td width="125"><input type="button" value="Autom&aacute;ticamente" id="btnAsignarJuezAuto" onclick="controlJueces(true);" class="btn_Generico"/></td>
			    		</tr>
			    	</table>
			    
			    </td>
			    
			  </tr>
			  <tr>
			    <td align="right" valign="bottom"><strong>Tipo de audiencia solicitada:</strong></td>
			    <td><input type="text" id="tipoAudienciaProgramarAudiencia" style="width: 200px; border: 0; background: #DDD;" readonly="readonly" /></td>
			    <td colspan="3" rowspan="7" align="center" valign="bottom"><table id="gridAgendaPJENA"></table></td>
			    <td colspan="3" rowspan="7" >
			    	<table id="gridSolicitudDeAudienciaJuecesPJENA"></table>
			    	<div id="divPagerGridSolicitudDeAudienciaJuecesPJENA"></div>
			    </td>
			  </tr>
			  <tr>
			    <td align="right" valign="bottom"><strong>N&uacute;mero de caso:</strong></td>
			    <td align="left" valign="bottom"><input type="text"
			                        id="numCasoProgramarAudiencia"
			                        style="width: 200px; border: 0; background: #DDD;"
			                        readonly="readonly" /></td>
			  </tr>
			  <tr>
			    <td align="right" valign="bottom"><strong>N&uacute;mero de
			    Causa:</strong></td>
			    <td align="left" valign="bottom"><input type="text"
			                        id="numExpedienteProgramarAudiencia"
			                        style="width: 200px; border: 0; background: #DDD;"
			                        readonly="readonly" /></td>
			  </tr>
			  <tr>
			    <td align="right" valign="bottom"><strong>Fecha l&iacute;mite de audiencia:</strong></td>
			    <td align="left" valign="bottom"><input type="text"
			                        id="fechaLimiteProgramarAudiencia"
			                        style="width: 200px; border: 0; background: #DDD;"
			                        readonly="readonly" /></td>
			  </tr>
			  <tr>
			    <td align="right" valign="bottom"><strong>Fecha Seleccionada:</strong></td>
			    <td align="left" valign="bottom"><input type="text"
			                        id="fechaSeleccionadaAudiencia"
			                        style="width: 200px; border: 0; background: #DDD;"
			                        readonly="readonly" /></td>
			  </tr>
			  <tr>
			    <td align="right" valign="bottom"><strong>Duraci&oacute;n estimada de audiencia:</strong></td>
			    <td align="left" valign="bottom">
			     <select id="duracionEstimadaProgramarAudiencia" style="width:200px;">
                	<option value="0" >-Seleccione-</option>
                	<option value="30">30 min.</option>
                	<option value="60">1 hr. </option>
                	<option value="90">1 hr. 30 min.</option>
                	<option value="120">2 hrs.</option>
                	<option value="150">2 hrs. 30 min.</option>
                	<option value="180">3 hrs.</option>
                	<option value="210">3 hrs. 30 min.</option>
                	<option value="240">4 hrs.</option>
                	<option value="270">4 hrs. 30 min.</option>
                	<option value="300">5 hrs.</option>
                	<option value="330">5 hrs. 30 min</option>
                	<option value="360">6 hrs.</option>
                	<option value="420">7 hrs.</option>
                	<option value="480">8 hrs.</option>
                	<option value="540">9 hrs.</option>
                	<option value="600">10 hrs.</option>
                	<option value="660">11 hrs.</option>
                	<option value="720">12 hrs.</option>
                	<option value="780">13 hrs.</option>
                	<option value="840">14 hrs.</option>
            	</select>
			    <!--<input type="text" id="duracionEstimadaProgramarAudiencia" style="width: 170px; border: 0; background: #DDD;" readonly="readonly" />-->
			    </td>
			  </tr>
			  <tr>
			    <td align="right"><strong>Sala seleccionada:</strong></td>
			    <td><input type="text"
			                        id="txtSalaSeleccionada"
			                        style="width: 200px; border: 0; background: #DDD;"
			                        readonly="readonly" /></td>
			  </tr>
			  <tr>
			    <td height="30" align="right"><strong>Hora de inicio:</strong></td>
			    <td><input type="text"
			                        id="txtHoraInicioSeleccionada"
			                        style="width: 200px; border: 0; background: #DDD;"
			                        readonly="readonly" /></td>
			    <td width="102">
			    	<strong><input type="text" size="4" style="width: 20px; border: 0; background: #669933;" readonly />
			    	</strong>Disponible </td>
			    <td width="123">
			    	<strong><input type="text" size="4" style="width: 20px; border: 0; background: red;" readonly />
			   		</strong>No Disponible
			    </td>
			    <td width="85">
			    	<input type="text" size="4" style="width: 20px; border: 0; background: #CCCCCC;" readonly />Inh&aacute;bil </td>
			    <td colspan="3">&nbsp;</td>
			  </tr>
			  <tr>
			    <td colspan="8">
			    	<table id="gridsTd" width="100px" border="0"></table>
			    </td>
			  </tr>
			  <tr>
			    <td>&nbsp;</td>
			    <td>&nbsp;</td>
			    <td colspan="3">&nbsp;</td>
			    <td colspan="3">&nbsp;</td>
			  </tr>
			  <tr>
			    <td>&nbsp;</td>
			    <td>&nbsp;</td>
			    <td colspan="3" align="center"><input type="button" class="btn_guardar" id="btnGuardarAudiencia" value="Guardar"  onclick="guardarAudiencia();" class="btn_Generico"/></td>
			    <td colspan="3" align="left"><input type="button" class="btn_mediano" id="btnDesignar" onclick="mostrarAsignarSalaTemporalPJENA();" value="Designar Sala Temporal"  class="btn_Generico"/></td>
			  </tr>
			</table>
			
														
		</div>
		
		
		<div id="tabsconsultaprincipal-3" align="left">
			<table width="700" height="355" class="back_bienvenido" border="0" cellpadding="0"
				cellspacing="5">
				<tr>
					<td colspan="4" align="center"><strong>Tipo de Audiencia
							Solicitada:</strong> <input type="text"
						id="tipoAudienciaTestigos"
						style="width: 180px; border: 0; background: #DDD;"
						readonly="readonly" />
					</td>
				</tr>
				<tr>
					<td colspan="4" align="center">&nbsp;</td>
				</tr>
				<tr>
					<td width="25%" align="right"><strong>N&uacute;mero de Caso:</strong>
					</td>
					<td width="27%"><input type="text"
						id="numCasoTestigos"
						style="width: 200px; border: 0; background: #DDD;"
						readonly="readonly" />
					</td>
					<td width="30%" align="right"><strong>N&uacute;mero de
							Causa:</strong>
					</td>
					<td width="25%"><input type="text"
						id="numExpedienteTestigos"
						style="width: 180px; border: 0; background: #DDD;"
						readonly="readonly" />
					</td>
				</tr>
				<tr>
					<td colspan="4" align="right">&nbsp;</td>
				</tr>
				<tr>
					<td colspan="4" align="center">
						<table id="gridTestigosPJA"></table>
						<div id="pagerGridTestigos" style="width: 300"></div>
					</td>
				</tr>
				<tr>
					<td colspan="4" align="right">&nbsp;</td>
				</tr>
			</table>
		</div>
	
		<div id="tabsconsultaprincipal-4" align="left">
			<table width="720" border="0" cellpadding="0" cellspacing="5">
				<tr>
					<td colspan="4" align="center"><strong>Audiencia
							Solicitada:</strong> <input type="text"
						id="tipoAudienciaTralados"
						style="width: 180px; border: 0; background: #DDD;"
						readonly="readonly" />
					</td>
				</tr>
				<tr>
					<td colspan="4" align="center">&nbsp;</td>
				</tr>
				<tr>
					<td width="18%" align="right"><strong>N&uacute;mero de Caso:</strong>
					</td>
					<td width="27%"><input type="text"
						id="numCasoTraslados"
						style="width: 200px; border: 0; background: #DDD;"
						readonly="readonly" />
					</td>
					<td width="30%" align="right"><strong>N&uacute;mero de
							Causa:</strong>
					</td>
					<td width="25%"><input type="text"
						id="numExpedienteTraslados"
						style="width: 180px; border: 0; background: #DDD;"
						readonly="readonly" />
					</td>
				</tr>
				<tr>
					<td colspan="4" align="right">&nbsp;</td>
				</tr>
				<tr>
					<td colspan="4" align="right"><table id="gridTrasladoPJA"></table>
						<div id="pager2" style="width=300" ></div>&nbsp;</td>
				</tr>
				<tr>
					<td colspan="4" align="right">&nbsp;</td>
				</tr>
				<tr>
				  <td colspan="2" align="center"><input type="button"
						value="Enviar" id="trasladoImputado"  class="btn_Generico"/> 
				  </td>
	                   <td colspan="2" align="center"><input type="button"
						value="Cancelar" id="trasladoImputado" class="btn_Generico" /> 
					</td>
				</tr>
				<tr>
					<td colspan="4" align="right">&nbsp;</td>
				</tr>
		</table>
	</div>
	
	<div id="tabs-11" class="tabTabsDocs">
		<br>
		<table id="gridDetalleFrmPrincipal"></table>
		<div id="pager1Documentos"></div>
		<form name="frmDoc" action="<%= request.getContextPath() %>/ConsultarContenidoArchivoDigital.do" method="post">
		</form>
	</div>
	
	<div id="tabsconsultaprincipal-5" align="left">
		<div id="tabRelacionesDelitoPersona">
			<jsp:include page="/WEB-INF/paginas/relacionDelitoPersonaIncludePJ.jsp" flush="true"></jsp:include>
		</div>
	</div>

</div>

	<!--Div para popup de traslados-->
	<div id="poponTraslados" style="display: none;">
		<table width="745" border="0">
			<tr>
	 			<td colspan="2" align="right">Nombre de la Persona a Trasladar:</td>
	 			<td colspan="2">&nbsp;&nbsp; <input name="textfield7" type="text" disabled="disabled" id="textfield7" /></td>
			</tr>
			<tr align="right">
			  <td width="187">Lugar de Origen:</td>
			  <td width="176"><label>
			    <input name="textfield" type="text" disabled="disabled" id="textfield" />
			  </label></td>
			  <td width="183">Lugar Destino:</td>
			  <td width="181"><input name="textfield2" type="text" disabled="disabled" id="textfield2" /></td>
			</tr>
			<tr align="right">
			  <td>Funcionario que Autoriza:</td>
			  <td><input name="textfield3" type="text" disabled="disabled" id="textfield3" /></td>
			  <td>Fecha de Presentacion:</td>
			  <td><input name="textfield4" type="text" disabled="disabled" id="textfield4" /></td>
			</tr>
			<tr align="right">
			  <td>Hora de Presentacion:</td>
			  <td><input name="textfield5" type="text" disabled="disabled" id="textfield5" /></td>
			  <td>Duracion de Presentacion:</td>
			  <td><input name="textfield6" type="text" disabled="disabled" id="textfield6" /></td>
			</tr>
		</table>
	</div>
	
	<!-- div para el alert dinamico -->
	<div id="dialog-Alert" style="display: none">
		<table align="center">
			<tr>
				<td align="center">
					<span id="divAlertTexto"></span>
				</td>
			</tr>
		</table>	
	</div>
	
	<!-- div para el alert dinamico antes de cerrar ventana -->
	<div id="dialog-AlertCerrar" style="display: none">
		<table align="center">
			<tr>
	        	<td align="center">
    	        	<span id="divAlertTextoCerrar"></span>
            	</td>
        	</tr>
     	</table>              
	</div>    	
	
	<form id="frmRecargaVisor" 
		  name="frmRecargaVisor" action="<%=request.getContextPath()%>/recargaVisor.do"
		  method="post" enctype="multipart/form-data">
		  
		<input type="hidden" name="idAudiencia" /> 
		<input type="hidden" name="idVisor" />
	</form>
<!-- Termina div, para agregar funcionario externo -->

<!-- Comienza div, para Editar Numero Expediente -->
	<div id="divEditarNumeroExpediente" style="display: none">
		<table width="400" cellspacing="0" cellpadding="0" border="0">
			<tr>
				<td>
					<bean:message key="audiencia.configurar.leyendaEditarNumeroExpediente"/>
				</td>
			</tr>
			<tr>
				<td colspan="4">
					<input type="text" tabindex="1" id="txtEditarNumExpConsecutivo" size="5" maxlength="5" onKeyPress="return solonumeros(event);" onblur="validaSoloNumeros(this);"/>
					<span>
						<strong>/</strong>
					</span>
						<input type="text" id="txtEditarNumExpArea" size="2" maxlength="2"  readonly="readonly" style="border: 0; background: #DDD;"/>
					<span>
						<strong>/</strong>
					</span>
					<input type="text" tabindex="2" id="txtEditarNumExpAnio" size="4" maxlength="4" onKeyPress="return solonumeros(event);" onblur="validaSoloNumeros(this);"/>
					<span>
						<strong>-</strong>
					</span>
					<input type="text" id="txtEditarNumExpInstitucion" size="2" maxlength="2"  readonly="readonly" style="border: 0; background: #DDD;"/>
					<span>
						<strong>-</strong>
					</span>
					<input type="text" id="txtEditarNumExpEntidadFed" size="6" maxlength="6"  readonly="readonly" style="border: 0; background: #DDD;"/>
					<span>
						<strong>-</strong>
					</span>
					<input type="text" tabindex="3" id="txtEditarNumExpCveJuzgado" size="3" maxlength="3" onKeyPress="return teclasAlfanumericas(event);" onblur="return soloLestrasYNumeros(this);" />
				</td>
			</tr>
		</table>
	</div>
</body>
</html>