public class Knoten extends GenomAssembling{
	protected int id;
	protected int[] inKantenIds;
	protected int[] outKantenIds;
	protected String value;
	public Knoten(int id, int[] inKantenIds, int[] outKantenIds, String value){
		this.id = id;
		this.inKantenIds = inKantenIds;
		this.outKantenIds = outKantenIds; 
		this.value = value;
	}
	public Knoten(int id, String value){
		this.id = id;
		this.value = value;
		this.inKantenIds = new int[1];
		this.outKantenIds = new int[1];
	}
	
	public int getId(){
		return this.id;
	}
	public int[] getInKantenIds(){
		return this.inKantenIds;
	}
	public void setInKantenIds(int[] newIds){
		this.inKantenIds = newIds;
	}
	public int[] getOutKantenIds(){
		return this.outKantenIds;
	}	
	public void setOutKantenIds(int[] newIds){
		this.outKantenIds = newIds;
	}
	public String getValue(){
		return this.value;
	}
	public void setValue(String value){
		this.value = value;
	}
}