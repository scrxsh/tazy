package com.project.sales_manager_ml.uploadFiles;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@Service
public class CargaArchivosService {


    private final String url = "uploads/images/";


    public String guardarArchivo(MultipartFile file, String nombre) throws IOException {
        if (!file.isEmpty()) {
            byte[] bytes = file.getBytes();
            String nombreOriginal = Objects.requireNonNull(file.getOriginalFilename());
            String extension = nombreOriginal.substring(file.getOriginalFilename().lastIndexOf("."));

            String encode = nombre.trim().replace(" ", "_").replaceAll("[^\\p{L}\\p{Nd}_\\-]", "");
            Path path = Paths.get(url + encode + extension);
            Files.createDirectories(path.getParent());
            Files.write(path, bytes);
            return encode + extension;
        }
        return null;
    }


    public void eliminarArchivo(String imgPrenda){
        File file = new File(url+imgPrenda);
        file.delete();
    }
}
