package com.vnpts.tracebility_v2.config;


import com.google.gson.*;
import com.vnpts.tracebility_v2.response.ResponseCode;
import com.vnpts.tracebility_v2.response.ResponseSession;
import com.vnpts.tracebility_v2.util.StringUtils;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import org.apache.commons.io.IOUtils;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;


@Component
public class AuthenticationFilter implements Filter {

    @Autowired
    private CheckService checkService;
    @Autowired
    private GsonResponse gson;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
                filterConfig.getServletContext());
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        ResponseCode responseCode = new ResponseCode();
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        ServletOutputStream out = httpResponse.getOutputStream();
        if (request instanceof HttpServletRequest && response instanceof HttpServletResponse) {
            httpResponse.addHeader("Access-Control-Allow-Origin", "*");
            httpResponse.addHeader("Access-Control-Allow-Methods", "GET,POST");
            httpResponse.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
            if (httpRequest.getMethod().equals("OPTIONS")) {
                httpResponse.setStatus(HttpServletResponse.SC_OK);
                filterChain.doFilter(request, response);
                return;
            }
            if (httpRequest.getMethod().equals("GET")) {
                filterChain.doFilter(request, response);
                return;
            }

            boolean isMultipart = ServletFileUpload.isMultipartContent(httpRequest);
            if (isMultipart) {
                try {
                    System.out.println("isMultipart - upload file");
                    boolean checkDone = checkUploadKey(httpRequest, out);
                    if (!checkDone) checkDone = checkSession(httpRequest, out);
                    if (!checkDone) checkDone = checkFunction(httpRequest, out);
                    if (checkDone) return;
                    filterChain.doFilter(request, response);
                } catch (Exception e) {
                    e.printStackTrace();
                    responseCode.setFailMaxDFileSize();
                    out.write(gson.toJson(responseCode).getBytes("UTF-8"));
                    out.flush();
                    out.close();
                }
                return;
            }

            MultipleReadHttpRequest wrappedRequest = new MultipleReadHttpRequest(httpRequest);
            httpResponse.setContentType("application/json");
            String requestBody = IOUtils.toString(wrappedRequest.getInputStream());
            try {
                JsonObject ob = new JsonParser().parse(requestBody).getAsJsonObject();
                String partner = ob.get("partner") == null ? "" : ob.get("partner").getAsString();
                //CHECK PARTNER HERE
                if (!checkService.checkPartner(partner)) {
                    responseCode.setWrongPartner();
                    sendReturn(responseCode, out);
                    return;
                }
                //CHECK SIGNATURE HERE
                boolean checkDone = checkSignature(ob, partner, out);
                //CHECK SESSION
                if (!checkDone) checkDone = checkSession(httpRequest, out);
                if (!checkDone) checkDone = checkFunction(httpRequest, out);
                // RETURN IF ANY CHECK IS DONE
                if (checkDone) return;
                filterChain.doFilter(wrappedRequest, response);
            } catch (NoSuchAlgorithmException e) {
                responseCode.setFailSystem();
                sendReturn(responseCode, out);
                return;
            } catch (JsonSyntaxException | IllegalStateException e) {
                e.printStackTrace();
                responseCode.setFailJson();
                sendReturn(responseCode, out);
                return;
            }
        }
    }

    @Override
    public void destroy() {
    }

    private boolean checkSignature(JsonObject ob, String partner, ServletOutputStream out) throws IOException, NoSuchAlgorithmException {
        //CHECK SIGNATURE HERE
        String signature = ob.get("signature") == null ? "" : ob.get("signature").getAsString();
        final StringBuffer check = new StringBuffer("");
        ob.keySet().forEach(key -> {
            if (!key.toLowerCase().equals("signature")) {
                JsonElement element = ob.get(key);
                String val = "";
                if (element.isJsonArray()) {
                    JsonArray arr = (JsonArray) element;
                    for (JsonElement a : arr) {
                        val += a.getAsString() + ",";
                    }
                    val = val.substring(0, val.length() - 1);
                } else if (element.isJsonNull()) val = "";
                else val = element.getAsString();
                check.append(val);
            }
        });
        if (!StringUtils.checkSignature(check.toString() + checkService.getKeyPartner(partner), signature)) {
            ResponseCode responseCode = new ResponseCode();
            responseCode.invalidSignature();
            sendReturn(responseCode, out);
            return true;
        }
        return false;
    }

    private boolean checkSession(HttpServletRequest httpRequest, ServletOutputStream out) throws IOException {
        ResponseSession res = new ResponseSession();
        String servletPath = httpRequest.getServletPath();
        if (servletPath.matches(checkService.getURL_SKIP_CHECK_SESSION())) return false;
        //CHECK SESSION HERE
        String header = httpRequest.getHeader(JWTConfig.HEADER_STRING);
        if (header == null) {
            res.invalidToken();
            sendReturn(res, out);
            return true;
        }
        String token = header.substring(JWTConfig.TOKEN_PREFIX.length() + 1);
        try {
            res = gson.castJson(JWTConfig.decodeToken(token), ResponseSession.class);
            res.setSuccess();
        } catch (ExpiredJwtException e) {
            res.expiredToken();
        } catch (MalformedJwtException | SignatureException | IllegalArgumentException e) {
            res.invalidToken();
        }
        if (res.isFail()) {
            sendReturn(res, out);
            return true;
        }
        httpRequest.getSession().setAttribute("resSession", res);
        return false;
    }

    public boolean checkFunction(HttpServletRequest httpRequest, ServletOutputStream out) throws IOException {
        if (!checkService.isCheckRole()) return false;
        String servletPath = httpRequest.getServletPath();
        if (servletPath.matches(checkService.getURL_SKIP_CHECK_SESSION())) return false;
        if (servletPath.matches(checkService.getURL_SKIP_CHECK_FUNCTION())) return false;
        ResponseSession responseSession = (ResponseSession) httpRequest.getSession().getAttribute("resSession");
        if (!responseSession.hasRole(servletPath)) {
            ResponseCode res = new ResponseCode();
            res.setForbidden();
            sendReturn(res, out);
            return true;
        }
        return false;
    }

    private void sendReturn(ResponseCode res, ServletOutputStream out) throws IOException {
        out.write(gson.toJson(res).getBytes("UTF-8"));
        out.flush();
        out.close();
    }

    private boolean checkUploadKey(HttpServletRequest httpRequest, ServletOutputStream out) throws IOException {
        String header = httpRequest.getHeader("UploadKey");
        if (header == null) {
            return false;
        }
        if (!checkService.checkUploadKey(header)) {
            ResponseCode res = new ResponseCode();
            res.invalidToken();
            sendReturn(res, out);
            return true;
        }
        httpRequest.getSession().setAttribute("keyUpload", header);
        return false;
    }
}
