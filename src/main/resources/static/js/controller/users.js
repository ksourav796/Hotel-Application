app.controller('userCtrl',
    function ($scope, $rootScope, $http, $location, $filter,$window, Notification) {
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

        $scope.reloadPage=function(){
            $window.location.reload();
        }
        $scope.saveuserDetails = function () {
            if ($scope.userText == ''||$scope.userText==null||angular.isUndefined($scope.userText)) {
                Notification.error({message: 'Please Enter User Name ', positionX: 'center', delay: 2000});
            }
            else if ($scope.PasswordText == ''||$scope.PasswordText==null||angular.isUndefined($scope.PasswordText)) {
                Notification.error({message: 'Please Enter Password ', positionX: 'center', delay: 2000});
            }
            else if ($scope.EmailAddressText == ''||$scope.EmailAddressText==null||angular.isUndefined($scope.EmailAddressText)) {
                Notification.error({message: 'please enter valid EmailId', positionX: 'center', delay: 2000});
            }
            else {
                var saveUserDetails;
                saveUserDetails = {
                    useraccountId:$scope.id,
                    userName: $scope.userText,
                    passwordUser: $scope.PasswordText,
                    full_name: $scope.FullNameText,
                    securityQuestion: $scope.SecurityQuestionText,
                    securityAnswer: $scope.AnswerText,
                    full_name: $scope.FullNameText,
                    phone: $scope.TelephoneNumberText,
                    email: $scope.EmailAddressText,
                    status:$scope.status
                };
                $http.post($scope.bshimServerURL + "/getuserDetails" , angular.toJson(saveUserDetails)).then(function (response) {
                    var data = response.data;
                    if (data == "" || data == 'undefined') {
                        Notification.error({message: ' Already exists', positionX: 'center', delay: 2000});
                    } else {
                        $("#add_user_master").modal('hide');
                        $scope.getUserList();
                        Notification.success({message: 'Users Created  successfully', positionX: 'center', delay: 2000});
                        $scope.removeuserDetails();

                        $scope.getPaginatedUsersList();
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

        $scope.getUserList = function (searchText) {
            if(angular.isUndefined(searchText)){
                $scope.searchText="";
            }
            $(".loader").css("display", "block");
            $http.post($scope.bshimServerURL + '/getUserList').then(function (response) {
                var data = response.data.object;
                console.log(data);
                $scope.userList = data;

            }, function (error) {
                Notification.error({
                    message: 'Something went wrong, please try again',
                    positionX: 'center',
                    delay: 2000
                });
            })

        };

        $scope.getPaginatedUsersList = function (page) {
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
            $http.post($scope.bshimServerURL + '/getPaginatedUsersList?searchText='+$scope.searchText,angular.toJson(paginationDetails)).then(function (response) {
                var data = response.data;
                console.log(data);
                $scope.userList = data.list;
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
        $scope.getPaginatedUsersList();

        $scope.backconfiguration=function(){
            $window.location.href='/home#!/configuration'
        };



        $scope.EditUser = function (data) {
            $scope.id=data.useraccountId;
            $scope.userText = data.userName;
            $scope.PasswordText = data.passwordUser;
            $scope.FullNameText = data.full_name;
            $scope.SecurityQuestionText = data.securityQuestion;
            $scope.AnswerText = data.securityAnswer;
            $scope.TelephoneNumberText = data.phone;
            $scope.EmailAddressText = data.email;
            $scope.status = data.status;
            $scope.operation = 'Edit';
            $("#submit").text("Update");
            $('#user-title').text("Edit User");
            $("#add_user_master").modal('show');

        }, function (error) {
            Notification.error({message: 'Something went wrong, please try again', positionX: 'center', delay: 2000});
        };

        $scope.DeleteUser=function(data) {
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
                        $http.post($scope.bshimServerURL + '/getDeleteUser?useraccountId=' + data.useraccountId).then(function (response, status, headers, config) {
                            var data = response.data;
                            Notification.success({
                                message: 'Users deleted  successfully',
                                positionX: 'center',
                                delay: 2000
                            });
                            $scope.getPaginatedUsersList();
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

        $scope.addUserDetails = function () {
            $scope.id=0;
            $scope.userText ="";
            $scope.PasswordText = "";
            $scope.FullNameText = "";
            $scope.SecurityQuestionText ="";
            $scope.AnswerText = "";
            $scope.TelephoneNumberText = "";
            $scope.EmailAddressText = "";
            $scope.status = "Active";
            $("#submit").text("Save");
            $('#user-title').text("Add User");
            $("#add_user_master").modal('show');
        };




    });
