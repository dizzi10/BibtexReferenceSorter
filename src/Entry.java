import java.util.Comparator;

public class Entry {
    private String id;
    private int year;
    private String text;

    public static class byID implements Comparator<Entry>{
        public int compare(Entry a, Entry b){return a.id.compareTo(b.id); }
    }
    public static class byYear implements Comparator<Entry>{
        public int compare(Entry a, Entry b){return a.year - b.year; }
    }
    public String getText(){return this.text;}

    public void setText(String text){this.text = text;}

    public void setYear(int year){this.year = year;}

    public void setId(String id){this.id = id;}
}
