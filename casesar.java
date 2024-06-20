package p;
import java.util.Scanner;
public class casesar {
    public static void main(String args[]){
        Scanner sc=new Scanner(System.in);
        System.out.print("Plane text: ");
        String planetext=sc.nextLine();
        System.out.print("Key: ");
        int key=sc.nextInt();

        String encrRes=encryption(planetext,key);
        System.out.println("Encryption: "+encrRes);
        String decrRes=decryption(encrRes,key);
        System.out.println("DEcryption: "+decrRes);
    }
    public static String encryption(String planetext,int key){
        String ciphertext="";
        for(int i=0;i<planetext.length();i++){
            char ch=planetext.charAt(i);
            if(Character.isLetter(ch)){
                char base=Character.isUpperCase(ch)?'A':'a';
                
                ch=(char)((ch-base+key)%26+base);
            }
            ciphertext+=ch;
        }
        return ciphertext;
    }
    public static String decryption(String ciphertext,int key){
        return encryption(ciphertext, 26-key);
    }
}
