import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;

public class PointsByDistance
{
	private HashMap<HashMap<Integer,Integer>,Double> dMap = new HashMap<HashMap<Integer,Integer>,Double>();
	private List<Map.Entry<Map.Entry<Integer, Integer>, Double>> mHashMapEntryList;
	public List<Entry<Entry<Integer, Integer>, Double>> DistanceList(Vector<Ring> vring){
		int number=vring.size();
		for(int i=0;i<=number-2;i++)
		{
			 Point2D sourceP = new Point2D.Double(vring.get(i).x0, vring.get(i).y0);
			for(int j=i+1;j<=number-1;j++){
		    Point2D point = new Point2D.Double(vring.get(j).x0, vring.get(j).y0);

				double dsSqure = point.distanceSq(sourceP);
				double ds=Math.sqrt(dsSqure); 
				HashMap<Integer, Integer> pMap=new HashMap<Integer, Integer>();
				pMap.put(i, j); //point set
				dMap.put(pMap, ds); //distance set
			System.out.println("dmap:"+dMap.toString());	 
			 
			}
		}
		
mHashMapEntryList=new ArrayList<Map.Entry<Map.Entry<Integer,Integer>,Double>>((Collection<? extends Entry<Entry<Integer, Integer>, Double>>) dMap.entrySet());
		
		System.out.println("-----> Before sorting");
		for (int i = 0; i < mHashMapEntryList.size(); i++) {
			 System.out.println(mHashMapEntryList.get(i));
		}
		
		Collections.sort(mHashMapEntryList, new Comparator<Map.Entry<Map.Entry<Integer,Integer>,Double>>() {

//			@Override
//			public int compare(Map.Entry<String,Integer> firstMapEntry, 
//							   Map.Entry<String,Integer> secondMapEntry) {
//				return firstMapEntry.getKey().compareTo(secondMapEntry.getKey());
//			}

			@Override
			public int compare(Entry<Entry<Integer, Integer>, Double> o1,
					Entry<Entry<Integer, Integer>, Double> o2) {
				// TODO Auto-generated method stub
				return o1.getValue().compareTo(o2.getValue());
			}
		});
		
		System.out.println("-----> after sorting");
		for (int i = 0; i < mHashMapEntryList.size(); i++) {
			 System.out.println(mHashMapEntryList.get(i));
		}
		
		
		return mHashMapEntryList;
		
	}
}