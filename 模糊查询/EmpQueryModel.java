模糊查询，我们可以重新建一个 model
把我们的查询变量封装到model里面




public class EmpQueryModel extends EmpModel{
	//追加生日作为查询条件的最大值字段
	private Long birthday2;
	private String birthday2View;
	
	public String getBirthday2View() {
		return birthday2View;
	}
	public Long getBirthday2() {
		return birthday2;
	}
	public void setBirthday2(Long birthday2) {
		this.birthday2 = birthday2;
		this.birthday2View = FormatUtil.formatDate(birthday2);
	}
	
}





QBC：条件查询
		Criteria criteria = session.createCriteria(Customer.class);
		criteria.add(Restrictions.eq("name", "凤姐"));		//  按条件进行查询，我们add 添加查询条件，里面填条件 
		List<Customer> list = criteria.list();