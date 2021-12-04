import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.lang.*;
import java.math.*;
import java.io.*;


public class GroupDH {
    public static KeyNode root;

    public static void main(String[] args)throws FileNotFoundException, IOException{
        File file=new File(args[0]);
        FileReader fr=new FileReader(file);   //reads the file
        BufferedReader br=new BufferedReader(fr);
        String line = br.readLine();
        String[] splitLine = line.split(" ");
        BigInteger p = new BigInteger(splitLine[0]);
        BigInteger g = new BigInteger(splitLine[1]);
        int count = Integer.parseInt(br.readLine());
        for(int i = 0;i<count;i++){
            line = br.readLine();
            splitLine = line.split(" ");
            if(i == 0){
                root = new KeyNode(null, splitLine[4]);
                root.leftUser = new User(root, Integer.parseInt(splitLine[1]), splitLine[0]);
                root.rightUser = new User(root, Integer.parseInt(splitLine[3]), splitLine[2]);
            }else if(splitLine[0] == "ADD"){
                addUser(splitLine[1], Integer.parseInt(splitLine[2]), splitLine[3], Integer.parseInt(splitLine[4]), splitLine[5]);
            }else if(splitLine[0] == "DEL"){
                deleteUser(splitLine[1], Integer.parseInt(splitLine[2]));
            }else if(splitLine[0] == "QUERY"){
                query(splitLine[1]);
            }
        }


    }
    public static void addUser(String sponsorName, int newKey1, String newUserName, int newKey2, String keyID){

    }
    public static void deleteUser(String SponsorName, int newKey){

    }
    public static void query(String searchName){

    }
}


//this is the shared key nodes class
class KeyNode{
    public KeyNode parent, leftKid, rightKid;
    public User leftUser, rightUser;
    public String name;
    public KeyNode(KeyNode inputParent, String inputName){
        parent = inputParent;
        name = inputName;
    }
}

//this is the actual user node class
class User{
    public KeyNode parent;
    public int key;
    public String name;

    public User(KeyNode inputparent, int inputkey, String inputName){
        parent = inputparent;
        key = inputkey;
        name = inputName;
    }
}