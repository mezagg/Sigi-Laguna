<%@page import="mx.gob.segob.nsjp.comun.enums.expediente.EstatusExpediente"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ page import="mx.gob.segob.nsjp.dto.configuracion.ConfiguracionDTO"%>
<%@ page import="mx.gob.segob.nsjp.web.base.action.GenericAction"%>	
<%@ page import="mx.gob.segob.nsjp.dto.usuario.UsuarioDTO"%>
<%@ page import="mx.gob.segob.nsjp.comun.enums.seguridad.Roles"%>
<%@ page import="mx.gob.segob.nsjp.comun.enums.solicitud.TiposSolicitudes"%>
<%@ page import="mx.gob.segob.nsjp.comun.enums.solicitud.EstatusSolicitud"%>


<%
  	UsuarioDTO usuario = (UsuarioDTO) request.getSession()
  			.getAttribute("KEY_SESSION_USUARIO_FIRMADO");
  	String rolUsuarioSesion = "";
  	Long idRolActivo = 0L;
  	if (usuario != null && usuario.getRolActivo() != null) {
  		rolUsuarioSesion = usuario.getRolActivo();
  		idRolActivo = usuario.getRolACtivo().getRol().getRolId();
  	}
%>
        
 <script>
 
 var idRolActivo = <%=idRolActivo%>;
 
/////////////////////////////////////////////////////////////////////////////////////
//////////						ASESORIAS 									/////////
/////////////////////////////////////////////////////////////////////////////////////
	
	/**
	* Funcion que abre la ventana para registrar una asesoria legal
	*/
	//MOD defensorATE
	function registrarAsesoriaLegal() {
		
		$.ajax({
			type: 'POST',
			url: '<%=request.getContextPath()%>/registrarSolicitudAsesoriaLegal.do',
			data: '',
			dataType: 'xml',
			async: false,
			success: function(xml){
				var errorCode = $(xml).find('response').find('code').text();
    			//Si errorCode=0 entonces continuamos con el flujo
    			if(parseInt(errorCode)==0){
    				folioSolicitud =$(xml).find('folioSolicitud').text();
    				numExpediente =$(xml).find('numeroExpediente').text();
    				idExpediente =$(xml).find('expedienteId').text();
    				numeroExpedienteId=$(xml).find('numeroExpedienteId').text();
    				documentoId =$(xml).find('documentoId').text();
    				
    				abrirVisorSolicitudAsesoriaLegalNueva();		
    			}
			}
		});
	
				
	}
	
	/**
	* Funcion que invoca al visor "SolicitudAsesoriaLegal" considerando el Turno dentro del titulo.
	*/
	//MOD defensorATE
	function abrirVisorSolicitudAsesoriaLegalNueva(){
		var titulo = "";				
		if(turnoEnCurso != undefined && turnoEnCurso != "undefined" && turnoEnCurso != ""){
			titulo = " - Turno: "+ turnoEnCurso;
		}
		abrirVisorSolicitudAsesoriaLegal(titulo, "");
	}
	
	/**
	* Funcion que invoca al visor "SolicitudAsesoriaLegal" considerando los parametros
	* para recargar el grid.
	*/
	//MOD defensorATE, coordinadorDEF
	function abrirVisorSolicitudAsesoriaLegalGrid(idInstitucion){
		var parametros = '&idInstitucion='+idInstitucion;
		abrirVisorSolicitudAsesoriaLegal("", parametros);
	}
	
	/*
	*	Funcion que se encarga de abrir el visor de la Solicitud de Asesoria Legal
	*/
	//MOD defensorATE, coordinadorDEF
	function abrirVisorSolicitudAsesoriaLegal(titulo, parametros){
		var titulo = "Asesor&iacute;a Legal: "+ numExpediente +  titulo;
		if(folioSolicitud != undefined && folioSolicitud != "undefined" && folioSolicitud != ""){
			titulo = titulo +" - Folio: "+ folioSolicitud;
		}
		parametros = 'numeroExpedienteCadena='+numExpediente+'&numeroExpedienteId='+numeroExpedienteId+'&expedienteId='+idExpediente+'&solicitudId='+documentoId + parametros;
		
		$.newWindow({id:"iframewindowAsesoriaLegal", statusBar: true, posx:50,posy:110,width:1090,height:570,title: titulo, type:"iframe"});
    	$.updateWindowContent("iframewindowAsesoriaLegal",'<iframe src="<%=request.getContextPath()%>/registrarAsesoriaLegal.do?'+parametros+'" width="1090" height="570" />');
	}
	
	
	
	//Variable para recargar el grid de Asesoria Legal
	var recargaGridAseLegal = true;

	/*
	* Funcion generica para consultar Solicitudes de Asesoria Legal 
	* con base en su estatus y la institucion
	*/
	//MOD defensorATE, coordinadorDEF, DEFENSOR
	function cargarGridAsesoria(idInstitucion,estatusSolicitud){
		var tipoSolicitud = '<%=TiposSolicitudes.ASESORIA_DEFENSORIA.getValorId()%>'
		var esConsultaAsesoria = false;
		if(parseInt(idRolActivo) == <%=Roles.DEFENSOR.getValorId()%>){
			esConsultaAsesoria = true;
		}
		
		//@deprecated
		//url:'<%= request.getContextPath()%>/solicitudesNoAtendidasAsesoria.do',
		
		if(recargaGridAseLegal==true){
			jQuery("#gridAsesorias").jqGrid({ 
				url:'<%= request.getContextPath()%>/consultarSolicitudesDeDefensorPorFiltro.do?idInstitucion='+idInstitucion+'&idEstatusSolicitud='+estatusSolicitud+'&idTipoSolicitud='+tipoSolicitud+'&esConsultaAsesoria='+esConsultaAsesoria+'',
				datatype: "xml", 
				colNames:['Folio','Estado','Caso','Expediente Asociado','Solicitante', 'Fecha-Hora Solicitud', 'ExpedienteId', 'NumeroExpedienteId', 'Defensor'], 
				colModel:[ 	{name:'folio',				index:'folio',				width:185,	align:"center"},
				           	{name:'estado',				index:'estado',				width:100,	align:'center',	sortable:false},
				           	{name:'caso',				index:'caso',		    	width:180,	align:'center',	sortable:false,	hidden:true	},
				           	{name:'numeroExpediente',	index:'numeroExpediente',	width:200,	align:'center',	sortable:false},
				           	{name:'solicitante',		index:'solicitante',		width:200,	align:'center',	sortable:false},
				           	{name:'fechaHoraSol',		index:'fechaHoraSol', 		width:150,	align:'center',	sortable:false},
				           	{name:'expedienteId',		index:'expedienteId',		width:20,	align:'center',	sortable:false, hidden:true},
				           	{name:'numeroExpedienteId',	index:'numeroExpedienteId',	width:20,	align:'center',	sortable:false, hidden:true},
				           	{name:'defensor',			index:'defensor',			width:200,	align:'center',	sortable:false}
						],
				pager: jQuery('#pagGridAsesorias'),
				rowNum:25,
				rowList:[10,20,30,40,50,60],
				autowidth: true,
				autoheight:true,
				viewrecords: true,
				sortorder: "desc",
				ondblClickRow: function(rowid) {
					seleccionAsesoria(rowid,idInstitucion,estatusSolicitud);
				}			
			}) //.navGrid('#pagGridAsesorias',{edit:false,add:false,del:false});
			recargaGridAseLegal = false;
		}
		else{
			jQuery("#gridAsesorias").jqGrid('setGridParam', { ondblClickRow: function(rowid) {
				seleccionAsesoria(rowid,idInstitucion,estatusSolicitud);
			} });
			jQuery("#gridAsesorias").jqGrid('setGridParam',{url:'<%= request.getContextPath()%>/consultarSolicitudesDeDefensorPorFiltro.do?idInstitucion='+idInstitucion+'&idEstatusSolicitud='+estatusSolicitud+'&idTipoSolicitud='+tipoSolicitud+'&esConsultaAsesoria='+esConsultaAsesoria+'',datatype:"xml" });
			jQuery("#gridAsesorias").trigger("reloadGrid");
		}
		$("#gview_gridAsesorias .ui-jqgrid-bdiv").css('height', '525px');
		ocultaMuestraGrids('7');
	}
	
	/**
	* Funcion utilizada para, una vez seleccionado un registro del grid,
	* se defina Abrir el visor o confirmar el seguimiento.
	*/
	function seleccionAsesoria(rowid,idInstitucion,estatusSolicitud){
		var ret =jQuery("#gridAsesorias").jqGrid('getRowData',rowid);
		
		documentoId 		= rowid;
		numExpediente 		= ret.numeroExpediente;
		numeroExpedienteId 	= ret.numeroExpedienteId;
		idExpediente 		= ret.expedienteId;
		folioSolicitud 		= ret.folio;
			
		if(parseInt(idRolActivo) == <%=Roles.DEFENSOR.getValorId()%> &&
			estatusSolicitud == <%=EstatusSolicitud.ASIGNADO.getValorId()%>){

			customConfirm("¿Dar seguimiento a la solicitud?", "Seguimiento de la solicitud", function () {
					actualizarEstatusSolicitud(<%=EstatusExpediente.EN_PROCESO.getValorId()%>, numeroExpedienteId, idInstitucion); 
				} );
		}
		else{
				abrirVisorSolicitudAsesoriaLegalGrid(idInstitucion);
		}
		/*
		var numeroExpedienteId = ret.numeroExpedienteId;
		numExpediente = ret.Expediente;	
		mostrarDetalleDesignaciones(rowid,solAsesoriaId,numeroExpedienteId); 
		*/
	}	
	
	/**
	* Funcion que actualiza el estatus de la solicitud 
	* de acuerdo a los prametros proporcionados
	*/
	//MOD Defensor
	function actualizarEstatusSolicitud(nuevoEstatusNumExpId, numeroExpedienteIdP, idInstitucion){
		
		if(numeroExpedienteIdP!=undefined || numeroExpedienteIdP != null || 
				numeroExpedienteIdP!="undefined" || numeroExpedienteIdP != "null" ||
				nuevoEstatusNumExpId!=undefined || nuevoEstatusNumExpId != null || 
				nuevoEstatusNumExpId!="undefined" || nuevoEstatusNumExpId != "null" ){
			
			$.ajax({
				async: false,
				type: 'POST',
				url: '<%=request.getContextPath()%>/actualizarEstatusSolicitudNumeroExpediente.do',
				dataType: 'xml',
	        	data: {
	        		numeroExpedienteId : numeroExpedienteIdP,
	        		nuevoEstatusNumExpId : nuevoEstatusNumExpId
	        	},
				success: function(xml){
					var errorCode = $(xml).find('response').find('code').text();
	    			//Si errorCode=0 entonces continuamos con el flujo
	    			if(parseInt(errorCode)==0){
						abrirVisorSolicitudAsesoriaLegalGrid(idInstitucion);
	    			}
				}
			});
		}
	}
	
 </script>
 