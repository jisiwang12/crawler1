package cn.gsq.crawler;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;


public class CrawlerTest {
    public static void main(String[] args) throws Exception  {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("https://www.seedmm.in/search/%E7%BE%8E&type=&parent=ce");
        CloseableHttpResponse response = null;
        response = httpClient.execute(httpGet);
        if (response.getStatusLine().getStatusCode() == 200) {
            HttpEntity entity = response.getEntity();
            String content = null;
            content = EntityUtils.toString(entity, "utf-8");
            response.close();
            httpClient.close();
            System.out.println(content);
        }

        }


}
