package nutech.awan.ppob.utils;

import lombok.Setter;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

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

    public boolean isValidImage(InputStream inputStream) {

        try (inputStream) {
            BufferedImage image = ImageIO.read(inputStream);
            image.getHeight();
            image.getWidth();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }

        return true;
    }

}
