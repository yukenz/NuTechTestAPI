package nutech.awan.ppob.utils;

import lombok.Setter;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class ImageResource implements ResourceLoaderAware {


    @Setter
    private ResourceLoader resourceLoader;

    public static final String CLASS_PATH_FOLDER = "classpath:/assets/";

    public InputStream getImageStream(String fileName) {

        String classPath = CLASS_PATH_FOLDER + fileName;
        Resource resource = resourceLoader.getResource(classPath);
        try {
            return resource.getInputStream();
        } catch (IOException ex) {
            System.out.printf("Failed open resource %s : %s%n", classPath, ex.getMessage());
            return null;
        }
    }

    public void saveImage(InputStream inputStream, String fileName) throws IOException {

        String classPath = CLASS_PATH_FOLDER;
        Resource assetsFolder = resourceLoader.getResource(classPath);

        Path pathFile = Path.of(Path.of(assetsFolder.getURI()).toString() + "/" + fileName);

        //Create
        if (!Files.exists(pathFile)) {
            Files.createFile(pathFile);
        }


        //Write ke file
        try (OutputStream outputStream = Files.newOutputStream(pathFile)) {
            outputStream.write(inputStream.readAllBytes());
            outputStream.flush();
        }

    }

}
