import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import bagel.AbstractGame;
import bagel.Image;
import bagel.Input;

public class ShadowLife extends AbstractGame  {

    private final ArrayList<Actor> staticActors = new ArrayList<>();
    private final ArrayList<Sign> signs = new ArrayList<>();
    private final ArrayList<MovingActor> movings = new ArrayList<>();


    private final Image background = new Image("res/images/background.png");

    // constant for processing command line argument and printing error
    public static final int VALID_NUM_CML_ARGV = 3;
    public static final int VALID_NUM_LINE_ITEM = 3;
    public static final String CML_ARGV_ERROR = "usage: ShadowLife <tick rate> <max ticks> <world file>";
    public static final String INVALID_FILE_ERROR_FORMAT = "error: file \"%s\" not found";
    public static final String INVALID_CONTENT_ERROR_FORMAT = "error: in file \"%s\" at line %d";

    // game configuration constant
    public final int TICK_RATE;
    public final int MAX_NUM_TICKS;
    public final String WORLD_FILE_PATH;
    private int numTicks = 0;

    public long curTime;
    public long nextTime;

    /**
     * Main function of the game
     * @param args Command Line Argument of the game
     *             Three component: tick rate, max num tick, data file
     */
    public static void main(String[] args) {
        // create new game with configurations from command line arguments
        ShadowLife newGame = new ShadowLife(args);
        newGame.run();
    }


    public ShadowLife(String[] args){
        checkArgument(args);
        // store valid command line arguments to program
        this.TICK_RATE = Integer.parseInt(args[0]);
        this.MAX_NUM_TICKS = Integer.parseInt(args[1]);
        this.WORLD_FILE_PATH = args[2];

        this.curTime = System.currentTimeMillis();
        this.nextTime = curTime + TICK_RATE;
        loadActors();
    }

    // check CML argument based on predefined condition
    private void checkArgument(String[] args){
        if(args.length != VALID_NUM_CML_ARGV){
            System.out.println(CML_ARGV_ERROR);
            System.exit(-1);
        }else if ((!parsableInt(args[0])) || (!parsableInt(args[1]))) {
            System.out.println(CML_ARGV_ERROR);
            System.exit(-1);
        }else if((Integer.parseInt(args[0]) < 0) || (Integer.parseInt(args[1]) <0)){
            System.out.println(CML_ARGV_ERROR);
            System.exit(-1);
        }
    }

    // save data into games from file
    private void loadActors() {
        try (BufferedReader reader = new BufferedReader(new FileReader(WORLD_FILE_PATH))) {
            String line;
            int count = 0;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                // check for number of element in a line
                if(parts.length != VALID_NUM_LINE_ITEM){
                    executeLineError(WORLD_FILE_PATH, count+1);
                }

                String type = parts[0];
                int x = 0;
                int y = 0;

                // check valid component of each line
                if(parsableInt(parts[1]) || parsableInt(parts[2])){
                    x = Integer.parseInt(parts[1]);
                    y = Integer.parseInt(parts[2]);
                }else{
                    executeLineError(WORLD_FILE_PATH,count+1);
                }

                int countBefore = count;

                switch (type) {
                    case Pool.TYPE:
                        staticActors.add(new Pool(x, y));
                        count++;
                        break;
                    case Pad.TYPE:
                        staticActors.add(new Pad(x, y));
                        count++;
                        break;
                    case Fence.TYPE:
                        staticActors.add(new Fence(x, y));
                        count++;
                        break;

                    case Tree.TYPE:
                        staticActors.add(new Tree(x, y));
                        count++;
                        break;
                    case GoldenTree.TYPE:
                        staticActors.add(new GoldenTree(x, y));
                        count++;
                        break;
                    case Stockpile.TYPE:
                        staticActors.add(new Stockpile(x, y));
                        count++;
                        break;
                    case Hoard.TYPE:
                        staticActors.add(new Hoard(x, y));
                        count++;
                        break;

                    case SignLeft.TYPE:
                        signs.add(new SignLeft(x, y));
                        count++;
                        break;
                    case SignDown.TYPE:
                        signs.add(new SignDown(x, y));
                        count++;
                        break;
                    case SignRight.TYPE:
                        signs.add(new SignRight(x, y));
                        count++;
                        break;
                    case SignUp.TYPE:
                        signs.add(new SignUp(x, y));
                        count++;
                        break;  

                    case Gatherer.TYPE:
                        movings.add(new Gatherer(x, y));
                        count++;
                        break;
                    case Thief.TYPE:
                        movings.add(new Thief(x, y));
                        count++;
                        break;
                }

                if(countBefore == count){
                    executeLineError(WORLD_FILE_PATH,count+1);
                }
            }
        } catch (IOException e) {
            // check for valid world file
            System.out.printf(INVALID_FILE_ERROR_FORMAT, WORLD_FILE_PATH);
            System.exit(-1);
        }
    }

    // check if input string does represent a number
    private boolean parsableInt(String input){
        try{
            Integer.parseInt(input);
            return true;
        }catch(NumberFormatException e){
            return false;
        }
    }

    // execute error in data file
    private void executeLineError(String fileName, int lineNumber){
        System.out.printf(INVALID_CONTENT_ERROR_FORMAT, fileName,lineNumber);
        System.exit(-1);
    }

    // check when game halts
    private void checkEndGame(){
        // exit game if MAX_NUM_TICKS is exceeded
        if(numTicks > MAX_NUM_TICKS){
            System.out.println("Timed out");
            System.exit(-1);
        }

        boolean exitGame = true;
        for(MovingActor actor : movings){
            if (actor != null) {
                if(actor.active){
                    exitGame = false;
                    break;
                }
            }
        }

        // print info after halting
        if(exitGame){
            System.out.println( Integer.valueOf(numTicks).toString() + " ticks");
            for (Actor actor : staticActors) {
                if (actor != null) {
                    if(actor instanceof Stockpile || actor instanceof Hoard){
                        FruitContainer newActor = (FruitContainer) actor;
                        System.out.println(newActor.numFruit);
                    }
                }
            }
            System.exit(0);
        }
    }

    @Override
    protected void update(Input input) {
        
        if(curTime >= nextTime){
            numTicks++;

            // variable facilitating pool condition check
            boolean reachPool = false;
            ArrayList<MovingActor> discardActor = new ArrayList<>();

            for(MovingActor moving : movings){

                // move if moving actor is active
                if(moving.active){
                    moving.move(moving.direction);
                }

                for (Actor staticActor : staticActors){
                    // actor become inactive if reach fence
                    if(staticActor instanceof Fence && 
                    staticActor.location.equals(moving.location)){
                        moving.active = false;
                        int new_direction = Direction.DOWN;
                        switch (moving.direction.value) {
                            case Direction.DOWN:
                                new_direction = Direction.UP;
                                break;
                            case Direction.UP:
                                new_direction = Direction.DOWN;
                                break;
                            case Direction.LEFT:
                                new_direction = Direction.RIGHT;
                                break;
                            case Direction.RIGHT:
                                new_direction = Direction.LEFT;
                                break;

                        }
                        moving.move(new_direction);
                    }

                    // check if actor reach pool
                    if(staticActor instanceof Pool &&
                    staticActor.location.equals(moving.location)){
                        reachPool = true;
                        if(moving instanceof Gatherer){
                            Gatherer newGatherer = (Gatherer) moving;
                            discardActor.add(newGatherer);
                        }else{
                            Thief newThief = (Thief) moving;
                            discardActor.add(newThief);
                        }
                    }

                    //change direction accordingly if meet signs
                    for (Sign sign : signs){
                        if(moving.location.equals(sign.location)){
                            moving.direction.changeDirection(sign.DIRECTION);
                        }
                    }

                    // check if thief reach pad 
                    if(staticActor instanceof Pad && moving instanceof Thief &&
                    staticActor.location.equals(moving.location)){
                        Thief newThief = (Thief) moving;
                        newThief.consuming = true;
                    }

                    // check condition for interacting with fruit
                    if(staticActor instanceof FruitContainer &&
                    moving.location.equals(staticActor.location)){

                        // collect fruit from tree
                        FruitContainer newFruitContainer = (FruitContainer) staticActor;
                        moving.collectFromTree(newFruitContainer);

                        // rotate direction for gatherer
                        if(moving instanceof Gatherer && 
                        (staticActor instanceof Hoard|| staticActor instanceof Stockpile)){
                            moving.direction.rotateDirection(180);
                        }

                        // interact with storage
                        FruitInteracted newFruitInteracted = (FruitInteracted) moving;
                        newFruitInteracted.interactWithStorage(newFruitContainer);
                    }
                }
                // check if thief reach gatherer
                for (MovingActor anotherMoving: movings){
                    if(moving instanceof Thief && 
                    anotherMoving instanceof Gatherer && moving.location.equals(anotherMoving.location)){
                        moving.direction.rotateDirection(270);
                        break;
                    }
                }
        }

        // add actor when reaching pool
        if(reachPool){
            for(MovingActor moving : discardActor){
                if(moving.type.equals("Gatherer")){
                    Gatherer newActor1 = new Gatherer(moving.location.x, moving.location.y);
                    Gatherer newActor2 = new Gatherer(moving.location.x, moving.location.y);
                    newActor1.copy((Gatherer) moving);
                    newActor2.copy((Gatherer) moving);
                    newActor1.direction.rotateDirection(90);
                    newActor2.direction.rotateDirection(-90);
                    newActor1.move(newActor1.direction);
                    newActor2.move(newActor2.direction);
                    movings.add(newActor1);
                    movings.add(newActor2);
                }else{
                    Thief newActor1 = new Thief(moving.location.x, moving.location.y);
                    Thief newActor2 = new Thief(moving.location.x, moving.location.y);
                    newActor1.copy((Thief) moving);
                    newActor2.copy((Thief) moving);
                    newActor1.direction.rotateDirection(90);
                    newActor2.direction.rotateDirection(-90);
                    newActor1.move(newActor1.direction);
                    newActor2.move(newActor2.direction);
                    movings.add(newActor1);
                    movings.add(newActor2);
                }
                movings.remove(moving);
            }
        }

        nextTime += TICK_RATE;
        }

        // draw element to screen
        background.drawFromTopLeft(0, 0);
        for (Actor actor : staticActors) {
            if (actor != null) {
                actor.tick();
            }
        }
        for(MovingActor actor : movings){
            if (actor != null) {
                actor.tick();
            }
        }
        for(Sign actor : signs){
            if (actor != null) {
                actor.tick();
            }
        }

        // update time 
        curTime =  System.currentTimeMillis();

        checkEndGame();
    }
}














