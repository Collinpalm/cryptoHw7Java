import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.*;
import java.io.*;
/*
*Fall 2021 CIS 3362
* Collin Palm
* Professor: Arup guha
*
* This is meant to be a group diffie hellman solution
* but for some reason I cant do basic math
* IDK if its data types or Im just a moron.
* Lets be honest... its probably the latter.
 */
public class GroupDH {
    public static KeyNode root;
    public static boolean flag = false;
    public static int p;
    public static int g;

    public static void main(String[] args)throws FileNotFoundException, IOException{
        //get the file obj set up
        File file=new File(args[0]);
        FileReader fr=new FileReader(file);   //reads the file
        BufferedReader br=new BufferedReader(fr);
        //read in the header info
        String line = br.readLine();
        String[] splitLine = line.split(" ");
        p = Integer.parseInt(splitLine[0]);
        g = Integer.parseInt(splitLine[1]);
        int count = Integer.parseInt(br.readLine());
        //main loop to run through all the commands
        for(int i = 0;i<count;i++){
            //get the next line
            line = br.readLine();
            splitLine = line.split(" ");
            //if its the first command, handle the special case
            if(i == 0){
                root = new KeyNode(null, null, null, 0, splitLine[4]);
                root.leftKid = new KeyNode(root, null, null, Integer.parseInt(splitLine[1]), splitLine[0]);
                root.rightKid = new KeyNode(root, null, null, Integer.parseInt(splitLine[3]), splitLine[2]);
                int a = root.leftKid.key;
                int b = root.rightKid.key;
                root.key = ((int)(Math.pow(g, (a*b)))%p);
            //check the three commands
            }else if(splitLine[0].equals("ADD")){
                addUser(splitLine[1], Integer.parseInt(splitLine[2]), splitLine[3], Integer.parseInt(splitLine[4]), splitLine[5]);
            }else if(splitLine[0].equals("DEL")){
                deleteUser(splitLine[1], Integer.parseInt(splitLine[2]));
            }else if(splitLine[0].equals("QUERY")){
                query(splitLine[1]);

            }
        }


    }
    //creates the new user and then reshuffles the tree and recalculates the keys where necessary
    public static void addUser(String sponsorName, int newKey1, String newUserName, int newKey2, String keyID){
        KeyNode sponsor = recurseKeyNode(sponsorName, root);
        sponsor.key = newKey1;
        KeyNode newShare = new KeyNode(sponsor.parent, sponsor, null,0, keyID );
        if(sponsor.parent.leftKid == sponsor){
            sponsor.parent.leftKid = newShare;
        }else if(sponsor.parent.rightKid == sponsor) {
            sponsor.parent.rightKid = newShare;
        }
        sponsor.parent = newShare;
        newShare.rightKid = new KeyNode(newShare, null, null, newKey2, newUserName);
        int a = newShare.leftKid.key;
        int b = newShare.rightKid.key;
        newShare.key = (int)((Math.pow(g, (a*b)))%p);
        while(newShare.parent != null){
            newShare = newShare.parent;
            a = newShare.leftKid.key;
            b = newShare.rightKid.key;
            newShare.key = (int)((Math.pow(g, (a*b)))%p);
        }
    }
    //deletes the user and then reshuffles/recalculates the keys when necessary
    public static void deleteUser(String targetName, int newKey){
        KeyNode survivor = recurseKeyNode(targetName, root);
        if(survivor.parent.leftKid == survivor && survivor.parent.parent.leftKid == survivor.parent){
            survivor.parent.parent.leftKid = survivor.parent.rightKid;
            survivor = survivor.parent.rightKid;
        }else if(survivor.parent.rightKid == survivor && survivor.parent.parent.leftKid == survivor.parent){
            survivor.parent.parent.leftKid =survivor.parent.leftKid;
            survivor = survivor.parent.leftKid;
        }else if(survivor.parent.leftKid == survivor && survivor.parent.parent.rightKid == survivor.parent){
            survivor.parent.parent.leftKid = survivor.parent.rightKid;
            survivor = survivor.parent.rightKid;
        }else if(survivor.parent.rightKid == survivor && survivor.parent.parent.rightKid == survivor.parent){
            survivor.parent.parent.leftKid = survivor.parent.leftKid;
            survivor = survivor.parent.leftKid;
        }
        int a, b;
        KeyNode newShare = survivor;
        while(newShare.parent != null){
            newShare = newShare.parent;
            a = newShare.leftKid.key;
            b = newShare.rightKid.key;
            newShare.key = (int)((Math.pow(g, (a*b)))%p);
        }
    }
    //handler for the recursive query method
    public static void query(String searchName){
        KeyNode result = recurseKeyNode(searchName, root);
        System.out.println(result.key);
    }
    //recursivly checks the tree for the correct node
    private static KeyNode recurseKeyNode(String target, KeyNode pos){
        if(pos != null) {
            if (pos.name.equals(target)) {
                return pos;
            }else{
                KeyNode temp = recurseKeyNode(target, pos.leftKid);
                if(temp == null){
                    temp = recurseKeyNode(target, pos.rightKid);
                }
                return temp;
            }
        }

        return null;
    }
}

//this is the shared key nodes class that stores all the data I need
class KeyNode{
    public KeyNode parent, leftKid, rightKid;
    public String name;
    public int key;
    //constructor
    public KeyNode(KeyNode inputParent, KeyNode left, KeyNode right, int inputkey, String inputName){
        parent = inputParent;
        leftKid = left;
        rightKid = right;
        key = inputkey;
        name = inputName;
    }
}

