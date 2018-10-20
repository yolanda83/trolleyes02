package net.daw.bean;

public class TipousuarioBean {
	
	private int id;
	private String desc;

        
    public TipousuarioBean() {
    }

    public TipousuarioBean(int id, String desc) {
        this.id = id;
        this.desc = desc;
    }
               

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
