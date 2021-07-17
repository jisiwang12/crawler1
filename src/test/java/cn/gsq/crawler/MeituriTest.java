package cn.gsq.crawler;

import junit.framework.TestCase;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.stream.Collectors;


public class MeituriTest extends TestCase {

    public void testMain() {
        ArrayList<String> strings = new ArrayList<String>();
        strings.add("1");
        strings.add("2");
        strings.add("1");
        String collect = strings.stream().distinct().collect(Collectors.joining(","));
        System.out.println(collect);
    }

    public static void main(String[] args) throws IOException {
        String url = "https://d5.wnacg.download/down/1212/69f86dae82be58cef56f3c0375a5b3b3.zip?n=[Nagisa%E9%AD%94%E7%89%A9%E5%96%B5]%C2%A0%C2%A0%E3%81%8A%E5%85%84%E3%81%95%E3%82%93%E2%80%A6%E7%A7%81%E3%82%92%E8%A6%8B%E3%81%AA%E3%81%84%E3%81%A7...";
        FileUtils.copyURLToFile(new URL(url),new File("./1"));
    }
    public void fileUtilsTest() throws IOException {

    }
}