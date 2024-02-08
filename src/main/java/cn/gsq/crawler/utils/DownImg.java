package cn.gsq.crawler.utils;


import cn.gsq.crawler.Main;
import com.alibaba.fastjson2.JSON;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author qsefr
 */
@Slf4j
@Component
public class DownImg {
/*    private String url;
    private String path;
    private Integer number;
    private String filePath;
    private static final String JPG = ".jpg";*/

/*
    public DownImg(String url, String path, Integer number, String filePath) {
        this.url = url;
        this.path = path;
        this.number = number;
        this.filePath = filePath;
    }
*/


/*    public void run() {

        for (int i = 1; i <= number; i++) {
            String downUrl = url + i + JPG;
            try {
                System.out.println(downUrl);
                FileUtils.copyInputStreamToFile(buildInputStream(new URL(downUrl)), new File(Main.PATH_HEADER + filePath + File.separator + path + File.separator + i + JPG));
//                FileUtils.copyURLToFile(new URL(url + i + ".jpg"), new File(filePathStr + path + i + ".jpg"), 20000, 20000);
                log.info("下载线程为：{}", Thread.currentThread().getName());
                log.info("该图片下载完成：{}", downUrl);
                Main.TOTAL_PICTURE.getAndIncrement();
            } catch (IOException e) {
                e.printStackTrace();
                Main.TOTAL_PICTURE_FAIL.getAndIncrement();
                Main.DOWN_FAILED_PICTURE_ARRAY.add(downUrl);
                // 使用Logger打印日志
                log.error("该图片下载失败:{}", downUrl);
            }
        }
        log.info("该图集下载完毕--{}", url);

    }*/

    public InputStream buildInputStream(URL url) throws IOException {
        URLConnection connection = url.openConnection();
        connection.setRequestProperty("Referer", "https://www.jimeilu.com/");
        connection.setRequestProperty("Host", "tjgew6d4ew.82pic.com");
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36");
        connection.setRequestProperty("Pragma", "no-cache");
        connection.setConnectTimeout(100000);
        connection.setReadTimeout(100000);
        return connection.getInputStream();
    }


    /**
     * 下载url到目标目录
     *
     * @param url
     * @param filePath
     * @return
     */
    @SneakyThrows
    @Retryable(maxAttempts = 20, include = Exception.class)
    public void downImgFromUrl(String url, String filePath) {
        try {
            FileUtils.copyInputStreamToFile(buildInputStream(new URL(url)), new File(filePath));
            log.info("下载线程为：{}", Thread.currentThread().getName());
            log.info("该图片下载完成：{}", url);
            Main.TOTAL_PICTURE.getAndIncrement();
        } catch (Exception e) {
            throw new Exception(url, e);
        }
    }

    /**
     * 捕获异常进行全部失败后的处理
     * @param e
     * @param url
     */
    @Recover
    public void handlerException(Exception e, String url) {
        log.error(JSON.toJSONString(e));
        Main.TOTAL_PICTURE_FAIL.getAndIncrement();
        Main.DOWN_FAILED_PICTURE_ARRAY.add(url);
        // 使用Logger打印日志
        log.error("该图片下载失败:{}", url);
    }
}

