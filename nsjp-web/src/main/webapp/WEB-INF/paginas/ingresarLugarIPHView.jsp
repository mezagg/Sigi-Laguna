    <!--CSS, modificada para las tabs del domicilio -->
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/estiloTab.css"/>
    <link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/multiselect/jquery.multiselect.css" />
	<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/multiselect/style.css" />
	<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/multiselect/prettify.css" />
	<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/south-street/jquery-ui-1.8.10.custom.css" />
	<script src="<%=request.getContextPath()%>/js/prettify.js"></script>
	<script src="<%=request.getContextPath()%>/js/jquery.multiselect.js"></script>
	    
	<script type="text/javascript">	 
	var idLugar = <%=request.getParameter("idLugar")%>;
	
	jQuery().ready(	
		function () {
			$("#tabs").tabs();
			$("#tabs-puntocarretero").tabs();

			ocultaDomicilioNotificaciones();
			//$("#cbxTipoLugar").change(onSelectChangeLugar);
			$("#cbxTipoLugar").multiselect({ 
				multiple: false, 
				header: "Seleccione", 
				position: { 
					my: 'top', 
					at: 'top' 
				},
				selectedList: 1, 
				close: function (event,ui){
					onSelectChangeLugar();}
			});

			$("#cbxTipoLugar").val(idLugar);
	});
	function onSelectChangeLugar(){
		var selected = $("#cbxTipoLugar option:selected");
		if (parseInt(selected.val()) == 1 ){
			$("#domicilio").css("display", "block");
			$("#imediacionLugar").css("display", "none");
		}else if (parseInt(selected.val()) == 2 ){
			$("#domicilio").css("display", "none");
			$("#imediacionLugar").css("display", "block");
		}else if (parseInt(selected.val()) == 3 ){
			$("#domicilio").css("display", "none");
			$("#imediacionLugar").css("display", "none");
		}else if (parseInt(selected.val()) == 4 ){
			$("#domicilio").css("display", "none");
			$("#imediacionLugar").css("display", "none");
		}else if (parseInt(selected.val()) == 5 ){
			$("#domicilio").css("display", "none");
			$("#imediacionLugar").css("display", "none");
		}else if (parseInt(selected.val()) == 6 ){
			$("#domicilio").css("display", "none");
			$("#imediacionLugar").css("display", "none");
		}
	}

	function obtenerDatos(){
		var parametros='';
		var selected = $("#cbxTipoLugar option:selected");
		parametros='&tipoLugar='+selected.val();
		if (parseInt(selected.val()) == 1 ){
			parametros += obtenerParametrosDomicilio();
		}else if (parseInt(selected.val()) == 2 ){

		}
		return parametros;
	}
		
	function seteaLugar(xml)
	{
		//Seteamos la informacion cuando el lugar es un DOMICILIO
		$(xml).find('hechoDTO').find('lugar').find('valorCalleId').each(function(){
			$('#cbxTipoLugar').find("option[value='1']").attr("selected","selected");
			onSelectChangeLugar();
			pintaDatosDomicilioHecho(xml);
			bloqueaCamposDomicilioHecho(0);
	      });	
	}
	
	function bloqueaAllCamposLugaresHecho(bandera)
	{
		//bloqueamos o desbloqueamos los campos para el lugar Domicilio
		bloqueaCamposDomicilioHecho(bandera);

	}
	
    </script>
    
    <table cellpadding="0" cellspacing="0" >
    	<tr height="10%">
    		<td>
    			<select id="cbxTipoLugar" style="width:200px;">
              		<option value="-1">-Seleccione-</option>
              		<option value="1">-Domicilio-</option>
              		<option value="2">-Imediaci&oacute;n del Lugar-</option>
            	</select>
            </td>
    	</tr>
    	<tr height="90%">
    		<td>
    			<div id="domicilio" style="display: none;" ><jsp:include page="ingresarDomicilioView.jsp"/></div>
    			<div id="imediacionLugar" style="display: none;" >imediacion del lugar</div>
    		</td>
    	</tr>
    </table>