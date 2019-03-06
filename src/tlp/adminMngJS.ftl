adminApp.controller('${tableName}MngCtrl', function ($scope, $http, $state) {
    $scope.search = {};
    $scope.search.pageIndex = 0;
    $scope.search.pageSize = 10;

    $scope.getPagedDataAsync = function () {
        $http.post("/${tableName}/list", $scope.search).success(function (data) {

            $scope.list = data.list;
            $scope.count = data.count;
        });

    };

    $scope.pagedCallback = function () {
        $scope.search.pageIndex = $scope.pageIndex - 1;
        $scope.getPagedDataAsync();
    };

    $scope.getPagedDataAsync();

    $scope.delete = function (id) {

        if (window.confirm("是否删除?")) {
            $http.delete('/${tableName}/' + id).success(function (data) {

                for (var i = 0; i < $scope.list.length; i++) {
                    if ($scope.list[i].id == id) {
                        $scope.list.splice(i, 1);
                        break;
                    }
                }

            });
        }

    }
});

adminApp.controller('${tableName}FormCtrl', function ($scope, $http, $state, $stateParams) {

    $scope.title = 新增;

    if ($stateParams.id > 0) {
        $http.get("/${tableName}/" + $stateParams.id).success(function (data) {
            $scope.${tableName} = data;
            $scope.title = 编辑;
        });

    }


    $scope.save = function () {
        if ($stateParams.id > 0) {
            $http.put('/${tableName}/', $scope.${tableName}).success(function (data) {

                $state.go('index.${tableName}Mng');
            });
        }
        else {
            $http.post('/${tableName}/', $scope.${tableName}).success(function (data) {
                $state.go('index.${tableName}Mng');
            });
        }
    };
});
