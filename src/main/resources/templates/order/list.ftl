<html>
<#include "../common/header.ftl">

<body>

<div id="wrapper" class="toggled">
    <!--边栏sidebar,边栏是nav的-->
    <#include "../common/nav.ftl">
    <!--主要内容-->
    <div id="page-content-wrapper">
        <div class="container-fluid">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <table class="table table-boardered table-condensed">
                        <thead>
                        <tr>
                            <th>
                                订单Id
                            </th>
                            <th>
                                姓名
                            </th>
                            <th>
                                手机号
                            </th>
                            <th>
                                地址
                            </th>
                            <th>
                                金额
                            </th>
                            <th>
                                订单状态
                            </th>
                            <th>
                                支付状态
                            </th>
                            <th>
                                创建时间
                            </th>
                            <th colspan="2">
                                操作
                            </th>
                        </tr>
                        </thead>
                        <tbody>
                        <#list  orderDTOPage.content as orderDTO>
                            <tr>
                                <td>
                                    ${orderDTO.orderId}
                                </td>
                                <td>
                                    ${orderDTO.buyerName}
                                </td>
                                <td>
                                    ${orderDTO.buyerPhone}
                                </td>
                                <td>
                                    ${orderDTO.buyerAddress}
                                </td>
                                <td>
                                    ${orderDTO.orderAmount}
                                </td>
                                <td>
                                    ${orderDTO.getOrderStatusEnum()}
                                </td>
                                <td>
                                    ${orderDTO.getPayStatusEnum()}
                                </td>
                                <td>
                                    ${orderDTO.createTime}
                                </td>
                                <td>
                                    <a href="/sell/seller/order/detail?orderId=${orderDTO.orderId}">详情</a>

                                </td>
                                <td>
                                    <#if orderDTO.getOrderStatusEnum().getMsg()=="新订单">
                                        <a href="/sell/seller/order/cancel?orderId=${orderDTO.orderId}">取消</a>
                                    </#if>

                                </td>
                            </tr>
                        </#list>
                        </tbody>
                    </table>
                </div>

                <div class="col-md-12 column">
                    <ul class="pagination pull-right">
                        <#if currentpage lte 1>
                            <li class="disabled"><a href="#">上一页</a></li>
                        <#else >
                            <li><a href="/sell/seller/order/list?page=${currentpage-1}&size=5">上一页</a></li>
                        </#if>

                        <#list 1..orderDTOPage.getTotalPages() as index><!--0 到3-->
                            <#if currentpage==index>
                                <li class="disabled"><a href="#">${index}</a></li>
                            <#else>
                                <li><a href="/sell/seller/order/list?page=${index}&size=5">${index}</a></li>
                            </#if>

                        </#list>

                        <#if currentpage gte orderDTOPage.getTotalPages()>
                            <li class="disabled"><a href="#">下一页</a></li>
                        <#else >
                            <li><a href="/sell/seller/order/list?page=${currentpage+1}&size=5">下一页</a></li>
                        </#if>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.bootcdn.net/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="https://cdn.bootcdn.net/ajax/libs/twitter-bootstrap/3.3.5/js/bootstrap.min.js"></script>

<script>
    var websocket=null;
    if('WebSocket' in window)
    {
        websocket=new WebSocket('ws://zbs999.natapp1.cc/sell/webSocket')
    }
    else
    {
        alert('该浏览器不支持websocket!')
    }

    websocket.onopen=function (event)
    {
        console.log('建立连接');
    }

    websocket.onclose=function (event)
    {
        console.log('断开连接');
    }

    websocket.onmessage=function (event)
    {
        console.log('收到消息'+event.data());
        $('myModal').modal('show');

        //弹窗提醒，播放音乐
    }
    websocket.onerror=function ()
    {
        console.log('websocket发生错误')
    }
    websocket.onbeforeunload=function ()
    {
        websocket.close();
    }

</script>
                </body>
                </html>

