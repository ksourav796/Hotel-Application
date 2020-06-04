app.controller('configuratorCtrl',
    function($scope, $http, $location, $filter, Notification, ngTableParams,  $timeout, $window, $rootScope) {
        $scope.bshimServerURL = "/hotel";
        $scope.word = /^[a-z]+[a-z0-9._]+@[a-z]+\.[a-z.]{2,5}$/;
        $scope.customerId = 1;
        $scope.userRights = [];
        $scope.operation = 'Create';
        $scope.customer = 1;
        $scope.today = new Date();
        $scope.operation='create';



        $scope.reloadPage = function () {
            $window.location.reload();
        };
        $scope.ConfiguratorList=[];
        $scope.getConfiguratorList = function (searchText) {
            if(angular.isUndefined(searchText)){
                $scope.searchText="";
            }
            $http.post($scope.bshimServerURL + '/getConfiguratorList?searchText=').then(function (response) {
                var data = response.data.object;
                $scope.ConfiguratorList = data;
                $scope.length=$scope.ConfiguratorList[0];
            }, function (error) {
                Notification.error({
                    message: 'Something went wrong, please try again',
                    positionX: 'center',
                    delay: 2000
                });
            })
        };

        $scope.getConfiguratorList();





        $scope.EditConfigurator = function (data) {
            $scope.id=data.id;
            $scope.configname = data.configuratorName;
            $scope.description = data.description;
            $scope.hoursbased = data.hoursBased;
            $scope.operation = 'Edit';
            $("#submit").text("Update");
            $('#configurator-title').text("Edit configurator");
            $("#add_new_configurator").modal('show');

        }, function (error) {
            Notification.error({message: 'Something went wrong, please try again', positionX: 'center', delay: 2000});
        };


        $scope.DeleteConfigurator=function(data){
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
                        $http.post($scope.bshimServerURL + '/getDeleteConfigurator?id=' + data.id).then(function (response) {
                            var data = response.data;
                            Notification.success({
                                message: 'Configurator deleted  successfully',
                                positionX: 'center',
                                delay: 2000
                            });
                            $scope.getConfiguratorList();
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


        $scope.addconfigurator = function () {
            $scope.id=0;
            $scope.configname="";
            $scope.description="";
            $scope.hoursbased="";
            $("#submit").text("Save");
            $('#configurator-title').text("Add Configurator");
            $("#add_new_configurator").modal('show');

        };

        $scope.backconfigurator=function(){
            $window.location.href='/home#!/configuration'
        };


        $scope.saveConfigurator = function () {
            if ($scope.configname == '' || $scope.configname == "" || angular.isUndefined($scope.configname)) {
                Notification.error({message: 'Configuration Name can not be empty', positionX: 'center', delay: 2000});
            }
            else if ($scope.hoursbased == '' || $scope.hoursbased == "" || angular.isUndefined($scope.hoursbased)) {
                Notification.error({message: 'Please Choose Hours', positionX: 'center', delay: 2000});
            }
            else {
                var saveConfiguratorDetails;
            saveConfiguratorDetails = {
                id: $scope.id,
                configuratorName: $scope.configname,
                description: $scope.description,
                hoursBased: $scope.hoursbased

            };
            $http.post($scope.bshimServerURL + "/saveConfigurator", angular.toJson(saveConfiguratorDetails)).then(function (response) {
                var data = response.data;
                if (data == "" || data == 'undefined' || data == null) {
                    Notification.error({message: ' Already exists', positionX: 'center', delay: 2000});
                }
                else {
                    $("#add_new_configurator").modal('hide');
                    $scope.getConfiguratorList();
                    if ($scope.operation == 'Edit') {
                        Notification.success({
                            message: 'Configurator is Updated successfully',
                            positionX: 'center',
                            delay: 2000
                        });

                    } else {
                        Notification.success({
                            message: 'Configurator is Created  successfully',
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
