$(document).ready(function() {

    // Construimos el grid de las notificaciones.
    jQuery("#notificaciones").jqGrid({
        url: CONTEXT_ROOT + '/consultarNotificacionesXUsuario.do',
        datatype: "xml",
        // * n�mero de caso, n�mero de expediente del usuario notificador, asunto, fecha-hora de elaboraci�n
        colNames:['idNotificacion','N�mero de caso','N�mero de expediente', 'asunto', 'Fecha de elaboraci�n'],
        colModel:[
                {name:'idNotificacion',index:'idNotificacion', width:75, viewable:false, key:true, hidden:true},
                {name:'numeroCaso',index:'numeroCaso', width:75},
                {name:'numeroExpediente',index:'numeroExpediente', width:90, sortable:false},
                {name:'asunto',index:'asunto', width:100, sortable:false},
                {name:'fechaElaboracion',index:'fechaElaboracion', width:80, align:"right"}
        ],
        rowNum:10,
        autowidth: true,
        rowList:[10,20,30],
        pager: jQuery('#paginador'),
        sortname: 'numeroCaso',
        viewrecords: true,
        sortorder: "desc",
        onSelectRow: function(id){
            if(id){
                var idNotificacion = id;
//                alert(id);
                abreNuevoFrame("iframeWindowDetalleNotificacion",
                            "/consultarDetalleNotificaciones.jsp?idNotificacion=" + idNotificacion, 0, 0, 800, 500, "Detalle de la notificaci�n");
            }
        },
        caption:"Notificaciones"
    }).navGrid('#paginador',{edit:false,add:false,del:false});
}); // fin onready de jquery

