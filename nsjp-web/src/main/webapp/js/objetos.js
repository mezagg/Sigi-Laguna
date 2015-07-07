
    /**
	 * Funcion que consulta el catalogo del tipo de estudio pericial 
	 * de acuerdo al tipo de Objeto.
	 */
	function consultarTipoEstudio(tipoObjetoId){
		
		jQuery("#gridCatTipoEstudio").jqGrid({ 							
        	url: contextoPagina+'/consultarEstudiosPericialesPorTipoObjeto.do?tipoObjetoId='+tipoObjetoId,
			datatype: "xml",
			colNames:['Estudio Pericial'], 
			colModel:[ 
			        	{name:'EstudioPericial',index:'2', width:50, align:'center', sortable: false}
					],
			pager: jQuery('#paginadorGridCatTipoEstudio'),
			rowNum:10,
			rowList:[10,20,30,40,50,60],
			width: 550,
			caption:"Tipo de Estudio",
			//sortname: '1',
			viewrecords: true,
			id: 'gridCatTipoEstudio'
			//sortorder: "desc"
		}).navGrid('#paginadorGridCatTipoEstudio',{edit:false,add:false,del:false});
		
		$("#gridCatTipoEstudio").trigger("reloadGrid");
	}
	
	/**
	* Funcion que carga el combo Relaciones del hecho
	*/
	function cargaRelacionesHecho(paramEntidad) {
		
		if(paramEntidad == entidadFedYuc){
			$('#trRelacionHecho').hide();
			return;
		}
		  
		$.ajax({
			async: false,
			type: 'POST',
			url: contextoPagina + '/consultarRelacionesHecho.do',
			data: '',
			dataType: 'xml',
			success: function(xml){
				$(xml).find('catRelacionesHechos').each(function(){
					$('#cbxRelacionHecho').append('<option value="' + $(this).find('clave').text() + '">' + $(this).find('valor').text() + '</option>');
				});
			}
		});
	}
	
	
	
	
	function existeCadenaDeCustodiaPorIdObjeto(elementoID){
		var existeCadena = null;
		$.ajax({
			type: 'POST',
        	url: contextoPagina+'/existeCadenaDeCustodiaPorIdObjeto.do',
			data: 'elementoID='+ elementoID, 
			async: false,
			dataType: 'xml',
			success: function(xml){
				var mensaje = $(xml).find('body').find('respuesta').text();
				if(parseInt(mensaje) == 1)
					existeCadena = true;
				else
					existeCadena = false;
										
			}
		});	
		return existeCadena;
	}
	
	function existenEslabonesPorIdObjeto(elementoID){
		var existeEslabon = null;
		$.ajax({
			type: 'POST',
        	url: contextoPagina+'/existenEslabonesPorIdObjeto.do',
			data: 'elementoID='+ elementoID, 
			async: false,
			dataType: 'xml',
			success: function(xml){
				var mensaje = $(xml).find('body').find('respuesta').text();
				if(parseInt(mensaje) == 1)
					existeEslabon = true;
				else
					existeEslabon = false;
										
			}
		});	
		return existeEslabon;
	}
	
	

	function validaExtensionDeImagenDeObjeto(archivo,extensionesPermitidas) {
			esExtensionPermitida = false; 
			if (!archivo) return;
			ext = (archivo.substring(archivo.lastIndexOf("."))).toLowerCase();

			arregloDeExtensionesPermitidas = extensionesPermitidas.split(",");
			for (var i = 0; i < arregloDeExtensionesPermitidas.length; i++) { 
				if (arregloDeExtensionesPermitidas[i] == ext) { 
	 			esExtensionPermitida = true; 
	 			break; 
				} 
			}
			return esExtensionPermitida;
	}

			
	
 	/**
 	 * Permite cerrar la venta de ingreso de un objeto
 	 * en cadena de custodia, cerrarVentanaObjeto
 	 * se declara en asentarRegCadenaCustodiaView.jsp
 	 */
	function regresarControlCadenaCustodiaActualizacion(){
		window.parent.cerrarVentanaDeObjeto();
	}
					