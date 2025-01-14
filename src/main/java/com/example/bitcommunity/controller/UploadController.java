package com.example.bitcommunity.controller;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import com.example.bitcommunity.json.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/upload")
public class UploadController {
    /*从配置文件中获取endpoint值*/
    @Value("${config.oss.endpoint}")
    private String endpoint;

    /*从配置文件中获取bucket值*/
    @Value("${config.oss.bucket}")
    private String bucket;

    /*从配置文件中获取accessKey值*/
    @Value("${config.oss.access-key}")
    private String accessKey;

    /*从配置文件中获取accessId值*/
    @Value("${config.oss.access-id}")
    private String accessId;

    @CrossOrigin
    @GetMapping("/policy")
    public Result getPolicy() {

        String host = "http://" + bucket + "." + endpoint; // host的格式为 bucketname.endpoint
        // callbackUrl为 上传回调服务器的URL，请将下面的IP和Port配置为您自己的真实信息。
        String callbackUrl = "http://88.88.88.88.:8888";
        String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String dir = format + "/"; // 用户上传文件时指定的前缀。

        OSSClient client = new OSSClient(endpoint, accessId, accessKey);
        Map<String, String> respMap = new LinkedHashMap<String, String>();
        try {
            long expireTime = 30;
            long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
            Date expiration = new Date(expireEndTime);
            PolicyConditions policyConds = new PolicyConditions();
            policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
            policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);

            String postPolicy = client.generatePostPolicy(expiration, policyConds);
            byte[] binaryData = postPolicy.getBytes("utf-8");
            String encodedPolicy = BinaryUtil.toBase64String(binaryData);
            String postSignature = client.calculatePostSignature(postPolicy);

            respMap.put("accessid", accessId);
            respMap.put("policy", encodedPolicy);
            respMap.put("signature", postSignature);
            respMap.put("dir", dir);
            respMap.put("host", host);
            respMap.put("expire", String.valueOf(expireEndTime / 1000));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            client.shutdown();
        }
        return Result.success(respMap);
    }

//    /**
//     * 获取public key
//     *
//     * @param url
//     * @return
//     */
//    @SuppressWarnings({ "finally" })
//    public String executeGet(String url) {
//        BufferedReader in = null;
//
//        String content = null;
//        try {
//            // 定义HttpClient
//            @SuppressWarnings("resource")
//            DefaultHttpClient client = new DefaultHttpClient();
//            // 实例化HTTP方法
//            HttpGet request = new HttpGet();
//            request.setURI(new URI(url));
//            HttpResponse response = client.execute(request);
//
//            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
//            StringBuffer sb = new StringBuffer("");
//            String line = "";
//            String NL = System.getProperty("line.separator");
//            while ((line = in.readLine()) != null) {
//                sb.append(line + NL);
//            }
//            in.close();
//            content = sb.toString();
//        } catch (Exception e) {
//        } finally {
//            if (in != null) {
//                try {
//                    in.close();// 最后要关闭BufferedReader
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//            return content;
//        }
//    }
//
//    /**
//     * 获取Post消息体
//     *
//     * @param is
//     * @param contentLen
//     * @return
//     */
//    public String GetPostBody(InputStream is, int contentLen) {
//        if (contentLen > 0) {
//            int readLen = 0;
//            int readLengthThisTime = 0;
//            byte[] message = new byte[contentLen];
//            try {
//                while (readLen != contentLen) {
//                    readLengthThisTime = is.read(message, readLen, contentLen - readLen);
//                    if (readLengthThisTime == -1) {// Should not happen.
//                        break;
//                    }
//                    readLen += readLengthThisTime;
//                }
//                return new String(message);
//            } catch (IOException e) {
//            }
//        }
//        return "";
//    }
//
//    /**
//     * 验证上传回调的Request
//     *
//     * @param request
//     * @param ossCallbackBody
//     * @return
//     * @throws NumberFormatException
//     * @throws IOException
//     */
//    protected boolean VerifyOSSCallbackRequest(HttpServletRequest request, String ossCallbackBody)
//            throws NumberFormatException, IOException {
//        boolean ret = false;
//        String autorizationInput = new String(request.getHeader("Authorization"));
//        String pubKeyInput = request.getHeader("x-oss-pub-key-url");
//        byte[] authorization = BinaryUtil.fromBase64String(autorizationInput);
//        byte[] pubKey = BinaryUtil.fromBase64String(pubKeyInput);
//        String pubKeyAddr = new String(pubKey);
//        if (!pubKeyAddr.startsWith("http://gosspublic.alicdn.com/")
//                && !pubKeyAddr.startsWith("https://gosspublic.alicdn.com/")) {
//            System.out.println("pub key addr must be oss addrss");
//            return false;
//        }
//        String retString = executeGet(pubKeyAddr);
//        retString = retString.replace("-----BEGIN PUBLIC KEY-----", "");
//        retString = retString.replace("-----END PUBLIC KEY-----", "");
//        String queryString = request.getQueryString();
//        String uri = request.getRequestURI();
//        String decodeUri = java.net.URLDecoder.decode(uri, "UTF-8");
//        String authStr = decodeUri;
//        if (queryString != null && !queryString.equals("")) {
//            authStr += "?" + queryString;
//        }
//        authStr += "\n" + ossCallbackBody;
//        ret = doCheck(authStr, authorization, retString);
//        return ret;
//    }
//
//    /**
//     * Post请求
//     */
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        String ossCallbackBody = GetPostBody(request.getInputStream(),
//                Integer.parseInt(request.getHeader("content-length")));
//        boolean ret = VerifyOSSCallbackRequest(request, ossCallbackBody);
//        System.out.println("verify result : " + ret);
//        // System.out.println("OSS Callback Body:" + ossCallbackBody);
//        if (ret) {
//            response(request, response, "{\"Status\":\"OK\"}", HttpServletResponse.SC_OK);
//        } else {
//            response(request, response, "{\"Status\":\"verdify not ok\"}", HttpServletResponse.SC_BAD_REQUEST);
//        }
//    }
//
//    /**
//     * 验证RSA
//     *
//     * @param content
//     * @param sign
//     * @param publicKey
//     * @return
//     */
//    public static boolean doCheck(String content, byte[] sign, String publicKey) {
//        try {
//            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//            byte[] encodedKey = BinaryUtil.fromBase64String(publicKey);
//            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
//            java.security.Signature signature = java.security.Signature.getInstance("MD5withRSA");
//            signature.initVerify(pubKey);
//            signature.update(content.getBytes());
//            boolean bverify = signature.verify(sign);
//            return bverify;
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return false;
//    }
//
//    /**
//     * 服务器响应结果
//     *
//     * @param request
//     * @param response
//     * @param results
//     * @param status
//     * @throws IOException
//     */
//    private void response(HttpServletRequest request, HttpServletResponse response, String results, int status)
//            throws IOException {
//        String callbackFunName = request.getParameter("callback");
//        response.addHeader("Content-Length", String.valueOf(results.length()));
//        if (callbackFunName == null || callbackFunName.equalsIgnoreCase(""))
//            response.getWriter().println(results);
//        else
//            response.getWriter().println(callbackFunName + "( " + results + " )");
//        response.setStatus(status);
//        response.flushBuffer();
//    }
//
//    /**
//     * 服务器响应结果
//     */
//    private void response(HttpServletRequest request, HttpServletResponse response, String results) throws IOException {
//        String callbackFunName = request.getParameter("callback");
//        if (callbackFunName == null || callbackFunName.equalsIgnoreCase(""))
//            response.getWriter().println(results);
//        else
//            response.getWriter().println(callbackFunName + "( " + results + " )");
//        response.setStatus(HttpServletResponse.SC_OK);
//        response.flushBuffer();
//    }
}
