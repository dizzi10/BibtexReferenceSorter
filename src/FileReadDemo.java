import java.util.Scanner; 
import java.io.*;       
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
/**
   This program reads data from a file.
*/

public class FileReadDemo
{
   public static void main(String[] args) throws IOException
   {
       Pattern yearPattern = Pattern.compile("[0-9]+");//to find the year
       String filename="C:\\Users\\david\\Desktop\\bug_triage.bib";
       ArrayList<ArticleLibrary> articleLibraries= new ArrayList<ArticleLibrary>();
         // Open the file.
       File file = new File(filename);
       Scanner inputFile = new Scanner(file);
       String actual = Files.readString(Paths.get(filename));
       Scanner scanner = new Scanner(actual).useDelimiter("@");
       while (scanner.hasNext()) {
            ArticleLibrary aLibrary= new ArticleLibrary();
            String paragraph = scanner.next();
            String [] lines=paragraph.split(",");
//            System.out.println(paragraph);
//            System.out.println("//");
            
            //Find the year
            String year="";
            Matcher yearMatch = yearPattern.matcher(lines[0]);
            if (yearMatch.find())
            {
               year = yearMatch.group();
              System.out.println(year);
            }  
              
            //Find the name  
            int yearIndex = lines[0].indexOf(year);
            int nameIndex=lines[0].indexOf("{");
            String name=lines[0].substring(nameIndex+1,yearIndex);
            aLibrary.name=name;
            aLibrary.year=Integer.parseInt(year);
            aLibrary.para=paragraph;
            articleLibraries.add(aLibrary);
         }
       inputFile.close();
       MergeSoftClass msc= new MergeSoftClass();
       msc.mergeSort(articleLibraries);
   }
   
    public static <T extends Comparable<T>> ArrayList<T> mergeSort(ArrayList<T> m)
        {
            // exception
           // if (m==null) throw new NoSuchElementException("List is null");
            // base case
            if (m.size() <= 1) return m;

            // make lists
            ArrayList<T> left = new ArrayList<>();
            ArrayList<T> right = new ArrayList<>();

            // get middle
            int middle = m.size()/2;

            // fill left list
            for (int i = 0; i < middle; i++)
            {
                if (m.get(i)!=null) left.add(m.get(i));
            }

            // fill right list
            for (int i = middle; i < m.size(); i++)
            {
                if (m.get(i)!=null) right.add(m.get(i));
            }

            // recurse
            left = mergeSort(left);
            right = mergeSort(right);

            // merge
            return merge(left,right);
        }
        
   private static <T extends Comparable<T>> ArrayList<T> merge(ArrayList<T> left, ArrayList<T> right)
        {
            ArrayList<T> result = new ArrayList<>();

            // merge
            while (!left.isEmpty() && !right.isEmpty())
            {
                if (left.get(0).compareTo(right.get(0)) <= 0)
                {
                    result.add(left.remove(0));
                }
                else
                {
                    result.add(right.remove(0));
                }
            }

            // cleanup leftovers
            while (!left.isEmpty())
            {
                result.add(left.remove(0));
            }
            while (!right.isEmpty())
            {
                result.add(right.remove(0));
            }
            return result;
        }

    }
   


 class ArticleLibrary
{
 public  String name;
 public  int year;
 public  String para;

 public static class ByName  
 {
 public int compare(ArticleLibrary v, ArticleLibrary w)
 { return v.name.compareTo(w.name); }
 }
 public static class ByYear   {
 public int compare(ArticleLibrary v, ArticleLibrary w)
 { return v.year - w.year; }
 }
}

class MergeSoftClass
{

 public static void mergeSort(ArrayList<ArticleLibrary> list) {
        ArrayList<ArticleLibrary> helper = new ArrayList<ArticleLibrary>();
        mergeSort(list, helper, 0, list.size()-1);
    }

    private static void mergeSort(ArrayList<ArticleLibrary> list, ArrayList<ArticleLibrary> helper, int low, int high) {
        if(low < high) {
            int middle = (low+high)/2;
            mergeSort(list, helper, low, middle); //sort left half
            mergeSort(list, helper, middle+1, high); //sort right half
            merge(list, helper, low, middle, high); // merge
        }
    }

    private static void merge(ArrayList<ArticleLibrary> list, ArrayList<ArticleLibrary> helper, int low, int middle, int high) {
        //This loop throws Exception
        for(int i=low; i< high + 1; i++) {
            helper.add(i, list.get(i));
        }

        int helperLeft = low;
        int helperRight = middle + 1;
        int current = low;

        /**
         * Iterate through helper array, copying back smaller element in the original list 
         */
        while(helperLeft < middle && helperRight < high) {
         //if(helper.get(helperLeft)<( helper.get(helperRight)))
            if(true)
             {
                list.set(current, helper.get(helperLeft));
                helperLeft++;
            } else {
                list.set(current, helper.get(helperRight));
                helperRight++;
            }
            current++;
        }

        //Copy remaining elements
        int remaining = middle - helperLeft;
        for(int j=0; j <= remaining; j++) {
            list.set(current+j, helper.get(helperLeft+j));
        }

    }
    }