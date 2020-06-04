app.controller('guestsController',
    function($scope, $http, $location, $filter, Notification, ngTableParams,  $timeout, $window, $rootScope) {
        console.log("aaaaaaaaaaaaa");
        $scope.bshimServerURL = "/hotel";
        $scope.word = /^[a-z]+[a-z0-9._]+@[a-z]+\.[a-z.]{2,5}$/;
        $scope.customerId = 1;
        $scope.userRights = [];
        $scope.operation = 'Create';
        $scope.customer = 1;
        $scope.today = new Date();

        $scope.reloadPage=function(){
            $window.location.reload();
        };

        $scope.dateOptions = {
            maxDate : new Date()
        };

        $scope.format = 'dd/MM/yyyy';
        $scope.openDate = function () {
            $scope.popup8.opened = true;
        };

        $scope.popup8 = {
            opened: false
        };

        $scope.getPaginatedGuestsList = function (page) {
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
            $http.post($scope.bshimServerURL + '/getPaginatedGuestsList?searchText='+$scope.searchText,angular.toJson(paginationDetails)).then(function (response) {
                var data = response.data;
                console.log(data);
                $scope.cityList = data.list;
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

        $scope.getPaginatedGuestsList();


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
            $scope.countryId=0;
            $scope.CountryNameText="";
            $scope.StatusText = "Active";
            $("#submit").text("Save");
            $('#country-title').text("Add Country");
            $("#add_new_country_modal").modal('show');
        };


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

        $scope.EditGuests = function (data) {
            $scope.id=data.id;
            $scope.countryName=data.country;
            $scope.getStateListBasedonContry(data.countryName);
            $scope.stateName=data.state;
            $scope.stateCity(data.stateName);
            $scope.firstname = data.firstname;
            $scope.lastname = data.lastname;
            $scope.gender = data.gender;
            $scope.email = data.email;
            $scope.password = data.password;
            $scope.address = data.address;
            $scope.mobile = data.mobile;
            // $scope.state = data.stateName;
            $scope.dob = new Date(data.dob);
            // $scope.countryName=data.country;
            $scope.city=data.city;
            $scope.statusText=data.status;
            $scope.operation = 'Edit';
            $("#submit").text("Update");
            $('#guests-title').text("Edit Guests");
            $("#add_guests").modal('show');

        }, function (error) {
            Notification.error({message: 'Something went wrong, please try again', positionX: 'center', delay: 2000});
        };

        $scope.getGuestsList = function (searchText) {
            if(angular.isUndefined(searchText)){
                $scope.searchText="";
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
        $scope.getGuestsList();

        $scope.DeleteGuests=function(data) {
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
                        $http.post($scope.bshimServerURL + '/getDeleteGuests?id=' + data.id).then(function (response, status, headers, config) {
                            var data = response.data;
                            Notification.success({
                                message: 'Guests deleted  successfully',
                                positionX: 'center',
                                delay: 2000
                            });
                            $scope.getGuestsList();
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

        $scope.addguests = function () {
            $scope.id="0";
            $scope.firstname="";
            $scope.getStateListBasedonContry($scope.countryName);
            $scope.stateCity($scope.stateName);
            $scope.lastname="";
            $scope.gender="";
            $scope.email="";
            $scope.password="";
            $scope.address="";
            $scope.mobile="";
            $scope.dob="";
            $scope.countryName="";
            $scope.stateName="";
            $scope.city="";
            $scope.statusText="Active";
            $scope.vip="";

            $scope.getGuestsList();
            $scope.getCountryList();
            $("#submit").text("Save");
            $('#guests-title').text("Add Guests");
            $("#add_guests").modal('show');

        };



        $scope.saveguest = function () {
            if ($scope.firstname == ''||$scope.firstname == null||angular.isUndefined($scope.firstname) ) {
                Notification.error({message: 'FirstName can not be empty', positionX: 'center', delay: 2000})
            }
            else if($scope.lastname == ''||angular.isUndefined($scope.firstname)||$scope.lastname==null) {
                Notification.error({message: 'Lastname can not be empty', positionX: 'center', delay: 2000})
            }
            else if($scope.mobile == ''||angular.isUndefined($scope.mobile)||$scope.mobile==null){
                Notification.error({message: 'mobile can not be empty', positionX: 'center', delay: 2000})
            }

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
                    state: $scope.stateName,
                    city: $scope.city

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
                                message: 'Guests is Created  successfully',
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
    });
