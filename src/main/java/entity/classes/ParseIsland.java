package entity.classes;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import lombok.Builder;
import lombok.Getter;
import entity.enumiration.enumRoutes;
import lombok.Setter;

@Builder
public class ParseIsland{

//region Fields
    @Setter
    public File file = null;
    @Getter
    private List<Island> islands = null;

    @Getter
    public String Error = null;
//endregion
//region Public Methods
    public void Parse(){
        try {
            ReadFile();
        } catch (FileNotFoundException ex){
            SetError(ex.getMessage());
        } catch (IllegalArgumentException ex){
            SetError(ex.getMessage());
        }
    }
//endregion
//region Private Methods
    private void SetError(String message){
        Error = message;
    }
    private void ReadFile() throws FileNotFoundException {

        if(!file.exists()){
            throw new FileNotFoundException("Файл отсутствует по указанному пути");
        }

        Scanner scanner = new Scanner(file);

        while(scanner.hasNextLine()){
            ParseText(scanner.nextLine());
        }
        scanner.close();

    }
    private void ParseText(String line){
        if(line == null || line.length() == 0){
            return;
        }
        String islandName = line.substring(0, line.indexOf(" "));
        String lineRoutes = line.substring(line.indexOf(" ")).trim();

        for(String lineRoute: lineRoutes.split(" ")){
            String[] arr = lineRoute.toString().split("=");

            enumRoutes route = SwitchRoutes(Array.get(arr, 0).toString());
            String toIslandName = Array.get(arr, 1).toString();
            if(route == enumRoutes.None || toIslandName == null || toIslandName.length() == 0 || islandName.equals(toIslandName)){
                throw new IllegalArgumentException("Маршрут в файле указан не верно: " + lineRoute);
            }

            Routes newRoute = Routes.builder().IslandName(toIslandName).Route(route).build();

            Island currentIsland = null;

            for (Island island: islands) {
                if(island.getIslandName().equals(islandName)){
                    currentIsland = island;
                    break;
                }
            }

            if(currentIsland == null){
                Island newIsland = Island.builder().islandName(islandName).islandRoutes(new ArrayList<Routes>()).build();
                newIsland.AddRoutes(newRoute);
                islands.add(newIsland);
            } else {
                if(currentIsland.ExistsRoutes(newRoute)){
                    throw new IllegalArgumentException("Имеются дублирующие маршруты для строки: " + lineRoute);
                }
                currentIsland.AddRoutes(newRoute);
            }
        }
    }
    private enumRoutes SwitchRoutes(String str){
        if(str.equals("север")){
            return enumRoutes.North;
        } else if(str.equals("восток")){
            return enumRoutes.East;
        } else if(str.equals("юг")){
            return enumRoutes.South;
        } else if(str.equals("запад")){
            return enumRoutes.West;
        } else {
            return enumRoutes.None;
        }
    }
//endregion
}