package arithmatic_coding;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class compression {


    public String comp(String data) throws FileNotFoundException {
        ArrayList<node> p = new ArrayList<>();
        ArrayList<Character> sym = new ArrayList<>();
        ArrayList<Double> prob = new ArrayList<>();
        double count = 0.0;
        sym.add(data.charAt(0));
        for (int i = 0; i < data.length(); i++) {
            boolean check = true;
            char x = data.charAt(i);
            if (i != 0) {
                for (int j = 0; j < sym.size(); j++) {
                    if (x == sym.get(j)) {
                        check = false;
                        break;
                    }
                }
                if (check == true && i != 0) {
                    sym.add(x);
                }
            }
            if (sym.size() != prob.size()) {
                for (int n = i; n < data.length(); n++) {
                    if (x == data.charAt(n)) {
                        count++;
                    }
                }
                prob.add(count / data.length());
                count = 0;
            }
        }
        for (int i = 0; i < sym.size(); i++) {

            for (int j = i + 1; j < sym.size(); j++) {

                if (sym.get(j) < sym.get(i)) {

                    char temp = sym.get(i);
                    sym.set(i, sym.get(j));
                    sym.set(j, temp);

                    double d = prob.get(i);
                    prob.set(i, prob.get(j));
                    prob.set(j, d);
                }
            }
        }
        double sum = 0;
        for (int i = 0; i < sym.size(); i++) {
            node n = new node();
            n.symbol = sym.get(i);
            n.low_p = sum;
            sum = sum + prob.get(i);
            n.high_p = sum;
            p.add(n);
        }
        double low, high, dif_h_l, s_v, rondom;
        int x = sym.indexOf(data.charAt(0));
        low = p.get(x).low_p;
        high = p.get(x).high_p;

        for (int i = 1; i < data.length(); i++) {

            dif_h_l = high - low;
            x = sym.indexOf(data.charAt(i));
            s_v = low;
            low = s_v + dif_h_l * p.get(x).low_p;
            high = s_v + dif_h_l * p.get(x).high_p;


        }
        rondom = (high + low) / 2;
        String rond = "";
        rond += Double.toString(rondom);
        PrintWriter write = new PrintWriter("compressed.txt");
        PrintWriter write1 = new PrintWriter("symbol.txt");
        PrintWriter write2 = new PrintWriter("high_prob.txt");
        PrintWriter write3 = new PrintWriter("low_prob.txt");
        PrintWriter write4 = new PrintWriter("data_size.txt");
        String s1="";
        String s2="";
        String s3="";
        String s4="";
        s4+=Integer.toString(data.length());
        write.print(rond);
        for(int i=0;i<p.size();i++){
            s1+=p.get(i).symbol;
            s2+=p.get(i).high_p;
            s2+="|";
            s3+=p.get(i).low_p;
            s3+="|";
        }
        write1.print(s1);
        write2.print(s2);
        write3.print(s3);
        write4.print(s4);

        write.close();
        write1.close();
        write2.close();
        write3.close();
        write4.close();
        return rond;
    }

    public String decomp() throws FileNotFoundException {
        ArrayList<node> p = new ArrayList<>();
        ArrayList<Double> l = new ArrayList<>();
        ArrayList<Double> h= new ArrayList<>();
        File f = new File("compressed.txt");
        File f1 = new File("symbol.txt");
        File f2 = new File("low_prob.txt");
        File f3 = new File("high_prob.txt");
        File f4 = new File("data_size.txt");
        String text = "";
        String s1="";
        String s2="";
        String s3="";
        String s4="";
        try (Scanner in = new Scanner(f)) {


            while (in.hasNextLine()) {
                text += in.nextLine();
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try (Scanner in_s = new Scanner(f4)) {


            while (in_s.hasNextLine()) {
                s4 += in_s.nextLine();
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try (Scanner in1 = new Scanner(f1)) {


            while (in1.hasNextLine()) {
                s1 += in1.nextLine();
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try (Scanner in2 = new Scanner(f2)) {


            while (in2.hasNextLine()) {

                s2 += in2.nextLine();

            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try (Scanner in3 = new Scanner(f3)) {


            while (in3.hasNextLine()) {
                s3+=in3.nextLine();
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

            int j=0;
            String m = "";
            while (j<s2.length()-1){
                if(s2.charAt(j)=='|') {
                    double y = Double.parseDouble(m);
                    l.add(y);
                    m = "";
                    j++;
                }

                m += s2.charAt(j);
                j++;
            }
        double y = Double.parseDouble(m);
        l.add(y);
        j=0;m="";
        while (j<s3.length()-1){
            if(s3.charAt(j)=='|') {
                 y = Double.parseDouble(m);
                h.add(y);
                m = "";
                j++;
            }

            m += s3.charAt(j);
            j++;
        }
         y = Double.parseDouble(m);
        h.add(y);
        int data_size=Integer.parseInt(s4);
        double number=Double.parseDouble(text);
            for (int i=0;i<s1.length();i++){
                node n=new node();
                p.add(n);
                n.symbol=s1.charAt(i);
                n.low_p=l.get(i);
                n.high_p=h.get(i);
            }
            double low,high,code,temporary_var,z,w;
        low=high=code=z=w=0;
        String data="";
            for(int i=0;i<p.size();i++){
                if(number<p.get(i).high_p &&number>p.get(i).low_p){
                    data+=p.get(i).symbol;

                    low=p.get(i).low_p;
                    high=p.get(i).high_p;
                    break;
                }
            }
        for(int q=1;q<data_size;q++){
                double r=high-low;
                code=(number-low)/r;
            for(int i=0;i<p.size();i++){
                if(code<p.get(i).high_p &&code>p.get(i).low_p){
                    data+=p.get(i).symbol;
                    z=p.get(i).high_p;
                    w=p.get(i).low_p;
//                    break;
                }
            }
            temporary_var=low;
            low=temporary_var+r*w;
            high=temporary_var+r*z;
                }

        try (PrintWriter write_data = new PrintWriter("decompressed.txt")) {
            write_data.print(data);
            write_data.close();
        }

        return data;
    }


    public static void main(String[] args) throws FileNotFoundException {
        gui obj = new gui();
        obj.setVisible(true);
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        compression x=new compression();
//        File f=new File("reading.txt");
//        Scanner in = new Scanner(f);
//        String data="";
//        while(in.hasNextLine())
//        {
//            data+=in.nextLine();
//        }
//        x.comp(data);
//        x.decomp();


    }
}
