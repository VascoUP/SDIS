
public class TestApp {

    public static void main(String[] args) {

    }

    public static boolean parseArgs(String[] args) {
        switch(args[2]) {
            case "BACKUP":
                parseBackUp(args);
                break;
            case "RESTORE":
                parseRestore(args);
                break;
        }
    }

    public static boolean parseBackUp(String[] args) {
        if( args.length == 5 )
    }

    public static boolean parseRestore(String[] args) {

    }
}