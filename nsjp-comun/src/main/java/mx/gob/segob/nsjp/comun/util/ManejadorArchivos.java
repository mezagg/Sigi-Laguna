package mx.gob.segob.nsjp.comun.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.log4j.Logger;

public class ManejadorArchivos {

    private static final Logger log = Logger.getLogger(ManejadorArchivos.class);

    /**
     * Metodo que sirve para guardar un archivo en la ruta especifica.
     *
     * @param archivo areglo de bytes que pertenece al archivo
     * @param ruta elemento que contiene la ruta obtenida del parametro de base
     * que indica las carpetas en donde se creara el archivo
     * @param nombre del archivo a crear
     * @param tipo del archivo a crear
     * @return ruta completa donde se guardo el archivo
     * @throws IOException
     */
    public String guardaArchivos(byte[] archivo, String ruta, String nombre, String tipo) throws IOException {
        String rutaCompleta = "";
        if (tipo.equals("image/jpeg") || tipo.equals("image/jpg")) {
            nombre = nombre + ".jpeg";
        } else if (tipo.equals("image/png")) {
            nombre = nombre + ".png";
        } else if (tipo.equals("image/gif")) {
            nombre = nombre + ".gif";
        } else {
            nombre = nombre + tipo;
        }
        File folder = new File(ruta);
        folder.mkdirs();
        rutaCompleta = ruta + "/" + nombre;
        OutputStream nuevo = new FileOutputStream(rutaCompleta);
        nuevo.write(archivo);
        nuevo.close();
        return rutaCompleta;
    }

    /**
     * Metodo que recupera el archivo de la ruta especificada
     *
     * @param ruta ruta especifica de el archivo
     * @return areglo de bytes que pertenece al archivo
     */
    public byte[] recuperaArchivo(String ruta) {

        if (ruta == null || ruta.trim().isEmpty() || ruta.trim().equals("")) {
            log.info("La ruta del archivo es nula o vacia.............");
            return null;
        }

        File file = null;
        FileInputStream fis = null;
        try {
            file = new File(ruta);
            byte[] bytesArchivo = null;

            if (file != null) {
                fis = new FileInputStream(file);
                long tamanoArchivo = file.length();
                if (fis != null && tamanoArchivo < Integer.MAX_VALUE) {
                    bytesArchivo = new byte[(int) tamanoArchivo];
                    fis.read(bytesArchivo);
                }
            }
            return bytesArchivo;
        } catch (Exception e) {
            log.error("Exception" + e.getCause());
            return null;
        } finally {
            try {
                fis.close();
            } catch (IOException e) {
                log.error("Exception" + e.getCause());
            }
        }
    }
}
