import java.io.*;
import java.util.*;

class interp{
    public static void run(File fn) {

        File f = fn;
        ArrayList<String> prog = new ArrayList<String>();
        ArrayList<String> vars = new ArrayList<String>();

        try(Scanner reader = new Scanner(f);){


            String fline = reader.nextLine();
            String name = fline.split(":")[0];

            for(String s: fline.split(":")[1].split(",")){
                vars.add(s);
            }

            while(reader.hasNextLine()){
                prog.add(reader.nextLine());
            }
            String code = "public class " + name + "{private static int nL="+prog.size()+";";
            code+="\n   private static String[] varNames = {";
            for(int i=0;i<vars.size();i++){
                code+= "\"" + vars.get(i) + "\", ";
            }
            code = code.substring(0,code.length()-2);
            code += "};";
            
            code+="\n   public static int getnL(){return nL;}";
            code+="\n   public static String[] getNames(){return varNames;}";
            for(int i=0;i<prog.size();i++){
                code+="\n   public static Boolean func"+i+"(";
                for(String h: vars){
                    code+= ("Boolean "+h+", ");
                }
                code = code.substring(0,code.length()-2);
                code += "){return "+parseProg(prog.get(i))+";}\n";
            }
            code+="\n}";
            
            

            String fPath = f.getParentFile().getAbsolutePath();
            String filename = fPath+"\\compiled\\"+name + ".java";

            final File tempfile = new File(filename);
            final File parent_directory = tempfile.getParentFile();

            if (null != parent_directory){
                parent_directory.mkdirs();
            }

            FileWriter writer = new FileWriter(tempfile);
            writer.write(code);
            writer.close();
        }
        catch(Exception e){e.printStackTrace();}

        

    }

    public static int[] findParentheses(String s, int c){
        int[] pIndeces = new int[2];
        int sum = 0;
        int i=c;
        while(i>0){
            i--;
            if(s.charAt(i)==')'|| s.charAt(i)==']'){sum++;}
            if(s.charAt(i)=='('|| s.charAt(i)=='['){sum--;}
            if(sum<0){break;}
            if(i==0){sum--;i--;}
        }
        pIndeces[0]=i;
        sum = 0;
        i=c;
        while(i<s.length()-1){
            i++;
            if(s.charAt(i)==')' || s.charAt(i)==']'){sum--;}
            if(s.charAt(i)=='(' || s.charAt(i)=='['){sum++;}
            if(sum<0){break;}
            if(i==s.length()-1){sum--;i++;}
        }
        pIndeces[1]=i;
        return pIndeces;
    }

    public static String[] splitNotInBrackets(String s){
        for(int i=0;i<s.length();i++){
            if(s.charAt(i)==','){
                int num = findParentheses(s, i)[0];
                if(num!=-1 && s.charAt(num)=='['){
                    s = s.substring(0,i)+"|"+s.substring(i+1);
                }
            }
        }
        String[] data = s.split(",");
        for(int i=0;i<data.length;i++){
            for(int j=0;j<data[i].length();j++){
                if(data[i].charAt(j)=='|'){data[i]=(data[i].substring(0,j)+","+data[i].substring(j+1));}
            }
        }
        return data;
    }

    public static String parseProg(String p){
        for(int i=0;i<p.length();i++){
            if(p.charAt(i)==','){
                int[] bounds = findParentheses(p, i);
                if(bounds[0]==-1){
                    if(bounds[1]==p.length()){p = "!(" + p.substring(0,i)+" && " + p.substring(i+1) + ")";}
                    else{p = "!(" + p.substring(0,i)+" && " + p.substring(i+1,bounds[1]) + ")"+p.substring(bounds[1]);}
                }else{
                    if(bounds[1]==p.length()){p = p.substring(0,bounds[0]+1)+ "!(" + p.substring(bounds[0]+1, i) +" && " + p.substring(i+1) + ")";}
                    else{
                        if(p.charAt(bounds[0])=='['){
                            String[] info = splitNotInBrackets(p.substring(bounds[0]+1,bounds[1]));
                            String newP = p.substring(0,bounds[0]) + info[0]+".func"+info[1]+"(";
                            for(int m=2;m<info.length;m++){
                                newP+=info[m];
                                if(m!=info.length-1){newP+="|";}
                            }
                            newP+=")"+p.substring(bounds[1]+1);
                            p=newP;
                            continue;
                        }
                        p = p.substring(0,bounds[0]+1)+ "!(" + p.substring(bounds[0]+1, i) +" && " + p.substring(i+1,bounds[1]) + ")"+p.substring(bounds[1]);}
                            
                    }
            }
        }
        for(int i=0;i<p.length();i++){
            if(p.charAt(i)=='|'){p=p.substring(0,i)+","+p.substring(i+1);}
        }
        return p;
    }
}