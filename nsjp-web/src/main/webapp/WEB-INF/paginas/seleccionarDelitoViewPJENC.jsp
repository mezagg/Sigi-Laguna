<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Prueba Visor de elementos</title>
<!--	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/cssGrid/jquery-ui.css" media="screen" />-->
<!--	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/cssGrid/ui.jqgrid.css" media="screen" />-->
<!--	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/cssGrid/ui.multiselect.css" media="screen" />-->

<!--	<script type="text/javascript" src="<%= request.getContextPath()%>/js/jsGrid/jquery.js"></script>-->
<!--	<script type="text/javascript" src="<%= request.getContextPath()%>/js/jsGrid/jquery-ui-1.8.1.custom.min.js"></script>-->
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jqgrid/i18n/grid.locale-es.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/js/jsGrid/jquery.jqGrid.min.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/js/jsGrid/jquery.tablednd.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/bloqueaTecla.js?n=1"></script>
		
<!--	<script type="text/javascript" src="<%= request.getContextPath()%>/js/jsGrid/jquery.contextmenu.js"></script>-->
<!--	<script type="text/javascript" src="<%= request.getContextPath()%>/js/jsGrid/ui.multiselect.js"></script>-->
<!--    <script type="text/javascript" src="<%= request.getContextPath()%>/js/jsGrid/jquery.layout-1.3.0.js"></script>-->

	<script type="text/javascript">
            $.jgrid.no_legacy_api = true;
           // $.jgrid.useJSON = true;
	</script>
        
    <script type="text/javascript">

    	var contDelitosGraves=0;
		var idExpedienteop="";
    	var numeroExpedienteId=0;
    	var deshabilitarCampos = window.parent.deshabilitarCamposPM;
    	
		$(document).ready(function(){
			if(deshabilitarCampos == true){
				$("#btnGuardarDelitosAg").hide();
				$("#pasar").hide();
				$("#pasarD").hide();
			}
			idExpedienteop='<%= request.getParameter("idExpedienteop")%>';
			numeroExpedienteId='<%= request.getParameter("idNumeroExpediente")%>';
			if(idExpedienteop=='null')
			{
				idExpedienteop='<%= request.getSession().getAttribute("idExpedienteConsulop")%>';
			}
			
			jQuery("#gridCatDelitos").jqGrid({ 
				url:'<%= request.getContextPath()%>/CargarDelitoPJENC.do?idExpediente='+idExpedienteop, 
				datatype: "xml", 
				colNames:['Clave Delito','Clave','Delito', '&iquest;Es grave?','&iquest;Es grave?','Delito Principal','Tipo','DelitoId'], 
				colModel:[ 	{name:'Clave', sortable: false,	index:'clave', width:20, align:'center',hidden:true},
				           	{name:'ClaveBD',index:'1',align:'center',width:30}, 
							{name:'Delito',index:'2',align:'left',width:140}, 
							//{name:'Gravedad',index:'gravedad', width:100}
							{name:'Gravedad',index:'3',width:45,align:'center',formatter:'checkbox',hidden:true},
							{name:'GravedadFormateada',index:'4',width:60,align:'center'},
							{name:'Principal',index:'5',width:50,hidden: true,align:'center'},
							{name:'Tipo',index:'6',width:50,hidden: true,align:'center'},
							{name:'DelitoId',index:'7',width:50,hidden: true,align:'center'}
						],
				pager: jQuery('#pagergridCatDelitos'),
				rowNum:10,
				rowList:[10,20,30,40,50,60],
				//autowidth: true,
				caption:"LISTA DE DELITOS",
				sortname: 'Clave',
				viewrecords: true,
				id: 'pagergridCatDelitos',
				sortorder: "desc",
				onSelectRow: function(id){
					//detEvi(id);
					}
			}).navGrid('#pagergridCatDelitos',{edit:false,add:false,del:false});

			//asigna a expediente op
			if(idExpedienteop=='null'){
				idExpedienteop=idExpediente;
				numeroExpedienteId=numeroExpediente;
						
			}
			
			jQuery("#gridDelitosAgraviados").jqGrid({ 
				url:'<%= request.getContextPath()%>/ConsultaDelitoPorExpedienteGrid.do?idNumeroExpediente='+idExpedienteop+'&numeroExpedienteId='+numeroExpedienteId+'',
				datatype: "xml",
				colNames:['Clave Delito','Clave','Delito', '&iquest;Es grave?','&iquest;Es grave?','Delito Principal','Tipo','DelitoId'], 
				colModel:[ 	{name:'Clave', sortable: false,	index:'clave', width:20, align:'center',hidden:true},
				           	{name:'ClaveBD',index:'clave', width:50}, 
							{name:'Delito',index:'delito', width:170}, 
							{name:'Gravedad',index:'gravedad',align:'center',formatter:'checkbox',hidden:true},
							{name:'GravedadFormateada',index:'gravedadFormateada',width:60,align:'center'},
							{name:'Principal',index:'principal',width:100,align:'center'},
							{name:'Tipo',index:'tipo',hidden: true,align:'center'},
							{name:'DelitoId',index:'delitoId',hidden: true,align:'center'}
						],
				//pager: jQuery('#pgrid2'),
				rowNum:50,
				rowList:[10,20,30,40,50,60],
				//autowidth: true,
				caption:"LISTA DE DELITOS DENUNCIADOS",
				sortname: 'Clave',
				viewrecords: true,
				//id: 'pgrid2',
				onSelectRow: function(){
					//detEvi(id);
					},
				sortorder: "desc"
			});

			 $("#gview_gridCatDelitos .ui-jqgrid-bdiv").css('width', '300px');
			 $("#gview_gridDelitosAgraviados .ui-jqgrid-bdiv").css('width', '450px');
			//.navGrid('#pgrid2',{edit:false,add:false,del:false}); arreglarProblema Paginacion
			//Instruccion pensada solo para el caso de policia ministerial
			if(deshabilitarCampos == true){
				$("#pasar").attr("disabled","");
				$("#pasarD").attr("disabled","");
				$("#btnGuardarDelitosAg").attr("disabled","");		
			}
			
		});

		function addRowRigthToLeft(){
			
			var row = jQuery("#gridCatDelitos").jqGrid('getGridParam','selrow');
			
			if (row) { 
				var ret = jQuery("#gridCatDelitos").jqGrid('getRowData',row);
				//alert(retd.id);
				jQuery("#gridDelitosAgraviados").jqGrid('addRowData',ret.DelitoId,ret);
				jQuery("#gridCatDelitos").jqGrid('delRowData',ret.DelitoId); 
				
			} else { alert("Por favor seleccione un renglon");} 	 				
		} 

		function addRowLeftToRigth(){
			
			var rowd = jQuery("#gridDelitosAgraviados").jqGrid('getGridParam','selrow');

			
			if (rowd) { 
				
				var retd = jQuery("#gridDelitosAgraviados").jqGrid('getRowData',rowd);
				//alert(retd.id);
				//jQuery("#gridCatDelitos").jqGrid('addRowData',retd.Clave,retd);
				//jQuery("#gridDelitosAgraviados").jqGrid('delRowData',retd.Clave);
				
				jQuery("#gridCatDelitos").jqGrid('addRowData',retd.DelitoId,retd);
				jQuery("#gridDelitosAgraviados").jqGrid('delRowData',retd.DelitoId);
			
			} else { alert("Por favor seleccione un renglon");} 	 				
		} 


		function delitoPrincipal(){
			
			var rowd = jQuery("#gridDelitosAgraviados").jqGrid('getGridParam','selrow');
			
			if (rowd) { 
				var retd = jQuery("#gridDelitosAgraviados").jqGrid('getRowData',rowd);
				 document.getElementById('cveDelitoPrincipal').value=retd.ClaveBD;
				 document.getElementById('delitoPrincipal').value=retd.Delito;
				 if(retd.Gravedad == "Yes")
				 	document.getElementById('graveDelitoPrincipal').value="Si";
				 else
					 document.getElementById('graveDelitoPrincipal').value=retd.Gravedad;				
			}	
			else { 
				alert("Por favor seleccione un renglon");
			} 
		}

		function limpiar(){
			document.getElementById('cveDelitoPrincipal').value="";
			document.getElementById('delitoPrincipal').value="";
		 	document.getElementById('graveDelitoPrincipal').value="";	
		}
		
	</script>
</head>
<body>
	<table border="0"  width="1500px">
		<tr>
			<td height="20" colspan="4" align="left" ><table width="75%"><tr>
			<td  align="right"><input type="button" id="btnGuardarDelitosAg" value="Guardar" onclick="guardarDelitosAgraviadosExp();" class="ui-button ui-corner-all ui-widget"/>
				
			</td>
			
		</tr></table>
				
			</td>
			
		</tr>
		<tr>
			<td></td>
		</tr>
		<tr>
			<td valign="top" width="5%"> 
				<table id="gridCatDelitos" width="300px"></table>
				<div id="pagergridCatDelitos"></div>
			</td>
			<td width="3%">
				<input type="button" id="pasar" value=">>" onclick="addRowRigthToLeft();" class="ui-button ui-corner-all ui-widget"/> <br/>
				<input type="button" id="pasarD" value="<<" onclick="addRowLeftToRigth();" class="ui-button ui-corner-all ui-widget"/>
			</td>
			<td valign="top" width="10%">
				<table id="gridDelitosAgraviados" width="450px"></table>
				<div id="pgrid2"></div>
				
				<span id="leyendaUnDelitoGrave">Se debe canalizar a la Unidad de Fiscales Investigadores</span>
				<span id="leyendaNingunDelitoGrave">Si no hay reincidencia por parte del <bean:message key="probableResponsableTitulo"/>,<br/> 
				se debe canalizar a la <bean:message key="leyendaNingunDelitoGraveRestaurativa"/></span>
			</td>
			<td valign="top" width="35%">
				<div id="actividadesSugeridas" style="width: 300px;height:27px;" class="ui-jqgrid-titlebar ui-widget-header ui-corner-top ui-helper-clearfix">
					<span class="ui-jqgrid-title">ACTIVIDADES SUGERIDAS :</span>
				</div>
				<div style="overflow:auto; width: 300px; height: 150px" id="actividadesXDelitosDelExpediente">
					<span ></span>
				</div>
			</td>
		</tr>
	</table>
</body>
</html>