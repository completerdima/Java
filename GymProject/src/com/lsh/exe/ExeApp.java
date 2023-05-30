package com.lsh.exe;

import java.util.Scanner;

import com.lsh.gym.MemberDAO;
import com.lsh.gym.MemberService;

public class ExeApp {
    private Scanner sc = new Scanner(System.in);
    private boolean go = true;
    private MemberService ms = new MemberService();

    public ExeApp() {
        run();
    }

    private void run() {
        while (go) {
            if (MemberService.memberInfo != null) {
                loginMenu();
            } else {
                logoutMenu();
            }
        }
    }

    private void logoutMenu() {
        System.out.println("1. 로그인 | 2. 회원가입 | 3. 아이디와 비밀번호 찾기 | 4. 종료");
        System.out.print("번호를 입력하세요: ");
        String menu = sc.nextLine();

        if (menu.equals("1")) {
            ms.login(); // 로그인 함수 호출
        } else if (menu.equals("2")) {
            ms.insertGym(); // 회원 가입 정보 등록 함수 호출
        } else if (menu.equals("3")) {
            // 아이디와 비밀번호 찾기 함수 호출
            System.out.print("이름을 입력하세요: ");
            String name = sc.nextLine();

            System.out.print("이메일을 입력하세요: ");
            String email = sc.nextLine();

            MemberDAO memberDao = MemberDAO.getInstance();
            String password = memberDao.findPassword(name, email);

            if (password != null) {
                System.out.println("비밀번호: " + password);
            } else {
                System.out.println("일치하는 정보가 없습니다.");
            }
        } else if (menu.equals("4")) {
            go = false;
            System.out.println("프로그램을 종료합니다.");
        } else {
            System.out.println("잘못된 번호를 입력하셨습니다.");
        }
    }

    private void loginMenu() {
        if (MemberService.memberInfo.getGymGrade() != null) {
            if (MemberService.memberInfo.getGymGrade().equals("A")) {
                System.out.println("1. 전체 회원 조회 | 2. 개별 회원 조회 | 3. 회원 정보 등록 | "
                        + "4. 회원 정보 수정 | 5. 회원 삭제 | 6. 회원 재방문률 조회 | 7. 매출 현황 조회 | 8. 종료");
                System.out.print("번호를 입력하세요: ");
                String menu = sc.nextLine();

                if (menu.equals("1")) {
                    // 전체 회원 조회 기능 구현
                	ms.getMemberList();
                } else if (menu.equals("2")) {
                    // 개별 회원 조회 기능 구현
                	ms.getMembername();
                } else if (menu.equals("3")) {
                    // 회원 정보 등록 기능 구현
                	ms.insertMember();
                } else if (menu.equals("4")) {
                    // 회원 정보 수정 기능 구현
                	ms.updateMember();
                } else if (menu.equals("5")) {
                    // 회원 삭제 기능 구현
                	ms.deleteMember();
                } else if (menu.equals("6")) {
                    // 회원 재방문률 조회 기능 구현
//                	ms.visitRate(); // 미완성 MemberDAO 설정을 안함
                } else if (menu.equals("7")) {
                    // 매출 현황 조회 기능 구현
                	
                } else if (menu.equals("8")) {
                    go = false;
                    System.out.println("프로그램을 종료합니다.");
                } else {
                    System.out.println("잘못된 번호를 입력하셨습니다.");
                }
            } else if (MemberService.memberInfo.getGymGrade().equals("P")
                    || MemberService.memberInfo.getGymGrade().equals("G")
                    || MemberService.memberInfo.getGymGrade().equals("N")) {
                System.out.println("1. 내정보 | 2. 로그아웃 | 3. 내정보 수정 | 4. 종료");
                String menu = sc.nextLine();

                if (menu.equals("1")) {
                    ms.confirmInfo(); // 내 정보 확인 기능 구현
                    
                } else if (menu.equals("2")) {
                    ms.logout(); // 로그아웃 기능 구현
                    
                } else if (menu.equals("3")) {
                    // 내 정보 수정 기능 구현
                	ms.updateUser();
                } else if (menu.equals("4")) {
                    go = false;
                    System.out.println("프로그램을 종료합니다.");
                } else {
                    System.out.println("잘못된 번호를 입력하셨습니다.");
                }
            }
        }
    }
}
