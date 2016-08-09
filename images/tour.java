/**
 * Created by Маша on 28.09.2015.
 */
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.Vector;


public class tour {
    String name;
    String country;
    SimpleDateFormat first;
    SimpleDateFormat second;
    int price;
    int amount;

    tour(){}

    tour(String name,String country,SimpleDateFormat first,SimpleDateFormat second,int price,int amount){
        this.name=name;
        this.country=country;
        this.first=first;
        this.second=second;
        this.price=price;
        this.amount=amount;
    }

    void getData(PrintWriter o) throws IOException {
        o.print(name);
        o.print(" ");
        o.print(country);
        o.print(" ");
        o.print(first.toPattern());
        o.print(" ");
        o.print(first.toPattern());
        o.print(" ");
        o.print(price);
        o.print(" ");
        o.println(amount);
    }

    void print(){
        System.out.println(name+" "+country+" "+first.toPattern()+" "+second.toPattern()+" "+price+" "+amount);
    }

    String getInfo(){
        return name+" "+country+" "+first.toPattern()+" "+second.toPattern()+" "+price+" "+amount;
    }

    public static void main(String[] args) throws Exception{


        Scanner se = new Scanner(System.in);

        Scanner in = new Scanner(new InputStreamReader(new FileInputStream("tour.txt")));
        Vector<tour> arr = new Vector<tour>(); int i=0;
        while(in.hasNext()==true){
            String name = in.next();
            String country = in.next();
            SimpleDateFormat first = new SimpleDateFormat(in.next());
            SimpleDateFormat second = new SimpleDateFormat(in.next());
            int price = in.nextInt();
            int amount = in.nextInt();
            arr.add(new tour(name,country,first,second,price,amount));

            i++;
        }
        in.close();

        int ch=1; menu m = new menu();
        do{
            m.article();
            System.out.print("Ваш выбор: ");
            ch = se.nextInt();
            System.out.println();
            switch(ch){
                case 1:m.print(arr);
                    break;
                case 2:m.addTour(arr);
                    break;
                case 3:m.removeTour(arr);
                    break;
                case 4:m.setTour(arr);
                    break;
                case 5:m.sendTour(arr);
                    break;
                case 6:m.receiveTour(arr);
                    System.out.print("Вы получили тур: ");
                    break;
            }
            System.out.println();
        }while(ch>0&&ch<7);

        PrintWriter fo = new PrintWriter(new OutputStreamWriter(new FileOutputStream("tour.txt")));
        for(i = 0; i<arr.size();i++){
            arr.get(i).getData(fo);
        }
        fo.flush();
        fo.close();

    }
}
