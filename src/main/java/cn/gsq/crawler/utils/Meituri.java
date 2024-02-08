package cn.gsq.crawler.utils;

import cn.gsq.crawler.Main;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static cn.gsq.crawler.Main.TOTAL_PICTURE_COLLECTION;
import static cn.gsq.crawler.Main.URL_ROOT;

/**
 * @author gsq
 */
@Slf4j
@Component
public class Meituri {

    // cookie
    private static final String COOKIE = "__51vcke__Je64MI06Q1Neac4F=383c7175-6d8e-5a69-911f-6b66219e7d6b; __51vuft__Je64MI06Q1Neac4F=1694611781822; uid=313524; name=jisiwang12; leixing=0; PHPSESSID=74ej9uurbnreat6bab3ejrtuc2; __51uvsct__Je64MI06Q1Neac4F=27; __vtins__Je64MI06Q1Neac4F=%7B%22sid%22%3A%20%2244087def-4767-5111-afb9-9357c45ac962%22%2C%20%22vd%22%3A%207%2C%20%22stt%22%3A%201467998%2C%20%22dr%22%3A%20402852%2C%20%22expires%22%3A%201703905401798%2C%20%22ct%22%3A%201703903601798%7D";
    public static String URL_HEADER_FORMAT = "";
    // 图集的真实下载url eg:https://tjg.sqmuying.com/a/4/
    private static String pathName = "";
    // 请求map
    private static final Map<String, String> HEADER_MAP = convertTextToMap();

    @Resource
    DownImg downImg;


    private static Document getDocumentByUrl(String url) throws IOException {
        return Jsoup
                .connect(url)
//                    .proxy(ProxyUtil.buildProxy())
                .headers(HEADER_MAP)
                .cookies(Meituri.convertCookie(COOKIE))
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/117.0.0.0 Safari/537.36")
                .get();
    }


    public void run(ExecutorService pool, String url, String count, String localPathName, List<String> allFileName,
                           boolean isFirst, int pageCount) throws IOException {

        // 下载的图片路径前缀
        // 拿取首页内容
        Document mainPage = null;
        try {
            mainPage = getDocumentByUrl(url);
        } catch (IOException e) {
            log.error(String.valueOf(e));
            return;
        }
        // 该页面所有的标题信息
        Elements as = mainPage.select("p.biaoti>a");
        // 该页面所有的数量信息
        Elements shuliangList = mainPage.select("span.shuliang");

        // 获取页数
        boolean hasFile = false;
        if (!isFirst) {
            for (int i = 0; i < as.size(); i++) {
                String titleName = as.get(i).text();
                // 当前页有下载过的图片
                if (allFileName.stream().anyMatch(s -> s.startsWith(titleName))) {
                    count += i;
                    hasFile = true;
                    break;
                }
            }
        }

        //0-20
        //20-40
        int countReal = 20;
        if (isFirst) {
            if (20 < Integer.parseInt(count)) {
                count = String.valueOf(Integer.parseInt(count) - 20);
                url = url + "&page=" + pageCount;
                pageCount += 1;
                run(pool, url, count, localPathName, allFileName, isFirst, pageCount);
            } else {
                countReal = Integer.parseInt(count);
            }
        } else {
            //        if (20 < Integer.parseInt(PathUtil2.COUNT)) {
            if (!hasFile) {
//            PathUtil2.COUNT = Integer.parseInt(PathUtil2.COUNT) - 20 + "";
                url = url + "&page=" + pageCount;
                pageCount += 1;
                run(pool, url, count, localPathName, allFileName, isFirst, pageCount);
            } else {
                if ("00".equals(count)) {
                    log.info("图集【{}】无可下载图片",localPathName);
                }
                countReal = Integer.parseInt(count);
            }
        }

        for (int i = 0; i < countReal; i++) {
            String href;
            try {
                href = as.get(i).attr("href");
            } catch (Exception e) {
                break;
            }
            String html = shuliangList.get(i).html();
            String number = html.split("P")[0];
            // 获取图集编号
            String[] split = href.split("id=");
            // 一个图集的id
            String id = split[1];
            log.info("图集url为：{}", URL_ROOT + "/a/?id=" + id);
            Document contentDoc = getDocumentByUrl(URL_ROOT + "/a/?id=" + id);
            // 单个图片的url eg:https://tjgew6d4ew.82pic.com/a/5/66977/e283e/1.jpg
            String attr = contentDoc.select(".lazy").get(0).attr("data-src");
            pathName = attr.replace("/1.jpg", "");
//            // 通过图集的id，拿取图集的第一张图片的url，防止图片url变化
//            pathName = getImageUrlById(id, mainPage);
            // 获取标签
            Elements tag = mainPage.select("#" + id);
            Elements tagP = tag.select("p");
            Elements tagA = tagP.get(1).select("a");
            String replaceTag = tagA.text().replace(" ", "&");
            // 标题
            String text = as.get(i).text();
            // 替换h1标题中的/
            String title = text.replaceAll("/", "-");
            String titleTag = title + "&" + replaceTag;
            // 等待所有的 CompletableFuture 完成
            CompletableFuture<Void> allOf = CompletableFuture.allOf(IntStream.rangeClosed(1, Integer.parseInt(number))
                    .mapToObj(imgIndex -> CompletableFuture.runAsync(
                            () -> downImg.downImgFromUrl(convertUrl(pathName, imgIndex), convertFilePath(titleTag, localPathName, imgIndex))
                            , pool))
                    .toArray(CompletableFuture[]::new));
            // 继续执行下一步
            allOf.join();
//            pool.execute(new DownImg(pathName + "/", titleTag, Integer.parseInt(number), localPathName));
            TOTAL_PICTURE_COLLECTION.getAndIncrement();
        }
    }


    /**
     * 转换为要进行生成的文件名
     *
     * @param titleTag
     * @param localPathName
     * @param imgIndex
     * @return
     */
    private String convertFilePath(String titleTag, String localPathName, int imgIndex) {
        return Main.PATH_HEADER + localPathName + File.separator + titleTag + File.separator + imgIndex + ".jpg";
    }

    /**
     * 转换为要进行下载的图片url
     * @param pathName
     * @param imgIndex
     * @return
     */
    private String convertUrl(String pathName, int imgIndex) {
        return pathName + "/" + imgIndex + ".jpg";
    }


    /**
     * 通过图集的id，拿取图集的第一张图片的url，防止图片url变化
     *
     * @param id
     * @param mainPage
     * @return
     */
    private static String getImageUrlById(String id, Document mainPage) {
        return mainPage.select("#" + id).get(0).select("a>img").get(0).attr("src").split(id)[0];

    }


    public static Map<String, String> convertCookie(String cookie) {
        HashMap<String, String> cookiesMap = new HashMap<>();
        String[] items = cookie.trim().split(";");
        for (String item : items) {
            cookiesMap.put(item.split("=")[0], item.split("=")[1]);
        }
        return cookiesMap;
    }


    /**
     * 构建headermap
     *
     * @return
     */
    public static Map<String, String> buildHeaderMap() {
        Map<String, String> map = new HashMap<>();
        map.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7");
        map.put("Accept-Encoding", "gzip, deflate, br");
        map.put("Authority", "www.sqmuying.com");
        map.put("Accept-Language", "zh-CN,zh;q=0.9");
        map.put("Cache-Control", "max-age=0");
        map.put("Connection", "keep-alive");
        map.put("Referer", "https://www.sqmuying.com");
        map.put("sec-ch-ua", "\"Google Chrome\";v=\"117\", \"Not;A=Brand\";v=\"8\", \"Chromium\";v=\"117\"");
        map.put("sec-ch-ua-mobile", "?0");
        map.put("sec-ch-ua-platform", "\"Windows\"");
        map.put("Sec-Fetch-Dest", "document");
        map.put("Sec-Fetch-Mode", "navigate");
        map.put("Sec-Fetch-Site", "cross-site");
        map.put("Sec-Fetch-User", "?1");
        map.put("Upgrade-Insecure-Requests", "1");
        map.put("Pragma", "no-cache");
        return map;
    }


    private static Map<String, String> convertTextToMap() {

        String text = "Authority: www.sqmuying.com\n" +
                ":Method: GET\n" +
                ":Path: /t/?id=68683\n" +
                ":Scheme: https\n" +
                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7\n" +
                "Accept-Encoding: gzip, deflate, br\n" +
                "Accept-Language: zh-CN,zh;q=0.9\n" +
                "Cache-Control: max-age=0\n" +
                "Cookie: __51vcke__Je64MI06Q1Neac4F=383c7175-6d8e-5a69-911f-6b66219e7d6b; __51vuft__Je64MI06Q1Neac4F=1694611781822; uid=313524; name=jisiwang12; leixing=0; PHPSESSID=74ej9uurbnreat6bab3ejrtuc2; __51uvsct__Je64MI06Q1Neac4F=27; __vtins__Je64MI06Q1Neac4F=%7B%22sid%22%3A%20%2244087def-4767-5111-afb9-9357c45ac962%22%2C%20%22vd%22%3A%207%2C%20%22stt%22%3A%201467998%2C%20%22dr%22%3A%20402852%2C%20%22expires%22%3A%201703905401798%2C%20%22ct%22%3A%201703903601798%7D\n" +
                "Sec-Ch-Ua: \"Not_A Brand\";v=\"8\", \"Chromium\";v=\"120\", \"Google Chrome\";v=\"120\"\n" +
                "Sec-Ch-Ua-Mobile: ?0\n" +
                "Sec-Ch-Ua-Platform: \"Windows\"\n" +
                "Sec-Fetch-Dest: document\n" +
                "Sec-Fetch-Mode: navigate\n" +
                "Sec-Fetch-Site: none\n" +
                "Sec-Fetch-User: ?1\n" +
                "Upgrade-Insecure-Requests: 1\n" +
                "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36";

        Map<String, String> map = new HashMap<>();

        String[] lines = text.split("\n");
        for (String line : lines) {
            String[] keyValue = line.split(":");
            if (keyValue.length == 2) {
                String key = keyValue[0].trim();
                String value = keyValue[1].trim();
                map.put(key, value);
            }
        }

        return map;
    }

}



