package cn.gsq.crawler.annotation;

/**
 * @author qsefr
 * @program: sqgong
 * @description: TODO
 * @date 2021-07-24 19:33:45
 */
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class ImageToASCII {

    //三十二位颜色
    private final static char[] color = {' ', '`', '.', '^', ',', ':', '~', '"',
            '<', '!', 'c', 't', '+', '{', 'i', '7', '?', 'u', '3', '0', 'p', 'w',
            '4', 'A', '8', 'D', 'X', '%', '#', 'H', 'W', 'M'};


    /**
     * 图片转字符画文本
     */
    public static void createCharTxt(String path, String fileUrl) {
        try {
            BufferedImage image = ImageIO.read(new File(path));
            StringBuilder imageToAscii = imageToAscii(image);
            FileWriter fileWriter = new FileWriter(fileUrl);
            fileWriter.write(imageToAscii.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 图片转字符
     */
    public static StringBuilder imageToAscii(BufferedImage image) {
        StringBuilder sb = new StringBuilder();
        int width = image.getWidth();
        int height = image.getHeight();
        for (int y = 0; y < height; y++) {
            for (int x= 0; x < width; x++) {
                sb.append(rgbToChar(image,x,y)+ " ");
            }
            sb.append("\n");
        }
        return sb;
    }

    /**
     * 像素转字节
     */
    public static char rgbToChar(BufferedImage image,int x,int y){
        int rgb = image.getRGB(x, y);
        int R = (rgb & 0xff0000) >> 16;
        int G = (rgb & 0x00ff00) >> 8;
        int B = rgb & 0x0000ff;
        int gray = (R * 30 + G * 59 + B * 11 + 50) / 100;
        int index = 31 * gray / 255;
        return color[index];
    }

    /**
     * @Description: 生成字符码图片
     * @param  imgPath 图片路径
     * @param  fileUrl 输出图片路径
     * @param  more 放大倍数
     * @throws
     */
    public static void createCharImg(String imgPath, String fileUrl,int more){
        try {
            FileInputStream fileInputStream = new FileInputStream(imgPath);
            BufferedImage image = ImageIO.read(fileInputStream);
            //生成字符图片
            int w = image.getWidth();
            int h = image.getHeight();
            BufferedImage imageBuffer = new BufferedImage(w*more, h*more, 1);;
            Graphics g = imageBuffer.getGraphics();
            //设置背景色
            g.setColor(Color.white);// 画笔颜色
            g.fillRect(0, 0, w*more, h*more);// 填充图形背景
            // 设置字体
            g.setFont(new Font("宋体", Font.ITALIC, more)); //more*2：字体高度
            g.setColor(Color.black);// 画笔颜色
            // 绘制字符
            for (int y = 0; y < h; y++) {
                for (int x = 0; x< w; x++) {
                    g.drawString(rgbToChar(image,x,y)+ " ", x*more, (y+1)*more); //绘制每行字体位置，主要y轴改变
                }
            }
            g.dispose();
            ImageIO.write(imageBuffer, "jpg", new File(fileUrl)); //输出图片
            System.out.println("字符画图片生成");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @Description: 生成字符码图片
     * @param  imgPath 图片路径
     * @param  fileUrl 输出图片路径
     * @more 放大倍数
     */
    public static void createCharImg(String imgPath, String fileUrl){
        createCharImg(imgPath,fileUrl,1);
    }


    public static void main(String[] args) throws IOException {
        createCharImg("K:\\K盘视频\\KDMI-037\\poster.jpg","C:\\Users\\qsefr\\Desktop\\a.jpg");
        createCharTxt("K:\\K盘视频\\KDMI-037\\poster.jpg","C:\\Users\\qsefr\\Desktop\\a.txt");
    }
}
