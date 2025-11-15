import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Baca file teks (asumsikan file bernama 'input.txt')
        String filePath = "input.txt";
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("File input.txt tidak ditemukan.");
            return;
        }

        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error membaca file: " + e.getMessage());
            return;
        }

        // Buat queue dan enqueue setiap baris
        Queue<String> queue = new LinkedList<>();
        for (String line : lines) {
            queue.add(line);
        }

        // Minta input jumlah bagian dari pengguna
        System.out.print("Masukkan jumlah bagian untuk memotong file: ");
        int n = scanner.nextInt();
        if (n <= 0) {
            System.out.println("Input tidak valid. Harus angka positif.");
            return;
        }

        int totalLines = queue.size();
        int baseSize = totalLines / n;
        int remainder = totalLines % n;

        for (int i = 0; i < n; i++) {
            int partSize = baseSize + (i < remainder ? 1 : 0);
            StringBuilder partContent = new StringBuilder();

            for (int j = 0; j < partSize; j++) {
                if (!queue.isEmpty()) {
                    partContent.append(queue.poll()).append("\n");
                }
            }

            // Tulis bagian ke file baru
            String outputFile = "part" + (i + 1) + ".txt";
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile))) {
                bw.write(partContent.toString().trim());
                System.out.println("Bagian " + (i + 1) + " ditulis ke " + outputFile);
            } catch (IOException e) {
                System.out.println("Error menulis file " + outputFile + ": " + e.getMessage());
            }
        }

        System.out.println("Pemotongan file selesai.");
        scanner.close();
    }
}
