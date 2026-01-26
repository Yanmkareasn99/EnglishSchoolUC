public class Teacher {
    private int id;
    private String name;

    public Teacher(int id, String name){
        this.id = id; this.name =  name;
    }

    public String toCsv() {
        return id + "," + name + "\n";
    }
}
