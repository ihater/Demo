
import java.util.List;

/**
 * 分页显示数据类
 * @author 传智.郭嘉
 * 因为我们如果要做分页 的话   ，我们前台要拿到的数据大概有
 * 	 向页面返回数据:
 * 	显示商品集合:
 * 	当前页数:
 * 	总页数:
 * 	第 1/12 页
 * 	通过总记录数.
 *	 参数每页显示记录数.
 *	我们要么就是将这些数据手动压入到值栈中，要么就手动提供 get 方法自动压入到值栈，但是我们要的参数太多了
 *	所以我们就可以提供一个工具类，专门来将这些数据封装到  bean 中去，我们只要在分页那个提供这个工具类  的  get 方法就好了
 ***** 将数据封装到一个Bean中.
 */
public class PageBean<T> {
	private Integer page;// 当前页数.
	private Integer limit;// 每页显示记录数
	private Integer totalCount;// 总记录数
	private Integer totalPage;// 总页数.
	private List<T> list; // 显示到浏览器的数据.
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getLimit() {
		return limit;
	}
	public void setLimit(Integer limit) {
		this.limit = limit;
	}
	public Integer getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	public Integer getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}
	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}
	
}
