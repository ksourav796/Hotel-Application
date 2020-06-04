app.controller('roomtypesController',
    function($scope, $http, $location, $filter, Notification, ngTableParams,  $timeout, $window, $rootScope) {
        console.log("aaaaaaaaaaaaa");
        $scope.bshimServerURL = "/hotel";
        $scope.word = /^[a-z]+[a-z0-9._]+@[a-z]+\.[a-z.]{2,5}$/;
        $scope.customerId = 1;
        $scope.userRights = [];
        $scope.operation = 'Create';
        $scope.customer = 1;
        $scope.firstPage = true;
        $scope.lastPage = false;
        $scope.pageNo = 0;
        $scope.prevPage = false;
        $scope.isPrev = false;
        $scope.isNext = false;

        $scope.today = new Date();

        $scope.reloadPage = function () {
            $window.location.reload();
        };
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

        $scope.getPaginatedRoomtypesList();

        $scope.getRoomTypesList = function (searchText) {
            if(angular.isUndefined(searchText)){
                $scope.searchText="";
            }
            $http.post($scope.bshimServerURL + '/getRoomTypesList').then(function (response) {
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

        $scope.addroomtypes = function () {
            $scope.id=0;
            $scope.title="";
            $scope.description="";
            $scope.baseprice="";
            $scope.extrabedprice="";
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
        $scope.backconfiguration=function(){
            $window.location.href='/home#!/configuration'
        };
        $scope.EditRoomTypes = function (data) {
            $scope.id=data.id;
            $scope.title = data.title;
            $scope.description = data.description;
            $scope.baseprice = data.base_price;
            $scope.operation = 'Edit';
            $("#submit").text("Update");
            $('#roomtypes-title').text("Edit RoomTypes");
            $("#add_room_types").modal('show');

        }, function (error) {
            Notification.error({message: 'Something went wrong, please try again', positionX: 'center', delay: 2000});
        };

        $scope.DeleteRoomTypes=function(data){
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
                        $http.post($scope.bshimServerURL + '/getDeleteRoomTypes?id=' + data.id).then(function (response) {
                            var data = response.data;
                            Notification.success({
                                message: 'RoomTypes deleted  successfully',
                                positionX: 'center',
                                delay: 2000
                            });
                            $scope.getPaginatedRoomtypesList();
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


    });
