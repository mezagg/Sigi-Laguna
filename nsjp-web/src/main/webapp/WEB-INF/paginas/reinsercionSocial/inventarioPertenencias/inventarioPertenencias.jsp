<%@page import="org.apache.commons.lang.BooleanUtils"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ page import="mx.gob.segob.nsjp.comun.enums.actividad.ConfActividadDocumento" %>
<%@ page import="mx.gob.segob.nsjp.dto.reinsercion.InventarioPertenenciaDTO" %>
<%@ page import="mx.gob.segob.nsjp.dto.sentencia.SentenciaDTO" %>
<script type="text/javascript">
	<%
		boolean esActuacion= BooleanUtils.toBoolean(request.getParameter("esActuacion"));
	%>

	var inventarioPertenenciaId = '';
	var sentenciaId = '';
	var cNumeroExpediente = '';
	
	$(document).ready(
			function() {
				
				inventarioPertenenciaId = jQuery('#inventarioPertenenciaId').val();
				sentenciaId = jQuery('#sentenciaId').val();
				cNumeroExpediente = jQuery('#numeroExpediente').val();
				
				jQuery("#tabsInventarioPertenencias").tabs();
				
				jQuery("#dialog:ui-dialog" ).dialog( "destroy" );
				
				jQuery("#dialogDetallesPertenencia").dialog({
					autoOpen: false,
					height: 'auto',
					width:'auto',
					modal: true,
					buttons: {
						Cancelar: function() {
							$( this ).dialog( "close" );
						}
					},
					resizable: false
				});
				
				jQuery("#dialog-cambioExito, #dialogValidarPertenencia").dialog({
					autoOpen: false,
					height: 'auto',
					width:'auto',
					modal: true,
					buttons: {
						Ok: function() {
							$( this ).dialog( "close" );
						}
					},
					resizable: false
				});
				
				jQuery("#dialogConfirmacionEliminarPertenencia").dialog({
					autoOpen: false,
					height: 'auto',
					width:'auto',
					modal: true,
					buttons: {
						Eliminar: function() {
							enviarForma('3');
						},
						Cancelar: function() {
							$( this ).dialog( "close" );
						}
					},
					resizable: false
				});
				
				jQuery("#dialogConfirmDefinitivo").dialog({
					autoOpen: false,
					height: 'auto',
					width:'auto',
					modal: true,
					buttons: {
						Generar: function() {
							$('#definitivo').attr('checked', 'checked');
							deshabilitarBotones();
							$( this ).dialog( "close" );
							enviarForma('4');
						},
						Cancelar: function() {
							$( this ).dialog( "close" );
						}
					},
					resizable: false
				});
				
				jQuery("#gridPertenencias").jqGrid({
					datatype: "xml", 
					rowNum:10,
					rowList:[10,20,30,40,50,60,70,80,90,100],
					autowidth: true,
					height:'auto',
					minWidth: 700, 
					minHeight: 200,						
					caption:"Pertenencias",
					viewrecords: true,
					onSelectRow: function(id){ 
						if($('#definitivo').attr('checked') === false){
							jQuery("#btnModificarPertenencia").button( "option", "disabled", false );
							jQuery("#btnEliminarPertenencia").button( "option", "disabled", false );							
						}
					},
					sortorder: "asc",		           		
					autowidth:true,
        			shrinkToFit:true,
					url:'<%=request.getContextPath()%>/llenarGridInventarioPertenencias.do?sentenciaId='+sentenciaId,
					colNames:['Tipo','Descripci&oacute;n','Cantidad', 'Unidad', 'Condici&oacute;n', 'Entregado'], 
					colModel:[ 	{name:'tipo',index:'1' }, 
								{name:'descripcion',index:'2'}, 
								{name:'cantidad',index:'3'}, 
								{name:'unidad',index:'4'},
								{name:'condicion',index:'5'},
								{name:'entregado',index:'6',<%if (!esActuacion) {out.print("hidden:true");}%>}
							],
					pager: jQuery('#pagerPertenencias')
				}).navGrid('#pagerPertenencias',{edit:false,add:false,del:false});
				
				jQuery( "#btnNuevaPertenencia" ).button().click(
						function() {
							limpiarForma();
							jQuery("#btnRegistrarCambioPertenencia").button( "option", "disabled", true );
							jQuery("#btnRegistrarNuevaPertenencia").button( "option", "disabled", false );
							jQuery("#dialogDetallesPertenencia").dialog("open");
						}
				);
				
				jQuery( "#btnModificarPertenencia" ).button().click(
						function() {
							var pertenenciaId = jQuery("#gridPertenencias").jqGrid('getGridParam','selrow');
							jQuery("#btnRegistrarCambioPertenencia").button( "option", "disabled", false );
							jQuery("#btnRegistrarNuevaPertenencia").button( "option", "disabled", true );
							llenarForma(pertenenciaId, false);	
						}
				);
				
				jQuery( "#btnEliminarPertenencia" ).button().click(
						function() {
							var pertenenciaId = jQuery("#gridPertenencias").jqGrid('getGridParam','selrow');
							jQuery("#btnRegistrarCambioPertenencia").button( "option", "disabled", false );
							jQuery("#btnRegistrarNuevaPertenencia").button( "option", "disabled", true );
							llenarForma(pertenenciaId, true);	
						}
				);
				
				if($('#definitivo').attr('checked') === false){					
					jQuery( "#btnImprimirRecibo" ).button().click(
							function() {
								jQuery("#dialogConfirmDefinitivo").dialog("open");
							}
					);
				}else{
					jQuery( "#btnImprimirRecibo" ).button().click(
						function() {
								consultaPDF($("#documentoId").val());
						}
					);
				
				}
				
				jQuery( "#btnRegistrarNuevaPertenencia" ).button().click(
						function() {
							enviarForma('1');
						}
				);
				
				jQuery( "#btnRegistrarCambioPertenencia" ).button().click(
						function() {
							enviarForma('2');
						}
				);
				
				jQuery("#btnModificarPertenencia").button( "option", "disabled", true );
				jQuery("#btnEliminarPertenencia").button( "option", "disabled", true );
				
				deshabilitarBotones();
				
				<% if (request.getParameter("cambioExitoso") != null){%>
					jQuery("#dialog-cambioExito" ).dialog("open");
				<% }
				
					if (esActuacion) {
				 %>
						$("#btnDevPertenencias").click(
							function() {
//								devolucionDePertenencias();
								seleccionaActuacion();
								cerrarDialogo();
							}
						);
						$("#btnReciboDevPertenencias").click(function () { 
								consultaPDF($("#entregaPertenenciasDocId").val());
								cerrarDialogo();
							}
						);
						$("#btnCancelar").click(cerrarDialogo);
 		
				 		jQuery("#customConfirm").bind( "dialogopen", function(event, ui) {
							jQuery("#customConfirm").dialog('option', 'buttons', []);
							jQuery("#customConfirm").parent().children().children('.ui-dialog-titlebar-close').hide();
						});
				<%}%>
			});

			//Variable para controlar el action para consultar pdf's
			var accionConsultarPdf="<%= request.getContextPath() %>/ConsultarContenidoArchivoDigital.do";
	
			/*
			*Funcion que aplica submit a la forma para abrir el documento solicitado
			*id= id del documento seleccionado en el grid de documentos
			*As&iacute; se obtenia anteriormente:
			*	
			*document.frmDoc.documentoId.value = id;
			*document.frmDoc.submit();
			*/
			function consultaPDF(id){
				document.frmDoc.action=accionConsultarPdf+"?documentoId="+id;
				document.frmDoc.submit();
			}
			<% 
				if (esActuacion) {
			 %>
			 
			 
				 	function cerrarDialogo(){
				 		jQuery("#customConfirm").dialog("close");
				 	}
			 
					function devolucionDePertenencias(){
						$.ajax({
							type: 'POST',
							url: '<%= request.getContextPath()%>/devolucionDePertenencias.do?sentenciaId='+sentenciaId,
							data: '',
							dataType: 'xml',
							async: false,
							success: function(xml) {
								var respuesta=$(xml).find('response').find('body').find('respuesta').text();
								
								if (respuesta == "exito") {
									customAlert("Los datos se han actualizado con exito.", 
									"Devoluci&oacute;n de pertenencias",
									function(){
									;
									cerrarDialogo();
									
									});
								} else {
									customAlert("Ha ocurrido un error, por favor intente mas tarde.", 
									"Devoluci&oacute;n de pertenencias");						
								}
							}
						});					
					}
			<% } %>


	
	function limpiarForma(){
		jQuery('#pertenenciaId').val("");
		jQuery('#tipoPertId').val("-1");
		jQuery('#descPert').val("");
		jQuery('#cantidadPert').val("0");
		jQuery('#unidadPertId').val("-1");
		jQuery('#condicionPertId').val("-1");
	}
	
	function llenarForma(id, banderaEliminacion){
		GetAJAXValues(
				id,
			function(objRemoteData){
				llenandoForma(
						objRemoteData.object
				);
				if (banderaEliminacion){
					jQuery("#dialogConfirmacionEliminarPertenencia").dialog("open");
				}else{
					jQuery("#dialogDetallesPertenencia").dialog("open");
				}
			}
		);		
	}
	
	function GetAJAXValues( id, fnCallback ){
		if (GetAJAXValues.Xhr){
			GetAJAXValues.Xhr.abort();
		}

		GetAJAXValues.Xhr = $.ajax({
			type: "post",
			url:'<%=request.getContextPath()%>/obtenerDetallesPertenenciaPorId.do',
			data: {
				id:id
			},
			dataType: "json",

			success: function( objData ){
				fnCallback({object: objData});
			},

			error: function(){
				customAlert("No se pueden mostrar los detalles.<br /> Intente de nuevo por favor. ");
			},

			complete: function(){
				GetAJAXValues.Xhr = null;
			}
		});
	}
	
	function llenandoForma(object){
		jQuery('#pertenenciaId').val(object.idPertenencia);
		jQuery('#tipoPertId').val(object.tipoPertenenciaId);
		jQuery('#descPert').val(object.descripcionPertenencia);
		jQuery('#cantidadPert').val(object.cantidadPertenencia);
		jQuery('#unidadPertId').val(object.unidadPertenenciaId);
		jQuery('#condicionPertId').val(object.condicionPertenenciaId);
	}
	
	function enviarForma(toPath){
		if (toPath == '4'){
<%-- 			var action= '<%=request.getContextPath()%>'; --%>
// 			action += '/generarReciboInventarioPertenencias.do';
// 			document.getElementById("registrarPertenenciaForm").action=action;
// 			document.getElementById("registrarPertenenciaForm").submit();
			var documentoId = jQuery('#documentoId').val(); 
			generarReciboInvPert(documentoId);
		}else{	
			var enviar = validarDatosForma();
			if(enviar){
				var action= '<%=request.getContextPath()%>';
				switch(toPath){
					case '1':
		  				action += '/guardarPertenenciaInventario.do';
		  				action += '?sentenciaId='+sentenciaId;
		  				break;
					case '2':
		  				action += '/actualizarPertenenciaInventario.do';
		  				action += '?sentenciaId='+sentenciaId;
		  				break;
					case '3':
		  				action += '/eliminarPertenenciaInventario.do';
		  				action += '?sentenciaId='+sentenciaId;
		  				break;
					default:
		  				return false;
					}
				document.getElementById("registrarPertenenciaForm").action=action;
				document.getElementById("registrarPertenenciaForm").submit();
			}else{
				jQuery("#dialogValidarPertenencia").dialog("open");
			}
		}
	}
	
	function validarDatosForma(){
		if (jQuery('#tipoPertId').val() == -1
				|| jQuery('#descPert').val() == ""
				|| !validaDecimalOchoDos(jQuery('#cantidadPert').val())
				|| jQuery('#condicionPertId').val() == -1 ){
			return false;
		}else{
			return true;
		}
	}
	
	function deshabilitarBotones(){
		if($('#definitivo').attr('checked') === true){
			jQuery("#btnNuevaPertenencia").button( "option", "disabled", true );
//			jQuery("#btnImprimirRecibo").button( "option", "disabled", true );
		}

		if(jQuery('#entregado').val() == "true"){
			$("#btnDevPertenencias").hide();
			$("#btnReciboDevPertenencias").show();
		} else {
			$("#btnDevPertenencias").show();
			$("#btnReciboDevPertenencias").hide();		
		}
		
	}
	
	function generarReciboInvPert(documentoId){
		var confActividadId=<%=ConfActividadDocumento.GENERAR_ACUSE_RECIBO_PERTENENCIAS.getValorId()%>;
		var actividad=0;
		var formaID=0;
		var titulo="";
		var usaeditor="";
		var estatusId="";
		$.ajax({
			type: 'POST',
			url: '<%= request.getContextPath()%>/obtenerConfActividadDocumento.do?idConf='+confActividadId,
			data: '',
			dataType: 'xml',
			async: false,
			success: function(xml){
				actividad=$(xml).find('confActividadDocumentoDTO').find('tipoActividadId').text();
				formaID=$(xml).find('confActividadDocumentoDTO').find('formaId').text();
				titulo=$(xml).find('confActividadDocumentoDTO').find('nombreDocumento').text();
				usaeditor=$(xml).find('confActividadDocumentoDTO').find('usaEditor').text();
				estatusId=$(xml).find('confActividadDocumentoDTO').find('estadoCambioExpediente').find('idCampo').text();
			}
		});
		actuacion=actividad;
		if(usaeditor == "true" && documentoId==0){
			var parametros = "?formaId="+formaID+"&numeroUnicoExpediente="+cNumeroExpediente + "&nuevaActividad=" + actividad 
					+ "&sentenciaId=" + sentenciaId + "&inventarioPertenenciaId=" + inventarioPertenenciaId;			 
			customVentana("iframewindowElaborarDocumentoRS", ""+titulo, "/elaborarDocumentosRS.do", parametros);    			
		}else if (usaeditor == "true" && documentoId!=0){
			var parametros = "?formaId="+formaID+"&numeroUnicoExpediente="+cNumeroExpediente + "&nuevaActividad=" + actividad 
					+ "&sentenciaId=" + sentenciaId + "&inventarioPertenenciaId=" + inventarioPertenenciaId + "&documentoId=" + documentoId;			 
			customVentana("iframewindowElaborarDocumentoRS", ""+titulo, "/elaborarDocumentosRS.do", parametros);
		}

	}
	
</script>
<div id="tabsInventarioPertenencias">
	<ul> 
		<li><a href="#InventarioPertenenciasRS">Administrar inventario de pertenencias</a></li>
	</ul>
	<div id="InventarioPertenenciasRS">
		<br />
		<fieldset>
			<legend>Datos de la sentencia</legend>
			<table border="0" align="center">
				<tr <% 
					if (esActuacion) {
						out.print("style='display:none;'");
					}
				 %>
				>
					<td colspan="2">
						<table border="0">
							<tr>
								<td align="right">Sentenciado:</td>
								<td align="left"><html:text
										name="InventarioPertenenciasForm"
										property="nombreSentenciado" readonly="true"
										styleId="nombreSentenciado" styleClass="texto" /></td>
							</tr>
							<tr>
								<td align="right">NUS:</td>
								<td align="left"><html:text
										name="InventarioPertenenciasForm" property="nus"
										readonly="true" styleId="nus" styleClass="texto" /></td>
							</tr>
							<tr>
								<td align="right">Puntos por cumplir:</td>
								<td align="left"><html:text
										name="InventarioPertenenciasForm"
										property="puntosPorAcumular"
										readonly="true"
										styleClass="texto" /></td>
							</tr>							
						</table></td>
					<td colspan="2">
						<table>
							<tr>
								<td align="right">Tipo de pena:</td>
								<td align="left"><html:text
										name="InventarioPertenenciasForm"
										property="tipoSentencia" readonly="true" size="30"
										styleId="tipoSentencia" styleClass="texto" /></td>
							</tr>
							<tr>
								<td align="right">CERESO:</td>
								<td align="left"><html:text
										name="InventarioPertenenciasForm" property="nombreCereso"
										readonly="true" size="30" styleId="nombreCereso"
										styleClass="texto" /></td>
							</tr>
							<tr>
								<td align="right">Puntos acumulados:</td>
								<td align="left"><html:text
										name="InventarioPertenenciasForm"
										property="puntosAcumulados" readonly="true"
										styleId="puntosAcumulados" styleClass="texto" /></td>
							</tr>
							
							
						</table></td>
					<td colspan="2">
						<table>
							<tr>
								<td align="right">Fecha de inicio de pena:</td>
								<td align="left"><html:text
										name="InventarioPertenenciasForm"
										property="fechaInicioPena" readonly="true"
										styleId="fechaInicioPena" styleClass="texto" /></td>
							</tr>
							<tr>
								<td align="right">Fecha de fin de pena:</td>
								<td align="left"><html:text
										name="InventarioPertenenciasForm" property="fechaFinPena"
										readonly="true" styleId="fechaFinPena" styleClass="texto" />
								</td>
							</tr>
							<tr>
								<td align="right">Es Definitivo:</td>
								<td align="left">
								<html:checkbox 	name="InventarioPertenenciasForm" 
												property="definitivo" 
												styleId="definitivo"
												disabled="true" />
							</tr>
						</table></td>
				</tr>
				<tr>
					<td colspan="6" align="center">
						<div id="catProg2">
							<table id="gridPertenencias"></table>
							<div id="pagerPertenencias" style="vertical-align: top;"></div>
						</div>
					</td>
				</tr>
				<tr>
					<td colspan="6" align="center">
				<% 
					if (esActuacion) {
				 %>
						<button id="btnDevPertenencias" type="button" class="btn_Generico">Devoluci&oacute;n de pertenencias</button>
						<button id="btnReciboDevPertenencias" type="button" class="btn_Generico">Recibo de devoluci&oacute;n de pertenencias</button>
						<button id="btnCancelar" type="button" class="btn_Generico">Cancelar</button>
				 <% } else { %>
						<button id="btnNuevaPertenencia" type="button" class="btn_Generico">Agregar</button>
						<button id="btnModificarPertenencia" type="button" class="btn_Generico">Modificar</button>
						<button id="btnEliminarPertenencia" type="button" class="btn_Generico">Eliminar</button>
					</td>
				</tr>
				<tr>
					<td colspan="6" align="center">
						<button id="btnImprimirRecibo" type="button" class="btn_Generico">Imprimir recibo</button>
				<% } %>
					</td>
				</tr>
			</table>
		</fieldset>
	</div>
	<div style="display: none;">	
		<div id="dialogDetallesPertenencia" title="Detalles de la pertenencia">
			<html:form action="/guardarPertenenciaInventario.do" styleId="registrarPertenenciaForm">
				<fieldset>
					<legend>Datos de la pertenencia</legend>
					<table>	
						<tr>
							<td align="right">Tipo :</td>
							<td align="left">
								<html:select name="InventarioPertenenciasForm" property="tipoPertId" styleId="tipoPertId" styleClass="texto">
									<option value="-1">SELECCIONE UNA OPCI&Oacute;N</option>
									<html:optionsCollection name="InventarioPertenenciasForm" property="lstCatTipoPertDTO" label="valor" value="clave" />
								</html:select>
							</td>
						</tr>
						<tr>
							<td align="right">Descripci&oacute;n :</td>
							<td align="left">
								<html:textarea name="InventarioPertenenciasForm" property="descPert" styleId="descPert" styleClass="texto" style="width: 99%;"/>
							</td>
						</tr>
						<tr>
							<td align="right">Cantidad :</td>
							<td align="left">
								<html:text name="InventarioPertenenciasForm" property="cantidadPert" styleId="cantidadPert" styleClass="texto" />
							</td>
						</tr>
						<tr>
							<td align="right">Unidad de medida :</td>
							<td align="left">
								<html:select name="InventarioPertenenciasForm" property="unidadPertId" styleId="unidadPertId" styleClass="texto">
									<option value="-1">SELECCIONE UNA OPCI&Oacute;N</option>
									<html:optionsCollection name="InventarioPertenenciasForm" property="lstCatUnidadPertDTO" label="valor" value="clave" />
								</html:select>
							</td>
						</tr>
						<tr>
							<td align="right">Condici&oacute;n :</td>
							<td align="left">
								<html:select name="InventarioPertenenciasForm" property="condicionPertId" styleId="condicionPertId" styleClass="texto">
									<option value="-1">SELECCIONE UNA OPCI&Oacute;N</option>
									<html:optionsCollection name="InventarioPertenenciasForm" property="lstCatCondicionPertDTO" label="valor" value="clave" />
								</html:select>
							</td>
						</tr>
						<tr>
							<td colspan="2" align="center">
								<button id="btnRegistrarNuevaPertenencia" type="button" class="btn_Generico" >Registrar</button>
								<button id="btnRegistrarCambioPertenencia" type="button" class="btn_Generico" >Modificar</button>
							</td>
						</tr>
					</table>
					<html:hidden name="InventarioPertenenciasForm" property="pertenenciaId" styleId="pertenenciaId" />
					<html:hidden name="InventarioPertenenciasForm" property="entregado" styleId="entregado" />
					<html:hidden name="InventarioPertenenciasForm" property="documentoId" styleId="documentoId" />
					<html:hidden name="InventarioPertenenciasForm" property="entregaPertenenciasDocId" styleId="entregaPertenenciasDocId" />
					<html:hidden name="InventarioPertenenciasForm" property="sentenciaId" styleId="sentenciaId" />
					<html:hidden name="InventarioPertenenciasForm" property="inventarioPertenenciaId" styleId="inventarioPertenenciaId" />
					<html:hidden name="InventarioPertenenciasForm" property="numeroExpediente" styleId="numeroExpediente" />
				</fieldset>
			</html:form>
		</div>
	
		<div id="dialogConfirmacionEliminarPertenencia" title="Confirmaci&oacute;n de eliminaci&oacute;n">
			<p>La pertenencia seleccionada ser&aacute; eliminada de manera definitiva.</p>
			<p>&iquest;Desea eliminar la pertenencia?</p>
		</div>
		<div id="dialogValidarPertenencia" title="Error de validaci&oacute;n">
			<p>Los siguientes datos de la pertenencia son requeridos:</p>
			<ul>
				<li>Tipo</li>
				<li>Descripci&oacute;n</li>
				<li>Cantidad (Se permiten valores de 6 d&iacute;gitos para los enteros y dos para los decimales )</li>
				<li>Condici&oacute;n</li>
			</ul>	 
			<p>Favor de verificar los datos anteriores</p>
		</div>
		<div id="dialogConfirmDefinitivo" title="Generar recibo de inventario de pertenencias">
			<p>El inventario de pertenencias ser&aacute; guardado de manera definitiva.<br/>
			Lo cual implica que no se podr&aacute;n modificar las pertenencias asociadas al inventario.<br/>
			Asimismo, se generar&aacute; el acuse de recibo de las pertenencias.<br/>
			&iquest;Desea guardar el inventario de pertenencias de manera definitiva y generar el recibo?</p>
		</div>
		<div id="dialog-cambioExito" title="Modificaci&oacute;n exitosa">
			<p>La informaci&oacute;n se actualiz&oacute; con &eacute;xito</p>
		</div>		
		<form name="frmDoc" action="<%= request.getContextPath() %>/ConsultarContenidoArchivoDigital.do" method="post">
			<!--<input type="hidden" name="documentoId" />-->
		</form>		
	</div>
</div>