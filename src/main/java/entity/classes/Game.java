package entity.classes;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {
//region Constructors
    public Game(List<Island> inputIslands, int inputCountVarriors){
        this.islanads = inputIslands;
        this.countVarrior = inputCountVarriors;
    }
//endregion
//region Fields
    private List<Island> islanads = null;
    private int countVarrior = 0;
//endregion
//region Public Methods
    public void StartGame(){

        if(this.countVarrior > this.islanads.size())
            this.countVarrior = this.islanads.size();
        //рассадка
        for(int i = 0; i < this.countVarrior && i < this.islanads.size(); i++){
            while(true) {
                int rand = Rand(0, this.islanads.size() - 1);
                Island island = islanads.get(rand);
                if(island.getVarriorName() == null){
                    island.setVarriorName("Викинг" + i);
                    if(island.getIslandRoutes().size() == 0){
                        System.out.println("АГР!!! "+island.getVarriorName()+" застрял на "+island.getIslandName()+" и больше не участвует в войне");
                    }
                    break;
                }
            }
        }

        //старт перемещений
        while(true){

            int randIsland = Rand(0, this.islanads.size());
            Island item = this.islanads.get(randIsland);

            if(item.getVarriorName() != null && item.getIslandRoutes().size() != 0){
                int rand = Rand(0, item.getIslandRoutes().size());
                Routes route = item.getIslandRoutes().get(rand);
                Island island = GetIsland(route.getIslandName());
                if(island.getVarriorName() != null){
                    String varriorName = item.getVarriorName();
                    item.setVarriorName(null);
                    Battle(island.getIslandName(), varriorName);
                } else{
                    island.setVarriorName(item.getVarriorName());
                    item.setVarriorName(null);
                }
            }
            int existsRoutes = 0;
            for(Island island: this.islanads){
                if(island.getIslandRoutes().size() > 0 && island.getVarriorName() != null){
                    existsRoutes++;
                }
            }
            if(existsRoutes < 2){
                System.out.println("Война закончилась викинки не смогу пересечься на маяках");
                break;
            }
        }
    }
//endregion
//region Private Methods
    private Island GetIsland(String name){
        for(int i = 0; i < this.islanads.size(); i++){
            Island island = this.islanads.get(i);
            if(island.getIslandName().equals(name)){
                return island;
            }
        }
        return null;
    }
    private void Battle(String islandName, String actor){
        List<Island> newIslands = new ArrayList<Island>();
        String varrior = null;
        for (Island island: this.islanads) {
            if(island.getIslandName().equals(islandName)) {
                island.RemoveRoute();
                System.out.println("АГР!!! На "+islandName+" уничтожен маяк, благодаря "+island.getVarriorName()+" и " + actor);
                island.setVarriorName(null);
            }
        }
        for (Island island: this.islanads) {
            if(!island.getIslandName().equals(islandName)){
                island.RemoveRoute(islandName);
            }
        }
    }
    private int Rand(int start, int end){
        try {
            Random random = new Random();
            return random.nextInt(end);
        } catch(Exception ex){
            System.out.println(start + " " + end + ex.getMessage());
            return start;
        }
    }
//endregion
}
