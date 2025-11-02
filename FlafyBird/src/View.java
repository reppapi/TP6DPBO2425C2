import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class View extends JPanel {
    // menentukan ukuran awal panel tampilan
    int width = 360;
    int height = 640;

    private Logic logic; // menyimpan referensi ke class logic agar view dapat mengambil data permainan

    // konstruktor view: menerima objek logic untuk menghubungkan tampilan dengan logika permainan
    public View(Logic logic){
        this.logic = logic;
        setPreferredSize(new Dimension(width, height)); // mengatur ukuran panel
        setBackground(Color.cyan); // warna latar belakang default
        setFocusable(true); // memungkinkan panel menerima input dari keyboard
        addKeyListener(logic); // menambahkan listener keyboard dari class logic
    }

    // method yang otomatis dipanggil ketika panel perlu digambar ulang
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // membersihkan tampilan lama
        draw(g); // memanggil method draw() untuk menggambar elemen permainan
    }

    private void drawCenteredString(Graphics g, String text, Font font, Color color, int y, int panelWidth) {
        FontMetrics metrics = g.getFontMetrics(font); // mengambil ukuran teks
        int x = (panelWidth - metrics.stringWidth(text)) / 2; // menghitung posisi agar teks berada di tengah
        g.setFont(font);
        g.setColor(color);
        g.drawString(text, x, y);
    }

    // method utama untuk menggambar seluruh elemen permainan
    public void draw(Graphics g) {
        // mengambil ukuran panel saat ini
        int currentWidth = getWidth();
        int currentHeight = getHeight();

        // menggambar background permainan
        Image bg = logic.getBackgroundImage();
        if (bg != null) g.drawImage(bg, 0, 0, currentWidth, currentHeight, null);

        // jika masih berada di menu utama
        if (logic.isInMenu()) {
            // menampilkan judul game di tengah
            drawCenteredString(g, "FLAPPY BIRD",
                    new Font("Arial Black", Font.BOLD, 36),
                    Color.white, 200, currentWidth);

            // menggambar tombol play berwarna hijau
            g.setColor(Color.GREEN);
            g.fillRect(logic.playButtonRect.x, logic.playButtonRect.y,
                    logic.playButtonRect.width, logic.playButtonRect.height);

            // menggambar tombol exit berwarna merah
            g.setColor(Color.RED);
            g.fillRect(logic.exitButtonRect.x, logic.exitButtonRect.y,
                    logic.exitButtonRect.width, logic.exitButtonRect.height);

            // menggambar teks “play” dan “exit” di tengah tombol masing-masing
            g.setFont(new Font("Arial", Font.BOLD, 24));
            g.setColor(Color.BLACK);
            FontMetrics fm = g.getFontMetrics();
            g.drawString("PLAY",
                    logic.playButtonRect.x + (logic.playButtonRect.width - fm.stringWidth("PLAY")) / 2,
                    logic.playButtonRect.y + (logic.playButtonRect.height - fm.getHeight()) / 2 + fm.getAscent());
            g.drawString("EXIT",
                    logic.exitButtonRect.x + (logic.exitButtonRect.width - fm.stringWidth("EXIT")) / 2,
                    logic.exitButtonRect.y + (logic.exitButtonRect.height - fm.getHeight()) / 2 + fm.getAscent());

            // menampilkan gambar burung di layar menu
            Image bird = logic.birdImage;
            g.drawImage(bird, 150, 230, 60, 45, null);
            return; // menghentikan proses menggambar jika masih di menu
        }

        // menggambar player (burung)
        Player player = logic.getPlayer();
        if (player != null) {
            g.drawImage(player.getImage(), player.getPosX(), player.getPosY(),
                    player.getWidth(), player.getHeight(), null);
        }

        // menggambar seluruh pipa
        ArrayList<Pipe> pipes = logic.getPipes();
        if (pipes != null) {
            for (int i = 0; i < pipes.size(); i++) {
                Pipe pipe = pipes.get(i);
                g.drawImage(pipe.getImage(), pipe.getPosX(), pipe.getPosY(),
                        pipe.getWidth(), pipe.getHeight(), null);
            }
        }

        // menampilkan skor di bagian atas layar
        drawCenteredString(g, String.valueOf(logic.getScore()),
                new Font("Arial Black", Font.BOLD, 26),
                Color.white, 40, currentWidth);

        // menampilkan tampilan "game over" jika permainan berakhir
        if (logic.isGameOver()) {
            g.setColor(new Color(0, 0, 0, 150)); // latar belakang semi-transparan
            g.fillRect(0, 0, currentWidth, currentHeight);

            int yBase = height / 2; // posisi vertikal dasar untuk teks

            // teks "game over"
            drawCenteredString(g, "GAME OVER",
                    new Font("Arial Black", Font.BOLD, 40),
                    Color.red, yBase - 40, currentWidth);

            // menampilkan skor akhir
            drawCenteredString(g, "Score: " + logic.getScore(),
                    new Font("Arial", Font.BOLD, 24),
                    Color.WHITE, yBase - 5, currentWidth);

            // menampilkan instruksi untuk restart dan kembali ke menu
            drawCenteredString(g, "Press R to Restart",
                    new Font("Arial", Font.BOLD, 18),
                    Color.white, yBase + 30, currentWidth);
            drawCenteredString(g, "Press ESC to Menu",
                    new Font("Arial", Font.BOLD, 18),
                    Color.white, yBase + 55, currentWidth);
        }
    }
}
