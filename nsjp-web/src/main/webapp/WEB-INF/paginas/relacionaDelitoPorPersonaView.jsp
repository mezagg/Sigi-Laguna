<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>	
<%@ page import="mx.gob.segob.nsjp.dto.usuario.UsuarioDTO"%>
<%@ page import="mx.gob.segob.nsjp.comun.enums.seguridad.Roles" %>
<%@ page import="mx.gob.segob.nsjp.comun.enums.configuracion.Parametros" %>
<%@ page import="mx.gob.segob.nsjp.dto.configuracion.ConfInstitucionDTO" %>
<%@ page import="mx.gob.segob.nsjp.comun.enums.institucion.Instituciones" %>
<%
	String rolActivo = "";
	UsuarioDTO usuario = (UsuarioDTO) request.getSession().getAttribute("KEY_SESSION_USUARIO_FIRMADO");
	Boolean esCoordinadorAmpGeneral = false;
	if (usuario != null 
			&& usuario.getRolACtivo() != null 
			&& usuario.getRolACtivo().getRol() != null
			&& usuario.getRolACtivo().getRol().getRolId() != null){
		rolActivo = usuario.getRolACtivo().getRol().getRolId().toString();
	}
	ConfInstitucionDTO institucion = usuario.getInstitucion();
	long valorInstitucion = institucion.getConfInstitucionId();
%>
	<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery.blockUI.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/comun.js?n=1"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/bloqueaTecla.js?n=1"></script>	
	<script type="text/javascript">
            $.jgrid.no_legacy_api = true;
           // $.jgrid.useJSON = true;

    	var contDelitosGraves=0;
    	var idProbResAviso;
    	var idDetencion;
    	var muestraCombosDelitoPersona;
    	var rolActivo='<%=rolActivo%>';

    	/***** Ejecutamos funciones para setear la pagina*****/
		$(document).ready(function(){
			
			jQuery(document).ajaxStop(desbloquearPantalla());
			$("#dialogDos:ui-dialog").dialog( "destroy" );
			
			$("#btnRelacionarRDPPV").hide();
			$("#btnAnulaRelacionDelPer").hide();
			
			if (rolActivo == '<%=Roles.PROCURADOR.getValorId()%>' || rolActivo == '<%=Roles.SUBPROCURADOR.getValorId()%>' ||
					rolActivo == '<%=Roles.DIRECTOR_GENERAL.getValorId()%>' || rolActivo == '<%=Roles.DIRECTOR_UI.getValorId()%>' || 
					rolActivo == '<%=Roles.COORDINADORAMPGENERAL.getValorId()%>' ||
				<%=valorInstitucion%> == '<%=Instituciones.DEF.getValorId()%>'){
				$("#btnRelacionarRDPPV").hide();
				$("#btnAnulaRelacionDelPer").hide();
				//NVO. REQ. SOLICITAR DEFENSOR					
				//$("#btnSolicitarDefensor").hide();
			}
			
		});
    	
	
    	function validaSeleccion1(){
    		    		
    		if($("#cbxProbableResponsableRDPPV option:selected").val()!=-1){
    			$("#trGridDelitosPRRDPPV").show();
    			if(rolActivo == '<%=Roles.PROCURADOR.getValorId()%>' || rolActivo == '<%=Roles.SUBPROCURADOR.getValorId()%>' ||
    					rolActivo == '<%=Roles.DIRECTOR_GENERAL.getValorId()%>' || rolActivo == '<%=Roles.DIRECTOR_UI.getValorId()%>' || 
    					rolActivo == '<%=Roles.COORDINADORAMPGENERAL.getValorId()%>' ||
    			   <%=valorInstitucion%> == '<%=Instituciones.DEF.getValorId()%>'){
    				$("#btnRelacionarRDPPV").hide();
    			}
    			else{
    				$("#btnRelacionarRDPPV").show();
    			}
    			consultaDelitosPRRDPPV();
    		}
    		else{    			
    			$("#trGridDelitosPRRDPPV").hide();
    			$("#btnRelacionarRDPPV").hide();
    			$("#btnAnulaRelacionDelPer").hide();
    			$("#muestraDatosDetencion1").hide();
    			$("#muestraDatosDetencion2").hide();
    		}
    	}

		/*Funcion para lanzar el popup que relacionara 
		*un delito al probable responsable seleccionado
		*/
		function abrePopupRelacionarDelitosPRRDPPV()
		{
			var bandeaDPP = false;
			
			//cargamos el combo de victimas del expediente
			bandeaDPP = cargaComboVictimasRDPPV();

			if(bandeaDPP == true ){
				return;
			}
			
			//cargamos el combo con los delitos del expediente
			cargaComboDelitosExpedientesRDPPV();
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
			
			if (rolActivo == '<%=Roles.ATPENAL.getValorId()%>'){
				ocultaCombosInnecesariosDelitoPersonaPorPersona();
			}
			else if (<%=valorInstitucion%> == '<%=Instituciones.PJ.getValorId()%>'){
				ocultaCombosRDPPVInnecesariosPJ();
			}
			
			//generamos el Dialogo
			$( "#dialogDos-confirm" ).dialog({
				resizable: false,
				height:425,
				width:820,
				modal: true,
				buttons: {
					"Guardar": function() {
						$( this ).dialog( "close" );
						$( "#dialogDos:ui-dialog" ).dialog( "destroy" );
						RelacionarDelitoRDPPV();
					},
					"Cancelar": function() {
						$( this ).dialog( "close" );
						$( "#dialogDos:ui-dialog" ).dialog( "destroy" );
					}
				}
			});
			$( ".ui-icon-closethick,.ui-dialog-titlebar-close" ).hide();
		}

		
		/*
		*Oculta los combos de formas participacion, modalida, modus, causa
		*para la institucion de PJ con el usuario encargadoCausa
		*/
		function ocultaCombosRDPPVInnecesariosPJ(){

			$("#trFormasParticipacionRDPPV").hide();
			$("#trModalidadRDPPV").hide();
			$("#trModusRDPPV").hide();
			$("#trCausaRDPPV").hide();
			$("#trClasificacionRDPPV").hide();
			$("#trLugarRDPPV").hide();
		}

		
		/*
		*Funcion que hara el llamado a BD para guardar el nuevo delito relacionado
		*por persona a BD y posteriormente agregara un renglon al grid
		*/
		function RelacionarDelitoRDPPV()
		{
			var idPR=$("#cbxProbableResponsableRDPPV option:selected").val();
			var idDelito=$("#cbxDelitosExpRDPPV option:selected").val();
			var idVictima=$("#cbxVictimasExpRDPPV option:selected").val();
			var idFormaP=$("#cbxFormasParticipacionRDPPV option:selected").val();
			
			var idClasificacion=$("#cbxClasificacionRDPPV option:selected").val();
			var idLugar=$("#cbxLugarRDPPV option:selected").val();
			var idModalidad=$("#cbxModalidadRDPPV option:selected").val();
			var idModus=$("#cbxModusRDPPV option:selected").val();
			var idCausa=$("#cbxCausaRDPPV option:selected").val();
			
			var esValida = false;
			
			if (rolActivo == '<%=Roles.ATPENAL.getValorId()%>'
					|| rolActivo == '<%=Roles.ENCARGADOCAUSA.getValorId()%>' ||  rolActivo == '<%=Roles.AGENTEMPSISTRAD.getValorId()%>'){
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
			
			if(esValida == true)
			{
				
				//mandamos guardar a BD
				 <%-- $.ajax({
					type: 'POST',
					url: '<%= request.getContextPath()%>/ExisteRelacionProbableResponsableVictimaDelito.do',//verifica si existe la relacion en BD
					data: 'idPR='+idPR+'&idDelito='+idDelito+'&idVictima='+idVictima+'&idFormaP='+idFormaP,
					dataType: 'xml',
					async: true,
					success: function(xml){
						if(parseInt($(xml).find('bandera').text())==0)
			    		{  --%>
							//mandamos guardar a BD
							$.ajax({
								type: 'POST',
								url: '<%= request.getContextPath()%>/AsociarDelitoProbableResponsable.do',//guardar a BD la nueva relacion
								data: 'idPR='+idPR+'&idDelito='+idDelito+'&idVictima='+idVictima+'&idFormaP='+idFormaP+'&idAsociacion=0'+'&idClasificacion='+idClasificacion+'&idLugar='+idLugar+'&idModalidad='+idModalidad+'&idModus='+idModus+'&idCausa='+idCausa,
								dataType: 'xml',
								async: true,
								success: function(xml){
									if(parseInt($(xml).find('code').text())==0)
						    		{
										$(xml).find('Asociacion').each(function(){
											var probableResponsableProp = '<bean:message key="msjProbableResponsable"/>';
											alertDinamico("Se asoci&oacute; correctamente el delito al "+probableResponsableProp);
											//$('#cbxDelitosExpRDPPV').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
											
										});
										consultaDelitosPRRDPPV();
										consultaDelitosPRRDPD();
						    		}
								}
							});	
			    		 /* }
						else {
							alertDinamico("Ya existe esta relaci&oacute;n. Favor de verificar.");
						 }
					}
				});
				 */
			}
			else
			{
				var txtProbableResponsableProp = '<bean:message key="msjProbableResponsable"/>';
				var txtModus = '<bean:message key="modusRelacionDelitoPersona"/>	';
				var txtClasificacion = '<bean:message key="clasificacionRelacionDelitoPersona"/>';
				var txtCausa = '<bean:message key="causaRelacionDelitoPersona"/>';
				
				if (rolActivo == '<%=Roles.ATPENAL.getValorId()%>'
						|| rolActivo == '<%=Roles.ENCARGADOCAUSA.getValorId()%>' ||  rolActivo == '<%=Roles.AGENTEMPSISTRAD.getValorId()%>'){
					customAlert("Debe seleccionar un delito, un " + txtProbableResponsableProp + " y una v&iacute;ctima.");
				}
				
				if (rolActivo == '<%=Roles.AGENTEMP.getValorId()%>' ||  rolActivo == '<%=Roles.POLICIAMINISTER.getValorId()%>'){
					customAlert("Debe seleccionar un delito, un " + txtProbableResponsableProp + ", una v&iacute;ctima, una " + txtClasificacion + ", un lugar, una modalidad, un(a) " + txtModus + " y un(a) " + txtCausa);
				}
			}
		}
		
		
		//cargamos el combo con los delitos del expediente
		function cargaComboProbableResponsableRDPPV()
		{
			//idNumeroExpedienteOp
			$('#cbxProbableResponsableRDPPV').empty();
			//seteamos el listener para el combo de PR en relacion de delitos por persona
	       // $("#cbxProbableResponsableRDPPV").change(consultaDelitosPRRDPPV);
	        //seteamos el listener para el boton de relacionar los delitos
			//$("#btnRelacionarRDPPV").click(abrePopupRelacionarDelitosPRRDPPV);
			$("#trGridDelitosPRRDPPV").hide();
			$("#muestraDatosDetencion1").hide();
			$("#muestraDatosDetencion2").hide();
			
			
			$( "#dialogDos:ui-dialog" ).dialog( "destroy" );			
			$('#cbxProbableResponsableRDPPV').append('<option value="-1" selected="selected">-Seleccione-</option>');
			$.ajax({
				type: 'POST',
				url: '<%= request.getContextPath()%>/ConsultaInvolucradosPorCalidadExpediente.do',
				data: 'idNumeroExpediente='+idNumeroExpedienteOp+'&calidadInvolucrado=PROBABLE_RESPONSABLE',
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
					if(contaProbResps==0)
					{
						if (rolActivo != '<%=Roles.VISITADOR.getValorId()%>'){
							var probableResponsableProp = '<bean:message key="msjProbableResponsable"/>';
							alertDinamico("Necesita un "+probableResponsableProp+" para poder relacionar un delito.");	
						}
					}
				}
			});		
		}

		//cargamos el combo con los delitos del expediente
		function cargaComboProbableResponsableRDPPVDelito(){
			//oculta jsp que muestra los datos de detencion
			$('#muestraDatosDetencion1').hide();
			$('#muestraDatosDetencion2').hide();
			//idNumeroExpedienteOp
			$('#cbxProbableResponsableRDPPV').empty();
			
	        //seteamos el listener para el boton de relacionar los delitos
			//$("#btnRelacionarRDPPV").click(abrePopupRelacionarDelitosPRRDPPV);
			$("#trGridDelitosPRRDPPV").hide();
			//$("#muestraDatosDetencion1").hide();
			//$("#muestraDatosDetencion2").hide();
			
			$( "#dialogDos:ui-dialog" ).dialog( "destroy" );			
			$('#cbxProbableResponsableRDPPV').append('<option value="-1" selected="selected">-Seleccione-</option>');
			$.ajax({
				type: 'POST',
				url: '<%= request.getContextPath()%>/ConsultaInvolucradosPorCalidadExpediente.do',
				data: 'idNumeroExpediente='+idNumeroExpedienteOp+'&calidadInvolucrado=PROBABLE_RESPONSABLE',
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
		
		//cargamos el combo con los delitos del expediente
		function cargaComboDelitosExpedientesRDPPV()
		{
			$("#cbxDelitosExpRDPPV").empty();
			$('#cbxDelitosExpRDPPV').append('<option value="-1" selected="selected">-Seleccione-</option>');
			$.ajax({
				type: 'POST',
				url: '<%= request.getContextPath()%>/ConsultaDelitoPorExpediente.do',
				data: 'idNumeroExpediente='+idNumeroExpedienteOp,
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
				data: 'idNumeroExpediente='+idNumeroExpedienteOp+'&calidadInvolucrado=VICTIMA',
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
					if(contaProbResps==0)
					{
						alertDinamico("Necesita una v&iacute;ctima para poder relacionar un delito.");
						banderaVictimas = true;
					}
				}
			});		
			return banderaVictimas;
		}
		
		//-----------------------------------------------
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
		
		var banderaPV=0;
		//Validar si el PR es detenido con relacion a delitos 
		var esDetenidoConRelacion=0;
        //funcion para mandar consultar los delitos de un probable responsable 
        function consultaDelitosPRRDPPV(){
        	var idPRval=$("#cbxProbableResponsableRDPPV option:selected").val();        
        	//revisamos que se haya seleccionado un probable responsable
        	if(parseInt(idPRval)!=-1)
        	{
        		if(banderaPV==0)
        		{
        		$("#trGridDelitosPRRDPPV").show();
        		//var params="idPR="+idPR+"&idExpediente="+idExpedienteop;
        		//cargamos el grid con los delitos del PR
	        	jQuery("#gridCatDelitosRDPPV").jqGrid({ 
					url:'<%= request.getContextPath()%>/CargarDelitoAsociadosInvolucrado.do?idPR='+idPRval +'&idExpediente='+idExpedienteop+'', 
					datatype: "xml",
					colNames:['Delito','Forma de Participaci&oacute;n','V&iacute;ctima'], 
					colModel:[ 	{name:'Delito',index:'delito', width:250}, 
								{name:'FormaParticipacion',index:'formaParticipacion',width:550},
								{name:'Victima',index:'victima',width:250},							
								
							],
					//pager: jQuery('#pager1'),
					rowNum:0,
					rowList:[0,0,0],
					autowidth: true,
					caption:"LISTA DE DELITOS",
					sortname: 'Clave',
					multiselect: true,
					viewrecords: true,
					id: 'divgridIzq',
					sortorder: "desc",
					ondblClickRow: function(rowid) {
					    $.newWindow({id:"consultaDelitoPersona", statusBar: true, posx:20,posy:20,width:500,height:350,title:"Consulta delito persona", type:"iframe"});
	             		$.updateWindowContent("consultaDelitoPersona",'<iframe src="<%= request.getContextPath() %>/abrePantallaConsultaDelitoPersona.do?idDelitoPersona='+rowid+'&idNumeroExpediente='+idNumeroExpedienteOp+'" width="500" height="350" />');
					},
					loadComplete: function(){

						ocultarFormasParticipacionGridPRRDPPV();		        		

						var longitudTabla = jQuery("#gridCatDelitosRDPPV").getDataIDs();
						var elementosListaDelitos = longitudTabla.length;

			       		if(elementosListaDelitos>0){ 			       		
				       		if(rolActivo == '<%=Roles.PROCURADOR.getValorId()%>' || rolActivo == '<%=Roles.SUBPROCURADOR.getValorId()%>' ||
									rolActivo == '<%=Roles.DIRECTOR_GENERAL.getValorId()%>' || rolActivo == '<%=Roles.DIRECTOR_UI.getValorId()%>' || 
									rolActivo == '<%=Roles.COORDINADORAMPGENERAL.getValorId()%>' ||
				       		   <%=valorInstitucion%> == '<%=Instituciones.DEF.getValorId()%>'){
								$("#btnRelacionarRDPPV").hide();
								$("#btnAnulaRelacionDelPer").hide();
								//NVO. REQ. SOLICITAR DEFENSOR
								//$("#btnSolicitarDefensor").hide();
								esDetenidoConRelacion = 0;
							}
							else{
								$("#btnAnulaRelacionDelPer").show();
								$("#btnRelacionarRDPPV").show();
								//NVO. REQ. SOLICITAR DEFENSOR
								//$("#btnSolicitarDefensor").show();
								esDetenidoConRelacion = 1;
							}	
						}
			       		else{
							$("#btnAnulaRelacionDelPer").hide();
							esDetenidoConRelacion = 0;
			       		}
			       	}
				}).navGrid('#pager1',{edit:false,add:false,del:false});
	        	$("#gridCatDelitosRDPPV").trigger("reloadGrid");
	        	banderaPV=1;
        		}
        		else
        		{
        			jQuery("#gridCatDelitosRDPPV").jqGrid('setGridParam', {url:'<%= request.getContextPath()%>/CargarDelitoAsociadosInvolucrado.do?idPR='+idPRval +'&idExpediente='+idExpedienteop+'',datatype: "xml" });
    				$("#gridCatDelitosRDPPV").trigger("reloadGrid");
    				$("#trGridDelitosPRRDPPV").show();
        		}
        	}
        	else
        	{
        		//ocultamos el grid cuando no se selecciono un PR
        		$("#trGridDelitosPRRDPPV").hide();
        		$('#muestraDatosDetencion1').hide();
        		$('#muestraDatosDetencion2').hide();
        	}
        	consultaDetencion(idPRval);
        }

        function ocultarFormasParticipacionGridPRRDPPV(){
	        if (<%=valorInstitucion%> == '<%=Instituciones.PJ.getValorId()%>'){
	        	jQuery("#gridCatDelitosRDPPV").jqGrid('hideCol',"FormaParticipacion");
			}
		}
        
        function consultaDetencion(idPRval){
        	$('#muestraDatosDetencion1').hide();
        	$('#muestraDatosDetencion2').hide();
            	 idProbResAviso = idPRval;
	   			$.ajax({
    		    	  type: 'POST',
    		    	  url:  '<%= request.getContextPath()%>/consultarInvolucrado.do',
    		    	  data: 'idInvolucrado='+idProbResAviso,
    		    	  dataType: 'xml',
    		    	  async: false,
    		    	  success: function(xml){
        		    	//NVO. REQ. SOLICITAR DEFENSOR  
			   			//var arrayIds = jQuery("#gridCatDelitosRDPPV").getDataIDs();
						//if(arrayIds.length>0){
						//	if(rolActivo == '<%=Roles.COORDINADORAMPGENERAL.getValorId()%>' ||
						//       <%=valorInstitucion%> == '<%=Instituciones.DEF.getValorId()%>'){
						//		$('#btnSolicitarDefensor').hide();
						//	}
						//	else{
								 //$('#btnSolicitarDefensor').show();
						//	}
						//}

						idDetencion = $(xml).find('involucradoDTO').find('detenciones').find('DetencionDTO').find('detencionId').first().text();
						
    		    		var avisoDetencion = $(xml).find('involucradoDTO').find('detenciones').find('DetencionDTO').find('avisoDetencion').find('documentoId').first().text()!= "";
    		    		  //$(xml).find('involucradoDTO').find('nombresDemograficoDTO').find('').
    		    		  if($(xml).find('involucradoDTO').find('esDetenido').first().text() && esDetenidoConRelacion){
    		    			//NVO. REQ. SOLICITAR DEFENSOR
    		    			  //$('#btnSolicitarDefensor').attr("disabled","");
    		    		 	  pintaDatosTiempoLapso(xml);
    		    			  bloqueaCamposTiempoLapso(1);
    		    			  bloqueaCamposTiempoLapso(0);
    		    			  $('#muestraDatosDetencion1').show();  
    		    			  $('#muestraDatosDetencion2').show();

    		    			  if(avisoDetencion){
    		    					//NVO. REQ. SOLICITAR DEFENSOR
       		    			   		//$('#btnSolicitarDefensor').attr("disabled","disabled");
        		    			   var fecha = $(xml).find('involucradoDTO').find('detenciones').find('DetencionDTO').find('avisoDetencion').find('fechaCreacion').text();
        		    			   var strfecha = obtenerFecha(fecha);   
        		    			   var strhora = obtenerHora(fecha); 	
        		    			   $('#fechaSolicitud').show();
        		    		       $('#horaSolicitud').show(); 
        		    		       $('#fechaSolicitudDefensor').val(strfecha);
        		    		       $('#horaSolicitudDefensor').val(strhora);
         		    			  }  		    			    			    		  
    			    		  }
    		    		  }
    		    });


        }
    			
        
        function anularRelacionDelitoPersonaPersona()
        {
			var idsRelacionesSeleccionados = jQuery("#gridCatDelitosRDPPV").jqGrid('getGridParam','selarrrow');
			if(idsRelacionesSeleccionados.length>0)
			{
			//Guardamos la relacion
				$.ajax({
					type: 'POST',		
		    		url: '<%= request.getContextPath()%>/anularRelacionDelitoPersona.do?idsRelacionesSeleccionados=' + idsRelacionesSeleccionados + '',
					data: '',
					dataType: 'xml',
					async: false,
					success: function(xml){
						if(parseInt($(xml).find('code').text())==0 && parseInt($(xml).find('bandera').text())==1){
							alertDinamico("Se anularon con &eacute;xito la(s) relaci\u00F3n(es)");
							consultaDelitosPRRDPPV();
							consultaDelitosPRRDPD();
						}				    		
			    		else
			    			alertDinamico("No se logr&oacute; anular la(s) relaci\u00F3n(es)");
					}
				});
			}
        }

        
        /*
        *Metodo para enviar el aviso de detencion
		*a la institucion de DEFENSORIA
		*/
		function enviaAvisoDetencion(){
			
			var params = '';
			
			params += 'idIndividuo='+idProbResAviso;
			params += '&numeroExpediente='+numeroExpediente;
			params += '&idDetencion='+ idDetencion;
						
			//Datos fechas
			datosPestania = recuperaDatosTiempoLapso();
			params += datosPestania;
			bloquearPantalla();
			$.ajax({								
				type: 'POST',
				url: '<%= request.getContextPath()%>/enviarAvisosDetencion.do',
				data: params,				
				dataType: 'xml',
				success: function(xml){
					if(parseInt($(xml).find('code').text())==0){
						if($(xml).find('body').text() != null 
								&& $(xml).find('body').text() != "null"
									&& $(xml).find('body').text() != ""){
							alertDinamico($(xml).find('body').text());
							//NVO. REQ. SOLICITAR DEFENSOR							
							//$('#btnSolicitarDefensor').attr("disabled","disabled");
						}
					}else{
						alertDinamico("Ocurri&oacute; un error al intentar enviar la solicitud de defensor.<br/>" +
						"por favor contacte al administrador");
					}
				}
			});
			desbloquearPantalla()
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

<!-- div para el alert dinamico -->
<div id="dialog-Alert" style="display: none">
	<table align="center">
		<tr>
        	<td align="center">
            	<span id="divAlertTexto"></span>
            </td>
        </tr>
     </table>              
</div>

	<table border="0"  width="1050px" id="tblRelacionarDelitoPorPersona">
		<tr>
			<td height="20" colspan="5" align="left" >
				<!-- <input type="button" id="btnGuardarDelitosAg" value="Guardar" onclick="guardarDelitosAgraviadosExp();"/> -->				
					<bean:message key="selProbableResponsable"/>: 
				<select id="cbxProbableResponsableRDPPV" onchange="validaSeleccion1();">
					<option value="-1" selected="selected">-Seleccione-</option>
				</select>
			</td>
		</tr>
		<tr>
			<td id="trGridDelitosPRRDPPV" colspan="3">
				<table id="gridCatDelitosRDPPV"></table>
				<div id="pager1"></div>
				<br/>				
			</td>
			<!-- <td id="muestraDatosDetencion" style="display: none"> -->
		  		<td id="muestraDatosDetencion1" align="left" ><jsp:include page="ingresarTiempoLapsoDetencion.jsp"></jsp:include></td>
		</tr>
		<tr><td>&nbsp;</td></tr>
		<tr>	
			<td align="left" valign="top">
				<input type="button" value="Relacionar" id="btnRelacionarRDPPV" onclick="abrePopupRelacionarDelitosPRRDPPV();" class="btn_modificar"/>
			</td>
			<td align="left" valign="top">
				<input type="button" id="btnAnulaRelacionDelPer" value="Anular relaci&oacute;n Delito - Persona" onclick="anularRelacionDelitoPersonaPersona();" class="btn_grande"/>
			</td>
			<td>&nbsp;</td>
			<td id="muestraDatosDetencion2" align="center" >
				<!-- NVO. REQ. SOLICITAR DEFENSOR -->
				<!--<input type="button" id="btnSolicitarDefensor" value="Solicitar Defensor" style="display: none" class="btn_mediano"/>-->
			</td>
		</tr>
	</table>

	<script type="text/javascript">
	//NVO. REQ. SOLICITAR DEFENSOR
	 //$('#btnSolicitarDefensor').click(enviaAvisoDetencion); 	 
	</script>

