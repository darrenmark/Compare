package compare;

import com.twmacinta.util.MD5;
import org.apache.commons.io.FileUtils;

import java.io.*;

/**
 */
public class Compare {

    private File baseFolder1 = null;
    private File baseFolder2 = null;
    OutputStream newFilesOutputStream;
    OutputStream modifiedFilesOutputStream;

    public Compare(File baseFolder1, File baseFolder2, OutputStream newFilesOutputStream, OutputStream modifiedFilesOutputStream) {
        this.baseFolder1 = baseFolder1;
        this.baseFolder2 = baseFolder2;
        this.newFilesOutputStream = newFilesOutputStream;
        this.modifiedFilesOutputStream = modifiedFilesOutputStream;
    }

    public void process() {
        process(baseFolder1);
    }
    private void process(File folder) {
        for(File file: folder.listFiles()) {
            String relativePath = file.getAbsolutePath().substring(baseFolder1.getAbsolutePath().length());
            File otherFile = new File(baseFolder2.getAbsolutePath() + File.separator +  relativePath);
            if(file.isFile()) {
                if(!otherFile.exists()) {
                    writeToStream(newFilesOutputStream, file.getAbsolutePath());
                } else {
                   checkMd5(file, otherFile);
                }
            } else {
                if(!otherFile.exists()) {
                    writeToStream(newFilesOutputStream, file.getAbsolutePath());
                }
                 process(file);
            }

        }

    }

    private void checkMd5(File file1, File file2) {
        if(!getMD5(file1).equals(getMD5(file2))) {
            writeToStream(modifiedFilesOutputStream, file1.getAbsolutePath());
        }
    }

    private String getMD5(File file) {
        try {
        MD5 md5 = new MD5();
        md5.Update(FileUtils.readFileToByteArray(file));
        return md5.asHex();
        }catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void writeToStream(OutputStream fileOutputStream, String data) {
        PrintWriter printWriter = new PrintWriter(fileOutputStream);
        printWriter.println(data);
        printWriter.flush();
    }
}
