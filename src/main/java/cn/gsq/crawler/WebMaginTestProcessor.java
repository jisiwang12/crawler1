package cn.gsq.crawler;

import org.jsoup.nodes.Document;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.processor.example.GithubRepoPageProcessor;
import us.codecraft.webmagic.scheduler.RedisScheduler;
import us.codecraft.webmagic.selector.Html;

import java.util.List;

public class WebMaginTestProcessor implements PageProcessor {

    private Site site = Site.me();
    //.setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.150 Safari/537.36")
    //            .setTimeOut(5000).setRetryTimes(3);

    public void process(Page page) {
        Html html = page.getHtml();
        List<String> all = html.css("div.repeatList ul li a").links().all();
        page.addTargetRequests(all);

    }

    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new WebMaginTestProcessor()).addUrl("http://finance.eastmoney.com/a/ccjdd.html").run();
    }
}
