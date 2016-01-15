<%@page import="mx.gob.segob.nsjp.comun.enums.convenios.Convenios"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Solicitar Permisos</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/estilos.css" media="screen" />
	<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/multiselect/jquery.multiselect.css" />
	<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/multiselect/style.css" />
	<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/multiselect/prettify.css" />
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/jquery-ui.css"/>
	<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/south-street/jquery-ui-1.8.10.custom.css" />
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/jquery.timeentry.css"/>  
	<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/jqgrid/ui.jqgrid.css" />
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery-1.5.1.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery-ui-1.8.10.custom.js"></script>
	<script src="<%=request.getContextPath()%>/js/prettify.js"></script>
	<script src="<%=request.getContextPath()%>/js/jquery.multiselect.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/jquery.timeentry.js"></script>
	<!--Scrip para el idioma del calendario-->
	<script src="<%=request.getContextPath()%>/resources/js/wdCalendar/Plugins/jquery.ui.datepicker-es.js"></script>
	
	<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/jqgrid/i18n/grid.locale-es.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/jqgrid/jquery.jqGrid.js"></script>	
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/currencyFormatMask.js"></script>  
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/wdCalendar/Plugins/jquery.ui.timepicker.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/bloqueaTecla.js?n=1"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/comun.js?n=1"></script>	
	<script type="text/javascript">
	
	var idNumeroExpedienteOp;
	var numeroExpediente;
	var area;
	var idRenglon=1;
	var idConvenio=parent.idConvenio;
	var fechaCompromisoid;
		$(document).ready(function() {
			actualizaDetalleConvenioTiempo();
			//guardamos las variables del paso de parametros
			numeroExpediente='<%= request.getParameter("numeroExpediente")%>';
			idNumeroExpedienteOp='<%= request.getParameter("numeroExpedienteId")%>';
			area='<%= request.getParameter("area")%>';
			
			consultarDetalleConvenio();

			$("#fCumplimientoID").datepicker({
				dateFormat: 'dd/mm/yy',
				yearRange: '1900:2100',
				changeMonth: true,
				changeYear: true,
				showOn: "button",
				buttonImage: "<%= request.getContextPath()%>/resources/images/date.png",
				buttonImageOnly: true			
			});

			$('#hCumplimientoID').timepicker({
	            onSelect: function(time, inst) {
	                $('#floating_selected_time').html('You selected ' + time);
	            }
	        });
		
			$("#fechaInicio,#fechaFin").datepicker({
				dateFormat: 'dd/mm/yy',
				yearRange: '1900:2100',
				changeMonth: true,
				changeYear: true,
				buttonImageOnly: true			
			});
			
			$("#fechaPago").datepicker({
				dateFormat: 'dd/mm/yy',
				yearRange: '1900:2100',
				changeMonth: true,
				changeYear: true,
				showOn: "button",
				buttonImage: "<%= request.getContextPath()%>/resources/images/date.png",
				buttonImageOnly: true			
			});
			
			jQuery("#gridPagos").jqGrid({ 
				url:'<%= request.getContextPath()%>/consultarPagosDeDetalleConvenio.do?idConvenio='+idConvenio, 
				datatype: "xml", 
				colNames:['Cantidad por pago','Fecha pago','Cumplio','Fecha Cumplimiento','Observaciones'], 
				colModel:[ 	{name:'Cantidad',index:'cantidad', width:140}, 
							{name:'Fecha',index:'fecha', width:100},
							{name:'Cumplio',index:'Cumplio', width:140}, 
							{name:'FechaC',index:'FechaC', width:220},
							
							{name:'Observaciones',index:'Observaciones', width:100},
						],
				pager: jQuery('#pagerGridPagos'),
				rowNum:0,
				rowList:[0,0,0],
				autowidth: true,
				sortname: 'fecha',
				viewrecords: true,
				sortorder: "desc",
				ondblClickRow: function(rowid) {
					fechaCompromisoid=rowid;
					consultaPopopPago(rowid);
				} 
			});
			cargaComboProbableResp();
		});
		//termina funcion onready
		
		/*
		*Funcion para llenar los combobox de Probable Responsable y Victimas del expediente
		*/
		function cargaComboProbableResp()
		{
			$('#cbxProbResponsable').empty();
			$('#cbxProbResponsable').append('<option value="-1" selected="selected">-Seleccione-</option>');
			$('#cbxVictima').empty();
			$('#cbxVictima').append('<option value="-1" selected="selected">-Seleccione-</option>');
			$.ajax({
				type: 'POST',
				url: '<%= request.getContextPath()%>/ConsultaInvolucradosPorCalidadExpediente.do',
				data: 'idNumeroExpediente='+idNumeroExpedienteOp+'&calidadInvolucrado=PROBABLE_RESPONSABLE',
				dataType: 'xml',
				async: false,
				success: function(xml){
					$(xml).find('involucradosDTO').find('involucradoDTO').each(function(){
			              var nombreCompleto=$(this).find('nombresDemograficoDTO').find('nombre').text()+" ";
						  nombreCompleto+=$(this).find('apellidoPaterno').text()+" ";
						  nombreCompleto+=$(this).find('apellidoMaterno').text()+" ";
			              if($(this).find('valorIdCalidad').find('idCampo').text()=="213")//PR
			              {
							$('#cbxProbResponsable').append('<option value="' + $(this).find('elementoId').text() + '">' + nombreCompleto+ '</option>');	  
			              }
			              else if($(this).find('valorIdCalidad').find('idCampo').text()=="214")//victima
			              {
			            	  $('#cbxVictima').append('<option value="' + $(this).find('elementoId').text() + '">' + nombreCompleto+ '</option>');
			              }
					});
				}
			});
		}
	
	/*
	*Funcion que valida la informacion requerida para realizar la captura de la informacion 
	*general del convenio
	*/
	function capturarConvenio()
	{
		var idPR=$("#cbxProbResponsable option:selected").val();
		var idVictima=$("#cbxVictima option:selected").val();
		
		if(parseInt(idPR)!=-1 && parseInt(idVictima)!=-1)
		{
			$("#tableInformacionGeneral").show();	
		}
		else
		{
			var probableResponsableProp = '<bean:message key="msjProbableResponsable"/>';
			customAlert("Debe seleccionar el nombre del "+ probableResponsableProp +" el nombre de la v&iacute;ctima, para los cuales se generar&aacute; el convenio");
		}
	}
	
	/*
	*Funcion que valida la inforamcion requerida para ingresar un pago
	*/
	function  validaIngresarPago()
	{
		if($("#fechaInicio").val()=="" || $("#fechaInicio").val().length==0)
		{
			return false;
		}
		if($("#fechaFin").val()=="" || $("#fechaFin").val().length==0)
		{
			return false;
		}
		if($("#txtBox").val()=="" || $("#txtBox").val().length<2)
		{
			return false;
		}
		return true;
	}
	
	/*
	*Funcion que lanza el popup para capturar los datos de un pago y posteriormente
	*agregarlos al grid del convenio, para ser persistidos en un paso posterior en la BD
	*/
	function ingresarPago()
	{
		if(validaIngresarPago())
		{
			//generamos el Dialogo
			$( "#dialogDos-confirm" ).dialog({
				resizable: false,
				height:150,
				width:700,
				modal: true,
				buttons: {
					"Guardar": function() {
						agregaPagoAGrid($("#txtBoxPagoPoppup").val(),$("#fechaPago").val());
						$("#txtBoxPagoPoppup,#fechaPago").val("");
						$( this ).dialog( "close" );
						$( "#dialogDos:ui-dialog" ).dialog( "destroy" );
					},
					"Cancelar": function() {
						$( this ).dialog( "close" );
						$( "#dialogDos:ui-dialog" ).dialog( "destroy" );
					}
				}
			});
			$( ".ui-icon-closethick,.ui-dialog-titlebar-close" ).hide();
		}
		else
		{
			customAlert("Debe seleccionar la fecha de inicio, la fecha de fin y la cantidad total a pagar del convenio");
		}
	}
	
	/*
	*Funcion que agrega los datos de un pago al grid correspondiente
	*/
	function agregaPagoAGrid(pago,fecha)
	{
		if($("#fechaPago").val()=="" || $("#fechaPago").val().length==0)
		{
			customAlert("Debe proporcionar la Fecha de Pago y el monto");
			return;
		}
		if($("#txtBoxPagoPoppup").val()=="" || $("#txtBoxPagoPoppup").val().length<2)
		{
			customAlert("Debe proporcionar la Fecha de Pago y el monto");
			return;
		}
		var mydata = [
					  {id:idRenglon,Cantidad:pago,Fecha:fecha}						  
	              ];
		for(var i=0;i<=mydata.length;i++)
			jQuery("#gridPagos").jqGrid('addRowData',i+1,mydata[i]);
		idRenglon++;
	}


	function consultaPopopPago(idRow)
	{
		
		var param="idFechaCompromiso="+idRow;
		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/consultarPagoPorId.do',
			data: param,
			dataType: 'xml',
			async: false,
			success: function(xml){
				var fecha = $(xml).find('fechaCumplimientoString').text();
				$("#fCumplimientoID").val(fecha);
			    var fecha1 = $(xml).find('horaCumplimientoString').text();
			    $("#hCumplimientoID").val(fecha1);
			    $("#descripcionConvenioID").val( $(xml).find('observaciones').first().text());
			    
			  	//generamos el Dialogo
			    $( "#actualizaConvenio" ).dialog({
					resizable: false,
					height:300,
					width:700,
					modal: true,
					buttons: {
						"Guardar": function() {
							actualizaDetalleConvenio();
							ocultaboton();
							$( this ).dialog( "close" );
							jQuery("#gridPagos").trigger('reloadGrid');
						},
						"Cancelar": function() {
							$( this ).dialog( "close" );
						}
					}
				});
				$( ".ui-icon-closethick,.ui-dialog-titlebar-close" ).hide();
				$("#fCumplimientoID").val(fecha);
				$("#hCumplimientoID").val(fecha1);
				$("#descripcionConvenioID").val( $(xml).find('observaciones').first().text());
			}
		});
	}
	
	function ocultaboton(){
		 $("#btnGuardarConvenio").css("display", "none");
	}

	/*
	*Funcion para llenar los combobox de Probable Responsable y Victimas del expediente
	*/
	function consultarPrimerConvenio()
	{
		var param="numeroExpediente="+numeroExpediente;
		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/consultarPrimerConvenio.do',
			data: param,
			dataType: 'xml',
			async: false
		});
	}

	function consultarDetalleConvenio(){
		
		var param="idConvenio="+idConvenio;
		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/consultarDetalleConvenio.do',
			data: param,
			dataType: 'xml',
			async: false,
			success: function(xml){
				//da formato a fecha
				var fecha = $(xml).find('fechaInicioString').first().text();
			    $("#fechaInicio").val(fecha);
			    var fecha1 = $(xml).find('fechaFinString').first().text();
			    $("#fechaFin").val(fecha1);
			    $("#txtBox").val($(xml).find('convenioDTO').find('compromisoPeriodicoDTO').find('monto').first().text()); 
				$("#cbxProbResponsable").val($(xml).find('convenioDTO').find('involucradoPR').find('nombresDemograficoDTO').find('nombre').first().text()+" "+$(xml).find('convenioDTO').find('involucradoPR').find('nombresDemograficoDTO').find('apellidoPaterno').first().text()+" "+$(xml).find('convenioDTO').find('involucradoPR').find('nombresDemograficoDTO').find('apellidoMaterno').first().text());
				$("#cbxVictima").val($(xml).find('convenioDTO').find('involucradoVictima').find('nombresDemograficoDTO').find('nombre').first().text()+" "+$(xml).find('convenioDTO').find('involucradoPR').find('nombresDemograficoDTO').find('apellidoPaterno').first().text()+" "+$(xml).find('convenioDTO').find('involucradoPR').find('nombresDemograficoDTO').find('apellidoMaterno').first().text());

				var tipocon= $(xml).find('convenioDTO').find('tipoConvenio').find('idCampo').text();
				if(tipocon==<%=Convenios.CONCILIACION.getValorId()%>){
					$("#radConvenio").attr("checked",true); 
				}else{ $("#radMediacion").attr("checked",true);}
			}
		});
	}


	function actualizaDetalleConvenio(){
		var param="idConvenio="+idConvenio;
		param+="&fechaCompromisoid="+fechaCompromisoid;
		param+="&fCumplimiento="+$("#fCumplimientoID").val();
		param+="&hCumplimiento="+$("#hCumplimientoID").val();
		param+="&descripcionConvenio="+$("#descripcionConvenioID").val();
		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/actualizaConvenio.do',
			data: param,
			dataType: 'xml',
			async: false
		});
		  
		$("#fCumplimientoID").val("");
		$("#hCumplimientoID").val("");
		$("#descripcionConvenioID").val("Observaciones:");
	}
	
	function actualizaDetalleConvenioTiempo(){
		var param="";
		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/horaActual.do',
			data: param,
			dataType: 'xml',
			async: false,
			success: function(xml){
				var horayfecha=$(xml).find('string').text();
				var horaActual=horayfecha.split(",")[0];
				var fechaActual=horayfecha.split(",")[1];
				$("#fCumplimientoID").val(horaActual);
				$("#hCumplimientoID").val(fechaActual);
			}
		});
	}
	
	</script>

</head>
<body bgcolor="#CCCCCC">
  
	<div id="divconPago">
		<br/>
		<table border="0" width="100%">
			<tr>
				<td width="32%" align="right"><bean:message key="probableResponsableTitulo" /> : </td>
				<td width="22%">
					<input type="text" id="cbxProbResponsable" disabled="disabled"/> 					
				</td>
				<td width="15%" align="right">V&iacute;ctima : </td>
				<td width="30%">
					<input type="text" id="cbxVictima" disabled="disabled">					
				</td>
				<td width="1%">
					<input type="button" value="Capturar Convenio" onclick="capturarConvenio();" style="display: none;" class="ui-button ui-corner-all ui-widget"/>
				</td>
			</tr>
		</table>
		<br/>

		<table width="98%" height="133" border="0" id="tableInformacionGeneral">
			<tr>
				<td align="right">&nbsp;</td>
		   	    <td align="left">
		   	    	<table width="200" border="0" align="center">
		  				<tr>
    						<td align="center">Conciliaci&oacute;n</td>
  						</tr>
  						<tr>
    						<td align="center"><input type="radio" name="tipoConvenio" id="radConvenio" value="<%=Convenios.CONCILIACION.getValorId()%>" disabled="disabled"/></td>
  						</tr>
					</table>
				</td>
				<td align="right">&nbsp;</td>
				<td align="left">
					<table width="200" border="0" align="center">
  						<tr>
    						<td align="center">Mediaci&oacute;n</td>
  						</tr>
  						<tr>
    						<td align="center"><input type="radio" name="tipoConvenio" id="radMediacion" value="<%=Convenios.MEDIACION.getValorId()%>" disabled="disabled"/></td>
  						</tr>
					</table>
				</td>
		   		<td align="right">Cantidad total a pagar : </td>
		   		<td align="left">
			    	<input type="textbox" style="text-align:right" id="txtBox"
			    			onkeyup="javascript:return mask(this.value,this);"
         					onblur="javascript:return mask(this.value,this);" disabled="disabled"/>
		   		</td>
	   		</tr>
	   		<tr>
		   		<td align="right">Fecha de inicio : </td>
		   		<td align="left">
			   		<input type="text" id="fechaInicio" name="fechaInicio" maxlength="10" readonly="readonly" style="width: 180px;"  disabled="disabled"/>
		   		</td>
		   		<td>Fecha de fin :</td>
		   		<td colspan="2"><input type="text" id="fechaFin" name="fechaFin" maxlength="10" readonly="readonly" style="width: 180px;"  disabled="disabled"/></td>		 		  
		   		<td align="left"></td>
	   		</tr>
	   		<tr>
		   		<td colspan="2"></td>
		   		<td colspan="2">
			   		<table id="gridPagos" width="370px"></table>
			   		<div id="pagerGridPagos"></div>
		   		</td>
		   		<td colspan="2" align="right">&nbsp;</td>
	   		</tr>
       		<tr>
       			<!--
		   		<td colspan="2"><input type="button" id="btnGenerarConstanciaConvenio2" value="Generar constancia de seguimiento a convenio" style="display: none;" class="ui-button ui-corner-all ui-widget"/></td>
		   		<td colspan="2"></td>
		  		-->
		   		<td colspan="2" align="right">
		     		<input type="button" id="btnGuardarConvenio" value="Guardar" onclick="ocultaboton()" style="display: none;" class="ui-button ui-corner-all ui-widget"/>
		   		</td>
	   		</tr>
   		</table>
		<br/>
	</div>
	<!-- DIV para el dialogo de asociacion de un delito por persona -->
	<div id="dialogDos-confirm" title="Ingresar Pago" >
		<p align="left">
			Fecha de Pago : <input type="text" id="fechaPago" name="fechaPago" maxlength="10" readonly="readonly" style="width: 180px;" />
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			Cantidad a Pagar :  <input type="textbox" style="text-align:right" id="txtBoxPagoPoppup"  
            onkeyup="javascript:return mask(this.value,this);"
            onblur="javascript:return mask(this.value,this);"/> 
		</p>
	</div>

	<div id="actualizaConvenio" style="display: none;">
		<table width="702" border="0">
  			<tr>
   				<!--   <td width="54">Cumplio:</td><td width="27"><input type="checkbox" id="cumplioConvenioID" /> </td> -->
    			<td width="138">Fecha Cumplimiento:</td>
    			<td width="164"><input type="text" id="fCumplimientoID" /> </td>
    			<td width="133">Hora Cumplimiento:</td>
    			<td width="160"><input type="text" id="hCumplimientoID"/> </td>
  			</tr>
  			<tr>
    			<td colspan="4" align="center"><textarea cols="40" rows="4" id="descripcionConvenioID">Observaciones: </textarea></td>
  			</tr>
		</table>
	</div>
</body>

<script type="text/javascript">
	$( "#dialogDos-confirm" ).dialog();
	$( "#dialogDos-confirm" ).dialog( "destroy" );
</script>
</html>