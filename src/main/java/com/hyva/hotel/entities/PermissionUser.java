package com.hyva.hotel.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "permission_user")
public class PermissionUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private Long userid;
    private String booking;
    private String bookingadd;
    private String bookingpayment;
    private String paid;
    private String priceadd;
    private String pricedelete;
    private String priceedit;
    private String priceview;
    private String price;
    private String roomsview;
    private String roomsadd;
    private String roomsdelete;
    private String roomsedit;
    private String roomshousekeeping;
    private String rooms;
    private String roomtypeadd;
    private String roomtypedelete;
    private String roomtypeedit;
    private String roomtypeview;
    private String roomtype;
    private String availityview;
    private String availity;
    private String menusview;
    private String menusdelete;
    private String menusedit;
    private String menuscreate;
    private String menus;
    private String guiestview;
    private String guiestaddvip;
    private String guiestedit;
    private String guiestdelete;
    private String guiestadd;
    private String guiest;
    private String checkinview;
    private String checkincheckout;
    private String checkindiscount;
    private String checkinadditem;
    private String checkinsaveroom;
    private String checkinedit;
    private String checkinpayment;
    private String checkinadd;
    private String checkin;
    private String bookingview;
    private String bookingcheckin;
    private String bookingsaveroom;
    private String bookingedit;
    private String paidview;
    private String paidedit;
    private String paiddelete;
    private String paidadd;
    private String coupon;
    private String couponview;
    private String couponedit;
    private String coupondelete;
    private String couponadd;
    private String floors;
    private String floorsview;
    private String floorsedit;
    private String floorsdelete;
    private String floorsadd;
    private String amenities;
    private String amenitiesview;
    private String amenitiesedit;
    private String amenitiesdelete;
    private String amenitiesadd;
    private String housekeep;
    private String housekeepview;
    private String housekeepedit;
    private String housekeepdelete;
    private String housekeepadd;
    private String reports;
    private String reportsoccupancyview;
    private String reportsguiestview;
    private String reportsfinancialview;
    private String reportscouponview;
    private String reportsexpenseview;
    private String emp;
    private String empview;
    private String empedit;
    private String empdelete;
    private String empadd;
    private String emppermission;
    private String dept;
    private String deptedit;
    private String deptview;
    private String deptdelete;
    private String deptadd;
    private String desig;
    private String desigview;
    private String desigedit;
    private String desigdelete;
    private String desigadd;
    private String pages;
    private String banner;
    private String gallery;
    private String mailtemplate;
    private String setting;
    private String lang;
    private String currency;
    private String location;
    private String taskmanager;
    private String testmonial;
    private String salesdiscount;
    private String expenses;
    private String expensesview;
    private String expensesedit;
    private String expensesdelete;
    private String expensesadd;
    private String expensescat;
    private String expensescatview;
    private String expensescatedit;
    private String expensescatdelete;
    private String expensescatadd;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public String getBooking() {
        return booking;
    }

    public void setBooking(String booking) {
        this.booking = booking;
    }

    public String getBookingadd() {
        return bookingadd;
    }

    public void setBookingadd(String bookingadd) {
        this.bookingadd = bookingadd;
    }

    public String getBookingpayment() {
        return bookingpayment;
    }

    public void setBookingpayment(String bookingpayment) {
        this.bookingpayment = bookingpayment;
    }

    public String getPaid() {
        return paid;
    }

    public void setPaid(String paid) {
        this.paid = paid;
    }

    public String getPriceadd() {
        return priceadd;
    }

    public void setPriceadd(String priceadd) {
        this.priceadd = priceadd;
    }

    public String getPricedelete() {
        return pricedelete;
    }

    public void setPricedelete(String pricedelete) {
        this.pricedelete = pricedelete;
    }

    public String getPriceedit() {
        return priceedit;
    }

    public void setPriceedit(String priceedit) {
        this.priceedit = priceedit;
    }

    public String getPriceview() {
        return priceview;
    }

    public void setPriceview(String priceview) {
        this.priceview = priceview;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRoomsview() {
        return roomsview;
    }

    public void setRoomsview(String roomsview) {
        this.roomsview = roomsview;
    }

    public String getRoomsadd() {
        return roomsadd;
    }

    public void setRoomsadd(String roomsadd) {
        this.roomsadd = roomsadd;
    }

    public String getRoomsdelete() {
        return roomsdelete;
    }

    public void setRoomsdelete(String roomsdelete) {
        this.roomsdelete = roomsdelete;
    }

    public String getRoomsedit() {
        return roomsedit;
    }

    public void setRoomsedit(String roomsedit) {
        this.roomsedit = roomsedit;
    }

    public String getRoomshousekeeping() {
        return roomshousekeeping;
    }

    public void setRoomshousekeeping(String roomshousekeeping) {
        this.roomshousekeeping = roomshousekeeping;
    }

    public String getRooms() {
        return rooms;
    }

    public void setRooms(String rooms) {
        this.rooms = rooms;
    }

    public String getRoomtypeadd() {
        return roomtypeadd;
    }

    public void setRoomtypeadd(String roomtypeadd) {
        this.roomtypeadd = roomtypeadd;
    }

    public String getRoomtypedelete() {
        return roomtypedelete;
    }

    public void setRoomtypedelete(String roomtypedelete) {
        this.roomtypedelete = roomtypedelete;
    }

    public String getRoomtypeedit() {
        return roomtypeedit;
    }

    public void setRoomtypeedit(String roomtypeedit) {
        this.roomtypeedit = roomtypeedit;
    }

    public String getRoomtypeview() {
        return roomtypeview;
    }

    public void setRoomtypeview(String roomtypeview) {
        this.roomtypeview = roomtypeview;
    }

    public String getRoomtype() {
        return roomtype;
    }

    public void setRoomtype(String roomtype) {
        this.roomtype = roomtype;
    }

    public String getAvailityview() {
        return availityview;
    }

    public void setAvailityview(String availityview) {
        this.availityview = availityview;
    }

    public String getAvaility() {
        return availity;
    }

    public void setAvaility(String availity) {
        this.availity = availity;
    }

    public String getMenusview() {
        return menusview;
    }

    public void setMenusview(String menusview) {
        this.menusview = menusview;
    }

    public String getMenusdelete() {
        return menusdelete;
    }

    public void setMenusdelete(String menusdelete) {
        this.menusdelete = menusdelete;
    }

    public String getMenusedit() {
        return menusedit;
    }

    public void setMenusedit(String menusedit) {
        this.menusedit = menusedit;
    }

    public String getMenuscreate() {
        return menuscreate;
    }

    public void setMenuscreate(String menuscreate) {
        this.menuscreate = menuscreate;
    }

    public String getMenus() {
        return menus;
    }

    public void setMenus(String menus) {
        this.menus = menus;
    }

    public String getGuiestview() {
        return guiestview;
    }

    public void setGuiestview(String guiestview) {
        this.guiestview = guiestview;
    }

    public String getGuiestaddvip() {
        return guiestaddvip;
    }

    public void setGuiestaddvip(String guiestaddvip) {
        this.guiestaddvip = guiestaddvip;
    }

    public String getGuiestedit() {
        return guiestedit;
    }

    public void setGuiestedit(String guiestedit) {
        this.guiestedit = guiestedit;
    }

    public String getGuiestdelete() {
        return guiestdelete;
    }

    public void setGuiestdelete(String guiestdelete) {
        this.guiestdelete = guiestdelete;
    }

    public String getGuiestadd() {
        return guiestadd;
    }

    public void setGuiestadd(String guiestadd) {
        this.guiestadd = guiestadd;
    }

    public String getGuiest() {
        return guiest;
    }

    public void setGuiest(String guiest) {
        this.guiest = guiest;
    }

    public String getCheckinview() {
        return checkinview;
    }

    public void setCheckinview(String checkinview) {
        this.checkinview = checkinview;
    }

    public String getCheckincheckout() {
        return checkincheckout;
    }

    public void setCheckincheckout(String checkincheckout) {
        this.checkincheckout = checkincheckout;
    }

    public String getCheckindiscount() {
        return checkindiscount;
    }

    public void setCheckindiscount(String checkindiscount) {
        this.checkindiscount = checkindiscount;
    }

    public String getCheckinadditem() {
        return checkinadditem;
    }

    public void setCheckinadditem(String checkinadditem) {
        this.checkinadditem = checkinadditem;
    }

    public String getCheckinsaveroom() {
        return checkinsaveroom;
    }

    public void setCheckinsaveroom(String checkinsaveroom) {
        this.checkinsaveroom = checkinsaveroom;
    }

    public String getCheckinedit() {
        return checkinedit;
    }

    public void setCheckinedit(String checkinedit) {
        this.checkinedit = checkinedit;
    }

    public String getCheckinpayment() {
        return checkinpayment;
    }

    public void setCheckinpayment(String checkinpayment) {
        this.checkinpayment = checkinpayment;
    }

    public String getCheckinadd() {
        return checkinadd;
    }

    public void setCheckinadd(String checkinadd) {
        this.checkinadd = checkinadd;
    }

    public String getCheckin() {
        return checkin;
    }

    public void setCheckin(String checkin) {
        this.checkin = checkin;
    }

    public String getBookingview() {
        return bookingview;
    }

    public void setBookingview(String bookingview) {
        this.bookingview = bookingview;
    }

    public String getBookingcheckin() {
        return bookingcheckin;
    }

    public void setBookingcheckin(String bookingcheckin) {
        this.bookingcheckin = bookingcheckin;
    }

    public String getBookingsaveroom() {
        return bookingsaveroom;
    }

    public void setBookingsaveroom(String bookingsaveroom) {
        this.bookingsaveroom = bookingsaveroom;
    }

    public String getBookingedit() {
        return bookingedit;
    }

    public void setBookingedit(String bookingedit) {
        this.bookingedit = bookingedit;
    }

    public String getPaidview() {
        return paidview;
    }

    public void setPaidview(String paidview) {
        this.paidview = paidview;
    }

    public String getPaidedit() {
        return paidedit;
    }

    public void setPaidedit(String paidedit) {
        this.paidedit = paidedit;
    }

    public String getPaiddelete() {
        return paiddelete;
    }

    public void setPaiddelete(String paiddelete) {
        this.paiddelete = paiddelete;
    }

    public String getPaidadd() {
        return paidadd;
    }

    public void setPaidadd(String paidadd) {
        this.paidadd = paidadd;
    }

    public String getCoupon() {
        return coupon;
    }

    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }

    public String getCouponview() {
        return couponview;
    }

    public void setCouponview(String couponview) {
        this.couponview = couponview;
    }

    public String getCouponedit() {
        return couponedit;
    }

    public void setCouponedit(String couponedit) {
        this.couponedit = couponedit;
    }

    public String getCoupondelete() {
        return coupondelete;
    }

    public void setCoupondelete(String coupondelete) {
        this.coupondelete = coupondelete;
    }

    public String getCouponadd() {
        return couponadd;
    }

    public void setCouponadd(String couponadd) {
        this.couponadd = couponadd;
    }

    public String getFloors() {
        return floors;
    }

    public void setFloors(String floors) {
        this.floors = floors;
    }

    public String getFloorsview() {
        return floorsview;
    }

    public void setFloorsview(String floorsview) {
        this.floorsview = floorsview;
    }

    public String getFloorsedit() {
        return floorsedit;
    }

    public void setFloorsedit(String floorsedit) {
        this.floorsedit = floorsedit;
    }

    public String getFloorsdelete() {
        return floorsdelete;
    }

    public void setFloorsdelete(String floorsdelete) {
        this.floorsdelete = floorsdelete;
    }

    public String getFloorsadd() {
        return floorsadd;
    }

    public void setFloorsadd(String floorsadd) {
        this.floorsadd = floorsadd;
    }

    public String getAmenities() {
        return amenities;
    }

    public void setAmenities(String amenities) {
        this.amenities = amenities;
    }

    public String getAmenitiesview() {
        return amenitiesview;
    }

    public void setAmenitiesview(String amenitiesview) {
        this.amenitiesview = amenitiesview;
    }

    public String getAmenitiesedit() {
        return amenitiesedit;
    }

    public void setAmenitiesedit(String amenitiesedit) {
        this.amenitiesedit = amenitiesedit;
    }

    public String getAmenitiesdelete() {
        return amenitiesdelete;
    }

    public void setAmenitiesdelete(String amenitiesdelete) {
        this.amenitiesdelete = amenitiesdelete;
    }

    public String getAmenitiesadd() {
        return amenitiesadd;
    }

    public void setAmenitiesadd(String amenitiesadd) {
        this.amenitiesadd = amenitiesadd;
    }

    public String getHousekeep() {
        return housekeep;
    }

    public void setHousekeep(String housekeep) {
        this.housekeep = housekeep;
    }

    public String getHousekeepview() {
        return housekeepview;
    }

    public void setHousekeepview(String housekeepview) {
        this.housekeepview = housekeepview;
    }

    public String getHousekeepedit() {
        return housekeepedit;
    }

    public void setHousekeepedit(String housekeepedit) {
        this.housekeepedit = housekeepedit;
    }

    public String getHousekeepdelete() {
        return housekeepdelete;
    }

    public void setHousekeepdelete(String housekeepdelete) {
        this.housekeepdelete = housekeepdelete;
    }

    public String getHousekeepadd() {
        return housekeepadd;
    }

    public void setHousekeepadd(String housekeepadd) {
        this.housekeepadd = housekeepadd;
    }

    public String getReports() {
        return reports;
    }

    public void setReports(String reports) {
        this.reports = reports;
    }

    public String getReportsoccupancyview() {
        return reportsoccupancyview;
    }

    public void setReportsoccupancyview(String reportsoccupancyview) {
        this.reportsoccupancyview = reportsoccupancyview;
    }

    public String getReportsguiestview() {
        return reportsguiestview;
    }

    public void setReportsguiestview(String reportsguiestview) {
        this.reportsguiestview = reportsguiestview;
    }

    public String getReportsfinancialview() {
        return reportsfinancialview;
    }

    public void setReportsfinancialview(String reportsfinancialview) {
        this.reportsfinancialview = reportsfinancialview;
    }

    public String getReportscouponview() {
        return reportscouponview;
    }

    public void setReportscouponview(String reportscouponview) {
        this.reportscouponview = reportscouponview;
    }

    public String getReportsexpenseview() {
        return reportsexpenseview;
    }

    public void setReportsexpenseview(String reportsexpenseview) {
        this.reportsexpenseview = reportsexpenseview;
    }

    public String getEmp() {
        return emp;
    }

    public void setEmp(String emp) {
        this.emp = emp;
    }

    public String getEmpview() {
        return empview;
    }

    public void setEmpview(String empview) {
        this.empview = empview;
    }

    public String getEmpedit() {
        return empedit;
    }

    public void setEmpedit(String empedit) {
        this.empedit = empedit;
    }

    public String getEmpdelete() {
        return empdelete;
    }

    public void setEmpdelete(String empdelete) {
        this.empdelete = empdelete;
    }

    public String getEmpadd() {
        return empadd;
    }

    public void setEmpadd(String empadd) {
        this.empadd = empadd;
    }

    public String getEmppermission() {
        return emppermission;
    }

    public void setEmppermission(String emppermission) {
        this.emppermission = emppermission;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getDeptedit() {
        return deptedit;
    }

    public void setDeptedit(String deptedit) {
        this.deptedit = deptedit;
    }

    public String getDeptview() {
        return deptview;
    }

    public void setDeptview(String deptview) {
        this.deptview = deptview;
    }

    public String getDeptdelete() {
        return deptdelete;
    }

    public void setDeptdelete(String deptdelete) {
        this.deptdelete = deptdelete;
    }

    public String getDeptadd() {
        return deptadd;
    }

    public void setDeptadd(String deptadd) {
        this.deptadd = deptadd;
    }

    public String getDesig() {
        return desig;
    }

    public void setDesig(String desig) {
        this.desig = desig;
    }

    public String getDesigview() {
        return desigview;
    }

    public void setDesigview(String desigview) {
        this.desigview = desigview;
    }

    public String getDesigedit() {
        return desigedit;
    }

    public void setDesigedit(String desigedit) {
        this.desigedit = desigedit;
    }

    public String getDesigdelete() {
        return desigdelete;
    }

    public void setDesigdelete(String desigdelete) {
        this.desigdelete = desigdelete;
    }

    public String getDesigadd() {
        return desigadd;
    }

    public void setDesigadd(String desigadd) {
        this.desigadd = desigadd;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getGallery() {
        return gallery;
    }

    public void setGallery(String gallery) {
        this.gallery = gallery;
    }

    public String getMailtemplate() {
        return mailtemplate;
    }

    public void setMailtemplate(String mailtemplate) {
        this.mailtemplate = mailtemplate;
    }

    public String getSetting() {
        return setting;
    }

    public void setSetting(String setting) {
        this.setting = setting;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTaskmanager() {
        return taskmanager;
    }

    public void setTaskmanager(String taskmanager) {
        this.taskmanager = taskmanager;
    }

    public String getTestmonial() {
        return testmonial;
    }

    public void setTestmonial(String testmonial) {
        this.testmonial = testmonial;
    }

    public String getSalesdiscount() {
        return salesdiscount;
    }

    public void setSalesdiscount(String salesdiscount) {
        this.salesdiscount = salesdiscount;
    }

    public String getExpenses() {
        return expenses;
    }

    public void setExpenses(String expenses) {
        this.expenses = expenses;
    }

    public String getExpensesview() {
        return expensesview;
    }

    public void setExpensesview(String expensesview) {
        this.expensesview = expensesview;
    }

    public String getExpensesedit() {
        return expensesedit;
    }

    public void setExpensesedit(String expensesedit) {
        this.expensesedit = expensesedit;
    }

    public String getExpensesdelete() {
        return expensesdelete;
    }

    public void setExpensesdelete(String expensesdelete) {
        this.expensesdelete = expensesdelete;
    }

    public String getExpensesadd() {
        return expensesadd;
    }

    public void setExpensesadd(String expensesadd) {
        this.expensesadd = expensesadd;
    }

    public String getExpensescat() {
        return expensescat;
    }

    public void setExpensescat(String expensescat) {
        this.expensescat = expensescat;
    }

    public String getExpensescatview() {
        return expensescatview;
    }

    public void setExpensescatview(String expensescatview) {
        this.expensescatview = expensescatview;
    }

    public String getExpensescatedit() {
        return expensescatedit;
    }

    public void setExpensescatedit(String expensescatedit) {
        this.expensescatedit = expensescatedit;
    }

    public String getExpensescatdelete() {
        return expensescatdelete;
    }

    public void setExpensescatdelete(String expensescatdelete) {
        this.expensescatdelete = expensescatdelete;
    }

    public String getExpensescatadd() {
        return expensescatadd;
    }

    public void setExpensescatadd(String expensescatadd) {
        this.expensescatadd = expensescatadd;
    }
}
