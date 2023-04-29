import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class TaskMonthsEnums {

  // Расширьте программу Task2MonthsEnums. Добавьте ещё один enum, который будет описывать времена
  // года (зима, лето, весна, осень). Для введённого пользователем месяца напишите, к какому
  // именно времени года относится выбранный месяц. Усовершенствуйте программу так, чтобы число
  // дней в месяце читалось из файла res/months.csv и сохранялось в Map<Month, Integer>.
  public static void main(String[] args) throws IOException {
    HashMap<String, Integer> months = readingFile(new File("res/months.csv"));
    String parseMonth = parseMonth(months);
    Seasons parseSeason = parseSeason(parseMonth);
    System.out.print("In " + parseMonth + " - " + months.get(parseMonth) + " days, ");
    System.out.print("season - " + parseSeason);
  }

  private enum Seasons {
    WINTER,
    SPRING,
    SUMMER,
    AUTUMN,
  }

  private static final String SEP = ",";

  private static HashMap<String, Integer> readingFile(File file) throws IOException {
    HashMap<String, Integer> hashMap = new HashMap<>();
    if (file.exists()) {
      BufferedReader br = new BufferedReader(new FileReader(file));
      while (br.ready()) {
        String line = br.readLine();
        if (!line.isEmpty()) {
          int sepIndex = line.indexOf(SEP);
          if (sepIndex != -1) {
            String month = line.substring(0, sepIndex);
            int days = Integer.parseInt(line.substring(sepIndex + 1));
            hashMap.put(month, days);
          }
        }
      }
      br.close();
    }
    return hashMap;
  }

  private static String parseMonth(HashMap<String, Integer> hashMap) {
    Scanner scanner = new Scanner(System.in);
    System.out.print("\nEnter month: ");
    String answer = scanner.next();
    String answerUpperCase = answer.toUpperCase();
    scanner.nextLine();
    while (!hashMap.containsKey(answerUpperCase)) {
      System.out.println("Non-existent value. Try again");
      answer = scanner.next();
      answerUpperCase = answer.toUpperCase();
    }
    System.out.println("You entered the month: " + answer);
    return answerUpperCase;
  }

  private static Seasons parseSeason(String month) {
    return switch (month) {
      case "DECEMBER", "JANUARY", "FEBRUARY" -> Seasons.WINTER;
      case "MARCH", "APRIL", "MAY" -> Seasons.SPRING;
      case "JUNE", "JULY", "AUGUST" -> Seasons.SUMMER;
      case "SEPTEMBER", "OCTOBER", "NOVEMBER" -> Seasons.AUTUMN;
      default -> throw new IllegalStateException("Unexpected value: " + month);
    };
  }
}
