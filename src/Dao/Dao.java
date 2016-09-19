package Dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import domain.User;


public class Dao {
	
	/**
	 * 获取连接
	 * @return
	 */
	static Connection getConn(){
		String url = "jdbc:sybase:Tds:PC-20160712FWRU:5000/test?charset=cp936";
		String name = "sa";
		String password = "";
		try {
			Class.forName("com.sybase.jdbc3.jdbc.SybDriver").newInstance(); //注册驱动
			Connection conn = DriverManager.getConnection(url, name, password); // 获取连接
			return conn;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 添加用户
	 */
	//@Test
	public void addUser(){
		Connection conn = null;
		PreparedStatement prepareStatement = null;
		try {
			conn = Dao.getConn();
			conn.setAutoCommit(false); //设置非自动提交
			prepareStatement = conn.prepareStatement("insert into T_USER (YHDM,DWDM,YHID,YHXM,YHBM,SFJY) values (?,?,?,?,?,?)");
			for(int i=0;i<5;i++){
				prepareStatement.setString(1, "320100lar"+(i+2));
				prepareStatement.setString(2, "320100");
				prepareStatement.setString(3, "lar"+(i+2));
				prepareStatement.setString(4, "立案人"+(i+2));
				prepareStatement.setString(5, "3201000"+(i+1));
				prepareStatement.setString(6, "0");
				prepareStatement.execute();
			}
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally{
			close(conn, prepareStatement, null);
			
		}
	}
	
	/**
	 * 添加部门
	 */
	//@Test
	public void addDEPART(){
		Connection conn = null;
		PreparedStatement prepareStatement = null;
		try {
			String[] BMMC = {"审判庭","财务室","警卫室","咨询室","收发室"};
			conn = Dao.getConn();
			conn.setAutoCommit(false);
			prepareStatement = conn.prepareStatement("insert into T_DEPART (BMDM,DWDM,BMID,BMMC,PXH)values(?,?,?,?,?)");
			for(int i=0;i<BMMC.length;i++){
				prepareStatement.setString(1, "3201000"+(i+4));
				prepareStatement.setString(2, "3201000");
				prepareStatement.setString(3, "0"+(i+4));
				prepareStatement.setString(4,BMMC[i]);
				prepareStatement.setInt(5, i+4);
				prepareStatement.execute();
			}
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally{
			close(conn, prepareStatement, null);
		}
	}
	
	/**
	 * 获取用户
	 * @return	map，键：用户代码；用户姓名
	 */
	//@Test
	public Map<String, String> getUsersToMap(){
		Connection conn = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			conn = Dao.getConn();
			prepareStatement = conn.prepareStatement("select * from T_USER");
			resultSet = prepareStatement.executeQuery();
			while(resultSet.next()){
				String yhdm = resultSet.getString("YHDM");
				String yhxm = resultSet.getString("YHXM");
				map.put(yhdm, yhxm);
			}
			System.out.println(map);
			return map;
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally{
			close(conn, prepareStatement, resultSet);
		}
		return null;
	}
	
	/**
	 * 获取用户
	 * @return	list的用户集合
	 */
	//@Test
	public List<User> getUsersToList(){
		Connection conn = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		ArrayList<User> users = new ArrayList<User>();
		try {
			conn = Dao.getConn();
			conn.setAutoCommit(false);
			prepareStatement = conn.prepareStatement("select YHDM,YHXM,YHBM,BMMC from T_USER,T_DEPART where T_USER.YHBM=T_DEPART.BMDM");
			resultSet = prepareStatement.executeQuery();
			while(resultSet.next()){
				User user = new User();
				user.setBmdm(resultSet.getString("YHBM"));
				user.setBmmc(resultSet.getString("BMMC"));
				user.setThxm(resultSet.getString("YHXM"));
				user.setYhdm(resultSet.getString("YHDM"));
				users.add(user);
			}
			return users;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}finally{
			close(conn, prepareStatement, resultSet);
		}
		return users;
	}
	
	/**
	 * 修改指定用户id的用户的信息
	 * @param params	包含要修改的信息的map，键：修改属性名称，值：修改属性的值
	 * @param yhid
	 */
	public void UpdateUser(Map<String, String> params,String yhid){
		Connection conn = null;
		PreparedStatement prepareStatement = null;
		StringBuilder sql = new StringBuilder("update T_USER set ");
		Set<String> keys = params.keySet();
		String[] values = new String[params.size()];
		int i=0;
		if(keys.size()>0){
			for(String key:keys){
				sql.append(key+"=?,");
				values[i++] = params.get(key);
			}
		}
		sql.deleteCharAt(sql.length()-1);
		sql.append(" where YHID = ?");
		try {
			conn = Dao.getConn();
			conn.setAutoCommit(false);
			prepareStatement = conn.prepareStatement(sql.toString());
			for(i=0;i<params.size();i++){
				prepareStatement.setString(i+1, values[i]);
			}
			prepareStatement.setString(params.size()+1, yhid);
			prepareStatement.execute();
			conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}finally{
			close(conn, prepareStatement, null);
		}
	}
	
	/**
	 * 根据用户id删除用户
	 * @param yhid	用户id
	 */
	public void DeleteUser(String yhid){
		Connection conn = null;
		PreparedStatement prepareStatement = null;
		StringBuilder sql = new StringBuilder("delete from T_USER where YHID=?");
		try {
			conn = Dao.getConn();
			conn.setAutoCommit(false);
			prepareStatement = conn.prepareStatement(sql.toString());
			prepareStatement.setString(1, yhid);
			prepareStatement.execute();
			conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}finally{
			close(conn, prepareStatement, null);
		}
	}
	
	/**
	 * 修改指定部门代码的部门的信息
	 * @param params	包含要修改的信息的map，键：修改属性名称，值：修改属性的值
	 * @param bmdm 		部门代码
	 * @author 
	 * @date 2016/7/21
	 */
	public void UpdateDEPART(Map<String, String> params,String bmdm){
		Connection conn = null;
		PreparedStatement prepareStatement = null;
		StringBuilder sql = new StringBuilder("update T_DEPART set ");
		Set<String> keys = params.keySet();
		String[] values = new String[params.size()];
		int i=0;
		if(keys.size()>0){
			for(String key:keys){
				sql.append(key+"=?,");
				values[i++] = params.get(key);
			}
		}
		sql.deleteCharAt(sql.length()-1);
		sql.append(" where BMDM = ?");
		try {
			conn = Dao.getConn();
			conn.setAutoCommit(false);
			prepareStatement = conn.prepareStatement(sql.toString());
			for(i=0;i<params.size();i++){
				prepareStatement.setString(i+1, values[i]);
			}
			prepareStatement.setString(params.size()+1, bmdm);
			prepareStatement.execute();
			conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}finally{
			close(conn, prepareStatement, null);
		}
	}
	
	/**
	 * 删除指定部门代码的部门
	 * @param bmdm	部门代码
	 */
	public void DeleteDEPART(String bmdm){
		Connection conn = null;
		PreparedStatement prepareStatement = null;
		StringBuilder sql = new StringBuilder("delete from T_DEPART where BMDM=?");
		try {
			conn = Dao.getConn();
			conn.setAutoCommit(false);
			prepareStatement = conn.prepareStatement(sql.toString());
			prepareStatement.setString(1, bmdm);
			prepareStatement.execute();
			conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}finally{
			close(conn, prepareStatement, null);
		}
	}
	
	/**
	 * 关闭连接
	 * @param conn	连接对象
	 * @param ps	预处理对象
	 * @param resultset	结果集对象
	 */
	private static void close(Connection conn,PreparedStatement ps,ResultSet resultset){
		try {
			if(resultset != null){
				resultset.close();
			}
		}catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		try {
			if(ps != null){
				ps.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try{
			if(conn != null){
				conn.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
				e.printStackTrace();
		}
	}
}
