



# AndroidAnimation

 Android 高级动画Demo

### VectorDrawable基础知识

#### SVG和Vector差异

- SVG————前端中使用，是一套语法规范

- Vector————在Android中使用

  > Vector只实现了SVG语法中的Path标签

#### Vector的常用语法

> M = moveto(M X,Y)  : 将画笔移动到指定的坐标位置
>
> L = lineto(L X,Y)  :  画直线到指定的坐标位置
>
> Z = closepath()  :  关闭路径
>
> H = horizontal lineto(H X) : 画水平线到指定的X坐标
>
> Y = vertical lineto(V Y) : 画垂直线到指定的Y坐标

#### SVG编辑器

[SVG Editor](http://editor.method.ac/)

![](img\svg_editor.png)

##### SVG转换VectorDrawable

- 由于Android原生支持的是Vector，所以可以将SVG转换成vector——[SVG2Android](http://inloop.github.io/svg2android/)

  ![](img\svg2Android.png)

- 也可以通过Android studio自带的**Vector Asset**工具将SVG转换成vector，在**res**下的**drawable**目录右击选择**new**，然后选择**Vector Asset**即可。

  ![](img\vector_asset.jpg)

###### 转换效果对比

![](img\result.jpg)

##### SVG图片资源

[SVG图片资源](http://www.iconfont.cn/plus/collections/index)

![](img\svg_download.png)

#### VectorDrawable兼容性

- Android L，只兼容minSDK>=21的版本

  > 几乎没有兼容性

- Gradle Plugin 1.5

  - 设备版本>=21——使用Vector
  - 设备版本<21——将Vector转换为PNG

  > 增加了兼容的成本，效果也有限

- AppCompat23.2

  - 静态Vector支持Android2.1+
  - 动态Vector支持Android3.0+

  > 几乎可以兼容大部分使用场景

#### 使用静态的VectorDrawable

- 配置引用和参数

  app的gradle文件中加入
  ```kotlin
    android {
        ...
        defaultConfig {
            ...
            vectorDrawables.useSupportLibrary = true
        }
    }
  ```

- Vector图像标签

  ```xml
<!-- 绘制一个矩形 -->
  <vector xmlns:android="http://schemas.android.com/apk/res/android"
  	android:width="48dp"
  	android:height="48dp"
  	android:viewportWidth="200"
  	android:viewportHeight="200">
  
  	<path
  		android:name="square"
  		android:fillColor="#272636"
  		android:pathData="M50,50 L100,50 L100,100 L50,100z"/>
  </vector>
  ```
  
  - **android:width\android:height** : 定义图片的宽高
  - **android:viewportWidth\android:viewportHeight** : 定义图像被划分的比例大小
  
- 在控件中使用

  - **ImageView\ImageButton**中使用：直接**android:src(minSDK>=21)\app:srcCompat=”@drawable/vector_image“(minSDK<21)**即可

  - **Button**中使用:需要通过selector来进行设置，并开启下面的设置

    ```java
    static {
      AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }
    ```
    
    > 如果没加静态代码块，５.0下会Crash。5.0以上可以正常显示。

#### 使用动态的VectorDrawable

- 配置动画粘合剂————animated-vector

  ```xml
  <?xml version="1.0" encoding="utf-8"?>
  <animated-vector              xmlns:android="http://schemas.android.com/apk/res/android"   android:drawable="@drawable/ic_arrows">  
      <target      
              android:animation="@animator/anim_left"      
              android:name="left"/>   
      <target      
              android:animation="@animator/anim_right"      
              android:name="right"/>
  </animated-vector>
  ```

  其中的**@animator/anim_left**和**@animator/anim_right**就是想要实现的属性动画效果。

- 属性动画

  ```xml
  <?xml version="1.0" encoding="utf-8"?>
  <objectAnimator 
         xmlns:android="http://schemas.android.com/apk/res/android"
         android:duration="1000"   
         android:interpolator="@android:interpolator/bounce"   
         android:propertyName="translateX"   
         android:repeatCount="infinite"   
         android:repeatMode="reverse"   
         android:valueFrom="0"   
         android:valueTo="10"   
         android:valueType="floatType">
  </objectAnimator>
  ```

- 使用

  Xml中使用，将animated-vector的xml文件设置给ImageView的src属性(minSDK>=21)或app:srcCompat(minSDK<21)属性

  ```xml
  <ImageView   
             android:id="@+id/iv_arrow"   
             android:layout_width="100dp"   
             android:layout_height="100dp"   
             android:src="@drawable/arrow_anim"   
             android:contentDescription="@string/app_name" />
  ```

  代码中启动动画即可

  ```java
  Drawable drawable = imageView.getDrawable();
  if (drawable instanceof Animatable) {    
      ((Animatable) drawable).start();
  }
  ```

##### 动态VectorDrawable兼容性问题

- 向下兼容问题
  - **Path Morphing**——路径转换动画，在Android pre-L版本下是无法使用
  - **Path Interpolation**——路径差值器，在Android pre-L版本只能使用系统提供的差值器，不能自定义
- 向上兼容问题
  - 抽取string兼容问题——不支持从strings.xml中读取<PathData>

#### VectorDrawable使用场景

- Vector vs Bitmap
  1. Bitmap的绘制效率并不一定会比Vector高，他们有一定的平衡点，当Vector比较简单时，其效率是一定比Bitmap高的，所以，为了保证Vector的高效率，Vector需要更加简单，PathData更加标准、精简，当Vector的图像变得非常复杂时，就需要使用Bitmap来代替了。
  2. Vector适用于ICON、Button、ImageView等图标等小ICON，或者是需要动画的效果，由于Bitmap在GPU中有缓存功能，而Vector并没有，所以Vector图像不能做频繁的重绘。
  3. Vector图像过于复杂时，不仅仅要注意绘制效率，初始化效率也是需要考虑的重要因素。
  4. SVG加载速度会快于PNG，但渲染速度会低于PNG，毕竟PNG有硬件加速，但平均下来，加载速度的提升弥补了渲染的速度缺陷。

### Bezier曲线

**Bézier curve(**[贝塞尔曲线](https://baike.baidu.com/item/%E8%B4%9D%E5%A1%9E%E5%B0%94%E6%9B%B2%E7%BA%BF/1091769?fr=aladdin)**)**是应用于二维图形应用程序的[数学曲线](http://baike.baidu.com/view/627248.htm)。 曲线定义：起始点、终止点（也称锚点）、控制点。通过调整控制点，贝塞尔曲线的形状会发生变化。 1962年，法国数学家**Pierre Bézier**第一个研究了这种[矢量](http://baike.baidu.com/view/77474.htm)绘制曲线的方法，并给出了详细的计算公式，因此按照这样的公式绘制出来的曲线就用他的姓氏来命名，称为贝塞尔曲线。

![一阶贝塞尔曲线](img\one_bezier.webp)

![二阶贝塞尔曲线](img\two_bezier.webp)

![三阶贝塞尔曲线](img\three_bezier.webp)

![四阶贝塞尔曲线](img\four_bezier.webp)

![五阶贝塞尔曲线](img\five_bezier.webp)

