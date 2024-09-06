import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.Objects;

/**
 * class that performs a series of operations on an input image
 */
class ImageOperations {


    /**
     * removes the red channel from the input images
     * @param img is the input image
     * @return image without the red channel
     */
    public static PpmImage zeroRed(PpmImage img) {
        int width = img.getWidth();
        int height = img.getHeight();
        Color[][] temp = img.getColors();
        PpmImage withoutRed = new PpmImage(width, height);

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Color color = temp[i][j];
                Color noRed = new Color(0, color.getGreen(), color.getBlue());
                temp[i][j] = noRed;
            }
        }
        withoutRed.setColors(temp);
        return withoutRed;
    }

    /**
     * Main method to test image operations
     * @param args is the input arguments
     */
    public static void main(String[] args) {
//        args[0] = "--zerored";
//        args[1] = "in-file.ppm";
//        args[2] = "out-file.ppm";
        if (args[0].equals("--zerored")) {
            PpmImage img = new PpmImage(args[1]);
            img.readImage(args[1]);
            PpmImage newimg = zeroRed(img);
            newimg.output(args[2]);
        } else if (args[0].equals("--grayscale")) {
            PpmImage img = new PpmImage(args[1]);
            img.readImage(args[1]);
            PpmImage newimg = grayscale(img);
            newimg.output(args[2]);
        } else if (args[0].equals("--mirror")) {
            //--mirror pm.ppm pmmirrorv.ppm V
            PpmImage img = new PpmImage(args[1]);
            img.readImage(args[1]);
            PpmImage newimg = mirror(img, args[3]);
            newimg.output(args[2]);
        } else if (args[0].equals("--crop")) {
            //--crop 100 100 50 50 pm.ppm pmcrop.ppm
            PpmImage img = new PpmImage(args[5]);
            img.readImage(args[5]);
            PpmImage newimg = crop(img, Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]));
            newimg.output(args[6]);
        } else if (args[0].equals("--invert")) {
            //--invert pm.ppm pminvert.ppm
            PpmImage img = new PpmImage(args[1]);
            img.readImage(args[1]);
            PpmImage newimg = invert(img);
            newimg.output(args[2]);
        } else if (args[0].equals("--repeat")) {
            //--repeat V 5 pm.ppm pmrepeatV5.ppm
            //--repeat V 5 pm.ppm pmrepeatV5.ppm
            //
            PpmImage img = new PpmImage(args[3]);
            img.readImage(args[3]);
            PpmImage newimg = repeat(img, Integer.parseInt(args[2]), args[1]);
            System.out.println(newimg.getWidth());
            System.out.println(newimg.getHeight());

            newimg.output(args[4]);
        }
    }

    /**
     * converts images to grayscale
     * @param img is the input image
     * @return image with grayscale
     */
    public static PpmImage grayscale(PpmImage img) {


        int width = img.getWidth();
        int height = img.getHeight();
        //int[][][] pixels;
        PpmImage pi = new PpmImage(width, height);
        Color[][] temp = img.getColors();
        //int width = temp.length;
        //int height = temp[0].length;


        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Color color = temp[i][j];
                int red = color.getRed();
                int green = color.getGreen();
                int blue = color.getBlue();

                int grayscale = (int) (0.299 * red + 0.587 * green + 0.114 * blue);

                //pi.setColors(grayscale);

                Color grayScale = new Color(grayscale, grayscale, grayscale);
                temp[i][j] = grayScale;
                pi.setColors(temp);
            }
        }
        return pi;
    }

    /**
     * inverts the color pixels of images
     * @param img is the input image
     * @return image with inverted colors
     */
    public static PpmImage invert(PpmImage img) {


//        BufferedImage bi = (BufferedImage) img;
//        for (int i = 0; i < bi.getWidth(); i++) {
//            for (int j = 0; j < bi.getHeight(); j++) {
//                int rgba = bi.getRGB(i,j);
//                Color c = new Color(rgba);
//                c = new Color(255-c.getRed(),
//                        255-c.getGreen(),
//                        255-c.getBlue());
//                bi.setRGB(i,j,c.getRGB());
//            }
//        }
//        return bi;

        int width = img.getWidth();
        int height = img.getHeight();
        PpmImage pi = new PpmImage(width, height);
        Color[][] temp = img.getColors();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Color color = temp[i][j];
                int red = color.getRed();
                int green = color.getGreen();
                int blue = color.getBlue();

                int invertRed = 255 - red;
                int invertGreen = 255 - green;
                int invertBlue = 255 - blue;

                Color invert = new Color(invertRed, invertGreen, invertBlue);
                temp[i][j] = invert;
                pi.setColors(temp);
            }
        }


        return pi;
    }

    /**
     * crops an image
     * @param img is the input image
     * @param x1 the starting x-coordinate of the image to be cropped
     * @param y1 the starting y-coordinate of the image to be cropped
     * @param w is the width of the image
     * @param h is the height of the image
     * @return cropped image
     */
    public static PpmImage crop(PpmImage img, int x1, int y1, int w, int h) {
        PpmImage cropImg = new PpmImage(w, h);
        Color[][] cropColors = img.getColors();

        for (int i = x1; i < x1 + h && i < img.getHeight(); i++) {
            for (int j = y1; j < y1 + w && j < img.getWidth(); j++) {
                cropColors[i - x1][j - y1] = img.getColors()[i][j];
            }
        }
        cropImg.setColors(cropColors);
        return cropImg;
    }


    /**
     * mirrors an image horizontally or vertically
     * @param img is the input image
     * @param mode is the direction that the image is being mirrored (horizontally or vertically)
     * @return an image reflected horizontally or vertically
     */
    public static PpmImage mirror(PpmImage img, String mode) {
        int width = img.getWidth();
        int height = img.getHeight();
        Color[][] temp = img.getColors();
        PpmImage mirrorImage = new PpmImage(width, height);
        Color[][] newimage = new Color[img.getWidth()][img.getHeight()];
        if (Objects.equals(mode, "H")) {
            int halfwidth = width / 2;
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (j < halfwidth) {

                    } else if (j > halfwidth) {
                        temp[i][j] = temp[i][width - j];
                    }
                }
            }
        } else {
            int halfHeight = height / 2;
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (i < halfHeight) {

                    } else if (i > halfHeight) {
                        temp[i][j] = temp[height - i][j];
                    }
                }
            }
        }
        mirrorImage.setColors(temp);
        return mirrorImage;
    }

    /**
     * repeats an image horizontally or vertically
     * @param img is the input image
     * @param n is the number of times that the image is repeated
     * @param dir is the direction that the image is being repeated (horizontal or vertical)
     * @return image that is repeated either horizontally or vertically
     */
    public static PpmImage repeat(PpmImage img, int n, String dir) {
        int repeatWidth;
        int repeatHeight;
        if (dir.equals("H")) {
            repeatWidth = img.getWidth() * n;
            repeatHeight = img.getHeight();
        }else if (dir.equals("V")) {
            repeatHeight = img.getHeight() * n;
            repeatWidth = img.getWidth();
        } else {
            repeatWidth = img.getWidth();
            repeatHeight = img.getHeight();
        }
        PpmImage img2 = new PpmImage(repeatWidth, repeatHeight);
        Color[][] repeatColor = img.getColors();
        Color[][] newimage = new Color[repeatHeight][repeatWidth];
        if (dir.equals("H")) {
            for (int i = 0; i < img.getHeight(); i++) {
                for (int j = 0; j < img.getWidth(); j++) {
                    for (int k = 0; k < n; k++){
                        newimage[i][j * n + k] = repeatColor[i][j];
                    }
                }
            }
        }else if (dir.equals("V")) {
//            for (int i = 0; i < img.getHeight(); i++) {
//                for (int j = 0; j < img.getWidth(); j++) {
//                    for (int k = 0; k < n; k++){
//                        newimage[i * n + k][j] = repeatColor[i][j];
//                    }
//                }
//
//            }
            for (int i = 0; i <img.getWidth() ; i++) {
                for (int j = 0; j < img.getHeight(); j++) {
                    for (int k = 0; k < n; k++) {
                        newimage[i + k *img.getHeight()][j] = repeatColor[i][j];
                    }
                }
            }

        }
        img2.setColors(newimage);
        return img2;
    }
//        int repeatWidth;
//        int repeatHeight;
//        if (dir.equals("H")) {
//            repeatWidth = img.getWidth() * n;
//            repeatHeight = img.getHeight();
//        } else if (dir.equals("V")) {
//            repeatHeight = img.getHeight() * n;
//            repeatWidth = img.getWidth();
//        } else {
//            repeatWidth = img.getWidth();
//            repeatHeight = img.getHeight();
//        }
//        PpmImage img2 = new PpmImage(repeatWidth, repeatHeight);
//        Color[][] repeatColor = img.getColors();
//        Color[][] newimage = new Color[repeatHeight][repeatWidth];       //System.out.println(repeatColor[0].length);
//        if (dir.equals("H")) {
//            System.out.println(repeatWidth);
//
//            for (int i = 0; i < repeatHeight; i++) {
//                for (int j = 0; j < repeatWidth; j++) {
//                    newimage[i][j] = repeatColor[i][j % img.getWidth()];
//                }
//            }
//        } else if (dir.equals("V")) {
//            for (int i = 0; i < repeatWidth; i++) {
//                for (int j = 0; j < repeatHeight; j++) {
//                    newimage[i][j] = repeatColor[i][j % img.getHeight()];
//                }
//
//            }
////
//}
//            img2.setColors(newimage);
//            return img2;

 //       for(int i = 0; i < repeatHeight; i++){
//            for(int j = 0; j < repeatWidth; j++){
//                int newHeight;
//                int newWidth;
//                if(dir.equals("H")){
//                    newWidth = j % repeatWidth;
//                    newHeight = i;
//                }
//                else if(dir.equals("V")){
//                    newWidth = j;
//                    newHeight = i % repeatHeight;
//                }
//                else{
//                    newWidth = j;
//                    newHeight = i;
//                }
//                newimage[i][j] = repeatColor[newHeight][newWidth];
//            }


}
