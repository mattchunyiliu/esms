package kg.kundoluk.school.storage.impl;

import kg.kundoluk.school.storage.ImageConvertService;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

@Service
public class ImageConvertServiceImpl implements ImageConvertService {
    @SneakyThrows
    @Override
    public InputStream convertToJpg(InputStream input) {
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            BufferedImage image = ImageIO.read(input);
            BufferedImage jpegBuffer = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
            jpegBuffer.createGraphics().drawImage(image, 0, 0, Color.WHITE, null);

            ImageIO.write(jpegBuffer, "jpg", os);
            return new ByteArrayInputStream(os.toByteArray());
        }
    }
}
