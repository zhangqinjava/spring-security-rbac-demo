package com.example.securitydemo.util;

import com.alibaba.fastjson.JSON;
import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import org.apache.commons.io.IOUtils;


public class CommonUtil {
    private static final String UNKNOWN = "unknown";

    /**
     * 解析入参的请求参数
     * @param request
     * @return
     * @throws IOException
     */
    public static String parseRequest(HttpServletRequest request) throws IOException {
        if (request == null) return null;
        String contentType = request.getContentType();
        String req = null;

        if (!StringUtils.hasText(contentType)) {
            // GET/DELETE/无ContentType
            req = JSON.toJSONString(request.getParameterMap());
        } else if (contentType.startsWith(MediaType.APPLICATION_JSON_VALUE)) {
            ContentCachingRequestWrapper wrapper = (ContentCachingRequestWrapper) request;
            req = new String(wrapper.getContentAsByteArray(), wrapper.getCharacterEncoding());
        } else if (contentType.startsWith(MediaType.APPLICATION_FORM_URLENCODED_VALUE)) {
            req = JSON.toJSONString(request.getParameterMap());
        } else if (contentType.startsWith(MediaType.MULTIPART_FORM_DATA_VALUE)) {
            Map<String, String[]> params = request.getParameterMap();
            req = "FormData=" + JSON.toJSONString(params) + ", 文件上传信息略";
        } else {
            req = IOUtils.toString(request.getInputStream(), request.getCharacterEncoding());
        }

        if (StringUtils.hasText(req) && req.length() > 3000) {
            req = req.substring(0, 3000); // 截断，避免日志过大
        }
        return req;
    }


    /**
     * 获取客户端 IP
     */
    public static String getIp(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        String ip = null;
        // 常见的代理头部
        String[] headers = {
                "x-forwarded-for",
                "Proxy-Client-IP",
                "WL-Proxy-Client-IP",
                "HTTP_CLIENT_IP",
                "HTTP_X_FORWARDED_FOR"
        };

        for (String header : headers) {
            ip = request.getHeader(header);
            if (ip != null && ip.length() != 0 && !UNKNOWN.equalsIgnoreCase(ip)) {
                break;
            }
        }

        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        // 多个 IP 时，取第一个非 unknown 的
        if (ip != null && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }

        // 处理本地回环地址
        if ("127.0.0.1".equals(ip) || "0:0:0:0:0:0:0:1".equals(ip)) {
            ip = "localhost";
        }

        return ip;
    }
}
