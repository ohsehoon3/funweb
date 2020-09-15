package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.AttachfileVo;
import com.example.mapper.AttachfileMapper;

import lombok.extern.java.Log;

@Log
@Service
@Transactional
public class AttachfileService {
	
	@Autowired
	private AttachfileMapper attachMapper;
	
	public AttachfileVo getAttachfileByUuid(String uuid) {
		AttachfileVo vo = attachMapper.getAttachfileByUuid(uuid);;
		return vo;
	}
	
	public List<AttachfileVo> getAttachfilesByBno(int bno) {
		List<AttachfileVo> list = null;
		
		list = attachMapper.getAttachfilesByBno(bno);
		
		return list;
	}
	
	// 첨부파일정보 한개 추가
	public void insert(AttachfileVo vo) {
		attachMapper.insert(vo);
	} // insert
	
	
	// 첨부파일정보 여러개 추가
	public void insert(List<AttachfileVo> list) {
		for (AttachfileVo vo : list) {
			this.insert(vo);
		}
	} // insert
	
	
	public void deleteAttachfilesByBno(int bno) {
		attachMapper.deleteAttachfilesByBno(bno);
	}
	
	
	public void deleteAttachfileByUuid(String uuid) {
		attachMapper.deleteAttachfileByUuid(uuid);
	}
	
	
	public void deleteAttachfilesByUuids(List<String> uuids) {
		attachMapper.deleteAttachfilesByUuids(uuids);
	}
}