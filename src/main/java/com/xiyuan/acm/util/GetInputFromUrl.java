package com.xiyuan.acm.util;

import com.xiyuan.util.Md5Util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by xiyuan_fengyu on 2016/9/2.
 */
public class GetInputFromUrl {

    public static String get(String urlStr) {
        String md5 = Md5Util.get(urlStr, "utf-8");
        String content = getCacheFromFile(md5);
        if (content != null) {
            return content;
        }
        else {
            try {
                URL url = new URL(urlStr);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.setDoOutput(false);
                connection.setUseCaches(false);
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                connection.setRequestProperty("Connection", "Keep-Alive");
                connection.setRequestProperty("Charset", "UTF-8");

                InputStream in = connection.getInputStream();
                return readFromInputStream(in, getResponseCharset(connection.getContentType()));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    private static String getResponseCharset(String ctype) {
        String charset = "UTF-8";
        if(ctype != null && !ctype.isEmpty()) {
            String[] params = ctype.split(";");
            String[] arr = params;
            int len = params.length;

            for(int i = 0; i < len; ++i) {
                String param = arr[i];
                param = param.trim();
                if(param.startsWith("charset")) {
                    String[] pair = param.split("=", 2);
                    if(pair.length == 2 && pair[1] != null && !pair[1].isEmpty()) {
                        charset = pair[1].trim();
                    }
                    break;
                }
            }
        }

        return charset;
    }

    private static String getCacheFromFile(String name) {
        File cacheDic = new File("cache");
        if (!cacheDic.exists()) {
            cacheDic.mkdirs();
        }
        File cacheFile = new File("cache/" + name);
        if (cacheFile.exists()) {
            try {
                return readFromInputStream(new FileInputStream(cacheFile), "utf-8");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private static String readFromInputStream(InputStream in, String charset) {
        StringBuilder strBld = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
            String line = null;
            while ((line = reader.readLine()) != null) {
                strBld.append(line).append('\n');
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return strBld.toString();
    }

}
