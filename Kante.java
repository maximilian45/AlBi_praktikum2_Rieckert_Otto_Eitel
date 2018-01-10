public class Kante extends GenomAssembling{
	protected int id;
	protected int startKnotenId;
	protected int endKnotenId;
	protected boolean besucht;
	protected String value;
	
	public Kante(int id, String value){
		this.id = id;
		this.value = value;
		this.startKnotenId = 0;
		this.endKnotenId = 0;
		this.besucht = false;
	}
	
	public Kante(int id, int startKnotenId, int endKnotenId, boolean besucht, String value){
		this.id = id;
		this.startKnotenId = startKnotenId;
		this.endKnotenId = endKnotenId;
		this.besucht = false;
		this.value = value;
	}
	public int getId(){
		return this.id;
	}
	public int getStartKnotenId(){
		return this.startKnotenId;
	}
	public void setStartKnotenId(int newId){
		this.startKnotenId = newId;
	}
	public int getEndKnotenId(){
		return this.endKnotenId;
	}	
	public void setEndKnotenId(int newId){
		this.endKnotenId = newId;
	}
	public boolean getBesucht(){
		return this.besucht;
	}	
	public void besuche(){
		this.besucht = true;
	}
	public String getValue(){
		return this.value;
	}
	public void setValue(String value){
		this.value = value;
	}
}