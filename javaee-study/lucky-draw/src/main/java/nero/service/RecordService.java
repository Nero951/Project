package nero.service;

import nero.mapper.RecordMapper;
import nero.model.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RecordService {
    @Autowired
    private RecordMapper recordMapper;
    @Transactional
    public void add(Integer awardId, List<Integer> memberIds) {
        //批量更新：1.循环遍历中更新 2.Mybatis批量操作
        for(Integer memberId : memberIds){
            Record record = new Record();
            record.setMemberId(memberId);
            record.setAwardId(awardId);
            recordMapper.insertSelective(record);
        }
    }

    @Transactional
    public void deleteByMemberId(Integer id) {
        Record r = new Record();
        r.setMemberId(id);
        recordMapper.deleteByCondition(r);
    }

    @Transactional
    public void deleteByAwardId(Integer id) {
        Record r = new Record();
        r.setAwardId(id);
        recordMapper.deleteByCondition(r);
    }
}
