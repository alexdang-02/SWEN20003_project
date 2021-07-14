import bagel.Image;

public abstract class Actor {
    public Location location;
    
    private final Image image;
    public final String type;

    public Actor(String filename, String type, int x, int y){
        image = new Image(filename);
        this.type = type;
        this.location = new Location(x, y);
    }

    /**
     * draw both actor and number of fruit if available tp screen
     */
    public final void tick() {
        update();
    }

    /**
     * draw actor to screen
     */
    public void render() {
        image.drawFromTopLeft(location.x, location.y);
    }

    public abstract void update();
}
