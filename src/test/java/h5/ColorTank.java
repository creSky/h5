package h5;
 
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
 
public class ColorTank {
	private int count=0;
	private List<List<Integer>> colorList=new ArrayList<List<Integer>>();
	ColorTank(int count){
		this.count=count;
		for(int i=0;i<=this.count;i++){
			colorList.add(new ArrayList<Integer>());
		}
	}
	void addToTank(int rgb){
		float gray=SuiCaiDemo.rgb2gray(rgb);
		int index=(int) ((gray/255)*count);
		colorList.get(index).add(rgb);
	}
	int getColor(){
		int maxIndex=0;
		int maxSize = 0;
		for (int i = 0; i < colorList.size(); i++) {
			List<Integer> temp=colorList.get(i);
			if (temp.size()>maxSize) {
				maxSize=temp.size();
				maxIndex=i;
			}
			
		}
		List<Integer> maxRGBList=colorList.get(maxIndex);
		int allColorR=0;
		int allColorG=0;
		int allColorB=0;
		for (Integer integer : maxRGBList) {
			Color c=new Color(integer);
			allColorR+=c.getRed();
			allColorG+=c.getGreen();
			allColorB+=c.getBlue();
		}
		allColorR=allColorR/maxRGBList.size();
		allColorG=allColorG/maxRGBList.size();
		allColorB=allColorB/maxRGBList.size();
		return new Color(allColorR, allColorG, allColorB).getRGB();
		
	}
}
