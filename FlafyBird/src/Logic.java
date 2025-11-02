import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

// class logic bertugas mengatur seluruh logika permainan
public class Logic implements ActionListener, KeyListener, MouseListener {
    // ukuran jendela permainan
    int frameWidth = 360;
    int frameHeight = 640;

    // posisi dan ukuran awal player
    int playerStartPosX = frameWidth / 2 - 30;
    int playerStartPosY = frameHeight / 2;
    int playerWidth = 34;
    int playerHeight = 24;

    // data awal untuk pipa
    int pipeStartPosX = frameWidth;
    int pipeStartPosY = 0;
    int pipeWidth = 64;
    int pipeHeight = 512;

    // objek untuk tampilan dan gambar
    View view;
    Image birdImage;
    Image backgroundImage;
    Image lowerPipeImage;
    Image upperPipeImage;

    // objek utama game
    Player player;
    ArrayList<Pipe> pipes;

    // timer untuk pergerakan game dan cooldown pembuatan pipa
    Timer gameLoop;
    Timer pipesCooldown;

    // variabel dasar logika fisik permainan
    int gravity = 1;
    int pipeVelocityX = -2;

    // status permainan
    boolean running = false;
    boolean gameOver = false;
    boolean inMenu = true;
    int score = 0;

    // area tombol di menu
    public Rectangle playButtonRect = new Rectangle(80, 310, 200, 40);
    public Rectangle exitButtonRect = new Rectangle(80, 360, 200, 40);

    // konstruktor utama, inisialisasi gambar, player, dan timer
    public Logic() {
        backgroundImage = new ImageIcon(getClass().getResource("assets/background.png")).getImage();
        birdImage = new ImageIcon(getClass().getResource("assets/bird.png")).getImage();
        lowerPipeImage = new ImageIcon(getClass().getResource("assets/lowerPipe.png")).getImage();
        upperPipeImage = new ImageIcon(getClass().getResource("assets/upperPipe.png")).getImage();

        player = new Player(playerStartPosX, playerStartPosY, playerWidth, playerHeight, birdImage);
        pipes = new ArrayList<Pipe>();

        // timer untuk membuat pipa secara berkala
        pipesCooldown = new Timer(2200, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                placePipes();
            }
        });

        // timer utama untuk loop game
        gameLoop = new Timer(1000 / 60, this);
        gameLoop.start();
    }

    // menghubungkan view dengan logic dan menambahkan listener
    public void setView(View view) {
        this.view = view;
        view.addKeyListener(this);
        view.setFocusable(true);
        view.requestFocusInWindow();
        view.addMouseListener(this); // menambahkan listener mouse untuk tombol menu
    }

    // getter dasar
    public Player getPlayer() { return player; }
    public ArrayList<Pipe> getPipes() { return pipes; }
    public Image getBackgroundImage() { return backgroundImage; }
    public boolean isGameOver() { return gameOver; }
    public int getScore() { return score; }
    public boolean isInMenu() { return inMenu; }

    // memulai permainan dari awal
    public void startGame() {
        inMenu = false;
        resetGame();
        running = true;
        gameOver = false;
        pipesCooldown.start();
    }

    // mengatur ulang posisi dan variabel permainan
    public void resetGame() {
        pipes.clear();
        score = 0;
        player.setPosX(playerStartPosX);
        player.setPosY(playerStartPosY);
        player.setVelocityY(0);
    }

    // membuat dua pipa (atas dan bawah) dengan jarak acak
    public void placePipes() {
        int randomPosY = (int) (pipeStartPosY - pipeHeight / 4 - Math.random() * (pipeHeight / 2));
        int openingSpace = frameHeight / 4;

        Pipe upperPipe = new Pipe(pipeStartPosX, randomPosY, pipeWidth, pipeHeight, upperPipeImage);
        pipes.add(upperPipe);

        Pipe lowerPipe = new Pipe(pipeStartPosX, randomPosY + openingSpace + pipeHeight, pipeWidth, pipeHeight, lowerPipeImage);
        pipes.add(lowerPipe);
    }

    // memperbarui posisi player dan pipa setiap frame
    public void move() {
        if (inMenu || !running) return;

        player.setVelocityY(player.getVelocityY() + gravity);
        player.setPosY(player.getPosY() + player.getVelocityY());
        player.setPosY(Math.max(player.getPosY(), 0));

        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            pipe.setPosX(pipe.getPosX() + pipeVelocityX);
            if (pipe.getPosX() + pipe.getWidth() < 0) {
                pipes.remove(i);
                i--;
            }
        }

        checkCollision();
        checkScore();
    }

    // memeriksa apakah player menabrak pipa atau tanah
    public void checkCollision() {
        if (player.getPosY() + player.getHeight() >= frameHeight - 20) {
            triggerGameOver();
            return;
        }

        Rectangle playerRect = player.getBounds();
        for (Pipe pipe : pipes) {
            if (playerRect.intersects(pipe.getBounds())) {
                triggerGameOver();
                return;
            }
        }
    }

    // menambahkan skor jika player berhasil melewati pipa
    public void checkScore() {
        for (Pipe pipe : pipes) {
            if (!pipe.isPassed() && !pipe.isTop()) {
                if (player.getPosX() > pipe.getPosX() + pipe.getWidth()) {
                    pipe.setPassed(true);
                    score++;
                }
            }
        }
    }

    // mengaktifkan status game over
    public void triggerGameOver() {
        running = false;
        gameOver = true;
        pipesCooldown.stop();
    }

    // memulai ulang permainan setelah game over
    public void restart() {
        resetGame();
        startGame();
    }

    // kembali ke menu utama dari permainan
    public void backToMenu() {
        inMenu = true;
        gameOver = false;
        running = false;
        pipes.clear();
    }

    // dijalankan setiap kali timer utama aktif
    public void actionPerformed(ActionEvent e) {
        move();
        if (view != null) view.repaint();
    }

    // event untuk keyboard
    public void keyTyped(KeyEvent e) {}

    // saat tombol ditekan: hanya digunakan untuk lompatan player
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (running && !gameOver && key == KeyEvent.VK_SPACE) {
            player.setVelocityY(-10);
        }
    }

    // saat tombol dilepas: digunakan untuk restart dan kembali ke menu
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (gameOver) {
            if (key == KeyEvent.VK_R) {
                restart();
            } else if (key == KeyEvent.VK_ESCAPE) {
                backToMenu();
            }
        }
    }

    // event klik mouse di menu utama
    @Override
    public void mouseClicked(MouseEvent e) {
        if (inMenu) {
            Point clickPoint = e.getPoint();

            if (playButtonRect.contains(clickPoint)) {
                startGame();
            }
            else if (exitButtonRect.contains(clickPoint)) {
                System.exit(0);
            }
        }
    }

    // event mouse lainnya (tidak digunakan)
    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
}
