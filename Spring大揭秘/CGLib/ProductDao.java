package cn.itcast.spring3.demo2;

/**
 *	因为我们的CGLib 可以直接对一个类进行增强，加入我们要增强以下的  类
 */
public class ProductDao {
	
	public void add(){
		System.out.println("添加商品...");
	}
	
	public void update(){
		System.out.println("修改商品...");
	}
}
