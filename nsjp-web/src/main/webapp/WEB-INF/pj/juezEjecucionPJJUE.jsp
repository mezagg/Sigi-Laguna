<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="mx.gob.segob.nsjp.web.reinsercionsocial.action.DatosGeneralesReinsercionAction"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.solicitud.EstatusSolicitud"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.solicitud.TiposSolicitudes"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.expediente.EstatusExpediente"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.involucrado.SituacionJuridica"%>
<%@page import="mx.gob.segob.nsjp.web.base.action.GenericAction"%>
<%@page import="java.util.Date"%>
<%@page import="mx.gob.segob.nsjp.web.login.action.LoginAction"%>
<%@page import="mx.gob.segob.nsjp.dto.configuracion.ConfiguracionDTO"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@page import="mx.gob.segob.nsjp.dto.usuario.RolDTO"%>
<%@page import="mx.gob.segob.nsjp.dto.usuario.UsuarioDTO"%>

<script type="text/javascript">
	var cargaPrimeraExpPorEstatus = true;
	var contextoPagina = "${pageContext.request.contextPath}";
	var primeraGridExpedientesDocumentoPJATP = true;
	var cargaPrimeraSentenciasDictadas = true;
	var cargaPrimeraSolsGeneradas = true;
	var global=0;
	var estatusNuevas = "";
	var fechaini  = "";
	var fechafin  = "";
	var pp = "penaPrivativa";
	
	var estatusPorAtender  = "<%=EstatusSolicitud.ABIERTA.getValorId()%>";
	var estatusAtendidas  = "<%=EstatusSolicitud.CERRADA.getValorId()%>";
	var estatusAbierto = "<%=EstatusExpediente.ABIERTO.getValorId()%>";
	var estatusEnviado = "<%=EstatusExpediente.ENVIADO.getValorId()%>";
	var estatusRechazado = "<%=EstatusExpediente.RECHAZADO.getValorId()%>";
	var estatusSolicitudAbiertoYCerrado = estatusPorAtender+','+estatusAtendidas;
	var situacionSentenciado = "<%=SituacionJuridica.SENTENCIADO.getValorId()%>";
	
	var idTipoSolicitudDGPRS = '<%=TiposSolicitudes.INFORMACION_DGPRS.getValorId()%>';
	var idTipoAvisoDGPRS = '<%=TiposSolicitudes.AVISO_DGPRS.getValorId()%>';
	
	var pa ="penaAlternativa";
		
	$(document).ready(function() {
				
		var dates =	$("#buscaporfechaIni, #buscaporfechaFin").datepicker(
			{
				numberOfMonths: 1,
				changeMonth: true,
				changeYear: true,
				yearRange: new Date().getFullYear() + ":+200",
	    		showOn: "both",
				onSelect: function( selectedDate ) {
					var option = this.id == "buscaporfechaIni" ? "minDate" : "maxDate",
					instance = $( this ).data( "datepicker" ),
					date = $.datepicker.parseDate(
					instance.settings.dateFormat ||
					$.datepicker._defaults.dateFormat,
					selectedDate, instance.settings );
					dates.not( this ).datepicker( "option", option, date );									
				},
				showOn: "button",
				buttonImage: "<%= request.getContextPath()%>/resources/images/date.png",
				buttonImageOnly: true			
			}
		);
		
		
			
		/*
		*Funcion que carga el grid, por default con las audiencias del dia
		*/
		jQuery("#gridAudienciasPJJU").jqGrid({ 
			url:'<%= request.getContextPath()%>/consultarAudienciaDelDiaPJENS.do',
			datatype: "xml", 
			colNames:['N&uacute;mero de Caso','N&uacute;mero de Causa','Car&aacute;cter', 'Tipo de Audiencia','Fecha de Audiencia','Hora de Audiencia','Sala'], 
			colModel:[ 	{name:'numeroCaso',index:'numeroCaso', width:70},
						{name:'expediente',index:'expediente', width:70}, 
						{name:'caracter',index:'caracter', width:25}, 
						{name:'tipoAudiencia',index:'tipoAudiencia', width:40},
						{name:'fechaAudiencia',index:'fechaAudiencia', width:50}, 
						{name:'horaAudiencia',index:'horaAudiencia', width:30},
						{name:'sala',index:'sala', width:50}
					],
			pager: jQuery('#pager1'),
			rowNum:10,
			rowList:[10,20,30],
			autowidth: true,
			sortname: 'expediente',
			viewrecords: true,
			sortorder: "desc",
			ondblClickRow: function(rowid) {
						mostrarVisorAudienciaJuezPJJU(rowid);
					}
		}).navGrid('#pager1',{edit:false,add:false,del:false});	
				
		var ambiente='<%=((ConfiguracionDTO) request.getSession().getAttribute(GenericAction.KEY_SESSION_CONFIGURACION_GLOBAL)).getAmbiente()%>';
		if(ambiente == undefined || ambiente == "undefined"){
			ambiente = "";
		}
		$("#ambienteLb").html('<strong><big>'+ambiente+'</big></strong>');
		
		
		cargaGridExpedientesPorEstatus(estatusPorAtender, fechaini, fechafin, pp);		
 		restablecerPantallas();
		
 		cargaGridSentenciasDictadas(situacionSentenciado);
		
	});
	//Fin OnReady

	/*
	*Funcion que abre el visor de audiencias
	*(Por el momento no acarrea el ID solo abre el visor) 
	*/
	function mostrarVisorAudienciaJuezPJJU(rowID){
		
		var newVentana = "iframewindowVisorAudienciaJuez" + global;
		
		$.newWindow({id:newVentana, statusBar: true, posx:200,posy:50,width:1140,height:450,title:"Visor de Audiencia", type:"iframe"});
		$.updateWindowContent(newVentana,'<iframe src="<%=request.getContextPath()%>/visorAudienciaPJENS.do?idEvento='+rowID+'"width="1140" height="450" />');
		
    	global = global + 1;
	}
	
	function muestraGridAudiencias(){
		recargaGrid();
	}	

	function restablecerPantallas(){
	 	ajustarGridAlCentro($("#gridAudienciasPJJU"));
	 	ajustarGridAlCentro($("#gridExpedientesDocumentoPJATP"));
	 	ajustarGridAlCentro($("#gridExpedientesPorEstatus"));
	}

    /*
	*Funcion que llama a la funcionalidad para recargar el grid 
	*/
	function recargaGrid(){
		
		$("#gridAudienciasPJJU").trigger("reloadGrid");
		
		$('#divGridExpedientesDocumentoPJATP').hide();
	 	$("#divGridExpedientePorEstatus").hide();
		$('#divGridAudienciasPJJU').show();
		$("#divGridSentenciasDictadas").hide();
		$("#divGridSolsGeneradas").hide();
	}

	/*
	*Funcion que realiza las validaciones para la carga el grid de consulta por fechas y expedientes
	*/
	function controlCargaGridExpedientes(){

		var fechaIni = $("#buscaporfechaIni").val();
		var fechaFin = $("#buscaporfechaFin").val();
		var numeroExpedienteId =$("#buscapornumexp").val();

		if(numeroExpedienteId == null || numeroExpedienteId == "" || numeroExpedienteId == "undefined"){

			var validacion = validaCamposFecha(fechaIni,fechaFin);
			
			if(validacion == true){
				cargaGridExpedientes(fechaIni, fechaFin, numeroExpedienteId);
			}			
		}else{
			
			cargaGridExpedientes(fechaIni, fechaFin, numeroExpedienteId);
		}
	}	


	/*
	*Funcion que carga el grid de consulta por fechas y numero de expediente
	*/
	function cargaGridExpedientes(fechaIni, fechaFin, numeroExpedienteId){
												  
		if(primeraGridExpedientesDocumentoPJATP == true){

			jQuery("#gridExpedientesDocumentoPJATP").jqGrid({ 
				url:'<%= request.getContextPath() %>/buscarExpedientePorNumeroDeExpedienteParaDocumentos.do?fechaIni='+fechaIni+'&fechaFin='+fechaFin+'&numeroExpedienteId='+numeroExpedienteId+'', 
				datatype: "xml",
				mtype: 'POST', 
				colNames:['<bean:message key="numeroDeCausa" />',
							'<bean:message key="numeroDeCaso" />',
							'<bean:message key="fechaDeCreacion" />',
							'<bean:message key="fechaDeModificacion" />',
							'<bean:message key="documentos" />',
				], 
				colModel:[ 	
							{name:'expediente',index:'numeroCaso',width:70,align:'center'},
							{name:'numeroCaso',index:'expediente',width:70,align:'center'}, 
							{name:'caracter',index:'caracter',width:25,align:'center'},
							//se oculta porque no se muestran datos debido a la bitacora 
							{name:'tipoAudiencia',index:'tipoAudiencia', width:40,hidden:true},
							{name:'documento',index:'documento', width:40,hidden:true}
						],
				pager: jQuery('#paginadorFechaPJATP'),
				rowNum:10,
				rowList:[10,20,30],
				width:830,
				height:410,
				viewrecords: true,
				ondblClickRow: function(rowid) {
					var ret2 = jQuery("#gridExpedientesDocumentoPJATP").jqGrid('getRowData',rowid);
					numCausa= ret2.expediente;
					numCaso = ret2.numeroCaso;
					dblClickRowvisorDocumentosExpediente(rowid);
				},
				sortorder: "desc"
			});
			$("#gview_GridExpedientesDocumentoPJATP .ui-jqgrid-bdiv").css('height', '450px');
			//cambia la bandera a false para que solo ejecute el reload
		  	primeraGridExpedientesDocumentoPJATP = false;
		}
		else{
			jQuery("#gridExpedientesDocumentoPJATP").jqGrid('setGridParam',{url:'<%= request.getContextPath() %>/buscarExpedientePorNumeroDeExpedienteParaDocumentos.do?fechaIni='+fechaIni+'&fechaFin='+fechaFin+'&numeroExpedienteId='+numeroExpedienteId+'',datatype:"xml" });
			$("#gridExpedientesDocumentoPJATP").trigger("reloadGrid");				  
		}

	 	$("#divGridExpedientePorEstatus").hide();
		$('#divGridExpedientesDocumentoPJATP').show();
		$('#divGridAudienciasPJJU').hide();
		$("#divGridSentenciasDictadas").hide();
		$("#divGridSolsGeneradas").hide();
	}	

	
	/*
	*Funcion para desplegar el poppoup de tipo de busqueda
	*/
	function  popupTipoBusqueda(tipo){
	
		var titulo="";
		
		$("#buscapornumexp").val("");  
		$("#buscaporfechaIni").val("");
		$("#buscaporfechaFin").val("");
		
		if(tipo=="expediente"){
			$("#tiposBusquedaExpediente").css("display","block");
			$("#tiposBusquedafecha").css("display","none");
			titulo="Buscar por número de causa";
		}else{
			$("#tiposBusquedaExpediente").css("display","none");
			$("#tiposBusquedafecha").css("display","block");
			titulo="Buscar causa por fecha";
		}
		  
		$( "#tiposBusquedaExpedienteid" ).dialog({
			title:titulo, 
			autoOpen: true,
			resizable: false,
			modal: true,
			height:'auto',
			width:'auto',
			buttons: {
				"Aceptar": function() {
					controlCargaGridExpedientes();
					$( this ).dialog( "close" );
				},
				"Cancelar": function() {
					$( this ).dialog( "close" );
					
				}
			}
		});	
	}
	

	/*
	*Funcion que crea el visor de  audiencia
	*/		
	function dblClickRowvisorDocumentosExpediente(idRow){
		$.newWindow({id:"iframewindowVisorEncargadoAdmonAudiencias", statusBar: true, posx:50,posy:111,width:1200,height:700,title:"Numero de Causa:"+numCausa+" "+"Numero de Caso:"+numCaso, type:"iframe"});
	    $.updateWindowContent("iframewindowVisorEncargadoAdmonAudiencias",'<iframe src="<%=request.getContextPath()%>/visorDocumentos.do?numExpedienteId='+idRow+'" width="1200" height="700" />'); 
	}

	function cargaRolNuevo(rolNuevo){
		document.frmRol2.rolname.value = rolNuevo;
		document.frmRol2.submit();
	
	}

	/*
	*Funcion que consulta los expedientes de acuerdo a su estatus y la fecha inicial y final
	*/
	
	function cargaGridExpedientesPorEstatus(estatus,fechaIni,fechaFin){
		var datosPeticion = {};
		if (estatus == estatusAbierto
				|| estatus == estatusEnviado
				|| estatus == estatusRechazado){
			datosPeticion = {estatusExpediente: estatus, estatusSolicitud: "0"};
		}else{
			datosPeticion = {estatusExpediente: "0", estatusSolicitud: estatus};
		}
		if(cargaPrimeraExpPorEstatus == true){
			jQuery("#gridExpedientesPorEstatus").jqGrid({ 
				url:'<%=request.getContextPath()%>/obtenerSentenciasGrid.do',
				postData: datosPeticion,
				datatype: "xml", 
	    		mtype: "POST",
				colNames:['Número De Caso','Número De Causa', 'Carpeta Ejecución', 'Nombre Sentenciado', 'Delito(s)', 'Fecha Cambio de Estatus', 'NumExpId'], 
				colModel:[ 	{name:'noCaso',index:'1', width:140}, 
							{name:'noCausa',index:'2', width:70}, 
							{name:'carpeta',index:'3', width:140, hidden:true}, 
							{name:'nombreSentenciado',index:'4', width:140},
							{name:'delitos',index:'5', width:140, cellattr: function (rowId, tv, rawObject, cm, rdata) { return 'style="white-space: normal;"'; }},
							{name:'fechaCreacion',index:'6', width:70},
							{name:'numExpId',index:'7', width:70, hidden:true}
						],
				pager: jQuery('#pagerGridExpedientesPorEstatus'),
				rowNum:10,
				rowList:[10,20,30,40,50,60,70,80,90,100],
				autowidth: true,
				height:360,
				sortname: 'Sentencia_id',
				caption:"Sentencias",
				autowidth:true,
				shrinkToFit:true,
				viewrecords: true,
				onSelectRow: function(id){
	
					},
				ondblClickRow: function(id) {
					consultarDatosGenerales(id);
				},
				sortorder: "asc"
			}).navGrid('#pagerGridExpedientesPorEstatus',{edit:false,add:false,del:false});
		
			cargaPrimeraExpPorEstatus = false;
		}else{
			if (estatus == estatusAbierto
					|| estatus == estatusEnviado
					|| estatus == estatusRechazado){
				jQuery("#gridExpedientesPorEstatus").jqGrid(
					'setGridParam', {
						url:'<%=request.getContextPath()%>/obtenerSentenciasGrid.do',
						postData: {
							estatusExpediente: estatus,
							estatusSolicitud: "0"
						},
						datatype: "xml"
					}
					
				);
			}else{
				jQuery("#gridExpedientesPorEstatus").jqGrid(
					'setGridParam', {
						url:'<%=request.getContextPath()%>/obtenerSentenciasGrid.do',
						postData: {
							estatusExpediente: "0",
							estatusSolicitud: estatus
						},
						datatype: "xml" 
					}
				);			
			}
			$("#gridExpedientesPorEstatus").trigger("reloadGrid");
		}
	
		 	$("#divGridExpedientePorEstatus").show();
			$("#divGridExpedientesDocumentoPJATP").hide();
			$("#divGridAudienciasPJJU").hide();
			$("#divGridSentenciasDictadas").hide();
			$("#divGridSolsGeneradas").hide();
	
	}
	
	function cargaGridSentenciasDictadas(situacionJuridica){
		if(cargaPrimeraSentenciasDictadas == true){
			jQuery("#gridSentenciasDictadas").jqGrid({ 
				url:'<%=request.getContextPath()%>/llenarGridSentenciasDictadas.do',
				postData: {
					situacionJuridicaId: situacionJuridica
				},
				datatype: "xml", 
	    		mtype: "POST",
				colNames:['N&uacute;mero De Caso','N&uacute;mero De Causa', 'Nombre Sentenciado', 'Delito(s)', 'Fecha Cambio de Estatus'], 
				colModel:[ 	{name:'noCaso',index:'1', width:200, sortable:false}, 
							{name:'noCausa',index:'2', width:160, sortable:false}, 
							{name:'nombreSentenciado',index:'3', width:140, sortable:false},
							{name:'delitos',index:'4', width:165, sortable:false, cellattr: function (rowId, tv, rawObject, cm, rdata) { return 'style="white-space: normal;"'; }}, 
							{name:'fechaCambio',index:'5', width:160, sortable:false}
						],
				pager: jQuery('#pagerGridSentenciasDictadas'),
				rowNum:10,
				rowList:[10,20,30],
				autowidth: true,
				height:395,
				sortname: 'noCaso',
				caption:"Sentencias Dictadas",
				shrinkToFit:true,
				viewrecords: true,
				ondblClickRow: function(id) {
					ingresarSentenciaEjecutoriada(id);
				},
				sortorder: "asc"
			}).navGrid('#pagerGridSentenciasDictadas',{edit:false,add:false,del:false});
		
			cargaPrimeraSentenciasDictadas = false;
		}else{
			jQuery("#gridSentenciasDictadas").jqGrid(
				'setGridParam', {
					url:'<%=request.getContextPath()%>/llenarGridSentenciasDictadas.do',
					postData: {
						situacionJuridicaId: situacionJuridica
					},
					datatype: "xml" 
				}
			);
			$("#gridSentenciasDictadas").trigger("reloadGrid");
		}
	
		$("#divGridSentenciasDictadas").show(); 	
		$("#divGridExpedientePorEstatus").hide();
		$("#divGridExpedientesDocumentoPJATP").hide();
		$("#divGridAudienciasPJJU").hide();
		$("#divGridSolsGeneradas").hide();
	
	}
	
	function cargaGridSolsGeneradas(tipoSolicitud, estatus) {
		if(cargaPrimeraSolsGeneradas == true){
			jQuery("#gridSolsGeneradas").jqGrid({ 
				url:"<%= request.getContextPath()%>/consultaSolsGeneradasPorSentencia.do",
				mType: "POST",
				postData :{
					tipoSolicitud : tipoSolicitud,
					estatus 	: estatus
				},
				datatype: "xml",
				colNames:['Procedimiento Ejecuci&oacute;n','Folio', 'Estatus','Sentenciado','Delito(s)','Fecha Creaci&oacute;n'], 
				colModel:[ 	{name:'procEjecucion',index:'procEjecucion', width:180, align:'center'}, 
							{name:'folio',index:'folio', width:100,align:'center'}, 
							{name:'estatus',index:'estatus', width:100,align:'center'}, 
							{name:'sentenciado',index:'sentenciado', width:160,align:'center', cellattr: function (rowId, tv, rawObject, cm, rdata) { return 'style="white-space: normal;"'; } },
							{name:'delito',index:'delito', width:160,align:'center', cellattr: function (rowId, tv, rawObject, cm, rdata) { return 'style="white-space: normal;"'; } },
							{name:'fechaCreacion',index:'fechaCreacion', width:100,align:'center'}
						],
				pager: jQuery("#pagerGridSolsGeneradas"),
				rowNum:10,
				rowList:[10,20,30,40,50,60,70,80,90,100],
				height: 435,
				autowidth: true,
				sortname: 'fechaCreacion',
				viewrecords: true,
				sortorder: "desc"
			}).navGrid("#pagerGridSolsGeneradas",{edit:false,add:false,del:false});
			cargaPrimeraSolsGeneradas = false;
		}else{
			jQuery("#gridSolsGeneradas").jqGrid('setGridParam', { 
					url:"<%= request.getContextPath()%>/consultaSolsGeneradasPorSentencia.do",
					mType: "POST",
					postData :{
						tipoSolicitud : tipoSolicitud,
						estatus : estatus
					},
					datatype: "xml"
				}
			);		
			$("#gridSolsGeneradas").trigger("reloadGrid");
		}
		$("#divGridSentenciasDictadas").hide(); 	
		$("#divGridExpedientePorEstatus").hide();
		$("#divGridExpedientesDocumentoPJATP").hide();
		$("#divGridAudienciasPJJU").hide();
		$("#divGridSolsGeneradas").show();
	}
	
	function consultarDatosGenerales(id){
		customVentana(	"idVentanaDatosGenerales"+id, 
						"Visor General de la Carpeta de Ejecuci&oacute;n", 
						"/consultarDatosGeneralesRS.do", 
						"?sentenciaId="+ id +
						"&enviarA=<%=DatosGeneralesReinsercionAction.SENTENCIAS_JUEZ_EJECUCION%>",
						function (){ 
							$("#gridExpedientesPorEstatus").trigger("reloadGrid");
						}
					);
	}
	
	function ingresarSentenciaEjecutoriada(id){
		customVentana(	"idIngresarSentenciaEjecutoriada"+id, 
						"Cambio de Estatus de Sentencia", 
						"/consultarDatosSentenciaDictada.do", 
						"?involucradoId="+ id,
						function (){ 
							$("#gridSentenciasDictadas").trigger("reloadGrid");
						}
					);
	}
	</script>
</head>

<body>


	<!--Comienza main content-->
	<div id="mainContent">
		<div class="ui-layout-center">
			<div class="ui-layout-content">
				<div class="ui-layout-north">
					<div id="divGridAudienciasPJJU">
						<table id="gridAudienciasPJJU" ></table>
						<div id="pager1"></div>
					</div>					
					<div id="divGridExpedientesDocumentoPJATP">
						<table id="gridExpedientesDocumentoPJATP"></table>
						<div id="paginadorFechaPJATP"></div>
					</div>
					<div id="divGridExpedientePorEstatus">
						<table id="gridExpedientesPorEstatus"></table>
						<div id="pagerGridExpedientesPorEstatus"
							style="vertical-align: top;"></div>
					</div>
					<div id="divGridSentenciasDictadas">
						<table id="gridSentenciasDictadas"></table>
						<div id="pagerGridSentenciasDictadas"
							style="vertical-align: top;"></div>
					</div>
					<div id="divGridSolsGeneradas">
						<table id="gridSolsGeneradas"></table>
						<div id="pagerGridSolsGeneradas"
							style="vertical-align: top;"></div>
					</div>
				</div>	
			</div>
		</div>
	</div>
	<!-- dialogos para Bloqueo de pantalla-->
	<div id="dialog-bloqueo" title="Bloqueo Sesi&oacute;n"  style="display: none;">
		<p align="center">
			<table border="0">
				<tr>
					<td colspan="2">La sesi&oacute;n se ha bloqueado, introduce tu contraseña para desbloquear.</td>
					
				</tr>
				<tr>
					<td align="right"><label style="color:#4A5C68">Contraseña:</label></td>
					<td><input type="password" name="password" id="password" value="" maxlength="15" size="20"></td>
				</tr>
				<tr id="captchaJPG" >
	            	<td align="right">
	                	<label style="color:#4A5C68">Captcha:</label>
                    </td>
	                <td>
	                	<img id="imgcaptcha" src="<%=request.getContextPath()%>/kaptcha.jpg">
	                </td>
	            </tr>
	            <tr id="captchaTXT" >
	            	<td align="right">
	                	<label style="color:#4A5C68">Captcha:</label>
	             	</td>
	                <td>
	                   	<input type="text" id="scaptcha" name="scaptcha" value="" maxlength="15" size="20">
	                   	<input type="hidden" name="captcha" value='<%= request.getAttribute("captcha")%>'>
	                </td>
	            </tr>
			</table>
		</p>
	</div>
	
	<!-- Cuadros de dialogo para buscar expedientes por numero de expediente y por fecha -->
	<div id="tiposBusquedaExpedienteid"  style="display: none;"> 
	
		<div id="tiposBusquedaExpediente"  style="display: none;"> 
			<table cellspacing="0" cellpadding="0" border="0">
				<tr>
					<td width="120">
						<bean:message key="numeroDeCausa"/>
					</td>
					<td width="153">
						<input type="text" id="buscapornumexp" size="28" maxlength="32"/>
					</td>
				</tr>
			</table>
		</div>
		
		<div id="tiposBusquedafecha"  style="display: none;">
		
			<table cellspacing="0" cellpadding="0" >
				<tr>
					<td width="153">&nbsp;</td>
					<td width="153">&nbsp;</td>
				</tr>
				<tr>
				  <td colspan="2" align="center">
				  	<strong>Fecha Inicio:</strong>
				  	<input type="text" id="buscaporfechaIni" size="15" readonly="readonly"/>
				  </td>
			    </tr>
				<tr>
				  <td align="center">&nbsp;</td>
				  <td align="center">&nbsp;</td>
		  		</tr>
				<tr>
				  <td colspan="2" align="center">
				  	<strong>Fecha Fin:&nbsp;&nbsp;</strong>&nbsp;
			      	<input type="text" id="buscaporfechaFin" size="15" readonly="readonly"/>
			      </td>
		  		</tr>
				<tr>
				  <td align="center">&nbsp;</td>
				  <td align="center">&nbsp;</td>
		  		</tr>
			</table>
			
		</div>

	</div>
</body>
</html>

