import java.util.HashSet;
import java.util.Set;

public class Octopus {
    private Boolean has_flashed = false;
    private Integer energy_level;
    private final Set<Octopus> adjacent = new HashSet<>();

    public Octopus(Integer energy_level){
        this.energy_level = energy_level;
    }

    public void link(Octopus octopus){
        if(octopus != null) {
            adjacent.add(octopus);
        }
    }

    public void increaseEnergy() {
        energy_level++;
        if (energy_level > 9 && !has_flashed){
            has_flashed = true;
            for (Octopus octopus: adjacent) {
                octopus.increaseEnergy();
            }
        }
    }

    public Integer didFlash(){
        if(energy_level > 9){
            energy_level = 0;
            has_flashed = false;
            return 1;
        }
        return 0;
    }

    public void printEnergyLevel(){
        System.out.print(energy_level);
    }
}
