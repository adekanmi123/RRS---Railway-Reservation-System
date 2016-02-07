public class CancelledHistory
{ int trid,ref;
  String pnr,doc,reason;
   public String getTransID()
  { return " "+trid; }
  public String canInfo()
  { String rf=""+ref;
    if(rf.length()<4)
    { int x=4-rf.length();
	  for(int i=0;i<x;i++)
	  { rf="  "+rf; }
	}
    return "           "+pnr+"                "+doc+"               "+reason+"             Rs. "+rf;  
  }
}