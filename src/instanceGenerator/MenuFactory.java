package instanceGenerator;

import java.util.Random;

import cookItBO.CookItBO;
import cookItBO.common.Date;
import cookItBO.recipes.Menu;
import cookItBO.recipes.Product;

public class MenuFactory {
	
	public static void runOn(CookItBO instance,int nMenu,Random r){
		Date d=new Date(0,0,0);
		Menu menu= new Menu(d);
		Product product;
		for(int i=0;i<nMenu;i++){
			product=new Product("Product"+i);
			menu.addProductInList(product);
		}
		instance.addMenuInList(d, menu);
	}
}
