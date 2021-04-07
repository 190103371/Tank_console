package Project_test;
import java.util.*;

public class Solution {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String className = input.nextLine();

        // Checking the implementation of the Position class
        if(className.equals("Position")){
            Position p1 = new Position(input.nextInt(), input.nextInt());
            System.out.println(p1);
            p1.setX(5);
            System.out.println(p1.getX());

            Position p2 = new Position(5, 10);
            System.out.println(p1.equals(p2));
        }

        // Checking the implementation of the Map class
        else if(className.equals("Map")){
            try{
                Map map = new Map(input);
                map.print();
                int size = map.getSize();
                System.out.println(size);
                System.out.println(map.getValueAt(size/2, size/2));
            }
            catch(Exception e){}
        }

        // Checking the Player interface and the MyPlayer class
        else if(className.equals("Player")){
            Player player = new MyPlayer();
            System.out.println(Player.class.isInterface());
            System.out.println(player instanceof Player);
            System.out.println(player instanceof MyPlayer);
        }

        // Checking the InvalidMapException class
        else if(className.equals("Exception")){
            try{
                throw new InvalidMapException("Some message");
            }
            catch(InvalidMapException e){
                System.out.println(e.getMessage());
            }
        }

        // Checking the Game class and all of its components
        else if(className.equals("Game")){

            // Initialize player, map, and the game
            Player player = new MyPlayer();
            Game game = null;

            try{
                game = new Game(new Map(input));
            }
            catch(InvalidMapException e){ // custom exception
                System.out.println(e.getMessage());
                System.exit(0);
            }

            game.addPlayer(player);

            // Make the player move based on the commands given in the input
            String moves = input.next();
            char move;
            for(int i=0; i<moves.length(); i++){
                move = moves.charAt(i);
                switch(move){
                    case 'R':
                        player.moveRight();
                        break;
                    case 'L':
                        player.moveLeft();
                        break;
                    case 'U':
                        player.moveUp();
                        break;
                    case 'D':
                        player.moveDown();
                        break;
                }
            }

            // Determine the final position of the player after completing all the moves above
            Position playerPosition = player.getPosition();
            System.out.println("Player final position");
            System.out.println("Row: " + playerPosition.getY());
            System.out.println("Col: " + playerPosition.getX());
        }
    }
}

class Position {
    private int x;
    private int y;
    private boolean isequal = false;

    public Position(int x, int y){
        this.x = x;
        this.y = y;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public void setX(int newX){
        x = newX;
    }
    public void setY(int newY){
        y = newY;
    }
    public boolean equals(Position position){
        if(position.getY() == getY() && position.getX() == getX()){
            isequal = true;
        }else isequal = false;
        return isequal;
    }
    public String toString(){
        return "(" + x + "," + y + ")";
    }

}

interface Player {
    public void setMap(Map map);

    public void moveRight();

    public void moveLeft();

    public void moveUp();

    public void moveDown();

    public Position getPosition();
}
class MyPlayer implements Player{
    private Map map;
    private static Position position;
    private static char[][] arr;
    private static int size;
    private static int row;
    private static int col;

    public MyPlayer() {

    }

    public MyPlayer(Map map ){

        this.map = map;
        arr = map.getArr();
        size = map.getSize();
        position = map.getPosition();
        col = position.getX();
        row = position.getY();
        position.setX(col);
        position.setY(row);

    }

    public void setMap(Map map){

        this.map = map;

    }

    public void moveRight(){

        if(position.getX() < size - 1 && arr[position.getX() + 1][position.getY()] != '1'){
            position.setX(position.getX() + 1);
        }

    }

    public void moveLeft(){

        if(position.getX() > 0 && arr[position.getX() - 1][position.getY()] != '1'){
            position.setX(position.getX() - 1);
        }

    }

    public void moveUp(){

        if(position.getY() > 0 && arr[position.getX()][position.getY() - 1] != '1'){
            position.setY(position.getY() - 1);
        }

    }

    public void moveDown(){

        if(position.getY() < size - 1 && arr[position.getX()][position.getY() + 1] != '1'){
            position.setY(position.getY() + 1);
        }

    }


    public Position getPosition(){

        return position;

    }
}


class Map {
    private int size;
    private char [][] arr;
    private int col;
    private int row;
    private Position position;

    public Map(Scanner in) throws InvalidMapException{

        size = in.nextInt();

        arr = new char[size][size];

        if(size == 0){
            throw new InvalidMapException("Map size can not be zero");
        }

        for (int i = 0; i < size; i++) {

            for (int j = 0; j < size; j++) {

                arr[j][i] = in.next().charAt(0);
                position = new Position(row, col);
                if (arr[j][i] == 'P') {

                    col = i;
                    row = j;

                }

            }
        }

    }
    public int getSize(){
        return size;
    }
    public void setSize(int nSize){
        size = nSize;
    }
    public char getValueAt(int x, int y){

        return arr[x][y];

    }
    public void print(){

        for(int i = 0; i < size; i++){

            for(int j = 0; j < size; j++){

                System.out.print(arr[j][i] + " ");

            }

            System.out.println();

        }

    }

    public char[][] getArr(){

        return arr;

    }

    public Position getPosition(){
        return position;
    }


}
class Game{

    private Map map;
    private ArrayList<Player> players = new ArrayList<Player>();
    private Player myPlayer;

    public Game(Map map){

        this.map = map;
        myPlayer = new MyPlayer(map);

    }

    public void setMap(Map nMap){

        map = nMap;

    }

    public void addPlayer(Player player){

        players.add(player);

    }
}
class InvalidMapException extends Exception{
    public InvalidMapException(String message){
        super("Some message");
    }
}
