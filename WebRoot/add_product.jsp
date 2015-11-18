<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
     function checkForm(){
         //校验form 表单 ，想校验那一项
         var price  = document.getElementById("id").value;
         if(price==null||price==""){
         alert("价格必须输入");
         return false;
         }else if(isNaN(price)||parseFloat(price)<=0){
         alert("输出价格必须位数字且大于0");
         return false;
         }
         
     }
</script>
</head>
<body>
<h1>添加商品</h1>
<!--文件上传：form post 提交 ，input file 必须name属性 ，enctype:multipart/form-data -->
<form action="/addproduct" method="post" enctype="multipart/form-data" onsubmit="return checkForm();">
<table>
      <tr>
          <td>商品名称</td>
          <td><input type="text" name="name"></td>
      </tr>
      <tr>
           <td>商品价格</td>
           <td><input type="text" name="price" id="price"/></td>
      </tr>
      <tr>
            <td>商品分类</td>
            <td>
                <select name="category">
                <option value="数码产品">数码产品</option>
                <option value="电脑及IT硬件">电脑及IT硬件</option>
                <option value="家用电器">家用电器</option>
                <option value="日常用品">日常用品</option>
                </select>
                
            </td>
      </tr>
      <tr>
           <td>商品描述</td>
           <td>
              <textarea rows="6" cols="80" name="description"></textarea>
           </td>
      </tr>
      <tr>
          <td>上传图片</td>
          <td><input type="file" name="img"/></td>
      </tr>
      <tr>
           <td colspan="2">
           <input type="submit" value="添加商品"/>
           
           </td>
      </tr>
</table>
</form>

</body>
</html>