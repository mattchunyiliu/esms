package kg.kundoluk.school.utils;

import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.DeviceUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class DeviceProvider {

    public static Device getCurrentDevice(HttpServletRequest request) {
        return DeviceUtils.getCurrentDevice(request);
    }

    public static Date now() {
        return new Date();
    }
}
