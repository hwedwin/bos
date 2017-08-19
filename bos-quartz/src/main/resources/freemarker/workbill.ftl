<h2><font color="green">来自:${fromAddressMail}的邮件!</font></h2>
<p>工作单汇总信息说明</p>
<table border="1px" width="100%">
   <tr align='center'>
      <th>工单编号</th>
      <th>工单状态</th>
      <th>追单次数</th>
      <th>取派员</th>
   </tr>
<#list workbills as workbill>
    <tr align='center'>
      <td>${workbill.id}</td>
      <td>${workbill.type}</td>
      <td>${workbill.attachbilltimes}</td>
      <td>${workbill.staff.name}</td>
   </tr>
</#list>
</table>
<br>
<#--<img src="data:image/jpeg;base64,${image}"/>-->
