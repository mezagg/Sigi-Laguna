$(document).ready(function() {
    $("#menuGenerarNotificacion").tabs();

    // Construimos el grid de las notificaciones.
    $("#destinatariosSistema").jqGrid({
        url: CONTEXT_ROOT + '/consultaDestinatarios.do?tipo=sistema',
        datatype: "xml",
        // * n�mero de caso, n�mero de expediente del usuario notificador, asunto, fecha-hora de elaboraci�n
        colNames:['idDestinatario','Nombre','Puesto', 'Correo Electr�nico', 'Principal', "Copia"],
        colModel:[
                {name:'idDestinatario',index:'idDestinatario', width:75, viewable:false, key:true, hidden:true},
                {name:'nombre',index:'nombre', width:75},
                {name:'puesto',index:'puesto', width:90, sortable:false},
                {name:'correo',index:'correo', width:100, sortable:false},
                {name:'esPrincipal',index:'esPrincipal', editable:true, edittype:'checkbox', editoptions: { value:"True:False"},
                    formatter: "checkbox", formatoptions: {disabled : false}, width:100, sortable:false, align:"center"},
                {name:'esCopia',index:'esCopia', width:80, editable:true, edittype:'checkbox', editoptions: { value:"True:False"},
                    formatter: "checkbox", formatoptions: {disabled : false}, align:"center"}
        ],
        rowNum:10,
        autowidth: true,
        rowList:[10,20,30],
        pager: $('#paginadorDestinatariosSistema'),
        sortname: 'idDestinatario',
        viewrecords: true,
        sortorder: "desc",
        caption:"Destinatario(s) Usuarios del Sistema"
    }).navGrid('#paginadorDestinatariosSistema',{edit:false,add:false,del:false});
    $("#destinatariosSistemaWrap").hide();

    $("#destinatariosFisico").jqGrid({
        url: CONTEXT_ROOT + '/consultaDestinatarios.do?tipo=fisico',
        datatype: "xml",
        // * n�mero de caso, n�mero de expediente del usuario notificador, asunto, fecha-hora de elaboraci�n
        colNames:['idDestinatario','Nombre','Puesto', 'Correo Electr�nico', 'Principal', "Copia"],
        colModel:[
                {name:'idDestinatario',index:'idDestinatario', width:75, viewable:false, key:true, hidden:true},
                {name:'nombre',index:'nombre', width:75},
                {name:'puesto',index:'puesto', width:90, sortable:false},
                {name:'correo',index:'correo', width:100, sortable:false},
                {name:'esPrincipal',index:'esPrincipal', editable:true, edittype:'checkbox', editoptions: { value:"True:False"},
                    formatter: "checkbox", formatoptions: {disabled : false}, width:100, sortable:false, align:"center"},
                {name:'esCopia',index:'esCopia', width:80, editable:true, edittype:'checkbox', editoptions: { value:"True:False"},
                    formatter: "checkbox", formatoptions: {disabled : false}, align:"center"}
        ],
        rowNum:10,
        autowidth: true,
        rowList:[10,20,30],
        pager: $('#paginadorDestinatariosFisico'),
        sortname: 'idDestinatario',
        viewrecords: true,
        sortorder: "desc",
        caption:"Destinatario(s) Externos"
    }).navGrid('#paginadorDestinatariosSistema',{edit:false,add:false,del:false});
    $("#destinatariosFisicoWrap").hide();

    ejecutaAction("/armaNotificacion", function(respuesta){
            armaNotificacion(respuesta)
    });

    function armaNotificacion(notificacionXml){
        var notificacion = $(notificacionXml).find("Notificacion");
        var funcionario = $(notificacionXml).find("Funcionario");
        var notificacionTextArea = $("#notificacionTextArea");
        var textoNotificacion = "";
        var numNotificacion = "No. de Notificaci�n: " + notificacion.find("consecutivoNotificacion").text();
        textoNotificacion += numNotificacion + "\n";
        var motivo = "ASUNTO: " + notificacion.find("motivo").text();
        textoNotificacion += motivo + "\n";
        var fechaElaboracion = "Fecha de elaboraci�n de la notificaci�n: " + notificacion.find("lugar").text();
        var fecha = Date.parse(notificacion.find("fechaCreacion").text());
        fecha = new Date(fecha);
        fechaElaboracion += " a d�a " + fecha.getDate() + " del mes " + getMesLetra(fecha) + " del a�o " + fecha.getFullYear();
        fechaElaboracion += ", " + getHoraMinutoActual(fecha);
        textoNotificacion += fechaElaboracion + "\n";
        var numeroRenglonesEspacio = 10;
        for(var i = 0; i < numeroRenglonesEspacio; ++i){
            textoNotificacion += "\n";
        }
        
        var atentamente = ""
        atentamente += "                                                                                                                                      ATENTAMENTE\n";
        atentamente += "                                                                                                                                      SUFRAGIO EFECTIVO NO REELECCI�N\n";
        var espacioIzquierdo = "";
        espacioIzquierdo += "                                                                                                                                ";
        var nombrePuesto = espacioIzquierdo + funcionario.find("puesto").find("valor").text();
        var areaAdministrativa = espacioIzquierdo + funcionario.find("departamento").find("area").find("nombre").text();
        var nombreServidor =espacioIzquierdo + funcionario.find("nombreFuncionario").text() + " ";
        nombreServidor += funcionario.find("apellidoPaternoFuncionario").text() + " ";
        nombreServidor += funcionario.find("apellidoMaternoFuncionario").text();

        textoNotificacion+=atentamente + "\n";
        textoNotificacion+=nombrePuesto + "\n";
        textoNotificacion+=areaAdministrativa + "\n";
        textoNotificacion+=nombreServidor + "\n";
        notificacionTextArea.val(textoNotificacion);
    }
}); // fin de onread jquery.

function seleccionarDestinatarios(){
    $("#destinatariosSistemaWrap").toggle("fast");
    $("#destinatariosFisicoWrap").toggle("fast");
}