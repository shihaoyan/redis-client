# 工程简介

#开发目的
目前市面上所有的redis客户端都是C/S结构或者以插件的形式存在的，但是很不方便，因为如果换电脑就需要重新弄一个客户端
所以我想设计一款能够直接在web上进行访问redis数据库的项目。
#开发思路
1. 设计一个登录界面
    
    登录界面需要包含host、port、password、database，当然其中
host、port、password、database都会采用默认值，都能够进行修改。
   
2. 主界面通过类似于客户端的显示方式进行显示value

3. 需要能够对数据库进行增删操作，当然这个东西需要设计一个权限控制。

#当前版本
目前只能够进行访问localhost本机redis，界面还没有弄，只有简单的reids操作。

#当前API

https://localhost:8080/keys ##（查看所有的keys）

https://localhost:8080/keys/abc ##（模糊匹配 `*abc*`）

https://localhost:8080/keys/abc* ##（模糊匹配 `abc*`）

https://localhost:8080/keys/*abc ##（模糊匹配 `*abc`）

https://localhost:8080/keys/*abc* ##（模糊匹配 `*abc*`）

https://localhost:8080/get/abc ##（精确匹配）

https://localhost:8080/set/{key}:{value} ##（设置值）

https://localhost:8080/delete/abc ##（删除值）
