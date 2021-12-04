import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.lang.*;
import java.math.*;
import java.io.*;


public class GroupDH {
    public static KeyNode root;
    long p;
    long g;

    public static void main(String[] args)throws FileNotFoundException, IOException{
        File file=new File(args[0]);
        FileReader fr=new FileReader(file);   //reads the file
        BufferedReader br=new BufferedReader(fr);
        String line = br.readLine();
        String[] splitLine = line.split(" ");
        p = Long.parseLong(splitLine[0]);
        g = Long.parseLong(splitLine[1]);
        int count = Integer.parseInt(br.readLine());
        for(int i = 0;i<count;i++){
            line = br.readLine();
            splitLine = line.split(" ");
            if(i == 0){
                root = new KeyNode(null, null, null, 0, splitLine[4]);
                root.leftKid = new KeyNode(root, null, null, Integer.parseInt(splitLine[1]), splitLine[0]);
                root.rightKid = new KeyNode(root, null, null, Integer.parseInt(splitLine[3]), splitLine[2]);
                int a = root.leftKid.key;
                int b = root.rightKid.key;
                root.key = (int)((Math.pow(g, (a*b)))%p);
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
        KeyNode sponsor = recurseKeyNode(sponsorName, root);
        sponsor.key = newKey1;
        KeyNode newShare = new KeyNode(sponsor.parent, sponsor, null,0, keyID );
        sponsor.parent = newShare;
        newShare.rightKid = new KeyNode(newShare, null, null, newKey2, newUserName);
        int a = newShare.leftKid.key;
        int b = newShare.rightKid.key;
        root.key = (int)((Math.pow(g, (a*b)))%p);
    }
    public static void deleteUser(String SponsorName, int newKey){

    }
    public static void query(String searchName){
        System.out.println(recurseKeyNode(searchName, root).key);
    }
    private static KeyNode recurseKeyNode(String target, KeyNode pos){
        if(pos.name == target){
            return pos;
        }
        if(pos.leftKid != null)
            return recurseKeyNode(target, pos.leftKid);
        if(pos != null)
            return pos;
        if(pos.rightKid != null)
            return recurseKeyNode(target, pos.rightKid);

        return null;
    }
}

//this is the shared key nodes class
class KeyNode{
    public KeyNode parent, leftKid, rightKid;
    public String name;
    public int key;
    public KeyNode(KeyNode inputParent, KeyNode left, KeyNode right, int inputkey, String inputName){
        parent = inputParent;
        leftKid = left;
        rightKid = right;
        key = inputkey;
        name = inputName;
    }
}

