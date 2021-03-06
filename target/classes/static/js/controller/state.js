app.controller('stateController',
    function ($scope, $http, $location, $filter, $timeout,$window, Notification) {// body...
        $scope.bshimServerURL = "/hotel";
        $scope.firstPage = true;
        $scope.lastPage = false;
        $scope.pageNo = 0;
        $scope.prevPage = false;
        $scope.isPrev = false;
        $scope.isNext = false;
        $scope.inactiveStatus = "Active";

        $scope.reloadPage = function () {
            $window.location.reload();
        };

        /***Method which sets all values to null**/
        $scope.removeStateDetails = function () {
            $scope.stateNameText = "";
            $scope.countryId = "";
            $scope.StatusText = "";
        };
        $scope.clicked = false;
        /****Method for getting the State List*******/
        $scope.getStateList = function (type, page,searchText) {
            if (angular.isUndefined(type,searchText)) {
                type = "";
                $scope.searchText="";
            }
            switch (page) {
                case 'firstPage':
                    $scope.firstPage = true;
                    $scope.lastPage = false;
                    $scope.pageNo = 0;
                    break;
                case 'lastPage':
                    $scope.lastPage = true;
                    $scope.firstPage = false;
                    $scope.pageNo = 0;
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
            paginationDetails = {
                firstPage: $scope.firstPage,
                lastPage: $scope.lastPage,
                pageNo: $scope.pageNo,
                prevPage: $scope.prevPage,
                prevPage: $scope.isPrev,
                nextPage: $scope.isNext
            }
            if (angular.isUndefined($scope.stateSearchText)) {
                $scope.stateSearchText = "";
            }
            $http.post($scope.bshimServerURL + '/getStateList?type=' + type + '&searchText=' + $scope.stateSearchText, angular.toJson(paginationDetails)).then(function (response) {
                var data = response.data;
                console.log(data);
                $scope.stateList = data.list;
                $scope.first = data.firstPage;
                $scope.last = data.lastPage;
                $scope.prev = data.prevPage;
                $scope.next = data.nextPage;
                $scope.pageNo = data.pageNo;
                /**/
                var i = 0;
                $scope.listStatus = true;
                if (i > 0) {
                    Notification.error({
                        message: 'State is InActive',
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



        $scope.backconfiguration=function(){
            $window.location.href='/home#!/configuration'
        };


        $scope.getCountryList = function () {
            $http.post($scope.bshimServerURL+ '/getCountryList?status='+'Active').then(function (response) {
                var data = response.data;
                $scope.country = angular.copy(data);
            }, function (error) {
                Notification.error({
                    message: 'Something went wrong, please try again',
                    positionX: 'center',
                    delay: 2000
                });
            });
        };
        /***Method for add POPUP*******/
        $scope.addNewState = function () {
            $scope.id=0;
            $scope.stateNameText="";
            $scope.countryId=null;
            $scope.StatusText = "Active";
            $("#submit").text("Save");
            $('#state-title').text("Add State");
            $("#add_new_State_modal").modal('show');
        };

        /******edit Popup with values for state*******/
        $scope.editState = function (name) {
            $http.post($scope.bshimServerURL + '/editState?stateName=' + name).then(function (response) {
                var data = response.data;
                $scope.countryId = data.country;
                $scope.stateObj = data;
                $scope.id = data.id;
                $scope.stateNameText = data.stateName;
                $scope.StatusText = data.status;
                $("#submit").text("Update");
                $('#state-title').text("Edit State");
                $("#add_new_State_modal").modal('show');
            }, function (error) {
                Notification.error({
                    message: 'Something went wrong, please try again',
                    positionX: 'center',
                    delay: 2000
                });
            });
        }, function (error) {
            Notification.error({message: 'Something went wrong, please try again', positionX: 'center', delay: 2000});
        };

        $scope.saveState = function () {
            if ($scope.countryId == ''||$scope.countryId==null||angular.isUndefined($scope.countryId)) {
                Notification.error({message: 'Please Select Country', positionX: 'center', delay: 2000});
            }
           else if ($scope.stateNameText == ''||$scope.stateNameText==null||angular.isUndefined($scope.stateNameText)) {
                Notification.error({message: 'Please Enter State Name ', positionX: 'center', delay: 2000});
            }

            else {
                var saveStateDetails;
                saveStateDetails = {
                    id: $scope.id,
                    status: $scope.StatusText,
                    stateName: $scope.stateNameText,
                    country: $scope.countryId
                };
                $http.post($scope.bshimServerURL + '/saveState', angular.toJson(saveStateDetails, "Create")).then(function (response, status, headers, config) {
                    var data = response.data;
                    if (data == "") {
                        Notification.error({message: 'Already exists', positionX: 'center', delay: 2000});
                    }
                    else {
                        $scope.removeStateDetails();
                        $scope.getStateList();
                        $("#add_new_State_modal").modal('hide');
                        if (!angular.isUndefined(data) && data !== null) {
                            $scope.stateNameText = "";
                        }
                        Notification.success({
                            message: 'State  Created  successfully',
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
        $scope.deleteState = function (data) {
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
                        // $scope.id = data.id;
                        $http.post($scope.bshimServerURL + '/deleteState?stateName=' + data).then(function (response, status, headers, config) {
                            var data = response.data;

                            if ($scope.StatusText == "InActive") {
                                $scope.getStateList("", "InActive");
                                Notification.success({
                                    message: 'Status has been changed to Active',
                                    positionX: 'center',
                                    delay: 2000
                                });
                            }
                            else {
                                $scope.getStateList("", "");
                                Notification.success({
                                    message: 'Status has been changed to InActive',
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
                }
            });
        };
        $scope.getStateList();
        $scope.getCountryList();
    });
