import java.awt.*;

/**
 * abstract image class that implements the Writable interface
 */
abstract class Image implements Writable {
    /**
     * represents an abstract image
     * provides methods for modifying image properties
     */

    private int width;
    private int height;
    private Color[][] colors;

    /**
     * this is the constructor for constructing an image
     * @param width is the given width of the image
     * @param height is the given height of the image
     */
    public Image(int width, int height) {
        this.height = height;
        this.width = width;
        colors = new Color[this.height][this.width];

    }

    /**
     * this is the constructor when no parameters are given
     */
    public Image(){
        this.height = 0;
        this.width = 0;
        colors = new Color[this.height][this.width];
    }
    public Color[][] getColors() {
        return colors;
    }

    public void setColors(Color[][] color) {
        this.colors = color;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }



}
