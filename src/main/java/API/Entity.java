package API;

public class Entity {
    private String Fullname;    
    private String name;
    private String symbol;
    private String description;
    private String image;
    
    public Entity(String Fullname, String name, String symbol, String description, String image) {
        this.Fullname = Fullname;        
        this.name = name;
        this.symbol = symbol;
        this.description = description;
        this.image = image;        
    }

    public String getFullName() {
        return Fullname;
    }

    public void setFullName(String Fullname) {
        this.Fullname = Fullname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }    

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getImage(){
        return image;
    }
    
    public void setImage(String image) {
        this.image = image;
    }
}
