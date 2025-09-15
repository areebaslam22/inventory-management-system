class Admin  {
	
	private int id;
	private String username;
	private String email;


	public Admin(int id, String username, String email){
		this.id=id;
		this.username=username;
		this.email=email;
	}//constructor


	public void setId(int id){

		this.id=id;
	}//setId()

	public void setUserName(String username){
		this.username=username;
	}//setUserName()

	public void setEmail(String email){
		this.email=email;
	}//setEmail()

	public int getId(){
		return id;
	}//getId()

	public String getUserName(){
		return username;
	}//getUserName()

	public String getEmail(){
		return email;
	}//getEmail()

	 @Override
    public String toString() {
    	return "Id: "+getId()+" UserName: "+getUserName()+" Email: "+getEmail();
    }//toString()

}//class