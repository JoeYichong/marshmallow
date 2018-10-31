package yich.base.util;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlInfo {
    final private static String URL_PTN = "^(?:(?i:(http(?:s?)):\\/\\/)?((?:(?:(?![\\W])(?i:www\\.)?[a-zA-Z0-9\\.\\-\\_]+(?:\\.[a-zA-Z]{2,3})+)|(?:\\b(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\b)))(?::(\\d{2,5}))?+((?:\\/[a-zA-Z0-9\\.\\-\\_=%]+)+|\\/)?(?:\\?((?:\\w+=\\w*&?)+))?)$";

    private String url;
    private Pattern url_ptn;
    private Matcher matcher;
    private boolean isValid;

    private Map<String, String> queryStrMap;

    private UrlInfo(){}

    public static UrlInfo of(String url) {
        UrlInfo urlInfo = new UrlInfo();
        urlInfo.url = url;
        urlInfo.url_ptn = Pattern.compile(URL_PTN);
        urlInfo.matcher = urlInfo.url_ptn.matcher(url);
        urlInfo.isValid = urlInfo.matcher.matches();
        return urlInfo;
    }

    public boolean isValid() {
        return this.isValid;
    }

    public String url() {
        return url;
    }

    public String url(String alt) {
        if (isValid) {
            return url();
        } else {
            return alt;
        }
    }

    public String protocol() {
        return isValid() ? matcher.group(1) : null;
    }

    public String protocol(String alt) {
        if (isValid) {
            return protocol();
        } else {
            return alt;
        }
    }

    public String domainName() {
        return isValid() ? matcher.group(2) : null;
    }

    public String domainName(String alt) {
        if (isValid) {
            return domainName();
        } else {
            return alt;
        }
    }

    public int port() {
        if (!isValid()) {
            return -1;
        }
        String port = matcher.group(3);
        if (port != null) {
            try {
                return Integer.parseInt(port);
            } catch (NumberFormatException e) {
                return -1;
            }
        }
        if ("http".equals(matcher.group(1).toLowerCase())) {
            return 80;
        }
        if ("https".equals(matcher.group(1).toLowerCase())) {
            return 443;
        }

        return -1;
    }

    public int port(int alt) {
        if (isValid) {
            return port();
        } else {
            return alt;
        }
    }

    public String path() {
        return isValid() ? matcher.group(4) : null;
    }

    public String path(String alt) {
        if (isValid) {
            return path();
        } else {
            return alt;
        }

    }

    public String getQueryStr() {
        return isValid() ? matcher.group(5) : null;
    }

    public Map<String, String> getQueryStrMap() {
        if (queryStrMap == null) {
            queryStrMap = new HashMap<>();
        }
        String query = isValid() ? matcher.group(5) : null;
        if (query != null) {
            String[] pairs = query.split("&");
            for(String pair : pairs) {
                String[] kv = pair.split("=");
                if (kv.length == 2) {
                    queryStrMap.put(kv[0], kv[1]);
                }
            }
        }
        return queryStrMap;
    }

    public String encodeUrl() {
        return null;
    }

    public String decodeUrl() {
        return null;
    }


//    public static void main(String[] args) {
//        UrlInfo urlInfo = UrlInfo.of("http://bbs.ph66.com/thread.php?fid=202&page=1");
//        System.out.println(urlInfo.url());
//        System.out.println(urlInfo.protocol());
//        System.out.println(urlInfo.domainName());
//        System.out.println(urlInfo.port());
//        System.out.println(urlInfo.path());
//        System.out.println(urlInfo.getQueryStr());
//        Map<String, String> map = urlInfo.getQueryStrMap();
//        System.out.println(map.toString());
//    }

}
