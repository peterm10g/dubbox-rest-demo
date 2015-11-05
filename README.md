#Dubbox-rest-demo

## 外部应用服务器的方式启动(采用了tomcat)<br/>

### maven整合的启动方式：<br/>
    需要在pom的plugins中加入tomcat plugin,具体参见user-provider的pom文件。

### 原生tomcat启动方式：
    需要把打包好的war包放入tomcat下即可。
## 嵌入式tomcat启动
    参见user-provider 中的 appMain类
    注意：需要在web.xml中加入
    <listener>
        <listener-class>com.alibaba.dubbo.remoting.http.servlet.BootstrapListener</listener-class>
    </listener>
    否则抛出com.alibaba.dubbo.rpc.RpcException: No servlet context found. If you are using server='servlet', make sure that   you've configured com.alibaba.dubbo.remoting.http.servlet.BootstrapListener in web.xml
