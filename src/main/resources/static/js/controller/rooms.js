app.controller('roomsController',
    function($scope, $http, $location, $filter, Notification, ngTableParams,  $timeout, $window, $rootScope) {
        console.log("aaaaaaaaaaaaa");
        $scope.hotelServerURL = "/hotel";
        $scope.word = /^[a-z]+[a-z0-9._]+@[a-z]+\.[a-z.]{2,5}$/;
        $scope.customerId = 1;
        $scope.userRights = [];
        $scope.operation = 'Create';
        $scope.customer = 1;
        $scope.today = new Date();
        $scope.firstPage = true;
        $scope.lastPage = false;
        $scope.pageNo = 0;
        $scope.prevPage = false;
        $scope.isPrev = false;
        $scope.isNext = false;


        $scope.reloadPage = function () {
            $window.location.reload();
        };

        $scope.getRoomsList = function (searchText) {
            if(angular.isUndefined(searchText)){
                $scope.searchText="";
            }
            $http.post($scope.hotelServerURL + '/getRoomsList').then(function (response) {
                var data = response.data.object;
                $scope.roomsList = data;
            }, function (error) {
                Notification.error({
                    message: 'Something went wrong, please try again',
                    positionX: 'center',
                    delay: 2000
                });
            })
        };
        $scope.getPaginatedRoomList = function (page) {
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
                    $scope.pageNo = 0;
                    $scope.isNext = false;
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
            $http.post($scope.hotelServerURL + '/getPaginatedRoomList?searchText='+$scope.searchText,angular.toJson(paginationDetails)).then(function (response) {
                var data = response.data;
                console.log(data);
                $scope.roomsList = data.list;
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

        $scope.getPaginatedRoomList();

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


        $scope.getFloorsList = function () {

            $http.post($scope.hotelServerURL + '/getFloorsList1?searchText=').then(function (response) {
                var data = response.data.object;
                $scope.floorsList = data;
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

        $scope.importPopup = function(){
            $("#import_Rooms").modal('show');
        }

        $scope.saveRoomsImport = function(){
            $scope.isDisabled= true;
            var formElement = document.getElementById("roomsDetails");
            var roomsDetails = new FormData(formElement);
            $http.post($scope.hotelServerURL + '/RoomsImportSave',roomsDetails,
                { headers: {'Content-Type': undefined},
                    transformRequest: angular.identity,
                }).then(function (response) {
                    $("#import_Rooms").modal('hide');
                $scope.getRoomTypesList();
                    $scope.getPaginatedRoomList();
                    $scope.isDisabled= false;
                }, function (error) {
                    Notification.error({
                        message: 'Something went wrong, please try again',
                        positionX: 'center',
                        delay: 2000
                    });
                    $scope.isDisabled= false;
                }
            )
        }




        $scope.addrooms = function () {
            $scope.id=0;
            $scope.roomno="";
            $scope.floorsNameText=null;
            $scope.roomsNameText=null;
            $scope.getFloorsList();
            $scope.getRoomTypesList();
            $scope.getPaginatedRoomList();
            $("#submit").text("Save");
            $('#rooms-title').text("Add Rooms");
            $("#add_new_rooms").modal('show');

        };
        $scope.saveRooms = function () {
            if($scope.roomno == ''||$scope.roomno==null||angular.isUndefined($scope.roomno)){
                Notification.error({message:'Please Enter RoomNo',positionX:'center',delay:2000})
            }
            else if ($scope.floorsNameText == ''||$scope.floorsNameText==null||angular.isUndefined($scope.floorsNameText)) {
                Notification.error({message: 'Please Select Floor ', positionX: 'center', delay: 2000});
            }
            else if($scope.roomsNameText ==''||$scope.roomsNameText==null||angular.isUndefined($scope.roomsNameText)) {
                Notification.error({message:'Please Select RoomType ',positionX:'center',delay:2000});
            }
            else {
                var saveRoomsDetails;
                saveRoomsDetails = {
                    id: $scope.id,
                    roomno: $scope.roomno,
                    floor_Id: $scope.floorsNameText,
                    room_typeId: $scope.roomsNameText

                };
                $http.post($scope.hotelServerURL + "/saveRooms", angular.toJson(saveRoomsDetails)).then(function (response) {
                    var data = response.data;
                    if (data == "" || data == 'undefined') {
                        Notification.error({message: ' Already exists', positionX: 'center', delay: 2000});
                    }
                    else {
                        $("#add_new_rooms").modal('hide');
                        $scope.getPaginatedRoomList();
                        $scope.getPaginatedRoomtypesList();
                        $scope.getPaginatedfloorsList();
                        if ($scope.operation == 'Edit') {
                            Notification.success({
                                message: 'Rooms is Updated successfully',
                                positionX: 'center',
                                delay: 2000
                            });

                        } else {
                            Notification.success({
                                message: 'Rooms is Created  successfully',
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
        };

        $scope.EditRooms = function (data) {
            $scope.id=data.id;
            $scope.roomno = data.roomno;
            $scope.floorsNameText = data.floor_Id;
            $scope.roomsNameText = data.room_typeId;
            $scope.getFloorsList();
            $scope.getRoomTypesList();
            $scope.operation = 'Edit';
            $("#submit").text("Update");
            $('#rooms-title').text("Edit Rooms");
            $("#add_new_rooms").modal('show');

        }, function (error) {
            Notification.error({message: 'Something went wrong, please try again', positionX: 'center', delay: 2000});
        };


        $scope.DeleteRooms=function(data){
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
                        $http.post($scope.hotelServerURL + '/DeleteRooms?id=' + data.id).then(function (response) {
                            var data = response.data;
                            Notification.success({
                                message: 'Rooms deleted  successfully',
                                positionX: 'center',
                                delay: 2000
                            });
                            $scope.getPaginatedRoomList();
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
