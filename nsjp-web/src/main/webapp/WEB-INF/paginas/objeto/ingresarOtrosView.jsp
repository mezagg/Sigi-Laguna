<%@page import="mx.gob.segob.nsjp.comun.enums.elemento.TipoElemento"%>
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
        <title>Ingresar Otros</title>
        <link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/jquery.windows-engine.css"/>
		<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/estilos.css"/>	
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/jquery-ui.css"/>
		<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/south-street/jquery-ui-1.8.10.custom.css" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/layout_complex.css" media="screen" />
	
        <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/estilos.css" media="screen" />
        <link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/multiselect/jquery.multiselect.css" />
        <link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/multiselect/style.css" />
        <link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/multiselect/prettify.css" />
        <script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery-1.5.1.js"></script>
        <script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery-ui-1.8.10.custom.js"></script>
        
        <link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/jqgrid/ui.jqgrid.css" />
      	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jqgrid/i18n/grid.locale-es.js"></script>
      	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jqgrid/jquery.jqGrid.js"></script>
        
        
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/bloqueaTecla.js?n=1"></script>
        <script src="<%=request.getContextPath()%>/js/prettify.js"></script>
        <script src="<%=request.getContextPath()%>/js/jquery.multiselect.js"></script>
        
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
        	
        	
	    	var idOtros="";
	    	var tipoObjeto="";
	    	var numeroExpediente="";
	    	var cadenaCustodia="";
	    	var fechaLevCadCus="";
	    	var origenEvdCadCus="";
	    	var deshabilitarCampos = window.parent.deshabilitarCamposPM;
	    	var ocultaAnularObjetoCadCus=null;
	    	var rolActivo = '<%=rolActivo%>';
	    	var entidadFedYuc = '<%=EntidadFederativa.YUCATAN.getValorId()%>';
	    	
	    	var contextoPagina = "${pageContext.request.contextPath}";
	    	
	    	var esModoConsulta = '<%= request.getParameter("esModoConsulta")%>';
	    	var elementoID=<%= request.getParameter("idOtros")%>;
	    	//Permite saber si la pantalla del objeto es nueva o proviene de una consulta(idElemento mayor a cero)
	    	var elementoIDOriginal=<%= request.getParameter("idOtros")%>;

    	
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
        		idOtros='<%= request.getParameter("idOtros")%>';
    			cadenaCustodia='<%= request.getParameter("cadenaCustodia")%>';
    			fechaLevCadCus='<%= request.getParameter("fechaLevCadCus")%>';
    			origenEvdCadCus='<%= request.getParameter("origenEvdCadCus")%>';
    			cargaRelacionesHecho('<%=((ConfiguracionDTO) request.getSession().getAttribute(GenericAction.KEY_SESSION_CONFIGURACION_GLOBAL)).getEntidadFederativaDespliegueId() %>');
    			
    			if(idOtros=='null')
    			{
    				idOtros=0;	
    			}
    			tipoObjeto='<%= request.getParameter("tipoObjeto")%>';
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
    			if(idOtros!=null && idOtros!=0)
    			{
    				consultaOtros();

    				if(existeCadenaDeCustodiaPorIdObjeto(idOtros))
    				{
    					$("#anularElemento").hide("");
    					
    					//Se muestra el grid de Tipo de Estudio
    	    			tipoObjetoId= '<%= Objetos.OTRO.getValorId() %>';
    	    			$("#gridCatTipoEstudioTD").show("");
    					consultarTipoEstudio(tipoObjetoId);
    					
    					if(existenEslabonesPorIdObjeto(idOtros)){
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
            });

            
        	/*
        	*Funcion para mandar consultar el objeto
        	*/
        	function consultaOtros()
        	{
        		$.ajax({
            		type: 'POST',
            		url: '<%=request.getContextPath()%>/ConsultaObjetoPorId.do',
            		data: 'idObjeto='+idOtros,
            		dataType: 'xml',
            		async: false,
            		success: function(xml){
            			if(parseInt($(xml).find('code').text())==0)
        	    		{
            				//seteamos la informacion del hecho
            				$(xml).find('ObjetoDTO').each(function(){
            						seteaDatosOtros($(this));
        		    	      });
        	    		}
            		}	
            	});
        	}

        	function seteaDatosOtros(xml)
        	{
        		$('#txtNombre').val($(xml).find('nombreObjeto').text());
        		if($(xml).find('almacen'))
        			$(xml).find('almacen').remove();
        		$('#txtBoxDescOtros').val($(xml).find('descripcion').text());
        		$('#cbxRelacionHecho').find("option[value='"+$(xml).find('relacionHechoVal').find('idCampo').text()+"']").attr("selected","selected");
        	}
        	
        	
            //	COMIENZAN FUNCIONES PARA EL GUARDADO DE LOS DATOS
            function obtenerValoresOtros(){		
                var paramOtros = "idOtros="+idOtros;  
                paramOtros += '&gcNombre=' + $("#txtNombre").val();
                paramOtros += '&gcDescripcion=' + $("#txtBoxDescOtros").val();
                paramOtros += '&relacionHechoId=' + $("#cbxRelacionHecho option:selected").val();
                
        		if(cadenaCustodia!='null')
        		{
        			paramOtros += '&cadenaCustodia=' + cadenaCustodia;
        			paramOtros += '&origenEvdCadCus=' + origenEvdCadCus;
        			paramOtros += '&fechaLevCadCus=' + fechaLevCadCus;
        		}
                
                $.ajax({
                    async: false,
                    type: 'POST',
                    url: '<%= request.getContextPath()%>/ingresarOtros.do?numeroExpediente='+numeroExpediente +'',
                    data: paramOtros,
                    dataType: 'xml',
                    success: function(xml){
      				  var id = $(xml).find('OtrosForm').find('idOtros').text();
      				
      				  //Se ha agregado nuevo elemento
      				  if(idOtros==0 && id>0)
      				  {
      					   					  
    					  //Desde asentarRegCadenaCustodiaView.jsp 
    					  if(parseInt( ocultaAnularObjetoCadCus ) == 1){
    						  customAlert("Se guard&oacute; correctamente la informaci&oacute;n");
    						  regresarControlCadenaCustodia();
    						  //customAlert("Se guard&oacute; correctamente la informaci&oacute;n", '', regresarControlCadenaCustodia);
    					  }else //Desde el ingresarMenuIntermedio.jsp
    					  {
    						  window.parent.customAlert('Se guard&oacute; correctamente la informaci&oacute;n', '',regresarControl(id,$("#txtNombre").val()));
    					  }
      				  }
      				  else if(idOtros==0 && id==0)
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
    						  window.parent.customAlert('La informaci&oacute;n se actualiz&oacute; correctamente', '',regresarControl );
    					  }
	  					  
	  				  }
      				  
      				  elementoID = id;
      				  idOtros = id;
      				  
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
	        		$("#btnGuardarOtros").fadeOut(1000);
        		}
        		window.parent.cargaObjetoCerrarVentana();
        	}
       
        	/**
        	* Funcion que permite controlar y evitar que el alert desaparecas y se cierre la ventana
        	* solo si viene desde ingresarMenuIntermedio.jsp
        	*/	
        	function regresarControl(id,nombre){
        		if(elementoIDOriginal==0){
	        		$("#btnAdjuntarImagen").fadeIn(1000);
        		}
        		window.parent.cargaOtros(id,nombre);
        	}

            function validaCamposObligatorios(){
            	var nombreOtros = $("#txtNombre").val();
            	var mensaje = "";
        		//Primera validacion por cada campo obligatorio		
        		if(nombreOtros == ""){
        			mensaje += "<br />- Nombre";
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
        			obtenerValoresOtros();
        		}
            }
            
            /*
        	*Funcion que manda a eliminar logicamente el objeto en la BD
        	*/
        	function anularObjeto(){
        		var paramOtros="idObjeto="+idOtros;
        		$.ajax({		
        			async: false,
        			type: 'POST',
        			url: '<%= request.getContextPath()%>/anularObjetoPorId.do',
        			data: paramOtros,
        			dataType: 'xml',
        			success: function(xml){
        				//procederemos a tratar de eliminar la evidencia
        				if(parseInt($(xml).find('bandera').text())==1)
        				{
        					//se anulo exitosamente el objeto , actualizamos el grid de menu intermedio y cerramos la ventana
        					window.parent.cargaOtros("","");
        					window.parent.cerrarVentanaOtros();
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
        		if(idOtros!=null && idOtros!=0)
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
        		var paramOtros="idObjeto="+idOtros;
        		$.ajax({		
        			async: false,
        			type: 'POST',
        			url: '<%= request.getContextPath()%>/validarObjPorIdEvdncNoEslbns.do',
        			data: paramOtros,
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
                    <table width="400px"  height="210px" border="0" align="center" cellpadding="0" cellspacing="0">
                        <tr><td>&nbsp;</td></tr>
                    	<tr height="12.5%">
                            <td height="21" colspan="3" align="center">
                            	<input type="button" value="Anular objeto" class="ui-button ui-corner-all ui-widget"id="anularElemento" onclick="solicitarAnlrObjeto()"/>&nbsp;&nbsp;
                            	<input type="button" id="btnGuardarOtros" value="Guardar" onclick="validaCamposObligatorios();" class="ui-button ui-corner-all ui-widget"/>
                            	&nbsp;&nbsp;<button value="Adjuntar imagen" id="btnAdjuntarImagen" class="ui-button ui-corner-all ui-widget" onclick="abreVentanaAdjuntarImagen()" style="display:none">Adjuntar imagen</button>
                            </td>
                        </tr>
                        <tr><td>&nbsp;</td></tr>
                        <tr height="12.5%">
                            <td width="26%"  align="right" height="36">Nombre: </td>
                            <td width="40%">
                            	<input type="text" maxlength="30" style="width: 180px;" id="txtNombre"/>
                            </td>
                        </tr>
                        <tr id="trRelacionHecho" height="12.5%">
                			<td width="26%" height="36" align="right">Relaci&oacute;n del hecho: </td>
                			<td width="40%">
                				<select name="cbxRelacionHecho" id="cbxRelacionHecho" style="width:180px">
                  					<option value="-1">-Seleccione-</option>
                				</select>
                			</td>
              			</tr>
                        <tr height="12.5%">
                          <td height="36" align="right">Descripci&oacute;n:</td>
                          <td><textarea name="txtBoxDescOtros" cols="29" rows="7" id="txtBoxDescOtros" maxlength="200"></textarea></td>
                        </tr>
                    </table>
        	
	<!--  Muestra las imanenes asociadas al objeto asi como el grid del tipo de estudio -->	
	<jsp:include page="/WEB-INF/paginas/detalleObjeto.jsp" flush="true"></jsp:include>


</body>
</html>