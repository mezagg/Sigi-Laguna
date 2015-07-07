<%@page import="mx.gob.segob.nsjp.comun.enums.elemento.TipoElemento"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="mx.gob.segob.nsjp.dto.usuario.UsuarioDTO"%>
<%@ page import="mx.gob.segob.nsjp.comun.enums.configuracion.Parametros"%>
<%@ page import="mx.gob.segob.nsjp.comun.enums.seguridad.Roles" %>
<%@ page import="mx.gob.segob.nsjp.dto.configuracion.ConfiguracionDTO"%>
<%@ page import="mx.gob.segob.nsjp.web.base.action.GenericAction"%>
<%@ page import="mx.gob.segob.nsjp.comun.enums.catalogo.EntidadFederativa"%>
<%@ page import="mx.gob.segob.nsjp.comun.enums.objeto.Objetos" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Ingresar Numerario</title>
	<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/jquery.windows-engine.css"/>
	<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/estilos.css"/>	
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/jquery-ui.css"/>
	<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/ui-lightness/jquery-ui-1.8.11.custom.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/layout_complex.css" media="screen" />

	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery-1.5.1.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery-ui-1.8.10.custom.js"></script>
	
	<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/jqgrid/ui.jqgrid.css" />
    <script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jqgrid/i18n/grid.locale-es.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jqgrid/jquery.jqGrid.js"></script>
    
	<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/multiselect/jquery.multiselect.css" />
	<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/multiselect/style.css" />
	<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/multiselect/prettify.css" />
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/jquery.timeentry.css"/>
	<script src="<%=request.getContextPath()%>/js/prettify.js"></script>
	<script src="<%=request.getContextPath()%>/js/jquery.multiselect.js"></script>  

	<script type="text/javascript" src="<%=request.getContextPath()%>/js/bloqueaTecla.js?n=1"></script>
	<script src="<%=request.getContextPath()%>/js/prettify.js"></script>
	<script src="<%=request.getContextPath()%>/js/jquery.multiselect.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/jquery.timeentry.js"></script>
	<!--Scrip para el idioma del calendario-->
	<script src="<%=request.getContextPath()%>/resources/js/wdCalendar/Plugins/jquery.ui.datepicker-es.js"></script>
	
	<script src="<%=request.getContextPath()%>/js/comun.js?n=1"></script>
	<script src="<%=request.getContextPath()%>/js/objetos.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/jquery.windows-engine.js"></script>
	
	
	<%
	String rolActivo = "";
	UsuarioDTO usuario = (UsuarioDTO) request.getSession().getAttribute("KEY_SESSION_USUARIO_FIRMADO"); 
	if (usuario != null 
			&& usuario.getRolACtivo() != null 
			&& usuario.getRolACtivo().getRol() != null
			&& usuario.getRolACtivo().getRol().getRolId() != null){
		rolActivo = usuario.getRolACtivo().getRol().getRolId().toString();
	}
	%>
		
	<script type="text/javascript">
	  
	var idNumerario="";
	var tipoObjeto="";
	var numeroExpediente="";
	var cadenaCustodia="";
	var fechaLevCadCus="";
	var origenEvdCadCus="";
	var deshabilitarCampos = window.parent.deshabilitarCamposPM;
	var ocultaAnularObjetoCadCus=null;
	
	var contextoPagina = "${pageContext.request.contextPath}";
	
	var esModoConsulta = '<%= request.getParameter("esModoConsulta")%>';
	var rolActivo = '<%=rolActivo%>';
	var entidadFedYuc = '<%=EntidadFederativa.YUCATAN.getValorId()%>';
	var elementoID=<%= request.getParameter("idNumerario")%>;
	//Permite saber si la pantalla del objeto es nueva o proviene de una consulta(idElemento mayor a cero)
	var elementoIDOriginal=<%= request.getParameter("idNumerario")%>;

	
	jQuery().ready(
			
		function () {
			
			if(elementoID!=null && elementoID!=0){
				$("#btnAdjuntarImagen").show();
				cargaGridArchivosDigitalesXElemento(elementoID);
			}
			
			numeroExpediente='<%= request.getParameter("numeroExpediente")%>';
			idNumerario='<%= request.getParameter("idNumerario")%>';
			cadenaCustodia='<%= request.getParameter("cadenaCustodia")%>';
			fechaLevCadCus='<%= request.getParameter("fechaLevCadCus")%>';
			origenEvdCadCus='<%= request.getParameter("origenEvdCadCus")%>';
			
			
			if(idNumerario=='null')
			{
				idNumerario=0;	
			}
			tipoObjeto='<%= request.getParameter("tipoObjeto")%>';
			
			$("#idFechaDate").datepicker({
				dateFormat: 'dd/mm/yy',
				yearRange: '1900:2100',
				changeMonth: true,
				changeYear: true,
				showOn: "button",
				buttonImage: "<%= request.getContextPath()%>/resources/images/date.png",
				buttonImageOnly: true			
			});
			cargaCondicion();
			cargaRelacionesHecho('<%=((ConfiguracionDTO) request.getSession().getAttribute(GenericAction.KEY_SESSION_CONFIGURACION_GLOBAL)).getEntidadFederativaDespliegueId() %>');
			var num=parent.num;
			
			//lineas para ocultar la opcion de anular objeto alconsultar desde una cadena de custodia
			//Viene con valor a 1 desde asentarRegCadenaCustodiaView.jsp
			//No es setteado en el menu intermedio
			ocultaAnularObjetoCadCus='<%= request.getParameter("anularConsultaCadCus")%>';
			
			if(parseInt( ocultaAnularObjetoCadCus ) == 1){
				$("#anularElemento").hide("")
			}
			
			$("#gridCatTipoEstudioTD").hide("");
					
			//revisamos si es una consulta
			if(idNumerario!=null && idNumerario!=0)
			{
				consultaNumerario();
				if(existeCadenaDeCustodiaPorIdObjeto(idNumerario))
				{
					$("#anularElemento").hide("");
					
					//Se muestra el grid de Tipo de Estudio
	    			tipoObjetoId= '<%= Objetos.NUMERARIO.getValorId() %>';
	    			$("#gridCatTipoEstudioTD").show("");
					consultarTipoEstudio(tipoObjetoId);
					
					if(existenEslabonesPorIdObjeto(idNumerario)){
						ocultaBotonesGenerico();
						inhabilitaCamposGenerico();
					}
				}
			}
			else
			{
				if(num!=null && num!="0"){
					$("#anularElemento").hide();
				}
			}
			
			
			if(esModoConsulta != null && esModoConsulta == "1"){
				ocultaBotonesGenerico();
				inhabilitaCamposGenerico();
			}
			
			if(rolActivo == '<%=Roles.PROCURADOR.getValorId()%>' || rolActivo == '<%=Roles.SUBPROCURADOR.getValorId()%>' ||
					rolActivo == '<%=Roles.DIRECTOR_GENERAL.getValorId()%>' || rolActivo == '<%=Roles.DIRECTOR_UI.getValorId()%>' || 
					rolActivo == '<%=Roles.COORDINADORAMPGENERAL.getValorId()%>'){
				$(":enabled").attr('disabled','disabled');
				$('input[type="submit"]').hide();
				$('input[type="button"]').hide();
			}
			if (rolActivo == '<%=Roles.DEFENSOR.getValorId()%>' || rolActivo == '<%=Roles.DEFENSORATE.getValorId()%>' || rolActivo == '<%=Roles.COORDINADORDEF.getValorId()%>' ){
				$("#anularElemento").hide();
			}
			
			
			//Instruccion pensada solo para el caso de policia ministerial
			if(deshabilitarCampos == true){
				$(":enabled").attr('disabled','disabled');
				$("img.ui-datepicker-trigger").hide();
			}
	});	
	
	/*
	*Funcion para mandar consultar el numerario
	*/
	function consultaNumerario()
	{
		$.ajax({
    		type: 'POST',
    		url: '<%=request.getContextPath()%>/ConsultaObjetoPorId.do',
    		data: 'idObjeto='+idNumerario,
    		dataType: 'xml',
    		async: false,
    		success: function(xml){
    			if(parseInt($(xml).find('code').text())==0)
	    		{
    				//seteamos la informacion del hecho
    				$(xml).find('NumerarioDTO').each(function(){
    							seteaDatosNumerario($(this));
		    	      });
	    		}
    		}	
    	});
	}
	
	function seteaDatosNumerario(xml)
	{
		$('#txtCantidadNumerario').val($(xml).find('cantidad').text());
		$('#txtMonedaNumerario').val($(xml).find('moneda').text());
		$('#txtCuentaTesoreriaNumerario').val($(xml).find('ctaTesoreria').text());
		
		//obtenemos la fecha y hora y mandamos setear los campos con sus respectivos valores
		var fechaBD=$(xml).find('fechaDeposito').text();
		seteaFechaHoraEnCamposPlugin(fechaBD,'idFechaDate','idHoraDate');
		//$('#idFechaDate').val($(xml).find('fechaDeposito').text());
		//$('#idHoraDate').val($(xml).find('fechaDeposito').text());
		$('#cbxCondicionNumerario').find("option[value='"+$(xml).find('valorByCondicionFisicaVal').find('idCampo').text()+"']").attr("selected","selected");
		$('#cbxRelacionHecho').find("option[value='"+$(xml).find('relacionHechoVal').find('idCampo').text()+"']").attr("selected","selected");
		if($(xml).find('almacen'))
			$(xml).find('almacen').remove();
		$('#txtBoxDescNumerario').val($(xml).find('descripcion').text());
	}

		
	function customRange(input) {
		return {minTime: (input.id == 'idHoraDateLapsoFin' ?
			$('#idHoraDate').timeEntry('getTime') : null),
			maxTime: (input.id == 'idHoraDate' ?
			$('#idHoraDateLapsoFin').timeEntry('getTime') : null)};
	}

	
	$(function(){
		$('.timeRange').timeEntry({beforeShow: customRange,timeSteps:[1,1,0],ampmPrefix: ' '});
	});

	     
	/*
	*Funcion que realiza la carga de la condicion del numerario
	*/
	function cargaCondicion() {
		$.ajax({
			async: false,
			type: 'POST',
			url: '<%= request.getContextPath()%>/consultarCondicion.do',
			data: '',
			dataType: 'xml',
			success: function(xml){
				$(xml).find('catCondicion').each(function(){
					$('#cbxCondicionNumerario').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
				});
			}
		});
	}	

	//COMIENZA FUNCIONES PARA EL GUARDADO DE LOS DATOS		
	function obtenerValoresNumerario()
	{
		var paramNumerario = "idNumerario="+idNumerario;  
		paramNumerario += '&glCantidad=' + $('#txtCantidadNumerario').val();
		paramNumerario += '&gcMoneda=' + $('#txtMonedaNumerario').val();
		paramNumerario += '&gcCtaTesoreria=' + $('#txtCuentaTesoreriaNumerario').val();
		paramNumerario += '&gcFechaDeposito='+ $('#idFechaDate').val();
		paramNumerario += '&gcHoraDeposito=' + $('#idHoraDate').val();
		paramNumerario += '&glCondicionFisica=' + $("#cbxCondicionNumerario option:selected").val();
		paramNumerario += '&gcDescripcion=' + $("#txtBoxDescNumerario").val();
		paramNumerario += '&relacionHechoId=' + $("#cbxRelacionHecho option:selected").val();
		
		if(cadenaCustodia!='null')
		{
			paramNumerario += '&cadenaCustodia=' + cadenaCustodia;
			paramNumerario += '&origenEvdCadCus=' + origenEvdCadCus;
			paramNumerario += '&fechaLevCadCus=' + fechaLevCadCus;
		}
		
			$.ajax({
				async: false,
				type: 'POST',
				url: '<%= request.getContextPath()%>/ingresarNumerario.do?numeroExpediente='+numeroExpediente +'',
				data: paramNumerario,
				dataType: 'xml',
				success: function(xml){
					var tipoNumerario = $("#txtMonedaNumerario").val();
					  var id = $(xml).find('NumerarioForm').find('idNumerario').text();
					  //Se ha agregado nuevo elemento
					  if(idNumerario==0 && id>0)
					  {	
							//Desde asentarRegCadenaCustodiaView.jsp 
						  if(parseInt( ocultaAnularObjetoCadCus ) == 1){
							  customAlert("Se guardó correctamente la información");
							  regresarControlCadenaCustodia();
							  //customAlert("Se guardó correctamente la información", '', regresarControlCadenaCustodia);
						  }else //Desde el ingresarMenuIntermedio.jsp
						  {
							  window.parent.customAlert('Se guardó correctamente la información', '',regresarControl(id,tipoNumerario) );
						  }
					  }
					  else if(idNumerario==0 && id==0)
					  {
						  window.parent.customAlert("Favor de revisar la información capturada");
					  }
					  else  //Actualizacion del elemento - solo desde el ingresarMenuIntermedio.jsp 
					  {   
						  
						//Desde asentarRegCadenaCustodiaView.jsp 
    					  if(parseInt( ocultaAnularObjetoCadCus ) == 1){
    						  regresarControlCadenaCustodiaActualizacion();
    						  customAlert("La información se actualizó correctamente");
    					  }else //Desde el ingresarMenuIntermedio.jsp
    					  {
    						  window.parent.customAlert('La información se actualizó correctamente', '',regresarControl(id,tipoNumerario) );
    					  }
					  }
					  
					  
					  elementoID = id;
					  idNumerario = id;
						  
				}
			});
	}

	/**
	* Funcion que permite controlar y evitar que el alert desaparecas y se cierre la ventana
	* solo si viene desde asentarRegCadenaCustodia.sjp
	*/
	function regresarControlCadenaCustodia(){
		if(elementoIDOriginal==0){
			$("#btnAdjuntarImagen").fadeIn(1000);
			$("#btnGuardarNumerario").fadeOut(1000);
		}
		window.parent.cargaObjetoCerrarVentana();
	}
	/**
	* Funcion que permite controlar y evitar que el alert desaparecas y se cierre la ventana
	* solo si viene desde ingresarMenuIntermedio.jsp
	*/	
	function regresarControl(id,tipoNumerario){
		if(elementoIDOriginal==0){
			$("#btnAdjuntarImagen").fadeIn(1000);
		}
		  window.parent.cargaNumerario(id,tipoNumerario);
	}
	
	function validaCamposObligatorios(){
		var condicionNumerario = $("#cbxCondicionNumerario option:selected").val();
		var mensaje = "";
		if(parseInt(condicionNumerario) == -1){
			mensaje += "<br />- Condición del numerario</li>";			
		}
		
		//Comienza segunda validacion para validacion de consistencia de expresiones regulares
		if(mensaje != ""){
			//mensaje += "\n\nEs necesario...."; FUTURAS VALIDACIONES
			if(parseInt( ocultaAnularObjetoCadCus ) ==1)
			{// alert especial cuando agregamos el objeto como evidencia en la cadena de custodia
				window.parent.alertDinamicoEvCadCus("<p align='left'>Favor de revisar los siguientes campos obligatorios: <br />"+mensaje+"</p>");
			}
			else
			{
				window.parent.customAlert("<p align='left'>Favor de revisar los siguientes campos obligatorios: <br />"+mensaje+"</p>");
			}
		}else{			
			obtenerValoresNumerario();
		}		
	}
	
	/*
	*Funcion que manda a eliminar logicamente el objeto en la BD
	*/
	function anularObjeto(){
		var paramNumerario="idObjeto="+idNumerario;
		$.ajax({		
			async: false,
			type: 'POST',
			url: '<%= request.getContextPath()%>/anularObjetoPorId.do',
			data: paramNumerario,
			dataType: 'xml',
			success: function(xml){
				//procederemos a tratar de eliminar la evidencia
				if(parseInt($(xml).find('bandera').text())==1)
				{
					//se anulo exitosamente el objeto , actualizamos el grid de menu intermedio y cerramos la ventana
					window.parent.cargaNumerario(idNumerario,"");
					window.parent.cerrarVentanaNumerario();
				}
				if(cadenaCustodia=='null'){
					//alert("cadCus::"+cadenaCustodia);
					window.parent.customAlert($(xml).find('mensajeOp').text());
				}
			}
		});

	}
	
	/*
	*Funcion para anular el objeto
	*/
	function solicitarAnlrObjeto(){
		//revisamos si es una consulta
		if(idNumerario!=null && idNumerario!=0)
		{
			//procederemos a tratar de eliminar la evidencia
			customConfirm ("¿Está seguro que desea anular el objeto?", "", validarObjEvdncNoEslbns);
		}
	}
	
	/*
	*Funcion que validara si el objeto es evidencia y NO tiene eslabones, de ser asi
	*se debe confirmar que se desea eliminar dado que el objeto ya esta en una cadena de custdia
	*/
	function validarObjEvdncNoEslbns()
	{
		var paramNumerario="idObjeto="+idNumerario;
		$.ajax({		
			async: false,
			type: 'POST',
			url: '<%= request.getContextPath()%>/validarObjPorIdEvdncNoEslbns.do',
			data: paramNumerario,
			dataType: 'xml',
			success: function(xml){
				//procederemos a tratar de eliminar la evidencia
				if(parseInt($(xml).find('bandera').text())==1)
				{
					//debemos mostrar un confirm
					customConfirm ("El objeto es ya una evidencia en alguna cadena de custodia <br/> ¿Está seguro que desea anular el objeto?", "", anularObjeto);
				}
				else if(parseInt($(xml).find('bandera').text())==2)
				{
					//se puede eliminar el objeto sin problemas
					anularObjeto();
				}
				else
				{
					window.parent.customAlert($(xml).find('mensajeOp').text());
				}
			}
		});
	}
	
	window.onload = function(){
		var selects = document.getElementsByTagName("textarea");
		for (var i = 0; i < selects.length; i++) { 
			if(selects[i].getAttribute("maxlength") > 0){
				selects[i].onkeydown = function(){
	                            if (this.value.length > this.getAttribute("maxlength")) 
	                                this.value = this.value.substring(0, this.getAttribute("maxlength"));
	                        }
	                        selects[i].onblur = function(){
	                            if (this.value.length > this.getAttribute("maxlength")) 
	                                this.value = this.value.substring(0, this.getAttribute("maxlength"));
	                        }
			}
		}
	}
	</script>  
</head>
 
<body>
				<table width="573"  height="256" border="0" align="center" cellpadding="0" cellspacing="0">
				    <tr><td>&nbsp;</td></tr>
					<tr height="12.5%">
					    <td height="21" colspan="3" align="center">
					    	<input type="button" value="Anular objeto" class="btn_Generico"id="anularElemento" onclick="solicitarAnlrObjeto()"/>&nbsp;&nbsp;
					    	<input type="button" id="btnGuardarNumerario" value="Guardar" onclick="validaCamposObligatorios();" class="btn_Generico" />
					    	&nbsp;&nbsp;<button value="Adjuntar imagen" id="btnAdjuntarImagen" class="btn_Generico" onclick="abreVentanaAdjuntarImagen()" style="display:none">Adjuntar imagen</button>
					    </td>
					</tr>
					<tr><td>&nbsp;</td></tr>
                    
					<tr height="12.5%">
					    <td width="29%" align="right">Fecha de Dep&oacute;sito:</td>
					    <td width="37%"><input type="text" id="idFechaDate" maxlength="10" readonly="readonly" width="50px" /></td>
					   <td width="34%" rowspan="6" align="center" valign="top">
					        Descripci&oacute;n:
					        <textarea cols="29" rows="9" id="txtBoxDescNumerario" maxlength="200"></textarea>
					   </td>
				   
					</tr>
					<tr height="12.5%">
					    <td width="29%" align="right">Hora de Dep&oacute;sito:</td>
					    <td width="37%"><input type="text" id="idHoraDate" size="10" class="timeRange" value="8:00 AM" /></td>
					</tr>
					<tr height="12.5%">
					    <td width="29%" align="right">Cantidad:</td>
					    <td width="37%"><input type="text" id="txtCantidadNumerario" maxlength="8" style="width:140px"/> Piezas</td>
					</tr>
					<tr height="12.5%">
					    <td width="29%" align="right">Moneda:</td>
					    <td width="37%"><input type="text" id="txtMonedaNumerario" maxlength="25" style="width:175px"/></td>
					</tr>
					<tr height="12.5%">
					    <td width="29%" align="right">Cuenta Tesorer&iacute;a:</td>
					    <td width="37%"><input type="text" id="txtCuentaTesoreriaNumerario" maxlength="30" style="width:175px"/></td>
					</tr>
					<tr height="12.5%">
					    <td width="29%" align="right">Condici&oacute;n:</td>
					    <td width="37%"><select id="cbxCondicionNumerario" style="width:180px">
					      <option value="-1">-Seleccione-</option>
					    </select></td>
					</tr>
					<tr id="trRelacionHecho" height="12.5%">
				    	<td align="right">Relaci&oacute;n del hecho: </td>
				    	<td >
				    		<select name="cbxRelacionHecho" id="cbxRelacionHecho" style="width:180px">
				    			<option value="-1">-Seleccione-</option>
				    		</select>
				    	</td>
				    </tr>
				</table>
		
	<!--  Muestra las imanenes asociadas al objeto asi como el grid del tipo de estudio -->	
<jsp:include page="/WEB-INF/paginas/detalleObjeto.jsp" flush="true"></jsp:include>


</body>
</html>