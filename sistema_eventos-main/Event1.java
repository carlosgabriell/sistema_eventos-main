public class Event1 {
    private String name;
    private String date;
    private String time;
    private String description;
    private String local;  // novo atributo

    public Event1(String name, String date, String time, String description, String local) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.description = description;
        this.local = local;  // atribuição do novo atributo
    }

    public String getName() { return name; }
    public String getDate() { return date; }
    public String getTime() { return time; }
    public String getDescription() { return description; }
    public String getLocal() { return local; }  // getter para local
}
