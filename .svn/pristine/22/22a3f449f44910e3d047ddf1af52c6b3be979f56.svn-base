package com.hyva.hotel.mapper;

import com.hyva.hotel.entities.FormSetUp;
import com.hyva.hotel.pojo.FormSetUpPojo;

import java.util.ArrayList;
import java.util.List;

public class FormSetupMapper {
    public static FormSetUp mapFormSetUpPojoToEntity(FormSetUpPojo formSetUpPojo) {
        FormSetUp formSetUp = new FormSetUp();
        formSetUp.setFormsetupId(formSetUpPojo.getFormsetupId());
        formSetUp.setTypeprefix(formSetUpPojo.getTypeprefix());
        formSetUp.setNextref(formSetUpPojo.getNextref());
        formSetUp.setTypename(formSetUpPojo.getTypename());
        formSetUp.setTransactionType(formSetUpPojo.getTransactionType());
        return formSetUp;
    }
    public static List<FormSetUpPojo> mapFormSetupEntityToPojo(List<FormSetUp> formSetUpList){
        List<FormSetUpPojo> list=new ArrayList<>();
        for(FormSetUp formSetUp:formSetUpList) {
            FormSetUpPojo formsetupDTO = new FormSetUpPojo();
            formsetupDTO.setNextref(formSetUp.getNextref());
            formsetupDTO.setFormsetupId(formSetUp.getFormsetupId());
            formsetupDTO.setTypename(formSetUp.getTypename());
            formsetupDTO.setTransactionType(formSetUp.getTransactionType());
            formsetupDTO.setTypeprefix(formSetUp.getTypeprefix());
            list.add(formsetupDTO);
        }
        return list;
    }
}