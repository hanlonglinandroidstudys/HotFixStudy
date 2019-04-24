# 热修复（tinker原理）

* 核心原理：

参考有道云 [ClassLoader的机制和原理](https://note.youdao.com/web/#/file/55C42675BFEA40EE9C909551EB93F3FA/note/WEB861c417354f9511911f7285c4a2c5c6f/)

* 实现步骤：

    1. 通过反射得到 BaseDexClassLoader 中的pathList属性;

    2. 通过反射得到pathList对象中的dexElements属性;

    3. 加载外部的修复文件 .dex , 并将其转化为Elements[] ;

    4. 将我们自己加载的Elements[] 加到 dexElements的前面，组成新的数组 newElements[];
    
    5. 使用newElements[] 替换 dexElements


代码见 ：
>> fix\Fix.java

* 生成 .dex 文件步骤 ：

    1. 找到Android 安装目录下对应版本的 dx.bat 文件，并将路径添加到环境变量中。

    2. 使用命令 dx --dex --output [d:\fix.dex] [c:\android\aa]
    注释： 第一个路径是 生成的.dex路径和文件名 ，第二个路径是 需要打包的class路径，注意这里是完整的包路径，
    例如全类名是 com.aa.Fix ，Fix.class路径是 c:\android\path\com\aa\Fix.class, 则需要填的路径是 c:\android\path, 打包会将目录下所有的.class文件打进.dex中


 