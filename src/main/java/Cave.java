import java.security.PublicKey;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public abstract class Cave {
    private String id;
    private Set<Cave> links = new HashSet<>();

    public Cave(String id){
        this.id = id;
    }

    public abstract Boolean isSmall();

    public void link(Cave cave){
        links.add(cave);
        cave.links.add(this);
    }

    public Set<Cave> getLinks(){
        return links;
    }

    public String getId() {
        return id;
    }

    public boolean equals(Cave cave) {
        return id.equals(cave.id);
    }
}