import java.io.File;
import java.io.FilenameFilter;

public class im {
    public static void main(String[] args){
        File f = new File(args[0]);
        File[] matchingFiles = f.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith("lc");
            }           
        });
        for(File file: matchingFiles){
            interp.run(file);
        }
    }
}
