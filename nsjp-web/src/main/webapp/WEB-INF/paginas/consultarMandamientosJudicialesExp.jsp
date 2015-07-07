<%@page import="mx.gob.segob.nsjp.comun.enums.calidad.Calidades"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<script type="text/javascript">

$(document).ready(function(){



	//Titulo del grin mandamientos persona
	var tituloGridMandJudAsocProbRespGenManJud = "Mandamientos judiciales asociados al "+'<bean:message key="probableResponsable"/>'+" seleccionado";

	//Construimos el grid de mandamientos persona
	jQuery("#gridMandJudAsocProbRespGenManJud").jqGrid({
		url:'<%=request.getContextPath()%>/limpiarGrid.do?numeroColumnas=NUM_COL_MP',
		datatype: "xml", 
		colNames:['Mandamiento(s)','Fecha Creacion','Estado Mandamiento Persona','Fecha de Ejecuci&oacute;n','Documento Estado','Documento','MandamientoId'], 
		colModel:[  {name:'mandamiento',index:'mandamiento', width:150, align:'center', sortable:false},						
		           	{name:'fechaResolutivo',index:'fechaResolutivo', width:150, align:'center'},
		           	{name:'estatusMandamiento',index:'estatusMandamiento', width:150, align:'center',sortable:false},
		           	{name:'fechaEjecucion',index:'fechaEjecucion', width:125,align:'center'}, 
		           	{name:'docEstatus',index:'docEstatus', width:150, align:'center',sortable:false},
		           	{name:'documento',index:'documento', width:150, align:'center',sortable:false},
		           	{name:'mandamientoId',index:'MandamientoId', width:150, align:'center', hidden:true}
				],
		pager: jQuery('#pagerGridMandJudAsocProbRespGenManJud'),
		rowNum:10,
		rowList:[10,20,30],
		width:1000,
		sortname:'estatusMandamiento',
		viewrecords:true,
		sortorder:"desc",
		async:false,
		onSelectRow: function(rowid) {
			if(rowid){
				var datosManJudAsocProbResp = jQuery("#gridMandJudAsocProbRespGenManJud").jqGrid('getRowData',rowid);
				cargarGridRelDelitoPersonaManJudExp(datosManJudAsocProbResp.mandamientoId);
			}
		},
		caption: tituloGridMandJudAsocProbRespGenManJud,
	});


	//Se gernera el grid de relaciones delito persona asociadas a la relacion mandamiento persona
	jQuery("#gridRelDelitoPersonaManJudExp").jqGrid({
		url:'<%=request.getContextPath()%>/limpiarGrid.do?numeroColumnas=NUM_COL',
		datatype: "xml", 
		colNames:['<bean:message key="probableResponsable"/>','Delito','Victima'], 
		colModel:[  {name:'probResponsable',index:'probResponsable', width:300, align:'center', sortable:false},
		           	{name:'delito',index:'delito', width:250, align:'center', sortable:false},
		           	{name:'victima',index:'victima', width:250, align:'center', sortable:false},
		        ],
		pager: jQuery('#pagerGridRelDelitoPersonaManJudExp'),
		rowNum:10,
		rowList:[10,20,30],
		width:800,
		async:false,
		sortname:'probResponsable',
		viewrecords:false,
		//sortorder:"desc",
		caption:"Relaciones Delito-Persona del mandamiento",
	});

	//Verificar si existe otra forma de que sean sincronos
	//Funcion que se ubica en: consultaMandamientoJudXProbResponsable.jsp
	if(typeof configurarPantallaConsulta == 'function'){
		setTimeout('configurarPantallaConsulta()',1000);
	}
}); 

/************************************************ FUNCIONES CEJA MANDAMIENTOS JUDICIALES DEL EXPEDIENTE  ****************************************************/
		//numero de columnas para limpiar el grid de mandamientos persona
		var NUM_COL_MP=7;
		var NUM_COL=3;


		/*
		*Incializa esta tab, cada que se cambia de Tab
		*/
		function inicializaTabMandamientosDelExpediente(){
			cargarGridProbResCausaGenManJud();
			limpiarGridMandJudAsocProbRespGenManJud();
			limpiarGridRelDelitoPersonaManJudExp();
		}

		
		/**
		*Funcion que carga el grid con los probables responsables a la causa
		*/
		var gridProbResCausaGenManJud = true;
		
		function cargarGridProbResCausaGenManJud(){

			var calidadId="<%=Calidades.PROBABLE_RESPONSABLE_PERSONA.getValorId()%>";
			
			if(gridProbResCausaGenManJud){

				jQuery("#gridProbResGenManJudCausa").jqGrid({
					url:'<%=request.getContextPath()%>/consultarInvolucradoPorCalidad.do?expedienteId='+idExpediente+'&calidadId='+calidadId+'',
					datatype: "xml", 
					colNames:['<bean:message key="probableResponsable"/>'], 
					colModel:[  
								{name:'probResponsable',index:'probResponsable', width:350, align:'center'},
							],
					pager: jQuery('#pagerGridProbResGenManJudCausa'),
					rowNum:10,
					rowList:[10,20,30],
					width:520,
					sortname:'probResponsable',
					toolbar: [true,"top"],
					viewrecords:true,
					caption:'<bean:message key="selProbableResponsable"/>',
					sortorder:"desc",
					onSelectRow: function(rowid) {
						cargarGridMandJudAsocProbRespGenManJud(rowid);
					}
				});

				gridProbResCausaGenManJud = false;
			}else{
				jQuery("#gridProbResGenManJudCausa").jqGrid('setGridParam', { onSelectRow: function(rowid) { cargarGridMandJudAsocProbRespGenManJud(rowid); } })
				jQuery("#gridProbResGenManJudCausa").jqGrid('setGridParam', {url:'<%=request.getContextPath()%>/consultarInvolucradoPorCalidad.do?expedienteId='+idExpediente+'&calidadId='+calidadId+'',datatype: "xml"});
				$("#gridProbResGenManJudCausa").trigger("reloadGrid");		
			}
			
		}

		
		/**
		*Funcion que carga el grid con los mandamientos judiciales asociados al probable responsable
		*seleccionado
		*/
		function cargarGridMandJudAsocProbRespGenManJud(probRespId){

			var parametros = '';
			
			parametros += '&probRespId=' + probRespId;
			parametros += '&audienciaId=' + audienciaId;
			parametros += '&esAudiencia=' + esAudiencia;
			
			
			console.info("**CARGA MANDAMIENTOS DEL PROBABLE**");
			jQuery("#gridMandJudAsocProbRespGenManJud").jqGrid('setGridParam', { onSelectRow: function(rowid) {
					if(rowid){
						var datosManJudAsocProbResp = jQuery("#gridMandJudAsocProbRespGenManJud").jqGrid('getRowData',rowid);
						cargarGridRelDelitoPersonaManJudExp(datosManJudAsocProbResp.mandamientoId);
					} 
				}
		 	})
			jQuery("#gridMandJudAsocProbRespGenManJud").jqGrid('setGridParam', {url:'<%=request.getContextPath()%>/consultarMandamientosPersonaPorPersona.do?parametros='+parametros+'',datatype: "xml"});
			$("#gridMandJudAsocProbRespGenManJud").trigger("reloadGrid");

			limpiarGridRelDelitoPersonaManJudExp();
		}
		
		/**
		*Funcion que carga las Relaciones Delito-Persona asoociados al Mandamiento Judicial o a la audiencia
		*/
		function cargarGridRelDelitoPersonaManJudExp(mandamientoIdSeleccionado){

			jQuery("#gridRelDelitoPersonaManJudExp").jqGrid('setGridParam', {url:'<%=request.getContextPath()%>/consultarRelDelitoPersonaPorMandamiento.do?mandamientoJudicialId='+mandamientoIdSeleccionado+'',datatype: "xml"});
			$("#gridRelDelitoPersonaManJudExp").trigger("reloadGrid");
		}

		
		//Limpia el grid de mandamientos persona
		function limpiarGridMandJudAsocProbRespGenManJud(){
			jQuery("#gridMandJudAsocProbRespGenManJud").jqGrid('setGridParam', {url:'<%=request.getContextPath()%>/limpiarGrid.do?numeroColumnas=NUM_COL_MP',datatype: "xml"});
			$("#gridMandJudAsocProbRespGenManJud").trigger("reloadGrid");
		}

		//Limpia el grid de relaciones delito persona
		function limpiarGridRelDelitoPersonaManJudExp(){
			jQuery("#gridRelDelitoPersonaManJudExp").jqGrid('setGridParam', {url:'<%=request.getContextPath()%>/limpiarGrid.do?numeroColumnas=NUM_COL',datatype: "xml"});
			$("#gridRelDelitoPersonaManJudExp").trigger("reloadGrid");
		}
/********************************************** TERMINAN FUNCIONES CEJA MANDAMIENTOS JUDICIALES DEL EXPEDIENTE  ********************************************/
</script>


		<!-- Inicia cuerpo Mandamientos judiciales del expediente -->
		<div>
	    	<!-- Tabla por default -->
			<table id="adminGenManJudExpTable" width="100%" border="0" >
				<tr>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td width="25%" align="right"><strong>N&uacute;mero de
							Caso:</strong></td>
					<td width="25%"><input type="text"
						id="numeroCasoGenManJudTxtExp" tabindex="6" style="width: 250px" disabled="disabled"/></td>
					<td width="5%">&nbsp;</td>
					
					<td colspan="3" rowspan="7" align="center">
						<table id="gridProbResGenManJudCausa" border="0"></table>
						<div id="pagerGridProbResGenManJudCausa"></div>
					</td>
				</tr>
				<tr>
					<td align="right"><strong>N&uacute;mero de Causa:</strong></td>
					<td><input type="text" id="numeroCausaGenManJudTxtExp"
						tabindex="7" style="width: 250px"  disabled="disabled"/></td>
					<td>&nbsp;</td>
				</tr>
		
				<tr>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
			</table>
			<!-- Tabla que se inicializa oculta, para mostrarla en caso de consulta -->
			<table id="consultarManJudExpTable" width="100%" border="0" style="display:none" >
				<tr>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td width="25%" align="right">
						<strong><bean:message key="probableResponsable"/>:</strong>
					</td>
					<td width="25%">
						<input type="text" id="proRespManJudTxtExp" tabindex="8" style="width: 300px" disabled="disabled" />
					</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				
			</table>
			<!-- Tabla que siempre se muestra -->
			<table width="100%" border="0">	
				<tr>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td colspan="5" align="center">
						<table id="gridMandJudAsocProbRespGenManJud" border="0">
						</table>
						<div id="pagerGridMandJudAsocProbRespGenManJud"></div>
					</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td colspan="5" align="center">
						<table id="gridRelDelitoPersonaManJudExp" border="0">
						</table>
						<div id="pagerGridRelDelitoPersonaManJudExp"></div>
					</td>
				</tr>
			</table>
			
		</div>
		<!-- Termina cuerpo Mandamientos judiciales del expediente -->
		
	<script type="text/javascript">

		
	</script>