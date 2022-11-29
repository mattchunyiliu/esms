package kg.kundoluk.school.config.ws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@Component
public class HttpHandshakeInterceptor implements HandshakeInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(HttpHandshakeInterceptor.class);

    @Override
    public boolean beforeHandshake(
            ServerHttpRequest serverHttpRequest,
            ServerHttpResponse serverHttpResponse,
            WebSocketHandler webSocketHandler,
            Map<String, Object> map) throws Exception {

        logger.info("Call before Handshake");

        // Set ip attribute to WebSocket session
        if (serverHttpRequest instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) serverHttpRequest;
            String ipAddress = servletRequest.getServletRequest().getHeader("X-FORWARDED-FOR");
            if (ipAddress == null) {
                ipAddress = servletRequest.getServletRequest().getRemoteAddr();
            }
            map.put("ip", ipAddress);
        }
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {
        logger.info("Call after Handshake");
    }
}
