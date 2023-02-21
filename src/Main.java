import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Main {
    public static void main(String[] args) {
        String currentDir = "D:/Games/savegames/";

        // Произвести распаковку архива в папке savegames
        openZip(currentDir + "zip.zip", currentDir);

        //Произвести считывание и десериализацию одного из разархивированных файлов save.dat
        GameProgress gameProgress = openProgress(currentDir + "save2.dat");

        //Вывести в консоль состояние сохранненой игры
        System.out.println(gameProgress);
    }

    public static void openZip(String z, String d) {
        try (ZipInputStream zin = new ZipInputStream(new FileInputStream(z))) {
            ZipEntry entry;
            String name;
            while ((entry = zin.getNextEntry()) != null) {
                name = entry.getName();
                try (FileOutputStream fout = new FileOutputStream(d + name)) {
                    for (int с = zin.read(); с != -1; с = zin.read()) {
                        fout.write(с);
                    }
                    fout.flush();
                    zin.closeEntry();
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static GameProgress openProgress(String f) {
        GameProgress gameProgress = null;
        try (FileInputStream fis = new FileInputStream(f);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            gameProgress = (GameProgress) ois.readObject();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return gameProgress;
    }
}