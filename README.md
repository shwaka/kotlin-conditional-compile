Conditional compilation (such as `#ifdef DEBUG`) in kotlin

もっと良い方法ないの？

## Usage

Published at [shwaka/maven](https://github.com/shwaka/maven)

```kotlin
// build.gradle.kts
repositories {
    maven(url = "https://shwaka.github.io/maven/")
}

dependencies {
    // Any other condition can be used here:
    if (System.getProperty("debug") == null) {
        implementation("com.github.shwaka.kococo:kococo-release-jvm:0.1")
    } else {
        implementation("com.github.shwaka.kococo:kococo-debug-jvm:0.1")
    }
}
```

```kotlin
import com.github.shwaka.kococo.debugOnly

fun main() {
    debugOnly {
        println("This should be shown only in debug mode!")
    }
}
```

```bash
./gradlew run
./gradlew run -Ddebug
```

## Decompile
```bash
cd example
./gradlew build # -Ddebug
cd build/classes/kotlin/main/com/github/shwaka/kococo/example
jad -a AppKt.class
```

### debug
```java
// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) annotate
// Source File Name:   App.kt

package com.github.shwaka.kococo.example;

import java.io.PrintStream;

public final class AppKt
{

    public static final void main()
    {
        int $i$f$debugOnly = 0;
    //    0    0:iconst_0
    //    1    1:istore_0
        int $i$a$-debugOnly-AppKt$main$1 = 0;
    //    2    2:iconst_0
    //    3    3:istore_1
        String s = "This should be shown only in debug mode!";
    //    4    4:ldc1            #11  <String "This should be shown only in debug mode!">
    //    5    6:astore_2
        boolean flag = false;
    //    6    7:iconst_0
    //    7    8:istore_3
        System.out.println(s);
    //    8    9:getstatic       #17  <Field PrintStream System.out>
    //    9   12:aload_2
    //   10   13:invokevirtual   #23  <Method void PrintStream.println(Object)>
    //*  11   16:nop
    //*  12   17:nop
    //   13   18:return
    }

    public static void main(String args[])
    {
        main();
    //    0    0:invokestatic    #9   <Method void main()>
    //    1    3:return
    }
}
```

### release
```java
// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) annotate
// Source File Name:   App.kt

package com.github.shwaka.kococo.example;


public final class AppKt
{

    public static final void main()
    {
        int $i$f$debugOnly = 0;
    //    0    0:iconst_0
    //    1    1:istore_0
    //*   2    2:nop
    //    3    3:return
    }

    public static void main(String args[])
    {
        main();
    //    0    0:invokestatic    #9   <Method void main()>
    //    1    3:return
    }
}
```


## Publish
1. `./publish.sh`
2. Make sure to "publish" in bintray from the web browser
