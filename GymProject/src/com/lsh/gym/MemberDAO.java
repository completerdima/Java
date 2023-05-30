package com.lsh.gym;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.lsh.common.DAO;

public class MemberDAO extends DAO {
    private static MemberDAO instance = null;
    
    private MemberDAO() {
    }

    public static MemberDAO getInstance() {
        if (instance == null) {
            instance = new MemberDAO();
        }
        return instance;
    }

    
    public Member login(String id) {
        Member mem = null;
        try {
            conn();

            //sql문 실행
            String sql = "SELECT * FROM gym2 WHERE member_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                mem = new Member();
                mem.setMemberId(rs.getString("member_id"));
                mem.setMemberPw(rs.getString("member_pw"));
                mem.setMemberPhone(rs.getInt("member_phone"));
                mem.setMemberNickname(rs.getString("member_nickname"));
                mem.setMemberName(rs.getString("member_name"));
                mem.setMemberAge(rs.getInt("member_age"));
                mem.setMemberEmail(rs.getString("member_email"));
                mem.setMemberWeight(rs.getString("member_weight"));
                mem.setMemberInbody(rs.getString("member_inbody"));
                mem.setMemberStartDate(rs.getString("member_startdate"));
                mem.setMemberEndDate(rs.getString("member_enddate"));
                
//                // member_startdate 값이 null인 경우에 대한 처리
//                Date startDate = rs.getString("member_startdate");
//                if (startDate != null) {
//                    LocalDate localStartDate = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//                    mem.setMemberStartDate(localStartDate);
//                }
//
//                Date endDate = rs.getString("member_enddate");
//                if (endDate != null) {
//                    LocalDate localEndDate = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//                    mem.setMemberEndDate(localEndDate);
//                }

                mem.setGymCosNumber(rs.getString("gym_cosnumber"));
                mem.setGymTotalSales(rs.getInt("gym_totalsales"));
                mem.setGymGrade(rs.getString("gym_grade"));
                mem.setGymCheckDate(rs.getString("gym_checkdate"));
                
                
//                // gymCheckDate 날짜 데이터 처리
//                Date checkDate = rs.getString("gym_checkdate");
//                if (checkDate != null) {
//                    LocalDate localCheckDate = checkDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//                    mem.setGymCheckDate(localCheckDate);
//                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disconn();
        }

        return mem;
    }
    
    
    //회원가입시 전송
    public int insertGym(Member mem) {
    	 int result = 0;
         try {
             conn();
             String sql = "INSERT INTO gym2 (member_id, member_pw, member_phone, member_nickname, member_name, member_age, "
                     + "member_email, member_weight, member_inbody, member_startdate, member_enddate, gym_cosnumber, "
                     + "gym_totalsales, gym_grade, gym_checkdate) "
                     + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

             pstmt = conn.prepareStatement(sql);

             pstmt.setString(1, mem.getMemberId());
             pstmt.setString(2, mem.getMemberPw());
             pstmt.setInt(3, mem.getMemberPhone());
             pstmt.setString(4, mem.getMemberNickname());
             pstmt.setString(5, mem.getMemberName());
             pstmt.setInt(6, mem.getMemberAge());
             pstmt.setString(7, mem.getMemberEmail());
             pstmt.setString(8, mem.getMemberWeight());
             pstmt.setString(9, mem.getMemberInbody());
             pstmt.setString(10, mem.getMemberStartDate());
             pstmt.setString(11, mem.getMemberStartDate());
             pstmt.setString(12, mem.getMemberEndDate());
             
             
             
//             LocalDate startDate = mem.getMemberStartDate();
//             if (startDate != null) {
//                 pstmt.setString(10, java.sql.Date.valueOf(startDate));
//             } else {
//                 pstmt.setNull(10, java.sql.Types.DATE);
//             }
//
//             LocalDate endDate = mem.getMemberEndDate();
//             if (endDate != null) {
//                 pstmt.setString(11, java.sql.Date.valueOf(endDate));
//             } else {
//                 pstmt.setNull(11, java.sql.Types.DATE);
//             }
             

             pstmt.setString(12, mem.getGymCosNumber());
             pstmt.setInt(13, mem.getGymTotalSales());
             pstmt.setString(14, mem.getGymGrade());
             pstmt.setString(15, mem.getGymCheckDate());
//             LocalDate checkDate = mem.getGymCheckDate();
//             if (checkDate != null) {
//                 pstmt.setString(15, java.sql.Date.valueOf(checkDate));
//             } else {
//                 pstmt.setNull(15, java.sql.Types.DATE);
//             }

             result = pstmt.executeUpdate();
         } catch (Exception e) {
             e.printStackTrace();
         } finally {
             disconn();
         }
         return result;
     }
    
    //아이디, 비밀번호 찾기
    public String findPassword(String name, String email) {
        String password = null;
        try {
            conn(); // DB 연결 수행
            
            String sql = "SELECT member_pw FROM gym2 WHERE member_name = ? AND member_email = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                password = rs.getString("member_pw");
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disconn(); // DB 연결 해제
        }
        return password;
    }


    
    
  

    //관리자가 전체조회
    public List<Member> getMemberList() {
    	List<Member> list = new ArrayList<>();
		Member mem = null;
        
        try {
            conn();
            String sql = "SELECT member_id, member_phone, member_nickname, member_name, member_age, member_email, "
            		+ "member_weight, member_inbody, member_startdate, member_enddate, gym_cosnumber, gym_totalsales, gym_grade, gym_checkdate FROM gym2";
            pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                mem = new Member();
                mem.setMemberId(rs.getString("member_id"));
//                member.setMemberPw(rs.getString("member_pw"));
                mem.setMemberPhone(rs.getInt("member_phone"));
                mem.setMemberNickname(rs.getString("member_nickname"));
                mem.setMemberName(rs.getString("member_name"));
                mem.setMemberAge(rs.getInt("member_age"));
                mem.setMemberEmail(rs.getString("member_email"));
                mem.setMemberWeight(rs.getString("member_weight"));
                mem.setMemberInbody(rs.getString("member_inbody"));
//                mem.setMemberStartDate(rs.getString("member_startdate").toLocalDate());
//                mem.setMemberEndDate(rs.getString("member_enddate").toLocalDate());
                mem.setMemberStartDate(rs.getString("member_startdate"));
                mem.setMemberEndDate(rs.getString("member_enddate"));
                mem.setGymCosNumber(rs.getString("gym_cosnumber"));
                mem.setGymTotalSales(rs.getInt("gym_totalsales"));
                mem.setGymGrade(rs.getString("gym_grade"));
//                mem.setGymCheckDate(rs.getString("gym_checkdate").toLocalDate());   
                mem.setGymCheckDate(rs.getString("gym_checkdate"));
                
                list.add(mem);
            }
//            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disconn();
        }
        return list;
    }

  //관리자에 의한 개별회원 조회 (이름입력시)
  	public Member getMembername(String memberName) {
  		Member mem = null;
  		try {
  			conn();
  			String sql = "SELECT * FROM gym2 WHERE member_name = ?";
  			pstmt = conn.prepareStatement(sql);
  			pstmt.setString(1, memberName);
  			
  			rs = pstmt.executeQuery();
  			
  			if(rs.next()) {
  				mem = new Member();
  				mem.setMemberId(rs.getString("member_id"));
//                mem.setMemberPw(rs.getString("member_pw"));
                mem.setMemberPhone(rs.getInt("member_phone"));
                mem.setMemberNickname(rs.getString("member_nickname"));
                mem.setMemberName(rs.getString("member_name"));
                mem.setMemberAge(rs.getInt("member_age"));
                mem.setMemberEmail(rs.getString("member_email"));
                mem.setMemberWeight(rs.getString("member_weight"));
                mem.setMemberInbody(rs.getString("member_inbody"));
                
                
                mem.setMemberStartDate(rs.getString("member_startdate"));
                mem.setMemberEndDate(rs.getString("member_enddate"));
                
             // member_startdate 값이 null인 경우에 대한 처리
//                Date startDate = rs.getString("member_startdate");
//                if (startDate != null) {
//                    LocalDate localStartDate = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//                    mem.setMemberStartDate(localStartDate);
//                }
//
//                Date endDate = rs.getString("member_enddate");
//                if (endDate != null) {
//                    LocalDate localEndDate = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//                    mem.setMemberEndDate(localEndDate);
//                }

                mem.setGymCosNumber(rs.getString("gym_cosnumber"));
                mem.setGymTotalSales(rs.getInt("gym_totalsales"));
                mem.setGymGrade(rs.getString("gym_grade"));
                
                mem.setGymCheckDate(rs.getString("gym_checkdate"));
                
                // gymCheckDate 날짜 데이터 처리
//                Date checkDate = rs.getString("gym_checkdate");
//                if (checkDate != null) {
//                    LocalDate localCheckDate = checkDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//                    mem.setGymCheckDate(localCheckDate);
//                }
  			}
  		}catch(Exception e) {
  			e.printStackTrace();
  		}finally {
  			disconn();
  		}
  		return mem;
  	}

  
  	//관리자가 고객등록
  	
  	public int insertMember(Member mem) {
  		int result = 0;
  		try {
  			conn();
  			String sql ="INSERT INTO gym2 VALUES('?','?','?','?','?','?','?','?','?','?','?','?','?','?','?' ) ";
  			
  			
  			pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, mem.getMemberId());
            pstmt.setString(2, mem.getMemberPw());
            pstmt.setInt(3, mem.getMemberPhone());
            pstmt.setString(4, mem.getMemberNickname());
            pstmt.setString(5, mem.getMemberName());
            pstmt.setInt(6, mem.getMemberAge());
            pstmt.setString(7, mem.getMemberEmail());
            pstmt.setString(8, mem.getMemberWeight());
            pstmt.setString(9, mem.getMemberInbody());
            pstmt.setString(10,  mem.getMemberStartDate());
            pstmt.setString(11, mem.getMemberEndDate());
            pstmt.setString(12, mem.getGymCosNumber());
            pstmt.setInt(13, mem.getGymTotalSales());
            pstmt.setString(14, mem.getGymGrade());
            pstmt.setString(15, mem.getGymCheckDate());
           
            result = pstmt.executeUpdate();
  			
  		}catch(Exception e) {
  			e.printStackTrace();
  		}finally {
  			disconn();
  		}
  		return result;
  	}
  	
  	//정보수정
  	public int updateMember (Member mem) {
  		int result = 0;
  		try {
  			conn();
  			String sql = "UPDATE gym2 SET member_pw = ?, member_phone = ?, membr_nickname = ?, member_name = ? , "
  					+ "member_age = ? , member_email = ?, member_email = ?, "
  					+ "member_weight = ?, member_inbody = ?, member_startdate = ? , member_enddate = ?, gym_cosnumber = ? , gym_totalsales = ? , gym_grade = ? , gym_checkdate = ? "
  					+ "WHERE member_id = ? ";
  			
  			pstmt = conn.prepareStatement(sql);
  		
  			 pstmt.setString(1, mem.getMemberPw());
             pstmt.setInt(2, mem.getMemberPhone());
             pstmt.setString(3, mem.getMemberNickname());
             pstmt.setString(4, mem.getMemberName());
             pstmt.setInt(5, mem.getMemberAge());
             pstmt.setString(6, mem.getMemberEmail());
             pstmt.setString(7, mem.getMemberWeight());
             pstmt.setString(8, mem.getMemberInbody());
  			 pstmt.setString(9,  mem.getMemberStartDate());
  			 pstmt.setString(10,  mem.getMemberEndDate());
  			
             
//             LocalDate startDate = mem.getMemberStartDate();
//             if (startDate != null) {
//                 pstmt.setString(10, java.sql.Date.valueOf(startDate));
//             } else {
//                 pstmt.setNull(10, java.sql.Types.DATE);
//             }
//
//             LocalDate endDate = mem.getMemberEndDate();
//             if (endDate != null) {
//                 pstmt.setString(11, java.sql.Date.valueOf(endDate));
//             } else {
//                 pstmt.setNull(11, java.sql.Types.DATE);
//             }
             

             pstmt.setString(11, mem.getGymCosNumber());
             pstmt.setInt(12, mem.getGymTotalSales());
             pstmt.setString(13, mem.getGymGrade());
             pstmt.setString(14, mem.getGymCheckDate());
//             LocalDate checkDate = mem.getGymCheckDate();
//             if (checkDate != null) {
//                 pstmt.setString(15, java.sql.Date.valueOf(checkDate));
//             } else {
//                 pstmt.setNull(15, java.sql.Types.DATE);
//             }
             pstmt.setString(15, mem.getMemberId());
             
   			result =pstmt.executeUpdate();
   			             
  		}catch(Exception e) {
  			e.printStackTrace();
  		}finally {
  			disconn();
  		}
  		return result;
  	}
  	
  	
  	
  	
	//회원삭제 기능
  	public int deleteMember(int cosNumber) {
		int result = 0;
		try {
			conn();
			String sql =  "DELETE FROM gym2 WHERE gym_cosnumber = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, cosNumber);
			
			result = pstmt.executeUpdate();
			rs = pstmt.executeQuery();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			disconn();
		}
		return result;
	}
  	
  	
  	
  	//유저가 정보
  	public int updateUser (Member user) {
  		int result = 0;
  		try {
  			conn();
  			String sql = "UPDATE gym2 SET member_pw = ?, member_phone = ?, member_nickname = ?, member_name = ? , "
  					+ "member_age = ? , member_email = ?,"
  					+ "member_weight = ?, member_inbody = ?";
  			pstmt = conn.prepareStatement(sql);
  		
  			 pstmt.setString(1, user.getMemberPw());
             pstmt.setInt(2, user.getMemberPhone());
             pstmt.setString(3, user.getMemberNickname());
             pstmt.setString(4, user.getMemberName());
             pstmt.setInt(5, user.getMemberAge());
             pstmt.setString(6, user.getMemberEmail());
             pstmt.setString(7, user.getMemberWeight());
             pstmt.setString(8, user.getMemberInbody());
  			

   			result =pstmt.executeUpdate();
   			             
  		}catch(Exception e) {
  			e.printStackTrace();
  		}finally {
  			disconn();
  		}
  		return result;
  	}
   
  	
}
    
  
