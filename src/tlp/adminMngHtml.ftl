<div ng-controller="${tableName}MngCtrl">
    <div class="page-heading">
        <div class="page-heading">
            <h3><font><font>
            ${tableName}列表
            </font></font></h3>
            <ul class="breadcrumb">
                <li>
                    <a href="javascript:;"><font><font>${tableName}</font></font></a>
                </li>
                <li class="active"><font><font> ${tableName}管理 </font></font></li>
            </ul>
        </div>
    </div>
    <div class="wrapper">
        <div class="row">
            <div class="col-md-12">
                <div class="panel" style="padding-top: 20px;">
                    <div class="panel-body">
                        <form class="form-horizontal" role="form">
                            <div class="form-group">
                                <div class="col-lg-4 col-sm-6">
                                    <button type="button" class="btn btn-primary" ui-sref="index.${tableName}Form"> 新增 </button>
                                </div>
                                <div class="col-lg-4 col-sm-6 col-lg-offset-4">
                                    <label class="col-sm-3 control-label col-lg-4">id：</label>
                                    <div class="col-lg-8">
                                        <div class="input-group m-bot15">
                                            <input type="text" class="form-control" ng-model="search.id">
                                                  <span class="input-group-btn">
                                                    <button class="btn btn-default" type="button" ng-click="getPagedDataAsync()"><i class="fa fa-search"></i></button>
                                                  </span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </form>
                        <div class="row" style="margin-top: 10px;">
                            <div class="col-md-12">
                                <table class="table table-bordered table-hover text-center">
                                    <thead>
                                    <tr>
                                    <#list fields as field>
                                        <#if field.colName != "updateTime" && !field.autoIncrement>
                                            <th class="col-md-2 col-sm-1"
                                                style="text-align: center; vertical-align: middle;">${field.colName}</th>
                                        </#if>
                                    </#list>
                                        <th class="col-md-2 col-sm-1" style="text-align: center; vertical-align: middle;">
                                            operation
                                        </th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr ng-repeat="item in list">
                                    <#list fields as field>
                                        <#if field.colName != "updateTime" && !field.autoIncrement>
                                            <td ng-bind="item.${field.colName}"
                                                style="text-align: center; vertical-align: middle;"></td>
                                        </#if>
                                    </#list>
                                        <td>
                                            <a class="btn btn-info btn-sm" ui-sref="index.${tableName}Form({id:item.id})">编辑</a>
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-12 text-right">
                                <uib-pagination boundary-links="true"
                                                max-size="5"
                                                items-per-page="search.pageSize"
                                                total-items="count"
                                                ng-model="pageIndex"
                                                ng-change="pagedCallback()"></uib-pagination>
                            </div>
                        </div>

                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

