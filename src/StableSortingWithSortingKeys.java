import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StableSortingWithSortingKeys {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please input file path: ");
        Path filePath = Paths.get(sc.nextLine());
        System.out.println("Please input sorting type, \"year\" or \"identifier\": ");
        String sortQuery = sc.nextLine();

        Entry[] bibEntries = null;
        try {
            bibEntries = start(filePath, sortQuery);
        }
        catch(Exception e ){
            e.printStackTrace();
            System.exit(0);
        }
        createBibFile(bibEntries);

    }

    public static Entry[] start(Path filepath, String sortQuery)throws IOException {

        String fileContent = Files.readString(filepath);
        ArrayList<Entry> bibEntriesArrayList = new ArrayList<>();

        for (String rawEntry : fileContent.split("\n\\s*\n")){
            Entry entry = new Entry();
            entry.setText(rawEntry.trim());

            Pattern yearPattern = Pattern.compile("(?<=year=\\{)(\\d{4})", Pattern.CASE_INSENSITIVE);
            Pattern idPattern = Pattern.compile("(?<=.[^=]\\{).*(?=,)", Pattern.CASE_INSENSITIVE);

            Matcher yrMatcher = yearPattern.matcher(rawEntry);
            Matcher idMatcher = idPattern.matcher(rawEntry);
            if(yrMatcher.find()){
                entry.setYear(Integer.parseInt(yrMatcher.group()));
            }
            else{
                System.out.println("No year present, will default to 2021");
                entry.setYear(2021);
            }

            if(idMatcher.find()){ entry.setId(idMatcher.group());}
            else{
                entry.setId("z");
                System.out.println("Could not get identifier");
            }
            bibEntriesArrayList.add(entry);
        }
        Entry[] bibEntries = new Entry[bibEntriesArrayList.size()];
        for (int i=0; i<= bibEntriesArrayList.size()-1; i++){
            bibEntries[i]=bibEntriesArrayList.get(i);
        }
        if (sortQuery.equals("year")){MergeSort.sort(bibEntries, new Entry.byYear());}
        else if(sortQuery.equals("identifier")) {MergeSort.sort(bibEntries, new Entry.byID());}
        else{
            System.out.println("Invalid Sorting Query");
            System.exit(0);
        }
        return bibEntries;
    }
    public static void createBibFile(Entry[] bibEntries){ //https://www.w3schools.com/java/java_files_create.asp

        try {
            FileWriter myWriter = new FileWriter("output.bib");
            for (int i=0; i<=bibEntries.length-1; i++){
                myWriter.write(bibEntries[i].getText());
                myWriter.write("\r\n\n");
            }
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}

// C:\Users\david\Desktop\bug_triage.bib