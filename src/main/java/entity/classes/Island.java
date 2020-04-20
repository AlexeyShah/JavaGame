package entity.classes;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Builder
public class Island {

//region Constructors
//endregion

//region Fields
    //направления с острова
    @Getter
    @Setter
    private List<Routes> islandRoutes = new ArrayList<Routes>();

    //название острова
    @Getter
    @Setter
    private String islandName = null;

    @Setter
    @Getter
    private String varriorName = null;
//endregion

//region Public Methods
    public void AddRoutes(Routes newRoute){
        islandRoutes.add(newRoute);
    }
    public boolean ExistsRoutes(Routes newRoutes){
        for (Routes item: islandRoutes) {
            if(item.IslandName.equals(newRoutes) && item.Route.equals(newRoutes)){
                return true;
            }
        }
        return false;
    }
    public void RemoveRoute(String islandName){
        List<Routes> routes = new ArrayList<Routes>();
        for(Routes route: this.islandRoutes){
            if(!route.getIslandName().equals(islandName)){
                routes.add(route);
            }
        }
        if(routes.size() == 0 && this.varriorName != null && this.islandRoutes.size() > 0){
            System.out.println("АГР!!! "+this.varriorName+" застрял на "+this.islandName+" и больше не участвует в войне”");
        }
        this.islandRoutes = routes;
    }
    public void RemoveRoute(){
        this.islandRoutes = new ArrayList<Routes>();
    }
//endregion
}