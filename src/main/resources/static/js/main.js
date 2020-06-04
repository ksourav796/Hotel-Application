// var taskManagerModule = angular.module('taskManagerApp', ['ngRoute','ngAnimate']);
//

var app = angular.module('myApp',['ngRoute', 'ngAnimate',
    'ngSanitize','ui-notification','ngTable', 'chart.js',
    'ngCookies', 'angular.filter','ui.bootstrap']);


app.config(['$routeProvider', function($routeProvider) {
    $routeProvider
        .when("/login", {
            templateUrl: "partials/login.html",
            controller: "loginController"
        })

        .when("/dashboard", {
            templateUrl: "partials/dashboard.html",
            controller: "dashboardController"
        })
        .when("/checkIn", {
            templateUrl: "partials/checkIn.html",
            controller: "checkInController"
        })
        .when("/bookings", {
            templateUrl: "partials/bookings.html",
            controller: "bookingsController"
        })
        .when("/city", {
            templateUrl: "partials/city.html",
            controller: "cityController"
        })
        .when("/configuration", {
            templateUrl: "partials/configuration.html"
        })


        .when("/state", {
            templateUrl: "partials/state.html",
            controller: "stateController"
        })
        .when("/country", {
            templateUrl: "partials/country.html",
            controller: "countryController"
        })

        .when("/configurator", {
            templateUrl: "partials/configurator.html",
            controller: "configuratorCtrl"
        })

        .when("/users", {
            templateUrl: "partials/users.html",
            controller: "userCtrl"
        })

        .when("/roomtypes", {
            templateUrl: "partials/roomtypes.html",
            controller: "roomtypesController"
        })
        .when("/guests", {
            templateUrl: "partials/guests.html",
            controller: "guestsController"
        })

        .when("/rooms", {
            templateUrl: "partials/rooms.html",
            controller: "roomsController"
        })

        .otherwise({redirectTo:'/login'});
}]);

app.directive("limitTo", [function () {
    return {
        restrict: "A",
        link: function (scope, elem, attrs) {
            var limit = parseInt(attrs.limitTo);
            angular.element(elem).on("keypress", function (e) {
                if (this.value.length === limit)
                    e.preventDefault();
            });
        }
    };
}]);


/* for only  Alpha without space function
 */
app.directive('alphaWithoutSpace', function() {
    return {
        require: '?ngModel',
        link: function(scope, element, attrs, ngModelCtrl) {
            if(!ngModelCtrl) {
                return;
            }

            ngModelCtrl.$parsers.push(function(val) {
                var clean = val.replace( /[^a-zA-Z]/g, '');
                if (val !== clean) {
                    ngModelCtrl.$setViewValue(clean);
                    ngModelCtrl.$render();
                }
                return clean;
            });

            element.bind('keypress', function(event) {
                if(event.keyCode === 32) {
                    event.preventDefault();
                }
            });
        }
    };
});
/* validation
 * for only number with space function
 */
app.directive('numWithSpace', function() {
    return {
        require: '?ngModel',
        link: function(scope, element, attrs, ngModelCtrl) {
            if(!ngModelCtrl) {
                return;
            }

            ngModelCtrl.$parsers.push(function(val) {
                var clean = val.replace( /[^\s0-9]+/g, '');
                if (val !== clean) {
                    ngModelCtrl.$setViewValue(clean);
                    ngModelCtrl.$render();
                }
                return clean;
            });
        }
    };
});

/* validation
 * for date and time function
 */
app.directive('dateTimePicker', function() {
    return {
        restrict: "A",
        require: "ngModel",
        link: function (scope, element, attrs, ngModelCtrl) {
            var parent = $(element).parent();
            var dtp = parent.datetimepicker({
                format: "LL",
                showTodayButton: true
            });
            dtp.on("dp.change", function (e) {
                ngModelCtrl.$setViewValue(moment(e.date).format("LL"));
                scope.$apply();
            });
        }
    };
});
/* validation
 * for date  function
 */
app.directive('datepicker', function() {
    return {
        require: 'ngModel',
        link: function(scope, el, attr, ngModel) {
            $(el).datepicker({
                onSelect: function(dateText) {
                    scope.$apply(function() {
                        ngModel.$setViewValue(dateText);
                    });
                }
            });
        }
    };
});

/* validation
 * It allows number,plus,hypen with space function
 */
app.directive('mobileNumWithSpace', function() {
    return {
        require: '?ngModel',
        link: function(scope, element, attrs, ngModelCtrl) {
            if(!ngModelCtrl) {
                return;
            }

            ngModelCtrl.$parsers.push(function(val) {
                var clean = val.replace( /[^- ^+^0-9]+/g, '');
                if (val !== clean) {
                    ngModelCtrl.$setViewValue(clean);
                    ngModelCtrl.$render();
                }
                return clean;
            });
        }
    };
});
app.directive('numWithOutSpace', function() {
    return {
        require: '?ngModel',
        link: function(scope, element, attrs, ngModelCtrl) {
            if(!ngModelCtrl) {
                return;
            }

            ngModelCtrl.$parsers.push(function(val) {
                var clean = val.replace( /[^0-9]+/g, '');
                if (val !== clean) {
                    ngModelCtrl.$setViewValue(clean);
                    ngModelCtrl.$render();
                }
                return clean;
            });
        }
    };
});
/* validation
 * It allows number,plus,hypen with space function
 */
app.directive('number', function() {
    return {
        require: '?ngModel',
        link: function(scope, element, attrs, ngModelCtrl) {
            if(!ngModelCtrl) {
                return;
            }

            ngModelCtrl.$parsers.push(function(val) {
                var clean = val.replace( /[^.0-9]+/g, '');
                if (val !== clean) {
                    ngModelCtrl.$setViewValue(clean);
                    ngModelCtrl.$render();
                }
                return clean;
            });
        }
    };
});



/* for only Alpha with space function
 */
app.directive('alphaWithSpace', function() {
    return {
        require: '?ngModel',
        link: function(scope, element, attrs, ngModelCtrl) {
            if(!ngModelCtrl) {
                return;
            }

            ngModelCtrl.$parsers.push(function(val) {
                var clean = val.replace( /[^\sa-zA-Z]/g, '');
                if (val !== clean) {
                    ngModelCtrl.$setViewValue(clean);
                    ngModelCtrl.$render();
                }
                return clean;
            });
        }
    };
});


/* for only Alpha and number with space function
 */
app.directive('alphanumWithSpace', function() {
    return {
        require: '?ngModel',
        link: function(scope, element, attrs, ngModelCtrl) {
            if(!ngModelCtrl) {
                return;
            }

            ngModelCtrl.$parsers.push(function(val) {
                var clean = val.replace( /[^\sa-zA-Z0-9]/g, '');
                if (val !== clean) {
                    ngModelCtrl.$setViewValue(clean);
                    ngModelCtrl.$render();
                }
                return clean;
            });
        }
    };
});


/* for only Alpha and number without space function
 */
app.directive('alphanumWithoutSpace', function() {
    return {
        require: '?ngModel',
        link: function(scope, element, attrs, ngModelCtrl) {
            if(!ngModelCtrl) {
                return;
            }

            ngModelCtrl.$parsers.push(function(val) {
                var clean = val.replace( /[^a-zA-Z^0-9]/g, '');
                if (val !== clean) {
                    ngModelCtrl.$setViewValue(clean);
                    ngModelCtrl.$render();
                }
                return clean;
            });

            element.bind('keypress', function(event) {
                if(event.keyCode === 32) {
                    event.preventDefault();
                }
            });
        }
    };
});

/* for only two digit decimal Function
 */

app.directive('twoDigitsDec', function() {
    return {
        require: '?ngModel',
        link: function(scope, element, attrs, ngModelCtrl) {
            if(!ngModelCtrl) {
                return;
            }

            ngModelCtrl.$parsers.push(function(val) {
                if (angular.isUndefined(val)) {
                    var val = '';
                }

                var clean = val.replace(/[^-0-9\.]/g, '');
                var negativeCheck = clean.split('-');
                var decimalCheck = clean.split('.');
                if(!angular.isUndefined(negativeCheck[1])) {
                    negativeCheck[1] = negativeCheck[1].slice(0, negativeCheck[1].length);
                    clean =negativeCheck[0] + '-' + negativeCheck[1];
                    if(negativeCheck[0].length > 0) {
                        clean =negativeCheck[0];
                    }

                }

                if(!angular.isUndefined(decimalCheck[1])) {
                    decimalCheck[1] = decimalCheck[1].slice(0,2);
                    clean =decimalCheck[0] + '.' + decimalCheck[1];
                }

                if (val !== clean) {
                    ngModelCtrl.$setViewValue(clean);
                    ngModelCtrl.$render();
                }
                return clean;
            });

            element.bind('keypress', function(event) {
                if(event.keyCode === 32) {
                    event.preventDefault();
                }
            });
        }
    };
});

app.directive('noSpace', function() {
    return {
        require: '?ngModel',
        link: function(scope, element, attrs, ngModelCtrl) {
            element.bind('keypress', function(event) {
                if(event.keyCode === 32) {
                    event.preventDefault();
                }
            });
        }
    };
});

// Change text to uppercase and add dash every 5 char
app.directive('capitalizeWithDash', function() {
    return {
        require: 'ngModel',
        link: function(scope, element, attrs, modelCtrl) {
            var capitalize = function(inputValue) {
                var number = 5;
                if (inputValue == undefined) inputValue = '';
                var inputUpper = inputValue.toUpperCase();
                var capitalizedArray = [];
                for(var i=0; i<inputUpper.length; i+= number){
                    if(inputUpper[i] == "-"){
                        capitalizedArray.push(inputUpper.substr(i+1,number))
                        i += 1;
                    }else {
                        capitalizedArray.push(inputUpper.substr(i, number))
                    }
                }
                var capitalized = capitalizedArray.join("-");
                if (capitalized !== inputValue) {
                    modelCtrl.$setViewValue(capitalized);
                    modelCtrl.$render();
                }
                return capitalized;
            }
            modelCtrl.$parsers.push(capitalize);
            capitalize(scope[attrs.ngModel]);
        }
    };
});

app.filter('setDecimal', function ($filter) {
    return function (input, places) {
        if (isNaN(input))
            return input;
        // If we want 1 decimal place, we want to mult/div by 10
        // If we want 2 decimal places, we want to mult/div by 100, etc
        // So use the following to create that factor
        var factor = "1" + Array(+(places > 0 && places + 1)).join("0");
        return Math.round(input * factor) / factor;
    };
});

app.filter('firstLetter', function () {
    return function (input, key, letter) {
        input = input || [];
        var out = [];
        input.forEach(function (item) {
            console.log('item: ', item[key][0].toLowerCase());
            console.log('letter: ', letter);
            if (item[key][0].toLowerCase() == letter.toLowerCase()) {
                out.push(item);
            }
        });
        return out;
    }
});

