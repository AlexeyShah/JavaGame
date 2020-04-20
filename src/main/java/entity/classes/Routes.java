package entity.classes;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import entity.enumiration.enumRoutes;

@Builder
public class Routes{

    @Getter
    @Setter
    public enumRoutes Route;

    @Getter
    @Setter
    public String IslandName;
}