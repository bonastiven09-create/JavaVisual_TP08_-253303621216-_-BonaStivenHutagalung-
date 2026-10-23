package com.unpri.charactermanager.view;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import java.awt.BorderLayout;
import java.awt.Font;
import java.util.ArrayList;
import com.unpri.charactermanager.model.Character;

/**
 * Jendela utama aplikasi Character Manager.
 * Menampilkan daftar karakter yang diterima dari luar (bukan dibuat sendiri).
 */
public class MainWindow extends JFrame {

    private JPanel mainPanel;
    private ArrayList<Character> characterList;   // data yang akan ditampilkan

    public MainWindow(ArrayList<Character> characterList) {
        super("Character Manager v0.3");           // judul title bar
        this.characterList = characterList;        // simpan data dari Main.java

        initComponents();

        setSize(500, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        // Judul
        JLabel titleLabel = new JLabel("=== Daftar Karakter ===");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        titleLabel.setAlignmentX(LEFT_ALIGNMENT);
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createVerticalStrut(10));

        // Daftar karakter DINAMIS: satu JLabel per karakter, dibuat lewat loop
        for (Character c : characterList) {
            JLabel row = new JLabel(String.format(
                    "%-12s | %-8s | Lv %2d | HP %3d | ATK %3d",
                    c.getName(), c.getType(), c.getLevel(),
                    c.getHitPoints(), c.getAttackPower()));
            row.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13)); // agar kolom sejajar
            row.setAlignmentX(LEFT_ALIGNMENT);
            mainPanel.add(row);
            mainPanel.add(Box.createVerticalStrut(4));
        }

        // Statistik di bawah daftar
        mainPanel.add(Box.createVerticalStrut(12));
        mainPanel.add(new JSeparator());
        mainPanel.add(Box.createVerticalStrut(10));

        JLabel statLabel = new JLabel(buildStatText());
        statLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        statLabel.setAlignmentX(LEFT_ALIGNMENT);
        mainPanel.add(statLabel);

        add(mainPanel, BorderLayout.NORTH);
    }

    /** Menyusun teks statistik. Dipisah dari initComponents agar mudah dibaca. */
    private String buildStatText() {
        if (characterList.isEmpty()) {
            return "Rata-rata Level: - (belum ada karakter)";   // hindari pembagian dengan 0
        }
        return String.format("Rata-rata Level: %.2f (dari %d karakter)",
                hitungRataRataLevel(), characterList.size());
    }

    /** Menjumlahkan semua level lalu membaginya dengan jumlah karakter. */
    private double hitungRataRataLevel() {
        int total = 0;
        for (Character c : characterList) {
            total += c.getLevel();                 // int dijumlahkan dulu
        }
        return (double) total / characterList.size();  // cast ke double sebelum dibagi
    }
}
