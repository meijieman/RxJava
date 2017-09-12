## 引入 lambda

两种方法(方法一是原生态的，方法二是第三方库的形式，推荐以第三方库的方法)

方法一（原生态）：

在App下的 `build.gradle` 文件中输入以下内容： 
```gradle
android { 
...
defaultConfig {

    jackOptions {
        enabled true
    }
}
```
```gradle
android { 
...
compileOptions { 
    sourceCompatibility JavaVersion.VERSION_1_8 
    targetCompatibility JavaVersion.VERSION_1_8 
} 

}
```

---

方法二(第三方库，推荐使用)：

在项目中添加retrolambda库

1.项目的build.gradle中添加retrolambda库
`classpath 'me.tatarka:gradle-retrolambda:3.4.0'`

2.app下的build.gradle中添加
`apply plugin: 'me.tatarka.retrolambda'`

还是需要添加
```gradle
android { 
...
compileOptions { 
    sourceCompatibility JavaVersion.VERSION_1_8 
    targetCompatibility JavaVersion.VERSION_1_8 
} 

}
```
```
allprojects {
    repositories {
        mavenCentral()
        jcenter()
    }
}
```



**注：推荐使用第第三方库的使用，如果使用android官方的，会和ButterKnife冲突，在绑定id的时候回出现空指针**

参考: http://blog.csdn.net/jianesrq0724/article/details/54892943

