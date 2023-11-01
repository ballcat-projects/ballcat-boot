# ballcat-boot

此项目是 Ballcat 单体应用的模板项目。

用户可以基于此模板项目进行业务的定制开发。

项目环境搭建参看文档 [快速搭建](https://docs.ballcat.org/guide/quick-start.html) 一章。

## 注意事项

### 代码格式化

由于项目使用了 spring-javaformat 插件进行格式管理，打包前请执行以下 maven 命令进行格式化，否则打包会失败：
```shell
mvn spring-javaformat:apply
```

使用 mvn 命令需要添加 maven 的环境变量， 如果没有添加，使用 idea 的用户可以通过 idea 右上角的 maven 工具进行格式化，  
点击 ballcat-boot 的 Plugins -> spring-javaformat -> spring-javaformat:apply 进行格式化。


### JDK11 使用

1. 默认格式化基于 jdk8 环境, 在 jdk11 下使用时请删除 .spirngjavaformat 文件，
或者修改其中的内容为 中的 `java-baseline=11`

2. 由于 jdk11 剔除了部分 javax 的包，如果使用 jdk11, 请将 pom.xml 中以下依赖的注释打开
```xml
    <dependency>
        <groupId>jakarta.xml.bind</groupId>
        <artifactId>jakarta.xml.bind-api</artifactId>
    </dependency>
    <dependency>
        <groupId>org.glassfish.jaxb</groupId>
        <artifactId>jaxb-runtime</artifactId>
    </dependency>
```

