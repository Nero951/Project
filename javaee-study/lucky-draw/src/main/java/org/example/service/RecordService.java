package org.example.service;

import org.example.mapper.RecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecordService {
    @Autowired
    private RecordMapper recordMapper;

    //批量插入抽奖记录
    public int add(List<Integer> membersId, Integer awardId) {
        return recordMapper.batchInsert(membersId, awardId);
    }

    public int deleteByMemberId(Integer memberId) {
        return recordMapper.deleteByMemberId(memberId);
    }

    public int deleteByAwardId(Integer id) {
        return recordMapper.deleteByAwardId(id);
    }

    public int deleteByUserId(Integer id) {
        return recordMapper.deleteByUserId(id);
    }
}
