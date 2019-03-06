<div ng-controller="${tableName}FormCtrl">
    <div class="page-heading">
        <div class="page-heading">
            <h3><font><font>
            {{title}}${tableName}
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
                <div class="panel">
                    <div class="panel-body">
                        <div class="row" style="margin-top: 10px;">
                            <div class="col-md-12">
                                <form class="form-horizontal adminex-form" name="form" role="form" novalidate>
                                <#list fields as field>
                                    <#if field.colName != "updateTime" && !field.autoIncrement>
                                        <#if (field_index % 2) gt 0>
                                        <div class="form-group">
                                        </#if>
                                        <div class="col-lg-6 col-md-6">
                                            <label class="control-label col-lg-3 col-sm-4">${field.colName}</label>

                                            <div class="col-lg-6 col-sm-8">
                                                <input type="text" class="form-control"
                                                       ng-model="${tableName}.${field.colName}" ng-required="true"/>
                                            </div>
                                        </div>
                                        <#if (field_index % 2) == 0 && field_index != 0 || !field_has_next>
                                        </div>
                                        </#if>
                                    </#if>
                                </#list>
                                    <div class="col-lg-12 text-center">
                                        <button class="btn btn-primary" ng-click="save();" ng-disabled="form.$invalid">保存</button>
                                        <button class="btn btn-default" ui-sref="index.${tableName}Mng">关闭</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>