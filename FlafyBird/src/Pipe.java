import java.awt.*;
import javax.swing.*;

public class Pipe {
    // atribut posisi, ukuran, gambar, dan kecepatan pipa
    private int posX;
    private int posY;
    private int width;
    private int height;
    private Image image;
    private int velocityX;

    // atribut tambahan untuk logika permainan
    private boolean passed = false; // menandakan apakah player sudah melewati pipa ini
    private boolean top = false; // menandakan apakah pipa ini adalah pipa atas

    // konstruktor untuk menginisialisasi nilai awal pipa
    public Pipe(int posX, int posY, int width, int height, Image image) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.image = image;
        this.passed = false;
        this.top = posY < 0; // menentukan apakah pipa berada di atas (posisi y negatif)
    }

    // memperbarui posisi pipa setiap frame agar bergerak ke kiri
    public void update() {
        posX += velocityX;
    }

    // menghasilkan objek rectangle untuk mendeteksi tabrakan dengan player
    public Rectangle getBounds() {
        return new Rectangle(posX, posY, width, height);
    }

    // getter: mengambil nilai atribut pipa
    public int getPosX() { return posX; }
    public int getPosY() { return posY; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public Image getImage() { return image; }
    public int getVelocityX() { return velocityX; }
    public boolean isPassed() { return passed; }
    public boolean isTop() { return top; }

    // setter: mengubah nilai atribut pipa
    public void setPosX(int x) { this.posX = x; }
    public void setPosY(int y) { this.posY = y; }
    public void setWidth(int w) { this.width = w; }
    public void setHeight(int h) { this.height = h; }
    public void setImage(Image img) { this.image = img; }
    public void setVelocityX(int v) { this.velocityX = v; }
    public void setPassed(boolean p) { this.passed = p; }
    public void setIsTop(boolean t) { this.top = t; }
}
