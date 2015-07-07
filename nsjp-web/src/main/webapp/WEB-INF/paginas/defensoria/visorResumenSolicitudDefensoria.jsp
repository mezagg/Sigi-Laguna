<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page	import="mx.gob.segob.nsjp.comun.enums.actividad.CategoriasActividad"%>
<%@page	import="mx.gob.segob.nsjp.comun.enums.actividad.ConfActividadDocumento"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.actividad.Actividades"%>
<%@page	import="mx.gob.segob.nsjp.comun.enums.solicitud.EstatusSolicitud"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.institucion.Instituciones"%>
<%@page import="mx.gob.segob.nsjp.comun.enums.forma.Formas"%>
<%@page	import="mx.gob.segob.nsjp.comun.enums.funcionario.TipoDefensoria"%>


<script type="text/javascript">

	$(document).ready(function() {
		
	});
	
	//****************************************FUNCIONES PARA LA CEJA DE RESUMEN***********************************************//
	
	/*
	*Funcion par mostrar el detalle de la solicitud de defensor
	*dependiendo de que institucion realizo la solicitud
	*/
	function configurarCejaResumen(xmlRespuesta){
		var institucionSolicitante = $(xmlRespuesta).find('solicitud').find('confInstitucion').first();
			
		if(existe(institucionSolicitante)){
			if($(institucionSolicitante).find('confInstitucionId').first().text() == '<%=Instituciones.PGJ.getValorId()%>'){
				$("#liResumenSolPG").show();
				$("#liResumenSolPJ").hide();
				$("#liResumenSolDEF").hide();
				$("#liResumenAudienciaSolPJ").hide();
				llenarDetalleSolicitudPG(xmlRespuesta);
			}else if($(institucionSolicitante).find('confInstitucionId').first().text() == '<%=Instituciones.PJ.getValorId()%>'){
				$("#tabsChildResumenSolicitud").tabs("option", "selected", [1]);	
				$("#liResumenSolPG").hide();
				$("#liResumenSolPJ").show();
				$("#liResumenSolDEF").hide();
				$("#liResumenAudienciaSolPJ").show();
				llenarDetalleSolicitudPJ(xmlRespuesta);
				llenarDetalleAudienciaSolicitudPJ(xmlRespuesta);
			}else if($(institucionSolicitante).find('confInstitucionId').first().text() == '<%=Instituciones.DEF.getValorId()%>'){
				$("#tabsChildResumenSolicitud").tabs("option", "selected", [2]);	
				$("#liResumenSolPG").hide();
				$("#liResumenSolPJ").hide();
				$("#liResumenSolDEF").show();
				$("#liResumenAudienciaSolPJ").hide();
				llenarDetalleSolicitudDEF(xmlRespuesta);
			}
		}
	}
	
	//********************************* FUNCIONES PARA SUB CEJA DE RESUMEN PG **************************/
	/*
	*Funcion que llena el detalle de la solicitud de PG
	*/		
	function llenarDetalleSolicitudPG(xmlRespuesta){

		//Obtenemos variables globales
		if(existe($(xmlRespuesta).find('solicitud').find('expedienteDTO').first().text())){
			expedienteId = $(xmlRespuesta).find('solicitud').find('expedienteDTO').find('expedienteId').first().text()
		}
		if(existe($(xmlRespuesta).find('solicitud').find('numeroExpediente').first().text())){
			numeroExpediente = $(xmlRespuesta).find('solicitud').find('numeroExpediente').last().text()			
		}
		if(existe($(xmlRespuesta).find('solicitud').find('expedienteDTO').first().text())){
			numeroExpedienteId = $(xmlRespuesta).find('solicitud').find('expedienteDTO').find('numeroExpedienteId').first().text()
		}

		//Llenamos los campos en vista
		if(existe($(xmlRespuesta).find('solicitud').find('numeroCasoAsociado').first().text())){
			$("#txtCasoResumenSolPG").val($(xmlRespuesta).find('solicitud').find('numeroCasoAsociado').first().text());
		}
		if(existe($(xmlRespuesta).find('solicitud').find('numeroExpediente').first().text())){
			$("#txtNumExpResumenSolPG").val($(xmlRespuesta).find('solicitud').find('numeroExpediente').first().text());
		}
		if(existe($(xmlRespuesta).find('solicitud').find('confInstitucion').find('nombreInst').first().text())){
			$("#txtInstOrigenResumenSolPG").val($(xmlRespuesta).find('solicitud').find('confInstitucion').find('nombreInst').first().text());
		}
		if(existe($(xmlRespuesta).find('solicitud').find('folioSolicitud').text())){
			$("#txtFolioResumenSolPG").val($(xmlRespuesta).find('solicitud').find('folioSolicitud').text());
		}
		if(existe($(xmlRespuesta).find('solicitud').find('numeroExpedienteAsociado').text())){
			$("#txtNumCausaResumenSolPG").val($(xmlRespuesta).find('solicitud').find('numeroExpedienteAsociado').text());
		}
		if(existe($(xmlRespuesta).find('solicitud').find('nombreSolicitante').text())){
			$("#txtAgenteMpResumenSolPG").val($(xmlRespuesta).find('solicitud').find('nombreSolicitante').text());
		}

		var nombreDestinatario = $(xmlRespuesta).find('solicitud').find('destinatario').find('nombreFuncionario').text();
		var apPatDestinatario = $(xmlRespuesta).find('solicitud').find('destinatario').find('apellidoPaternoFuncionario').text();
		
		if(existe(nombreDestinatario) && existe(apPatDestinatario)){

			var nombreCompleto = nombreDestinatario +" "+ apPatDestinatario;
			var apMatDestinatario = $(xmlRespuesta).find('solicitud').find('destinatario').find('apellidoMaternoFuncionario').text();

			if(existe(apMatDestinatario)){
				nombreCompleto = nombreCompleto+" "+apMatDestinatario;
			}
			$("#txtRespSolResumenSolPG").val(nombreCompleto);	
		}
		if(existe($(xmlRespuesta).find('solicitud').find('strFechaCreacion').first().text())){
			$("#txtFechaSolResumenSolPG").val($(xmlRespuesta).find('solicitud').find('strFechaCreacion').first().text());
		}
		if(existe($(xmlRespuesta).find('solicitud').find('strHoraCreacion').first().text())){
			$("#txtHoraSolResumenSolPG").val($(xmlRespuesta).find('solicitud').find('strHoraCreacion').first().text());
		}
		if(existe($(xmlRespuesta).find('solicitud').find('estatus').first().find('idCampo').text())){
			$("#txtEstadoSolResumenSolPG").val($(xmlRespuesta).find('solicitud').find('estatus').first().find('valor').text());	
		}
		if(existe($(xmlRespuesta).find('nombreDeLaUnidadDeInvestigacionDelSolicitante').first().text())){
			$("#txtUIEResumenSolPG").val($(xmlRespuesta).find('nombreDeLaUnidadDeInvestigacionDelSolicitante').first().text().toLowerCase());	
		}		
		if(existe($(xmlRespuesta).find('solicitud').find('delitos'))){
			mostrarDelitosDelExpediente($(xmlRespuesta).find('solicitud').find('delitos'));
		}
	}


	/*
	*Funcion que llena el detalle de la solicitud de PJ
	*/		
	function llenarDetalleSolicitudPJ(xmlRespuesta){

		//Obtenemos variables globales
		if(existe($(xmlRespuesta).find('solicitud').find('expedienteDTO').first().text())){
			expedienteId = $(xmlRespuesta).find('solicitud').find('expedienteDTO').find('expedienteId').first().text();
		}
		if(existe($(xmlRespuesta).find('solicitud').find('numeroExpediente').first().text())){
			numeroExpediente = $(xmlRespuesta).find('solicitud').find('numeroExpediente').last().text();			
		}
		if(existe($(xmlRespuesta).find('solicitud').find('expedienteDTO').first().text())){
			numeroExpedienteId = $(xmlRespuesta).find('solicitud').find('expedienteDTO').find('numeroExpedienteId').first().text();
		}

		//Llenamos los campos en vista
		if(existe($(xmlRespuesta).find('solicitud').find('numeroCasoAsociado').first().text())){
			$("#txtCasoResumenSolPJ").val($(xmlRespuesta).find('solicitud').find('numeroCasoAsociado').first().text());
		}
		if(existe($(xmlRespuesta).find('solicitud').find('numeroExpediente').first().text())){
			$("#txtNumExpResumenSolPJ").val($(xmlRespuesta).find('solicitud').find('numeroExpediente').first().text());
		}
		if(existe($(xmlRespuesta).find('solicitud').find('confInstitucion').find('nombreInst').first().text())){
			$("#txtInstOrigenResumenSolPJ").val($(xmlRespuesta).find('solicitud').find('confInstitucion').find('nombreInst').first().text());
		}
		if(existe($(xmlRespuesta).find('solicitud').find('folioSolicitud').text())){
			$("#txtFolioResumenSolPJ").val($(xmlRespuesta).find('solicitud').find('folioSolicitud').text());
		}
		if(existe($(xmlRespuesta).find('solicitud').find('numeroExpedienteAsociado').text())){
			$("#txtNumCausaResumenSolPJ").val($(xmlRespuesta).find('solicitud').find('numeroExpedienteAsociado').text());
		}

		//if(existe($(xmlRespuesta).find('solicitud').find('nombreSolicitante').text())){
		//	$("#txtAgenteMpResumenSolPG").val($(xmlRespuesta).find('solicitud').find('nombreSolicitante').text());
		//}

		var nombreDestinatario = $(xmlRespuesta).find('solicitud').find('destinatario').find('nombreFuncionario').text();
		var apPatDestinatario = $(xmlRespuesta).find('solicitud').find('destinatario').find('apellidoPaternoFuncionario').text();
		
		if(existe(nombreDestinatario) && existe(apPatDestinatario)){

			var nombreCompleto = nombreDestinatario +" "+ apPatDestinatario;
			var apMatDestinatario = $(xmlRespuesta).find('solicitud').find('destinatario').find('apellidoMaternoFuncionario').text();

			if(existe(apMatDestinatario)){
				nombreCompleto = nombreCompleto+" "+apMatDestinatario;
			}
			$("#txtRespSolResumenSolPJ").val(nombreCompleto);	
		}
		if(existe($(xmlRespuesta).find('solicitud').find('strFechaCreacion').first().text())){
			$("#txtFechaSolResumenSolPJ").val($(xmlRespuesta).find('solicitud').find('strFechaCreacion').first().text());
		}
		if(existe($(xmlRespuesta).find('solicitud').find('strHoraCreacion').first().text())){
			$("#txtHoraSolResumenSolPJ").val($(xmlRespuesta).find('solicitud').find('strHoraCreacion').first().text());
		}
		if(existe($(xmlRespuesta).find('solicitud').find('estatus').first().find('idCampo').text())){
			$("#txtEstadoSolResumenSolPJ").val($(xmlRespuesta).find('solicitud').find('estatus').first().find('valor').text());	
		}		
		if(existe($(xmlRespuesta).find('solicitud').find('delitos'))){
			mostrarDelitosDelExpediente($(xmlRespuesta).find('solicitud').find('delitos'));
		}
	}


	/*
	*Funcion que llena el detalle de la de la audiencia de la solicitud de PJ
	*/		
	function llenarDetalleAudienciaSolicitudPJ(xmlRespuesta){

		if(existe($(xmlRespuesta).find('solicitud').find('audiencia').first())){

			var audiencia = $(xmlRespuesta).find('solicitud').find('audiencia').first();

			if(existe($(audiencia).find('folioAudiencia'))){
				$("#txtFolioAudResumenSolPJ").val($(audiencia).find('folioAudiencia').text());	
			}
			if(existe($(audiencia).find('caracter'))){
				$("#txtCaracterAudResumenSolPJ").val($(audiencia).find('caracter').text());	
			}
			if(existe($(audiencia).find('tipoAudiencia').find('valor'))){
				$("#txtTipoAudResumenSolPJ").val($(audiencia).find('tipoAudiencia').find('valor').text());	
			}
			if(existe($(audiencia).find('duracionEstimada'))){
				$("#txtDuracionAudResumenSolPJ").val($(audiencia).find('duracionEstimada').text()+" min.");	
			}
			if(existe($(audiencia).find('fechaEvento'))){

				var fechaEvento = $(audiencia).find('fechaEvento').text();
				var fechaFrac = fechaEvento.split(" ")[0];
				var horaFrac = fechaEvento.split(" ")[1];

				horaFracPos = horaFrac.indexOf(":", 0);
				hora=horaFrac.substring(0,horaFracPos+3);		
									
				$("#txtFechaAudResumenSolPJ").val(fechaFrac);
				$("#txtHoraAudResumenSolPJ").val(hora);
			}
			
			if(existe($(audiencia).find('sala'))){
				
				if(existe($(audiencia).find('sala').find('nombreSala'))){
					$("#txtNombreSalaAudResumenSolPJ").val($(audiencia).find('sala').find('nombreSala').text());
				}
				if(existe($(audiencia).find('sala').find('ubicacionSala'))){
					$("#txtUbicacionAudResumenSolPJ").val($(audiencia).find('sala').find('ubicacionSala').text());
				}
				if(existe($(audiencia).find('sala').find('domicilioSala'))){
					$("#txtDomicilioAudResumenSolPJ").val($(audiencia).find('sala').find('domicilioSala').text());
				}
			}
		}
	}


	/*
	*Funcion que llena el detalle de la solicitud de DEF
	*/		
	function llenarDetalleSolicitudDEF(xmlRespuesta){

		//Obtenemos variables globales
		if(existe($(xmlRespuesta).find('solicitud').find('expedienteDTO').first().text())){
			expedienteId = $(xmlRespuesta).find('solicitud').find('expedienteDTO').find('expedienteId').first().text();
		}
		if(existe($(xmlRespuesta).find('solicitud').find('numeroExpediente').first().text())){
			numeroExpediente = $(xmlRespuesta).find('solicitud').find('numeroExpediente').last().text();			
		}
		if(existe($(xmlRespuesta).find('solicitud').find('expedienteDTO').first().text())){
			numeroExpedienteId = $(xmlRespuesta).find('solicitud').find('expedienteDTO').find('numeroExpedienteId').first().text();
		}

		//Llenamos los campos en vista
		if(existe($(xmlRespuesta).find('solicitud').find('numeroCasoAsociado').first().text())){
			$("#txtCasoResumenSolDEF").val($(xmlRespuesta).find('solicitud').find('numeroCasoAsociado').first().text());
		}
		if(existe($(xmlRespuesta).find('solicitud').find('numeroExpediente').first().text())){
			$("#txtNumExpResumenSolDEF").val($(xmlRespuesta).find('solicitud').find('numeroExpediente').first().text());
		}
		if(existe($(xmlRespuesta).find('solicitud').find('confInstitucion').find('nombreInst').first().text())){
			$("#txtInstOrigenResumenSolDEF").val($(xmlRespuesta).find('solicitud').find('confInstitucion').find('nombreInst').first().text());
		}
		if(existe($(xmlRespuesta).find('solicitud').find('folioSolicitud').text())){
			$("#txtFolioResumenSolDEF").val($(xmlRespuesta).find('solicitud').find('folioSolicitud').text());
		}
		//if(existe($(xmlRespuesta).find('solicitud').find('numeroExpedienteAsociado').text())){
		//	$("#txtNumCausaResumenSolPJ").val($(xmlRespuesta).find('solicitud').find('numeroExpedienteAsociado').text());
		//}

		//Nombre del involucrado Solicitante
		if(existe($(xmlRespuesta).find('solicitud').find('involucradoDTO').find('nombresDemograficoDTO').first().find('nombreDemograficoDTO'))){

			var nombreDemograficoInvoSol = $(xmlRespuesta).find('solicitud').find('involucradoDTO').find('nombresDemograficoDTO').first().find('nombreDemograficoDTO');

			var nombreInvoSol = $(nombreDemograficoInvoSol).find('nombre').text();
			var apPatInvoSol = $(nombreDemograficoInvoSol).find('apellidoPaterno').text();
			var apMatInvoSol = $(nombreDemograficoInvoSol).find('apellidoMaterno').text();
			
			var nombreCompletoInvoSol = "";
			
			if(existe(nombreInvoSol)){
				nombreCompletoInvoSol = nombreInvoSol;
			}
			if(existe(apPatInvoSol)){
				nombreCompletoInvoSol = nombreCompletoInvoSol+" "+apPatInvoSol;
			}
			if(existe(apMatInvoSol)){
				nombreCompletoInvoSol = nombreCompletoInvoSol+" "+apMatInvoSol;
			}
			
			$("#txtNombreSolicitanteResumenSolDEF").val(nombreCompletoInvoSol);
		}

		if(existe($(xmlRespuesta).find('solicitud').find('destinatario'))){
			var nombreDestinatario = $(xmlRespuesta).find('solicitud').find('destinatario').find('nombreFuncionario').text();
			var apPatDestinatario = $(xmlRespuesta).find('solicitud').find('destinatario').find('apellidoPaternoFuncionario').text();
			
			if(existe(nombreDestinatario) && existe(apPatDestinatario)){
				var nombreCompleto = nombreDestinatario +" "+ apPatDestinatario;
				var apMatDestinatario = $(xmlRespuesta).find('solicitud').find('destinatario').find('apellidoMaternoFuncionario').text();
	
				if(existe(apMatDestinatario)){
					nombreCompleto = nombreCompleto+" "+apMatDestinatario;
				}
				$("#txtRespSolResumenSolDEF").val(nombreCompleto);	
			}
		}
		
		if(existe($(xmlRespuesta).find('solicitud').find('strFechaCreacion').first().text())){
			$("#txtFechaSolResumenSolDEF").val($(xmlRespuesta).find('solicitud').find('strFechaCreacion').first().text());
		}
		
		if(existe($(xmlRespuesta).find('solicitud').find('strHoraCreacion').first().text())){
			$("#txtHoraSolResumenSolDEF").val($(xmlRespuesta).find('solicitud').find('strHoraCreacion').first().text());
		}
		
		if(existe($(xmlRespuesta).find('solicitud').find('estatus').first().find('idCampo').text())){
			$("#txtEstadoSolResumenSolDEF").val($(xmlRespuesta).find('solicitud').find('estatus').first().find('valor').text());	
		}
				
		if(existe($(xmlRespuesta).find('solicitud').find('delitos'))){
			mostrarDelitosDelExpediente($(xmlRespuesta).find('solicitud').find('delitos'));
		}
		
	}
	
	//******************************* FUNCIONES PARA SUB CEJA DE DETALLE DE INTERVINIENTES**************************/
	var reloadGridInvolucradoSolicitudDefensor = false; 

	/*
	*Funcion para cargar el grid con los involucrados a la solicitud
	*mediante una consulta a "InvolucradoSolicitudDefensor" con id de la 
	*solicitud de defensor
	*/
	function cargaGridInvolucradoSolicitudDefensor(solicitudId){

		if (reloadGridInvolucradoSolicitudDefensor == true) {
			$("#tblGridInvolucradoSolicitudDefensor").trigger("reloadGrid"); 
		}
		else{
			jQuery("#tblGridInvolucradoSolicitudDefensor").jqGrid({ 
				url:'<%=request.getContextPath()%>/consultarInvolucradosSolicitudDefensor.do?solicitudId='+solicitudId+'', 
				datatype: "xml", 
				colNames:['Nombre','Calidad','Detenido','Fecha Detenci&oacute;n','Hora Detenci&oacute;n'], 
				colModel:[ 
						   {name:'Nombre',			index:'nombre', 		width:180,	sortable:false, align:'center'},
						   {name:'Calidad',			index:'calidad', 		width:150,	sortable:false, align:'center'},
						   {name:'Detenido',		index:'detenido', 		width:70,	sortable:false, align:'center'},			           	
						   {name:'FechaDetencion',	index:'fechaDetencion', width:120,	sortable:false, align:'center'},
						   {name:'HoraDetencion',	index:'horaDetencion', 	width:120,	sortable:false, align:'center'}
						],
				pager: jQuery('#divPagerGridInvolucradoSolicitudDefensor'),
				rowNum:10,
				rowList:[10,20,30],
				height:230,
				viewrecords: true,
				sortorder: "desc",
				loadComplete: function(){
					reloadGridInvolucradoSolicitudDefensor = true;
				},
				onSelectRow: function(rowID) {
						
				}
			});	
		}
	}

	/*
	*Funcion para validar si un valor o variable es indefinido o nulo
	*/
	//TODO Se ha definido la misma funcion en comun.jsp
	function existe(val){
		if(val != undefined && val != null && val != "" && val.length > 0){
			return true;
		}
		return false;
	}

		
	/*
	*Muestra la lista de los delitos del expediente
	*/
	function mostrarDelitosDelExpediente(listaDelitosXml){

		$(listaDelitosXml).find('delito').each(function(){
			$('#txtDelitosDetalleSol').append('<option value="' + $(this).find('catDelitoDTO').find('claveDelito').text() + '">' + $(this).find('catDelitoDTO').find('nombre').text() + '</option>');
		});
	}
	
</script>

<div id="tabsVisorDefensoria-1" align="left">

	<!--Comienzan tabs child de resumen de la solicitud-->
	<div id="tabsChildResumenSolicitud" class="tabs-bottom" align="left">

		<ul>
			<li id="liResumenSolPG" style="display: none">
				<!--Detalle de solicitud para PG--> <a
				href="#tabsChildResumenSolicitud-1">Detalle de Solicitud</a>
			</li>
			<li id="liResumenSolPJ" style="display: none">
				<!--Detalle de solicitud para PJ--> <a
				href="#tabsChildResumenSolicitud-2">Detalle de Solicitud</a>
			</li>

			<li id="liResumenSolDEF" style="display: none">
				<!--Detalle de solicitud para DEF--> <a
				href="#tabsChildResumenSolicitud-3">Detalle de Solicitud</a>
			</li>

			<li id="liResumenAudienciaSolPJ" style="display: none">
				<!--Detalle de la audiencia para la solicitud desde PJ--> <a
				href="#tabsChildResumenSolicitud-4">Detalle de la audiencia</a>
			</li>

			<li id="liResumenIntervinientes">
				<!--Detalle de solicitud Intervinientes General--> <a
				href="#tabsChildResumenSolicitud-5">Detalle de Involucrados</a>
			</li>
		</ul>

		<!--Comienza tab hijo 1 de Resumen de la solicitud de PG-->
		<div id="tabsChildResumenSolicitud-1" style="height: 400px">
			<fieldset>
				<!-- <legend> Resumen de la solicitud </legend> -->
				<table width="100%" border="0">
					<tr>
						<td width="30%" align="right"></td>
						<td width="20%" align="left"></td>
						<td width="20%" align="right"></td>
						<td width="30%" align="left"></td>
					</tr>
					<tr>
						<td align="right">
							<strong> N&uacute;mero de Caso: </strong>			</td>
						<td align="left">
							<input type="text" class=""	style="width: 190px; border: 0; background: #DDD;" readonly="readonly" id="txtCasoResumenSolPG" />			</td>
						<td align="right">
							<strong> N&uacute;mero de Legajo/Causa: </strong>			</td>
						<td align="left">
							<input type="text" class="" style="width: 190px; border: 0; background: #DDD;" readonly="readonly" id="txtNumCausaResumenSolPG" />			</td>
					</tr>
					<tr>
						<td align="right">
							<strong> N&uacute;mero de expediente: </strong>			</td>
						<td align="left">
							<input type="text" class="" style="width: 190px; border: 0; background: #DDD;" readonly="readonly" id="txtNumExpResumenSolPG" />			</td>
						<td align="right">
							<strong> Instituci&oacute;n Origen: </strong>			</td>
						<td align="left">
							<input type="text" class="" style="width: 180px; border: 0; background: #DDD;" readonly="readonly" id="txtInstOrigenResumenSolPG" />			</td>
					</tr>
					<tr>
						<td align="right">
							<strong>Folio Solicitud: </strong>			</td>
						<td align="left">
							<input id="txtFolioResumenSolPG" type="text" class="" style="width: 180px; border: 0; background: #DDD;" readonly="readonly" >			</td>
						<td align="right">
							<strong>Estado Solicitud: </strong>			</td>
						<td align="left">
							<input id="txtEstadoSolResumenSolPG" type="text" class="" style="width: 180px; border: 0; background: #DDD;" readonly="readonly" />			</td>
					</tr>
					<tr>
						<td align="right">
							<strong>Fecha Solicitud: </strong>			</td>
						<td align="left">
							<input id="txtFechaSolResumenSolPG" type="text" class="" style="width: 180px; border: 0; background: #DDD;" readonly="readonly" />			</td>
						<td align="right">
							<strong>Hora Solicitud: </strong>			</td>
						<td align="left">
							<input id="txtHoraSolResumenSolPG" type="text" class="" style="width: 180px; border: 0; background: #DDD;" readonly="readonly" />			</td>
					</tr>
					<tr>
						<td align="right">
							<strong> Agente del Ministerio P&uacute;blico:</strong>			</td>
						<td align="left">
							<input type="text" class="" style="width: 180px; border: 0; background: #DDD;" readonly="readonly" id="txtAgenteMpResumenSolPG" />			</td>
						<td align="right">
							<strong> Responsable de la Solicitud:</strong>			</td>
						<td align="left">
							<input type="text" class="" style="width: 180px; border: 0; background: #DDD;" readonly="readonly" id="txtRespSolResumenSolPG" />			</td>
					</tr>
					<tr>
						<td align="right">
							<strong> Unidad de Investigaci&oacute;n: </strong>
						</td>
						<td colspan="4" align="left">
							<input type="text" class="" style="width: 570px; border: 0; background: #DDD;" readonly="readonly" id="txtUIEResumenSolPG" />
						</td>
					</tr>
					<tr>
						<td align="right"></td>
						<td align="left"></td>
						<td align="right"></td>
						<td align="left"></td>
					</tr>
				</table>
			</fieldset>
		</div>
		<!--Termina tab hijo 1 de Resumen de la solicitud de PG-->


		<!--Comienza tab hijo 2 de Resumen de la solicitud de PJ-->
		<div id="tabsChildResumenSolicitud-2" style="height: 400px">
			<fieldset>
				<!-- <legend> Resumen de la solicitud </legend> -->
				<table width="100%" border="0">
					<tr>
						<td width="30%" align="right"></td>
						<td width="20%" align="left"></td>
						<td width="20%" align="right"></td>
						<td width="30%" align="left"></td>
					</tr>
					<tr>
						<td align="right"><strong> N&uacute;mero de Caso: </strong></td>
						<td align="left"><input type="text" class=""
							style="width: 190px; border: 0; background: #DDD;"
							readonly="readonly" id="txtCasoResumenSolPJ" /></td>
						<td align="right"><strong> N&uacute;mero de
								Legajo/Causa: </strong></td>
						<td align="left"><input type="text" class=""
							style="width: 190px; border: 0; background: #DDD;"
							readonly="readonly" id="txtNumCausaResumenSolPJ" /></td>
					</tr>
					<tr>
						<td align="right"><strong> N&uacute;mero de
								expediente: </strong></td>
						<td align="left"><input type="text" class=""
							style="width: 190px; border: 0; background: #DDD;"
							readonly="readonly" id="txtNumExpResumenSolPJ" /></td>
						<td align="right">
							<!--strong>
										Agente del Ministerio P&uacute;blico: 
									</strong-->
						</td>
						<td align="left">
							<!--input type="text" class="" style="width: 180px; border: 0; background: #DDD;" readonly="readonly" id="txtAgenteMpResumenSolPG"/-->
						</td>
					</tr>
					<tr>
						<td align="right"><strong> Instituci&oacute;n
								Origen: </strong></td>
						<td align="left"><input type="text" class=""
							style="width: 180px; border: 0; background: #DDD;"
							readonly="readonly" id="txtInstOrigenResumenSolPJ" /></td>
						<td align="right"><strong> Responsable de la
								Solicitud: </strong></td>
						<td align="left"><input type="text" class=""
							style="width: 180px; border: 0; background: #DDD;"
							readonly="readonly" id="txtRespSolResumenSolPJ" /></td>
					</tr>
					<tr>
						<td align="right"><strong>Folio Solicitud: </strong></td>
						<td align="left"><input id="txtFolioResumenSolPJ" type="text"
							class="" style="width: 180px; border: 0; background: #DDD;"
							readonly="readonly" /></td>
						<td align="right"><strong>Fecha Solicitud: </strong></td>
						<td align="left"><input id="txtFechaSolResumenSolPJ"
							type="text" class=""
							style="width: 180px; border: 0; background: #DDD;"
							readonly="readonly" /></td>
					</tr>
					<tr>
						<td align="right"><strong>Estado Solicitud: </strong></td>
						<td align="left"><input id="txtEstadoSolResumenSolPJ"
							type="text" class=""
							style="width: 180px; border: 0; background: #DDD;"
							readonly="readonly" /></td>
						<td align="right"><strong>Hora Solicitud: </strong></td>
						<td align="left"><input id="txtHoraSolResumenSolPJ"
							type="text" class=""
							style="width: 180px; border: 0; background: #DDD;"
							readonly="readonly" /></td>
					</tr>
					<tr>
						<td align="right"></td>
						<td align="left"></td>
						<td align="right"></td>
						<td align="left"></td>
					</tr>
					<tr>
						<td align="right"></td>
						<td align="left"></td>
						<td align="right"></td>
						<td align="left"></td>
					</tr>
				</table>
			</fieldset>

		</div>
		<!--Termina tab hijo 2 de Resumen de la solicitud de PJ-->


		<!--Comienza tab hijo 3 de Resumen de la solicitud de DEF-->
		<div id="tabsChildResumenSolicitud-3" style="height: 400px">
			<fieldset>
				<!-- <legend> Resumen de la solicitud </legend> -->
				<table width="100%" border="0">
					<tr>
						<td width="30%" align="right"></td>
						<td width="20%" align="left"></td>
						<td width="20%" align="right"></td>
						<td width="30%" align="left"></td>
					</tr>
					<tr>
						<td align="right"><strong> N&uacute;mero de Caso: </strong></td>
						<td align="left"><input type="text" class=""
							style="width: 190px; border: 0; background: #DDD;"
							readonly="readonly" id="txtCasoResumenSolDEF" /></td>
						<td align="right"><strong> Nombre del solicitante: </strong>
						</td>
						<td align="left"><input type="text" class=""
							style="width: 180px; border: 0; background: #DDD;"
							readonly="readonly" id="txtNombreSolicitanteResumenSolDEF" /></td>
					</tr>
					<tr>
						<td align="right"><strong> N&uacute;mero de
								expediente: </strong></td>
						<td align="left"><input type="text" class=""
							style="width: 190px; border: 0; background: #DDD;"
							readonly="readonly" id="txtNumExpResumenSolDEF" /></td>
						<td align="right">
							<!--strong>
										Agente del Ministerio P&uacute;blico: 
									</strong-->
						</td>
						<td align="left">
							<!--input type="text" class="" style="width: 180px; border: 0; background: #DDD;" readonly="readonly" id="txtAgenteMpResumenSolPG"/-->
						</td>
					</tr>
					<tr>
						<td align="right"><strong> Instituci&oacute;n
								Origen: </strong></td>
						<td align="left"><input type="text" class=""
							style="width: 180px; border: 0; background: #DDD;"
							readonly="readonly" id="txtInstOrigenResumenSolDEF" /></td>
						<td align="right"><strong> Responsable de la
								Solicitud: </strong></td>
						<td align="left"><input type="text" class=""
							style="width: 180px; border: 0; background: #DDD;"
							readonly="readonly" id="txtRespSolResumenSolDEF" /></td>
					</tr>
					<tr>
						<td align="right"><strong>Folio Solicitud: </strong></td>
						<td align="left"><input id="txtFolioResumenSolDEF"
							type="text" class=""
							style="width: 180px; border: 0; background: #DDD;"
							readonly="readonly" /></td>
						<td align="right"><strong>Fecha Solicitud: </strong></td>
						<td align="left"><input id="txtFechaSolResumenSolDEF"
							type="text" class=""
							style="width: 180px; border: 0; background: #DDD;"
							readonly="readonly" /></td>
					</tr>
					<tr>
						<td align="right"><strong>Estado Solicitud: </strong></td>
						<td align="left"><input id="txtEstadoSolResumenSolDEF"
							type="text" class=""
							style="width: 180px; border: 0; background: #DDD;"
							readonly="readonly" /></td>
						<td align="right"><strong>Hora Solicitud: </strong></td>
						<td align="left"><input id="txtHoraSolResumenSolDEF"
							type="text" class=""
							style="width: 180px; border: 0; background: #DDD;"
							readonly="readonly" /></td>
					</tr>
					<tr>
						<td align="right"></td>
						<td align="left"></td>
						<td align="right"></td>
						<td align="left"></td>
					</tr>
					<tr>
						<td align="right"></td>
						<td align="left"></td>
						<td align="right"></td>
						<td align="left"></td>
					</tr>
				</table>
			</fieldset>

		</div>
		<!--Termina tab hijo 3 de Resumen de la solicitud de DEF-->

		<!--Comienza tab hijo 4 de Resumen de la audiencia de la solicitud de PJ -->
		<div id="tabsChildResumenSolicitud-4" style="height: 400px">
			<fieldset>
				<legend> Resumen de la audiencia </legend>
				<table width="100%" border="0">
					<tr>
						<td width="21%" align="right"></td>
						<td width="29%" align="left"></td>
						<td width="20%" align="right"></td>
						<td width="30%" align="left"></td>
					</tr>
					<tr>
						<td align="right"><strong> Folio de Audiencia: </strong></td>
						<td align="left"><input type="text" class=""
							style="width: 180px; border: 0; background: #DDD;"
							readonly="readonly" id="txtFolioAudResumenSolPJ" /></td>
						<td align="right"><strong> Car&aacute;cter: </strong></td>
						<td align="left"><input type="text" class=""
							style="width: 180px; border: 0; background: #DDD;"
							readonly="readonly" id="txtCaracterAudResumenSolPJ" /></td>
					</tr>
					<tr>
						<td align="right"><strong> Tipo: </strong></td>
						<td align="left"><input type="text" class=""
							style="width: 300px; border: 0; background: #DDD;"
							readonly="readonly" id="txtTipoAudResumenSolPJ" /></td>
						<td align="right"><strong> Duraci&oacute;n Estimada:
						</strong></td>
						<td align="left"><input type="text" class=""
							style="width: 180px; border: 0; background: #DDD;"
							readonly="readonly" id="txtDuracionAudResumenSolPJ" /></td>
					</tr>
					<tr>
						<td align="right"><strong> Fecha: </strong></td>
						<td align="left"><input type="text" class=""
							style="width: 180px; border: 0; background: #DDD;"
							readonly="readonly" id="txtFechaAudResumenSolPJ" /></td>
						<td align="right"><strong> Hora: </strong></td>
						<td align="left"><input type="text" class=""
							style="width: 180px; border: 0; background: #DDD;"
							readonly="readonly" id="txtHoraAudResumenSolPJ" /></td>
					</tr>
					<tr>
						<td align="right"><strong>Nombre Sala: </strong></td>
						<td align="left"><input id="txtNombreSalaAudResumenSolPJ"
							type="text" class=""
							style="width: 180px; border: 0; background: #DDD;"
							readonly="readonly" /></td>
						<td align="right"><strong>Ubicaci&oacute;n: </strong></td>
						<td align="left"><input id="txtUbicacionAudResumenSolPJ"
							type="text" class=""
							style="width: 180px; border: 0; background: #DDD;"
							readonly="readonly" /></td>
					</tr>
					<tr>
						<td align="right"><strong>Domicilio: </strong></td>
						<td colspan="2" align="left"><input
							id="txtDomicilioAudResumenSolPJ" type="text" class=""
							style="width: 300px; border: 0; background: #DDD;"
							readonly="readonly" />
						<td align="left"></td>
					</tr>
					<tr>
						<td align="right"></td>
						<td align="left"></td>
						<td align="right"></td>
						<td align="left"></td>
					</tr>
					<tr>
						<td align="right"></td>
						<td align="left"></td>
						<td align="right"></td>
						<td align="left"></td>
					</tr>
				</table>
			</fieldset>

		</div>
		<!--Termina tab hijo 4 de Resumen de la solicitud de PJ-->


		<!--Comienza tab hijo 5 de Resumen de la solicitud-->
		<div id="tabsChildResumenSolicitud-5" style="height: 400px"
			align="left">

			<table width="100%" border="0">
				<tr>
					<td width="65%"></td>
					<td width="35%"></td>
				</tr>
				<tr>
					<td rowspan="6">
						<table id="tblGridInvolucradoSolicitudDefensor"></table>
						<div id="divPagerGridInvolucradoSolicitudDefensor"></div>
					</td>
					<td align="center"><strong>Delitos del expediente:</strong></td>
				</tr>
				<tr>
					<td align="center" align="center" valign="top"><select
						id="txtDelitosDetalleSol" multiple="true" size="4"
						style="width: 250px; height: 200px; border: 0; background: #DDD;"></select>
					</td>
				</tr>
				<tr>
					<td></td>
				</tr>
				<tr>
					<td></td>
				</tr>
				<tr>
					<td></td>
				</tr>
				<tr>
					<td></td>
				</tr>
				<tr>
					<td></td>
					<td></td>
				</tr>
			</table>

		</div>
		<!--Termina tab hijo 5 de Resumen de la solicitud-->

	</div>
	<!--Termina tabs child de resumen de la solicitud-->

</div>
<!--Termina tab 1-->


