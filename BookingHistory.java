public class BookingHistory
{ int trid,ref;
  String pnr,src,des,doj,dob,res_stat,doc;
  public String bookInfo()
  { String t="";
    if(res_stat.equals("CNF"))
    { t="Confirmed     "; }
	else if(res_stat.equals("P_CAN"))
	{ t="P_Cancedlled"; }
	else if(res_stat.equals("CAN"))
	{ t="Cancedlled    "; }
	else if(res_stat.equals("WL"))
	{ t="WaitingList   "; }
	else
	{ t="Journey Over "; }
    return "        "+doj+"           "+t+"           "+dob; 
  }
  public String getTransID()
  { return " "+trid; }
}