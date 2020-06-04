package com.hyva.hotel.Hotelendpoints;

import com.hyva.hotel.entities.*;
import com.hyva.hotel.pojo.*;
import com.hyva.hotel.respositories.HotelRoomTypesRepository;
import com.hyva.hotel.respositories.HotelRoomsRepository;
import com.hyva.hotel.service.HotelService;
import com.hyva.hotel.respositories.OrdersRepository;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * Created by kishore on 8/6/18.
 */
@RestController
@RequestMapping("/hotel")
public class HotelController {
    @Autowired
    HotelService hotelService;
    @Autowired
    OrdersRepository ordersRepository;
    @Autowired
    HotelRoomTypesRepository hotelRoomTypesRepository;
    @Autowired
    HotelRoomsRepository hotelRoomsRepository;


    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public EntityResponse login(@RequestBody User credentials) throws Exception {
//            User bshimData = bshimUserService.get(credentials) ;
        String accessToken = "12345";
        if (StringUtils.isBlank(credentials.getEmail()) || StringUtils.isBlank(credentials.getUserName()) || StringUtils.isBlank(credentials.getPasswordUser())) {
            return new EntityResponse(HttpStatus.OK.value(), "Invalid User");
        }
        return new EntityResponse(HttpStatus.OK.value(), "success", credentials);
    }

    @RequestMapping(value = "/saveLoginDetails", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public User saveLoginDetails(@RequestBody UserPojo userPojo) {
        return hotelService.saveUserDetails(userPojo);
    }


    @RequestMapping(value = "/saveConfigurator", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity saveConfigurator(@RequestBody ConfiguratorPojo configuratorPojo) throws Exception {
        Configurator configurator = null;
        configurator = hotelService.saveConfigurator(configuratorPojo);
        return ResponseEntity.status(200).body(configurator);
    }



    @RequestMapping(value = "/saveRoomTypes", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity saveRoomTypes(@RequestBody RoomTypesPojo roomTypesPojo) throws Exception {
        roomTypesPojo = hotelService.saveRoomTypes(roomTypesPojo);
        return ResponseEntity.status(200).body(roomTypesPojo);
    }



    @RequestMapping(value = "/getDeleteRoomTypes", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity getDeleteRoomTypes(@RequestParam(value = "id") Long id) {
        hotelService.getDeleteRoomTypes(id);
        return ResponseEntity.status(200).body(null);
    }


    @RequestMapping(value = "/saveguests", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity saveguests(@RequestBody GuestsPojo guestsPojo) throws Exception {
        guestsPojo = hotelService.saveguests(guestsPojo);
        return ResponseEntity.status(200).body(guestsPojo);
    }




    @RequestMapping(value = "/getGuestsList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public EntityResponse getGuestsList() {
        List<GuestsPojo> guestsPojo = hotelService.getGuestsList();
        return new EntityResponse(HttpStatus.OK.value(), " success", guestsPojo);
    }

    @RequestMapping(value = "/getBookingList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public EntityResponse getBookingList() {
        List<BookingPojo> bookingPojos = hotelService.getBookingList();
        return new EntityResponse(HttpStatus.OK.value(), " success", bookingPojos);
    }


    @RequestMapping(value = "/getDeleteGuests", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity getDeleteGuests(@RequestParam(value = "id") Long id) {
        hotelService.getDeleteGuests(id);
        return ResponseEntity.status(200).body(null);
    }


    @RequestMapping(value = "/saveRooms", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity saveRooms(@RequestBody RoomsPojo roomsPojo) throws Exception {
        roomsPojo = hotelService.saveRooms(roomsPojo);
        return ResponseEntity.status(200).body(roomsPojo);
    }

    @RequestMapping(value = "/getStateList", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity getStateList(@RequestParam(value = "type", required = false) String type,
                                       @RequestParam(value = "searchText", required = false) String searchText,
                                       @RequestBody BasePojo basePojo) {
        return ResponseEntity.status(200).body(hotelService.getStateList(type, basePojo, searchText));
    }

    @RequestMapping(value = "/saveState", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity saveState(@RequestBody StatePojo state) {
        return ResponseEntity.status(200).body(hotelService.saveState(state));
    }

    @RequestMapping(value = "/deleteState", method = RequestMethod.POST, produces = "application/json")
    public void deleteState(@RequestParam(value = "stateName", required = false) String stateName) {
        hotelService.deleteState(stateName);
    }

    @RequestMapping(value = "/editState", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity editState(@RequestParam(value = "stateName", required = false) String stateName) {
        return ResponseEntity.status(200).body(hotelService.editState(stateName));
    }

    @RequestMapping(value = "/editCity", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity editCity(@RequestParam(value = "name", required = false) String name,
                                   @RequestParam(value = "countryName", required = false) String countryName,
                                   @RequestParam(value = "state", required = false) String state) {
        return ResponseEntity.status(200).body(hotelService.editCity(name, countryName, state));
    }

    @RequestMapping(value = "/getCountryList", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity getCountryList(@RequestParam(value = "type", required = false) String type) {
        return ResponseEntity.status(200).body(hotelService.getCountryList(type));
    }

    @RequestMapping(value = "/getCityList", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity getCityList(@RequestParam(value = "type", required = false) String type) {
        return ResponseEntity.status(200).body(hotelService.getCityList(type));
    }

    @RequestMapping(value = "/saveCountry", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity saveCountry(@RequestBody CountryPojo countryPojo) {
        return ResponseEntity.status(200).body(hotelService.saveCountry(countryPojo));
    }

    @RequestMapping(value = "/saveBooking", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity saveBooking(@RequestBody BookingPojo bookingPojo) {
        return ResponseEntity.status(200).body(hotelService.saveBooking(bookingPojo));
    }

    @RequestMapping(value = "/saveCity", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity saveCity(@RequestBody CityPojo cityPojo) {
        return ResponseEntity.status(200).body(hotelService.saveCity(cityPojo));
    }


    @RequestMapping(value = "/deleteCountry", method = RequestMethod.POST, produces = "application/json")
    public void deleteCountry(@RequestParam(value = "countryName", required = false) String countryName) {
        hotelService.deleteCountry(countryName);
    }

    @RequestMapping(value = "/deleteCity", method = RequestMethod.POST, produces = "application/json")
    public void deleteCity(@RequestParam(value = "name", required = false) String name,
                           @RequestParam(value = "country", required = false) String countryName,
                           @RequestParam(value = "state", required = false) String state) {
        hotelService.deleteCity(name, countryName, state);
    }

    @RequestMapping(value = "/editCountry", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity editCountry(@RequestParam(value = "countryName", required = false) String countryName) {
        return ResponseEntity.status(200).body(hotelService.editCountry(countryName));
    }



    @RequestMapping(value = "/DeleteRooms", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity DeleteRooms(@RequestParam(value = "id") Long id) {
        hotelService.DeleteRooms(id);
        return ResponseEntity.status(200).body(null);
    }



    @RequestMapping(value = "/getRoomTypesList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public EntityResponse getRoomTypesList() {
        List<RoomTypesPojo> roomTypesPojos = hotelService.getRoomTypesList();
        return new EntityResponse(HttpStatus.OK.value(), " success", roomTypesPojos);
    }

    @RequestMapping(value = "/getRoomsList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public EntityResponse getRoomsList() {
        List<RoomsPojo> roomsPojo = hotelService.getRoomsList();
        return new EntityResponse(HttpStatus.OK.value(), " success", roomsPojo);
    }


    @RequestMapping(value = "/getcheckinList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public EntityResponse getcheckinList(@RequestParam(value = "type") String status) {
        List<OrdersPojo> ordersPojo = hotelService.getcheckinList(status);
        return new EntityResponse(HttpStatus.OK.value(), " success", ordersPojo);
    }


    @RequestMapping(value = "/userValidate", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public User userValidate(@RequestBody UserPojo userPojo) {
        return hotelService.userValidate(userPojo);
    }

    @RequestMapping(value = "/getRoomList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public EntityResponse getRoomList(@RequestParam(value = "roomTypeId") Long roomTypeId) {
        List<Rooms> rooms = hotelService.roomList(roomTypeId);
        return new EntityResponse(HttpStatus.OK.value(), " success", rooms);
    }

    @RequestMapping(value = "/getuserDetails", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity getuserDetails(@RequestBody UserPojo userpojo) {
        userpojo = hotelService.getuserDetails(userpojo);
        return ResponseEntity.status(200).body(userpojo);
    }



    @RequestMapping(value = "/getUserList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public EntityResponse getUserList() {
        List<UserPojo> userPojo = hotelService.getUserList();
        return new EntityResponse(HttpStatus.OK.value(), " success", userPojo);
    }


    @RequestMapping(value = "/getDeleteUser", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity getDeleteUser(@RequestParam(value = "useraccountId") Long useraccountId) {
        hotelService.getDeleteUser(useraccountId);
        return ResponseEntity.status(200).body(null);
    }



    @RequestMapping(value = "/getDeleteConfigurator", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity getDeleteConfigurator(@RequestParam(value = "id") Long id) {
        hotelService.getDeleteConfigurator(id);
        return ResponseEntity.status(200).body(null);
    }

    @RequestMapping(value = "/getDeleteHotel", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity getDeleteHotel(@RequestParam(value = "id") Long id) {
        hotelService.getDeleteHotel(id);
        return ResponseEntity.status(200).body(null);
    }

    @RequestMapping(value = "/getRoomTypeObj", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public EntityResponse getRoomTypeObj(@RequestParam(value = "roomTypeId") Long roomTypeId) {
        RoomTypes roomTypes = hotelService.roomTypeObj(roomTypeId);
        return new EntityResponse(HttpStatus.OK.value(), " success", roomTypes);
    }


    @RequestMapping(value = "/getConfiguratorList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public EntityResponse getConfiguratorList() {
        List<ConfiguratorPojo> configuratorPojos = hotelService.getConfiguratorList();
        return new EntityResponse(HttpStatus.OK.value(), " success", configuratorPojos);
    }


    @RequestMapping(value = "/savePaidServices", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity savePaidServices(@RequestBody ServicesPojo servicesPojo) throws Exception {
        servicesPojo = hotelService.savePaidServices(servicesPojo);
        return ResponseEntity.status(200).body(servicesPojo);
    }

    @RequestMapping(value = "/getDeletePaidServices", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity getDeletePaidServices(@RequestParam(value = "id") Long id) {
        hotelService.getDeletePaidServices(id);
        return ResponseEntity.status(200).body(null);
    }

    @RequestMapping(value = "/getPaidServiceList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public EntityResponse getPaidServiceList() {
        List<ServicesPojo> servicesPojos = hotelService.getServicesList();
        return new EntityResponse(HttpStatus.OK.value(), " success", servicesPojos);
    }

    @RequestMapping(value = "/getServiceListBasedOnRoomType", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public EntityResponse getServiceListBasedOnRoomType(@RequestParam(value = "roomTypeId") Long roomTypeId) {
        List<ServicesPojo> servicesPojos = hotelService.getServiceListBasedOnRoomType(roomTypeId);
        return new EntityResponse(HttpStatus.OK.value(), " success", servicesPojos);
    }



    @RequestMapping(value = "/getStateCity", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity getStateCity(@RequestParam(value = "stateName", required = false) String stateName) {
        return ResponseEntity.status(200).body(hotelService.getStateCity(stateName));
    }

    @RequestMapping(value = "getGuestList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public EntityResponse getGuestList() {
        List<GuestsPojo> guestsPojos = hotelService.getGuestsList();
        return new EntityResponse(HttpStatus.OK.value(), " success", guestsPojos);
    }



    @RequestMapping(value = "/getStateListBasedOnCountry", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity getStateListBasedOnCountry(@RequestParam(value = "country", required = false) String country1) {
        return ResponseEntity.status(200).body(hotelService.getStateListBasedOnCountry(country1));
    }



    @RequestMapping(value = "/saveOrUpdateformsetup", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity saveOrUpdateformsetup(@RequestBody FormSetUpPojo formSetUpPojo) {
        return ResponseEntity.status(200).body(hotelService.saveOrUpdateformsetup(formSetUpPojo));
    }

    @RequestMapping(value = "/getFormsetupList", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity getFormsetupList() {
        return ResponseEntity.status(200).body(hotelService.getFormSetupList());
    }

    @RequestMapping(value = "/editFormSetupMethod", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity editFormSetupMethod(@RequestParam(value = "typeName", required = false) String typeName) {
        return ResponseEntity.status(200).body(hotelService.editFormsetupMethod(typeName));
    }


    @RequestMapping(value = "/getPaginatedRoomtypesList", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity getPaginatedRoomtypesList(@RequestParam(value = "searchText", required = false) String searchText,
                                                    @RequestBody BasePojo basePojo) {
        return ResponseEntity.status(200).body(hotelService.getPaginatedRoomtypesList(basePojo, searchText));
    }

    @RequestMapping(value = "/getPaginatedRoomList", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity getPaginatedRoomList(@RequestParam(value = "searchText", required = false) String searchText,
                                               @RequestBody BasePojo basePojo) {
        return ResponseEntity.status(200).body(hotelService.getPaginatedRoomList(basePojo, searchText));
    }

    @RequestMapping(value = "/getPaginatedPaidList", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity getPaginatedPaidList(@RequestParam(value = "searchText", required = false) String searchText,
                                               @RequestBody BasePojo basePojo) {
        return ResponseEntity.status(200).body(hotelService.getPaginatedPaidList(basePojo, searchText));
    }



    @RequestMapping(value = "/getPaginatedUsersList", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity getPaginatedUsersList(@RequestParam(value = "searchText", required = false) String searchText,
                                                @RequestBody BasePojo basePojo) {
        return ResponseEntity.status(200).body(hotelService.getPaginatedUsersList(basePojo, searchText));
    }

    @RequestMapping(value = "/getPaginatedCountryList", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity getPaginatedCountryList(@RequestParam(value = "searchText", required = false) String searchText,
                                                  @RequestBody BasePojo basePojo) {
        return ResponseEntity.status(200).body(hotelService.getPaginatedCountryList(basePojo, searchText));
    }
    @RequestMapping(value = "/getPaginatedCityList", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity getPaginatedCityList(@RequestParam(value = "searchText",required = false) String searchText,
                                                  @RequestBody BasePojo basePojo) {
        return ResponseEntity.status(200).body(hotelService.getPaginatedCityList(basePojo,searchText));
    }


    @RequestMapping(value = "/getPaginatedGuestsList", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity getPaginatedGuestsList(@RequestParam(value = "searchText",required = false) String searchText,
                                                 @RequestBody BasePojo basePojo) {
        return ResponseEntity.status(200).body(hotelService.getPaginatedGuestsList(basePojo,searchText));
    }




    @RequestMapping(path = "/UsersExport", method = RequestMethod.GET)
    public ResponseEntity UsersExport(@RequestParam(value = "type") String type,
                                      @RequestParam(value = "val") String searchText) {
        HttpHeaders headers = new HttpHeaders();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        if (StringUtils.equalsIgnoreCase(type, "Pdf")) {
            hotelService.downloadUsersPdf(outputStream, searchText);
            headers.add("Content-Disposition", "attachment; filename=\"" + "Users.pdf" + "\"");
        } else if (StringUtils.equalsIgnoreCase(type, "Excel")) {
            hotelService.downloadUsersExcelSheet(outputStream,searchText);
            headers.add("Content-Disposition", "attachment; filename=\"" + "users.xlsx" + "\"");
        }
        ByteArrayResource byteArrayResource = new ByteArrayResource(outputStream.toByteArray());
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(byteArrayResource.contentLength())
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(byteArrayResource);
    }




 @RequestMapping(path = "/GuestsExport", method = RequestMethod.GET)
    public ResponseEntity GuestsExport(@RequestParam(value = "type") String type,
                                      @RequestParam(value = "val") String searchText) {
        HttpHeaders headers = new HttpHeaders();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        if (StringUtils.equalsIgnoreCase(type, "Pdf")) {
            hotelService.downloadGuestsPdf(outputStream, searchText);
            headers.add("Content-Disposition", "attachment; filename=\"" + "Guests.pdf" + "\"");
        } else if (StringUtils.equalsIgnoreCase(type, "Excel")) {
            hotelService.downloadGuestsExcelSheet(outputStream,searchText);
            headers.add("Content-Disposition", "attachment; filename=\"" + "Guests.xlsx" + "\"");
        }
        ByteArrayResource byteArrayResource = new ByteArrayResource(outputStream.toByteArray());
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(byteArrayResource.contentLength())
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(byteArrayResource);
    }



    @RequestMapping(value = "/guestsImportSave" ,method = RequestMethod.POST)
    public ResponseEntity guestsImportSave(@RequestParam("myFile") MultipartFile uploadfiles) throws Exception {
        System.out.println(uploadfiles.getOriginalFilename());
        try {
            boolean b=false;
            XSSFWorkbook workbook = new XSSFWorkbook(uploadfiles.getInputStream());
            XSSFSheet sheet = workbook.getSheetAt(0);
            for (int i = 1; i < sheet.getLastRowNum() + 1; i++) {
                Row row = sheet.getRow(i);
                if(row==null)
                    break;
                if(row!=null&&row.getPhysicalNumberOfCells()>0) {
                    Cell firstname = row.getCell(0);
                    Cell lastname = row.getCell(1);
                    Cell gender = row.getCell(2);
                    Cell email = row.getCell(3);
                    Cell address = row.getCell(4);
                    Cell mobile = row.getCell(5);
                    Cell companyname = row.getCell(6);
                    Cell dob = row.getCell(7);
                    Cell country = row.getCell(8);
                    Cell vip = row.getCell(9);
                    GuestsPojo guestsPojo = new GuestsPojo();
                    guestsPojo.setId( 0L );
                    guestsPojo.setFirstname(firstname == null ? null :  firstname.toString() );
                    guestsPojo.setLastname(lastname == null ? null :  lastname.toString() );
                    guestsPojo.setGender(gender == null ? null :  gender.toString() );
                    guestsPojo.setEmail(email == null ? null :  email.toString() );
                    guestsPojo.setAddress(address == null ? null :  address.toString() );
                    guestsPojo.setMobile(mobile == null ? null : new java.text.DecimalFormat("0").format( mobile.getNumericCellValue()));
                    guestsPojo.setCompanyname(companyname == null ? null :  companyname.toString() );
                    SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
                    if(dob!=null) {
                        Date formattedDate = formatter.parse( dob.toString() );
                        guestsPojo.setDob(dob == null ? null : new java.sql.Date(formattedDate.getTime()) );
                    }
                    guestsPojo.setCountry(country == null ? null :  country.toString() );
                    guestsPojo.setVip(vip == null ? "true" : vip.toString() );

//                    guestsPojo.setVip(vip == null ? null :  vip.toString() );
                    guestsPojo.setStatus("Active");

                    hotelService.saveguests(guestsPojo);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity(HttpStatus.OK);
    }
    @RequestMapping(value = "/usersImportSave" ,method = RequestMethod.POST)
    public ResponseEntity usersImportSave(@RequestParam("myFile") MultipartFile uploadfiles) throws Exception {
        System.out.println(uploadfiles.getOriginalFilename());
        try {
            boolean b=false;
            XSSFWorkbook workbook = new XSSFWorkbook(uploadfiles.getInputStream());
            XSSFSheet sheet = workbook.getSheetAt(0);
            for (int i = 1; i < sheet.getLastRowNum() + 1; i++) {
                Row row = sheet.getRow(i);
                if(row==null)
                    break;
                if(row!=null&&row.getPhysicalNumberOfCells()>0) {
                    Cell userName = row.getCell(0);
                    Cell passwordUser = row.getCell(1);
                    Cell full_name = row.getCell(2);
                    Cell securityQuestion = row.getCell(3);
                    Cell securityAnswer = row.getCell(4);
                    Cell phone = row.getCell(5);
                    Cell email = row.getCell(6);
                    UserPojo userPojo = new UserPojo();
                    userPojo.setUseraccountId( 0L );
                    userPojo.setUserName(userName == null ? null :  userName.toString() );
                    userPojo.setPasswordUser(passwordUser == null ? null :  passwordUser.toString() );
                    userPojo.setFull_name(full_name == null ? null :  full_name.toString() );
                    userPojo.setSecurityQuestion(securityQuestion == null ? null :  securityQuestion.toString() );
                    userPojo.setSecurityAnswer(securityAnswer == null ? null :  securityAnswer.toString() );
                    userPojo.setPhone(phone == null ? null : new java.text.DecimalFormat("0").format( phone.getNumericCellValue()));
                    userPojo.setEmail(email == null ? null :  email.toString() );
                    userPojo.setStatus("Active");
                    hotelService.saveUserDetails(userPojo);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity(HttpStatus.OK);
    }
}
