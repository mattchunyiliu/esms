package kg.kundoluk.school.storage;

import java.io.InputStream;

public interface ImageConvertService {
    InputStream convertToJpg(InputStream input);
}
