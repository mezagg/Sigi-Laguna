<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>	
<%@ page import="mx.gob.segob.nsjp.dto.usuario.UsuarioDTO"%>
<%@ page import="mx.gob.segob.nsjp.comun.enums.seguridad.Roles" %>
<%@ page import="mx.gob.segob.nsjp.comun.enums.configuracion.Parametros" %>
<%@ page import="mx.gob.segob.nsjp.dto.configuracion.ConfInstitucionDTO" %>
<%@ page import="mx.gob.segob.nsjp.comun.enums.institucion.Instituciones" %>
<%
	String rolActivo = "";
	UsuarioDTO usuario = (UsuarioDTO) request.getSession().getAttribute("KEY_SESSION_USUARIO_FIRMADO");
	//Boolean esCoordinadorAmpGeneral = false;
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

//ON READY	
$(document).ready(function(){
	try{
		if(deshabilitarCamposPM === true){
			$("#tblEstablecerModosYGrados :enabled").attr('disabled','disabled');
		}
	}catch(e){};

});
	//FIN ON READY


	//Variable para controlar la carga del grid de delitos persona
	var fistGridRelDelPerModosParticipacionPG = true;
	
	/*
	*Funcion para cargar el grid de las relaciones delito persona del expediente
	*/
	function gridRelacionesDelitoPersonaModosParticipacionPG(){
		
		if(fistGridRelDelPerModosParticipacionPG){
			jQuery("#gridRelDelPerModosParticipacionPG").jqGrid({ 							
	            url:'<%= request.getContextPath()%>/consultarRelacionDelitoPorExpediente.do?idExpediente='+idExpedienteop+'',
				datatype: "xml",
				colNames:['Nombre '+'<bean:message key="msjProbableResponsable"/>','Delito','Nombre V&iacute;ctima','Grado de Participaci&oacute;n'],
				 
				colModel:[ 	{name:'NombrePRFP',		index:'1',	width:250,	align:'center', sortable: false},
							{name:'NombreDELFP',	index:'2',	width:250,	align:'center', sortable: false},
							{name:'NombreVICFP',	index:'3',	width:250,	align:'center', sortable: false},
							{name:'FormaP',			index:'4',	width:300,	align:'center', sortable: false}
				 		],
				pager: jQuery('#pagerGridRelDelPerModosParticipacionPG'),
				rowNum:10,
				rowList:[10,20,30],
				width:1050,
				caption:"LISTA DE RELACIONES DELITO-PERSONA Y GRADO DE PARTICIPACION",
				sortname: 'Nombre',
				viewrecords: true,
				multiselect: true,
				toolbar: [true,"top"],
				ondblClickRow: function(rowid) {
					pintaDatosDelitoPersona(rowid);
				},
				gridComplete: function () {
					seleccionarItems($(this));
					try{
						if(deshabilitarCamposPM === true){
							$("#tblEstablecerModosYGrados :enabled").attr('disabled','disabled');
						}
					}catch(e){};
				},
				onPaging: function (a) {
					guardarItemsSeleccionados($(this)); 
				},
				onSortCol: function(){
					eliminarItemsSeleccionados($(this));
				}, 
				sortorder: "desc"
			});
			$("#gview_gridRelDelPerModosParticipacionPG .ui-jqgrid-bdiv").css('height', '250px');

			//Agregamos el boton para limpiar las selecciones del multigird
			$("#t_gridRelDelPerModosParticipacionPG").append("<input type='button' class='btn_Generico' value='Limpiar selecci&oacute;n' style='height:20px;font-size:-3'/>");
			$("input","#t_gridRelDelPerModosParticipacionPG").click(function(){
				eliminarItemsSeleccionados($("#gridRelDelPerModosParticipacionPG"));
			});
			
			fistGridRelDelPerModosParticipacionPG=false;
		}else{
			jQuery("#gridRelDelPerModosParticipacionPG").jqGrid('setGridParam', {url:'<%= request.getContextPath()%>/consultarRelacionDelitoPorExpediente.do?idExpediente='+idExpedienteop+'',datatype: "xml"});
			$("#gridRelDelPerModosParticipacionPG").trigger("reloadGrid");				
		}
	}


	/*
	*Funcion para lanzar el popup que relacionara 
	*un delito al probable responsable seleccionado
	*/
	function dialogoAtributosRelDelPer(){

		var idsRelacionesSeleccionadas = obtenerSeleccionados($('#gridRelDelPerModosParticipacionPG'));
		
		if(idsRelacionesSeleccionadas.length <= 0){
			customAlert("Seleccione almenos una relaci&oacute;n delito persona","Aviso");
			return;
		}
		
		//cargamos el combo con las formas de participacion del involucrado
		cargaComboFormaParticipacion();
		//cargamos el combo de clasificacion delito
		cargaComboClasificacionDelito();
		//cargamos el combo del lugar de delito
		cargaComboLugarDelito();
		//cargamos el combo de modalidad delito
//		cargaComboModalidadDelito();
		//cargamos el combo de modus delito
//		cargaComboModusDelito();
		//cargamos el combo de causa delito
//		cargaComboCausaDelito();
		//cargamos el combo delito estadistica
		cargaComboEstadisticaDelito();
		//cargamos el combo tipo de delito
		cargaComboTipoDelito();
		//carga combo calificacion del delito
		cargaComboCalificacionDelito();
		//carga combo concurso
		cargaComboConcursoDelito();
		//carga combo orden de resultado
		cargaComboOrdenResultadoDelito();

		if (rolActivo == '<%=Roles.ATPENAL.getValorId()%>' || rolActivo == '<%=Roles.COORDINADORAT.getValorId()%>'){
			ocultaCombosInnecesariosDelitoPersonaPorPersona();
		}
		else if (<%=valorInstitucion%> == '<%=Instituciones.PJ.getValorId()%>'){
			ocultaCombosRDPPVInnecesariosPJ();
		}
		
		if (rolActivo != '<%=Roles.ATPENAL.getValorId()%>' && rolActivo != '<%=Roles.COORDINADORAT.getValorId()%>'
				&& rolActivo != '<%=Roles.AGENTEMP.getValorId()%>' && rolActivo != '<%=Roles.COORDINADORAMP.getValorId()%>'
				&& rolActivo != '<%=Roles.POLICIAMINISTER.getValorId()%>'){
			ocultaCombosInnecesariosDelitoRolesDistintos();
		}
		
		//generamos el Dialogo
		$( "#dialogModosYGrados-confirm" ).dialog({
			resizable: false,
			height:425,
			width:820,
			modal: true,
			buttons: {
				"Guardar": function() {
					//Ingresar nuevos atributos
					actualizarAtributosRelDelPer(false,null);
				},
				"Cancelar": function() {
					$( this ).dialog( "close" );
					$( "#dialogModosYGrados:ui-dialog" ).dialog( "destroy" );
				}
			}
		});
		$( ".ui-icon-closethick,.ui-dialog-titlebar-close" ).hide();
	}

	
	/*
	*Funcion que hara el llamado a BD para guardar el nuevo delito relacionado
	*por persona a BD y posteriormente agregara un renglon al grid
	*/
	function actualizarAtributosRelDelPer(actualizarConsulta,idRelConsultada){
		
		var idFormaP=$("#cbxFormasParticipacionRelDelPer option:selected").val();
		var idClasificacion=$("#cbxClasificacionRelDelPer option:selected").val();
		var idLugar=$("#cbxLugarRelDelPer option:selected").val();
//		var idModalidad=$("#cbxModalidadRelDelPer option:selected").val();
//		var idModus=$("#cbxModusRelDelPer option:selected").val();
//		var idCausa=$("#cbxCausaRelDelPer option:selected").val();
		var idEstadistica=$("#cbxEstadisticaRelDelPer option:selected").val();
		var idTipo=$("#cbxTipoRelDelPer option:selected").val();
		var idCalificacion=$("#cbxCalificacionDelPer option:selected").val();
		var idConcurso=$("#cbxConcursoDelPer option:selected").val();
		var idOrdenRes=$("#cbxOrdenDelPer option:selected").val();
		var idsRelacionesSeleccionadas;

		
		if(actualizarConsulta){
			//Actualiaza los atributos de una relacion, en especifico
			idsRelacionesSeleccionadas = idRelConsultada;
			if(idRelConsultada == null || idRelConsultada === undefined){
				customAlert("Imposible capturar los atributos, intente nuevamente","Error!");
				return;
			}
		}else{
			//Guarda nuevos atributos, para un conjunto de relaciones delito persona
			idsRelacionesSeleccionadas = obtenerSeleccionados($('#gridRelDelPerModosParticipacionPG'));
			
			if(idsRelacionesSeleccionadas.length <= 0){
				customAlert("Seleccione almenos una relaci&oacute;n delito persona","Aviso");
				return;
			}
		}
		
		if(validarCamposObligatoriosPorRol(idFormaP,idClasificacion,idLugar,/* idModalidad,idModus,idCausa, */
											 idEstadistica, idTipo,idCalificacion,idConcurso,idOrdenRes)){

			var parametros = "";

			parametros += '&idFormaP=' + idFormaP;
			parametros += '&idClasificacion=' + idClasificacion;
			parametros += '&idLugar=' + idLugar;
//			parametros += '&idModalidad=' + idModalidad;
//			parametros += '&idModus=' + idModus;
//			parametros += '&idCausa=' + idCausa;
			parametros += '&idsRelsDelPersona=' + idsRelacionesSeleccionadas;
			parametros += '&idEstadistica=' + idEstadistica;
			parametros += '&idTipo=' + idTipo;
			parametros += '&idCalificacion=' + idCalificacion;
			parametros += '&idConcurso=' + idConcurso;
			parametros += '&idOrdenRes=' + idOrdenRes;
			
			//Lamada a guardar la asociacion
			ejecutaAction("/establecerModosGradosYFormasDeParticipacion", function(xmlRespuesta){					
				if($(xmlRespuesta).find('body').find('respuesta').text()== "exito"){
					//Recargamos el grid
					gridRelacionesDelitoPersonaModosParticipacionPG();
					//Limpiamos la seleccion del grid de relaciones delito persona
					eliminarItemsSeleccionados($('#gridRelDelPerModosParticipacionPG'));

					//Cerramos la ventana poppup
					$("#dialogModosYGrados-confirm").dialog( "close" );
					$("#dialogModosYGrados:ui-dialog" ).dialog( "destroy" );
					
					//customAlert(texto,titulo,funcion)
					customAlert("Se capturaron correctamente los atributos de la(s) relaci&oacute;n(es)","",function(){
					});
				}else{
					customAlert("Imposible capturar los atributos, intente nuevamente","Error!");
				}
			} , parametros);
		}
	}


	/**
	*Funcion para validar los campos que se deben llenar de manera obligatoria
	*para definir los taributos de las relaciones delito persona
	*/
	function validarCamposObligatoriosPorRol(idFormaP,idClasificacion,idLugar,/* idModalidad,idModus,idCausa, */
												 idEstadistica, idTipo,idCalificacion,idConcurso,idOrdenRes){

		var esValida = false;
		
		var txtClasificacion = '<bean:message key="clasificacionRelacionDelitoPersona"/>';
		var txtProbableResponsableProp = '<bean:message key="msjProbableResponsable"/>';
//		var txtModus = '<bean:message key="modusRelacionDelitoPersona"/>	';
//		var txtCausa = '<bean:message key="causaRelacionDelitoPersona"/>';
		

		if (rolActivo == '<%=Roles.AGENTEMP.getValorId()%>' ||  rolActivo == '<%=Roles.POLICIAMINISTER.getValorId()%>'){
			if(parseInt(idClasificacion)!=-1 && parseInt(idLugar)!=-1 /* && parseInt(idModalidad)!=-1  && parseInt(idModus)!=-1  && parseInt(idCausa)!=-1 */
					 && parseInt(idEstadistica)!=-1 && parseInt(idTipo)!=-1 && parseInt(idCalificacion)!=-1 && parseInt(idConcurso)!=-1 && parseInt(idOrdenRes)!=-1){
				esValida = true;
			}else{
				customAlert("Debe seleccionar, un " + txtClasificacion + ", un lugar, un delito estadistica" + ", un tipo de delito, una calificacion del delito, un concurso y una orden de resultado");
			}
		}else{
			esValida = true;
		}
		
		return esValida;
	}
	
	//cargamos el combo con los delitos del expediente
	function pintaDatosDelitoPersona(idDelitoPersona){

		var parametros = "";
		parametros += '&idDelitoPersona=' + idDelitoPersona;

		//Carga los combos de atributos
		cargaCombosGradoYAtributosRelDelPer();

		//Lamada a guardar la asociacion
		ejecutaAction("/consultaDelitoPersonaPorIdentificador", function(xml){					
			//Grado de participacion
			$("#cbxFormasParticipacionRelDelPer").val($(xml).find('DelitoPersonaDTO').find('formaParticipacion').first().find('idCampo').text());
			//Clasificacion
			$("#cbxClasificacionRelDelPer").val($(xml).find('DelitoPersonaDTO').find('catDelitoClasificacionId').first().text());
			//Lugar
			$("#cbxLugarRelDelPer").val($(xml).find('DelitoPersonaDTO').find('catDelitoLugarId').first().text());
			//Modalidad
//			$("#cbxModalidadRelDelPer").val($(xml).find('DelitoPersonaDTO').find('catDelitoModalidadId').first().text());
			//Modus
//			$("#cbxModusRelDelPer").val($(xml).find('DelitoPersonaDTO').find('catDelitoModusId').first().text());
			//Causa
//			$("#cbxCausaRelDelPer").val($(xml).find('DelitoPersonaDTO').find('catDelitoCausaId').first().text());
			//Estadistica
			$("#cbxEstadisticaRelDelPer").val($(xml).find('DelitoPersonaDTO').find('catDelitoEstadisticaId').first().text());
			//Tipo
			$("#cbxTipoRelDelPer").val($(xml).find('DelitoPersonaDTO').find('catDelitoTipoId').first().text());
			//Calificacion
			$("#cbxCalificacionDelPer").val($(xml).find('DelitoPersonaDTO').find('catDelitoCalificacionId').first().text());
			//Concurso
			$("#cbxConcursoDelPer").val($(xml).find('DelitoPersonaDTO').find('catDelitoConcursoId').first().text());
			//Orden de resultado
			$("#cbxOrdenDelPer").val($(xml).find('DelitoPersonaDTO').find('catDelitoOrdenResId').first().text());
		} , parametros);

		
		if (rolActivo == '<%=Roles.ATPENAL.getValorId()%>' || rolActivo == '<%=Roles.COORDINADORAT.getValorId()%>'){
			ocultaCombosInnecesariosDelitoPersonaPorPersona();
		}
		else if (<%=valorInstitucion%> == '<%=Instituciones.PJ.getValorId()%>'){
			ocultaCombosRDPPVInnecesariosPJ();
		}
		
		if (rolActivo != '<%=Roles.ATPENAL.getValorId()%>' && rolActivo != '<%=Roles.COORDINADORAT.getValorId()%>'
				&& rolActivo != '<%=Roles.AGENTEMP.getValorId()%>' && rolActivo != '<%=Roles.COORDINADORAMP.getValorId()%>'){
			ocultaCombosInnecesariosDelitoRolesDistintos();
		}

		//generamos el Dialogo
		$( "#dialogModosYGrados-confirm" ).dialog({
			resizable: false,
			height:425,
			width:820,
			modal: true,
			buttons: {
				"Guardar": function() {
					actualizarAtributosRelDelPer(true,idDelitoPersona);
				},
				"Cancelar": function() {
					$( this ).dialog( "close" );
					$( "#dialogModosYGrados:ui-dialog" ).dialog( "destroy" );
				}
			}
		});
		$( ".ui-icon-closethick,.ui-dialog-titlebar-close" ).hide();
	}


	/*
	*Funcion para anular una o mas, relaciones delito persona
	*/
	function anularRelacionDelitoPersonaPersona(){

		var idsRelacionesSeleccionadas = obtenerSeleccionados($('#gridRelDelPerModosParticipacionPG'));
		
		if(idsRelacionesSeleccionadas.length <= 0){
			customAlert("Seleccione almenos una relaci&oacute;n delito persona","Aviso");
			return;
		}
	
		//Anulamos la relacion
			$.ajax({
				type: 'POST',		
	    		url: '<%= request.getContextPath()%>/anularRelacionDelitoPersona.do?idsRelacionesSeleccionados=' + idsRelacionesSeleccionadas + '',
				data: '',
				dataType: 'xml',
				async: false,
				success: function(xml){
					if(parseInt($(xml).find('code').text())==0 && parseInt($(xml).find('bandera').text())==1){
						//Recargamos el grid de formas de participacion
						gridRelacionesDelitoPersonaModosParticipacionPG();
						//Es necesario recargar el grid de relaciones delito-persona por delito
						gridRelacionesDelitoPersona();
						customAlert("Se anul&oacute; con &eacute;xito la(s) relaci&oacute;n(es)");
					}				    		
		    		else
		    			customAlert("No se logr&oacute; anular la(s) relaci&oacute;n(es)");
				}
			});
    }


	/*
	* Funcion para ocultar los combos innecesarios
	*/
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
					$("#trFormasParticipacionRelDelPer").hide();
					/* $("#trModalidadRelDelPer").hide();
					$("#trModusRelDelPer").hide();
					$("#trCausaRelDelPer").hide(); */
				}
			}
		});
	}


	/*
	*Oculta los combos de formas participacion, modalida, modus, causa
	*para la institucion de PJ con el usuario encargadoCausa
	*/
	function ocultaCombosRDPPVInnecesariosPJ(){

		$("#trFormasParticipacionRelDelPer").hide();
		/* $("#trModalidadRelDelPer").hide();
		$("#trModusRelDelPer").hide();
		$("#trCausaRelDelPer").hide(); */
		$("#trClasificacionRelDelPer").hide();
		$("#trLugarRelDelPer").hide();
		$("#trEstadisticaRelDelPer").hide();
		$("#trTipoRelDelPer").hide();
		$("#trCalificacionRelDelPer").hide();
		$("#trConcursoRelDelPer").hide();
		$("#trOrdenRelDelPer").hide();
	}
	
	/*
	*Oculta los combos de delito estadistica, tipo delito, calificacion del delito, 
	*concurso y orden de resultado para roles diferentes a atpenal y agentemp
	*/
	function ocultaCombosInnecesariosDelitoRolesDistintos(){
		
		$("#trEstadisticaRelDelPer").hide();
		$("#trTipoRelDelPer").hide();
		$("#trCalificacionRelDelPer").hide();
		$("#trConcursoRelDelPer").hide();
		$("#trOrdenRelDelPer").hide();
		
	}
	
	//**************************************************************CARGA DE COMBOS DE CATALOGOS**********************************************************//	
	
	//cargamos el combo con las formas de participacion del involucrado
	function cargaComboFormaParticipacion(){
		$("#cbxFormasParticipacionRelDelPer").empty();
		$('#cbxFormasParticipacionRelDelPer').append('<option value="-1" selected="selected">-Seleccione-</option>');
		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/ConsultarCatalogoModoParticipacionDelito.do',
			dataType: 'xml',
			async: false,
			success: function(xml){
				$(xml).find('ModoParticipacionDelito').each(function(){
					$('#cbxFormasParticipacionRelDelPer').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
				});
			}
		});
	}
				
		
	//cargamos el combo de clasificacion del delito
	function cargaComboClasificacionDelito(){
		
		$("#cbxClasificacionRelDelPer").empty();
		$('#cbxClasificacionRelDelPer').append('<option value="-1" selected="selected">-Seleccione-</option>');
		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/ConsultarCatalogoClasificacionDelito.do',
			dataType: 'xml',
			async: false,
			success: function(xml){
				$(xml).find('ModoParticipacionDelito').each(function(){
					$('#cbxClasificacionRelDelPer').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
					
				});
			}
		});
	}

	
	//cargamos el combo de lugar del delito
	function cargaComboLugarDelito(){
		
		$("#cbxLugarRelDelPer").empty();
		$('#cbxLugarRelDelPer').append('<option value="-1" selected="selected">-Seleccione-</option>');
		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/ConsultarCatalogoLugarDelito.do',
			dataType: 'xml',
			async: false,
			success: function(xml){
				$(xml).find('ModoParticipacionDelito').each(function(){
					$('#cbxLugarRelDelPer').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
					
				});
			}
		});
	}

	
	//cargamos el combo de Modalidad del delito
	<%-- function cargaComboModalidadDelito(){
		
		$("#cbxModalidadRelDelPer").empty();
		$('#cbxModalidadRelDelPer').append('<option value="-1" selected="selected">-Seleccione-</option>');
		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/ConsultarCatalogoModalidadDelito.do',
			dataType: 'xml',
			async: false,
			success: function(xml){
				$(xml).find('ModoParticipacionDelito').each(function(){
					$('#cbxModalidadRelDelPer').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
					
				});
			}
		});
	} --%>

	
	//cargamos el combo de Modus del delito
	<%-- function cargaComboModusDelito(){
		
		$("#cbxModusRelDelPer").empty();
		$('#cbxModusRelDelPer').append('<option value="-1" selected="selected">-Seleccione-</option>');
		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/ConsultarCatalogoModusDelito.do',
			dataType: 'xml',
			async: false,
			success: function(xml){
				$(xml).find('ModoParticipacionDelito').each(function(){
					$('#cbxModusRelDelPer').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
					
				});
			}
		});
	} --%>

	
	//cargamos el combo de Causa del delito
	<%-- function cargaComboCausaDelito(){
		
		$("#cbxCausaRelDelPer").empty();
		$('#cbxCausaRelDelPer').append('<option value="-1" selected="selected">-Seleccione-</option>');
		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/ConsultarCatalogoCausaDelito.do',
			dataType: 'xml',
			async: false,
			success: function(xml){
				$(xml).find('ModoParticipacionDelito').each(function(){
					$('#cbxCausaRelDelPer').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
					
				});
			}
		});
	} --%>
	
	//cargamos el combo delito estadistica
	
	function cargaComboEstadisticaDelito(){
		
		$("#cbxEstadisticaRelDelPer").empty();
		$('#cbxEstadisticaRelDelPer').append('<option value="-1" selected="selected">-Seleccione-</option>');
		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/ConsultarCatalogoModusDelito.do',
			dataType: 'xml',
			async: false,
			success: function(xml){
				$(xml).find('ModoParticipacionDelito').each(function(){
					$('#cbxEstadisticaRelDelPer').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
					
				});
			}
		});
	} 
	
	
	//cargamos el combo tipo de delito
	function cargaComboTipoDelito(){
		
		$("#cbxTipoRelDelPer").empty();
		$('#cbxTipoRelDelPer').append('<option value="-1" selected="selected">-Seleccione-</option>');
		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/ConsultarCatalogoTipoDelito.do',
			dataType: 'xml',
			async: false,
			success: function(xml){
				$(xml).find('ModoParticipacionDelito').each(function(){
					$('#cbxTipoRelDelPer').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
					
				});
			}
		});
	}
	//carga combo calificacion del delito
	function cargaComboCalificacionDelito(){
		
		$("#cbxCalificacionDelPer").empty();
		$('#cbxCalificacionDelPer').append('<option value="-1" selected="selected">-Seleccione-</option>');
		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/ConsultarCatalogoCalificacionDelito.do',
			dataType: 'xml',
			async: false,
			success: function(xml){
				$(xml).find('ModoParticipacionDelito').each(function(){
					$('#cbxCalificacionDelPer').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
					
				});
			}
		});
	}
	
	//carga combo concurso
	function cargaComboConcursoDelito(){
		
		$("#cbxConcursoDelPer").empty();
		$('#cbxConcursoDelPer').append('<option value="-1" selected="selected">-Seleccione-</option>');
		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/ConsultarCatalogoConcursoDelito.do',
			dataType: 'xml',
			async: false,
			success: function(xml){
				$(xml).find('ModoParticipacionDelito').each(function(){
					$('#cbxConcursoDelPer').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
					
				});
			}
		});
	}
	//carga combo orden de resultado
	function cargaComboOrdenResultadoDelito(){
		
		$("#cbxOrdenDelPer").empty();
		$('#cbxOrdenDelPer').append('<option value="-1" selected="selected">-Seleccione-</option>');
		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/ConsultarCatalogoOrdenResDelito.do',
			dataType: 'xml',
			async: false,
			success: function(xml){
				$(xml).find('ModoParticipacionDelito').each(function(){
					$('#cbxOrdenDelPer').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
					
				});
			}
		});
	} 
 
	/*
	*Funcion para llamar a cargar todos los combos de la relacion delito
	*persona
	*/
	function cargaCombosGradoYAtributosRelDelPer(){
		//cargamos el combo con las formas de participacion del involucrado
		cargaComboFormaParticipacion();
		//cargamos el combo de clasificacion delito
		cargaComboClasificacionDelito();
		//cargamos el combo del lugar de delito
		cargaComboLugarDelito();
		//cargamos el combo de modalidad delito
//		cargaComboModalidadDelito();
		//cargamos el combo de modus delito
//		cargaComboModusDelito();
		//cargamos el combo de causa delito
//		cargaComboCausaDelito();
		//cargamos el combo delito estadistica
		cargaComboEstadisticaDelito();
		//cargamos el combo tipo de delito
		cargaComboTipoDelito();
		//carga combo calificacion del delito
		cargaComboCalificacionDelito();
		//carga combo concurso
		cargaComboConcursoDelito();
		//carga combo orden de resultado
		cargaComboOrdenResultadoDelito();
	}
	

</script>

<table width="100%%" border="0" id="tblEstablecerModosYGrados">
  <tr>
    <td width="10%"></td>
    <td width="20%"></td>
    <td width="20%"></td>
    <td width="20%"></td>
    <td width="20%"></td>
    <td width="10%"></td>
  </tr>
  <tr>
    <td></td>
    <td colspan="4" rowspan="6">
    	<table id="gridRelDelPerModosParticipacionPG"></table>
		<div id="pagerGridRelDelPerModosParticipacionPG" align="center"></div>
    </td>
    <td></td>
  </tr>
  <tr>
    <td></td>
    <td></td>
  </tr>
  <tr>
    <td></td>
    <td></td>
  </tr>
  <tr>
    <td></td>
    <td></td>
  </tr>
  <tr>
    <td></td>
    <td></td>
  </tr>
  <tr>
    <td></td>
    <td></td>
  </tr>
  <tr>
    <td></td>
    <td>
    	<input type="button" id="btnAttrRelacionDelPer" value="Grado de participaci&oacute;n" onclick="dialogoAtributosRelDelPer();" class="ui-button ui-corner-all ui-widget"/>
    </td>
    <td>
    	<input type="button" id="btnAnulaRelacionDelPerPg" value="Anular relaci&oacute;n" onclick="anularRelacionDelitoPersonaPersona();" class="ui-button ui-corner-all ui-widget"/>
    </td>
    <td></td>
    <td></td>
    <td></td>
  </tr>
</table>