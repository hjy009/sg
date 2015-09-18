package fuzhu.gwee.passport;


import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.*;

import org.yccheok.numrecognition.FeatureParameter;
import org.yccheok.numrecognition.NumberImageFeatureFactory;
import org.yccheok.numrecognition.NumberImageLRTBHVFeatureFactory;
import org.yccheok.numrecognition.NumberNeuralNetworkRecognizer;

public class ImgIdent {

	// 构造函数
	public ImgIdent() throws IOException {

	}

	//图像二值化
	public BufferedImage ImgColor(BufferedImage aimg){
		int bkcolor,frcolor,alphacolor; //前景色、背景色、透明度
		
		//前景色、背景色、透明度
		int i,j,pixel,alpha,red,green,blue,lcolor;
		int cnt1,cnt2,idx1,idx2;
		int[] colorcnt = new int[256];
		//主色调
		red = 0;
		green = 0;
		blue = 0;
		for (i = 0; i <aimg.getWidth(); i++) {
			for (j = 0; j< aimg.getHeight(); j++){
				pixel = aimg.getRGB(i, j);
				alpha = (pixel >> 24) & 0xff;
				red = (pixel >> 16) & 0xff;
				green = (pixel >> 8) & 0xff;
				blue = (pixel) & 0xff;
				colorcnt[(red+green+blue)/3]++;
			}
		}

		cnt1 = 0;
		idx1 = 0;
		for (i=0;i<256;i++){
			if (cnt1<colorcnt[i]){
				cnt1 = colorcnt[i] * (4 - i/64);
				idx1 = i;
			}
		}
		cnt2 = 0;
		idx2 = 0;
		for (i=0;i<256;i++){
			if ((cnt2 < colorcnt[i]  * (4 - i/64)) & (colorcnt[i]  * (4 - i/64) < cnt1)){
				cnt2 = colorcnt[i] * (4 - i/64);
				idx2 = i;
			}
		}
		bkcolor = idx1;
		frcolor = idx2;
		alphacolor = idx2/2;
		
		//二值化
		BufferedImage limg;
		
		limg = new BufferedImage(aimg.getWidth(), aimg.getHeight(), aimg.TYPE_BYTE_GRAY);
		for (i=0;i<aimg.getWidth();i++){
			for (j=0;j<aimg.getHeight();j++){
				pixel = aimg.getRGB(i, j);
				alpha = (pixel >> 24) & 0xff;
				red = (pixel >> 16) & 0xff;
				green = (pixel >> 8) & 0xff;
				blue = (pixel) & 0xff;
				lcolor = (red+green+blue)/3;
				//if ((alpha >= (frcolor-alphacolor)) && (alpha <= (frcolor+alphacolor))){
				if ((lcolor > (frcolor-alphacolor)) && (lcolor < 200)){
					limg.setRGB(i, j, 0xff000000);
				} else {
					limg.setRGB(i, j, 0xffffffff);
				}
			}
		}
		return limg;
	}
	//图像范围
	public BufferedImage ImgArea(BufferedImage aimg){
		BufferedImage limg;
		int left,right,top,botton;
		int i,j,cnt,alpha;
		alpha = 0;
		left = 0;
		for (i=0;i<aimg.getWidth();i++){
			cnt = 0;
			for (j=0;j<aimg.getHeight();j++){
				if (aimg.getRGB(i, j) == 0xff000000){
					cnt++;
				}
			}
			if (cnt > alpha){
				left = i;
				break;
			}
		}
		right = 0;
		for (i=aimg.getWidth()-1;i>=0;i--){
			cnt = 0;
			for (j=0;j<aimg.getHeight();j++){
				if (aimg.getRGB(i, j) == 0xff000000){
					cnt++;
				}
			}
			if (cnt > alpha){
				right = i;
				break;
			}
		}
		top = 0;
		for (i=0;i<aimg.getHeight();i++){
			cnt = 0;
			for (j=0;j<aimg.getWidth();j++){
				if (aimg.getRGB(j, i) == 0xff000000){
					cnt++;
				}
			}
			if (cnt > alpha){
				top = i;
				break;
			}
		}
		botton = 0;
		for (i=aimg.getHeight()-1;i>=0;i--){
			cnt = 0;
			for (j=0;j<aimg.getWidth();j++){
				if (aimg.getRGB(j, i) == 0xff000000){
					cnt++;
				}
			}
			if (cnt > alpha){
				botton = i;
				break;
			}
		}
		
		limg = aimg.getSubimage(left, top, right-left+1, botton-top+1);
		return limg;
		
	}

	public String getImageNumber(String file){
		BufferedImage imgs,img,imgc,imga;
		int[][] pix;
		int i,j,iw,ih;
		String sd,r = "",lfile = "AfterImgArea.bmp";
		
		try {
			imgs = ImageIO.read(new File(file));// 用ImageIO的静态方法读取图像
			iw = imgs.getWidth();
			ih = imgs.getHeight();
			for (i=0;i<4;i++){
				img = imgs.getSubimage(iw/4*i+1, 1, iw/4-2, ih-2);		
				imgc = ImgColor(img);
				imga = ImgArea(imgc);
				ImageIO.write(imga, "BMP", new File(lfile));

				// 得到像匹配的数字。
				sd = String.format("%d", getImageDigit(lfile));
				r = r + sd;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		return r;
	}
		
	//	 得到图像的值
	public int getImageDigit(String file) {
		int i ;

        NumberImageFeatureFactory imageFeature = new NumberImageLRTBHVFeatureFactory();
        NumberNeuralNetworkRecognizer neuralNetwork = new NumberNeuralNetworkRecognizer(
                "LRTBHV-training-data.txt-I=96-H=200-LR=0.9-M=0.1-C=2000-.snet",    
                imageFeature,
                FeatureParameter.DEFAULT_FEATURE_PARAMETER);
        i = neuralNetwork.recognize(file);
                
		return i;
	}
}
