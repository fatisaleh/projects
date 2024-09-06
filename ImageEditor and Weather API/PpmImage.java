import java.awt.*;
import java.io.*;
import java.util.Scanner;

/**
 * class that interprets pixel data
 */

class PpmImage extends Image {
    /**
     * represents an image in PPM format
     * @param width is the image width
     * @param height is the image height
     */

    public PpmImage(int width, int height) {
        super(width, height);
    }

    /**
     * constructor for PpmImage
     * @param filename - string filename
     */
    public PpmImage(String filename){
        super();
        readImage(filename);
    }

    /**
     * reads an image and assigns the instance variables appropriately
     * @param filename - string filename
     */
     void readImage(String filename) {
// Open the scanner. CATCH THE EXCEPTION! DO NOT
// SIMPLY MARK THE METHOD AS "throws IOEXception"
        try{
            File file = new File(filename);
            Scanner sc = new Scanner(file);
// Skip the P3 line.
            sc.nextLine();
            int width = sc.nextInt();
            int height = sc.nextInt();
            this.setWidth(width);
            this.setHeight(height);
            this.setColors(new Color[height][width]);
            Color[][] temp = new Color[height][width];
            //skip alpha
            sc.nextInt();



            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
// Populate the pixels array.


                    temp[j][i] = new Color(sc.nextInt(),sc.nextInt(),sc.nextInt());
                }
            }
            this.setColors(temp);
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }

    }


    /**
     * main method for testing image output
     * @param args is the input arguments
     */

//    public static void main(String[] args) {
//       PpmImage ppmImage = new PpmImage("test.ppm");
//
//        // Output the image to a new file
//        ppmImage.output("output.ppm");
//
//        System.out.println("Image output successful!");
//
//    }

    /**
     * override for image output
     * @param filename is the input file name
     */
    @Override
    public void output(String filename) {

        try(BufferedWriter br = new BufferedWriter(new FileWriter(filename))){
            br.write("P3");
            br.write("\n");
            br.write(getWidth()+" "+getHeight());
            br.write("\n");
            br.write("255");
            br.write("\n");

            Color[][] temp = getColors();

//            for (int i = 0; i < getWidth(); i++) {
//                for (int j = 0; j < getHeight(); j++) {
//                    Color color = temp[j][i];
//                    br.write(color.getRed()+" " + color.getGreen() + " " + color.getBlue());
//                    br.write(" ");
//                }
//                br.write("\n");
//            }
            for (int i = 0; i < getWidth(); i++) {
                for (int j = 0; j < getHeight(); j++) {
                    Color color = temp[j][i];
                    br.write(color.getRed()+" " + color.getGreen() + " " + color.getBlue());
                    br.write(" ");
                }
                br.write("\n");
            }
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
    }


}
