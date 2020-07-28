package com.hubu.myFirstSSM.service;

import com.hubu.myFirstSSM.mapper.PropertyValueMapper;
import com.hubu.myFirstSSM.pojo.Product;
import com.hubu.myFirstSSM.pojo.Property;
import com.hubu.myFirstSSM.pojo.PropertyValue;
import com.hubu.myFirstSSM.pojo.PropertyValueExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyValueServiceImpl implements PropertyValueService {
    @Autowired
    PropertyService propertyService;

    @Autowired
    PropertyValueMapper propertyValueMapper;

    @Override
    public void init(Product p) {

        List<Property> pts = propertyService.list(p.getCid());
        for (Property pt: pts) {
            PropertyValue pv = get(p.getId(),pt.getId());
            if(null == pv){
                pv = new PropertyValue();
                pv.setPid(p.getId());
                pv.setPtid(pt.getId());
                propertyValueMapper.insert(pv);
            }
        }
//        List<PropertyValue> pts = pvs.list(p.getId());
//        for (PropertyValue p : pts) {
//            if(null == p){
//                p.setPid(p.getId());
//                //禁止套娃
//                p.setPtid(p.getPid());
//            }
//        }
    }

    @Override
    public void update(PropertyValue pv) {
        propertyValueMapper.updateByPrimaryKey(pv);
    }

    @Override
    public PropertyValue get(int pid, int ptid) {
        PropertyValueExample example = new PropertyValueExample();
        example.createCriteria().andPidEqualTo(pid).andPtidEqualTo(ptid);
        List<PropertyValue> pvs = propertyValueMapper.selectByExample(example);
        if(pvs.isEmpty())
            return null;

        return pvs.get(0);
    }

    @Override
    public List<PropertyValue> list(int pid) {
        PropertyValueExample example = new PropertyValueExample();
        example.createCriteria().andPidEqualTo(pid);
        example.setOrderByClause("id asc");
        List<PropertyValue> pvs = propertyValueMapper.selectByExample(example);
        for (PropertyValue pv : pvs) {
            Property property = propertyService.get(pv.getPtid());
            pv.setProperty(property);
        }
        return pvs;
    }
}
