package com.service;

import java.util.List;

import com.entity.BoundInfo;

public interface BoundInfoService {
	public List<BoundInfo> getAll();

	public BoundInfo getByOpenId(String openId);
}
