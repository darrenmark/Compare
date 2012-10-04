package compare;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main(String[] args) throws IOException {
        if(args.length < 2)  {
            System.out.println("compare.App <Path2Folder1> <Path2Folder2>");
            System.exit(0);
        }
        File folder1 = validateIsFolder(new File(args[0]));
        File folder2 = validateIsFolder(new File(args[1]));
        FileOutputStream newFiles = new FileOutputStream("New.txt");
        FileOutputStream modifiedFiles = new FileOutputStream("Modified.txt");
        new Compare(folder1, folder2, newFiles, modifiedFiles).process();
    }

    private static File validateIsFolder(File file) {
        if(!file.exists() || !file.isDirectory()) {
            System.out.println(file.getAbsolutePath() + " does not exists or is not a folder");
            System.exit(0);
        }
        return file;
    }
}
