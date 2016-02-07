public class TrainListObj
{ int index,trno;
  float arr;
  String art,dpt,trna,avl;
  int dist,fr;
  TrainListObj(int i,int tno,String tna,String a,String b,String avlb,int di)
  { index=i; trno=tno; trna=tna; art=a; dpt=b;	avl=avlb;
    arr=Float.parseFloat(art);
	dist=di;
  }
  public void setFare(int f)
  { fr=f; }
  TrainListObj()
  {} 
  public float getArrival()
  { return arr; }
  public String toString()
  { return ""+trno+" "+trna+" "+art+" "+dpt; } 
}