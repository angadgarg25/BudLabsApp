package greggnod.budlabapp;

/**
 * Created by Alan on 9/18/16.
 */
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.File;
import javax.imageio.ImageIO;
import java.net.URL;
import java.io.IOException;

public class ImageProcess
{
    public static void main(String[] args) throws IOException
    {
        Color color;
        URL url = new URL("https://www.agric.wa.gov.au/sites/gateway/files/styles/original/public/physiological_leaf_spot_barley_3_thumb_type.jpg?itok=vXj0rjyh");
        BufferedImage i_rgb = ImageIO.read(url);
        int[] pixelarray1 = i_rgb.getRGB(0, 0, i_rgb.getWidth(), i_rgb.getHeight(), null, 0, i_rgb.getWidth());
        int[][] pixelarray = new int[i_rgb.getWidth()][i_rgb.getHeight()];
        for (int i = 0; i < pixelarray.length; i++){
            for (int j = 0; j < pixelarray[0].length; j++){
                if ((i*pixelarray.length+j)>=pixelarray1.length){
                    break;
                }
                pixelarray[i][j] = pixelarray1[i*pixelarray.length+j];
            }
        }
        double [][] exg = new double[pixelarray.length][pixelarray[0].length];

        for (int i = 0; i < pixelarray.length; i++){
            for (int j = 0; j < pixelarray[0].length; j++){
                color = new Color(pixelarray[i][j]);
                exg[i][j] = -.8*color.getRed() +2*color.getGreen() -0.1*color.getBlue();
            }
        }
        for (int i = 0; i < pixelarray.length; i++){
            for (int j = 0; j < pixelarray[0].length; j++){
                if (exg[i][j] >= 120)
                    exg[i][j] = 255;
            }
        }

        double[][] f = new double[(int)((exg.length)/3)][(int)(exg[0].length/3)];
        double[][] z = new double[(int)((exg.length)/3)*(int)(exg[0].length/3)][4];
        int x = 0;

        for (int i = 0; i < f.length; i++){
            for (int j = 0; j < f[0].length; j++){
                double count = 0;
                for (int k = 0; k < 3; k++){
                    for (int l = 0; k < 3; k++){
                        count += exg[(i)*3+l][(j)*3+k]/255;
                    }
                }
                f[i][j] = count;
                z[x][1]=(i-1)*3+1;
                z[x][2]=(j-1)*3+1;
                z[x][3]=i*3;
                //z[x][4]=j*3;
                x++;
            }
        }

        HeatChart map = new HeatChart(f);
        map.setTitle("Heat Chart");
        map.setXAxisLabel("X Axis");
        map.setYAxisLabel("Y Axis");

        map.saveToFile(new File("top-crop-heat-chart5.png"));
    }

    public static BufferedImage toBufferedImage(Image img)
    {
        if (img instanceof BufferedImage)
        {
            return (BufferedImage) img;
        }

        // Create a buffered image with transparency
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        // Return the buffered image
        return bimage;
    }
}