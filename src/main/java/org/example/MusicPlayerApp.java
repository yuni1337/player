package org.example;

import javax.swing.*;
import java.awt.*;
import java.io.File;


public class MusicPlayerApp extends JFrame {
    private JList<String> fileList;
    private DefaultListModel<String> listModel;
    private File audioFolder = new File("C:/Users/my pc/Desktop/player/src/main/java/org/example/audio"); // Папка с аудиофайлами
    private AudioFilePlayer player;

    public MusicPlayerApp() {
        setTitle("Audio Player");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Панель управления
        JPanel controlPanel = new JPanel();
        listModel = new DefaultListModel<>();
        fileList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(fileList);
        controlPanel.add(scrollPane);

        JButton playButton = new JButton("Play");
        controlPanel.add(playButton);

        if (playButton.getText().equals("Pause") && player == null) {
            playButton.setText("Play");
        }
        playButton.addActionListener(e -> {
            if (playButton.getText().equals("Play")) {
                playSelectedFile();
                playButton.setText("Pause");
            }
            else {
                stopFile();
                playButton.setText("Play");
            }
        });
        add(controlPanel, BorderLayout.CENTER);

        // Загрузка всех файлов из папки audio
        loadAudioFiles();
    }

    private void loadAudioFiles() {
        if (!audioFolder.exists() || !audioFolder.isDirectory()) {
            JOptionPane.showMessageDialog(this, "Папка audio не найдена!", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return;
        }

        File[] files = audioFolder.listFiles((dir, name) -> name.endsWith(".mp3") || name.endsWith(".wav") || name.endsWith(".mp4"));
        if (files != null) {
            for (File file : files) {
                listModel.addElement(file.getName());
            }
        }
    }

    private void playSelectedFile() {
        String selectedFileName = fileList.getSelectedValue();
        if (selectedFileName == null) {
            JOptionPane.showMessageDialog(this, "Выберите файл для воспроизведения", "Ошибка", JOptionPane.WARNING_MESSAGE);
            return;
        }

        File selectedFile = new File(audioFolder, selectedFileName);
        if (!selectedFile.exists()) {
            JOptionPane.showMessageDialog(this, "Файл не найден: " + selectedFile.getAbsolutePath(), "Ошибка", JOptionPane.ERROR_MESSAGE);
            return;
        }
        player = new AudioFilePlayer(selectedFile);
        player.play();
    }

    private void stopFile() {
        if (player != null) {
            player.stop();
            player = null; // Освобождаем объект
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MusicPlayerApp audioPlayer = new MusicPlayerApp();
            audioPlayer.setVisible(true);
        });
    }
}
