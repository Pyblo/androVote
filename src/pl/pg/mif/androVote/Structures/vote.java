package pl.pg.mif.androVote;

public class vote {
	public int id;
	public String description;
	public boolean votePlaced;
	public vote(int _id, String _desc){
		id = _id;
		description = _desc;
		votePlaced = false;
	}
}