package com.hyva.hotel.service;

import com.hyva.hotel.entities.*;

import com.hyva.hotel.pojo.*;
import com.hyva.hotel.mapper.*;
import com.hyva.hotel.respositories.*;
import com.hyva.hotel.util.ObjectMapperUtils;
//import com.sun.xml.internal.bind.v2.runtime.reflect.Lister;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.html.WebColors;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.draw.LineSeparator;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Struct;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;


@Component
public class HotelService {

    @Autowired
    BsUserRepository bsUserRepository;

    @Autowired
    HotelRoomTypesRepository hotelRoomTypesRepository;
    @Autowired
    HotelRoomsRepository hotelRoomsRepository;

    @Autowired
    HotelUserRepository hotelUserRepository;
    @Autowired
    ServicesRepository servicesRepository;
    @Autowired
    FormSetupRepository formSetupRepository;
    @Autowired
    OrdersRepository ordersRepository;
    @Autowired
    HotelGuestsRepository hotelGuestsRepository;
    @Autowired
    StateRepository stateRepository;
    @Autowired
    CountryRepository countryRepository;
    @Autowired
    CityRepository cityRepository;
    @Autowired
    ConfiguratorRepository configuratorRepository;
    @Autowired
    BookingRepository bookingRepository;

    int paginatedConstants=5;

    public User userValidate(UserPojo userPojo) {
        User user = bsUserRepository.findByUserNameAndPasswordUserAndStatus(
                userPojo.getUserName(), userPojo.getPasswordUser(),"Active");
        if (user != null) {
            return user;
        } else {
            return null;
        }
    }

    public User saveUserDetails(UserPojo userPojo) {
        User user = null;
        user = bsUserRepository.findByEmail(userPojo.getEmail());
        if (user != null) {
            user = null;
        } else {
            user = UserMapper.mapPojoToEntity(userPojo);
            bsUserRepository.save(user);
        }
        return user;
    }


    public Configurator saveConfigurator(ConfiguratorPojo pojo) throws JSONException {
        Configurator configurator=null;
        List<Configurator> list=configuratorRepository.findAll();
        if(list.size()>0)
        configurator=list.get(0);
        if(configurator==null){
            configurator=new Configurator();
        }else {
            pojo.setId(configurator.getId());
        }
        configurator = ConfiguratorMapper.mapPojoToEntity(pojo);
        configuratorRepository.save(configurator);
        return configurator;
    }



    public RoomTypesPojo saveRoomTypes(RoomTypesPojo roomTypespojo) throws JSONException {
        List<RoomTypes> list=new ArrayList<>();
        if(roomTypespojo.getId()!=0) {
            list = hotelRoomTypesRepository.findByTitleAndIdNotIn(roomTypespojo.getTitle(), roomTypespojo.getId());
        }
        else {
            list=hotelRoomTypesRepository.findByTitle(roomTypespojo.getTitle());
        }
        if(list.size()==0){
            RoomTypes roomTypes = null;
            roomTypes = HotelRoomTypeMapper.mapPojoToEntity(roomTypespojo);
            hotelRoomTypesRepository.save(roomTypes);
            return roomTypespojo;
        }
        else{
            return null;
        }

    }


    public List<RoomsPojo> getRoomsList() {
        List<Rooms> rooms = new ArrayList<>();
        rooms = (List<Rooms>) hotelRoomsRepository.findAll();
        List<RoomsPojo> roomsPojo = ObjectMapperUtils.mapAll(rooms, RoomsPojo.class);
        for(RoomsPojo roomsPojo1:roomsPojo){
//            roomsPojo1.setFloor_Id(roomsPojo1.getFloorId().getId());
            roomsPojo1.setRoom_typeId(roomsPojo1.getRoomTypeId().getId());
        }
        return roomsPojo;
    }



    public void DeleteRooms(Long id){
        hotelRoomsRepository.delete(id);
    }

    public UserPojo getuserDetails(UserPojo userPojo){
        List<User>list=new ArrayList<>();
        if(userPojo.getUseraccountId()!=null){
            list=hotelUserRepository.findByUserNameAndUseraccountIdNotIn(userPojo.getUserName(), userPojo.getUseraccountId());
        }
        else {
            list=hotelUserRepository.findByUserName(userPojo.getUserName());
        }
        if(list.size()==0){
            User user = UserMapper.mapPojoToEntity(userPojo);
            hotelUserRepository.save(user);
            return userPojo;
        }else{
            return null;
        }

    }

    public List<UserPojo> getUserList() {
        List<User> user = (List<User>) hotelUserRepository.findAll();
        List<UserPojo> usersPojo1 = ObjectMapperUtils.mapAll(user, UserPojo.class);
        return usersPojo1;
    }


    public void getDeleteUser(Long useraccountId) {
        hotelUserRepository.delete(useraccountId);
    }

    public void getDeleteConfigurator(Long id) {
        configuratorRepository.delete(id);
    }

    public void getDeleteHotel(Long id) {
        bookingRepository.delete(id);
    }


    public List<ConfiguratorPojo> getConfiguratorList() {
        List<Configurator> configurators = (List<Configurator>) configuratorRepository.findAll();
        List<ConfiguratorPojo> configuratorPojos = ObjectMapperUtils.mapAll(configurators, ConfiguratorPojo.class);
        return configuratorPojos;
    }

    public ServicesPojo savePaidServices(ServicesPojo servicesPojo) throws JSONException {
        List<Services>list=new ArrayList<>();
        if(servicesPojo.getId()!=0){
            list=servicesRepository.findByTitleAndIdNotIn(servicesPojo.getTitle(),servicesPojo.getId());
        }
        else{
            list= servicesRepository.findByTitle(servicesPojo.getTitle());
        }
        if(list.size()==0){
            RoomTypes roomTypesObj = hotelRoomTypesRepository.findById(servicesPojo.getRoomTypeId());
            servicesPojo.setRoomTypes(roomTypesObj);
            Services services = ServiceMapper.mapPojoToEntity(servicesPojo);
            servicesRepository.save(services);
            return servicesPojo;
        }else {
            return null;
        }

    }
    public void getDeletePaidServices(Long id){
        servicesRepository.delete(id);
    }
    public List<ServicesPojo> getServicesList() {
        List<Services> services = (List<Services>) servicesRepository.findAll();
        List<ServicesPojo> servicesPojos = ObjectMapperUtils.mapAll(services, ServicesPojo.class);
        for(ServicesPojo servicesPojo1:servicesPojos){
           servicesPojo1.setRoomTypeId(servicesPojo1.getRoomTypes().getId());
        }
        return servicesPojos;
    }

    public List<ServicesPojo> getServiceListBasedOnRoomType(Long roomTypeId) {
        if (roomTypeId != null) {
            RoomTypes roomTypesObj = hotelRoomTypesRepository.findOne(roomTypeId);
            List<Services> services = (List<Services>) servicesRepository.findByRoomTypes(roomTypesObj);
            List<ServicesPojo> servicesPojos = ObjectMapperUtils.mapAll(services, ServicesPojo.class);
            return servicesPojos;
        }else {
            return null;
        }
    }





    public static String getNextRefInvoice(String prefix, String nextRef) {
        StringBuilder sb = new StringBuilder();
        return sb.append(prefix).append(nextRef).toString();
    }

    public List<StatePojo> getStateListBasedOnCountry(String country1){
        List<State> stateList=stateRepository.findAllByCountryNameAndStatus(country1,"Active");
        List<StatePojo> statePojos=StateMapper.mapStateEntityToPojo(stateList);
        return statePojos;
    }

    public List<CityPojo> getStateCity(String stateName){
        List<Cities> cities=cityRepository.findAllByState(stateName);
        List<CityPojo> cityPojos=CityMapper.mapCitiesEntityToPojo(cities);
        return cityPojos;
    }


    public State saveState(StatePojo statePojo) {
        State state1=new State();
        if(statePojo.getId()!=null){
            state1=stateRepository.findByStateNameAndIdNotIn(statePojo.getStateName(),statePojo.getId());
        }else {
            state1=stateRepository.findByStateName(statePojo.getStateName());
        }
        if(state1==null) {
            State state = StateMapper.mapStatePojoToEntity(statePojo);
            Country country = countryRepository.findByCountryName(statePojo.getCountry());
            if(country!=null)
            state.setCountryName(country.getCountryName());
            stateRepository.save(state);
            return state;
        }else {
            return null;
        }
    }


    public BasePojo getStateList(String status,BasePojo basePojo,String searchText) {
        List<State> list = new ArrayList<>();
        basePojo.setPageSize(5);
        Sort sort=new Sort(new Sort.Order(Sort.Direction.ASC,"stateName"));
        if(basePojo.isLastPage()==true){
            sort=new Sort(new Sort.Order(Sort.Direction.DESC,"stateName"));
        }
//        if(StringUtils.isEmpty(status)){
//            status="Active";
//        }
        State state=new State();
        Pageable pageable=new PageRequest(basePojo.getPageNo(),5,sort);
        if(basePojo.isNextPage()==true || basePojo.isFirstPage()==true){
            sort=new Sort(new Sort.Order(Sort.Direction.DESC,"stateName"));
        }
        if (!StringUtils.isEmpty(searchText)) {
            state=stateRepository.findFirstByStateCodeContainingOrStateNameContaining(searchText,searchText,sort);
            list = stateRepository.findAllByStateCodeContainingOrStateNameContaining(searchText,searchText,pageable);
        } else {
            state=stateRepository.findFirstBy(sort);
            list = stateRepository.findAllBy(pageable);
        }
        if(list.contains(state)){
            basePojo.setStatus(true);
        }else {
            basePojo.setStatus(false);
        }
        List<StatePojo> stateList =StateMapper.mapStateEntityToPojo(list);
        basePojo=calculatePagination(basePojo,stateList.size());
        basePojo.setList(stateList);
        return basePojo;
    }
    public BasePojo calculatePagination(BasePojo basePojo,int size){
        if(basePojo.isLastPage()==true){
            basePojo.setFirstPage(false);
            basePojo.setNextPage(true);
            basePojo.setPrevPage(false);
        }else if(basePojo.isFirstPage()==true){
            basePojo.setLastPage(false);
            basePojo.setNextPage(false);
            basePojo.setPrevPage(true);
            if(basePojo.isStatus()==true){
                basePojo.setLastPage(true);
                basePojo.setNextPage(true);
            }
        }else if(basePojo.isNextPage()==true){
            basePojo.setLastPage(false);
            basePojo.setFirstPage(false);
            basePojo.setPrevPage(false);
            basePojo.setNextPage(false);
            if(basePojo.isStatus()==true){
                basePojo.setLastPage(true);
                basePojo.setNextPage(true);
            }
        }else if(basePojo.isPrevPage()==true){
            basePojo.setLastPage(false);
            basePojo.setFirstPage(false);
            basePojo.setNextPage(false);
            basePojo.setPrevPage(false);
            if(basePojo.isStatus()==true){
                basePojo.setPrevPage(true);
                basePojo.setFirstPage(true);
            }
        }
        if(size==0){
            basePojo.setLastPage(true);
            basePojo.setFirstPage(true);
            basePojo.setNextPage(true);
            basePojo.setPrevPage(true);
        }
        return basePojo;
    }

    public StatePojo editState(String stateName) {
        State state = stateRepository.findByStateName(stateName);
        List<State> states = new ArrayList<>();
        states.add(state);
        StatePojo statePojo = StateMapper.mapStateEntityToPojo(states).get(0);
        return statePojo;
    }
   public CityPojo editCity(String name,String countryName,String state) {
        Cities cities = cityRepository.findByNameAndCountryAndState(name,countryName,state);
        List<Cities> cities1 = new ArrayList<>();
        cities1.add(cities);
        CityPojo cityPojo = CityMapper.mapCitiesEntityToPojo(cities1).get(0);
        return cityPojo;
    }

    public List<CountryPojo> getCountryList(String status) {
        List<Country> list = new ArrayList<>();
        if (StringUtils.isEmpty(status)) {
            list = (List<Country>) countryRepository.findAll();
        } else {
            list = countryRepository.findAllByStatus(status);
        }
        List<CountryPojo> country = CountryMapper.mapCountryEntityToPojo(list);
        return country;
    }
    public List<CityPojo> getCityList(String status) {
        List<Cities> list = new ArrayList<>();
        if (StringUtils.isEmpty(status)) {
            list = (List<Cities>) cityRepository.findAll();
        } else {
            list = cityRepository.findAllBy(status);
        }
        List<CityPojo> cityPojos = CityMapper.mapCitiesEntityToPojo(list);
        return cityPojos;
    }
    public void deleteState(String stateName) {
        stateRepository.delete(stateRepository.findByStateName(stateName));
    }
    public void deleteCity(String name,String countryName,String state) {
        cityRepository.delete(cityRepository.findByNameAndCountryAndState(name,countryName,state));
    }
    public Country saveCountry(CountryPojo countryPojo) {
        Country countries = new Country();
        if(countryPojo.getCountryId()!=null){
            countries=countryRepository.findByCountryNameAndCountryIdNotIn(countryPojo.getCountryName(),countryPojo.getCountryId());
        }
        else{
            countries=countryRepository.findByCountryName(countryPojo.getCountryName());
        }
        if(countries==null){
            Country country = CountryMapper.mapCountryPojoToEntity(countryPojo);
            countryRepository.save(country);
            return country;
        }else{
            return null;
        }

    }


    public Booking saveBooking(BookingPojo bookingPojo) {
        Booking booking = null;
//        if (holidayPojo.getId() != null) {
//            holiday = holidayRepositories.findAllByHolidayNameAndIdNotIn(holidayPojo.getHolidayName(), holidayPojo.getId());
//        } else {
//            holiday = holidayRepositories.findAllByHolidayName(holidayPojo.getHolidayName());
//        }
        Booking booking1 = BookingMapper.mapPojoToEntity(bookingPojo);
        bookingRepository.save(booking1);
        return booking1;

//        else {
//            return null;
//        }
    }


//    public Booking saveBooking(BookingPojo bookingPojo) {
//        Booking booking = new Booking();
////        if(bookingPojo.getBookingId()!=null){
////            booking=bookingRepository.findByGuestAndBookingIdNotIn(bookingPojo.getGuest(),bookingPojo.getBookingId());
////        }
////        else{
////            booking=bookingRepository.findByGuest(bookingPojo.getGuest());
////        }
//        if(booking==null){
//            Booking booking1 = BookingMapper.mapPojoToEntity(bookingPojo);
//            bookingRepository.save(booking1);
//            return booking1;
//        }
//        else
//            {
//            return booking;
//        }
//
//    }
    public Cities saveCity(CityPojo cityPojo) {
        Cities cities1 = new Cities();
        if(cityPojo.getId()!=null){
            cities1=cityRepository.findByNameAndCountryAndStateAndIdNotIn(cityPojo.getName(),cityPojo.getCountry(),cityPojo.getState(),cityPojo.getId());
        }
        else{
            cities1=cityRepository.findByNameAndCountryAndState(cityPojo.getName(),cityPojo.getCountry(),cityPojo.getState());
        }
        if(cities1==null){
            Cities cities = CityMapper.mapCityPojoToEntity(cityPojo);
            cityRepository.save(cities);
            return cities;
        }else{
            return null;
        }

    }
    public void deleteCountry(String countryName) {
        countryRepository.delete(countryRepository.findByCountryName(countryName));
    }

    public CountryPojo editCountry(String countryName) {
        Country country = countryRepository.findByCountryName(countryName);
        List<Country> countries = new ArrayList<>();
        countries.add(country);
        CountryPojo countryPojo = CountryMapper.mapCountryEntityToPojo(countries).get(0);
        return countryPojo;
    }


    public List<RoomTypesPojo> getRoomTypesList() {
        List<RoomTypes> roomTypes = new ArrayList<>();
        roomTypes = (List<RoomTypes>) hotelRoomTypesRepository.findAll();
        List<RoomTypesPojo> roomTypesPojo = ObjectMapperUtils.mapAll(roomTypes, RoomTypesPojo.class);
        return roomTypesPojo;
    }

    public List<OrdersPojo> getcheckinList(String status) {
        List<Orders> orders = new ArrayList<>();
        Sort sort=new Sort(new Sort.Order(Sort.Direction.DESC,"id"));
//        if (StringUtils.equalsIgnoreCase(status, "Normal")){
//            orders=ordersRepository.findByBookingstatus(status,sort);
//        }else{
//            orders=ordersRepository.findByAdvanceBooking(true,sort);
//        }
        List<OrdersPojo> ordersPojo = ObjectMapperUtils.mapAll(orders, OrdersPojo.class);
//        for(OrdersPojo ordersPojo1:ordersPojo){
//            RoomTypes roomTypes=hotelRoomTypesRepository.findOne(ordersPojo1.getRoom_type_id());
//            ordersPojo1.setRoomTypeName(roomTypes.getTitle());
//        }
        return ordersPojo;
    }
    public OrdersPojo getordersobj(Long id) {
        Orders orders = new Orders();
        orders = (Orders) ordersRepository.findOne(id);
        OrdersPojo ordersPojo = OrdersMapper.EntityToPojo(orders);
        return ordersPojo;
//        OrdersPojo ordersPojo = ObjectMapperUtils.mapAll(orders, Orders.class);
    }


    public List<Rooms> roomList(Long roomTypeId) {
        RoomTypes roomTypesObj = hotelRoomTypesRepository.findById(roomTypeId);
        List<Rooms> roomsList = hotelRoomsRepository.findByRoomTypeId(roomTypesObj);
        return roomsList;
    }

    public RoomTypes roomTypeObj(Long roomTypeId) {
        RoomTypes roomTypesObj = hotelRoomTypesRepository.findById(roomTypeId);
        return roomTypesObj;
    }

    public GuestsPojo saveguests(GuestsPojo guestsPojo) throws JSONException {
        List<Guests>list=new ArrayList<>();
        if(guestsPojo.getId()!=0) {
             list = hotelGuestsRepository.findByFirstnameAndLastnameAndIdNotIn(guestsPojo.getFirstname(), guestsPojo.getLastname(), guestsPojo.getId());
        }
        else{
            list=hotelGuestsRepository.findByFirstnameAndLastname(guestsPojo.getFirstname(),guestsPojo.getLastname());
        }
        if(list.size()==0){
            Guests guests = null;
            guests = HotelGuestsMapper.mapPojoToEntity(guestsPojo);
            hotelGuestsRepository.save(guests);
            return guestsPojo;
        }
        else {
            return null;
        }
    }


    public List<GuestsPojo> getGuestsList() {
        List<Guests> guests = new ArrayList<>();
        guests = (List<Guests>) hotelGuestsRepository.findAll();
        List<GuestsPojo> guestsPojo = ObjectMapperUtils.mapAll(guests, GuestsPojo.class);
        return guestsPojo;
    }

    public List<BookingPojo> getBookingList() {
        List<Booking> guests = new ArrayList<>();
        guests = (List<Booking>) bookingRepository.findAll();
        List<BookingPojo> guestsPojo = ObjectMapperUtils.mapAll(guests, BookingPojo.class);
        return guestsPojo;
    }

   public void getDeleteGuests(Long id){
        hotelGuestsRepository.delete(id);
   }


    public FormSetUp saveOrUpdateformsetup(FormSetUpPojo formSetUpPojo) {
        FormSetUp formSetUps =new FormSetUp();
        if(formSetUpPojo.getFormsetupId()!=null){
            formSetUps=formSetupRepository.findAllByTypenameAndFormsetupIdNotIn(formSetUpPojo.getTypename(),formSetUpPojo.getFormsetupId());

        }else{
            formSetUps=formSetupRepository.findAllByTypename(formSetUpPojo.getTypename());
        }
        if(formSetUps==null){
            FormSetUp formSetUp = FormSetupMapper.mapFormSetUpPojoToEntity(formSetUpPojo);
            formSetupRepository.save(formSetUp);
            return formSetUp;
        }else{
            return null;
        }

    }

    public List<FormSetUpPojo> getFormSetupList() {
        List<FormSetUp> list = new ArrayList<>();
        list = formSetupRepository.findAll();
        List<FormSetUpPojo> formsetupDTOS = FormSetupMapper.mapFormSetupEntityToPojo(list);
        return formsetupDTOS;
    }
    public FormSetUpPojo editFormsetupMethod(String formsetupName) {
        FormSetUp formSetUp = formSetupRepository.findAllByTypename(formsetupName);
        List<FormSetUp> formSetUpList = new ArrayList<>();
        formSetUpList.add(formSetUp);
        FormSetUpPojo formSetUpPojo = FormSetupMapper.mapFormSetupEntityToPojo(formSetUpList).get(0);
        return formSetUpPojo;
    }

    public BasePojo getPaginatedRoomtypesList(BasePojo basePojo,String searchText) {
        List<RoomTypes> list = new ArrayList<>();
        basePojo.setPageSize(paginatedConstants);
        Sort sort=new Sort(new Sort.Order(Sort.Direction.DESC,"id"));
        if(basePojo.isLastPage()==true){
            List<RoomTypes> list1=new ArrayList<>();
            if (!StringUtils.isEmpty(searchText)) {
                list1 = hotelRoomTypesRepository.findAllByTitleContaining(searchText);
            }else {
                list1=hotelRoomTypesRepository.findAll();
            }
            int size=list1.size()%paginatedConstants;
            int pageNo=list1.size()/paginatedConstants;
            if(size!=0){
                basePojo.setPageNo(pageNo);
            }else{
                basePojo.setPageNo(pageNo-1);
            }
        }
        RoomTypes roomTypes=new RoomTypes();
        Pageable pageable=new PageRequest(basePojo.getPageNo(),basePojo.getPageSize(),sort);
        if(basePojo.isNextPage()==true || basePojo.isFirstPage()==true){
            sort=new Sort(new Sort.Order(Sort.Direction.ASC,"id"));
        }
        if (!StringUtils.isEmpty(searchText)) {
            roomTypes=hotelRoomTypesRepository.findFirstByTitleContaining(searchText,sort);
            list = hotelRoomTypesRepository.findAllByTitleContaining(searchText,pageable);
        } else {
            roomTypes=hotelRoomTypesRepository.findFirstBy(sort);
            list = hotelRoomTypesRepository.findAllBy(pageable);
        }
        if(list.contains(roomTypes)){
            basePojo.setStatus(true);
        }else {
            basePojo.setStatus(false);
        }
        List<RoomTypesPojo> typesList = HotelRoomTypeMapper.mapEntityToPojo(list);
        basePojo=calculatePagination(basePojo,typesList.size());
        basePojo.setList(typesList);
        return basePojo;
    }
    public BasePojo getPaginatedRoomList(BasePojo basePojo,String searchText) {
        List<Rooms> list = new ArrayList<>();
        basePojo.setPageSize(paginatedConstants);
        Sort sort=new Sort(new Sort.Order(Sort.Direction.DESC,"id"));
        if(basePojo.isLastPage()==true){
            List<Rooms> list1=new ArrayList<>();
            if (!StringUtils.isEmpty(searchText)) {
                list1 = hotelRoomsRepository.findAllByRoomnoContaining(searchText);
            }else {
                list1=hotelRoomsRepository.findAll();
            }
            int size=list1.size()%paginatedConstants;
            int pageNo=list1.size()/paginatedConstants;
            if(size!=0){
                basePojo.setPageNo(pageNo);
            }else{
                basePojo.setPageNo( pageNo-1 );
            }
        }
        Rooms rooms=new Rooms();
        Pageable pageable=new PageRequest(basePojo.getPageNo(),basePojo.getPageSize(),sort);
        if(basePojo.isNextPage()==true || basePojo.isFirstPage()==true){
            sort=new Sort(new Sort.Order(Sort.Direction.ASC,"id"));
        }
        if (!StringUtils.isEmpty(searchText)) {
            rooms=hotelRoomsRepository.findFirstByRoomnoContaining(searchText,sort);
            list = hotelRoomsRepository.findAllByRoomnoContaining(searchText,pageable);
        } else {
            rooms=hotelRoomsRepository.findFirstBy(sort);
            list = hotelRoomsRepository.findAllBy(pageable);
        }
        if(list.contains(rooms)){
            basePojo.setStatus(true);
        }else {
            basePojo.setStatus(false);
        }
        List<RoomsPojo> typesList =HotelRoomsMapper.mapEntityToPojo(list);
        for(RoomsPojo roomsPojo1:typesList){
//            roomsPojo1.setFloor_Id(roomsPojo1.getFloorId().getId());
            roomsPojo1.setRoom_typeId(roomsPojo1.getRoomTypeId().getId());
        }
        basePojo=calculatePagination(basePojo,typesList.size());
        basePojo.setList(typesList);
        return basePojo;
    }

    public BasePojo getPaginatedPaidList(BasePojo basePojo,String searchText) {
        List<Services> list = new ArrayList<>();
        basePojo.setPageSize(paginatedConstants);
        Sort sort=new Sort(new Sort.Order(Sort.Direction.DESC,"id"));
        if(basePojo.isLastPage()==true){
            List<Services> list1=new ArrayList<>();
            if (!StringUtils.isEmpty(searchText)) {
                list1 = servicesRepository.findAllByTitleContaining(searchText);
            }else {
                list1=servicesRepository.findAll();
            }
            int size=list1.size()%paginatedConstants;
            int pageNo=list1.size()/paginatedConstants;
            if(size!=0){
                basePojo.setPageSize(size);
                basePojo.setPageNo(pageNo);
            }else{
                basePojo.setPageNo(pageNo-1);
            }
        }
        Services services=new Services();
        Pageable pageable=new PageRequest(basePojo.getPageNo(),basePojo.getPageSize(),sort);
        if(basePojo.isNextPage()==true || basePojo.isFirstPage()==true){
            sort=new Sort(new Sort.Order(Sort.Direction.ASC,"id"));
        }
        if (!StringUtils.isEmpty(searchText)) {
            services=servicesRepository.findFirstByTitleContaining(searchText,sort);
            list = servicesRepository.findAllByTitleContaining(searchText,pageable);
        } else {
            services=servicesRepository.findFirstBy(sort);
            list = servicesRepository.findAllBy(pageable);
        }
        if(list.contains(services)){
            basePojo.setStatus(true);
        }else {
            basePojo.setStatus(false);
        }
        List<ServicesPojo> typesList =ServiceMapper.mapEntityToPojo(list);
        for(ServicesPojo servicesPojo:typesList){
            servicesPojo.setRoomTypeId(servicesPojo.getRoomTypes().getId());
        }
        basePojo=calculatePagination(basePojo,typesList.size());
        basePojo.setList(typesList);
        return basePojo;
    }

    public BasePojo getPaginatedUsersList(BasePojo basePojo,String searchText) {
        List<User> list = new ArrayList<>();
        basePojo.setPageSize(paginatedConstants);
        Sort sort=new Sort(new Sort.Order(Sort.Direction.ASC,"useraccountId"));
        if(basePojo.isLastPage()==true){
            List<User> list1=new ArrayList<>();
            if (!StringUtils.isEmpty(searchText)) {
                list1 = hotelUserRepository.findAllByUserNameContaining(searchText);
            }else {
                list1=hotelUserRepository.findAll();
            }
            int size=list1.size()%paginatedConstants;
            if(size!=0){
                basePojo.setPageSize(size);
            }
            sort=new Sort(new Sort.Order(Sort.Direction.DESC,"useraccountId"));
        }
        User user=new User();
        Pageable pageable=new PageRequest(basePojo.getPageNo(),basePojo.getPageSize(),sort);
        if(basePojo.isNextPage()==true || basePojo.isFirstPage()==true){
            sort=new Sort(new Sort.Order(Sort.Direction.DESC,"useraccountId"));
        }
        if (!StringUtils.isEmpty(searchText)) {
            user=hotelUserRepository.findFirstByUserNameContaining(searchText,sort);
            list = hotelUserRepository.findAllByUserNameContaining(searchText,pageable);
        } else {
            user=hotelUserRepository.findFirstBy(sort);
            list = hotelUserRepository.findAllBy(pageable);
        }
        if(list.contains(user)){
            basePojo.setStatus(true);
        }else {
            basePojo.setStatus(false);
        }
        List<UserPojo> typesList =UserMapper.mapEntityToPojo(list);
        basePojo=calculatePagination(basePojo,typesList.size());
        basePojo.setList(typesList);
        return basePojo;
    }
    public BasePojo getPaginatedCountryList(BasePojo basePojo,String searchText) {
        List<Country> list = new ArrayList<>();
        basePojo.setPageSize(paginatedConstants);
        Sort sort=new Sort(new Sort.Order(Sort.Direction.DESC,"countryId"));
        if(basePojo.isLastPage()==true){
            List<Country> list1=new ArrayList<>();
            if (!StringUtils.isEmpty(searchText)) {
                list1 = countryRepository.findAllByCountryNameContaining(searchText);
            }else {
                list1=countryRepository.findAll();
            }
            int size=list1.size()%paginatedConstants;
            int pageNo=list1.size()/paginatedConstants;
            if(size!=0){
                basePojo.setPageNo(pageNo);
            }else {
                basePojo.setPageNo(pageNo-1);
            }
        }
        Country country=new Country();
        Pageable pageable=new PageRequest(basePojo.getPageNo(),basePojo.getPageSize(),sort);
        if(basePojo.isNextPage()==true || basePojo.isFirstPage()==true){
            sort=new Sort(new Sort.Order(Sort.Direction.ASC,"countryId"));
        }
        if (!StringUtils.isEmpty(searchText)) {
            country=countryRepository.findFirstByCountryNameContaining(searchText,sort);
            list = countryRepository.findAllByCountryNameContaining(searchText,pageable);
        } else {
            country=countryRepository.findFirstBy(sort);
            list = countryRepository.findAllBy(pageable);
        }
        if(list.contains(country)){
            basePojo.setStatus(true);
        }else {
            basePojo.setStatus(false);
        }
        List<CountryPojo> typesList =CountryMapper.mapCountryEntityToPojo(list);
        basePojo=calculatePagination(basePojo,typesList.size());
        basePojo.setList(typesList);
        return basePojo;
    }
    public BasePojo getPaginatedCityList(BasePojo basePojo,String searchText) {
        List<Cities> list = new ArrayList<>();
        basePojo.setPageSize(paginatedConstants);
        Sort sort=new Sort(new Sort.Order(Sort.Direction.DESC,"id"));
        if(basePojo.isLastPage()==true){
            List<Cities> list1=new ArrayList<>();
            if (!StringUtils.isEmpty(searchText)) {
                list1 = cityRepository.findAllByNameContainingOrCountryContainingOrStateContaining(searchText,searchText,searchText);
            }else {
                list1=cityRepository.findAll();
            }
            int size=list1.size()%paginatedConstants;
            int pageNo=list1.size()/paginatedConstants;
            if(size!=0){
                basePojo.setPageNo(pageNo);
            }else{
                basePojo.setPageNo(pageNo-1);
            }
        }
        Cities cities=new Cities();
        Pageable pageable=new PageRequest(basePojo.getPageNo(),basePojo.getPageSize(),sort);
        if(basePojo.isNextPage()==true || basePojo.isFirstPage()==true){
            sort=new Sort(new Sort.Order(Sort.Direction.ASC,"id"));
        }
        if (!StringUtils.isEmpty(searchText)) {
            cities=cityRepository.findFirstByNameContaining(searchText,sort);
            list = cityRepository.findAllByNameContaining(searchText,pageable);
        } else {
            cities=cityRepository.findFirstBy(sort);
            list = cityRepository.findAllBy(pageable);
        }
        if(list.contains(cities)){
            basePojo.setStatus(true);
        }else {
            basePojo.setStatus(false);
        }
        List<CityPojo> typesList =CityMapper.mapCitiesEntityToPojo(list);
        basePojo=calculatePagination(basePojo,typesList.size());
        basePojo.setList(typesList);
        return basePojo;
    }

    public static BaseFont getcustomfont() {
        String relativeWebPath = "fonts/arial.ttf";
        return FontFactory.getFont("arial", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 0.8f, Font.NORMAL, Color.BLACK).getBaseFont();
    }
    @Transactional
    public void downloadGuestsListReportPdf(OutputStream outputStream) {
        try {
            Font font1 = new Font(getcustomfont(), 12F, Font.BOLD);
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, outputStream);
            document.open();
            Chunk CONNECT = new Chunk(new LineSeparator(1, 100, Color.BLACK, Element.ALIGN_JUSTIFIED, 3f));
            document.add(CONNECT);
            document.add(new Paragraph("", font1));
            Chunk CONNECT1 = new Chunk(new LineSeparator(1, 100, Color.WHITE, Element.ALIGN_JUSTIFIED, 3f));
            document.add(CONNECT1);
            PdfPTable table = createFirstGuetsReport();
            document.add(table);
            document.add(CONNECT1);
            Paragraph foot = new Paragraph();
            document.add(foot);
            document.add(CONNECT);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
    public PdfPTable createFirstGuetsReport() {
        Font font1 = new Font(getcustomfont(), 8, Font.NORMAL, Color.BLACK);
        int a = 0;
        PdfPTable tab = new PdfPTable(1);
        Font f = new Font(getcustomfont(), 15, Font.BOLD, Color.WHITE);
        PdfPTable table = new PdfPTable(a + 5);
        Font font = new Font(getcustomfont(), 10, Font.NORMAL, Color.WHITE);
        Color myColor = WebColors.getRGBColor("#326397");
        PdfPCell p = new PdfPCell(new Phrase("GuestsListReport", f));
        p.setBackgroundColor(myColor);
        tab.addCell(p);
        PdfPCell pc2 = new PdfPCell(new Phrase("Name", font));
        pc2.setBackgroundColor(myColor);
        PdfPCell pc3 = new PdfPCell(new Phrase("Email", font));
        pc3.setBackgroundColor(myColor);
        PdfPCell pc4 = new PdfPCell(new Phrase("Address", font));
        pc4.setBackgroundColor(myColor);
        PdfPCell pc5 = new PdfPCell(new Phrase("Mobile No", font));
        pc5.setBackgroundColor(myColor);
        PdfPCell pc6 = new PdfPCell(new Phrase("DOB", font));
        pc6.setBackgroundColor(myColor);
        table.addCell(pc2);
        table.addCell(pc3);
        table.addCell(pc4);
        table.addCell(pc5);
        table.addCell(pc6);
        List<GuestsPojo> guetsList =getGuestsList();
        for (GuestsPojo list : guetsList) {
            table.addCell(new Phrase(list.getFirstname() + "", font1));
            table.addCell(new Phrase(list.getEmail() + "", font1));
            table.addCell(new Phrase(list.getAddress() + "", font1));
            table.addCell(new Phrase(list.getMobile() + "", font1));
            table.addCell(new Phrase(list.getDob() + "", font1));
        }
        tab.addCell(table);
        return tab;
    }
    @Transactional
    public void downloadGuestsListReportExcel(OutputStream out) {
        try {
            List<GuestsPojo> guetsList =getGuestsList();
            HSSFWorkbook hwb = new HSSFWorkbook();
            HSSFSheet sheet = hwb.createSheet("First Sheet");
            HSSFRow headerRow = sheet.createRow(0);
            headerRow.setHeightInPoints((2 * sheet.getDefaultRowHeightInPoints()));
            headerRow.createCell(0).setCellValue("GuestsListReport ");
            HSSFRow headerRow1 = sheet.createRow(1);
            headerRow1.createCell(0).setCellValue("Name");
            headerRow1.createCell(1).setCellValue("Email");
            headerRow1.createCell(2).setCellValue("Address");
            headerRow1.createCell(3).setCellValue("Mobile No");
            headerRow1.createCell(4).setCellValue("DOB");
            int i = 1;
            for (GuestsPojo list : guetsList) {
                HSSFRow row = sheet.createRow(++i);
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                row.createCell(0).setCellValue(list.getFirstname());
                row.createCell(1).setCellValue(list.getEmail());
                row.createCell(2).setCellValue(list.getAddress());
                row.createCell(3).setCellValue(list.getMobile());
                row.createCell(4).setCellValue(dateFormat.format(list.getDob()));
            }
            hwb.write(out);
            out.close();
        } catch (Exception gex) {
            gex.printStackTrace();
        }
    }





    public BasePojo getPaginatedGuestsList(BasePojo basePojo,String searchText) {
        List<Guests> list = new ArrayList<>();
        basePojo.setPageSize(paginatedConstants);
        Sort sort=new Sort(new Sort.Order(Sort.Direction.ASC,"id"));
        if(basePojo.isLastPage()==true){
            List<Guests> list1=new ArrayList<>();
            if (!StringUtils.isEmpty(searchText)) {
                list1 = hotelGuestsRepository.findAllByLastnameContaining(searchText);
            }else {
                list1=hotelGuestsRepository.findAll();
            }
            int size=list1.size()%paginatedConstants;
            if(size!=0){
                basePojo.setPageSize(size);
            }
            sort=new Sort(new Sort.Order(Sort.Direction.DESC,"id"));
        }
        Guests guests=new Guests();
        Pageable pageable=new PageRequest(basePojo.getPageNo(),basePojo.getPageSize(),sort);
        if(basePojo.isNextPage()==true || basePojo.isFirstPage()==true){
            sort=new Sort(new Sort.Order(Sort.Direction.DESC,"id"));
        }
        if (!StringUtils.isEmpty(searchText)) {
            guests=hotelGuestsRepository.findFirstByFirstnameAndLastnameContaining(searchText,sort);
            list = hotelGuestsRepository.findAllByLastnameContaining(searchText,pageable);
        } else {
            guests=hotelGuestsRepository.findFirstBy(sort);
            list = hotelGuestsRepository.findAllBy(pageable);
        }
        if(list.contains(guests)){
            basePojo.setStatus(true);
        }else {
            basePojo.setStatus(false);
        }
        List<GuestsPojo> typesList =HotelGuestsMapper.mapEntityToPojo(list);
        basePojo=calculatePagination(basePojo,typesList.size());
        basePojo.setList(typesList);
        return basePojo;
    }
    @Transactional
    public void downloadCountryPdf(OutputStream outputStream,String searchText) {
        try {
            Font font1 = new Font(getcustomfont(), 12F, Font.BOLD);
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, outputStream);
            document.open();
            Chunk CONNECT = new Chunk(new LineSeparator(1, 100, Color.BLACK, Element.ALIGN_JUSTIFIED, 3f));
            document.add(CONNECT);
            document.add(new Paragraph("", font1));
            Chunk CONNECT1 = new Chunk(new LineSeparator(1, 100, Color.WHITE, Element.ALIGN_JUSTIFIED, 3f));
            document.add(CONNECT1);
            PdfPTable table = createFirstTableCountry(searchText);
            table.setHeaderRows(1);
            document.add(table);
            document.add(CONNECT1);
            Paragraph foot = new Paragraph();
            Phrase ph5 = new Phrase("Terms And Condition:Terms                    Prepared By                                        Approved By   ", font1);
            foot.add(ph5);
            document.add(foot);
            document.add(CONNECT);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
    private PdfPTable createFirstTableCountry(String searchText) {
        Font font1 = new Font(getcustomfont(), 8, Font.NORMAL, Color.BLACK);
        int a = 0;
        PdfPTable table = new PdfPTable(a + 1);
        PdfPTable table1 = new PdfPTable(a +1);
        Font font = new Font(getcustomfont(), 10, Font.NORMAL, Color.WHITE);
        Color myColor = WebColors.getRGBColor("#326397");
        PdfPCell p = new PdfPCell(new Phrase("Country", font));
        p.setBackgroundColor(myColor);
        table1.addCell(p);
        PdfPCell pc2 = new PdfPCell(new Phrase("Country Name", font));
        pc2.setBackgroundColor(myColor);
        table.addCell(pc2);
        List<CountryPojo> countryList = getCountryList(searchText);
        table.setWidthPercentage(100);
        for (CountryPojo countryPojo : countryList) {
            table.addCell(new Phrase(countryPojo.getCountryName() + "", font1));
        }
        table1.addCell(table);
        return table1;
    }
    public void downloadCountryExcelSheet(ByteArrayOutputStream outputStream,  String searchText) {
        try {
            List<CountryPojo> countryList = getCountryList(searchText);
            HSSFWorkbook hwb = new HSSFWorkbook();
            HSSFSheet sheet = hwb.createSheet("First Sheet");
            HSSFRow headerRow = sheet.createRow(0);
            headerRow.setHeightInPoints((2 * sheet.getDefaultRowHeightInPoints()));
            HSSFRow headerRow1 = sheet.createRow(0);
            headerRow1.createCell(0).setCellValue("Country Name");
            int i = 0;
            for (CountryPojo country : countryList) {
                HSSFRow row = sheet.createRow(++i);
                row.createCell(0).setCellValue(country.getCountryName());
            }

            hwb.write(outputStream);
            outputStream.close();
        } catch (IOException ioex) {
            ioex.printStackTrace();
        } catch (Exception gex) {
            gex.printStackTrace();
        }
    }
    @Transactional
    public void downloadStatePdf(OutputStream outputStream,String searchText) {
        try {
            Font font1 = new Font(getcustomfont(), 12F, Font.BOLD);
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, outputStream);
            document.open();
            Chunk CONNECT = new Chunk(new LineSeparator(1, 100, Color.BLACK, Element.ALIGN_JUSTIFIED, 3f));
            document.add(CONNECT);
            document.add(new Paragraph("", font1));
            Chunk CONNECT1 = new Chunk(new LineSeparator(1, 100, Color.WHITE, Element.ALIGN_JUSTIFIED, 3f));
            document.add(CONNECT1);
            PdfPTable table = createFirstTableState(searchText);
            table.setHeaderRows(1);
            document.add(table);
            document.add(CONNECT1);
            Paragraph foot = new Paragraph();
            Phrase ph5 = new Phrase("Terms And Condition:Terms                    Prepared By                                        Approved By   ", font1);
            foot.add(ph5);
            document.add(foot);
            document.add(CONNECT);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
    public PdfPTable createFirstTableState(String searchText) {
        Font font1 = new Font(getcustomfont(), 8, Font.NORMAL, Color.BLACK);
        int a = 0;
        PdfPTable table = new PdfPTable(a + 2);
        PdfPTable table1 = new PdfPTable(a +1);
        Font font = new Font(getcustomfont(), 10, Font.NORMAL, Color.WHITE);
        Color myColor = WebColors.getRGBColor("#326397");
        PdfPCell p = new PdfPCell(new Phrase("State", font));
        p.setBackgroundColor(myColor);
        table1.addCell(p);
        PdfPCell pc2 = new PdfPCell(new Phrase("State Name", font));
        PdfPCell pc3 = new PdfPCell(new Phrase("Country Name", font));
        pc2.setBackgroundColor(myColor);
        pc3.setBackgroundColor(myColor);
        table.addCell(pc2);
        table.addCell(pc3);
        List<StatePojo> statePojoList = getStateList(searchText);
        table.setWidthPercentage(100);
        for (StatePojo statePojo : statePojoList) {
            table.addCell(new Phrase(statePojo.getStateName() + "", font1));
            table.addCell(new Phrase(statePojo.getCountry() + "", font1));
        }
        table1.addCell(table);
        return table1;
    }
    public void downloadStateExcelSheet(OutputStream out, String searchText) {
        try {
            List<StatePojo> statePojoList = getStateList(searchText);
            HSSFWorkbook hwb = new HSSFWorkbook();
            HSSFSheet sheet = hwb.createSheet("First Sheet");
            HSSFRow headerRow = sheet.createRow(0);
            headerRow.setHeightInPoints((2 * sheet.getDefaultRowHeightInPoints()));
            HSSFRow headerRow1 = sheet.createRow(0);
            headerRow1.createCell(0).setCellValue("State Name");
            headerRow1.createCell(1).setCellValue("Country Name");
            int i = 0;
            for (StatePojo statePojo : statePojoList) {
                HSSFRow row = sheet.createRow(++i);
                row.createCell(0).setCellValue(statePojo.getStateName());
                row.createCell(1).setCellValue(statePojo.getCountry());
            }

            hwb.write(out);
            out.close();
        } catch (IOException ioex) {
            ioex.printStackTrace();
        } catch (Exception gex) {
            gex.printStackTrace();
        }
    }
    public List<StatePojo> getStateList(String searchText){
        List<State> list = new ArrayList<>();
        if (!StringUtils.isEmpty(searchText)) {
            list = stateRepository.findAllByStateCodeContainingOrStateNameContaining(searchText,searchText);
        } else {
            list = stateRepository.findAll();
        }
        List<StatePojo> stateList =StateMapper.mapStateEntityToPojo(list);
        return stateList;
    }


    public List<RoomsPojo> getRoomsPojoList(String searchText){
        List<Rooms> list = new ArrayList<>();
        if (!StringUtils.isEmpty(searchText)) {
            list = hotelRoomsRepository.findAllByRoomnoContaining(searchText);
        } else {
            list = hotelRoomsRepository.findAll();
        }
        List<RoomsPojo> roomsPojoList =HotelRoomsMapper.mapEntityToPojo(list);
        return roomsPojoList;
    }

    @Transactional
   public void downloadPaidServicesPdf(OutputStream outputStream,String searchText) {
    try {
        Font font1 = new Font(getcustomfont(), 12F, Font.BOLD);
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, outputStream);
        document.open();
        Chunk CONNECT = new Chunk(new LineSeparator(1, 100, Color.BLACK, Element.ALIGN_JUSTIFIED, 3f));
        document.add(CONNECT);
        document.add(new Paragraph("", font1));
        Chunk CONNECT1 = new Chunk(new LineSeparator(1, 100, Color.WHITE, Element.ALIGN_JUSTIFIED, 3f));
        document.add(CONNECT1);
        PdfPTable table;
        table = createFirstTablePaidServices(searchText);
        table.setHeaderRows(1);
        document.add(table);
        document.add(CONNECT1);
        Paragraph foot = new Paragraph();
        Phrase ph5 = new Phrase("Terms And Condition:Terms                    Prepared By                                        Approved By   ", font1);
        foot.add(ph5);
        document.add(foot);
        document.add(CONNECT);
        document.close();
    } catch (Exception e) {
        e.printStackTrace();

    }
}
    public PdfPTable createFirstTablePaidServices(String searchText) {
        Font font1 = new Font(getcustomfont(), 8, Font.NORMAL, Color.BLACK);
        int a = 0;
        PdfPTable table = new PdfPTable(a + 7);
        PdfPTable table1 = new PdfPTable(a +1);
        Font font = new Font(getcustomfont(), 10, Font.NORMAL, Color.WHITE);
        Color myColor = WebColors.getRGBColor("#326397");
        PdfPCell p = new PdfPCell(new Phrase("PaidServices", font));
        p.setBackgroundColor(myColor);
        table1.addCell(p);
        PdfPCell pc2 = new PdfPCell(new Phrase("Title", font));
        PdfPCell pc3 = new PdfPCell(new Phrase("Description", font));
        PdfPCell pc4 = new PdfPCell(new Phrase("RoomType", font));
        PdfPCell pc5 = new PdfPCell(new Phrase("Price", font));
        PdfPCell pc6 = new PdfPCell(new Phrase("Short Description", font));
        PdfPCell pc7 = new PdfPCell(new Phrase("Status", font));
        PdfPCell pc8 = new PdfPCell(new Phrase("Price Type", font));
        pc2.setBackgroundColor(myColor);
        pc3.setBackgroundColor(myColor);
        pc4.setBackgroundColor(myColor);
        pc5.setBackgroundColor(myColor);
        pc6.setBackgroundColor(myColor);
        pc7.setBackgroundColor(myColor);
        pc8.setBackgroundColor(myColor);
        table.addCell(pc2);
        table.addCell(pc3);
        table.addCell(pc4);
        table.addCell(pc5);
        table.addCell(pc6);
        table.addCell(pc7);
        table.addCell(pc8);
        List<ServicesPojo> servicesPojoList = getServicesPojoList(searchText);
        table.setWidthPercentage(100);
        for (ServicesPojo servicesPojo : servicesPojoList) {
            table.addCell(new Phrase(servicesPojo.getTitle() + "", font1));
            table.addCell(new Phrase(servicesPojo.getDescription() + "", font1));
            table.addCell(new Phrase(servicesPojo.getRoomTypeId() + "", font1));
            table.addCell(new Phrase(servicesPojo.getPrice() + "", font1));
            table.addCell(new Phrase(servicesPojo.getShort_description() + "", font1));
            table.addCell(new Phrase(servicesPojo.getStatus() + "", font1));
            table.addCell(new Phrase(servicesPojo.getPrice_type() + "", font1));
        }
        table1.addCell(table);
        return table1;
    }
    public void downloadPaidServicesExcelSheet(OutputStream out, String searchText) {
        try {
            List<ServicesPojo> servicesPojoList = getServicesPojoList(searchText);
            HSSFWorkbook hwb = new HSSFWorkbook();
            HSSFSheet sheet = hwb.createSheet("First Sheet");
            HSSFRow headerRow = sheet.createRow(0);
            headerRow.setHeightInPoints((2 * sheet.getDefaultRowHeightInPoints()));
            HSSFRow headerRow1 = sheet.createRow(0);
            headerRow1.createCell(0).setCellValue("Title");
            headerRow1.createCell(1).setCellValue("Description");
            headerRow1.createCell(2).setCellValue("RoomType");
            headerRow1.createCell(3).setCellValue("Price");
            headerRow1.createCell(4).setCellValue("Status");
            headerRow1.createCell(5).setCellValue("Short Description");
            headerRow1.createCell(6).setCellValue("Price Type");
            int i = 0;
            for (ServicesPojo servicesPojo : servicesPojoList) {
                HSSFRow row = sheet.createRow(++i);
                row.createCell(0).setCellValue(servicesPojo.getTitle());
                row.createCell(1).setCellValue(servicesPojo.getDescription());
                row.createCell(3).setCellValue(servicesPojo.getPrice());
                row.createCell(4).setCellValue(servicesPojo.getStatus());
                row.createCell(5).setCellValue(servicesPojo.getShort_description());
                row.createCell(6).setCellValue(servicesPojo.getPrice_type());
            }

            hwb.write(out);
            out.close();
        } catch (IOException ioex) {
            ioex.printStackTrace();
        } catch (Exception gex) {
            gex.printStackTrace();
        }
    }
    public List<ServicesPojo> getServicesPojoList(String searchText){
        List<Services> list = new ArrayList<>();
        if (!StringUtils.isEmpty(searchText)) {
            list =servicesRepository .findAllByTitleContainingOrDescriptionContaining(searchText,searchText);
        } else {
            list = servicesRepository.findAll();
        }
        List<ServicesPojo> servicesPojoList =ServiceMapper.mapEntityToPojo(list);
        return servicesPojoList;
    }


    @Transactional
    public void downloadUsersPdf(OutputStream outputStream,String searchText) {
        try {
            Font font1 = new Font(getcustomfont(), 12F, Font.BOLD);
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, outputStream);
            document.open();
            Chunk CONNECT = new Chunk(new LineSeparator(1, 100, Color.BLACK, Element.ALIGN_JUSTIFIED, 3f));
            document.add(CONNECT);
            document.add(new Paragraph("", font1));
            Chunk CONNECT1 = new Chunk(new LineSeparator(1, 100, Color.WHITE, Element.ALIGN_JUSTIFIED, 3f));
            document.add(CONNECT1);
            PdfPTable table = createFirstTableUsers(searchText);
            table.setHeaderRows(1);
            document.add(table);
            document.add(CONNECT1);
            Paragraph foot = new Paragraph();
            Phrase ph5 = new Phrase("Terms And Condition:Terms                    Prepared By                                        Approved By   ", font1);
            foot.add(ph5);
            document.add(foot);
            document.add(CONNECT);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
    public PdfPTable createFirstTableUsers(String searchText) {
        Font font1 = new Font(getcustomfont(), 8, Font.NORMAL, Color.BLACK);
        int a = 0;
        PdfPTable table = new PdfPTable(a + 8);
        PdfPTable table1 = new PdfPTable(a +1);
        Font font = new Font(getcustomfont(), 10, Font.NORMAL, Color.WHITE);
        Color myColor = WebColors.getRGBColor("#326397");
        PdfPCell p = new PdfPCell(new Phrase("Users", font));
        p.setBackgroundColor(myColor);
        table1.addCell(p);
        PdfPCell pc2 = new PdfPCell(new Phrase("User Name", font));
        PdfPCell pc3 = new PdfPCell(new Phrase("Password", font));
        PdfPCell pc4 = new PdfPCell(new Phrase("Full Name", font));
        PdfPCell pc5 = new PdfPCell(new Phrase("Security Question", font));
        PdfPCell pc6 = new PdfPCell(new Phrase("Answer", font));
        PdfPCell pc7 = new PdfPCell(new Phrase("Telephone Number", font));
        PdfPCell pc8 = new PdfPCell(new Phrase("Email Address", font));
        PdfPCell pc9 = new PdfPCell(new Phrase("Status", font));
        pc2.setBackgroundColor(myColor);
        pc3.setBackgroundColor(myColor);
        pc4.setBackgroundColor(myColor);
        pc5.setBackgroundColor(myColor);
        pc6.setBackgroundColor(myColor);
        pc7.setBackgroundColor(myColor);
        pc8.setBackgroundColor(myColor);
        pc9.setBackgroundColor(myColor);
        table.addCell(pc2);
        table.addCell(pc3);
        table.addCell(pc4);
        table.addCell(pc5);
        table.addCell(pc6);
        table.addCell(pc7);
        table.addCell(pc8);
        table.addCell(pc9);
        List<UserPojo> userPojoList = getUserPojoList(searchText);
        table.setWidthPercentage(100);
        for (UserPojo userPojo : userPojoList) {
            table.addCell(new Phrase(userPojo.getUserName() + "", font1));
            table.addCell(new Phrase(userPojo.getPasswordUser() + "", font1));
            table.addCell(new Phrase(userPojo.getFull_name() + "", font1));
            table.addCell(new Phrase(userPojo.getSecurityQuestion() + "", font1));
            table.addCell(new Phrase(userPojo.getSecurityAnswer() + "", font1));
            table.addCell(new Phrase(userPojo.getPhone() + "", font1));
            table.addCell(new Phrase(userPojo.getEmail() + "", font1));
            table.addCell(new Phrase(userPojo.getStatus() + "", font1));

        }
        table1.addCell(table);
        return table1;
    }
    public void downloadUsersExcelSheet(OutputStream out, String searchText) {
        try {
            List<UserPojo> userPojoList = getUserPojoList(searchText);
            HSSFWorkbook hwb = new HSSFWorkbook();
            HSSFSheet sheet = hwb.createSheet("First Sheet");
            HSSFRow headerRow = sheet.createRow(0);
            headerRow.setHeightInPoints((2 * sheet.getDefaultRowHeightInPoints()));
            HSSFRow headerRow1 = sheet.createRow(0);
            headerRow1.createCell(0).setCellValue("User Name");
            headerRow1.createCell(1).setCellValue("Password");
            headerRow1.createCell(2).setCellValue("Full Name");
            headerRow1.createCell(3).setCellValue("Security Question");
            headerRow1.createCell(4).setCellValue("Answer");
            headerRow1.createCell(5).setCellValue("Telephone Number");
            headerRow1.createCell(6).setCellValue("Email Address");
            headerRow1.createCell(7).setCellValue("Status");
            int i = 0;
            for (UserPojo usersPojo: userPojoList) {
                HSSFRow row = sheet.createRow(++i);
                row.createCell(0).setCellValue(usersPojo.getUserName());
                row.createCell(1).setCellValue(usersPojo.getPasswordUser());
                row.createCell(2).setCellValue(usersPojo.getFull_name());
                row.createCell(3).setCellValue(usersPojo.getSecurityQuestion());
                row.createCell(4).setCellValue(usersPojo.getSecurityAnswer());
                row.createCell(5).setCellValue(usersPojo.getPhone());
                row.createCell(6).setCellValue(usersPojo.getEmail());
                row.createCell(7).setCellValue(usersPojo.getStatus());
            }

            hwb.write(out);
            out.close();
        } catch (IOException ioex) {
            ioex.printStackTrace();
        } catch (Exception gex) {
            gex.printStackTrace();
        }
    }
    public List<UserPojo> getUserPojoList(String searchText){
        List<User> list = new ArrayList<>();
        if (!StringUtils.isEmpty(searchText)) {
            list = hotelUserRepository.findByUserName(searchText);
        } else {
            list = hotelUserRepository.findAll();
        }
        List<UserPojo> userPojoList =UserMapper.mapEntityToPojo(list);
        return userPojoList;
    }



  @Transactional
   public void downloadCityPdf(OutputStream outputStream,String searchText) {
    try {
        Font font1 = new Font(getcustomfont(), 12F, Font.BOLD);
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, outputStream);
        document.open();
        Chunk CONNECT = new Chunk(new LineSeparator(1, 100, Color.BLACK, Element.ALIGN_JUSTIFIED, 3f));
        document.add(CONNECT);
        document.add(new Paragraph("", font1));
        Chunk CONNECT1 = new Chunk(new LineSeparator(1, 100, Color.WHITE, Element.ALIGN_JUSTIFIED, 3f));
        document.add(CONNECT1);
        PdfPTable table;
        table = createFirstTableCity(searchText);
        table.setHeaderRows(1);
        document.add(table);
        document.add(CONNECT1);
        Paragraph foot = new Paragraph();
        Phrase ph5 = new Phrase("Terms And Condition:Terms                    Prepared By                                        Approved By   ", font1);
        foot.add(ph5);
        document.add(foot);
        document.add(CONNECT);
        document.close();
    } catch (Exception e) {
        e.printStackTrace();

    }
}
    public PdfPTable createFirstTableCity(String searchText) {
        Font font1 = new Font(getcustomfont(), 8, Font.NORMAL, Color.BLACK);
        int a = 0;
        PdfPTable table = new PdfPTable(a +3);
        PdfPTable table1 = new PdfPTable(a +1);
        Font font = new Font(getcustomfont(), 10, Font.NORMAL, Color.WHITE);
        Color myColor = WebColors.getRGBColor("#326397");
        PdfPCell p = new PdfPCell(new Phrase("City", font));
        p.setBackgroundColor(myColor);
        table1.addCell(p);
        PdfPCell pc2 = new PdfPCell(new Phrase("Country", font));
        PdfPCell pc3 = new PdfPCell(new Phrase("State", font));
        PdfPCell pc4 = new PdfPCell(new Phrase("City Name", font));
        pc2.setBackgroundColor(myColor);
        pc3.setBackgroundColor(myColor);
        pc4.setBackgroundColor(myColor);
        table.addCell(pc2);
        table.addCell(pc3);
        table.addCell(pc4);
        List<CityPojo> cityPojoList = getCityPojos(searchText);
        table.setWidthPercentage(100);
        for(CityPojo cityPojo:cityPojoList) {

            table.addCell( new Phrase( cityPojo.getCountry() + "", font1 ) );
            table.addCell( new Phrase( cityPojo.getState() + "", font1 ) );
            table.addCell( new Phrase( cityPojo.getName() + "", font1 ) );
        }

        table1.addCell(table);
        return table1;
    }
    public void downloadCityExcelSheet(OutputStream out, String searchText) {
        try {
            List<CityPojo> cityPojoList = getCityPojos(searchText);
            HSSFWorkbook hwb = new HSSFWorkbook();
            HSSFSheet sheet = hwb.createSheet("First Sheet");
            HSSFRow headerRow = sheet.createRow(0);
            headerRow.setHeightInPoints((2 * sheet.getDefaultRowHeightInPoints()));
            HSSFRow headerRow1 = sheet.createRow(0);
            headerRow1.createCell(0).setCellValue("Country");
            headerRow1.createCell(1).setCellValue("State");
            headerRow1.createCell(2).setCellValue("City Name");
            int i = 0;
            for (CityPojo cityPojo : cityPojoList) {
                HSSFRow row = sheet.createRow(++i);
                row.createCell(0).setCellValue(cityPojo.getState());
                row.createCell(1).setCellValue(cityPojo.getCountry());
                row.createCell(2).setCellValue(cityPojo.getName());
            }

            hwb.write(out);
            out.close();
        } catch (IOException ioex) {
            ioex.printStackTrace();
        } catch (Exception gex) {
            gex.printStackTrace();
        }
    }
    public List<CityPojo> getCityPojos(String searchText ){
        List<Cities> list = new ArrayList<>();
        if (!StringUtils.isEmpty(searchText)) {
            list =cityRepository .findAllByNameContaining(searchText);
        } else {
            list = cityRepository.findAll();
        }
        List<CityPojo> cityPojoList =CityMapper.mapCitiesEntityToPojo(list);
        return cityPojoList;
    }


    public List<RoomTypesPojo> getRoomTypesPojos(String searchText){
        List<RoomTypes> list = new ArrayList<>();
        if (!StringUtils.isEmpty(searchText)) {
            list = hotelRoomTypesRepository.findAllByTitleContaining(searchText);
        } else {
            list = hotelRoomTypesRepository.findAll();
        }
        List<RoomTypesPojo> roomTypesPojos =HotelRoomTypeMapper.mapEntityToPojo(list);
        return roomTypesPojos;
    }



    @Transactional
    public void downloadGuestsPdf(OutputStream outputStream,String searchText) {
        try {
            Font font1 = new Font(getcustomfont(), 12F, Font.BOLD);
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, outputStream);
            document.open();
            Chunk CONNECT = new Chunk(new LineSeparator(1, 100, Color.BLACK, Element.ALIGN_JUSTIFIED, 3f));
            document.add(CONNECT);
            document.add(new Paragraph("", font1));
            Chunk CONNECT1 = new Chunk(new LineSeparator(1, 100, Color.WHITE, Element.ALIGN_JUSTIFIED, 3f));
            document.add(CONNECT1);
            PdfPTable table;
            table = createFirstTableGuests(searchText);
            table.setHeaderRows(1);
            document.add(table);
            document.add(CONNECT1);
            Paragraph foot = new Paragraph();
            Phrase ph5 = new Phrase("Terms And Condition:Terms                    Prepared By                                        Approved By   ", font1);
            foot.add(ph5);
            document.add(foot);
            document.add(CONNECT);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
    public PdfPTable createFirstTableGuests(String searchText) {
        Font font1 = new Font(getcustomfont(), 8, Font.NORMAL, Color.BLACK);
        int a = 0;
        PdfPTable table = new PdfPTable(a + 11);
        PdfPTable table1 = new PdfPTable(a +1);
        Font font = new Font(getcustomfont(), 10, Font.NORMAL, Color.WHITE);
        Color myColor = WebColors.getRGBColor("#326397");
        PdfPCell p = new PdfPCell(new Phrase("Guests", font));
        p.setBackgroundColor(myColor);
        table1.addCell(p);
        PdfPCell pc2 = new PdfPCell(new Phrase(" First Name", font));
        pc2.setBackgroundColor(myColor);
        table.addCell(pc2);
        PdfPCell pc3 = new PdfPCell(new Phrase(" Last Name", font));
        pc3.setBackgroundColor(myColor);
        table.addCell(pc3);
        PdfPCell pc4 = new PdfPCell(new Phrase(" Gender", font));
        pc4.setBackgroundColor(myColor);
        table.addCell(pc4);
        PdfPCell pc5 = new PdfPCell(new Phrase(" Email", font));
        pc5.setBackgroundColor(myColor);
        table.addCell(pc5);
        PdfPCell pc6 = new PdfPCell(new Phrase(" Address", font));
        pc6.setBackgroundColor(myColor);
        table.addCell(pc6);
        PdfPCell pc7 = new PdfPCell(new Phrase(" Mobile", font));
        pc7.setBackgroundColor(myColor);
        table.addCell(pc7);
        PdfPCell pc8 = new PdfPCell(new Phrase(" Company Name", font));
        pc8.setBackgroundColor(myColor);
        table.addCell(pc8);
        PdfPCell pc9 = new PdfPCell(new Phrase(" Dob", font));
        pc9.setBackgroundColor(myColor);
        table.addCell(pc9);
        PdfPCell pc10 = new PdfPCell(new Phrase(" Country", font));
        pc10.setBackgroundColor(myColor);
        table.addCell(pc10);
        PdfPCell pc11 = new PdfPCell(new Phrase(" Status", font));
        pc11.setBackgroundColor(myColor);
        table.addCell(pc11);
        PdfPCell pc12 = new PdfPCell(new Phrase(" Vip", font));
        pc12.setBackgroundColor(myColor);
        table.addCell(pc12);
        List<GuestsPojo> guestsPojos = getGuestsPojos(searchText);
        table.setWidthPercentage(100);
        for (GuestsPojo guestsPojo : guestsPojos) {
            table.addCell(new Phrase(guestsPojo.getFirstname() + "", font1));
            table.addCell(new Phrase(guestsPojo.getLastname() + "", font1));
            table.addCell(new Phrase(guestsPojo.getGender() + "", font1));
            table.addCell(new Phrase(guestsPojo.getEmail() + "", font1));
            table.addCell(new Phrase(guestsPojo.getAddress() + "", font1));
            table.addCell(new Phrase(guestsPojo.getMobile() + "", font1));
            table.addCell(new Phrase(guestsPojo.getCompanyname() + "", font1));
            table.addCell(new Phrase(guestsPojo.getDob() + "", font1));
            table.addCell(new Phrase(guestsPojo.getCountry() + "", font1));
            table.addCell(new Phrase(guestsPojo.getStatus() + "", font1));
            table.addCell(new Phrase(guestsPojo.getVip() + "", font1));



        }
        table1.addCell(table);
        return table1;
    }
    public void downloadGuestsExcelSheet(OutputStream out, String searchText) {
        try {
            List<GuestsPojo> guestsPojos = getGuestsPojos(searchText);
            HSSFWorkbook hwb = new HSSFWorkbook();
            HSSFSheet sheet = hwb.createSheet("First Sheet");
            HSSFRow headerRow = sheet.createRow(0);
            headerRow.setHeightInPoints((2 * sheet.getDefaultRowHeightInPoints()));
            HSSFRow headerRow1 = sheet.createRow(0);
            headerRow1.createCell(0).setCellValue("First Name");
            headerRow1.createCell(1).setCellValue("Last Name");
            headerRow1.createCell(2).setCellValue("Gender");
            headerRow1.createCell(3).setCellValue("Email");
            headerRow1.createCell(4).setCellValue("Address");
            headerRow1.createCell(5).setCellValue("Mobile");
            headerRow1.createCell(6).setCellValue("Company Name");
            headerRow1.createCell(7).setCellValue("Dob");
            headerRow1.createCell(8).setCellValue("Country");
            headerRow1.createCell(9).setCellValue("Status");
            headerRow1.createCell(10).setCellValue("Vip");
            int i = 0;
            for (GuestsPojo guestsPojo :guestsPojos) {
                HSSFRow row = sheet.createRow(++i);
                row.createCell(0).setCellValue(guestsPojo.getFirstname());
                row.createCell(1).setCellValue(guestsPojo.getLastname());
                row.createCell(2).setCellValue(guestsPojo.getGender());
                row.createCell(3).setCellValue(guestsPojo.getEmail());
                row.createCell(4).setCellValue(guestsPojo.getAddress());
                row.createCell(5).setCellValue(guestsPojo.getMobile());
                row.createCell(6).setCellValue(guestsPojo.getCompanyname());
                row.createCell(7).setCellValue(guestsPojo.getDob());
                row.createCell(8).setCellValue(guestsPojo.getCountry());
                row.createCell(9).setCellValue(guestsPojo.getStatus());
                row.createCell(10).setCellValue(guestsPojo.getVip());
            }
            hwb.write(out);
            out.close();
        } catch (IOException ioex) {
            ioex.printStackTrace();
        } catch (Exception gex) {
            gex.printStackTrace();
        }
    }
    public List<GuestsPojo> getGuestsPojos(String searchText){
        List<Guests> list = new ArrayList<>();
        if (!StringUtils.isEmpty(searchText)) {
            list = hotelGuestsRepository.findAllByLastnameContaining(searchText);
        } else {
            list = hotelGuestsRepository.findAll();
        }
        List<GuestsPojo> guestsPojos =HotelGuestsMapper.mapEntityToPojo(list);
        return guestsPojos;
    }

    public void getDeleteRoomTypes(Long id){
        hotelRoomTypesRepository.delete(id);
    }

    public RoomsPojo saveRooms(RoomsPojo roomspojo) throws JSONException {
        List<Rooms>list=new ArrayList<>();
        if(roomspojo.getId()!=0){
            list=hotelRoomsRepository.findByRoomnoAndIdNotIn(roomspojo.getRoomno(),roomspojo.getId());
        }
        else{
            list=hotelRoomsRepository.findByRoomno(roomspojo.getRoomno());
        }
        if(list.size()==0){
            Rooms rooms = null;
            RoomTypes roomTypesObj = hotelRoomTypesRepository.findOne(roomspojo.getRoom_typeId());
//            Floors floorsObj = hotelFloorRepository.findOne(roomspojo.getFloor_Id());
//            roomspojo.setFloorId(floorsObj);
            roomspojo.setRoomTypeId(roomTypesObj);
            rooms = HotelRoomsMapper.mapPojoToEntity(roomspojo);
            hotelRoomsRepository.save(rooms);
            return roomspojo;
        }
        else{
            return null;
        }

    }


}





