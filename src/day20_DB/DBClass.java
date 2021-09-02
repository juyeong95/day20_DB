package day20_DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DBClass {
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String id ="juyeong";
	private String pwd = "1234";
	
	public DBClass() {
		// 1. 자바에서 오라클에 관련된 기능을 사용할 수 있게 기능을 등록하는 것
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (Exception e) {
			e.printStackTrace(); //무조건 써줘야함
		}
		
		
	}
	public ArrayList<StudentDTO> getList() {
		ArrayList<StudentDTO> list = new ArrayList<StudentDTO>();
		try {
			// 2. 데이터베이스 연결( con은 DB에 연결된 객체다 )
			Connection con = DriverManager.getConnection(url, id, pwd);
			System.out.println("연결이 잘 이루어졌습니다.");
			// 3. 데이터베이스에 연결된 객체를 이용해서 명령어를 수행할 수 있는 객체를 얻어온다.
			String sql = "select * from newst";
			PreparedStatement ps = con.prepareStatement(sql);
			
			// 4. 명령어를 수행할 수 있는 객체를 이용해서 명령어 수행
			// 5. 수행 결과를 저장한다.
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				/*System.out.println("id: "+rs.getString("id"));
				System.out.println("name: "+rs.getString("name"));
				System.out.println("age: "+rs.getInt("age"));
				System.out.println("--------------------------------");
				*/
				StudentDTO dto = new StudentDTO();
				dto.setId(rs.getString("id"));
				dto.setName(rs.getString("name"));
				dto.setAge(rs.getInt("age"));
				
				list.add(dto);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
		
	}
	public StudentDTO searchST(String id) {
		//select * from newst where id = '222';
		String sql = "select * from newst where id = '"+id +"'";
		StudentDTO dto = null;
		try {
			/*System.out.println(url);
			System.out.println(id); //입력받은 아이디로 출력됨 
			System.out.println(pwd);  문제가 생기면 이런식으로 확인 해 볼것*/
			//1.디비 연결
			Connection con = DriverManager.getConnection(url, this.id, pwd); //this.id로 바꿔줌
			System.out.println("---- 연결 확인 ----");
			// 2. 명령어(쿼리문)전송 객체 생성
			PreparedStatement ps = con.prepareStatement(sql);
			// 3. 전송 후 결과값 저장
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				dto = new StudentDTO();
				dto.setId(rs.getString("id"));
				dto.setName(rs.getString("name"));
				dto.setAge(rs.getInt("age"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dto;
	}
	public int saveData(String userId, String userName, int userAge) {
		//insert into newst values('aaa','김개똥','33');
		//String sql = "insert into newst values('"+userId+"','"+userName+"','"+userAge+"')";
		String sql = "insert into newst values(?, ?, ?)";
		int result = 0;
		try {
			// 1.데이터베이스 연결 객체 얻어오기
			Connection con = DriverManager.getConnection(url, id, pwd);
			// 2. 쿼리문 전송객체 얻어오기
			PreparedStatement ps = con.prepareStatement(sql);
			// 3. ?자리 채우기
			ps.setString(1, userId);  //1,2,3의 의미는 ? 자리의 순서이다.
			ps.setString(2, userName);
			ps.setInt(3, userAge); 
			// 4. 쿼리문 전송(실행)
			//ResultSet rs = ps.executeQuery(); // select 쿼리문일때 executeQuery를 사용한다
			// select를 제외한 다른 쿼리문은 executeUpdate()를 사용한다.
			// executeUpdate는 int형태의 값을 돌려준다. 성공1, 실패0 또는 에러
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
		
	}
	public int deleteData(String userId) {
		int result=0;
		// delete from newst where id='aaa';
		String sql = "delete from newst where id='"+userId+"';";
		//String sql = "delete from newst where id=?";
		try {
			Connection con = DriverManager.getConnection(url,id,pwd);
			PreparedStatement ps = con.prepareStatement(sql);
			//ps.setString(1,userId); // sql ?쿼리문일때 사용
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
		
	}
	public int updateData(String userId, String name, int age) {
		//update newst set name = '길이다',age=30 where id='111';
		int result = 0;
		
		String sql = "update newst set name = ?, age=? where id=? ";
		
		try {
			Connection con = DriverManager.getConnection(url, id, pwd);
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(3, userId);
			ps.setString(1, name);
			ps.setInt(2, age);
			
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
		
	}
}
