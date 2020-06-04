
app.controller('cityController',
    function ($scope, $timeout, $http, $location, $filter, $window, Notification) {
        $scope.bshimServerURL = "/hotel";
        $scope.CountryNameText = "";
        $scope.StatusText = "";
        $scope.operation = 'Create';
        $scope.firstPage = true;
        $scope.lastPage = false;
        $scope.pageNo = 0;
        $scope.prevPage = false;
        $scope.isPrev = false;
        $scope.isNext = false;

        $scope.inactiveStatus = "Active";
        $scope.removeCountryDetails = function () {
            $scope.countryId="";
            $scope.CountryNameText = "";
            $scope.StatusText = "";
            $scope.listStatus = "";
            $scope.operation = "";
        };
        $scope.companyLogoPath = "";

        $scope.reloadPage = function () {
            $window.location.reload();
        };

        $scope.clicked = false;
        $scope.ButtonStatus = "InActive";
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

        $scope.getPaginatedCityList = function (type,page,searchText) {
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
                    $scope.isPrev = false;
                    $scope.isNext = false;
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
            $http.post($scope.bshimServerURL + '/getPaginatedCityList?searchText='+$scope.searchText,angular.toJson(paginationDetails)).then(function (response) {
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

        $scope.getPaginatedCityList();
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

        $scope.backconfiguration=function(){
            $window.location.href='/home#!/configuration'
        };

        $scope.getCityList = function (type,searchText) {
            if (angular.isUndefined(type,searchText)) {
                type = "";
                $scope.searchText="";
            }
            $http.post($scope.bshimServerURL + '/getCityList?&type=' + type).then(function (response) {
                var data = response.data;
                console.log(data);
                var i = 0;
                $scope.cityList = data;
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
        $scope.editCity = function (data) {
            $http.post($scope.bshimServerURL + '/editCity?name=' + data.name+'&countryName='+data.country+'&state='+data.state).then(function (response) {
                var data = response.data;
                $scope.id=data.id;
                $scope.name=data.name;
                $scope.countryName=data.country;
                $scope.state=data.state;
                $scope.getStateListBasedonContry($scope.countryName);
                $scope.getCountryList();
                $scope.operation = 'Edit';
                $("#submit").text("Update");
                $('#city-title').text("Edit City");
                $("#add_new_city_modal").modal('show');
            }, function (error) {
                Notification.error({
                    message: 'Something went wrong, please try again',
                    positionX: 'center',
                    delay: 2000
                });
            });
        };
        $scope.addNewcityPopulate = function () {
              $scope.id=0
              $scope.name="";
              $scope.countryName=null;
              $scope.state=null;
            $scope.getCountryList();
            $("#submit").text("Save");
            $('#city-title').text("Add City");
            $("#add_new_city_modal").modal('show');
        };

        $scope.saveCity = function () {
            if ($scope.countryName == ''||$scope.countryName == null||angular.isUndefined($scope.countryName)) {
                Notification.error({message: 'Please Select Country ', positionX: 'center', delay: 2000});
            }
            else if ($scope.state == ''||$scope.state == null||angular.isUndefined($scope.state)) {
                Notification.error({message: 'Please Select State', positionX: 'center', delay: 2000});
            }
            else if ($scope.name == ''||$scope.name == null||angular.isUndefined($scope.name)) {
                Notification.error({message: 'Please Enter City Name ', positionX: 'center', delay: 2000});
            }
            else {
                var saveCityDetails;
            saveCityDetails = {
                  id:$scope.id,
                country:$scope.countryName,
                name:$scope.name,
                state:$scope.state
                };
                $http.post($scope.bshimServerURL+ '/saveCity', angular.toJson(saveCityDetails, "Create")).then(function (response, status, headers, config) {
                    var data = response.data;
                    if (data == "") {
                        Notification.error({message: 'Already exists', positionX: 'center', delay: 2000});
                    }
                    else {
                        $scope.getPaginatedCityList();
                        $("#add_new_city_modal").modal('hide');
                        if (!angular.isUndefined(data) && data !== null) {
                            $scope.countrySearchText = "";
                        }
                        Notification.success({
                            message: 'City Created  successfully',
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

            };
        };
        $scope.deleteCity = function (data) {
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
                        $http.post($scope.bshimServerURL + '/deleteCity?name=' + data.name+'&country='+data.country+'&state='+data.state).then(function (response) {
                            var data = response.data;
                            if (data == "") {
                                $scope.getPaginatedCityList();
                                Notification.success({
                                    message: 'City deleted successfully',
                                    positionX: 'center',
                                    delay: 2000
                                });
                            }
                            else {
                                $("#add_new_city_modal").modal('hide');
                                if (!angular.isUndefined(data) && data !== null) {
                                    $scope.countrySearchText = "";
                                }
                                if ($scope.StatusText == "InActive") {
                                    $scope.getCityList("", "InActive");
                                    Notification.success({
                                        message: 'Status has been changed to Active',
                                        positionX: 'center',
                                        delay: 2000
                                    });
                                }
                                else {
                                    $scope.getCountryList("", "");
                                    Notification.success({
                                        message: 'Status has been changed to InActive',
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
                }
            });
        };

    });