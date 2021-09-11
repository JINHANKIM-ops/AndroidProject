package dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import vo.ReservationCheckVO;
import vo.VincentVO;

public class DetailInfoDAO {
	
	SqlSession sqlSession;
	
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	//회줜정보 등록
	public String register( VincentVO vo ) {
			
			try {
				sqlSession.insert("v.insert_vincent", vo);
				return "{res:[{'result':'success'}]}";
				
			}catch (Exception e) {
				return "{res:[{'result':'fail'}]}";
			}
			
		}
	
	
	//로그인
	public String login(VincentVO vo) {
		
		VincentVO vinuser = sqlSession.selectOne("v.login_vincent",vo);
		
		if( vinuser.getEmail()  == null && vinuser.getPwd() == null) {
				return "{res:[{'result':'fail'}]}";
			}else{
				return "{res:[{'result':'success'}]}";
			
			}
		}
	
	//Id, Pwd 찾기
	public String find(VincentVO vo) {
		
		VincentVO vinuser = sqlSession.selectOne("v.find_vincent", vo);
				
			if( vinuser.getEmail()  == null && vinuser.getName() == null ) {
					return "{res:[{'result':'fail'}]}";
				}else{
					return "{res:[{'result':'success'}]}";		
				}
			}
	
	//티켓 에매 정보 저장
	public int reservation_insert(ReservationCheckVO vo) {
		
		try{
			int res = sqlSession.insert("v.reservation_insert", vo);
			return res;		
		}catch (Exception e) {
			System.out.println("err:" + e.getMessage());
		}
		return 0;
	}
	
	//이메일 의 인덱스
	public List<VincentVO> selectOne(String email) {
		List<VincentVO> vo = sqlSession.selectList("v.selectOne", email);
		System.out.println("vo:"+vo);
		return vo;
	}
	//예약내역의 인덱스
	public List<ReservationCheckVO> selectIdx(String id) {
		List<ReservationCheckVO> r_vo = sqlSession.selectList("v.selectIdx", id);
		System.out.println("r_vo:"+r_vo);

		return r_vo;
	}
	
	//티켓 예매 내역
	public List<ReservationCheckVO> reservation_list(HashMap<String, Integer> h) {
		System.out.println("r_vo:"+h.get("idx"));
		System.out.println("vo:"+ h.get("r_idx"));
		
		List<ReservationCheckVO> list = sqlSession.selectList("v.reservation_list", h);
		return list;
	}
	
	//예약취소
	public int reservation_delete(ReservationCheckVO vo) {
			
		int res = sqlSession.delete("v.reservation_delete", vo);
		System.out.println("res = " + res);
		return res;
	}
		
}
