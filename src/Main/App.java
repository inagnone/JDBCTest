package Main;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import Util.ToolUtils;
import domain.User;
import Dao.Dao;
import javafx.scene.chart.PieChart.Data;


public class App {
	public static void main(String[] args) {
		Dao dao = new Dao();
		//添加部门
		dao.addDEPART();
		//添加用户
		dao.addUser();
		//map获取用户
		Map<String, String> usersmap = dao.getUsersToMap();
		Set<String> keys = usersmap.keySet();
		for(String key:keys){
			System.out.println(key+">>>>>>>>>>"+usersmap.get(key));
		}
		//list获取用户
		List<User> userlist = dao.getUsersToList();
		for(User user : userlist){
			System.out.println(user.getThxm()+">>>>>>>>>>"+user.getBmmc());
		}
		//修改用户信息
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("YHXM", "123");
		params.put("DWDM", "111");
		dao.UpdateUser(params, "lar1");
		
		//删除用户
		dao.DeleteUser("lar3");
		
		//修改部门信息
		HashMap<String, String> params2 = new HashMap<String, String>();
		params2.put("BMMC", "hehehe");
		params2.put("DWDM", "111");
		dao.UpdateDEPART(params2, "32010001");
		
		//删除部门
		dao.DeleteDEPART("32010001");
		
		//时间格式
		Calendar calendar = Calendar.getInstance();
		System.out.println(calendar.getTime().toString());
		calendar.set(2016, 0, 1, 0, 0, 0);
		System.out.println(ToolUtils.convertRq(new Date(calendar.getTimeInMillis()), "yyyy-MM-dd"));
		calendar.add(Calendar.MONTH, 1);
		calendar.add(Calendar.DAY_OF_MONTH, 28);
		Date date = new Date(calendar.getTimeInMillis());
		System.out.println(ToolUtils.convertRq(date, "yyyy-MM-dd"));
	}
}
