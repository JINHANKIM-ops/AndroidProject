package com.korea.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import dao.DetailInfoDAO;
import vo.ReservationCheckVO;
import vo.VincentVO;

@Controller
public class Vcontroller {
	
	DetailInfoDAO infoDao;
	
	public void setInfoDao(DetailInfoDAO infoDao) {
		this.infoDao = infoDao;
	}
	
	//회원가입
	@RequestMapping("/register.do")
	@ResponseBody //결과 Ajax로 return
	public String register( VincentVO vo ) {
		return infoDao.register(vo);
	}
	
	//로그인
	@RequestMapping("/login.do")
	@ResponseBody
	public String login(VincentVO vo) {
		return infoDao.login(vo);
	}
	
	//Id, Pwd 찾기
	@RequestMapping("/find.do")
	@ResponseBody
	public String find(VincentVO vo) {
		return infoDao.find(vo);
	}
	
	//티켓 예매 정보 저장
	@RequestMapping("/reservation.do")
	@ResponseBody
	public String reservation(ReservationCheckVO vo) {
		
		int res = infoDao.reservation_insert(vo);
		
		if(res == 1) {
			return "{res:[{'result':'srccess'}]}";
		}
		return "{res:[{'result':'fail'}]}";
		
	}
	
	//티켓 예매 내역
	@RequestMapping("/reservation_list.do")
	@ResponseBody
	public List<ReservationCheckVO> reservation_list(String id, String email) {
		
		List<ReservationCheckVO> r_vo = infoDao.selectIdx(id);
		List<VincentVO> vo = infoDao.selectOne(email);
		
		HashMap<String, Integer> h = new HashMap<String, Integer>();
		System.out.println(vo.get(0).getIdx());
		System.out.println(r_vo.get(0).getIdx());
		
		h.put("r_idx", r_vo.get(0).getIdx());
		h.put("idx", vo.get(0).getIdx());
		
		
		return infoDao.reservation_list(h);
	}
	
	//예매 취소
	@RequestMapping("/reservation_del.do")
	@ResponseBody
	public String reservation_del(ReservationCheckVO vo) {
		int res = infoDao.reservation_delete(vo);
		if(res == 1) {
			return "{res:[{'result':'srccess'}]}";
		}
		return "{res:[{'result':'fail'}]}";
		
	}
	
}
