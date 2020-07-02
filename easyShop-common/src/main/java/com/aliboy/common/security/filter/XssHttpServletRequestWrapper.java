package com.aliboy.common.security.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.List;
import java.util.regex.Pattern;

/**
 *
 * @author xiquee.com. <br>
 * @date 2018-11-09 10:16:00
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

    final static Logger logger = LoggerFactory.getLogger(MyFormAuthenticationFilter.class);

    public static final String APPLICATION_XML = "application/xml";
    private boolean isUpData = false;//判断是否是上传 上传忽略
    private boolean supportRichText = false;

    public XssHttpServletRequestWrapper(HttpServletRequest servletRequest, List<String> whiteListUrls) {
        super(servletRequest);
        String contentType = servletRequest.getContentType();
        if (null != contentType) {
            isUpData = contentType.startsWith("multipart");
        }

        supportRichText = checkWhiteList(whiteListUrls);
    }

    private boolean checkWhiteList(List<String> whiteListUrls) {
        if (APPLICATION_XML.equalsIgnoreCase(this.getContentType())) {
            return true;
        }
        boolean isRichText = false;
        String path = this.getRequestURI();
        if (whiteListUrls != null && whiteListUrls.size() > 0 && path != null) {
            for (String url : whiteListUrls) {
                if (path.toLowerCase().startsWith(url)) {
                    isRichText = true;
                }
            }
        }
        return isRichText;
    }

    @Override
    public String[] getParameterValues(String parameter) {
        String[] values = super.getParameterValues(parameter);
        if (values == null) {
            return null;
        }
        int count = values.length;
        String[] encodedValues = new String[count];
        for (int i = 0; i < count; i++) {
            encodedValues[i] = cleanXSS(values[i], supportRichText);
        }
        return encodedValues;
    }

    @Override
    public String getParameter(String parameter) {
        String value = super.getParameter(parameter);
        if (value == null) {
            return null;
        }
        return cleanXSS(value, supportRichText);
    }

    /**
     * 获取request的属性时，做xss过滤
     */
    @Override
    public Object getAttribute(String name) {
        Object value = super.getAttribute(name);
        if (null != value && value instanceof String) {
            value = cleanXSS((String) value, supportRichText);
        }
        return value;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        //忽略银行回调请求
        if (isUpData || supportRichText) {
            return super.getInputStream();
        }
        final ByteArrayInputStream bais = new ByteArrayInputStream(inputHandlers(super.getInputStream()).getBytes());
        return new ServletInputStream() {
            @Override
            public int read() throws IOException {
                return bais.read();
            }

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {
            }
        };
    }

    String inputHandlers(ServletInputStream servletInputStream) {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(servletInputStream, Charset.forName("UTF-8")));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("error occurred when filter xss string", e);
        } finally {
            if (servletInputStream != null) {
                try {
                    servletInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return cleanXSS(sb.toString(), supportRichText);
    }

    private static String cleanXSS(String value, boolean isRichText) {
        if (value != null && value.length() > 0) {

            if (isRichText) { //ignore
//                value = scriptPattern.matcher(value).replaceAll("");
//                value = expressPattern.matcher(value).replaceAll("");
//                value = evalPattern.matcher(value).replaceAll("");

            } else {
                value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
                value = value.replaceAll("%3C", "&lt;").replaceAll("%3E", "&gt;");
                value = PatternEmoji.matcher(value).replaceAll("");
            }
        }
        return value;
    }

    /**
     * Mysql doesn't support emoji by default, filter it here.
     */
    private static Pattern PatternEmoji = Pattern.compile(
            "[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
            Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);

//    private static Pattern scriptPattern = Pattern.compile("<script(.*?)>(.*?)</script>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
//    private static Pattern evalPattern = Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
//    private static Pattern expressPattern = Pattern.compile("expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
//    private static Pattern imagePattern = Pattern.compile("<img \\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);

}
