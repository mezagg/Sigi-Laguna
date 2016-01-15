<%@page import="mx.gob.segob.nsjp.comun.enums.elemento.TipoElemento"%>
<%@ page import="org.omg.CORBA.Request"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Ingresar Arma</title>
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/estilos.css" media="screen" />
	
		<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/jquery.windows-engine.css"/>
	<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/estilos.css"/>	
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/jquery-ui.css"/>
	<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/south-street/jquery-ui-1.8.10.custom.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/layout_complex.css" media="screen" />
	
	<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/multiselect/jquery.multiselect.css" />
	<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/multiselect/style.css" />
	<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/multiselect/prettify.css" />
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery-1.5.1.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery-ui-1.8.10.custom.js"></script>
	
	<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/jqgrid/ui.jqgrid.css" />
    <script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jqgrid/i18n/grid.locale-es.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jqgrid/jquery.jqGrid.js"></script>
    
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/bloqueaTecla.js?n=1"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/comun.js?n=1"></script>
	<script src="<%=request.getContextPath()%>/js/prettify.js"></script>
	<script src="<%=request.getContextPath()%>/js/jquery.multiselect.js"></script>	
	<script src="<%=request.getContextPath()%>/js/objetos.js"></script>
	
	<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/jquery.windows-engine.js"></script>
	
	
	<!-- JS para la validacion de solo numeros -->
	
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
	
	var idArma="";
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
	var elementoID=<%= request.getParameter("idArma")%>;
	//Permite saber si la pantalla del objeto es nueva o proviene de una consulta(idElemento mayor a cero)
	var elementoIDOriginal=<%= request.getParameter("idArma")%>;


	
	jQuery().ready(			
		function () {
			
			if(elementoID!=null && elementoID!=0){
				$("#btnAdjuntarImagen").show();
				cargaGridArchivosDigitalesXElemento(elementoID);
			}
			
			//Instruccion pensada solo para el caso de policia ministerial
			if(deshabilitarCampos == true){
				$(":enabled").attr('disabled','disabled');
			}			
			
			
			
			numeroExpediente='<%= request.getParameter("numeroExpediente")%>';
			idArma='<%= request.getParameter("idArma")%>';
			cadenaCustodia='<%= request.getParameter("cadenaCustodia")%>';
			fechaLevCadCus='<%= request.getParameter("fechaLevCadCus")%>';
			origenEvdCadCus='<%= request.getParameter("origenEvdCadCus")%>';
			
			if(idArma=='null')
			{
				idArma=0;	
			}
			tipoObjeto='<%= request.getParameter("tipoObjeto")%>';
			
			cargaTiposArma();
			cargaMarcasArma();
			cargaCondicion();
			cargaRelacionesHecho('<%=((ConfiguracionDTO) request.getSession().getAttribute(GenericAction.KEY_SESSION_CONFIGURACION_GLOBAL)).getEntidadFederativaDespliegueId() %>');
			/*$("#cbxTipoArma, #cbxMarcaArma, #cbxCondicionArma").multiselect({ 
				multiple: false, 
				header: "Seleccione", 
				position: { 
					my: 'top', 
					at: 'top' 
				},
				selectedList: 1 
			});*/
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
			if(idArma!=null && idArma!=0)
			{
				consultaArma();
				
				if(existeCadenaDeCustodiaPorIdObjeto(idArma))
				{
					$("#anularElemento").hide("");
					
					//Se muestra el grid de Tipo de Estudio
	    			tipoObjetoId= '<%= Objetos.ARMA.getValorId() %>';
	    			$("#gridCatTipoEstudioTD").show("");
					consultarTipoEstudio(tipoObjetoId);
					
					if(existenEslabonesPorIdObjeto(idArma)){
						ocultaBotonesGenerico();
						inhabilitaCamposGenerico();
					}
				}
	
			}
			else{
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
	});

	/*
	*Funcion para mandar consultar el arma
	*/
	function consultaArma()
	{
		$.ajax({
    		type: 'POST',
    		url: '<%=request.getContextPath()%>/ConsultaObjetoPorId.do',
    		data: 'idObjeto='+idArma,
    		dataType: 'xml',
    		async: false,
    		success: function(xml){
    			if(parseInt($(xml).find('code').text())==0)
	    		{
    				//seteamos la informacion del hecho
    				$(xml).find('ArmaDTO').each(function(){
    						//if($(this).find('elementoId').text()==idArma)
    						//{
    							seteaDatosArma($(this));
    						//}
		    	      });
	    		}
    		}	
    	});
	}
	
	function seteaDatosArma(xml)
	{
		$('#txtModeloArma').val($(xml).find('modelo').text());
		$('#txtMatriculaArma').val($(xml).find('matricula').text());
		$('#txtCalibreArma').val($(xml).find('calibre').text());
		if($(xml).find('almacen'))
			$(xml).find('almacen').remove();
		$("#txtBoxDescArma").val($(xml).find('descripcion').text());
		
		$('#cbxTipoArma').find("option[value='"+$(xml).find('tipoArma').find('idCampo').text()+"']").attr("selected","selected");
		$('#cbxMarcaArma').find("option[value='"+$(xml).find('marca').find('idCampo').text()+"']").attr("selected","selected");
		$('#cbxCondicionArma').find("option[value='"+$(xml).find('valorByCondicionFisicaVal').find('idCampo').text()+"']").attr("selected","selected");
		$('#cbxRelacionHecho').find("option[value='"+$(xml).find('relacionHechoVal').find('idCampo').text()+"']").attr("selected","selected");

	}

	/*
	*Funcion que realiza la carga del combo de tipos de arma
	*/
	function cargaTiposArma() {
		  
		$.ajax({
			async: false,
			type: 'POST',
			url: '<%= request.getContextPath()%>/consultarTiposArma.do',
			data: '',
			dataType: 'xml',
			success: function(xml){
				$(xml).find('catTiposArma').each(function(){
					$('#cbxTipoArma').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
				});
			}
		});
	}

	/*
	*Funcion que realiza la carga de marcas del arma
	*/
	function cargaMarcasArma() {
		  
		$.ajax({
			async: false,
			type: 'POST',
			url: '<%= request.getContextPath()%>/consultarMarcasArma.do',
			data: '',
			dataType: 'xml',
			success: function(xml){
				$(xml).find('catMarcasArma').each(function(){
					$('#cbxMarcaArma').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
				});
			}
		});
	}

	/*
	*Funcion que realiza la carga de la condicion del arma
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
					$('#cbxCondicionArma').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
				});
			}
		});
	}


//	COMIENZA FUNCIONES PARA EL GUARDADO DE LOS DATOS		
	function obtenerValoresArma(){		
		var paramArma = "idArma="+idArma; 
		paramArma += '&glTipoArmaId=' + $("#cbxTipoArma option:selected").val();
		paramArma += '&glMarcaArmaId=' + $("#cbxMarcaArma option:selected").val();
		paramArma += '&gcModeloArma=' + $('#txtModeloArma').val();
		paramArma += '&gcMatriculaArma=' + $('#txtMatriculaArma').val();
		paramArma += '&gcCalibreArma=' + $('#txtCalibreArma').val();
		paramArma += '&glCondicionFisicaArmaId=' + $("#cbxCondicionArma option:selected").val();
		paramArma += '&gcDescripcionArma=' + $("#txtBoxDescArma").val();
		paramArma += '&relacionHechoId=' + $("#cbxRelacionHecho option:selected").val();
		
		if(cadenaCustodia!='null')
		{
			paramArma += '&cadenaCustodia=' + cadenaCustodia;
			paramArma += '&origenEvdCadCus=' + origenEvdCadCus;
			paramArma += '&fechaLevCadCus=' + fechaLevCadCus;
		}

		$.ajax({
		
			async: false,
			type: 'POST',
			url: '<%= request.getContextPath()%>/ingresarArma.do?numeroExpediente='+numeroExpediente +'',
			data: paramArma,
			dataType: 'xml',
			success: function(xml){
				  var tipoArma = $("#cbxTipoArma option:selected").text();
				  var id = $(xml).find('ArmaForm').find('glTipoArmaId').text();
				  //Se ha agregado nuevo elemento
				  if(idArma==0 && id>0)
				  {	
					  					  
					  //Desde asentarRegCadenaCustodiaView.jsp 
					  if(parseInt( ocultaAnularObjetoCadCus ) == 1){
						  customAlert("Se guard&oacute; correctamente la informaci&oacute;n");
						  regresarControlCadenaCustodia();
						  //customAlert("Se guard&oacute; correctamente la informaci&oacute;n", '', regresarControlCadenaCustodia);
					  }else //Desde el ingresarMenuIntermedio.jsp
					  {
						  window.parent.customAlert('Se guard&oacute; correctamente la informaci&oacute;n', '',regresarControl(id,tipoArma) );
					  }
				  }
				  else if(idArma==0 && id==0)
				  {
					  window.parent.customAlert("Favor de revisar la informaci&oacute;n capturada");
				  }
				  else  //Actualizacion del elemento - solo desde el ingresarMenuIntermedio.jsp 
				  {   
					  
					//Desde asentarRegCadenaCustodiaView.jsp 
					  if(parseInt( ocultaAnularObjetoCadCus ) == 1){
						  regresarControlCadenaCustodiaActualizacion();
						  customAlert("La informaci&oacute;n se actualiz&oacute; correctamente");
					  }else //Desde el ingresarMenuIntermedio.jsp
					  {
						  window.parent.customAlert('La informaci&oacute;n se actualiz&oacute; correctamente', '',regresarControl(id,tipoArma) );
					  }
				  }
				  
				  elementoID = id;
				  idArma = id;
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
			$("#btnGuardarArma").fadeOut(1000);
		}
		window.parent.cargaObjetoCerrarVentana();
	}
	/**
	* Funcion que permite controlar y evitar que el alert desaparecas y se cierre la ventana
	* solo si viene desde ingresarMenuIntermedio.jsp
	*/	
	function regresarControl(id,tipoArma){
		if(elementoIDOriginal==0){
			$("#btnAdjuntarImagen").fadeIn(1000);
		}
		window.parent.cargaArma(id,tipoArma);
	}
	
	function validaCamposObligatorios(){
		var tipoArma = $("#cbxTipoArma option:selected").val();
		var marcaArma = $("#cbxMarcaArma option:selected").val();
		var condicionArma = $("#cbxCondicionArma option:selected").val();		
		var mensaje = "";
		//Primera validacion por cada campo obligatorio		
		if(parseInt(tipoArma) == -1){
			mensaje += "<br />- Tipo del arma";			
		}
		if(parseInt(marcaArma) == -1){
			mensaje += "<br />- Marca del arma";			
		}
		if(parseInt(condicionArma) == -1){
			mensaje += "<br />- Condici&oacute;n del arma";			
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
			obtenerValoresArma();
		}
	}
	
	/*
	*Funcion que manda a eliminar logicamente el objeto en la BD
	*/
	function anularObjeto(){
		var paramArma="idObjeto="+idArma;
		$.ajax({		
			async: false,
			type: 'POST',
			url: '<%= request.getContextPath()%>/anularObjetoPorId.do',
			data: paramArma,
			dataType: 'xml',
			success: function(xml){
				//procederemos a tratar de eliminar la evidencia
				if(parseInt($(xml).find('bandera').text())==1)
				{
					//se anulo exitosamente el objeto , actualizamos el grid de menu intermedio y cerramos la ventana
					window.parent.cargaArma(idArma,"");
					window.parent.cerrarVentanaArma();
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
		if(idArma!=null && idArma!=0)
		{
			//procederemos a tratar de eliminar la evidencia
			customConfirm ("&iquest;Est&aacute; seguro que desea anular el objeto?", "", validarObjEvdncNoEslbns);
		}
	}
	
	/*
	*Funcion que validara si el objeto es evidencia y NO tiene eslabones, de ser asi
	*se debe confirmar que se desea eliminar dado que el objeto ya esta en una cadena de custdia
	*/
	function validarObjEvdncNoEslbns()
	{
		var paramArma="idObjeto="+idArma;
		$.ajax({		
			async: false,
			type: 'POST',
			url: '<%= request.getContextPath()%>/validarObjPorIdEvdncNoEslbns.do',
			data: paramArma,
			dataType: 'xml',
			success: function(xml){
				//procederemos a tratar de eliminar la evidencia
				if(parseInt($(xml).find('bandera').text())==1)
				{
					//debemos mostrar un confirm
					customConfirm ("El objeto es ya una evidencia en alguna cadena de custodia <br/> &iquest;Est&aacute; seguro que desea anular el objeto?", "", anularObjeto);
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
				 <table width="560"  height="233" border="0" align="center" cellpadding="0" cellspacing="0">
				 	 <tr><td>&nbsp;</td></tr>
				     <tr height="12.5%">
				        <td height="21" colspan="3" align="center">
				         	<input type="button" value="Anular objeto" class="ui-button ui-corner-all ui-widget"id="anularElemento" onclick="solicitarAnlrObjeto()"/>&nbsp;&nbsp;
				         	<input type="button" id="btnGuardarArma" value="Guardar" class="ui-button ui-corner-all ui-widget" onclick="validaCamposObligatorios();" />
				         	&nbsp;&nbsp;<button value="Adjuntar imagen" id="btnAdjuntarImagen" class="ui-button ui-corner-all ui-widget" onclick="abreVentanaAdjuntarImagen()" style="display:none">Adjuntar imagen</button>
				         	
				         </td>
				     </tr>
				     <tr><td>&nbsp;</td></tr>
				     <tr height="12.5%">
				         <td width="42%" align="right">Tipo:</td>
				         <td width="28%"><select id="cbxTipoArma" style="width:180px">
				          <option value="-1">-Seleccione-</option>
				        </select></td>
				        <td width="30%" rowspan="6" align="center" valign="top">
				            Descripci&oacute;n:
				            <textarea cols="29" rows="9" id="txtBoxDescArma" maxlength="200"></textarea>
				        </td>
				    </tr>
				    <tr height="12.5%">
				        <td width="42%" align="right">Marca:</td>
				        <td width="28%"><select 
                         id="cbxMarcaArma" style="width:180px">
				          <option value="-1">-Seleccione-</option>
				        </select></td>
				    </tr>
				    <tr height="12.5%">
				        <td width="42%" align="right">Modelo:</td>
				        <td width="28%"><input type="text" id="txtModeloArma" maxlength="25" style="width:175px"/></td>
				    </tr>
				    <tr height="12.5%">
				        <td width="42%" align="right">Calibre:</td>
				        <td width="28%"><input type="text" id="txtCalibreArma" onKeyPress="return numerosDecimales(event);" maxlength="5" style="width:135px"/> mm.</td>
				    </tr>
				    <tr height="12.5%">
				        <td width="42%" align="right">Matr&iacute;cula:</td>
				        <td width="28%"><input type="text" id="txtMatriculaArma" maxlength="20" style="width:175px" onkeypress="return letrasNumero(event);"/></td>
				    </tr>
				    <tr height="12.5%">
				        <td width="42%" align="right">Condici&oacute;n:</td>
				        <td width="28%"><select id="cbxCondicionArma" style="width:180px">
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
