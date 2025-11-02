import javax.swing.*;

public class App {
    public static void main(String[] args){
        // menjalankan pembuatan tampilan GUI di thread milik swing
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // membuat jendela utama dengan judul "flafy bird"
                JFrame frame = new JFrame("Flappy Bird");

                // mengatur agar program berhenti saat tombol close ditekan
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                // mengatur ukuran jendela menjadi 360x640 pixel
                frame.setSize(360, 640);

                // menempatkan jendela di tengah layar
                frame.setLocationRelativeTo(null);

                // mengunci ukura jendela agar tidak dapat diubah oleh pengguna
                frame.setResizable(false);

                // membuat logic sebagai pengatur logika permainan
                Logic logic = new Logic();
                // membuat objek view sebagai tampilan permainan
                View view = new View(logic);

                // menghubungkan objek logic dengan view
                logic.setView(view);

                // menambahkan panel view ke dalam jendela utama
                frame.add(view);

                // menampilkan jendela permainan ke layar
                frame.setVisible(true);

                // meminta fokus pada view agar input dari keyboard dapat diterima
                view.requestFocusInWindow();
            }
        });
    }
}