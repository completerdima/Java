package com.lsh.gym;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.sql.Date;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import javax.sql.DataSource;

public class MemberService {
	private Scanner sc = new Scanner(System.in);
    public static Member memberInfo = null;
    private MemberDAO memberDao = MemberDAO.getInstance();
    
    public void login() {
        System.out.print("아이디를 입력하세요> ");
        String id = sc.nextLine();
        
        System.out.print("비밀번호를 입력하세요> ");
        String pw = sc.nextLine();
        
        Member member = memberDao.login(id);
        
        if (member != null) {
            if (member.getMemberPw().equals(pw)) { // 입력한 데이터와 비교
                System.out.println("로그인되었습니다.");
                System.out.println(member.getMemberId() + "님, 환영합니다.");
                memberInfo = member;
            } else {
                System.out.println("비밀번호가 다릅니다.");
            }
        } else {
            System.out.println("해당 아이디가 존재하지 않습니다.");
        }
    }
    
    
    // 회원 가입
    public void insertGym() {
        System.out.println("======== 회원 가입하기 ========");
        String id = "";
        Member member = null;
        
        while (true) {
            System.out.print("아이디를 입력하세요> ");
            id = sc.nextLine();
            member = MemberDAO.getInstance().login(id);
            
            if (member != null) {
                System.out.println("이미 존재하는 아이디입니다.");
            } else {
                System.out.println("사용 가능한 아이디입니다.");
                break;
            }
        }
        
        System.out.print("비밀번호를 입력하세요> ");
        String pw = sc.nextLine();

        System.out.print("전화번호를 입력하세요> ");
        int phone = Integer.parseInt(sc.nextLine());
        //sc.nextLine(); // 개행 문자(Enter) 처리를 위해 추가

        System.out.print("닉네임을 입력하세요> ");
        String nickname = sc.nextLine();

        System.out.print("이름을 입력하세요> ");
        String name = sc.nextLine();

        System.out.print("나이를 입력하세요> ");
        int age = Integer.parseInt(sc.nextLine());
        

        System.out.print("이메일 주소를 입력하세요> ");
        String email = sc.nextLine();

        System.out.print("몸무게를 입력하세요> ");
        String weight = sc.nextLine();

        System.out.print("인바디 점수를 입력하세요> ");
        String inbody = sc.nextLine();
        
        member = new Member();
        member.setMemberId(id);
        member.setMemberPw(pw);
        member.setMemberPhone(phone);
        member.setMemberNickname(nickname);
        member.setMemberName(name);
        member.setMemberAge(age);
        member.setMemberEmail(email);
        member.setMemberWeight(weight);
        member.setMemberInbody(inbody);
        
        member.setGymGrade("N");

        int result = memberDao.insertGym(member);

        if (result > 0) {
            System.out.println("회원 가입이 완료되었습니다.");
        } else {
            System.out.println("회원 가입에 실패했습니다.");
        }
    }
    
    //로그아웃
    public void logout() {
		memberInfo = null;
		System.out.println("로그아웃 완료");
	}

  //관리자가 전체 조회 가능한 정보
    public void getMemberList() {
    	List<Member> list = MemberDAO.getInstance().getMemberList();
		System.out.println("전체 회원 조회");
		for(int i  = 0; i<list.size(); i++) {
			System.out.println("아이디 : " +list.get(i).getMemberId());
//			System.out.println("비밀번호 : " +list.get(i).getMemberPw());
			System.out.println("전화번호 : " +list.get(i).getMemberPhone());
			System.out.println("이름 : " + list.get(i).getMemberName());
			System.out.println("닉네임 : " + list.get(i).getMemberNickname());
			System.out.println("나이 : " + list.get(i).getMemberAge());
			System.out.println("이메일주소 : " + list.get(i).getMemberEmail());
			System.out.println("체중 : " + list.get(i).getMemberWeight());
			System.out.println("인바디 : " + list.get(i).getMemberInbody());
			System.out.println("시작일 : " + list.get(i).getMemberStartDate());
			System.out.println("시작일 : " + list.get(i).getMemberEndDate());
			System.out.println("회원번호 : " + list.get(i).getGymCosNumber());
			System.out.println("매출액 : " + list.get(i).getGymTotalSales());
			System.out.println("회원등급 :" + list.get(i).getGymGrade());
			System.out.println("출석일 : " + list.get(i).getGymCheckDate() );
			System.out.println("(☞ﾟヮﾟ)☞===============☜(ﾟヮﾟ☜)");
		}
		
	}
    
  //관리자에 의한 개별회원 조회
    public void getMembername() {
  		
  		//고객번호에서 이름으로 선회했음
  		System.out.println("(☞ﾟヮﾟ)☞ =====회원 정보 조회===== ☜(ﾟヮﾟ☜)");
  		System.out.println("회원의 이름을 입력하세요>");
  		String name =sc.nextLine();
  		
  		//검색결과
  		Member mem = MemberDAO.getInstance().getMembername(name);
  		
  		
//  		Member mem = MemberDAO.getInstance().login(memberInfo.getMemberId());
  		
  		if(mem == null) {
  			System.out.println("등록되지 않은 고객입니다");
  		}else {
  		
  		
  		System.out.println("아이디 : " + mem.getMemberId());
//  		System.out.println("비밀번호 : " + mem.getMemberPw());
  		System.out.println("전화번호 : " + mem.getMemberPhone());
  		System.out.println("닉네임 : " + mem.getMemberNickname());
  		System.out.println("이름 : " + mem.getMemberName());
  		System.out.println("나이 : " + mem.getMemberAge());
  		System.out.println("이메일주소 : " + mem.getMemberEmail());
  		System.out.println("체중 : " + mem.getMemberWeight());
  		System.out.println("인바디점수 :" + mem.getMemberInbody());
  		System.out.println("시작일 : " + mem.getMemberStartDate());
  		System.out.println("종료일 : " + mem.getMemberEndDate());
  		System.out.println("고객번호 : " + mem.getGymCosNumber());
  		System.out.println("고객매출액 : " + mem.getGymTotalSales());
  		System.out.println("고객등급 : " + mem.getGymGrade());
  		System.out.println("출석일 : " + mem.getGymCheckDate());
  		}
    }
    
  //사용자가 확인 가능한 정보
  	public void confirmInfo() {
  		Member mem = MemberDAO.getInstance().login(memberInfo.getMemberId());
  		
  		System.out.println("(☞ﾟヮﾟ)☞ =====회원 정보 조회===== ☜(ﾟヮﾟ☜)");
  		
  		System.out.println("아이디 : " + mem.getMemberId());
  		System.out.println("비밀번호 : " + mem.getMemberPw());
  		System.out.println("전화번호 : " + mem.getMemberPhone());
  		System.out.println("이름 : " + mem.getMemberName());
  		System.out.println("닉네임 : " + mem.getMemberNickname());
  		System.out.println("나이 : " + mem.getMemberAge());
  		System.out.println("이메일주소 : " + mem.getMemberEmail());
  		System.out.println("체중 : " + mem.getMemberWeight());
  		System.out.println("시작일 : " + mem.getMemberStartDate());
  		System.out.println("종료일 : " + mem.getMemberEndDate());
  		
  		if(mem.getGymGrade().equals("P")) {
  			System.out.println("프리미엄등급");
  		}else if(mem.getGymGrade().equals("G")) {
  			System.out.println("골드등급"); 		
  		}else if(mem.getGymGrade().equals("N")) {
  			System.out.println("노멀등급");
  		}else {
  			
  		}
  	}
  	
  	//회원삭제
  	public void deleteMember() {
  	    System.out.println("=== 회원 삭제 ===");
  	    System.out.println("삭제할 회원의 고객 번호를 입력하세요:");
  	    int cosNumber = sc.nextInt();
  	    
  	    int result = MemberDAO.getInstance().deleteMember(cosNumber);
  	    
  	    if (result > 0) {
  	        System.out.println("회원이 삭제되었습니다.");
  	    } else {
  	        System.out.println("회원 삭제에 실패했습니다.");
  	    }
	
	}

  
  	
  	//관리자가 고객 등록
  	public void insertMember() {
  		Member mem = new Member();

  		System.out.println(" ==== 매장 및 회원정보 등록 ==== ");
  		
  		System.out.println("회원 아이디를 입력하세요>");
  		mem.setMemberId(sc.nextLine());
  		
  		System.out.println("회원 비밀번호를 입력하세요>");
  		mem.setMemberPw(sc.nextLine());
  		
  		System.out.println("회원 전화번호를 입력하세요>");
  		mem.setMemberPhone(Integer.parseInt(sc.nextLine()));
  		
  		System.out.println("회원 닉네임을 입력하세요>");
  		mem.setMemberNickname(sc.nextLine());
  		
  		System.out.println("회원 이름을 입력하세요>");
  		mem.setMemberName(sc.nextLine());
  		
  		System.out.println("회원 나이를 입력하세요>");
  		mem.setMemberAge(Integer.parseInt(sc.nextLine()));
  		
  		System.out.println("회원 이메일 주소를 입력하세요>");
  		mem.setMemberEmail(sc.nextLine());
  		
  		System.out.println("회원 몸무게를 입력하세요>");
  		mem.setMemberWeight(sc.nextLine());
  		
  		System.out.println("회원 인바디 점수를 입력하세요>");
  		mem.setMemberInbody(sc.nextLine());
  		
  		System.out.println("회원의 헬스장 시작일을 입력하세요 ex)2011-11-20>");
  		mem.setMemberStartDate(sc.nextLine());
  		//local date에 맞게 설정이 안됨
  		
  		System.out.println("회원의 헬스장 종료일을 입력하세요 ex)2011-11-20>");
  		mem.setMemberEndDate(sc.nextLine());
  		
  		System.out.println("고객의 회원번호를 만들어주세요");
  		mem.setGymCosNumber(sc.nextLine());
  		
  		System.out.println("회원의 헬스장 매출액을 입력하세요>");
  		mem.setGymTotalSales(Integer.parseInt(sc.nextLine()));
  		
  		System.out.println("회원의 헬스장 등급을 입력하세요>");
  		mem.setGymGrade(sc.nextLine());
  		
  		int result = MemberDAO.getInstance().insertGym(mem);
  		
  		if(result > 0) {
  			System.out.println("정보 등록 완료");
  		}else {
  			System.out.println("정보 입력 실패");
  		}
  		
  	}
  	
  	//회원 정보수정
  	public void updateMember() {
  		Member mem = new Member();
  		System.out.println("===회원 정보 수정===");
  		
  		System.out.println("회원 아이디를 입력하세요");
  		mem.setMemberId(sc.nextLine());
  		
  		System.out.println("회원 비밀번호를 입력하세요>");
  		mem.setMemberPw(sc.nextLine());
  		
  		System.out.println("회원 전화번호를 입력하세요>");
  		mem.setMemberPhone(Integer.parseInt(sc.nextLine()));
  		
  		System.out.println("회원 닉네임을 입력하세요>");
  		mem.setMemberNickname(sc.nextLine());
  		
  		System.out.println("회원 이름을 입력하세요>");
  		mem.setMemberName(sc.nextLine());
  		
  		System.out.println("회원 나이를 입력하세요>");
  		mem.setMemberAge(Integer.parseInt(sc.nextLine()));
  		
  		System.out.println("회원 이메일 주소를 입력하세요>");
  		mem.setMemberEmail(sc.nextLine());
  		
  		System.out.println("회원 몸무게를 입력하세요>");
  		mem.setMemberWeight(sc.nextLine());
  		
  		System.out.println("회원 인바디 점수를 입력하세요>");
  		mem.setMemberInbody(sc.nextLine());
  		
  		System.out.println("회원의 헬스장 시작일을 입력하세요 ex)2011-11-20>");
  		mem.setMemberStartDate(sc.nextLine());
  		//local date에 맞게 설정이 안됨
  		
  		System.out.println("회원의 헬스장 종료일을 입력하세요 ex)2011-11-20>");
  		mem.setMemberEndDate(sc.nextLine());
  		
  		System.out.println("고객의 회원번호를 만들어주세요");
  		mem.setGymCosNumber(sc.nextLine());
  		
  		System.out.println("회원의 헬스장 매출액을 입력하세요>");
  		mem.setGymTotalSales(Integer.parseInt(sc.nextLine()));
  		
  		System.out.println("회원의 헬스장 등급을 입력하세요>");
  		mem.setGymGrade(sc.nextLine());
  		
  		int result = MemberDAO.getInstance().updateMember(mem);
  		
  		if(result > 0) {
  			System.out.println("수정완료");
  		}else {
  			System.out.println("수정실패");
  		}
  	}
        
  	
//  	//고객 재방문율
//  	public void visitRate() {
//  		System.out.println("=== 고객 재방문율 ===");
//  		System.out.println("방문율을 측정할 시작일을 입력하세요(yyyy-mm-dd)");
//  		Date startDateStr = Date.valueOf(sc.nextLine());
//  		
//  		System.out.println("방문율을 측정할 종료일을 입력하세요(yyyy-mm-dd)");
//  		Date endDateStr = Date.valueOf(sc.nextLine());
//  		
//  
//  	    
//  	  List<Member> memberList = memberDao.getMemberList();
//      int totalMembers = memberList.size();
//      int revisitedMembers = 0;
//
//      for (Member member : memberList) {
//          Date memberStartDate = member.getMemberStartDate();
//          Date memberEndDate = member.getMemberEndDate();
//
//          if (memberStartDate != null && memberEndDate != null &&
//              memberStartDate.before(endDate) && memberEndDate.after(startDate)) {
//              revisitedMembers++;
//          }
//      }
//
//      double revisitRate = (double) revisitedMembers / totalMembers * 100;
//
//      System.out.println("Revisit Rate: " + revisitRate + "%");
//  }
  	
  	
        
     /*   // 시작일과 종료일 설정
        LocalDate startDate = LocalDate.now();
        mem.setMemberStartDate(startDate);
        LocalDate endDate = calculateEndDate(startDate);
        mem.setMemberEndDate(endDate);
        
        // 등급 결정
        String grade = determineGrade();
        mem.setGymGrade(grade);
        
        // 임의의 회원번호 생성 및 설정
        String cosNumber = generateCosNumber();
        mem.setGymCosNumber(cosNumber);
        
        // 매출 정보 설정
        int totalSales = calculateSales();
        mem.setGymTotalSales(totalSales);
        
        */
    
//    // 종료일 계산 로직 구현
//    private LocalDate calculateEndDate(LocalDate startDate) {
//        // 로직 구현
//    }
//    
//    // 등급 결정 로직 구현
//    private String determineGrade() {
//        // 로직 구현
//    }
//    
//    // 임의의 회원번호 생성 로직 구현
//    private String generateCosNumber() {
//        // 로직 구현
//    }
//    
//    // 매출 계산 로직 구현
//    private int calculateSales() {
//        // 로직 구현
//    }

//  	 public Date transformDate(String date)
//     {
//         SimpleDateFormat beforeFormat = new SimpleDateFormat("yyyymmdd");
//         
//         // Date로 변경하기 위해서는 날짜 형식을 yyyy-mm-dd로 변경해야 한다.
//         SimpleDateFormat afterFormat = new SimpleDateFormat("yyyy-mm-dd");
//         
//         java.util.Date tempDate = null;
//         
//         try {
//             // 현재 yyyymmdd로된 날짜 형식으로 java.util.Date객체를 만든다.
//             tempDate = beforeFormat.parse(date);
//         } catch (ParseException e) {
//             e.printStackTrace();
//         }
//         
//         // java.util.Date를 yyyy-mm-dd 형식으로 변경하여 String로 반환한다.
//         String transDate = afterFormat.format(tempDate);
//         
//         // 반환된 String 값을 Date로 변경한다.
//         Date d = Date.valueOf(transDate);
//         
//         return d;
//     }
// }

  	
  //회원 정보수정
  	public void updateUser() {
  		Member user = new Member();
  		System.out.println("===회원 정보 수정===");
  		
  		
  		System.out.println("회원 비밀번호를 입력하세요>");
  		user.setMemberPw(sc.nextLine());
  		
  		System.out.println("회원 전화번호를 입력하세요>");
  		user.setMemberPhone(Integer.parseInt(sc.nextLine()));
  		
  		System.out.println("회원 닉네임을 입력하세요>");
  		user.setMemberNickname(sc.nextLine());
  		
  		System.out.println("회원 이름을 입력하세요>");
  		user.setMemberName(sc.nextLine());
  		
  		System.out.println("회원 나이를 입력하세요>");
  		user.setMemberAge(Integer.parseInt(sc.nextLine()));
  		
  		System.out.println("회원 이메일 주소를 입력하세요>");
  		user.setMemberEmail(sc.nextLine());
  		
  		System.out.println("회원 몸무게를 입력하세요>");
  		user.setMemberWeight(sc.nextLine());
  		
  		System.out.println("회원 인바디 점수를 입력하세요>");
  		user.setMemberInbody(sc.nextLine());
  		
  		
  		int result = MemberDAO.getInstance().updateUser(user);
  		
  		if(result > 0) {
  			System.out.println("수정완료");
  		}else {
  			System.out.println("수정실패");
  		}
  	}
  	
  	

}



