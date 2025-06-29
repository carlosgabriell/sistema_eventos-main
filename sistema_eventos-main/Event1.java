public class Event1 {
    private int id;
    private String name;
    private String date;
    private String time;
    private String description;
    private String local;
    private String bannerPath;
    private String category;

    public Event1(int id, String name, String date, String time, String description, String local, String bannerPath, String category) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
        this.description = description;
        this.local = local;
        this.bannerPath = bannerPath;
        this.category = category;
    }

    // Construtor auxiliar caso categoria não seja informada (para compatibilidade)
    public Event1(int id, String name, String date, String time, String description, String local, String bannerPath) {
        this(id, name, date, time, description, local, bannerPath, "");
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getDate() { return date; }
    public String getTime() { return time; }
    public String getDescription() { return description; }
    public String getLocal() { return local; }
    public String getBannerPath() { return bannerPath; }
    public String getCategory() { return category; }

    
    public void setBannerPath(String bannerPath) { this.bannerPath = bannerPath; }
    public void setCategory(String category) { this.category = category; }
}
