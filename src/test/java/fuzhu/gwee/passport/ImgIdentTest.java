package fuzhu.gwee.passport;

import static org.junit.Assert.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.yccheok.numrecognition.FeatureParameter;
import org.yccheok.numrecognition.NumberImageFeatureFactory;
import org.yccheok.numrecognition.NumberImageLRTBHVFeatureFactory;
import org.yccheok.numrecognition.NumberNeuralNetworkRecognizer;

public class ImgIdentTest {

	BufferedImage imgs;// 待识别多字符图像组
	BufferedImage img;// 待识别单字符图像
	
	ImgIdent imgident;
	String path = "res/img/";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		String file;
		
		file =path + "9630.png";
		imgs = ImageIO.read(new File(file));// 用ImageIO的静态方法读取图像
		imgident = new ImgIdent();
	
		img = imgs.getSubimage(1, 1, 13, 19);		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testImgColor() {
		BufferedImage imgc,imga;
		
		
		try {
			ImageIO.write(img, "JPEG", new File(path+"BeforeImgColor" + ".jpg"));
			imgc = imgident.ImgColor(img);
			ImageIO.write(imgc, "JPEG", new File(path+"AfterImgColor" + ".jpg"));
			imga = imgident.ImgArea(imgc);
			ImageIO.write(imga, "JPEG", new File(path+"ImgArea" + ".jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public final void testMutipleNum() {
        NumberImageFeatureFactory imageFeature = new NumberImageLRTBHVFeatureFactory();
        NumberNeuralNetworkRecognizer neuralNetwork = new NumberNeuralNetworkRecognizer(
        		"LRTBHV-training-data.txt-I=96-H=200-LR=0.9-M=0.1-C=2000-.snet",    
                imageFeature,
                FeatureParameter.DEFAULT_FEATURE_PARAMETER);
        
        String fileName = path+"1749.png";
        System.out.println(fileName+" is recognized as "+neuralNetwork.recognize(fileName));        

        System.out.println(fileName+" is recognized as ");
        String[] s = neuralNetwork.recognize(fileName, 14);
        for(int i=0; i<s.length; i++) {
            System.out.println(""+s[i]);
        }
                
	}

	@Test
	public final void testlibnumrecognitiondemo() {
        // Construct a factory to generate image feature.
        //
        NumberImageFeatureFactory imageFeature = new NumberImageLRTBHVFeatureFactory();
        
        // Construct a neural network for number image recognition purpose.
        //
        NumberNeuralNetworkRecognizer neuralNetwork = new NumberNeuralNetworkRecognizer(
                // This is the pre-trained neural network by using 10000 hand writen 
                // number images from http://yann.lecun.com/exdb/mnist/
                // This neural network has 96 input neurons, 200 hiden neurons.
                // It is trained by using learning rate 0.9, momentum 0.1
                // with iteration 2000.
                //
        		 "LRTBHV-training-data.txt-I=96-H=200-LR=0.9-M=0.1-C=2000-.snet",    
                
                // We will use LRTBHV feature.
                //
                imageFeature,
                
                // With default feature parameter.
                //
                FeatureParameter.DEFAULT_FEATURE_PARAMETER);
        
        // Try to recognize handwritten number image (1 digit).
        //
        String fileName = path+"1-1.jpg";
        //System.out.println(fileName+" is recognized as "+neuralNetwork.recognize(fileName));        
        assertEquals(fileName+" is not recognized as 1",1,neuralNetwork.recognize(fileName));
        fileName = path+"2-1.jpg";
       // System.out.println(fileName+" is recognized as "+neuralNetwork.recognize(fileName));              
        assertEquals(fileName+" is not recognized as 2",2,neuralNetwork.recognize(fileName));
        fileName = path+"3-1.jpg";
        //System.out.println(fileName+" is recognized as "+neuralNetwork.recognize(fileName));
        assertEquals(fileName+" is not recognized as 3",3,neuralNetwork.recognize(fileName));
        fileName = path+"4-1.jpg";
        //System.out.println(fileName+" is recognized as "+neuralNetwork.recognize(fileName));              
        assertEquals(fileName+" is not recognized as 4",4,neuralNetwork.recognize(fileName));
       fileName = path+"5-1.jpg";
       // System.out.println(fileName+" is recognized as "+neuralNetwork.recognize(fileName));              
       assertEquals(fileName+" is not recognized as 5",5,neuralNetwork.recognize(fileName));
        fileName = path+"6-1.jpg";
        //System.out.println(fileName+" is recognized as "+neuralNetwork.recognize(fileName));              
        assertEquals(fileName+" is not recognized as 6",6,neuralNetwork.recognize(fileName));
       fileName = path+"7-1.jpg";
        //System.out.println(fileName+" is recognized as "+neuralNetwork.recognize(fileName));              
        assertEquals(fileName+" is not recognized as 7",7,neuralNetwork.recognize(fileName));
        fileName = path+"8-1.jpg";
        //System.out.println(fileName+" is recognized as "+neuralNetwork.recognize(fileName));              
        assertEquals(fileName+" is not recognized as 8",8,neuralNetwork.recognize(fileName));
        fileName = path+"9-1.jpg";
        //System.out.println(fileName+" is recognized as "+neuralNetwork.recognize(fileName));              
        assertEquals(fileName+" is not recognized as 9",9,neuralNetwork.recognize(fileName));
       fileName = path+"0-1.jpg";
        //System.out.println(fileName+" is recognized as "+neuralNetwork.recognize(fileName));              
        assertEquals(fileName+" is not recognized as 0",0,neuralNetwork.recognize(fileName));

        
        // Try to recognize handwritten number image (multiple digits).
        //         
        //System.out.println("multiple-numbers-1.bmp is recognized as ");
        String[] s = neuralNetwork.recognize(path+"multiple-numbers-1.bmp", 144);
        /*
        for(int i=0; i<s.length; i++) {
            System.out.println(""+s[i]);
        }
        */
        assertEquals("multiple-numbers-1.bmp is not recognized as 123","123",s[0]);
        assertEquals("multiple-numbers-1.bmp is not recognized as 789","789",s[1]);
        assertEquals("multiple-numbers-1.bmp is not recognized as 456","456",s[2]);
        assertEquals("multiple-numbers-1.bmp is not recognized as 034","034",s[3]);
                
        //System.out.println("multiple-numbers-2.bmp is recognized as ");
        s = neuralNetwork.recognize(path+"multiple-numbers-2.bmp", 144);
        /*
       for(int i=0; i<s.length; i++) {
            System.out.println(""+s[i]);
        }
        */
        assertEquals("multiple-numbers-2.bmp is not recognized as 0","0",s[0]);
        assertEquals("multiple-numbers-2.bmp is not recognized as 877","877",s[1]);
        assertEquals("multiple-numbers-2.bmp is not recognized as 123","123",s[2]);
	}

	@Test
	public final void test1digit() {
        NumberImageFeatureFactory imageFeature = new NumberImageLRTBHVFeatureFactory();
        NumberNeuralNetworkRecognizer neuralNetwork = new NumberNeuralNetworkRecognizer(
        		"LRTBHV-training-data.txt-I=96-H=200-LR=0.9-M=0.1-C=2000-.snet",    
                imageFeature,
                FeatureParameter.DEFAULT_FEATURE_PARAMETER);
        String fileName = path+"AfterImgColor.jpg";
        //System.out.println(fileName+" is recognized as "+neuralNetwork.recognize(fileName));
        assertEquals(fileName+" is not recognized as 9",9,neuralNetwork.recognize(fileName));
	}

	@Test
	public final void testImgIdentFile() throws IOException {
		String file,validate;
		
		file = path+"1749.png";		
        validate = imgident.getImageNumber(file);
        //System.out.println(validate);
        assertEquals(file+" is not recognized as 1749","1749",validate);

        file = path+"9630.png";		
        validate = imgident.getImageNumber(file);
        //System.out.println(validate);
        assertEquals(file+" is not recognized as 9630","9630",validate);
	}
	
}
