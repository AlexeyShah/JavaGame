
import entity.classes.Game;
import entity.classes.Island;
import entity.classes.ParseIsland;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args){

        if(args.length != 2){
            System.out.println("Отсутствует входной файл островов или параметр количества войнов");
            return;
        }

        String filePath = Array.get(args, 1).toString();
        int countVariors = Integer.parseInt(Array.get(args, 0).toString());

        ParseIsland parser = ParseIsland.builder().file(new File(filePath)).islands(new ArrayList<Island>()).build();

        parser.Parse();

        if(parser.getError() != null){
            System.out.println(String.format("Файл {0} не корректный: {1}", filePath, parser.getError()));
            return;
        }

        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("Начнем игру!");

        Game game = new Game(parser.getIslands(), countVariors);
        game.StartGame();

        System.out.println("Игра закончилась!");
    }
}
