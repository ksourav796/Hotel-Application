app.controller('bookingsController',
    function ($scope, $rootScope, $http, $location, $window, $filter, Notification) {
        $scope.hotelServerURL = "/hotel";
        $scope.bshimServerURL = "/hotel";
        $scope.word = /^[a-z]+[a-z0-9._]+@[a-z]+\.[a-z.]{2,5}$/;
        $scope.customerId = 1;
        $scope.pricePerNightList = [];
        $scope.totalPricePerNightList = [];
        $scope.relOrderPaidServiceList = [];
        $scope.operation = 'Create';
        $scope.customer = 1;
        $scope.today = new Date();

        $scope.reloadPage = function () {
            $window.location.reload();
        };

        $scope.dateOptions = {
            minDate: new Date()
        };

        $scope.format = 'dd/MM/yyyy';
        $scope.openDate1 = function () {
            $scope.popup1.opened = true;
        };

        $scope.popup1 = {
            opened: false
        };

        $scope.getCountryList = function (type) {
            if (angular.isUndefined(type)) {
                type = "";
            }
            $http.post($scope.bshimServerURL + '/getCountryList?type=' + type).then(function (response) {
                var data = response.data;
                $scope.countryList = data;

            }, function (error) {
                Notification.error({
                    message: 'Something went wrong, please try again',
                    positionX: 'center',
                    delay: 2000
                });
            })
        };
        $scope.getCountryList();

        $scope.getStateListBasedonContry=function (country) {
            $http.post($scope.bshimServerURL + '/getStateListBasedOnCountry?country=' + country).then(function (response) {
                var data = response.data;
                $scope.stateList = data;
            }, function (error) {
                Notification.error({
                    message: 'Something went wrong, please try again',
                    positionX: 'center',
                    delay: 2000
                });
            })
        }

        $scope.stateCity=function (state) {
            $http.post($scope.bshimServerURL + '/getStateCity?stateName=' + state).then(function (response) {
                var data = response.data;
                $scope.cityList = data;
            }, function (error) {
                Notification.error({
                    message: 'Something went wrong, please try again',
                    positionX: 'center',
                    delay: 2000
                });
            })
        }

        $scope.addpayment = function () {
            $scope.ordernumber=$scope.CheckOutOrderObj.order_no;
            $scope.addeddate=$filter('date')(new Date(),'MM/dd/yyyy');

            $("#payment_modal").modal('show');
        };

        $scope.getCountryList = function (type) {
            if (angular.isUndefined(type)) {
                type = "";
            }
            $http.post($scope.bshimServerURL + '/getCountryList?&type=' + type).then(function (response) {
                var data = response.data;
                var i = 0;
                $scope.countryList = data;
                $scope.listStatus = true;
                angular.forEach($scope.countryList, function (value, key) {
                    if (value.countryName.toUpperCase() == type.toUpperCase()) {
                        if (value.status == 'InActive') {
                            i++;
                        }
                    }
                })
                if (i > 0) {
                    Notification.warning({
                        message: 'country Became InActive',
                        positionX: 'center',
                        delay: 2000
                    });
                }

            }, function (error) {
                Notification.error({
                    message: 'Something went wrong, please try again',
                    positionX: 'center',
                    delay: 2000
                });
            })
        };


        $scope.addNewcountryPopulate = function () {
            $scope.CountryNameText = "";
            $scope.StatusText = "Active";
            $("#submit").text("Save");
            $('#country-title').text("Add Country");
            $("#add_new_country_modal").modal('show');
        };

        $scope.saveCountry = function () {
            if ($scope.CountryNameText == '' || $scope.CountryNameText == null || angular.isUndefined($scope.CountryNameText)) {
                Notification.warning({message: 'Country Name can not be empty', positionX: 'center', delay: 2000});
            }
            else {
                var saveCountryDetails;
                saveCountryDetails = {
                    countryId: $scope.countryId,
                    countryName: $scope.CountryNameText,
                    status: $scope.StatusText
                };
                $http.post($scope.bshimServerURL + '/saveCountry', angular.toJson(saveCountryDetails, "Create")).then(function (response, status, headers, config) {
                    var data = response.data;
                    if (data == "") {
                        Notification.error({message: 'Already exists', positionX: 'center', delay: 2000});
                    }
                    else {
                        $scope.getCountryList();
                        $("#add_new_country_modal").modal('hide');
                        if (!angular.isUndefined(data) && data !== null) {
                            $scope.countrySearchText = "";
                        }
                        Notification.success({
                            message: 'Country Created  successfully',
                            positionX: 'center',
                            delay: 2000
                        });
                    }
                }, function (error) {
                    Notification.error({
                        message: 'Something went wrong, please try again',
                        positionX: 'center',
                        delay: 2000
                    });
                });

            }
            ;
        };
        $scope.getCountryList();

        $scope.getPaginatedRoomtypesList = function (page) {
            switch (page) {
                case 'firstPage':
                    $scope.firstPage = true;
                    $scope.lastPage = false;
                    $scope.pageNo = 0;
                    $scope.isNext = false;
                    $scope.isPrev = false;
                    break;
                case 'lastPage':
                    $scope.lastPage = true;
                    $scope.firstPage = false;
                    $scope.pageNo = 0;
                    $scope.isNext = false;
                    $scope.isPrev = false;
                    break;
                case 'nextPage':
                    $scope.isNext = true;
                    $scope.isPrev = false;
                    $scope.lastPage = false;
                    $scope.firstPage = false;
                    $scope.pageNo = $scope.pageNo + 1;
                    break;
                case 'prevPage':
                    $scope.isPrev = true;
                    $scope.lastPage = false;
                    $scope.firstPage = false;
                    $scope.isNext = false;
                    $scope.pageNo = $scope.pageNo - 1;
                    break;
                default:
                    $scope.firstPage = true;
                    $scope.lastPage = false;
                    $scope.pageNo =0;
                    $scope.isNext=false;
                    $scope.isPrev = false;
            }
            var paginationDetails;
            paginationDetails={
                firstPage: $scope.firstPage,
                lastPage: $scope.lastPage,
                pageNo: $scope.pageNo,
                prevPage: $scope.isPrev,
                nextPage: $scope.isNext
            }
            if(angular.isUndefined($scope.searchText)){
                $scope.searchText="";
            }
            $http.post($scope.bshimServerURL + '/getPaginatedRoomtypesList?searchText='+$scope.searchText,angular.toJson(paginationDetails)).then(function (response) {
                var data = response.data;
                console.log(data);
                $scope.roomtypesList = data.list;
                $scope.first = data.firstPage;
                $scope.last = data.lastPage;
                $scope.prev = data.prevPage;
                $scope.next = data.nextPage;
                $scope.pageNo = data.pageNo;
            }, function (error) {
                Notification.error({
                    message: 'Something went wrong, please try again',
                    positionX: 'center',
                    delay: 2000
                });
            })
        };



        $scope.addroomtypes = function () {
            $scope.id=0;
            $scope.title="";
            $scope.description="";
            $scope.baseprice="";
            $scope.extrabedprice="";
            $scope.getPaginatedRoomtypesList();
            $("#submit").text("Save");
            $('#roomtypes-title').text("Add RoomTypes");
            $("#add_room_types").modal('show');

        };

        $scope.saveRoomTypes = function () {
            if ($scope.title == '' || $scope.title == null || angular.isUndefined($scope.title)) {
                Notification.error({message: 'Please Enter Title ', positionX: 'center', delay: 2000})
            }
            else if ($scope.baseprice == '' || $scope.baseprice == null || angular.isUndefined($scope.baseprice)) {
                Notification.error({message: 'Please Enter Base Price ', positionX: 'center', delay: 2000})
            }
            else if ($scope.baseprice<=0) {
                Notification.error({message: 'Base Price Should not be zero', positionX: 'center', delay: 2000})
            }
            else {
                var saveRoomTypesDetails;
                saveRoomTypesDetails = {
                    id: $scope.id,
                    title: $scope.title,
                    description: $scope.description,
                    base_price: $scope.baseprice,

                };
                $http.post($scope.bshimServerURL + "/saveRoomTypes", angular.toJson(saveRoomTypesDetails)).then(function (response) {
                    var data = response.data;
                    if (data == "") {
                        Notification.error({
                            message: 'Already exists',
                            positionX: 'center',
                            delay: 2000
                        });
                    } else {
                        $("#add_room_types").modal('hide');
                        $scope.getPaginatedRoomtypesList();
                        if ($scope.operation == 'Edit') {
                            Notification.success({
                                message: 'RoomTypes is Updated successfully',
                                positionX: 'center',
                                delay: 2000
                            });

                        } else {
                            Notification.success({
                                message: 'RoomTypes is Created  successfully',
                                positionX: 'center',
                                delay: 2000
                            });
                        }

                    }
                }, function (error) {
                    Notification.error({
                        message: 'Something went wrong, please try again',
                        positionX: 'center',
                        delay: 2000
                    });
                });
            };
        }


        $scope.addguests = function () {
            $scope.id = "0";
            $scope.firstname = "";
            $scope.lastname = "";
            $scope.gender = "";
            $scope.email = "";
            $scope.password = "";
            $scope.address = "";
            $scope.mobile = "";
            $scope.companyname = "";
            $scope.dob = "";
            $scope.country = null;
            $scope.statusText = "Active";
            $scope.vip = "";

            $scope.getGuestsList();
            $scope.getCountryList();
            $("#add_guests").modal('show');

        };
        $scope.saveguests = function () {
            if ($scope.firstname == '' || $scope.firstname == null || angular.isUndefined($scope.firstname)) {
                Notification.error({message: 'FirstName can not be empty', positionX: 'center', delay: 2000})
            }
            else if ($scope.lastname == '' || angular.isUndefined($scope.firstname) || $scope.lastname == null) {
                Notification.error({message: 'Lastname can not be empty', positionX: 'center', delay: 2000})
            }
            else if ($scope.email == '' || angular.isUndefined($scope.email) || $scope.email == null) {
                Notification.error({message: 'Email can not be empty', positionX: 'center', delay: 2000})
            }
            else if ($scope.mobile == '' || angular.isUndefined($scope.mobile) || $scope.mobile == null) {
                Notification.error({message: 'Mobile Number can not be empty', positionX: 'center', delay: 2000})
            }
            // else if ($scope.country == '' || angular.isUndefined($scope.country) || $scope.country == null) {
            //     Notification.error({message: 'Please Select Country', positionX: 'center', delay: 2000})
            // }
            else {
                var saveGuestDetails;
                saveGuestDetails = {
                    id: $scope.id,
                    firstname: $scope.firstname,
                    lastname: $scope.lastname,
                    gender: $scope.gender,
                    email: $scope.email,
                    password: $scope.password,
                    address: $scope.address,
                    mobile: $scope.mobile,
                    companyname: $scope.companyname,
                    dob: $scope.dob,
                    status: $scope.statusText,
                    country: $scope.countryName,
                    state_id: $scope.stateName,
                    city: $scope.city,
                    vip: $scope.vip
                };
                $http.post($scope.bshimServerURL + "/saveguests", angular.toJson(saveGuestDetails)).then(function (response) {
                    var data = response.data;
                    if (data == "" || data == 'undefined') {
                        Notification.error({message: ' Already exists', positionX: 'center', delay: 2000});
                    }
                    else {
                        $("#add_guests").modal('hide');
                        $scope.getGuestsList();
                        if ($scope.operation == 'Edit') {
                            Notification.success({
                                message: 'Guests is Updated successfully',
                                positionX: 'center',
                                delay: 2000
                            });

                        } else {
                            Notification.success({
                                message: 'Guests  Created  successfully',
                                positionX: 'center',
                                delay: 2000
                            });
                        }

                    }
                }, function (error) {
                    Notification.error({
                        message: 'Something went wrong, please try again',
                        positionX: 'center',
                        delay: 2000
                    });
                });
            }
            ;
        }
        $scope.format = 'dd/MM/yyyy';
        $scope.openDate = function () {
            $scope.popup8.opened = true;
        };

        $scope.popup8 = {
            opened: false
        };


        $scope.getGuestsList = function (searchText) {
            if (angular.isUndefined(searchText)) {
                $scope.searchText = "";
            }
            $http.post($scope.bshimServerURL + '/getGuestsList').then(function (response) {
                var data = response.data.object;
                $scope.guestsList = data;
            }, function (error) {
                Notification.error({
                    message: 'Something went wrong, please try again',
                    positionX: 'center',
                    delay: 2000
                });
            })
        };


        $scope.today = new Date();
        $scope.today1 = function () {
            $scope.dateOfBirth = new Date();
            $scope.dobAdmission = new Date();
            $scope.joinDate = new Date();

        };
        $scope.today1();
        $scope.format = 'dd/MM/yyyy';
        $scope.openDate1 = function () {
            $scope.popup1.opened = true;
        };

        $scope.popup1 = {
            opened: false
        };

        $scope.openDate2 = function () {
            $scope.popup2.opened = true;
        };

        $scope.popup2 = {
            opened: false
        };


        $scope.getRoomTypesList = function () {
            $http.post($scope.hotelServerURL + '/getRoomTypesList').then(function (response) {
                var data = response.data.object;
                $scope.roomtypesList = data;
            }, function (error) {
                Notification.error({
                    message: 'Something went wrong, please try again',
                    positionX: 'center',
                    delay: 2000
                });
            })
        };

        $scope.getRoomsList = function () {
            $http.post($scope.hotelServerURL + '/getRoomsList').then(function (response) {
                var data = response.data.object;
                console.log("roomsList")
                console.log(data);
                $scope.roomsList = data;
            }, function (error) {
                Notification.error({
                    message: 'Something went wrong, please try again',
                    positionX: 'center',
                    delay: 2000
                });
            })
        };





        $scope.newCheckIn = function () {
            $scope.guests = null;
            $scope.adults = "";
            $scope.checkOut = "";
            $scope.countryName = "";
            $scope.stateName = "";
            $scope.city = "";
            $scope.wifi = "";
            $scope.airConditioning = "";
            $scope.rating = "";
            $scope.reviews = "";
            $scope.couponamt = 0;
            $scope.roomTypeId = null;
            $scope.roomId = null;
            $scope.checkIn = "";
            $scope.stayCost = "";
            $scope.getRoomTypesList();
            $scope.getRoomsList();
            $scope.getGuestsList();
            $('#student-title').text("Booking form");
            $("#add_CheckIn_modal").modal('show');
        };


        $scope.addnewcheckin=function () {
            $scope.getRoomBasedOnRoomType($scope.CheckOutOrderObj.room_type_id);
        $("#checkin_modal").modal('show');
    }

        $scope.saveRoomTypes = function () {
            if ($scope.title == '' || $scope.title == null || angular.isUndefined($scope.title)) {
                Notification.error({message: 'Please Enter Title ', positionX: 'center', delay: 2000})
            }
            else if ($scope.baseprice == '' || $scope.baseprice == null || angular.isUndefined($scope.baseprice)) {
                Notification.error({message: 'Please Enter Base Price ', positionX: 'center', delay: 2000})
            }
            else if ($scope.baseprice<=0) {
                Notification.error({message: 'Base Price Should not be zero', positionX: 'center', delay: 2000})
            }
            else {
                var saveRoomTypesDetails;
                saveRoomTypesDetails = {
                    id: $scope.id,
                    title: $scope.title,
                    description: $scope.description,
                    base_price: $scope.baseprice,

                };
                $http.post($scope.bshimServerURL + "/saveRoomTypes", angular.toJson(saveRoomTypesDetails)).then(function (response) {
                    var data = response.data;
                    if (data == "") {
                        Notification.error({
                            message: 'Already exists',
                            positionX: 'center',
                            delay: 2000
                        });
                    } else {
                        $("#add_room_types").modal('hide');
                        $scope.getPaginatedRoomtypesList();
                        if ($scope.operation == 'Edit') {
                            Notification.success({
                                message: 'RoomTypes is Updated successfully',
                                positionX: 'center',
                                delay: 2000
                            });

                        } else {
                            Notification.success({
                                message: 'RoomTypes is Created  successfully',
                                positionX: 'center',
                                delay: 2000
                            });
                        }

                    }
                }, function (error) {
                    Notification.error({
                        message: 'Something went wrong, please try again',
                        positionX: 'center',
                        delay: 2000
                    });
                });
            };
        }



        $scope.getcheckinList = function (searchText) {
            if(angular.isUndefined(searchText)){
                $scope.searchText="";
            }
            $http.post($scope.bshimServerURL + '/getBookingList').then(function (response) {
                var data = response.data.object;
                $scope.checkinList = data;
            }, function (error) {
                Notification.error({
                    message: 'Something went wrong, please try again',
                    positionX: 'center',
                    delay: 2000
                });
            })
        };
        $scope.getcheckinList();


        //Fetching Rooms based on RoomType
        $scope.getRoomBasedOnRoomType = function (roomTypeId) {
            $http.post($scope.hotelServerURL + "/getRoomList?roomTypeId=" + roomTypeId).then(function (response) {
                var data = response.data.object;
                $scope.roomId=null;
                $scope.roomsList = data;
            })
        }


        $scope.editHotel = function (data) {
            $scope.bookingId = data.bookingId;
            $scope.guests = data.guest;
            $scope.countryName = data.country;
            $scope.stateName = data.state;
            $scope.city = data.city;
            $scope.checkIn = new Date(data.check_in);
            $scope.checkOut =new Date(data.check_out);
            $scope.date=new Date(data.date);
            $scope.adults = data.adults;
            $scope.roomTypeId = data.room_type_id;
            $scope.wifi = data.wifi;
            $scope.meals = data.meals;
            $scope.airConditioning = data.airConditioning;
            $scope.rating=data.rating;
            $scope.reviews=data.reviews;
            $scope.stayCost=data.stayCost;

            $scope.operation = 'Edit';
            $("#submit").text("Update");
            $('#Hotel-title').text("Edit Hotel");
            $("#add_CheckIn_modal").modal('show');

        }, function (error) {
            Notification.error({message: 'Something went wrong, please try again', positionX: 'center', delay: 2000});
        };


        $scope.saveOrder = function () {

            if($scope.guests==''||$scope.guests==null||angular.isUndefined($scope.guests))
            {
                Notification.error({message: 'Please Select Guests', positionX: 'center', delay: 2000})
            }
            else if($scope.adults==''||$scope.adults==null||angular.isUndefined($scope.adults)){
                Notification.error({message: 'Please Select Adults', positionX: 'center', delay: 2000})
            }
            else if($scope.checkIn==''||$scope.checkIn==null||angular.isUndefined($scope.checkIn)){
                Notification.error({message: 'Please Select CheckIn Date', positionX: 'center', delay: 2000})
            }
            else if($scope.checkOut==''||$scope.checkOut==null||angular.isUndefined($scope.checkOut)){
                Notification.error({message: 'Please Select CheckOut Date', positionX: 'center', delay: 2000})
            }
            else if($scope.checkOut<=$scope.checkIn){
                Notification.error({message: 'CheckOut Date Should be greater than CheckIn Date', positionX: 'center', delay: 2000})
            }
            else if($scope.roomTypeId==''||$scope.roomTypeId==null||angular.isUndefined($scope.roomTypeId)){
                Notification.error({message: 'Please Select RoomType', positionX: 'center', delay: 2000})
            }
            else {
                var checkInDetails;
                checkInDetails = {
                    bookingId: $scope.bookingId,
                    guest: $scope.guests,
                    check_in: $scope.checkIn,
                    check_out: $scope.checkOut,
                    ordered_on: new Date(),
                    adults: $scope.adults,
                    kids: $scope.kids,
                    room_no: $scope.roomId,
                    room_type_id: $scope.roomTypeId,
                    country: $scope.countryName,
                    state: $scope.state,
                    city: $scope.city,
                    rating: $scope.rating,
                    reviews: $scope.reviews,
                    wifi: $scope.wifi,
                    meals: $scope.meals,
                    airConditioning: $scope.airConditioning,
                    hotelAvailable: $scope.hotelAvailable,
                    stayCost: $scope.stayCost,

                };

                $http.post($scope.hotelServerURL + '/saveBooking', angular.toJson(checkInDetails)).then(function (response) {
                    var data = response.data.object;
                    // console.log(data);
                    // $scope.studentList = data;
                    $("#add_CheckIn_modal").modal('hide');
                    $scope.getcheckinList();

                    $window.location.href='/home#!/checkIn'


                } , function (error) {
                    Notification.error({
                        message: 'Something went wrong, please try again',
                        positionX: 'center',
                        delay: 2000
                    });
                })
            }
        };


        $scope.deleteHotel=function(data) {
            bootbox.confirm({
                title: "Alert",
                message: "Do you want to Continue ?",
                buttons: {
                    confirm: {
                        label: 'OK'
                    },
                    cancel: {
                        label: 'Cancel'
                    }
                },
                callback: function (result) {
                    if (result == true) {
                        $http.post($scope.bshimServerURL + '/getDeleteHotel?id=' + data.bookingId).then(function (response, status, headers, config) {
                            var data = response.data;
                            Notification.success({
                                message: 'Booking deleted  successfully',
                                positionX: 'center',
                                delay: 2000
                            });
                            $scope.getcheckinList();
                        }, function (error) {
                            Notification.error({
                                message: 'Something went wrong, please try again',
                                positionX: 'center',
                                delay: 2000
                            });
                            $scope.isDisabled = false;
                        });
                    }
                }
            });
        };


        $scope.getGuestList = function () {
            $(".loader").css("display", "block");
            $http.post($scope.hotelServerURL + "/getGuestList").then(function (response) {
                var data = response.data.object;
                console.log(data);
                $scope.guestList = data;

            }, function (error) {
                Notification.error({
                    message: 'Something went wrong, please try again',
                    positionX: 'center',
                    delay: 2000
                });
            })

        };

    });





