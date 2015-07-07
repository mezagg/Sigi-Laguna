<%@ page import="mx.gob.segob.nsjp.web.reinsercionsocial.action.AsignarProgramaReinsercionAction"%>
<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-nested.tld" prefix="nested"%>

<script type="text/javascript">

	var datosPrograma = {
		id:"", Tipo:"", Nombre:"", FechaInicio:"", FechaTermino:"", Puntos:""
	};
	var sentenciaId = <%=request.getParameter("sentenciaId")%>;

	jQuery().ready(
		function () {
				jQuery("#gridProgramas").jqGrid({
					datatype: "xml", 
					rowNum:10,
					rowList:[10,20,30,40,50,60,70,80,90,100],
					height:'auto',
					minWidth: 500,
					width: 800, 
					minHeight: 200,						
					caption:"Programas Asignados",
					viewrecords: true,
					onSelectRow: function(id){
						var programaSeleccionado = jQuery("#gridProgramas").jqGrid('getRowData',id); 
						copiarDatosPrograma(programaSeleccionado, id);
					},
					sortorder: "asc",		           		
					autowidth:true,
	    			shrinkToFit:true,
					url:'<%=request.getContextPath()%>/llenarGridAsignacionPrograma.do?sentenciaId='+sentenciaId,
					mType : "POST",
					postData : {
						gridID : "asignacionDePuntos",
						sentenciaId : sentenciaId,
						bCertificadoEmitido : false
					},
					colNames:['Tipo','Nombre','Fecha Inicio','Fecha T&eacute;rmino', 'Puntos', 'asignacionProgramaId'], 
					colModel:[ 	{name:'Tipo',index:'1', sortable:false}, 
								{name:'Nombre',index:'2', sortable:false}, 
								{name:'FechaInicio',index:'3', sortable:false}, 
								{name:'FechaTermino',index:'4', sortable:false},
								{name:'Puntos',index:'5', sortable:false},
								{name:'asignacionProgramaId',index:'6', hidden:true}
							],
					pager: jQuery('#pagerProgramas'),
					gridComplete: function(){
						cambiarBotonesDialog("grid");
					}
				}).navGrid('#pagerProgramas',{edit:false,add:false,del:false});
				
				
		}
	);
	

	
	function cambiarBotonesDialog(opcion) {
		var botones = {};	
		switch(opcion){
			case "grid" :
				botones = [
					    	{
						        text: "Aceptar",
						        id:"btn_ccAceptar",
						        click: function() {
						        	asignarCertificadoPrograma();
						        }
						    },
						    {
						        text: "Cancelar",
						        id:"btn_ccCancelar",
						        click: function() { 
						        			$(this).dialog("close");
						        			datosPrograma = {};
						        }
						    }			    
						];
				break;
		}
		
		jQuery("#customConfirm").dialog('option', 'buttons', botones);
	}

	function copiarDatosPrograma(datosSeleccionados, id){
		datosPrograma.id = getValorDelGrid(datosSeleccionados.asignacionProgramaId);
		datosPrograma.Tipo = getValorDelGrid(datosSeleccionados.Tipo);
		datosPrograma.Nombre = getValorDelGrid(datosSeleccionados.Nombre);
		datosPrograma.FechaInicio = getValorDelGrid(datosSeleccionados.FechaInicio);
		datosPrograma.FechaTermino = getValorDelGrid(datosSeleccionados.FechaTermino);
		datosPrograma.Puntos = 0;
	}

	function getValorDelGrid(valor){
		if(valor != undefined && valor != "undefined"){
		 	if(valor.indexOf("div") > -1){
		 		var from = valor.indexOf(">", valor.indexOf("div"));
				var to = valor.indexOf("<", from);
				if(from != -1 && to != -1) {
					valor = valor	.substring(from+1, to);
				}
			}
		}
		return valor;
	}

	function asignarCertificadoPrograma(){
		if(datosPrograma.id != undefined 
	        	&& datosPrograma.id != "undefined" 
	        	&& datosPrograma.id != ""){
			persistirCertificadoEmitido(datosPrograma.id);
		} else {
			customAlert("Se debe de seleccionar un programa.");
		}
	}

	function persistirCertificadoEmitido(asignacionProgramaId) {
		bloquearPantalla();		
	    $.ajax({
			url: '<%= request.getContextPath()%>/persistirCertificadoEmitido.do',
			type: 'POST',
			async: false,
        	dataType: "xml",
        	data: 'asignacionProgramaId='+asignacionProgramaId,
			success: function(xml){
				jQuery("#customConfirm").dialog("close");
				customAlert("La informaci&oacute;n del programa se actualiz&oacute; con &eacute;xito", "Certificado Emitido", seleccionaActuacion("&asignacionProgramaId="+datosPrograma.id));
			}
		});
	}

</script>

<fieldset>
	<table width="99%" border="0">
		<tr>
			<td align="right">
				<table id="gridProgramas"></table>
				<div id="pagerProgramas" style="vertical-align: top;"></div>
			</td>
		</tr>	
	</table>
</fieldset>
