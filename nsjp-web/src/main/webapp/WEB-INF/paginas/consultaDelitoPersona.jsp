<!-- 
 Nombre del Programa 	: consultaDelitoPersona.jsp                                   
 Autor               	: Ricardo Gama                                           
 Compania            	: Ultrasist                                                

 Proyecto            	: NSJP                    Fecha: 28/09/2012 
 Marca de cambio        : N/A                                                     
 Descripcion General    : JSP que contiene la funcionalidad para consultar un
 							  DelitoPersona                  
 Programa Dependiente 	: N/A                                                      
 Programa Subsecuente 	: N/A                                                      
 Cond. de ejecucion     : N/A                                                      
 Dias de ejecucion      : N/A                             Horario: N/A       
 -->
 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@page import="mx.gob.segob.nsjp.dto.usuario.RolDTO"%>
<%@page import="mx.gob.segob.nsjp.dto.usuario.UsuarioDTO"%>
<%@ page import="mx.gob.segob.nsjp.comun.enums.seguridad.Roles" %>
<%@ page import="mx.gob.segob.nsjp.comun.enums.configuracion.Parametros" %>

<head>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Consultar Delito Persona</title>

<!--Hoja de estilos de Layout-->
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/layout_complex.css" media="screen" />

<!--Hoja de estilos ultrasist-->
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/estilos.css" media="screen" />

<!--Hoja de estilos windows engine (frames)-->
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/resources/css/jquery.windows-engine.css"/>

<!--Hojas de estilos para los componentes UI de Jquery-->
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/jquery-ui.css"/>
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/themes/1.8.10/south-street/jquery-ui.css" />

<!--Hoja de estilos para el grid-->
<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/jqgrid/ui.jqgrid.css" />

<!--scripts de java script-->
<script type="text/javascript" src="<%= request.getContextPath()%>/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/themes/1.8.10/jquery-ui.min.js"></script>

<!--script de windows engine (frames)-->
<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/jquery.windows-engine.js"></script>

<!--script de jquery UI-->
<script type="text/javascript" src="<%= request.getContextPath()%>/themes/1.8.10/jquery-ui.min.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/js/prettify.js"></script>

<!--script de los combos multiselect-->
<!-- script type="text/javascript" src="<%= request.getContextPath()%>/js/jquery.multiselect.js"></script-->

<!--scripts del gird-->
<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jqgrid/i18n/grid.locale-es.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jqgrid/jquery.jqGrid.min.js"></script>

<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.layout-1.3.0.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/bloqueaTecla.js?n=1"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery.blockUI.js"></script>				
<script type="text/javascript" src="<%=request.getContextPath()%>/js/comun.js?n=1"></script>

<% 
	UsuarioDTO usuario = (UsuarioDTO) request.getSession().getAttribute("KEY_SESSION_USUARIO_FIRMADO");
	RolDTO rolDTO = usuario.getRolACtivo().getRol();
	Long idRolActivo = rolDTO.getRolId();
 %>

<script type="text/javascript">

	var idDelitoPersona = '<%= request.getParameter("idDelitoPersona")%>';
	var idNumeroExpediente = '<%= request.getParameter("idNumeroExpediente")%>';
	var rolActivo = <%=idRolActivo%>;
	var muestraCombosDelitoPersona;
	var deshabilitarCampos = window.parent.deshabilitarCamposPM; 

	//Carga las funciones correspondientes al iniciar la pagina
	$(document).ready(function(){
		//alert("idDelitoPersona: " + idDelitoPersona);
		//alert("idNumeroExpediente: " + idNumeroExpediente);
		//cargamos el combo con los delitos del expediente
		cargaComboDelitosExpedientesRDPPV();
		//Cargamos el combo de victimas del expediente
		cargaComboVictimasRDPPV();
		//Cargamos el combo de PR del expediente
		cargaComboProbableResponsableRDPPVDelito();
		//cargamos el combo con las formas de participacion del involucrado
		cargaComboFormaParticipacionRDPPV();
		//cargamos el combo de clasificacion delito
		cargaComboClasificacionDelitoRDPPV();
		//cargamos el combo del lugar de delito
		cargaComboLugarDelitoRDPPV();
		//cargamos el combo de modalidad delito
		cargaComboModalidadDelitoRDPPV();
		//cargamos el combo de modus delito
		cargaComboModusDelitoRDPPV();
		//cargamos el combo de causa delito
		cargaComboCausaDelitoRDPPV();
		//Permite mostrar la informaci&oacute;n del delito persona
		pintaDatosDelitoPersona();
		//Permite deshabilitar los cambos
		//inhabilitaCamposGenerico();
		
		//Para Defensoria, se manda la opcion para Modo Consulta
		if(deshabilitarCampos == true || deshabilitarCampos =='true'){
			activarModoConsulta();
		}
		
		if (rolActivo == '<%=Roles.ATPENAL.getValorId()%>'){
			ocultaCombosInnecesariosDelitoPersonaPorPersona();
		}
		// Asociaci&oacute;n de eventos
		$("#btnGuardar").click(RelacionarDelitoRDPPV);
	}); //Fin Ready

	
	//cargamos el combo con los delitos del expediente
	function cargaComboDelitosExpedientesRDPPV()
	{
		$("#cbxDelitosExpRDPPV").empty();
		$('#cbxDelitosExpRDPPV').append('<option value="-1" selected="selected">-Seleccione-</option>');
		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/ConsultaDelitoPorExpediente.do',
			data: 'idNumeroExpediente='+idNumeroExpediente,
			dataType: 'xml',
			async: false,
			success: function(xml){
				$(xml).find('DelitoDTO').each(function(){
					$('#cbxDelitosExpRDPPV').append('<option value="' + $(this).find('delitoId').text() + '">' + $(this).find('nombreDelito').text() + '</option>');
				});
			}
		});
	}
	//cargamos el combo con las formas de participacion del involucrado
	function cargaComboFormaParticipacionRDPPV()
	{
		$("#cbxFormasParticipacionRDPPV").empty();
		$('#cbxFormasParticipacionRDPPV').append('<option value="-1" selected="selected">-Seleccione-</option>');
		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/ConsultarCatalogoModoParticipacionDelito.do',
			dataType: 'xml',
			async: false,
			success: function(xml){
				$(xml).find('ModoParticipacionDelito').each(function(){
					$('#cbxFormasParticipacionRDPPV').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
					
				});
			}
		});
	}
	
	//cargamos el combo de victimas del expediente
	function cargaComboVictimasRDPPV()
	{
		var banderaVictimas = false;
		$("#cbxVictimasExpRDPPV").empty();
		$('#cbxVictimasExpRDPPV').append('<option value="-1" selected="selected">-Seleccione-</option>');
		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/ConsultaInvolucradosPorCalidadExpediente.do',
			data: 'idNumeroExpediente='+idNumeroExpediente+'&calidadInvolucrado=VICTIMA',
			dataType: 'xml',
			async: false,
			success: function(xml){
				var contaProbResps=0;
				$(xml).find('involucradoDTO').each(function(){
					var nombreCompleto="";
					if($(this).find('tipoPersona').text() == "0"){
						nombreCompleto=$(this).find('organizacionDTO').find('nombreOrganizacion').text();
						
					}else{
						nombreCompleto=$(this).find('nombresDemograficoDTO').find('nombre').text()+" ";
						nombreCompleto+=$(this).find('apellidoPaterno').text()+" ";
						nombreCompleto+=$(this).find('apellidoMaterno').text()+" ";
					}
					if(nombreCompleto==null || nombreCompleto=="null" || nombreCompleto=="" || nombreCompleto=="   "){
						nombreCompleto="An&oacute;nimo";
					}
					$('#cbxVictimasExpRDPPV').append('<option value="' + $(this).find('elementoId').first().text() + '">' + nombreCompleto+ '</option>');
					contaProbResps++;
				});
			}
		});		
		return banderaVictimas;
	}
	
	//cargamos el combo de probables responsables del expediente
	function cargaComboProbableResponsableRDPPVDelito(){
		$('#cbxProbableResponsableRDPPV').empty();
		$('#cbxProbableResponsableRDPPV').append('<option value="-1" selected="selected">-Seleccione-</option>');
		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/ConsultaInvolucradosPorCalidadExpediente.do',
			data: 'idNumeroExpediente='+idNumeroExpediente+'&calidadInvolucrado=PROBABLE_RESPONSABLE',
			dataType: 'xml',
			async: false,
			success: function(xml){
				var contaProbResps=0;
				$(xml).find('involucradoDTO').each(function(){
					var nombreCompleto="";
					if($(this).find('tipoPersona').text() == "0"){
						nombreCompleto=$(this).find('organizacionDTO').find('nombreOrganizacion').text();
						
					}else{
						nombreCompleto=$(this).find('nombresDemograficoDTO').find('nombre').text()+" ";
						nombreCompleto+=$(this).find('apellidoPaterno').text()+" ";
						nombreCompleto+=$(this).find('apellidoMaterno').text()+" ";
					}
					$('#cbxProbableResponsableRDPPV').append('<option value="' + $(this).find('elementoId').first().text() + '">' + nombreCompleto+ '</option>');
					contaProbResps++;
					
					
					
				});
			}
		});		
	}
	
	//cargamos el combo de clasificacion del delito
	function cargaComboClasificacionDelitoRDPPV()
	{
		$("#cbxClasificacionRDPPV").empty();
		$('#cbxClasificacionRDPPV').append('<option value="-1" selected="selected">-Seleccione-</option>');
		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/ConsultarCatalogoClasificacionDelito.do',
			dataType: 'xml',
			async: false,
			success: function(xml){
				$(xml).find('ModoParticipacionDelito').each(function(){
					$('#cbxClasificacionRDPPV').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
					
				});
			}
		});
	}

	//cargamos el combo de lugar del delito
	function cargaComboLugarDelitoRDPPV()
	{
		$("#cbxLugarRDPPV").empty();
		$('#cbxLugarRDPPV').append('<option value="-1" selected="selected">-Seleccione-</option>');
		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/ConsultarCatalogoLugarDelito.do',
			dataType: 'xml',
			async: false,
			success: function(xml){
				$(xml).find('ModoParticipacionDelito').each(function(){
					$('#cbxLugarRDPPV').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
					
				});
			}
		});
	}

	//cargamos el combo de Modalidad del delito
	function cargaComboModalidadDelitoRDPPV()
	{
		$("#cbxModalidadRDPPV").empty();
		$('#cbxModalidadRDPPV').append('<option value="-1" selected="selected">-Seleccione-</option>');
		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/ConsultarCatalogoModalidadDelito.do',
			dataType: 'xml',
			async: false,
			success: function(xml){
				$(xml).find('ModoParticipacionDelito').each(function(){
					$('#cbxModalidadRDPPV').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
					
				});
			}
		});
	}

	//cargamos el combo de Modus del delito
	function cargaComboModusDelitoRDPPV()
	{
		$("#cbxModusRDPPV").empty();
		$('#cbxModusRDPPV').append('<option value="-1" selected="selected">-Seleccione-</option>');
		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/ConsultarCatalogoModusDelito.do',
			dataType: 'xml',
			async: false,
			success: function(xml){
				$(xml).find('ModoParticipacionDelito').each(function(){
					$('#cbxModusRDPPV').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
					
				});
			}
		});
	}

	//cargamos el combo de Causa del delito
	function cargaComboCausaDelitoRDPPV()
	{
		$("#cbxCausaRDPPV").empty();
		$('#cbxCausaRDPPV').append('<option value="-1" selected="selected">-Seleccione-</option>');
		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/ConsultarCatalogoCausaDelito.do',
			dataType: 'xml',
			async: false,
			success: function(xml){
				$(xml).find('ModoParticipacionDelito').each(function(){
					$('#cbxCausaRDPPV').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
					
				});
			}
		});
	}
	
	//cargamos el combo con los delitos del expediente
	function pintaDatosDelitoPersona()
	{
		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/consultaDelitoPersonaPorIdentificador.do',
			data: 'idDelitoPersona='+idDelitoPersona,
			dataType: 'xml',
			async: false,
			success: function(xml){
					//Delito del expediente
					$("#cbxDelitosExpRDPPV").val($(xml).find('DelitoPersonaDTO').find('delito').first().find('delitoId').text());
					//Grado de participacion
					$("#cbxFormasParticipacionRDPPV").val($(xml).find('DelitoPersonaDTO').find('formaParticipacion').first().find('idCampo').text());
					//Victima
					$("#cbxVictimasExpRDPPV").val($(xml).find('DelitoPersonaDTO').find('victima').first().find('elementoId').text());
					//Probable responsable
					$("#cbxProbableResponsableRDPPV").val($(xml).find('DelitoPersonaDTO').find('probableResponsable').first().find('elementoId').text());
					//Clasificacion
					$("#cbxClasificacionRDPPV").val($(xml).find('DelitoPersonaDTO').find('catDelitoClasificacionId').first().text());
					//Lugar
					$("#cbxLugarRDPPV").val($(xml).find('DelitoPersonaDTO').find('catDelitoLugarId').first().text());
					//Modalidad
					$("#cbxModalidadRDPPV").val($(xml).find('DelitoPersonaDTO').find('catDelitoModalidadId').first().text());
					//Modus
					$("#cbxModusRDPPV").val($(xml).find('DelitoPersonaDTO').find('catDelitoModusId').first().text());
					//Causa
					$("#cbxCausaRDPPV").val($(xml).find('DelitoPersonaDTO').find('catDelitoCausaId').first().text());
			}
		});
	}
	
	/**
	* Funcion que activa en Modo Consulta, es decir,
	* deshabilitar los combobox y ocultar el boton de 
	* guardar/modificar
	*
	**/
	function activarModoConsulta(){
		$(":enabled").attr('disabled','disabled');
		$('#btnGuardar').hide();
	}  
	
	/*
	*Funcion que hara el llamado a BD para guardar el nuevo delito relacionado
	*por persona a BD y posteriormente agregara un renglon al grid
	*/
	function RelacionarDelitoRDPPV()
	{
		var idDelito=$("#cbxDelitosExpRDPPV option:selected").val();
		var idFormaP=$("#cbxFormasParticipacionRDPPV option:selected").val();
		var idVictima=$("#cbxVictimasExpRDPPV option:selected").val();
		var idPR=$("#cbxProbableResponsableRDPPV option:selected").val();
		var idClasificacion=$("#cbxClasificacionRDPPV option:selected").val();
		var idLugar=$("#cbxLugarRDPPV option:selected").val();
		var idModalidad=$("#cbxModalidadRDPPV option:selected").val();
		var idModus=$("#cbxModusRDPPV option:selected").val();
		var idCausa=$("#cbxCausaRDPPV option:selected").val();
		
		var esValida = false;
		
		
		if (rolActivo == '<%=Roles.ATPENAL.getValorId()%>'){
			if(parseInt(idPR)!=-1 && parseInt(idDelito)!=-1 && parseInt(idVictima)!=-1){
				esValida = true;
			}else{
				esValida = false;
			}	
		}		
		
		if (rolActivo == '<%=Roles.AGENTEMP.getValorId()%>' ||  rolActivo == '<%=Roles.POLICIAMINISTER.getValorId()%>'){
			if(parseInt(idPR)!=-1 && parseInt(idDelito)!=-1 && parseInt(idVictima)!=-1 && parseInt(idClasificacion)!=-1 && parseInt(idLugar)!=-1 && parseInt(idModalidad)!=-1  && parseInt(idModus)!=-1  && parseInt(idCausa)!=-1){
				esValida = true;
			}else{
				esValida = false;
			}
		}

		
		if(esValida === true)
		{
			//mandamos guardar a BD
			$.ajax({
				type: 'POST',
				url: '<%= request.getContextPath()%>/AsociarDelitoProbableResponsable.do',//guardar a BD la nueva relacion
				data: 'idAsociacion='+idDelitoPersona+'&idPR='+idPR+'&idDelito='+idDelito+'&idVictima='+idVictima+'&idFormaP='+idFormaP+'&idClasificacion='+idClasificacion+'&idLugar='+idLugar+'&idModalidad='+idModalidad+'&idModus='+idModus+'&idCausa='+idCausa,
				dataType: 'xml',
				async: true,
				success: function(xml){
					if(parseInt($(xml).find('code').text())==0)
		    		{
						$(xml).find('Asociacion').each(function(){
							try{window.parent.consultaDelitosPRRDPPV();}catch(e){};
							try{window.parent.consultaDelitosPRRDPD();}catch(e){};
							try{window.parent.cargaGridConsultaDelitosTodos();}catch(e){};
							customAlert("Se actualiz&oacute; de forma correctamente la relaci&oacute;n.");
						});
		    		}
				}
			});	
		}
		else
		{
			var txtProbableResponsableProp = '<bean:message key="msjProbableResponsable"/>';
			var txtModus = '<bean:message key="modusRelacionDelitoPersona"/>	';
			var txtClasificacion = '<bean:message key="clasificacionRelacionDelitoPersona"/>';
			var txtCausa = '<bean:message key="causaRelacionDelitoPersona"/>';
			
			if (rolActivo == '<%=Roles.ATPENAL.getValorId()%>'){
				customAlert("Debe seleccionar un delito, un " + txtProbableResponsableProp + " y una v&iacute;ctima.");
			}
			
			if (rolActivo == '<%=Roles.AGENTEMP.getValorId()%>' ||  rolActivo == '<%=Roles.POLICIAMINISTER.getValorId()%>'){
				customAlert("Debe seleccionar un delito, un " + txtProbableResponsableProp + ", una v&iacute;ctima, una " + txtClasificacion + ", un lugar, una modalidad, un(a) " + txtModus + " y un(a) " + txtCausa);
			}
		}
	}
	
	 function ocultaCombosInnecesariosDelitoPersonaPorPersona(){
			var idParametro = '<%=Parametros.MUESTRA_COMBOS_DELITO_PERSONA.ordinal()%>';
			$.ajax({
				type: 'POST',
				url: '<%=request.getContextPath()%>/consultarParametro.do',
				data: 'idParametro='+ idParametro, 
				async: false,
				dataType: 'xml',
				success: function(xml){					
					muestraCombosDelitoPersona = $(xml).find('body').find('respuesta').text();
					if(muestraCombosDelitoPersona == "0"){
						$("#trFormasParticipacionRDPPV").hide();
						$("#trModalidadRDPPV").hide();
						$("#trModusRDPPV").hide();
						$("#trCausaRDPPV").hide();
					}
				}
			});
		}
	
</script>
</head>
<body>
	<table width="650" border="0" cellspacing="0" id="tblDelitoPersonaVariantes">
		<tr>
		  <td align="right">&nbsp;</td>
		  <td>&nbsp;</td>
	  </tr>
		<tr>
			<td align="right">
				Delito del Expediente :
			</td>
			<td width="484">
				<select id="cbxDelitosExpRDPPV" class="select_mediano">
					<option selected="selected" value="-1">-Seleccione-</option>
				</select>
			</td>
		</tr>
		<tr id="trFormasParticipacionRDPPV">
			<td align="right">
				Grado de participaci&oacute;n : 
			</td>
			<td>
				<select id="cbxFormasParticipacionRDPPV" class="select_mediano">
					<option selected="selected" value="-1">-Seleccione-</option>
				</select>
			</td>
		</tr>
		<tr>
			<td align="right">
				V&iacute;ctima : 
			</td>
			<td>
				<select id="cbxVictimasExpRDPPV" class="select_mediano">
					<option selected="selected" value="-1">-Seleccione-</option>
				</select>
			</td>
		</tr>
			<tr>
			<td align="right">
				<bean:message key="probableResponsableTitulo"/> : 
			</td>
			<td>
				<select id="cbxProbableResponsableRDPPV" class="select_mediano">
					<option selected="selected" value="-1">-Seleccione-</option>
				</select>
			</td>
		</tr>
	  <tr>
	  <td width="162"><div align="right"><bean:message key="clasificacionRelacionDelitoPersona"/> :</div></td>
	    <td><select id="cbxClasificacionRDPPV" class="select_mediano">
	      <option selected="selected" value="-1">-Seleccione-</option>
	    </select></td>
	  </tr>
	  <tr>
	    <td><div align="right">Lugar :</div></td>
	    <td><select id="cbxLugarRDPPV" class="select_mediano">
	      <option selected="selected" value="-1">-Seleccione-</option>
	    </select></td>
	  </tr>
	  <tr id="trModalidadRDPPV">
	    <td><div align="right">Modalidad :</div></td>
	    <td><select id="cbxModalidadRDPPV" class="select_mediano">
	      <option selected="selected" value="-1">-Seleccione-</option>
	    </select></td>
	  </tr>
	  <tr id="trModusRDPPV">
	    <td><div align="right"><bean:message key="modusRelacionDelitoPersona"/> :</div></td>
	    <td><select id="cbxModusRDPPV" class="select_mediano">
	      <option selected="selected" value="-1">-Seleccione-</option>
	    </select></td>
	  </tr>
	  <tr id="trCausaRDPPV">
	    <td><div align="right"><bean:message key="causaRelacionDelitoPersona"/>:</div></td>
	    <td><select id="cbxCausaRDPPV" class="select_mediano">
	      <option selected="selected" value="-1">-Seleccione-</option>
	    </select></td>
	  </tr>
	  <tr>
	    <td colspan="2">
	    	<div align="center"><input type="button" id="btnGuardar" value="Modificar datos" class="ui-button ui-corner-all ui-widget"/></div>
	    </td>
	  </tr>
	</table>	
    
</body>
</html>