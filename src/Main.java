/** 
* 
* @author Şimal Ece Kazdal simal.kazdal@ogr.sakarya.edu.tr 
* @since 16.11.2024
* <p> 
*  Tüm işlemlerin yapıldığı yer
* </p> 
*/ 

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Lütfen analiz edilecek dosya yolunu girin:");
        String filePath = scanner.nextLine();

        File file = new File(filePath);
        if (!file.exists() || file.isDirectory()) {
            System.err.println("Geçersiz dosya yolu!");
            return;
        }

        DosyaAnalizEt(file); 
    }

    public static void DosyaAnalizEt(File file) {
        int tekliOperator = 0, ikiliOperator = 0, ucluOperator = 0;

        // Operatörler
        String[] ucluOps = {"?:", "?"}; // Üçlü operatörler
        String[] ikiliOps = {"<<=", ">>=", "==", "!=", "<=", ">=", "&&", "||", "+=", "-=", "*=", "/=", "%=", "&=", "|=", "^=", "<<", ">>", ">", "<", "+", "-", "*", "/", "%", "&", "|", "^", "="}; // İkili operatörler
        String[] tekliOps = {"++", "--", "!", "~"}; // Tekli operatörler

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = temizle(line); // Yorumları ve string ifadeleri kaldır

                StringBuilder lineBuilder = new StringBuilder(line); // Satırı düzenleyebilmek için

                int i = 0;
                while (i < lineBuilder.length()) {
                    if (i < lineBuilder.length() - 1 && lineBuilder.charAt(i) == '!' && lineBuilder.charAt(i + 1) == '=') {
                        ikiliOperator++; // != operatörü
                        lineBuilder.delete(i, i + 2); // !='i kaldır
                    } else if (i < lineBuilder.length() - 1 && lineBuilder.charAt(i) == '&' && lineBuilder.charAt(i + 1) == '=') {
                        ikiliOperator++; // &= operatörü
                        lineBuilder.delete(i, i + 2); // &='i kaldır
                    } else if (i < lineBuilder.length() - 1 && lineBuilder.charAt(i) == '&' && lineBuilder.charAt(i + 1) == '&') {
                        ikiliOperator++; // && operatörü
                        lineBuilder.delete(i, i + 2); // &&'i kaldır
                    } else if (i < lineBuilder.length() - 1 && lineBuilder.charAt(i) == '|' && lineBuilder.charAt(i + 1) == '|') {
                        ikiliOperator++; // || operatörü
                        lineBuilder.delete(i, i + 2); // ||'i kaldır
                    } else if (i < lineBuilder.length() - 1 && lineBuilder.charAt(i) == '=' && lineBuilder.charAt(i + 1) == '=') {
                        ikiliOperator++; // == operatörü
                        lineBuilder.delete(i, i + 2); // =='i kaldır
                    } else if (i < lineBuilder.length() - 1 && lineBuilder.charAt(i) == '+' && lineBuilder.charAt(i + 1) == '+') {
                        tekliOperator++; // ++ operatörü
                        lineBuilder.delete(i, i + 2); // ++'i kaldır
                    } else if (i < lineBuilder.length() - 1 && lineBuilder.charAt(i) == '-' && lineBuilder.charAt(i + 1) == '-') {
                        tekliOperator++; // -- operatörü
                        lineBuilder.delete(i, i + 2); // --'i kaldır
                    } else if (i < lineBuilder.length() - 1 && lineBuilder.charAt(i) == '-' && lineBuilder.charAt(i + 1) == '=') {
                        ikiliOperator++; // -= operatörü
                        lineBuilder.delete(i, i + 2); // -='i kaldır
                    } else if (i < lineBuilder.length() - 1 && lineBuilder.charAt(i) == '^' && lineBuilder.charAt(i + 1) == '=') {
                        ikiliOperator++; //^= operatörü
                        lineBuilder.delete(i, i + 2); // ^='i kaldır 
                    } else if (i < lineBuilder.length() - 1 && lineBuilder.charAt(i) == '|' && lineBuilder.charAt(i + 1) == '=') {
                        ikiliOperator++; //|= operatörü
                        lineBuilder.delete(i, i + 2); // |='i kaldır 
                    } else if (i < lineBuilder.length() - 1 && lineBuilder.charAt(i) == '+' && lineBuilder.charAt(i + 1) == '=') {
                        ikiliOperator++; // += operatörü
                        lineBuilder.delete(i, i + 2); // +='i kaldır
                    } else if (i < lineBuilder.length() - 1 && lineBuilder.charAt(i) == '*' && lineBuilder.charAt(i + 1) == '=') {
                        ikiliOperator++; // *= operatörü
                        lineBuilder.delete(i, i + 2); // *='i kaldır
                    
                    } else if (i < lineBuilder.length() - 1 && lineBuilder.charAt(i) == '&' && lineBuilder.charAt(i + 1) == '&') {
                        if (i < lineBuilder.length() - 2 && lineBuilder.charAt(i + 2) == '=') {
                            ikiliOperator++; // &&= operatörü
                            lineBuilder.delete(i, i + 3);
                        }
                    } else if (i < lineBuilder.length() - 1 && lineBuilder.charAt(i) == '<' && lineBuilder.charAt(i + 1) == '<') {
                        if (i < lineBuilder.length() - 2 && lineBuilder.charAt(i + 2) == '=') {
                            ikiliOperator++; // <<= operatörü
                            lineBuilder.delete(i, i + 3);
                        } else {
                            ikiliOperator++; // << operatörü
                            lineBuilder.delete(i, i + 2);
                        }
                    } else if (i < lineBuilder.length() - 1 && lineBuilder.charAt(i) == '>' && lineBuilder.charAt(i + 1) == '>') {
                        if (i < lineBuilder.length() - 2 && lineBuilder.charAt(i + 2) == '=') {
                            ikiliOperator++; // >>= operatörü
                            lineBuilder.delete(i, i + 3);
                        } else {
                            ikiliOperator++; // >> operatörü
                            lineBuilder.delete(i, i + 2);
                        }
                    } else {
                        boolean matched = false;
                        for (String op : ikiliOps) {
                            if (lineBuilder.substring(i).startsWith(op)) {
                                ikiliOperator++;
                                lineBuilder.delete(i, i + op.length()); // İkili operatörü kaldır
                                matched = true;
                                break;
                            }
                        }
                        if (!matched) {
                            for (String op : tekliOps) {
                                if (lineBuilder.substring(i).startsWith(op)) {
                                    tekliOperator++;
                                    lineBuilder.delete(i, i + op.length()); // Tekli operatörü kaldır
                                    break;
                                }
                            }
                        }
                        i++;
                    }
                }


                // Üçlü operatörleri say
                for (String op : ucluOps) {
                    ucluOperator += countOccurrences(lineBuilder.toString(), op);
                    lineBuilder = new StringBuilder(lineBuilder.toString().replace(op, " ")); // Üçlü operatörleri kaldır
                }
            }
        } catch (IOException e) {
            System.err.println("Hata oluştu: " + e.getMessage());
        }

        // Sonuçları yazdır
        System.out.println("Tekli Operatör Sayısı: " + tekliOperator);
        System.out.println("İkili Operatör Sayısı: " + ikiliOperator);
        System.out.println("Üçlü Operatör Sayısı: " + ucluOperator);
    }

    // Belirli bir stringde bir operatörün kaç kez geçtiğini bulur
    public static int countOccurrences(String line, String op) {
        int count = 0, index = 0;
        while ((index = line.indexOf(op, index)) != -1) {
            count++;
            index += op.length();
        }
        return count;
    }

    // Yorumları ve string ifadelerini temizler
    public static String temizle(String line) {
        // Tek satırlık yorumları temizle
        line = line.replaceAll("//.*", "");
        // Çok satırlı yorumları temizle
        line = line.replaceAll("/\\*.*?\\*/", "");
        // Çift tırnak içindeki string ifadeleri temizle
        line = line.replaceAll("\"(\\\\.|[^\"\\\\])*\"", "");
        // Tek tırnak içindeki karakterleri temizle
        line = line.replaceAll("'(\\\\.|[^'\\\\])*'", "");
        return line;
    }
}
