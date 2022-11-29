package kg.kundoluk.school.service.telegram;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kg.kundoluk.school.dto.ApiError;
import org.springframework.stereotype.Service;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author ELAMAN NAZARKULOV on 4/12/20
 */
@Service
public class TelegramBotService {

    private static final String CHAT_ID = "649415681";
    private static final String TOKEN = "1391096602:AAFDWWYuC1vtkKsde2c1ukm2ighmVBN4ukM";

    public void send(ApiError apiError) throws JsonProcessingException {

        String urlString = "https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text=%s";

        ObjectMapper objectMapper = new ObjectMapper();
        String text = objectMapper.writeValueAsString(apiError);//apiError.getMessage();

        urlString = String.format(urlString, TOKEN, CHAT_ID, text);

        try {
            URL url = new URL(urlString);
            URLConnection conn = url.openConnection();
            InputStream is = new BufferedInputStream(conn.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
