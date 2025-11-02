import java.awt.*;

public class Player {
    // atribut posisi, ukuran, dan gambar untuk objek player
    private int posX;
    private int posY;
    private int width;
    private int height;
    private Image image;

    // atribut kecepatan jatuh vertikal dan gravitasi
    private int velocityY;
    private final int gravity = 1;

    // konstruktor untuk menginisialisasi data player saat pertama kali dibuat
    public Player(int posX, int posY, int width, int height, Image image){
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.image = image;
        this.velocityY = 0; // nilai awal kecepatan vertikal
    }

    // menambahkan efek gravitasi ke player setiap frame
    public void applyGravity() {
        velocityY += gravity; // menambah kecepatan jatuh
        if (velocityY > 12) velocityY = 12; // membatasi kecepatan jatuh maksimal
    }

    // memperbarui posisi player berdasarkan kecepatan vertikal
    public void update() {
        posY += velocityY;
    }

    // menghasilkan objek rectangle untuk mendeteksi tabrakan dengan objek lain
    public Rectangle getBounds() {
        return new Rectangle(posX, posY, width, height);
    }

    // getter: mengambil nilai dari atribut player
    public int getPosX() { return posX; }
    public int getPosY() { return posY; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public Image getImage() { return image; }
    public int getVelocityY() { return velocityY; }

    // setter: mengubah nilai atribut player
    public void setPosX(int posX) { this.posX = posX; }
    public void setPosY(int posY) { this.posY = posY; }
    public void setWidth(int width) { this.width = width; }
    public void setHeight(int height) { this.height = height; }
    public void setImage(Image image) { this.image = image; }
    public void setVelocityY(int velocityY) { this.velocityY = velocityY; }
}