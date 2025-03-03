import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;
import java.util.Scanner;

public class TaiXiuGame {
    private static final int TAI = 1; // Tài
    private static final int XIU = 2; // Xỉu
    private static double changeProbability; // Xác suất thay đổi kết quả

    public static void main(String[] args) {
        loadConfig();

        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        System.out.println("Chào mừng bạn đến với trò chơi Tài Xỉu!");
        System.out.println("1: Tài (11-18)");
        System.out.println("2: Xỉu (3-10)");
        System.out.print("Chọn Tài hoặc Xỉu: ");
        int playerChoice = scanner.nextInt();

        int dice1 = random.nextInt(6) + 1;
        int dice2 = random.nextInt(6) + 1;
        int dice3 = random.nextInt(6) + 1;
        int sum = dice1 + dice2 + dice3;

        System.out.println("Kết quả xúc xắc gốc: " + dice1 + ", " + dice2 + ", " + dice3);
        System.out.println("Tổng điểm gốc: " + sum);

        // Thay đổi kết quả dựa trên xác suất từ tệp cấu hình
        if (random.nextDouble() < changeProbability) {
            sum = changeResult(sum);
            System.out.println("Kết quả đã bị thay đổi!");
        }

        System.out.println("Tổng điểm cuối cùng: " + sum);

        if ((sum >= 11 && sum <= 18 && playerChoice == TAI) || (sum >= 3 && sum <= 10 && playerChoice == XIU)) {
            System.out.println("Bạn đã thắng!");
        } else {
            System.out.println("Bạn đã thua! Thử lại lần sau.");
        }

        scanner.close();
    }

    private static void loadConfig() {
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream("config.properties")) {
            properties.load(input);
            changeProbability = Double.parseDouble(properties.getProperty("changeProbability", "0.1"));
        } catch (IOException e) {
            e.printStackTrace();
            changeProbability = 0.1; // Mặc định xác suất thay đổi là 10%
        }
    }

    private static int changeResult(int originalSum) {
        Random random = new Random();
        int newSum;

        // Nếu tổng gốc là Tài, thay đổi thành Xỉu
        if (originalSum >= 11 && originalSum <= 18) {
            newSum = random.nextInt(8) + 3; // Tạo số ngẫu nhiên từ 3 đến 10
        } else {
            // Nếu tổng gốc là Xỉu, thay đổi thành Tài
            newSum = random.nextInt(8) + 11; // Tạo số ngẫu nhiên từ 11 đến 18
        }

        return newSum;
    }
}
