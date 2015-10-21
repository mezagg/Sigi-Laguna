/**
 * 
 */
package mx.gob.segob.nsjp.sesion;

import java.util.HashMap;
import java.util.Map;

/**
 * @author IgnacioFO
 *
 */
public enum BloqueosNoRestringidos {

	  FONDO_MENU_BARRA	("FONDO_MENU_BARRA.JPG"),
	  FONDO_MENU_BARRA_OVER	("FONDO_MENU_BARRA_OVER.JPG"),
	  ICO_FOLDER_SISUB	("ICO_FOLDER_SISUB.PNG"),
	  ICO_FOLDER_NOSUB	("ICO_FOLDER_NOSUB.PNG"),
	  CHECK	("CHECK.PNG"),
	  PLEC_TOP_NAR	("PLEC_TOP_NAR.JPG"),
	  BACK_USUARIO	("BACK_USUARIO.PNG"),
	  BACK_BIENVENIDO	("BACK_BIENVENIDO.PNG"), 
	  BACK_BIENVENIDO_DEF	("BACK_BIENVENIDO_DEF.PNG"),
	  PLECA_BOTTOM	("PLECA_BOTTOM.PNG"),
	  BACK_IZQ_GRAL	("BACK_IZQ_GRAL.PNG"),
	  BACK_DENUNCIA	("BACK_DENUNCIA.PNG"),
	  BACK_VICTIMA	("BACK_VICTIMA.PNG"),
	  ICN_FLERETRO	("ICN_FLERETRO.PNG"),
	  ICN_FLEADELAN	("ICN_FLEADELAN.PNG"),
	  BTN_MODIFICAR	("BTN_MODIFICAR.PNG"),
	  BTN_GRANDE	("BTN_GRANDE.PNG"),
	  BTN_MEDIANO	("BTN_MEDIANO.PNG"),
	  BACK_DATOS_GRAL	("BACK_DATOS_GRAL.PNG"),
	  BTN_REPRESENTA	("BTN_REPRESENTA.PNG"),
	  BACK_LOGIN	("BACK_LOGIN.PNG"),
	  BACK_BTN_LOG	("BACK_BTN_LOG.PNG"),
	  BTN_MAS	("BTN_MAS.PNG"),
	  BACK_GENERALES	("BACK_GENERALES.PNG"),
	  BACK_OBJ	("BACK_OBJ.PNG"),
	  BACK_HECHOS	("BACK_HECHOS.PNG"),
	  BACK_BTN_NAR	("BACK_BTN_NAR.PNG"),
	  BACK_PLECA	("BACK_PLECA.JPG"),
	  ESTILOS	("ESTILOS.CSS"),
	  LAYOUT_COMPLEX	("LAYOUT_COMPLEX.CSS"),
	  JQUERY	("JQUERY-1.5.1.JS"),
	  JQUERY_UI_CUSTOM	("JQUERY-UI-1.8.10.CUSTOM.JS"),
	  BLOQUEATECLA	("BLOQUEATECLA.JS"),
	  BACK_GRALLOGIN	("BACK_GRALLOGIN.JPG"),
	  TOP_GRAL	("TOP_GRAL.JPG"),
	  LOGO_LOGIN	("LOGO_LOGIN.PNG"),
	  BACK_HUELLA	("BACK_HUELLA.PNG"),
	  PLECA_TOP_LOG	("PLECA_TOP_LOG.PNG"),
	  UI_BG_GLASS_100_FDF5CE_1X400	("UI-BG_GLASS_100_FDF5CE_1X400.PNG"),
	  EEEEEE_40X100_TEXTURES_02_GLASS_90	("EEEEEE_40X100_TEXTURES_02_GLASS_90.PNG"),
	  BACK_APLICA	("BACK_APLICA.PNG"),
	  BACK_ACOR_AD	("BACK_ACOR_AD.JPG"),
	  D6D6D6_40X100_TEXTURES_02_GLASS_80	("D6D6D6_40X100_TEXTURES_02_GLASS_80.PNG"),
	  RESIZABLE_S	("RESIZABLE-S.GIF"),
	  RESIZABLE_W	("RESIZABLE-W.GIF"),
	  RESIZABLE_E	("RESIZABLE-E.GIF"),
	  TOGGLE_UP	("TOGGLE-UP.GIF"),
	  TOGGLE_DN	("TOGGLE-DN.GIF"),
	  D1E6FC_40X100_TEXTURES_10_DOTS_MEDIUM_90	("D1E6FC_40X100_TEXTURES_10_DOTS_MEDIUM_90.PNG"),
	  GO_UP_ON	("GO-UP-ON.GIF"),
	  PIN_UP_ON	("PIN-UP-ON.GIF"),
	  PIN_DN_ON	("PIN-DN-ON.GIF"),
	  PIN_UP_OFF	("PIN-UP-OFF.GIF"),
	  PIN_DN_OFF	("PIN-DN-OFF.GIF"),
	  GO_LT_OFF	("GO-LT-OFF.GIF"),
	  GO_LT_ON	("GO-LT-ON.GIF"),
	  GO_RT_OFF	("GO-RT-OFF.GIF"),
	  GO_RT_ON	("GO-RT-ON.GIF"),
	  UI_BG_GLOSS_WAVE_35_F6A828_500X100	("UI-BG_GLOSS-WAVE_35_F6A828_500X100.PNG"),
	  BACK_ACORDEON_HOR_AC	("BACK_ACORDEON_HOR_AC.PNG"),
	  UI_BG_GLASS_65_FFFFFF_1X400	("UI-BG_GLASS_65_FFFFFF_1X400.PNG"),
	  UI_BG_HIGHLIGHT_SOFT_75_FFE45C_1X100	("UI-BG_HIGHLIGHT-SOFT_75_FFE45C_1X100.PNG"),
	  UI_BG_DIAGONALS_THICK_18_B81900_40X40	("UI-BG_DIAGONALS-THICK_18_B81900_40X40.PNG"),
	  UI_ICONS_222222_256X240	("UI-BG_DIAGONALS-THICK_18_B81900_40X40.PNG"),
	  UI_ICONS_FFFFFF_256X240	("UI-BG_DIAGONALS-THICK_18_B81900_40X40.PNG"),
	  UI_ICONS_EF8C08_256X240	("UI-BG_DIAGONALS-THICK_18_B81900_40X40.PNG"),
	  UI_ICONS_228EF1_256X240	("UI-BG_DIAGONALS-THICK_18_B81900_40X40.PNG"),
	  UI_ICONS_FFD27A_256X240	("UI-ICONS_FFD27A_256X240.PNG"),
	  COMUN	("COMUN.JS"),
	  JQUERY_UI("JQUERY-UI.CSS"),
	  JQUERY_UI_1_8_11_CUSTOM ("JQUERY-UI-1.8.11.CUSTOM.CSS");
	  	
private String valor;
private final static Map<String, BloqueosNoRestringidos> hash = new HashMap<String, BloqueosNoRestringidos>();

static {
	BloqueosNoRestringidos[] acts = BloqueosNoRestringidos.values();
int pos = 0;
while (pos < acts.length) {
    hash.put(acts[pos].getValor(), acts[pos]);
    pos++;
}
}
private BloqueosNoRestringidos(String valor) {
this.valor = valor;
}

public static BloqueosNoRestringidos getByValor(String valor) {
return hash.get(valor);
}

/**
* M�todo de acceso al campo valorId.
* 
* @return El valor del campo valorId asociado en le BD.
*/
public String getValor() {
return valor;
}	
	
	
	
	
	
	
}
