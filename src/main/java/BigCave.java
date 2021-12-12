public class BigCave extends Cave {
    public BigCave(String id){
        super(id);
    }

    @Override
    public Boolean isSmall() {
        return false;
    }
}
