app.controller('checkInController',
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
            minDate : new Date()
        };

        $scope.dateOptions1 = {
            maxDate : new Date()
        };


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


