
/**
 * 商品访问的Action类
 * 
 * @author 传智.郭嘉
 * 
 */
/**
 *		 这里我们为什么要继承  ModelDriver  ？？
 *		因为我们继承了这个，里面传的是 我们的 Product 类，这是 模型驱动！！！！！！！！！！！TM的老是忘记
 *		这样我们就可以获取到从前台传过来的
 *		关于商品信息的请求！！！！ 
 */
public class ProductAction extends ActionSupport implements
		ModelDriven<Product> {

	// 接收cid
	private Integer cid;

	// 接收当前页数
	private Integer page;


	// 注入商品的Service
	private ProductService productService;

	// 分页的商品显示:
	private PageBean<Product> pageBean;


	/**
	 *  我们手动提供一个 工具类，让框架去自动封装  我们做分页要 用到的  对象 
	 *   向页面返回数据:
	 * 		显示商品集合:
	 * 		当前页数:
	 * 		总页数:
	 * 		第 1/12 页
	 * 		通过总记录数.
	 * 		参数每页显示记录数.
	 ***** 将数据封装到一个Bean中.  
	 */
	public PageBean<Product> getPageBean() {			用于 struts2自动封装 page 属性的
		return pageBean;
	}


	public void setCid(Integer cid) {					前台传值自动拿到 cid
		this.cid = cid;
	}

	public Integer getCid() {
		return cid;
	}

	public void setPage(Integer page) {
		this.page = page;
	}




	// 查询商品的方法:
	public String findByCid() {
		
		// 查询商品:
		pageBean = productService.findByPage(cid, page);

		return "findByCidSuccess";
	}

	