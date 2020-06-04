package com.hyva.hotel.mapper;

import com.hyva.hotel.entities.State;
import com.hyva.hotel.pojo.StatePojo;

import java.util.ArrayList;
import java.util.List;

public class StateMapper {
    public static State mapStatePojoToEntity(StatePojo statePojo){
        State state = new State();
        state.setStateCode(statePojo.getStateCode());
        state.setId(statePojo.getId());
        state.setStateName(statePojo.getStateName());
        state.setVehicleSeries(statePojo.getVehicleSeries());
        state.setStatus(statePojo.getStatus());
        return state;
    }

    public static List<StatePojo> mapStateEntityToPojo(List<State> stateList){
        List<StatePojo> list=new ArrayList<>();
        for(State state:stateList) {
            StatePojo statePojo = new StatePojo();
            statePojo.setId(state.getId());
            statePojo.setStateCode(state.getStateCode());
            statePojo.setStateName(state.getStateName());
            statePojo.setVehicleSeries(state.getVehicleSeries());
            statePojo.setStatus(state.getStatus());
            statePojo.setCountry(state.getCountryName());
            list.add(statePojo);
        }
        return list;
    }
}
